package com.bylan.dcybackend.utils;

import cn.hutool.extra.spring.SpringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author Rememorio
 * @date 2022-04-20 16:45:20
 */
@Component
public class RedisUtil {

    private static final Logger log = LogManager.getLogger(RedisUtil.class);

    @SuppressWarnings("unchecked")
    private static final RedisTemplate<String, Object> REDIS_TEMPLATE = SpringUtil.getBean(RedisTemplate.class);

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 秒
     */
    public static void expire(String key, long time) {
        if (time > 0) {
            Boolean res = REDIS_TEMPLATE.expire(key, time, TimeUnit.SECONDS);
            log.info("指定缓存失效时间：" + key + "，结果：" + res);
        }
    }

    /**
     * 指定缓存失效时间
     *
     * @param key      键
     * @param time     秒
     * @param timeUnit 单位
     */
    public static void expire(String key, long time, TimeUnit timeUnit) {
        if (time > 0) {
            REDIS_TEMPLATE.expire(key, time, timeUnit);
        }
    }

    /**
     * 获取过期时间
     *
     * @param key 键 不能为null
     * @return 单位：秒，0代表永久有效，-2代表无效key
     */
    public static long getExpire(String key) {
        if (null == key) {
            return -2L;
        }
        Long res = REDIS_TEMPLATE.getExpire(key, TimeUnit.SECONDS);
        if (null == res) {
            return -2L;
        }
        return res;
    }

    /**
     * 查找匹配key
     *
     * @param pattern key，*key*，*key，key*
     * @return 匹配的所有key
     */
    public static List<String> scan(String pattern) {
        ScanOptions options = ScanOptions.scanOptions().match(pattern).build();
        RedisConnectionFactory factory = REDIS_TEMPLATE.getConnectionFactory();
        RedisConnection rc = Objects.requireNonNull(factory).getConnection();
        Cursor<byte[]> cursor = rc.scan(options);
        List<String> result = new ArrayList<>();
        while (cursor.hasNext()) {
            result.add(new String(cursor.next()));
        }
        RedisConnectionUtils.releaseConnection(rc, factory);
        return result;
    }

    /**
     * 分页查询 key
     *
     * @param patternKey key
     * @param page       页码，0开始
     * @param size       每页数目
     * @return 没有返回size=0数组
     */
    public static List<String> findKeysPage(String patternKey, int page, int size) {
        ScanOptions options = ScanOptions.scanOptions().match(patternKey).build();
        RedisConnectionFactory factory = REDIS_TEMPLATE.getConnectionFactory();
        RedisConnection rc = Objects.requireNonNull(factory).getConnection();
        Cursor<byte[]> cursor = rc.scan(options);
        List<String> result = new ArrayList<>(size);
        int tmpIndex = 0;
        int fromIndex = page * size;
        int toIndex = page * size + size;
        while (cursor.hasNext()) {
            if (tmpIndex >= fromIndex && tmpIndex < toIndex) {
                result.add(new String(cursor.next()));
                tmpIndex++;
                continue;
            }
            // 获取到满足条件的数据后,就可以退出了
            if (tmpIndex >= toIndex) {
                break;
            }
            tmpIndex++;
            cursor.next();
        }
        RedisConnectionUtils.releaseConnection(rc, factory);
        return result;
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true-存在，false-不存在
     */
    public static boolean hasKey(String key) {
        Boolean res = REDIS_TEMPLATE.hasKey(key);
        if (null == res) {
            return false;
        }
        return res;
    }

    /**
     * 删除缓存，有几个key就删除几个
     *
     * @param keys 可以传一个值或多个
     */
    public static void del(String... keys) {
        if (keys != null && keys.length > 0) {
            Set<String> keySet = new HashSet<>(Arrays.asList(keys));
            Long count = REDIS_TEMPLATE.delete(keySet);
            log.info("计划删除缓存：" + keySet + "，成功删除：" + count + "个");
        }
    }

    // ===================================Key Value===================================

    /**
     * 获取
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        return key == null ? null : REDIS_TEMPLATE.opsForValue().get(key);
    }

    /**
     * 批量获取
     *
     * @param keys 键
     * @return 值
     */
    public static List<Object> multiGet(Set<String> keys) {
        return REDIS_TEMPLATE.opsForValue().multiGet(keys);
    }

    /**
     * 存，无限期
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, Object value) {
        REDIS_TEMPLATE.opsForValue().set(key, value);
    }

    /**
     * 存，并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  单位：秒，如果time小于等于0将设置无限期
     */
    public static void set(String key, Object value, long time) {
        if (time > 0) {
            set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
    }

    /**
     * 存，并设置时间
     *
     * @param key      键
     * @param value    值
     * @param time     时间
     * @param timeUnit 类型
     */
    public static void set(String key, Object value, long time, TimeUnit timeUnit) {
        if (time > 0) {
            REDIS_TEMPLATE.opsForValue().set(key, value, time, timeUnit);
        } else {
            set(key, value);
        }
    }

    // ================================Hash=================================

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true-存在，false-不存在
     */
    public static boolean hHasKey(String key, String item) {
        return REDIS_TEMPLATE.opsForHash().hasKey(key, item);
    }

    /**
     * Hash，Get
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public static Object hGet(String key, String item) {
        return REDIS_TEMPLATE.opsForHash().get(key, item);
    }

    /**
     * Hash，获取所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<Object, Object> hEntries(String key) {
        return REDIS_TEMPLATE.opsForHash().entries(key);
    }

    /**
     * Hash，存
     *
     * @param key 键
     * @param map 对应多个键值
     */
    public static void hPutAll(String key, Map<String, Object> map) {
        REDIS_TEMPLATE.opsForHash().putAll(key, map);
    }

    /**
     * Hash，存，并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 单位：秒
     */
    public static void hPutAll(String key, Map<String, Object> map, long time) {
        REDIS_TEMPLATE.opsForHash().putAll(key, map);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * Hash，存项
     *
     * @param key   键
     * @param item  项
     * @param value 值
     */
    public static void hPut(String key, String item, Object value) {
        REDIS_TEMPLATE.opsForHash().put(key, item, value);
    }

    /**
     * Hash，删除
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个不能为null
     */
    public static void hDel(String key, Object... item) {
        Long num = REDIS_TEMPLATE.opsForHash().delete(key, item);
        log.info("Hash计划删除key：" + key + "，item：" + Arrays.toString(item) + "，实际删除：" + num + "个");
    }

    /**
     * hash递增，仅适用于value是数字的值，如果不存在就创建
     *
     * @param key  键
     * @param item 项
     * @param by   要增加数
     * @return 新增后的值
     */
    public static double hincr(String key, String item, double by) {
        return REDIS_TEMPLATE.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减，仅适用于value是数字的值，如果不存在就创建
     *
     * @param key  键
     * @param item 项
     * @param by   要减少数
     * @return 递减后的值
     */
    public static double hdecr(String key, String item, double by) {
        return REDIS_TEMPLATE.opsForHash().increment(key, item, -by);
    }

    // ============================Set=============================

    /**
     * Set，根据key获取Set中的所有值
     *
     * @param key 键
     * @return Set中的所有值
     */
    public static Set<Object> setMembers(String key) {
        return REDIS_TEMPLATE.opsForSet().members(key);
    }

    /**
     * Set，是否存在某个值
     *
     * @param key   键
     * @param value 值
     * @return true-存在，false-不存在
     */
    public static boolean setIsMember(String key, Object value) {
        Boolean isMember = REDIS_TEMPLATE.opsForSet().isMember(key, value);
        if (null == isMember) {
            return false;
        }
        return isMember;
    }

    /**
     * Set，长度
     *
     * @param key 键
     * @return 长度
     */
    public static long setSize(String key) {
        Long size = REDIS_TEMPLATE.opsForSet().size(key);
        if (null == size) {
            return 0;
        }
        return size;
    }

    /**
     * Set，存
     *
     * @param key    键
     * @param values 值，可以是多个
     * @return 成功存入个数
     */
    public static long setAdd(String key, Object... values) {
        Long num = REDIS_TEMPLATE.opsForSet().add(key, values);
        if (null == num) {
            return 0;
        }
        return num;
    }

    /**
     * Set，存，并设置过期时间
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功存入个数
     */
    public static long setAddTime(String key, long time, Object... values) {
        Long count = REDIS_TEMPLATE.opsForSet().add(key, values);
        if (time > 0) {
            expire(key, time);
        }
        if (null == count) {
            return 0;
        }
        return count;
    }

    /**
     * Set，弹出
     *
     * @param key key
     * @return 一个值
     */
    public static Object setPop(String key) {
        return REDIS_TEMPLATE.opsForSet().pop(key);
    }

    /**
     * Set，移除值为value的值
     *
     * @param key    键
     * @param values 值，可以是多个
     * @return 实际移除的个数
     */
    public static long setRemove(String key, Object... values) {
        Long count = REDIS_TEMPLATE.opsForSet().remove(key, values);
        log.info("Set计划删除key：" + key + "，values：" + Arrays.toString(values) + "，实际删除：" + count + "个");
        if (null == count) {
            return 0;
        }
        return count;
    }

    // ===============================List=================================

    /**
     * List，获取
     *
     * @param key   键
     * @param start 开始，包含，0开始
     * @param end   结束，不包含
     * @return 数据集合
     */
    public static List<Object> listRange(String key, long start, long end) {
        return REDIS_TEMPLATE.opsForList().range(key, start, end);
    }

    /**
     * List，长度
     *
     * @param key 键
     * @return 长度
     */
    public static long listSize(String key) {
        Long size = REDIS_TEMPLATE.opsForList().size(key);
        if (null == size) {
            return 0;
        }
        return size;
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引index>=0时，0表头；index<0时，-1表尾
     * @return 值
     */
    public static Object listIndex(String key, long index) {
        return REDIS_TEMPLATE.opsForList().index(key, index);
    }

    /**
     * List，右侧放入
     *
     * @param key   键
     * @param value 值
     * @return 数组长度
     */
    public static long listRightPush(String key, Object value) {
        Long num = REDIS_TEMPLATE.opsForList().rightPush(key, value);
        if (null == num) {
            return 0;
        }
        return num;
    }

    /**
     * List，右侧放入多个
     *
     * @param key   键
     * @param value 值
     * @return 数组长度
     */
    public static long listRightPushAll(String key, List<Object> value) {
        Long num = REDIS_TEMPLATE.opsForList().rightPushAll(key, value);
        if (null == num) {
            return 0;
        }
        return num;
    }

    /**
     * List，value1右侧放入，如果没有value1则不放入
     *
     * @param key    键
     * @param value1 值
     * @param value2 值
     * @return 数组长度，失败返回-1
     */
    public static long listRightPush(String key, Object value1, Object value2) {
        Long num = REDIS_TEMPLATE.opsForList().rightPush(key, value1, value2);
        if (null == num) {
            return 0;
        }
        return num;
    }

    /**
     * List，右侧放入，key值必须存在
     *
     * @param key   键
     * @param value 值
     * @return 数组长度，失败返回-1
     */
    public static long listRightPushIfPresent(String key, Object value) {
        Long num = REDIS_TEMPLATE.opsForList().rightPushIfPresent(key, value);
        if (null == num) {
            return 0;
        }
        return num;
    }

    /**
     * List，左侧放入
     *
     * @param key   键
     * @param value 值
     * @return 数组长度
     */
    public static long listLeftPush(String key, Object value) {
        Long num = REDIS_TEMPLATE.opsForList().leftPush(key, value);
        if (null == num) {
            return 0;
        }
        return num;
    }

    /**
     * List，左侧放入，多个
     *
     * @param key    键
     * @param values 值
     * @return 数组长度
     */
    public static long listLeftPushAll(String key, List<Object> values) {
        Long num = REDIS_TEMPLATE.opsForList().leftPushAll(key, values);
        if (null == num) {
            return 0;
        }
        return num;
    }

    /**
     * List，左侧放入，当key存在的时候
     *
     * @param key   键
     * @param value 值
     * @return 数组长度，失败返回-1
     */
    public static long listLeftPushIfPresent(String key, Object value) {
        Long num = REDIS_TEMPLATE.opsForList().leftPushIfPresent(key, value);
        if (null == num) {
            return 0;
        }
        return num;
    }

    /**
     * List，value1左侧放入，没有value1则不放入
     *
     * @param key    键
     * @param value1 值1
     * @param value2 值2
     * @return 数组长度，失败返回-1
     */
    public static long listLeftPush(String key, Object value1, Object value2) {
        Long num = REDIS_TEMPLATE.opsForList().leftPush(key, value1, value2);
        if (null == num) {
            return 0;
        }
        return num;
    }

    /**
     * List，根据索引修改某条数据
     * 索引不正确会抛出异常：ERR index out of range
     *
     * @param key   键
     * @param index 索引，0开始
     * @param value 值
     */
    public static void listSet(String key, long index, Object value) {
        REDIS_TEMPLATE.opsForList().set(key, index, value);
    }

    /**
     * List，移除N个值为value，左边开始移出
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     */
    public static void listRemove(String key, long count, Object value) {
        Long num = REDIS_TEMPLATE.opsForList().remove(key, count, value);
        log.info("List计划移出元素" + value + count + "个，实际移出" + num + "个");
    }
}

package com.bylan.dcybackend.component;

import com.bylan.dcybackend.dao.TeacherDAO;
import com.bylan.dcybackend.service.ReachService;
import com.bylan.dcybackend.vo.HomeCourseReachVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 达成度计算组件
 *
 * @author Rememorio
 * @date 2022-04-20 16:16
 */
@Component
public class ReachCalculateTask {

    private static final Logger log = LogManager.getLogger(ReachCalculateTask.class);

    @Autowired
    ReachService reachService;

    @Autowired
    TeacherDAO teacherDAO;

    private static ConcurrentHashMap<String, List<HomeCourseReachVO>> teacherHomeReachMap = new ConcurrentHashMap<>();

    /**
     * PostConstruct注解：在依赖注入完成后自动调用
     * Scheduled注解：定时任务，每天凌晨4点执行一次计算
     * cron表达式：秒 分 时 日 月 星期 年
     */
    //@PostConstruct
    @Scheduled(cron = "0 0 4 * * ? ")
    public void calculateHomeReach() {
        log.info("执行定时任务");
        List<String> teacherIds = teacherDAO.queryAllId();
        for (String teacherId : teacherIds) {
            List<Long> courseIds = reachService.mGetCourseIdByTeacherId(teacherId);
            List<HomeCourseReachVO> courseReachVOList = reachService.mGetCourseReachByCourseId(courseIds);
            if (courseReachVOList != null) {
                teacherHomeReachMap.put(teacherId, courseReachVOList);
            }
        }
        log.info("teacherHomeReachMap: {}", teacherHomeReachMap);
    }

    public static List<HomeCourseReachVO> getHomeReach(String teacherId) {
        if (teacherHomeReachMap.containsKey(teacherId)) {
            return teacherHomeReachMap.get(teacherId);
        }
        return null;
    }

    public static boolean setHomeReach(String teacherId, List<HomeCourseReachVO> courseReachVOList) {
        if (courseReachVOList == null) {
            return false;
        }
        teacherHomeReachMap.put(teacherId, courseReachVOList);
        return true;
    }

}

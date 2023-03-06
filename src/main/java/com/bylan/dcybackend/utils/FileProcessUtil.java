package com.bylan.dcybackend.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.bylan.dcybackend.domain.Constant.Path.ROOT;
import static com.bylan.dcybackend.domain.Constant.Public.TIME_FORMAT_FOR_FILE;

/**
 * 文件处理工具类
 *
 * @author Rememorio
 */
public class FileProcessUtil {

    private static final Logger log = LogManager.getLogger(FileProcessUtil.class);

    /**
     * 将文件名解析成文件的上传路径
     *
     * @param filename 文件名
     * @return 上传到服务器的文件名
     */
    public static String convertFilename(String filename) {
        Date date = new Date();
        // 定义到毫秒
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_FOR_FILE);
        String nowStr = sdf.format(date);
        // 去掉后缀的文件名
        String filenameStr = filename.substring(0, filename.lastIndexOf("."));
        // 后缀
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        // 如果名称不为"",说明该文件存在，否则说明该文件不存在
        if (!"".equals(filename.trim())) {
            // 定义上传路径
            filename = filenameStr + "_" + nowStr + "." + suffix;
        }
        return filename;
    }

    /**
     * 上传文件，返回处理之后的文件路径
     *
     * @param file     文件
     * @param path     文件路径（前面不用带/)
     * @param filename 文件名（前面不用带/)
     * @return 处理之后的文件路径
     */
    public static String uploadFile(MultipartFile file, String path, String filename) {
        // 转化为系统的文件名称，防止重名
        filename = convertFilename(filename);
        String rootFilePath = String.join(File.separator, ROOT, path, filename);
        // 把文件以字节类型写入到上传的路径
        System.out.println(rootFilePath);
        try {
            FileUtil.writeBytes(file.getBytes(), rootFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传文件失败 堆栈信息: {}; 文件名：{}", e, file.getName());
            return "";
        }
        return filename;
    }

    /**
     * 下载文件
     *
     * @param path     包括文件名的文件路径
     * @param response 响应
     */
    public static void downloadFile(String path, HttpServletResponse response) {
        // 创建一个文件对象
        File file = FileUtil.file(path);
        // 文件命名输入
        String filename = FileNameUtil.getName(file);
        // 转化为系统路径
        path = String.join(File.separator, ROOT, path);
        log.info("download from {}", path);
        try {
            // 对文件名进行编码处理中文问题
            filename = java.net.URLEncoder.encode(filename, StandardCharsets.UTF_8);
            response.reset();
            response.setCharacterEncoding("UTF-8");
            // 不同类型的文件对应不同的MIME类型
            response.setContentType("application/x-msdownload");
            // inline在浏览器中直接显示，不提示用户下载
            // attachment弹出对话框，提示用户进行下载保存本地
            // 默认为inline方式
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            // 通过文件的路径读取文件字节流
            byte[] bytes = FileUtil.readBytes(path);
            // 通过输出流返回文件
            // 新建一个输出流对象变量，为了保存到硬盘里
            OutputStream os = response.getOutputStream();
            os.write(bytes);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("下载文件失败 堆栈信息: {}; 文件名：{}", e, file.getName());
        }
    }

    /**
     * 删除某个路径下的文件
     *
     * @param path 文件路径
     * @return 成功
     */
    public static boolean deleteFile(String path) {
        String rootFilePath = String.join(File.separator, ROOT, path);
        File file = new File(rootFilePath);
        log.info("文件[{}]存在状态：{}", rootFilePath, file.exists());
        return file.delete();
    }
}
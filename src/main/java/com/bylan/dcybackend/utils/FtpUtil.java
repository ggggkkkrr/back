package com.bylan.dcybackend.utils;

import com.jcraft.jsch.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @author wuhuaming
 * @date 2022/4/25 10:32
 */
@Component
public class FtpUtil {
    private static final Logger log = LogManager.getLogger(FtpUtil.class);

    private static String host;

    private static int port;

    private static String userName;

    private static String password;

    private static String rootPath;

    private static String fileUrl;

    @Value("${ftp.host}")
    public void setHost(String host) {
        FtpUtil.host = host;
    }

    @Value("${ftp.port}")
    public void setPort(int port) {
        FtpUtil.port = port;
    }

    @Value("${ftp.userName}")
    public void setUserName(String userName) {
        FtpUtil.userName = userName;
    }

    @Value("${ftp.password}")
    public void setPassword(String password) {
        FtpUtil.password = password;
    }

    @Value("${ftp.rootPath}")
    public void setRootPath(String rootPath) {
        FtpUtil.rootPath = rootPath;
    }

    @Value("${ftp.fileUrl}")
    public void setFileUrl(String fileUrl) {
        FtpUtil.fileUrl = fileUrl;
    }

    public static String getHost() {
        return host;
    }

    public static int getPort() {
        return port;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getPassword() {
        return password;
    }

    public static String getRootPath() {
        return rootPath;
    }

    public static String getFileUrl() {
        return fileUrl;
    }

    private static ChannelSftp getChannel() throws Exception {
        JSch jsch = new JSch();

        // ->ssh root@host:port
        log.info("用户名为:{}", userName);
        log.info("host为:{}", host);
        log.info("password为:{}", password);
        log.info("rootPath为:{}", rootPath);
        log.info("fileUrl为:{}", fileUrl);
        Session sshSession = jsch.getSession(userName, host, port);
        // 密码
        sshSession.setPassword(password);

        Properties sshConfig = new Properties();
        // 设置第一次登陆的时候提示，可选值：(ask | yes | no)
        sshSession.setConfig("StrictHostKeyChecking", "no");
        sshSession.setConfig(sshConfig);
        // 设置登陆超时时间
        // 注意！！这里不设置超时间会报错
        sshSession.connect(60000);

        ChannelSftp channelSftp = (ChannelSftp) sshSession.openChannel("sftp");
        channelSftp.connect(1000);

        Class cl = ChannelSftp.class;

        Field f1 =cl.getDeclaredField("server_version");
        f1.setAccessible(true);
        f1.set(channelSftp, 2);
        channelSftp.setFilenameEncoding("utf8");
        return channelSftp;
    }

    public static String putFile(InputStream inputStream, String fileName, String type) {
        try {
            ChannelSftp sftp = getChannel();

            String path = rootPath + "/" + type + "/";

            createDir(path,sftp);
            // 上传文件
            sftp.put(inputStream, path+fileName);
            log.info("上传的文件为"+fileName);
            log.info("上传成功！");
            sftp.quit();
            sftp.exit();

            // 处理返回的路径
            String resultFile;
            resultFile = fileUrl + "/" + type + "/" + fileName;

            return resultFile;

        } catch (Exception e) {
            log.error("上传失败：" + e.getMessage());
             e.printStackTrace();
        }
        return "";
    }

    public static boolean createDir(String createpath, ChannelSftp sftp) {
        try
        {
            if (isDirExist(createpath,sftp)) {
                sftp.cd(createpath);
                return true;
            }
            String pathArry[] = createpath.split("/");
            StringBuffer filePath = new StringBuffer("/");
            for (String path : pathArry) {
                if (path.equals("")) {
                    continue;
                }
                filePath.append(path + "/");
                if (isDirExist(filePath.toString(),sftp)) {
                    sftp.cd(filePath.toString());
                } else {
                    // 建立目录
                    sftp.mkdir(filePath.toString());
                    // 进入并设置为当前目录
                    sftp.cd(filePath.toString());
                }
            }
            sftp.cd(createpath);
            return true;
        }
        catch (SftpException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean isDirExist(String directory, ChannelSftp sftp)
    {
        boolean isDirExistFlag = false;
        try
        {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        }
        catch (Exception e)
        {
            if (e.getMessage().toLowerCase().equals("no such file"))
            {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }

}

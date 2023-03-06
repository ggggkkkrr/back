package com.bylan.dcybackend.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 压缩工具类
 *
 * @author Rememorio
 * @date 2022-05-23 11:00
 */
public class ZipUtil {

    private static final Logger log = LogManager.getLogger(ZipUtil.class);

    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * 缓冲器大小
     */
    private static final int BUFFER = 1024;

    /**
     * 压缩得到的文件的后缀名
     */
    private static final String SUFFIX = ".zip";

    /**
     * 压缩成ZIP 方法1
     *
     * @param srcDir           压缩文件夹路径
     * @param out              压缩文件输出流
     * @param keepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(String srcDir, OutputStream out, boolean keepDirStructure) {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile, zos, sourceFile.getName(), keepDirStructure);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("方法1压缩失败：{}", e.getMessage());
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 压缩成ZIP 方法2
     *
     * @param srcFiles 需要压缩的文件列表
     * @param out      压缩文件输出流
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(List<File> srcFiles, OutputStream out) {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("方法2压缩失败：{}", e.getMessage());
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 压缩成ZIP 方法3
     *
     * @param srcIos 待压缩文件输入流
     * @param out    压缩文件输出流
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(Map<String, InputStream> srcIos, OutputStream out) {

        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            for (String fileName : srcIos.keySet()) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(fileName));
                int len;
                InputStream srcIo = srcIos.get(fileName);
                while ((len = srcIo.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                srcIo.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("方法3压缩失败：{}", e.getMessage());
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 递归压缩方法
     *
     * @param sourceFile       源文件
     * @param zos              zip输出流
     * @param name             压缩后的名称
     * @param keepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean keepDirStructure) {
        try {
            byte[] buf = new byte[BUFFER_SIZE];
            if (sourceFile.isFile()) {
                // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
                zos.putNextEntry(new ZipEntry(name));
                // copy文件到zip输出流中
                int len;
                FileInputStream in = new FileInputStream(sourceFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                // Complete the entry
                zos.closeEntry();
                in.close();
            } else {
                File[] listFiles = sourceFile.listFiles();
                if (listFiles == null || listFiles.length == 0) {
                    // 需要保留原来的文件结构时,需要对空文件夹进行处理
                    if (keepDirStructure) {
                        // 空文件夹的处理
                        zos.putNextEntry(new ZipEntry(name + File.separator));
                        // 没有文件，不需要文件的copy
                        zos.closeEntry();
                    }
                } else {
                    for (File file : listFiles) {
                        // 判断是否需要保留原来的文件结构
                        if (keepDirStructure) {
                            // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                            // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                            compress(file, zos, String.join(File.separator, name, file.getName()), keepDirStructure);
                        } else {
                            compress(file, zos, file.getName(), keepDirStructure);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("压缩失败：{}", e.getMessage());
        }
    }

    /**
     * 解压
     *
     * @param zipFileName 文件路径
     * @param destPath    解压的文件路径
     */
    public static void decompress(String zipFileName, String destPath) {
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFileName), Charset.forName("GBK"));
            ZipEntry zipEntry;
            // 缓冲器
            byte[] buffer = new byte[BUFFER];
            // 每次读出来的长度
            int readLength = 0;
            while ((zipEntry = zis.getNextEntry()) != null) {
                // 若是目录
                if (zipEntry.isDirectory()) {
                    File file = new File(String.join(File.separator, destPath, zipEntry.getName()));
                    if (!file.exists()) {
                        file.mkdirs();
                        log.info("mkdirs: {}", file.getCanonicalPath());
                        continue;
                    }
                }
                // 若是文件
                File file = createFile(destPath, zipEntry.getName());
                log.info("file created: {}", file.getCanonicalPath());
                OutputStream os = new FileOutputStream(file);
                while ((readLength = zis.read(buffer, 0, BUFFER)) != -1) {
                    os.write(buffer, 0, readLength);
                }
                os.close();
                log.info("file uncompressed: {}", file.getCanonicalPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("解压失败：{}", e.getMessage());
        }
    }

    /**
     * 创建文件
     *
     * @param destPath 解压目标路径
     * @param fileName 解压文件的相对路径
     * @return 文件
     */
    public static File createFile(String destPath, String fileName) {
        // 将文件名的各级目录分解
        String[] dirs = fileName.split(File.separator);
        File file = new File(destPath);
        // 文件有上级目录
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                // 依次创建文件对象知道文件的上一级目录
                file = new File(file, dirs[i]);
            }

            if (!file.exists()) {
                file.mkdirs();//文件对应目录若不存在，则创建
                try {
                    log.info("mkdirs: {}", file.getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("创建文件失败：{}", e.getMessage());
                }
            }
            //创建文件
            file = new File(file, dirs[dirs.length - 1]);
            return file;
        }
        // 若目标路径的目录不存在，则创建
        if (!file.exists()) {
            file.mkdirs();
            try {
                log.info("mkdirs: {}", file.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
                log.error("创建文件失败：{}", e.getMessage());
            }
        }
        // 创建文件
        file = new File(file, dirs[0]);
        return file;
    }

}
package com.excellence.basetoolslibrary.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;

import static com.excellence.basetoolslibrary.utils.EmptyUtils.isEmpty;
import static com.excellence.basetoolslibrary.utils.FileUtils.createNewFile;
import static com.excellence.basetoolslibrary.utils.FileUtils.isFileExists;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2018/9/10
 *     desc   : 文件流相关
 * </pre> 
 */
public class FileIOUtils {

    private static final int BUF_SIZE = 8 * 1024;

    /**
     * 将字符串写入文件
     *
     * @param file
     * @param content
     * @param append
     * @return
     */
    public static boolean writeFile(File file, String content, boolean append) {
        try {
            if (file == null || isEmpty(content)) {
                return false;
            }
            createNewFile(file);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
            writer.write(content);
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将字符串写入文件
     *
     * @param path
     * @param content
     * @param append
     * @return
     */
    public static boolean writeFile(String path, String content, boolean append) {
        if (isEmpty(path)) {
            return false;
        }
        return writeFile(new File(path), content, append);
    }

    /**
     * 将字节数组写入文件
     *
     * @param file
     * @param bytes
     * @param append
     * @return
     */
    public static boolean writeFile(File file, byte[] bytes, boolean append) {
        try {
            if (file == null || isEmpty(bytes)) {
                return false;
            }
            createNewFile(file);
            OutputStream os = new BufferedOutputStream(new FileOutputStream(file, append));
            os.write(bytes);
            os.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将字节数组写入文件
     *
     * @param path
     * @param bytes
     * @param append
     * @return
     */
    public static boolean writeFile(String path, byte[] bytes, boolean append) {
        if (isEmpty(path)) {
            return false;
        }
        return writeFile(new File(path), bytes, append);
    }

    /**
     * 将输入流写入文件里
     *
     * @param file
     * @param is
     * @param append
     * @param isCloseable 是否关闭输入流
     * @return
     */
    public static boolean writeFile(File file, InputStream is, boolean append, boolean isCloseable) {
        try {
            if (file == null || is == null) {
                return false;
            }
            createNewFile(file);
            OutputStream os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte[] buf = new byte[BUF_SIZE];
            int len;
            while ((len = is.read(buf, 0, buf.length)) != -1) {
                os.write(buf, 0, len);
            }
            os.close();
            if (isCloseable) {
                is.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将输入流写入文件里
     *
     * @param path
     * @param is
     * @param append
     * @param isCloseable 是否关闭输入流
     * @return
     */
    public static boolean writeFile(String path, InputStream is, boolean append, boolean isCloseable) {
        if (isEmpty(path)) {
            return false;
        }
        return writeFile(new File(path), is, append, isCloseable);
    }

    /**
     * 读取输入流为字节数组
     *
     * @param is
     * @return
     */
    public static byte[] readFile2Bytes(InputStream is) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buf = new byte[BUF_SIZE];
            int len;
            while ((len = is.read(buf, 0, buf.length)) != -1) {
                os.write(buf, 0, len);
            }
            is.close();
            os.close();
            return os.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取文件为字节数组
     *
     * @param file
     * @return
     */
    public static byte[] readFile2Bytes(File file) {
        try {
            if (!isFileExists(file)) {
                return null;
            }
            InputStream is = new FileInputStream(file);
            return readFile2Bytes(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取文件为字节数组
     *
     * @param path
     * @return
     */
    public static byte[] readFile2Bytes(String path) {
        if (isEmpty(path)) {
            return null;
        }
        return readFile2Bytes(new File(path));
    }

    /**
     * 读取输入流为字符串
     *
     * @param is
     * @param charset
     * @return
     */
    public static String readFile2String(InputStream is, String charset) {
        try {
            if (isEmpty(is)) {
                return null;
            }

            byte[] bytes = readFile2Bytes(is);
            if (bytes != null) {
                if (isEmpty(charset)) {
                    return new String(bytes);
                } else {
                    return new String(bytes, charset);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取文件为字符串
     *
     * @param file
     * @param charset UTF-8、GBK...
     * @return
     */
    public static String readFile2String(File file, String charset) {
        try {
            if (!isFileExists(file)) {
                return null;
            }
            InputStream is = new FileInputStream(file);
            return readFile2String(is, charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取文件为字符串
     *
     * @param path
     * @param charset UTF-8、GBK...
     * @return
     */
    public static String readFile2String(String path, String charset) {
        if (isEmpty(path)) {
            return null;
        }
        return readFile2String(new File(path), charset);
    }

    /**
     * 拷贝文件
     *
     * @param is
     * @param os
     * @return
     */
    public static boolean copyFile(InputStream is, OutputStream os) {
        try {
            if (is == null || os == null) {
                return false;
            }
            byte[] buf = new byte[BUF_SIZE];
            int len;
            while ((len = is.read(buf, 0, buf.length)) != -1) {
                os.write(buf, 0, len);
            }
            is.close();
            os.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 拷贝文件
     *
     * @param sourceFile
     * @param targetFile
     * @return
     */
    public static boolean copyFile(File sourceFile, File targetFile) {
        if (!isFileExists(sourceFile)) {
            return false;
        }
        try {
            return copyFile(new FileInputStream(sourceFile), new FileOutputStream(targetFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

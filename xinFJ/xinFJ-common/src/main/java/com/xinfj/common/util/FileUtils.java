package com.xinfj.common.util;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author PXIN
 * @create 2021-06-21 13:49
 */
public class FileUtils {

    /**
     * 跟据文件路径获取文件
     * @param filePath 文件路径
     * @return
     */
    public static File getFileByPath(String filePath){
        return filePath !=null ? new File(filePath) : null;
    }

    /**
     * 判断文件是否存在
     * @param filePath 文件路径
     * @return
     */
    public static boolean isFileExists(String filePath){
        return isFileExists(getFileByPath(filePath));
    }

    /**
     *判断文件是否存在
     * @param file 文件
     * @return
     */
    public static boolean isFileExists(File file){
       return file != null && file.exists();
    }

    /**
     * 判断目录是否存在
     * @param dirPath  目录路径
     * @return
     */
    public static boolean isDir(String dirPath){
        return isDir(getFileByPath(dirPath));
    }

    /**
     * 判断目录路径是否存在
     * @param file 文件
     * @return
     */
    public static boolean isDir(File file){
        return isFileExists(file) && file.isDirectory();
    }

    /**
     * 判断是否为文件
     * @param filePath 文件路径
     * @return
     */
    public static boolean isFile(String filePath){
        return isFile(getFileByPath(filePath));
    }

    /**
     * 判断是否为文件
     * @param file 文件
     * @return
     */
    public static boolean isFile(File file) {
        return isFileExists(file) && file.isFile();
    }

    /**
     * 获取文件名
     * @param filePath 文件路径
     * @return
     */
    public static String getFileName(String filePath){
        return getFileName(getFileByPath(filePath));
    }

    /**
     * 获取文件名
     * @param file 文件
     * @return
     */
    public static String getFileName(File file) {
        return isFileExists(file) ? file.getName() : "File path does not exist";
    }

    /**
     * 获取文件的绝对路径
     * @param filePath 文件路径
     * @return
     */
    public static String getAbsPath(String filePath){
        return getAbsPath(getFileByPath(filePath));
    }

    /**
     * 获取文件的绝对路径
     * @param file 文件
     * @return
     */
    public static String getAbsPath(File file) {
        String path="";
        if(isFile(file)){
           path=file.getAbsolutePath().substring(0,file.getAbsolutePath().length()-file.getName().length());
        }else if (isDir(file)){
           path=file.getAbsolutePath();
        }
        return path;
    }

    /**
     * 获取文件的大小,单位kb
     * @param filePath 文件路径
     * @return
     */
    public static String getFileSize(String filePath){
        return getFileSize(getFileByPath(filePath));
    }

    /**
     * 获取文件的大小，单位kb
     * @param file 文件
     * @return
     */
    public static String getFileSize(File file) {
        String value ="";
        if(isFileExists(file) && isFile(file)){
          value = String.valueOf(file.length());
        }
        return value;
    }

    /**
     * 获取文件的大小，单位kb
     * @param filePath 文件路径
     * @return
     */
    public static long getInputSize(String filePath){
        return getInputSize(getFileByPath(filePath));
    }

    /**
     * 获取文件大小,单位kb
     * @param file 文件
     * @return
     */
    public static long getInputSize(File file) {
        long size = 0;
        if(isFileExists(file) && isFile(file)){
            try {
                FileInputStream fis = new FileInputStream(file);
                FileChannel fileChannel = fis.getChannel();
                size = fileChannel.size();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return size;
    }

    /**
     * 获取新的文件路径
     * @param filePath  源文件路径
     * @param targetPath  目标文件路径
     * @return
     */
    public static String getNewPath(String filePath,String targetPath){
        return getNewPath(getFileByPath(filePath),getFileByPath(targetPath));
    }

    /**
     * 获取新的文件路径
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @return
     */
    public static String getNewPath(File sourceFile, File targetFile){
        if (!isFile(sourceFile) || !isFileExists(targetFile)) {
           return "FilePath Error";
        }
        if(sourceFile.getAbsolutePath().equals(targetFile.getAbsolutePath())){
           return "FilePath Same";
        }
        String sourceFileName = sourceFile.getName();
        return targetFile.getAbsolutePath() + File.separator + sourceFileName;
    }

    /**
     * 获取字节缓冲输入流
     * @param filePath 文件路径
     * @return
     */
    public static InputStreamReader getInputStream(String filePath){
        return getInputStream(getFileByPath(filePath));
    }

    /**
     * 获取字节缓冲输入流
     * @param file 文件
     * @return
     */
    public static InputStreamReader getInputStream(File file) {
        InputStreamReader isr =null;
        if(isFile(file)){
            try {
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                //将字节缓冲流转换为字符流解决乱码问题
                isr = new InputStreamReader(bis, StandardCharsets.UTF_8);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return isr;
    }

    /**
     * 获取字节缓冲输出流
     * @param filePath 文件路径
     * @return
     */
    public static OutputStreamWriter getOutputStream(String filePath){
        return getOutputStream(getFileByPath(filePath));
    }

    /**
     * 获取字节缓冲输出流
     * @param file 文件
     * @return
     */
    public static OutputStreamWriter getOutputStream(File file) {
        OutputStreamWriter osw =null;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos =new BufferedOutputStream(fos);
            //将字节缓冲流转换为字符流解决乱码问题
            osw = new OutputStreamWriter(bos, StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return osw;
    }

    /**
     * 获取字符缓冲输入流
     * @param filePath 文件路径
     * @return
     */
    public static BufferedReader getReader(String filePath){
        return getReader(getFileByPath(filePath));
    }

    /**
     * 获取字符缓冲输入流
     * @param file 文件
     * @return
     */
    public static BufferedReader getReader(File file) {
        BufferedReader br=null;
        if(isFile(file)){
            try {
                FileReader reader = new FileReader(file);
                br=new BufferedReader(reader);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return br;
    }

    /**
     * 获取字符缓冲输出流
     * @param filePath 文件路径
     * @return
     */
    public static BufferedWriter getWriter(String filePath){
        return getWriter(getFileByPath(filePath));
    }

    /**
     * 获取字符缓冲输出流
     * @param file 文件
     * @return
     */
    public static BufferedWriter getWriter(File file) {
        BufferedWriter bw=null;
        try {
            FileWriter writer = new FileWriter(file);
            bw=new BufferedWriter(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bw;
    }

    /**
     * 文件复制
     * @param filePath 源文件路径
     * @param targetPath 目标文件路径
     * @return
     */
    public static boolean copyFile(String filePath,String targetPath) {
        return copyFile(getFileByPath(filePath), getFileByPath(targetPath));
    }

    /**
     * 文件复制
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @return
     */
    public static boolean copyFile(File sourceFile, File targetFile) {
       if(isFile(sourceFile) && isFileExists(targetFile)){
           String newPath = getNewPath(sourceFile, targetFile);
           InputStreamReader is = getInputStream(sourceFile);
           OutputStreamWriter os = getOutputStream(newPath);
           //缓冲字符数组
           char[] chars = new char[1024];
           int hasRead;
           try {
               while ((hasRead=is.read(chars))!=-1){
                   os.write(chars,0,hasRead);
               }
               os.flush();
               //先打开的后关闭
               os.close();
               is.close();
               return true;
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       return false;
    }

    /**
     * 另一种采用nio流方式的文件复制
     * @param filePath 文件路径
     * @param targetPath 目标文件路径
     * @return
     */
    public static boolean copyFileNew(String filePath,String targetPath){
        //或者采用这种方式转换为path
        // new File(filePath).toPath();
        return copyFileNew(Paths.get(filePath),getFileByPath(targetPath));
    }

    /**
     * 另一种采用nio流方式的文件复制
     * @param path 路径
     * @param targetFile 文件
     * @return
     */
    public static boolean copyFileNew(Path path, File targetFile) {
        long copy = 0;
        try {
           copy = Files.copy(path, new FileOutputStream(targetFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
       return copy>0;
    }

    /**
     * 文件夹复制
     * @param filePath 源文件路径
     * @param targetPath  目标路径
     * @return
     */
    public static boolean copyDir(String filePath,String targetPath){
        return copyDir(getFileByPath(filePath),getFileByPath(targetPath));
    }

    /**
     * 文件夹复制
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @return
     */
    public static boolean copyDir(File sourceFile, File targetFile) {
        boolean flag=false;
        if(sourceFile.isDirectory()){
            File newFolder = new File(targetFile, sourceFile.getName());
            if(!newFolder.exists()){
                flag=newFolder.mkdirs();
            }
            for (File file : Objects.requireNonNull(sourceFile.listFiles())) {
                flag=copyDir(file, newFolder);
            }
        }else {
            flag=copyFile(sourceFile,targetFile);
        }
        return flag;
    }

    /**
     * 文件夹复制详情
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @return
     */
    public static boolean copyDirDetail(File sourceFile, File targetFile) {
        boolean flag=false;
        if(sourceFile.isFile()){
            flag= copyFile(sourceFile,targetFile);
        }
        if(sourceFile.isDirectory()){
            File newFolder = new File(targetFile, sourceFile.getName());
            if(!newFolder.exists()){
                flag=newFolder.mkdirs();
            }
            for (File file : Objects.requireNonNull(sourceFile.listFiles())) {
                    flag=copyDirDetail(file,newFolder);
            }
        }
        return flag;
    }

    /**
     * 删除文件
     * @param filePath 文件路径
     * @return
     */
    public static boolean deleteFile(String filePath){
        return deleteFile(getFileByPath(filePath));
    }

    /**
     * 删除文件
     * @param file 文件
     * @return
     */
    public static boolean deleteFile(File file) {
        if(isFile(file)){
            return file.delete();
        }
        if(isDir(file)){
            for (File f : Objects.requireNonNull(file.listFiles())) {
                deleteFile(f);
            }
            return file.delete();
        }
        return false;
    }







    public static void main(String[] args) {
//        boolean b = delFile("E:/new.txt");
//        System.out.println(b ? "删除成功":"删除失败");

//        boolean copyFile = copyGeneralFile("E:/test.txt", "D:/");
//        System.out.println(copyFile ? "复制成功":"复制失败");

//       String fileName = getFileName("D:/test.txt");
//        System.out.println(fileName);

//        String absPath = getAbsPath("E:\\测试\\2\\z.docx");
//        System.out.println(absPath);
//
//        System.out.println(isFile("E:\\测试\\test.txt"));
//        System.out.println(isFile("E:\\测试\\2\\z.docx"));

//        String fileSize = getFileSize("E:/测试/2/z.docx");
//        System.out.println(fileSize);
//
//        long size = getInputSize("E:/测试/test.txt");
//        System.out.println(size);

//        String newPath = getNewPath("E:\\测试\\test.txt", "E:\\测试\\1");
//        System.out.println(newPath);
//
//        System.out.println(copyFile("E:/测试/test.txt", "E:/测试/1"));

//        System.out.println(deleteFile("E:/测试/1"));
//        System.out.println(copyDir("E:/测试", "E:/ceshi01"));
        System.out.println(copyFileNew("E:/test.txt", "E:/new.txt"));


    }
}

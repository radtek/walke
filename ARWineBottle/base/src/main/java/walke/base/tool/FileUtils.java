package walke.base.tool;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by suxq on 2017/4/7.
 */

public class FileUtils {

    private static final String TAG = "FileUtils";

    /**
     * 判断外部存储是否可用
     *
     * @return true: 可用
     */
    public static boolean isSDcardAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * 获取外部存储的根目录路径
     *
     * @return
     * @throws IOException
     */
    public static String getSDcardPath() throws IOException {
        if (isSDcardAvailable()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            throw new FileNotFoundException("没有外部存储");
        }
    }

    /**
     * 获取程序的外部存储的数据存放根目录 {/gank}
     *
     * @return
     */
    public static String getAppRootDirectoryPath(){
        if (isSDcardAvailable()) {
            try {
                String path = getSDcardPath();
                File file = new File(path, "aaHui");
                if (!file.exists()) {
                    file.mkdirs();
                }
                return file.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取程序外部存储下的download目录路径
     *
     * @return
     */
    public static String getAppDownloadDirectory() {
        if (isSDcardAvailable()) {
            String path = getAppRootDirectoryPath();
            File file = new File(path, "download");
            if (!file.exists()) {
                file.mkdirs();
            }
            return file.getAbsolutePath();
        }
        return null;
    }

    /**
     * 创建子目录
     *
     * @param parent
     * @param name
     * @return
     */
    public static boolean createDirectory(String parent, String name) {
        File file = new File(parent, name);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 复制文件，默认删除原文件
     *
     * @param sourceFile
     * @param destFile
     */
    public static void copyFile(File sourceFile, File destFile) {
        copyFile(sourceFile, destFile, true);
    }

    /**
     * 复制文件
     *
     * @param sourceFile
     * @param destFile
     * @param isDeleteSource
     */
    public static void copyFile(File sourceFile, File destFile, boolean isDeleteSource) {
        try {
            int byteRead = 0;
            if (sourceFile.exists()) {
                FileInputStream is = new FileInputStream(sourceFile);
                FileOutputStream os = new FileOutputStream(destFile);
                byte[] buffer = new byte[1024];
                while ((byteRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, byteRead);
                }
                if (isDeleteSource) {
                    sourceFile.delete();
                }
                is.close();
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件或文件夹
     *
     * @param file
     * @throws IOException
     */
    public static void deleteFile(File file) throws IOException {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                deleteFile(f);
            }
        } else {
            file.delete();
        }
    }

    /**
     * 创建目录
     *
     * @param dir 目录
     */
    public static void mkdir(String dir) {
        try {
            String dirTemp = dir;
            File dirPath = new File(dirTemp);
            if (!dirPath.exists()) {
                dirPath.mkdir();
            }
        } catch (Exception e) {
            Log.e(TAG, "mkdir: "+"创建目录操作出错: "+e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 新建文件
     *
     * @param fileName
     *            String 包含路径的文件名 如:E:\phsftp\src\123.txt
     * @param content
     *            String 文件内容
     *
     */
    public static void createNewFile(String fileName, String content) {
        try {
            String fileNameTemp = fileName;
            File filePath = new File(fileNameTemp);
            if (!filePath.exists()) {
                filePath.createNewFile();
            }
            FileWriter fw = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(fw);
            String strContent = content;
            pw.println(strContent);
            pw.flush();
            pw.close();
            fw.close();
        } catch (Exception e) {
            Log.e(TAG, "createNewFile: "+"新建文件操作出错: "+e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * 删除文件
     *
     * @param fileName 包含路径的文件名
     */
    public static void delFile(String fileName) {
        try {
            String filePath = fileName;
            File delFile = new File(filePath);
            delFile.delete();
        } catch (Exception e) {
            Log.e(TAG, "delFile: "+"删除文件操作出错: "+e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 删除文件夹
     *
     * @param folderPath  文件夹路径
     */
    public static void delFolder(String folderPath) {
        try {
            // 删除文件夹里面所有内容
            delAllFile(folderPath);
            String filePath = folderPath;
            File myFilePath = new File(filePath);
            // 删除空文件夹
            myFilePath.delete();
        } catch (Exception e) {
            Log.e(TAG, "delFolder: "+ "删除文件夹操作出错"+e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 删除文件夹里面的所有文件
     *
     * @param path 文件夹路径
     */
    public static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] childFiles = file.list();
        File temp = null;
        for (int i = 0; i < childFiles.length; i++) {
            //File.separator与系统有关的默认名称分隔符
            //在UNIX系统上，此字段的值为'/'；在Microsoft Windows系统上，它为 '\'。
            if (path.endsWith(File.separator)) {
                temp = new File(path + childFiles[i]);
            } else {
                temp = new File(path + File.separator + childFiles[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + childFiles[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + childFiles[i]);// 再删除空文件夹
            }
        }
    }


    /**
     * 复制单个文件
     *
     * @param srcFile
     *            包含路径的源文件 如：E:/phsftp/src/abc.txt
     * @param dirDest
     *            目标文件目录；若文件目录不存在则自动创建  如：E:/phsftp/dest
     * @throws IOException
     */
    public static void copyFile(String srcFile, String dirDest) {
        try {
            FileInputStream in = new FileInputStream(srcFile);
            mkdir(dirDest);
            FileOutputStream out = new FileOutputStream(dirDest+"/"+new File(srcFile).getName());
            int len;
            byte buffer[] = new byte[1024];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
            out.close();
            in.close();
        } catch (Exception e) {
            Log.e(TAG, "copyFile: "+ "复制文件操作出错:"+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 复制文件夹
     *
     * @param oldPath
     *            String 源文件夹路径 如：E:/phsftp/src
     * @param newPath
     *            String 目标文件夹路径 如：E:/phsftp/dest
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {
        try {
            // 如果文件夹不存在 则新建文件夹
            mkdir(newPath);
            File file = new File(oldPath);
            String[] files = file.list();
            File temp = null;
            for (int i = 0; i < files.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + files[i]);
                } else {
                    temp = new File(oldPath + File.separator + files[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath
                            + "/" + (temp.getName()).toString());
                    byte[] buffer = new byte[1024 * 2];
                    int len;
                    while ((len = input.read(buffer)) != -1) {
                        output.write(buffer, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {// 如果是子文件夹
                    copyFolder(oldPath + "/" + files[i], newPath + "/" + files[i]);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "copyFolder: "+"复制文件夹操作出错:"+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 移动文件到指定目录
     *
     * @param oldPath 包含路径的文件名 如：E:/phsftp/src/ljq.txt
     * @param newPath 目标文件目录 如：E:/phsftp/dest
     */
    public static void moveFile(String oldPath, String newPath) {
        copyFile(oldPath, newPath);
        delFile(oldPath);
    }

    /**
     * 移动文件到指定目录，不会删除文件夹
     *
     * @param oldPath 源文件目录  如：E:/phsftp/src
     * @param newPath 目标文件目录 如：E:/phsftp/dest
     */
    public static void moveFiles(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delAllFile(oldPath);
    }

    /**
     * 移动文件到指定目录，会删除文件夹
     *
     * @param oldPath 源文件目录  如：E:/phsftp/src
     * @param newPath 目标文件目录 如：E:/phsftp/dest
     */
    public static void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }

   /* *//**
     * 解压zip文件
     *
     * @param srcDir
     *            解压前存放的目录
     * @param destDir
     *            解压后存放的目录
     * @throws Exception
     *//*
    public static void jieYaZip(String srcDir, String destDir) throws Exception {
        int leng = 0;
        byte[] b = new byte[1024*2];
        *//** 获取zip格式的文件 **//*
        File[] zipFiles = new FileFilterByExtension("zip").getFiles(srcDir);
        if(zipFiles!=null && !"".equals(zipFiles)){
            for (int i = 0; i < zipFiles.length; i++) {
                File file = zipFiles[i];
                *//** 解压的输入流 * *//*
                ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
                ZipEntry entry=null;
                while ((entry=zis.getNextEntry())!=null) {
                    File destFile =null;
                    if(destDir.endsWith(File.separator)){
                        destFile = new File(destDir + entry.getName());
                    }else {
                        destFile = new File(destDir + "/" + entry.getName());
                    }
                    *//** 把解压包中的文件拷贝到目标目录 * *//*
                    FileOutputStream fos = new FileOutputStream(destFile);
                    while ((leng = zis.read(b)) != -1) {
                        fos.write(b, 0, leng);
                    }
                    fos.close();
                }
                zis.close();
            }
        }
    }*/

    /**
     * 压缩文件
     *
     * @param srcDir
     *            压缩前存放的目录
     * @param destDir
     *            压缩后存放的目录
     * @throws Exception
     */
    public static void yaSuoZip(String srcDir, String destDir) throws Exception {
        String tempFileName=null;
        byte[] buf = new byte[1024*2];
        int len;
        //获取要压缩的文件
        File[] files = new File(srcDir).listFiles();
        if(files!=null){
            for (File file : files) {
                if(file.isFile()){
                    FileInputStream fis = new FileInputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    if (destDir.endsWith(File.separator)) {
                        tempFileName = destDir + file.getName() + ".zip";
                    } else {
                        tempFileName = destDir + "/" + file.getName() + ".zip";
                    }
                    FileOutputStream fos = new FileOutputStream(tempFileName);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    ZipOutputStream zos = new ZipOutputStream(bos);// 压缩包

                    ZipEntry ze = new ZipEntry(file.getName());// 压缩包文件名
                    zos.putNextEntry(ze);// 写入新的ZIP文件条目并将流定位到条目数据的开始处

                    while ((len = bis.read(buf)) != -1) {
                        zos.write(buf, 0, len);
                        zos.flush();
                    }
                    bis.close();
                    zos.close();

                }
            }
        }
    }


    /**
     * 读取数据
     *
     * @param inSream
     * @param charsetName
     * @return
     * @throws Exception
     */
    public static String readData(InputStream inSream, String charsetName) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while( (len = inSream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inSream.close();
        return new String(data, charsetName);
    }

    /**
     * 一行一行读取文件，适合字符读取，若读取中文字符时会出现乱码
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static Set<String> readFile(String path) throws Exception {
        Set<String> datas=new HashSet<String>();
        FileReader fr=new FileReader(path);
        BufferedReader br=new BufferedReader(fr);
        String line=null;
        while ((line=br.readLine())!=null) {
            datas.add(line);
        }
        br.close();
        fr.close();
        return datas;
    }



}

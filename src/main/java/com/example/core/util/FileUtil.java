package com.example.core.util;

import org.apache.commons.io.FilenameUtils;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.example.core.util.StringUtil.isEmpty;

public class FileUtil {

    /**
     * @param fileList: 上傳檔案的List
     * @param targetDir: 上傳檔案路徑
     * @param isRandomName: true: 使用亂數數檔案, false: 使用原來檔名
     * @return
     */
    public static List<HashMap<String, String>> uploadMultiFile(List<MultipartFile> fileList, String targetDir, boolean isRandomName){

        List<HashMap<String, String>> list = new ArrayList<>();

        //Check the directory is exist or not
        Path dir = Paths.get(targetDir);
        try {
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
        } catch (IOException e) {
            System.out.println("Upload File Error: " + e.toString());
        }

        if (fileList!=null && fileList.size()>0){

            for (MultipartFile multipartFile: fileList) {
                if (StringUtils.isNotBlank( multipartFile.getOriginalFilename())) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    boolean isSuccess = true;

                    String originalFileName = multipartFile.getOriginalFilename();
                    String fileName = originalFileName;
                    if (isRandomName){
                        fileName = getRandomFileName(fileName);
                    }

                    try {
                        InputStream inputStream = multipartFile.getInputStream();
                        long copy = Files.copy(inputStream, dir.resolve(fileName));
                        
                    } catch (IOException e) {
                        System.out.println("error="+ e.toString());
                        isSuccess = false;
                    }

                    hashMap.put("IsRandomName", String.valueOf(isRandomName));
                    hashMap.put("IsSuccess", String.valueOf(isSuccess));
                    hashMap.put("OriginalFileName", originalFileName);
                    hashMap.put("FileName", fileName);
                    hashMap.put("Type", multipartFile.getContentType());
                    hashMap.put("Size", String.valueOf(multipartFile.getSize()));
                    hashMap.put("FileFullPath", targetDir);

                    list.add(hashMap);
                }
            }// End for

        }// End if

        return list;
    }

    /**
     * @param multipartFile: 上傳檔案
     * @param targetDir: 上傳檔案路徑
     * @param isRandomName: true: 使用亂數數檔案, false: 使用原來檔名
     * @return
     */
    public static HashMap<String, String> uploadSingleFile(MultipartFile multipartFile, String targetDir, boolean isRandomName){
        //Check the directory is exist or not
        Path dir = Paths.get(targetDir);
        try {
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
        } catch (IOException e) {
            System.out.println("Upload File Error: " + e.toString());
        }

        HashMap<String, String> hashMap = new HashMap<>();
        boolean isSuccess = true;

        String originalFileName = multipartFile.getOriginalFilename();
        String fileName = originalFileName;
        if (isRandomName){
            fileName = getRandomFileName(fileName);
        }

        try {
            InputStream inputStream = multipartFile.getInputStream();
            long copy = Files.copy(inputStream, dir.resolve(fileName));

        } catch (IOException e) {
            System.out.println("error="+ e.toString());
            isSuccess = false;
        }

        hashMap.put("IsRandomName", String.valueOf(isRandomName));
        hashMap.put("IsSuccess", String.valueOf(isSuccess));
        hashMap.put("OriginalFileName", originalFileName);
        hashMap.put("FileName", fileName);
        hashMap.put("Type", multipartFile.getContentType());
        hashMap.put("Size", String.valueOf(multipartFile.getSize()));
        hashMap.put("FileFullPath", targetDir);

        return  hashMap;
    }


    /**
     * @param response: HttpServletResponse
     * @param filePath: 檔案完整路徑(包含檔名ex: /usr/file/abc.jpg)
     * @param fileName: 下載儲存的檔案名稱
     * @param isPreview: true: 在browser上預覽, false: browser是出現詢問存位置
     */
    public static void downloadFile(HttpServletResponse response, String filePath, String fileName, boolean isPreview){
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("Download File Error : filePath is not exist.");
            return;
        }

        if (isEmpty(fileName)) {
            fileName = file.getName();
        }

        try {
            fileName = URLEncoder.encode(fileName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        //get the mimetype
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            //unknown mimetype so set the mimetype to application/octet-stream
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);

        /**
         * In a regular HTTP response, the Content-Disposition response header is a
         * header indicating if the content is expected to be displayed inline in the
         * browser, that is, as a Web page or as part of a Web page, or as an
         * attachment, that is downloaded and saved locally.
         *
         */

        if (isPreview) {
            /**  Here we have mentioned it to show inline */
//            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + fileName + "\""));
            response.setHeader("Content-Disposition","inline; filename*=UTF-8''"+fileName);
        } else {

            /** Here we have mentioned it to show as attachment */
//            response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + fileName + "\""));
            response.setHeader("Content-Disposition","attachment; filename*=UTF-8''"+fileName);

        }

        response.setContentLength((int) file.length());

        InputStream inputStream = null;

        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            FileCopyUtils.copy(inputStream, response.getOutputStream());

        } catch (FileNotFoundException e) {
            System.out.println("Download File Error (FileNotFoundException): " + e.toString());

        } catch (IOException e) {
            System.out.println("Download File Error (IOException): " + e.toString());
        }

    }

    /**
     * @param fileName: 檔案(含副檔名)完整名稱
     * @return
     */
    public static String getRandomFileName(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        return newFileName + "." + extension;
    }

    public static boolean deleteFile(String filePath){
        boolean isSuccess = true;

        File file = new File(filePath);
        if (file.isFile() && file.exists()){
            file.delete();
        }

        return isSuccess;
    }

    public static boolean deleteFolder(File folder) {
        File[] allContents = folder.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteFolder(file);
            }
        }
        return folder.delete();
    }

    public static String writeToFile(String filePath, String content){
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter fileOut = new PrintWriter(bufferedWriter);
            fileOut.println(content);
            fileOut.close();
//            System.out.println("the file test_output.txt is created!");
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return filePath;
    }
}

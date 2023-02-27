package com.example.demo.util.file;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.http.entity.ContentType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class FileUtils {

    /**
     * 传入file类型转为MultipartFile
     * @param file
     * @return
     * @throws IOException
     */
    public static MultipartFile getMultipartFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
        return multipartFile;
    }

    public static List<UnzipFileVo> Ectract(MultipartFile multipartFile) throws IOException {
        List<UnzipFileVo> list= new ArrayList<>();
        //获取文件输入流
        InputStream input = multipartFile.getInputStream();
        //获取ZIP输入流(一定要指定字符集Charset.forName("GBK")否则会报java.lang.IllegalArgumentException: MALFORMED)
        ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(input), Charset.forName("GBK"));
        ZipFile zf = toFile(multipartFile);
        //定义ZipEntry置为null,避免由于重复调用zipInputStream.getNextEntry造成的不必要的问题
        ZipEntry ze = null;
        //循环遍历
        while ((ze =zipInputStream.getNextEntry())!= null) {
            InputStream is = zf.getInputStream(ze);

            UnzipFileVo unzipFileVo = new UnzipFileVo();
            unzipFileVo.setFile(getMultipartFile(is,ze.getName()));
            list.add(unzipFileVo);
        }
        //一定记得关闭流
        zipInputStream.closeEntry();
        input.close();
        return list;
    }

    /**
     * 获取封装得MultipartFile
     *
     * @param inputStream inputStream
     * @param fileName    fileName
     * @return MultipartFile
     */
    public static MultipartFile getMultipartFile(InputStream inputStream, String fileName) {
        FileItem fileItem = createFileItem(inputStream, fileName);
        //CommonsMultipartFile是feign对multipartFile的封装，但是要FileItem类对象
        return new CommonsMultipartFile(fileItem);
    }

    private  static  ZipFile toFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.getSize() <= 0) {
            return null;
        }
        File file = multipartFileToFile(multipartFile);
        if (file == null || !file.exists()) {
            return null;
        }
        ZipFile zipFile = new ZipFile(file, Charset.forName("GBK"));
        return zipFile;
    }

    private static File multipartFileToFile(MultipartFile multipartFile) {
        File file = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
            file = new File(multipartFile.getOriginalFilename());
            outputStream = new FileOutputStream(file);
            write(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    public static void  write(InputStream inputStream, OutputStream outputStream) {
        byte[] buffer = new byte[4096];
        try {
            int count = inputStream.read(buffer, 0, buffer.length);
            while (count != -1) {
                outputStream.write(buffer, 0, count);
                count = inputStream.read(buffer, 0, buffer.length);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * FileItem类对象创建
     *
     * @param inputStream inputStream
     * @param fileName    fileName
     * @return FileItem
     */
    public static FileItem createFileItem(InputStream inputStream, String fileName) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = "file";
        FileItem item = factory.createItem(textFieldName, MediaType.MULTIPART_FORM_DATA_VALUE, true, fileName);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        OutputStream os = null;
        //使用输出流输出输入流的字节
        try {
            os = item.getOutputStream();
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            inputStream.close();
        } catch (IOException e) {

            throw new IllegalArgumentException("文件上传失败");
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {

                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }

        return item;
    }


}

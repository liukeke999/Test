package com.example.demo.util.file;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件解析对象
 *
 * @author liukk
 * @since 2023/01/10
 */
@Data
public class UnzipFileVo {

    /**
     * 文件
     */
    private MultipartFile file;

}
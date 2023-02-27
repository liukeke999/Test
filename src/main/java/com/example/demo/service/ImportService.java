package com.example.demo.service;

import com.example.demo.util.file.UnzipFileVo;

import java.io.IOException;
import java.util.List;

/**
 * 导入数据接口类
 */
public interface ImportService {

    /**
     * 导入数据
     * @param read
     */
    void importData(List<List<String>> read);

    /**
     * 处理.zip压缩文件
     * @param list
     * @return
     */
    Integer uploadzip(List<UnzipFileVo> list) throws IOException;
}

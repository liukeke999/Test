package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.demo.common.enums.ExceptionEnums;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.ImportService;
import com.example.demo.util.file.UnzipFileVo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件上传实现类
 */
@Service
public class ImportServiceImpl implements ImportService {



    @Override
    public void importData(List<List<String>> read) {
        if (CollectionUtils.isNotEmpty(read)){

            //处理表格数据
            List<?> importList = read.stream().map(e -> {
                //处理数据

                return new Object();
            }).collect(Collectors.toList());

        }else{
            throw new BusinessException(ExceptionEnums.SERVER_OTHER_ERROR.getCode());
        }

    }

    @Override
    public Integer uploadzip(List<UnzipFileVo> list) throws IOException {
        int sum = 0; //处理的条数
        for (int i = 0; i < list.size(); i++) {

        }
        return sum;
    }
}

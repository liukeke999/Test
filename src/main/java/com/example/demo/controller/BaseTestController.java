package com.example.demo.controller;


import com.example.demo.common.enums.ExceptionEnums;
import com.example.demo.common.response.Result;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.ImportService;
import com.example.demo.util.file.FileUtils;
import com.example.demo.util.ImportExcelUtil;
import com.example.demo.util.file.UnzipFileVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/import")
public class BaseTestController {

    @Autowired
    private ImportService importService;


    /**
     * Excel导入
     * @return
     */
    @PostMapping("/importBaseStandard")
    public Result<?> importBaseStandard() {
        try {
            org.springframework.core.io.Resource standardResource = new ClassPathResource("resources下的文件路径");
            MultipartFile file = FileUtils.getMultipartFile(standardResource.getInputStream(),standardResource.getFilename());
            //工具类
            ImportExcelUtil readExcelUtil = new ImportExcelUtil();
            String fileName = file.getOriginalFilename();
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
            List<List<String>> read = readExcelUtil.read(file.getInputStream(), fileSuffix );
            //调用数据处理接口
            importService.importData(read);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ExceptionEnums.SERVER_OTHER_ERROR.getCode());
        }
        return Result.success();
    }

    /**
     * 处理.zip压缩包数据
     * @return
     * @throws IOException
     */
    @PostMapping("/UploadBzwzFile")
    public Result<?> UploadBzwzFile() throws IOException {
        org.springframework.core.io.Resource bzwzCheckResource = new ClassPathResource("文件路径");
        MultipartFile file = FileUtils.getMultipartFile(bzwzCheckResource.getInputStream(), bzwzCheckResource.getFilename());
        if(Objects.isNull(file) || file.isEmpty()) {//验证文件是否为空
            return Result.failure("上传文件为空或不存在");
        }
        List<UnzipFileVo> unzipFileVoList = FileUtils.Ectract(file);
        //获取zip文件里面的文件，并组装到新的List对象//过滤文件夹
        List<UnzipFileVo> collect = unzipFileVoList.stream().filter(item -> item.getFile().getOriginalFilename().contains(".") && item.getFile().getSize() > 0).
                collect(Collectors.toList());

        //上面就是把zip压缩包的内容转换称了文件流集合，下面可根据自己的业务对文件进行操作
        Integer sum = importService.uploadzip(collect);

        return Result.success("解析zip成功，成功上传"+sum+"条数据");
    }



}

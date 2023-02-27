package com.example.demo.baidu;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.AuthService;
import com.example.demo.util.GsonUtils;
import com.example.demo.util.HttpUtil;
import com.example.demo.util.ImgBase64;

import java.util.HashMap;
import java.util.Map;

/**
 * 人脸融合
 */
public class FaceMerge {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String faceMerge() {

        String url1 = "C:\\安装包\\3.jpg";
        String url2 = "C:\\安装包\\4.jpg";
        String imgStr = ImgBase64.getImgStr(url1);
        String imgStr1 = ImgBase64.getImgStr(url2);
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v1/merge";
        try {
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> image_templateMap = new HashMap<>();
            image_templateMap.put("image", imgStr1);
            image_templateMap.put("image_type", "BASE64");
            image_templateMap.put("quality_control", "NONE");
            map.put("image_template", image_templateMap);
            Map<String, Object> image_targetMap = new HashMap<>();
            image_targetMap.put("image", imgStr);
            image_targetMap.put("image_type", "BASE64");
            image_targetMap.put("quality_control", "NONE");
            map.put("image_target", image_targetMap);

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String faceMerge = FaceMerge.faceMerge();
        System.out.println(faceMerge);
        JSONObject jsonObject = JSONObject.parseObject(faceMerge);
        String aa = String.valueOf(jsonObject.get("result"));
        JSONObject object = JSONObject.parseObject(aa);
        String merge_image = String.valueOf(object.get("merge_image"));
        System.out.println(merge_image);

    }
}
package com.example.demo.baidu;



import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.AuthService;
import com.example.demo.util.Base64Util;
import com.example.demo.util.file.FileUtil;
import com.example.demo.util.HttpUtil;

import java.net.URLEncoder;

/**
 * 图像风格转换
 */
public class StyleTrans {

    /**
     * cartoon：卡通画风格
     * pencil：铅笔风格
     * color_pencil：彩色铅笔画风格
     * warm：彩色糖块油画风格
     * wave：神奈川冲浪里油画风格
     * lavender：薰衣草油画风格
     * mononoke：奇异油画风格
     * scream：呐喊油画风格
     * gothic：哥特油画风格
     *
     */

    static String style = "mononoke";

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String styleTrans() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-process/v1/style_trans";
        try {
            // 本地文件路径
            String filePath = "C:\\安装包\\qwe.jpg";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "option=" + style + "&image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth();

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String styleTrans = StyleTrans.styleTrans();
        JSONObject jsonObject = JSONObject.parseObject(styleTrans);
        String image = String.valueOf(jsonObject.get("image"));
        System.out.println(image);
    }
}

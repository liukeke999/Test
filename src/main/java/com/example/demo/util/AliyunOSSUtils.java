package com.example.demo.util;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Date;

@Component
@Data
public class AliyunOSSUtils implements InitializingBean {

	// 读取配置文件内容
	@Value("${aliyun_oss.end_point}")
	private String endPoint;

	@Value("${aliyun_oss.access_key_id}")
	private String accessKeyId;

	@Value("${aliyun_oss.access_key_secret}")
	private String accessKeySecret;

	@Value("${aliyun_oss.bucket_name}")
	private String bucketName;

	@Value("${aliyun_oss.cdn_url}")
	private String cdnUrl;

	@Value("${aliyun_oss.service_name}")
	private String serviceName;

	@Value("${aliyun_oss.active}")
	private String active;

	private static final StringBuffer ACCESS_KEY_ID = new StringBuffer();
	private static final StringBuffer ACCESS_KEY_SECRET = new StringBuffer();
	private static final StringBuffer BUCKET_NAME = new StringBuffer();
	private static final StringBuffer CDN_URL = new StringBuffer();
	private static final StringBuffer END_POINT = new StringBuffer();
	private static final StringBuffer SERVICE_NAME = new StringBuffer();
	private static final StringBuffer ACTIVE = new StringBuffer();

	@Override
	public void afterPropertiesSet() throws Exception {
		ACCESS_KEY_ID.append(accessKeyId);
		ACCESS_KEY_SECRET.append(accessKeySecret);
		BUCKET_NAME.append(bucketName);
		CDN_URL.append(cdnUrl);
		END_POINT.append(endPoint);
		SERVICE_NAME.append(serviceName);
		ACTIVE.append(active);
	}

	/**
	 * 物资设备附件
	 */
	public static final String WZ_DEVICE = "wz_device";



	/**
	 * @param dir       文件目录
	 * @param filename  文件名
	 * @param fileBytes 文件字节数组
	 * @return 文件key
	 */
	public static String saveFile(String dir, String filename, byte[] fileBytes) {
		OSS ossClient = null;
		try {
			ClientBuilderConfiguration clientConfiguration = new ClientBuilderConfiguration();
			clientConfiguration.setProtocol(Protocol.HTTPS);
			ossClient = new OSSClientBuilder().build(END_POINT.toString(), ACCESS_KEY_ID.toString(),
					ACCESS_KEY_SECRET.toString(), clientConfiguration);

			String key = ACTIVE.toString() + "/" + SERVICE_NAME.toString() + "/" + dir + "/" + filename;
			ossClient.putObject(BUCKET_NAME.toString(), key, new ByteArrayInputStream(fileBytes));
			return key;
		} finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}
	}



	/**
	 * 根据文件key，获取临时访问地址（8小时）
	 * 
	 * @param key
	 * @return
	 */
	public static String getUrl(String key) {
		OSS ossClient = null;
		try {
			ClientBuilderConfiguration clientConfiguration = new ClientBuilderConfiguration();
			clientConfiguration.setProtocol(Protocol.HTTPS);
			ossClient = new OSSClientBuilder().build(END_POINT.toString(), ACCESS_KEY_ID.toString(),
					ACCESS_KEY_SECRET.toString(), clientConfiguration);

			// 设置URL过期时间为8h
			Date expiration = new Date(new Date().getTime() + 8 * 60 * 60 * 1000);
			GeneratePresignedUrlRequest generatePresignedUrlRequest;
			generatePresignedUrlRequest = new GeneratePresignedUrlRequest(BUCKET_NAME.toString(), key);
			generatePresignedUrlRequest.setExpiration(expiration);
			URL url = ossClient.generatePresignedUrl(generatePresignedUrlRequest);
			return url.toString();
		} finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}
	}

	public static String getUrl(String key, long timeless) {
		OSS ossClient = null;
		try {
			ClientBuilderConfiguration clientConfiguration = new ClientBuilderConfiguration();
			clientConfiguration.setProtocol(Protocol.HTTPS);
			ossClient = new OSSClientBuilder().build(END_POINT.toString(), ACCESS_KEY_ID.toString(),
					ACCESS_KEY_SECRET.toString(), clientConfiguration);

			// 设置URL过期时间为1分钟
			Date expiration = new Date(new Date().getTime() + timeless);
			GeneratePresignedUrlRequest generatePresignedUrlRequest;
			generatePresignedUrlRequest = new GeneratePresignedUrlRequest(BUCKET_NAME.toString(), key);
			generatePresignedUrlRequest.setExpiration(expiration);
			URL url = ossClient.generatePresignedUrl(generatePresignedUrlRequest);
			return url.toString();
		} finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}
	}
}

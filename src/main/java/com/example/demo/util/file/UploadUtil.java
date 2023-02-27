package com.example.demo.util.file;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.example.demo.exception.BusinessException;
import com.example.demo.util.AliyunOSSUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class UploadUtil {

	/**
	 * 上传图片：jpg png
	 */
	public static UploadFile uploadPicture(MultipartFile file, String dir) throws IOException {
		String originalFilename = file.getOriginalFilename();
		long fileSize = file.getSize();
		byte[] fileBytes = file.getBytes();
		ByteArrayInputStream in = new ByteArrayInputStream(fileBytes);
		String type = FileTypeUtil.getType(in);
		if (!("jpg".equals(type) || "png".equals(type))) {
			throw new BusinessException("图片只支持jpg|png格式");
		}

		String filename;
		if (originalFilename == null) {
			filename = IdUtil.randomUUID() + "." + type;
		} else {
			String destFileName = originalFilename.replaceAll("[\\s\\\\/:\\*\\?\\\"<>\\|]", "_");
			destFileName = destFileName.split("\\.")[0];
			filename = destFileName + "_" + IdUtil.randomUUID() + "." + type;
		}

		String key;
		if (fileSize > 2 * 1048576) {// 2MB
			BigDecimal scale = new BigDecimal("2097152").divide(new BigDecimal(fileSize), 3, RoundingMode.HALF_UP);
			if (scale.compareTo(BigDecimal.ZERO) != 0) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				Thumbnails.of(new ByteArrayInputStream(fileBytes)).scale(scale.floatValue()).outputQuality(0.5f)
						.toOutputStream(out);
				key = AliyunOSSUtils.saveFile(dir, filename, out.toByteArray());
			} else {
				key = AliyunOSSUtils.saveFile(dir, filename, fileBytes);
			}
		} else {
			key = AliyunOSSUtils.saveFile(dir, filename, fileBytes);
		}

		String url = AliyunOSSUtils.getUrl(key);
		return new UploadFile(originalFilename, key, url);
	}

	/**
	 * 上传 docx 文件
	 */
	public static UploadFile uploadDocx(MultipartFile file, String dir) throws IOException {
		String originalFilename = file.getOriginalFilename();
		byte[] fileBytes = file.getBytes();
		if (StrUtil.isEmpty(originalFilename) || !originalFilename.endsWith("docx"))
			throw new BusinessException("文件只支持docx格式");

		String destFileName = originalFilename.replaceAll("[\\s\\\\/:\\*\\?\\\"<>\\|]", "_");
		destFileName = destFileName.split("\\.")[0];
		String filename = destFileName + "_" + IdUtil.randomUUID() + ".docx";

		String key = AliyunOSSUtils.saveFile(dir, filename, fileBytes);
		String url = AliyunOSSUtils.getUrl(key, 86564914755650L);
		return new UploadFile(originalFilename, key, url);
	}

	/**
	 * 上传 PDF 文件
	 */
	public static UploadFile uploadPDF(String originalFilename, byte[] fileBytes, String dir) throws IOException {
		String destFileName = originalFilename.replaceAll("[\\s\\\\/:\\*\\?\\\"<>\\|]", "_");
		destFileName = destFileName.split("\\.")[0];
		String filename = destFileName + "_" + IdUtil.randomUUID() + ".pdf";

		String key = AliyunOSSUtils.saveFile(dir, filename, fileBytes);
		String url = AliyunOSSUtils.getUrl(key, 86564914755650L);
		return new UploadFile(originalFilename, key, url);
	}


	/**
	 * 上传文件
	 */
	public static UploadFile uploadFile(MultipartFile file, String dir) throws IOException {
		String originalFilename = file.getOriginalFilename();
		byte[] fileBytes = file.getBytes();
		String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

		String filename;
		if (originalFilename == null) {
			filename = IdUtil.randomUUID() + "." + fileSuffix;
		} else {
			String destFileName = originalFilename.replaceAll("[\\s\\\\/:\\*\\?\\\"<>\\|]", "_");
			destFileName = destFileName.split("\\.")[0];
			filename = destFileName + "_" + IdUtil.randomUUID() + "." + fileSuffix;
		}

		String key = AliyunOSSUtils.saveFile(dir, filename, fileBytes);
		String url = AliyunOSSUtils.getUrl(key, 86564914755650L);
		return new UploadFile(originalFilename, key, url);
	}

	/**
	 * @param len  文件长度
	 * @param size 限制大小
	 * @param unit 限制单位（B,K,M,G）
	 * @描述 判断文件大小
	 */
	public static boolean checkFileSize(Long len, int size, String unit) {
		double fileSize = 0;
		if ("B".equalsIgnoreCase(unit)) {
			fileSize = (double) len;
		} else if ("K".equalsIgnoreCase(unit)) {
			fileSize = (double) len / 1024;
		} else if ("M".equalsIgnoreCase(unit)) {
			fileSize = (double) len / 1048576;
		} else if ("G".equalsIgnoreCase(unit)) {
			fileSize = (double) len / 1073741824;
		}
		return !(fileSize > size);
	}

}

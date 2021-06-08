package com.jetshine.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 * File文件转换
 * @author caisong
 * 2020年2月14日 下午6:05:17
 */
public class MultipartFileToFile {

	/**
	 * MultipartFile 转 File
	 *
	 * @param file
	 * @throws Exception
	 */
	public static File multipartFileToFile(MultipartFile file) throws Exception {
		File toFile = null;
		if (file ==null || file.getOriginalFilename().trim().equals("") || file.getSize() <= 0) {
			file = null;
		} else {
			InputStream ins = null;
			ins = file.getInputStream();
			if(file.getOriginalFilename().contains("/")){
				String[] split = file.getOriginalFilename().split("/");
				String fileName = split[split.length-1];
				toFile = new File(fileName);
			} else {
				toFile = new File(file.getOriginalFilename());
			}
			inputStreamToFile(ins, toFile);
			ins.close();
		}
		return toFile;
	}

	public static File byteArraysToFile(byte[] bytes,String fileName) throws Exception {
		File toFile = null;
		if (bytes !=null && bytes.length>0) {
			InputStream ins = null;
			ins = new ByteArrayInputStream(bytes);
			toFile = new File(fileName);
			inputStreamToFile(ins, toFile);
			ins.close();
		}
		return toFile;
	}


	// 获取流文件
	public static void inputStreamToFile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[10240];
			while ((bytesRead = ins.read(buffer, 0, 10240)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除本地临时文件
	 *
	 * @param file
	 */
	public static void delteTempFile(File file) {
		if (file != null) {
			File del = new File(file.toURI());
			del.delete();
		}
	}
}

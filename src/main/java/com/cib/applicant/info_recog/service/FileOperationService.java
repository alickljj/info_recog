/**
 * 
 */
package com.cib.applicant.info_recog.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.cib.applicant.info_recog.util.exception.ExceptionUtils;

/**
 * 文件操作服务
 * 
 * @since 2018年12月26日下午7:01:29
 * @author 刘俊杰
 */
@Service
public class FileOperationService {

	/**
	 * 获取转换后的properties文件信息
	 * 
	 * @since 2018年12月26日下午7:03:22
	 * @author 刘俊杰
	 * @param file
	 * @return
	 */
	public Properties getFilePro(File file) {
		Properties prop = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			prop.load(in);
		} catch (IOException e) {
			ExceptionUtils.wrapException(e);
			;
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					ExceptionUtils.wrapException(e);
				}
			}
		}
		return prop;
	}

	/**
	 * 将properties文件信息写入文件
	 * 
	 * @since 2018年12月26日下午7:03:22
	 * @author 刘俊杰
	 * @param file
	 * @param prop
	 */
	public void setFilePro(File file, Properties prop) {
		FileOutputStream oFile = null;
		try {
			oFile = new FileOutputStream(file.getPath(), false);
			// 将Properties类对象的属性列表保存到输出流中
			prop.store(oFile, "");

		} catch (Exception e) {
			ExceptionUtils.wrapException(e);
		} finally {
			if (oFile != null) {
				try {
					oFile.close();
				} catch (IOException e) {
					ExceptionUtils.wrapException(e);
				}
			}
		}
	}
}

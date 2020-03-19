/**
 * 
 */
package com.cib.applicant.info_recog.service;

import java.util.List;

import com.cib.applicant.info_recog.entity.doc.SourceParagraph;


/**
 * word 文档内容提取
 * 
 * @since 2018年8月7日上午11:03:47
 * @author 刘俊杰
 */
public interface IFileExtractService {
	//
	// /**
	// * 解析2003版本word
	// * @since 2018年8月7日上午11:14:40
	// * @author 刘俊杰
	// * @param fileBytes
	// */
	// String extractDoc(byte[] fileBytes);

	/**
	 * 解析2007以上版本word
	 * 
	 * @since 2018年8月7日上午11:14:50
	 * @author 刘俊杰
	 * @param fileBytes
	 */
	List<SourceParagraph> extractDocx(byte[] fileBytes);
}

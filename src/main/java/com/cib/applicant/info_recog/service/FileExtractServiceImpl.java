/**
 * 
 */
package com.cib.applicant.info_recog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cib.applicant.info_recog.entity.doc.SourceParagraph;
import com.cib.applicant.info_recog.service.word.WordExtract;
import com.cib.applicant.info_recog.util.exception.ExceptionUtils;

/**
 * word 文档内容提取
 * 
 * @since 2018年8月7日上午11:03:05
 * @author 刘俊杰
 */
@Service("IFileExtractService")
public class FileExtractServiceImpl implements IFileExtractService {

//	public static void main(String args[]) {
//		FileExtractServiceImpl extract = new FileExtractServiceImpl();
//		extract.extractDocx(new byte[10]);
//	}

	/**
	 * 解析2007以上版本word
	 * 
	 * @since 2018年8月7日上午11:13:11
	 * @author 刘俊杰
	 */
	@Override
	public List<SourceParagraph> extractDocx(byte[] fileBytes) {
		List<SourceParagraph> sourceParagraphs = new ArrayList<SourceParagraph>();
		try {
			WordExtract wordExtract = new WordExtract();
			sourceParagraphs = wordExtract.extractDocx(fileBytes);
		} catch (Exception e) {
			ExceptionUtils.wrapException(e);
		}
		return sourceParagraphs;
	}
	public static void main(String[] args) {
		String a = "aaaaa\n bbbbb";
		FileExtractServiceImpl f = new FileExtractServiceImpl();
	}
}

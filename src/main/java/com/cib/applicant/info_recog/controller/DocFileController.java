//package com.cib.applicant.info_recog.controller;
//
//import java.io.IOException;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.cib.applicant.info_recog.service.DocFileService;
//import com.cib.applicant.info_recog.util.exception.ExceptionUtils;
//
//@RestController("/doc/")
//public class DocFileController {
//
//	@Autowired
//	private DocFileService docFileService;
//
//	@PostMapping("upload")
//	public void upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletResponse resp) {
//		try {
//			byte[] readAllBytes = file.getBytes();
//			docFileService.extract(readAllBytes,resp);
//		} catch (IOException e) {
//			ExceptionUtils.wrapException(e);
//		}
//	}
//}

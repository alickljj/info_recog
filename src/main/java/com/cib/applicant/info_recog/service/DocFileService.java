package com.cib.applicant.info_recog.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cib.applicant.info_recog.entity.doc.SourceParagraph;
import com.cib.applicant.info_recog.entity.doc.TableCellNode;
import com.cib.applicant.info_recog.entity.info.ResumeVO;
import com.cib.applicant.info_recog.util.ComUtil;
import com.cib.applicant.info_recog.util.Constant;

public class DocFileService {
	
	public static void main(String[] args) throws Exception {
		byte[] readAllBytes = Files.readAllBytes(Paths.get("D:/西安分行行内推荐应聘登记表.docx"));
		DocFileService doc = new DocFileService();
		doc.extract(readAllBytes);
	}
	

	/**
	 * 解析上传的doc文件
	 * 
	 * @author wanglubin wanglubin@ai-strong.com
	 * @date 2020年3月17日 下午4:04:53
	 * @param readAllBytes
	 * @param resp
	 */
	public void extract(byte[] readAllBytes) {
		IFileExtractService extractService = new FileExtractServiceImpl();
		List<SourceParagraph> extractDocx = extractService.extractDocx(readAllBytes);
		JSONObject json = new JSONObject();
		json.put("tableSheet", extractDocx.get(0).getTableSheet());
		json.put("structTuple", extractDocx.get(0).getStructTuple());
		String transferHtml = ComUtil.transferHtml(json.toJSONString());
		System.out.println(transferHtml);

		List<List<TableCellNode>> childs = extractDocx.get(0).getTableSheet().getChilds();
		ResumeVO resume = new ResumeVO();
		for (int i = 0; i < childs.size(); i++) {
			List<TableCellNode> list = childs.get(i);
			for (int j = 0; j < list.size(); j++) {
				TableCellNode tableCellNode = list.get(j);
				if (!tableCellNode.isRe()) {
					String content = tableCellNode.getContent().replace("　", "");
					switch (content) {
					case "应聘部门":
						for (j++; j < list.size(); j++) {
							if (!list.get(j).isRe()) {
								resume.setDepart(list.get(j).getContent());
								break;
							}
						}
						break;
					case "应聘岗位":
						for (j++; j < list.size(); j++) {
							if (!list.get(j).isRe()) {
								resume.setPost(list.get(j).getContent());
								break;
							}
						}
						break;
					case "推荐人":
						for (j++; j < list.size(); j++) {
							if (!list.get(j).isRe()) {
								resume.setRecommender(list.get(j).getContent());
								break;
							}
						}
						break;
					case "姓名":
						for (j++; j < list.size(); j++) {
							if (!list.get(j).isRe()) {
								resume.setName(list.get(j).getContent());
								break;
							}
						}
						break;
					case "出生年月":
						for (j++; j < list.size(); j++) {
							if (!list.get(j).isRe()) {
								resume.setBorn(list.get(j).getContent());
								break;
							}
						}
						break;
					case "性别":
						for (j++; j < list.size(); j++) {
							if (!list.get(j).isRe()) {
								resume.setSex(list.get(j).getContent());
								break;
							}
						}
						break;
					case "金融工作年限":
						for (j++; j < list.size(); j++) {
							if (!list.get(j).isRe()) {
								resume.setFinancialYear(list.get(j).getContent());
								break;
							}
						}
						break;
					case "政治面貌":
						for (j++; j < list.size(); j++) {
							if (!list.get(j).isRe()) {
								resume.setPoliticCountenance(list.get(j).getContent());
								break;
							}
						}
						break;
					case "出生地":
						for (j++; j < list.size(); j++) {
							if (!list.get(j).isRe()) {
								resume.setLocation(list.get(j).getContent());
								break;
							}
						}
						break;
					case "身份证号码":
						for (j++; j < list.size(); j++) {
							if (!list.get(j).isRe()) {
								resume.setIdCard(list.get(j).getContent());
								break;
							}
						}
						break;
					case "手机":
						for (j++; j < list.size(); j++) {
							if (!list.get(j).isRe()) {
								resume.setPhone(list.get(j).getContent());
								break;
							}
						}
						break;

					case "入党(团)时间":
						for (j++; j < list.size(); j++) {
							if (!list.get(j).isRe()) {
								resume.setPartyDate(list.get(j).getContent());
								break;
							}
						}
						break;

					case "参加工作时间":
						for (j++; j < list.size(); j++) {
							if (!list.get(j).isRe()) {
								resume.setWorkDate(list.get(j).getContent());
								break;
							}
						}
						break;
					case "学历":
						for (j++; j < list.size(); j++) {
							if (!list.get(j).isRe()) {
								resume.setEducation(list.get(j).getContent());
								break;
							}
						}
						break;
					}
				}
			}
		}
		List<ResumeVO> list = new ArrayList<ResumeVO>();
		list.add(resume);
		export(list);
	}

	/**
	 * 文件导出
	 * 
	 * @author wanglubin wanglubin@ai-strong.com
	 * @date 2020年3月17日 下午4:10:39
	 * @param list
	 * @param resp
	 */
	private void export(List<ResumeVO> list) {
		HSSFWorkbook sxssfWorkbook = null;
		OutputStream out = null;
		try {
			sxssfWorkbook = new HSSFWorkbook();

			HSSFCellStyle style = sxssfWorkbook.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中

			HSSFSheet sheet = sxssfWorkbook.createSheet("兴业银行西安分行应聘人员信息表");
			HSSFRow header = sheet.createRow(0);
			HSSFCell cell = header.createCell(0);
			CellRangeAddress mergeCell = new CellRangeAddress(0, 0, 0, 27);
			sheet.addMergedRegion(mergeCell);
			cell.setCellValue("兴业银行西安分行应聘人员信息表");
			cell.setCellStyle(style);
			HSSFRow row = sheet.createRow(1);
			for (int i = 0; i < Constant.head.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(Constant.head[i]);
			}
			for (int i = 0; i < list.size(); i++) {
				ResumeVO resume = list.get(i);
				// 序号
				row = sheet.createRow(i + 2);
				cell = row.createCell(0);
				cell.setCellValue(i + 1);
				cell = row.createCell(1);
				cell.setCellValue(resume.getName());
				cell = row.createCell(2);
				cell.setCellValue(resume.getId());
				cell = row.createCell(3);
				cell.setCellValue(resume.getLine());
				cell = row.createCell(4);
				cell.setCellValue(resume.getDepart());
				cell = row.createCell(5);
				cell.setCellValue(resume.getPost());
				cell = row.createCell(6);
				cell.setCellValue(resume.getDuty());
				cell = row.createCell(7);
				cell.setCellValue(resume.getLevel());
				cell = row.createCell(8);
				cell.setCellValue(resume.getSex());
				cell = row.createCell(9);
				cell.setCellValue(resume.getIdCard());
				cell = row.createCell(10);
				cell.setCellValue(resume.getBorn());
				cell = row.createCell(11);
				cell.setCellValue(resume.getWorkDate());
				cell = row.createCell(12);
				cell.setCellValue(resume.getIndustryDate());
				cell = row.createCell(13);
				cell.setCellValue(resume.getEducation());
				cell = row.createCell(14);
				cell.setCellValue(resume.getPoliticCountenance());
				cell = row.createCell(15);
				cell.setCellValue(resume.getPartyDate());
				cell = row.createCell(16);
				cell.setCellValue(resume.getFirstDegree());
				cell = row.createCell(17);
				cell.setCellValue(resume.getFirstGraduate());
				cell = row.createCell(18);
				cell.setCellValue(resume.getInEducation());
				cell = row.createCell(19);
				cell.setCellValue(resume.getInGraduate());
				cell = row.createCell(20);
				cell.setCellValue(resume.getFinancialYear());
				cell = row.createCell(21);
				cell.setCellValue(resume.getLocation());
				cell = row.createCell(22);
				cell.setCellValue(resume.getOfficeLocation());
				cell = row.createCell(23);
				cell.setCellValue(resume.getBankCard());
				cell = row.createCell(24);
				cell.setCellValue(resume.getPhone());
				cell = row.createCell(25);
				cell.setCellValue(resume.getOriginalWork());
				cell = row.createCell(26);
				cell.setCellValue(resume.getGainInformation());
				cell = row.createCell(27);
				cell.setCellValue(resume.getRecommender());
			}
			File excelFile = null;
			excelFile = File.createTempFile("兴业银行西安分行应聘人员信息表", ".xls", new File("D://"));
			out = new FileOutputStream(excelFile);
			// 写内容，xls文件已经可以打开
			sxssfWorkbook.write(out);
			// 刷新缓冲区
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

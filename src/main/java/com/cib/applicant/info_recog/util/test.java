package com.cib.applicant.info_recog.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cib.applicant.info_recog.entity.doc.SourceParagraph;
import com.cib.applicant.info_recog.entity.doc.TableCellNode;
import com.cib.applicant.info_recog.service.FileExtractServiceImpl;
import com.cib.applicant.info_recog.service.IFileExtractService;

public class test {

	static class Resume {
		/**
		 * 姓名
		 */
		private String name;
		/**
		* ID号
		*/
		private String id;
		/**
		* 条线
		*/
		private String line;
		/**
		* 部门
		*/
		private String depart;
		/**
		* 岗位
		*/
		private String post;
		/**
		* 职务
		*/
		private String duty;
		/**
		* 级别
		*/
		private String level;
		/**
		* 性别
		*/
		private String sex;
		/**
		* 身份证号
		*/
		private String idCard;
		/**
		* 出生日期
		*/
		private String born;
		/**
		* 参加工作日期
		*/
		private String workDate;
		/**
		* 入行日期
		*/
		private String industryDate;
		/**
		* 学历
		*/
		private String education;
		/**
		* 政治面貌
		*/
		private String politicCountenance;
		/**
		* 入党时间
		*/
		private String partyDate;

		/**
		* 第一学历
		*/
		private String firstDegree;
		/**
		* 毕业院校及专业(第一学历)
		*/
		private String firstGraduate;
		/**
		* 在职教育
		*/
		private String inEducation;

		/**
		 * 毕业院校及专业（在职教育）
		 */
		private String inGraduate;
		/**
		* 金融行业从业年限
		*/
		private String financialYear;
		/**
		* 所在地区
		*/
		private String location;
		/**
		* 办公地点
		*/
		private String officeLocation;
		/**
		* 银行卡号
		*/
		private String bankCard;
		/**
		* 联系电话
		*/
		private String phone;
		/**
		* 原工作单位
		*/
		private String originalWork;
		/**
		* 获取应聘信息途径
		*/
		private String gainInformation;
		/**
		* 推荐人
		*/
		private String recommender;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getLine() {
			return line;
		}

		public void setLine(String line) {
			this.line = line;
		}

		public String getDepart() {
			return depart;
		}

		public void setDepart(String depart) {
			this.depart = depart;
		}

		public String getPost() {
			return post;
		}

		public void setPost(String post) {
			this.post = post;
		}

		public String getDuty() {
			return duty;
		}

		public void setDuty(String duty) {
			this.duty = duty;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getIdCard() {
			return idCard;
		}

		public void setIdCard(String idCard) {
			this.idCard = idCard;
		}

		public String getBorn() {
			return born;
		}

		public void setBorn(String born) {
			this.born = born;
		}

		public String getWorkDate() {
			return workDate;
		}

		public void setWorkDate(String workDate) {
			this.workDate = workDate;
		}

		public String getIndustryDate() {
			return industryDate;
		}

		public void setIndustryDate(String industryDate) {
			this.industryDate = industryDate;
		}

		public String getEducation() {
			return education;
		}

		public void setEducation(String education) {
			this.education = education;
		}

		public String getPoliticCountenance() {
			return politicCountenance;
		}

		public void setPoliticCountenance(String politicCountenance) {
			this.politicCountenance = politicCountenance;
		}

		public String getPartyDate() {
			return partyDate;
		}

		public void setPartyDate(String partyDate) {
			this.partyDate = partyDate;
		}

		public String getFirstDegree() {
			return firstDegree;
		}

		public void setFirstDegree(String firstDegree) {
			this.firstDegree = firstDegree;
		}

		public String getFirstGraduate() {
			return firstGraduate;
		}

		public void setFirstGraduate(String firstGraduate) {
			this.firstGraduate = firstGraduate;
		}

		public String getInEducation() {
			return inEducation;
		}

		public void setInEducation(String inEducation) {
			this.inEducation = inEducation;
		}

		public String getInGraduate() {
			return inGraduate;
		}

		public void setInGraduate(String inGraduate) {
			this.inGraduate = inGraduate;
		}

		public String getFinancialYear() {
			return financialYear;
		}

		public void setFinancialYear(String financialYear) {
			this.financialYear = financialYear;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getOfficeLocation() {
			return officeLocation;
		}

		public void setOfficeLocation(String officeLocation) {
			this.officeLocation = officeLocation;
		}

		public String getBankCard() {
			return bankCard;
		}

		public void setBankCard(String bankCard) {
			this.bankCard = bankCard;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getOriginalWork() {
			return originalWork;
		}

		public void setOriginalWork(String originalWork) {
			this.originalWork = originalWork;
		}

		public String getGainInformation() {
			return gainInformation;
		}

		public void setGainInformation(String gainInformation) {
			this.gainInformation = gainInformation;
		}

		public String getRecommender() {
			return recommender;
		}

		public void setRecommender(String recommender) {
			this.recommender = recommender;
		}

	}

	/**
	 * 将tableSheet转化为html代码
	 * 
	 * @author wanglubin wanglubin@ai-strong.com
	 * @date 2018年9月28日 下午2:20:08
	 * @param jsonStr
	 * @return
	 */
	public static String transferHtml(String jsonStr) {
		JSONObject json = JSONObject.parseObject(jsonStr);
		JSONArray table = json.getJSONObject("tableSheet").getJSONArray("childs");
		StringBuffer html = new StringBuffer();
		html.append("<table class='doc_tbl' border=1>");
		for (int i = 0; i < table.size(); i++) {
			JSONArray row = table.getJSONArray(i);
			html.append("<tr>");
			for (int j = 0; j < row.size(); j++) {
				int rs = row.getJSONObject(j).getIntValue("height");
				int cs = row.getJSONObject(j).getIntValue("width");
				boolean re = row.getJSONObject(j).getBoolean("re");
				String content = row.getJSONObject(j).getString("content");
				if (!re) {
					html.append("<td rowspan=" + rs + " colspan=" + cs + " >");
					html.append(content);
					html.append("</td>");
				}
			}
			html.append("</tr>");
		}
		html.append("</table>");
		return html.toString();
	}

	public static void main(String[] args) throws IOException {
		IFileExtractService s = new FileExtractServiceImpl();
		byte[] readAllBytes = Files.readAllBytes(Paths.get("D:/西安分行行内推荐应聘登记表.docx"));
		List<SourceParagraph> extractDocx = s.extractDocx(readAllBytes);
		JSONObject json = new JSONObject();
		json.put("tableSheet", extractDocx.get(0).getTableSheet());
		json.put("structTuple", extractDocx.get(0).getStructTuple());
		String transferHtml = transferHtml(json.toJSONString());
		System.out.println(transferHtml);

		List<List<TableCellNode>> childs = extractDocx.get(0).getTableSheet().getChilds();
		Resume resume = new Resume();
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

		List<Resume> list = new ArrayList<Resume>();
		list.add(resume);

		export(list);
	}

	public static void export(List<Resume> list) throws IOException {
		String head[] = new String[] { "序号", "姓名", "ID号", "条线", "部门", "岗位", "职务", "级别", "性别", "身份证号", "出生日期", "参加工作日期", "入行日期", "学历", "政治面貌", "入党时间",
				"第一学历", "毕业院校及专业(第一学历)", "在职教育", "毕业院校及专业（在职教育）", "金融行业从业年限", "所在地区", "办公地点", "银行卡号", "联系电话", "原工作单位", "获取应聘信息途径", "推荐人" };
		File excelFile = null;
		FileOutputStream fOut = null;
		HSSFWorkbook sxssfWorkbook = null;
		excelFile = File.createTempFile("兴业银行西安分行应聘人员信息表", ".xls", new File("D://"));
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
		for (int i = 0; i < head.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(head[i]);
		}
		for (int i = 0; i < list.size(); i++) {
			Resume resume = list.get(i);
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
		fOut = new FileOutputStream(excelFile);
		//写内容，xls文件已经可以打开
		sxssfWorkbook.write(fOut);
		//刷新缓冲区
		fOut.flush();
	}
}

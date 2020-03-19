/**
 * 
 */
package com.cib.applicant.info_recog.entity.doc;


import java.util.List;

/**
 * 文档内容解析结果结构体
 * @since 2018年7月23日上午11:03:13
 * @author 刘俊杰
 */
public class SourceParagraph {

	//段落原始文本
	private String rawText;
	//预处理后文本
	private String processedText;
	//表格对象
	private SimpleTable tableSheet;
	//解析后表格
	private List<StructTuple<String, String, String>> structTuple;
	//类型1-文本，2-表格
	private int type;
	/**
	 * @return the rawText
	 */
	public String getRawText() {
		return rawText;
	}
	/**
	 * @param rawText the rawText to set
	 */
	public void setRawText(String rawText) {
		this.rawText = rawText;
	}
	/**
	 * @return the processedText
	 */
	public String getProcessedText() {
		return processedText;
	}
	/**
	 * @param processedText the processedText to set
	 */
	public void setProcessedText(String processedText) {
		this.processedText = processedText;
	}
	/**
	 * @return the tableSheet
	 */
	public SimpleTable getTableSheet() {
		return tableSheet;
	}
	/**
	 * @param tableSheet the tableSheet to set
	 */
	public void setTableSheet(SimpleTable tableSheet) {
		this.tableSheet = tableSheet;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the structTuple
	 */
	public List<StructTuple<String, String, String>> getStructTuple() {
		return structTuple;
	}
	/**
     * @param structTuple the structTuple to set
     */
	public void setStructTuple(List<StructTuple<String, String, String>> structTuple) {
		this.structTuple = structTuple;
	}
	
	
}

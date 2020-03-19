/**
 * 
 */
package com.cib.applicant.info_recog.entity.doc;

/**
 * 
 * @since 2018年9月26日下午2:17:59
 * @author 刘俊杰
 */
public class TableCellNode {

    private String content;
    private int height = 1;
    private int width = 1;
    private boolean re = false;
    private String id;
    private boolean isValue = false;
    
    
	/**
	 * @return the isValue
	 */
	public boolean isValue() {
		return isValue;
	}
	/**
	 * @param isValue the isValue to set
	 */
	public void setValue(boolean isValue) {
		this.isValue = isValue;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return the reRow
	 */
	public boolean isRe() {
		return re;
	}
	/**
	 * @param reRow the reRow to set
	 */
	public void setRe(boolean re) {
		this.re = re;
	}

    
}

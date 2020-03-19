/**
 * 
 */
package com.cib.applicant.info_recog.entity.doc;

import java.util.List;

/**
 * 
 * @since 2018年9月26日下午2:27:04
 * @author 刘俊杰
 */
public class SimpleTable {

    private List<List<TableCellNode>> childs;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	/**
	 * @return the childs
	 */
	public List<List<TableCellNode>> getChilds() {
		return childs;
	}

	/**
	 * @param childs the childs to set
	 */
	public void setChilds(List<List<TableCellNode>> childs) {
		this.childs = childs;
	}

}

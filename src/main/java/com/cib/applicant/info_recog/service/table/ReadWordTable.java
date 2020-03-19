package com.cib.applicant.info_recog.service.table;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;

import com.cib.applicant.info_recog.entity.doc.SimpleTable;
import com.cib.applicant.info_recog.entity.doc.StructTuple;
import com.cib.applicant.info_recog.entity.doc.TableCellNode;

public class ReadWordTable {
	public SimpleTable readTable(XWPFTable table) throws IOException {
		// 表格行数
		int tableRowsSize = table.getRows().size();
		SimpleTable simpleTable = new SimpleTable();
		List<List<TableCellNode>> childs = new ArrayList<List<TableCellNode>>();

		int size = table.getRow(0).getTableCells().size();
		int i = 0;
		if (size == 1 && isTitle(table.getRow(0).getCell(0).getText())) {
			simpleTable.setContent(table.getRow(0).getCell(0).getText());
			i = 1;
		}

		for (; i < tableRowsSize; i++) {
			List<TableCellNode> list = new ArrayList<TableCellNode>();
			// 获取单元格数量
			int tableCellsSize = table.getRow(i).getTableCells().size();
			for (int j = 0; j < tableCellsSize; j++) {
				TableCellNode tcn = new TableCellNode();
				tcn.setId(i + "_" + j);
				XWPFTableCell tableCell = table.getRow(i).getCell(j);
				// 获取单元格的属性
				CTTcPr tcPr = tableCell.getCTTc().getTcPr();
				String text = tableCell.getText();
				int width = getColspan(tcPr);
				tcn.setWidth(width);
				tcn.setContent(text);
				tcn.setRe(false);
				tcn.setId(i + "_" + j);
				tcn.setValue(!isContainText(text));

				// 列合并的第一行设置高度为0
				if (isRestartRow(tableCell)) {
					tcn.setHeight(0);
				}

				// 列合并的中间行设置高度为-1
				if (isContinueRow(tableCell)) {
					tcn.setHeight(-1);
				}

				list.add(tcn);
				// 拆分列
				for (int k = 1; k < width; k++) {
					TableCellNode ttcn = new TableCellNode();
					ttcn.setWidth(1);
					ttcn.setRe(true);
					ttcn.setValue(!isContainText(text));
					ttcn.setContent(text);
					ttcn.setId(i + "_" + j);
					list.add(ttcn);
				}
			}
			childs.add(list);
		}
		simpleTable.setChilds(childs);

		// 拆分行
		simpleTable = breakUpRow(simpleTable);
		return simpleTable;
	}

	public boolean isTitle(String text) {
		boolean isHase = false;
		Pattern pc = Pattern.compile(".*表.*");
		Matcher mc = pc.matcher(text);
		if (mc.find()) {
			isHase = true;
		}
		Pattern pe = Pattern.compile(".*图.*");
		Matcher me = pe.matcher(text);
		if (me.find()) {
			isHase = true;
		}
		return isHase;
	}

	/**
	 * 拆分行来获取列数并将合并单元格复制
	 * 
	 * @author wanglubin wanglubin@ai-strong.com
	 * @date 2018年10月30日 上午11:30:22
	 * @param simpleTable
	 * @return
	 */
	private SimpleTable breakUpRow(SimpleTable simpleTable) {
		List<List<TableCellNode>> childs = simpleTable.getChilds();
		for (int i = 0; i < childs.size(); i++) {
			List<TableCellNode> list = childs.get(i);
			for (int j = 0; j < list.size(); j++) {
				TableCellNode tcn = list.get(j);
				if (tcn.getHeight() == 0) {
					List<Boolean> tempList = new ArrayList<>();
					int num = getColspan(childs, i, j, tempList);
					tcn.setHeight(num);
				}
				for (int k = 1; k < tcn.getHeight(); k++) {
					TableCellNode temp = childs.get(i + k).get(j);
					temp.setHeight(1);
					temp.setRe(true);
					temp.setContent(tcn.getContent());
					temp.setValue(!isContainText(tcn.getContent()));
					temp.setId(tcn.getId());
				}
				for (int k = 1; k < tcn.getWidth(); k++) {
					TableCellNode temp = list.get(j + k);
					temp.setContent(tcn.getContent());
					temp.setValue(!isContainText(tcn.getContent()));
					temp.setId(tcn.getId());
				}
				if (tcn.isRe()) {
					tcn.setHeight(1);
					tcn.setWidth(1);
				}
			}
		}
		simpleTable.setChilds(childs);
		return simpleTable;
	}

	/**
	 * 获取列数
	 * 
	 * @author wanglubin wanglubin@ai-strong.com
	 * @date 2018年10月30日 上午11:29:52
	 * @return
	 */
	private int getColspan(List<List<TableCellNode>> childs, int i, int j, List<Boolean> list) {
		i = i + 1;
		TableCellNode tcn = childs.get(i).get(j);
		if (tcn.getHeight() == -1) {
			list.add(true);
			getColspan(childs, i, j, list);
		}
		return list.size() + 1;
	}

	/**
	 * 判断是否包含文字
	 * 
	 * @author wanglubin wanglubin@ai-strong.com
	 * @date 2018年10月30日 上午11:29:46
	 * @param text
	 * @return
	 */
	private boolean isContainText(String text) {
		boolean isHase = false;
		Pattern pc = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher mc = pc.matcher(text);
		if (mc.find()) {
			isHase = true;
		}
		Pattern pe = Pattern.compile("[a-zA-z]");
		Matcher me = pe.matcher(text);
		if (me.find()) {
			isHase = true;
		}
		return isHase;
	}

	/**
	 * 获取当前单元格的colspan（列合并）的列数
	 * 
	 * @param tcPr
	 *            单元格属性
	 * @return
	 */
	public int getColspan(CTTcPr tcPr) {
		// 判断是否存在列合并
		CTDecimalNumber gridSpan = null;
		if ((gridSpan = tcPr.getGridSpan()) != null) { // 合并的起始列
			// 获取合并的列数
			BigInteger num = gridSpan.getVal();
			return num.intValue();
		} else { // 其他被合并的列或正常列
			return 1;
		}
	}

	/**
	 * 判断是否是合并行的起始行单元格
	 * 
	 * @param tableCell
	 * @return
	 */
	public boolean isRestartRow(XWPFTableCell tableCell) {
		CTTcPr tcPr = tableCell.getCTTc().getTcPr();
		if (tcPr.getVMerge() == null) {
			return false;
		}
		if (tcPr.getVMerge().getVal() == null) {
			return false;
		}
		if (tcPr.getVMerge().getVal().toString().equalsIgnoreCase("restart")) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是合并行的中间行单元格（包括结尾的最后一行的单元格）
	 * 
	 * @param tableCell
	 * @return
	 */
	public boolean isContinueRow(XWPFTableCell tableCell) {
		CTTcPr tcPr = tableCell.getCTTc().getTcPr();
		if (tcPr.getVMerge() == null) {
			return false;
		}
		if (tcPr.getVMerge().getVal() == null) {
			return true;
		}
		return false;
	}

	/**
	 * 有线条表格数据还原
	 * 
	 * @since 2018年10月8日下午2:07:23
	 * @author 刘俊杰
	 * @param tableCellArray
	 * @return
	 */
	public List<StructTuple<String, String, String>> getHaseColumnTuples(List<List<TableCellNode>> tableCellArray) {
		List<StructTuple<String, String, String>> tuples = new ArrayList<StructTuple<String, String, String>>();
		for (int rowNum = 0; rowNum < tableCellArray.size(); rowNum++) {
			List<TableCellNode> lineCell = tableCellArray.get(rowNum);
			for (int colNum = 0; colNum < lineCell.size(); colNum++) {
				TableCellNode cell = lineCell.get(colNum);
				// 如果cell包含中英文或者cell是复制扩展的则跳过
				if (cell.isRe() || !cell.isValue()) {
					continue;
				}
				String colTitle = "";
				String rowTitle = "";
				int startX = colNum;
				int startY = rowNum;
				while (startX >= 0) {
					TableCellNode colTitleCell = tableCellArray.get(rowNum).get(startX);
					if (!colTitleCell.isValue()) {
						String colContents = colTitleCell.getContent();
						colTitle = colContents + colTitle;

					}
					startX--;
				}
				while (startY >= 0) {
					TableCellNode rowTitleCell = tableCellArray.get(startY).get(colNum);
					if (!rowTitleCell.isValue()) {
						String rowContents = rowTitleCell.getContent();
						rowTitle = rowContents + rowTitle;

					}
					startY--;
				}
				String content = cell.getContent();
				if ("".equals(content) || content == null) {
					content = " ";
				}
				StructTuple<String, String, String> tuple = new StructTuple<String, String, String>(colTitle, rowTitle,
						content);
				tuples.add(tuple);
			}
		}
		return tuples;
	}

}

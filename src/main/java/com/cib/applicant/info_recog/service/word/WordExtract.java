package com.cib.applicant.info_recog.service.word;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.springframework.util.StringUtils;

import com.cib.applicant.info_recog.entity.doc.SimpleTable;
import com.cib.applicant.info_recog.entity.doc.SourceParagraph;
import com.cib.applicant.info_recog.entity.doc.StructTuple;
import com.cib.applicant.info_recog.service.table.ReadWordTable;

public class WordExtract {

	private Logger log = LogManager.getLogger(WordExtract.class);

	public List<SourceParagraph> extractDocx(byte[] fileBytes) {
		InputStream is;
		ReadWordTable readWord = new ReadWordTable();
		List<SourceParagraph> sourceParagraphs = new ArrayList<SourceParagraph>();
		try {
			is = new ByteArrayInputStream(fileBytes);
			XWPFDocument doc = new XWPFDocument(is);
			CTDocument1 ctDoc = doc.getDocument();
			XmlCursor docCursor = ctDoc.newCursor();
			docCursor.selectPath("./*");
			while (docCursor.toNextSelection()) {
				XmlObject o = docCursor.getObject();
				if (o instanceof CTBody) {
					XmlCursor bodyCursor = o.newCursor();
					bodyCursor.selectPath("./*");
					List<String> paragraphs = new ArrayList<String>();
					while (bodyCursor.toNextSelection()) {
						XmlObject bodyObj = bodyCursor.getObject();
						SourceParagraph sourceParagraph = new SourceParagraph();
						// 判断是否文字信息
						if (bodyObj instanceof CTP) {
							XWPFParagraph p = new XWPFParagraph((CTP) bodyObj, doc);
							List<XWPFRun> runs = p.getRuns();
							StringBuffer out = new StringBuffer();
							for (XWPFRun run : runs) {
								// 去除角注
								if (run.getCTR().getFootnoteReferenceArray().length < 1) {
									out.append(run);
								}
							}
							String str = out.toString();
							paragraphs.add(str);
							sourceParagraph.setType(1);
							sourceParagraph.setRawText(str);
							sourceParagraphs.add(sourceParagraph);
						}
						// 判断是否表格信息
						else if (bodyObj instanceof CTTbl) {
							XWPFTable table = new XWPFTable((CTTbl) bodyObj, doc);
							SimpleTable simpleTable = readWord.readTable(table);
							if (StringUtils.isEmpty(simpleTable.getContent())) {
								if (!paragraphs.isEmpty()) {
									String text = paragraphs.get(paragraphs.size() - 1);
									boolean isTitle = readWord.isTitle(text);
									if (isTitle) {
										simpleTable.setContent(text);
									}
								}
							}
							List<StructTuple<String, String, String>> structTuple = readWord
									.getHaseColumnTuples(simpleTable.getChilds());
							sourceParagraph.setType(2);
							sourceParagraph.setTableSheet(simpleTable);
							sourceParagraph.setStructTuple(structTuple);
							sourceParagraphs.add(sourceParagraph);
						}
					}
					bodyCursor.dispose();
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return sourceParagraphs;
	}
}

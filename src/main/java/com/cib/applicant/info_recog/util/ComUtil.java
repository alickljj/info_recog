package com.cib.applicant.info_recog.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 通用工具类
 * 
 * @author wanglubin wanglubin@ai-strong.com
 * @date 2020年3月17日 下午3:59:16
 */
public class ComUtil {
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
}

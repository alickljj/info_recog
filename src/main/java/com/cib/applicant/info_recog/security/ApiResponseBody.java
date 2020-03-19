package com.cib.applicant.info_recog.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.alibaba.fastjson.JSONObject;
import com.cib.applicant.info_recog.util.WebUtils;

@ControllerAdvice
public class ApiResponseBody implements ResponseBodyAdvice<Object> {

	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		List<String> methods = new ArrayList<String>();
		String[] tmp = { "uploadEditorImg", "upload", "fileUpload" };
		for (String method : tmp) {
			methods.add(method);
		}
		if (methods.contains(returnType.getMethod().getName())) {
			return false;
		} else
			return true;
	}

	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		// 判断是否为ajax请求
		if (WebUtils.isAjax(request)) {
			JSONObject json = new JSONObject();
			json.put("data", body);
			json.put("success", true);
			return json.toString();
		} 
		if ("error".equals(returnType.getMethod().getName())) {
			JSONObject jsonbody = (JSONObject) JSONObject.toJSON(body);
			if("org.springframework.security.authentication.BadCredentialsException".equals(jsonbody.getString("exception"))){
				jsonbody.put("status", 209);
				jsonbody.remove("timestamp");
				jsonbody.remove("exception");
				jsonbody.remove("path");
				return jsonbody;
			}
		}
		return body;

	}

}

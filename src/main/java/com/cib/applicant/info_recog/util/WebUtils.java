package com.cib.applicant.info_recog.util;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.lang.Assert;

public class WebUtils {
	private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
	private static final String X_REQUESTED_WITH = "X-Requested-With";

	// 判断是否为ajax请求
	public static Boolean isAjax(ServerHttpRequest req) {
		List<String> list = req.getHeaders().get(X_REQUESTED_WITH);
		if (list != null && list.contains(XML_HTTP_REQUEST)) {
			return true;
		} else {
			return false;
		}
	}

	public static Boolean isAjax(HttpServletRequest req) {
		String header = req.getHeader(X_REQUESTED_WITH);
		if (!StringUtils.isEmpty(header) && XML_HTTP_REQUEST.equals(header)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 在reponse中增加cookies默认有效时间1一小时
	 * 
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param response
	 * @param timeout
	 *            cookie有效时间单位：秒
	 * 
	 */
	public static void addCookie(String name, String value, HttpServletResponse response, Integer timeout) {
		Cookie cookie = new Cookie(name, value);
		//cookie.setSecure(true);
		if (timeout != null) {
			cookie.setMaxAge(timeout);
		} else {
			cookie.setMaxAge(60 * 60);
		}
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return cookie
	 */
	public static String getCookieByName(HttpServletRequest request, String name) {
		Cookie[] cookie = request.getCookies();
		Assert.notNull(cookie, "No authentication data provided");
		for (Cookie c : cookie) {
			if (c.getName().equals(name)) {
				return c.getValue();
			}
		}

		return "";
	}

	/** 
	 * 从request中获取IP地址
	 * @since 2018年7月5日上午10:45:53
	 * @author 刘俊杰
	 * @param request
	 * @return String ip地址
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}

/**
 * 
 */
package com.cib.applicant.info_recog.security.model.token;

/**
 * 
 * @since 2018年5月14日下午10:22:05
 * @author 刘俊杰
 */
public class TokenDTO {

	/**
	 * token
	 */
	private String token;
	
	/**
	 * 刷新token
	 */
	private String refreshToken;

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the refreshToken
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * @param refreshToken the refreshToken to set
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	
	
}

package com.cib.applicant.info_recog.service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import com.cib.applicant.info_recog.util.RSAUtil;
import com.cib.applicant.info_recog.util.exception.ExceptionUtils;


/**
 * RSA服务
 * 
 * @since 2018年5月16日上午11:34:20
 * @author 刘俊杰
 */
@Service
public class RsaService {
	/**
	 * 加解密解决方案,支持大量的密码算法，并提供实现。
	 */
	private BouncyCastleProvider bcProvider = new BouncyCastleProvider();
	/**
	 * 提供加密和解密实现功能。
	 */
	private Cipher cipher;
	/**
	 * 密钥对
	 */
	private KeyPair keyPair;
	/**
	 * 密钥对生成方法
	 */
	private KeyPairGenerator keyPairGen;

	/**
	 * 字符转byte
	 * 
	 * @since 2018年5月16日上午11:34:44
	 * @author 刘俊杰
	 * @param cha
	 * @return
	 */
	private static byte charToByte(char cha) {
		return (byte) "0123456789ABCDEF".indexOf(cha);
	}

	public String decrypt(String data) {
		if (this.keyPair == null) {
			ExceptionUtils.wrapBusinessException("密钥空");
		}
		// 获取私钥解密
		PrivateKey pkey = this.keyPair.getPrivate();
		// 将字符串转换成byte数组，js加密的时候会导致byte[]类型密文比指定的长，
		// js加密的密文偶尔会比正确的密文byte[]多出一byte，里面是0，
		byte[] raw = this.hexStringToBytes(data);
		try {
			byte[] de_ras = RSAUtil.decrypt(pkey, raw, this.cipher);
			StringBuffer strBuff = new StringBuffer();
			strBuff.append(new String(de_ras));
			strBuff = strBuff.reverse();
			return strBuff.toString();
		} catch (Exception ex) {
			ExceptionUtils.wrapException(ex);
		}
		return null;
	}

	/** 
	 * 获取RSA公钥
	 * @since 2018年5月25日上午9:57:57
	 * @author 刘俊杰
	 * @return PublicKey 公钥
	 */
	public PublicKey getRSAPublicKey() {

		if (this.keyPair == null) {
			try {
				this.cipher = Cipher.getInstance("RSA", this.bcProvider);
				this.keyPairGen = KeyPairGenerator.getInstance("RSA", this.bcProvider);
				this.keyPair = RSAUtil.generateKeyPair(this.keyPairGen);
			} catch (Exception ex) {
				ExceptionUtils.wrapException(ex);
			}
		}
		return this.keyPair.getPublic();
	}

	/** 
	 * 前端js加密后解密前转byte[]需要特殊处理
	 * @since 2018年5月25日上午9:58:14
	 * @author 刘俊杰
	 * @param hexString
	 * @return
	 */
	private byte[] hexStringToBytes(String hexString) {

		if (hexString == null || hexString.equals("")) {
			return null;
		}

		String str = hexString.toUpperCase();
		int length = str.length() / 2;
		char[] hexChars = str.toCharArray();
		byte[] dbyte = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			dbyte[i] = (byte) (RsaService.charToByte(hexChars[pos]) << 4 | RsaService.charToByte(hexChars[pos + 1]));
		}
		return dbyte;
	}

}

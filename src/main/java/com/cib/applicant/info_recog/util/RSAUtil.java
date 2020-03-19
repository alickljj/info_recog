package com.cib.applicant.info_recog.util;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.cib.applicant.info_recog.util.exception.ExceptionUtils;

/**
 * RSA工具类
 * 
 * @since 2018年5月25日上午9:59:18
 * @author 刘俊杰
 */
public class RSAUtil {

	private RSAUtil() {
	}

	/**
	 * 根据私钥解密
	 * 
	 * @since 2018年5月25日上午9:59:27
	 * @author 刘俊杰
	 * @param pk
	 *            私钥
	 * @param raw
	 *            解密源byte数组
	 * @param cipher
	 *            加密和解密实现功能对象
	 * @return byte[] 明文对象
	 */
	public static byte[] decrypt(PrivateKey pk, byte[] raw, Cipher cipher) {

		ByteArrayOutputStream bout = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, pk);

			bout = new ByteArrayOutputStream(64);
			int num = 0;
			int blockSize = cipher.getBlockSize();
			while (raw.length - num * blockSize > 0) {
				bout.write(cipher.doFinal(raw, num * blockSize, blockSize));
				num++;
			}
			return bout.toByteArray();
		} catch (Exception e) {
			ExceptionUtils.wrapException(e);
		}
		return null;
	}

	/**
	 * 根据公钥加密
	 * 
	 * @since 2018年5月25日上午10:00:40
	 * @author 刘俊杰
	 * @param pk
	 *            RSA公钥
	 * @param data
	 *            待加密明文byte数组
	 * @return byte[]
	 */
	public static byte[] encrypt(PublicKey pk, byte[] data) {
		try {
			Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();
			int outputSize = cipher.getOutputSize(data.length);
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int num = 0;
			while (data.length - num * blockSize > 0) {
				if (data.length - num * blockSize > blockSize) {
					cipher.doFinal(data, num * blockSize, blockSize, raw, num * outputSize);
				} else {

					cipher.doFinal(data, num * blockSize, data.length - num * blockSize, raw, num * outputSize);
				}
				num++;
			}
			return raw;
		} catch (Exception e) {
			ExceptionUtils.wrapException(e);
		}
		return null;
	}

	/**
	 * 生成RSA密钥对
	 * 
	 * @since 2018年5月25日上午10:01:38
	 * @author 刘俊杰
	 * @param keyPairGen
	 *            密钥对生成方法
	 * @return KeyPair 生成密钥对
	 */
	public static KeyPair generateKeyPair(KeyPairGenerator keyPairGen) {
		try {
			final int key_size = 512;
			keyPairGen.initialize(key_size, new SecureRandom());
			KeyPair keyPair = keyPairGen.generateKeyPair();
			return keyPair;
		} catch (Exception e) {
			ExceptionUtils.wrapException(e);
		}
		return null;
	}

	/**
	 * 根据modulus和privateExponent生成私钥
	 * 
	 * @since 2018年5月25日上午10:02:56
	 * @author 刘俊杰
	 * @param modulus
	 * @param privateExponent
	 * @return
	 */
	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA", new BouncyCastleProvider());
			RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus),
					new BigInteger(privateExponent));
			return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
		} catch (NoSuchAlgorithmException ex) {
			ExceptionUtils.wrapException(ex);
		} catch (InvalidKeySpecException ex) {
			ExceptionUtils.wrapException(ex);
		}
		return null;
	}

	/**
	 * 根据modulus和privateExponent生成公钥钥
	 * 
	 * @since 2018年5月25日上午10:03:46
	 * @author 刘俊杰
	 * @param modulus
	 * @param publicExponent
	 * @return
	 */
	public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA", new BouncyCastleProvider());
			RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
			return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
		} catch (NoSuchAlgorithmException ex) {
			ExceptionUtils.wrapException(ex);
		} catch (InvalidKeySpecException ex) {
			ExceptionUtils.wrapException(ex);
		}
		return null;
	}
}

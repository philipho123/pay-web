package com.woniu.pay.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;


import sun.misc.BASE64Encoder;
/**
 * @author Jahn
 * @since 2012-3-27
 * @version 1.0
 */
public class EncryptUtils {
	
    public static Key createKey() throws NoSuchAlgorithmException {//创建一个密钥
        Security.insertProviderAt(new com.sun.crypto.provider.SunJCE(), 1);
        KeyGenerator generator = KeyGenerator.getInstance("DES");
        generator.init(new SecureRandom());

        return generator.generateKey();
    }
    public static Key getKey(InputStream is) {
        try {
            ObjectInputStream ois = new ObjectInputStream(is);
            return (Key) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    private static byte[] doEncrypt(Key key, byte[] data) {//对数据进行加密
        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public static InputStream doDecrypt(Key key, InputStream in) {//对数据进行解密
        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] tmpbuf = new byte[1024];
            int count = 0;
            while ((count = in.read(tmpbuf)) != -1) {
                bout.write(tmpbuf, 0, count);
                tmpbuf = new byte[1024];
            }
            in.close();
            byte[] orgData = bout.toByteArray();
            byte[] raw = cipher.doFinal(orgData);
            return new ByteArrayInputStream(raw);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
	/**
	 * 使用MD5加密
	 * @param signature
	 * @return
	 * @throws Exception
	 */
	public static String encrpt2MD5ByHass(String s, String charsetName) {
		Charset charset = Charset.forName(charsetName);
		byte[] defaultBytes = charset.encode(s).array();
		MessageDigest algorithmOfMD5 = null;
		try {
			algorithmOfMD5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(algorithmOfMD5==null)
			return null;
		algorithmOfMD5.reset();
		algorithmOfMD5.update(defaultBytes);
		byte messageDigest[] = algorithmOfMD5.digest();		             
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < messageDigest.length; i++) {
			String hex = Integer.toHexString(0xFF & messageDigest[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		
		return hexString.toString().toUpperCase();
	}
	/**
	 * 使用MD5加密
	 * @param signature
	 * @return
	 * @throws Exception
	 */
	public static String encrypt2MD5(String signature) throws RuntimeException {
		if(signature == null){
			return null;
		}
		MessageDigest algorithmOfMD5 = null;
		try {
			algorithmOfMD5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("加密算法错误");
		}
		String ret = null;
		byte[] plainText = signature.getBytes();
		algorithmOfMD5.update(plainText);
		ret = bytes2HexString(algorithmOfMD5.digest());
		return ret.toUpperCase();
	}

	/**
	 * 二行制转字符串
	 * 
	 * @param bytes 字节数组
	 * @return 二进制字符串
	 */
	public static String bytes2HexString(byte[] bytes) {
		
		StringBuffer sbHexstr = new StringBuffer();
		if (bytes != null) {
			for (byte b : bytes) {
				String tmp = (Integer.toHexString(b & 0XFF));
				if (tmp.length() == 1) 
					sbHexstr.append("0");
				sbHexstr.append(tmp);
				
			}
		}
		return sbHexstr.toString().toUpperCase();
	}
	/**
	 * SHA加密
	 * 
	 * @param stext
	 * @return 字符
	 */
	public static String encrtyBySHA(String stext) throws NoSuchAlgorithmException{
		java.security.MessageDigest alga=java.security.MessageDigest.getInstance("SHA-1");
		alga.update(stext.getBytes());
		byte[] digesta = alga.digest();
		 
		StringBuffer sb= new StringBuffer();
	    String stmp="";
	    for (int i=0;i<digesta.length;i++){
	       stmp=(java.lang.Integer.toHexString(digesta[i] & 0XFF));
	       if (stmp.length()==1) 
	    	   sb.append("0"+stmp);
	       else 
	    	   sb.append(stmp);
	    }
	    return sb.toString();
	}
	
	public static String enCode(String str1) { // 加码函数，将系统用到的控制符变成转义符号
		byte[] bsrc = str1.getBytes();
		String dest = "", str;
		byte bb;
		int num;
		if (bsrc == null) {
			return "";
		}
		for (int ii = 0; ii < bsrc.length; ii++) {
			bb = bsrc[ii];
			if (bb >= 0) {
				num = bb;
			} else {
				num = (bb & 0x7F) + (1 << 7);
			}
			str = Integer.toHexString(num);
			if (str.length() < 2) {
				str = "0" + str;
			}
			dest += str.toUpperCase();
		}
		return dest;
	}
	
	public static String deCode(String src) { // 还原

		if (src.length() < 2 || src.length() % 2 != 0  ) {
			return null;
		}
		byte[] dest = new byte[src.length() / 2];
		byte rb;
		String str;
		Arrays.fill(dest, (byte) 0);
		//int index = 0;
		StringBuffer sb= new StringBuffer();
		for (int ii = 0; ii < src.length() - 1; ii++) {
			str = "#" + src.substring(ii, ii + 2);
			rb = (byte) Integer.decode(str).intValue();
			sb.append((char)rb);
			ii++;
		}
		return sb.toString();
	}
	/**
	 * BASE64转码
	 * @param plain
	 * @return
	 */
	public static String base64encode(String plain){
		if(plain == null || plain.equals("")){
			return plain;
		}
		BASE64Encoder b = new BASE64Encoder();
		String crypto = b.encode(plain.getBytes());
		return crypto;
	}
	
    public static void main(String[] args) throws Exception {//提供了Java命令使用该工具的功能
        if (args.length == 2 && args[0].equals("key")) {// 生成密钥文件
            Key key = EncryptUtils.createKey();
            String path = System.getProperty("user.dir") + "/" + args[1];
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(key);
            oos.close();
            System.out.println("gen encrypt key: " + path);
        } else if (args.length == 3 && args[0].equals("encrypt")) {//对文件进行加密
            File file = new File(System.getProperty("user.dir") + "/" + args[1]);
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] tmpbuf = new byte[1024];
            int count = 0;
            while ((count = in.read(tmpbuf)) != -1) {
                bout.write(tmpbuf, 0, count);
                tmpbuf = new byte[1024];
            }
            in.close();
            byte[] orgData = bout.toByteArray();
            Key key = getKey(new FileInputStream(args[2]));
            byte[] raw = EncryptUtils.doEncrypt(key, orgData);
            file = new File(file.getParent() + "\\en_" + file.getName());
            FileOutputStream out = new FileOutputStream(file);
            out.write(raw);
            out.close();
            System.out.println("成功加密，加密文件位于:"+file.getAbsolutePath());
        } else if (args.length == 3 && args[0].equals("decrypt")) {//对文件进行解密
            File file = new File(System.getProperty("user.dir") + "/" + args[1]);
            FileInputStream fis = new FileInputStream(file);
            Key key = getKey(new FileInputStream(args[2]));
            InputStream raw = EncryptUtils.doDecrypt(key, fis);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] tmpbuf = new byte[1024];
            int count = 0;
            while ((count = raw.read(tmpbuf)) != -1) {
                bout.write(tmpbuf, 0, count);
                tmpbuf = new byte[1024];
            }
            raw.close();
            byte[] orgData = bout.toByteArray();
            file = new File(file.getParent() + "\\rs_" + file.getName());
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(orgData);
            System.out.println("成功解密，解密文件位于:"+file.getAbsolutePath());
        }
    }
    
}


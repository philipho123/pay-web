package com.woniu.pay.util;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;

import org.bouncycastle.cms.CMSEnvelopedData;
import org.bouncycastle.cms.CMSEnvelopedDataGenerator;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.util.encoders.Base64;

/**
 * 实例化SignatureData对象时，
 * 需要通过构造方法 SignatureData( String pubkeyPath, String paymentPubKeyPath, String pkcs12FilePath, String keyPass )
 * 来实例化，需要相关证书数据来加密相应数据
 * @author Jahn
 * @since 2012-6-11
 * @version 1.0
 */
public class SignatureData {

	//private String 	prvkeyPath; //我方私钥文件路径
	private String 	pubkeyPath; //我方公共证书文件路径
	private String 	paymentPubKeyPath;//支付平台公共证书文件
	private String  pkcs12FilePath;//pkcs12文件路径
	private String 	pkcs12KeyPass;//pkcs12文件加密key
	
	public SignatureData(){
	}
	/**
	 * 数字加密构造函数
	 * @param pubkeyPath 我方公共证书文件路径
	 * @param paymentPubKeyPath 支付平台公共证书文件
	 * @param pkcs12FilePath pkcs12文件路径
	 * @param pkcs12KeyPass pkcs12文件加密key
	 * */
	public SignatureData( String pubkeyPath, String paymentPubKeyPath, String pkcs12FilePath, String keyPass ) {
		//this.prvkeyPath = prvkeyPath;
		this.pubkeyPath = pubkeyPath;
		this.paymentPubKeyPath = paymentPubKeyPath;
		this.pkcs12FilePath = pkcs12FilePath;
		this.pkcs12KeyPass = keyPass;
	}	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String encrypt(String _data) 
			throws IOException, CertificateException, KeyStoreException, UnrecoverableKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CertStoreException, CMSException {
		_data = _data.replace(',', '\n');
		CertificateFactory cf = CertificateFactory.getInstance("X509", "BC");
		// Read the Private Key
		KeyStore ks = KeyStore.getInstance("PKCS12", "BC");
		ks.load( new FileInputStream(pkcs12FilePath), pkcs12KeyPass.toCharArray() );
		//ks.load( new FileInputStream(keyPath), keyPass.toCharArray() );
		//ks.load(this.getClass().getClassLoader().getResourceAsStream(keyPath), keyPass.toCharArray() );
		String keyAlias = null;
		Enumeration aliases = ks.aliases();
		while (aliases.hasMoreElements()) {
			keyAlias = (String) aliases.nextElement();
		}

		PrivateKey privateKey = (PrivateKey) ks.getKey( keyAlias, pkcs12KeyPass.toCharArray() );

		// Read the Certificate
		X509Certificate certificate = (X509Certificate) cf.generateCertificate( new FileInputStream(pubkeyPath) );

		// Read the PayPal Cert
		X509Certificate payPalCert = (X509Certificate) cf.generateCertificate( new FileInputStream(paymentPubKeyPath) );

		// Create the Data
		byte[] data = _data.getBytes();
		
		// Sign the Data with my signing only key pair
		CMSSignedDataGenerator signedGenerator = new CMSSignedDataGenerator();

		signedGenerator.addSigner( privateKey, certificate, CMSSignedDataGenerator.DIGEST_SHA1 );

		ArrayList certList = new ArrayList();
		certList.add(certificate);
		CertStore certStore = CertStore.getInstance( "Collection", new CollectionCertStoreParameters(certList) );
		signedGenerator.addCertificatesAndCRLs(certStore);

		CMSProcessableByteArray cmsByteArray = new CMSProcessableByteArray(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		cmsByteArray.write(baos);
		//System.out.println( "CMSProcessableByteArray contains [" + baos.toString() + "]" );

		CMSSignedData signedData = signedGenerator.generate(cmsByteArray, true, "BC");
		
		byte[] signed = signedData.getEncoded();

		CMSEnvelopedDataGenerator envGenerator = new CMSEnvelopedDataGenerator();
		envGenerator.addKeyTransRecipient(payPalCert);
		
		CMSEnvelopedData envData = envGenerator.generate( new CMSProcessableByteArray(signed),
				 CMSEnvelopedDataGenerator.DES_EDE3_CBC, "BC" );

		byte[] pkcs7Bytes = envData.getEncoded();

		
		return new String( DERtoPEM(pkcs7Bytes, "PKCS7") );

	}

	public static byte[] DERtoPEM(byte[] bytes, String headfoot) 
	{
		ByteArrayOutputStream pemStream = new ByteArrayOutputStream();
		PrintWriter writer = new PrintWriter(pemStream);
		
		byte[] stringBytes = Base64.encode(bytes);
		
		//System.out.println("Converting " + stringBytes.length + " bytes");
		
		String encoded = new String(stringBytes);

		if (headfoot != null) {
			writer.print("-----BEGIN " + headfoot + "-----\n");
		}

		// write 64 chars per line till done
		int i = 0;
		while ((i + 1) * 64 < encoded.length()) {
			writer.print(encoded.substring(i * 64, (i + 1) * 64));
			writer.print("\n");
			i++;
		}
		if (encoded.length() % 64 != 0) {
			writer.print(encoded.substring(i * 64)); // write remainder
			writer.print("\n");
		}
		if (headfoot != null) {
			writer.print("-----END " + headfoot + "-----\n");
		}
		writer.flush();
		return pemStream.toByteArray();
	}
}

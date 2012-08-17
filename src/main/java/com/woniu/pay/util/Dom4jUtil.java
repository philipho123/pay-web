package com.woniu.pay.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.dom.DOMCDATA;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.xpath.DefaultXPath;

/**
 * XML操作工具类 辅助进行一些XML文档操作 使用DOM4J类库
 * 
 * @author weijun
 * 
 */
public class Dom4jUtil {

	/**
	 * 系列转换之 文件至XML文档
	 * 
	 * @param filePath
	 * @return
	 */
	public static Document file2Document(String filePath) {
		Document doc = null;
		FileInputStream fis = null;
		try {
			SAXReader saxReader = new SAXReader();
			saxReader.setEncoding("UTF-8");
			File f = new File(filePath);
			if (f.exists()) {
				fis = new FileInputStream(f);
				doc = saxReader.read(fis);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return doc;
	}

	/**
	 * 系列转换之 文件至XML文档
	 * 
	 * @param file
	 * @return
	 */
	public static Document file2Document(File file) {
		Document doc = null;
		FileInputStream fis = null;
		try {
			SAXReader saxReader = new SAXReader();
			saxReader.setEncoding("UTF-8");

			if (file.exists()) {
				fis = new FileInputStream(file);
				doc = saxReader.read(fis);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return doc;
	}

	/**
	 * 系列转换之 XML文档至文件
	 * 
	 * @param doc
	 * @param filePath
	 * @return
	 */
	public static boolean document2File(Document doc, String filePath) {
		boolean returnCode = true;
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		format.setOmitEncoding(false);
		XMLWriter writer = null;
		try {
			File f = new File(filePath);
			if (!f.exists())
				f.createNewFile();
			writer = new XMLWriter(new FileOutputStream(f), format);
			writer.write(doc);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				returnCode = false;
			}
		}
		return returnCode;
	}

	/**
	 * 系列转换之 输入流至XML文档
	 * 
	 * @param inputStream
	 * @return
	 */
	public static Document inputStream2Document(InputStream inputStream) {
		Document doc = null;
		if (inputStream != null) {
			try {
				SAXReader saxReader = new SAXReader();
				saxReader.setEncoding("UTF-8");
				doc = saxReader.read(inputStream);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return doc;
	}

	/**
	 * 系列转换之 字符串至XML文档
	 * 
	 * @param sXML
	 * @return
	 * @throws Exception
	 */
	public static Document string2Document(String sXML) {
		Document doc = null;
		if (sXML != null) {
			try {
				doc = DocumentHelper.parseText(sXML);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		return doc;
	}

	public static void output(Document doc, OutputStream output) {
		if (doc != null) {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			XMLWriter writer = null;
			try {
				writer = new XMLWriter(output, format);
				writer.write(doc);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static void output(String filePath, OutputStream output) {
		Document doc = file2Document(filePath);
		output(doc, output);
	}

	public static void output(Element ele, OutputStream output) {

		if (ele != null) {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			XMLWriter writer = null;
			try {
				writer = new XMLWriter(output, format);
				writer.write(ele);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	/**
	 * 系列转换之 字符串至输出流 add by weijun
	 * 
	 * @param xml
	 * @param out
	 */
	public static void string2OutputStream(String xml, OutputStream out) {
		try {
			output(string2Document(xml), out);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 系列转换之 XML文档至输出流
	 * 
	 * @param out
	 */
	public static void document2OutputStream(Document doc, OutputStream out) {
		output(doc, out);
	}

	/**
	 * 系统列转换之 XML文档至字符串
	 * 
	 * @param doc
	 * @return
	 */
	public static String document2String(Document doc) {
		if (doc != null)
			return doc.asXML();
		else
			return null;
	}

	/**
	 * 系列转换之 元素至字符串
	 * 
	 * @param doc
	 * @return
	 */
	public static String element2String(Element ele) {
		if (ele != null)
			return ele.asXML();
		else
			return null;
	}

	/**
	 * 设置某结点的属性值 如果存在该属性则更新其 如果不存在则向该结点添加新的属性值
	 * 
	 * @param ele
	 * @param key
	 * @param value
	 */
	public static void setAttribute(Element ele, String key, String value) {
		if (ele == null || key == null || value == null)
			return;
		if (ele.attribute(key) == null)
			ele.addAttribute(key, value);
		else
			ele.attribute(key).setText(value);
	}

	/**
	 * 将某结点设置为CDATA字段 不存在则创建，否则修改
	 * 
	 * @param ele
	 * @param cdata
	 */
	public static void setCDATA(Element ele, String cdata) {
		if (ele == null)
			return;
		ele.clearContent();
		ele.add(new DOMCDATA(cdata));
	}

	/**
	 * 空文档 根据参数(用户登陆否)输出
	 * 
	 * @param output
	 * @param isLogin
	 */
	public static void getEmptyDocXml(OutputStream output, Boolean isLogin) {
		if (output != null) {
			Document doc = DocumentHelper.createDocument();
			Element rootEle = doc.addElement("root");
			if (Boolean.TRUE.equals(isLogin))
				rootEle.addAttribute("isLogin", "1");
			else
				rootEle.addAttribute("isLogin", "0");
			document2OutputStream(doc, output);
		}
	}

	/**
	 * 取一个空的mainData结点
	 * 
	 * @param output
	 * @param errorMessage
	 * @param flag
	 */
	public static void getEmptyMainData(OutputStream output, String errorMessage, boolean flag) {
		Document doc = DocumentHelper.createDocument();
		Element rootEle = doc.addElement("main_root");
		rootEle.addAttribute("flag", flag + "");
		if (errorMessage != null && errorMessage.length() > 0) {
			Element errorMsg = rootEle.addElement("errorMessage");
			errorMsg.setText(errorMessage);
			document2OutputStream(doc, output);
		}
	}

	/**
	 * 输出XML格式状态信息至流
	 * 
	 * @param output
	 * @param flag
	 * @param message
	 */
	public static void getStateXML(OutputStream output, boolean flag, String message) {
		Document doc = DocumentHelper.createDocument();
		Element rootEle = doc.addElement("root");
		if (flag == false && message != null && message.length() > 0) {
			Element errorMsg = rootEle.addElement("errorMessage");
			errorMsg.addCDATA(message);
		}
		if (flag)
			rootEle.addAttribute("flag", "1");
		else
			rootEle.addAttribute("flag", "0");
		document2OutputStream(doc, output);
	}

	/**
	 * 输出XML格式的infoMessage至流
	 * 
	 * @param output
	 * @param infoMessage
	 */
	public static void getInfoDocXml(OutputStream output, String infoMessage, boolean flag) {
		Document doc = DocumentHelper.createDocument();
		Element rootEle = doc.addElement("root");
		if (flag == true)
			rootEle.addAttribute("flag", "1");
		else
			rootEle.addAttribute("flag", "0");
		if (infoMessage != null && infoMessage.length() > 0) {
			Element errorMsg = rootEle.addElement("InfoMessage");
			errorMsg.setText(infoMessage);
			document2OutputStream(doc, output);
		}
	}

	/**
	 * 返回一个节点对象集合
	 * 
	 * @param element
	 * @param childPath
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static List getChildren(Object ele, String childPath) {
		if (ele == null || childPath == null)
			return null;
		XPath path = new DefaultXPath(childPath);
		return path.selectNodes(ele);
	}

	/**
	 * 返回一个节点对象
	 * 
	 * @param element
	 * @param childPath
	 * @return
	 * @throws Exception
	 */
	public static Object getChild(Object element, String childPath) {
		if (element == null || childPath == null)
			return null;
		XPath path = new DefaultXPath(childPath);
		return path.selectSingleNode(element);
	}

	@SuppressWarnings({ "deprecation" })
	public static Object getFunctionChild(Object element, String childPath) {
		if (element == null || childPath == null)
			return null;
		XPath path = new DefaultXPath(childPath);

		return path.selectObject(element);
	}

	/**
	 * 输出XML格式的出错文档，根据错误码
	 */
	public static void getErrorDocXml(OutputStream output, int errorCode) {
		getErrorDocXml(output, errorCode);

	}

	/**
	 * 输出XML格式的出错文档，根据错误对象
	 */
	/*
	 * public static void getErrorDocXml(OutputStream output, ErrorVO errorVo) {
	 * getErrorDocXml(output, errorVo.getCode()); }
	 */

	/**
	 * 输出XML格式出错信息至流
	 */
	public static void getErrorDocXml(OutputStream output, String errorMessage) {
		Document doc = DocumentHelper.createDocument();
		Element rootEle = doc.addElement("root");
		rootEle.addAttribute("flag", "0");

		if (errorMessage != null && errorMessage.length() > 0) {
			Element errorMsg = rootEle.addElement("errorMessage");
			errorMsg.setText(errorMessage);
			document2OutputStream(doc, output);
		}
	}

	/**
	 * 输出XML格式的infoMessage至流
	 * 
	 * @param output
	 * @param infoMessage
	 */
	public static void getInfoDocXml(OutputStream output, String infoMessage) {
		Document doc = DocumentHelper.createDocument();
		Element rootEle = doc.addElement("root");

		if (infoMessage != null && infoMessage.length() > 0) {
			Element errorMsg = rootEle.addElement("InfoMessage");
			errorMsg.setText(infoMessage);
			document2OutputStream(doc, output);
		}
	}

	/**
	 * 用XSLT模板将XML文档转换成HTML文档
	 * 
	 * @param document
	 *            XML源文档
	 * @param xsltpath
	 *            XSLT模板文件路径
	 * @return Document HTML文档
	 * @author shezengyong
	 */
	public static Document transform2Html(Document document, String xsltpath) {
		TransformerFactory factory = TransformerFactory.newInstance();
		Document transformedDoc = null;
		try {
			Transformer transformer = factory.newTransformer(new StreamSource(xsltpath));
			DocumentSource source = new DocumentSource(document);
			DocumentResult result = new DocumentResult();
			transformer.transform(source, result);
			transformedDoc = result.getDocument();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return transformedDoc;
	}

}
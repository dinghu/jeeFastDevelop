package com.xjj.framework.utils;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.File;
import java.nio.charset.Charset;

/**
 * 字符编码工具类
 * 
 * @author xjj
 */
public class EncodingUtils {

	/**
	 * 获得文件的编码格式
	 * @param fileName 文件名
	 * @return 文件编码
	 */
	public static String getFileCharset(String fileName) {
		return getFileCharset(new File(fileName));
	}
	
	/**
	 * 获得文件的编码格式
	 * @param file 文件
	 * @return 文件编码
	 */
	public static String getFileCharset(File file) {
		if(file == null || !file.exists()){
			return "";
		}
		// detector是探测器，它把探测任务交给具体的探测实现类的实例完成。
		// cpDetector内置了一些常用的探测实现类，这些探测实现类的实例可以通过add方法 加进来，
		// 如ParsingDetector、 JChardetFacade、ASCIIDetector、UnicodeDetector。
		// detector按照“谁最先返回非空的探测结果，就以该结果为准”的原则返回探测到的字符集编码。
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();

		// ParsingDetector可用于检查HTML、XML等文件或字符流的编码,构造方法中的参数用于
		// 指示是否显示探测过程的详细信息，为false不显示。
		// detector.add(new
		// ParsingDetector(false));//如果不希望判断xml的encoding，而是要判断该xml文件的编码，则可以注释掉

		// JChardetFacade封装了由Mozilla组织提供的JChardet，它可以完成大多数文件的编码
		// 测定。所以，一般有了这个探测器就可满足大多数项目的要求，如果你还不放心，可以
		// 再多加几个探测器，比如下面的ASCIIDetector、UnicodeDetector等。
		//detector.add(JChardetFacade.getInstance());
		// ASCIIDetector用于ASCII编码测定
		detector.add(ASCIIDetector.getInstance());
		// UnicodeDetector用于Unicode家族编码的测定
		detector.add(UnicodeDetector.getInstance());

		String fileCharacterEnding = "UTF-8";
		Charset charset = null;
		try {
			charset = detector.detectCodepage(file.toURI().toURL());
			if (charset != null) {
				fileCharacterEnding = charset.name();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return fileCharacterEnding;
	}
	
	/**
	 * 转化字符串的编码
	 * @param string 待转换的字符串
	 * @param srcEncode 原编码
	 * @param targetEncode 目标编码
	 * @return
	 */
	public static String transEncoding(String string,String srcEncode){
		return transEncoding(string,srcEncode,"UTF-8");
	}
	/**
	 * 转化字符串的编码
	 * @param string 待转换的字符串
	 * @param srcEncode 原编码
	 * @param targetEncode 目标编码
	 * @return
	 */
	public static String transEncoding(String string,String srcEncode,String targetEncode){
		if(string == null){
			return null;
		}
		if(srcEncode == null || targetEncode==null){
			return string;
		}
		String str = "";
		try {
			str = new String(string.getBytes(srcEncode), targetEncode);
		} catch (Exception e) {
			return string;
		}
		return str;
	}
}

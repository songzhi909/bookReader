package com.songzhi.utils;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件名称为：URLAvailability.java 文件功能简述： 描述一个URL地址是否有效
 * 
 * @author Jason
 * @time 2010-9-14
 * 
 */
public class URLAvailability {
	private static Logger log = LoggerFactory.getLogger(URLAvailability.class);
	
	private static URL url;
	private static HttpURLConnection con;
	private static int state = -1;

	/**
	 * url 是否可用
	 * 
	 * @param urlStr
	 *          指定URL网络地址
	 * @return URL
	 */
	public static boolean isAvailability(String urlStr) {
		boolean flag = false;
		int counts = 0;
		if (urlStr == null || urlStr.length() <= 0) {
			return flag;
		}
		try {
			url = new URL(urlStr);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		while (counts < 5) {
			try {
				con = (HttpURLConnection) url.openConnection();
				state = con.getResponseCode();
				if (state == 200) {
					log.debug("URL可用！");
				}
				break;
			} catch (Exception ex) {
				counts++;
				log.debug("URL不可用，连接第{}次", counts);
				urlStr = null;
				continue;
			}
		}
		return flag;
	}
}

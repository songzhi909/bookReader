package com.songzhi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 动态ip生成工具
 * 
 * @author songzhi
 *
 */
public class DynamicIp {
	final static Logger log = LoggerFactory.getLogger(DynamicIp.class);
	

	public static Object[] getIps() {
		log.info("获取代理IP列表...");
		List<String> ips = new ArrayList<String>();

		try {
			String url = "http://www.89ip.cn/api/?&tqsl=100&sxa=&sxb=&tta=&ports=&ktip=&cf=1";

			 Document doc = Jsoup.connect(url).get();
			 String htmlContent = doc.html();

//			String htmlContent = "113.123.77.89:808<BR>178.245.233.103:8080<BR>113.231.60.192:38459<BR>116.19.123.166:808<BR>222.85.50.224:808<BR>182.39.145.146:12689<BR>141.196.141.238:8080<BR>183.153.62.123:808<BR>122.245.69.225:808<BR>72.169.149.117:87<BR>179.127.137.143:8080<BR>103.14.76.218:8080<BR>123.201.233.39:8080<BR>110.73.52.211:8123<BR>175.155.228.141:808<BR>118.120.196.243:9797<BR>60.176.39.98:8123<BR>36.249.25.192:808<BR>180.183.90.31:8080<BR>123.131.212.51:30282<BR>114.238.114.67:808<BR>27.8.158.171:808<BR>1.24.133.249:8998<BR>90.78.70.115:3128<BR>175.155.153.127:808<BR>176.237.6.114:8080<BR>110.81.193.254:808<BR>115.220.145.111:808<BR>124.112.116.34:10421<BR>175.155.240.16:808<BR>36.249.27.237:808<BR>182.244.181.89:21469<BR>113.206.168.172:59263<BR>123.169.85.31:808<BR>180.110.16.24:808<BR>175.155.24.43:808<BR>125.122.203.16:808<BR>161.53.184.106:21320<BR>114.99.7.18:808<BR>36.249.29.105:808<BR>175.155.229.150:808<BR>176.237.74.84:8080<BR>112.85.208.219:808<BR>121.226.169.156:808<BR>36.35.196.186:26018<BR>36.80.113.158:8080<BR>125.89.121.195:808<BR>115.220.149.64:808<BR>119.5.217.34:808<BR>110.83.46.144:808<BR>101.92.66.62:8123<BR>111.72.154.92:808<BR>114.239.144.244:808<BR>113.69.38.249:808<BR>115.151.60.184:808<BR>60.178.84.210:808<BR>141.196.137.243:8080<BR>117.31.179.215:8998<BR>175.155.247.26:808<BR>113.58.235.159:808<BR>39.87.99.189:8998<BR>59.62.171.23:46750<BR>112.80.222.239:50389<BR>154.73.45.25:8080<BR>123.169.86.66:808<BR>183.153.39.42:808<BR>222.94.144.113:808<BR>113.69.38.202:808<BR>110.240.207.146:51575<BR>183.152.8.17:8998<BR>113.123.78.37:808<BR>115.230.10.218:808<BR>183.70.119.121:24054<BR>92.241.104.226:8080<BR>36.249.30.17:808<BR>114.239.150.19:808<BR>114.232.155.91:808<BR>113.93.186.242:808<BR>121.232.148.61:9000<BR>159.192.254.212:8888<BR>101.25.58.92:35054<BR>113.58.233.12:808<BR>122.4.190.89:808<BR>115.212.166.33:9000<BR>36.249.24.144:808<BR>118.161.144.129:8888<BR>114.106.253.147:39474<BR>123.161.238.85:48566<BR>185.139.236.22:8080<BR>115.213.167.11:808<BR>88.160.19.81:3128<BR>118.174.193.52:8080<BR>178.245.184.207:8080<BR>159.203.36.47:8080<BR>218.68.248.139:9797<BR>218.0.182.121:8998<BR>183.47.65.241:8998<BR>175.155.141.240:808<BR>176.237.142.32:8080<BR>36.73.15.197:8080<BR>";

			Pattern pattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}:\\d*");
			Matcher matcher = pattern.matcher(htmlContent);

			while (matcher.find()) {
				String ip = matcher.group();
				ips.add(ip);
				log.debug(ip);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ips.toArray();
	}
}

package com.songzhi.crawler;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.songzhi.utils.DynamicIp;
import com.songzhi.utils.FileUtils;


/**
 * 漫画爬取器
 * @author songzhi
 *
 */
public class ComicCrawler {
	final static Logger log = LoggerFactory.getLogger(ComicCrawler.class);

	/** 待搜索资源 */
	Queue<String> searchUrls = new ConcurrentLinkedQueue<String>();
	/** 已搜索资源 */
	Queue<String> hitUrls = new ConcurrentLinkedQueue<String>();
	
	/** 最大线程数 */
	private int maxThreadNum = 10;
	/** 连接超时时间 */
	private int timeout = 10000;
	/** 代理IP*/
	private Object[] proxyIps;
	private int proxyIpIndex;
	
	public ComicCrawler() {
		// TODO Auto-generated constructor stub
	}
	
	public ComicCrawler(String url) {
		searchUrls.add(url);
		proxyIps = DynamicIp.getIps();
		proxyIpIndex = 0;
	}
	
	/** 获取代理地址 */
	public String getProxyIp() {
		int index = proxyIpIndex % proxyIps.length;
		proxyIpIndex++;
		return String.valueOf(proxyIps[index]);
	}
	
	/**
	 * 爬取网页图片
	 * @param url 爬取目标url
	 * @return
	 */
	public Object crawler() {
//		输入漫画url
//		解析漫画url
//		下载漫画图片到本地文件/上传到云盘
//		将相关信息存入数据库

		ExecutorService threadPool  = Executors.newFixedThreadPool(maxThreadNum); //开启线程池
		
		while(!searchUrls.isEmpty()) {
			final String url = searchUrls.poll();
			
			//如果已经搜索,则跳过该url,否则就将该url加入已搜索url
			if(!hitUrls.contains(url)) {
				hitUrls.add(url);
				
				if(url.indexOf("photos-index") != -1) {	//漫画章节目录
					log.info("searching the url :" + url);
					searchUrl(url);
				}else if(url.indexOf("photos-view-id") != -1) { //漫画章节详细
					log.info("downloading the url :" + url);
					
					threadPool.execute(new Runnable() {
						@Override
						public void run() {
							download(url);
						}
					});
				}
			}
		}
		
		threadPool.shutdown();
		
		
		//生成浏览页面
		FileUtils.generateHtml(filePath);
		
		return true;
	}
	
	/**
	 * 下载图片
	 * @param url
	 */
	private void download(String url) {
		try {
			Document doc = connect(url);
			String pic = doc.select("#picarea").first().absUrl("src");
			
			String filename = pic.substring(pic.lastIndexOf("/") + 1);	//漫画文件名称
			String temp = pic.substring(0, pic.lastIndexOf("/"));
			String savePath = temp.substring(temp.lastIndexOf("/") + 1);	//漫画对应路径
			
			filePath = savePath;
			
			if(!new File(savePath + File.separator + filename).exists()) {	//不重复下载
				log.info("downing the pic: [comicpath=" + savePath + "]&[filename=" + filename + "]");
				FileUtils.download(pic, filename, savePath); 
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			
			searchUrls.add(url);
			hitUrls.remove(url);
		}
		
	}
	
	private String filePath;

	private Document connect(String url) throws IOException {
		
		//设置代理
		String proxyIp = getProxyIp();
		String proxyHost = proxyIp.split(":")[0];
		String proxyPort = proxyIp.split(":")[1];
		System.setProperty("http.proxyHost", proxyHost);
		System.setProperty("http.proxyPort", proxyPort);
		System.setProperty("https.proxyHost", proxyHost);
		System.setProperty("https.proxyPort", proxyPort);
		
		Document doc = Jsoup.connect(url).timeout(timeout).get();
		return doc;
	}

	/**
	 * 搜索资源地址
	 * @param url
	 */
	private void searchUrl(String url) {
		try {
			Document doc = connect(url);
			Elements picEles = doc.select("div.pic_box>a, div.f_left a");
			Iterator<Element> it = picEles.iterator();
			while(it.hasNext()) {
				Element element = it.next();
				String picUrl = element.attr("abs:href");
				if(!searchUrls.contains(picUrl) && !hitUrls.contains(picUrl))
					searchUrls.add(picUrl);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			
			searchUrls.add(url);
			hitUrls.remove(url);
		}
		
	}
 
}

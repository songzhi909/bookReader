package com.songzhi.crawler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.songzhi.model.Comic;
import com.songzhi.model.ComicPic;
import com.songzhi.service.ComicService;
import com.songzhi.utils.DynamicIp;
import com.songzhi.utils.FileUtils;


/**
 * 漫画爬取器
 * @author songzhi
 *
 */
@Component
public class ComicCrawler2 {
	final static Logger log = LoggerFactory.getLogger(ComicCrawler2.class);

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
	
	@Autowired
	private ComicService comicService;
	
	public ComicCrawler2() {
		proxyIps = DynamicIp.getIps();
		proxyIpIndex = 0;
	}
	
	public ComicCrawler2(String url) {
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

	List<Comic> comics = new ArrayList<Comic>();

	/**
	 * 爬取网页漫画目录
	 * @param url 爬取目标url
	 * @return
	 */
	public Object crawlerIndex() {

		// 扫描漫画
		while (!searchUrls.isEmpty()) {
			String url = searchUrls.poll();
			log.info("searching url:" + url);
			searchUrl(url);
		}
		
		//生成漫画页面
		
		//生成漫画索引

		return true;
	}
	
	/**
	 * 爬取某个漫画
	 * @param comic
	 * @return
	 */
	public boolean crawlerComic(Comic comic) {
		try {
			log.info("starting crawl the comic: " + comic.getName() + "...");
			Document doc = connect(comic.getUrl());
			//先查询初始图片编号，再查询最后图片编号
			String firstTargetUrl = doc.select(".pic_box>a").first().absUrl("href");
			String firstPicUrl = getPicUrl(firstTargetUrl); 
			
			String lastPageUrl = doc.select(".paginator>a").last().absUrl("href");
			Document lastDoc = connect(lastPageUrl);
			String lastTargetUrl = lastDoc.select(".pic_box>a").last().absUrl("href");
			String lastPicUrl = getPicUrl(lastTargetUrl);
			
			String prefix = firstPicUrl.substring(0, firstPicUrl.lastIndexOf("/")+1);
			
			int from = Integer.parseInt(firstPicUrl.substring(firstPicUrl.lastIndexOf("/") + 1).split("\\.")[0]);
			int end = Integer.parseInt(lastPicUrl.substring(lastPicUrl.lastIndexOf("/") + 1).split("\\.")[0]);
			
			int comicId = comic.getId();
			for(int i=from;i <= end; i++) {
				String picUrl = prefix +  String.format("%03d", i);
				ComicPic comicPic = new ComicPic(comicId, picUrl);
				comicService.addPic(comicPic);
			}
			
			log.debug("end crawl the comic: " + comic.getName() + "!");
			return true;
		} catch (IOException e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	
	/**获取图片url地址*/
	public String getPicUrl(String targetUrl) {
		String url = "";
		try {
			Document doc = connect(targetUrl);
			url = doc.select("#picarea").first().absUrl("src");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return url;
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
	private void searchUrl(String url){
		try {
			
			hitUrls.add(url);
			
			
//			ComicService comicService = SpringContent.getBean(ComicService.class);
			
			//扫描分类路径中的漫画
			Document doc = connect(url);
			
			Elements picEles =  doc.select("div.pic_box");
			Iterator<Element> it = picEles.iterator();
			while(it.hasNext()) {
				Element element = it.next();
				String picUrl = element.select("a").first().absUrl("href");
				String name = element.select("a>img").first().attr("alt");
				String photoUrl = element.select("a>img").first().absUrl("src");
				
				URL targetURL = new URL(picUrl);
				String website = targetURL.getHost();
				String wid = picUrl.substring(picUrl.lastIndexOf("-") + 1).split("\\.")[0];	//漫画文件名称
				
				Comic comic = new Comic(website, Integer.parseInt(wid), name, photoUrl, picUrl);
				comics.add(comic);
				
				comicService.add(comic);
				log.debug(comic.toString());
			}

			//搜索分类路径
			Elements pageEles =  doc.select("div.paginator>a");
			Iterator<Element> pageIt = pageEles.iterator();
			while(pageIt.hasNext()) {
				Element element = pageIt.next();
				String targetUrl = element.absUrl("href");
				if(!hitUrls.contains(targetUrl) && !searchUrls.contains(targetUrl))
					searchUrls.add(targetUrl);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
			
			searchUrls.add(url);
			hitUrls.remove(url);
		}
	}
	
	/**
	 * 根据图片文件夹生成可阅读html页面
	 * @param pathname
	 */
	public static void generateHtml(String pathname) {
		File file = new File(pathname);
		String[] fileNames = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toUpperCase().matches(".*\\.(JPG)?(PNG)?");
			}
		});
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head><style type=text/css>");
		sb.append("<!--body{font-size: 16pt; line-height: 140%;Font-FAMILY:华文楷体;color: #000000}-->");

		sb.append("</style></head><body width='80%' >");

		for (String fileName : fileNames) {
			sb.append("<CENTER><IMG border=0 src=\"..\\" + pathname + "\\" + fileName + "\"></CENTER>");
			sb.append("\n");
		}
		sb.append("</body>");
		sb.append("</html>");
		try {
			File exportFile = new File(pathname + "\\index.html");
			if (!exportFile.exists())
				exportFile.createNewFile();
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(exportFile));
			out.write(sb.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

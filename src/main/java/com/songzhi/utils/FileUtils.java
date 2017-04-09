package com.songzhi.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * 文件工具
 * 
 * @author songzhi
 *
 */
public class FileUtils {

	/**
	 * 下载资源文件
	 * 
	 * @param urlString
	 *          资源路径
	 * @param filename
	 * @param savePath
	 * @throws Exception
	 */
	public static void download(String urlString, String filename, String savePath) throws Exception {
		// 构造URL
		URL url = new URL(urlString);
		// 打开连接
		URLConnection con = url.openConnection();
		// 设置请求超时为5s
		con.setConnectTimeout(5 * 1000);
		// 输入流
		InputStream is = con.getInputStream();

		// 1K的数据缓冲
		byte[] bs = new byte[1024];
		// 读取到的数据长度
		int len;
		// 输出的文件流
		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}
		OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
		// 开始读取
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// 完毕，关闭所有链接
		os.close();
		is.close();
	}

	/**
	 * 根据图片文件夹生成可阅读html页面
	 * @param path	图片文件夹路径
	 * @param filename html页面文件
	 */
	public void generateHtml(String path, String filename) {
		try {
			File[] files = new File(path).listFiles();
			StringBuffer data = new StringBuffer();
			data.append("<html><head><style type=text/css>");
			data.append("<!--body{font-size: 16pt; line-height: 140%;Font-FAMILY:华文楷体;color: #000000}-->");
			data.append("</style></head><body width='80%' >");
			boolean hasFile = false;
			for (File file : files) {
				if (file.isDirectory()) {
//					generateFile(file.getAbsolutePath(), filename);
				} else if (file.getName().toUpperCase().matches(".*\\.(JPG)?(PNG)?")) {
					if (!hasFile) hasFile = true;
					data.append("<CENTER><IMG border=0 src=\"../" + new File(path).getName() + "/" + file.getName() + "\"></CENTER>\n");
				}
			}

			File tempFile = new File(filename + "\\page");
			if (!tempFile.exists())
				tempFile.mkdir();
			if (hasFile) {
				String exportpath = filename + "\\page\\" + new File(path).getName() + ".html";
				exportFile(data.toString(), exportpath);
//				indexPage.append("<a href='" + new File(path).getName() + ".html'>").append(new File(path).getName()).append("</a>\n<br/>");
			}
		} catch (Exception e) {
			e.printStackTrace();
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

	/**
	 * 将数据写入文件
	 * @param data 数据
	 * @param filepath 文件名
	 */
	public void exportFile(String data, String filepath) {
		try {
			File exportFile = new File(filepath);
			if (!exportFile.exists())
				exportFile.createNewFile();
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(exportFile));
			out.write(data);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

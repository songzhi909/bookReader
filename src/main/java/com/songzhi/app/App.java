package com.songzhi.app;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.songzhi.conf.SpringContent;
import com.songzhi.model.Catalog;
import com.songzhi.model.Comic;
import com.songzhi.service.ComicService;
import com.songzhi.ui.MainController;
import com.songzhi.ui.download.ComicDownloadController;
import com.songzhi.ui.website.WebSiteComicController;
import com.songzhi.ui.website.WebSiteIndexController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 应用启动页面
 * 
 * @author songzhi
 *
 */
public class App extends Application {
	final static Logger log = LoggerFactory.getLogger(App.class);

	private Stage primaryStage;
	private VBox mainLayout;
	private ScrollPane contentPane;

	private ComicService service;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("小说漫画下载阅读器");

		// 注入服务
		service = SpringContent.getBean(ComicService.class);

		initRootLayout();

	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("../ui/Main.fxml"));

			mainLayout = (VBox) loader.load();

			MainController controller = loader.getController();
			controller.setApp(this);
			controller.loadTree();

			contentPane = controller.getContentPane();

			// Show the scene containing the root layout.
			Scene scene = new Scene(mainLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 显示网站目录
	 */
	public void showWebSitIndex(final Catalog catalog) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("../ui/website/WebSiteIndex.fxml"));

			Pane pane = loader.load();
			contentPane.setContent(pane);

			WebSiteIndexController controller = loader.getController();
			controller.setApp(this);

			controller.loadComics(catalog);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 显示网站漫画信息
	 * 
	 * @param comic
	 */
	public void showWebSitComic(Comic comic) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("../ui/website/WebSiteComic.fxml"));

			Pane pane = loader.load();
			contentPane.setContent(pane);

			WebSiteComicController controller = loader.getController();
			controller.setApp(this);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 显示当前下载列表信息
	 * 
	 * @param comic
	 */
	public void showDownloadingList(Comic comic) {

	}

	public void showComicDownload(Comic comic) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("../ui/download/ComicDownload.fxml"));

			Pane pane = loader.load();
			contentPane.setContent(pane);

			ComicDownloadController controller = loader.getController();
			controller.setApp(this);
			
			controller.setComic(comic);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public ComicService getService() {
		return service;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Catalog createCatalog() {
		// TODO Auto-generated method stub
		return null;
	}

}

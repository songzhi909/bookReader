package com.songzhi.app;

import java.io.IOException;

import com.songzhi.conf.SpringContent;
import com.songzhi.model.Catalog;
import com.songzhi.service.ComicService;
import com.songzhi.ui.MainController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 应用启动页面
 * 
 * @author songzhi
 *
 */
public class App extends Application {

	private Stage primaryStage;
	private VBox mainLayout;

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

			// Show the scene containing the root layout.
			Scene scene = new Scene(mainLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
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

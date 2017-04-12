package com.songzhi.ui.website;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import com.songzhi.model.Catalog;
import com.songzhi.model.Comic;
import com.songzhi.ui.BaseController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WebSiteIndexController extends BaseController {
	final static Logger log = LoggerFactory.getLogger(WebSiteIndexController.class);
	@FXML
	private Label webSitLabel;

	@FXML
	private GridPane comicGridPane;

	@FXML
	private Pagination pagination;

	@Override
	public void beforeInitialize() {
		// app.getService().findComicsByPage(page, size)
		super.beforeInitialize();
	}

	/** 分页加载漫画信息 */
	public void loadComics(Catalog catalog) {
		Date startDate = new Date();
		log.info("开始加载{}漫画目录。。。", catalog.getName());
		Page<Comic> comicPage = app.getService().findComicsByCatalogId(1, 25, catalog.getId());
		List<Comic> comics = comicPage.getContent();
		int row = comics.size() % 5 == 0 ? comics.size() / 5 : comics.size() / 5 + 1;
		int colnum = 5;
		for (int rowIndex = 0; rowIndex < row; rowIndex++) {
			// 当数据不是5的整数，且是最后一行时
			if (rowIndex == row - 1 && comics.size() % 5 > 0) colnum = comics.size() % 5;
			for (int columnIndex = 0; columnIndex < colnum; columnIndex++) {
				log.info("columnIndex:{}, rowIndex:{}", columnIndex, rowIndex);
				
				Comic comic = comics.get(rowIndex * 5 + columnIndex);

				VBox vBox = new VBox();

				Image image = new Image(comic.getPhoto(), false);
				ImageView imageView = new ImageView(image);
				imageView.setFitWidth(100);
				imageView.setFitHeight(100);
				
				imageView.setOnMouseClicked(e -> app.showWebSitComic(comic)); //显示漫画详细

				CheckBox checkBox = new CheckBox(comic.getName());
				checkBox.setSelected(comic.getFlag() == 1);
				checkBox.setDisable(false);

				Image btnImg = new Image(getClass().getResourceAsStream("/images/download.png"));
				Button button = new Button("",new ImageView(btnImg));
				
				button.setOnMouseClicked(e-> app.showComicDownload(comic)); //显示下载信息

				vBox.getChildren().addAll(imageView, checkBox, button);
				comicGridPane.setPrefHeight(1000);
				comicGridPane.add(vBox, columnIndex, rowIndex);
			}
		}
		Date endDate = new Date();
		log.info("结束漫画目录{}加载, 花费{}秒", catalog.getName(), (endDate.getTime() - startDate.getTime()) / 1000);

	}

}

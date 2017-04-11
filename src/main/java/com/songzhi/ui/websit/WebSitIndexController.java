package com.songzhi.ui.websit;

import com.songzhi.ui.BaseController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.GridPane;

public class WebSitIndexController extends BaseController {
	@FXML
	private Label webSitLabel;
	
	@FXML
	private GridPane comicGridPane;
	
	@FXML
	private Pagination pagination;
	

	@Override
	public void beforeInitialize() {
//		app.getService().findComicsByPage(page, size)
		super.beforeInitialize();
	}

}

package com.songzhi.ui.download;

import java.util.List;

import com.songzhi.model.Comic;
import com.songzhi.model.ComicPic;
import com.songzhi.ui.BaseController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ComicDownloadController extends BaseController {

	@FXML
	private VBox downloadPane;
	
	private Comic comic;
	
	@Override
	public void afterInitialize() {
		super.afterInitialize();
		
		List<ComicPic> comicPics = app.getService().findComicPicUrls(comic);
		for(ComicPic comicPic : comicPics) {
			HBox box = new HBox();
			
			Label seqNo = new Label(comicPic.getId() + "");
			ProgressBar bar = new ProgressBar(0);
			bar.setPrefWidth(400);
			String url = comicPic.getFlag() == 0 ? "images/download.png" : "images/action_check.png";
			ImageView state = new ImageView(url);
			
			box.getChildren().addAll(seqNo, bar, state);
		}
	}


	public void setComic(Comic comic) {
		this.comic = comic;
	}
}

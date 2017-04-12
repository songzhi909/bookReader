package com.songzhi.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.songzhi.app.App;

import javafx.fxml.FXML;

/**
 * 控制器基类
 * @author songzhi
 *
 */
public class BaseController {
	private Logger log = LoggerFactory.getLogger(BaseController.class);
	
	public App app;
	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * @param app
	 */
	public void setApp(App app) {
		this.app = app;

		// Add observable list data to the table
		// personTable.setItems(mainApp.getPersonData());
		
		beforeInitialize();
	}

	/**
	 * 处理control初始化之前的工作
	 */
	public void beforeInitialize() {
		
	}
	
	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
	public BaseController() {

	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		log.info("initializes the {}....", getClass().getName());
		
		afterInitialize();
	}
	
	/**
	 * 初始化后续操作
	 */
	public void afterInitialize() {
		
	}
}

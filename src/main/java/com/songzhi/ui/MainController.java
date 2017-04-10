package com.songzhi.ui;

import java.util.List;

import com.songzhi.app.App;
import com.songzhi.model.Catalog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainController {

	@FXML
	private AnchorPane catalogTreePane;
	@FXML
	private TreeView<Catalog> catalogTree;

	private App app;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
	public MainController() {

	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

	}

	/**
	 * 加载树型
	 */
	public void loadTree() {

		TreeItem<Catalog> root = new TreeItem<Catalog>(new Catalog(0, "全部", 0));
		root.setExpanded(true);

		List<Catalog> level1 = app.getService().findTopCatalogs();
		for (Catalog catalog : level1) {
			TreeItem<Catalog> item = new TreeItem<Catalog>(catalog);

			List<Catalog> level2 = app.getService().findCatalogsByPid(catalog.getId());
			for (Catalog c : level2) {
				TreeItem<Catalog> childNode = new TreeItem<Catalog>(c);
				childNode.addEventHandler(MouseEvent.ANY, e -> {
					Node node = e.getPickResult().getIntersectedNode();
					if (node instanceof TreeCell) {
						TreeCell<Catalog> clickCell = (TreeCell<Catalog>) node;
						System.out.println(clickCell.getItem());
					}
				});
				item.getChildren().add(childNode);
			}
			root.getChildren().add(item);
		}

		catalogTree.setOnMouseClicked(e -> {
			TreeItem<Catalog> catalog = catalogTree.getSelectionModel().getSelectedItem();
			System.out.println(catalog);
			// Node node = e.getPickResult().getIntersectedNode();
		  // if(node instanceof TreeCell) {
		  // TreeCell<Catalog> clickCell = (TreeCell<Catalog>) node;
		  // System.out.println(clickCell.getItem());
		  // }
		});

		catalogTree.setCellFactory((TreeView<Catalog> treeView) -> new CatalogTreeCellImpl(treeView));

		catalogTree.setRoot(root);

	}

	private class CatalogTreeCellImpl extends TreeCell<Catalog> {
		private TextField textField;
		private ContextMenu addMenu = new ContextMenu();

		public CatalogTreeCellImpl(TreeView<Catalog> treeView) {
			MenuItem addMenuItem = new MenuItem("添加");
			addMenu.getItems().add(addMenuItem);
			addMenuItem.setOnAction((ActionEvent t) -> {
				Catalog catalog = app.createCatalog();
				if (catalog != null) {
					TreeItem<Catalog> newEmployee = new TreeItem<Catalog>(catalog);
					getTreeItem().getChildren().add(newEmployee);
				}
			});
		}

		@Override
		public void startEdit() {
			super.startEdit();

			if (textField == null) {
				createTextField();
			}
			setText(getItem().getName());
			setGraphic(textField);
			textField.selectAll();
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();

			setText(getItem().getName());
			setGraphic(getTreeItem().getGraphic());
		}

		@Override
		public void updateItem(Catalog item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				setText(null);
				setGraphic(null);
			} else {
				if (isEditing()) {
					if (textField != null) {
						textField.setText(getString());
					}
					setText(null);
					setGraphic(textField);
				} else {
					setText(getString());
					setGraphic(getTreeItem().getGraphic());
					if (getItem().getLevel() != null && getItem().getLevel() == 1) {
						setContextMenu(addMenu);
					}
				}
			}
		}

		private void createTextField() {
			textField = new TextField(getString());
			textField.setOnKeyReleased((KeyEvent t) -> {
				if (t.getCode() == KeyCode.ENTER) {

					// commitEdit(textField.getText());
				} else if (t.getCode() == KeyCode.ESCAPE) {
					cancelEdit();
				}
			});

		}

		private String getString() {
			return getItem() == null ? "" : getItem().getName();
		}
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param app
	 */
	public void setApp(App app) {
		this.app = app;

		// Add observable list data to the table
		// personTable.setItems(mainApp.getPersonData());
	}
}

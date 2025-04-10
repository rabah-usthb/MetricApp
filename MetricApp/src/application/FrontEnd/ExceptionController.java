package application.FrontEnd;

	import javafx.fxml.FXML;



	import javafx.fxml.FXMLLoader;
	import javafx.geometry.Insets;
	import javafx.geometry.Pos;
	import javafx.scene.Parent;
	import javafx.scene.Scene;
	import javafx.scene.control.Alert;
	import javafx.scene.control.ButtonType;
	import javafx.scene.control.ContentDisplay;
	import javafx.scene.control.Label;
	import javafx.scene.control.TreeCell;
	import javafx.scene.control.TreeItem;
	import javafx.scene.control.TreeView;
	import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
	import javafx.scene.shape.SVGPath;
	import javafx.stage.Stage;

	import java.io.File;
	import java.io.IOException;
	import java.util.ArrayList;

import application.BackEnd.ExceptionStatus;
import application.BackEnd.ImportStatus;
import application.BackEnd.Package;
import application.BackEnd.XMLResult;
import javafx.util.Callback;
public class ExceptionController {
        static ArrayList<ExceptionStatus>ListException = new ArrayList<>();
	    @FXML
	    private TreeView<TreeItemData> treeView;
	    @FXML
	    private Label ExceptionLabel;

	    @FXML
	    GridPane pane;
	    
	    public void initialize(String FilePath) {
	        File file = new File(FilePath);
	        ExceptionLabel.setText("Exceptions Of "+file.getName());
	        
	       try {
			ListException = ExceptionStatus.FetchThrowable(file);
		System.out.println(ListException);
	       } catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	        TreeItemData rootItemData = new TreeItemData("Exceptions","M 12 2 C 6.48 2 2 6.48 2 12 s 4.48 10 10 10 s 10 -4.48 10 -10 S 17.52 2 12 2 Z m 1 15 h -2 v -2 h 2 v 2 Z m 0 -4 h -2 V 7 h 2 v 6 Z");
	        TreeItem<TreeItemData> rootItem = new TreeItem<>(rootItemData);
	        treeView.setRoot(rootItem);
	        TreeItem<TreeItemData> DefaultException = new TreeItem<>(new TreeItemData("Default Exceptions","M 11.622 24.74 s -1.23 0.748 0.855 0.962 c 2.51 0.32 3.847 0.267 6.625 -0.267 a 10.02 10.02 0 0 0 1.763 0.855 c -6.25 2.672 -14.16 -0.16 -9.244 -1.55 Z m -0.8 -3.473 s -1.336 1.015 0.748 1.23 c 2.725 0.267 4.862 0.32 8.55 -0.427 a 3.26 3.26 0 0 0 1.282 0.801 c -7.534 2.244 -15.976 0.214 -10.58 -1.603 Z m 14.747 6.09 s 0.908 0.748 -1.015 1.336 c -3.58 1.07 -15.014 1.39 -18.22 0 c -1.122 -0.48 1.015 -1.175 1.7 -1.282 c 0.695 -0.16 1.07 -0.16 1.07 -0.16 c -1.23 -0.855 -8.175 1.763 -3.526 2.51 c 12.77 2.084 23.296 -0.908 19.983 -2.404 Z M 12.2 17.633 s -5.824 1.39 -2.084 1.87 c 1.603 0.214 4.755 0.16 7.694 -0.053 c 2.404 -0.214 4.81 -0.64 4.81 -0.64 s -0.855 0.374 -1.443 0.748 c -5.93 1.55 -17.312 0.855 -14.052 -0.748 c 2.778 -1.336 5.076 -1.175 5.076 -1.175 Z m 10.42 5.824 c 5.984 -3.1 3.206 -6.09 1.282 -5.717 c -0.48 0.107 -0.695 0.214 -0.695 0.214 s 0.16 -0.32 0.534 -0.427 c 3.794 -1.336 6.786 4.007 -1.23 6.09 c 0 0 0.053 -0.053 0.107 -0.16 Z m -9.83 8.442 c 5.77 0.374 14.587 -0.214 14.8 -2.94 c 0 0 -0.427 1.07 -4.755 1.87 c -4.916 0.908 -11.007 0.8 -14.587 0.214 c 0 0 0.748 0.64 4.542 0.855 Z M 18.996 0.001 s 3.313 3.366 -3.152 8.442 c -5.183 4.114 -1.175 6.465 0 9.137 c -3.046 -2.725 -5.236 -5.13 -3.74 -7.373 C 14.294 6.893 20.332 5.3 18.996 0.001 Z m -1.7 15.335 c 1.55 1.763 -0.427 3.366 -0.427 3.366 s 3.954 -2.03 2.137 -4.542 c -1.656 -2.404 -2.94 -3.58 4.007 -7.587 c 0 0 -10.953 2.725 -5.717 8.763 Z"));
	        TreeItem<TreeItemData> NotDefaultException = new TreeItem<>(new TreeItemData("Not Default Exception","M 19 3 h -4.18 C 14.4 1.84 13.3 1 12 1 S 9.6 1.84 9.18 3 H 5 C 4.86 3 4.73 3.01 4.6 3.04 C 4.21 3.12 3.86 3.32 3.59 3.59 c -0.18 0.18 -0.33 0.4 -0.43 0.64 C 3.06 4.46 3 4.72 3 5 v 14 c 0 0.27 0.06 0.54 0.16 0.78 c 0.1 0.24 0.25 0.45 0.43 0.64 c 0.27 0.27 0.62 0.47 1.01 0.55 C 4.73 20.99 4.86 21 5 21 h 14 c 1.1 0 2 -0.9 2 -2 V 5 C 21 3.9 20.1 3 19 3 Z M 11 14.17 l -1.41 1.42 L 6 12 l 3.59 -3.59 L 11 9.83 L 8.83 12 L 11 14.17 Z M 12 4.25 c -0.41 0 -0.75 -0.34 -0.75 -0.75 S 11.59 2.75 12 2.75 s 0.75 0.34 0.75 0.75 S 12.41 4.25 12 4.25 Z M 14.41 15.59 L 13 14.17 L 15.17 12 L 13 9.83 l 1.41 -1.42 L 18 12 L 14.41 15.59 Z"));
	        String CheckedSvgPath="M 9 16.17 L 4.83 12 l -1.42 1.41 L 9 19 L 21 7 l -1.41 -1.41 Z";
	        String UncheckedSvgPath="M 19 6.41 L 17.59 5 L 12 10.59 L 6.41 5 L 5 6.41 L 10.59 12 L 5 17.59 L 6.41 19 L 12 13.41 L 17.59 19 L 19 17.59 L 13.41 12 Z";
	        for (ExceptionStatus exception : ListException) {
	        	System.out.println("cddc");
	        	//TreeItem<TreeItemData> ImportItem = createTreeItem(Import);
	            //System.out.println(exception.ExceptionName);
	        	if(exception.DefaultStatus == 0) {
	            	if(exception.CheckedStatus == 0) {
	            		System.out.println("cddc");
	            		System.out.println(exception.ExceptionName);
	            		DefaultException.getChildren().add(new TreeItem<>(new TreeItemData(exception.ExceptionName,CheckedSvgPath)));
	            	}
	            	else {
	            		System.out.println(exception.ExceptionName);
	            		DefaultException.getChildren().add(new TreeItem<>(new TreeItemData(exception.ExceptionName,UncheckedSvgPath)));
	            	}
	            	}
	            else {
	            	if(exception.CheckedStatus == 0) {
	            		System.out.println(exception.ExceptionName);
		            	NotDefaultException.getChildren().add(new TreeItem<>(new TreeItemData(exception.ExceptionName,CheckedSvgPath)));
		            	}
		            	else {
		            		System.out.println(exception.ExceptionName);
		            		NotDefaultException.getChildren().add(new TreeItem<>(new TreeItemData(exception.ExceptionName,UncheckedSvgPath)));
		            	}
	            }
	        }
	        if(DefaultException.getChildren().isEmpty()) {
	        	DefaultException.getChildren().add(new TreeItem<>(new TreeItemData("None","M 12 2 C 6.5 2 2 6.5 2 12 s 4.5 10 10 10 s 10 -4.5 10 -10 S 17.5 2 12 2 Z M 4 12 c 0 -4.4 3.6 -8 8 -8 c 1.8 0 3.5 0.6 4.9 1.7 L 5.7 16.9 C 4.6 15.5 4 13.8 4 12 Z m 8 8 c -1.8 0 -3.5 -0.6 -4.9 -1.7 L 18.3 7.1 C 19.4 8.5 20 10.2 20 12 c 0 4.4 -3.6 8 -8 8 Z")));
	        }
	         if(NotDefaultException.getChildren().isEmpty()) {
	        	NotDefaultException.getChildren().add(new TreeItem<>(new TreeItemData("None","M 12 2 C 6.5 2 2 6.5 2 12 s 4.5 10 10 10 s 10 -4.5 10 -10 S 17.5 2 12 2 Z M 4 12 c 0 -4.4 3.6 -8 8 -8 c 1.8 0 3.5 0.6 4.9 1.7 L 5.7 16.9 C 4.6 15.5 4 13.8 4 12 Z m 8 8 c -1.8 0 -3.5 -0.6 -4.9 -1.7 L 18.3 7.1 C 19.4 8.5 20 10.2 20 12 c 0 4.4 -3.6 8 -8 8 Z")));
	        }
	        rootItem.getChildren().add(DefaultException);
	        rootItem.getChildren().add(NotDefaultException);
	        setTreeViewStyle();
	        
	     
	    }


	  
	    
	    
	    
	    private void setTreeViewStyle() {
	    	treeView.setCellFactory(new Callback<TreeView<TreeItemData>, TreeCell<TreeItemData>>() {
	    	    @Override
	    	    public TreeCell<TreeItemData> call(TreeView<TreeItemData> param) {
	    	        return new CustomTreeCell();
	    	    }
	    	});

	    }
	   
	    
	    public class CustomTreeCell extends TreeCell<TreeItemData> {
	        private SVGPath svgPath;
	        private Label label;
	        private HBox hbox;

	        public CustomTreeCell() {
	            this.svgPath = new SVGPath();
	            this.label = new Label();
	            this.hbox = new HBox(svgPath, label);
	            this.hbox.setAlignment(Pos.CENTER_LEFT);
	            setGraphic(hbox);
	            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	            setFocusTraversable(true); 
	        }

	        @Override
	        protected void updateItem(TreeItemData item, boolean empty) {
	            super.updateItem(item, empty);

	            if (empty || item == null) {
	                // Clear the content when the item is empty or null
	                setText(null);
	                setGraphic(null);
	                // Reset style classes when the item is empty
	                label.getStyleClass().clear();
	                svgPath.getStyleClass().clear();
	            } else {
	                TreeItem<TreeItemData> treeItem = getTreeItem();
	                if (treeItem != null) {
	                    if (treeItem.getParent() == null) {
	                        label.getStyleClass().setAll("root-node-label");
	                        svgPath.getStyleClass().setAll("root-node-svg");
	                    } else if (!treeItem.isLeaf()) {
	                        label.getStyleClass().setAll("parent-node-label");
	                        if(treeItem.getValue().label.equals("Default Exceptions")) {
	                        svgPath.getStyleClass().setAll("parent-node-default-svg");
	                        }
	                        else {
	                        	svgPath.getStyleClass().setAll("parent-node-notdefault-svg");
	                        }
	                    } else {
	                        label.getStyleClass().setAll("leaf-node-label");
	                        svgPath.getStyleClass().setAll("leaf-node-svg");
	                    }
	                } else {
	                    // Clear style for empty cells
	                    setStyle(null);
	                }

	                if (item.GetSVG() != null && item.GetSVG().getContent() != null && !item.GetSVG().getContent().isEmpty()) {
	                    // Set the SVG content if available
	                    svgPath.setContent(item.GetSVG().getContent());
	                } else {
	                    // Clear the SVG content if not available
	                    svgPath.setContent(null);
	                }

	                if (item.GetLabel() != null) {
	                    // Set the text label
	                    label.setText(item.GetLabel());
	                } else {
	                    // Clear the text label if not available
	                    label.setText(null);
	                }
	                label.setPadding(new Insets(0, 0, 0, 10)); // Example padding: 10px on the right
	                // Set the HBox as the graphic content
	                setGraphic(hbox);
	            }
	        }
	    }
	    
	    
	    



	        
	  
	}



package application.FrontEnd;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import application.BackEnd.Henderson;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;
import javafx.util.Callback;

public class HendersonController {



        static LinkedList<Henderson>ListHenderson = new LinkedList<>();
	    @FXML
	    private TreeView<TreeItemData> treeView;
	    @FXML
	    private Label HendersonLabel;

	    public void initialize(String FilePath) {
	        File file = new File(FilePath);
	        HendersonLabel.setText("Henderson Metric Of "+file.getName());
	           
	        ListHenderson =  Henderson.fetchHendersonData(file);
	        System.out.println("\n\n\nlist :" +ListHenderson);
	        String SumSvg="M 18 4 H 6 v 2 l 6.5 6 L 6 18 v 2 h 12 v -3 h -7 l 5 -5 l -5 -5 h 7 Z";
	        String ClassSvg="M 2 1.75 C 2 0.784 2.784 0 3.75 0 h 6.586 c 0.464 0 0.909 0.184 1.237 0.513 l 2.914 2.914 c 0.329 0.328 0.513 0.773 0.513 1.237 v 9.586 A 1.75 1.75 0 0 1 13.25 16 h -9.5 A 1.75 1.75 0 0 1 2 14.25 Z m 1.75 -0.25 a 0.25 0.25 0 0 0 -0.25 0.25 v 12.5 c 0 0.138 0.112 0.25 0.25 0.25 h 9.5 a 0.25 0.25 0 0 0 0.25 -0.25 V 6 h -2.75 A 1.75 1.75 0 0 1 9 4.25 V 1.5 Z m 6.75 0.062 V 4.25 c 0 0.138 0.112 0.25 0.25 0.25 h 2.688 l -0.011 -0.013 l -2.914 -2.914 l -0.013 -0.011 Z";
	        String MethodSvg="M 17.09 18.5 l -3.47 -3.47 L 12.5 18 L 10 10 l 8 2.5 l -2.97 1.11 l 3.47 3.47 L 17.09 18.5 Z M 10 3.5 c -3.58 0 -6.5 2.92 -6.5 6.5 s 2.92 6.5 6.5 6.5 c 0.15 0 0.3 -0.01 0.45 -0.02 l 0.46 1.46 C 10.61 17.98 10.31 18 10 18 c -4.42 0 -8 -3.58 -8 -8 s 3.58 -8 8 -8 l 0 0 c 4.42 0 8 3.58 8 8 c 0 0.31 -0.02 0.61 -0.05 0.91 l -1.46 -0.46 c 0.01 -0.15 0.02 -0.3 0.02 -0.45 C 16.5 6.42 13.58 3.5 10 3.5 M 10 6.5 c -1.93 0 -3.5 1.57 -3.5 3.5 c 0 1.76 1.31 3.23 3.01 3.47 L 10 15 c 0 0 -0.01 0 -0.01 0 C 7.23 15 5 12.76 5 10 c 0 -2.76 2.24 -5 5 -5 l 0 0 c 2.76 0 5 2.23 5 4.99 c 0 0 0 0.01 0 0.01 l -1.53 -0.49 C 13.23 7.81 11.76 6.5 10 6.5";
	        String AttributSvg="M 17 7 H 3 V 4 h 14 V 7 Z M 17 8.5 H 3 v 3 h 14 V 8.5 Z M 17 13 H 3 v 3 h 14 V 13 Z";
	        TreeItemData rootItemData = new TreeItemData("Henderson",SumSvg);
	        TreeItem<TreeItemData> rootItem = new TreeItem<>(rootItemData);
	        treeView.setRoot(rootItem);
	         for(Henderson henderson : ListHenderson) {
	        	TreeItem<TreeItemData> ClassParent = new TreeItem<>(new TreeItemData("Class "+henderson.getclassName(),ClassSvg));
	        	ClassParent.getChildren().add(new TreeItem<>(new TreeItemData(henderson.getNumberMethods()+" Methods" , MethodSvg)));
	        	ClassParent.getChildren().add(new TreeItem<>(new TreeItemData(henderson.getNumberAttributs()+" Attributs" , AttributSvg)));
	        	rootItem.getChildren().add(ClassParent);
		        	
	        }
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
	                        svgPath.getStyleClass().setAll("parent-node-svg");
	                        
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





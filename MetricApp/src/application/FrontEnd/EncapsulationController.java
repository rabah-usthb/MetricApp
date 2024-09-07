package application.FrontEnd;

import java.io.File;
import java.util.ArrayList;

import application.BackEnd.Encapsulation;
import application.BackEnd.ImportStatus;
import application.BackEnd.XMLResult;
import application.FrontEnd.ImportController.CustomTreeCell;
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

public class EncapsulationController {

    @FXML
    private TreeView<TreeItemData> treeView;
    @FXML
    private Label EncapsulationLabel;

    public void initialize(String FilePath) {
        File file = new File(FilePath);
        EncapsulationLabel.setText("Encapsulation of "+file.getName());
        Encapsulation encapsulation =   Encapsulation.EncapsulationFetch(file);
        XMLResult.ER_XML(encapsulation);
        String TotalSvgPath="M 18 4 H 6 v 2 l 6.5 6 L 6 18 v 2 h 12 v -3 h -7 l 5 -5 l -5 -5 h 7 Z";
        TreeItemData rootItemData = new TreeItemData("Total Elements : "+encapsulation.GetTotal(),TotalSvgPath);
        TreeItem<TreeItemData> rootItem = new TreeItem<>(rootItemData);
        treeView.setRoot(rootItem);
        String RationSvgPath="M 1.75 0 h 12.5 C 15.216 0 16 0.784 16 1.75 v 12.5 A 1.75 1.75 0 0 1 14.25 16 H 1.75 A 1.75 1.75 0 0 1 0 14.25 V 1.75 C 0 0.784 0.784 0 1.75 0 Z M 1.5 1.75 v 12.5 c 0 0.138 0.112 0.25 0.25 0.25 h 12.5 a 0.25 0.25 0 0 0 0.25 -0.25 V 1.75 a 0.25 0.25 0 0 0 -0.25 -0.25 H 1.75 a 0.25 0.25 0 0 0 -0.25 0.25 Z M 11.75 3 a 0.75 0.75 0 0 1 0.75 0.75 v 7.5 a 0.75 0.75 0 0 1 -1.5 0 v -7.5 a 0.75 0.75 0 0 1 0.75 -0.75 Z m -8.25 0.75 a 0.75 0.75 0 0 1 1.5 0 v 5.5 a 0.75 0.75 0 0 1 -1.5 0 Z M 8 3 a 0.75 0.75 0 0 1 0.75 0.75 v 3.5 a 0.75 0.75 0 0 1 -1.5 0 v -3.5 A 0.75 0.75 0 0 1 8 3 Z";
        String Ratio = String.format("%.2f", encapsulation.GetTaux());
        TreeItem<TreeItemData> RatioEncapsulation = new TreeItem<>(new TreeItemData("Ratio Encapsulation : "+Ratio,RationSvgPath));
        String PublicSvgPath="M 12 2 C 6.48 2 2 6.48 2 12 s 4.48 10 10 10 s 10 -4.48 10 -10 S 17.52 2 12 2 Z m -1 17.93 c -3.95 -0.49 -7 -3.85 -7 -7.93 c 0 -0.62 0.08 -1.21 0.21 -1.79 L 9 15 v 1 c 0 1.1 0.9 2 2 2 v 1.93 Z m 6.9 -2.54 c -0.26 -0.81 -1 -1.39 -1.9 -1.39 h -1 v -3 c 0 -0.55 -0.45 -1 -1 -1 H 8 v -2 h 2 c 0.55 0 1 -0.45 1 -1 V 7 h 2 c 1.1 0 2 -0.9 2 -2 v -0.41 c 2.93 1.19 5 4.06 5 7.41 c 0 2.08 -0.8 3.97 -2.1 5.39 Z"; 
        String ProtectedSvgPath="M 12 1 L 3 5 v 6 c 0 5.55 3.84 10.74 9 12 c 5.16 -1.26 9 -6.45 9 -12 V 5 l -9 -4 Z m 0 10.99 h 7 c -0.53 4.12 -3.28 7.79 -7 8.94 V 12 H 5 V 6.3 l 7 -3.11 v 8.8 Z";
        String PrivateSvgPath="M 18 8 h -1 V 6 c 0 -2.76 -2.24 -5 -5 -5 S 7 3.24 7 6 v 2 H 6 c -1.1 0 -2 0.9 -2 2 v 10 c 0 1.1 0.9 2 2 2 h 12 c 1.1 0 2 -0.9 2 -2 V 10 c 0 -1.1 -0.9 -2 -2 -2 Z m -6 9 c -1.1 0 -2 -0.9 -2 -2 s 0.9 -2 2 -2 s 2 0.9 2 2 s -0.9 2 -2 2 Z m 3.1 -9 H 8.9 V 6 c 0 -1.71 1.39 -3.1 3.1 -3.1 c 1.71 0 3.1 1.39 3.1 3.1 v 2 Z";
        String NoneSvgPath="M 12 17 c 1.1 0 2 -0.9 2 -2 s -0.9 -2 -2 -2 s -2 0.9 -2 2 s 0.9 2 2 2 Z m 6 -9 h -1 V 6 c 0 -2.76 -2.24 -5 -5 -5 S 7 3.24 7 6 h 1.9 c 0 -1.71 1.39 -3.1 3.1 -3.1 c 1.71 0 3.1 1.39 3.1 3.1 v 2 H 6 c -1.1 0 -2 0.9 -2 2 v 10 c 0 1.1 0.9 2 2 2 h 12 c 1.1 0 2 -0.9 2 -2 V 10 c 0 -1.1 -0.9 -2 -2 -2 Z m 0 12 H 6 V 10 h 12 v 10 Z";
        RatioEncapsulation.getChildren().add(new TreeItem<>(new TreeItemData("Public "+encapsulation.GetPublic(),PublicSvgPath)));	
        RatioEncapsulation.getChildren().add(new TreeItem<>(new TreeItemData("None "+encapsulation.GetNone(),NoneSvgPath)));
        RatioEncapsulation.getChildren().add(new TreeItem<>(new TreeItemData("Protected "+encapsulation.GetProtected(),ProtectedSvgPath)));
        RatioEncapsulation.getChildren().add(new TreeItem<>(new TreeItemData("Private "+encapsulation.GetPrivate(),PrivateSvgPath)));
        rootItem.getChildren().add(RatioEncapsulation);
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
                        if(treeItem.getValue().label.equals("Used Imports")) {
                        svgPath.getStyleClass().setAll("parent-node-used-svg");
                        }
                        else {
                        	svgPath.getStyleClass().setAll("parent-node-notused-svg");
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




package application.FrontEnd;

import javafx.application.Platform;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import      javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;


import application.BackEnd.Java;
import application.BackEnd.Package;
import application.BackEnd.RMSRCalculator;
import javafx.util.Callback;

public class MetricController {
	public static String PathProject;
	public static String FileSelectedPath;
	static String SelectedItem;
    @FXML
    private TreeView<TreeItemData> treeView;


    public void initialize(String pathProject) {
    	PathProject = pathProject;
        ArrayList<Package> listPackage = new ArrayList<>();
        File projectFile = new File(pathProject);
        File[] srcFile = projectFile.listFiles();
        Java.FetchSrcJavaFile(srcFile, listPackage);
        String ClosedFolderSvg="M 1.75 1 A 1.75 1.75 0 0 0 0 2.75 v 10.5 C 0 14.216 0.784 15 1.75 15 h 12.5 A 1.75 1.75 0 0 0 16 13.25 v -8.5 A 1.75 1.75 0 0 0 14.25 3 H 7.5 a 0.25 0.25 0 0 1 -0.2 -0.1 l -0.9 -1.2 C 6.07 1.26 5.55 1 5 1 H 1.75 Z";
        String OpenedFolderSvg="M 0.513 1.513 A 1.75 1.75 0 0 1 1.75 1 h 3.5 c 0.55 0 1.07 0.26 1.4 0.7 l 0.9 1.2 a 0.25 0.25 0 0 0 0.2 0.1 H 13 a 1 1 0 0 1 1 1 v 0.5 H 2.75 a 0.75 0.75 0 0 0 0 1.5 h 11.978 a 1 1 0 0 1 0.994 1.117 L 15 13.25 A 1.75 1.75 0 0 1 13.25 15 H 1.75 A 1.75 1.75 0 0 1 0 13.25 V 2.75 c 0 -0.464 0.184 -0.91 0.513 -1.237 Z";
        TreeItemData rootItemData = new TreeItemData("Src Folder",ClosedFolderSvg);
        TreeItem<TreeItemData> rootItem = new TreeItem<>(rootItemData);
        treeView.setRoot(rootItem);
        for (Package pkg : listPackage) {
            TreeItem<TreeItemData> packageItem = createTreeItem(pkg);
            rootItem.getChildren().add(packageItem);
        }
          
        rootItem.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                rootItem.getValue().svgPath.setContent(OpenedFolderSvg);
            } else {
                rootItem.getValue().svgPath.setContent(ClosedFolderSvg);
            }
            // Refresh the tree to apply changes
            treeView.refresh();
        });
        
        setTreeViewStyle();
        
        treeView.setOnKeyPressed(event -> {
            TreeItem<TreeItemData> selectedItem = treeView.getSelectionModel().getSelectedItem();
            if (selectedItem != null && event.getCode() == KeyCode.ENTER) {
                if (selectedItem.isLeaf()) {
                    // Handle leaf node selection here
                  System.out.println("Leaf node selected: " + selectedItem.getValue().GetLabel());
                    // Show a dialog with options
                  SelectedItem = selectedItem.getValue().label; 
                  String FilePath = RealPathConcat(getFileHierarchy(selectedItem),pathProject);
                    FileSelectedPath = FilePath;
                   LoadTabPane();
                }
            }
            });
        

        


                    
                    //alert.getButtonTypes().setAll(buttonType1, buttonType2, buttonType3, buttonTypeCancel);
                    
                   // alert.getDialogPane().requestFocus();
                   
                    
                    
                    //buttonBox.setPadding(new Insets(10));
                    //buttonBox.setAlignment(javafx.geometry.Pos.CENTER); // Center buttons horizontally

                    // Set HBox as the content of the dialog pane
                  //  alert.getDialogPane().setContent(buttonBox);
                    
                        
    }
    
    
    private void LoadTabPane() {
    	  try {
          	  Stage stage = new Stage();
              // Load the FXML file
              FXMLLoader loader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/TabedMetric.fxml"));
              String css = this.getClass().getResource("/ressource/Css Folder/TabedPane.css").toExternalForm();
              Parent root = loader.load();
              // Set the FXML content to the scene
              Scene scene = new Scene(root);
              scene.getStylesheets().add(css);
              stage.setResizable(false);
              stage.setScene(scene);
              stage.show();
          } catch (Exception e) {
              e.printStackTrace();
          }
    }
    
    public TreeView<TreeItemData> getTreeView() {
        return this.treeView;
    }
    
  
    String RealPathConcat(String FileHierachy , String JavaProjectPath) {
    	if(FileHierachy.contains(File.separator+"Default Package"+File.separator)) {
    		FileHierachy = FileHierachy.replace("Default Package"+File.separator, "");
    	}
    	FileHierachy = FileHierachy.replace("Src Folder", "");
    	FileHierachy=JavaProjectPath+FileHierachy;
    	return FileHierachy;
    }

    private String getFileHierarchy(TreeItem<TreeItemData> leafNode) {
        StringBuilder hierarchy = new StringBuilder(leafNode.getValue().GetLabel());
        TreeItem<TreeItemData> parent = leafNode.getParent();
        while (parent != null) {
            hierarchy.insert(0, parent.getValue().GetLabel() + File.separator);
            parent = parent.getParent();
        }
        return hierarchy.toString();
    }


    private TreeItem<TreeItemData> createTreeItem(Package pkg) {
        TreeItem<TreeItemData> packageItem = new TreeItem<>(new TreeItemData(pkg.PackageName,"M 3 3 v 8 h 8 V 3 H 3 Z m 6 6 H 5 V 5 h 4 v 4 Z m -6 4 v 8 h 8 v -8 H 3 Z m 6 6 H 5 v -4 h 4 v 4 Z m 4 -16 v 8 h 8 V 3 h -8 Z m 6 6 h -4 V 5 h 4 v 4 Z m -6 4 v 8 h 8 v -8 h -8 Z m 6 6 h -4 v -4 h 4 v 4 Z"));
        
        for (Package subPackage : pkg.SubPackges) {
            TreeItem<TreeItemData> subPackageItem = createTreeItem(subPackage);
            packageItem.getChildren().add(subPackageItem);
        }
        for (String fileInfo : pkg.FileNameList) {
            packageItem.getChildren().add(new TreeItem<>(new TreeItemData(fileInfo,"M 2 1.75 C 2 0.784 2.784 0 3.75 0 h 6.586 c 0.464 0 0.909 0.184 1.237 0.513 l 2.914 2.914 c 0.329 0.328 0.513 0.773 0.513 1.237 v 9.586 A 1.75 1.75 0 0 1 13.25 16 h -9.5 A 1.75 1.75 0 0 1 2 14.25 Z m 1.75 -0.25 a 0.25 0.25 0 0 0 -0.25 0.25 v 12.5 c 0 0.138 0.112 0.25 0.25 0.25 h 9.5 a 0.25 0.25 0 0 0 0.25 -0.25 V 6 h -2.75 A 1.75 1.75 0 0 1 9 4.25 V 1.5 Z m 6.75 0.062 V 4.25 c 0 0.138 0.112 0.25 0.25 0.25 h 2.688 l -0.011 -0.013 l -2.914 -2.914 l -0.013 -0.011 Z")));
        }
        return packageItem;
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

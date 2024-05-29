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
                    String FilePath = RealPathConcat(getFileHierarchy(selectedItem),pathProject);
                    FileSelectedPath = FilePath;
                    System.out.println(FilePath);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Java Metrics");
                    alert.setContentText("Choose an action to perform:");
                   // DialogPane dialogPane = alert.getDialogPane();
                    //dialogPane.lookup(".header-panel").getStyleClass().add("alert-header");
                   // Label header = (Label) alert.getDialogPane().lookup(".header-panel");
                   // header.setStyle("-fx-font-size: 30px;");
                    //dialogPane.lookup(".content.label").getStyleClass().add("alert-content");
                   
                   // HBox buttonBox = new HBox(10,buttonType1);
                    alert.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/ressource/PNG Folder/metrics (2).png"))));
                    ImageView imageView = (ImageView) alert.getGraphic();
                    imageView.setFitWidth(60); // Set the preferred width
                    imageView.setFitHeight(60); // Set the preferred height
                 //   alert.getGraphic().getStyleClass().add("imageView");
                    alert.getButtonTypes().remove(ButtonType.OK); 
                    Button importsButton = new Button("ICM");
                    Button AnalysisButton = new Button("JAX");
                    Button exceptionButton = new Button("JEA");
                    Button EncapsulationButton = new Button("TE");
                    Button SwingButton = new Button("SM");
                    Button PolymorphismeButon  = new Button("RMSR");
                    Button MVCButton = new Button("MVC");
                    Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
                    cancelButton.setDefaultButton(true);
                    
                    
                    
                    importsButton.setOnAction(e -> {
                        // Handle "Imports" button action
                        System.out.println("Imports button clicked");

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/Import.fxml"));
                        Parent root = null;
						try {
							root = fxmlLoader.load();
						} catch (IOException exception) {
					
							exception.printStackTrace();
						}
                        ImportController importController = fxmlLoader.getController();
                        importController.initialize(FilePath);
                        Scene scene = new Scene(root);
                        String css = this.getClass().getResource("/ressource/Css Folder/Import.css").toExternalForm();
                        scene.getStylesheets().add(css);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                        alert.close();
                    
                    });

                    AnalysisButton.setOnAction(e -> {
                        // Handle "Line Code" button action
                        System.out.println("Line Code button clicked");
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/Analysis.fxml"));
                        Parent root = null;
						try {
							root = fxmlLoader.load();
						} catch (IOException exception) {
							// TODO Auto-generated catch block
							exception.printStackTrace();
						}
                   	AnalysisController analysisController = fxmlLoader.getController();
                       analysisController.initialize(FilePath);
                       Scene scene = new Scene(root);
                       String css = this.getClass().getResource("/ressource/Css Folder/Analysis.css").toExternalForm();
                       scene.getStylesheets().add(css);
                       Stage stage = new Stage();
                       stage.setScene(scene);
                       stage.show();
                       alert.close();
                    
                    });

                    exceptionButton.setOnAction(e -> {
                        // Handle "Exception" button action
                        System.out.println("Exception button clicked");
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/Exception.fxml"));
                        Parent root = null;
						try {
							root = fxmlLoader.load();
						} catch (IOException exception) {
							// TODO Auto-generated catch block
							exception.printStackTrace();
						}
                   	ExceptionController exceptionController = fxmlLoader.getController();
                       exceptionController.initialize(FilePath);
                       Scene scene = new Scene(root);
                       String css = this.getClass().getResource("/ressource/Css Folder/Exception.css").toExternalForm();
                       scene.getStylesheets().add(css);
                       Stage stage = new Stage();
                       stage.setScene(scene);
                       stage.show();
                       alert.close();

                    });

                    SwingButton.setOnAction(e->{
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/SwingComponent.fxml"));
                        Parent root = null;
						try {
							root = fxmlLoader.load();
						} catch (IOException exception) {
							// TODO Auto-generated catch block
							exception.printStackTrace();
						}
                   	SwingComponentController swingController = fxmlLoader.getController();
                       swingController.initialize(FilePath);
                       Scene scene = new Scene(root);
                       String css = this.getClass().getResource("/ressource/Css Folder/Swing.css").toExternalForm();
                       scene.getStylesheets().add(css);
                       Stage stage = new Stage();
                       stage.setScene(scene);
                       stage.show();
                       alert.close();
                   });
                    
                    EncapsulationButton.setOnAction(e-> {
                         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/Encapsulation.fxml"));
                         Parent root = null;
 						try {
 							root = fxmlLoader.load();
 						} catch (IOException exception) {
 							// TODO Auto-generated catch block
 							exception.printStackTrace();
 						}
                    	EncapsulationController encapsulationController = fxmlLoader.getController();
                        encapsulationController.initialize(FilePath);
                        Scene scene = new Scene(root);
                        String css = this.getClass().getResource("/ressource/Css Folder/Exception.css").toExternalForm();
                        scene.getStylesheets().add(css);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                        alert.close();
                    });
                    
                    PolymorphismeButon.setOnAction(e->{
                    	 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/RMRS.fxml"));
                         Parent root = null;
 						try {
 							root = fxmlLoader.load();
 						} catch (IOException exception) {
 							// TODO Auto-generated catch block
 							exception.printStackTrace();
 						}
 				       RMRScontroller rmrsController = fxmlLoader.getController();
                        rmrsController.initialize(FilePath,selectedItem.getValue().GetLabel());
                        Scene scene = new Scene(root);
                        String css = this.getClass().getResource("/ressource/Css Folder/RMRS.css").toExternalForm();
                      scene.getStylesheets().add(css);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                        alert.close();
                    });
                    /*
                    MVCButton.setOnAction(e->{
                   	 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/MVC.fxml"));
                        Parent root = null;
						try {
							root = fxmlLoader.load();
						} catch (IOException exception) {
							// TODO Auto-generated catch block
							exception.printStackTrace();
						}
				       MVCinfoscontroller MvcController = fxmlLoader.getController();
                       MvcController.initialize(PathProject,"C:\\Users\\DELL\\PROJET-PLURIDISCIPLINAIRE\\MetricApp\\src\\application\\FrontEnd\\RMRScontroller.java","C:\\Users\\DELL\\PROJET-PLURIDISCIPLINAIRE\\MetricApp\\src\\application\\BackEnd\\RegularExpression.java","C:\\Users\\DELL\\PROJET-PLURIDISCIPLINAIRE\\MetricApp\\src\\application\\FrontEnd\\Main.java");
                       Scene scene = new Scene(root);
                       String css = this.getClass().getResource("/ressource/Css Folder/RMRS.css").toExternalForm();
                     scene.getStylesheets().add(css);
                       Stage stage = new Stage();
                       stage.setScene(scene);
                       stage.show();
                   });
                    */
                    // Create an HBox container for the buttons
                    HBox buttonBox = new HBox(10, importsButton, AnalysisButton, exceptionButton,cancelButton,EncapsulationButton,SwingButton,PolymorphismeButon);
                    buttonBox.setAlignment(Pos.CENTER); // Center the buttons horizontally within the HBox

                    // Add buttons to the dialog pane
                   
                    Label instructionLabel = new Label("Choose an action to perform:");
                    instructionLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
                    VBox content = new VBox(30,instructionLabel,buttonBox);
                    content.setAlignment(Pos.CENTER); // Center the content vertically within the VBox

                    // Set the content of the alert dialog
                    alert.getDialogPane().setContent(content);
              
                   

                    String CssAlert=this.getClass().getResource("/ressource/Css Folder/alert.css").toExternalForm();
                    alert.getDialogPane().getStylesheets().add(CssAlert);
                    Platform.runLater(() -> {
                        // Show the alert and wait for user action
                        alert.showAndWait();
                    });
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

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
	import javafx.scene.layout.HBox;
	import javafx.scene.shape.SVGPath;
	import javafx.stage.Stage;

	import java.io.File;
	import java.io.IOException;
	import java.util.ArrayList;

import application.BackEnd.*;
import javafx.util.Callback;
public class ImportController {

	    @FXML
	    private TreeView<TreeItemData> treeView;
	    @FXML
	    private Label ImportLabel;
	    static ArrayList<ImportStatus>ListImport;

	    public void initialize(String FilePath) {
	        File file = new File(FilePath);
	        ImportLabel.setText("Imports of "+file.getName());
	        ListImport = new ArrayList<ImportStatus>();
	       ListImport = ImportStatus.update(file,(ImportStatus.ImportFetch(file)));
	       ImportStatus.UpdateConflictFlag(ListImport);
	        String BookSvg="M 2 2.5 A 2.5 2.5 0 0 1 4.5 0 h 8.75 a 0.75 0.75 0 0 1 0.75 0.75 v 12.5 a 0.75 0.75 0 0 1 -0.75 0.75 h -2.5 a 0.75 0.75 0 0 1 0 -1.5 h 1.75 v -2 h -8 a 1 1 0 0 0 -0.714 1.7 a 0.75 0.75 0 1 1 -1.072 1.05 A 2.495 2.495 0 0 1 2 11.5 Z m 10.5 -1 h -8 a 1 1 0 0 0 -1 1 v 6.708 A 2.486 2.486 0 0 1 4.5 9 h 8 Z M 5 12.25 a 0.25 0.25 0 0 1 0.25 -0.25 h 3.5 a 0.25 0.25 0 0 1 0.25 0.25 v 3.25 a 0.25 0.25 0 0 1 -0.4 0.2 l -1.45 -1.087 a 0.249 0.249 0 0 0 -0.3 0 L 5.4 15.7 a 0.25 0.25 0 0 1 -0.4 -0.2 Z";
	        String UsedSvg="M 9 16.17 L 4.83 12 l -1.42 1.41 L 9 19 L 21 7 l -1.41 -1.41 Z";
	        String NotUsedSvg="M 19 6.41 L 17.59 5 L 12 10.59 L 6.41 5 L 5 6.41 L 10.59 12 L 5 17.59 L 6.41 19 L 12 13.41 L 17.59 19 L 19 17.59 L 13.41 12 Z";
	        String ConflictSvg="M 7.467 0.133 a 1.748 1.748 0 0 1 1.066 0 l 5.25 1.68 A 1.75 1.75 0 0 1 15 3.48 V 7 c 0 1.566 -0.32 3.182 -1.303 4.682 c -0.983 1.498 -2.585 2.813 -5.032 3.855 a 1.697 1.697 0 0 1 -1.33 0 c -2.447 -1.042 -4.049 -2.357 -5.032 -3.855 C 1.32 10.182 1 8.566 1 7 V 3.48 a 1.75 1.75 0 0 1 1.217 -1.667 Z m 0.61 1.429 a 0.25 0.25 0 0 0 -0.153 0 l -5.25 1.68 a 0.25 0.25 0 0 0 -0.174 0.238 V 7 c 0 1.358 0.275 2.666 1.057 3.86 c 0.784 1.194 2.121 2.34 4.366 3.297 a 0.196 0.196 0 0 0 0.154 0 c 2.245 -0.956 3.582 -2.104 4.366 -3.298 C 13.225 9.666 13.5 8.36 13.5 7 V 3.48 a 0.251 0.251 0 0 0 -0.174 -0.237 l -5.25 -1.68 Z M 8.75 4.75 v 3 a 0.75 0.75 0 0 1 -1.5 0 v -3 a 0.75 0.75 0 0 1 1.5 0 Z M 9 10.5 a 1 1 0 1 1 -2 0 a 1 1 0 0 1 2 0 Z";
	        String PackageSvg="M 3 3 v 8 h 8 V 3 H 3 Z m 6 6 H 5 V 5 h 4 v 4 Z m -6 4 v 8 h 8 v -8 H 3 Z m 6 6 H 5 v -4 h 4 v 4 Z m 4 -16 v 8 h 8 V 3 h -8 Z m 6 6 h -4 V 5 h 4 v 4 Z m -6 4 v 8 h 8 v -8 h -8 Z m 6 6 h -4 v -4 h 4 v 4 Z";
	        String ClassSvg="M 2 1.75 C 2 0.784 2.784 0 3.75 0 h 6.586 c 0.464 0 0.909 0.184 1.237 0.513 l 2.914 2.914 c 0.329 0.328 0.513 0.773 0.513 1.237 v 9.586 A 1.75 1.75 0 0 1 13.25 16 h -9.5 A 1.75 1.75 0 0 1 2 14.25 Z m 1.75 -0.25 a 0.25 0.25 0 0 0 -0.25 0.25 v 12.5 c 0 0.138 0.112 0.25 0.25 0.25 h 9.5 a 0.25 0.25 0 0 0 0.25 -0.25 V 6 h -2.75 A 1.75 1.75 0 0 1 9 4.25 V 1.5 Z m 6.75 0.062 V 4.25 c 0 0.138 0.112 0.25 0.25 0.25 h 2.688 l -0.011 -0.013 l -2.914 -2.914 l -0.013 -0.011 Z";
	        TreeItemData rootItemData = new TreeItemData("Imports",BookSvg);
	        TreeItem<TreeItemData> rootItem = new TreeItem<>(rootItemData);
	        treeView.setRoot(rootItem);
	        TreeItem<TreeItemData> UsedImportParent = new TreeItem<>(new TreeItemData("Used Imports",UsedSvg));
	        TreeItem<TreeItemData> NotUsedImportParent = new TreeItem<>(new TreeItemData("Unused Imports",NotUsedSvg));
	        TreeItem<TreeItemData> ConflictImport = new TreeItem<>(new TreeItemData("Conflict Imports", ConflictSvg));
	        for (ImportStatus Import : ListImport) {
	            //TreeItem<TreeItemData> ImportItem = createTreeItem(Import);
	            if(Import.ConflictStatus!=1) {
	        	if(Import.ImportStatus == 1) {
	        		if(!Import.ImportName.contains("*")) {
	            UsedImportParent.getChildren().add(new TreeItem<>(new TreeItemData(Import.LineNumber+Import.ImportName,PackageSvg)));
	        		}
	        		else {
	        			 TreeItem<TreeItemData> WildCard = new TreeItem<>(new TreeItemData(Import.ImportName,PackageSvg));
	        		        			
	        			for(String classUsed : Import.UsedClassList) {
	        				WildCard.getChildren().add(new TreeItem<>(new TreeItemData(classUsed,ClassSvg)));
	        			}
	        			UsedImportParent.getChildren().add(WildCard);
	        		}
	        		}
	            else {
	            	NotUsedImportParent.getChildren().add(new TreeItem<>(new TreeItemData(Import.LineNumber+Import.ImportName,PackageSvg)));
	            }
	            }
	            else {
	            	ConflictImport.getChildren().add(new TreeItem<>(new TreeItemData(Import.ImportName,"M 3 3 v 8 h 8 V 3 H 3 Z m 6 6 H 5 V 5 h 4 v 4 Z m -6 4 v 8 h 8 v -8 H 3 Z m 6 6 H 5 v -4 h 4 v 4 Z m 4 -16 v 8 h 8 V 3 h -8 Z m 6 6 h -4 V 5 h 4 v 4 Z m -6 4 v 8 h 8 v -8 h -8 Z m 6 6 h -4 v -4 h 4 v 4 Z")));
	            }
	        }
	        if(UsedImportParent.getChildren().isEmpty()) {
	        	UsedImportParent.getChildren().add(new TreeItem<>(new TreeItemData("None","M 12 2 C 6.5 2 2 6.5 2 12 s 4.5 10 10 10 s 10 -4.5 10 -10 S 17.5 2 12 2 Z M 4 12 c 0 -4.4 3.6 -8 8 -8 c 1.8 0 3.5 0.6 4.9 1.7 L 5.7 16.9 C 4.6 15.5 4 13.8 4 12 Z m 8 8 c -1.8 0 -3.5 -0.6 -4.9 -1.7 L 18.3 7.1 C 19.4 8.5 20 10.2 20 12 c 0 4.4 -3.6 8 -8 8 Z")));
	        }
	         if(NotUsedImportParent.getChildren().isEmpty()) {
	        	NotUsedImportParent.getChildren().add(new TreeItem<>(new TreeItemData("None","M 12 2 C 6.5 2 2 6.5 2 12 s 4.5 10 10 10 s 10 -4.5 10 -10 S 17.5 2 12 2 Z M 4 12 c 0 -4.4 3.6 -8 8 -8 c 1.8 0 3.5 0.6 4.9 1.7 L 5.7 16.9 C 4.6 15.5 4 13.8 4 12 Z m 8 8 c -1.8 0 -3.5 -0.6 -4.9 -1.7 L 18.3 7.1 C 19.4 8.5 20 10.2 20 12 c 0 4.4 -3.6 8 -8 8 Z")));
	        }
	         if(ConflictImport.getChildren().isEmpty()) {
	        	 ConflictImport.getChildren().add(new TreeItem<>(new TreeItemData("None","M 12 2 C 6.5 2 2 6.5 2 12 s 4.5 10 10 10 s 10 -4.5 10 -10 S 17.5 2 12 2 Z M 4 12 c 0 -4.4 3.6 -8 8 -8 c 1.8 0 3.5 0.6 4.9 1.7 L 5.7 16.9 C 4.6 15.5 4 13.8 4 12 Z m 8 8 c -1.8 0 -3.5 -0.6 -4.9 -1.7 L 18.3 7.1 C 19.4 8.5 20 10.2 20 12 c 0 4.4 -3.6 8 -8 8 Z")));
		        }
	        rootItem.getChildren().add(UsedImportParent);
	        rootItem.getChildren().add(NotUsedImportParent);
	        rootItem.getChildren().add(ConflictImport);

	        treeView.setOnKeyPressed(event -> {
	        	TreeItem<TreeItemData> selectedItem = treeView.getSelectionModel().getSelectedItem();
	        	if (selectedItem != null && event.getCode() == KeyCode.ENTER) {
	        	if(!selectedItem.isLeaf() && selectedItem.getParent()!=null&&selectedItem.getParent().getValue().label.equals("Used Imports")) {
	        		System.out.println("Selected Wild Card : "+selectedItem.getValue().label);
	        		ImportStatus WildCardImport = null;
	        		for(ImportStatus Import : ListImport) {
	        			if(Import.ImportName.equals(selectedItem.getValue().label)) {
	        				WildCardImport = Import;
	        			}
	        		}
	        		System.out.println(WildCardImport.ImportName);
	        		System.out.println(WildCardImport.LineNumber);
	        		String ReplacedImport="";
	        		for(String ClassName : WildCardImport.UsedClassList) {
	        			ReplacedImport = ReplacedImport+"import "+WildCardImport.ImportName.replace("*", ClassName)+";\n";
	        		}
	        		 
	        		showConfirmationDialog(WildCardImport.ImportName, WildCardImport.LineNumber-1, ReplacedImport);
	        	}
	        		
	        	}
	        	
	        });
	        
	        setTreeViewStyle();
	        
	     
	    }


	    
	    private void showConfirmationDialog(String WildImport,int NumberLine,String ReplacedImport) {
	        // Create a confirmation dialog
	        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	        alert.setTitle("Confirmation Dialog");
	        alert.setHeaderText("Replacing WildCard Import");
	        alert.setContentText("Are you sure you want to replace "+WildImport);

	        // Show the dialog and wait for the user's response
	        alert.showAndWait().ifPresent(response -> {
	            if (response == ButtonType.OK) {
	                try {
						ImportStatus.ReplaceWildCardImport(MetricController.FileSelectedPath, NumberLine, ReplacedImport);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                // Perform deletion or other action here
	            } else {
	                System.out.println("User clicked Cancel");
	                // Cancel action or close the dialog
	            }
	        });
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
	                        
	                        if(treeItem.getValue().label.contains("*")) {
	                        	svgPath.getStyleClass().setAll("wild-node-svg");
	                        	label.getStyleClass().setAll("wild-node-label");	
	                        }
	                        else if(treeItem.getValue().label.equals("Used Imports")) {
	                        svgPath.getStyleClass().setAll("parent-node-used-svg");
	                        label.getStyleClass().setAll("parent-node-label");
	                        }
	                        else {
	                        	svgPath.getStyleClass().setAll("parent-node-notused-svg");
	                        	label.getStyleClass().setAll("parent-node-label");
	                        }
	                    } else {
	                    	label.getStyleClass().setAll("leaf-node-label");
	                        if(treeItem.getValue().label.contains(".")) {
	                    	svgPath.getStyleClass().setAll("leaf-node-svg");
	                        }else if(treeItem.getValue().label.equals("None")){
	                        	svgPath.getStyleClass().setAll("none-node-svg");
	                        }
	                        else {
	                        	svgPath.getStyleClass().setAll("class-node-svg");
	                        }
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


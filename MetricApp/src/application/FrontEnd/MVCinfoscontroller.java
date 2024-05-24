package application.FrontEnd;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import application.BackEnd.Line;
import application.BackEnd.MVCinfosCalculator;
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


public class MVCinfoscontroller {
	
    @FXML
    private TreeView<TreeItemData> treeView;
    @FXML
    private Label MVCLabel;
    
    public void initialize(String FilePath,String filenameController,String filenameModel,String filenameView) {
        File file = new File(FilePath);
        MVCinfosCalculator mvc = null;
		try {
			mvc = MVCinfosCalculator.fetchMVCinfos(FilePath,filenameController, filenameModel, filenameView );
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        MVCLabel.setText("Number Of methods Of "+file.getName());
        TreeItemData rootItemData = new TreeItemData("MVCinfos ","M18 4H6v2l6.5 6L6 18v2h12v-3h-7l5-5-5-5h7z");
        TreeItem<TreeItemData> rootItem = new TreeItem<>(rootItemData);
        treeView.setRoot(rootItem);
        String dependenciesSvgPath="M10,2c-4.42,0-8,3.58-8,8s3.58,8,8,8s8-3.58,8-8S14.42,2,10,2z M9.88,15.62V14c-1.02-0.03-1.98-0.44-2.71-1.17 C6.42,12.07,6,11.07,6,10c0-0.7,0.18-1.38,0.52-1.98l0.74,0.74C7.09,9.15,7,9.57,7,10c0,0.8,0.31,1.55,0.88,2.12 c0.54,0.53,1.27,0.82,2,0.85v-1.59L12,13.5L9.88,15.62z M13.48,11.98l-0.74-0.74C12.91,10.85,13,10.43,13,10 c0-0.8-0.31-1.55-0.88-2.12c-0.54-0.54-1.24-0.85-2-0.88v1.62L8,6.5l2.12-2.12v1.66c1,0.03,1.98,0.41,2.71,1.13 C13.58,7.93,14,8.93,14,10C14,10.7,13.82,11.38,13.48,11.98z";
        String nameMethodsSvgPath="M18.95,14.89l0.85-0.79l-0.67-1.15l-1.12,0.33c-0.31-0.28-0.67-0.48-1.07-0.62l-0.28-1.16h-1.33l-0.27,1.17 c-0.39,0.13-0.75,0.34-1.05,0.61l-1.14-0.34L12.2,14.1l0.87,0.81c-0.11,0.54-0.05,0.97,0,1.21l-0.87,0.83l0.67,1.15l1.16-0.36 c0.3,0.26,0.64,0.46,1.03,0.59l0.27,1.17h1.33l0.28-1.16c0.39-0.13,0.75-0.33,1.05-0.6l1.15,0.35l0.67-1.15l-0.86-0.81 C19.07,15.53,18.98,15.07,18.95,14.89z M16.01,17c-0.83,0-1.5-0.67-1.5-1.5s0.67-1.5,1.5-1.5s1.5,0.67,1.5,1.5S16.84,17,16.01,17z M10.49,16.98C10.33,16.99,10.17,17,10,17c-3.87,0-7-3.13-7-7h1.5c0,2.91,2.27,5.3,5.13,5.49L10.49,16.98z M15.5,10 c0-3.03-2.47-5.5-5.5-5.5c-1.7,0-3.22,0.78-4.22,2H8V8H3V3h1.5v2.71C5.78,4.07,7.76,3,10,3c3.87,0,7,3.13,7,7H15.5z M10.75,6v3.38 l1.55,1.55l-0.78,1.34L9.25,10V6H10.75z";
        String complexitySvgPath="M18.95,14.89l0.85-0.79l-0.67-1.15l-1.12,0.33c-0.31-0.28-0.67-0.48-1.07-0.62l-0.28-1.16h-1.33l-0.27,1.17 c-0.39,0.13-0.75,0.34-1.05,0.61l-1.14-0.34L12.2,14.1l0.87,0.81c-0.11,0.54-0.05,0.97,0,1.21l-0.87,0.83l0.67,1.15l1.16-0.36 c0.3,0.26,0.64,0.46,1.03,0.59l0.27,1.17h1.33l0.28-1.16c0.39-0.13,0.75-0.33,1.05-0.6l1.15,0.35l0.67-1.15l-0.86-0.81 C19.07,15.53,18.98,15.07,18.95,14.89z M16.01,17c-0.83,0-1.5-0.67-1.5-1.5s0.67-1.5,1.5-1.5s1.5,0.67,1.5,1.5S16.84,17,16.01,17z M10.49,16.98C10.33,16.99,10.17,17,10,17c-3.87,0-7-3.13-7-7h1.5c0,2.91,2.27,5.3,5.13,5.49L10.49,16.98z M15.5,10 c0-3.03-2.47-5.5-5.5-5.5c-1.7,0-3.22,0.78-4.22,2H8V8H3V3h1.5v2.71C5.78,4.07,7.76,3,10,3c3.87,0,7,3.13,7,7H15.5z M10.75,6v3.38 l1.55,1.55l-0.78,1.34L9.25,10V6H10.75z";


        // String RatiooverloadValue = String.format("%.2f", rm.RatioMethodsSur);
        // String RatiooverrideValue = String.format("%.2f", rm.RatioMethodsRedef);
        TreeItem<TreeItemData> dependencies = new TreeItem<>(new TreeItemData("dependecies Statistique ",dependenciesSvgPath));
        TreeItem<TreeItemData> nameMethods = new TreeItem<>(new TreeItemData("nameMethods Statistique ",nameMethodsSvgPath));
        TreeItem<TreeItemData> complexity = new TreeItem<>(new TreeItemData("complexity Statistique ",complexitySvgPath));
        dependencies.getChildren().add(new TreeItem<>(new TreeItemData(printCouplings(mvc.couplings),complexitySvgPath)));
        nameMethods.getChildren().add(new TreeItem<>(new TreeItemData(DisplayMethods(mvc.loadedClassController)+DisplayMethods(mvc.loadedClassModel)+DisplayMethods(mvc.loadedClassView),complexitySvgPath)));
        complexity.getChildren().add(new TreeItem<>(new TreeItemData(printReport(mvc.dataFlowCount),complexitySvgPath)));     
        
        rootItem.getChildren().add(dependencies);
        rootItem.getChildren().add(nameMethods);
        rootItem.getChildren().add(complexity);
        
        setTreeViewStyle();



    }


    private String printCouplings(Map<String, Set<String>> couplings) {
        String Output = null;
        for (Map.Entry<String, Set<String>> entry : couplings.entrySet()) {
            Output.concat(entry.getKey() + " depends on: " + entry.getValue()+"\n");
        }
        return Output;
    }


    private String DisplayMethods(Class<?> cl){
        Method[] methods = cl.getDeclaredMethods();
        String Output;
        Output="mothods of the class: "+ cl.getName() +" Are: \n";
        for(Method method : methods){
            Output.concat("  "+method.getName()+" , ");
        }
        Output.concat("\n");
        return Output;
    }

    public String printReport( Map<String, Integer> dataFlowCount) {
       String Output = null;
        dataFlowCount.forEach((method, count) -> Output.concat("Method " + method + " is called " + count + " times.\n"));
        Output.concat("Total complexity: " + dataFlowCount.values().stream().mapToInt(Integer::intValue).sum());
        return Output;
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
                label.setPadding(new Insets(0, 0, 0, 15)); // Example padding: 10px on the right
                // Set the HBox as the graphic content
                setGraphic(hbox);
            }
        }
    }
    
    
    



        
  
}



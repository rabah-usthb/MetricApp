package application.FrontEnd;

import java.io.File;


import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import application.BackEnd.JAX;
import application.BackEnd.Line;
import application.BackEnd.Package;
import application.BackEnd.PerformanceMetric;
import application.BackEnd.RegularExpression;
import application.BackEnd.SoftwareSize;
import application.BackEnd.XMLResult;
import application.BackEnd.classType;
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

public class AnalysisController {
    @FXML
    private TreeView<TreeItemData> treeView;
    @FXML
    private Label LineLabel;
    String NoneSvg="M 12 2 C 6.5 2 2 6.5 2 12 s 4.5 10 10 10 s 10 -4.5 10 -10 S 17.5 2 12 2 Z M 4 12 c 0 -4.4 3.6 -8 8 -8 c 1.8 0 3.5 0.6 4.9 1.7 L 5.7 16.9 C 4.6 15.5 4 13.8 4 12 Z m 8 8 c -1.8 0 -3.5 -0.6 -4.9 -1.7 L 18.3 7.1 C 19.4 8.5 20 10.2 20 12 c 0 4.4 -3.6 8 -8 8 Z";
    String ParentSvg="M 16.5 12 c 1.38 0 2.49 -1.12 2.49 -2.5 S 17.88 7 16.5 7 C 15.12 7 14 8.12 14 9.5 s 1.12 2.5 2.5 2.5 Z M 9 11 c 1.66 0 2.99 -1.34 2.99 -3 S 10.66 5 9 5 C 7.34 5 6 6.34 6 8 s 1.34 3 3 3 Z m 7.5 3 c -1.83 0 -5.5 0.92 -5.5 2.75 V 19 h 11 v -2.25 c 0 -1.83 -3.67 -2.75 -5.5 -2.75 Z M 9 13 c -2.33 0 -7 1.17 -7 3.5 V 19 h 7 v -2.25 c 0 -0.85 0.33 -2.34 2.37 -3.47 C 10.5 13.1 9.66 13 9 13 Z";
    String NumberSvg="M 18 4 H 6 v 2 l 6.5 6 L 6 18 v 2 h 12 v -3 h -7 l 5 -5 l -5 -5 h 7 Z";
    String InterfaceSvg="M 19.14 12.94 c 0.04 -0.3 0.06 -0.61 0.06 -0.94 c 0 -0.32 -0.02 -0.64 -0.07 -0.94 l 2.03 -1.58 c 0.18 -0.14 0.23 -0.41 0.12 -0.61 l -1.92 -3.32 c -0.12 -0.22 -0.37 -0.29 -0.59 -0.22 l -2.39 0.96 c -0.5 -0.38 -1.03 -0.7 -1.62 -0.94 L 14.4 2.81 c -0.04 -0.24 -0.24 -0.41 -0.48 -0.41 h -3.84 c -0.24 0 -0.43 0.17 -0.47 0.41 L 9.25 5.35 C 8.66 5.59 8.12 5.92 7.63 6.29 L 5.24 5.33 c -0.22 -0.08 -0.47 0 -0.59 0.22 L 2.74 8.87 C 2.62 9.08 2.66 9.34 2.86 9.48 l 2.03 1.58 C 4.84 11.36 4.8 11.69 4.8 12 s 0.02 0.64 0.07 0.94 l -2.03 1.58 c -0.18 0.14 -0.23 0.41 -0.12 0.61 l 1.92 3.32 c 0.12 0.22 0.37 0.29 0.59 0.22 l 2.39 -0.96 c 0.5 0.38 1.03 0.7 1.62 0.94 l 0.36 2.54 c 0.05 0.24 0.24 0.41 0.48 0.41 h 3.84 c 0.24 0 0.44 -0.17 0.47 -0.41 l 0.36 -2.54 c 0.59 -0.24 1.13 -0.56 1.62 -0.94 l 2.39 0.96 c 0.22 0.08 0.47 0 0.59 -0.22 l 1.92 -3.32 c 0.12 -0.22 0.07 -0.47 -0.12 -0.61 L 19.14 12.94 Z M 12 15.6 c -1.98 0 -3.6 -1.62 -3.6 -3.6 s 1.62 -3.6 3.6 -3.6 s 3.6 1.62 3.6 3.6 S 13.98 15.6 12 15.6 Z";
    String MethodSvg ="M 17.09 18.5 l -3.47 -3.47 L 12.5 18 L 10 10 l 8 2.5 l -2.97 1.11 l 3.47 3.47 L 17.09 18.5 Z M 10 3.5 c -3.58 0 -6.5 2.92 -6.5 6.5 s 2.92 6.5 6.5 6.5 c 0.15 0 0.3 -0.01 0.45 -0.02 l 0.46 1.46 C 10.61 17.98 10.31 18 10 18 c -4.42 0 -8 -3.58 -8 -8 s 3.58 -8 8 -8 l 0 0 c 4.42 0 8 3.58 8 8 c 0 0.31 -0.02 0.61 -0.05 0.91 l -1.46 -0.46 c 0.01 -0.15 0.02 -0.3 0.02 -0.45 C 16.5 6.42 13.58 3.5 10 3.5 M 10 6.5 c -1.93 0 -3.5 1.57 -3.5 3.5 c 0 1.76 1.31 3.23 3.01 3.47 L 10 15 c 0 0 -0.01 0 -0.01 0 C 7.23 15 5 12.76 5 10 c 0 -2.76 2.24 -5 5 -5 l 0 0 c 2.76 0 5 2.23 5 4.99 c 0 0 0 0.01 0 0.01 l -1.53 -0.49 C 13.23 7.81 11.76 6.5 10 6.5";
    String ClassSvg="M 2 1.75 C 2 0.784 2.784 0 3.75 0 h 6.586 c 0.464 0 0.909 0.184 1.237 0.513 l 2.914 2.914 c 0.329 0.328 0.513 0.773 0.513 1.237 v 9.586 A 1.75 1.75 0 0 1 13.25 16 h -9.5 A 1.75 1.75 0 0 1 2 14.25 Z m 1.75 -0.25 a 0.25 0.25 0 0 0 -0.25 0.25 v 12.5 c 0 0.138 0.112 0.25 0.25 0.25 h 9.5 a 0.25 0.25 0 0 0 0.25 -0.25 V 6 h -2.75 A 1.75 1.75 0 0 1 9 4.25 V 1.5 Z m 6.75 0.062 V 4.25 c 0 0.138 0.112 0.25 0.25 0.25 h 2.688 l -0.011 -0.013 l -2.914 -2.914 l -0.013 -0.011 Z";

    public void initialize(String FilePath) {
        System.out.println("test "+RegularExpression.IsClass("static class kdijoid{	"));
    	File file = new File(FilePath);
        JAX jax = new JAX(file);
        LineLabel.setText("The Analysis Of "+file.getName());
        String RootSvg = "M 16.44 15.38 C 16.79 14.84 17 14.19 17 13.5 c 0 -1.93 -1.57 -3.5 -3.5 -3.5 S 10 11.57 10 13.5 s 1.57 3.5 3.5 3.5 c 0.69 0 1.34 -0.21 1.88 -0.56 L 17.94 19 L 19 17.94 L 16.44 15.38 Z M 13.5 15.5 c -1.1 0 -2 -0.9 -2 -2 s 0.9 -2 2 -2 s 2 0.9 2 2 S 14.6 15.5 13.5 15.5 Z M 17.78 2 L 19 2.87 l -3.88 5.9 h 0 C 14.61 8.59 14.07 8.5 13.5 8.5 L 17.78 2 Z M 13.5 8.5 c -0.58 0 -1.13 0.1 -1.65 0.28 l 0 0 l -0.78 -1.1 l -3.41 5.36 l -2.48 -2.97 l -2.96 4.81 L 1 14 l 4 -6.5 l 2.5 3 L 11 5 L 13.5 8.5 Z";
        TreeItem<TreeItemData> rootItem = new TreeItem<>(new TreeItemData(" Java Analyzer ",RootSvg));
        treeView.setRoot(rootItem);
         TreeItemData ParentLineData = new TreeItemData("Total Number Of jax.line "+jax.line.totalLine,"M 2 17 h 2 v 0.5 H 3 v 1 h 1 v 0.5 H 2 v 1 h 3 v -4 H 2 v 1 Z m 1 -9 h 1 V 4 H 2 v 1 h 1 v 3 Z m -1 3 h 1.8 L 2 13.1 v 0.9 h 3 v -1 H 3.2 L 5 10.9 V 10 H 2 v 1 Z m 5 -6 v 2 h 14 V 5 H 7 Z m 0 14 h 14 v -2 H 7 v 2 Z m 0 -6 h 14 v -2 H 7 v 2 Z");
        TreeItem<TreeItemData> ParentLineItem = new TreeItem<>(ParentLineData);
        String StatSvgPath="M 9 17 H 7 v -7 h 2 v 7 Z m 4 0 h -2 V 7 h 2 v 10 Z m 4 0 h -2 v -4 h 2 v 4 Z m 2.5 2.1 h -15 V 5 h 15 v 14.1 Z m 0 -16.1 h -15 c -1.1 0 -2 0.9 -2 2 v 14 c 0 1.1 0.9 2 2 2 h 15 c 1.1 0 2 -0.9 2 -2 V 5 c 0 -1.1 -0.9 -2 -2 -2 Z";
        String RatioSvgPath="M 11 5.08 V 2 C 6 2.5 2 6.81 2 12 s 4 9.5 9 10 v -3.08 c -3 -0.48 -6 -3.4 -6 -6.92 S 8 5.56 11 5.08 Z M 18.97 11 H 22 c -0.47 -5 -4 -8.53 -9 -9 v 3.08 C 16 5.51 18.54 8 18.97 11 Z M 13 18.92 V 22 c 5 -0.47 8.53 -4 9 -9 h -3.03 C 18.54 16 16 18.49 13 18.92 Z";
        String CodeSvgPath="m 11.28 3.22 l 4.25 4.25 a 0.75 0.75 0 0 1 0 1.06 l -4.25 4.25 a 0.749 0.749 0 0 1 -1.275 -0.326 a 0.749 0.749 0 0 1 0.215 -0.734 L 13.94 8 l -3.72 -3.72 a 0.749 0.749 0 0 1 0.326 -1.275 a 0.749 0.749 0 0 1 0.734 0.215 Z m -6.56 0 a 0.751 0.751 0 0 1 1.042 0.018 a 0.751 0.751 0 0 1 0.018 1.042 L 2.06 8 l 3.72 3.72 a 0.749 0.749 0 0 1 -0.326 1.275 a 0.749 0.749 0 0 1 -0.734 -0.215 L 0.47 8.53 a 0.75 0.75 0 0 1 0 -1.06 Z";
        String BlankSvgPath="M 19 5 v 14 H 5 V 5 h 14 m 0 -2 H 5 c -1.1 0 -2 0.9 -2 2 v 14 c 0 1.1 0.9 2 2 2 h 14 c 1.1 0 2 -0.9 2 -2 V 5 c 0 -1.1 -0.9 -2 -2 -2 Z";
        String CurlyBracesSvgPath="M 4 6.25 v 1.5 C 4 8.16 3.66 8.5 3.25 8.5 H 2 v 3 h 1.25 C 3.66 11.5 4 11.84 4 12.25 v 1.5 C 4 14.99 5.01 16 6.25 16 H 8 v -1.5 H 6.25 c -0.41 0 -0.75 -0.34 -0.75 -0.75 v -1.5 c 0 -1.16 -0.88 -2.11 -2 -2.24 V 9.99 c 1.12 -0.12 2 -1.08 2 -2.24 v -1.5 c 0 -0.41 0.34 -0.75 0.75 -0.75 H 8 V 4 H 6.25 C 5.01 4 4 5.01 4 6.25 Z M 16.75 8.5 C 16.34 8.5 16 8.16 16 7.75 v -1.5 C 16 5.01 14.99 4 13.75 4 H 12 v 1.5 h 1.75 c 0.41 0 0.75 0.34 0.75 0.75 v 1.5 c 0 1.16 0.88 2.11 2 2.24 v 0.03 c -1.12 0.12 -2 1.08 -2 2.24 v 1.5 c 0 0.41 -0.34 0.75 -0.75 0.75 H 12 V 16 h 1.75 c 1.24 0 2.25 -1.01 2.25 -2.25 v -1.5 c 0 -0.41 0.34 -0.75 0.75 -0.75 H 18 v -3 H 16.75 Z";
        String CommentSvgPath="M 20 2 H 4 c -1.1 0 -1.99 0.9 -1.99 2 L 2 22 l 4 -4 h 14 c 1.1 0 2 -0.9 2 -2 V 4 c 0 -1.1 -0.9 -2 -2 -2 Z M 9 11 H 7 V 9 h 2 v 2 Z m 4 0 h -2 V 9 h 2 v 2 Z m 4 0 h -2 V 9 h 2 v 2 Z";
        TreeItem<TreeItemData> CodeStat = new TreeItem<>(new TreeItemData("Code Statistique ",StatSvgPath));
        TreeItem<TreeItemData> CommentStat = new TreeItem<>(new TreeItemData("Comment Statistique ",StatSvgPath));
        CodeStat.getChildren().add(new TreeItem<>(new TreeItemData("Code Ratio "+jax.line.ratioCode,RatioSvgPath)));
        CodeStat.getChildren().add(new TreeItem<>(new TreeItemData("line Of Code "+jax.line.totalCode,CodeSvgPath)));
        CodeStat.getChildren().add(new TreeItem<>(new TreeItemData("Empty line "+jax.line.totalEmpty,BlankSvgPath)));
        CodeStat.getChildren().add(new TreeItem<>(new TreeItemData("Curly Braces jax.line "+jax.line.totalBraces,CurlyBracesSvgPath)));     
        
        CommentStat.getChildren().add(new TreeItem<>(new TreeItemData("Comment Ratio "+jax.line.ratioComment,RatioSvgPath)));
        CommentStat.getChildren().add(new TreeItem<>(new TreeItemData("jax.line Of Comment Only "+jax.line.totalComment,CommentSvgPath)));
        ParentLineItem.getChildren().add(CodeStat);
        ParentLineItem.getChildren().add(CommentStat);
        
        
        String ParentSoftwareSvg="M 19 3 h -4.18 C 14.4 1.84 13.3 1 12 1 S 9.6 1.84 9.18 3 H 5 C 4.86 3 4.73 3.01 4.6 3.04 C 4.21 3.12 3.86 3.32 3.59 3.59 c -0.18 0.18 -0.33 0.4 -0.43 0.64 C 3.06 4.46 3 4.72 3 5 v 14 c 0 0.27 0.06 0.54 0.16 0.78 c 0.1 0.24 0.25 0.45 0.43 0.64 c 0.27 0.27 0.62 0.47 1.01 0.55 C 4.73 20.99 4.86 21 5 21 h 14 c 1.1 0 2 -0.9 2 -2 V 5 C 21 3.9 20.1 3 19 3 Z M 11 14.17 l -1.41 1.42 L 6 12 l 3.59 -3.59 L 11 9.83 L 8.83 12 L 11 14.17 Z M 12 4.25 c -0.41 0 -0.75 -0.34 -0.75 -0.75 S 11.59 2.75 12 2.75 s 0.75 0.34 0.75 0.75 S 12.41 4.25 12 4.25 Z M 14.41 15.59 L 13 14.17 L 15.17 12 L 13 9.83 l 1.41 -1.42 L 18 12 L 14.41 15.59 Z";
        TreeItem<TreeItemData> SoftwareParent = new TreeItem<>(new TreeItemData("Software ",ParentSoftwareSvg));
        
         for(classType Class : jax.soft.ClassList) {
        	 TreeItem<TreeItemData> ClassesItem = createTreeItem(Class);
        	 SoftwareParent.getChildren().add(ClassesItem);
         }
        
         
         
       
        
        String PerformanceSvg="M 20.38 8.57 l -1.23 1.85 a 8 8 0 0 1 -0.22 7.58 H 5.07 A 8 8 0 0 1 15.58 6.85 l 1.85 -1.23 A 10 10 0 0 0 3.35 19 a 2 2 0 0 0 1.72 1 h 13.85 a 2 2 0 0 0 1.74 -1 a 10 10 0 0 0 -0.27 -10.44 Z m -9.79 6.84 a 2 2 0 0 0 2.83 0 l 5.66 -8.49 l -8.49 5.66 a 2 2 0 0 0 0 2.83 Z";
        String RunTimeSvg="M 11.99 2 C 6.47 2 2 6.48 2 12 s 4.47 10 9.99 10 C 17.52 22 22 17.52 22 12 S 17.52 2 11.99 2 Z M 12 20 c -4.42 0 -8 -3.58 -8 -8 s 3.58 -8 8 -8 s 8 3.58 8 8 s -3.58 8 -8 8 Z M 12.5 7 H 11 v 6 l 5.25 3.15 l 0.75 -1.23 l -4.5 -2.67 Z";
        String SizeSvg="M 21 6 H 3 c -1.1 0 -2 0.9 -2 2 v 8 c 0 1.1 0.9 2 2 2 h 18 c 1.1 0 2 -0.9 2 -2 V 8 c 0 -1.1 -0.9 -2 -2 -2 Z m 0 10 H 3 V 8 h 2 v 4 h 2 V 8 h 2 v 4 h 2 V 8 h 2 v 4 h 2 V 8 h 2 v 4 h 2 V 8 h 2 v 8 Z";
        //String ComplexitySvg="M 16 6 l 2.29 2.29 l -4.88 4.88 l -4 -4 L 2 16.59 L 3.41 18 l 6 -6 l 4 4 l 6.3 -6.29 L 22 12 V 6 Z";
         XMLResult.JAX_XML(jax.line, jax.perf);
        String formattedRuntime = String.format("%.5f",jax.perf.RunTime);
        //Complexity compl = new Complexity(Complexity.FetchComplexity(file));
        TreeItem<TreeItemData> PerformanceParent = new TreeItem<>(new TreeItemData("Performance",PerformanceSvg));
        PerformanceParent.getChildren().add(new TreeItem<>(new TreeItemData("RuntTime In Seconds "+formattedRuntime,RunTimeSvg)));
        PerformanceParent.getChildren().add(new TreeItem<>(new TreeItemData("FileSize In Byte "+jax.perf.FileSize,SizeSvg)));
       // PerformanceParent.getChildren().add(new TreeItem<>(new TreeItemData(compl.labelCom+" Complexity",ComplexitySvg)));
        
        
        rootItem.getChildren().add(ParentLineItem);
        rootItem.getChildren().add(SoftwareParent);
        rootItem.getChildren().add(PerformanceParent);
        setTreeViewStyle();
    }

    private <T>void fillList(Collection<T>list,TreeItem<TreeItemData>Parent,String svg) {
    	if(list.isEmpty()) {
    	   TreeItem<TreeItemData> Item = new TreeItem<>(new TreeItemData("None",NoneSvg));
           Parent.getChildren().add(Item);   		
    	}
    	else {
    	for(T element : list) {
    	TreeItem<TreeItemData> Item = new TreeItem<>(new TreeItemData(element.toString(),svg));
        Parent.getChildren().add(Item);   	
    	}
    	}
    }
    
    private TreeItem<TreeItemData> createTreeItem(classType Class) {
        TreeItem<TreeItemData> classItem = new TreeItem<>(new TreeItemData(Class.ClassName,"M 3 3 v 8 h 8 V 3 H 3 Z m 6 6 H 5 V 5 h 4 v 4 Z m -6 4 v 8 h 8 v -8 H 3 Z m 6 6 H 5 v -4 h 4 v 4 Z m 4 -16 v 8 h 8 V 3 h -8 Z m 6 6 h -4 V 5 h 4 v 4 Z m -6 4 v 8 h 8 v -8 h -8 Z m 6 6 h -4 v -4 h 4 v 4 Z"));
        TreeItem<TreeItemData> methodParentItem = new TreeItem<>(new TreeItemData("Methods",""));
        TreeItem<TreeItemData> interfaceParentItem = new TreeItem<>(new TreeItemData("Interfaces",""));
        TreeItem<TreeItemData> parentTreeParentItem = new TreeItem<>(new TreeItemData("Parent Tree",""));
        
        fillList(Class.MethodList, methodParentItem,MethodSvg);
        fillList(Class.InterfaceList, interfaceParentItem,InterfaceSvg);
        fillList(Class.ParentTree, parentTreeParentItem,ParentSvg);
        
        classItem.getChildren().add(methodParentItem);
        classItem.getChildren().add(interfaceParentItem);
        classItem.getChildren().add(parentTreeParentItem);
        
        
        for (classType subClass : Class.InnerClass) {
            TreeItem<TreeItemData> subClassItem = createTreeItem(subClass);
            classItem.getChildren().add(subClassItem);
        }
        
        return classItem;
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




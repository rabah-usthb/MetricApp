package application.FrontEnd;

import java.io.File;

import application.BackEnd.SwingComponent;
import application.BackEnd.XMLResult;
import application.FrontEnd.EncapsulationController.CustomTreeCell;
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

public class SwingComponentController {

    @FXML
    private TreeView<TreeItemData> treeView;
    @FXML
    private Label SwingLabel;

    public void initialize(String FilePath) {
        File file = new File(FilePath);
        SwingLabel.setText("Swing Component of "+file.getName());
        SwingComponent swing =   SwingComponent.FetchSwingComponentNumber(file);
        XMLResult.SM_XML(swing);
        String TotalSvgPath="M 18 4 H 6 v 2 l 6.5 6 L 6 18 v 2 h 12 v -3 h -7 l 5 -5 l -5 -5 h 7 Z";
        TreeItemData rootItemData = new TreeItemData("Total Swing Component : "+swing.TotalComponent,TotalSvgPath);
        TreeItem<TreeItemData> rootItem = new TreeItem<>(rootItemData);

        String TreeSvg="M 22 11 V 3 h -7 v 3 H 9 V 3 H 2 v 8 h 7 V 8 h 2 v 10 h 4 v 3 h 7 v -8 h -7 v 3 h -2 V 8 h 2 v 3 Z";
        String ButtonSvg="M 12 2 C 6.48 2 2 6.48 2 12 s 4.48 10 10 10 s 10 -4.48 10 -10 S 17.52 2 12 2 Z m -2 14.5 v -9 l 6 4.5 l -6 4.5 Z";
        String ListSvg="M 4 10.5 c -0.83 0 -1.5 0.67 -1.5 1.5 s 0.67 1.5 1.5 1.5 s 1.5 -0.67 1.5 -1.5 s -0.67 -1.5 -1.5 -1.5 Z m 0 -6 c -0.83 0 -1.5 0.67 -1.5 1.5 S 3.17 7.5 4 7.5 S 5.5 6.83 5.5 6 S 4.83 4.5 4 4.5 Z m 0 12 c -0.83 0 -1.5 0.68 -1.5 1.5 s 0.68 1.5 1.5 1.5 s 1.5 -0.68 1.5 -1.5 s -0.67 -1.5 -1.5 -1.5 Z M 7 19 h 14 v -2 H 7 v 2 Z m 0 -6 h 14 v -2 H 7 v 2 Z m 0 -8 v 2 h 14 V 5 H 7 Z";
        String FrameSvg="M 3 5 v 4 h 2 V 5 h 4 V 3 H 5 c -1.1 0 -2 0.9 -2 2 Z m 2 10 H 3 v 4 c 0 1.1 0.9 2 2 2 h 4 v -2 H 5 v -4 Z m 14 4 h -4 v 2 h 4 c 1.1 0 2 -0.9 2 -2 v -4 h -2 v 4 Z m 0 -16 h -4 v 2 h 4 v 4 h 2 V 5 c 0 -1.1 -0.9 -2 -2 -2 Z";
        String TableSvg="M 10 10.02 h 5 V 21 h -5 Z M 17 21 h 3 c 1.1 0 2 -0.9 2 -2 v -9 h -5 v 11 Z m 3 -18 H 5 c -1.1 0 -2 0.9 -2 2 v 3 h 19 V 5 c 0 -1.1 -0.9 -2 -2 -2 Z M 3 19 c 0 1.1 0.9 2 2 2 h 3 V 10 H 3 v 9 Z";
        String FileChoserSvg="M 16.5 6 v 11.5 c 0 2.21 -1.79 4 -4 4 s -4 -1.79 -4 -4 V 5 c 0 -1.38 1.12 -2.5 2.5 -2.5 s 2.5 1.12 2.5 2.5 v 10.5 c 0 0.55 -0.45 1 -1 1 s -1 -0.45 -1 -1 V 6 H 10 v 9.5 c 0 1.38 1.12 2.5 2.5 2.5 s 2.5 -1.12 2.5 -2.5 V 5 c 0 -2.21 -1.79 -4 -4 -4 S 7 2.79 7 5 v 12.5 c 0 3.04 2.46 5.5 5.5 5.5 s 5.5 -2.46 5.5 -5.5 V 6 h -1.5 Z";
        String PasswordFieldSvg="M 2 17 h 20 v 2 H 2 V 17 Z M 3.15 12.95 L 4 11.47 l 0.85 1.48 l 1.3 -0.75 L 5.3 10.72 H 7 v -1.5 H 5.3 l 0.85 -1.47 L 4.85 7 L 4 8.47 L 3.15 7 l -1.3 0.75 L 2.7 9.22 H 1 v 1.5 h 1.7 L 1.85 12.2 L 3.15 12.95 Z M 9.85 12.2 l 1.3 0.75 L 12 11.47 l 0.85 1.48 l 1.3 -0.75 l -0.85 -1.48 H 15 v -1.5 h -1.7 l 0.85 -1.47 L 12.85 7 L 12 8.47 L 11.15 7 l -1.3 0.75 l 0.85 1.47 H 9 v 1.5 h 1.7 L 9.85 12.2 Z M 23 9.22 h -1.7 l 0.85 -1.47 L 20.85 7 L 20 8.47 L 19.15 7 l -1.3 0.75 l 0.85 1.47 H 17 v 1.5 h 1.7 l -0.85 1.48 l 1.3 0.75 L 20 11.47 l 0.85 1.48 l 1.3 -0.75 l -0.85 -1.48 H 23 V 9.22 Z";
        String ColorChoserSvg="M 10 2 c -4.41 0 -8 3.59 -8 8 s 3.59 8 8 8 c 1.1 0 2 -0.9 2 -2 c 0 -0.49 -0.18 -0.96 -0.51 -1.34 c -0.06 -0.08 -0.1 -0.17 -0.1 -0.26 c 0 -0.22 0.18 -0.4 0.4 -0.4 h 1.42 c 2.65 0 4.8 -2.15 4.8 -4.8 C 18 5.23 14.41 2 10 2 Z M 5.5 10.75 c -0.69 0 -1.25 -0.56 -1.25 -1.25 c 0 -0.69 0.56 -1.25 1.25 -1.25 S 6.75 8.81 6.75 9.5 C 6.75 10.19 6.19 10.75 5.5 10.75 Z M 8 7.75 c -0.69 0 -1.25 -0.56 -1.25 -1.25 c 0 -0.69 0.56 -1.25 1.25 -1.25 S 9.25 5.81 9.25 6.5 C 9.25 7.19 8.69 7.75 8 7.75 Z M 12 7.75 c -0.69 0 -1.25 -0.56 -1.25 -1.25 c 0 -0.69 0.56 -1.25 1.25 -1.25 s 1.25 0.56 1.25 1.25 C 13.25 7.19 12.69 7.75 12 7.75 Z M 14.5 10.75 c -0.69 0 -1.25 -0.56 -1.25 -1.25 c 0 -0.69 0.56 -1.25 1.25 -1.25 s 1.25 0.56 1.25 1.25 C 15.75 10.19 15.19 10.75 14.5 10.75 Z";
        String InternalFrameSvg="M 20 4 h -4 l -4 -4 l -4 4 H 4 c -1.1 0 -2 0.9 -2 2 v 14 c 0 1.1 0.9 2 2 2 h 16 c 1.1 0 2 -0.9 2 -2 V 6 c 0 -1.1 -0.9 -2 -2 -2 Z m 0 16 H 4 V 6 h 4.52 l 3.52 -3.5 L 15.52 6 H 20 v 14 Z M 18 8 H 6 v 10 h 12";
        String CheckBoxSvg="M 19 3 H 5 c -1.11 0 -2 0.9 -2 2 v 14 c 0 1.1 0.89 2 2 2 h 14 c 1.11 0 2 -0.9 2 -2 V 5 c 0 -1.1 -0.89 -2 -2 -2 Z m -9 14 l -5 -5 l 1.41 -1.41 L 10 14.17 l 7.59 -7.59 L 19 8 l -9 9 Z";
        String RadioButtonSvg="M 12 7 c -2.76 0 -5 2.24 -5 5 s 2.24 5 5 5 s 5 -2.24 5 -5 s -2.24 -5 -5 -5 Z m 0 -5 C 6.48 2 2 6.48 2 12 s 4.48 10 10 10 s 10 -4.48 10 -10 S 17.52 2 12 2 Z m 0 18 c -4.42 0 -8 -3.58 -8 -8 s 3.58 -8 8 -8 s 8 3.58 8 8 s -3.58 8 -8 8 Z";
        String EditorPaneSvg="M 3 17.25 V 21 h 3.75 L 17.81 9.94 l -3.75 -3.75 L 3 17.25 Z M 20.71 7.04 c 0.39 -0.39 0.39 -1.02 0 -1.41 l -2.34 -2.34 c -0.39 -0.39 -1.02 -0.39 -1.41 0 l -1.83 1.83 l 3.75 3.75 l 1.83 -1.83 Z";
        String LayeredPaneSvg="M 11.99 18.54 l -7.37 -5.73 L 3 14.07 l 9 7 l 9 -7 l -1.63 -1.27 l -7.38 5.74 Z M 12 16 l 7.36 -5.73 L 21 9 l -9 -7 l -9 7 l 1.63 1.27 L 12 16 Z";
        String ScrollPaneSvg="M 12 5.83 L 15.17 9 l 1.41 -1.41 L 12 3 L 7.41 7.59 L 8.83 9 L 12 5.83 Z m 0 12.34 L 8.83 15 l -1.41 1.41 L 12 21 l 4.59 -4.59 L 15.17 15 L 12 18.17 Z";
        String MenuSvg="M 19 4 H 5 c -1.11 0 -2 0.9 -2 2 v 12 c 0 1.1 0.89 2 2 2 h 14 c 1.1 0 2 -0.9 2 -2 V 6 c 0 -1.1 -0.89 -2 -2 -2 Z m 0 14 H 5 V 8 h 14 v 10 Z";
        String SeparatorSvg="M 11 5 h 2 v 14 h -2 V 5 Z M 5 12 c 1.1 0 2 -0.9 2 -2 c 0 -1.1 -0.9 -2 -2 -2 s -2 0.9 -2 2 C 3 11.1 3.9 12 5 12 Z M 7.78 13.58 C 6.93 13.21 5.99 13 5 13 s -1.93 0.21 -2.78 0.58 C 1.48 13.9 1 14.62 1 15.43 L 1 16 h 8 l 0 -0.57 C 9 14.62 8.52 13.9 7.78 13.58 Z M 19 12 c 1.1 0 2 -0.9 2 -2 c 0 -1.1 -0.9 -2 -2 -2 s -2 0.9 -2 2 C 17 11.1 17.9 12 19 12 Z M 21.78 13.58 C 20.93 13.21 19.99 13 19 13 s -1.93 0.21 -2.78 0.58 C 15.48 13.9 15 14.62 15 15.43 L 15 16 h 8 l 0 -0.57 C 23 14.62 22.52 13.9 21.78 13.58 Z";
        String SplitPaneSvg="M 18 4 v 5 H 6 V 4 H 18 M 18 2 H 6 C 4.9 2 4 2.9 4 4 v 5 c 0 1.1 0.9 2 2 2 h 12 c 1.1 0 2 -0.9 2 -2 V 4 C 20 2.9 19.1 2 18 2 Z M 18 15 v 5 H 6 v -5 H 18 M 18 13 H 6 c -1.1 0 -2 0.9 -2 2 v 5 c 0 1.1 0.9 2 2 2 h 12 c 1.1 0 2 -0.9 2 -2 v -5 C 20 13.9 19.1 13 18 13 Z"; 
        String TextPaneSvg="M 23 7 V 1 h -6 v 2 H 7 V 1 H 1 v 6 h 2 v 10 H 1 v 6 h 6 v -2 h 10 v 2 h 6 v -6 h -2 V 7 h 2 Z M 3 3 h 2 v 2 H 3 V 3 Z m 2 18 H 3 v -2 h 2 v 2 Z m 12 -2 H 7 v -2 H 5 V 7 h 2 V 5 h 10 v 2 h 2 v 10 h -2 v 2 Z m 4 2 h -2 v -2 h 2 v 2 Z M 19 5 V 3 h 2 v 2 h -2 Z m -5.27 9 h -3.49 l -0.73 2 H 7.89 l 3.4 -9 h 1.4 l 3.41 9 h -1.63 l -0.74 -2 Z m -3.04 -1.26 h 2.61 L 12 8.91 l -1.31 3.83 Z";
        String TextAreaSvg="M 19 3 H 5 c -1.1 0 -2 0.9 -2 2 v 14 c 0 1.1 0.9 2 2 2 h 14 c 1.1 0 2 -0.9 2 -2 V 5 c 0 -1.1 -0.9 -2 -2 -2 Z m -5 14 H 7 v -2 h 7 v 2 Z m 3 -4 H 7 v -2 h 10 v 2 Z m 0 -4 H 7 V 7 h 10 v 2 Z";
        String LabelSvg="M 21 11 h -1.5 v -0.5 h -2 v 3 h 2 V 13 H 21 v 1 c 0 0.55 -0.45 1 -1 1 h -3 c -0.55 0 -1 -0.45 -1 -1 v -4 c 0 -0.55 0.45 -1 1 -1 h 3 c 0.55 0 1 0.45 1 1 V 11 Z M 8 10 v 5 H 6.5 v -1.5 h -2 V 15 H 3 v -5 c 0 -0.55 0.45 -1 1 -1 h 3 C 7.55 9 8 9.45 8 10 Z M 6.5 10.5 h -2 V 12 h 2 V 10.5 Z M 13.5 12 c 0.55 0 1 0.45 1 1 v 1 c 0 0.55 -0.45 1 -1 1 h -4 V 9 h 4 c 0.55 0 1 0.45 1 1 v 1 C 14.5 11.55 14.05 12 13.5 12 Z M 11 10.5 v 0.75 h 2 V 10.5 H 11 Z M 13 12.75 h -2 v 0.75 h 2 V 12.75 Z";
        String DialogueSvg="M 20 2 H 4 c -1.1 0 -1.99 0.9 -1.99 2 L 2 22 l 4 -4 h 14 c 1.1 0 2 -0.9 2 -2 V 4 c 0 -1.1 -0.9 -2 -2 -2 Z M 9 11 H 7 V 9 h 2 v 2 Z m 4 0 h -2 V 9 h 2 v 2 Z m 4 0 h -2 V 9 h 2 v 2 Z";
        String PanelSvg="M 10.75 10 H 17 v 5.5 c 0 0.83 -0.67 1.5 -1.5 1.5 h -4.75 V 10 Z M 4.5 3 C 3.67 3 3 3.67 3 4.5 v 10.94 C 3 16.3 3.7 17 4.56 17 h 4.69 V 3 H 4.5 Z M 17 8.5 v -4 C 17 3.67 16.33 3 15.5 3 h -4.75 v 5.5 H 17 Z";
        String TabSvg="M 21 3 H 3 c -1.1 0 -2 0.9 -2 2 v 14 c 0 1.1 0.9 2 2 2 h 18 c 1.1 0 2 -0.9 2 -2 V 5 c 0 -1.1 -0.9 -2 -2 -2 Z m 0 16 H 3 V 5 h 10 v 4 h 8 v 10 Z";
        String SliderSvg="M 3 17 v 2 h 6 v -2 H 3 Z M 3 5 v 2 h 10 V 5 H 3 Z m 10 16 v -2 h 8 v -2 h -8 v -2 h -2 v 6 h 2 Z M 7 9 v 2 H 3 v 2 h 4 v 2 h 2 V 9 H 7 Z m 14 4 v -2 H 11 v 2 h 10 Z m -6 -4 h 2 V 7 h 4 V 5 h -4 V 3 h -2 v 6 Z";
        String ToolTipSvg="M 9 21 c 0 0.5 0.4 1 1 1 h 4 c 0.6 0 1 -0.5 1 -1 v -1 H 9 v 1 Z m 3 -19 C 8.1 2 5 5.1 5 9 c 0 2.4 1.2 4.5 3 5.7 V 17 c 0 0.5 0.4 1 1 1 h 6 c 0.6 0 1 -0.5 1 -1 v -2.3 c 1.8 -1.3 3 -3.4 3 -5.7 c 0 -3.9 -3.1 -7 -7 -7 Z";
        String ToolBarSvg="M 22.7 19 l -9.1 -9.1 c 0.9 -2.3 0.4 -5 -1.5 -6.9 c -2 -2 -5 -2.4 -7.4 -1.3 L 9 6 L 6 9 L 1.6 4.7 C 0.4 7.1 0.9 10.1 2.9 12.1 c 1.9 1.9 4.6 2.4 6.9 1.5 l 9.1 9.1 c 0.4 0.4 1 0.4 1.4 0 l 2.3 -2.3 c 0.5 -0.4 0.5 -1.1 0.1 -1.4 Z";
        String SpinnerSvg="M 10 2 c -4.42 0 -8 3.58 -8 8 s 3.58 8 8 8 s 8 -3.58 8 -8 S 14.42 2 10 2 Z M 10 12.6 L 6.63 9.23 l 1.06 -1.06 L 10 10.48 l 2.31 -2.31 l 1.06 1.06 L 10 12.6 Z";
        String ProgressBarSvg="M 6 10 c -1.1 0 -2 0.9 -2 2 s 0.9 2 2 2 s 2 -0.9 2 -2 s -0.9 -2 -2 -2 Z m 12 0 c -1.1 0 -2 0.9 -2 2 s 0.9 2 2 2 s 2 -0.9 2 -2 s -0.9 -2 -2 -2 Z m -6 0 c -1.1 0 -2 0.9 -2 2 s 0.9 2 2 2 s 2 -0.9 2 -2 s -0.9 -2 -2 -2 Z";
        String TextFieldSvg="M 5 17 v 2 h 14 v -2 H 5 Z m 4.5 -4.2 h 5 l 0.9 2.2 h 2.1 L 12.75 4 h -1.5 L 6.5 15 h 2.1 l 0.9 -2.2 Z M 12 5.98 L 13.87 11 h -3.74 L 12 5.98 Z";
        String ComboBoxSvg="M 19.14 12.94 c 0.04 -0.3 0.06 -0.61 0.06 -0.94 c 0 -0.32 -0.02 -0.64 -0.07 -0.94 l 2.03 -1.58 c 0.18 -0.14 0.23 -0.41 0.12 -0.61 l -1.92 -3.32 c -0.12 -0.22 -0.37 -0.29 -0.59 -0.22 l -2.39 0.96 c -0.5 -0.38 -1.03 -0.7 -1.62 -0.94 L 14.4 2.81 c -0.04 -0.24 -0.24 -0.41 -0.48 -0.41 h -3.84 c -0.24 0 -0.43 0.17 -0.47 0.41 L 9.25 5.35 C 8.66 5.59 8.12 5.92 7.63 6.29 L 5.24 5.33 c -0.22 -0.08 -0.47 0 -0.59 0.22 L 2.74 8.87 C 2.62 9.08 2.66 9.34 2.86 9.48 l 2.03 1.58 C 4.84 11.36 4.8 11.69 4.8 12 s 0.02 0.64 0.07 0.94 l -2.03 1.58 c -0.18 0.14 -0.23 0.41 -0.12 0.61 l 1.92 3.32 c 0.12 0.22 0.37 0.29 0.59 0.22 l 2.39 -0.96 c 0.5 0.38 1.03 0.7 1.62 0.94 l 0.36 2.54 c 0.05 0.24 0.24 0.41 0.48 0.41 h 3.84 c 0.24 0 0.44 -0.17 0.47 -0.41 l 0.36 -2.54 c 0.59 -0.24 1.13 -0.56 1.62 -0.94 l 2.39 0.96 c 0.22 0.08 0.47 0 0.59 -0.22 l 1.92 -3.32 c 0.12 -0.22 0.07 -0.47 -0.12 -0.61 L 19.14 12.94 Z M 12 15.6 c -1.98 0 -3.6 -1.62 -3.6 -3.6 s 1.62 -3.6 3.6 -3.6 s 3.6 1.62 3.6 3.6 S 13.98 15.6 12 15.6 Z";
        
        treeView.setRoot(rootItem);
        String ContainerSvg="M 19 5 v 14 H 5 V 5 h 14 m 0 -2 H 5 c -1.1 0 -2 0.9 -2 2 v 14 c 0 1.1 0.9 2 2 2 h 14 c 1.1 0 2 -0.9 2 -2 V 5 c 0 -1.1 -0.9 -2 -2 -2 Z";
        String InputSvg="M 20 5 H 4 c -1.1 0 -1.99 0.9 -1.99 2 L 2 17 c 0 1.1 0.9 2 2 2 h 16 c 1.1 0 2 -0.9 2 -2 V 7 c 0 -1.1 -0.9 -2 -2 -2 Z m -9 3 h 2 v 2 h -2 V 8 Z m 0 3 h 2 v 2 h -2 v -2 Z M 8 8 h 2 v 2 H 8 V 8 Z m 0 3 h 2 v 2 H 8 v -2 Z m -1 2 H 5 v -2 h 2 v 2 Z m 0 -3 H 5 V 8 h 2 v 2 Z m 9 7 H 8 v -2 h 8 v 2 Z m 0 -4 h -2 v -2 h 2 v 2 Z m 0 -3 h -2 V 8 h 2 v 2 Z m 3 3 h -2 v -2 h 2 v 2 Z m 0 -3 h -2 V 8 h 2 v 2 Z";
        String ActionSvg="M 11.71 17.99 C 8.53 17.84 6 15.22 6 12 c 0 -3.31 2.69 -6 6 -6 c 3.22 0 5.84 2.53 5.99 5.71 l -2.1 -0.63 C 15.48 9.31 13.89 8 12 8 c -2.21 0 -4 1.79 -4 4 c 0 1.89 1.31 3.48 3.08 3.89 L 11.71 17.99 Z M 22 12 c 0 0.3 -0.01 0.6 -0.04 0.9 l -1.97 -0.59 C 20 12.21 20 12.1 20 12 c 0 -4.42 -3.58 -8 -8 -8 s -8 3.58 -8 8 s 3.58 8 8 8 c 0.1 0 0.21 0 0.31 -0.01 l 0.59 1.97 C 12.6 21.99 12.3 22 12 22 C 6.48 22 2 17.52 2 12 C 2 6.48 6.48 2 12 2 S 22 6.48 22 12 Z M 18.23 16.26 L 22 15 l -10 -3 l 3 10 l 1.26 -3.77 l 4.27 4.27 l 1.98 -1.98 L 18.23 16.26 Z";
        String DisplaySvg="M 20 3 H 4 c -1.1 0 -2 0.9 -2 2 v 11 c 0 1.1 0.9 2 2 2 h 3 l -1 1 v 2 h 12 v -2 l -1 -1 h 3 c 1.1 0 2 -0.9 2 -2 V 5 c 0 -1.1 -0.9 -2 -2 -2 Z m 0 13 H 4 V 5 h 16 v 11 Z";
        //String ActionSvg="M 19.14 12.94 c 0.04 -0.3 0.06 -0.61 0.06 -0.94 c 0 -0.32 -0.02 -0.64 -0.07 -0.94 l 2.03 -1.58 c 0.18 -0.14 0.23 -0.41 0.12 -0.61 l -1.92 -3.32 c -0.12 -0.22 -0.37 -0.29 -0.59 -0.22 l -2.39 0.96 c -0.5 -0.38 -1.03 -0.7 -1.62 -0.94 L 14.4 2.81 c -0.04 -0.24 -0.24 -0.41 -0.48 -0.41 h -3.84 c -0.24 0 -0.43 0.17 -0.47 0.41 L 9.25 5.35 C 8.66 5.59 8.12 5.92 7.63 6.29 L 5.24 5.33 c -0.22 -0.08 -0.47 0 -0.59 0.22 L 2.74 8.87 C 2.62 9.08 2.66 9.34 2.86 9.48 l 2.03 1.58 C 4.84 11.36 4.8 11.69 4.8 12 s 0.02 0.64 0.07 0.94 l -2.03 1.58 c -0.18 0.14 -0.23 0.41 -0.12 0.61 l 1.92 3.32 c 0.12 0.22 0.37 0.29 0.59 0.22 l 2.39 -0.96 c 0.5 0.38 1.03 0.7 1.62 0.94 l 0.36 2.54 c 0.05 0.24 0.24 0.41 0.48 0.41 h 3.84 c 0.24 0 0.44 -0.17 0.47 -0.41 l 0.36 -2.54 c 0.59 -0.24 1.13 -0.56 1.62 -0.94 l 2.39 0.96 c 0.22 0.08 0.47 0 0.59 -0.22 l 1.92 -3.32 c 0.12 -0.22 0.07 -0.47 -0.12 -0.61 L 19.14 12.94 Z M 12 15.6 c -1.98 0 -3.6 -1.62 -3.6 -3.6 s 1.62 -3.6 3.6 -3.6 s 3.6 1.62 3.6 3.6 S 13.98 15.6 12 15.6 Z";
        String AppletSvg="M 17.85 8.5 h -1.54 c -0.48 -2.03 -1.93 -3.68 -3.82 -4.48 V 4.5 C 12.5 5.33 11.83 6 11 6 H 9 v 1 c 0 0.55 -0.45 1 -1 1 H 7 v 2 h 1 v 2 H 7 L 3.64 8.64 C 3.55 9.08 3.5 9.53 3.5 10 c 0 3.58 2.92 6.5 6.5 6.5 V 18 c -4.42 0 -8 -3.58 -8 -8 s 3.58 -8 8 -8 C 13.91 2 17.15 4.8 17.85 8.5 Z M 18 16.44 l -1.06 1.06 l -2.56 -2.56 c -0.54 0.35 -1.19 0.56 -1.88 0.56 C 10.57 15.5 9 13.93 9 12 s 1.57 -3.5 3.5 -3.5 S 16 10.07 16 12 c 0 0.69 -0.21 1.34 -0.56 1.88 L 18 16.44 Z M 14.5 12 c 0 -1.1 -0.9 -2 -2 -2 s -2 0.9 -2 2 s 0.9 2 2 2 S 14.5 13.1 14.5 12 Z";
        
        TreeItem<TreeItemData> ContainersParent = new TreeItem<>(new TreeItemData("Containers",ContainerSvg));
        ContainersParent.getChildren().add(new TreeItem<>(new TreeItemData("JFrame "+swing.compteurJFrame,FrameSvg)));
        ContainersParent.getChildren().add(new TreeItem<>(new TreeItemData("JDialog "+swing.compteurJDialog,DialogueSvg)));
        ContainersParent.getChildren().add(new TreeItem<>(new TreeItemData("JApplet "+swing.compteurJApplet,AppletSvg)));
        ContainersParent.getChildren().add(new TreeItem<>(new TreeItemData("JPanel "+swing.compteurJPanel,PanelSvg)));
        ContainersParent.getChildren().add(new TreeItem<>(new TreeItemData("JLayeredPane "+swing.compteurJLayeredPane,LayeredPaneSvg)));    
        ContainersParent.getChildren().add(new TreeItem<>(new TreeItemData("JScrollPane "+swing.compteurJScrollPane,ScrollPaneSvg)));
        ContainersParent.getChildren().add(new TreeItem<>(new TreeItemData("JSplitPane "+swing.compteurJSplitPane,SplitPaneSvg)));
        ContainersParent.getChildren().add(new TreeItem<>(new TreeItemData("JTabbedPane "+swing.compteurJTabbedPane,TabSvg)));
        ContainersParent.getChildren().add(new TreeItem<>(new TreeItemData("JToolBar "+swing.compteurJToolBar,ToolBarSvg)));
        
        TreeItem<TreeItemData> InputParent = new TreeItem<>(new TreeItemData("Input Component",InputSvg));
        InputParent.getChildren().add(new TreeItem<>(new TreeItemData("JTextField "+swing.compteurJTextField,TextFieldSvg)));
        InputParent.getChildren().add(new TreeItem<>(new TreeItemData("JPasswordField "+swing.compteurJPasswordField,PasswordFieldSvg)));
        InputParent.getChildren().add(new TreeItem<>(new TreeItemData("JFileChoser "+swing.compteurJFileChooser,FileChoserSvg)));
        InputParent.getChildren().add(new TreeItem<>(new TreeItemData("JColorChoser "+swing.compteurJColorChooser,ColorChoserSvg)));
        InputParent.getChildren().add(new TreeItem<>(new TreeItemData("JEditorPane "+swing.compteurJEditorPane,EditorPaneSvg)));
        InputParent.getChildren().add(new TreeItem<>(new TreeItemData("JTextPane "+swing.compteurJTextPane,TextPaneSvg)));
        InputParent.getChildren().add(new TreeItem<>(new TreeItemData("JTextArea "+swing.compteurJTextArea,TextAreaSvg)));
        InputParent.getChildren().add(new TreeItem<>(new TreeItemData("JSpinner "+swing.compteurJSpinner,SpinnerSvg)));
        InputParent.getChildren().add(new TreeItem<>(new TreeItemData("JSlider "+swing.compteurJSlider,SliderSvg)));
          
        
        
        
        
        
        

        TreeItem<TreeItemData> DisplayParent = new TreeItem<>(new TreeItemData("Display Component",DisplaySvg));
        DisplayParent.getChildren().add(new TreeItem<>(new TreeItemData("JTree "+swing.compteurJTree,TreeSvg)));
        DisplayParent.getChildren().add(new TreeItem<>(new TreeItemData("JList "+swing.compteurJList,ListSvg)));
        DisplayParent.getChildren().add(new TreeItem<>(new TreeItemData("JTable "+swing.compteurJTable,TableSvg)));
        DisplayParent.getChildren().add(new TreeItem<>(new TreeItemData("JInternalFrame "+swing.compteurJInternalFrame,InternalFrameSvg)));
        
        DisplayParent.getChildren().add(new TreeItem<>(new TreeItemData("JSeparator "+swing.compteurJSeparator,SeparatorSvg)));
        DisplayParent.getChildren().add(new TreeItem<>(new TreeItemData("JLabel "+swing.compteurJLabel,LabelSvg)));        
        DisplayParent.getChildren().add(new TreeItem<>(new TreeItemData("JToolTip "+swing.compteurJToolTip,ToolTipSvg)));
        DisplayParent.getChildren().add(new TreeItem<>(new TreeItemData("JProgressBar "+swing.compteurJProgressBar,ProgressBarSvg)));
        
        
        
        TreeItem<TreeItemData> ActionParent = new TreeItem<>(new TreeItemData("Action Component",ActionSvg));
        ActionParent.getChildren().add(new TreeItem<>(new TreeItemData("JButton "+swing.compteurJButton,ButtonSvg)));
        ActionParent.getChildren().add(new TreeItem<>(new TreeItemData("JCheckBox "+swing.compteurJCheckBox,CheckBoxSvg)));
        ActionParent.getChildren().add(new TreeItem<>(new TreeItemData("JRadioButton "+swing.compteurJRadioButton,RadioButtonSvg)));
        ActionParent.getChildren().add(new TreeItem<>(new TreeItemData("JComboBox "+swing.compteurJComboBox,ComboBoxSvg)));
        ActionParent.getChildren().add(new TreeItem<>(new TreeItemData("JMenu "+swing.compteurJMenu,MenuSvg)));
        
        
        rootItem.getChildren().add(ContainersParent);
        rootItem.getChildren().add(InputParent);
        rootItem.getChildren().add(ActionParent);
        rootItem.getChildren().add(DisplayParent);
        
        
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




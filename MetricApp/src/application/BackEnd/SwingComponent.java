package application.BackEnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SwingComponent {
	   public  int TotalComponent=0;    
	   public  int compteurJInternalFrame = 0;
	   public  int compteurJComboBox = 0;
	   public  int compteurJCheckBox = 0;
	   public  int compteurJList = 0;
	   public  int compteurJButton = 0;
	   public  int compteurJMenu = 0;
	   public  int compteurJRadioButton = 0;
	   public  int compteurJSlider = 0;
	   public  int compteurJSpinner = 0;
	   public  int compteurJTextField = 0;
	   public  int compteurJPasswordField = 0;
	   public  int compteurJColorChooser = 0;
	   public  int compteurJEditorPane = 0;
	   public  int compteurJTextPane = 0;
	   public  int compteurJFileChooser = 0;
	   public  int compteurJTable = 0;
	   public  int compteurJLayeredPane = 0;
	   public  int compteurJTextArea = 0;
	   public  int compteurJTree = 0;
	   public  int compteurJLabel = 0;
	   public  int compteurJProgressBar = 0;
	   public  int compteurJSeparator = 0;
	   public  int compteurJToolTip = 0;
	   public  int compteurJApplet = 0;
	   public  int compteurJDialog = 0;
	   public  int compteurJFrame = 0;
	   public  int compteurJPanel = 0;
	   public  int compteurJScrollPane = 0;
	   public  int compteurJSplitPane = 0;
	   public  int compteurJTabbedPane = 0;
	   public  int compteurJToolBar = 0;
	     
	     public static void SwitchCase(SwingComponent swingComponent, String className) {
	    	    switch(className) {
	    	        case "JButton":
	    	            swingComponent.compteurJButton++;
	    	            break;
	    	        case "JToolBar":
	    	            swingComponent.compteurJToolBar++;
	    	            break;
	    	        case "JSplitPane":
	    	            swingComponent.compteurJSplitPane++;
	    	            break;
	    	        case "JInternalFrame":
	    	            swingComponent.compteurJInternalFrame++;
	    	            break;
	    	        case "JComboBox":
	    	            swingComponent.compteurJComboBox++;
	    	            break;
	    	        case "JCheckBox":
	    	            swingComponent.compteurJCheckBox++;
	    	            break;
	    	        case "JList":
	    	            swingComponent.compteurJList++;
	    	            break;
	    	        case "JMenuBar":
	    	            swingComponent.compteurJMenu++;
	    	            break;
	    	        case "JRadioButton":
	    	            swingComponent.compteurJRadioButton++;
	    	            break;
	    	        case "JSlider":
	    	            swingComponent.compteurJSlider++;
	    	            break;
	    	        case "JSpinner":
	    	            swingComponent.compteurJSpinner++;
	    	            break;
	    	        case "JTextField":
	    	            swingComponent.compteurJTextField++;
	    	            break;
	    	        case "JPasswordField":
	    	            swingComponent.compteurJPasswordField++;
	    	            break;
	    	        case "JColorChooser":
	    	            swingComponent.compteurJColorChooser++;
	    	            break;
	    	        case "JEditorPane":
	    	            swingComponent.compteurJEditorPane++;
	    	            break;
	    	        case "JTextPane":
	    	            swingComponent.compteurJTextPane++;
	    	            break;
	    	        case "JFileChooser":
	    	            swingComponent.compteurJFileChooser++;
	    	            break;
	    	        case "JTable":
	    	            swingComponent.compteurJTable++;
	    	            break;
	    	        case "JLayeredPane":
	    	            swingComponent.compteurJLayeredPane++;
	    	            break;
	    	        case "JTextArea":
	    	            swingComponent.compteurJTextArea++;
	    	            break;
	    	        case "JTree":
	    	            swingComponent.compteurJTree++;
	    	            break;
	    	        case "JLabel":
	    	            swingComponent.compteurJLabel++;
	    	            break;
	    	        case "JProgressBar":
	    	            swingComponent.compteurJProgressBar++;
	    	            break;
	    	        case "JSeparator":
	    	            swingComponent.compteurJSeparator++;
	    	            break;
	    	        case "JToolTip":
	    	            swingComponent.compteurJToolTip++;
	    	            break;
	    	        case "JApplet":
	    	            swingComponent.compteurJApplet++;
	    	            break;
	    	        case "JDialog":
	    	            swingComponent.compteurJDialog++;
	    	            break;
	    	        case "JFrame":
	    	            swingComponent.compteurJFrame++;
	    	            break;
	    	        case "JPanel":
	    	            swingComponent.compteurJPanel++;
	    	            break;
	    	        case "JScrollPane":
	    	            swingComponent.compteurJScrollPane++;
	    	            break;
	    	        case "JTabbedPane":
	    	            swingComponent.compteurJTabbedPane++;
	    	            break;
	    	    }
	    	    // Calculate total components
	    	    swingComponent.TotalComponent = swingComponent.compteurJButton +
	    	                                     swingComponent.compteurJToolBar +
	    	                                     swingComponent.compteurJSplitPane +
	    	                                     swingComponent.compteurJInternalFrame +
	    	                                     swingComponent.compteurJComboBox +
	    	                                     swingComponent.compteurJCheckBox +
	    	                                     swingComponent.compteurJList +
	    	                                     swingComponent.compteurJMenu +
	    	                                     swingComponent.compteurJRadioButton +
	    	                                     swingComponent.compteurJSlider +
	    	                                     swingComponent.compteurJSpinner +
	    	                                     swingComponent.compteurJTextField +
	    	                                     swingComponent.compteurJPasswordField +
	    	                                     swingComponent.compteurJColorChooser +
	    	                                     swingComponent.compteurJEditorPane +
	    	                                     swingComponent.compteurJTextPane +
	    	                                     swingComponent.compteurJFileChooser +
	    	                                     swingComponent.compteurJTable +
	    	                                     swingComponent.compteurJLayeredPane +
	    	                                     swingComponent.compteurJTextArea +
	    	                                     swingComponent.compteurJTree +
	    	                                     swingComponent.compteurJLabel +
	    	                                     swingComponent.compteurJProgressBar +
	    	                                     swingComponent.compteurJSeparator +
	    	                                     swingComponent.compteurJToolTip +
	    	                                     swingComponent.compteurJApplet +
	    	                                     swingComponent.compteurJDialog +
	    	                                     swingComponent.compteurJFrame +
	    	                                     swingComponent.compteurJPanel +
	    	                                     swingComponent.compteurJScrollPane +
	    	                                     swingComponent.compteurJTabbedPane;
	    	}

 
	     public static void UpdateSwingComponentFlag(SwingComponent swingComponent ,String line) {
	    	 if(RegularExpression.IsMethodPrototype(line)) {
	    		 ArrayList<String>ClassNameArgument = new ArrayList<>();
	    	    ClassNameArgument.addAll(RegularExpression.FetchMethodArgumentType(line));
	    	    ArrayList<String>ClassName = new ArrayList<>();
	    	    ClassName.addAll(ClassNameArgument);
	    	    if(!RegularExpression.IsConstructor(line)) {
	    	    String ReturnType = RegularExpression.FetchMethodReturnType(line);
	    	    ClassName.add(ReturnType);
	    	    }
	    	    
	    	    for(String Class : ClassName) {
	    	    	SwitchCase(swingComponent, Class);
	    	    }
	    	 }
	    	    else {
	    	    	if(RegularExpression.IsNew(line)) {
	    	    		ArrayList<String>newClassName = new ArrayList<>();
	    	    		newClassName.addAll(RegularExpression.ExtractNewClassNames(line));	
		    	    	for(String Class : newClassName) {
			    	    	SwitchCase(swingComponent, Class);
			    	    }
	    	    	}
	    	    	
	    	    	else if	( RegularExpression.IsVariable(line))  {
	    	   
	    	    		ArrayList<String>varClassName =new ArrayList<>();
		    	         varClassName.addAll(RegularExpression.ExtractVarClassNames(line));
		    	    	
		    	    	for(String Class : varClassName) {
			    	    	SwitchCase(swingComponent, Class);
			    	    }
	    	    	
	    	    	}
	    	    	
	    	    	
	    	    }
	    	 
	     }
	     
	    public static SwingComponent FetchSwingComponentNumber(File file) { 
	    	SwingComponent swingComponent = new SwingComponent();
			String Line;
			 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
		          while ((Line = reader.readLine() )!= null) {
		          	Line = Line.trim();
		          	Line = Qoute.RemoveQoute(Line);
		          	ArrayList<String> ListCode=new ArrayList<String>();
		          	if(!Line.isBlank() && !Line.isEmpty() && !Comment.IsCommentOnlyCompleted(Line) && ! RegularExpression.IsPackage(Line)) {
		          		//System.out.println(Line);
		          		if(Comment.ContainsComment(Line)) {
		  	            	//System.out.println(line);
		  	            		Line = Comment.RemoveComment(Line);
		  	            	}
		          		else {
		          			if(Comment.FinishedComment(Line)) {
			            			if(!Comment.ContainsOpeningComment(Line)) {
			            				ListCode.add(Comment.CodeOpeningComment(Line));
			            			}
			            			if(!Comment.ContainsClosingComment(Line)) {
			            				ListCode.add(Comment.CodeClosingComment(Line));
			            			}
			            		}
		          			else if (Comment.NotFinishedComment(Line)) {
			            			Comment.JumpComment(Line,ListCode,reader);
			            		}

		          			if(!ListCode.isEmpty()) {
		          				for(String code : ListCode) {
		          					if(RegularExpression.IsNew(code)||RegularExpression.IsVariable(code) || RegularExpression.IsMethodPrototype(code)) {
		          	            		System.out.println(code);
		          						UpdateSwingComponentFlag(swingComponent, code);
		          	            	}
		          					
		          				}
		          			}
		          		}
		          		if(ListCode.isEmpty()) {
		          			if(RegularExpression.IsNew(Line)||RegularExpression.IsVariable(Line) || RegularExpression.IsMethodPrototype(Line)) {
		  	            		System.out.println(Line);
		          				UpdateSwingComponentFlag(swingComponent, Line);
		  	            	}
		          			
		          		}
		          	}
		          }
		      } catch (IOException e) {
		          e.printStackTrace();
		      }
			
			return swingComponent;
	    }
}

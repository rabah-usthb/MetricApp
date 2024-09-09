package application.BackEnd;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class XMLResult {
final static String IC_Path = System.getProperty("user.dir")+"/src/Ressource/XML Folder/IC.xml";
final static String JEA_Path = System.getProperty("user.dir")+"/src/Ressource/XML Folder/JEA.xml";
final static String ER_Path = System.getProperty("user.dir")+"/src/Ressource/XML Folder/ER.xml";
final static String OOMR_Path = System.getProperty("user.dir")+"/src/Ressource/XML Folder/OOMR.xml";
final static String SM_Path = System.getProperty("user.dir")+"/src/Ressource/XML Folder/SM.xml";
final static String JAX_Path = System.getProperty("user.dir")+"/src/Ressource/XML Folder/JAX.xml";

public static void PrintMap(HashMap<String, ArrayList<Object>>map) {
	for(Map.Entry<String,ArrayList<Object>> entry : map.entrySet()) {
	      System.out.println("key " +entry.getKey());
	      System.out.println("value "+entry.getValue());
	}
}


public static int Nb_Element(HashMap<String, ArrayList<Object>>map) {
	for(Map.Entry<String,ArrayList<Object>> entry : map.entrySet()) {
	  return entry.getValue().size();    
	}
	return 0;
}

public static HashMap<String, ArrayList<Object>> buildMap(MapEntry...entries) {
    HashMap<String, ArrayList<Object>> map = new HashMap<>();

    for (MapEntry entry : entries) {
        map.put(entry.key, entry.values);
    }

    return map;
}


public static class ValueEntry {
   String Field;
   Object value;

    public ValueEntry(String Field, Object value) {
        this.Field = Field;
        this.value = value;
    }
}

public static class MapEntry {
    String key;
    ArrayList<Object> values = new ArrayList<>();

    public MapEntry(String key,Object...values) {
        this.key = key;
        Collections.addAll(this.values,values);
    }
    public MapEntry(String key, ArrayList<?> values) {
        this.key = key;
        this.values = new ArrayList<>(values);
    }
}


 static void setChildren(Document document,Element parentElement,String TagName ,HashMap<String,ArrayList<Object>>map) {
	   
	    int i = 0;
	    int Max=Nb_Element(map);
		int j=0;	
	   PrintMap(map);
		if(Max!=0) {
	    while(i<Max) {
	       Element childElement = document.createElement(TagName);  
                  
	       for(Map.Entry<String,ArrayList<Object>> entry : map.entrySet()) {
	           if(entry.getValue().size()!=0) {
	           childElement.setAttribute(entry.getKey(), entry.getValue().get(j).toString()); 
		       parentElement.appendChild(childElement);
	           }
		    }
		    j++;  
	       parentElement.appendChild(childElement);
		       
				i++;
	}
	    }    
	
 }

	static <T> Element setParent(Document document,Element rootElement,String TagName ,HashMap<String,ArrayList<Object>>map) {
	   Element parentElement = document.createElement(TagName);
	   if(map !=null) {
	 	  for(Map.Entry<String,ArrayList<Object>> entry : map.entrySet()) {
	 		parentElement.setAttribute(entry.getKey(),entry.getValue().get(0).toString());  
	 	  }
	   }
		  rootElement.appendChild(parentElement);
		   return parentElement;
	}
	
    static Element setRoot(Document document,String TagName ,HashMap<String,ArrayList<Object>>map) {
       Element rootElement = document.createElement(TagName);
      if(map !=null) {
    	  for(Map.Entry<String,ArrayList<Object>> entry : map.entrySet()) {
    		rootElement.setAttribute(entry.getKey(),entry.getValue().get(0).toString());  
    	  }
      }
  	  document.appendChild(rootElement);
  	   return rootElement;
   }
    
   static void Document_To_XML(Document document ,String XmlPath) {
	   TransformerFactory transformerFactory = TransformerFactory.newInstance();
       Transformer transformer = null;
	try {
		transformer = transformerFactory.newTransformer();
	} catch (TransformerConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       transformer.setOutputProperty(OutputKeys.INDENT, "yes");
       DOMSource source = new DOMSource(document);
       StreamResult result = new StreamResult(new File(XmlPath));
       try {
		transformer.transform(source, result);
	} catch (TransformerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}   
   }

    static Document Create_Document() {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = null;
	try {
		builder = factory.newDocumentBuilder();
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
	}
   	  return builder.newDocument(); 	  
    }

    @SuppressWarnings("unchecked")
	public static <T,R> ArrayList<R> filterByEqualAttribute(ArrayList<T> list,String returnAttribute,ValueEntry...entry) {
        ArrayList<R> filteredList = new ArrayList<>();
        boolean equal = true;
        try {
        	
        	for(T element : list) {
        		for(ValueEntry Entry : entry) {
        		    Field field = element.getClass().getDeclaredField(Entry.Field);
                    field.setAccessible(true);
                	Object fieldValue = field.get(element);
                	if(fieldValue.equals(Entry.value)) {
                		equal = true;
                	}else {
                		equal = false;
                		break;
                	}
                	
        		}
        		if(equal) {
        		 	Field Returnfield = element.getClass().getDeclaredField(returnAttribute);
                    Returnfield.setAccessible(true);
                    Object returnValue = Returnfield.get(element);
                    if (returnValue instanceof Collection<?>) {
                        filteredList.addAll((Collection<R>) returnValue);
                    } else {
                        filteredList.add((R) returnValue);
                    }
                    }
                              	
        		}
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filteredList;
    }


    @SuppressWarnings("unchecked")
	public static <T,R,V extends Number & Comparable<V>> ArrayList<R> filterByGreaterAttribute(ArrayList<T> list, String attributeName, V attributeValue,String returnAttribute) {
        ArrayList<R> filteredList = new ArrayList<>();
        try {
            for (T element : list) {
                Field field = element.getClass().getDeclaredField(attributeName);
                field.setAccessible(true);
                Object FieldValue = field.get(element);
                if(FieldValue instanceof Number) {
                Number number =(Number) FieldValue;
                if (number.doubleValue()>=attributeValue.doubleValue()) {
                	Field Returnfield = element.getClass().getDeclaredField(returnAttribute);
                    Returnfield.setAccessible(true);
                	filteredList.add((R)Returnfield.get(element));
                }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filteredList;
    }
    
    public static void FilterUsedImport_WildImport_SimpleImport(ArrayList<String>UsedImport,ArrayList<String>SimpleImport,ArrayList<String>WildImport) {
    	for(String Import : UsedImport) {
    		if(Import.contains("*")) {
    			WildImport.add(Import);
    		}
    		else {
    			SimpleImport.add(Import);
    		}
    	}
    }
    
    
    public static int totalNumberDuplicate (ArrayList<Integer>Duplicate) {
    	int cmp = 0;
    	for(Integer number : Duplicate) {
    		cmp=cmp+number;
    	}
    	return cmp;
    }
    public static void JAX_XML(Line line) {
    	Document document = Create_Document();   
  
    	Element JAX = setRoot(document,"JAX", null);
        Element ParentLine = setParent(document, JAX, "Line", buildMap(new MapEntry("total")));
    	/*Element Line total=##>
    	Element Code total=## , ratio=##.##>
    	Element CurlyBraces total=##/>
    	Element Empty total=##/>
    	Element Comment total=## , ratio=##.##>
    	Element Software total=##>
    	Element Methods total=##>
    	Element method prototype=”method 1”/>
    	Element method prototype=”method 2”/>
    	Element Interfaces total=##>
    	Element /interface name=”interface 1”/>
    	Element /interface name=”interface 2”/>
    	Element Classes total=##>
    	Element SubClasses total=##>
    	Element Class signature=”class 1 extends SuperClass 1”>
    	Element Class signature=”Protected class 2 extends SuperClass 2”>
    	Element /SubClasses>
    	Element AbstractClasses total=##>
    	Element Class signature=”Private Abstract class 3”>
    	Element Class signature=”Public Abstract class 4”>
    	Element /AbstractClasses>
    	Element /Classes>
    	Element /Software>
    	Element Performance>
    	Element RunTime value=##/>
    	Element FileSize value=##/>
    	Element /Performance>
    */
        Document_To_XML(document, JAX_Path);     
    
    }
    
    public static void SM_XML(SwingComponent sm) {
    	Document document = Create_Document();   
    	Element Swing = setRoot(document,"Swing", buildMap(new MapEntry("total",sm.TotalComponent)));
       
    	Element JInternalFrame = setParent(document, Swing,"JernalFrame", buildMap(new MapEntry("total", sm.compteurJInternalFrame)));    
    	Element JComboBox = setParent(document, Swing,"JComboBox", buildMap(new MapEntry("total", sm.compteurJComboBox)));    
    	Element JCheckBox = setParent(document, Swing,"JCheckBox", buildMap(new MapEntry("total", sm.compteurJCheckBox)));    
    	Element JList = setParent(document, Swing,"JList", buildMap(new MapEntry("total", sm.compteurJList)));    
    	Element JButton = setParent(document, Swing,"JButton", buildMap(new MapEntry("total", sm.compteurJButton)));    
    	Element JMenu = setParent(document, Swing,"JMenu", buildMap(new MapEntry("total", sm.compteurJMenu)));    
    	Element JRadioButton = setParent(document, Swing,"JRadioButton", buildMap(new MapEntry("total", sm.compteurJRadioButton)));    
    	Element JSlider = setParent(document, Swing,"JSlider", buildMap(new MapEntry("total", sm.compteurJSlider)));    
    	Element JSpinner = setParent(document, Swing,"JSpinner", buildMap(new MapEntry("total", sm.compteurJSpinner)));    
    	Element JTextField = setParent(document, Swing,"JTextField", buildMap(new MapEntry("total", sm.compteurJTextField)));    
    	Element JPasswordField = setParent(document, Swing,"JPasswordField", buildMap(new MapEntry("total", sm.compteurJPasswordField)));    
    	Element JColorChooser = setParent(document, Swing,"JColorChooser", buildMap(new MapEntry("total", sm.compteurJColorChooser)));    
    	Element JEditorPane = setParent(document, Swing,"JEditorPane", buildMap(new MapEntry("total", sm.compteurJEditorPane)));    
    	Element JTextPane = setParent(document, Swing,"JTextPane", buildMap(new MapEntry("total", sm.compteurJTextPane)));    
    	Element JFileChooser = setParent(document, Swing,"JFileChooser", buildMap(new MapEntry("total", sm.compteurJFileChooser)));    
    	Element JTable = setParent(document, Swing,"JTable", buildMap(new MapEntry("total", sm.compteurJTable)));    
    	Element JLayeredPane = setParent(document, Swing,"JLayeredPane", buildMap(new MapEntry("total", sm.compteurJLayeredPane)));    
    	Element JTextArea = setParent(document, Swing,"JTextArea", buildMap(new MapEntry("total", sm.compteurJTextArea)));    
    	Element JTree = setParent(document, Swing,"JTree", buildMap(new MapEntry("total", sm.compteurJTree)));    
    	Element JLabel = setParent(document, Swing,"JLabel", buildMap(new MapEntry("total", sm.compteurJLabel)));    
    	Element JProgressBar = setParent(document, Swing,"JProgressBar", buildMap(new MapEntry("total", sm.compteurJProgressBar)));    
    	Element JSeparator = setParent(document, Swing,"JSeparator", buildMap(new MapEntry("total", sm.compteurJSeparator)));    
    	Element JToolTip = setParent(document, Swing,"JToolTip", buildMap(new MapEntry("total", sm.compteurJToolTip)));    
    	Element JApplet = setParent(document, Swing,"JApplet", buildMap(new MapEntry("total", sm.compteurJApplet)));    
    	Element JDialog = setParent(document, Swing,"JDialog", buildMap(new MapEntry("total", sm.compteurJDialog)));    
    	Element JFrame = setParent(document, Swing,"JFrame", buildMap(new MapEntry("total", sm.compteurJFrame)));    
    	Element JPanel = setParent(document, Swing,"JPanel", buildMap(new MapEntry("total", sm.compteurJPanel)));    
    	Element JScrollPane = setParent(document, Swing,"JScrollPane", buildMap(new MapEntry("total", sm.compteurJScrollPane)));    
    	Element JSplitPane = setParent(document, Swing,"JSplitPane", buildMap(new MapEntry("total", sm.compteurJSplitPane)));    
    	Element JTabbedPane = setParent(document, Swing,"JTabbedPane", buildMap(new MapEntry("total", sm.compteurJTabbedPane)));    
    	Element JToolBar = setParent(document, Swing,"JToolBar", buildMap(new MapEntry("total", sm.compteurJToolBar)));    
    	
    	
        Document_To_XML(document, SM_Path);     
        
    }
    
    public static void OOMR_XML(OOMRCalculator oomr) {
    	Document document = Create_Document();   
    	
    	int totalMethod = oomr.totalMethods;
    	double oomRatio = oomr.oomr; 
    	Element Methods = setRoot(document,"Methods", buildMap(new MapEntry("total",totalMethod),new MapEntry("OOMR", oomRatio)));
       
    	int totalOverride = oomr.overrideMethods;
        double RatioOverride = oomr.RatioMethodsRedef;
        Element Override = setParent(document, Methods,"Override", buildMap(new MapEntry("total",totalOverride),new MapEntry("Ratio", RatioOverride)));
    
        int totalOverload = oomr.overloadedMethods;
        double RatioOverload = oomr.RatioMethodsSur;
        Element Overload = setParent(document, Methods,"Override", buildMap(new MapEntry("total",totalOverload),new MapEntry("Ratio", RatioOverload)));
    
        Document_To_XML(document, OOMR_Path);   
    }
    
    public static void ER_XML(Encapsulation ER) {
    	Document document = Create_Document();
    	Element elementRoot = setRoot(document,"Element", buildMap(new MapEntry("Total",ER.Total),new MapEntry("ER", ER.GetTaux()))); 
        Element publicElement = setParent(document,elementRoot,"Public", buildMap(new MapEntry("Total",ER.CompteurPublic))); 
        Element privateElement = setParent(document,elementRoot,"Private", buildMap(new MapEntry("Total",ER.CompteurPrivate))); 
        Element protectedElement = setParent(document,elementRoot,"Protected", buildMap(new MapEntry("Total",ER.CompteurProtected))); 
        Element noneElement = setParent(document,elementRoot,"None", buildMap(new MapEntry("Total",ER.CompteurNone))); 
        
        Document_To_XML(document, ER_Path);     
    }
    
    public static void JEA_XML(ArrayList<ExceptionStatus>ListException) {
    	Document document = Create_Document();
        int totalNumberException = ListException.size();
    	int totalNumberDefaultExc = ExceptionStatus.getTotalNumberDefaultException(ListException);
    	int totalNumberNotDefaultExc = ExceptionStatus.getTotalNumberNotDefaultException(ListException);
    	int totalNumberCompileTimeExc = ExceptionStatus.getTotalNumberCompileTimeException(ListException);
    	int totalNumberRunTimeExc = ExceptionStatus.getTotalNumberRunTimeException(ListException);
        
        Element Exception = setRoot(document,"Exceptions", buildMap(new MapEntry("total",totalNumberException) , new MapEntry("CompileTime", totalNumberCompileTimeExc),new MapEntry("RunTime", totalNumberRunTimeExc)));
        Element DefaultException = setParent(document, Exception,"DefaultExceptions", buildMap(new MapEntry("total",totalNumberDefaultExc)));
        ArrayList<String>DefaultRunTimeList= filterByEqualAttribute(ListException,"ExceptionName", new ValueEntry("DefaultStatus",0),new ValueEntry("CheckedStatus", 1));
        ArrayList<String>DefaultCompileTimeList = filterByEqualAttribute(ListException,"ExceptionName", new ValueEntry("DefaultStatus",0),new ValueEntry("CheckedStatus", 0));
        Element RunTimeDefault = setParent(document, DefaultException,"RunTime",null); 
        Element CompileTimeDefault = setParent(document, DefaultException,"CompileTime",null); 
        setChildren(document, CompileTimeDefault,"Exception", buildMap(new MapEntry("name",DefaultCompileTimeList)));
        setChildren(document,RunTimeDefault,"Exception", buildMap(new MapEntry("name",DefaultRunTimeList)));
        
        Element NotDefaultException =  setParent(document, Exception,"NotDefaultExceptions", buildMap(new MapEntry("total",totalNumberNotDefaultExc)));;
        ArrayList<String>NotDefaultRunTimeList= filterByEqualAttribute(ListException,"ExceptionName", new ValueEntry("DefaultStatus",1),new ValueEntry("CheckedStatus", 1));
        ArrayList<String>NotDefaultCompileTimeList = filterByEqualAttribute(ListException,"ExceptionName", new ValueEntry("DefaultStatus",1),new ValueEntry("CheckedStatus", 0));
        Element RunTimeNotDefault = setParent(document, NotDefaultException,"RunTime",null); 
        Element CompileTimeNotDefault = setParent(document, NotDefaultException,"CompileTime",null); 
        setChildren(document, CompileTimeNotDefault,"Exception", buildMap(new MapEntry("name",NotDefaultCompileTimeList)));
        setChildren(document,RunTimeNotDefault,"Exception", buildMap(new MapEntry("name",NotDefaultRunTimeList)));
        Document_To_XML(document, JEA_Path);
    }
    
    public static void IC_XML(ArrayList<ImportStatus> List) {
      int totalImport =ImportStatus.getTotalNumberImports(List);
      Document document = Create_Document();
      ArrayList<String>UsedImport = filterByEqualAttribute(List,"ImportName", new ValueEntry("ImportStatus",1));
      int totalUsedImport  = UsedImport.size();
      ArrayList<String>SimpleImport = new ArrayList<>();
      ArrayList<String>WildImport = new ArrayList<>();
      FilterUsedImport_WildImport_SimpleImport(UsedImport, SimpleImport, WildImport);
      Element Imports =setRoot(document,"Imports",
	  buildMap(new MapEntry("total" ,totalImport)));
	  // Cr eate a book element with an attribute
	  Element Used = setParent(document, Imports,"Used",  buildMap(new MapEntry("total",totalUsedImport)));
	  setChildren(document,Used,"Import", 
	 		  buildMap(new MapEntry("name", SimpleImport))
			  );
	  for(String Import : WildImport) {
		  ArrayList<String>UsedClass = filterByEqualAttribute(List,"UsedClassList",new ValueEntry("ImportName",Import));
		  
		  Element wildImport = setParent(document, Used,"Import", buildMap(new MapEntry("name",Import)));
	      setChildren(document, wildImport,"Class", buildMap(new MapEntry("name",UsedClass)));		      
	  }  
	  
	  ArrayList<String>ListNotUsd =  filterByEqualAttribute(List,"ImportName",new ValueEntry("ImportStatus",0));
	  int totalUnused=ListNotUsd.size();
	  Element NotUsed = setParent(document, Imports,"NotUsed", buildMap(new MapEntry("total",totalUnused)));
	  setChildren(document, NotUsed,"Import", 
	 
			  buildMap(
			  new MapEntry("name",ListNotUsd)
			  )
	  );
	  
	  ArrayList<Integer>NumberDuplicate = filterByGreaterAttribute(List,"DuplicatStatus",1,"DuplicatStatus");
      ArrayList<String>NameDuplicate =filterByGreaterAttribute(List,"DuplicatStatus",1,"ImportName");
      int totalDuplicate =totalNumberDuplicate(NumberDuplicate);
	  Element Duplicate = setParent(document, Imports,"Duplicate", buildMap(new MapEntry("total",totalDuplicate)));
      
	  setChildren(document,Duplicate,"Import", 
				 
			  buildMap(
			  new MapEntry("name",NameDuplicate),
			  new MapEntry("total",NumberDuplicate)
				 
					  )
	  ); 
	  ArrayList<String>ConflictList = filterByEqualAttribute(List,"ImportName",new ValueEntry("ConflictStatus",1));
	  int totalConflict = ConflictList.size();
	  Element Conflict = setParent(document, Imports,"Conflict", buildMap(new MapEntry("total",totalConflict)));
	  setChildren(document,Conflict,"Import", 
				 
			  buildMap(
			  new MapEntry("name",ConflictList)
			  )
	  ); 
	  
	  
	  Document_To_XML(document, IC_Path);
	
}
}

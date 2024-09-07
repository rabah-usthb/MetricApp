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
import java.util.HashMap;
import java.util.Map;

public class XMLResult {
final static String IC_Path = System.getProperty("user.dir")+"/src/Ressource/XML Folder/IC.xml";
final static String JEA_Path = System.getProperty("user.dir")+"/src/Ressource/XML Folder/JEA.xml";
final static String ER_Path = System.getProperty("user.dir")+"/src/Ressource/XML Folder/ER.xml";

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
    ArrayList<Object> values;

    public MapEntry(String key, ArrayList<?> values) {
        this.key = key;
        // Cast the list to ArrayList<Object>
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

    /*
    @SuppressWarnings("unchecked")
	public static <T,V,R> ArrayList<R> filterByEqualAttribute(ArrayList<T> list, String attributeName, V attributeValue,String returnAttribute) {
        ArrayList<R> filteredList = new ArrayList<>();
        try {
            for (T element : list) {
                Field field = element.getClass().getDeclaredField(attributeName);
                field.setAccessible(true);
                if (field.get(element).equals(attributeValue)) {
                	Field Returnfield = element.getClass().getDeclaredField(returnAttribute);
                    Returnfield.setAccessible(true);
                    Object returnValue = Returnfield.get(element);
                    if (returnValue instanceof Collection<?>) {
                        filteredList.addAll((Collection<R>) returnValue);
                    } else {
                        filteredList.add((R) returnValue);
                    }}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filteredList;
    }

*/
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
    
    public static void ER_XML(Encapsulation ER) {
    	Document document = Create_Document();
    	ArrayList<Integer>TotalElement = new ArrayList<>();
    	TotalElement.add(ER.Total);
    	ArrayList<Double>EncapsulationRatio = new ArrayList<>();
        EncapsulationRatio.add(ER.GetTaux());
        Element elementRoot = setRoot(document,"Element", buildMap(new MapEntry("Total",TotalElement),new MapEntry("ER", EncapsulationRatio))); 
        ArrayList<Integer>PublicElement = new ArrayList<>();
        PublicElement.add(ER.CompteurPublic);
        ArrayList<Integer>ProtectedElement = new ArrayList<>();
        ProtectedElement.add(ER.CompteurProtected);
        ArrayList<Integer>PrivateElement = new ArrayList<>();
        PrivateElement.add(ER.CompteurPrivate);
        ArrayList<Integer>NoneElement = new ArrayList<>();
        NoneElement.add(ER.CompteurNone);
        Element publicElement = setParent(document,elementRoot,"Public", buildMap(new MapEntry("Total",PublicElement))); 
        Element privateElement = setParent(document,elementRoot,"Private", buildMap(new MapEntry("Total",PrivateElement))); 
        Element protectedElement = setParent(document,elementRoot,"Protected", buildMap(new MapEntry("Total",ProtectedElement))); 
        Element noneElement = setParent(document,elementRoot,"None", buildMap(new MapEntry("Total",NoneElement))); 
        
        Document_To_XML(document, ER_Path);     
    }
    
    public static void JEA_XML(ArrayList<ExceptionStatus>ListException) {
    	Document document = Create_Document();
        int totalNumberException = ListException.size();
    	int totalNumberDefaultExc = ExceptionStatus.getTotalNumberDefaultException(ListException);
    	int totalNumberNotDefaultExc = ExceptionStatus.getTotalNumberNotDefaultException(ListException);
    	int totalNumberCompileTimeExc = ExceptionStatus.getTotalNumberCompileTimeException(ListException);
    	int totalNumberRunTimeExc = ExceptionStatus.getTotalNumberRunTimeException(ListException);
        	
        ArrayList<Integer>rootAttributeTotal = new ArrayList<>();
        rootAttributeTotal.add(totalNumberException);
        ArrayList<Integer>rootAttributeCompileTime = new ArrayList<>();
        rootAttributeCompileTime.add(totalNumberCompileTimeExc);
        ArrayList<Integer>rootAttributeRunTime = new ArrayList<>();
        rootAttributeRunTime.add(totalNumberRunTimeExc);
        ArrayList<Integer>parentAttributeDefault = new ArrayList<>();
        parentAttributeDefault.add(totalNumberDefaultExc);
        ArrayList<Integer>parentAttributeNotDefault = new ArrayList<>();
        parentAttributeNotDefault.add(totalNumberNotDefaultExc);
        
        Element Exception = setRoot(document,"Exceptions", buildMap(new MapEntry("total",rootAttributeTotal) , new MapEntry("CompileTime", rootAttributeCompileTime),new MapEntry("RunTime", rootAttributeRunTime)));
        Element DefaultException = setParent(document, Exception,"DefaultExceptions", buildMap(new MapEntry("total",parentAttributeDefault)));
        ArrayList<String>DefaultRunTimeList= filterByEqualAttribute(ListException,"ExceptionName", new ValueEntry("DefaultStatus",0),new ValueEntry("CheckedStatus", 1));
        ArrayList<String>DefaultCompileTimeList = filterByEqualAttribute(ListException,"ExceptionName", new ValueEntry("DefaultStatus",0),new ValueEntry("CheckedStatus", 0));
        System.out.println("DefaultRunTimeList "+DefaultRunTimeList);
        System.out.println("DefaultCompileTimeList "+DefaultCompileTimeList);
        Element RunTimeDefault = setParent(document, DefaultException,"RunTime",null); 
        Element CompileTimeDefault = setParent(document, DefaultException,"CompileTime",null); 
        setChildren(document, CompileTimeDefault,"Exception", buildMap(new MapEntry("name",DefaultCompileTimeList)));
        setChildren(document,RunTimeDefault,"Exception", buildMap(new MapEntry("name",DefaultRunTimeList)));
        
        Element NotDefaultException =  setParent(document, Exception,"NotDefaultExceptions", buildMap(new MapEntry("total",parentAttributeNotDefault)));;
        ArrayList<String>NotDefaultRunTimeList= filterByEqualAttribute(ListException,"ExceptionName", new ValueEntry("DefaultStatus",1),new ValueEntry("CheckedStatus", 1));
        ArrayList<String>NotDefaultCompileTimeList = filterByEqualAttribute(ListException,"ExceptionName", new ValueEntry("DefaultStatus",1),new ValueEntry("CheckedStatus", 0));
        Element RunTimeNotDefault = setParent(document, NotDefaultException,"RunTime",null); 
        Element CompileTimeNotDefault = setParent(document, NotDefaultException,"CompileTime",null); 
        setChildren(document, CompileTimeNotDefault,"Exception", buildMap(new MapEntry("name",NotDefaultCompileTimeList)));
        setChildren(document,RunTimeNotDefault,"Exception", buildMap(new MapEntry("name",NotDefaultRunTimeList)));
        Document_To_XML(document, JEA_Path);
    }
    
    public static void IC_XML(ArrayList<ImportStatus> List) {
      ArrayList<Integer>rootAttribute = new ArrayList<>();
      rootAttribute.add(ImportStatus.getTotalNumberImports(List));
	  Document document = Create_Document();
      ArrayList<String>UsedImport = filterByEqualAttribute(List,"ImportName", new ValueEntry("ImportStatus",1));
      ArrayList<String>SimpleImport = new ArrayList<>();
      ArrayList<String>WildImport = new ArrayList<>();
      FilterUsedImport_WildImport_SimpleImport(UsedImport, SimpleImport, WildImport);
      Element Imports =setRoot(document,"Imports",
	  buildMap(new MapEntry("total" ,rootAttribute)));
	  // Create a book element with an attribute
	  Element Used = setParent(document, Imports,"Used", null);
	  setChildren(document,Used,"Import", 
	 		  buildMap(new MapEntry("name", SimpleImport))
			  );
	  for(String Import : WildImport) {
		  ArrayList<String>UsedClass = filterByEqualAttribute(List,"UsedClassList",new ValueEntry("ImportName",Import));
		  ArrayList<String>ImportName = new ArrayList<>();
		  ImportName.add(Import);
		  Element wildImport = setParent(document, Used,"Import", buildMap(new MapEntry("name",ImportName)));
	      setChildren(document, wildImport,"Class", buildMap(new MapEntry("name",UsedClass)));		      
	  
	  
	  ArrayList<String>ListNotUsd =  filterByEqualAttribute(List,"ImportName",new ValueEntry("ImportStatus",0));
	  ArrayList<Integer>NotUsedTotal = new ArrayList<>();
	  NotUsedTotal.add(ListNotUsd.size());
	  Element NotUsed = setParent(document, Imports,"NotUsed", buildMap(new MapEntry("total",NotUsedTotal)));
	  setChildren(document, NotUsed,"Import", 
	 
			  buildMap(
			  new MapEntry("name",ListNotUsd)
			  )
	  );
	  
	  ArrayList<Integer>NumberDuplicate = filterByGreaterAttribute(List,"DuplicatStatus",1,"DuplicatStatus");
      ArrayList<String>NameDuplicate =filterByGreaterAttribute(List,"DuplicatStatus",1,"ImportName");
      ArrayList<Integer>TotalDuplicate = new ArrayList<>();
      TotalDuplicate.add(totalNumberDuplicate(NumberDuplicate));
	  Element Duplicate = setParent(document, Imports,"Duplicate", buildMap(new MapEntry("total",TotalDuplicate)));
      
	  setChildren(document,Duplicate,"Import", 
				 
			  buildMap(
			  new MapEntry("name",NameDuplicate),
			  new MapEntry("total",NumberDuplicate)
				 
					  )
	  ); 
	  ArrayList<String>ConflictList = filterByEqualAttribute(List,"ImportName",new ValueEntry("ConflictStatus",1));
	  ArrayList<Integer>ConflictTotal = new ArrayList<>();
	  ConflictTotal.add(ConflictList.size());
	  Element Conflict = setParent(document, Imports,"Conflict", buildMap(new MapEntry("total",ConflictTotal)));
	  setChildren(document,Conflict,"Import", 
				 
			  buildMap(
			  new MapEntry("name",ConflictList)
			  )
	  ); 
	  
	  
	  Document_To_XML(document, IC_Path);
	}
}
}

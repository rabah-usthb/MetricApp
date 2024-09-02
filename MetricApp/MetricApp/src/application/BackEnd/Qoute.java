package application.BackEnd;

import java.util.regex.Pattern;

public class Qoute {

	   
	   //Method That Detect Qoute In Line
	   static boolean IsQoute(String Line) {
			Line = Line.trim();
			return Line.contains("\"");
		}
	   
	   //Method To RemoveQoute From Line
	   static String RemoveQoute(String line) {
	   	String qoute;
	   	if(IsQoute(line)) {
	   	while(line.contains("\"")) {
	             
	   		int BI = line.indexOf("\"");
	   		 int BS = line.indexOf("\"",BI+1);
	   		  if (BS == -1) {
	   			  line = line.replace("\"", "");// Check if the closing quote was found
	   	            break; // Exit the loop if not found to avoid StringIndexOutOfBoundsException
	   	        }
	   		  qoute = line.substring(BI,BS+1);
	   		 line = line.replaceAll(Pattern.quote(qoute), "");
	   		
	   		
	   	}
	   	}
	   	return line;
	   }
}

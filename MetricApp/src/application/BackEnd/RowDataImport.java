package application.BackEnd;

import java.util.ArrayList;

public class RowDataImport {
 int nbUsed,nbNotUsed,nbConflict,nbDuplicate = 0;
 
  public RowDataImport(ArrayList<ImportStatus>list) {
	  for (ImportStatus Import : list) {
		  if(Import.ImportStatus == 1) {
			  ++this.nbUsed;
		  }
		  
		  if(Import.ImportStatus == 0 ) {
			  ++this.nbNotUsed;
		  }
		  
		  if(Import.DuplicatStatus!=0) {
			  this.nbDuplicate = nbDuplicate + Import.DuplicatStatus;
		  }
		  
		  if(Import.ConflictStatus == 1) {
			  ++this.nbConflict;
		  }
	  }
  }
}

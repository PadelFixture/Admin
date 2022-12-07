package wordCreator;

import java.util.List;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class main {
	@SuppressWarnings({"resource" })
	public static void main(String[] args) throws InvalidFormatException, IOException {
				

		XWPFDocument doc = new XWPFDocument(OPCPackage.open("pruebasDocumentos/input.docx"));
		for (XWPFParagraph p : doc.getParagraphs()) {
		    List<XWPFRun> runs = p.getRuns();
		    if (runs != null) {
		        for (XWPFRun r : runs) {
		            String text = r.getText(0);
		            if (text != null && text.contains("[persona]")) {
		                text = text.replace("[persona]", "Jhon");
		                r.setText(text, 0);
		            }
		        }
		    }
		}
		/*for (XWPFTable tbl : doc.getTables()) {
		   for (XWPFTableRow row : tbl.getRows()) {
		      for (XWPFTableCell cell : row.getTableCells()) {
		         for (XWPFParagraph p : cell.getParagraphs()) {
		            for (XWPFRun r : p.getRuns()) {
		              String text = r.getText(0);
		              if (text != null && text.contains("[Persona]")) {
			                text = text.replace("[Persona]", "Jhon");
			                r.setText(text, 0);
			            }
		            }
		         }
		      }
		   }
		}
	*/	
		doc.write(new FileOutputStream("pruebasDocumentos/output.docx"));
		System.out.println("Documento creado");

		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}

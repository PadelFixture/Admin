package wordCreator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class mainWordPdfConverter {

	public static void main(String[] args) throws InvalidFormatException, FileNotFoundException, IOException {
		 long startTime = System.currentTimeMillis();

	        
	            // 1) Load docx with POI XWPFDocument
	           
	            OPCPackage pkg = OPCPackage.open(new FileInputStream("outputs/output.docx"));
	       	 //XWPFDocument doc = new XWPFDocument(OPCPackage.open(path));
	       	 XWPFDocument doc = new XWPFDocument(pkg);
	            // 2) Convert POI XWPFDocument 2 PDF with iText
	            File outFile = new File( "outputs/DocxBig.pdf" );
	            outFile.getParentFile().mkdirs();

	            OutputStream out = new FileOutputStream( outFile );
	            PdfOptions options = PdfOptions.create().fontEncoding( "windows-1250" );
	            PdfConverter.getInstance().convert( doc, out, options );
	       
	        
	        System.out.println( "Generate DocxBig.pdf with " + ( System.currentTimeMillis() - startTime ) + " ms." );

	}

}

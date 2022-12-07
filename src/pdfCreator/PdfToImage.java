package pdfCreator;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;


public class PdfToImage {

	public static ByteArrayInputStream convertPDFInputStreamToPNG(InputStream input) throws Exception{

		ByteArrayOutputStream output = new ByteArrayOutputStream() ;
		
        try (final PDDocument document = PDDocument.load(input)){
            PDFRenderer pdfRenderer = new PDFRenderer(document);
           
                BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);
                ImageIOUtil.writeImage(bim, "png", output);
            
            document.close();
        } catch (Exception e){
            System.err.println("Exception while trying to create pdf document - " + e);
        }
        
        return new ByteArrayInputStream(output.toByteArray());
        
    }
	
	
	
	
	
	
}

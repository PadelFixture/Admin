package lib.data.json;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import wordCreator.utils;

public class ConviertePdfFromWordx {
	
	private final static Logger LOG = LoggerFactory.getLogger(ConviertePdfFromWordx.class);
	private final static String UPLOAD_DIRECTORY = utils.obtenerCarpetaServidor();
	private final static String jsonConVariables="{'codigoTrabajador':'ASC-123456','ciudadContrato':'Talca','nombreEmpresa':'cyber','rutEmpresa':'76428956-0','appPatTrabajador':'Caris','appMaternoTrabajador':'Roman','nombreTrabajador':'Mauricio Nicolas','rutSinDVTrabajador':'16855117','digitoVerificadorTrabajador':'7','rutCompletoTrabajador':'16855117-7','estadoCivil':'soltero','fechaNacimientoTrabajador':'26 de noviembre de 1987','nacionalidadTrabajador':'Chileno','direccionTrabajador':'14 de Diciembre','comuna':'Melipilla','cargoTrabajador':'Temporero','faenaContratacion':'marzo 2018','temporadaContratacion':'segunda Temporada','sueldoCPuntosTrabajador':'560.000','sueldoEnPalabrasTrabajador':'Quinientos sesenta mil','fechaInicio':'6 de junio','cuidadContrato':'Santiago'}";

	public static void main(String[] args) {		
		LOG.info("Ingresamos a generar pdf desde word");
//		ConviertePdfFromWordx cwoWord = new ConviertePdfFromWordx();
////		cwoWord.convertToPDF("contrato.docx", UPLOAD_DIRECTORY+File.separator+"test.pdf", jsonConVariables);
////		cwoWord.convertToPDFMap(new File("contrato.docx"), UPLOAD_DIRECTORY+File.separator+"test.pdf", generaMapDatos());
//		try {
//			cwoWord.test("contrato.pdf");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		String nombre = String.format("%010d", Integer.parseInt("123456"));
		String apellidoMat = String.format("%-10s", null);
		String apellidoPat = String.format("%-10s", "ABC");
		System.out.println("["+ nombre + apellidoPat + apellidoMat + "]");
		LOG.info("Finalizamos generacion de pdf desde word");
	}
	
	public void convertToPDF(String docPath, String pdfPath, String json) {
		LOG.info("Se inicia la generacion del documento");
        try {            
        	
        	 File initialFile = new File(docPath);
        	 InputStream doc = new FileInputStream(initialFile);
    
        	// input stream
            //InputStream doc = getClass().getClassLoader().getResourceAsStream(docPath);
            XWPFDocument docAux = new XWPFDocument(doc);
            
            File f = new File(pdfPath); 
            
            if (!f.exists()) {
            	 File outFile = new File(pdfPath);
         		 OutputStream out = new FileOutputStream(outFile);
                 PdfOptions options = PdfOptions.create().fontEncoding("iso-8859-15");
                 PdfConverter.getInstance().convert(docAux, out, options);
            }
           
        } catch (Exception ex) {
        	LOG.error("Ha ocurrido un error >> ",ex.getMessage());
        	ex.printStackTrace();
        }
        LOG.info("Se termina la generacion del documento");
    }
	
	public void convertToPDFMap(File docWord, String pdfPath, Map<String, String> datosDocumento) {
        try {
        	ByteArrayInputStream doc = new ByteArrayInputStream(FileUtils.readFileToByteArray(docWord));
            LOG.info("Se obtiene doc ");
            XWPFDocument docAux = reemplazarTextoMap(doc, datosDocumento);
            File outFile = new File(pdfPath);
    		OutputStream out = new FileOutputStream(outFile);
            PdfOptions options = PdfOptions.create().fontEncoding("windows-1250");
            PdfConverter.getInstance().convert(docAux, out, options);
            LOG.info("Se transfomra a PDF ");
        } catch (IOException ex) {
        	LOG.error(ex.getMessage());
        }
    }
	
	private static XWPFDocument reemplazarTexto(InputStream doc, String json){
		LOG.info("Se ingresa a modificar el texto del documento");
		XWPFDocument docx = new XWPFDocument();
		try{
			OPCPackage pkg = OPCPackage.open(doc);
			
			HashMap<String,String> result = new ObjectMapper().readValue(json, HashMap.class);
			for(String key : result.keySet()){
				LOG.info("Texto [{}] - valor [{}]",key, result.get(key));
			}
			
	        docx = new XWPFDocument(pkg);
	        for (XWPFParagraph p : docx.getParagraphs()) {
				List<XWPFRun> runs = p.getRuns();
				if (runs != null) {
					for (XWPFRun r : runs) {
						String text = r.getText(0);
						LOG.info("Texto {}",text);
						for(String key : result.keySet()){
							if (text != null && text.contains(key)) {
								if(result.get(key)!=null && !"".equalsIgnoreCase(result.get(key))){
									LOG.info("Texto [{}] - valor [{}]",key, result.get(key));
									text = text.replace(key, result.get(key));
									r.setText(text, 0);
								}else{
									text = text.replace(key, "Sin información");
									r.setText(text, 0);
								}
							}
						}
					}
				}
			}			
		}catch(Exception e){
			LOG.error(e.getMessage());
		}
		LOG.info("Se finaliza la modificacion del texto del documento");
        return docx;
    }
	
	private static XWPFDocument reemplazarTextoMap(InputStream doc, Map<String, String> datosDocumento){
		LOG.info("Se ingresa a modificar el texto del documento");
		XWPFDocument docx = new XWPFDocument();
		try{
			OPCPackage pkg = OPCPackage.open(doc);
			
			LOG.info("###########################################################");
			
	        docx = new XWPFDocument(pkg);
	        for (XWPFParagraph p : docx.getParagraphs()) {
				List<XWPFRun> runs = p.getRuns();
				if (runs != null) {
					for (XWPFRun r : runs) {
						String text = r.getText(0);
						LOG.info("Texto {}",text);
						for (String key : datosDocumento.keySet()) {
							if (text.contains(key) && datosDocumento.get(text)!=null) {
								LOG.info("Texto encontrado para reemplazar {}",text);
								text = text.replace(key, datosDocumento.get(text));
								r.setText(text, 0);
							}
						}
					}
				}
			}
//	        for (XWPFTable tbl : docx.getTables()) {
//				for (XWPFTableRow row : tbl.getRows()) {
//					for (XWPFTableCell cell : row.getTableCells()) {
//						for (XWPFParagraph p : cell.getParagraphs()) {
//							for (XWPFRun r : p.getRuns()) {
//								String text = r.getText(0);
//								for (int i = 0; i < pairKeyValue.length; i++) {
//									if (text != null && text.contains("[" + pairKeyValue[i][0] + "]")) {
//										text = text.replace("[" + pairKeyValue[i][0] + "]", pairKeyValue[i][1]);
//										r.setText(text, 0);
//									}
//								}
//							}
//						}
//					}
//				}
//			}
		}catch(Exception e){
			LOG.error(e.getMessage());
		}
		LOG.info("Se finaliza la modificacion del texto del documento");
        return docx;
    }
	
	private static Map<String, String> generaMapDatos() {
        Map<String, String> filledMap = new HashMap<>();
        filledMap.put("codigoTrabajador", "ASC-123456");
        filledMap.put("ciudadContrato", "Talca");
        filledMap.put("nombreEmpresa", "cyber");
        filledMap.put("rutEmpresa", "76428956-0");
        filledMap.put("appPatTrabajador", "Caris");
        filledMap.put("appMaternoTrabajador", "Roman");
        filledMap.put("nombreTrabajador", "Mauricio Nicolas");
        filledMap.put("rutCompletoTrabajador", "16855117-7");
        filledMap.put("estadoCivil", "soltero");
        filledMap.put("fechaNacimientoTrabajador", "26 de noviembre de 1987");
        filledMap.put("nacionalidadTrabajador", "Chileno");
        filledMap.put("direccionTrabajador", "14 de Diciembre");
        filledMap.put("comuna", "Melipilla");
        filledMap.put("cargoTrabajador", "Temporero");
        filledMap.put("faenaContratacion", "marzo 2018");
        filledMap.put("temporadaContratacion", "segunda Temporada");
        filledMap.put("sueldoCPuntosTrabajador", "560.000");
        filledMap.put("sueldoEnPalabrasTrabajador", "Quinientos sesenta mil");
        filledMap.put("fechaInicio", "6 de junio");
        filledMap.put("cuidadContrato", "Santiago");
 
        return filledMap;
    }
	
	public void test(String docPath) throws Exception {
		LOG.info("ingresamos a doc parser");
		URL url = new URL("https://api.docparser.com/v1/document/upload/ddwopajhytgj");
		URLConnection uc = url.openConnection();
		uc.setRequestProperty("api_key", "37b00141975631ac5b2f7fd009bf218c1a2d1330");
		String resp = "";
//		MultipartFile multipartFile = request.getFile("file");
//		Long size = multipartFile.getSize();
//		String contentType = multipartFile.getContentType();
//		InputStream stream = multipartFile.getInputStream();
		
		ByteArrayInputStream doc = new ByteArrayInputStream(FileUtils.readFileToByteArray(new File("Contrato.pdf")));
		byte[] bytes = IOUtils.toByteArray(doc);
		System.out.println(DatatypeConverter.printBase64Binary(bytes));
		uc.setRequestProperty("file_content", new String(DatatypeConverter.printBase64Binary(bytes)));
		InputStreamReader is = new InputStreamReader(uc.getInputStream());
		BufferedReader reader = new BufferedReader(is);
		for (String line; (line = reader.readLine()) != null;) {
			System.out.println(line);
			resp += line;
		}
	}

}

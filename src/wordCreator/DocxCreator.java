package wordCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import lib.classSW.DocFiniquito;
import lib.classSW.DocLiquidacion;
import lib.db.sw.DocumentsDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;

public class DocxCreator {

	private final static Logger LOG = LoggerFactory.getLogger(DocxCreator.class);

	XWPFDocument wordx;
	FileOutputStream out;
	FileInputStream in;

	public DocxCreator() {
		wordx = new XWPFDocument();// Constructor de la clase, solo instancia

	}

	@SuppressWarnings("resource")
	private String readDocx(String path) throws IOException {
		String content = "";

		File file = new File("DocumentoPrueba.docx");
		FileInputStream fis = new FileInputStream(file);
		InputStream introArch = fis;
		wordx = new XWPFDocument(introArch);
		XWPFWordExtractor xwpf_we = new XWPFWordExtractor(wordx);
		content = xwpf_we.getText();
		return content;

	}

	@SuppressWarnings({ "unused", "resource" })
	private String readDocx(FileInputStream fi) throws IOException {
		String content = "";

		// File file = new File("DocumentoPrueba.docx");
		// FileInputStream fis = new FileInputStream(file);
		InputStream introArch = fi;
		wordx = new XWPFDocument(introArch);
		XWPFWordExtractor xwpf_we = new XWPFWordExtractor(wordx);
		content = xwpf_we.getText();
		return content;
	}

	// imprimir por consola

	public void PrintDocument(String Path) throws IOException {
		String content = readDocx(Path);
		System.out.println(content);
	}

	public void modifyDocumentMasivo(String pathfinal, ArrayList<String> JSONS, FileInputStream template)
			throws Exception {
		// LOG.info("Iniciamos modificacion documento");
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");

		String filename = "" + ".docx";
		// modifyDoc(pathfinal);
		System.out.println(this.modifyDocxMasivo(pathfinal, filename, JSONS, template));
	}

	public void modifyDocumentWithFormat(String pathfinal, String JSONS, FileInputStream template) throws Exception {

		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
		String filename = "" + ".docx";
		this.modifyDocxWithFormat(pathfinal, filename, JSONS, template);

	}

	// funcion para modificar un documento de tipo .doc o .docx
	public String modifyDocument(String pathfinal, String JSON, FileInputStream template) throws Exception {
		LOG.info("Iniciamos modificacion documento");
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");

		String filename = "" + ".docx";
		modifyDoc(pathfinal);
		return this.modifyDocx(pathfinal, filename, JSON, template);

	}

	private void modifyDoc(String path) {
		System.out.println("modificando docx: " + path);
	}

	private CTRow generateTemplateRow(int templateRow, XWPFTable table) throws XmlException, IOException {

		// Obtenemos la fila que sera la fila Template con la cual crearemos las
		// demas
		XWPFTableRow rowTemplate = table.getRow(templateRow);

		// Obtenemos el template de la fila
		XWPFTableRow oldRow = rowTemplate;
		CTRow ctrow = null;
		ctrow = CTRow.Factory.parse(oldRow.getCtRow().newInputStream());

		return ctrow;

	}
	
	private void printListInTable(int templateLine,int startLine,XWPFTable table, filterSql datos) throws XmlException, IOException{
		
		 XWPFTable tablaConceptos = table;
			
		 // Obtenemos la fila que sera la fila Template con la cual crearemos las demas
		 //XWPFTableRow rowTemplate = tablaConceptos.getRow(templateLine);
				
		 //Copiamos los estilos de la fila
		 CTRow templateRow = generateTemplateRow(templateLine, tablaConceptos);
		 
		 // Crear Nueva Fila
		 XWPFTableRow newRow = new XWPFTableRow(templateRow, tablaConceptos);
		 
		 newRow.getCell(0).removeParagraph(0);
		 
		 XWPFParagraph paragraph1 = newRow.getCell(0).addParagraph();
		 setRun(paragraph1.createRun() , "Calibre" , 10, "000000" , datos.getCampo() , false, false);
		 
		 newRow.getCell(1).removeParagraph(0);
		 
		 if(datos.getValue() == null){
			 datos.setValue(" ");
		 } 
		 
		 
		 XWPFParagraph paragraph2 = newRow.getCell(1).addParagraph();
		 setRun(paragraph2.createRun() , "Calibre" , 10, "000000" , datos.getValue() , false, false);
		 
		 // agregar la nueva fila a la tabla
		 tablaConceptos.addRow(newRow, startLine);

		
	}
	
	private static void setRun (XWPFRun run , String fontFamily , int fontSize , String colorRGB , String text , boolean bold , boolean addBreak) {
        run.setFontFamily(fontFamily);
        run.setFontSize(fontSize);
        run.setColor(colorRGB);
        run.setText(text);
        run.setBold(bold);
        if (addBreak) run.addBreak();
    }
	
	
	private void printDetailInTable(int templateLine,int startLine,XWPFTable table, filterSql datos, String value) throws Exception {
		
		 XWPFTable tablaConceptos = table;
			
		 // Obtenemos la fila que sera la fila Template con la cual crearemos las demas
		 //XWPFTableRow rowTemplate = tablaConceptos.getRow(templateLine);
				
		 //Copiamos los estilos de la fila
		 CTRow templateRow = generateTemplateRow(templateLine, tablaConceptos);
		 
		 // Crear Nueva Fila
		 XWPFTableRow newRow = new XWPFTableRow(templateRow, tablaConceptos);
		 
		 newRow.getCell(0).removeParagraph(0);
		 XWPFParagraph paragraph1 = newRow.getCell(0).addParagraph();
		 setRun(paragraph1.createRun() , "Calibre" , 10, "000000" , value , false, false);
		 
		 newRow.getCell(1).removeParagraph(0);
		 XWPFParagraph paragraph2 = newRow.getCell(1).addParagraph();
		 setRun(paragraph2.createRun() , "Calibre" , 10, "000000" , datos.getCampo() , false, false);
		 
		 if(datos.getValue() == null){
			 datos.setValue(" ");
		 }
		 
		 
		 newRow.getCell(2).removeParagraph(0);
		 XWPFParagraph paragraph3 = newRow.getCell(2).addParagraph();
		 setRun(paragraph3.createRun() , "Calibre" , 10, "000000" ,  "".equals(datos.getValue()) ? "-" : datos.getValue() , false, false);
		 
		 // agregar la nueva fila a la tabla
		 tablaConceptos.addRow(newRow, startLine);
	}
	
	
	
	public String modifyDocumentWithTable(String pathSalida, String filename, String JSONs, FileInputStream Fi,
			List<Map<String, Object>> datos, ArrayList<filterSql> datosExtras) throws Exception {

		OPCPackage pkg = OPCPackage.open(Fi);

		XWPFDocument doc = new XWPFDocument(pkg);
		JsonStringParser j = new JsonStringParser();

		String[][] pairKeyValue = j.parser(JSONs.replaceAll("\"", "").replaceAll("\n", "").replaceAll("\r", "").trim());

		for (XWPFParagraph p : doc.getParagraphs()) {
			List<XWPFRun> runs = p.getRuns();
			String tex = p.getParagraphText();

			for (int i = 0; i < pairKeyValue.length; i++) {
				if (tex != null && tex.contains("$$" + pairKeyValue[i][0].trim() + "$$")) {
					tex = tex.replace("$$" + pairKeyValue[i][0].trim() + "$$",
							pairKeyValue[i][1].replaceAll("@", ":").trim());
				}
			}

			int a = 0;
			if (runs != null) {
				for (XWPFRun r : runs) {
					if (a == 0) {
						r.setText(tex, 0);
					} else {
						r.setText("", 0);
					}
					a++;
				}
			}
			tex = "";
		}

		// Obtener la tabla en el Documento Word
		List<XWPFTable> table = doc.getTables();

		// Lista de Haberes Imponibles
		ArrayList<filterSql> listHaberesImponibles = new ArrayList<>();
		// Lista de Haberes No Imponibles
		ArrayList<filterSql> listHaberesNoImponibles = new ArrayList<>();
		// Lista de Descuento
		ArrayList<filterSql> listDescuentos = new ArrayList<>();

		// FONASA
		String fonasa = null;
		// SEGURO SESANTIA
		String seguroCesantia = null;
		// TOPE IMPONIBLE
		String topeImponible = null;
		// CAJA
		String caja = null;
		//SALUD
		String saludMonto = null;
		
		//SALUD Porcentaje
		String saludNombre = null;
		//AFP
		String AFPNombre = null;

		// Totales Haberes Imponibles
		String totalHaberImponible = null;
		// Totales Haberes No Imponibles
		String totalHaberNoImponible = "0";
		//Total Descuento Legales
		String totalDescuentosLegales = null;
		//Total Haberes
		String totalHaberes = "0";
		// Totales Descuentos
		String totalDescuentos = null;
		// Total Liquido
		String totalLiquido = null;

		// Obtener Datos
		for (Map<String, Object> dato : datos) {

			int concepto = 0;
			if (dato.get("idConcepto") != null) {
				concepto = Integer.parseInt(dato.get("idConcepto").toString());
			}

			filterSql haber = new filterSql();
			String aux1 = "";
			String aux2 = "";

			if (dato.get("descripcion") != null) {
				aux1 = dato.get("descripcion").toString().replaceAll("\\\"", "");
			}

			if (dato.get("valor") != null) {
				aux2 = dato.get("valor").toString().replaceAll("\\\"", "");
			}
			
			haber.setCampo(aux1);
			haber.setValue(aux2);
			
			//Listados Haberes
			if (concepto >= 3 && concepto <= 9) {
				listHaberesImponibles.add(haber);
			}else if (concepto >= 31 && concepto <= 39 && concepto != 32) {
				listHaberesNoImponibles.add(haber);
			 }
			
			//Variables para la liquidacion
			if(concepto == 32 && aux1.contains("FONASA (")){
				 fonasa = aux2;
			}
			if(concepto == 32 && aux1.contains("CAJA (")){
				 caja = aux2;
			}
			if(concepto == 92 && aux1.contains("SEGURO SESANTIA")){
				seguroCesantia = aux2;
			}
			if(concepto == 0 && aux1.contains("TOPE IMPONIBLE")){
				 topeImponible = aux2;
			}
			
			//Totales
			if(concepto == 10 && aux1.contains("TOTAL HAB.")){
				 totalHaberImponible = aux2;
			}
			if(aux1.contains("TOTAL HABERES NO IMPONIBLES")){
				 totalHaberNoImponible = aux2;
			}
			if(concepto == 40 && aux1.contains("TOTAL DESCUENTOS IMP")){
				 totalDescuentosLegales = aux2;
			}
			if(concepto == 21 && aux1.contains("TOTAL HABERES")){
				 totalHaberes = aux2;
			}
			if(concepto == 51 && aux1.contains("TOTAL DESCUENTOS")){
				 totalDescuentos = aux2;
			}
			if(concepto == 100 && aux1.contains("TOTAL PAGO")){
				 totalLiquido = aux2;
			}
			
			
			if(concepto == 999){
				saludNombre = aux1;
			}
			if(concepto == 998){
				AFPNombre = aux1;
			}
			
			
		}
		
		if(fonasa != null && caja != null){
			saludMonto = GeneralUtility.formatStringNumberWithDotAndDecimal(Double.valueOf(fonasa.replace(".", ""))+Double.valueOf(caja.replace(".", ""))+"");
		}else{
			saludMonto = "0";
		}
		
		// Numero de Fila
		int tempateRowId = 3;
		int startLine = 4;
		System.out.println(startLine);
		
		//Imprimir Titulo
		filterSql vacio = new filterSql();
		vacio.setCampo("1. HABERES IMPONIBLES");
		vacio.setValue("TOTAL");
		printListInTable(2, startLine, table.get(0), vacio);
		startLine++;
		
		//Imprimir listado de Haberes Imponibles
		for (filterSql haberes : listHaberesImponibles) {
			printListInTable(tempateRowId, startLine, table.get(0), haberes);
			startLine++;
		}
		
		//Imprimir total
		filterSql totalHaberImponibleObject = new filterSql();
		totalHaberImponibleObject.setCampo("TOTAL HABERES IMPONIBLES");
		totalHaberImponibleObject.setValue(totalHaberImponible);
		printListInTable(tempateRowId, startLine, table.get(0), totalHaberImponibleObject);
		startLine++;
			
		//Imprimir Titulo
		vacio = new filterSql();
		vacio.setCampo("2. HABERES NO IMPONIBLES");
		vacio.setValue("TOTAL");
		printListInTable(2, startLine, table.get(0), vacio);
		startLine++;
		
		//Imprimir listado de Haberes No Imponibles
		for (filterSql haberesNoImponibles : listHaberesNoImponibles) {
			printListInTable(tempateRowId, startLine, table.get(0), haberesNoImponibles);
			startLine++;
		}
		
		//Imprimir total Haberes No Imponible
		filterSql totalHaberNoImponibleObject = new filterSql();
		totalHaberNoImponibleObject.setCampo("TOTAL HABERES NO IMPONIBLES");
		totalHaberNoImponibleObject.setValue(totalHaberNoImponible);
		printListInTable(tempateRowId, startLine, table.get(0), totalHaberNoImponibleObject);
		startLine++;
		
		//Imprimir Total haberes
		filterSql totalHaberesObject = new filterSql();
		totalHaberesObject.setCampo("TOTAL HABERES");
		totalHaberesObject.setValue(totalHaberes);
		printListInTable(tempateRowId, startLine, table.get(0), totalHaberesObject);
		startLine++;
		
		//Imprimir Tope Imponible
		filterSql topeImponibleObject = new filterSql();
		topeImponibleObject.setCampo("TOPE IMPONIBLE");
		topeImponibleObject.setValue(topeImponible);
		printListInTable(tempateRowId, startLine, table.get(0), topeImponibleObject);
		startLine++;
		
		//remover la fila template
		table.get(0).removeRow(2);
		table.get(0).removeRow(2);
		
		
		//Segunda Tabla Descuentos
		int templateRowTitle = 1;
		int templateRowSimple = 2;
		int templateRowDetail = 3;
		startLine = 4;
		
		//Imprimir Titulo
		 vacio = new filterSql();
		 vacio.setCampo("1. DESCUENTOS LEGALES");
		 vacio.setValue("TOTAL");
		 printListInTable(templateRowTitle, startLine, table.get(1), vacio);
		 startLine++;
		 
		//Imprimir Datos AFP
		 vacio = new filterSql();
		 vacio.setCampo(AFPNombre);
		 vacio.setValue("");
		 printDetailInTable(templateRowDetail, startLine, table.get(1), vacio, "Descuentos Previsional");
		 startLine++;
		 
		//Imprimir Datos Fonasa
		 vacio = new filterSql();
		 vacio.setCampo(saludNombre);
		 vacio.setValue(saludMonto);
		 printDetailInTable(templateRowDetail, startLine, table.get(1), vacio, "Descuentos Salud");
		 startLine++;
		 
		//Imprimir Datos Seguro Cesantia
		 vacio = new filterSql();
		 vacio.setCampo("Seguro Cesantia");
		 vacio.setValue(seguroCesantia);
		 printListInTable(templateRowSimple, startLine, table.get(1), vacio);
		 startLine++;
		  
		//Imprimir total Descuentos legales
		filterSql totalDescuentosLegalesObject = new filterSql();
		totalDescuentosLegalesObject.setCampo("TOTAL DESCUENTOS LEGALES");
		totalDescuentosLegalesObject.setValue(totalDescuentosLegales);
		printListInTable(templateRowSimple, startLine, table.get(1), totalDescuentosLegalesObject);
		startLine++;
		
		//Imprimir Titulo
		 vacio = new filterSql();
		 vacio.setCampo("2. OTROS DESCUENTOS");
		 vacio.setValue("TOTAL");
		 printListInTable(templateRowTitle, startLine, table.get(1), vacio);
		 startLine++;
		 
		//Imprimir listado de Otros Descuentos
		 for (filterSql descuentos : listDescuentos) {
			printListInTable(templateRowSimple, startLine, table.get(1), descuentos);
			startLine++;
		 }
		 
		//Imprimir total descuentos
		filterSql totalDescuentosObject = new filterSql();
		totalDescuentosObject.setCampo("TOTAL DESCUENTOS");
		totalDescuentosObject.setValue(totalDescuentos);
		printListInTable(templateRowSimple, startLine, table.get(1), totalDescuentosObject);
		startLine++;
		
		table.get(1).removeRow(1);
		table.get(1).removeRow(1);
		table.get(1).removeRow(1);
		 
		//Tabla Final de Pago de Liquidacion TODO:
		// Numero de Fila
		 int templateRowLiquido = 0;
		 int templateRowLiquidoText = 1;
		 startLine = 2;
				
		//Imprimir Titulo
		 vacio = new filterSql();
		 vacio.setCampo("LIQUIDO A PAGAR");
		 if(totalLiquido == null){
			 totalLiquido = "0";
		 }
		 vacio.setValue(totalLiquido);
		 printListInTable(templateRowLiquido, startLine, table.get(2), vacio);
		 startLine++;
		 
		 String cantidadConLetra;
		 try{
		  cantidadConLetra = GeneralUtility.cantidadConLetra(totalLiquido.replace(".", ""));
		 }
		 catch(Exception e){
		  cantidadConLetra = "0";
		 }
		 
		//Imprimir Titulo
		 vacio = new filterSql();
		 vacio.setCampo("SON: ");
		 vacio.setValue(cantidadConLetra);
		 printListInTable(templateRowLiquidoText, startLine, table.get(2), vacio);
		 startLine++;
		
		 table.get(2).removeRow(0);
		 table.get(2).removeRow(0);

		/* Constructor del path */
		String fileName = filename.split("@")[0];
		String outPath = pathSalida + fileName + ".docx";
		Path path = Paths.get(outPath);

		File f = new File(outPath);
		if (f.exists() && !f.isDirectory()) {
			System.out.println("Archivo ya existe");
		} else {
			Files.createFile(path);
			System.out.println("outPath " + outPath);
			doc.write(new FileOutputStream(outPath));
			System.out.println("Documento creado");
			doc.close();
		}

		Fi.close();
		
		return outPath;

	}
	
	@SuppressWarnings("deprecation")
	private static void setRun (XWPFRun run , String fontFamily , int fontSize , String colorRGB , String text , boolean bold , boolean addBreak, boolean rightText) {
		if (rightText == true){
        	run.getParagraph().setAlignment(ParagraphAlignment.RIGHT);	
        }
		run.setFontFamily(fontFamily);
        run.setFontSize(fontSize);
        run.setColor(colorRGB);
        run.setText(text);
        run.setBold(bold);
        CTPPr ppr = run.getParagraph().getCTP().getPPr();
        if (ppr == null) ppr = run.getParagraph().getCTP().addNewPPr();
        
//        CTSpacing spacing = ppr.isSetSpacing()? ppr.getSpacing() : ppr.addNewSpacing();
//        spacing.setAfter(BigInteger.valueOf(0));
//        spacing.setBefore(BigInteger.valueOf(0));
//       // spacing.setLineRule(STLineSpacingRule.AUTO);
//       // spacing.setLine(BigInteger.valueOf(240));
        if (addBreak) run.addBreak();
    }
	
	@SuppressWarnings("deprecation")
	private static void setRun (XWPFRun run , String fontFamily , String colorRGB , String text , boolean bold , boolean addBreak, boolean rightText) {
		if (rightText == true){
        	run.getParagraph().setAlignment(ParagraphAlignment.RIGHT);	
        }
		run.setFontFamily(fontFamily);
        //run.setFontSize(fontSize);
        run.setColor(colorRGB);
        run.setText(text);
        run.setBold(bold);
        CTPPr ppr = run.getParagraph().getCTP().getPPr();
        if (ppr == null) ppr = run.getParagraph().getCTP().addNewPPr();
        
//        CTSpacing spacing = ppr.isSetSpacing()? ppr.getSpacing() : ppr.addNewSpacing();
//        spacing.setAfter(BigInteger.valueOf(0));
//        spacing.setBefore(BigInteger.valueOf(0));
//       // spacing.setLineRule(STLineSpacingRule.AUTO);
//       // spacing.setLine(BigInteger.valueOf(240));
        if (addBreak) run.addBreak();
    }
	

	private String modifyDocx(String pathSalida, String filename, String JSONs, FileInputStream Fi) throws Exception {
		LOG.info("Iniciamos modificacion documentoX");
		Field pathField = FileInputStream.class.getDeclaredField("path");
		pathField.setAccessible(true);
		String pathTemplate = (String) pathField.get(Fi);
		System.out.println("path template: " + pathTemplate);

		// String ext= FilenameUtils.getExtension(pathfinal);
		// recupera el nombre del archivo
		String fileName = FilenameUtils.getName(pathTemplate);

		String fn = fileName.replaceAll(".docx", "");
		// OPCPackage pkg = OPCPackage.open(new
		// FileInputStream("pruebasDocumentos/contrato.docx"));
		OPCPackage pkg = OPCPackage.open(Fi);
		// XWPFDocument doc = new XWPFDocument(OPCPackage.open(path));
		@SuppressWarnings("resource")
		XWPFDocument doc = new XWPFDocument(pkg);
		JsonStringParser j = new JsonStringParser();

		String[][] pairKeyValue = j.parser(JSONs.replaceAll("\"", "").replaceAll("\n", "").replaceAll("\r", "").trim());
		for (XWPFParagraph p : doc.getParagraphs()) {
			List<XWPFRun> runs = p.getRuns();
			String tex = p.getParagraphText();
			//String st=p.getStyle();
			
			
			for (int i = 0; i < pairKeyValue.length; i++) {
				if (tex != null && tex.contains("$$" + pairKeyValue[i][0].trim() + "$$")) {
					tex = tex.replace("$$" + pairKeyValue[i][0].trim() + "$$",
							pairKeyValue[i][1].replaceAll("@", ":").trim());
					//

				}
			}

			int a = 0;
			if (runs != null) {
				for (XWPFRun r : runs) {
					
					if (a == 0) {
						r.setText(tex, 0);
						setRun(r , "Courier New" , "000000" , "" , false, false, false);
					} else {
						r.setText("", 0);
						setRun(r , "Courier New" , "000000" , "" , false, false, false);
					}
					a++;
				}
			}
			tex = "";
		}

		/* Constructor del path */
		String[] z = filename.split("@");
		fileName = z[0];
		fileName = fileName + ".docx";

		String[] y = fn.split("@");
		fn = y[0];

		String outPath = pathSalida + fn + filename;
		Path path = Paths.get(outPath);
		Files.createFile(path);

		// System.out.println("outPath "+outPath);
		doc.write(new FileOutputStream(outPath));
		// System.out.println("Documento creado");
		//
		//

		return outPath;
	}

	@SuppressWarnings("unused")
	private String modifyDocxMasivo(String pathSalida, String filename, ArrayList<String> JSONS, FileInputStream Fi)
			throws Exception {
		// LOG.info("Iniciamos modificacion documentoX");
		Field pathField = FileInputStream.class.getDeclaredField("path");
		pathField.setAccessible(true);
		String pathTemplate = (String) pathField.get(Fi);
		// System.out.println("path template: "+pathTemplate);

		// String ext= FilenameUtils.getExtension(pathfinal);
		// recupera el nombre del archivo
		String fileName = FilenameUtils.getName(pathTemplate);

		String fn = fileName.replaceAll(".docx", "");
		// OPCPackage pkg = OPCPackage.open(new
		// FileInputStream("pruebasDocumentos/contrato.docx"));
		OPCPackage pkg = OPCPackage.open(Fi);
		// XWPFDocument doc = new XWPFDocument(OPCPackage.open(path));
		@SuppressWarnings("resource")
		XWPFDocument doc = new XWPFDocument(pkg);
		@SuppressWarnings("resource")
		XWPFDocument doc2 = new XWPFDocument(pkg);

		JsonStringParser j = new JsonStringParser();
		String outPath = "";
		/// marcador

		
		List<XWPFParagraph> pb = null;
		List<XWPFParagraph> pb2 = null;
		List<XWPFParagraph> pb4 = null;
		ArrayList<XWPFParagraph> pb3 = new ArrayList<XWPFParagraph>();
		// listado con los JSONS
		int firstdoc = 0;

		pb2 = doc2.getParagraphs();

		@SuppressWarnings("resource")
		XWPFDocument document = new XWPFDocument();
		document.createParagraph();

		// -----------------------------------------------------------------------------------------------------
		for (int ii = 0; ii < JSONS.size(); ii++) {

			// extraigo el primer array de valores
			@SuppressWarnings("resource")
			XWPFDocument doc3 = new XWPFDocument(pkg);
			pb = doc3.getParagraphs();
			String[][] pairKeyValue = j
					.parser(JSONS.get(ii).replaceAll("\"", "").replaceAll("\n", "").replaceAll("\r", "").trim());
			Gson g = new Gson();
			// extraer todos los parrafos del documento
			for (int a = 0; a < pb2.size(); a++) {
				XWPFParagraph p = pb2.get(a);

				String tex = p.getParagraphText();
			

				for (int i = 0; i < pairKeyValue.length; i++) {
					if (tex != null && tex.contains("$$" + pairKeyValue[i][0].trim() + "$$")) {
						tex = tex.replace("$$" + pairKeyValue[i][0].trim() + "$$",
								pairKeyValue[i][1].replaceAll("@", ":").trim());
					}
				}
				int aa = 0;
				for (XWPFRun r : pb2.get(a).getRuns()) {

					if (aa == 0) {

						r.setText(tex, 0);
						firstdoc++;

					} else {
						r.setText("", 0);
					}
					aa++;
				}

				aa = 0;

				document.createParagraph();
				document.setParagraph(pb2.get(a), document.getParagraphs().size() - 1);

			}
			
			document.getParagraphs().get(document.getParagraphs().size() - 1).setPageBreak(true);
			pb2 = pb;

		}

		// ---------------------------------------------------------------------------------------------------------

		/* Constructor del path */
		String[] z = filename.split("@");
		fileName = z[0];
		fileName = fileName + ".docx";

		String[] y = fn.split("@");
		fn = y[0];

		outPath = pathSalida + fn + filename;
		Path path = Paths.get(outPath);
		Files.createFile(path);


		document.write(new FileOutputStream(outPath));
		
		return outPath;
	}

	public void convertToPdf(String PathDocx, String PathSalida, String nameofdocument) {
		/*
		 * set variables
		 *
		 * fileName
		 */

		// String fileName = FilenameUtils.getName(PathDocx);
		// String parentDirectory = FilenameUtils.getPath(PathSalida);
		// String fn = fileName.replaceAll(".docx", "");

	}

	public void convertToPdf(String PathSalida, FileInputStream fi) throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException, InvalidFormatException, IOException {
		Field pathField = FileInputStream.class.getDeclaredField("path");
		pathField.setAccessible(true);
		String pathTemplate = (String) pathField.get(fi);

		// String ext= FilenameUtils.getExtension(pathfinal);
		// recupera el nombre del archivo
		String fileName = FilenameUtils.getName(pathTemplate);
		String parentDirectory = FilenameUtils.getPath(PathSalida);
		String fn = fileName.replaceAll(".docx", "");
		OPCPackage pkg = OPCPackage.open(fi);
		// XWPFDocument doc = new XWPFDocument(OPCPackage.open(path));
		XWPFDocument doc = new XWPFDocument(pkg);
		// 2) Convert POI XWPFDocument 2 PDF with iText
		// String pathFinalDeSalida = "";

		String SA = generarSecuenciaAleatoria();

		String outpath = parentDirectory + fn + SA + ".pdf";
		File outFile = new File(outpath);
		// outFile.getParentFile().mkdirs();

		OutputStream out = new FileOutputStream(outFile);
		PdfOptions options = PdfOptions.create().fontEncoding("windows-1250");
		PdfConverter.getInstance().convert(doc, out, options);

		
	}

	private String generarSecuenciaAleatoria() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
		return uuid;
	}

	public void createAndSave() throws IOException {
		// TODO:
		/*
		 * Debe crear un documento seg�n una plantilla prehecha Ddebe ser
		 * capaz de crear multiples documentos dependiendo de las variables que
		 * se entreguen y del tipo de plantilla que asigne
		 * 
		 */
		// Solo crea un documento blanco en word

		out = new FileOutputStream(new File("DocumentoPrueba.docx"));

		wordx.write(out);
		out.close();


	}

	public void createParagraph() {

	}

	public XWPFDocument getWord() {
		return wordx;
	}

	public void setWord(XWPFDocument word) {
		this.wordx = word;
	}

	private static Map<Integer, XWPFRun> getPosToRuns(XWPFParagraph paragraph) {
		int pos = 0;
		Map<Integer, XWPFRun> map = new HashMap<Integer, XWPFRun>(10);
		for (XWPFRun run : paragraph.getRuns()) {
			String runText = run.text();
			if (runText != null) {
				for (int i = 0; i < runText.length(); i++) {
					map.put(pos + i, run);
				}
				pos += runText.length();
			}
		}
		return (map);
	}

	public static <V> void replace(XWPFDocument document, Map<String, V> map) {
		List<XWPFParagraph> paragraphs = document.getParagraphs();
		for (XWPFParagraph paragraph : paragraphs) {
			replace(paragraph, map);
		}
	}

	public static <V> void replace(XWPFDocument document, String searchText, V replacement) {
		List<XWPFParagraph> paragraphs = document.getParagraphs();
		for (XWPFParagraph paragraph : paragraphs) {
			replace(paragraph, searchText, replacement);
		}
	}

	private static <V> void replace(XWPFParagraph paragraph, Map<String, V> map) {
		for (Map.Entry<String, V> entry : map.entrySet()) {
			replace(paragraph, entry.getKey(), entry.getValue());
		}
	}

	public static <V> void replace(XWPFParagraph paragraph, String searchText, V replacement) {
		boolean found = true;
		while (found) {
			found = false;
			int pos = paragraph.getText().indexOf(searchText);
			if (pos >= 0) {
				found = true;
				Map<Integer, XWPFRun> posToRuns = getPosToRuns(paragraph);
				XWPFRun run = posToRuns.get(pos);
				XWPFRun lastRun = posToRuns.get(pos + searchText.length() - 1);
				int runNum = paragraph.getRuns().indexOf(run);
				int lastRunNum = paragraph.getRuns().indexOf(lastRun);
				String texts[] = replacement.toString().split("\n");
				run.setText(texts[0], 0);
				XWPFRun newRun = run;
				for (int i = 1; i < texts.length; i++) {
					newRun.addCarriageReturn();
					newRun = paragraph.insertNewRun(runNum + i);
					/*
					 * We should copy all style attributes to the newRun from
					 * run also from background color, ... Here we duplicate
					 * only the simple attributes...
					 */
					newRun.setText(texts[i]);
					newRun.setBold(run.isBold());
					newRun.setCapitalized(run.isCapitalized());
					// newRun.setCharacterSpacing(run.getCharacterSpacing());
					newRun.setColor(run.getColor());
					newRun.setDoubleStrikethrough(run.isDoubleStrikeThrough());
					newRun.setEmbossed(run.isEmbossed());
					newRun.setFontFamily(run.getFontFamily());
					newRun.setFontSize(run.getFontSize());
					newRun.setImprinted(run.isImprinted());
					newRun.setItalic(run.isItalic());
					newRun.setKerning(run.getKerning());
					newRun.setShadow(run.isShadowed());
					newRun.setSmallCaps(run.isSmallCaps());
					newRun.setStrikeThrough(run.isStrikeThrough());
					newRun.setSubscript(run.getSubscript());
					newRun.setUnderline(run.getUnderline());
				}
				for (int i = lastRunNum + texts.length - 1; i > runNum + texts.length - 1; i--) {
					paragraph.removeRun(i);
				}
			}
		}
	}

	public static void cloneParagraph(XWPFParagraph clone, XWPFParagraph source) {
		CTPPr pPr = clone.getCTP().isSetPPr() ? clone.getCTP().getPPr() : clone.getCTP().addNewPPr();
		pPr.set(source.getCTP().getPPr());
		for (XWPFRun r : source.getRuns()) {
			XWPFRun nr = clone.createRun();
			cloneRun(nr, r);
		}
	}

	public static void cloneRun(XWPFRun clone, XWPFRun source) {
		CTRPr rPr = clone.getCTR().isSetRPr() ? clone.getCTR().getRPr() : clone.getCTR().addNewRPr();
		rPr.set(source.getCTR().getRPr());
		clone.setText(source.getText(0));
	}

	private String modifyDocxWithFormat(String pathSalida, String filename, String JSONs, FileInputStream Fi)
			throws Exception {

		Field pathField = FileInputStream.class.getDeclaredField("path");
		pathField.setAccessible(true);
		String pathTemplate = (String) pathField.get(Fi);

		// recupera el nombre del archivo
		String fileName = FilenameUtils.getName(pathTemplate);

		String fn = fileName.replaceAll(".docx", "");
		OPCPackage pkg = OPCPackage.open(Fi);
		XWPFDocument doc = new XWPFDocument(pkg);
		JsonStringParser j = new JsonStringParser();

		String[][] pairKeyValue = j.parser(JSONs.replaceAll("\"", "").replaceAll("\n", "").replaceAll("\r", "").trim());

		try {
			for (int i = 0; i < pairKeyValue.length; i++) {
				replace(doc, ("" + pairKeyValue[i][0].trim() + ""),
						pairKeyValue[i][1].replaceAll("@", ":").replaceAll("&", ",").trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/* Constructor del path */
		String[] z = filename.split("@");
		fileName = z[0];
		fileName = fileName + ".docx";

		String[] y = fn.split("@");
		fn = y[0];

		String outPath = pathSalida + fn + filename;
		Path path = Paths.get(outPath);
		Files.createFile(path);

		
		doc.write(new FileOutputStream(outPath));
		System.out.println("Documento creado");

		return outPath;

	}

	public String modifyDocxWithFormatMasivo(String pathSalida, String filename, ArrayList<String> JSONs,
			FileInputStream Fi) throws Exception {
		Field pathField = FileInputStream.class.getDeclaredField("path");
		pathField.setAccessible(true);
		String pathTemplate = (String) pathField.get(Fi);

		// recupera el nombre del archivo
		String fileName = FilenameUtils.getName(pathTemplate);

		String fn = fileName.replaceAll(".docx", "");
		OPCPackage pkg = OPCPackage.open(Fi);
		XWPFDocument newdoc = new XWPFDocument();

		JsonStringParser j = new JsonStringParser();

		for (String json : JSONs) {

			XWPFDocument doc = new XWPFDocument(pkg);

			String[][] pairKeyValue = j
					.parser(json.replaceAll("\"", "").replaceAll("\n", "").replaceAll("\r", "").trim());

			for (int i = 0; i < pairKeyValue.length; i++) {

				replace(doc, ("" + pairKeyValue[i][0].trim() + ""),
						pairKeyValue[i][1].replaceAll("@", ":").replaceAll("&", ",").trim());
			}

			doc.close();

			Iterator<XWPFParagraph> paragraphIt = doc.getParagraphsIterator();

			while (paragraphIt.hasNext()) {
				XWPFParagraph existingParagraph = paragraphIt.next();

				XWPFParagraph newParagraph = newdoc.createParagraph();

				cloneParagraph(newParagraph, existingParagraph); // http://stackoverflow.com/questions/23112924/make-an-exact-copy-of-a-paragraph-including-all-contents-and-properties

			}

		}

		/* Constructor del path */
		String[] z = filename.split("@");
		fileName = z[0];
		fileName = fileName + ".docx";

		String[] y = fn.split("@");
		fn = y[0];

		String outPath = pathSalida + fn + filename + ".docx";
		Path path = Paths.get(outPath);
		Files.createFile(path);

	
		newdoc.write(new FileOutputStream(outPath));
		System.out.println("Documento creado");
		newdoc.close();
		return outPath;

	}
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////         								 ///////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	public String crearFiniquitoMasivo(String pathSalida, String nombre, ArrayList<DocFiniquito> informacion, String idTemplate) throws Exception{
		System.out.println("estoy aca");
		Field pathField = FileInputStream.class.getDeclaredField("path");
		pathField.setAccessible(true);
		// String ext= FilenameUtils.getExtension(pathfinal);
		// recupera el nombre del archivo
		String fileName = "";
		Blob Contrato = DocumentsDB.getFileById("76");
		System.out.println("saque la template de la DB");
		@SuppressWarnings("resource")
		XWPFDocument doc2=new XWPFDocument();
		@SuppressWarnings("resource")
		XWPFDocument doc3=new XWPFDocument();
		for(int i=0;i<informacion.size();i++){
			InputStream in=Contrato.getBinaryStream();
			OPCPackage pkg = OPCPackage.open(in);
			XWPFDocument doc = new XWPFDocument(pkg);
			if(i==0){
				doc2=crearFiniquitoIndividual(informacion.get(i), doc);
				doc.createParagraph().setPageBreak(true);
			}
			else{
				doc3=crearFiniquitoIndividual(informacion.get(i), doc);
				if(i<informacion.size()-1){
				doc3.createParagraph().setPageBreak(true);
				}
				doc2=merge(doc2,doc3);
			}	
		}
		/* Constructor del path */
		fileName = nombre + ".docx";
		System.out.println("hola estoy casi terminando");
		String outPath = pathSalida + fileName;
		System.out.println("out: "+outPath);
		Path path = Paths.get(outPath);
		System.out.println("out: "+path);
		//Files.createFile(path);
		System.out.println("outPath "+outPath);
		FileOutputStream ou=new FileOutputStream(outPath);
		doc3.close();
		doc2.write(ou);
		doc2.close();
		ou.close();		
		 System.out.println("Documento creado");
		//
		//
		return fileName;
	}
	private XWPFDocument merge(XWPFDocument doc0, XWPFDocument doc1) {
		
		for(IBodyElement e : doc1.getBodyElements()){
	        if(e instanceof XWPFParagraph){
	            XWPFParagraph p = (XWPFParagraph) e;
	            if(p.getCTP().getPPr()!=null && p.getCTP().getPPr().getSectPr()!=null){
	                continue;
	            }else{
	                doc0.createParagraph();
	                doc0.setParagraph(p, doc0.getParagraphs().size()-1);
	            }
	        }else if(e instanceof XWPFTable){
	            XWPFTable t = (XWPFTable)e;
	            doc0.createTable();
	            doc0.setTable(doc0.getTables().size()-1,t);
	        }else if(e instanceof XWPFSDT){
	            // boh!
	        }
	    }
		return doc0;
	}

	private XWPFDocument crearFiniquitoIndividual(DocFiniquito informacion, XWPFDocument doc ){
		int counter=0;
		//veo las tablas del documento
		for(XWPFParagraph p: doc.getParagraphs()){
			List<XWPFRun> runs = p.getRuns();
			String tex = p.getParagraphText();
			//String st=p.getStyle();
			for (int i = 0; i < informacion.getDatosDuros().length; i++) {
				if (tex != null && tex.contains(informacion.getDatosDuros()[i][0]==null?"NaNValue":informacion.getDatosDuros()[i][0])) {
					tex = tex.replace(informacion.getDatosDuros()[i][0],
							informacion.getDatosDuros()[i][1]==null?"":informacion.getDatosDuros()[i][1]);
					//
				}
			}
			int a = 0;
			if (runs != null) {
				for (XWPFRun r : runs) {
					if (a == 0) {
						r.setText(tex, 0);
					} else {
						r.setText("", 0);
					}
					a++;
				}
			}
			tex = "";
		}
		for (XWPFTable tbl : doc.getTables()) {
			if(counter==0){
				if(informacion.getTablaUno()!=null){
				for(int i=0;i<informacion.getTablaUno().length;i++){
				if(i==0){
				}
				else{
					tbl.createRow();
				}
				XWPFTableRow row =tbl.getRow(i);
				row.setHeight(8);
				int celda=0;
				for (XWPFTableCell cell : row.getTableCells()) {
					cell.removeParagraph(0);
					cell.addParagraph();
					cell.getParagraphs().get(0).createRun(); 
					cell.getParagraphs().get(0).getRuns().get(0).setText(informacion.getTablaUno()[i][celda]);
					if(celda==0 ){
						for(XWPFParagraph p: cell.getParagraphs()){
							for(XWPFRun r: p.getRuns()){
								r.setFontFamily("Curier New");
							       r.setFontSize(8);
							}
						}
					}
					else if(celda==1 ||celda==2){
						for(XWPFParagraph p: cell.getParagraphs()){
							p.setAlignment(ParagraphAlignment.RIGHT);
							for(XWPFRun r: p.getRuns()){
								r.setFontFamily("Curier New");
							       r.setFontSize(8);
							}
						}
					}
			         celda++;
			      }
				celda=0;
				}
				}
			}
			else if(counter==1){
				if(informacion.getTablaTres()!=null){
				for(int i=0;i<informacion.getTablaTres().length;i++){
					if(i==0){
					}
					else{
						tbl.createRow();
					}
				XWPFTableRow row =tbl.getRow(i);
				int celda=0;
				for (XWPFTableCell cell : row.getTableCells()) {
					cell.removeParagraph(0);
					cell.addParagraph();
					cell.getParagraphs().get(0).createRun(); 
					cell.getParagraphs().get(0).getRuns().get(0).setText(informacion.getTablaTres()[i][celda]);
					if(celda==0 ){
						for(XWPFParagraph p: cell.getParagraphs()){
							for(XWPFRun r: p.getRuns()){
								r.setFontFamily("Curier New");
							       r.setFontSize(8);
							}
						}
					}
					else if(celda==1 ||celda==2){
						for(XWPFParagraph p: cell.getParagraphs()){
							p.setAlignment(ParagraphAlignment.RIGHT);
							for(XWPFRun r: p.getRuns()){
								r.setFontFamily("Curier New");
							       r.setFontSize(8);
							}
						}
					}
			         celda++;
			      }
				celda=0;
				}
				}
			}	
			else if(counter==3){
				 for (XWPFTableRow row : tbl.getRows()) {
					 @SuppressWarnings("unused")
					int celda=0;
					 for (XWPFTableCell cell : row.getTableCells()) {
						 for(int i=0;i<informacion.getDatosDuros().length;i++){
							if(cell.getText().contains(informacion.getDatosDuros()[i][0]==null?"NaNValue":informacion.getDatosDuros()[i][0])){
								cell.setText(informacion.getDatosDuros()[i][1]==null?"":informacion.getDatosDuros()[i][1]);
							}
						 }
						 celda++;
					 } 
					 celda=0;
				   }
			}
			else if(counter==2){
			}
				counter++;
			}
		return doc;
	}
	
	public String crearFiniquito(String pathSalida, String nombre, DocFiniquito informacion, String idTemplate) throws Exception {
		System.out.println("estoy aca");
		Field pathField = FileInputStream.class.getDeclaredField("path");
		pathField.setAccessible(true);
		// String ext= FilenameUtils.getExtension(pathfinal);
		// recupera el nombre del archivo
		String fileName = "";
		Blob Contrato = DocumentsDB.getFileById("76");
		System.out.println("saque la template de la DB");
		InputStream in=Contrato.getBinaryStream();
		OPCPackage pkg = OPCPackage.open(in);
		XWPFDocument doc = new XWPFDocument(pkg);
		//------------------------------------
		System.out.println("numero de tablas: "+ doc.getTables().size());
		Gson g= new Gson();
		System.out.println(g.toJson(informacion));
		int counter=0;
		//veo las tablas del documento
		for(XWPFParagraph p: doc.getParagraphs()){
			List<XWPFRun> runs = p.getRuns();
			String tex = p.getParagraphText();
			//String st=p.getStyle();
			for (int i = 0; i < informacion.getDatosDuros().length; i++) {
				if (tex != null && tex.contains(informacion.getDatosDuros()[i][0]==null?"NaNValue":informacion.getDatosDuros()[i][0])) {
					tex = tex.replace(informacion.getDatosDuros()[i][0],
							informacion.getDatosDuros()[i][1]==null?"":informacion.getDatosDuros()[i][1]);
					//
				}
			}
			int a = 0;
			if (runs != null) {
				for (XWPFRun r : runs) {
					if (a == 0) {
						r.setText(tex, 0);
					} else {
						r.setText("", 0);
					}
					a++;
				}
			}
			tex = "";
		}
		for (XWPFTable tbl : doc.getTables()) {
			if(counter==0){
				if(informacion.getTablaUno()!=null){
				for(int i=0;i<informacion.getTablaUno().length;i++){
				if(i==0){
				}
				else{
					tbl.createRow();
				}
				XWPFTableRow row =tbl.getRow(i);
				row.setHeight(8);
				int celda=0;
				for (XWPFTableCell cell : row.getTableCells()) {
					cell.removeParagraph(0);
					cell.addParagraph();
					cell.getParagraphs().get(0).createRun(); 
					cell.getParagraphs().get(0).getRuns().get(0).setText(informacion.getTablaUno()[i][celda]);
					if(celda==0 ){
						for(XWPFParagraph p: cell.getParagraphs()){
							for(XWPFRun r: p.getRuns()){
								r.setFontFamily("Curier New");
							       r.setFontSize(8);
							}
						}
					}
					else if(celda==1 ||celda==2){
						for(XWPFParagraph p: cell.getParagraphs()){
							p.setAlignment(ParagraphAlignment.RIGHT);
							for(XWPFRun r: p.getRuns()){
								r.setFontFamily("Curier New");
							       r.setFontSize(8);
							}
						}
					}
			         celda++;
			      }
				celda=0;
				}
				}
			}
			else if(counter==1){
				if(informacion.getTablaTres()!=null){
				for(int i=0;i<informacion.getTablaTres().length;i++){
					if(i==0){
					}
					else{
						tbl.createRow();
					}
				XWPFTableRow row =tbl.getRow(i);
				int celda=0;
				for (XWPFTableCell cell : row.getTableCells()) {
					cell.removeParagraph(0);
					cell.addParagraph();
					cell.getParagraphs().get(0).createRun(); 
					cell.getParagraphs().get(0).getRuns().get(0).setText(informacion.getTablaTres()[i][celda]);
					if(celda==0 ){
						for(XWPFParagraph p: cell.getParagraphs()){
							for(XWPFRun r: p.getRuns()){
								r.setFontFamily("Curier New");
							       r.setFontSize(8);
							}
						}
					}
					else if(celda==1 ||celda==2){
						for(XWPFParagraph p: cell.getParagraphs()){
							p.setAlignment(ParagraphAlignment.RIGHT);
							for(XWPFRun r: p.getRuns()){
								r.setFontFamily("Curier New");
							       r.setFontSize(8);
							}
						}
					}
			         celda++;
			      }
				celda=0;
				}
				}
			}	
			else if(counter==3){
				 for (XWPFTableRow row : tbl.getRows()) {
					 @SuppressWarnings("unused")
					int celda=0;
					 for (XWPFTableCell cell : row.getTableCells()) {
						 for(int i=0;i<informacion.getDatosDuros().length;i++){
							if(cell.getText().contains(informacion.getDatosDuros()[i][0]==null?"NaNValue":informacion.getDatosDuros()[i][0])){
								cell.setText(informacion.getDatosDuros()[i][1]==null?"":informacion.getDatosDuros()[i][1]);
							}
						 }
						 celda++;
					 } 
					 celda=0;
				   }
			}
			else if(counter==2){
			}
			counter++;
			  
			}
		/* Constructor del path */
		fileName = nombre + ".docx";
		System.out.println("hola estoy casi terminando");
		String outPath = pathSalida + fileName;
		System.out.println("out: "+outPath);
		Path path = Paths.get(outPath);
		System.out.println("out: "+path);
		//Files.createFile(path);
		System.out.println("outPath "+outPath);
		FileOutputStream ou=new FileOutputStream(outPath);
		doc.write(ou);
		doc.close();
		ou.close();		
		 System.out.println("Documento creado");
		//
		//
		return outPath;
	}
	public String crearLiquidacion(String pathSalida, String nombre, DocLiquidacion dl, int idTemplate) throws Exception{
		System.out.println("estoy aca");
		Field pathField = FileInputStream.class.getDeclaredField("path");
		pathField.setAccessible(true);
		// String ext= FilenameUtils.getExtension(pathfinal);
		// recupera el nombre del archivo
		String fileName = "";
		Blob Contrato = DocumentsDB.getFileById("75");
		System.out.println("saque la template de la DB");
		InputStream in=Contrato.getBinaryStream();
		OPCPackage pkg = OPCPackage.open(in);
		XWPFDocument doc = new XWPFDocument(pkg);
		
		System.out.println("numero de tablas: "+ doc.getTables().size());
		Gson g= new Gson();
		System.out.println(g.toJson(dl));
		int counter=0;
		
		
		//veo las tablas del documento
		
		for (XWPFTable tbl : doc.getTables()) {
			if(counter==0 || counter==1 || counter==4){
				 for (XWPFTableRow row : tbl.getRows()) {
				      for (XWPFTableCell cell : row.getTableCells()) {
				    	 for(XWPFParagraph p: cell.getParagraphs()){
				    		 String tex=p.getText();
//				 			for (int i = 0; i < tp.getDatos().length; i++) {
//								if (tex != null && tex.contains(tp.getDatos()[i][0])) {
//									String salvador=tp.getDatos()[i][1]==null?"":tp.getDatos()[i][1];
//									tex = tex.replace(tp.getDatos()[i][0].trim(),
//											salvador);
//									//
//									
//									
//								}
//								
//							}
				 			int a = 0;
				 			if (p.getRuns() != null) {
				 				for (XWPFRun r : p.getRuns()) {
				 					if (a == 0) {
				 						r.setText(tex, 0);
				 					} else {
				 						r.setText("", 0);
				 					}
				 					a++;
				 				}
				 			}
				    		 
				    	 }
				         
				      }
				   }
			}
			else if(counter==2){
				 for (XWPFTableRow row : tbl.getRows()) {
				      for (XWPFTableCell cell : row.getTableCells()) {
				    	 for(XWPFParagraph p: cell.getParagraphs()){
				    		 String tex=p.getText();
//				 			for (int i = 0; i < tp.getDatosPrincipales().length; i++) {
//								
//								if (tex != null && tex.contains(tp.getDatosPrincipales()[i][0])) {
//									String salvador=tp.getDatosPrincipales()[i][1]==null?"":tp.getDatosPrincipales()[i][1];
//									tex = tex.replace(tp.getDatosPrincipales()[i][0].trim(),
//											salvador);
//									//
//									
//									
//								}
//								
//							}
				 			int a = 0;
				 			if (p.getRuns() != null) {
				 				for (XWPFRun r : p.getRuns()) {
				 					if (a == 0) {
				 						r.setText(tex, 0);
				 					} else {
				 						r.setText("", 0);
				 					}
				 					a++;
				 				}
				 			}
				    		 
				    	 }
				         
				      }
				   }
				
			}
			else if(counter==3){
//				for(int i=0;i<tp.getTablaDatos().length;i++){
//					tbl.createRow();
//					XWPFTableRow row =tbl.getRow(i+1);
//					int celda=0;
//					for (XWPFTableCell cell : row.getTableCells()) {
//						cell.setText(tp.getTablaDatos()[i][celda]);
//				         celda++;
//				      }
//					celda=0;
//				}
			}
			counter++;
			  
			}
		/* Constructor del path */
		
		fileName = nombre + ".docx";
		System.out.println("hola estoy casi terminando");
		
		String outPath = pathSalida + fileName;
		System.out.println("out: "+outPath);
		Path path = Paths.get(outPath);
		System.out.println("out: "+path);
		//Files.createFile(path);
		System.out.println("outPath "+outPath);
		FileOutputStream ou=new FileOutputStream(outPath);
		doc.write(ou);
		doc.close();
		ou.close();		
		 System.out.println("Documento creado");
		//
		//
		
		
		
		
		
		
		
		return outPath;
	}
	

}

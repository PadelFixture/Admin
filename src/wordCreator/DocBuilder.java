package wordCreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;

import lib.classSW.DatosLiquidacion;
import lib.classSW.GetDatosContratoTrabajador;
import lib.utils.GeneralUtility;
import lib.utils.NumericUtility;

public class DocBuilder {

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
	
	private void printListInTable(int templateLine,int startLine,XWPFTable table, String datos1, String datos2, int fontSize) throws XmlException, IOException{
		
		 XWPFTable tablaConceptos = table;
			
		 // Obtenemos la fila que sera la fila Template con la cual crearemos las demas
		 //XWPFTableRow rowTemplate = tablaConceptos.getRow(templateLine);
				
		 //Copiamos los estilos de la fila
		 CTRow templateRow = generateTemplateRow(templateLine, tablaConceptos);
		 
		 // Crear Nueva Fila
		 XWPFTableRow newRow = new XWPFTableRow(templateRow, tablaConceptos);
		 
		 newRow.getCell(0).removeParagraph(0);
		 
		 XWPFParagraph paragraph1 = newRow.getCell(0).addParagraph();
		 setRun(paragraph1.createRun() , "Courier New" , fontSize, "000000" , datos1 , false, false, false);
		 
		 newRow.getCell(1).removeParagraph(0);
		  
		 XWPFParagraph paragraph2 = newRow.getCell(1).addParagraph();
		 setRun(paragraph2.createRun() , "Courier New" , fontSize, "000000" , datos2 , false, false, true);
		 
		 // agregar la nueva fila a la tabla
		 tablaConceptos.addRow(newRow, startLine);

		
	}
	
	
	private void printListInTableHeader(int templateLine,int startLine,XWPFTable table, String datos1, String datos2, int fontSize) throws XmlException, IOException{
		
		 XWPFTable tablaConceptos = table;
			
		 // Obtenemos la fila que sera la fila Template con la cual crearemos las demas
		 //XWPFTableRow rowTemplate = tablaConceptos.getRow(templateLine);
				
		 //Copiamos los estilos de la fila
		 CTRow templateRow = generateTemplateRow(templateLine, tablaConceptos);
		 
		 // Crear Nueva Fila
		 XWPFTableRow newRow = new XWPFTableRow(templateRow, tablaConceptos);
		 
		 newRow.getCell(0).removeParagraph(0);
		 
		 XWPFParagraph paragraph1 = newRow.getCell(0).addParagraph();
		 setRun(paragraph1.createRun() , "Courier New" , fontSize, "000000" , datos1 , false, false, false);
		 
		 newRow.getCell(1).removeParagraph(0);
		  
		 XWPFParagraph paragraph2 = newRow.getCell(1).addParagraph();
		 setRun(paragraph2.createRun() , "Courier New" , fontSize, "000000" , datos2 , false, false, false);
		 
		 // agregar la nueva fila a la tabla
		 tablaConceptos.addRow(newRow, startLine);

		
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
        
        CTSpacing spacing = ppr.isSetSpacing()? ppr.getSpacing() : ppr.addNewSpacing();
        spacing.setAfter(BigInteger.valueOf(0));
        spacing.setBefore(BigInteger.valueOf(0));
        spacing.setLineRule(STLineSpacingRule.AUTO);
        spacing.setLine(BigInteger.valueOf(240));
        if (addBreak) run.addBreak();
    }
	
	
	@SuppressWarnings("deprecation")
	private static void setParagraphRun (XWPFRun run , String fontFamily , int fontSize , String colorRGB , String text , boolean bold , boolean addBreak, boolean rightText) {
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
        
        CTSpacing spacing = ppr.isSetSpacing()? ppr.getSpacing() : ppr.addNewSpacing();
        spacing.setAfter(BigInteger.valueOf(0));
        spacing.setBefore(BigInteger.valueOf(0));
        spacing.setLineRule(STLineSpacingRule.AUTO);
        spacing.setLine(BigInteger.valueOf(240));
        if (addBreak) run.addBreak();
    }
	
	private void printDetailInTable(int templateLine,int startLine,XWPFTable table, String datos1, String datos2, String datos3, int fontSize) throws Exception {
				
		 XWPFTable tablaConceptos = table;
			
		 // Obtenemos la fila que sera la fila Template con la cual crearemos las demas
		 //XWPFTableRow rowTemplate = tablaConceptos.getRow(templateLine);
				
		 //Copiamos los estilos de la fila
		 CTRow templateRow = generateTemplateRow(templateLine, tablaConceptos);
		 
		 // Crear Nueva Fila
		 XWPFTableRow newRow = new XWPFTableRow(templateRow, tablaConceptos);
		 
		 newRow.getCell(0).removeParagraph(0);
		 XWPFParagraph paragraph1 = newRow.getCell(0).addParagraph();
		 setRun(paragraph1.createRun() , "Courier New" , fontSize, "000000" , datos1 , false, false, false);
		 
		 newRow.getCell(1).removeParagraph(0);
		 XWPFParagraph paragraph2 = newRow.getCell(1).addParagraph();
		 setRun(paragraph2.createRun() , "Courier New" , fontSize, "000000" , datos2 , false, false, false);
		  
		 newRow.getCell(2).removeParagraph(0);
		 XWPFParagraph paragraph3 = newRow.getCell(2).addParagraph();
		 setRun(paragraph3.createRun() , "Courier New" , fontSize, "000000" ,  datos3 , false, false, true);
		 
		 // agregar la nueva fila a la tabla
		 tablaConceptos.addRow(newRow, startLine);
	}

	public String createLiquidacionDocInvertec(String pathSalida, String filename, GetDatosContratoTrabajador datosTrabajador,String JSONs, FileInputStream Fi,
			DatosLiquidacion datos) throws Exception {

		try{
	
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

			//setRun(p.createRun() , "Courier New" , 9, "000000" , tex , false, false, false);
			
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
		
		//----------------------DATOS DUROS---------------------------//
		String[][] datosFijos = new String[5][3];
		
		datosFijos[0][0] = "CODIGO: " + datosTrabajador.getCodigoTrabajador();
		datosFijos[0][1] = "";
		datosFijos[0][2] = "RUT: "+ datosTrabajador.getRutCompletoTrabajador();
		
		datosFijos[1][0] = "TRABAJADOR: " + datosTrabajador.getAppPatTrabajador() + " " + datosTrabajador.getAppMaternoTrabajador() + " " + datosTrabajador.getNombreTrabajador();
		datosFijos[1][1] = "";
		datosFijos[1][2] = "FECHA CONTRATO: " + datosTrabajador.getFechaInicio();
		
		datosFijos[2][0] = "FECHA FIN CONTRATO: " + datosTrabajador.getFechaTerminoContratoFaena() == null ? " " : datosTrabajador.getFechaTerminoContratoFaena();
		datosFijos[2][1] = "";
		datosFijos[2][2] = "TIPO CONTRATO: " + datosTrabajador.getTipoContrato();
		
		datosFijos[3][0] = "TIPO PAGO: " + datosTrabajador.getTipoPago();
		datosFijos[3][1] = "";
		datosFijos[3][2] = "FECHA PAGO: "+ datosTrabajador.getFechaPagoFiniquito();
		
		datosFijos[4][0] = "DIAS TRABAJADOS: " + datos.getDIAS_TRAJADOS();
		datosFijos[4][1] = "";
		datosFijos[4][2] = "LIQUIDO MES: " + GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getTOTAL_LIQUIDO_MES());
		
		
		//----------------------HABERES------------------------------//
		
		String[][] haberes = new String[30][3]; 
		int h = 0;
		/*
		haberes[h][0] = "1. HABERES IMPONIBLES";
		haberes[h][1] = "";
		haberes[h][2] = "TOTAL";
		h++;
		*/
		haberes[h][0] = "SUELDO BASE (" + GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getSUELDO_BASE_CONTRATO()) + ") "; 
		haberes[h][1] = "";
		haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getSUELDO_BASE()).trim();
		h++;
		
		haberes[h][0] = "GRATIFICACION";
		haberes[h][1] = "";
		haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getGRATIFICACION()).trim();
		h++;
		
		//OBTENER BONOS IMPONIBLES
		if(datos.getBONO() != null){
		for (int i = 0; i < datos.getBONO().size(); i++) {
					
			haberes[h][0] = datos.getBONO_NOMBRE().get(i);
			haberes[h][1] = "";
			haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getBONO().get(i)).trim();
			h++;
						
		}
		}
		
		if(datos.getHORAS_EXTRA() != null){
		for (int i = 0; i < datos.getHORAS_EXTRA().size(); i++) {
					
			haberes[h][0] = datos.getHORAS_EXTRA_NOMBRE().get(i) + " ( " + NumericUtility.formatStringNumberWithDotAnd2Decimals(datos.getHORAS_EXTRA_NHORAS().get(i)) + " ) " + NumericUtility.formatStringNumberWithTest2(datos.getHORAS_EXTRA_VHORAS().get(i));
			haberes[h][1] = "";
			haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getHORAS_EXTRA().get(i)).trim();
			h++;
								
		}
		}
			
		if(datos.getHORAS_FALTA() != null){
		for (int i = 0; i < datos.getHORAS_FALTA().size(); i++) {
			//Terminar ACA TODO:			
			haberes[h][0] = datos.getHORAS_FALTA_NOMBRE().get(i) + " ( " + NumericUtility.formatStringNumberWithDotAnd2Decimals(datos.getHORAS_FALTA_NHORAS().get(i)) + " ) " + NumericUtility.formatStringNumberWithTest2(datos.getHORAS_FALTA_VHORAS().get(i)) ;
			haberes[h][1] = "";
			haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getHORAS_FALTA().get(i)).trim();
			h++;
									
		}
		}
		
		haberes[h][0] = "TOTAL HABERES IMPONIBLES";
		haberes[h][1] = "";
		haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getTOTAL_HAB_IMPONIBLE()).trim();
		h++;
		
		/*
		if(datos.getTOTAL_HAB_NO_IMPONIBLE() != null){
			haberes[h][0] = "2. HABERES NO IMPONIBLES";
			haberes[h][1] = "";
			haberes[h][2] = "TOTAL";
			h++;
		}*/
		
		if(datos.getCARGA_FAMILIAR_SIMPLE() != null){
			
			Double TOTAL_CARGA_FAMILIAR1 = Double.valueOf(datos.getCARGA_FAMILIAR_SIMPLE());
			String TOTAL_CARGA_FAMILIAR_STRING1 = String.valueOf(TOTAL_CARGA_FAMILIAR1);
			
			haberes[h][0] = datos.getCARGA_FAMILIAR_SIMPLE_NOMBRE().toUpperCase() + " ( " + datos.getCARGA_FAMILIAR_SIMPLE_CANTIDAD() + " ) " ;
			haberes[h][1] = "";
			haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(TOTAL_CARGA_FAMILIAR_STRING1.substring(0, TOTAL_CARGA_FAMILIAR_STRING1.indexOf("."))).trim();
			h++;
		}
		
		if(datos.getCARGA_FAMILIAR_MATERNAL() != null){
			
			Double TOTAL_CARGA_FAMILIAR2 = Double.valueOf(datos.getCARGA_FAMILIAR_MATERNAL());
			String TOTAL_CARGA_FAMILIAR_STRING2 = String.valueOf(TOTAL_CARGA_FAMILIAR2);
			
			haberes[h][0] = datos.getCARGA_FAMILIAR_MATERNAL_NOMBRE().toUpperCase() + " ( " + datos.getCARGA_FAMILIAR_MATERNAL_CANTIDAD() + " ) " ;
			haberes[h][1] = "";
			haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(TOTAL_CARGA_FAMILIAR_STRING2.substring(0, TOTAL_CARGA_FAMILIAR_STRING2.indexOf(".")).trim());
			h++;
		}
		
		if(datos.getCARGA_FAMILIAR_RETROACTIVO() != null){
			
			Double TOTAL_CARGA_FAMILIAR3 = (Double.valueOf(datos.getCARGA_FAMILIAR_RETROACTIVO())) ;
			String TOTAL_CARGA_FAMILIAR_STRING3 = String.valueOf(TOTAL_CARGA_FAMILIAR3);
			
			haberes[h][0] = datos.getCARGA_FAMILIAR_RETROACTIVO_NOMBRE().toUpperCase() + " ( " + datos.getCARGA_FAMILIAR_RETROACTIVO_CANTIDAD() + " ) " ;
			haberes[h][1] = "";
			haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(TOTAL_CARGA_FAMILIAR_STRING3.substring(0, TOTAL_CARGA_FAMILIAR_STRING3.indexOf(".")).trim());
			h++;
		}
		
		if(datos.getBONO_NO_IMPONIBLE() != null){
		for (int i = 0; i < datos.getBONO_NO_IMPONIBLE().size(); i++) {
			
			haberes[h][0] = datos.getBONO_NO_IMPONIBLE_NOMBRE().get(i);
			haberes[h][1] = "";
			haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getBONO_NO_IMPONIBLE().get(i)).trim();
			h++;
						
		}
		}
		
		if(datos.getCONCEPTO_8() != null){
			for (int i = 0; i < datos.getBONO_NO_IMPONIBLE().size(); i++) {
			haberes[h][0] = datos.getCONCEPTO_8_NOMBRE().get(i);
			haberes[h][1] = "";
			haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getCONCEPTO_8().get(i)).trim();
			h++;
			}
		}
		
		
		if(datos.getTOTAL_HAB_NO_IMPONIBLE() != null){
		
		haberes[h][0] = "TOTAL HABERES NO IMPONIBLES";
		haberes[h][1] = "";
		haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getTOTAL_HAB_NO_IMPONIBLE()).trim();
		h++;
		
		}
		
		//Mostrar Haber Imponible o Tope Maximo
		String topeImponible = datos.getTOTAL_HABERES() == null ? "0" : datos.getTOTAL_HABERES();
		
		if(!("0".equals(topeImponible))){
			if(Double.valueOf(datos.getTOTAL_HABERES()) > Double.valueOf(datos.getTOPE_IMPONIBLE())){
				topeImponible = datos.getTOPE_IMPONIBLE();
			}
		}
		
		haberes[h][0] = "TOPE IMPONIBLE";
		haberes[h][1] = "";
		haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(topeImponible).trim();
		h++;
		
		haberes[h][0] = "TOTAL HABERES";
		haberes[h][1] = "";
		haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getTOTAL_HABERES()).trim();
		h++;
		
		//----------------------DESCUENTOS------------------------------//
		
		String[][] descuentos = new String[30][3];
		int d = 0;
		/*
		descuentos[d][0] = "1. DESCUENTOS LEGALES";
		descuentos[d][1] = "";
		descuentos[d][2] = "TOTAL";
		d++;
		*/
		String afp = "";
		if(datos.getAFP_NOMBRE() == null && datos.getAFP_PORCENTAJE() == null){
			afp = "PENSIONADO";
		}else{
			afp = datos.getAFP_NOMBRE() + "(" + datos.getAFP_PORCENTAJE() + "%)";
		}
		
		
		descuentos[d][0] = "DESCUENTOS PREVISIONAL " + afp;
		descuentos[d][1] = "";
		descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getAFP()).trim();
		d++;
		
		String caja = datos.getCAJA() == null ? "0" : datos.getCAJA();
		String caja_porcentaje = datos.getCAJA_PORCENTAJE() == null ? "0" : datos.getCAJA_PORCENTAJE();
		
		String getSalud = datos.getSALUD() == null ? "0" : datos.getSALUD();
		String getSaludPorcentaje = datos.getSALUD_PORCENTAJE() == null ? "0" : datos.getSALUD_PORCENTAJE(); 
		
		String salud = String.valueOf(Double.valueOf(getSalud) + Double.valueOf(caja));
		String salud_porcentaje = String.valueOf(Double.valueOf(getSaludPorcentaje.replace(",", ".")) + Double.valueOf(caja_porcentaje.replace(",", ".")));
		String salud_nombre = datos.getSALUD_NOMBRE()+ " " +"("+salud_porcentaje+"%)";
			
		descuentos[d][0] = "DESCUENTOS SALUD " + salud_nombre;
		descuentos[d][1] = "";
		descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(salud.substring(0, salud.indexOf("."))).trim();
		d++;
		
		if(datos.getSEGURO_CESANTIA_AFC_TRABAJADOR() != null){
		
			descuentos[d][0] = "SEGURO CESANTIA TRABAJADOR";
			descuentos[d][1] = "";
			descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getSEGURO_CESANTIA_AFC_TRABAJADOR()).trim();
			d++;
		}
		
		if(datos.getIMPUESTO_UNICO() != null ){
			for (int i = 0; i < datos.getIMPUESTO_UNICO().size(); i++) {
				if(!"0".equals(datos.getIMPUESTO_UNICO().get(i))){
				descuentos[d][0] = datos.getIMPUESTO_UNICO_NOMBRE().get(i).toUpperCase();
				descuentos[d][1] = "";
				descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getIMPUESTO_UNICO().get(i)).trim();
				d++;
				}
			}
		}
		
		if(datos.getAHORRO_VOLUNTARIO_AFP() != null && !"0".equals(datos.getAHORRO_VOLUNTARIO_AFP())){
			descuentos[d][0] = "AHORRO VOLUNTARIO AFP";
			descuentos[d][1] = "";
			descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getAHORRO_VOLUNTARIO_AFP()).trim();
			d++;
		}
		
		if(datos.getPLAN_ADICIONAL_TRIBUTABLE() != null ){
			for (int i = 0; i < datos.getPLAN_ADICIONAL_TRIBUTABLE().size(); i++) {
				if(!"0".equals(datos.getPLAN_ADICIONAL_TRIBUTABLE().get(i))){
					descuentos[d][0] = datos.getPLAN_ADICIONAL_TRIBUTABLE_NOMBRE().get(i).toUpperCase();
					descuentos[d][1] = "";
					descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getPLAN_ADICIONAL_TRIBUTABLE().get(i)).trim();
					d++;
				}		
			}
		}
		
		
		if(datos.getAPV() != null){
			descuentos[d][0] = "APV "+ datosTrabajador.getApvTrabajador();
			descuentos[d][1] = "";
			descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getAPV()).trim();
			d++;
		}
		
		if( datos.getAPV_ADICIONAL() != null && !"0".equals(datos.getAPV_ADICIONAL()) ){
			descuentos[d][0] = "VALOR APV ADICIONAL NO IMPONIBLE";
			descuentos[d][1] = "";
			descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getAPV_ADICIONAL()).trim();
			d++;
		}
		
		
		if(datos.getDESCUENTOS() != null){
			for (int i = 0; i < datos.getDESCUENTOS().size(); i++) {
				descuentos[d][0] = datos.getDESCUENTOS_NOMBRE().get(i);
				descuentos[d][1] = "";
				descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getDESCUENTOS().get(i)).trim();
				d++;
			}
		}
		
//		if(datos.getCONCEPTO_MEDICO() != null ){
//			for (int i = 0; i < datos.getCONCEPTO_MEDICO().size(); i++) {
//				if(!"0".equals(datos.getCONCEPTO_MEDICO().get(i))){
//					descuentos[d][0] = datos.getCONCEPTO_MEDICO_NOMBRE().get(i);
//					descuentos[d][1] = "";
//					descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getCONCEPTO_MEDICO().get(i));
//					d++;
//				}		
//			}
//		}
		
		if(datos.getSOBREGIRO() != null ){
		for (int i = 0; i < datos.getSOBREGIRO().size(); i++) {
			if(!"0".equals(datos.getSOBREGIRO().get(i))){
				descuentos[d][0] = datos.getSOBREGIRO_NOMBRE().get(i);
				descuentos[d][1] = "";
				descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getSOBREGIRO().get(i)).trim();
				d++;
				}		
			}
		}
		
		if(datos.getANTICIPO() != null){
			for (int i = 0; i < datos.getANTICIPO().size(); i++) {
				descuentos[d][0] = datos.getANTICIPO_NOMBRE().get(i).toUpperCase();
				descuentos[d][1] = "";
				descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getANTICIPO().get(i)).trim();
				d++;
			}	
		}
		
		if(datos.getSALDO_ANTERIOR() != null){
			for (int i = 0; i < datos.getSALDO_ANTERIOR().size(); i++) {
				descuentos[d][0] = datos.getSALDO_ANTERIOR_NOMBRE().get(i);
				descuentos[d][1] = "";
				descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getSALDO_ANTERIOR().get(i)).trim();
				d++;
			}	
		}
		
		if(datos.getPLAN_ADICIONAL_NO_TRIBUTABLE() != null){
		
			String UF_VALOR_PLAN = "";
			if(datos.getSALUD_UF()!= null){
				UF_VALOR_PLAN = datos.getSALUD_UF();
			}
				
			for (int i = 0; i < datos.getPLAN_ADICIONAL_NO_TRIBUTABLE().size(); i++) {
	
				String PLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE = "";
				
				if(datos.getPLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE().get(i).contains("VALOR PLAN")){
					PLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE = datos.getPLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE().get(i).replace("VALOR PLAN", "VALOR PLAN " + UF_VALOR_PLAN + " UF");
				}else{
					PLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE = datos.getPLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE().get(i);
				}
				descuentos[d][0] = PLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE;
				descuentos[d][1] = "";
				descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getPLAN_ADICIONAL_NO_TRIBUTABLE().get(i)).trim();
				d++;
			}	
		}
		
		descuentos[d][0] = "TOTAL DESCUENTOS";
		descuentos[d][1] = "";
		descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getTOTAL_DESCUENTOS()).trim();
		d++;
		
		//-----------------------TOTAL----------------------//
		
		String[][] total = new String[3][3];
		int t = 0;
		
		total[t][0] = "LIQUIDO A PAGAR";
		total[t][1] = "";
		total[t][2] = datos.getTOTAL_PAGO();
		t++; 
		
		String getTotalPago = datos.getTOTAL_PAGO() == null ? "0" : datos.getTOTAL_PAGO();
		
		total[t][0] = "SON: ";
		total[t][1] = "";
		total[t][2] = GeneralUtility.cantidadConLetra(getTotalPago.replace(".", "")).trim();
		t++;
		
		//---------------------CREAR DOCUMENTOS-----------------//
		
		int startLine = 0;
		int templateRowSimple = 0;
		
		//IMPRIMIR DATOS FIJOS
		for (int i = 0; i < datosFijos.length; i++) {
			
			String getDatosFijos = datosFijos[i][2] == "" ? "" : datosFijos[i][2];
			printListInTableHeader(templateRowSimple, startLine, table.get(0), datosFijos[i][0].trim(), getDatosFijos.toUpperCase().trim(), 8 );
			startLine++;
		}
		
		table.get(0).removeRow(0);
		
		// Numero de Fila
		int templateRowTitle = 0;
		templateRowSimple = 0;
		startLine = 0;
		
		//IMPRIMIR TODOS LOS HABERES
		for (int i = 0; i < h; i++) {
			printDetailInTable(templateRowTitle, startLine, table.get(1), haberes[i][0].trim(), haberes[i][2].trim() ,haberes[i][1].trim(), 8);
			startLine++;
		}
		
		//IMPRIMIR TODOS LOS DESCUENTOS
		for (int i = 0; i < d; i++) {
			printDetailInTable(templateRowTitle, startLine, table.get(1), descuentos[i][0], descuentos[i][1] ,descuentos[i][2], 8);
			startLine++;
		}
		/*
		table.get(1).removeRow(1);
		table.get(1).removeRow(1);
		table.get(1).removeRow(1);
		*/
		 
		//Tabla Final de Pago de Liquidacion
						
		//LIQUIDO A PAGAR
		 printListInTable(0, 0, table.get(2), total[0][0], GeneralUtility.formatStringNumberWithDotAndDecimal(total[0][2]).trim(), 8);
		 
		 //MONTO EN TEXTO
		 printListInTable(0, 1, table.get(2), total[1][0], total[1][2], 8);
		 
		 table.get(2).removeRow(2);
		 
		 //COPIA DOCUMENTO
		 
		  startLine = 0;
		  templateRowSimple = 0;
			
		  //IMPRIMIR DATOS FIJOS
			for (int i = 0; i < datosFijos.length; i++) {
				
				String getDatosFijos = datosFijos[i][2] == "" ? "" : datosFijos[i][2];
				printListInTableHeader(templateRowSimple, startLine, table.get(3), datosFijos[i][0].trim(), getDatosFijos.toUpperCase().trim(), 8 );
				startLine++;
			}
			
			table.get(3).removeRow(0);
			
			// Numero de Fila
			templateRowTitle = 0;
			templateRowSimple = 0;
			startLine = 0;
			
			//IMPRIMIR TODOS LOS HABERES
			for (int i = 0; i < h; i++) {
				printDetailInTable(templateRowTitle, startLine, table.get(4), haberes[i][0].trim(), haberes[i][2].trim() ,haberes[i][1].trim(), 8);
				startLine++;
			}
			
			//IMPRIMIR TODOS LOS DESCUENTOS
			for (int i = 0; i < d; i++) {
				printDetailInTable(templateRowTitle, startLine, table.get(4), descuentos[i][0], descuentos[i][1] ,descuentos[i][2], 8);
				startLine++;
			}
			/*
			table.get(1).removeRow(1);
			table.get(1).removeRow(1);
			table.get(1).removeRow(1);
			*/
			 
			//Tabla Final de Pago de Liquidacion
							
			//LIQUIDO A PAGAR
			 printListInTable(0, 0, table.get(5), total[0][0], GeneralUtility.formatStringNumberWithDotAndDecimal(total[0][2]).trim(), 8);
			 
			 //MONTO EN TEXTO
			 printListInTable(0, 1, table.get(5), total[1][0], total[1][2], 8);
			 
			 table.get(2).removeRow(2);
		 
		 //COPIA DOCUMENTO
		 
		/* Constructor del path */
		String fileName = filename.split("@")[0];
		String outPath = pathSalida + fileName + ".docx";
		Path path = Paths.get(outPath);

		File f = new File(outPath);
		if (f.exists() && !f.isDirectory()) {
			System.out.println("Archivo ya existe");
			f.delete();
			File f2 = new File(outPath.replace(".docx", ".pdf"));
			if (f2.exists() && !f2.isDirectory()) {
				f2.delete();
			}
		}
		
		Files.createFile(path);
		System.out.println("outPath " + outPath);
		doc.write(new FileOutputStream(outPath));
		System.out.println("Documento creado");
		doc.close();
		

		Fi.close();
		
		
		
		
		return outPath;
		
		}catch(Exception ex){
			
			throw new Exception(" ERROR: " + ex ); 
			
			
		}


	}
	
	
	public String createLiquidacionDoc(String pathSalida, String filename, GetDatosContratoTrabajador datosTrabajador,String JSONs, FileInputStream Fi,
			DatosLiquidacion datos) throws Exception {

		try{
	
	
		
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

			//setRun(p.createRun() , "Courier New" , 9, "000000" , tex , false, false, false);
			
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
		
		//----------------------DATOS DUROS---------------------------//
		String[][] datosFijos = new String[10][3];
		
		datosFijos[0][0] = "CODIGO";
		datosFijos[0][1] = "";
		datosFijos[0][2] = datosTrabajador.getCodigoTrabajador();
		
		datosFijos[1][0] = "RUT";
		datosFijos[1][1] = "";
		datosFijos[1][2] = datosTrabajador.getRutCompletoTrabajador();
		
		datosFijos[2][0] = "TRABAJADOR";
		datosFijos[2][1] = "";
		datosFijos[2][2] = datosTrabajador.getAppPatTrabajador() + " " + datosTrabajador.getAppMaternoTrabajador() + " " + datosTrabajador.getNombreTrabajador() ;
		
		datosFijos[3][0] = "FECHA CONTRATO";
		datosFijos[3][1] = "";
		datosFijos[3][2] = datosTrabajador.getFechaInicio();
		
		datosFijos[4][0] = "FECHA FIN CONTRATO";
		datosFijos[4][1] = "";
		datosFijos[4][2] = datosTrabajador.getFechaTerminoContratoFaena() == null ? " " : datosTrabajador.getFechaTerminoContratoFaena();
		
		datosFijos[5][0] = "TIPO CONTRATO";
		datosFijos[5][1] = "";
		datosFijos[5][2] = datosTrabajador.getTipoContrato();
		
		datosFijos[6][0] = "TIPO PAGO";
		datosFijos[6][1] = "";
		datosFijos[6][2] = datosTrabajador.getTipoPago();
		
		datosFijos[7][0] = "FECHA PAGO";
		datosFijos[7][1] = "";
		datosFijos[7][2] = datosTrabajador.getFechaPagoFiniquito();
		
		datosFijos[8][0] = "DIAS TRABAJADOS";
		datosFijos[8][1] = "";
		datosFijos[8][2] = datos.getDIAS_TRAJADOS();
		
		datosFijos[9][0] = "LIQUIDO MES";
		datosFijos[9][1] = "";
		datosFijos[9][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getTOTAL_LIQUIDO_MES());
		
		
		//----------------------HABERES------------------------------//
		
		String[][] haberes = new String[30][3]; 
		int h = 0;
		
		haberes[h][0] = "1. HABERES IMPONIBLES";
		haberes[h][1] = "";
		haberes[h][2] = "TOTAL";
		h++;
		
		haberes[h][0] = "SUELDO BASE (" + GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getSUELDO_BASE_CONTRATO()) + ") "; 
		haberes[h][1] = "";
		haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getSUELDO_BASE()).trim();
		h++;
		
		haberes[h][0] = "GRATIFICACION";
		haberes[h][1] = "";
		haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getGRATIFICACION()).trim();
		h++;
		
		//OBTENER BONOS IMPONIBLES
		if(datos.getBONO() != null){
		for (int i = 0; i < datos.getBONO().size(); i++) {
					
			haberes[h][0] = datos.getBONO_NOMBRE().get(i);
			haberes[h][1] = "";
			haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getBONO().get(i)).trim();
			h++;
						
		}
		}
		
		if(datos.getHORAS_EXTRA() != null){
		for (int i = 0; i < datos.getHORAS_EXTRA().size(); i++) {
					
			haberes[h][0] = datos.getHORAS_EXTRA_NOMBRE().get(i) + " ( " + NumericUtility.formatStringNumberWithDotAnd2Decimals(datos.getHORAS_EXTRA_NHORAS().get(i)) + " ) " + NumericUtility.formatStringNumberWithTest2(datos.getHORAS_EXTRA_VHORAS().get(i));
			haberes[h][1] = "";
			haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getHORAS_EXTRA().get(i)).trim();
			h++;
								
		}
		}
			
		if(datos.getHORAS_FALTA() != null){
		for (int i = 0; i < datos.getHORAS_FALTA().size(); i++) {
			//Terminar ACA TODO:			
			haberes[h][0] = datos.getHORAS_FALTA_NOMBRE().get(i) + " ( " + NumericUtility.formatStringNumberWithDotAnd2Decimals(datos.getHORAS_FALTA_NHORAS().get(i)) + " ) " + NumericUtility.formatStringNumberWithTest2(datos.getHORAS_FALTA_VHORAS().get(i)) ;
			haberes[h][1] = "";
			haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getHORAS_FALTA().get(i)).trim();
			h++;
									
		}
		}
		
		haberes[h][0] = "TOTAL HABERES IMPONIBLES";
		haberes[h][1] = "";
		haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getTOTAL_HAB_IMPONIBLE()).trim();
		h++;
		
		if(datos.getTOTAL_HAB_NO_IMPONIBLE() != null){
			haberes[h][0] = "2. HABERES NO IMPONIBLES";
			haberes[h][1] = "";
			haberes[h][2] = "TOTAL";
			h++;
		}
		
		if(datos.getCARGA_FAMILIAR_SIMPLE() != null){
			
			Double TOTAL_CARGA_FAMILIAR1 = Double.valueOf(datos.getCARGA_FAMILIAR_SIMPLE());
			String TOTAL_CARGA_FAMILIAR_STRING1 = String.valueOf(TOTAL_CARGA_FAMILIAR1);
			
			haberes[h][0] = datos.getCARGA_FAMILIAR_SIMPLE_NOMBRE().toUpperCase() + " ( " + datos.getCARGA_FAMILIAR_SIMPLE_CANTIDAD() + " ) " ;
			haberes[h][1] = "";
			haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(TOTAL_CARGA_FAMILIAR_STRING1.substring(0, TOTAL_CARGA_FAMILIAR_STRING1.indexOf("."))).trim();
			h++;
		}
		
		if(datos.getCARGA_FAMILIAR_MATERNAL() != null){
			
			Double TOTAL_CARGA_FAMILIAR2 = Double.valueOf(datos.getCARGA_FAMILIAR_MATERNAL());
			String TOTAL_CARGA_FAMILIAR_STRING2 = String.valueOf(TOTAL_CARGA_FAMILIAR2);
			
			haberes[h][0] = datos.getCARGA_FAMILIAR_MATERNAL_NOMBRE().toUpperCase() + " ( " + datos.getCARGA_FAMILIAR_MATERNAL_CANTIDAD() + " ) " ;
			haberes[h][1] = "";
			haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(TOTAL_CARGA_FAMILIAR_STRING2.substring(0, TOTAL_CARGA_FAMILIAR_STRING2.indexOf(".")).trim());
			h++;
		}
		
		if(datos.getCARGA_FAMILIAR_RETROACTIVO() != null){
			
			Double TOTAL_CARGA_FAMILIAR3 = (Double.valueOf(datos.getCARGA_FAMILIAR_RETROACTIVO())) ;
			String TOTAL_CARGA_FAMILIAR_STRING3 = String.valueOf(TOTAL_CARGA_FAMILIAR3);
			
			haberes[h][0] = datos.getCARGA_FAMILIAR_RETROACTIVO_NOMBRE().toUpperCase() + " ( " + datos.getCARGA_FAMILIAR_RETROACTIVO_CANTIDAD() + " ) " ;
			haberes[h][1] = "";
			haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(TOTAL_CARGA_FAMILIAR_STRING3.substring(0, TOTAL_CARGA_FAMILIAR_STRING3.indexOf(".")).trim());
			h++;
		}
		
		if(datos.getBONO_NO_IMPONIBLE() != null){
		for (int i = 0; i < datos.getBONO_NO_IMPONIBLE().size(); i++) {
			
			haberes[h][0] = datos.getBONO_NO_IMPONIBLE_NOMBRE().get(i);
			haberes[h][1] = "";
			haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getBONO_NO_IMPONIBLE().get(i)).trim();
			h++;
						
		}
		}
		
		if(datos.getCONCEPTO_8() != null){
			for (int i = 0; i < datos.getBONO_NO_IMPONIBLE().size(); i++) {
			haberes[h][0] = datos.getCONCEPTO_8_NOMBRE().get(i);
			haberes[h][1] = "";
			haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getCONCEPTO_8().get(i)).trim();
			h++;
			}
		}
		
		
		if(datos.getTOTAL_HAB_NO_IMPONIBLE() != null){
		
		haberes[h][0] = "TOTAL HABERES NO IMPONIBLES";
		haberes[h][1] = "";
		haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getTOTAL_HAB_NO_IMPONIBLE()).trim();
		h++;
		
		}
		
		//Mostrar Haber Imponible o Tope Maximo
		String topeImponible = datos.getTOTAL_HABERES() == null ? "0" : datos.getTOTAL_HABERES();
		
		if(!("0".equals(topeImponible))){
			if(Double.valueOf(datos.getTOTAL_HABERES()) > Double.valueOf(datos.getTOPE_IMPONIBLE())){
				topeImponible = datos.getTOPE_IMPONIBLE();
			}
		}
		
		haberes[h][0] = "TOPE IMPONIBLE";
		haberes[h][1] = "";
		haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(topeImponible).trim();
		h++;
		
		haberes[h][0] = "TOTAL HABERES";
		haberes[h][1] = "";
		haberes[h][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getTOTAL_HABERES()).trim();
		h++;
		
		//----------------------DESCUENTOS------------------------------//
		
		String[][] descuentos = new String[30][3];
		int d = 0;
		
		descuentos[d][0] = "1. DESCUENTOS LEGALES";
		descuentos[d][1] = "";
		descuentos[d][2] = "TOTAL";
		d++;
		
		String afp = "";
		if(datos.getAFP_NOMBRE() == null && datos.getAFP_PORCENTAJE() == null){
			afp = "PENSIONADO";
		}else{
			afp = datos.getAFP_NOMBRE() + "(" + datos.getAFP_PORCENTAJE() + "%)";
		}
		
		
		descuentos[d][0] = "DESCUENTOS PREVISIONAL";
		descuentos[d][1] = afp;
		descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getAFP()).trim();
		d++;
		
		String caja = datos.getCAJA() == null ? "0" : datos.getCAJA();
		String caja_porcentaje = datos.getCAJA_PORCENTAJE() == null ? "0" : datos.getCAJA_PORCENTAJE();
		
		String getSalud = datos.getSALUD() == null ? "0" : datos.getSALUD();
		String getSaludPorcentaje = datos.getSALUD_PORCENTAJE() == null ? "0" : datos.getSALUD_PORCENTAJE(); 
		
		String salud = String.valueOf(Double.valueOf(getSalud) + Double.valueOf(caja));
		String salud_porcentaje = String.valueOf(Double.valueOf(getSaludPorcentaje.replace(",", ".")) + Double.valueOf(caja_porcentaje.replace(",", ".")));
		String salud_nombre = datos.getSALUD_NOMBRE()+ " " +"("+salud_porcentaje+"%)";
			
		descuentos[d][0] = "DESCUENTOS SALUD " + salud_nombre;
		descuentos[d][1] = "";
		descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(salud.substring(0, salud.indexOf("."))).trim();
		d++;
		
		if(datos.getSEGURO_CESANTIA_AFC_TRABAJADOR() != null){
		
			descuentos[d][0] = "SEGURO CESANTIA TRABAJADOR";
			descuentos[d][1] = "";
			descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getSEGURO_CESANTIA_AFC_TRABAJADOR()).trim();
			d++;
		}
		
		if(datos.getIMPUESTO_UNICO() != null ){
			for (int i = 0; i < datos.getIMPUESTO_UNICO().size(); i++) {
				if(!"0".equals(datos.getIMPUESTO_UNICO().get(i))){
				descuentos[d][0] = datos.getIMPUESTO_UNICO_NOMBRE().get(i).toUpperCase();
				descuentos[d][1] = "";
				descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getIMPUESTO_UNICO().get(i)).trim();
				d++;
				}
			}
		}
		
		if(datos.getAHORRO_VOLUNTARIO_AFP() != null && !"0".equals(datos.getAHORRO_VOLUNTARIO_AFP())){
			descuentos[d][0] = "AHORRO VOLUNTARIO AFP";
			descuentos[d][1] = "";
			descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getAHORRO_VOLUNTARIO_AFP()).trim();
			d++;
		}
		
		if(datos.getPLAN_ADICIONAL_TRIBUTABLE() != null ){
			for (int i = 0; i < datos.getPLAN_ADICIONAL_TRIBUTABLE().size(); i++) {
				if(!"0".equals(datos.getPLAN_ADICIONAL_TRIBUTABLE().get(i))){
					descuentos[d][0] = datos.getPLAN_ADICIONAL_TRIBUTABLE_NOMBRE().get(i).toUpperCase();
					descuentos[d][1] = "";
					descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getPLAN_ADICIONAL_TRIBUTABLE().get(i)).trim();
					d++;
				}		
			}
		}
		
		
		if(datos.getAPV() != null){
			descuentos[d][0] = "APV "+ datosTrabajador.getApvTrabajador();
			descuentos[d][1] = "";
			descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getAPV()).trim();
			d++;
		}
		
		if( datos.getAPV_ADICIONAL() != null && !"0".equals(datos.getAPV_ADICIONAL()) ){
			descuentos[d][0] = "VALOR APV ADICIONAL NO IMPONIBLE";
			descuentos[d][1] = "";
			descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getAPV_ADICIONAL()).trim();
			d++;
		}
		
		
		if(datos.getDESCUENTOS() != null){
			for (int i = 0; i < datos.getDESCUENTOS().size(); i++) {
				descuentos[d][0] = datos.getDESCUENTOS_NOMBRE().get(i);
				descuentos[d][1] = "";
				descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getDESCUENTOS().get(i)).trim();
				d++;
			}
		}
		
//		if(datos.getCONCEPTO_MEDICO() != null ){
//			for (int i = 0; i < datos.getCONCEPTO_MEDICO().size(); i++) {
//				if(!"0".equals(datos.getCONCEPTO_MEDICO().get(i))){
//					descuentos[d][0] = datos.getCONCEPTO_MEDICO_NOMBRE().get(i);
//					descuentos[d][1] = "";
//					descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getCONCEPTO_MEDICO().get(i));
//					d++;
//				}		
//			}
//		}
		
		if(datos.getSOBREGIRO() != null ){
		for (int i = 0; i < datos.getSOBREGIRO().size(); i++) {
			if(!"0".equals(datos.getSOBREGIRO().get(i))){
				descuentos[d][0] = datos.getSOBREGIRO_NOMBRE().get(i);
				descuentos[d][1] = "";
				descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getSOBREGIRO().get(i)).trim();
				d++;
				}		
			}
		}
		
		if(datos.getANTICIPO() != null){
			for (int i = 0; i < datos.getANTICIPO().size(); i++) {
				descuentos[d][0] = datos.getANTICIPO_NOMBRE().get(i).toUpperCase();
				descuentos[d][1] = "";
				descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getANTICIPO().get(i)).trim();
				d++;
			}	
		}
		
		if(datos.getSALDO_ANTERIOR() != null){
			for (int i = 0; i < datos.getSALDO_ANTERIOR().size(); i++) {
				descuentos[d][0] = datos.getSALDO_ANTERIOR_NOMBRE().get(i);
				descuentos[d][1] = "";
				descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getSALDO_ANTERIOR().get(i)).trim();
				d++;
			}	
		}
		
		if(datos.getPLAN_ADICIONAL_NO_TRIBUTABLE() != null){
		
			String UF_VALOR_PLAN = "";
			if(datos.getSALUD_UF()!= null){
				UF_VALOR_PLAN = datos.getSALUD_UF();
			}
				
			for (int i = 0; i < datos.getPLAN_ADICIONAL_NO_TRIBUTABLE().size(); i++) {
	
				String PLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE = "";
				
				if(datos.getPLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE().get(i).contains("VALOR PLAN")){
					PLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE = datos.getPLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE().get(i).replace("VALOR PLAN", "VALOR PLAN " + UF_VALOR_PLAN + " UF");
				}else{
					PLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE = datos.getPLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE().get(i);
				}
				descuentos[d][0] = PLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE;
				descuentos[d][1] = "";
				descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getPLAN_ADICIONAL_NO_TRIBUTABLE().get(i)).trim();
				d++;
			}	
		}
		
		descuentos[d][0] = "TOTAL DESCUENTOS";
		descuentos[d][1] = "";
		descuentos[d][2] = GeneralUtility.formatStringNumberWithDotAndDecimal(datos.getTOTAL_DESCUENTOS()).trim();
		d++;
		
		//-----------------------TOTAL----------------------//
		
		String[][] total = new String[3][3];
		int t = 0;
		
		total[t][0] = "LIQUIDO A PAGAR";
		total[t][1] = "";
		total[t][2] = datos.getTOTAL_PAGO();
		t++; 
		
		String getTotalPago = datos.getTOTAL_PAGO() == null ? "0" : datos.getTOTAL_PAGO();
		
		total[t][0] = "SON: ";
		total[t][1] = "";
		total[t][2] = GeneralUtility.cantidadConLetra(getTotalPago.replace(".", "")).trim();
		t++;
		
		//---------------------CREAR DOCUMENTOS-----------------//
		
		int startLine = 1;
		int templateRowSimple = 0;
		
		//IMPRIMIR DATOS FIJOS
		for (int i = 0; i < datosFijos.length; i++) {
			
			String getDatosFijos = datosFijos[i][2] == null ? "0" : datosFijos[i][2];
			printListInTableHeader(templateRowSimple, startLine, table.get(0), datosFijos[i][0].trim(), getDatosFijos.toUpperCase().trim(), 9 );
			startLine++;
		}
		
		table.get(0).removeRow(0);
		
		// Numero de Fila
		int templateRowTitle = 2;
		templateRowSimple = 3;
		startLine = 4;
		System.out.println(startLine);
		
		//IMPRIMIR TODOS LOS HABERES
		for (int i = 0; i < h; i++) {
			printListInTable(haberes[i][2].matches(".*\\d+.*") ? templateRowSimple : templateRowTitle, startLine, table.get(1), haberes[i][0].trim(), haberes[i][2].trim(), 9);
			startLine++;
		}
		
		//remover la fila template
		table.get(1).removeRow(2);
		table.get(1).removeRow(2);
		
		//Segunda Tabla Descuentos
		templateRowTitle  = 1;
		templateRowSimple = 2;
		int templateRowDetail = 3;
		startLine = 4;
		
for (int i = 0; i < d; i++) {
			
			if(i == 0){
				//TITULO DESCUENTOS
				printListInTable(templateRowTitle, startLine, table.get(2), descuentos[i][0], descuentos[i][2], 9);
				startLine++;
			}
			else if(i >= 1 && i <= 2){
				//DESCUENTOS PREVISIONALES AFP //DESCUENTOS SALUD
				if(descuentos[1][2] != null){
				printDetailInTable(templateRowDetail, startLine, table.get(2), descuentos[i][0], descuentos[i][1], descuentos[i][2], 9);
				startLine++;
				}
			}
			else{
				printListInTable(descuentos[i][2].matches(".*\\d+.*") ? templateRowTitle : templateRowTitle, startLine, table.get(2), descuentos[i][0], descuentos[i][2], 9);
				startLine++;
			}
			
			
		}
		
		table.get(2).removeRow(1);
		table.get(2).removeRow(1);
		table.get(2).removeRow(1);
		 
		//Tabla Final de Pago de Liquidacion
		
		// Numero de Fila
		 int templateRowLiquido = 0;
		 int templateRowLiquidoText = 1;
		 startLine = 2;
				
		//LIQUIDO A PAGAR
		 printListInTable(templateRowLiquido, startLine, table.get(3), total[0][0], GeneralUtility.formatStringNumberWithDotAndDecimal(total[0][2]).trim(), 9);
		 startLine++;
		 
		 //MONTO EN TEXTO
		 printListInTable(templateRowLiquidoText, startLine, table.get(3), total[1][0], total[1][2], 9);
		 startLine++;
		
		 table.get(3).removeRow(0);
		 table.get(3).removeRow(0);
		 
		 
		 

		/* Constructor del path */
		String fileName = filename.split("@")[0];
		String outPath = pathSalida + fileName + ".docx";
		Path path = Paths.get(outPath);

		File f = new File(outPath);
		if (f.exists() && !f.isDirectory()) {
			System.out.println("Archivo ya existe");
			f.delete();
			File f2 = new File(outPath.replace(".docx", ".pdf"));
			if (f2.exists() && !f2.isDirectory()) {
				f2.delete();
			}
		}
		
		Files.createFile(path);
		System.out.println("outPath " + outPath);
		doc.write(new FileOutputStream(outPath));
		System.out.println("Documento creado");
		doc.close();
		

		Fi.close();
		
		
		
		
		return outPath;
		
		}catch(Exception ex){
			
			throw new Exception(" ERROR: " + ex ); 
			
			
		}


	}
	
	
	
}

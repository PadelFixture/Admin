package lib.excelSA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import lib.classSA.LIQUIDACION;
import lib.classSA.RENDIMIENTO_DIARIO;
import lib.classSA.cierre_mensual;
import lib.db.ConnectionDB;
import wordCreator.utils;

public class excelAgro {

	public String GENERAR_EXCEL_LISTADO(ArrayList<RENDIMIENTO_DIARIO> row) throws Exception{
		String urlDocGenerado = utils.reportesExcel();
		urlDocGenerado=urlDocGenerado+"listado"+".xlsx";
		File archivo = new File(urlDocGenerado);
		try {
			SXSSFWorkbook workbook = new SXSSFWorkbook();
	        SXSSFSheet sheet = workbook.createSheet();
	        workbook.setSheetName(0, "Listado Rendimiento");
	        String[] headers = new String[]{
                "Fecha",
                "Supervisor",
                "Rut",
                "Trabajador",
                "Tipo",
                "Contratista",
                "Fundo",
                "Etnia",
                "Especie",
                "Variedad",
                "Cuartel",
                "Agrupación",
                "OrdenCO",
                "CeCO",
                "Estado CeCo",
                "Faena",
                "Labor",
                "Horas Trabajadas",
                "N Personas",
                "Horas Extras",
                "Valor Horas Extras",
                "Valor Pagado",
                "Monto Horas Extras",
                "Horas Extras 2",
                "Valor Horas Extras 2",
                "Bono 2",
                "Base Piso",
                "Tipo Pago",
                "Valor",
                "Valor Trato",
                "Rendimiento",
                "Valor Rendimiento",
                "Bono",
                "Subsidio",
                "Valor Líquido",
                "Maquinaria",
                "Implemento",
                "Estado",
                "Costo Empresa"
	        };
	        CellStyle headerStyle = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setBold(true);
	        headerStyle.setFont(font);
	        
	        CellStyle style = workbook.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        SXSSFRow headerRow = sheet.createRow(0);
	        
	        for (int i = 0; i < headers.length; ++i) {
	            String header = headers[i];
	            SXSSFCell cell = headerRow.createCell(i);
	            cell.setCellStyle(headerStyle);
	            cell.setCellValue(header);
	        }
	        int i = 1;
	        for(RENDIMIENTO_DIARIO rd: row){
	        	SXSSFRow dataRow = sheet.createRow(i);
	        	dataRow.createCell(0).setCellValue(rd.getFecha());
	        	dataRow.createCell(1).setCellValue(rd.getSupervisor());
	        	dataRow.createCell(2).setCellValue(rd.getRut());
	        	dataRow.createCell(3).setCellValue(rd.getNombre());
	        	if(rd.getIdContratista().equals("0")){
	        		dataRow.createCell(4).setCellValue("PROPIO");
	        	}else{
	        		dataRow.createCell(4).setCellValue("CONTRATISTA");
	        	}
	        	dataRow.createCell(5).setCellValue(rd.getIdContratista());
	        	dataRow.createCell(6).setCellValue(rd.getDescripcion());
	        	dataRow.createCell(7).setCellValue(rd.getEtnia());
	        	dataRow.createCell(8).setCellValue(rd.getnEspecie());
	        	dataRow.createCell(9).setCellValue(rd.getnVariedad());
	        	dataRow.createCell(10).setCellValue(rd.getNvnombre());
	        	dataRow.createCell(11).setCellValue(rd.getMacroco());
	        	dataRow.createCell(12).setCellValue(rd.getOrdenco());
	        	dataRow.createCell(13).setCellValue(rd.getCeco());
	        	if(rd.getEstado() == 1){
	        		dataRow.createCell(14).setCellValue("Productivo");
	        	}else{
	        		dataRow.createCell(14).setCellValue("No Productivo");
	        	}
	        	dataRow.createCell(15).setCellValue(rd.getnFaena());
	        	dataRow.createCell(16).setCellValue(rd.getnLabor());
	        	dataRow.createCell(17).setCellValue(rd.getHoras_trabajadas());
	        	dataRow.createCell(18).setCellValue(rd.getN_personas());
	        	dataRow.createCell(19).setCellValue(rd.getHoras_extras());
	        	dataRow.createCell(20).setCellValue(rd.getValor_hx());
	        	dataRow.createCell(21).setCellValue(rd.getRes_hx() + rd.getValor_hx());
	        	dataRow.createCell(22).setCellValue(rd.getMonto_hx());
	        	dataRow.createCell(23).setCellValue(rd.getHx_dos());
	        	dataRow.createCell(24).setCellValue(rd.getValor_hx_dos());
	        	dataRow.createCell(25).setCellValue(rd.getBono2());
	        	if(rd.getBase_piso_hora() == 1){
	        		dataRow.createCell(26).setCellValue("SI");
	        	}else{
	        		dataRow.createCell(26).setCellValue("NO");
	        	}
	        	if(rd.getTipo_pago() == 1){
	        		dataRow.createCell(27).setCellValue("DIA");
	        	}else if(rd.getTipo_pago() == 2){
	        		dataRow.createCell(27).setCellValue("TRATO");
	        	}else{
	        		dataRow.createCell(27).setCellValue("MIXTO");
	        	}
	        	dataRow.createCell(28).setCellValue(rd.getValor());
	        	dataRow.createCell(29).setCellValue(rd.getValor_trato());
	        	dataRow.createCell(30).setCellValue(Double.valueOf(String.valueOf(rd.getRendimiento())));
	        	dataRow.createCell(31).setCellValue(rd.getValor_rendimiento());
	        	dataRow.createCell(32).setCellValue(rd.getBono1());
	        	dataRow.createCell(33).setCellValue(rd.getSubsidio());
	        	dataRow.createCell(34).setCellValue(rd.getValor_liquido());
	        	dataRow.createCell(35).setCellValue(rd.getnMaquinaria());
	        	dataRow.createCell(36).setCellValue(rd.getnImplemento());
	        	dataRow.createCell(37).setCellValue(rd.getNestado());
	        	dataRow.createCell(38).setCellValue(rd.getCosto_empresa());
	        	if(!rd.getIdContratista().equals("0")){
	        		dataRow.createCell(38).setCellValue(rd.getValor_liquido());
	        	}
	        	i++;
	        }
	        SXSSFRow dataRow = sheet.createRow(1 + row.size());
	        SXSSFCell total = dataRow.createCell(31);
	        total.setCellType(CellType.FORMULA);
	        total.setCellStyle(style);
	        total.setCellFormula(String.format("SUM(AF2:AF%d)", 1 + row.size()));
	        FileOutputStream salida = new FileOutputStream(archivo);
	        workbook.write(salida);
	        workbook.close();
	        salida.close();
	        System.out.println("Archivo creado existosamente");
	        return archivo.getName();
		}catch (FileNotFoundException ex) {
			System.out.println("Archivo no localizable en sistema de archivos");
		    return "0";
		} catch (IOException ex) {
			System.out.println("Error de entrada/salida");
	        return "0";
		}
	}
	public String GENERAR_EXCEL_LIBROCAMPO(String row) throws Exception{
		JSONArray data = new JSONArray(row);
		String urlDocGenerado = utils.reportesExcel();
		try {
			SXSSFWorkbook workbook = new SXSSFWorkbook();
	        SXSSFSheet sheet = workbook.createSheet();
	        sheet.getPrintSetup().setLandscape(true);
	        sheet.setFitToPage(true);
			PrintSetup ps = sheet.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);
	        workbook.setSheetName(0, "Libro Campo");
	        String[] headers = new String[]{
                "N OA",
                "Predio",
                "Especie",
                "Variedad",
                "Cuartel",
                "Ha Cuartel",
                "Fecha Inicio",
                "Hora Inicio",
                "Fecha Termino",
                "Hora Termino",
                "Para Control de",
                "Nombre Comercial",
                "Ingrediente Activo",
                "Fecha Reingreso",
                "Dosis 100",
                "UM",
                "Dosis Has",
                "UM",
                "Real (Lts/ha)",
                "Forma Aplicación",
                "Pesona que aplicó",
                "N° Tractor",
                "N° Maq",
                "Dosificador",
                "Mercado",
                "Inicio Potencial Cosecha",
                "Cantidad Real",
                "Orientacion Viento",
                "Velocidad Viento",
                "Temperatura"
	        };
	        CellStyle headerStyle = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setBold(true);
	        headerStyle.setFont(font);
	        
	        CellStyle style = workbook.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        SXSSFRow headerRow = sheet.createRow(0);
	        
	        for (int i = 0; i < headers.length; ++i) {
	            String header = headers[i];
	            SXSSFCell cell = headerRow.createCell(i);
	            cell.setCellStyle(headerStyle);
	            cell.setCellValue(header);
	        }
	        int i = 1;
	        for(int ix = 0; ix < data.length(); ix++){
	        	JSONObject e = new JSONObject(data.getJSONObject(ix).toString());
	        	SXSSFRow dataRow = sheet.createRow(i);
	        	dataRow.createCell(0).setCellValue(e.getInt("id_orden"));
	        	dataRow.createCell(1).setCellValue(e.getString("campo").replace("Ã­", "í").replace("Ã©", "é"));
	        	dataRow.createCell(2).setCellValue(e.getString("especie"));
	        	dataRow.createCell(3).setCellValue(e.getString("variedad"));
	        	dataRow.createCell(4).setCellValue(e.getString("cuartel").replace("Ã­", "í").replace("Ã©", "é"));
	        	dataRow.createCell(5).setCellValue(new BigDecimal(e.getDouble("has")).doubleValue());
	        	dataRow.createCell(6).setCellValue(e.getString("fecha_inicio"));
	        	dataRow.createCell(7).setCellValue(e.getString("hora_inicio"));
	        	dataRow.createCell(8).setCellValue(e.getString("fecha_termino"));
	        	dataRow.createCell(9).setCellValue(e.getString("hora_termino"));
	        	dataRow.createCell(10).setCellValue(e.getString("control_aplicacion").replace("Ã±", "ñ"));
	        	dataRow.createCell(11).setCellValue(e.getString("arrMaterial"));
	        	dataRow.createCell(12).setCellValue(e.getString("inActivo"));
	        	dataRow.createCell(13).setCellValue(e.getString("arrReingreso"));
	        	dataRow.createCell(14).setCellValue(e.getFloat("dosis_100"));
	        	dataRow.createCell(15).setCellValue(e.getString("um"));
	        	dataRow.createCell(16).setCellValue(e.getFloat("dosis_has"));
	        	dataRow.createCell(17).setCellValue(e.getString("arrUm"));
	        	dataRow.createCell(18).setCellValue(e.getInt("mojamiento"));
	        	dataRow.createCell(19).setCellValue(e.getString("forma_aplicacion"));
	        	dataRow.createCell(20).setCellValue(e.getString("aplicador"));
	        	dataRow.createCell(21).setCellValue(e.getString("maquinaria"));
	        	dataRow.createCell(22).setCellValue(e.getString("implemento"));
	        	dataRow.createCell(23).setCellValue(e.getString("jefe_aplicacion"));
	        	dataRow.createCell(24).setCellValue(e.getString("mercado"));
	        	dataRow.createCell(25).setCellValue(e.getString("fecha_estimada_cosecha"));
	        	dataRow.createCell(26).setCellValue(new BigDecimal(e.getDouble("cantidad_real")).doubleValue());
	        	dataRow.createCell(27).setCellValue(e.getString("orientacion_viento"));
	        	dataRow.createCell(28).setCellValue(e.getString("velocidad_viento"));
	        	dataRow.createCell(29).setCellValue(e.getString("temperatura"));
				i++;
			}
			urlDocGenerado=urlDocGenerado+"libroCampo.xlsx";
			File archivo = new File(urlDocGenerado);
	        FileOutputStream salida = new FileOutputStream(archivo);
	        workbook.write(salida);
	        workbook.close();
	        salida.close();
	        System.out.println("Archivo creado existosamente");
	        return archivo.getName();
		}catch (FileNotFoundException ex) {
			System.out.println("Archivo no localizable en sistema de archivos");
		    return "0";
		} catch (IOException ex) {
			System.out.println("Error de entrada/salida");
	        return "0";
		}
	}
	public String GENERAR_EXCEL_CIERRE_MENSUAL(ArrayList<cierre_mensual> row) throws Exception{
		String urlDocGenerado = utils.reportesExcel();
		try {
			SXSSFWorkbook workbook = new SXSSFWorkbook();
	        SXSSFSheet sheet = workbook.createSheet();
	        sheet.getPrintSetup().setLandscape(true);
	        sheet.setFitToPage(true);
			PrintSetup ps = sheet.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);
	        workbook.setSheetName(0, "Centralizacion Agro");
	        String[] headers = new String[]{
                "Codigo",
                "Trabajador",
                "Cuenta",
                "Centro de Costo (Nombre)",
                "Centro de Costo (Codigo)",
                "OrdenCo (Nombre)",
                "OrdenCo (Codigo)",
                "Periodo",
                "Sociedad Centralizacion",
                "Sociedad Imputacion",
                "Valor Liquido",
                "Porcentaje",
                "Costo Empresa",
                "Horas Extras %",
                "Bono %",
                "Bono 2 %",
                "Valor Rendimiento %",
                "Base Dia %"
	        };
	        CellStyle headerStyle = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setBold(true);
	        headerStyle.setFont(font);
	        
	        CellStyle style = workbook.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        SXSSFRow headerRow = sheet.createRow(0);
	        
	        for (int i = 0; i < headers.length; ++i) {
	            String header = headers[i];
	            SXSSFCell cell = headerRow.createCell(i);
	            cell.setCellStyle(headerStyle);
	            cell.setCellValue(header);
	        }
	        int i = 1;
	        for(cierre_mensual e: row){
	        	SXSSFRow dataRow = sheet.createRow(i);
	        	dataRow.createCell(0).setCellValue(e.getCodigo());
	        	dataRow.createCell(1).setCellValue(e.getTrabajador());
	        	dataRow.createCell(2).setCellValue(e.getCuenta());
	        	dataRow.createCell(3).setCellValue(e.getCeco());
	        	dataRow.createCell(4).setCellValue(e.getNombreCeco());
	        	dataRow.createCell(5).setCellValue(e.getOrdenco());
	        	dataRow.createCell(6).setCellValue(e.getNombreOrdenCo());
	        	dataRow.createCell(7).setCellValue(e.getPeriodo());
	        	dataRow.createCell(8).setCellValue(e.getSociedadCentralizacion());
	        	dataRow.createCell(9).setCellValue(e.getSociedadImputacion());
	        	dataRow.createCell(10).setCellValue(e.getValor());
	        	dataRow.createCell(11).setCellValue(Double.valueOf(String.valueOf(e.getPercent())));
	        	dataRow.createCell(12).setCellValue(e.getCosto_empresa());
	        	dataRow.createCell(13).setCellValue(Double.valueOf(String.valueOf(e.getP_hx())));
	        	dataRow.createCell(14).setCellValue(Double.valueOf(String.valueOf(e.getP_bono())));
	        	dataRow.createCell(15).setCellValue(Double.valueOf(String.valueOf(e.getP_bono_dos())));
	        	dataRow.createCell(16).setCellValue(Double.valueOf(String.valueOf(e.getP_valor_rendimiento())));
	        	dataRow.createCell(17).setCellValue(Double.valueOf(String.valueOf(e.getP_base_dia())));
	        	i++;
	        }
			urlDocGenerado=urlDocGenerado+"CierrerMensual.xlsx";
			File archivo = new File(urlDocGenerado);
	        FileOutputStream salida = new FileOutputStream(archivo);
	        workbook.write(salida);
	        workbook.close();
	        salida.close();
	        System.out.println("Archivo creado existosamente");
	        return archivo.getName();
		}catch (FileNotFoundException ex) {
			System.out.println("Archivo no localizable en sistema de archivos");
		    return "0";
		} catch (IOException ex) {
			System.out.println("Error de entrada/salida");
	        return "0";
		}
		//return false;
		//return "0";
	}
	public String EXCEL_GASTOS_CECO(String data) throws Exception{
		String urlDocGenerado = utils.reportesExcel();
		JSONArray r = new JSONArray(data);
		try {
			SXSSFWorkbook workbook = new SXSSFWorkbook();
	        SXSSFSheet sheet = workbook.createSheet();
	        sheet.getPrintSetup().setLandscape(true);
	        sheet.setFitToPage(true);
			PrintSetup ps = sheet.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);
	        workbook.setSheetName(0, "Centralizacion Agro Terceros");
	        String[] headers = new String[]{
                "Scciedad",
                "Concepto",
                "Descripcion",
                "Proveedor",
                "Cuenta",
                "Codigo Ceco",
                "Descripcion Ceco",
                "Codigo OrdenCo",
                "Descripcion OrdenCo",
                "Valor"
	        };
	        CellStyle headerStyle = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setBold(true);
	        headerStyle.setFont(font);
	        
	        CellStyle style = workbook.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        SXSSFRow headerRow = sheet.createRow(0);
	        
	        for (int i = 0; i < headers.length; ++i) {
	            String header = headers[i];
	            SXSSFCell cell = headerRow.createCell(i);
	            cell.setCellStyle(headerStyle);
	            cell.setCellValue(header);
	        }
	        int i = 1;
	        for(int ix = 0; ix < r.length(); ix++){
	        	JSONObject e = new JSONObject(r.getJSONObject(ix).toString());
	        	SXSSFRow dataRow = sheet.createRow(i);
	        	dataRow.createCell(0).setCellValue(e.getString("id_sociedad"));
	        	dataRow.createCell(1).setCellValue(e.getString("concepto"));
	        	dataRow.createCell(2).setCellValue(e.getString("descripcion"));
	        	dataRow.createCell(3).setCellValue(e.getString("proveedor"));
	        	dataRow.createCell(4).setCellValue(e.getString("cuenta"));
	        	dataRow.createCell(5).setCellValue(e.getString("idCECO"));
	        	dataRow.createCell(6).setCellValue(e.getString("descCeco"));
	        	dataRow.createCell(7).setCellValue(e.getString("ordenCO"));
	        	dataRow.createCell(8).setCellValue(e.getString("descOrdenCo"));
	        	dataRow.createCell(9).setCellValue(e.getInt("valor"));
				i++;
			}
			urlDocGenerado=urlDocGenerado+"Informe Gastos por Ceco.xlsx";
			File archivo = new File(urlDocGenerado);
	        FileOutputStream salida = new FileOutputStream(archivo);
	        workbook.write(salida);
	        workbook.close();
	        salida.close();
	        System.out.println("Archivo creado existosamente");
	        return archivo.getName();
		}catch (FileNotFoundException ex) {
			System.out.println("Archivo no localizable en sistema de archivos");
		    return "0";
		} catch (IOException ex) {
			System.out.println("Error de entrada/salida");
	        return "0";
		}
		//return false;
		//return "0";
	}
	public String GENERAR_EXCEL_REPORTE_CIERRE(ArrayList<cierre_mensual> row) throws Exception{
		String urlDocGenerado = utils.reportesExcel();
		
		try {
			SXSSFWorkbook workbook = new SXSSFWorkbook();
	        SXSSFSheet sheet = workbook.createSheet();
	        sheet.getPrintSetup().setLandscape(true);
	        sheet.setFitToPage(true);
			PrintSetup ps = sheet.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);
	        workbook.setSheetName(0, "Centralizacion Agro Terceros");
	        String[] headers = new String[]{
                "Trabajador",
                "Cuenta",
                "Centro de Costo",
                "Periodo",
                "Sociedad Centralizacion",
                "Nombre Sociedad Centralizacion",
                "Sociedad Imputacion",
                "Nombre Sociedad Imputacion",
                "Valor Liquido",
                "Porcentaje",
                "Costo Empresa"
	        };
	        CellStyle headerStyle = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setBold(true);
	        headerStyle.setFont(font);
	        
	        CellStyle style = workbook.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        SXSSFRow headerRow = sheet.createRow(0);
	        
	        for (int i = 0; i < headers.length; ++i) {
	            String header = headers[i];
	            SXSSFCell cell = headerRow.createCell(i);
	            cell.setCellStyle(headerStyle);
	            cell.setCellValue(header);
	        }
	        int i = 1;
	        for(cierre_mensual e: row){
	        	SXSSFRow dataRow = sheet.createRow(i);
	        	dataRow.createCell(0).setCellValue(e.getTrabajador());
	        	dataRow.createCell(1).setCellValue(e.getCuenta());
	        	dataRow.createCell(2).setCellValue(e.getCeco());
	        	dataRow.createCell(3).setCellValue(e.getPeriodo());
	        	dataRow.createCell(4).setCellValue(e.getSociedadCentralizacion());
	        	dataRow.createCell(5).setCellValue(e.getNombreCen());
	        	dataRow.createCell(6).setCellValue(e.getSociedadImputacion());
	        	dataRow.createCell(7).setCellValue(e.getNombreIm());
	        	dataRow.createCell(8).setCellValue(e.getValor());
	        	dataRow.createCell(9).setCellValue(Double.valueOf(String.valueOf(e.getPercent())));
	        	dataRow.createCell(10).setCellValue(e.getCosto_empresa());
	        	i++;
	        }
			urlDocGenerado=urlDocGenerado+"ReporteCierre.xlsx";
			File archivo = new File(urlDocGenerado);
	        FileOutputStream salida = new FileOutputStream(archivo);
	        workbook.write(salida);
	        workbook.close();
	        salida.close();
	        System.out.println("Archivo creado existosamente");
	        return archivo.getName();
		}catch (FileNotFoundException ex) {
			System.out.println("Archivo no localizable en sistema de archivos");
		    return "0";
		} catch (IOException ex) {
			System.out.println("Error de entrada/salida");
	        return "0";
		}
		//return false;
		//return "0";
	}
	public String GENERAR_EXCEL_LISTADO_CODIFICADO(ArrayList<RENDIMIENTO_DIARIO> row) throws Exception{
		String urlDocGenerado = utils.reportesExcel();
		urlDocGenerado=urlDocGenerado+"listado_codificado"+".xlsx";
		File archivo = new File(urlDocGenerado);
		try {
			SXSSFWorkbook workbook = new SXSSFWorkbook();
	        SXSSFSheet sheet = workbook.createSheet();
	        workbook.setSheetName(0, "Listado Rendimiento");
	        String[] headers = new String[]{
                "Fecha",
                "Supervisor",
                "Rut",
                "Codigo Trabajador",
                "Trabajador",
                "Tipo",
                "Codigo Contratista",
                "Contratista",
                "Campo",
                "Etnia",
                "Codigo Especie",
                "Especie",
                "Codigo Variedad",
                "Variedad",
                "Codigo Cuartel",
                "Cuartel",
                "Codigo Agrupación",
                "Agrupación",
                "Codigo OrdenCO",
                "OrdenCO",
                "Codigo CeCO",
                "CeCO",
                "Estado CeCo",
                "Codigo Faena",
                "Faena",
                "Codigo Labor",
                "Labor",
                "Horas Trabajadas",
                "N Personas",
                "Horas Extras",
                "Valor Horas Extras",
                "Valor Pagado",
                "Monto Horas Extras",
                "Horas Extras 2",
                "Valor Horas Extras 2",
                "Bono 2",
                "Base Piso",
                "Tipo Pago",
                "Valor",
                "Valor Trato",
                "Rendimiento",
                "Valor Rendimiento",
                "Bono",
                "Subsidio",
                "Valor Líquido",
                "Maquinaria",
                "Implemento",
                "Estado",
                "Costo Empresa"
	        };
	        CellStyle headerStyle = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setBold(true);
	        headerStyle.setFont(font);
	        
	        CellStyle style = workbook.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        SXSSFRow headerRow = sheet.createRow(0);
	        
	        for (int i = 0; i < headers.length; ++i) {
	            String header = headers[i];
	            SXSSFCell cell = headerRow.createCell(i);
	            cell.setCellStyle(headerStyle);
	            cell.setCellValue(header);
	        }
	        int i = 1;
	        for(RENDIMIENTO_DIARIO rd: row){
	        	SXSSFRow dataRow = sheet.createRow(i);
	        	dataRow.createCell(0).setCellValue(rd.getFecha());
	        	dataRow.createCell(1).setCellValue(rd.getSupervisor());
	        	dataRow.createCell(2).setCellValue(rd.getRut());
	        	dataRow.createCell(3).setCellValue(rd.getTrabajador());
	        	dataRow.createCell(4).setCellValue(rd.getNombre());
	        	if(rd.getIdContratista().equals("0")){
	        		dataRow.createCell(5).setCellValue("PROPIO");
	        	}else{
	        		dataRow.createCell(5).setCellValue("CONTRATISTA");
	        	}
	        	dataRow.createCell(6).setCellValue(rd.getIdContratista());
	        	dataRow.createCell(7).setCellValue(rd.getnContratista());
	        	dataRow.createCell(8).setCellValue(rd.getDescripcion());
	        	dataRow.createCell(9).setCellValue(rd.getEtnia());
	        	dataRow.createCell(10).setCellValue(rd.getEspecie());
	        	dataRow.createCell(11).setCellValue(rd.getnEspecie());
	        	dataRow.createCell(12).setCellValue(rd.getVariedad());
	        	dataRow.createCell(13).setCellValue(rd.getnVariedad());
	        	dataRow.createCell(14).setCellValue(rd.getCuartel());
	        	dataRow.createCell(15).setCellValue(rd.getNvnombre());
	        	dataRow.createCell(16).setCellValue(rd.getMacroco());
	        	dataRow.createCell(17).setCellValue(rd.getnMacro());
	        	dataRow.createCell(18).setCellValue(rd.getOrdenco());
	        	dataRow.createCell(19).setCellValue(rd.getnOrdenco());
	        	dataRow.createCell(20).setCellValue(rd.getCeco());
	        	dataRow.createCell(21).setCellValue(rd.getnCeco());
	        	if(rd.getEstado() == 1){
	        		dataRow.createCell(22).setCellValue("Productivo");
	        	}else{
	        		dataRow.createCell(22).setCellValue("No Productivo");
	        	}
	        	dataRow.createCell(23).setCellValue(rd.getFaena());
	        	dataRow.createCell(24).setCellValue(rd.getnFaena());
	        	dataRow.createCell(25).setCellValue(rd.getLabor());
	        	dataRow.createCell(26).setCellValue(rd.getnLabor());
	        	dataRow.createCell(27).setCellValue(rd.getHoras_trabajadas());
	        	dataRow.createCell(28).setCellValue(rd.getN_personas());
	        	dataRow.createCell(29).setCellValue(rd.getHoras_extras());
	        	dataRow.createCell(30).setCellValue(rd.getValor_hx());
	        	dataRow.createCell(31).setCellValue(rd.getRes_hx() + rd.getValor_hx());
	        	dataRow.createCell(32).setCellValue(rd.getMonto_hx());
	        	dataRow.createCell(33).setCellValue(rd.getHx_dos());
	        	dataRow.createCell(34).setCellValue(rd.getValor_hx_dos());
	        	dataRow.createCell(35).setCellValue(rd.getBono2());
	        	if(rd.getBase_piso_hora() == 1){
	        		dataRow.createCell(36).setCellValue("SI");
	        	}else{
	        		dataRow.createCell(36).setCellValue("NO");
	        	}
	        	if(rd.getTipo_pago() == 1){
	        		dataRow.createCell(37).setCellValue("DIA");
	        	}else if(rd.getTipo_pago() == 2){
	        		dataRow.createCell(37).setCellValue("TRATO");
	        	}else{
	        		dataRow.createCell(37).setCellValue("MIXTO");
	        	}
	        	dataRow.createCell(38).setCellValue(rd.getValor());
	        	dataRow.createCell(39).setCellValue(rd.getValor_trato());
	        	dataRow.createCell(40).setCellValue(Double.valueOf(String.valueOf(rd.getRendimiento())));
	        	dataRow.createCell(41).setCellValue(Double.valueOf(String.valueOf(rd.getValor_rendimiento())));
	        	dataRow.createCell(42).setCellValue(rd.getBono1());
	        	dataRow.createCell(43).setCellValue(rd.getSubsidio());
	        	dataRow.createCell(44).setCellValue(rd.getValor_liquido());
	        	dataRow.createCell(45).setCellValue(rd.getnMaquinaria());
	        	dataRow.createCell(46).setCellValue(rd.getnImplemento());
	        	dataRow.createCell(47).setCellValue(rd.getNestado());
	        	dataRow.createCell(48).setCellValue(rd.getCosto_empresa());
	        	if(!rd.getIdContratista().equals("0")){
	        		dataRow.createCell(48).setCellValue(rd.getValor_liquido());
	        	}
	        	i++;
	        }
	        SXSSFRow dataRow = sheet.createRow(1 + row.size());
	        SXSSFCell total = dataRow.createCell(41);
	        total.setCellType(CellType.FORMULA);
	        total.setCellStyle(style);
	        total.setCellFormula(String.format("SUM(AP2:AP%d)", 1 + row.size()));
	        FileOutputStream salida = new FileOutputStream(archivo);
	        workbook.write(salida);
	        workbook.close();
	        salida.close();
	        System.out.println("Archivo creado existosamente");
	        return archivo.getName();
		}catch (FileNotFoundException ex) {
			System.out.println("Archivo no localizable en sistema de archivos");
		    return "0";
		} catch (IOException ex) {
			System.out.println("Error de entrada/salida");
	        return "0";
		}
		//return false;
		//return "0";
	}
	public String GENERAR_EXCEL_LIQUIDACION(String row) throws Exception{
		String urlDocGenerado = utils.reportesExcel();
		System.out.println(row);
		JSONObject json = new JSONObject(row);
		JSONArray data = new JSONArray(json.get("data").toString());
		try {
			SXSSFWorkbook workbook = new SXSSFWorkbook();
	        SXSSFSheet sheet = workbook.createSheet();
	        sheet.getPrintSetup().setLandscape(true);
	        sheet.setFitToPage(true);
			PrintSetup ps = sheet.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);
	        workbook.setSheetName(0, "Liquidacion "+json.getInt("codigo"));
	        String[] headers = new String[]{
                "Fecha",
                "Cuartel/Ceco",
                "Faena",
                "Labor",
                "Tipo Pago",
                "Valor Trato",
                "Rendimiento",
                "Valor Rendimiento",
                "Bono",
                "Valor Liquido"
	        };
	        CellStyle headerStyle = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setBold(true);
	        headerStyle.setFont(font);
	        
	        CellStyle style = workbook.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        
	        SXSSFRow rowContratista = sheet.createRow(0);
	        SXSSFCell cellContratista = rowContratista.createCell(0);
	        cellContratista.setCellStyle(headerStyle);
	        cellContratista.setCellValue("Contratista:");
	        SXSSFCell cellContratista1 = rowContratista.createCell(1);
	        cellContratista1.setCellValue(json.getString("nombreContratista"));

	        SXSSFRow rowRut = sheet.createRow(1);
	        SXSSFCell cellRut = rowRut.createCell(0);
	        cellRut.setCellStyle(headerStyle);
	        cellRut.setCellValue("Rut:");
	        SXSSFCell cellRut1 = rowRut.createCell(1);
	        cellRut1.setCellValue(json.getString("rut"));

	        SXSSFRow rowCodigo = sheet.createRow(2);
	        SXSSFCell cellCodigo = rowCodigo.createCell(0);
	        cellCodigo.setCellStyle(headerStyle);
	        cellCodigo.setCellValue("Codigo:");
	        SXSSFCell cellCodigo1 = rowCodigo.createCell(1);
	        cellCodigo1.setCellValue(json.getString("contratista"));

	        SXSSFRow rowLiquidacion = sheet.createRow(3);
	        SXSSFCell cellLiquidacion = rowLiquidacion.createCell(0);
	        cellLiquidacion.setCellStyle(headerStyle);
	        cellLiquidacion.setCellValue("N° Liquidacion:");
	        SXSSFCell cellLiquidacion1 = rowLiquidacion.createCell(1);
	        cellLiquidacion1.setCellValue(json.getInt("codigo"));

	        SXSSFRow rowComprobante = sheet.createRow(4);
	        SXSSFCell cellComprobante = rowComprobante.createCell(0);
	        cellComprobante.setCellStyle(headerStyle);
	        cellComprobante.setCellValue("N° Comprobante:");
	        SXSSFCell cellComprobante1 = rowComprobante.createCell(1);
	        cellComprobante1.setCellValue(json.getString("asiento_contable"));

	        SXSSFRow rowFactura = sheet.createRow(5);
	        SXSSFCell cellFactura = rowFactura.createCell(0);
	        cellFactura.setCellStyle(headerStyle);
	        cellFactura.setCellValue("N° Factura:");
	        SXSSFCell cellFactura1 = rowFactura.createCell(1);
	        cellFactura1.setCellValue(json.getString("n_factura"));
	        
	        
	        
	        
	        SXSSFRow headerRow = sheet.createRow(6);
	        
	        for (int i = 0; i < headers.length; ++i) {
	            String header = headers[i];
	            SXSSFCell cell = headerRow.createCell(i);
	            cell.setCellStyle(headerStyle);
	            cell.setCellValue(header);
	        }
	        int i = 7;
	        int iva = 0;
	        float neto = 0;
	        int bruto = 0;
	        int numero = 0;
	        for(int ix = 0; ix < data.length(); ix++){
	        	JSONObject e = new JSONObject(data.getJSONObject(ix).toString());
	        	iva = e.getInt("iva");
	        	neto = e.getFloat("totalLiquidacion");
	        	bruto = e.getInt("total_liquido");
	        	SXSSFRow dataRow = sheet.createRow(i);
	        	dataRow.createCell(0).setCellValue(e.getString("fecha"));
	        	dataRow.createCell(1).setCellValue(e.getString("nCeco"));
	        	dataRow.createCell(2).setCellValue(e.getString("nFaena"));
	        	dataRow.createCell(3).setCellValue(e.getString("nLabor"));
	        	if(e.getInt("tipo_pago") == 1){
	        		dataRow.createCell(4).setCellValue("DIA");
	        	}else if(e.getInt("tipo_pago") == 2){
	        		dataRow.createCell(4).setCellValue("TRATO");
	        	}else{
	        		dataRow.createCell(4).setCellValue("MIXTO");
	        	}
	        	dataRow.createCell(5).setCellValue(e.getFloat("valor_trato"));
	        	dataRow.createCell(6).setCellValue(e.getFloat("rendimiento"));
	        	dataRow.createCell(7).setCellValue(e.getFloat("valor_rendimiento"));
	        	dataRow.createCell(8).setCellValue(e.getFloat("bono1"));
	        	dataRow.createCell(9).setCellValue(e.getInt("valor_liquido"));
				i++;
			}
	        SXSSFRow dataRow = sheet.createRow(7 + data.length());
	        SXSSFCell cellNeto = dataRow.createCell(8);
	        cellNeto.setCellStyle(headerStyle);
	        cellNeto.setCellValue("Neto: ");
	        SXSSFCell cellNetoTotal = dataRow.createCell(9);
	        cellNetoTotal.setCellValue(neto);
	        
	        SXSSFRow dataRow2 = sheet.createRow(8 + data.length());
	        SXSSFCell cellIva = dataRow2.createCell(8);
	        cellIva.setCellStyle(headerStyle);
	        cellIva.setCellValue("Iva: ");
	        SXSSFCell cellIvaTotal = dataRow2.createCell(9);
	        cellIvaTotal.setCellValue(iva);

	        SXSSFRow dataRow3 = sheet.createRow(9 + data.length());
	        SXSSFCell cellLiquido = dataRow3.createCell(8);
	        cellLiquido.setCellStyle(headerStyle);
	        cellLiquido.setCellValue("Total Liquido: ");
	        SXSSFCell Total = dataRow3.createCell(9);
	        Total.setCellValue(bruto);

	        SXSSFRow dataRow4 = sheet.createRow(11 + data.length());
	        SXSSFCell cellNeto2 = dataRow4.createCell(8);
	        cellNeto2.setCellStyle(headerStyle);
	        cellNeto2.setCellValue("Neto: ");
	        SXSSFCell cellNetoTotal2 = dataRow4.createCell(9);
	        cellNetoTotal2.setCellValue(neto);
	        
	        SXSSFRow dataRow5 = sheet.createRow(12 + data.length());
	        SXSSFCell cellAnticipo = dataRow5.createCell(8);
	        cellAnticipo.setCellStyle(headerStyle);
	        cellAnticipo.setCellValue("(-) Anticipo:");
	        SXSSFCell cellAnticipo1 = dataRow5.createCell(9);
	        cellAnticipo1.setCellValue(json.getInt("anticipo"));

	        SXSSFRow dataRow6 = sheet.createRow(13 + data.length());
	        SXSSFCell cellRetencion = dataRow6.createCell(8);
	        cellRetencion.setCellStyle(headerStyle);
	        cellRetencion.setCellValue("(-) Retencion:");
	        SXSSFCell cellRetencion1 = dataRow6.createCell(9);
	        cellRetencion1.setCellValue(json.getInt("valor_retencion"));

	        SXSSFRow dataRow7 = sheet.createRow(14 + data.length());
	        SXSSFCell cellLiquidoPago = dataRow7.createCell(8);
	        cellLiquidoPago.setCellStyle(headerStyle);
	        cellLiquidoPago.setCellValue("Liquido a Pagar:");
	        SXSSFCell cellLiquidoPago1 = dataRow7.createCell(9);
	        cellLiquidoPago1.setCellValue((json.getInt("valor_liquido")) - (json.getInt("valor_retencion")) - (json.getInt("anticipo")));
	        
			urlDocGenerado=urlDocGenerado+"Liquidacion_"+json.getInt("codigo")+".xlsx";
			File archivo = new File(urlDocGenerado);
	        FileOutputStream salida = new FileOutputStream(archivo);
	        workbook.write(salida);
	        workbook.close();
	        salida.close();
	        System.out.println("Archivo creado existosamente");
	        return archivo.getName();
		}catch (FileNotFoundException ex) {
			System.out.println("Archivo no localizable en sistema de archivos");
		    return "0";
		} catch (IOException ex) {
			System.out.println("Error de entrada/salida");
	        return "0";
		}
		//return false;
		//return "0";
	}
	public String GENERAR_EXCEL_LIQUIDACION_PULENTO(ArrayList<LIQUIDACION> row) throws Exception{
		String urlDocGenerado = utils.reportesExcel();
		try {
			SXSSFWorkbook workbook = new SXSSFWorkbook();
	        SXSSFSheet sheet = workbook.createSheet();
	        sheet.getPrintSetup().setLandscape(true);
	        sheet.setFitToPage(true);
			PrintSetup ps = sheet.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);
	        workbook.setSheetName(0, "Liquidaciones Contratistas");
	        String[] headers = new String[]{
	        	"N° de Liquidacion",
                "Fecha Creacion",
                "Semana(s)",
                "Codigo Contratista",
                "Contratista",
                "Neto",
                "Iva",
                "Bruto",
                "Estado",
                "Monto Pagado",
                "Monto Retenido",
                "Anticipo",
                "Asiento Contable",
                "N° Orden de Pago",
                "N° Factura"
	        };
	        CellStyle headerStyle = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setBold(true);
	        headerStyle.setFont(font);
	        
	        CellStyle style = workbook.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        SXSSFRow headerRow = sheet.createRow(0);
	        
	        for (int i = 0; i < headers.length; ++i) {
	            String header = headers[i];
	            SXSSFCell cell = headerRow.createCell(i);
	            cell.setCellStyle(headerStyle);
	            cell.setCellValue(header);
	        }
	        int i = 1;
	        for(LIQUIDACION e: row){
	        	SXSSFRow dataRow = sheet.createRow(i);
	        	dataRow.createCell(0).setCellValue(e.getCodigo());
	        	dataRow.createCell(1).setCellValue(e.getFecha());
	        	dataRow.createCell(2).setCellValue(e.getSemanas());
	        	dataRow.createCell(3).setCellValue(e.getContratista());
	        	dataRow.createCell(4).setCellValue(e.getNombreContratista());
	        	dataRow.createCell(5).setCellValue(e.getValor_liquido());
	        	dataRow.createCell(6).setCellValue(e.getIva());
	        	dataRow.createCell(7).setCellValue(e.getTotal());
	        	dataRow.createCell(8).setCellValue(e.getEstado());
	        	dataRow.createCell(9).setCellValue(e.getTotal() - e.getValor_retencion() - e.getAnticipo());
	        	dataRow.createCell(10).setCellValue(e.getValor_retencion());
	        	dataRow.createCell(11).setCellValue(e.getAnticipo());
	        	dataRow.createCell(12).setCellValue(e.getAsiento_contable());
	        	dataRow.createCell(13).setCellValue(e.getOrden());
	        	dataRow.createCell(14).setCellValue(e.getN_factura());
	        	i++;
	        }
			urlDocGenerado=urlDocGenerado+"Liquidaciones.xlsx";
			File archivo = new File(urlDocGenerado);
	        FileOutputStream salida = new FileOutputStream(archivo);
	        workbook.write(salida);
	        workbook.close();
	        salida.close();
	        System.out.println("Archivo creado existosamente");
	        return archivo.getName();
		}catch (FileNotFoundException ex) {
			System.out.println("Archivo no localizable en sistema de archivos");
		    return "0";
		} catch (IOException ex) {
			System.out.println("Error de entrada/salida");
	        return "0";
		}
		//return false;
		//return "0";
	}
	public String EXCEL_CONSUMO_PETROLEO(String row) throws Exception{
		JSONArray data = new JSONArray(row);
		String urlDocGenerado = utils.reportesExcel();
		try {
			SXSSFWorkbook workbook = new SXSSFWorkbook();
	        SXSSFSheet sheet = workbook.createSheet();
	        sheet.getPrintSetup().setLandscape(true);
	        sheet.setFitToPage(true);
			PrintSetup ps = sheet.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);
	        workbook.setSheetName(0, "Libro Campo");
	        String[] headers = new String[]{
                "Campo",
                "Fecha",
                "Litros Totales",
                "Documento Material"
	        };
	        CellStyle headerStyle = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setBold(true);
	        headerStyle.setFont(font);
	        
	        CellStyle style = workbook.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        SXSSFRow headerRow = sheet.createRow(0);
	        
	        for (int i = 0; i < headers.length; ++i) {
	            String header = headers[i];
	            SXSSFCell cell = headerRow.createCell(i);
	            cell.setCellStyle(headerStyle);
	            cell.setCellValue(header);
	        }
	        int i = 1;
	        String campo = "";
	        for(int ix = 0; ix < data.length(); ix++){
	        	JSONObject e = new JSONObject(data.getJSONObject(ix).toString());
	        	SXSSFRow dataRow = sheet.createRow(i);
	        	campo = e.getString("campo").replace("Ã­", "í").replace("Ã©", "é");
	        	dataRow.createCell(0).setCellValue(e.getString("campo").replace("Ã­", "í").replace("Ã©", "é"));
	        	dataRow.createCell(1).setCellValue(e.getString("fecha"));
	        	dataRow.createCell(2).setCellValue(new BigDecimal(e.getDouble("litros_totales")).doubleValue());
	        	dataRow.createCell(3).setCellValue(e.getString("doc_material"));
				i++;
			}
			urlDocGenerado=urlDocGenerado+"ConsumoPetroleo"+campo+".xlsx";
			File archivo = new File(urlDocGenerado);
	        FileOutputStream salida = new FileOutputStream(archivo);
	        workbook.write(salida);
	        workbook.close();
	        salida.close();
	        System.out.println("Archivo creado existosamente");
	        return archivo.getName();
		}catch (FileNotFoundException ex) {
			System.out.println("Archivo no localizable en sistema de archivos");
		    return "0";
		} catch (IOException ex) {
			System.out.println("Error de entrada/salida");
	        return "0";
		}
	}
	public String EXCEL_CONSUMO_PETROLEO_DETALLE(String campo, String doc) throws Exception{
		URL url = new URL("http://200.55.206.140/SCLEM/JSON_BAPI_EQUI_GETLIST.aspx?CENTRO="+campo);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output = "";
		JSONObject json = new JSONObject();
		JSONArray EQUIPMENT_LIST = new JSONArray();
		JSONArray data = new JSONArray();
		
		if ((output = br.readLine()) != null) {
			json = new JSONObject(output);
		}
		EQUIPMENT_LIST = (JSONArray) json.get("EQUIPMENT_LIST");
		System.out.println(EQUIPMENT_LIST.toString());
		String urlDocGenerado = utils.reportesExcel();
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql +=	"SELECT ";
			sql +=		"*, ";
			sql +=		"IF((SELECT nombre FROM trabajadores WHERE id = md.operador) IS NULL, md.operador, ";
			sql +=		"(SELECT  CONCAT(apellidoPaterno, ' ', apellidoMaterno, ' ', nombre) AS nombre FROM trabajadores WHERE id = md.operador)) AS nombreOperador ";
			sql +=	"FROM ";
			sql +=		"consumo_combustible md ";
			sql +=	"WHERE ";
			sql +=		"material_document = '"+doc+"' ";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				JSONObject ob = new JSONObject();
				for(int ix = 0; ix < EQUIPMENT_LIST.length(); ix++){
		        	JSONObject e = new JSONObject(EQUIPMENT_LIST.getJSONObject(ix).toString());
		        	if(Integer.parseInt(e.getString("EQUIPMENT")) == rs.getInt("vehiculo")){
		        		ob.put("nombreEquipo", e.getString("DESCRIPT"));
		        	}
				}
				ob.put("tipo", rs.getString("tipo"));
				ob.put("vehiculo", rs.getString("vehiculo"));
				ob.put("fecha", rs.getString("fecha"));
				ob.put("litro", rs.getFloat("litro"));
				ob.put("nombreOperador", rs.getString("nombreOperador"));
				ob.put("horometro", rs.getFloat("horometro"));
				ob.put("material_document", rs.getString("material_document"));
				data.put(ob);
			}
			System.out.println(data.toString());
			SXSSFWorkbook workbook = new SXSSFWorkbook();
	        SXSSFSheet sheet = workbook.createSheet();
	        sheet.getPrintSetup().setLandscape(true);
	        sheet.setFitToPage(true);
			PrintSetup pse = sheet.getPrintSetup();
			pse.setFitWidth((short) 1);
			pse.setFitHeight((short) 0);
	        workbook.setSheetName(0, "Libro Campo");
	        String[] headers = new String[]{
		        "Documento Material",
	            "Fecha",
                "Tipo",
                "Vehiculo",
                "Nombre Vehiculo",
                "Nombre Operador",
                "Litros",
                "Horometro"
	        };
	        CellStyle headerStyle = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setBold(true);
	        headerStyle.setFont(font);
	        
	        CellStyle style = workbook.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        SXSSFRow headerRow = sheet.createRow(0);
	        
	        for (int i = 0; i < headers.length; ++i) {
	            String header = headers[i];
	            SXSSFCell cell = headerRow.createCell(i);
	            cell.setCellStyle(headerStyle);
	            cell.setCellValue(header);
	        }
	        int i = 1;
	        for(int ix = 0; ix < data.length(); ix++){
	        	JSONObject e = new JSONObject(data.getJSONObject(ix).toString());
	        	SXSSFRow dataRow = sheet.createRow(i);
	        	dataRow.createCell(0).setCellValue(e.getString("material_document").replace("Ã­", "í").replace("Ã©", "é"));
	        	dataRow.createCell(1).setCellValue(e.getString("fecha"));
	        	dataRow.createCell(2).setCellValue(e.getString("tipo"));
	        	dataRow.createCell(3).setCellValue(e.getString("vehiculo"));
	        	dataRow.createCell(4).setCellValue(e.getString("nombreEquipo"));
	        	dataRow.createCell(5).setCellValue(e.getString("nombreOperador"));
	        	dataRow.createCell(6).setCellValue(new BigDecimal(e.getDouble("litro")).doubleValue());
	        	dataRow.createCell(7).setCellValue(new BigDecimal(e.getDouble("horometro")).doubleValue());
				i++;
			}
			urlDocGenerado=urlDocGenerado+"ConsumoPetroleo"+doc+".xlsx";
			File archivo = new File(urlDocGenerado);
	        FileOutputStream salida = new FileOutputStream(archivo);
	        workbook.write(salida);
	        workbook.close();
	        salida.close();
	        System.out.println("Archivo creado existosamente");
			rs.close();
			ps.close();
			db.conn.close();
	        return archivo.getName();
		}catch (FileNotFoundException ex) {
			System.out.println("Archivo no localizable en sistema de archivos");
		    return "0";
		} catch (IOException ex) {
			System.out.println("Error de entrada/salida");
	        return "0";
		}
	}
	public String EXCEL_CONSUMO_EQUIPO(String row) throws Exception{
		JSONArray data = new JSONArray(row);
		String urlDocGenerado = utils.reportesExcel();
		try {
			SXSSFWorkbook workbook = new SXSSFWorkbook();
	        SXSSFSheet sheet = workbook.createSheet();
	        sheet.getPrintSetup().setLandscape(true);
	        sheet.setFitToPage(true);
			PrintSetup ps = sheet.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);
	        workbook.setSheetName(0, "Consumo Equipo");
	        String[] headers = new String[]{
                "Codigo",
                "Campo",
                "Tipo Ingreso",
                "Tipo Equipo",
                "N Equipo",
                "Nombre Equipo",
                "Fecha Ingreso",
                "Fecha Salida",
                "Doc Material Consumo",
                "Codigo Material",
                "Material",
                "UM",
                "Cantidad Consumo",
                "Valorización Consumo",
                "Tipo Moneda",
                "Fecha consumo",
                "Horometro Ingreso",
                "Horometro Salida"
	        };
	        CellStyle headerStyle = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setBold(true);
	        headerStyle.setFont(font);
	        
	        CellStyle style = workbook.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        SXSSFRow headerRow = sheet.createRow(0);
	        
	        for (int i = 0; i < headers.length; ++i) {
	            String header = headers[i];
	            SXSSFCell cell = headerRow.createCell(i);
	            cell.setCellStyle(headerStyle);
	            cell.setCellValue(header);
	        }
	        int i = 1;
	        for(int ix = 0; ix < data.length(); ix++){
	        	JSONObject e = new JSONObject(data.getJSONObject(ix).toString());
	        	SXSSFRow dataRow = sheet.createRow(i);
	        	dataRow.createCell(0).setCellValue(e.getInt("codigo"));
	        	dataRow.createCell(1).setCellValue(e.getString("campo").replace("Ã­", "í").replace("Ã©", "é").replace("Ã¡", "á"));
	        	dataRow.createCell(2).setCellValue(e.getString("tipo_ingreso"));
	        	dataRow.createCell(3).setCellValue(e.getString("tipo_equipo"));
	        	dataRow.createCell(4).setCellValue(e.getInt("n_equipo"));
	        	dataRow.createCell(5).setCellValue(e.getString("nombre_equipo"));
	        	dataRow.createCell(6).setCellValue(e.getString("fecha_ingreso"));
	        	dataRow.createCell(7).setCellValue(e.getString("fecha_salida"));
	        	dataRow.createCell(8).setCellValue(e.getString("doc_material"));
	        	dataRow.createCell(9).setCellValue(e.getInt("cod_material"));
	        	dataRow.createCell(10).setCellValue(e.getString("material"));
	        	dataRow.createCell(11).setCellValue(e.getString("um"));
	        	dataRow.createCell(12).setCellValue(e.getInt("cant_consumo"));
	        	dataRow.createCell(13).setCellValue(e.getInt("val_consumo"));
	        	dataRow.createCell(14).setCellValue(e.getString("tipo_moneda"));
	        	dataRow.createCell(15).setCellValue(e.getString("fecha_consumo"));
	        	dataRow.createCell(16).setCellValue(e.getInt("horo_ingreso"));
	        	dataRow.createCell(17).setCellValue(e.getInt("horo_salida"));
				i++;
			}
			urlDocGenerado=urlDocGenerado+"ConsumoEquipo.xlsx";
			File archivo = new File(urlDocGenerado);
	        FileOutputStream salida = new FileOutputStream(archivo);
	        workbook.write(salida);
	        workbook.close();
	        salida.close();
	        System.out.println("Archivo creado existosamente");
	        return archivo.getName();
		}catch (FileNotFoundException ex) {
			System.out.println("Archivo no localizable en sistema de archivos");
		    return "0";
		} catch (IOException ex) {
			System.out.println("Error de entrada/salida");
	        return "0";
		}
	}
}
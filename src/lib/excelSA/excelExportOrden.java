package lib.excelSA;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.MultiColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import lib.classSA.RENDIMIENTO_DIARIO;
import lib.classSA.respuesta;
import lib.db.ConnectionDB;
import lib.security.session;
import wordCreator.utils;

public class excelExportOrden {
	public String EXCEL_RECOTEC(String r) throws Exception{
		JSONObject datos = new JSONObject(r);
		String urlDocGenerado = utils.reportesExcel();
		urlDocGenerado=urlDocGenerado+datos.getString("nombreArchivo")+".xlsx";
		File archivo = new File(urlDocGenerado);
		JSONArray data = new JSONArray(datos.get("data").toString());
		try {
			SXSSFWorkbook workbook = new SXSSFWorkbook();
	        SXSSFSheet sheet = workbook.createSheet();
	        workbook.setSheetName(0, datos.getString("nombreHoja"));
	        CellStyle headerStyle = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setBold(true);
	        headerStyle.setFont(font);
	        
	        CellStyle style = workbook.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        SXSSFRow headerRow = sheet.createRow(0);
	        int i = 1;
	        ArrayList<String> aux = new ArrayList<String>();
	        for(int ix = 0; ix < data.length(); ix++){
	        	JSONObject object = data.optJSONObject(i);
	            Iterator<String> iterator = object.keys();
	            while(iterator.hasNext()) {
	            	String currentKey = iterator.next();
	            	if(aux.indexOf(currentKey) == -1){
	            		aux.add(currentKey);
	            	}
	            }
	        }
	        for(int id = 0; id < aux.size(); id++){
	        	String header = aux.get(id);
	            SXSSFCell cell = headerRow.createCell(id);
	            cell.setCellStyle(headerStyle);
	            cell.setCellValue(header);
	        }
	        for(int ix = 0; ix < data.length(); ix++){
	        	JSONObject e = new JSONObject(data.getJSONObject(ix).toString());
	        	SXSSFRow dataRow = sheet.createRow(i);
	        	for(int id = 0; id < aux.size(); id++){
	        		dataRow.createCell(id).setCellValue(String.valueOf(e.get(aux.get(id))));
		        }
				i++;
			}
	        FileOutputStream salida = new FileOutputStream(archivo);
	        workbook.write(salida);
	        workbook.close();
	        salida.close();
	        System.out.println("Archivo creado existosamente");
	        return archivo.getName();
		}catch (FileNotFoundException ex) {
			System.out.println("Archivo no localizable en sistema de archivos");
		    return "Archivo no localizable en sistema de archivos  "+ex.getMessage();
		} catch (IOException ex) {
			System.out.println("Error de entrada/salida");
	        return "Error de entrada/salida  "+ex.getMessage();
		}
		//return false;
		//return "0";
	}
	public String JSON_EXCEL(String row) throws Exception{
		JSONObject res = new JSONObject();
		String n = new String(row.getBytes("ISO-8859-1"), "UTF-8");
		JSONObject json = new JSONObject(n);
		JSONArray head = json.getJSONArray("HEADER");
		JSONArray data = json.getJSONArray("DATA");
		JSONObject names = json.getJSONObject("NAMES");
		String urlDocGenerado = utils.reportesExcel();
		urlDocGenerado=urlDocGenerado+names.getString("FILE")+".xlsx";
		File archivo = new File(urlDocGenerado);
		try {
			SXSSFWorkbook workbook = new SXSSFWorkbook();
	        SXSSFSheet sheet = workbook.createSheet();
	        workbook.setSheetName(0, names.getString("SHEET"));
	        CellStyle headerStyle = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setBold(true);
	        headerStyle.setFont(font);
	        
	        CellStyle style = workbook.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        SXSSFRow headerRow = sheet.createRow(0);
	        List<String> headE = new ArrayList<String>();
	        for(int i = 0; i < head.length(); i++){
	        	headE.add(head.getString(i));
			}
	        for (int i = 0; i < headE.size(); ++i) {
	            String header = headE.get(i);
	            SXSSFCell cell = headerRow.createCell(i);
	            cell.setCellStyle(headerStyle);
	            cell.setCellValue(header);
	        }
	        int i = 1;
	        for(int ix = 0; ix < data.length(); ix++){
	        	JSONArray e = data.getJSONArray(ix);
	        	SXSSFRow dataRow = sheet.createRow(i);
	        	for(int x = 0; x <= e.length(); x++){
	        		try{
	        			if(isNumeric(String.valueOf(e.get(x))) == true){
	        				dataRow.createCell(x).setCellValue(e.getDouble(x));
	        			}else{
	        				dataRow.createCell(x).setCellValue(e.getString(x));
	        			}
	        		}catch(Exception er){
	        			
	        		}
	        	}
				i++;
			}
	        FileOutputStream salida = new FileOutputStream(archivo);
	        workbook.write(salida);
	        workbook.close();
	        salida.close();
			res.put("mensaje", archivo.getName());
			res.put("error", 0);
		}catch (FileNotFoundException ex) {
			res.put("mensaje", ex.getMessage());
			res.put("error", 1);
		} catch (IOException ex) {
			res.put("mensaje", ex.getMessage());
			res.put("error", 2);
		}
        return res.toString();
	}
	private static boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
}

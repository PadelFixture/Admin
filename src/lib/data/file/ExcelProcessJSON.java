package lib.data.file;

import java.io.InputStream;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.openxml4j.opc.OPCPackage;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lib.db.sw.ProcedimientoDB;
import lib.utils.GeneralUtility;

@Controller
public class ExcelProcessJSON {

	
	//Importar Excel
	@PostMapping(value = "/work/ExcelImport/modifyDataTrabajadorPerProcess/")
	public @ResponseBody ResponseEntity<Set<String>> modifyDataTrabajadorPerProcess(HttpServletRequest request, HttpSession httpSession,
		@RequestParam("documento") MultipartFile multipartFile) throws Exception {
		
		//Mensajes de Error
		String process_error = "";
		
		// Obtener Documento en InputStream
		InputStream fileInputStream = multipartFile.getInputStream();
	
		LinkedHashMap<String,LinkedList<String>> datos = new LinkedHashMap<>();
		
		//Leer Excel
		OPCPackage pkg = OPCPackage.open(fileInputStream);
		
		//Instancia del Libro
		 Workbook workbook;
	     workbook = WorkbookFactory.create(pkg);
	     Sheet sheet = workbook.getSheetAt(0);
	     Row row;
	     
	     String rut = "";
	     String fechaIngreso = "";
  	   	 String fechaTermino = "";
  	   	 String periodo = "";

	     
	     for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	    	
	    	//Fila
    		row = sheet.getRow(i);
	    	 
    		if(!row.getCell(1).getStringCellValue().isEmpty()){
				rut = row.getCell(1).getStringCellValue();
			}
	    	 
	    	 //Meses
	    	 for (int j = 5; j <= 7; j++) {
			
	    		 //Recorrer Periodos
	    		 if(row.getCell(j).getNumericCellValue() == 1){
	    			
	    			 LinkedList<String> celdaExcel = new LinkedList<>();
	    			 
	    			 if(j == 5){ //Octubre
	    				if(!row.getCell(3).getStringCellValue().isEmpty()){
	    					fechaIngreso = GeneralUtility.convertStringToYYYYMMDD(row.getCell(3).getStringCellValue());
	    				}
	    				if(!row.getCell(4).getStringCellValue().isEmpty()){
	    					fechaTermino = GeneralUtility.convertStringToYYYYMMDD(row.getCell(4).getStringCellValue());
	    				}
	    				
	    				celdaExcel.add(rut);
	    				celdaExcel.add(fechaIngreso);
	    				celdaExcel.add(fechaTermino);
	    				celdaExcel.add("201810");
	    				
	    				datos.put(i+"-1", celdaExcel);
	    				
	    			 }//Octubre
	    			 
	    			 if(j == 6){ //Noviembre
		    				if(!row.getCell(3).getStringCellValue().isEmpty()){
		    					fechaIngreso = GeneralUtility.convertStringToYYYYMMDD(row.getCell(3).getStringCellValue());
		    				}
		    				if(!row.getCell(4).getStringCellValue().isEmpty()){
		    					fechaTermino = GeneralUtility.convertStringToYYYYMMDD(row.getCell(4).getStringCellValue());
		    				}
		    				
		    				
		    				celdaExcel.add(rut);
		    				celdaExcel.add(fechaIngreso);
		    				celdaExcel.add(fechaTermino);
		    				celdaExcel.add("201811");
		    				
		    				datos.put(i+"-2", celdaExcel);
		    				
		    		}//Noviembre
	    			 
	    			 if(j == 7){ //Diciembre
		    				if(!row.getCell(3).getStringCellValue().isEmpty()){
		    					fechaIngreso = GeneralUtility.convertStringToYYYYMMDD(row.getCell(3).getStringCellValue());
		    				}
		    				if(!row.getCell(4).getStringCellValue().isEmpty()){
		    					fechaTermino = GeneralUtility.convertStringToYYYYMMDD(row.getCell(4).getStringCellValue());
		    				}
		    				
		    				
		    				celdaExcel.add(rut);
		    				celdaExcel.add(fechaIngreso);
		    				celdaExcel.add(fechaTermino);
		    				celdaExcel.add("201812");
		    				
		    				datos.put(i+"-3", celdaExcel);
		    				
		    		}//Diciembre
	    			 
	    		 }
	    		 	 
	    	 }//FOR PERIODO
	    	 
	    	 
	    	 
	    	 
	    }//FOR POR FILA
	        	
	    process_error = ProcedimientoDB.modifyDataTrabajadorPerProcess2(datos, process_error);

		return new ResponseEntity<>(Collections.singleton(process_error), HttpStatus.OK);
	}
			
	
	
}



//row = sheet.getRow(i);
//
//LinkedList<String> celdaExcel = new LinkedList<>();
//	
//boolean error = false;
//
//String periodoOctubre = "";
//String periodoNoviembre = "";
//String periodoDiciembre = "";
//String periodo = "";
//
////Obtener Rut
//try {	    
//	if(!row.getCell(1).getStringCellValue().isEmpty()){
//		rut = row.getCell(1).getStringCellValue();
//	}
//
//} catch (Exception e) {
//	   rut = "";
//}
//
//try{
//		   
//	//Tiene periodo 201810 Octubre Obtener las fechas   
//	if(row.getCell(5).getNumericCellValue() == 1){
//	
//		periodo = "201810";
//		
//		if(!row.getCell(3).getStringCellValue().isEmpty()){
//			fechaIngreso = row.getCell(3).getStringCellValue();
//		}
//		
//		if(!row.getCell(4).getStringCellValue().isEmpty()){
//			fechaTermino = row.getCell(4).getStringCellValue();
//		}
//		
//		fechaIngreso = GeneralUtility.convertStringToYYYYMMDD(fechaIngreso);
//		fechaTermino = GeneralUtility.convertStringToYYYYMMDD(fechaTermino);
//		
//		celdaExcel.add(fechaIngreso);
//		celdaExcel.add(fechaTermino);
//		celdaExcel.add(periodo);
//		
//	} 
//	
//	
//	//Tiene periodo 201811 Noviembre Obtener las fechas   
//	if(row.getCell(6).getNumericCellValue() == 1){
//		
//		periodo = "201811";
//		
//		if(!row.getCell(3).getStringCellValue().isEmpty()){
//			fechaIngreso = row.getCell(3).getStringCellValue();
//		}
//		
//		if(!row.getCell(4).getStringCellValue().isEmpty()){
//			fechaTermino = row.getCell(4).getStringCellValue();
//		}
//		
//		fechaIngreso = GeneralUtility.convertStringToYYYYMMDD(fechaIngreso);
//		fechaTermino = GeneralUtility.convertStringToYYYYMMDD(fechaTermino);
//		
//		celdaExcel.add(fechaIngreso);
//		celdaExcel.add(fechaTermino);
//		celdaExcel.add(periodo);
//		
//		
//	}
//	
//	
//	//Tiene periodo 201812 Diciembre Obtener las fechas   
//	if(row.getCell(6).getNumericCellValue() == 1){
//		
//		periodo = "201812";
//		
//		if(!row.getCell(3).getStringCellValue().isEmpty()){
//			fechaIngreso = row.getCell(3).getStringCellValue();
//		}
//		
//		if(!row.getCell(4).getStringCellValue().isEmpty()){
//			fechaTermino = row.getCell(4).getStringCellValue();
//		}
//		
//		fechaIngreso = GeneralUtility.convertStringToYYYYMMDD(fechaIngreso);
//		fechaTermino = GeneralUtility.convertStringToYYYYMMDD(fechaTermino);
//		
//		celdaExcel.add(rut);
//		celdaExcel.add(fechaIngreso);
//		celdaExcel.add(fechaTermino);
//		celdaExcel.add(periodo);
//		
//	}
//	   
//		 
// } catch (Exception e) {
// 		 process_error += " <br> <p class='btn-danger'> Error en la Linea: "+ (i+1) + "</p> ";
// 		 error = true;
// 	 }
//	   
//if(!error){
//datos.put(i, celdaExcel);
//}



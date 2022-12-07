package lib.data.file;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import lib.db.ConnectionDB;
import lib.db.sw.CentralizacionDetalleTmpDB;
import lib.db.sw.ExcelImportDB;
import lib.struc.filterSql;
import lib.utils.NumericUtility;

@Controller
public class ExcelImportJSON {
	

	//Importar Excel
	@RequestMapping(value = "/work/ExcelImport/Centralizacion/", method = RequestMethod.POST)
	public @ResponseBody Set<String> centralizacion(HttpServletRequest request , HttpSession httpSession,
			@RequestParam("documento") MultipartFile multipartFile) throws Exception{
	
		try{
		
		// Obtener Documento en InputStream
		InputStream fileInputStream = multipartFile.getInputStream();
		
		String idSociedad      = request.getParameter("idSociedad");
		String codTrabajador   = "";
		String fechaProceso    = request.getParameter("fechaProceso");
		String periodo         = request.getParameter("periodo");
		
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		filterSql campo1 = new filterSql();
		campo1.setCampo("id_sociedad");
		campo1.setValue(idSociedad);
		filterSql campo2 = new filterSql();
		campo2.setCampo("periodo");
		campo2.setValue(periodo);
		filter.add(campo1);
		filter.add(campo2);
		
		if(CentralizacionDetalleTmpDB.getCentralizacionDetalleTmp(filter).size() > 0 ){
			CentralizacionDetalleTmpDB.deleteCentralizacionDetalleTmp(filter);
		}
		
		OPCPackage pkg = OPCPackage.open(fileInputStream);
		//POIFSFileSystem fs = new POIFSFileSystem(fileInputStream);
		
		 Workbook workbook;
	        workbook = WorkbookFactory.create(pkg);
	        Sheet sheet = workbook.getSheetAt(0);
	        Row row;
	        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
	        	row = (Row) sheet.getRow(i);
	            
	        	PreparedStatement ps = null;
	    		String sql = "";
	    		ConnectionDB db = new ConnectionDB();
	    		int k = 1;
	            
	            sql = " INSERT INTO sw_centralizacionDetalle_tmp (id_sociedad, codTrabajador, fecha_proceso, periodo, concepto, descripcion, idCECO, ordenCO, cuenta, proveedor, valor) "
	            	+ " VALUES(?,?,?,?, ?,?,?,?,?,?,?)";
	            ps = db.conn.prepareStatement(sql);
	            
	            ps.setString(k++,idSociedad);
	            ps.setString(k++,codTrabajador);
	            ps.setString(k++,fechaProceso);
	            ps.setString(k++,periodo);
	           
	            for (int j = 0; j < 7; j++) {
	            	try {
	            		ps.setString(k++,row.getCell(j).getStringCellValue());
	            	} catch (Exception e) {
	            		k--;
	            		ps.setDouble(k++,row.getCell(j).getNumericCellValue());
	            	}
				}
	            
	            System.out.println(ps.toString());
	            ps.execute();
				ps.close();
				db.conn.close();
				
			 
	        }
	 
	    	return null;
	        
	     } catch (Exception e) {
	    	 
	        
	    	 return null;
	     }

	}
	
	
	//Importar Excel
		@SuppressWarnings("deprecation")
		@RequestMapping(value = "/work/ExcelImport/insertDatos/", method = RequestMethod.POST)
		public @ResponseBody ResponseEntity<Set<String>> insertDatos(HttpServletRequest request , HttpSession httpSession,
				@RequestParam("documento") MultipartFile multipartFile) throws Exception{
		
			String process_error = "";
			//TODO: Acomodar
			
			// Obtener Documento en InputStream
			InputStream fileInputStream = multipartFile.getInputStream();
			System.out.println(multipartFile.getSize());
			LinkedHashMap<Integer,ArrayList<String>> datos = new LinkedHashMap<Integer, ArrayList<String>>();
			
			//Obtener Tipo de Importacion
			String idImportador = request.getParameter("idImportador");
			
			OPCPackage pkg = OPCPackage.open(fileInputStream);
			//POIFSFileSystem fs = new POIFSFileSystem(fileInputStream);
			
			 Workbook workbook;
		     workbook = WorkbookFactory.create(pkg);
		     Sheet sheet = workbook.getSheetAt(0);
		     Row row;
		       for (int i = 1; i <= sheet.getLastRowNum(); i++) {
		    	   
		    	   row = (Row) sheet.getRow(i);
		    	   int maxCell=  row.getLastCellNum();
		    	   ArrayList<String> celdaExcel = new ArrayList<>();
		        	
		    	   boolean error = false;
		    	   //NumericUtility
		    	   
		    	   for (int j = 0; j < maxCell; j++) {
		    		   
		    		   try{
		    			   celdaExcel.add( row.getCell(j).getCellTypeEnum() == CellType.NUMERIC ? NumericUtility.formatDoubleToStringWithoutDecimal(row.getCell(j).getNumericCellValue()) : row.getCell(j).getStringCellValue() );
				    	 } catch (Exception e) {
				    		 process_error += " <br> <p class='btn-danger'> Error en la Linea: "+ (i+1) + "</p> ";
				    		 error = true;
				    		 break;
				    	 }
		    		   
					}
		    	   if(!error){
		    	   datos.put(i, celdaExcel);
		    	   }
		    	   
		        }
		        	
		    process_error = ExcelImportDB.insertDatos(idImportador, datos, process_error);
	
			return new ResponseEntity<>(Collections.singleton(process_error), HttpStatus.OK);
		        
		}
		

}

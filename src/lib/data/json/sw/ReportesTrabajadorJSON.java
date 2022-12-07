//package lib.data.json.sw;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Map;
//import java.util.Set;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import excelCreator.Excel;
//import lib.classSW.ExcelSW;
//import lib.db.sw.ReportesTrabajadorDB;
//import lib.security.session;
//import lib.struc.filterSql;
//
//@Controller
//public class ReportesTrabajadorJSON {
//
//	@RequestMapping(value = "/work/reportesTrabajador/getInformeTrabajadorDuplicados/{Nombre}/", method = { RequestMethod.GET })
//	public @ResponseBody ResponseEntity<Set<String>> getInformeTrabajadorDuplicados(@PathVariable("Nombre") String Nombre, HttpServletRequest request ,HttpSession httpSession) throws Exception {
//
//		
//		//Obtener todos los parametros del URL
//				Map<String, String[]> parameters = request.getParameterMap();
//
//				ArrayList<filterSql> filter = new ArrayList<filterSql>();
//
//				//Obtener todos los parametros enviados por el URL
//				for (String key : parameters.keySet()) {
//					String[] vals = parameters.get(key);
//					//Obtener cada uno de los parametros y valores
//					for (String val : vals) {
//						filterSql fil = new filterSql();
//						fil.setCampo(key);
//						fil.setValue(val);
//						//Añadir campo y valor 
//						filter.add(fil);
//					}
//				}
//		
//		session ses = new session(httpSession);
//		ExcelSW datosExcel = ReportesTrabajadorDB.getInformeTrabajadorDuplicados(filter);
//		Excel excel=new Excel();
//		if (ses.isValid()) {
//			Collections.singleton("");
//		}
//	
//		//retornar el nombre del archivo
//		return new ResponseEntity<>(Collections.singleton(excel.getInformeTrabajadorDuplicados(datosExcel, Nombre)), HttpStatus.OK);
//
//	}
//	
//	
//	
//	
//}

package lib.data.json.sw;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.EstadoDocumentos;
import lib.db.sw.EstadoDocumentosDB;
import lib.struc.filterSql;

@Controller
public class EstadoDocumentosJSON {


	//get 
	@GetMapping("/work/EstadoDocumentos/getEstadoDocumentosWithFilter/")
	@ResponseBody
	public ArrayList<EstadoDocumentos> getEstadoDocumentosWithFilter(HttpServletRequest request) throws SQLException{
		
		//Obtener todos los parametros del URL
		Map<String, String[]> parameters = request.getParameterMap();

		ArrayList<filterSql> filter = new ArrayList<filterSql>();

		//Obtener todos los parametros enviados por el URL
		for (String key : parameters.keySet()) {
			String[] vals = parameters.get(key);
			//Obtener cada uno de los parametros y valores
			for (String val : vals) {
				filterSql fil = new filterSql();
				fil.setCampo(key);
				fil.setValue(val);
				//Añadir campo y valor 
				filter.add(fil);
			}
		}
		
		return EstadoDocumentosDB.getEstadoDocumentosWithFilter(filter);
		
	}
	
	//insert
	@PostMapping(value="/work/EstadoDocumentos/insertEstadoDocumentos", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String insertEstadoDocumentos(@RequestBody EstadoDocumentos EstadoDocumentos) throws SQLException{
		return EstadoDocumentosDB.insertEstadoDocumentos(EstadoDocumentos);
	}
	
	//getAll
	@GetMapping("/work/EstadoDocumentos/getAllEstadoDocumentos")
	@ResponseBody
	public ArrayList<EstadoDocumentos> getAllEstadoDocumentos(){
		return EstadoDocumentosDB.getAllEstadoDocumentos();
	}
	
	
	//delete
	@GetMapping("/work/EstadoDocumentos/deleteEstadoDocumentos/{id}")
	@ResponseBody
	public String deleteEstadoDocumentos(@PathVariable String id) throws SQLException{
		return EstadoDocumentosDB.deleteEstadoDocumentos(id);
	}
	
	
	
	
	
	
}

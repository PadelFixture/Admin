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

import lib.classSW.RutNoAutorizados;
import lib.db.sw.RutNoAutorizadosDB;
import lib.struc.filterSql;

@Controller
public class RutNoAutorizadosJSON {

	
	//get 
	@GetMapping("/work/RutNoAutorizados/getRutNoAutorizadosWithFilter/")
	@ResponseBody
	public ArrayList<RutNoAutorizados> getRutNoAutorizadosWithFilter(HttpServletRequest request) throws SQLException{
		
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
		
		return RutNoAutorizadosDB.getRutNoAutorizadosWithFilter(filter);
		
	}
	
	//insert
	@PostMapping(value="/work/RutNoAutorizados/insertRutNoAutorizados", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String insertRutNoAutorizados(@RequestBody RutNoAutorizados rutNoAutorizados) throws SQLException{
		return RutNoAutorizadosDB.insertRutNoAutorizados(rutNoAutorizados);
	}
	
	//getAll
	@GetMapping("/work/RutNoAutorizados/getAllRutNoAutorizados")
	@ResponseBody
	public ArrayList<RutNoAutorizados> getAllRutNoAutorizados(){
		return RutNoAutorizadosDB.getAllRutNoAutorizados();
	}
	
	
	//delete
	@GetMapping("/work/RutNoAutorizados/deleteRutNoAutorizados/{id}")
	@ResponseBody
	public String deleteRutNoAutorizados(@PathVariable String id) throws SQLException{
		return RutNoAutorizadosDB.deleteRutNoAutorizados(id);
	}
	
	
}

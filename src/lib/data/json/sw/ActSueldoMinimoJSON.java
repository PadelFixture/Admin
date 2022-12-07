package lib.data.json.sw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lib.classSW.ActSueldoMinimo;
import lib.db.sw.ActSueldoMinimoDB;
import lib.struc.filterSql;

@Controller
public class ActSueldoMinimoJSON {

	
	@PostMapping("/work/ActSueldoMinimo/modificarSueldoMinimo/")
	ResponseEntity<Set<String>> modificarSueldoMinimo(@RequestBody ActSueldoMinimo actSueldoMinimo, HttpServletRequest request) throws Exception{
		
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
		
		String response = ActSueldoMinimoDB.modificarSueldoMinimo(actSueldoMinimo.getSueldoMinimo(),actSueldoMinimo.getNuevoSueldo(),filter);
		return new ResponseEntity<>(Collections.singleton(response), HttpStatus.OK);
	}
	
	
	
	
	
	
}

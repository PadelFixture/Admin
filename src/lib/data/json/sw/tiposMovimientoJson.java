package lib.data.json.sw;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.contrato;
import lib.classSW.tiposMovimiento;
import lib.data.json.dataTable;
import lib.db.sw.tiposMovimientoDB;
import lib.security.session;
import lib.struc.filterSql;

@Controller
public class tiposMovimientoJson {

	//Insert tiposMovimiento
	@RequestMapping(value = "/work/insertTiposMovimiento/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertTiposMovimiento(@RequestBody tiposMovimiento tiposMovimiento, HttpSession httpSession) throws Exception{

		session ses = new session(httpSession);
		if(ses.isValid()){
			return false;
		}

		return tiposMovimientoDB.insertTiposMovimiento(tiposMovimiento);
	}


	//Actualizar tiposMovimiento

	//Borrar tiposMovimiento por Id

	//Obtener tiposMovimiento por Id
	@RequestMapping(value = "/work/getTiposMovimientoById/{id}", method = {RequestMethod.GET})
	public @ResponseBody tiposMovimiento getTiposMovimientoById(@PathVariable String id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		tiposMovimiento tiposMovimiento = new tiposMovimiento();

		if (ses.isValid()) {
			return tiposMovimiento;
		}

		tiposMovimiento = tiposMovimientoDB.getTiposMovimientoById(id);
		return tiposMovimiento;

	}

	//Obtener Todas las tiposMovimientoes
	@RequestMapping(value = "/work/getTiposMovimiento/", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ArrayList<tiposMovimiento> getTiposMovimiento(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<tiposMovimiento> tiposMovimientoList = new ArrayList<tiposMovimiento>();

		if (ses.isValid()) {
			return tiposMovimientoList;
		}

		tiposMovimientoList = tiposMovimientoDB.getTiposMovimiento();
		return tiposMovimientoList;

	}

	//Obtener Todas las tiposMovimiento con Filtros
	@RequestMapping(value = "/work/getAllTiposMovimiento", method = { RequestMethod.POST, RequestMethod.GET} )
	public @ResponseBody dataTable getAllTiposMovimiento(HttpServletRequest request,HttpSession httpSession) {

		session ses = new session(httpSession);
		dataTable data = new dataTable();
		if (ses.isValid()){
			data.setDraw(0);
			data.init();
			return data;
		}

		Map<String, String[]> parameters = request.getParameterMap();
		ArrayList<filterSql> filter = new ArrayList<filterSql>();

		for(String key : parameters.keySet()){

			if(key.startsWith("vw_")){
				String[] vals = parameters.get(key);

				for(String val : vals){
					filterSql fil = new filterSql();
					fil.setCampo(key.substring(3));
					fil.setValue(val);
					filter.add(fil);
				}

			}//Fin del If para StartsWith

		}//Fin del For

		data.setDraw(0);
		data.init();

		//int start = Integer.parseInt(parameters.get("start")[0]);
		//int length = Integer.parseInt(parameters.get("length")[0]);
		
		ArrayList<contrato> datas;

		try {

			datas = new ArrayList<contrato>();/*contratoDB.getAlltiposMovimiento(filter, "", start, length); TODO: Metodo getAlltiposMovimiento*/
			Iterator<contrato> f = datas.iterator();

			//data.setRecordsFiltered(contratoDB.getAllTiposMovimiento(filter)); TODO: metodo getAlltiposMovimiento()
			//data.setRecordsTotal(contratoDB.getAllTiposMovimiento(filter)); TODO: metodo getAlltiposMovimiento()

			while (f.hasNext()){
				//contrato row = f.next();
				String[] d = {};/*{ row.getIdTrabajador()+"", row.getIdContrato()+"", row.getFechaIngreso(), row.getFechaTermino(), "", "" }; TODO: Colocar Datos*/ 
				data.setData(d);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}


		return data;

	}

}

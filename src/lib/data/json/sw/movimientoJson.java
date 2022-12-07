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

import lib.classSW.movimiento;
import lib.data.json.dataTable;
import lib.db.sw.movimientoDB;
import lib.security.session;
import lib.struc.filterSql;

@Controller
public class movimientoJson {

	//Insert movimiento
	@RequestMapping(value = "/work/insertMovimiento/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertMovimiento(@RequestBody movimiento movimiento, HttpSession httpSession) throws Exception{

		session ses = new session(httpSession);
		if(ses.isValid()){
			return false;
		}

		return movimientoDB.insertMovimiento(movimiento);
	}

	//Actualizar movimiento
	@RequestMapping(value = "/work/updateMovimiento/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateTrabajador(@RequestBody movimiento movimiento,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return movimientoDB.updateMovimiento(movimiento);
	}

	//Borrar movimiento por Id

	//Obtener movimiento por Id
	@RequestMapping(value = "/work/getmovimientoById/{id}", method = {RequestMethod.GET})
	public @ResponseBody movimiento getMovimientoById(@PathVariable String id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		movimiento movimiento = new movimiento();

		if (ses.isValid()) {
			return movimiento;
		}

		movimiento = movimientoDB.getMovimientoById(id);
		return movimiento;

	}

	//Obtener Todos los movimientos

	//Obtener movimientos por Id Trabajador con Filtros
	@RequestMapping(value = "/work/getMovimientoByIdTrabajador/{idTrabajador}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ArrayList<movimiento> getMovimientoByIdTrabajador(@PathVariable String idTrabajador, HttpServletRequest request, HttpSession httpSession) throws Exception{


		ArrayList<movimiento> datas = new ArrayList<movimiento>();
		
		session ses = new session(httpSession);
		
		if (ses.isValid()){
		
			return datas;
		}

		return datas = movimientoDB.getMovimientoByIdTrabajador(idTrabajador);

	}


	//Obtener movimientos por Id Trabajador para Cambio de Empresa
	@RequestMapping(value = "/work/getMovimientoByIdTrabajadorToCambioEmpresa/{idTrabajador}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable getMovimientoByIdTrabajadorToCambioEmpresa(@PathVariable String idTrabajador, HttpServletRequest request, HttpSession httpSession){


		session ses = new session(httpSession);
		dataTable data = new dataTable();
		if (ses.isValid()){
			data.setDraw(0);
			data.init();
			return data;
		}

		data.setDraw(0);
		data.init();

		ArrayList<movimiento> datas;

		try {

			datas = movimientoDB.getMovimientoByIdTrabajadorToCambioEmpresa(idTrabajador, null, "", 0, 0);

			Iterator<movimiento> f = datas.iterator();

			while (f.hasNext()){
				movimiento row = f.next();
				String[] d = { row.getIdTrabajador()+"", row.getSociedad()+"", row.getFechaIngreso(), row.getFechaTermino(), "", row.getIdMovimiento()+"" };
				data.setData(d);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}


		return data;

	}

	//TODO: Cambiando Separacion Servicio
	//Obtener movimientos por Id Trabajador para separacion
	@RequestMapping(value = "/work/getMovimientoByIdTrabajadorToSeparacion/{idTrabajador}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable getMovimientoByIdTrabajadorToSeparacion(@PathVariable String idTrabajador, HttpServletRequest request, HttpSession httpSession){


		session ses = new session(httpSession);
		dataTable data = new dataTable();
		if (ses.isValid()){
			data.setDraw(0);
			data.init();
			return data;
		}

		data.setDraw(0);
		data.init();

		ArrayList<movimiento> datas;

		try {

			datas = movimientoDB.getMovimientoByIdTrabajadorToSeparacion(idTrabajador, null, "", 0, 0);

			Iterator<movimiento> f = datas.iterator();

			while (f.hasNext()){
				movimiento row = f.next();
				String[] d = { row.getIdTrabajador()+"", row.getSociedad()+"", row.getIdMovimiento()+"", row.getFechaIngreso(), row.getFechaTermino(), "" };
				data.setData(d);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}


		return data;

	}



	//Obtener Todos los movimientos con Filtros
	@RequestMapping(value = "/work/getAllMovimiento", method = { RequestMethod.POST, RequestMethod.GET} )
	public @ResponseBody dataTable getAllMovimiento(HttpServletRequest request,HttpSession httpSession) {

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

		int start = Integer.parseInt(parameters.get("start")[0]);
		int length = Integer.parseInt(parameters.get("length")[0]);

		ArrayList<movimiento> datas;

		try {

			datas = movimientoDB.getAllMovimiento(filter, "", start, length);
			
			Iterator<movimiento> f = datas.iterator();

			data.setRecordsFiltered(movimientoDB.getAllMovimiento(filter));
			data.setRecordsTotal(movimientoDB.getAllMovimiento(filter));

			while (f.hasNext()){
				movimiento row = f.next();
				String[] d = { row.getIdTrabajador()+"", row.getIdMovimiento()+"", row.getFechaIngreso(), row.getFechaTermino(), "", "" };
				data.setData(d);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}


		return data;

	}


	//Obtener Ultimo movimiento activo por Id del Trabajador
	@RequestMapping(value = "/work/getUltimoMovimientoActivoByIdTrabajador/{id}", method = {RequestMethod.GET})
	public @ResponseBody movimiento getUltimoMovimientoActivoByIdTrabajador(@PathVariable String id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		movimiento movimiento = new movimiento();

		if (ses.isValid()) {
			return movimiento;
		}

		movimiento = movimientoDB.getUltimoMovimientoActivoByIdTrabajador(id);
		return movimiento;

	}



}

package lib.data.json.sw;

import java.sql.SQLException;
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

import lib.classSW.ContratoTrabajador;
import lib.classSW.Contratos;
import lib.classSW.contrato;
import lib.data.json.dataTable;
import lib.db.sw.contratoDB;
import lib.security.session;
import lib.struc.filterSql;

@Controller
public class contratoJson {

	// Insert Contrato
	@RequestMapping(value = "/work/insertContrato/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertContrato(@RequestBody contrato contrato, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}

		return contratoDB.insertContrato(contrato);
	}

	// Actualizar Contrato
	@RequestMapping(value = "/work/updateContrato/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateTrabajador(@RequestBody contrato contrato, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return contratoDB.updateContrato(contrato);
	}

	// Actualizar Contrato
	@RequestMapping(value = "/work/updateContratoTrabajador/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateContratoTrabajador(@RequestBody contrato contrato, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return contratoDB.updateContratoTrabajador(contrato);
	}
	
	
	// Actualizar Contrato
		@RequestMapping(value = "/work/contrato/activarDesactivarContrato/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean activarDesactivarContrato(@RequestBody contrato contrato, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			return contratoDB.activarDesactivarContrato(contrato);
		}
	
	

	// Borrar Contrato por Id

	// Obtener Contrato por Id
	@RequestMapping(value = "/work/getContratoById/{id}", method = { RequestMethod.GET })
	public @ResponseBody contrato getContratoById(@PathVariable String id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		contrato contrato = new contrato();

		if (ses.isValid()) {
			return contrato;
		}

		contrato = contratoDB.getContratoById(id);
		return contrato;

	}

	// Obtener Todos los Contratos

	// Obtener Contratos por Id Trabajador con Filtros
	@RequestMapping(value = "/work/getContratoByIdTrabajador/{idTrabajador}", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody ArrayList<Contratos> getContratoByIdTrabajador(@PathVariable String idTrabajador,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<Contratos> contratos = new ArrayList<Contratos>();
		// Contratos contratos = new Contratos();

		if (ses.isValid()) {

			return contratos;
		}

		contratos = contratoDB.getContratoByIdTrabajador(idTrabajador);

		return contratos;

	}
	
	
	// Obtener Contratos por Id Trabajador con Filtros
	@RequestMapping(value = "/work/contrato/getContratoWithFilter/", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody ArrayList<Contratos> getContratoWithFilter(HttpServletRequest request,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<Contratos> contratos = new ArrayList<Contratos>();
		// Contratos contratos = new Contratos();

		if (ses.isValid()) {

			return contratos;
		}
		
		Map<String, String[]> parameters = request.getParameterMap();
		ArrayList<filterSql> filter = new ArrayList<filterSql>();

		for (String key : parameters.keySet()) {
			String[] vals = parameters.get(key);

			for (String val : vals) {
				filterSql fil = new filterSql();
				fil.setCampo(key);
				fil.setValue(val);
				filter.add(fil);
			} // Fin del If para StartsWith

		} // Fin del For

		contratos = contratoDB.getContratoWithFilter(filter);

		return contratos;

	}
	

	// Obtener Contratos por Id Trabajador para Cambio de Empresa
	@RequestMapping(value = "/work/getContratoByIdTrabajadorToCambioEmpresa/{idTrabajador}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable getContratoByIdTrabajadorToCambioEmpresa(@PathVariable String idTrabajador,
			HttpServletRequest request, HttpSession httpSession) {

		session ses = new session(httpSession);
		dataTable data = new dataTable();
		if (ses.isValid()) {
			data.setDraw(0);
			data.init();
			return data;
		}

		data.setDraw(0);
		data.init();

		ArrayList<contrato> datas;

		try {

			datas = contratoDB.getContratoByIdTrabajadorToCambioEmpresa(idTrabajador, null, "", 0, 0);

			Iterator<contrato> f = datas.iterator();

			while (f.hasNext()) {
				contrato row = f.next();
				String[] d = { row.getCodigo_trabajador() + "", row.getId_sociedad() + "",
						row.getFecha_inicio_actividad(), row.getFecha_termino_actividad(), "", row.getId() + "" };
				data.setData(d);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;

	}
	
	
	// Obtener Contratos por Id Trabajador para Cambio de Empresa
		@RequestMapping(value = "/work/contrato/getCambioEmpresa/{idContrato}/{fechaInicioCambio}/{aprobadoPor}/{nuevaSociedad}", method = {
				RequestMethod.POST, RequestMethod.GET })
		public @ResponseBody String getContratoByIdTrabajadorToCambioEmpresa(@PathVariable String fechaInicioCambio,
				@PathVariable String idContrato, @PathVariable String aprobadoPor, @PathVariable String nuevaSociedad , HttpSession httpSession) throws Exception {

			fechaInicioCambio = fechaInicioCambio.equals("null") ? "" : fechaInicioCambio;
			
			contratoDB.getCambioEmpresa(idContrato,fechaInicioCambio, aprobadoPor, nuevaSociedad);
			
			return "ejecutado";

		}
	
	

	// Obtener Contratos por Id Trabajador para separacion
	@RequestMapping(value = "/work/getContratoByIdTrabajadorToSeparacion/{idTrabajador}", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody dataTable getContratoByIdTrabajadorToSeparacion(@PathVariable String idTrabajador,
			HttpServletRequest request, HttpSession httpSession) {

		session ses = new session(httpSession);
		dataTable data = new dataTable();
		if (ses.isValid()) {
			data.setDraw(0);
			data.init();
			return data;
		}

		data.setDraw(0);
		data.init();

		ArrayList<contrato> datas;

		try {

			datas = contratoDB.getContratoByIdTrabajadorToSeparacion(idTrabajador, null, "", 0, 0);

			Iterator<contrato> f = datas.iterator();

			while (f.hasNext()) {
				contrato row = f.next();
				String[] d = { row.getCodigo_trabajador() + "", row.getId_sociedad() + "", row.getId() + "",
						row.getFecha_inicio_actividad(), row.getFecha_termino_actividad(), "" };
				data.setData(d);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;

	}

	// Obtener Todos los Contratos con Filtros
	@RequestMapping(value = "/work/getAllContratoWithFilter", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ArrayList<contrato> getAllContratoWithFilter(HttpServletRequest request,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return null;
		}

		Map<String, String[]> parameters = request.getParameterMap();
		ArrayList<filterSql> filter = new ArrayList<filterSql>();

		for (String key : parameters.keySet()) {
			String[] vals = parameters.get(key);

			for (String val : vals) {
				filterSql fil = new filterSql();
				fil.setCampo(key);
				fil.setValue(val);
				filter.add(fil);
			} // Fin del If para StartsWith

		} // Fin del For

		try {

			return contratoDB.getAllContratoWithFilter(filter);

		} catch (Exception e) {
			e.getMessage();
			return null;
		}

	}

	// Obtener Todos los Contratos con Filtros
	@RequestMapping(value = "/work/getAllContratoTrabajadorWithFilter", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody ArrayList<contrato> getAllContratoTrabajadorWithFilter(HttpServletRequest request,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return null;
		}

		Map<String, String[]> parameters = request.getParameterMap();
		ArrayList<filterSql> filter = new ArrayList<filterSql>();

		for (String key : parameters.keySet()) {
			String[] vals = parameters.get(key);

			for (String val : vals) {
				filterSql fil = new filterSql();
				fil.setCampo(key);
				fil.setValue(val);
				filter.add(fil);
			} // Fin del If para StartsWith

		} // Fin del For

		try {

			return contratoDB.getAllContratoTrabajadorWithFilter(filter);

		} catch (Exception e) {
			e.getMessage();
			return null;
		}

	}

	// Obtener Todos los Contratos con Filtros
	@RequestMapping(value = "/work/contrato/getAllContratoTrabajadorWithFilter", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody ArrayList<ContratoTrabajador> getAllContratoTrabajadorWithFilter2(HttpServletRequest request,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return null;
		}

		Map<String, String[]> parameters = request.getParameterMap();
		ArrayList<filterSql> filter = new ArrayList<filterSql>();

		for (String key : parameters.keySet()) {
			String[] vals = parameters.get(key);

			for (String val : vals) {
				filterSql fil = new filterSql();
				fil.setCampo(key);
				fil.setValue(val);
				filter.add(fil);
			} // Fin del If para StartsWith

		} // Fin del For

		try {

			return contratoDB.getAllContratoTrabajadorWithFilter2(filter);

		} catch (Exception e) {
			e.getMessage();
			return null;
		}

	}

	// Obtener Ultimo contrato activo por Id del Trabajador
	@RequestMapping(value = "/work/getUltimoContratoActivoByIdTrabajador/{id}", method = { RequestMethod.GET })
	public @ResponseBody contrato getUltimoContratoActivoByIdTrabajador(@PathVariable String id,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		contrato contrato = new contrato();

		if (ses.isValid()) {
			return contrato;
		}

		contrato = contratoDB.getUltimoContratoActivoByIdTrabajador(id);
		return contrato;

	}

	// Obtener Ultimo contrato activo por Id del Trabajador
	@RequestMapping(value = "/work/getUltimoContrato/", method = { RequestMethod.GET })
	public @ResponseBody contrato getUltimoContrato(HttpServletRequest request, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		contrato contrato = new contrato();

		if (ses.isValid()) {
			return contrato;
		}

		Map<String, String[]> parameters = request.getParameterMap();
		ArrayList<filterSql> filter = new ArrayList<filterSql>();

		for (String key : parameters.keySet()) {
			String[] vals = parameters.get(key);

			for (String val : vals) {
				filterSql fil = new filterSql();
				fil.setCampo(key);
				fil.setValue(val);
				filter.add(fil);
			} // Fin del If para StartsWith

		} // Fin del For

		contrato = contratoDB.getUltimoContrato(filter);
		return contrato;

	}

	// Obtener todos los contratos Activos del trabajor
	@RequestMapping(value = "/work/getAllContratoActivoByIdTrabajador/{id}", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<contrato> getAllContratoActivoByIdTrabajador(@PathVariable String id,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<contrato> contrato = new ArrayList<contrato>();

		if (ses.isValid()) {
			return contrato;
		}

		contrato = contratoDB.getAllContratoActivoByIdTrabajador(id);
		return contrato;

	}

	// Obtener todas las sociedades dado el codigo de trabajador
	@RequestMapping(value = "/work/getAllSociedadesByCodigoTrabajador/{codigo_trabajador}", method = {
			RequestMethod.GET })
	public @ResponseBody ArrayList<Integer> getAllSociedadesByCodigoTrabajador(@PathVariable String codigo_trabajador,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<Integer> idSociedadLista = null;

		if (ses.isValid()) {
			return idSociedadLista;
		}

		idSociedadLista = contratoDB.getAllSociedadesByCodigoTrabajador(codigo_trabajador);
		return idSociedadLista;

	}

}

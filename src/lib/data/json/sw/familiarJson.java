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

import lib.classSW.sw_familiar;
import lib.data.json.dataTable;
import lib.db.sw.familiarDB;
import lib.security.session;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;

@Controller
public class familiarJson {

	// Insertar Familiar
	@RequestMapping(value = "/work/insertFamiliar/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertFamiliar(@RequestBody sw_familiar sw_familiar, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}

		return familiarDB.insertFamiliar(sw_familiar);
	}

	
	// Actualizar Familiar Contrato
		@RequestMapping(value = "/work/actualizarFamiliarContrato/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean actualizarFamiliarContrato(@RequestBody sw_familiar sw_familiar, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}

			return familiarDB.actualizarFamiliarContrato(sw_familiar);
		}
	
	// Update Familiar

	// Obtener Familiar By ID

	// Obtener Todos los Familiares por Id Trabajador con Filtros
	@RequestMapping(value = "/work/getFamiliarByIdTrabajador/{idTrabajador}", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody dataTable getFamiliarByIdTrabajador(@PathVariable String idTrabajador,
			HttpServletRequest request, HttpSession httpSession) {

		session ses = new session(httpSession);
		dataTable data = new dataTable();
		if (ses.isValid()) {

			data.setDraw(0);
			data.init();
			return data;
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

			}
		}

		data.setDraw(0);
		data.init();

		ArrayList<sw_familiar> datas;
		try {
			datas = familiarDB.getFamiliarByIdTrabajador(idTrabajador, filter);

			Iterator<sw_familiar> f = datas.iterator();

			while (f.hasNext()) {
				sw_familiar row = f.next();
				String[] d = { row.getIdFamiliar() + "",
						row.getApellidoPaternoFamiliar() + " " + row.getApellidoMaternoFamiliar() + ", "
								+ row.getNombreFamiliar(),
						row.getfNacimientoFamiliar(), row.getTipoCarga() + "", "".equals(row.getTramoFamiliar().trim()) ? " " : "TRAMO " + row.getTramoFamiliar()  + "",
						GeneralUtility.formatStringNumberWithDotAndDecimal(row.getMontoFamiliar()+""), row.getFechaInicioFamiliar(), row.getFechaFinFamiliar(), row.getIdContrato() + "", "" };

				data.setData(d);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;

	}

	// Actualizar familiar
	@RequestMapping(value = "/work/updateFamiliar/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateFamiliar(@RequestBody sw_familiar sw_familiar, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return familiarDB.updateFamiliar(sw_familiar);
	}

	// Obtener familiar por Id
	@RequestMapping(value = "/work/getFamiliarById/{id}", method = { RequestMethod.GET })
	public @ResponseBody sw_familiar getFamiliarById(@PathVariable String id, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		sw_familiar sw_familiar = new sw_familiar();

		if (ses.isValid()) {
			return sw_familiar;
		}

		sw_familiar = familiarDB.getFamiliarById(id);
		return sw_familiar;

	}

	// Eliminar Familiar
//	@RequestMapping(value = "/work/getFamiliarById/{id}", method = { RequestMethod.GET })
//	public @ResponseBody sw_familiar getFamiliarById(@PathVariable String id, HttpSession httpSession)
//			throws Exception {
//
//		session ses = new session(httpSession);
//		sw_familiar sw_familiar = new sw_familiar();
//
//		if (ses.isValid()) {
//			return sw_familiar;
//		}
//
//		sw_familiar = familiarDB.getFamiliarById(id);
//		return sw_familiar;
//
//	}

	
	
	// Obtener trabajadores por Rut
		@RequestMapping(value = "/work/existFamiliarByRut/", method = { RequestMethod.POST })
		public @ResponseBody sw_familiar existFamiliarByRut(@RequestBody String rut, HttpSession httpSession)
				throws Exception {

			String rutNumber = rut.split("=")[1];

			session ses = new session(httpSession);
			sw_familiar sw_familiar = new sw_familiar();

			if (ses.isValid()) {
				return sw_familiar;
			}

			sw_familiar = familiarDB.getFamiliarByRut(rutNumber);
			return sw_familiar;

		}
	
	
}

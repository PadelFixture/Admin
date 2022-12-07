package lib.data.json.sw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import excelCreator.Excel;
//import lib.ClassSASW.ResponseObject;
import lib.classSW.CentraRow;
import lib.classSW.Centralizacion;
import lib.classSW.Centralizar;
import lib.classSW.sociedad;
import lib.db.sw.CentralizacionDB;
import lib.db.sw.sociedadDB;
import lib.security.session;
import lib.struc.filterSql;
import wordCreator.utils;

@Controller
public class CentralizacionJSON {

	// Obtener Todas los Cargos
	@RequestMapping(value = "/work/Centralizacion/getCentralizacion/{soc}/{periodo}", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<CentraRow> getCentralizacion(@PathVariable("soc") String soc,
			@PathVariable("periodo") int periodo, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<CentraRow> rows = new ArrayList<CentraRow>();

		if (ses.isValid()) {
			return rows;
		}
		return rows = CentralizacionDB.getCentralizacion(soc, periodo);

	}

	@RequestMapping(value = "/work/Centralizacion/Centralizar/{soc}/{periodo}/{date}/{usuario}", method = { RequestMethod.GET })
	public @ResponseBody String Centralizar(@PathVariable("soc") String soc, @PathVariable("periodo") int periodo,
			@PathVariable("date") String date, @PathVariable("usuario") String usuario, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<CentraRow> rows = new ArrayList<CentraRow>();

		if (ses.isValid()) {
			return null;
		}
		rows = CentralizacionDB.getCentralizacion(soc, periodo);

		Centralizar c = new Centralizar();
		String ObjetoJSON = c.CentralizarDatos(rows, soc, periodo, date, usuario);
		System.out.println(ObjetoJSON);

		return ObjetoJSON;
	}

	@RequestMapping(value = "/work/Centralizacion/getPeriodosBy/{soc}", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<Periodos> getPeriodosBy(@PathVariable("soc") String soc, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		ArrayList<Periodos> rows = new ArrayList<Periodos>();

		if (ses.isValid()) {
			return rows;
		}
		return rows = CentralizacionDB.getPeriodosBy(soc);

	}

	@RequestMapping(value = "/work/Centralizacion/descargarCentralizacion/{soc}/{per}/{nombre}", method = RequestMethod.GET)
	@CrossOrigin(origins = { "*" })
	public @ResponseBody String descargarLibroRemuneraciones(@PathVariable("soc") String soc,
			@PathVariable("per") int per, @PathVariable("nombre") String nombre, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		try {
			Excel e = new Excel();
			ArrayList<CentraRow> cr = CentralizacionDB.getCentralizacion(soc, per);
			sociedad s = sociedadDB.getSociedadByCod(soc);
			Workbook w = e.generarCentralizacion(cr, s.getDenominacionSociedad(), per);

			String fileName = nombre;
			fileName = fileName.replaceAll("\"", "");
			System.out.println("ruta: {}" + fileName);

			String urlDocGenerado = utils.reportesExcel() + fileName + ".xlsx";

			System.out.println("aqui   " + urlDocGenerado);
			ServletOutputStream out = response.getOutputStream();
			response.addHeader("Content-disposition", "attachment; filename= " + fileName + ".xlsx");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setCharacterEncoding("iso-8859-1");
			w.write(out);
			w.close();
			out.flush();
			out.close();
			System.out.println("termine de hacer el archivo");
			return urlDocGenerado;

		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	// InsertOrUpdate
	@RequestMapping(value = "/work/Centralizacion/insertOrUpdateCentralizacion/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Centralizacion insertOrUpdateCentralizacion(@RequestBody Centralizacion Centralizacion,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return null;
		}

		try {
			Centralizacion = CentralizacionDB.insertOrUpdateCentralizacion(Centralizacion);
		} catch (Exception e) {
			return null;
		}

		return Centralizacion;
	} // fin insertar
	
	//ChangeEstadoCentralizacion
	@RequestMapping(value = "/work/Centralizacion/updateEstadoCentralizacion/{id_centralizacion}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String updateEstadoCentralizacion(@PathVariable String id_centralizacion, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			if (ses.isValid()) {
				return null;
			}

			try {
				CentralizacionDB.updateEstadoCentralizacion(id_centralizacion);
			} catch (Exception e) {
				return null;
			}

			return "true";
		} // fin insertar
	
	

	// Delete

	// Get
	@RequestMapping(value = "/work/Centralizacion/getCentralizacionWithFilter/", method = { RequestMethod.GET })
	public @ResponseBody ResponseEntity<Set<ArrayList<Centralizacion>>> getCentralizacionWithFilter(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<Centralizacion> rows = new ArrayList<Centralizacion>();
		
		if (ses.isValid()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		// Obtener todos los parametros del URL
		Map<String, String[]> parameters = request.getParameterMap();

		ArrayList<filterSql> filter = new ArrayList<filterSql>();

		// Obtener todos los parametros enviados por el URL
		for (String key : parameters.keySet()) {
			String[] vals = parameters.get(key);
			// Obtener cada uno de los parametros y valores
			for (String val : vals) {
				filterSql fil = new filterSql();
				fil.setCampo(key);
				fil.setValue(val);
				// Añadir campo y valor
				filter.add(fil);
			}
		}

		
		rows = CentralizacionDB.getCentralizacionWithFilter(filter);
		return new ResponseEntity<>(Collections.singleton(rows), HttpStatus.OK);

	}

}

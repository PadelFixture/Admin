package lib.data.json.sw;

import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import excelCreator.Excel;
import excelCreator.Excel2;
//import lib.ClassSASW.ResponseObject;
import lib.classSW.CentraRowReporte;
import lib.classSW.sociedad;
import lib.db.sw.CentralizacionReporteDB;
import lib.db.sw.sociedadDB;
import lib.security.session;
import wordCreator.utils;

@Controller
public class CentralizacionReporteJSON {

	// Obtener Todas los Cargos
	@RequestMapping(value = "/work/CentralizacionReporte/getCentralizacionReporte/{soc}/{periodo}", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<CentraRowReporte> getCentralizacionReporte(@PathVariable("soc") String soc,
			@PathVariable("periodo") int periodo, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<CentraRowReporte> rows = new ArrayList<CentraRowReporte>();

		if (ses.isValid()) {
			return rows;
		}
		return rows = CentralizacionReporteDB.getCentralizacionReporte(soc, periodo);

	}

//	@RequestMapping(value = "/work/CentralizacionReporte/Centralizar/{soc}/{periodo}/{date}/{usuario}", method = { RequestMethod.GET })
//	public @ResponseBody String Centralizar(@PathVariable("soc") String soc, @PathVariable("periodo") int periodo,
//			@PathVariable("date") String date, @PathVariable("usuario") String usuario, HttpSession httpSession) throws Exception {
//
//		session ses = new session(httpSession);
//		ArrayList<CentraRowReporte> rows = new ArrayList<CentraRowReporte>();
//
//		if (ses.isValid()) {
//			return null;
//		}
//		rows = CentralizacionReporteDB.getCentralizacionReporte(soc, periodo);
//
//		Centralizar c = new Centralizar();
//		String ObjetoJSON = c.CentralizarDatos(rows, soc, periodo, date, usuario);
//		System.out.println(ObjetoJSON);
//
//		return ObjetoJSON;
//	}

	@RequestMapping(value = "/work/CentralizacionReporte/getPeriodosBy/{soc}", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<Periodos> getPeriodosBy(@PathVariable("soc") String soc, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		ArrayList<Periodos> rows = new ArrayList<Periodos>();

		if (ses.isValid()) {
			return rows;
		}
		return rows = CentralizacionReporteDB.getPeriodosBy(soc);

	}

	@RequestMapping(value = "/work/CentralizacionReporte/descargarCentralizacionReporte/{soc}/{per}/{nombre}", method = RequestMethod.GET)
	@CrossOrigin(origins = { "*" })
	public @ResponseBody String descargarLibroRemuneraciones(@PathVariable("soc") String soc,
			@PathVariable("per") int per, @PathVariable("nombre") String nombre, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		try {
			Excel2 e = new Excel2();
			ArrayList<CentraRowReporte> cr = CentralizacionReporteDB.getCentralizacionReporte(soc, per);
			sociedad s = sociedadDB.getSociedadByCod(soc);
			Workbook w = e.generarCentralizacionReporte(cr, s.getDenominacionSociedad(), per);

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
//	@RequestMapping(value = "/work/CentralizacionReporte/insertOrUpdateCentralizacionReporte/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody CentralizacionReporte insertOrUpdateCentralizacionReporte(@RequestBody CentralizacionReporte CentralizacionReporte,
//			HttpSession httpSession) throws Exception {
//
//		session ses = new session(httpSession);
//		if (ses.isValid()) {
//			return null;
//		}
//
//		try {
//			CentralizacionReporte = CentralizacionReporteDB.insertOrUpdateCentralizacionReporte(CentralizacionReporte);
//		} catch (Exception e) {
//			return null;
//		}
//
//		return CentralizacionReporte;
//	} // fin insertar
	
	//ChangeEstadoCentralizacionReporte
	@RequestMapping(value = "/work/CentralizacionReporte/updateEstadoCentralizacionReporte/{id_CentralizacionReporte}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String updateEstadoCentralizacionReporte(@PathVariable String id_CentralizacionReporte, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			if (ses.isValid()) {
				return null;
			}

			try {
				CentralizacionReporteDB.updateEstadoCentralizacionReporte(id_CentralizacionReporte);
			} catch (Exception e) {
				return null;
			}

			return "true";
		} // fin insertar
	
	

	// Delete

	// Get
//	@RequestMapping(value = "/work/CentralizacionReporte/getCentralizacionReporteWithFilter/", method = { RequestMethod.GET })
//	public @ResponseBody ResponseEntity<Set<ArrayList<CentralizacionReporte>>> getCentralizacionReporteWithFilter(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) throws Exception {
//
//		session ses = new session(httpSession);
//		ArrayList<CentralizacionReporte> rows = new ArrayList<CentralizacionReporte>();
//		
//		if (ses.isValid()) {
//			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//		}
//		
//		// Obtener todos los parametros del URL
//		Map<String, String[]> parameters = request.getParameterMap();
//
//		ArrayList<filterSql> filter = new ArrayList<filterSql>();
//
//		// Obtener todos los parametros enviados por el URL
//		for (String key : parameters.keySet()) {
//			String[] vals = parameters.get(key);
//			// Obtener cada uno de los parametros y valores
//			for (String val : vals) {
//				filterSql fil = new filterSql();
//				fil.setCampo(key);
//				fil.setValue(val);
//				// Añadir campo y valor
//				filter.add(fil);
//			}
//		}
//
//		
//		rows = CentralizacionReporteDB.getCentralizacionReporteWithFilter(filter);
//		return new ResponseEntity<>(Collections.singleton(rows), HttpStatus.OK);
//
//	}

}

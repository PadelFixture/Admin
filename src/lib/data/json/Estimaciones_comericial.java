package lib.data.json;


import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.SADB.Categoria;
import lib.SADB.ESTIMACIONES;
import lib.SADB.MAPA;
import lib.SADB.RENDIMIENTO;
import lib.SADB.material;
import lib.classSA.CATEGORIA;
import lib.classSA.PROGRA_FITOSANITARIO;
import lib.classSA.RENDIMIENTO_GENERAL;
import lib.classSA.SEMANA;
import lib.classSA.VERSION;
import lib.classSA.estimacionAnual;
import lib.classSA.estimacion_calibre_categoria;
import lib.classSA.estimacion_data_prd;
import lib.classSA.estimacion_productiva;
import lib.classSA.estimacion_semanal_21;
import lib.classSA.parametros_estimacion;
import lib.security.session;
@Controller
public class Estimaciones_comericial {

	@RequestMapping(value = "/AGRO/GET_SEMANAS/{especie}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody SEMANA GET_SEMANAS(@PathVariable String especie, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		SEMANA ob = new SEMANA();
		if (ses.isValid()) {
			return ob;
		}
		return ob =  ESTIMACIONES.getSemanas(especie);
	}
	
	@RequestMapping(value = "/AGRO/GET_CATEGORIA/{especie}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<CATEGORIA> GET_CATEGORIA(@PathVariable String especie, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<CATEGORIA> ob = new ArrayList<CATEGORIA>();
		if (ses.isValid()) {
			return ob;
		}
		return ob =  ESTIMACIONES.getCategoria(especie);
	}
	
	@RequestMapping(value = "/AGRO/GET_CALIBRE/{especie}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<CATEGORIA> GET_CALIBRE(@PathVariable String especie, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<CATEGORIA> ob = new ArrayList<CATEGORIA>();
		if (ses.isValid()) {
			return ob;
		}
		return ob =  ESTIMACIONES.getCalibre(especie);
	}
	
	@RequestMapping(value = "/AGRO/INSERT_ANUAL/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean RESERVA_MATERIAL(@RequestBody  estimacionAnual row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		System.out.println("ENTRA");
		if (ses.isValid()) {
			return false;
		}
		return ESTIMACIONES.insertEstimacion(row);
	}
	
	@RequestMapping(value = "/AGRO/UPDATE_ANUAL/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPDATE_ANUAL(@RequestBody  estimacionAnual row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		System.out.println("ENTRA");
		if (ses.isValid()) {
			return false;
		}
		return ESTIMACIONES.updateEstimacion(row);
	}
	
	@RequestMapping(value = "/AGRO/GET_ESTIMACION_ANUAL/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody estimacionAnual GET_ESTIMACION_ANUAL(@RequestBody estimacionAnual  row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		estimacionAnual ob = new estimacionAnual();
		if (ses.isValid()) {
			return ob;
		}
		return ob =  ESTIMACIONES.getEstimacionAnual(row);
	}
	
	
	@RequestMapping(value = "/AGRO/GET_TEMPORADA/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<VERSION> GET_TEMPORADA(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<VERSION> respuesta = new ArrayList<VERSION>();
		if (ses.isValid()) {
			return respuesta;
		}
		return respuesta =  ESTIMACIONES.getTemporada();
	}
	
	@RequestMapping(value = "/AGRO/GET_VERSION/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<VERSION> GET_VERSION( HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<VERSION> respuesta = new ArrayList<VERSION>();
		if (ses.isValid()) {
			return respuesta;
		}
		return respuesta =  ESTIMACIONES.getVersion();
	}
	
	@RequestMapping(value = "/AGRO/GET_DATA_21_DIAS/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<estimacion_data_prd> GET_DATA_21_DIAS(@RequestBody estimacionAnual  row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<estimacion_data_prd> respuesta = new ArrayList<estimacion_data_prd>();
		if (ses.isValid()) {
			return respuesta;
		}
		return respuesta =  ESTIMACIONES.getData21Dias(row, false);
	}
	
	@RequestMapping(value = "/AGRO/INSERT_ESTIMACION_21/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean INSERT_ESTIMACION_21(@RequestBody  estimacion_semanal_21 row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return ESTIMACIONES.insertEstimacionSemanal(row);
	}
	
	@RequestMapping(value = "/AGRO/UPDATE_ESTIMACION_21/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPDATE_ESTIMACION_21(@RequestBody  estimacion_semanal_21 row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return ESTIMACIONES.updateEstimacionSemanal(row);
	}
	
	@RequestMapping(value = "/AGRO/GET_VARIEDAD_ESTIMADA/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<estimacionAnual> GET_VARIEDAD_ESTIMADA(@RequestBody estimacionAnual  row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<estimacionAnual> respuesta = new ArrayList<estimacionAnual>();
		if (ses.isValid()) {
			return respuesta;
		}
		return respuesta =  ESTIMACIONES.getVariedadesEstimadas(row);
	}
	
	@RequestMapping(value = "/AGRO/GET_DATA_CATEGORIA_CALIBRE/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<estimacion_calibre_categoria> GET_DATA_CATEGORIA_CALIBRE(@RequestBody estimacion_calibre_categoria  row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<estimacion_calibre_categoria> respuesta = new ArrayList<estimacion_calibre_categoria>();
		if (ses.isValid()) {
			return respuesta;
		}
		return respuesta =  ESTIMACIONES.getDataCalibreCategoria(row.getCodigo(), row.getSemana(), row.getEditado(),0);
	}
	
	@RequestMapping(value = "/AGRO/REPORTE_ESTIMACION_ANUAL/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<estimacionAnual> REPORTE_ESTIMACION_ANUAL(@RequestBody estimacionAnual  row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<estimacionAnual> respuesta = new ArrayList<estimacionAnual>();
		if (ses.isValid()) {
			return respuesta;
		}
		return respuesta =  ESTIMACIONES.getReporteEstimacionAnual(row);
	}
	
	@RequestMapping(value = "/AGRO/REPORTE_ESTIMACION_SEMANAL/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<estimacionAnual> REPORTE_ESTIMACION_SEMANAL(@RequestBody estimacionAnual  row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<estimacionAnual> respuesta = new ArrayList<estimacionAnual>();
		if (ses.isValid()) {
			return respuesta;
		}
		return respuesta =  ESTIMACIONES.getReporteEstimacionSemana(row);
	}
}

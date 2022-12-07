package lib.data.json.sw;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import SWDB.impexp_trabajador;
import SWDB.sw_HorasAsistenciaDB;
import lib.classSW.CargarTipodePago;
import lib.classSW.HorasAsistencia;
import lib.classSW.LoadTrabajadorSociedad;
import lib.classSW.UpdateTrabajadorHD;
import lib.security.session;
import lib.struc.trabajadores;
import wordCreator.utils;

@Controller
public class sw_horas_Asistencia {
	
	@RequestMapping(value = "/work/insertHoraAsistencia/{agro}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String insertarHoraLicencia(@RequestBody ArrayList<HorasAsistencia> row,
			@PathVariable int agro,HttpSession httpSession) throws Exception {
		
		
		session ses = new session(httpSession);
		String es = null;
	
		if (ses.isValid()) {
			return es;
		}
		
		String	recc = "";
		for (HorasAsistencia rec : row) {

			recc = sw_HorasAsistenciaDB.insertarHoraLicencia(rec,agro);
		}

		return recc;

	}
	
	
		
		@RequestMapping(value = "/work/allTrabajaCodNomHorasAsistencia/{empr},{div},{subdiv},{gru},{concepto},{periodo},{estado_proceso},{rolPrivado}", method = {
				RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody ArrayList<LoadTrabajadorSociedad> getallTrabajaCodNomHorasAsistencia(@PathVariable String empr,
				@PathVariable String div, @PathVariable String subdiv, @PathVariable String gru,
				@PathVariable String concepto,
				@PathVariable String periodo,
				@PathVariable String estado_proceso,
				@PathVariable int rolPrivado,
				HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);

			ArrayList<LoadTrabajadorSociedad> r = new ArrayList<LoadTrabajadorSociedad>();

			if (ses.isValid()) {
				return r;
			}
			r = sw_HorasAsistenciaDB.getallTrabajaCodNomHorasAsistencia(empr, div, subdiv, gru, concepto, periodo,estado_proceso,rolPrivado);

			return r;

		}
		
		@RequestMapping(value = "/work/selectBuscarHorasAsistencia/{soc},{cod},{div},{subdiv},{gru},{concepto},{periodo},{estado_proceso}", method = {
				RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody ArrayList<LoadTrabajadorSociedad> getselectBuscarHorasAsistencia(@PathVariable String soc,@PathVariable String cod,
				@PathVariable String div, @PathVariable String subdiv, @PathVariable String gru,
				@PathVariable String concepto,@PathVariable String periodo,@PathVariable String estado_proceso, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);

			ArrayList<LoadTrabajadorSociedad> r = new ArrayList<LoadTrabajadorSociedad>();

			if (ses.isValid()) {
				return r;
			}
			r = sw_HorasAsistenciaDB.getselectBuscarHorasAsistencia(soc,cod, div, subdiv, gru, concepto, periodo,estado_proceso);

			return r;

		}
		
		@RequestMapping(value = "/work/EliminarHora_Asistencia/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean eliminarHoraAsistencia(@PathVariable int id, HttpSession httpSession) throws Exception {
			boolean recc = false;
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return recc;
			}

			recc = sw_HorasAsistenciaDB.eliminarHoraAsistencia(id);

			return recc;

		}
		
		@RequestMapping(value = "/work/ListaConcepto/", method = { RequestMethod.GET, RequestMethod.POST })
		
		public @ResponseBody ArrayList<CargarTipodePago> getListaconceptoHA(HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<CargarTipodePago> es = new ArrayList<CargarTipodePago>();

			if (ses.isValid()) {
				return es;
			}

			es = sw_HorasAsistenciaDB.getListaconceptoHA();
			return es;

		}
		
		@RequestMapping(value = "/work/UpdateHora_Asistencia", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean updateHoraAsistencia(@RequestBody HorasAsistencia row, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			return sw_HorasAsistenciaDB.updateHoraAsistencia(row);
		}
     
		@RequestMapping(value = "/work/insertExelmysqlHoraAsistencia/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody String insertExcelHoraAsistencia(@RequestBody HorasAsistencia row, HttpSession httpSession) throws Exception {
			String recc = "";
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return recc;
			}
            
			

				recc = sw_HorasAsistenciaDB.insertExcelHoraAsistencia(row);

			
			
			return recc;

		}
		
		@RequestMapping(value = "/work/insertExelmysqlDiasFalta/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody String insertExcelHoraDiasFalta(@RequestBody HorasAsistencia row, HttpSession httpSession) throws Exception {
			String recc = "";
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return recc;
			}
            
			

				recc = sw_HorasAsistenciaDB.insertExcelHoraDiasFalta(row);

			
			
			return recc;

		}
}

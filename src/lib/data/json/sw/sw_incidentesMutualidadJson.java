package lib.data.json.sw;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import SWDB.impexp_trabajador;
import SWDB.sw_IncidentesMutualidadDB;
import lib.classSW.IncidentesMutualidad;
import lib.classSW.LoadTrabajadorSociedad;
import lib.classSW.Previred;
import lib.classSW.reclutamiento;
import lib.security.session;

@Controller
public class sw_incidentesMutualidadJson {
	
	@RequestMapping(value = "/work/BuscarIncidentes/{incidente}", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<IncidentesMutualidad> getIncidentes(
			@PathVariable int incidente, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<IncidentesMutualidad> r = new ArrayList<IncidentesMutualidad>();

		if (ses.isValid()) {
			return r;
		}
		r = sw_IncidentesMutualidadDB.getIncidentes(incidente);

		return r;

	}
	
	@RequestMapping(value = "/work/getCausasEventoMutual/", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<IncidentesMutualidad> getCausas(
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<IncidentesMutualidad> r = new ArrayList<IncidentesMutualidad>();

		if (ses.isValid()) {
			return r;
		}
		r = sw_IncidentesMutualidadDB.getCausas();

		return r;

	}
	
	@RequestMapping(value = "/work/UpdateIncidentesMutualidad", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateIncidentesMutualidad(@RequestBody IncidentesMutualidad row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return sw_IncidentesMutualidadDB.updateIncidentesMutualidad(row);
	}
	
	@RequestMapping(value = "/work/Eliminar_incidentesMutualidad/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean eliminarIncidenteMutualidad(@PathVariable int id, HttpSession httpSession) throws Exception {
		boolean recc = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return recc;
		}

		recc = sw_IncidentesMutualidadDB.eliminarIncidenteMutualidad(id);

		return recc;

	}
	
	@RequestMapping(value = "/work/insertIncidenciaMutualidad/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String insertarIncidenciaM(@RequestBody ArrayList<IncidentesMutualidad> row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		String es = null;
	
		if (ses.isValid()) {
			return es;
		}
		
		String	recc = "";
		for (IncidentesMutualidad rec : row) {

			recc = sw_IncidentesMutualidadDB.insertarIncidenciaM(rec);
		}

		return recc;

	}
		
	@RequestMapping(value = "/work/registrodeincidentesdetalle/{soc},{huerto_},{zona_},{ceco_},"
															+ "{periodo},{estado_proceso},{tipo_accidente},"
															+ "{clasificacion_accidente},{causa_accidente},"
															+ "{anio_inicio},{anio_fin},{cod}", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<IncidentesMutualidad> getregistrodeincidentesdetalle(
			@PathVariable String soc,
			@PathVariable String huerto_,
			@PathVariable String zona_, 
			@PathVariable String ceco_, 
			@PathVariable String periodo,
			@PathVariable String estado_proceso,
			@PathVariable String tipo_accidente,
			@PathVariable String clasificacion_accidente,
			@PathVariable String causa_accidente,
			@PathVariable String anio_inicio,
			@PathVariable String anio_fin,
			@PathVariable String cod,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<IncidentesMutualidad> r = new ArrayList<IncidentesMutualidad>();

		if (ses.isValid()) {
			return r;
		}
		r = sw_IncidentesMutualidadDB.getregistrodeincidentesdetalle(soc,huerto_,zona_,ceco_,periodo,estado_proceso,tipo_accidente,
				clasificacion_accidente,causa_accidente,anio_inicio,anio_fin,cod);

		return r;

	}
	
	@RequestMapping(value = "/work/CrearExelDetalleregistroAccidente/{tipoinforme}", method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getExelDetalleregistroAccidente(@RequestBody ArrayList<IncidentesMutualidad> row,@PathVariable int tipoinforme, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		String r = "";
		if (ses.isValid()) {
			return "";

		}
		List<IncidentesMutualidad> incidente = new ArrayList<>();
		if(tipoinforme == 1){
			r = sw_IncidentesMutualidadDB.getExelDetalleregistroAccidente(row);
		}else if(tipoinforme == 2){
			r = sw_IncidentesMutualidadDB.getExelDetalleregistroAccidenteResumen(row);
		}else if(tipoinforme == 3){
			r = sw_IncidentesMutualidadDB.getExelDetalleregistroAccidenteTrabajador(row);
		}else if(tipoinforme == 6){
			
			for (IncidentesMutualidad rec : row) {
				
				incidente.addAll(sw_IncidentesMutualidadDB.getDatosDiasPerdidos(rec.getAnio(),rec.getEmpresa()));
			}
			r = sw_IncidentesMutualidadDB.getExeltasaDiasPerdidos(incidente);
		}
		else if(tipoinforme == 7){
			
			for (IncidentesMutualidad rec : row) {
				
				incidente.addAll(sw_IncidentesMutualidadDB.getDatosporanio(rec.getAnio(),rec.getEmpresa()));
			}
			r = sw_IncidentesMutualidadDB.getExeltasaAccidentabilidad(incidente);
		}else if(tipoinforme == 5){
			
			for (IncidentesMutualidad rec : row) {
				
				incidente.addAll(sw_IncidentesMutualidadDB.getDatosMoviles(
						rec.getAnio(),rec.getAnio2(),rec.getEmpresa(),rec.getMes_s(),rec.getMesf_s(),rec.getPeriodofin()));
			}
			
			
			r = sw_IncidentesMutualidadDB.getExelAnalisisPeriodosMoviles(incidente);
			
		}
		
		
		
		 System.out.println(incidente);

		

		return r;

	}
	
	@RequestMapping(value = "/work/allTrabajaInformeAccidentes/{soc},{huerto_},{zona_},"
			                                                 + "{ceco_},{estado_proceso},"
			                                                 + "{tipo_accidente},{clasificacion_accidente},"
			                                                 + "{causa_accidente}", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LoadTrabajadorSociedad> getallTrabajaInformeAcc(
			@PathVariable String soc, 
			@PathVariable String huerto_,
			@PathVariable String zona_,
			@PathVariable String ceco_,
			@PathVariable String estado_proceso,
			@PathVariable String tipo_accidente,
			@PathVariable String clasificacion_accidente,
			@PathVariable String causa_accidente,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<LoadTrabajadorSociedad> r = new ArrayList<LoadTrabajadorSociedad>();

		if (ses.isValid()) {
			return r;
		}
		r = sw_IncidentesMutualidadDB.getallTrabajaInformeAcc(soc,huerto_,zona_,ceco_,estado_proceso,tipo_accidente,clasificacion_accidente,causa_accidente);

		return r;

	}
	
	

}

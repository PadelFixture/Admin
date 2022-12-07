package lib.data.json;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import SWDB.LoadNumero_Solicitud;
import SWDB.addPreseleccionados;
import SWDB.cargarFaena;
import SWDB.impexp_trabajador;
import SWDB.loadListSolicitudes;
import SWDB.notificacionSolicitud;
import lib.classSW.AnticiposIndividuales;
import lib.classSW.CargarTipodePago;
import lib.classSW.CreateLiquidacion;
import lib.classSW.ExportarCSV;
import lib.classSW.GetDatosContratoTrabajador;
import lib.classSW.InsertContrato;
import lib.classSW.InsertHD;
import lib.classSW.ListaSociedad;
import lib.classSW.LoadCargoPreseleccion;
import lib.classSW.LoadConceptos;
import lib.classSW.LoadContratacion;
import lib.classSW.LoadFaena;
import lib.classSW.LoadTrabajadorSociedad;
import lib.classSW.NominaAnticipos;
import lib.classSW.NotificacionContrato;
import lib.classSW.OrdenDePagoPREVIRED;
import lib.classSW.PreNominaAnticipo;
import lib.classSW.PreseleccionDetalle;
import lib.classSW.PreseleccionDetalleVer;
import lib.classSW.RechazoPreseleccionado;
import lib.classSW.TipoContrato;
import lib.classSW.TipoLicencia;
import lib.classSW.TodoTablaContrato;
import lib.classSW.UpdateEstadoReclutamiento;
import lib.classSW.UpdateTrabajadorHD;
import lib.classSW.Cargo;
import lib.classSW.contrato_SW;
import lib.classSW.haberesDescuentos;
import lib.classSW.listaRechazo;
import lib.classSW.notificacionPreseleccion;
import lib.classSW.posiciones;
import lib.classSW.preseleccionados;
import lib.classSW.reclutamiento;
import lib.classSW.seleccionados;
import lib.classSW.sw_haberesDescuentos;
import lib.classSW.sw_huerto;
import lib.classSW.tablaPermisoLicencia;
import lib.classSW.trabajador;
import lib.classSW.trabajador_pre;
import lib.classSW.trabajadores_prese;
import lib.db.dteBD;
import lib.security.session;
import lib.struc.peticion;
import lib.struc.trabajadores;

//import wordCreator.DocxCreator;
import wordCreator.utils;

@Controller
public class SimpleWork {
	@RequestMapping(value = "/work/addPeticion", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertPeticion(@RequestBody peticion row, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return dteBD.insertPeticion(row);

	}// trabajadores segun rut

	@RequestMapping(value = "/WORK/LOADTRABAJADORRUT/{rut}", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ArrayList<trabajador> getTrabajador(@PathVariable String rut ,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		ArrayList<trabajador> es = new ArrayList<trabajador>();
		
		if (ses.isValid()) {
			return es;
		}
		
		es = impexp_trabajador.gettrabajador(rut);
		return es;

	}// rut de todos los trabajadores

	@RequestMapping(value = "/work/rutTrabajadores/", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<trabajador> getSECTOR(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<trabajador> r = new ArrayList<trabajador>();
		if (ses.isValid()) {
			return r;
		}
		r = impexp_trabajador.getruttrab();
		return r;
	}
	// trabajadores segun peticion

	@RequestMapping(value = "/work/preseTrabajadores/{peticion},{entero}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ArrayList<trabajadores_prese> getTrabajador(@PathVariable int peticion,
			@PathVariable int entero, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<trabajadores_prese> es = new ArrayList<trabajadores_prese>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getTrabajPrese(peticion, entero);
		return es;

	}// trabajadores supervisores

	@RequestMapping(value = "/work/loadSupervisores", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<trabajador> getSupervisor(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<trabajador> es = new ArrayList<trabajador>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getsupervisores();
		return es;

	}// todos los trabajadores

	@RequestMapping(value = "/work/alltrabajadores", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<trabajador> getalltrabaja(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<trabajador> es = new ArrayList<trabajador>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getalltrabaja();
		return es;

	}// trabajadores segun id_contrato

	@RequestMapping(value = "/work/trabajadorContrato/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<trabajador> gettrabajadorPorId(@PathVariable String id, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		ArrayList<trabajador> es = new ArrayList<trabajador>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.gettrabajadorPorId(id);
		return es;

	}// insert trabajador

	@RequestMapping(value = "/work/insertTrabajador/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertTraba(@RequestBody trabajador row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return impexp_trabajador.insertTraba(row);
	}

	@RequestMapping(value = "/work/loadNotiPreseleccion", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<notificacionPreseleccion> loadNotiPreseleccion(HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		ArrayList<notificacionPreseleccion> r = new ArrayList<notificacionPreseleccion>();

		if (ses.isValid()) {
			return r;
		}

		r = notificacionSolicitud.loadNotificacionPreseleccion();

		return r;

	}
	// detalle notificacion en preseleccion

	@RequestMapping(value = "/work/LoadNumeroSolicitud/{entero}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<PreseleccionDetalle> LoadNSolicitud(@PathVariable int entero,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<PreseleccionDetalle> r = new ArrayList<PreseleccionDetalle>();

		if (ses.isValid()) {
			return r;
		}

		r = impexp_trabajador.LoadNSolicitud(entero);

		return r;

	}

	@RequestMapping(value = "/work/PreseleccionDetalleVerLista/{entero}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)

	public @ResponseBody ArrayList<PreseleccionDetalleVer> PreseleccionDetalleVerMasLista(@PathVariable int entero,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<PreseleccionDetalleVer> r = new ArrayList<PreseleccionDetalleVer>();

		if (ses.isValid()) {
			return r;
		}

		r = impexp_trabajador.PreseleccionDetalleVerMasLista(entero);
		return r;

	}
	// detalle notificacion en preseleccion

	@RequestMapping(value = "/work/PreseleccionDetalleVer/{entero}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)

	public @ResponseBody ArrayList<PreseleccionDetalleVer> PreseleccionDetalleVerMas(@PathVariable int entero,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<PreseleccionDetalleVer> r = new ArrayList<PreseleccionDetalleVer>();

		if (ses.isValid()) {
			return r;
		}

		r = impexp_trabajador.PreseleccionDetalleVerMas(entero);
		return r;

	}

	@RequestMapping(value = "/work/addPreseleccionSimple", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String PreseleccionSimple(@RequestBody lib.classSW.PreseleccionSimple row,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		
		String r = "";
		if (ses.isValid()) {
			return "";

		}

		
		r =  impexp_trabajador.PreseleccionSimple(row);
	 

		return r;

	}
	
	@RequestMapping(value = "/work/addPreseleccionSimple2", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String PreseleccionSimple2(@RequestBody lib.classSW.PreseleccionSimple row,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		
		String r = "";
		if (ses.isValid()) {
			return "";
		}
		
		r =  impexp_trabajador.PreseleccionSimple2(row);
	 

		return r;

	}
	
	@RequestMapping(value = "/work/addPreseleccionSimple3", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String PreseleccionSimple3(@RequestBody lib.classSW.PreseleccionSimple row,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return "";

		}
		r =  impexp_trabajador.PreseleccionSimple3(row);
		return r;
	}
	
	@RequestMapping(value = "/work/addPreseleccionSimple4", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String PreseleccionSimple4(@RequestBody lib.classSW.PreseleccionSimple row,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return "";

		}
		r =  impexp_trabajador.PreseleccionSimple4(row);
		return r;
	}
	
	// actualizar si cambian la fecha de entrevista
	
	@RequestMapping(value = "/work/actualizarFechaEntrevista", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String actualizarFechaEntrevista(@RequestBody lib.classSW.PreseleccionSimple row,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return "";

		}
		r =  impexp_trabajador.actualizarFechaEntrevista(row);
		return r;
	}

	// rechazo preseleccionado
	@RequestMapping(value = "/work/RechazarPreseleccionado", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean rechazoPre(@RequestBody RechazoPreseleccionado row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}

		return impexp_trabajador.rechazoPre(row);

	}

	@RequestMapping(value = "/work/Seleccionado", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean seleccionPre(@RequestBody seleccionados row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}

		return impexp_trabajador.seleccionPre(row);

	}

	@RequestMapping(value = "/work/addPreseleccion", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean Preseleccion(@RequestBody lib.classSW.Preseleccion row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}

		return addPreseleccionados.insertPreseleccion(row);

	}

	// todos las sociedades
	@RequestMapping(value = "/work/ListaSociedad/", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<ListaSociedad> getSociedad(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<ListaSociedad> es = new ArrayList<ListaSociedad>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getSociedad();
		return es;

	}

	@RequestMapping(value = "/work/ListaSolicitud/{reclutamiento}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<lib.classSW.ListaSolicitudes> ListaSolicitudes(@PathVariable String reclutamiento,HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		ArrayList<lib.classSW.ListaSolicitudes> r = new ArrayList<lib.classSW.ListaSolicitudes>();

		if (ses.isValid()) {
			return r;
		}

		r = loadListSolicitudes.loadListaSolicitudes(reclutamiento);

		return r;

	}

	// cambiar estado peticion pagina lista preseleccion
	@RequestMapping(value = "/work/UpdateOrdenReclutamiento", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UpdateReclutamiento(@RequestBody UpdateEstadoReclutamiento row,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return impexp_trabajador.UpdateReclutamiento(row);

	}
	// cargar select cargo pantalla preseleccion

	@RequestMapping(value = "/work/LoadCargoPreseleccion/{entero}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LoadCargoPreseleccion> getCargoPreseleccion(@PathVariable int entero,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<LoadCargoPreseleccion> r = new ArrayList<LoadCargoPreseleccion>();

		if (ses.isValid()) {
			return r;
		}

		// r = dteBD.loadNotificacionPreseleccion();
		r = impexp_trabajador.getCargoPreseleccion(entero);

		return r;

	}
	
	
	// cargar select criterio preseleccion

		@RequestMapping(value = "/work/LoadCriterioPreseleccion/", method = { RequestMethod.GET,
				RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody ArrayList<LoadCargoPreseleccion> getCriteriopreseleccion(
				HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);

			ArrayList<LoadCargoPreseleccion> r = new ArrayList<LoadCargoPreseleccion>();

			if (ses.isValid()) {
				return r;
			}

			r = impexp_trabajador.getCriteriopreseleccion();

			return r;

		}

	@RequestMapping(value = "/map/LoadNumeroSolicitud/{get}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<lib.classSW.LoadNumeroSolicitud> LoadNumeroSolicitud(@PathVariable int get,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<lib.classSW.LoadNumeroSolicitud> r = new ArrayList<lib.classSW.LoadNumeroSolicitud>();

		if (ses.isValid()) {
			return r;
		}

		// r = dteBD.loadNotificacionPreseleccion();
		r = LoadNumero_Solicitud.LoadNumeroSolicitudes(get);

		return r;

	}

	@RequestMapping(value = "/work/loadContratacion", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<NotificacionContrato> ListaNotificacionContrato(HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		ArrayList<NotificacionContrato> r = new ArrayList<NotificacionContrato>();

		if (ses.isValid()) {
			return r;
		}

		r = impexp_trabajador.ListaNotificacionContrato();

		return r;

	}

	@RequestMapping(value = "/work/ListaFaena", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LoadFaena> ListaFaena(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<LoadFaena> r = new ArrayList<LoadFaena>();

		if (ses.isValid()) {
			return r;
		}

		r = cargarFaena.loadDatosFaena();

		return r;

	}

	// contrato segun trabajador
	@RequestMapping(value = "/work/contratoById/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<contrato_SW> getcontratoById(@PathVariable String id, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		ArrayList<contrato_SW> es = new ArrayList<contrato_SW>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getcontratoById(id);
		return es;

	}// update trabajadores

	@RequestMapping(value = "/work/updatePreseleccion", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updatePreseleccion(@RequestBody trabajador_pre row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return impexp_trabajador.updateTrabajadorPre(row);
	}
	

	// insert reclutamiento
	@RequestMapping(value = "/work/insertReclutamiento/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertReclu(@RequestBody ArrayList<reclutamiento> row, HttpSession httpSession)
			throws Exception {
		boolean recc = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return recc;
		}

		// rec.setUsuario(ses.getValue("usuario"));

		recc = impexp_trabajador.insertReclu(row.get(0));

		for (reclutamiento rec : row) {
			rec.setEmpresa(ses.getValue("sociedad"));
			recc = impexp_trabajador.insertReclu2(rec);
		}

		return recc;

	}

	// cargar trabajadores seleccionados a pantalla de contratacion

	@RequestMapping(value = "/work/ContratacionSelecc/{id_peticion},{cod_peticion}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LoadContratacion> LoadSeleccContratacion(@PathVariable int id_peticion,
			@PathVariable int cod_peticion, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<LoadContratacion> r = new ArrayList<LoadContratacion>();

		if (ses.isValid()) {
			return r;
		}

		r = impexp_trabajador.LoadSeleccContratacion(id_peticion, cod_peticion);

		return r;

	}

	@RequestMapping(value = "/work/ListaCargo/{sociedad}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<Cargo> getCargos(@PathVariable int sociedad,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<Cargo> es = new ArrayList<Cargo>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getCargos(sociedad);
		return es;

	}// todos las posiciones

	@RequestMapping(value = "/work/ListaPosicion/", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<posiciones> getPosiciones(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<posiciones> es = new ArrayList<posiciones>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getPosiciones();
		return es;

	}

	@RequestMapping(value = "/work/rechazo/", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<listaRechazo> getRechazos(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<listaRechazo> es = new ArrayList<listaRechazo>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getRechazos();
		return es;

	}

	@RequestMapping(value = "/work/loadPersonal/{idcargo},{idpeticion}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<trabajadores> loadPersonal(@PathVariable int idcargo,@PathVariable int idpeticion,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<trabajadores> r = new ArrayList<trabajadores>();

		if (ses.isValid()) {
			return r;
		}

		r = impexp_trabajador.loadPersonal(idcargo,idpeticion);

		Collections.sort(r, trabajadores.TNameComparator);
		
		return r;

	}
	@RequestMapping(value = "/work/loadPersonal3/{idcargo},{idpeticion},{soc},{criterio_preseleccion},{genero},{edad1},{edad2},{region_},{provincia_},{comuna_},{nreclutamiento}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<trabajadores> loadPersonal3(@PathVariable int idcargo,
															   @PathVariable int idpeticion,
															   @PathVariable int soc,
															   @PathVariable String criterio_preseleccion,
															   @PathVariable String genero,
															   @PathVariable String edad1,
															   @PathVariable String edad2,
															   @PathVariable String region_,
															   @PathVariable String provincia_,
															   @PathVariable String comuna_,
															   @PathVariable int nreclutamiento,
															   HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<trabajadores> r = new ArrayList<trabajadores>();

		if (ses.isValid()) {
			return r;
		}

		r = impexp_trabajador.loadPersonal3(idcargo,idpeticion,soc,criterio_preseleccion,genero,edad1,edad2,region_,provincia_,comuna_,nreclutamiento);

		Collections.sort(r, trabajadores.TNameComparator);
		
		return r;

	}
	
	@RequestMapping(value = "/work/loadPersonal4/{idcargo},{idpeticion},{soc},{criterio_preseleccion},{genero},{edad1},{edad2},{region_},{provincia_},{comuna_}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<trabajadores> loadPersonal4(@PathVariable int idcargo,
															   @PathVariable int idpeticion,
															   @PathVariable int soc,
															   @PathVariable String criterio_preseleccion,
															   @PathVariable String genero,
															   @PathVariable String edad1,
															   @PathVariable String edad2,
															   @PathVariable String region_,
															   @PathVariable String provincia_,
															   @PathVariable String comuna_,
															   HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<trabajadores> r = new ArrayList<trabajadores>();

		if (ses.isValid()) {
			return r;
		}

		r = impexp_trabajador.loadPersonal4(idcargo,idpeticion,soc,criterio_preseleccion,genero,edad1,edad2,region_,provincia_,comuna_);

		Collections.sort(r, trabajadores.TNameComparator);
		
		return r;

	}
	
	@RequestMapping(value = "/work/loadPersonal2/{idcargo},{idpeticion},{soc},{criterio_preseleccion},{genero},{edad1},{edad2},{region_},{provincia_},{comuna_}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<trabajadores> loadPersonal2(@PathVariable int idcargo,
															   @PathVariable int idpeticion,
															   @PathVariable int soc,
															   @PathVariable String criterio_preseleccion,
															   @PathVariable String genero,
															   @PathVariable String edad1,
															   @PathVariable String edad2,
															   @PathVariable String region_,
															   @PathVariable String provincia_,
															   @PathVariable String comuna_,
															   HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<trabajadores> r = new ArrayList<trabajadores>();

		if (ses.isValid()) {
			return r;
		}

		r = impexp_trabajador.loadPersonal2(idcargo,idpeticion,soc,criterio_preseleccion,genero,edad1,edad2,region_,provincia_,comuna_);

		Collections.sort(r, trabajadores.TNameComparator);
		
		return r;

	}

	// todos los p haberes y dsc
	@RequestMapping(value = "/work/allPHabDsc", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<haberesDescuentos> getHaberDsc(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<haberesDescuentos> es = new ArrayList<haberesDescuentos>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getHaberDsc();
		return es;

	}



	// cargar conceptos por tipo de haberes o descuentos

	@RequestMapping(value = "/work/LoadConceptos/{HD}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LoadConceptos> getConceptos(@PathVariable String HD, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		ArrayList<LoadConceptos> r = new ArrayList<LoadConceptos>();

		if (ses.isValid()) {
			return r;
		}
		r = impexp_trabajador.getConceptos(HD);

		return r;

	}

	// todos los trabajadores
	@RequestMapping(value = "/work/allTrabajadoresCodNom/{idSociedad},{huerto},{zona},{ceco},{rolPrivado},{estado_proceso}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<trabajadores> allTrabajadoresCodNom(@PathVariable String idSociedad,
			@PathVariable String huerto,@PathVariable String zona,@PathVariable String ceco,@PathVariable int rolPrivado,@PathVariable String estado_proceso, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		ArrayList<trabajadores> es = new ArrayList<trabajadores>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getallTrabajaCodNom(idSociedad,huerto,zona,ceco,rolPrivado,estado_proceso);
		return es;

	}

	// ingresar haberes y descuento a trabajadores

	@RequestMapping(value = "/work/insertHD/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertarHD(@RequestBody ArrayList<InsertHD> row, HttpSession httpSession)
			throws Exception {
		boolean recc = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return recc;
		}
		for (InsertHD rec : row) {

			recc = impexp_trabajador.insertarHD(rec);
		}

		return recc;

	}

	// todos los p haberes y dsc
	@RequestMapping(value = "/work/allSWHabDsc", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<sw_haberesDescuentos> sw_haberesDescuento(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<sw_haberesDescuentos> es = new ArrayList<sw_haberesDescuentos>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.sw_haberesDescuento();
		return es;

	}

	// update trabajadores HD
	@RequestMapping(value = "/work/updateTrabHD", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateTHD(@RequestBody UpdateTrabajadorHD row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return impexp_trabajador.updateTHD(row);
	}

	// @RequestMapping(value = "/work/insertContratacion", method =
	// RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	// public @ResponseBody trabajadores insertarContratos(@RequestBody
	// InsertContrato row,HttpSession httpSession) throws Exception {
	//
	// session ses = new session(httpSession);
	// trabajadores variable = new trabajadores();
	// if (ses.isValid()) {
	// return null;
	// }
	// variable = impexp_trabajador.insertarContrato(row);
	// System.out.println(variable);
	//
	// final Gson gson = new Gson();
	// final String representacionJSON = gson.toJson(variable);
	// System.out.println(representacionJSON);
	// DocxCreator dx = new DocxCreator();
	//
	// //dx.modifyDocument(String pathfinal, String JSON, FileInputStream
	// template)
	// return variable;
	// }

	// @RequestMapping(value = "/work/insertContratacion/{pet},{cod}", method =
	// RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)

	// @RequestMapping(value = "/work/insertContratacion", method =
	// RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	// public @ResponseBody trabajadores insertarContratos(@RequestBody
	// InsertContrato row,HttpSession httpSession) throws Exception {
	//
	// session ses = new session(httpSession);
	// trabajadores variable = new trabajadores();
	// if (ses.isValid()) {
	// return null;
	// }
	// variable = impexp_trabajador.insertarContrato(row);
	// System.out.println(variable);
	//
	// final Gson gson = new Gson();
	// final String representacionJSON = gson.toJson(variable);
	// System.out.println(representacionJSON);
	// DocxCreator dx = new DocxCreator();
	//
	// //dx.modifyDocument(String pathfinal, String JSON, FileInputStream
	// template)
	// return variable;
	// }
	//
	//
	@RequestMapping(value = "/work/insertContratacion/{pet},{cod}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody GetDatosContratoTrabajador insertarContratos(@RequestBody InsertContrato row,
			@PathVariable int pet, @PathVariable int cod, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		GetDatosContratoTrabajador variable = new GetDatosContratoTrabajador();
		if (ses.isValid()) {
			return null;
		}
		variable = impexp_trabajador.insertarContrato(row, pet, cod);

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(variable);

		ConviertePdfFromWordx obtenerContratoPdf = new ConviertePdfFromWordx();
		String urlDocGenerado = utils.obtenerCarpetaServidor() + File.separator;
		String nombreDoc = "Contrato_" + Calendar.getInstance().getTimeInMillis() + ".pdf";
		obtenerContratoPdf.convertToPDF("contrato.docx", urlDocGenerado + nombreDoc, json);

		//
		//
		// final Gson gson = new Gson();
		// final String representacionJSON = gson.toJson(variable);asd
		//
		// String f =representacionJSON.toString().replace("\"","'");
		// System.out.println(f);
		//
		//
		// DocxCreator dx = new DocxCreator();
		// FileInputStream template= new FileInputStream("C:/mm/contrato.docx");
		// dx.modifyDocument("C:/mm/", f.toString(), template);

		return variable;
	}

	@RequestMapping(value = "/work/selectSaldoNotificacion/{codigo_P},{id_P}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ArrayList<lib.classSW.ListaSolicitudes> saldoNotificacion(@PathVariable int codigo_P,
			@PathVariable int id_P, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<lib.classSW.ListaSolicitudes> es = new ArrayList<lib.classSW.ListaSolicitudes>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.saldoNotificacion(codigo_P, id_P);
		return es;

	}

	// cambiar estado peticion pagina lista preseleccion
	@RequestMapping(value = "/work/updateListaPeticion/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateListadoP(@RequestBody NotificacionContrato row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return impexp_trabajador.updateListadoP(row);

	}

	// saldo por orden de reclutamiento
	@RequestMapping(value = "/work/selectSaldoOrden/{codigo_P},{id_P}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ArrayList<lib.classSW.ListaSolicitudes> saldoxOrden(@PathVariable int codigo_P,
			@PathVariable int id_P, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<lib.classSW.ListaSolicitudes> es = new ArrayList<lib.classSW.ListaSolicitudes>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.saldoxOrden(codigo_P, id_P);
		return es;

	}

	// cambiar estado orden cuando se completa el total de la cantidad de
	// personas solicitadas
	@RequestMapping(value = "/work/updateListaOrden/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateListadoO(@RequestBody NotificacionContrato row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return impexp_trabajador.updateListadoO(row);

	}

	// devolver seleccionados a preseleccion

	@RequestMapping(value = "/work/devolverSeleccionados/{codigo_P},{id_P}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ArrayList<preseleccionados> devolverSelec(@PathVariable int codigo_P, @PathVariable int id_P,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<lib.classSW.preseleccionados> es = new ArrayList<preseleccionados>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.devolverSelec(codigo_P, id_P);
		return es;

	}

	@RequestMapping(value = "/work/updateTranajadorNoSelec/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UTraNoSelec(@RequestBody ArrayList<preseleccionados> row, HttpSession httpSession)
			throws Exception {
		boolean recc = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return recc;
		}
		for (preseleccionados rec : row) {

			recc = impexp_trabajador.UTraNoSelec(rec);
		}

		return recc;

	}

	// traspazo trabajador
	@RequestMapping(value = "/work/traspazar", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean traspazarTr(@RequestBody RechazoPreseleccionado row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}

		return impexp_trabajador.traspazarTr(row);

	}

	// ------Cargar todos los trabajadores asociados a una empresa
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "/work/LoadTrabajadorXSociedad/{idSociedad},{huerto},{zona},{ceco},{iduser},{estadoproceso},{rolprivado}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LoadTrabajadorSociedad> getConceptos(
			@PathVariable String idSociedad,
			@PathVariable String huerto,
			@PathVariable String zona,
			@PathVariable String ceco,
			@PathVariable int iduser,
			@PathVariable String estadoproceso,
			@PathVariable int rolprivado,
			HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		ArrayList<LoadTrabajadorSociedad> r = new ArrayList<LoadTrabajadorSociedad>();

		if (ses.isValid()) {
			return r;
		}
		r = impexp_trabajador.getSociedadTrab(idSociedad,huerto,zona,ceco,iduser,estadoproceso,rolprivado);

		return r;

	}
	
	@RequestMapping(value = "/work/LoadTrabajadorXSociedadListadoPermiso/{idSociedad},{huerto},{zona},{ceco},{estado_proceso},{rolPrivado}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LoadTrabajadorSociedad> getSociedadTrabListadoP(
			@PathVariable String idSociedad,
			@PathVariable String huerto,
			@PathVariable String zona,
			@PathVariable String ceco,
			@PathVariable String estado_proceso,
			@PathVariable int rolPrivado,
			HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		ArrayList<LoadTrabajadorSociedad> r = new ArrayList<LoadTrabajadorSociedad>();

		if (ses.isValid()) {
			return r;
		}
		r = impexp_trabajador.getSociedadTrabListadoP(idSociedad,huerto,zona,ceco,estado_proceso,rolPrivado);

		return r;

	}

	// -------------------------------------END---------------------------------------------------------------------------------------------------------
	// ------detalle trabajador para pantalla permiso y
	// licencia---------------------------------------------------------------------------------------
	@RequestMapping(value = "/work/detalleTrabajadorPermisoLicencia/{codigo},{id}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LoadTrabajadorSociedad> getConceptos(@PathVariable int codigo, @PathVariable int id,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<LoadTrabajadorSociedad> r = new ArrayList<LoadTrabajadorSociedad>();

		if (ses.isValid()) {
			return r;
		}
		r = impexp_trabajador.getTrabajadorPermisolicencia(codigo, id);

		return r;

	}

	// -------------------------------------END---------------------------------------------------------------------------------------------------------
	// ------cargar tabla pantalla permiso
	// licencia---------------------------------------------------------------------------------------
	@RequestMapping(value = "/work/LodtablaPermisoLicencia/{codigo},{idAccion},{idEmpresa},{huerto},{zona},{ceco},{periodo}", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<tablaPermisoLicencia> getTablaPL(@PathVariable String codigo,
			@PathVariable int idAccion, @PathVariable int idEmpresa,
			@PathVariable String huerto,@PathVariable String zona,@PathVariable String ceco,@PathVariable int periodo,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<tablaPermisoLicencia> r = new ArrayList<tablaPermisoLicencia>();

		if (ses.isValid()) {
			return r;
		}
		r = impexp_trabajador.getTablaPL(codigo, idAccion, idEmpresa,huerto,zona,ceco,periodo);

		return r;

	}

	// -------------------------------------END---------------------------------------------------------------------------------------------------------
	// ------------------- insertar permiso trabajador
	@RequestMapping(value = "/work/insertTrabajadorPermisoSinGoceDeSueldo/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertarPermiso(@RequestBody ArrayList<tablaPermisoLicencia> row,
			HttpSession httpSession) throws Exception {
		boolean recc = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return recc;
		}
		for (tablaPermisoLicencia rec : row) {

			recc = impexp_trabajador.insertarPermiso(rec);
		}

		return recc;

	}

	@RequestMapping(value = "/work/insertTrabajadorPermiso/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertarPermisoConGoce(@RequestBody ArrayList<tablaPermisoLicencia> row,
			HttpSession httpSession) throws Exception {
		boolean recc = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return recc;
		}
		for (tablaPermisoLicencia rec : row) {

			recc = impexp_trabajador.insertarPermisoConGoce(rec);
		}

		return recc;

	}

	// ------------------------Lista Tipo
	// Licencia--------------------------------------------
	@RequestMapping(value = "/work/ListaTipoLicencia/", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<TipoLicencia> getTipoLicencia(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<TipoLicencia> es = new ArrayList<TipoLicencia>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getTipoLicencia();
		return es;

	}
	// ------------------------Lista Subtipo
	// Licencia-------------------------------

	@RequestMapping(value = "/work/ListaSubtipoLicencia/", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<TipoLicencia> getSubtipoLicencia(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<TipoLicencia> es = new ArrayList<TipoLicencia>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getSubtipoLicencia();
		return es;

	}

	// ------------------- insertar Licencia trabajador
	@RequestMapping(value = "/work/insertTrabajadorLicencia/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertarLicencia(@RequestBody ArrayList<tablaPermisoLicencia> row,
			HttpSession httpSession) throws Exception {
		boolean recc = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return recc;
		}
		for (tablaPermisoLicencia rec : row) {

			recc = impexp_trabajador.insertarLicencia(rec);
		}

		return recc;

	}

	// --------------------eliminar haber o descuento
	// -----------------------------------
	@RequestMapping(value = "/work/EliminarHaberDescuento/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean eliminarHD(@PathVariable int id, HttpSession httpSession) throws Exception {
		boolean recc = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return recc;
		}

		recc = impexp_trabajador.eliminarHD(id);

		return recc;

	}

	// -----------------------Load tipo de
	// pago-------------------------------------------------
	@RequestMapping(value = "/work/ListaTipoPago/", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<CargarTipodePago> getTipoPago(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<CargarTipodePago> es = new ArrayList<CargarTipodePago>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getTipoPago();
		return es;

	}

	// ----------------LOAD Lista
	// División------------------------------------------------
	@RequestMapping(value = "/work/ListaDivision/", method = { RequestMethod.GET, RequestMethod.POST })
	// ocupa la misma clase ya que se recupera los mismos datos
	public @ResponseBody ArrayList<CargarTipodePago> getTipoDivision(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<CargarTipodePago> es = new ArrayList<CargarTipodePago>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getTipoDivision();
		return es;

	}

	// ----------------LOAD Lista
	// Sub-División------------------------------------------------
	@RequestMapping(value = "/work/ListaSubDivision/", method = { RequestMethod.GET, RequestMethod.POST })
	// ocupa la misma clase ya que se recupera los mismos datos
	public @ResponseBody ArrayList<CargarTipodePago> getTipoSubDivision(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<CargarTipodePago> es = new ArrayList<CargarTipodePago>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getTipoSubDivision();
		return es;

	}

	// ----------------LOAD Lista
	// Grupo------------------------------------------------
	@RequestMapping(value = "/work/ListaGrupo/", method = { RequestMethod.GET, RequestMethod.POST })
	// ocupa la misma clase ya que se recupera los mismos datos
	public @ResponseBody ArrayList<CargarTipodePago> getListaGrupo(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<CargarTipodePago> es = new ArrayList<CargarTipodePago>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getListaGrupo();
		return es;

	}

	// ----------------LOAD Lista
	// Grupo------------------------------------------------
	@RequestMapping(value = "/work/ListaSubGrupo/", method = { RequestMethod.GET, RequestMethod.POST })
	// ocupa la misma clase ya que se recupera los mismos datos
	public @ResponseBody ArrayList<CargarTipodePago> getListaSubGrupo(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<CargarTipodePago> es = new ArrayList<CargarTipodePago>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getListaSubGrupo();
		return es;

	}

	// ----------------LOAD Lista
	// Frecuencia------------------------------------------------
	@RequestMapping(value = "/work/ListaFrecuencia/", method = { RequestMethod.GET, RequestMethod.POST })
	// ocupa la misma clase ya que se recupera los mismos datos
	public @ResponseBody ArrayList<CargarTipodePago> getListaFrecuencia(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<CargarTipodePago> es = new ArrayList<CargarTipodePago>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getListaFrecuencia();
		return es;

	}

	// ----------------LOAD Lista tipo de reposo
	// ------------------------------------------------
	@RequestMapping(value = "/work/ListaTipoDeReposo/", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<CargarTipodePago> getListaTipoReposo(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<CargarTipodePago> es = new ArrayList<CargarTipodePago>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getListaTipoReposo();
		return es;

	}

	// ----------------LOAD Lista tipo de reposo Parcial
	// ------------------------------------------------
	@RequestMapping(value = "/work/ListaTipoDeParcial/", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<CargarTipodePago> getListaTipoReposoParcial(HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		ArrayList<CargarTipodePago> es = new ArrayList<CargarTipodePago>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getListaTipoReposoParcial();
		return es;

	}

	// ----------------LOAD Lista Accidente Trabajo
	// ------------------------------------------------
	@RequestMapping(value = "/work/ListaAccidenteTrabajo/", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<CargarTipodePago> getListaAccidenteTrabajo(HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		ArrayList<CargarTipodePago> es = new ArrayList<CargarTipodePago>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getListaAccidenteTrabajo();
		return es;

	}

	// ----------------LOAD Maternal
	// ------------------------------------------------
	@RequestMapping(value = "/work/ListaMaternal/", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<CargarTipodePago> getListaMaternal(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<CargarTipodePago> es = new ArrayList<CargarTipodePago>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getListaMaternal();
		return es;
	}

	// ------------------- insertar Licencia Mutualidad
	@RequestMapping(value = "/work/insertLicenciaMutualidad/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertarLicenciaMutualidad(@RequestBody ArrayList<tablaPermisoLicencia> row,
			HttpSession httpSession) throws Exception {
		boolean recc = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return recc;
		}
		for (tablaPermisoLicencia rec : row) {

			recc = impexp_trabajador.insertarLicenciaMutualidad(rec);
		}

		return recc;

	}

	// -----------------------load contrato
	// trabajador-------------------------------------------------------------
	@RequestMapping(value = "/work/LoadSelectIdContrato/{cod}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<TodoTablaContrato> getIdContrato(@PathVariable int cod, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		ArrayList<TodoTablaContrato> r = new ArrayList<TodoTablaContrato>();

		if (ses.isValid()) {
			return r;
		}
		r = impexp_trabajador.getIdContrato(cod);

		return r;

	}

	// ------------------------------------------------------------------------------------
	@RequestMapping(value = "/work/LoadSelectIdContratoPantallaListadoHD/{cod}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<TodoTablaContrato> getIdContratoPLHD(@PathVariable int cod, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		ArrayList<TodoTablaContrato> r = new ArrayList<TodoTablaContrato>();

		if (ses.isValid()) {
			return r;
		}
		r = impexp_trabajador.getIdContratoPLHD(cod);

		return r;

	}
	
	@RequestMapping(value = "/work/allHDperiodoAll/{codtrabajador}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<sw_haberesDescuentos> getswHDPeriodoAll(@PathVariable String codtrabajador,
			 HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<sw_haberesDescuentos> es = new ArrayList<sw_haberesDescuentos>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getswHDPeriodoAll(codtrabajador);
		return es;

	}


	@RequestMapping(value = "/work/allHDperiodo/{periodo},{soci},{idcontrato},{codtrabajador},{huerto},{zona},{ceco},{hdimput},{concepto},{estado_proceso}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<sw_haberesDescuentos> getswHDPeriodo(@PathVariable String periodo,
			@PathVariable String soci,@PathVariable String idcontrato,@PathVariable String codtrabajador,
			@PathVariable String huerto,@PathVariable String zona,@PathVariable String ceco,
			@PathVariable String hdimput,@PathVariable String concepto,@PathVariable String estado_proceso,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<sw_haberesDescuentos> es = new ArrayList<sw_haberesDescuentos>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getswHDPeriodo(periodo, soci, idcontrato, codtrabajador, huerto,zona,ceco,hdimput,concepto,estado_proceso);
		return es;

	}
	// -----------------------insert Anticipos
	// Individuales-------------------------------------------------------------

	@RequestMapping(value = "/work/insertAnticiposIndividuales/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertarAnticiposInd(@RequestBody ArrayList<AnticiposIndividuales> row,
			HttpSession httpSession) throws Exception {
		boolean recc = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return recc;
		}
		for (AnticiposIndividuales rec : row) {

			recc = impexp_trabajador.insertarAnticiposInd(rec);
		}

		return recc;

	}

	/// -----------------------cargar popup update pantalla asignacion de
	/// antipos-------------------------------
	@RequestMapping(value = "/work/cargarPopupUpdateasignacionAnticiposIndividuales/{id}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ArrayList<AnticiposIndividuales> getUpdateAnticiposIndividuales(@PathVariable int id,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<AnticiposIndividuales> es = new ArrayList<AnticiposIndividuales>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getUpdateAnticiposIndividuales(id);
		return es;
	}

	// ------pantalla anticipos simple cargar por codigo trabajador
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "/work/cargarXCodTrabajador/{cod}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<AnticiposIndividuales> getcargarXCodTrabajador(@PathVariable int cod,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<AnticiposIndividuales> es = new ArrayList<AnticiposIndividuales>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getcargarXCodTrabajador(cod);
		return es;
	}

	// -------------------Eliminar anticipo
	// simple--------------------------------------------------------------
	@RequestMapping(value = "/work/EliminarAnticipoSimple/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean DeleteAnticipoSimple(@RequestBody AnticiposIndividuales row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}

		return impexp_trabajador.DeleteAnticipoSimple(row);

	}

	// ------Cargar todos los trabajadores asociados a una empresa y si tienen
	// anticipos
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "/work/LoadTrabajadorXSociedadAnticiposSimple/{id},{div},{subdiv},{gru},{estado_proceso},{rolPrivado}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LoadTrabajadorSociedad> getSociedaTrabAS(
			@PathVariable String id,
			@PathVariable String div, 
			@PathVariable String subdiv, 
			@PathVariable String gru, 
			@PathVariable String estado_proceso,
			@PathVariable int rolPrivado,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<LoadTrabajadorSociedad> r = new ArrayList<LoadTrabajadorSociedad>();

		if (ses.isValid()) {
			return r;
		}
		r = impexp_trabajador.getSociedaTrabAS(id,div,subdiv, gru,estado_proceso,rolPrivado);

		return r;

	}

	// ------Cargar todos los trabajadores asociados a una empresa y si tienen
	// anticipos
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "/work/LoadTrabajadorXSociedadAnticiposSimple2/{empr},{huerto},{subdiv},{gru},{tipo_contrato},{fecha_pago},{estado_proceso},{rolPrivado}", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LoadTrabajadorSociedad> getSociedaTrabAS2(@PathVariable String empr,
			@PathVariable String huerto, 
			@PathVariable String subdiv,
			@PathVariable String gru,
			@PathVariable String tipo_contrato, 
			@PathVariable String fecha_pago,
			@PathVariable String estado_proceso, 
			@PathVariable int rolPrivado, 
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<LoadTrabajadorSociedad> r = new ArrayList<LoadTrabajadorSociedad>();

		if (ses.isValid()) {
			return r;
		}
		r = impexp_trabajador.getSociedaTrabAS2(empr, huerto, subdiv, gru, tipo_contrato,fecha_pago,estado_proceso,rolPrivado);

		return r;

	}

	// -----------cargar por codigo trabajador pantalla asignacion anticipos
	// Simple-------------------------------------------------------------------------
	@RequestMapping(value = "/work/cargarPorCodTrabajadorAsignacionSimple/{p}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ArrayList<AnticiposIndividuales> getCodTrabAsignacionSimple(@PathVariable int p,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<AnticiposIndividuales> es = new ArrayList<AnticiposIndividuales>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getCodTrabAsignacionSimple(p);
		return es;
	}

	// -----------cargar por fecha pantalla asignacion anticipos
	// Simple-------------------------------------------------------------------------
	@RequestMapping(value = "/work/cargarPorFechaAsignacionSimple/{p}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ArrayList<AnticiposIndividuales> getFechaAsignacionSimple(@PathVariable String p,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<AnticiposIndividuales> es = new ArrayList<AnticiposIndividuales>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getFechaAsignacionSimple(p);
		return es;
	}

	// -------------------update anticipo
	// simple--------------------------------------------------------------
	@RequestMapping(value = "/work/UpdateAnticiposIndividuales/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UpdateAnticipoSimple(@RequestBody AnticiposIndividuales row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}

		return impexp_trabajador.UpdateAnticipoSimple(row);

	}

	// -----------------------conseguir codigo trabajador x
	// rut-------------------------------------------------------------
	@RequestMapping(value = "/work/getcodtrabajador/{rut:.+}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<TodoTablaContrato> getcodxrut(@PathVariable String rut, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		ArrayList<TodoTablaContrato> r = new ArrayList<TodoTablaContrato>();

		if (ses.isValid()) {
			return r;
		}
		r = impexp_trabajador.getcodxrut(rut);

		return r;

	}

	// -----------------------crear
	// liquidacion-------------------------------------------------------------
	@RequestMapping(value = "/work/createLiquidacion/{cod},{idcontrato},{periodo}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<CreateLiquidacion> getCreateLiquidacion(@PathVariable int cod,
			@PathVariable int idcontrato, @PathVariable int periodo, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<CreateLiquidacion> r = new ArrayList<CreateLiquidacion>();

		if (ses.isValid()) {
			return r;
		}
		r = impexp_trabajador.getCreateLiquidacion(cod, idcontrato, periodo);

		return r;

	}

	@RequestMapping(value = "/work/getContrato", method = RequestMethod.GET)
	public @ResponseBody String getAutorizacion(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		try {
			String idruta = request.getParameter("ruta");

			System.out.println("ruta: {}" + idruta);

			tablaPermisoLicencia var = impexp_trabajador.getRutaTablapermisoLicencia(idruta);

			String nombreArchivo = var.getRuta_archivo();

			String split[] = nombreArchivo.split("/");
			String nombreArchi = split[4];

			File file = new File(var.getRuta_archivo());

			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);
			response.addHeader("Content-disposition", "attachment; filename= " + nombreArchi + "");
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream out = response.getOutputStream();
			out.write(bytes);
			out.flush();
			out.close();

			return "1";

		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@RequestMapping(value = "/work/NombreTrabajadorMasIdContrato/{codigo}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<sw_haberesDescuentos> getswHDNombreTrabajador(@PathVariable int codigo,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<sw_haberesDescuentos> es = new ArrayList<sw_haberesDescuentos>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getswHDNombreTrabajador(codigo);
		return es;

	}
	
	@RequestMapping(value = "/work/NombreTrabajador/{codigo}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<sw_haberesDescuentos> getswHDNombreTrabajador2(@PathVariable int codigo,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<sw_haberesDescuentos> es = new ArrayList<sw_haberesDescuentos>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getswHDNombreTrabajador2(codigo);
		return es;

	}

	@RequestMapping(value = "/work/generaContratoTrabajador", method = RequestMethod.GET)
	public @ResponseBody String generaContratoTrabajador(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		try {
			String idTrabajador = request.getParameter("id");
			System.out.println("idTrabajador: " + idTrabajador);

			GetDatosContratoTrabajador variable = new GetDatosContratoTrabajador();
			variable = impexp_trabajador.obtenerDatosTrabajador(idTrabajador);

			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(variable);

			ConviertePdfFromWordx obtenerContratoPdf = new ConviertePdfFromWordx();
			String urlDocGenerado = utils.obtenerCarpetaServidor() + File.separator;
			String nombreDoc = "Contrato_" + idTrabajador + ".pdf";
			obtenerContratoPdf.convertToPDF("contrato.docx", urlDocGenerado + nombreDoc, json);

			File file = new File(urlDocGenerado + nombreDoc);
			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);
			response.addHeader("Content-disposition", "attachment; filename= " + nombreDoc + "");
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream out = response.getOutputStream();
			out.write(bytes);
			out.flush();
			out.close();

			if (file.exists()) {
				file.deleteOnExit();
			}

			return "1";

		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	@RequestMapping(value = "/work/allTrabajadoresCodNomHD/{cod}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<trabajadores> getallTrabajaCodNomHD(@PathVariable int cod, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		ArrayList<trabajadores> es = new ArrayList<trabajadores>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getallTrabajaCodNomHD(cod);
		return es;

	}
 
	// GET TIPO moneda para pantalla de permiso y licencia
	@RequestMapping(value = "/work/TipoMonedaHD/", method = { RequestMethod.GET, RequestMethod.POST })
	// ocupa la misma clase ya que se recupera los mismos datos
	public @ResponseBody ArrayList<CargarTipodePago> getTipoMonedaHD(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<CargarTipodePago> es = new ArrayList<CargarTipodePago>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.TipoMonedaHD();
		return es;

	}
	
	@RequestMapping(value = "/work/EliminarPermisoyLicencia/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean eliminarPL(@PathVariable int id, HttpSession httpSession) throws Exception {
		boolean recc = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return recc;
		}

		recc = impexp_trabajador.eliminarPL(id);

		return recc;

	}
	
/////////////DESCARGA ARCHIVO PRE NOMINA//////////////////////////////////////
@RequestMapping(value = "/work/getPreNomina", method = RequestMethod.GET)
public @ResponseBody String getAutorizacion2(HttpServletRequest request, HttpServletResponse response,
HttpSession session) {
try {
String idruta = request.getParameter("id");
int idrutaInt = Integer.parseInt(idruta);

System.out.println("ruta: {}" + idruta);

PreNominaAnticipo var = SWDB.sw_AsignacionAnticiposDB.getRutaArchivoPreNomina(idrutaInt);

String nombreArchivo = var.getRuta();;


String split[] = nombreArchivo.split("/");
String nombreArchi = split[4];

File file = new File(var.getRuta());

FileInputStream fileInputStreamReader = new FileInputStream(file);
byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);
response.addHeader("Content-disposition", "attachment; filename= " + nombreArchi + "");
response.setContentType("application/pdf");
response.setContentLength(bytes.length);
ServletOutputStream out = response.getOutputStream();
out.write(bytes);
out.flush();
out.close();

return "1";

} catch (Exception e) {
e.printStackTrace();
return "0";
}
}

// División------------------------------------------------
@RequestMapping(value = "/work/ListaHuero/", method = { RequestMethod.GET, RequestMethod.POST })
// ocupa la misma clase ya que se recupera los mismos datos
public @ResponseBody ArrayList<sw_huerto> getListHuerto(HttpSession httpSession) throws Exception {

	session ses = new session(httpSession);
	ArrayList<sw_huerto> es = new ArrayList<sw_huerto>();

	if (ses.isValid()) {
		return es;
	}

	es = impexp_trabajador.getHuertoLista();
	return es;

}

//------Cargar todos los trabajadores asociados a una empresa y si tienen
	// anticipos
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "/work/LoadTrabajadorXSociedadAnticipoImprimir/{id},{rolPrivado}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LoadTrabajadorSociedad> getTrabajadoresAnticipoImprimir(
			@PathVariable int id,
			@PathVariable int rolPrivado,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<LoadTrabajadorSociedad> r = new ArrayList<LoadTrabajadorSociedad>();

		if (ses.isValid()) {
			return r;
		}
		r = impexp_trabajador.getTrabajadoresAnticipoImprimir(id,rolPrivado);

		return r;

	}
	
	// LISTA DE PERMISOS CONVENCIONALES 
	@RequestMapping(value = "/work/ListaPermisosConvencionales/", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<TipoLicencia> getPermisosConvencionales(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<TipoLicencia> es = new ArrayList<TipoLicencia>();

		if (ses.isValid()) {
			return es;
		}

		es = impexp_trabajador.getPermisosConvencionales();
		return es;

	}
	
	// LISTA DE PERMISOS LEGALES 
		@RequestMapping(value = "/work/ListaPermisosLegales/", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody ArrayList<TipoLicencia> getPermisoslegales(HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<TipoLicencia> es = new ArrayList<TipoLicencia>();

			if (ses.isValid()) {
				return es;
			}

			es = impexp_trabajador.getPermisoslegales();
			return es;

		}
		
		@RequestMapping(value = "/work/BuscarHuertoTrabajador&id/{cod}", method = { RequestMethod.GET,
				RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody ArrayList<TodoTablaContrato> getHuertoTrab(@PathVariable int cod, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			ArrayList<TodoTablaContrato> r = new ArrayList<TodoTablaContrato>();

			if (ses.isValid()) {
				return r;
			}
			r = impexp_trabajador.getHuertoTrab(cod);

			return r;

		}
		
		@RequestMapping(value = "/work/insertTrabajadorPermisoAgro/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean insertarPermisoAgro(@RequestBody ArrayList<tablaPermisoLicencia> row,
				HttpSession httpSession) throws Exception {
			
			
			session ses = new session(httpSession);

			boolean r = false;
			if (ses.isValid()) {
				return r;

			}

			r = impexp_trabajador.insertarPermiso(row.get(0));

			for (tablaPermisoLicencia rec : row) {
				r = impexp_trabajador.insertarPermisoRendimientoDiario(rec);
			}
			return r;

		}
		
		@RequestMapping(value = "/work/cerraryactualizarHD", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean closeAndUpdateHD(@RequestBody UpdateTrabajadorHD row, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			return impexp_trabajador.closeAndUpdateHD(row);
		}
		
		
		@RequestMapping(value = "/work/allRetenciones/{periodo},{soci},{hdimput},{concepto},{rolPrivado}", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody ArrayList<sw_haberesDescuentos> getswHDRetenciones(@PathVariable String periodo,
				@PathVariable String soci,
				@PathVariable String hdimput,
				@PathVariable String concepto,
				@PathVariable int rolPrivado,
				HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<sw_haberesDescuentos> es = new ArrayList<sw_haberesDescuentos>();

			if (ses.isValid()) {
				return es;
			}

			es = impexp_trabajador.getswHDRetenciones(periodo, soci,hdimput,concepto,rolPrivado);
			return es;

		}
		
		@RequestMapping(value = "/work/GenerarExecelhaberesdescuento/", method = { RequestMethod.PUT,
				RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody String generarExcelPreNomina(@RequestBody ArrayList<sw_haberesDescuentos> row, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			String r = "";
			if (ses.isValid()) {
				return "";

			}

			
			r = impexp_trabajador.GenerarExcelHaberesdescuento(row);
		 

			return r;
		}
		
		@RequestMapping(value = "/work/LodtablaPermisoLicencia1/{codigo},{idAccion},{idEmpresa},{huerto},{zona},{ceco},{periodo},{estado_proceso}", method = {
				RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody ArrayList<tablaPermisoLicencia> getTablaPL1(@PathVariable String codigo,
				@PathVariable int idAccion, @PathVariable int idEmpresa,
				@PathVariable String huerto,@PathVariable String zona,@PathVariable String ceco,@PathVariable int periodo,@PathVariable String estado_proceso,HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);

			ArrayList<tablaPermisoLicencia> r = new ArrayList<tablaPermisoLicencia>();

			if (ses.isValid()) {
				return r;
			}
			r = impexp_trabajador.getTablaPL1(codigo, idAccion, idEmpresa,huerto,zona,ceco,periodo,estado_proceso);

			return r;

		}
		
		@RequestMapping(value = "/work/allTrabajadoresCodNomHD2/{cod},{estado_proceso},{rolPrivado}", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody ArrayList<trabajadores> getallTrabajaCodNomHD2(
				@PathVariable int cod,
				@PathVariable String estado_proceso,
				@PathVariable int rolPrivado,
				HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);
			ArrayList<trabajadores> es = new ArrayList<trabajadores>();

			if (ses.isValid()) {
				return es;
			}

			es = impexp_trabajador.getallTrabajaCodNomHD2(cod,estado_proceso,rolPrivado);
			return es;

		}
		
		@RequestMapping(value = "/work/ListaTipoTrabajadorReclutamiento/", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody ArrayList<TipoContrato> getTipoTrabajadorReclutamiento(HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<TipoContrato> es = new ArrayList<TipoContrato>();

			if (ses.isValid()) {
				return es;
			}

			es = impexp_trabajador.getTipoTrabajadorReclutamiento();
			return es;

		}
		

				@RequestMapping(value = "/work/LoadListadoreclutamientoContratacion/", method = { RequestMethod.GET,
						RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
				public @ResponseBody ArrayList<LoadCargoPreseleccion> getListadoReclu(
						HttpSession httpSession) throws Exception {

					session ses = new session(httpSession);

					ArrayList<LoadCargoPreseleccion> r = new ArrayList<LoadCargoPreseleccion>();

					if (ses.isValid()) {
						return r;
					}

					r = impexp_trabajador.getListadoReclu();

					return r;

				}
				
				@RequestMapping(value = "/work/LoadCargoPreseleccion2/{entero}", method = { RequestMethod.GET,
						RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
				public @ResponseBody ArrayList<LoadCargoPreseleccion> getCargoPreseleccion2(@PathVariable int entero,
						HttpSession httpSession) throws Exception {

					session ses = new session(httpSession);

					ArrayList<LoadCargoPreseleccion> r = new ArrayList<LoadCargoPreseleccion>();

					if (ses.isValid()) {
						return r;
					}

					// r = dteBD.loadNotificacionPreseleccion();
					r = impexp_trabajador.getCargoPreseleccion2(entero);

					return r;

				}
				
				@RequestMapping(value = "/work/LoadListadoreclutamientoContratacion2/{soc}", method = { RequestMethod.GET,
						RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
				public @ResponseBody ArrayList<LoadCargoPreseleccion> getListadoReclu2(@PathVariable int soc,
						HttpSession httpSession) throws Exception {

					session ses = new session(httpSession);

					ArrayList<LoadCargoPreseleccion> r = new ArrayList<LoadCargoPreseleccion>();

					if (ses.isValid()) {
						return r;
					}

					r = impexp_trabajador.getListadoReclu2(soc);

					return r;

				}
				
				// rechazo preseleccionado
				@RequestMapping(value = "/work/DescartarConfirmado/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
				public @ResponseBody boolean descartadoConfi(@RequestBody RechazoPreseleccionado row, HttpSession httpSession)
						throws Exception {

					session ses = new session(httpSession);

					if (ses.isValid()) {
						return false;
					}

					return impexp_trabajador.descartadoConfi(row);

				}
				
				
				
				@RequestMapping(value = "/work/nuevoprevired/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
				public @ResponseBody String eviarDocumetoSapPREVIRED(@RequestBody OrdenDePagoPREVIRED row,
						HttpSession httpSession) throws Exception {

					session ses = new session(httpSession);

					if (ses.isValid()) {
						return "";
					}
					return impexp_trabajador.eviarDocumetoSapPREVIRED(row);

				}

}

package lib.data.json;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.ClassSASW.parametros;
import lib.SADB.CALIFICACION_ESTADO;
import lib.SADB.CALIFICACION_RENIDIMIENTO;
import lib.SADB.CONTROL_APLICACION;
import lib.SADB.Calibre;
import lib.SADB.Campo_ESPECIE;
import lib.SADB.Categoria;
import lib.SADB.DotacionDiaria;
import lib.SADB.ESTADO_FENOLOGICO;
import lib.SADB.FACTOR_DECISION;
import lib.SADB.FAENA;
import lib.SADB.FORMA_APLICACION;
import lib.SADB.labor;
import lib.SADB.material;
import lib.SADB.cuartel;
import lib.SADB.MAPA;
import lib.SADB.NOTIFICACIONES;
import lib.SADB.ORDEN_APLICA;
import lib.SADB.PROGRAMA_FITOSANITARIO;
import lib.SADB.RECORRIDO;
import lib.SADB.RENDIMIENTO;
import lib.SADB.bloqueo_labor;
import lib.SADB.cuadrilla;
import lib.SADB.detalleCuartelDB;
import lib.SADB.INCIDENCIAS;
import lib.SADB.INGRESO_RIEGO;
import lib.SADB.MAESTRO_MOTIVOINGRESO;
import lib.SADB.MANTENEDOR_GENERICO;
import lib.SADB.MANTENEDOR_SA;
import lib.classSA.BLOQUEO_LABOR;
import lib.classSA.CALIBRE;
import lib.classSA.CAMPO;
import lib.classSA.CATEGORIA;
import lib.classSA.CECO;
import lib.classSA.CONFIRMACION_APLICACION;
import lib.classSA.CUARTEL;
import lib.classSA.ESPECIE;
import lib.classSA.ESTADO;
import lib.classSA.FACTOR;
import lib.classSA.FILTRO_PF;
import lib.classSA.FORMA_APLICA;
import lib.classSA.INGRESORIEGO;
import lib.classSA.incidencia;
import lib.classSA.LABOR;
import lib.classSA.MAESTRO_MOTIVO_INGRESO;
import lib.classSA.MANTENEDOR_GEN;
import lib.classSA.MATERIAL;
import lib.classSA.MATERIAL_PF;
import lib.classSA.Mantenedor_SA;
import lib.classSA.CUARTEL_PF;
import lib.classSA.Campo_Especie;
import lib.classSA.Campo_Variedad;
import lib.classSA.DOTACION_DIARIA;
import lib.classSA.ORDEN_APLICACION;
import lib.classSA.PERFIL;
import lib.classSA.PROGRA_FITOSANITARIO;
import lib.classSA.RENDIMIENTO_DIARIO;
import lib.classSA.VARIEDAD;
import lib.classSA.bloqueo_periodo;
import lib.classSA.calificacion_campo;
import lib.classSA.CONTROL_AP;
import lib.classSA.detalleCuartel;
import lib.classSA.estado_fenologico;
import lib.classSA.estado_rendimiento;
import lib.classSA.faena;
import lib.classSA.filtro_notif;
import lib.classSA.notificacion;
import lib.classSA.programa_aplicacion;
import lib.classSA.recorrido;
import lib.classSW.EQUIPO;
import lib.db.ConnectionDB;
import lib.db.simpleagroDB;
import lib.db.SASW.parametrosDB;
import lib.security.session;
import lib.sesionSA.SESION;
import lib.classSA.SECTOR;
import lib.classSA.SESIONVAR;
import lib.classSA.TEMPORADA;

@Controller
public class SimpleAgro {

	
	
	@RequestMapping(value = "/AGRO/GETMantenedor_SA/{categoria}", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody ArrayList<Mantenedor_SA> getParametros(@PathVariable String categoria, HttpSession httpSession) throws Exception{
		session ses = new session(httpSession);
		ArrayList<Mantenedor_SA> pm = new ArrayList<Mantenedor_SA>();
		if(ses.isValid()){
			return pm;
		}
		pm = MANTENEDOR_SA.GETMantenedor_SA(categoria);
		return pm;
	}
	
	@RequestMapping(value = "/AGRO/Get_CampoEspecie/", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Campo_Especie> Get_CampoEspecie(HttpSession httpSession) throws Exception {
//		session ses = new session(httpSession);
		ArrayList<Campo_Especie> r = new ArrayList<Campo_Especie>();
//		if (ses.isValid()) {
//			return r;
//		}
		r = Campo_ESPECIE.Get_CampoEspecie();
		return r;
	}	
	
	@RequestMapping(value = "/AGRO/Get_CampoVariedad/", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Campo_Variedad> Get_CampoVariedad(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		SESION mc= new SESION(httpSession);
		ArrayList<Campo_Variedad> r = new ArrayList<Campo_Variedad>();
//		if (ses.isValid()) {
//			return r;
//		}
		r = Campo_ESPECIE.Get_CampoVariedad(mc.getIdUserSesion());
		return r;
	}	
	
	@RequestMapping(value = "/AGRO/Get_CampoVariedad2/", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Campo_Variedad> Get_CampoVariedad2(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		SESION mc= new SESION(httpSession);
		ArrayList<Campo_Variedad> r = new ArrayList<Campo_Variedad>();
//		if (ses.isValid()) {
//			return r;
//		}
		r = Campo_ESPECIE.Get_CampoVariedad2(mc.getIdUserSesion());
		return r;
	}	
//	 
//----------------------------------CAMPO------------------------------------------------------
	@RequestMapping(value = "/AGRO/GETCAMPO/", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<CAMPO> getCAMPO(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<CAMPO> r = new ArrayList<CAMPO>();
//		if (ses.isValid()) {
//			return r;
//		}
		int id = ses.getIdUser();
		r = simpleagroDB.getCAMPO(httpSession, id);
		return r;
	}
//	UPDATE 
	@RequestMapping(value = "/AGRO/UP_SUBSIDIO_CUARTEL/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updatesubsidioCampo(@RequestBody  CAMPO row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return simpleagroDB.updatesubsidioCampo(row);
	}
	
	
	
	
//-------------------------------- FIN CAMPO ----------------------------------------------------
	
	
	
	
	
//----------------------------------SECTOR--------------------------------------------------------
	//	SElECT
	@RequestMapping(value = "/AGRO/GETSECTOR/", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<SECTOR> getSECTOR(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<SECTOR> r = new ArrayList<SECTOR>();
		if (ses.isValid()) {
			return r;
		}
		r = simpleagroDB.getSECTOR(httpSession);
		return r;
	}
	@RequestMapping(value = "/AGRO/GETSECTORES/{codigo}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<SECTOR> getMantenedorEspecie(@PathVariable String codigo, HttpSession httpSession)
			throws Exception {
		session ses = new session(httpSession);
		ArrayList<SECTOR> pm = new ArrayList<SECTOR>();
		if (ses.isValid()) {
			return pm;
		}
		pm = simpleagroDB.getSECTOR(codigo);
		return pm;
	}
//	INSERT
	@RequestMapping(value = "/AGRO/ADDSECTOR/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean setUser(@RequestBody  SECTOR row,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return MAPA.addSector(row, httpSession); 
	}
	
//	UPDATE
	@RequestMapping(value = "/AGRO/UP_SECTOR/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateSECTOR(@RequestBody  SECTOR row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return simpleagroDB.updateSECTOR(row);
	}
	@RequestMapping(value = "/AGRO/ADDSECTOR_SECTOR/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADDSECTOR_SECTOR(@RequestBody  SECTOR row,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return simpleagroDB.ADDSECTOR_SECTOR(row, httpSession); 
	}
	
	
	@RequestMapping(value = "/AGRO/GETCUARTEL/", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<CUARTEL> getCUARTEL(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<CUARTEL> r = new ArrayList<CUARTEL>();
//		if (ses.isValid()) {
//			return r;
//		}
		r = simpleagroDB.getCUARTEL(httpSession);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_ALL_CUARTEL/", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<CUARTEL> GET_ALL_CUARTEL(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<CUARTEL> r = new ArrayList<CUARTEL>();
//		if (ses.isValid()) {
//			return r;
//		}
		r = simpleagroDB.GET_ALL_CUARTEL();
		return r;
	}
	@RequestMapping(value = "/AGRO/ADDCUARTEL/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean addCuartel(@RequestBody  CUARTEL row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return MAPA.addCuartel(row); 

	}
	
	@RequestMapping(value = "/AGRO/GETALLCAMPO/{id}", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ArrayList<CUARTEL> loadRuta(@PathVariable int id ,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<CUARTEL> es = new ArrayList<CUARTEL>();
		if (ses.isValid()) {
			return es;
		}
		es = MAPA.GETALLCAMPO(id);
		return es;
	}
	@RequestMapping(value = "/AGRO/DELETE_CUARTEL/{id}", method = {RequestMethod.PUT,RequestMethod.POST})
	public @ResponseBody boolean DELETE_CUARTEL(@PathVariable  int id,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			return false;
		}
		return MAPA.DELETE_CUARTEL(id);

	}
	@RequestMapping(value = "/AGRO/UPDATE_CUARTEL", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPDATE_CUARTEL(@RequestBody  CUARTEL row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return MAPA.UPDATE_CUARTEL(row, httpSession); 
		
	}
	@RequestMapping(value = "/AGRO/UPDATE_GEOREFERENCIA", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPDATE_GEOREFERENCIA(@RequestBody  CUARTEL row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return MAPA.UPDATE_GEOREFERENCIA(row); 
		
	}
	
	
//	UPDATE
	@RequestMapping(value = "/AGRO/UP_CUARTEL/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateCUARTEL(@RequestBody  CUARTEL row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return simpleagroDB.updateCUARTEL(row);
	}
//-----------------------------------FIN CUARTEL-----------------------------------------------------
	
	
	
	

//-------------------------------------ESPECIE--------------------------------------------------------
//	GETESPECIE
	@RequestMapping(value = "/AGRO/GETESPECIE/", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<ESPECIE> getESPECIE(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<ESPECIE> r = new ArrayList<ESPECIE>();
		if (ses.isValid()) {
			return r;
		}
		r = simpleagroDB.getESPECIE(httpSession);
		return r;
	}
//-------------------------------------FIN ESPECIE------------------------------------------------------	
	

	
	
	
//	--------------------------------------VARIEDAD-------------------------------------------------------
//	SELECT
	@RequestMapping(value = "/AGRO/GETVARIEDAD/", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<VARIEDAD> getVARIEDAD(HttpServletRequest req,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<VARIEDAD> r = new ArrayList<VARIEDAD>();
		if (ses.isValid()) {
			return r;
		}
		r = simpleagroDB.getVARIEDAD(httpSession);
		return r;
	}
//-----------------------------------------FIN VARIEDAD--------------------------------------------------


	
//---------------------------------------MAPA.CUARTEL-----------------------------------------------------
//	SELECT
	@RequestMapping(value = "/AGRO/GET_CUARTEL_SECTOR/{id}", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ArrayList<CUARTEL> GET_CUARTEL_SECTOR(@PathVariable String id ,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<CUARTEL> es = new ArrayList<CUARTEL>();
		if (ses.isValid()) {
			return es;
		}
		es = MAPA.GET_CUARTEL_SECTOR(id);
		return es;
	}
	
	
	
	
//	-----------------------FIN CUARTEL-----------------------------
	@RequestMapping(value = "/AGRO/GETVARS/", method = { RequestMethod.GET })
	@ResponseBody SESIONVAR getCampos(HttpServletRequest request, HttpSession httpSession)throws Exception {
		
		session ses = new session(httpSession);	

		SESION mc= new SESION(httpSession);
//		simpleagroDB.getCAMPO(httpSession, ses.getIdUser());
//		simpleagroDB.getSECTOR(httpSession);
//		simpleagroDB.getESPECIE(httpSession);
//		simpleagroDB.getVARIEDAD(httpSession);
			
		return mc.getView();
	}
//----------------------------------------FIN MAPA.CUARTEL-----------------------------------------------------
	
	
	
	

//------------------------PROGRAMA FITOSANITARIO-----------------------------------------------------------------
//	SELECT
	@RequestMapping(value = "/AGRO/GETPF/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<PROGRA_FITOSANITARIO> GETPF(@RequestBody FILTRO_PF row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<PROGRA_FITOSANITARIO> r = new ArrayList<PROGRA_FITOSANITARIO>();
		if (ses.isValid()) {
			return r;
		}
		r = PROGRAMA_FITOSANITARIO.getPF(row);
		return r;
	}
//	INSERT
	@RequestMapping(value = "/AGRO/INSERTPF/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PROGRA_FITOSANITARIO INSERTPF(@RequestBody PROGRA_FITOSANITARIO row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		PROGRA_FITOSANITARIO prog = new PROGRA_FITOSANITARIO();
		if (ses.isValid()) {
			return prog; 
		}
		return PROGRAMA_FITOSANITARIO.insertPF(row);
	}
	
//	UPDATE
	@RequestMapping(value = "/AGRO/UPPF/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updatePF(@RequestBody  PROGRA_FITOSANITARIO row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return PROGRAMA_FITOSANITARIO.updatePF(row);
	}
	@RequestMapping(value = "/AGRO/UPPF2/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updatePF2(@RequestBody  PROGRA_FITOSANITARIO row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return PROGRAMA_FITOSANITARIO.updatePF2(row);
	}
	
	//RECHAZAR
	@RequestMapping(value = "/AGRO/RECPF/{id}/{estado}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean rechazarPF(@PathVariable int id,@PathVariable int estado , HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return PROGRAMA_FITOSANITARIO.rechazarPF(id,estado);
	}
	
//-----------------------FIN PROGRAMA FITOSANITARIO--------------------------------------------------------------
	
	
	
	
	
//-------------------------------DETALLE.CUARTEL----------------------------------------------------------------
	@RequestMapping(value = "/AGRO/GETDETALLECUARTEL/", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<detalleCuartel> GETDETALLECUARTEL(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<detalleCuartel> r = new ArrayList<detalleCuartel>();
		if (ses.isValid()) {
			return r;
		}
		r = detalleCuartelDB.getDetalleCuartel();
		return r;
	}
//-------------------------------FIN DETALLE.CUARTEL------------------------------------------------------------
	
	
	
	
//--------------------------------INCIDENCIAS--------------------------------
//	@RequestMapping(value = "/AGRO/GET_INCIDENCIA/", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody ArrayList<incidencia> GET_INCIDENCIA(HttpSession httpSession) throws Exception {
//		session ses = new session(httpSession);
//		ArrayList<incidencia> r = new ArrayList<incidencia>();
//		if (ses.isValid()) {
//			return r;
//		}
//		r = INCIDENCIAS.GET_INCIDENCIA();
//		return r;
//	}
	
//	INSERT
	@RequestMapping(value = "/AGRO/INSERT_INCIDENCIAS/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertINCIDENCIA(@RequestBody incidencia row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return INCIDENCIAS.insertINCIDENCIA(row);
	}
	
//	UPDATE
	@RequestMapping(value = "/AGRO/UP_INCIDENCIAS/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateINCIDENCIA(@RequestBody  incidencia row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return INCIDENCIAS.updateINCIDENCIA(row);
	}
//-------------------------------FIN INCIDENCIAS---------------------------
	
	
	

	
	
	
	
//	-----------------MATERIAL----------------------
	
//	SELECT
	@RequestMapping(value = "/AGRO/GETMA/", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<MATERIAL> GETMA(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<MATERIAL> r = new ArrayList<MATERIAL>();
		if (ses.isValid()) {
			return r;
		}
		r = material.GETMA();
		return r;
	}
//	------------------FIN MATERIAL-----------------
	
	
	
	
	
//--------------------MATERIAL_PF------------------
//	PROBAR MATERIAL PF
//	SELECT 
	@RequestMapping(value = "/AGRO/GETMPF/{id}", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<MATERIAL_PF> getMaterialPF(@PathVariable int id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<MATERIAL_PF> r = new ArrayList<MATERIAL_PF>();
		if (ses.isValid()) {
			return r;
		}
		r = material.getMPF(id);
		return r;
	}
//	INSERT
	@RequestMapping(value = "/AGRO/ADDMPF/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertMPF(@RequestBody  MATERIAL_PF row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return material.insertMPF(row);
	}
//	DELETE
	@RequestMapping(value = "/AGRO/DEMPF/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateMPF(@RequestBody int id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return material.deleteMPF(id);
	}
//----------------FIN MATERIAL_PF-----------------------
	
	
	//--------------------CUARTEL_PF------------------
//	PROBAR MATERIAL PF
//	SELECT 
	@RequestMapping(value = "/AGRO/GETCPF/{id}", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<CUARTEL_PF> getCuartelPF(@PathVariable int id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<CUARTEL_PF> r = new ArrayList<CUARTEL_PF>(); 
		if (ses.isValid()) {
			return r;
		}
		r = cuartel.getCPF(id); 
		return r;
	} 
//	INSERT
	@RequestMapping(value = "/AGRO/ADDCPF/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertCPF(@RequestBody  CUARTEL_PF row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return cuartel.insertCPF(row);
	}
//	DELETE

//----------------FIN CUARTEL_PF-----------------------
	
	
//-------------ESTADO_FENOLOGICO---------------------
//	SELECT
	@RequestMapping(value = "/AGRO/GET_EstadoFenologico/", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<estado_fenologico> getEstadoFenologico(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<estado_fenologico> r = new ArrayList<estado_fenologico>();
		if (ses.isValid()) {
			return r;
		}
		r = ESTADO_FENOLOGICO.getEstadoFenologico();
		return r;
	}
//	INSERT
	@RequestMapping(value = "/AGRO/ADD_EstadoFenologico/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertEF(@RequestBody  estado_fenologico row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return ESTADO_FENOLOGICO.insertEF(row);
	}
//	UPDATE
	@RequestMapping(value = "/AGRO/UP_EstadoFenologico/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateEF(@RequestBody  estado_fenologico row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return ESTADO_FENOLOGICO.updateEF(row);
	}
	
//	UPDATE
	@RequestMapping(value = "/AGRO/UP_EstadoFenologico_Estado/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UP_EstadoFenologico_Estado(@RequestBody  estado_fenologico row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return ESTADO_FENOLOGICO.UP_EstadoFenologico_Estado(row);
	}
//-----------FIN ESTADO_FENOLOGICO--------------------
	
	
	
	
	
//--------------PROGRAMA_APLICACION-------------
//	SELECT PROGRAMA_APLICACION
	@RequestMapping(value = "/AGRO/getPA/", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<programa_aplicacion> getProgramaAplicacion(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<programa_aplicacion> r = new ArrayList<programa_aplicacion>();
		if (ses.isValid()) {
			return r;
		}
		r = PROGRAMA_FITOSANITARIO.getProgramaAplicacion();
		return r;
	}
//	INSERT
	@RequestMapping(value = "/AGRO/ADDPA/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertPA(@RequestBody  programa_aplicacion row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return PROGRAMA_FITOSANITARIO.insertPA(row);
	}
//	UPDATE
	@RequestMapping(value = "/AGRO/UPPA/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updatePA(@RequestBody  programa_aplicacion row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return PROGRAMA_FITOSANITARIO.updatePA(row);
	}
//-----------FIN PROGRAMA_APLICACION------
	
	
	
	
	
//-----------------Perfil---------- 
//	SELECT
	@RequestMapping(value = "/AGRO/getP/{id}", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<PERFIL> getPerfil(@PathVariable int id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<PERFIL> r = new ArrayList<PERFIL>();
		if (ses.isValid()) {
			return r;
		}
		r = PROGRAMA_FITOSANITARIO.getPerfil(id);
		return r;
	}
//-----------FIN PERFIL----------
	
	

	
	
//-----------LABOR----------------
//	SELECT
	@RequestMapping(value = "/AGRO/GET_LABOR/{codigo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LABOR> GET_LABOR(@PathVariable String codigo, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<LABOR> r = new ArrayList<LABOR>();
		if (ses.isValid()) {
			return r;
		}
		r = labor.GET_LABOR(codigo);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_LABOR_FAENA/{id}/{zona}", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LABOR> GET_LABOR_FAENA(@PathVariable int id, @PathVariable String zona,  HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<LABOR> r = new ArrayList<LABOR>();
		if (ses.isValid()) {
			return r;
		}
		r = labor.GET_LABOR_FAENA(id, zona);
		return r;
	}
//	actualizar estado
	@RequestMapping(value = "/AGRO/UP_LABOR_FAENA_ESTADO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UP_LABOR_FAENA_ESTADO(@RequestBody  LABOR row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		} 
		return labor.UP_LABOR_FAENA_ESTADO(row);
	}
	
	@RequestMapping(value = "/AGRO/ADD_LABOR/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertLabor(@RequestBody  LABOR row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return labor.insertLabor(row);
	}
	
//	UPDATE
	@RequestMapping(value = "/AGRO/UPDATE_LABOR/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateLAbor(@RequestBody  LABOR row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return labor.updateLAbor(row);
	}
	
//	---------FIN LABOR------------
	
	
	
	
	
//--------------FAENA-------------
//	SELECT
	@RequestMapping(value = "/AGRO/GETFAENA/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<faena> GETFAENA( HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<faena> r = new ArrayList<faena>();
		if (ses.isValid()) {
			return r;
		}
		r = FAENA.GET_FAENA();
		return r;
	}
//	INSERT
	@RequestMapping(value = "/AGRO/ADDFAENA/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADDFAENA(@RequestBody  faena row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return FAENA.ADD_FAENA(row);
	}
//	UPDATE
	@RequestMapping(value = "/AGRO/UPDATEFAENA/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPDATEFAENA(@RequestBody  faena row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return FAENA.UPDATEFAENA(row);
	}
//	UPDATE
	@RequestMapping(value = "/AGRO/UPFAENA_ESTADO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPFAENA_ESTADO(@RequestBody  faena row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return FAENA.UPFAENA_ESTADO(row);
	}
//---------------------FIN CUADRILLA-----------------
	
	
	
	
	
//-------------------NOTIFICACION--------------------
//	SELECT
	@RequestMapping(value = "/AGRO/GETNotAlert/{tipo}/{estado}/{campo}", method = RequestMethod.GET)
	public @ResponseBody ArrayList<notificacion> GETNotAlert( @PathVariable  int tipo,@PathVariable  int estado, @PathVariable  String campo,  HttpSession httpSession) throws Exception {
		session ses = new session(httpSession); 
		int id = ses.getIdUser();
		ArrayList<notificacion> r = new ArrayList<notificacion>();
		if (ses.isValid()) {
			return r;
		}
		int user = 1;
		r = NOTIFICACIONES.GETN(user,tipo,estado,campo,id);
		return r;
	}
//	@RequestMapping(value = "/AGRO/GETNot/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody ArrayList<notificacion> GETNot(@PathVariable  int id, HttpSession httpSession) throws Exception {
//		session ses = new session(httpSession);
//		
//		ArrayList<notificacion> r = new ArrayList<notificacion>();
//		if (ses.isValid()) {
//			return r;
//		}
//		r = NOTIFICACIONES.GETN(id,1);
//		return r;
//	}
//	INSERT
	@RequestMapping(value = "/AGRO/ADDN/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertN(@RequestBody  notificacion row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return NOTIFICACIONES.insertN(row);
	}
	
//	UPDATE ESTADO
	@RequestMapping(value = "/AGRO/UpdateEstadoNot/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UpdateEstadoNot(@PathVariable  int id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return NOTIFICACIONES.updateEstadoNot(id,1);
	} 
//------------------FIN NOTIFICACION---------------
	
	
	
	
	
//------------------CALIFICACION_CAMPO-------------
	

//	INSERT
	@RequestMapping(value = "/AGRO/ADDCC/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertCC(@RequestBody  calificacion_campo row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return PROGRAMA_FITOSANITARIO.insertCC(row);
	}
//	UPDATE
	@RequestMapping(value = "/AGRO/UPCC/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateCC(@RequestBody  calificacion_campo row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return PROGRAMA_FITOSANITARIO.updateCC(row);
	}
	@RequestMapping(value = "/AGRO/GETER/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<estado_rendimiento> GETER(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<estado_rendimiento> r = new ArrayList<estado_rendimiento>();
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GETER();
		return r;
	}
	
//	SELECT
	@RequestMapping(value = "/AGRO/GETCREAR_CALIFICACION/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<calificacion_campo> GETCREAR_CALIFICACION(@RequestBody calificacion_campo row,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<calificacion_campo> r = new ArrayList<calificacion_campo>();
		if (ses.isValid()) {
			return r;
		}
		r = CALIFICACION_RENIDIMIENTO.GETCREAR_CALIFICACION(row);
		return r;
	}
	
	
//	SELECT
	@RequestMapping(value = "/AGRO/GETCALIFICACION/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<calificacion_campo> GETCALIFICACION( HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<calificacion_campo> r = new ArrayList<calificacion_campo>();
		if (ses.isValid()) {
			return r;
		}
		r = CALIFICACION_RENIDIMIENTO.GETCALIFICACION();
		return r;
	}
	
//	INSERT
	@RequestMapping(value = "/AGRO/ADDCAMPO_LABOR/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertCAMPO_LABOR(@RequestBody  calificacion_campo row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		ArrayList<calificacion_campo> r = new ArrayList<calificacion_campo>();
		r = CALIFICACION_RENIDIMIENTO.GETCREAR_CALIFICACION(row);
		if(r.size() == 0){
			return CALIFICACION_RENIDIMIENTO.insertCAMPO_LABOR(row);
		} else {
			return CALIFICACION_RENIDIMIENTO.updateCC(row);
		}
		//return CALIFICACION_RENIDIMIENTO.insertCAMPO_LABOR(row);
	}
//--------------FIN ESTADO_RENDIMIENTO--------------
	
	
	
	
//------------------ORDEN_APLICACION----------------
//	SELECT
	@RequestMapping(value = "/AGRO/GETOA/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<ORDEN_APLICACION> GETOA(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<ORDEN_APLICACION> r = new ArrayList<ORDEN_APLICACION>();
		if (ses.isValid()) {
			return r;
		}
		r = ORDEN_APLICA.GETOA();
		return r;
	}
////	insert
	@RequestMapping(value = "/AGRO/ADDOA/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody int insertOA(@RequestBody  ORDEN_APLICACION row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return 0;
		}
		return ORDEN_APLICA.insertOA(row);
	}
	@RequestMapping(value = "/AGRO/UPDATEOA/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateOA(@RequestBody  ORDEN_APLICACION row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return ORDEN_APLICA.updateOA(row);
	}
//	UPDATE
//	@RequestMapping(value = "/AGRO/UPOA/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody boolean updateOA(@RequestBody  ORDEN_APLICACION row, HttpSession httpSession) throws Exception {
//		session ses = new session(httpSession);
//		if (ses.isValid()) {
//			return false;
//		}
//		return ORDEN_APLICA.updateOA(row);
//	}

//---------------FIN ORDEN_APLICACION--------------






//----------------------CONFIRMACION_APLICACION-------------------
//	SELECT
	@RequestMapping(value = "/AGRO/GETCAP/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<CONFIRMACION_APLICACION> GETCAP(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<CONFIRMACION_APLICACION> r = new ArrayList<CONFIRMACION_APLICACION>();
		if (ses.isValid()) {
			return r;
		}
		r = ORDEN_APLICA.GETCAP();
		return r;
	}
//	insert
	@RequestMapping(value = "/AGRO/ADDCAP/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertCAP(@RequestBody  CONFIRMACION_APLICACION row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return ORDEN_APLICA.insertCAP(row);
	}
//	UPDATE
	@RequestMapping(value = "/AGRO/UPCAP/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateCAP(@RequestBody  CONFIRMACION_APLICACION row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return ORDEN_APLICA.updateCAP(row);
	}
//-------------------FIN CONFIRMACION_APLICACION----------------
	
	
	

//	--------------------CECO------------------------------------
//	SELECT
	@RequestMapping(value = "/AGRO/GETCECO/{campo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<CECO> GETCECO(@PathVariable int campo ,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<CECO> r = new ArrayList<CECO>();
		if (ses.isValid()) {
			return r;
		}
		r = cuartel.GETCECO(campo);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_ALL_CECO/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<CECO> GET_ALL_CECO(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<CECO> r = new ArrayList<CECO>();
		if (ses.isValid()) {
			return r;
		}
		r = cuartel.GET_ALL_CECO();
		return r;
	}
//--------------------------FIN CECO---------------------------
	
	
//	--------------------FORMA APLICACION------------------------------------
//	SELECT
	@RequestMapping(value = "/AGRO/GETFA/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<FORMA_APLICA> GETFA(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<FORMA_APLICA> r = new ArrayList<FORMA_APLICA>();
		if (ses.isValid()) {
			return r;
		}
		r = FORMA_APLICACION.GETFA();
		return r;
	}
//	insert
	@RequestMapping(value = "/AGRO/ADDFORMAPLICACION/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertFA(@RequestBody  FORMA_APLICA row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return FORMA_APLICACION.insertFA(row);
	}
	
//	actualizar
	@RequestMapping(value = "/AGRO/UPFORMAPLICACION/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateFA(@RequestBody  FORMA_APLICA row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return FORMA_APLICACION.updateFA(row);
	}
	
//	actualizar estado
	@RequestMapping(value = "/AGRO/UPFORMA_APLICACION_ESTADO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPFORMA_APLICACION_ESTADO(@RequestBody  FORMA_APLICA row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		} 
		return FORMA_APLICACION.UPFORMA_APLICACION_ESTADO(row);
	}
//--------------------------FIN FORMA APLICACION---------------------------

	
//	--------------------CONTROL APLICACION------------------------------------
//	SELECT
	@RequestMapping(value = "/AGRO/GETCONTROL_APLICACION/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<CONTROL_AP> GETCA(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<CONTROL_AP> r = new ArrayList<CONTROL_AP>();
		if (ses.isValid()) {
			return r;
		}
		r = CONTROL_APLICACION.getControlAplicacion();
		return r;
	}
//	insert
	@RequestMapping(value = "/AGRO/ADDCONTROL_APLICACION/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertCA(@RequestBody  CONTROL_AP row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return CONTROL_APLICACION.insertCA(row);
	}
//	actualiza
	@RequestMapping(value = "/AGRO/UPCONTROL_APLICACION/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateFA(@RequestBody  CONTROL_AP row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return CONTROL_APLICACION.updateCA(row);
	}

	//update estado
	@RequestMapping(value = "/AGRO/UP_ControlAplicacion_Estado/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UP_ControlAplicacion_Estado(@RequestBody CONTROL_AP row,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return CONTROL_APLICACION.UP_ControlAplicacion_Estado(row);
	}
//--------------------------FIN CONTROL APLICACION---------------------------
	
	

//------------------------BLOQUEO LABOR---------------------
	
//	SELECT
	@RequestMapping(value = "/AGRO/GETBLOQUEO_LABOR/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<BLOQUEO_LABOR> GET_BLOQUEO_LABOR(@RequestBody  BLOQUEO_LABOR row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<BLOQUEO_LABOR> r = new ArrayList<BLOQUEO_LABOR>();
		if (ses.isValid()) {
			return r;
		}
		r = bloqueo_labor.GET_BLOQUEO_LABOR(row);
		return r;
	}
		

	
//	SELECT
	@RequestMapping(value = "/AGRO/GETBLOQUEO_LABOR_ID/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<BLOQUEO_LABOR> GET_BLOQUEO_LABOR_ID( HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<BLOQUEO_LABOR> r = new ArrayList<BLOQUEO_LABOR>();
		if (ses.isValid()) {
			return r;
		}
		r = bloqueo_labor.GET_BLOQUEO_LABOR_ID();
		return r;
	}	
	
	@RequestMapping(value = "/AGRO/ADDBLOQUEO_LABOR/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertBLOQUEO_Labor(@RequestBody  BLOQUEO_LABOR row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		ArrayList<BLOQUEO_LABOR> r = new ArrayList<BLOQUEO_LABOR>();
		r = bloqueo_labor.GET_BLOQUEO_LABOR(row);
		if(r.size() == 0){
			return bloqueo_labor.insertBLOQUEO_Labor(row);
		} else {
			return bloqueo_labor.updateBLOQUEO_LABOR(row);
		}
	}

//	actualiza
	@RequestMapping(value = "/AGRO/UPBLOQUEO_LABOR/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateBLOQUEO_LABOR(@RequestBody  BLOQUEO_LABOR row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return bloqueo_labor.updateBLOQUEO_LABOR(row);
	}
//-----------------------FIN BLOQUEO LABOR-----------------
	
	
	
//---------------------RECORRIDOS----------------
//	SELECT RECORRIDO
	@RequestMapping(value = "/AGRO/GET_RECORRIDO/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<recorrido> GET_RECORRIDO(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<recorrido> r = new ArrayList<recorrido>();
		if (ses.isValid()) {
			return r;
		}
		r = RECORRIDO.GET_RECORRIDO();
		return r;
	}	
	
	@RequestMapping(value = "/AGRO/GET_HRECORRIDO/{codigo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<recorrido> GET_HRECORRIDO(@PathVariable int codigo,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<recorrido> r = new ArrayList<recorrido>();
		if (ses.isValid()) {
			return r;
		}
		r = RECORRIDO.GET_HRECORRIDO(codigo);
		return r;
	}
	
//	insert recorrido
	@RequestMapping(value = "/AGRO/ADDRECORRIDO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertRECORRIDO(@RequestBody  recorrido row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RECORRIDO.insertRECORRIDO(row); 
	}
	
//	actualiza
	@RequestMapping(value = "/AGRO/UPRECORRIDO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateRECORRIDO(@RequestBody  recorrido row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RECORRIDO.updateRECORRIDO(row);
	}
//	-----------------FIN RECORRIDOS--------------
	
	
	
//-----------------CALIFICACION ESTADO---------
//	SELECT
	@RequestMapping(value = "/AGRO/Get_CalificacionEstado/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<ESTADO> Get_CalificacionEstado(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<ESTADO> r = new ArrayList<ESTADO>();
		if (ses.isValid()) {
			return r;
		}
		r = CALIFICACION_ESTADO.Get_CalificacionEstado();
		return r;
	}		
	
	@RequestMapping(value = "/AGRO/ADD_Calificacion_Estado/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean calificacion_estado(@RequestBody  ESTADO row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		ArrayList<ESTADO> r = new ArrayList<ESTADO>();
		r = CALIFICACION_ESTADO.Get_Calificacion_Estado(row);
		if(r.size() == 0){
			return CALIFICACION_ESTADO.Insert_Calificacion_Estado(row);
		} else {
			return CALIFICACION_ESTADO.Update_Calificacion_Estado(row);
		}
	}
//--------------------------------------------
	
	
	@RequestMapping(value = "/AGRO/GET_DotacionDiaria/", method = {RequestMethod.POST,RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<DOTACION_DIARIA> GET_DotacionDiaria(@RequestBody DOTACION_DIARIA row, HttpSession httpSession) throws Exception {	
		session ses = new session(httpSession);
		ArrayList<DOTACION_DIARIA> r = new ArrayList<DOTACION_DIARIA>();
		if (ses.isValid()) {
			return r;
		}
		r = DotacionDiaria.GET_DotacionDiaria(row);
		return r;
	}
//	@RequestMapping(value = "/AGRO/ADD_Categoria/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody boolean ADD_Categoria(@RequestBody  CATEGORIA row,HttpSession httpSession) throws Exception {
//		
//		session ses = new session(httpSession);
//		if (ses.isValid()) {
//			return false;
//		}
//		return Categoria.ADD_Categoria(row); 
//	}
	
	@RequestMapping(value = "/AGRO/UPDATE_Categoria/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPDATE_Categoria(@RequestBody  CATEGORIA row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return Categoria.UPDATE_Categoria(row);
	}
	@RequestMapping(value = "/AGRO/UP_Categoria_Estado/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UP_Categoria_Estado(@RequestBody  CATEGORIA row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return Categoria.UP_Categoria_Estado(row);
	} 
	@RequestMapping(value = "/AGRO/ADD_Calibre/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_Calibre(@RequestBody  CALIBRE row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return Calibre.ADD_Calibre(row); 
	}
	
	@RequestMapping(value = "/AGRO/UPDATE_Calibre/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPDATE_Calibre(@RequestBody  CALIBRE row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return Calibre.UPDATE_Calibre(row);
	}
	@RequestMapping(value = "/AGRO/UP_Calibre_Estado/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UP_Calibre_Estado(@RequestBody  CALIBRE row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return Calibre.UP_Calibre_Estado(row);
	}
	//-----------------MANTENEDOR GENÉRICO---------
//	insert
	@RequestMapping(value = "/AGRO/ADDMANTENEDORGEN/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertMG(@RequestBody  MANTENEDOR_GEN row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return MANTENEDOR_GENERICO.insertMG(row);
	}
	@RequestMapping(value = "/AGRO/ADD_USUARIO_CAMPO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_USUARIO_CAMPO(@RequestBody  ArrayList<CAMPO> campo, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		ConnectionDB db = new ConnectionDB();
		return MAPA.ADD_USUARIO_CAMPO(db, campo);
	}

	@RequestMapping(value = "/AGRO/GET_FAENA_ZONA", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<faena> GET_FAENA_ZONA(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<faena> r = new ArrayList<faena>();
		if (ses.isValid()) {
			return r;
		}
		String zona = request.getParameter("ZONA");
		r = FAENA.GET_FAENA_ZONA(zona);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_LABOR_FAENA_MAQ/{id}/{zona}", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LABOR> GET_LABOR_FAENA_MAQ(@PathVariable int id, @PathVariable String zona,  HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<LABOR> r = new ArrayList<LABOR>();
		if (ses.isValid()) {
			return r;
		}
		r = labor.GET_LABOR_FAENA_MAQ(id, zona);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_SOCIEDAD/{id}", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<CAMPO> GET_SOCIEDAD(@PathVariable int id,  HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<CAMPO> r = new ArrayList<CAMPO>();
		if (ses.isValid()) {
			return r;
		}
		int ids = ses.getIdUser();
		r = simpleagroDB.GET_SOCIEDAD(ids);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_CALIFICACION", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<calificacion_campo> GET_CALIFICACION(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<calificacion_campo> r = new ArrayList<calificacion_campo>();
		if (ses.isValid()) {
			return r;
		}
		String campo = request.getParameter("CAMPO");
		int labor = Integer.parseInt(request.getParameter("LABOR"));
		r = simpleagroDB.GET_CALIFICACION(campo, labor);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_BLOQUEO_M_P", method = {RequestMethod.POST,RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<bloqueo_periodo> GET_BLOQUEO_M_P(HttpServletRequest request, HttpSession httpSession) throws Exception {	
		session ses = new session(httpSession);
		ArrayList<bloqueo_periodo> r = new ArrayList<bloqueo_periodo>();
		if (ses.isValid()) {
			return r;
		}
		int periodo = Integer.parseInt(request.getParameter("PERIODO"));
		String campo = request.getParameter("CAMPO");
		int tipo = Integer.parseInt(request.getParameter("AW"));
		r = simpleagroDB.GET_BLOQUEO_M_P(periodo, campo, tipo);
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_VARIEDAD/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_VARIEDAD(@RequestBody  VARIEDAD row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return simpleagroDB.ADD_VARIEDAD(row,httpSession);
	}
	@RequestMapping(value = "/AGRO/UPD_VARIEDAD/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPD_VARIEDAD(@RequestBody  VARIEDAD row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return simpleagroDB.UPD_VARIEDAD(row,httpSession);
	}
	
	@RequestMapping(value = "/AGRO/GET_LABORESYFAENA/", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LABOR> GET_LABORESYFAENA( HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<LABOR> r = new ArrayList<LABOR>();
		if (ses.isValid()) {
			return r;
		}
		int ids = ses.getIdUser();
		r = simpleagroDB.GET_LABORYFAENA();
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_TEMPORADA/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_TEMPORADA(@RequestBody  TEMPORADA row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return simpleagroDB.ADD_TEMPORADA(row);
	}
	@RequestMapping(value = "/AGRO/GET_LOGIN_APP", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody SESIONVAR GET_LOGIN_APP(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		SESIONVAR r = new SESIONVAR();
		if (ses.isValid()) {
			return r;
		}
		String user = request.getParameter("USER");
		r = simpleagroDB.GET_LOGIN_APP(user, httpSession);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_DOSIFICADORES", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_DOSIFICADORES(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		String r = "";
		if (ses.isValid()) {
			return "Se ha perdido sesion";
		}
		String campo = request.getParameter("CAMPO");
		r = simpleagroDB.GET_DOSIFICADORES(campo);
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_DOSIFICADOR", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_DOSIFICADOR(@RequestBody  String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return simpleagroDB.ADD_DOSIFICADOR(row);
	}
	@RequestMapping(value = "/AGRO/UPD_DOSIFICADOR", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPD_DOSIFICADOR(@RequestBody  String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return simpleagroDB.UPD_DOSIFICADOR(row);
	}
	@RequestMapping(value = "/AGRO/DEL_DOSIFICADOR", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean DEL_DOSIFICADOR(@RequestBody  String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return simpleagroDB.DEL_DOSIFICADOR(row);
	}
	@RequestMapping(value = "/AGRO/GET_VARIEDAD_CAMPO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_VARIEDAD_CAMPO(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		String r = "";
		if (ses.isValid()) {
			return "Se ha perdido sesion";
		}
		int variedad = Integer.parseInt(request.getParameter("VARIEDAD"));
		r = simpleagroDB.GET_VARIEDAD_CAMPO(variedad);
		return r;
	}
	@RequestMapping(value = "/AGRO/UPD_FECHAS_VARIEDAD", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPD_FECHAS_VARIEDAD(@RequestBody  String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		JSONArray d = new JSONArray(row);
		for(int i = 0; i < d.length(); i++){
			JSONObject e = new JSONObject(d.getJSONObject(i).toString());
			ConnectionDB db = new ConnectionDB();
			simpleagroDB.UPD_FECHAS_VARIEDAD(e.toString(), db);
		}
		return true;
	}
	
}
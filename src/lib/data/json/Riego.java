package lib.data.json;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.SADB.Categoria;
import lib.SADB.ConsumoCombustible;
import lib.SADB.FACTOR_DECISION;
import lib.SADB.INGRESO_RIEGO;
import lib.SADB.MAESTRO_MOTIVOINGRESO;
import lib.SADB.MantecionDB;
import lib.SADB.PackingFrigorifico;
import lib.SADB.RENDIMIENTO;
import lib.SADB.RIEGO;
import lib.SADB.ServicioExterno;
import lib.classSA.BLOQUE;
import lib.classSA.CUARTEL_PF;
import lib.classSA.Consumo_Combustible;
import lib.classSA.FACTOR;
import lib.classSA.INGRESORIEGO;
import lib.classSA.MAESTRO_MOTIVO_INGRESO;
import lib.classSA.Packing_Frigorifico;
import lib.classSA.Servicio_Externo;
import lib.classSA.estimacion_data_prd;
import lib.classSA.evaporacionPrd;
import lib.classSA.programa_riego;
import lib.classSA.respuesta;
import lib.classSA.taller;
import lib.classSW.EQUIPO;
import lib.classSW.evaporacion;
import lib.classSW.evaporacionAcumulada;
import lib.classSW.riegos;
import lib.db.ConnectionDB;
import lib.security.session;

@Controller
public class Riego {
	@RequestMapping(value = "/AGRO/GETEQUIPO/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<EQUIPO> GETEQUIPO(@PathVariable String id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<EQUIPO> r = new ArrayList<EQUIPO>();
		if (ses.isValid()) {
			return r;
		}
		r = RIEGO.GET_EQUIPO(id);
		return r;
	}
	@RequestMapping(value = "/AGRO/GETEQUIPOBYCOD/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<EQUIPO> GETEQUIPOBYCOD(@PathVariable String id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<EQUIPO> r = new ArrayList<EQUIPO>();
		if (ses.isValid()) {
			return r;
		}
		r = RIEGO.GET_EQUIPOBYCOD(id);
		return r;
	}
	@RequestMapping(value = "/AGRO/ADDBLOQUE/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADDBLOQUE(@RequestBody  BLOQUE row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RIEGO.addBloque(row);
	}
	@RequestMapping(value = "/AGRO/UPDATEBLOQUE/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPDATEBLOQUE(@RequestBody BLOQUE row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RIEGO.updateBloque(row);
	}
	@RequestMapping(value = "/AGRO/GETBLOQUES/{codigo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<BLOQUE> GETBLOQUES(@PathVariable String[] codigo, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<BLOQUE> r = new ArrayList<BLOQUE>();
		if (ses.isValid()) {
			return r;
		}
		r = RIEGO.GET_BLOQUES(codigo);
		return r;
	}
	
	@RequestMapping(value = "/AGRO/GETBLOQUESBYCAMPO/{codigo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<BLOQUE> GETBLOQUESBYCAMPO(@PathVariable String codigo, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<BLOQUE> r = new ArrayList<BLOQUE>();
		if (ses.isValid()) {
			return r;
		}
		r = RIEGO.GET_BLOQUESBYCAMPO(codigo);
		return r;
	}
	@RequestMapping(value = "/AGRO/GETBLOQUESBYEQUIPO/{cod_campo}/{cod_equipo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<EQUIPO> GETBLOQUESBYEQUIPO(@PathVariable String cod_campo, @PathVariable String cod_equipo, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<EQUIPO> r = new ArrayList<EQUIPO>();
		if (ses.isValid()) {
			return r;
		}
		r = RIEGO.GET_BLOQUESBYEQUIPO(cod_campo, cod_equipo);
		return r;
	}
	@RequestMapping(value = "/AGRO/GETFACTORBYCAMPO/{codigo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<FACTOR> GETFACTORBYCAMPO(@PathVariable String codigo ,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<FACTOR> r = new ArrayList<FACTOR>();
		if (ses.isValid()) {
			return r;
		}
		r = FACTOR_DECISION.GETFACTORBYCAMPO(codigo);
		return r;
	}
	@RequestMapping(value = "/AGRO/UPDATE_FactorDecision/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPDATE_FactorDecision(@RequestBody  FACTOR row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return FACTOR_DECISION.UPDATE_FactorDecision(row);
	}
	@RequestMapping(value = "/AGRO/UPEVAPORACIONACTUAL/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPEVAPORACIONACTUAL(@RequestBody  evaporacion row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RIEGO.UPEVAPORACIONACTUAL(row);
	}
	@RequestMapping(value = "/AGRO/UPEVAPORACIONNEXT/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPEVAPORACIONNEXT(@RequestBody  evaporacion row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RIEGO.UPEVAPORACIONNEXT(row);
	}
	@RequestMapping(value = "/AGRO/GETFACTORBYMES/{codigo}/{codigo2}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<FACTOR> GETFACTORBYMES(@PathVariable String codigo , @PathVariable String codigo2 ,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<FACTOR> r = new ArrayList<FACTOR>();
		if (ses.isValid()) {
			return r;
		}
		r = FACTOR_DECISION.GETFACTORBYMES(codigo, codigo2);
		return r;
	}
	@RequestMapping(value = "/AGRO/GETFACTORBYEQUIPO/{codigo}/{codigo2}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<EQUIPO> GETFACTORBYEQUIPO(@PathVariable  String codigo, @PathVariable  String codigo2, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<EQUIPO> r = new ArrayList<EQUIPO>();
		if (ses.isValid()) {
			return r;
		}
		r = FACTOR_DECISION.GETFACTORBYEQUIPO(codigo, codigo2);
		return r;
	}
	@RequestMapping(value = "/AGRO/GETFACTORES/{codigo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<FACTOR> GETFACTORES(@PathVariable String[] codigo, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<FACTOR> r = new ArrayList<FACTOR>();
		if (ses.isValid()) {
			return r;
		}
		r = FACTOR_DECISION.GET_FACTORES(codigo);
		return r;
	}
	@RequestMapping(value = "/AGRO/DELETEBLOQUE/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean DELETEBLOQUE(@PathVariable String id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RIEGO.deleteBloque(id);
	}
	@RequestMapping(value = "/AGRO/GET_CUARTEL_BLOQUES/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<CUARTEL_PF> GET_CUARTEL_BLOQUES(@PathVariable String id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<CUARTEL_PF> r = new ArrayList<CUARTEL_PF>();
		if (ses.isValid()) {
			return r;
		}
		r = RIEGO.GET_BLOQUES_CUARTEL(id);
		return r;
	}
	
	@RequestMapping(value = "/AGRO/Get_MaestroIngreso/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<MAESTRO_MOTIVO_INGRESO> GET_MaestroMotivoIngreso(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<MAESTRO_MOTIVO_INGRESO> r = new ArrayList<MAESTRO_MOTIVO_INGRESO>();
		if (ses.isValid()) {
			return r;
		}
		r = MAESTRO_MOTIVOINGRESO.GET_MaestroMotivoIngreso();
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_MaestroIngreso/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_MaestroMotivoIngreso(@RequestBody  MAESTRO_MOTIVO_INGRESO row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		
		return MAESTRO_MOTIVOINGRESO.ADD_MaestroMotivoIngreso(row); 
	}
	

	@RequestMapping(value = "/AGRO/ADDIngresoRiego/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_Ingreso_Riego(@RequestBody  INGRESORIEGO row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}	
		return INGRESO_RIEGO.ADD_Ingreso_Riego(row); 
	}
	//@RequestMapping(value = "/AGRO/Get_MaestroIngreso/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	//public @ResponseBody ArrayList<INGRESORIEGO> GET_Ingreso_Riego(HttpSession httpSession) throws Exception {
	//	session ses = new session(httpSession);
	//	
	//	ArrayList<INGRESORIEGO> r = new ArrayList<INGRESORIEGO>();
	//	if (ses.isValid()) {
	//		return r;
	//	}
	//	r = INGRESO_RIEGO.GET_Ingreso_Riego();
	//	return r;
	//}
	
	@RequestMapping(value = "/AGRO/GET_Ingreso_Riego/{campo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<INGRESORIEGO> GET_Ingreso_Riego(@PathVariable String campo ,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<INGRESORIEGO> r = new ArrayList<INGRESORIEGO>();
		if (ses.isValid()) {
			return r;
		}
		r = INGRESO_RIEGO.GET_Ingreso_Riego(campo);
		return r;
	}
	
	@RequestMapping(value = "/AGRO/ADD_ConsumoCombustible/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_ConsumoCombustible(@RequestBody  ArrayList<Consumo_Combustible> row,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return ConsumoCombustible.ADD_ConsumoCombustible(row); 
		}
	
	@RequestMapping(value = "/AGRO/UP_MaestroMotivoIngreso/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UP_MaestroMotivoIngreso(@RequestBody  MAESTRO_MOTIVO_INGRESO row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		} 
		return MAESTRO_MOTIVOINGRESO.UP_MaestroMotivoIngreso(row);
	}
	
	//actualizar estado
	@RequestMapping(value = "/AGRO/UP_MaestroMotivoIngreso_Estado/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UP_MaestroMotivoIngreso_Estado(@RequestBody  MAESTRO_MOTIVO_INGRESO row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		} 
		return MAESTRO_MOTIVOINGRESO.UP_MaestroMotivoIngreso_Estado(row);
	}
	
	@RequestMapping(value = "/AGRO/GET_PackingFrigorifico/{campo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Packing_Frigorifico> GET_PackingFrigorifico(@PathVariable String campo ,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<Packing_Frigorifico> r = new ArrayList<Packing_Frigorifico>();
		if (ses.isValid()) {
			return r;
		}
		r = PackingFrigorifico.GET_PackingFrigorifico(campo);
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_PackingFrigorifico/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_PackingFrigorifico(@RequestBody  Packing_Frigorifico row,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}	
		return PackingFrigorifico.ADD_PackingFrigorifico(row); 
	}
	
	@RequestMapping(value = "/AGRO/ADD_ServicioExterno/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_ServicioExterno_Envio(@RequestBody  Servicio_Externo row,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}	
		return ServicioExterno.ADD_ServicioExterno_Envio(row); 
	}
	@RequestMapping(value = "/AGRO/GET_ServicioExterno_Envio/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Servicio_Externo> GET_ServicioExterno_Envio(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<Servicio_Externo> r = new ArrayList<Servicio_Externo>();
		if (ses.isValid()) {
			return r;
		}
		r = ServicioExterno.GET_ServicioExterno_Envio();
		return r;
	}
	@RequestMapping(value = "/AGRO/UP_ServicioExterno/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UP_ServicioExterno(@RequestBody  Servicio_Externo row,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}	
		return ServicioExterno.UP_ServicioExterno(row); 
	}
	@RequestMapping(value = "/AGRO/cerrar_ingreso_riego/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean cerrar_ingresoRiego(@RequestBody INGRESORIEGO row,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}	
		return INGRESO_RIEGO.CERRAR_INGRESO_RIEGO(row);
	}
	@RequestMapping(value = "/AGRO/cerrar_ingreso_packing/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean cerrar_ingresoPacking(@RequestBody Packing_Frigorifico row,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}	
		return PackingFrigorifico.CERRAR_INGRESO_PACKING(row);
	}
	@RequestMapping(value = "/AGRO/getEvaporacionByCampo/{campo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<evaporacion> getEvaporacionByCampo(@PathVariable String campo, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<evaporacion> r = new ArrayList<evaporacion>();
		if (ses.isValid()) {
			return r;
		}
		r = RIEGO.getEvaporacionByCampo(campo);
		return r;
	}
	@RequestMapping(value = "/AGRO/getEvaporacionAcumulada/{campo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<evaporacionAcumulada> getEvaporacionAcumulada(@PathVariable String campo, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<evaporacionAcumulada> r = new ArrayList<evaporacionAcumulada>();
		if (ses.isValid()) {
			return r;
		}
		r = RIEGO.getEvaporacionAcumulada(campo);
		return r;
	}
	@RequestMapping(value = "/AGRO/UPEVAPORACIONACUMULADA/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPEVAPORACIONACUMULADA(@RequestBody  evaporacionAcumulada row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RIEGO.UPEVAPORACIONACUMULADA(row);
	}
	@RequestMapping(value = "/AGRO/getRiegosByCampo/{campo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<riegos> getRiegosByCampo(@PathVariable String campo, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<riegos> r = new ArrayList<riegos>();
		if (ses.isValid()) {
			return r;
		}
		r = RIEGO.getRiegosByCampo(campo);
		return r;
	}
	@RequestMapping(value = "/AGRO/updateEstadoRiego/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateEstadoVariedades(@RequestBody riegos row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RIEGO.updateEstadoRiego(row);
	}
	@RequestMapping(value = "/AGRO/GET_CUARTELES_BLOQUE", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<CUARTEL_PF> GET_CUARTELES_BLOQUE(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<CUARTEL_PF> r = new ArrayList<CUARTEL_PF>();
//		if (ses.isValid()) {
//			return "0";
//		}
		int codigo = Integer.parseInt(request.getParameter("BLOQUE"));
		System.out.println(codigo);
		r = RIEGO.GET_CUARTELES_BLOQUE(codigo);
		return 	r;
	}
	@RequestMapping(value = "/AGRO/GET_EVAPORACION", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<evaporacionPrd> GET_EVAPORACION(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<evaporacionPrd> r = new ArrayList<evaporacionPrd>();
		if (ses.isValid()) {
			return r;
		}
		String campo = request.getParameter("CAMPO");
		r = RIEGO.GET_EVAPORACION(campo);
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_EVAPORACION", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<respuesta> ADD_EVAPORACION(@RequestBody  ArrayList<evaporacionPrd> row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<respuesta> res = new ArrayList<respuesta>();
		if (ses.isValid()) {
			return res;
		}
		for(evaporacionPrd r: row){
			ConnectionDB db = new ConnectionDB();
			respuesta e = new respuesta();
			e = RIEGO.ADD_EVAPORACION(db, r);
			res.add(e);
		}
		return res;
	}
	@RequestMapping(value = "/AGRO/GET_MES_FACTOR", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_MES_FACTOR(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return r;
		}
		int ano = Integer.parseInt(request.getParameter("ANO"));
		int semana = Integer.parseInt(request.getParameter("SEMANA"));
		r = RIEGO.GET_MES_FACTOR(ano, semana);
		System.out.println(r);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_RIEGO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<programa_riego> GET_RIEGO(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<programa_riego> r = new ArrayList<programa_riego>();
		if (ses.isValid()) {
			return r;
		}
		String campo = request.getParameter("CAMPO");
		int semana = Integer.parseInt(request.getParameter("SEMANA"));
		r = RIEGO.GET_RIEGO(campo, semana);
		
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_RIEGO", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_RIEGO(@RequestBody  programa_riego row,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}	
		return RIEGO.ADD_RIEGO(row); 
	}
	@RequestMapping(value = "/AGRO/GET_DIAS_SEMANA", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<String> GET_DIAS_SEMANA(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<String> r = new ArrayList<String>();
		if (ses.isValid()) {
			return r;
		}
		int semana = Integer.parseInt(request.getParameter("SEMANA"));
		r = RIEGO.GET_DIAS_SEMANA(semana);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_REPORTE_RIEGO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<String[]> GET_REPORTE_RIEGO(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<String[]> r = new ArrayList<String[]>();
		if (ses.isValid()) {
			return r;
		}
		String campo = request.getParameter("CAMPO");
		int semana = Integer.parseInt(request.getParameter("SEMANA"));
		r = RIEGO.GET_REPORTE_RIEGO(campo, semana);
		return r;
	}
}
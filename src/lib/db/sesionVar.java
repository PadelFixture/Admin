package lib.db;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import lib.jsonMap.jsonSesionVars;
import lib.struc.M_FORMA_APLICACION;
import lib.struc.M_MERCADO;
import lib.struc.TIPO_PAGO;
import lib.struc.mapConection;

public class sesionVar {
	private HttpSession sesion;
	jsonSesionVars gb;
	
	public sesionVar(HttpSession httpSession) {
		sesion = httpSession;
		if (sesion.getAttribute("GLOBALS") == null) {
			gb = new jsonSesionVars();
			ArrayList<mapConection> map = new ArrayList<mapConection>();
			ArrayList<M_FORMA_APLICACION> mfa = new ArrayList<M_FORMA_APLICACION>();
			ArrayList<M_MERCADO> m = new ArrayList<M_MERCADO>();
			ArrayList<TIPO_PAGO> tp = new ArrayList<TIPO_PAGO>();
			gb.setMAPCONECTION(map);
			gb.setM_FORMA_APLICACION(mfa);
			gb.setM_MERCADO(m);
			gb.setTIPO_PAGO(tp);
		} else{
			gb = (jsonSesionVars) sesion.getAttribute("GLOBALS");
		}
	}
	public void addCampo(mapConection row,int idCampo)throws Exception{
		if(!campoExist(idCampo)){
			mapConection e = new mapConection();
			e.setIdtest(row.getIdtest());
			e.setCoordenada(row.getCoordenada());
			e.setValor1(row.getValor1());
			e.setColor(row.getColor());
			e.setEspecie(row.getEspecie());
			e.setVariedad(row.getVariedad());
			e.setHectarias(row.getHectarias());
			e.setCantidad(row.getCantidad());
			e.setPlantacion(row.getPlantacion());
			e.setStatus(row.getStatus());
			gb.getMAPCONECTION().add(e);
		}
	}
	public void addMFormaAplicacion(ArrayList<M_FORMA_APLICACION> mfa){
		gb.getM_FORMA_APLICACION().addAll(mfa);
	}
	public void addMercado(ArrayList<M_MERCADO> mercado){
		gb.getM_MERCADO().addAll(mercado);
	}
	public void addTipoPago(ArrayList<TIPO_PAGO> tipo_pago){
		gb.getTIPO_PAGO().addAll(tipo_pago);
	}
	private boolean campoExist(int id) {
		boolean sw = false;
		for(mapConection row: gb.getMAPCONECTION()){
			if(row.idtest == id){
				sw = true;
			}
		}
		return sw;
	}
	public jsonSesionVars getCampo(){
		return gb;
	}
	public void saveCampo() {
		sesion.setAttribute("GLOBALS", gb);
	}
}

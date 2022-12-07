package lib.jsonMap;

import java.util.ArrayList;
import java.util.List;

import lib.struc.M_FORMA_APLICACION;
import lib.struc.mapConection;
import lib.struc.M_MERCADO;
import lib.struc.TIPO_PAGO;

public class jsonSesionVars {
	public ArrayList<mapConection> MAPCONECTION;
	public ArrayList<M_FORMA_APLICACION> M_FORMA_APLICACION;
	public ArrayList<M_MERCADO> M_MERCADO;
	public ArrayList<TIPO_PAGO> TIPO_PAGO;
	public ArrayList<mapConection> getMAPCONECTION() {
		return MAPCONECTION;
	}
	public void setMAPCONECTION(ArrayList<mapConection> mAPCONECTION) {
		MAPCONECTION = mAPCONECTION;
	}
	public ArrayList<M_FORMA_APLICACION> getM_FORMA_APLICACION() {
		return M_FORMA_APLICACION;
	}
	public void setM_FORMA_APLICACION(ArrayList<M_FORMA_APLICACION> m_FORMA_APLICACION) {
		M_FORMA_APLICACION = m_FORMA_APLICACION;
	}
	public ArrayList<M_MERCADO> getM_MERCADO() {
		return M_MERCADO;
	}
	public void setM_MERCADO(ArrayList<M_MERCADO> m_MERCADO) {
		M_MERCADO = m_MERCADO;
	}
	public ArrayList<TIPO_PAGO> getTIPO_PAGO() {
		return TIPO_PAGO;
	}
	public void setTIPO_PAGO(ArrayList<TIPO_PAGO> tIPO_PAGO) {
		TIPO_PAGO = tIPO_PAGO;
	}
} 

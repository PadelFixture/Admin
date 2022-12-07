package lib.classSW;

import java.util.ArrayList;

public class CentraObject {
	String BUS_ACT;
	String TIPO_DOC;
	String TEXTO;
	String USUARIO;
	String SOCIEDAD;
	int FECHA;
	int ANOFISCAL;
	String REFERENCIA;
	ArrayList<CuentaCentra> CUENTAS;
	public String getBUS_ACT() {
		return BUS_ACT;
	}
	public void setBUS_ACT(String bUS_ACT) {
		BUS_ACT = bUS_ACT;
	}
	public String getTIPO_DOC() {
		return TIPO_DOC;
	}
	public void setTIPO_DOC(String tIPO_DOC) {
		TIPO_DOC = tIPO_DOC;
	}
	public String getTEXTO() {
		return TEXTO;
	}
	public void setTEXTO(String tEXTO) {
		TEXTO = tEXTO;
	}
	public String getUSUARIO() {
		return USUARIO;
	}
	public void setUSUARIO(String uSUARIO) {
		USUARIO = uSUARIO;
	}
	public String getSOCIEDAD() {
		return SOCIEDAD;
	}
	public void setSOCIEDAD(String sOCIEDAD) {
		SOCIEDAD = sOCIEDAD;
	}
	public int getFECHA() {
		return FECHA;
	}
	public void setFECHA(int fECHA) {
		FECHA = fECHA;
	}
	public int getANOFISCAL() {
		return ANOFISCAL;
	}
	public void setANOFISCAL(int aNOFISCAL) {
		ANOFISCAL = aNOFISCAL;
	}
	public String getREFERENCIA() {
		return REFERENCIA;
	}
	public void setREFERENCIA(String rEFERENCIA) {
		REFERENCIA = rEFERENCIA;
	}
	public ArrayList<CuentaCentra> getCUENTAS() {
		return CUENTAS;
	}
	public void setCUENTAS(ArrayList<CuentaCentra> cUENTAS) {
		CUENTAS = cUENTAS;
	}	
	
}

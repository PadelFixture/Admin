package lib.classSW;
import java.math.BigDecimal;
import java.sql.Time;
public class Turno {
	int idTurno;
	String nombreTurno;
	String sociedad;
	String huerto;
	String zona;
	String ceco;
	
	String descripcionTurno;
	int jornadaTurno;
	String nombreJornada;
	BigDecimal horasTurno;
	BigDecimal lunesTurno;
	BigDecimal martesTurno;
	BigDecimal miercolesTurno;
	BigDecimal juevesTurno;
	BigDecimal viernesTurno;
	BigDecimal sabadoTurno;
	BigDecimal domingoTurno;
	Time lunesAI;
	Time lunesAF;
	Time martesAI;
	Time martesAF;
	Time miercolesAI;
	Time miercolesAF;
	Time juevesAI;
	Time juevesAF;
	Time viernesAI;
	Time viernesAF;
	Time sabadoAI;
	Time sabadoAF;
	Time domingoAI;
	Time domingoAF;
	Time lunesPI;
	Time lunesPF;
	Time martesPI;
	Time martesPF;
	Time miercolesPI;
	Time miercolesPF;
	Time juevesPI;
	Time juevesPF;
	Time viernesPI;
	Time viernesPF;
	Time sabadoPI;
	Time sabadoPF;
	Time domingoPI;
	Time domingoPF;
	
	
	
	public String getSociedad() {
		return sociedad;
	}
	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}
	public String getHuerto() {
		return huerto;
	}
	public void setHuerto(String huerto) {
		this.huerto = huerto;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public String getCeco() {
		return ceco;
	}
	public void setCeco(String ceco) {
		this.ceco = ceco;
	}
	public Time getLunesPI() {
		return lunesPI;
	}
	public void setLunesPI(Time lunesPI) {
		this.lunesPI = lunesPI;
	}
	public Time getLunesPF() {
		return lunesPF;
	}
	public void setLunesPF(Time lunesPF) {
		this.lunesPF = lunesPF;
	}
	public Time getMartesPI() {
		return martesPI;
	}
	public void setMartesPI(Time martesPI) {
		this.martesPI = martesPI;
	}
	public Time getMartesPF() {
		return martesPF;
	}
	public void setMartesPF(Time martesPF) {
		this.martesPF = martesPF;
	}
	public Time getMiercolesPI() {
		return miercolesPI;
	}
	public void setMiercolesPI(Time miercolesPI) {
		this.miercolesPI = miercolesPI;
	}
	public Time getMiercolesPF() {
		return miercolesPF;
	}
	public void setMiercolesPF(Time miercolesPF) {
		this.miercolesPF = miercolesPF;
	}
	public Time getJuevesPI() {
		return juevesPI;
	}
	public void setJuevesPI(Time juevesPI) {
		this.juevesPI = juevesPI;
	}
	public Time getJuevesPF() {
		return juevesPF;
	}
	public void setJuevesPF(Time juevesPF) {
		this.juevesPF = juevesPF;
	}
	public Time getViernesPI() {
		return viernesPI;
	}
	public void setViernesPI(Time viernesPI) {
		this.viernesPI = viernesPI;
	}
	public Time getViernesPF() {
		return viernesPF;
	}
	public void setViernesPF(Time viernesPF) {
		this.viernesPF = viernesPF;
	}
	public Time getSabadoPI() {
		return sabadoPI;
	}
	public void setSabadoPI(Time sabadoPI) {
		this.sabadoPI = sabadoPI;
	}
	public Time getSabadoPF() {
		return sabadoPF;
	}
	public void setSabadoPF(Time sabadoPF) {
		this.sabadoPF = sabadoPF;
	}
	public Time getDomingoPI() {
		return domingoPI;
	}
	public void setDomingoPI(Time domingoPI) {
		this.domingoPI = domingoPI;
	}
	public Time getDomingoPF() {
		return domingoPF;
	}
	public void setDomingoPF(Time domingoPF) {
		this.domingoPF = domingoPF;
	}
	public String getNombreJornada() {
		return nombreJornada;
	}
	public void setNombreJornada(String nombreJornada) {
		this.nombreJornada = nombreJornada;
	}
	
	public int getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(int idTurno) {
		this.idTurno = idTurno;
	}
	public String getNombreTurno() {
		return nombreTurno;
	}
	public void setNombreTurno(String nombreTurno) {
		this.nombreTurno = nombreTurno;
	}
	public String getDescripcionTurno() {
		return descripcionTurno;
	}
	public void setDescripcionTurno(String descripcionTurno) {
		this.descripcionTurno = descripcionTurno;
	}
	public int getJornadaTurno() {
		return jornadaTurno;
	}
	public void setJornadaTurno(int jornadaTurno) {
		this.jornadaTurno = jornadaTurno;
	}
	public BigDecimal getHorasTurno() {
		return horasTurno;
	}
	public void setHorasTurno(BigDecimal horasTurno) {
		this.horasTurno = horasTurno;
	}
	public BigDecimal getLunesTurno() {
		return lunesTurno;
	}
	public void setLunesTurno(BigDecimal lunesTurno) {
		this.lunesTurno = lunesTurno;
	}
	public BigDecimal getMartesTurno() {
		return martesTurno;
	}
	public void setMartesTurno(BigDecimal martesTurno) {
		this.martesTurno = martesTurno;
	}
	public BigDecimal getMiercolesTurno() {
		return miercolesTurno;
	}
	public void setMiercolesTurno(BigDecimal miercolesTurno) {
		this.miercolesTurno = miercolesTurno;
	}
	public BigDecimal getJuevesTurno() {
		return juevesTurno;
	}
	public void setJuevesTurno(BigDecimal juevesTurno) {
		this.juevesTurno = juevesTurno;
	}
	public BigDecimal getViernesTurno() {
		return viernesTurno;
	}
	public void setViernesTurno(BigDecimal viernesTurno) {
		this.viernesTurno = viernesTurno;
	}
	public BigDecimal getSabadoTurno() {
		return sabadoTurno;
	}
	public void setSabadoTurno(BigDecimal sabadoTurno) {
		this.sabadoTurno = sabadoTurno;
	}
	public BigDecimal getDomingoTurno() {
		return domingoTurno;
	}
	public void setDomingoTurno(BigDecimal domingoTurno) {
		this.domingoTurno = domingoTurno;
	}
	public Time getLunesAI() {
		return lunesAI;
	}
	public void setLunesAI(Time lunesAI) {
		this.lunesAI = lunesAI;
	}
	public Time getLunesAF() {
		return lunesAF;
	}
	public void setLunesAF(Time lunesAF) {
		this.lunesAF = lunesAF;
	}
	public Time getMartesAI() {
		return martesAI;
	}
	public void setMartesAI(Time martesAI) {
		this.martesAI = martesAI;
	}
	public Time getMartesAF() {
		return martesAF;
	}
	public void setMartesAF(Time martesAF) {
		this.martesAF = martesAF;
	}
	public Time getMiercolesAI() {
		return miercolesAI;
	}
	public void setMiercolesAI(Time miercolesAI) {
		this.miercolesAI = miercolesAI;
	}
	public Time getMiercolesAF() {
		return miercolesAF;
	}
	public void setMiercolesAF(Time miercolesAF) {
		this.miercolesAF = miercolesAF;
	}
	public Time getJuevesAI() {
		return juevesAI;
	}
	public void setJuevesAI(Time juevesAI) {
		this.juevesAI = juevesAI;
	}
	public Time getJuevesAF() {
		return juevesAF;
	}
	public void setJuevesAF(Time juevesAF) {
		this.juevesAF = juevesAF;
	}
	public Time getViernesAI() {
		return viernesAI;
	}
	public void setViernesAI(Time viernesAI) {
		this.viernesAI = viernesAI;
	}
	public Time getViernesAF() {
		return viernesAF;
	}
	public void setViernesAF(Time viernesAF) {
		this.viernesAF = viernesAF;
	}
	public Time getSabadoAI() {
		return sabadoAI;
	}
	public void setSabadoAI(Time sabadoAI) {
		this.sabadoAI = sabadoAI;
	}
	public Time getSabadoAF() {
		return sabadoAF;
	}
	public void setSabadoAF(Time sabadoAF) {
		this.sabadoAF = sabadoAF;
	}
	public Time getDomingoAI() {
		return domingoAI;
	}
	public void setDomingoAI(Time domingoAI) {
		this.domingoAI = domingoAI;
	}
	public Time getDomingoAF() {
		return domingoAF;
	}
	public void setDomingoAF(Time domingoAF) {
		this.domingoAF = domingoAF;
	}
	
}

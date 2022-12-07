package lib.classSA;

import java.util.ArrayList;


public class SESIONVAR {
	public int idUser;
	public String user;
	public String pass;
	public ArrayList<CAMPO> campo;
	public ArrayList<SECTOR> sector;
	public ArrayList<ESPECIE> especie;
	public ArrayList<VARIEDAD> variedad;
	public ArrayList<incidencia> incidencia;
	public ArrayList<CUARTEL> cuartel;
	public ArrayList<bloqueo_periodo> bloqueo;
	public String grupoCompra;
	public String solicitante;
	private int rolPrivado;
	private int eliminar;
	private int ver;
	private int editar;
	
	
	

	
	public ArrayList<bloqueo_periodo> getBloqueo() {
		return bloqueo;
	}
	public void setBloqueo(ArrayList<bloqueo_periodo> bloqueo) {
		this.bloqueo = bloqueo;
	}
	public String getGrupoCompra() {
		return grupoCompra;
	}
	public void setGrupoCompra(String grupoCompra) {
		this.grupoCompra = grupoCompra;
	}
	public String getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public ArrayList<incidencia> getIncidencia() {
		return incidencia;
	}
	public void setIncidencia(ArrayList<incidencia> incidencia) {
		this.incidencia = incidencia;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public ArrayList<CAMPO> getCampo() {
		return campo;
	}
	public ArrayList<ESPECIE> getEspecie() {
		return especie;
	}
	public void setEspecie(ArrayList<ESPECIE> especie) {
		this.especie = especie;
	}
	public ArrayList<VARIEDAD> getVariedad() {
		return variedad;
	}
	public void setVariedad(ArrayList<VARIEDAD> variedad) {
		this.variedad = variedad;
	}
	public void setCampo(ArrayList<CAMPO> campo) {
		this.campo = campo;
	}
	public ArrayList<SECTOR> getSector() {
		return sector;
	}
	public void setSector(ArrayList<SECTOR> sector) {
		this.sector = sector;
	}
	public ArrayList<CUARTEL> getCuartel() {
		return cuartel;
	}
	public void setCuartel(ArrayList<CUARTEL> cuartel) {
		this.cuartel = cuartel;
	}
	public int getRolPrivado() {
		return rolPrivado;
	}
	public void setRolPrivado(int rolPrivado) {
		this.rolPrivado = rolPrivado;
	}
	public int getEliminar() {
		return eliminar;
	}
	public void setEliminar(int eliminar) {
		this.eliminar = eliminar;
	}
	public int getVer() {
		return ver;
	}
	public void setVer(int ver) {
		this.ver = ver;
	}
	public int getEditar() {
		return editar;
	}
	public void setEditar(int editar) {
		this.editar = editar;
	}
	
	
}
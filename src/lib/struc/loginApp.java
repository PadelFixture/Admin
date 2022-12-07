package lib.struc;

public class loginApp {
	public int id;
	public String usuario;
	public String pass;
	public int perfil;
	public String perfilText;
	public String grupoCompra;
	public String solicitante;
	private int club;
	
	
	
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	public int getPerfil() {
		return perfil;
	}
	public void setPerfil(int perfil) {
		this.perfil = perfil;
	}
	public String getPerfilText() {
		return perfilText;
	}
	public void setPerfilText(String perfilText) {
		this.perfilText = perfilText;
	}

	public int getClub() {
		return club;
	}
	public void setClub(int club) {
		this.club = club;
	}
	
	
}

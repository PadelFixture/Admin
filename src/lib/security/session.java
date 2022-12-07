package lib.security;

import javax.servlet.http.HttpSession;


public class session {

	private HttpSession session;
	
	private int idUser;
	private int idPerfil;
	private String nombre;
	private String grupoCompra;
	private String solicitante;
	private int rolPrivado;
	private int eliminar;
	private int ver;
	private int editar;
	
	
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
	public int getIdUser()
	{
		int value = (int) session.getAttribute("idUser");
		return value;
	}
	public void setIdUser(int value)
	{
		session.setAttribute("idUser", value);
	}
	public int getIdPerfil()
	{
		int value = (int) session.getAttribute("idPerfil");
		return value;
	}
	public void setIdPerfil(int value)
	{
		session.setAttribute("idPerfil", value);
	}
	
	public String getNombre()
	{
		String value = (String) session.getAttribute("nombre");
		return value;
	}
	public void setNombre(String value)
	{
		session.setAttribute("nombre", value);
	}
	
	
	//////////////////////////////////////////////////////////////
	
	public session(HttpSession httpSession) {
		session = httpSession;
		
	}

	public void init() {
		session.setMaxInactiveInterval(60*60);
		session.setAttribute("login", "ok");
	}
	public void setValue(String key,String value)
	{
		session.setAttribute(key, value);
		
	}
	
	public String getValue(String key)
	{
		String value = (String) session.getAttribute(key);
		if (value == null) {
			value="";
			
		}

		return value;
	}
	

	public boolean isValid() {
		boolean sw = true;
		String login = (String) session.getAttribute("login");

		if (login != null) {
			sw = false;
			
		}

		return sw;
	}

	public void close() {
		session.invalidate();
	}
	
	public int getRolPrivado()
	{
		int value = (int) session.getAttribute("rolPrivado");
		return value;
	}
	public void setRolPrivado(int value)
	{
		session.setAttribute("rolPrivado", value);
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
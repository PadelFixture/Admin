package lib.classSA;

public class estimacion_data_prd extends estimacion_prd_dias{
	public int id;
	public String campo;
	public int especie;
	public int variedad;
	public int semana;
	public int ano;
	public int usuario;
	public int usuarioupd;
	private int codigo_semana;
	private int codigo_21;
	
	
	
	
	public int getCodigo_semana() {
		return codigo_semana;
	}
	public void setCodigo_semana(int codigo_semana) {
		this.codigo_semana = codigo_semana;
	}
	public int getCodigo_21() {
		return codigo_21;
	}
	public void setCodigo_21(int codigo_21) {
		this.codigo_21 = codigo_21;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public int getEspecie() {
		return especie;
	}
	public void setEspecie(int especie) {
		this.especie = especie;
	}
	public int getVariedad() {
		return variedad;
	}
	public void setVariedad(int variedad) {
		this.variedad = variedad;
	}
	public int getSemana() {
		return semana;
	}
	public void setSemana(int semana) {
		this.semana = semana;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public int getUsuario() {
		return usuario;
	}
	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}
	public int getUsuarioupd() {
		return usuarioupd;
	}
	public void setUsuarioupd(int usuarioupd) {
		this.usuarioupd = usuarioupd;
	}
}

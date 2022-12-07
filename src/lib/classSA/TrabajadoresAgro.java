package lib.classSA;

public class TrabajadoresAgro {
	
	private String codigo;
	public int idTrabajador;
	private String rut;
	private String nombre;
	private String apellidoPaterno;
	private int idSociedad;
	private int    agro;
	private double sueldoBase;
	private double sueldoDiario;
	private int jornadaSemanal;
	private double jornadaDiaria;
	private int cargo;
	private int tipoTrabajador;
	private String descripcionTipoTrabajador;
	private String calculoJornadaSemanal;
	private double horas_restantes;
	private String idContratista;
	public float hx_semana;
	public double hx;
	public int permiso;
	
	
	
	public int getPermiso() {
		return permiso;
	}
	public void setPermiso(int permiso) {
		this.permiso = permiso;
	}
	public double getHx() {
		return hx;
	}
	public void setHx(double d) {
		this.hx = d;
	}
	public float getHx_semana() {
		return hx_semana;
	}
	public void setHx_semana(float hx_semana) {
		this.hx_semana = hx_semana;
	}
	public String getIdContratista() {
		return idContratista;
	}
	public void setIdContratista(String idContratista) {
		this.idContratista = idContratista;
	}
	public double getHoras_restantes() {
		return horas_restantes;
	}
	public void setHoras_restantes(double horas_restantes) {
		this.horas_restantes = horas_restantes;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public int getIdTrabajador() {
		return idTrabajador;
	}
	public void setIdTrabajador(int idTrabajador) {
		this.idTrabajador = idTrabajador;
	}
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public int getIdSociedad() {
		return idSociedad;
	}
	public void setIdSociedad(int idSociedad) {
		this.idSociedad = idSociedad;
	}
	public int getAgro() {
		return agro;
	}
	public void setAgro(int agro) {
		this.agro = agro;
	}
	public double getSueldoBase() {
		return sueldoBase;
	}
	public void setSueldoBase(double sueldoBase) {
		this.sueldoBase = sueldoBase;
	}
	public double getSueldoDiario() {
		return sueldoDiario;
	}
	public void setSueldoDiario(double sueldoDiario) {
		this.sueldoDiario = sueldoDiario;
	}
	public int getJornadaSemanal() {
		return jornadaSemanal;
	}
	public void setJornadaSemanal(int jornadaSemanal) {
		this.jornadaSemanal = jornadaSemanal;
	}
	public double getJornadaDiaria() {
		return jornadaDiaria;
	}
	public void setJornadaDiaria(double jornadaDiaria) {
		this.jornadaDiaria = jornadaDiaria;
	}
	public int getCargo() {
		return cargo;
	}
	public void setCargo(int cargo) {
		this.cargo = cargo;
	}
	public int getTipoTrabajador() {
		return tipoTrabajador;
	}
	public void setTipoTrabajador(int tipoTrabajador) {
		this.tipoTrabajador = tipoTrabajador;
	}
	public String getDescripcionTipoTrabajador() {
		return descripcionTipoTrabajador;
	}
	public void setDescripcionTipoTrabajador(String descripcionTipoTrabajador) {
		this.descripcionTipoTrabajador = descripcionTipoTrabajador;
	}
	public String getCalculoJornadaSemanal() {
		return calculoJornadaSemanal;
	}
	public void setCalculoJornadaSemanal(String calculoJornadaSemanal) {
		this.calculoJornadaSemanal = calculoJornadaSemanal;
	}
	@Override
	public String toString() {
		return "{codigo:" + codigo + ", idTrabajador:" + idTrabajador + ", rut:'" + rut + "', nombre:'"
				+ nombre + "', apellidoPaterno:'" + apellidoPaterno + "', idSociedad:" + idSociedad + ", agro:" + agro
				+ ", sueldoBase:" + sueldoBase + ", sueldoDiario:" + sueldoDiario + ", jornadaSemanal:" + jornadaSemanal
				+ ", jornadaDiaria:" + jornadaDiaria + ", cargo:" + cargo + ", tipoTrabajador:" + tipoTrabajador
				+ ", descripcionTipoTrabajador:'" + descripcionTipoTrabajador + "', calculoJornadaSemanal:"
				+ calculoJornadaSemanal + ", horas_restantes:" + horas_restantes + ", idContratista:" + idContratista
				+ ", hx_semana:" + hx_semana + ", hx:" + hx + "}";
	}
	
	
	
	
	

}

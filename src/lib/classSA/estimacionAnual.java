package lib.classSA;

import java.util.ArrayList;

public class estimacionAnual extends estimacion_prd_dias {

	private int codigo;
	private String productor;
	private String nproductor;
	private String especie;
	private int variedad;
	private String nvariedad;
	private int version;
	private int temporada;
	private int usuario;
	private String fecha;
	private ArrayList<estimacion_semana> estimacion_semana;
	private ArrayList<EstimacionIngreso> estimacion_ingreso;
	private ArrayList<estimacion_calibre_categoria> estimacion_calibre_categoria;
	private int s1;
	private int s2;
	private int s3;
	private int se;
	
	private int semana;
	private float exportacion;
	private float valor;
	private String categoria;
	private String calibre;
	
	
	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getCalibre() {
		return calibre;
	}
	public void setCalibre(String calibre) {
		this.calibre = calibre;
	}
	public int getSemana() {
		return semana;
	}
	public void setSemana(int semana) {
		this.semana = semana;
	}
	public float getExportacion() {
		return exportacion;
	}
	public void setExportacion(float exportacion) {
		this.exportacion = exportacion;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public String getNproductor() {
		return nproductor;
	}
	public void setNproductor(String nproductor) {
		this.nproductor = nproductor;
	}
	
	
	public int getSe() {
		return se;
	}
	public void setSe(int se) {
		this.se = se;
	}
	public int getS1() {
		return s1;
	}
	public void setS1(int s1) {
		this.s1 = s1;
	}
	public int getS2() {
		return s2;
	}
	public void setS2(int s2) {
		this.s2 = s2;
	}
	public int getS3() {
		return s3;
	}
	public void setS3(int s3) {
		this.s3 = s3;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public ArrayList<EstimacionIngreso> getEstimacion_ingreso() {
		return estimacion_ingreso;
	}
	public void setEstimacion_ingreso(ArrayList<EstimacionIngreso> estimacion_ingreso) {
		this.estimacion_ingreso = estimacion_ingreso;
	}
	public ArrayList<estimacion_calibre_categoria> getEstimacion_calibre_categoria() {
		return estimacion_calibre_categoria;
	}
	public void setEstimacion_calibre_categoria(ArrayList<estimacion_calibre_categoria> estimacion_calibre_categoria) {
		this.estimacion_calibre_categoria = estimacion_calibre_categoria;
	}
	
	
	public ArrayList<estimacion_semana> getEstimacion_semana() {
		return estimacion_semana;
	}
	public void setEstimacion_semana(ArrayList<estimacion_semana> estimacion_semana) {
		this.estimacion_semana = estimacion_semana;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getProductor() {
		return productor;
	}
	public void setProductor(String productor) {
		this.productor = productor;
	}
	public String getEspecie() {
		return especie;
	}
	public void setEspecie(String especie) {
		this.especie = especie;
	}
	public int getVariedad() {
		return variedad;
	}
	public void setVariedad(int variedad) {
		this.variedad = variedad;
	}
	public String getNvariedad() {
		return nvariedad;
	}
	public void setNvariedad(String nvariedad) {
		this.nvariedad = nvariedad;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getTemporada() {
		return temporada;
	}
	public void setTemporada(int temporada) {
		this.temporada = temporada;
	}
	public int getUsuario() {
		return usuario;
	}
	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}
	
	
	
}

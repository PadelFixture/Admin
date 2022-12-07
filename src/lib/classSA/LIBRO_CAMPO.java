package lib.classSA;

import java.util.ArrayList;

public class LIBRO_CAMPO extends PROGRA_FITOSANITARIO  {
	
	public int codigo_pf;
	public int idPrograma;
	public int idOrden;
	public String fecha_inicio;
	public String fecha_termino;
	public String nombre_cuartel;
	public String has;
	public String has_real;
	public String codigo_material;
	public double dosis_100;
	public double dosis_has;
	public String forma_apliacion;
	public String aplicador;
	public String jefe_aplicacion;
	public String maquinaria;
	public String implemento;
	public String fecha_estimada_cosecha;
	public String mercado;
	private double cantidad_real;
	private ArrayList<MAQUINARIA_PF> maq_PF;
	private String fecha_viable_cosecha;
	private MATERIAL_PF detalleMaterial;
	public String um;
	public String arrUm;
	public String arrMaterial;
	public String inActivo;
	public String arrReingreso;
	
	
	
	
	public String getUm() {
		return um;
	}
	public void setUm(String um) {
		this.um = um;
	}
	public String getArrUm() {
		return arrUm;
	}
	public void setArrUm(String arrUm) {
		this.arrUm = arrUm;
	}
	public String getArrMaterial() {
		return arrMaterial;
	}
	public void setArrMaterial(String arrMaterial) {
		this.arrMaterial = arrMaterial;
	}
	public String getInActivo() {
		return inActivo;
	}
	public void setInActivo(String inActivo) {
		this.inActivo = inActivo;
	}
	public String getArrReingreso() {
		return arrReingreso;
	}
	public void setArrReingreso(String arrReingreso) {
		this.arrReingreso = arrReingreso;
	}
	public MATERIAL_PF getDetalleMaterial() {
		return detalleMaterial;
	}
	public void setDetalleMaterial(MATERIAL_PF detalleMaterial) {
		this.detalleMaterial = detalleMaterial;
	}
	public String getFecha_viable_cosecha() {
		return fecha_viable_cosecha;
	}
	public void setFecha_viable_cosecha(String fecha_viable_cosecha) {
		this.fecha_viable_cosecha = fecha_viable_cosecha;
	}
	public int getCodigo_pf() {
		return codigo_pf;
	}
	public void setCodigo_pf(int codigo_pf) {
		this.codigo_pf = codigo_pf;
	}
	public ArrayList<MAQUINARIA_PF> getMaq_PF() {
		return maq_PF;
	}
	public void setMaq_PF(ArrayList<MAQUINARIA_PF> maq_PF) {
		this.maq_PF = maq_PF;
	}
	public double getCantidad_real() {
		return cantidad_real;
	}
	public void setCantidad_real(double cantidad_real) {
		this.cantidad_real = cantidad_real;
	}
	public String getMercado() {
		return mercado;
	}
	public void setMercado(String mercado) {
		this.mercado = mercado;
	}
	public String getAplicador() {
		return aplicador;
	}
	public void setAplicador(String aplicador) {
		this.aplicador = aplicador;
	}
	public String getJefe_aplicacion() {
		return jefe_aplicacion;
	}
	public void setJefe_aplicacion(String jefe_aplicacion) {
		this.jefe_aplicacion = jefe_aplicacion;
	}
	public String getMaquinaria() {
		return maquinaria;
	}
	public void setMaquinaria(String maquinaria) {
		this.maquinaria = maquinaria;
	}
	public String getImplemento() {
		return implemento;
	}
	public void setImplemento(String implemento) {
		this.implemento = implemento;
	}
	public String getFecha_estimada_cosecha() {
		return fecha_estimada_cosecha;
	}
	public void setFecha_estimada_cosecha(String fecha_estimada_cosecha) {
		this.fecha_estimada_cosecha = fecha_estimada_cosecha;
	}
	public String getForma_apliacion() {
		return forma_apliacion;
	}
	public void setForma_apliacion(String forma_apliacion) {
		this.forma_apliacion = forma_apliacion;
	}
	public double getDosis_100() {
		return dosis_100;
	}
	public void setDosis_100(double dosis_100) {
		this.dosis_100 = dosis_100;
	}
	public double getDosis_has() {
		return dosis_has;
	}
	public void setDosis_has(double dosis_has) {
		this.dosis_has = dosis_has;
	}
	public String getCodigo_material() {
		return codigo_material;
	}
	public void setCodigo_material(String codigo_material) {
		this.codigo_material = codigo_material;
	}
	public String getHas_real() {
		return has_real;
	}
	public void setHas_real(String has_real) {
		this.has_real = has_real;
	}
	public String getHas() {
		return has;
	}
	public void setHas(String has) {
		this.has = has;
	}
	public String getNombre_cuartel() {
		return nombre_cuartel;
	}
	public void setNombre_cuartel(String nombre_cuartel) {
		this.nombre_cuartel = nombre_cuartel;
	}
	public String getFecha_inicio() {
		return fecha_inicio;
	}
	public void setFecha_inicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}
	public String getFecha_termino() {
		return fecha_termino;
	}
	public void setFecha_termino(String fecha_termino) {
		this.fecha_termino = fecha_termino;
	}
	public int getIdPrograma() {
		return idPrograma;
	}
	public void setIdPrograma(int idPrograma) {
		this.idPrograma = idPrograma;
	}
	public int getIdOrden() {
		return idOrden;
	}
	public void setIdOrden(int idOrden) {
		this.idOrden = idOrden;
	}
	
	

}

package lib.classSA;

import java.sql.Date;
import java.util.ArrayList;

public class LISTA_APLICACIONES_OBJ {
	public int numero_orden;
	public int idorden;
	public int idPrograma;
	public int codigo;
	public String nombre_aplicador;
	public Date fecha_estimada_aplicacion;
	public String especie;
	public String codEspecie;
	public String variedad;
	public String estado_fenologico;
	public Date fecha_estimada_cosecha;
	public String mercado;
	public String forma_aplicacion;
	public String nforma_aplicacion;
	public String maquinaria;	
	public String implemento;
	public int idProgramaAplicacion;
	public String programa_aplicacion;
	public String tipo_control;
	public ArrayList<MATERIAL_PF> lista_materiales;
	public ArrayList<CUARTEL_PF> lista_cuarteles;
	public ArrayList<MAQUINARIA_PF> lista_maquinaria;
	public String Observacion;
	public int mojamiento;
	public String presion_bomba;
	public String cambio_tractor;
	public String marcha_tractor;
	public int tipo_programa;
	public String campo;
	public String codCampo;
	public String jefe_aplicacion;
	public String nreserva;
	public String estado;
	public String fecha_viable;
	public String codMercado;
	public String solped;
	public String um;
	public double capacidad_maquina;
	public String campos_maq;
	public double mojamiento_real;
	public String codEstadoFenologico;
	public String codProgramaAplicacion;
	public String codControl;
	public String codJefeAplicacion;
	public String codNombreAplicador;
	public String codLibro;
	public String codFormaAplicacion;
	public String adm_campo;
	private String velocidad_viento;
	private String orientacion_viento;
	private String temperatura;
	private String fecha_termino;
	
	
	
	
	
	
	
	public String getFecha_termino() {
		return fecha_termino;
	}
	public void setFecha_termino(String fecha_termino) {
		this.fecha_termino = fecha_termino;
	}
	public String getVelocidad_viento() {
		return velocidad_viento;
	}
	public void setVelocidad_viento(String velocidad_viento) {
		this.velocidad_viento = velocidad_viento;
	}
	public String getOrientacion_viento() {
		return orientacion_viento;
	}
	public void setOrientacion_viento(String orientacion_viento) {
		this.orientacion_viento = orientacion_viento;
	}
	public String getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
	}
	public String getAdm_campo() {
		return adm_campo;
	}
	public void setAdm_campo(String adm_campo) {
		this.adm_campo = adm_campo;
	}
	public String getNforma_aplicacion() {
		return nforma_aplicacion;
	}
	public void setNforma_aplicacion(String nforma_aplicacion) {
		this.nforma_aplicacion = nforma_aplicacion;
	}
	public String getCodFormaAplicacion() {
		return codFormaAplicacion;
	}
	public void setCodFormaAplicacion(String codFormaAplicacion) {
		this.codFormaAplicacion = codFormaAplicacion;
	}
	public String getCodControl() {
		return codControl;
	}
	public void setCodControl(String codControl) {
		this.codControl = codControl;
	}
	public String getCodJefeAplicacion() {
		return codJefeAplicacion;
	}
	public void setCodJefeAplicacion(String codJefeAplicacion) {
		this.codJefeAplicacion = codJefeAplicacion;
	}
	public String getCodNombreAplicador() {
		return codNombreAplicador;
	}
	public void setCodNombreAplicador(String codNombreAplicador) {
		this.codNombreAplicador = codNombreAplicador;
	}
	public String getCodLibro() {
		return codLibro;
	}
	public void setCodLibro(String codLibro) {
		this.codLibro = codLibro;
	}
	public String getCodProgramaAplicacion() {
		return codProgramaAplicacion;
	}
	public void setCodProgramaAplicacion(String codProgramaAplicacion) {
		this.codProgramaAplicacion = codProgramaAplicacion;
	}
	public String getCodEstadoFenologico() {
		return codEstadoFenologico;
	}
	public void setCodEstadoFenologico(String codEstadoFenologico) {
		this.codEstadoFenologico = codEstadoFenologico;
	}
	public String getCodEspecie() {
		return codEspecie;
	}
	public void setCodEspecie(String codEspecie) {
		this.codEspecie = codEspecie;
	}
	public double getMojamiento_real() {
		return mojamiento_real;
	}
	public void setMojamiento_real(double mojamiento_real) {
		this.mojamiento_real = mojamiento_real;
	}
	public String getCampos_maq() {
		return campos_maq;
	}
	public void setCampos_maq(String campos_maq) {
		this.campos_maq = campos_maq;
	}
	public double getCapacidad_maquina() {
		return capacidad_maquina;
	}
	public void setCapacidad_maquina(double capacidad_maquina) {
		this.capacidad_maquina = capacidad_maquina;
	}
	public String getUm() {
		return um;
	}
	public void setUm(String um) {
		this.um = um;
	}
	public String getSolped() {
		return solped;
	}
	public void setSolped(String solped) {
		this.solped = solped;
	}
	public String getCodMercado() {
		return codMercado;
	}
	public void setCodMercado(String codMercado) {
		this.codMercado = codMercado;
	}
	public String getFecha_viable() {
		return fecha_viable;
	}
	public void setFecha_viable(String fecha_viable) {
		this.fecha_viable = fecha_viable;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getIdorden() {
		return idorden;
	}
	public void setIdorden(int idorden) {
		this.idorden = idorden;
	}
	public int getIdPrograma() {
		return idPrograma;
	}
	public void setIdPrograma(int idPrograma) {
		this.idPrograma = idPrograma;
	}
	public String getNreserva() {
		return nreserva;
	}
	public void setNreserva(String nreserva) {
		this.nreserva = nreserva;
	}
	public String getCodCampo() {
		return codCampo;
	}
	public void setCodCampo(String codCampo) {
		this.codCampo = codCampo;
	}
	public String getJefe_aplicacion() {
		return jefe_aplicacion;
	}
	public void setJefe_aplicacion(String jefe_aplicacion) {
		this.jefe_aplicacion = jefe_aplicacion;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public int getTipo_programa() {
		return tipo_programa;
	}
	public void setTipo_programa(int tipo_programa) {
		this.tipo_programa = tipo_programa;
	}
	public String getPresion_bomba() {
		return presion_bomba;
	}
	public void setPresion_bomba(String presion_bomba) {
		this.presion_bomba = presion_bomba;
	}
	public String getCambio_tractor() {
		return cambio_tractor;
	}
	public void setCambio_tractor(String cambio_tractor) {
		this.cambio_tractor = cambio_tractor;
	}
	public String getMarcha_tractor() {
		return marcha_tractor;
	}
	public void setMarcha_tractor(String marcha_tractor) {
		this.marcha_tractor = marcha_tractor;
	}
	public ArrayList<MAQUINARIA_PF> getLista_maquinaria() {
		return lista_maquinaria;
	}
	public void setLista_maquinaria(ArrayList<MAQUINARIA_PF> lista_maquinaria) {
		this.lista_maquinaria = lista_maquinaria;
	}
	
	public int getIdProgramaAplicacion() {
		return idProgramaAplicacion;
	}
	public void setIdProgramaAplicacion(int idProgramaAplicacion) {
		this.idProgramaAplicacion = idProgramaAplicacion;
	}
	
	
	public int getMojamiento() {
		return mojamiento;
	}
	public void setMojamiento(int mojamiento) {
		this.mojamiento = mojamiento;
	}
	public String getPrograma_aplicacion() {
		return programa_aplicacion;
	}
	public void setPrograma_aplicacion(String programa_aplicacion) {
		this.programa_aplicacion = programa_aplicacion;
	}
	public String getTipo_control() {
		return tipo_control;
	}
	public void setTipo_control(String tipo_control) {
		this.tipo_control = tipo_control;
	}
	public String getObservacion() {
		return Observacion;
	}
	public void setObservacion(String observacion) {
		Observacion = observacion;
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
	public ArrayList<MATERIAL_PF> getLista_materiales() {
		return lista_materiales;
	}
	public void setLista_materiales(ArrayList<MATERIAL_PF> lista_materiales) {
		this.lista_materiales = lista_materiales;
	}
	public ArrayList<CUARTEL_PF> getLista_cuarteles() {
		return lista_cuarteles;
	}
	public void setLista_cuarteles(ArrayList<CUARTEL_PF> lista_cuarteles) {
		this.lista_cuarteles = lista_cuarteles;
	}
	public int getNumero_orden() {
		return numero_orden;
	}
	public void setNumero_orden(int numero_orden) {
		this.numero_orden = numero_orden;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre_aplicador() {
		return nombre_aplicador;
	}
	public void setNombre_aplicador(String nombre_aplicador) {
		this.nombre_aplicador = nombre_aplicador;
	}
	public Date getFecha_estimada_aplicacion() {
		return fecha_estimada_aplicacion;
	}
	public void setFecha_estimada_aplicacion(Date fecha_estimada_aplicacion) {
		this.fecha_estimada_aplicacion = fecha_estimada_aplicacion;
	}
	public String getEspecie() {
		return especie;
	}
	public void setEspecie(String especie) {
		this.especie = especie;
	}
	public String getVariedad() {
		return variedad;
	}
	public void setVariedad(String variedad) {
		this.variedad = variedad;
	}
	public String getEstado_fenologico() {
		return estado_fenologico;
	}
	public void setEstado_fenologico(String estado_fenologico) {
		this.estado_fenologico = estado_fenologico;
	}
	public Date getFecha_estimada_cosecha() {
		return fecha_estimada_cosecha;
	}
	public void setFecha_estimada_cosecha(Date fecha_estimada_cosecha) {
		this.fecha_estimada_cosecha = fecha_estimada_cosecha;
	}
	public String getMercado() {
		return mercado;
	}
	public void setMercado(String mercado) {
		this.mercado = mercado;
	}
	public String getForma_aplicacion() {
		return forma_aplicacion;
	}
	public void setForma_aplicacion(String forma_aplicacion) {
		this.forma_aplicacion = forma_aplicacion;
	}
	
		
}

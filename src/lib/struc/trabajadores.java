package lib.struc;

import java.util.Comparator;

public class trabajadores  {
	public int id;
	public String nombre;
	public String codigo;
	public String rut;
	public String f_ingreso;
	public String direccion;
	public String telefono;
	public String fNacimineto;
	public String nacionalidad;
	public String estado_civil;
	public String email;
	public String prevision;
	public String isapre;
	public int c_normales;
	public int c_invalidas;
	public String t_asign_familiar;
	public int id_perfil;
	public String f_termino;
	public String est_contrato;
	public String tipo_contrato;
	public int id_departamento;
	public String establecimiento;
	public int hrs_semanal;
	public String ajus_sueldo_base;
	public String bene_semana_corrida;
	public String seguro_cesantia;
	public String in_per_seg_ces;
	public String aefc_seg_accidentes;
	public String tipo_pacto;
	public String moneda_mon_ges;
	public int monto_pactado;
	public int monto_ges;
	public String tipo_sueldo_base;
	public int sueldo_mensual;
	public int asign_zona_extrema;
	public String gratificacion_legal;
	public String no_cuenta;
	public String institucion_bco;
	public String p_descripcion;
	public String d_descripcion;
	public int cargo;
	public String ap_paterno;
	public String ap_materno;
	public String nombre_cargo;
	public int postulante; // 0 es postulante 1 = estuvo contratado
	public int id_reclutamiento;
	public int id_peticion;
	public String fecha_e;
	public String hora_e;
	
	
	public String getFecha_e() {
		return fecha_e;
	}
	public void setFecha_e(String fecha_e) {
		this.fecha_e = fecha_e;
	}
	public String getHora_e() {
		return hora_e;
	}
	public void setHora_e(String hora_e) {
		this.hora_e = hora_e;
	}
	public int getId_reclutamiento() {
		return id_reclutamiento;
	}
	public void setId_reclutamiento(int id_reclutamiento) {
		this.id_reclutamiento = id_reclutamiento;
	}
	public int getId_peticion() {
		return id_peticion;
	}
	public void setId_peticion(int id_peticion) {
		this.id_peticion = id_peticion;
	}
	public int getPostulante() {
		return postulante;
	}
	public void setPostulante(int postulante) {
		this.postulante = postulante;
	}
	public String getNombre_cargo() {
		return nombre_cargo;
	}
	public void setNombre_cargo(String nombre_cargo) {
		this.nombre_cargo = nombre_cargo;
	}
	public String getAp_paterno() {
		return ap_paterno;
	}
	public void setAp_paterno(String ap_paterno) {
		this.ap_paterno = ap_paterno;
	}
	public String getAp_materno() {
		return ap_materno;
	}
	public void setAp_materno(String ap_materno) {
		this.ap_materno = ap_materno;
	}
	public int getCargo() {
		return cargo;
	}
	public void setCargo(int cargo) {
		this.cargo = cargo;
	}
	public String getP_descripcion() {
		return p_descripcion;
	}
	public void setP_descripcion(String p_descripcion) {
		this.p_descripcion = p_descripcion;
	}
	public String getD_descripcion() {
		return d_descripcion;
	}
	public void setD_descripcion(String d_descripcion) {
		this.d_descripcion = d_descripcion;
	}
	public String getF_termino() {
		return f_termino;
	}
	public void setF_termino(String f_termino) {
		this.f_termino = f_termino;
	}
	public String getEst_contrato() {
		return est_contrato;
	}
	public void setEst_contrato(String est_contrato) {
		this.est_contrato = est_contrato;
	}
	public String getTipo_contrato() {
		return tipo_contrato;
	}
	public void setTipo_contrato(String tipo_contrato) {
		this.tipo_contrato = tipo_contrato;
	}
	public int getId_departamento() {
		return id_departamento;
	}
	public void setId_departamento(int id_departamento) {
		this.id_departamento = id_departamento;
	}
	public String getEstablecimiento() {
		return establecimiento;
	}
	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}
	public int getHrs_semanal() {
		return hrs_semanal;
	}
	public void setHrs_semanal(int hrs_semanal) {
		this.hrs_semanal = hrs_semanal;
	}
	public String getAjus_sueldo_base() {
		return ajus_sueldo_base;
	}
	public void setAjus_sueldo_base(String ajus_sueldo_base) {
		this.ajus_sueldo_base = ajus_sueldo_base;
	}
	public String getBene_semana_corrida() {
		return bene_semana_corrida;
	}
	public void setBene_semana_corrida(String bene_semana_corrida) {
		this.bene_semana_corrida = bene_semana_corrida;
	}
	public String getSeguro_cesantia() {
		return seguro_cesantia;
	}
	public void setSeguro_cesantia(String seguro_cesantia) {
		this.seguro_cesantia = seguro_cesantia;
	}
	public String getIn_per_seg_ces() {
		return in_per_seg_ces;
	}
	public void setIn_per_seg_ces(String in_per_seg_ces) {
		this.in_per_seg_ces = in_per_seg_ces;
	}
	public String getAefc_seg_accidentes() {
		return aefc_seg_accidentes;
	}
	public void setAefc_seg_accidentes(String aefc_seg_accidentes) {
		this.aefc_seg_accidentes = aefc_seg_accidentes;
	}
	public String getTipo_pacto() {
		return tipo_pacto;
	}
	public void setTipo_pacto(String tipo_pacto) {
		this.tipo_pacto = tipo_pacto;
	}
	public String getMoneda_mon_ges() {
		return moneda_mon_ges;
	}
	public void setMoneda_mon_ges(String moneda_mon_ges) {
		this.moneda_mon_ges = moneda_mon_ges;
	}
	public int getMonto_pactado() {
		return monto_pactado;
	}
	public void setMonto_pactado(int monto_pactado) {
		this.monto_pactado = monto_pactado;
	}
	public int getMonto_ges() {
		return monto_ges;
	}
	public void setMonto_ges(int monto_ges) {
		this.monto_ges = monto_ges;
	}
	public String getTipo_sueldo_base() {
		return tipo_sueldo_base;
	}
	public void setTipo_sueldo_base(String tipo_sueldo_base) {
		this.tipo_sueldo_base = tipo_sueldo_base;
	}
	public int getSueldo_mensual() {
		return sueldo_mensual;
	}
	public void setSueldo_mensual(int sueldo_mensual) {
		this.sueldo_mensual = sueldo_mensual;
	}
	public int getAsign_zona_extrema() {
		return asign_zona_extrema;
	}
	public void setAsign_zona_extrema(int asign_zona_extrema) {
		this.asign_zona_extrema = asign_zona_extrema;
	}
	public String getGratificacion_legal() {
		return gratificacion_legal;
	}
	public void setGratificacion_legal(String gratificacion_legal) {
		this.gratificacion_legal = gratificacion_legal;
	}
	public String getNo_cuenta() {
		return no_cuenta;
	}
	public void setNo_cuenta(String no_cuenta) {
		this.no_cuenta = no_cuenta;
	}
	public String getInstitucion_bco() {
		return institucion_bco;
	}
	public void setInstitucion_bco(String institucion_bco) {
		this.institucion_bco = institucion_bco;
	}
	public String getT_asign_familiar() {
		return t_asign_familiar;
	}
	public void setT_asign_familiar(String t_asign_familiar) {
		this.t_asign_familiar = t_asign_familiar;
	}
	public int getC_normales() {
		return c_normales;
	}
	public void setC_normales(int c_normales) {
		this.c_normales = c_normales;
	}
	public int getC_invalidas() {
		return c_invalidas;
	}
	public void setC_invalidas(int c_invalidas) {
		this.c_invalidas = c_invalidas;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getF_ingreso() {
		return f_ingreso;
	}
	public void setF_ingreso(String f_ingreso) {
		this.f_ingreso = f_ingreso;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public String getEstado_civil() {
		return estado_civil;
	}
	public void setEstado_civil(String estado_civil) {
		this.estado_civil = estado_civil;
	}
	public String getPrevision() {
		return prevision;
	}
	public void setPrevision(String prevision) {
		this.prevision = prevision;
	}
	public String getIsapre() {
		return isapre;
	}
	public void setIsapre(String isapre) {
		this.isapre = isapre;
	}
	public int getId_perfil() {
		return id_perfil;
	}
	public void setId_perfil(int id_perfil) {
		this.id_perfil = id_perfil;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
	public String getfNacimineto() {
		return fNacimineto;
	}
	public void setfNacimineto(String fNacimineto) {
		this.fNacimineto = fNacimineto;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	 /*Comparator for sorting the list by Student Name*/
    public static Comparator<trabajadores> TNameComparator = new Comparator<trabajadores>() {


	@Override
	public int compare(trabajadores o1, trabajadores o2) {
		 String TName1 = o1.getNombre().toUpperCase();
		 String TName2 = o2.getNombre().toUpperCase();

		   //ascending order
		   //return TName2.compareTo(TName1);

		   //descending order
		   return TName1.compareTo(TName2);
	}};
	
	
	
	
}

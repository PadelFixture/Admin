//package lib.jsonMap;
//
//import java.util.List;
//
//import org.codehaus.jackson.annotate.JsonProperty;
//
//import lib.struc.departamentos;
//import lib.struc.perfil_usuario;
//import lib.struc.labores;
//
//public class innerTrabajadores {
//	@JsonProperty("id")
//	public int id;
//	
//	@JsonProperty("nombre")
//	public String nombre;
//	
//	@JsonProperty("codigo")
//	public String codigo;
//	
//	@JsonProperty("rut")
//	public String rut;
//	
//	@JsonProperty("f_ingreso")
//	public String f_ingreso;
//	
//	@JsonProperty("direccion")
//	public String direccion;
//	
//	@JsonProperty("telefono")
//	public String telefono;
//	
//	@JsonProperty("fNacimieto")
//	public String fNacimineto;
//	
//	@JsonProperty("nacionalidad")
//	public String nacionalidad;
//	
//	@JsonProperty("estado_civil")
//	public String estado_civil;
//	
//	@JsonProperty("email")
//	public String email;
//	
//	@JsonProperty("prevision")
//	public String prevision;
//	
//	@JsonProperty("isapre")
//	public String isapre;
//	
//	@JsonProperty("c_normales")
//	public int c_normales;
//	
//	@JsonProperty("c_invalidas")
//	public int c_invalidas;
//	
//	@JsonProperty("t_asign_familiar")
//	public String t_asign_familiar;
//	
//	@JsonProperty("id_perfil")
//	public int id_perfil;
//	
//	@JsonProperty("f_termino")
//	public String f_termino;
//	
//	@JsonProperty("est_contrato")
//	public String est_contrato;
//	
//	@JsonProperty("tipo_contrato")
//	public String tipo_contrato;
//	
//	@JsonProperty("id_departamento")
//	public int id_departamento;
//	
//	@JsonProperty("establecimiento")
//	public String establecimiento;
//	
//	@JsonProperty("hrs_semanal")
//	public int hrs_semanal;
//	
//	@JsonProperty("ajus_sueldo_base")
//	public String ajus_sueldo_base;
//	
//	@JsonProperty("bene_semana_corrida")
//	public String bene_semana_corrida;
//	
//	@JsonProperty("seguro_cesantia")
//	public String seguro_cesantia;
//	
//	@JsonProperty("in_per_seg_ces")
//	public String in_per_seg_ces;
//	
//	@JsonProperty("aefc_seg_accidentes")
//	public String aefc_seg_accidentes;
//	
//	@JsonProperty("tipo_pacto")
//	public String tipo_pacto;
//	
//	@JsonProperty("moneda_monto_ges")
//	public String moneda_mon_ges;
//	
//	@JsonProperty("monto_pactado")
//	public int monto_pactado;
//	
//	@JsonProperty("monto_ges")
//	public int monto_ges;
//	
//	@JsonProperty("tipo_sueldo_base")
//	public String tipo_sueldo_base;
//	
//	@JsonProperty("sueldo_mensual")
//	public int sueldo_mensual;
//	
//	@JsonProperty("asign_zona_extrema")
//	public int asign_zona_extrema;
//	
//	@JsonProperty("gratificacion_legal")
//	public String gratificacion_legal;
//	
//	@JsonProperty("no_cuenta")
//	public String no_cuenta;
//	
//	@JsonProperty("institucion_bco")
//	public String institucion_bco;
//
//	@JsonProperty("perfil")
//	public List<perfil_usuario> perfil;
//	
//	@JsonProperty("departamento")
//	public List<departamentos> departamento;
//	
//	@JsonProperty("labores")
//	public List<labores> labores;
//	
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public String getNombre() {
//		return nombre;
//	}
//
//	public void setNombre(String nombre) {
//		this.nombre = nombre;
//	}
//
//	public String getCodigo() {
//		return codigo;
//	}
//
//	public void setCodigo(String codigo) {
//		this.codigo = codigo;
//	}
//
//	public String getRut() {
//		return rut;
//	}
//
//	public void setRut(String rut) {
//		this.rut = rut;
//	}
//
//	public String getF_ingreso() {
//		return f_ingreso;
//	}
//
//	public void setF_ingreso(String f_ingreso) {
//		this.f_ingreso = f_ingreso;
//	}
//
//	public String getDireccion() {
//		return direccion;
//	}
//
//	public void setDireccion(String direccion) {
//		this.direccion = direccion;
//	}
//
//	public String getTelefono() {
//		return telefono;
//	}
//
//	public void setTelefono(String telefono) {
//		this.telefono = telefono;
//	}
//
//	public String getfNacimineto() {
//		return fNacimineto;
//	}
//
//	public void setfNacimineto(String fNacimineto) {
//		this.fNacimineto = fNacimineto;
//	}
//
//	public String getNacionalidad() {
//		return nacionalidad;
//	}
//
//	public void setNacionalidad(String nacionalidad) {
//		this.nacionalidad = nacionalidad;
//	}
//
//	public String getEstado_civil() {
//		return estado_civil;
//	}
//
//	public void setEstado_civil(String estado_civil) {
//		this.estado_civil = estado_civil;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getPrevision() {
//		return prevision;
//	}
//
//	public void setPrevision(String prevision) {
//		this.prevision = prevision;
//	}
//
//	public String getIsapre() {
//		return isapre;
//	}
//
//	public void setIsapre(String isapre) {
//		this.isapre = isapre;
//	}
//
//	public int getC_normales() {
//		return c_normales;
//	}
//
//	public void setC_normales(int c_normales) {
//		this.c_normales = c_normales;
//	}
//
//	public int getC_invalidas() {
//		return c_invalidas;
//	}
//
//	public void setC_invalidas(int c_invalidas) {
//		this.c_invalidas = c_invalidas;
//	}
//
//	public String getT_asign_familiar() {
//		return t_asign_familiar;
//	}
//
//	public void setT_asign_familiar(String t_asign_familiar) {
//		this.t_asign_familiar = t_asign_familiar;
//	}
//
//	public int getId_perfil() {
//		return id_perfil;
//	}
//
//	public void setId_perfil(int id_perfil) {
//		this.id_perfil = id_perfil;
//	}
//
//	public String getF_termino() {
//		return f_termino;
//	}
//
//	public void setF_termino(String f_termino) {
//		this.f_termino = f_termino;
//	}
//
//	public String getEst_contrato() {
//		return est_contrato;
//	}
//
//	public void setEst_contrato(String est_contrato) {
//		this.est_contrato = est_contrato;
//	}
//
//	public String getTipo_contrato() {
//		return tipo_contrato;
//	}
//
//	public void setTipo_contrato(String tipo_contrato) {
//		this.tipo_contrato = tipo_contrato;
//	}
//
//	public int getId_departamento() {
//		return id_departamento;
//	}
//
//	public void setId_departamento(int id_departamento) {
//		this.id_departamento = id_departamento;
//	}
//
//	public String getEstablecimiento() {
//		return establecimiento;
//	}
//
//	public void setEstablecimiento(String establecimiento) {
//		this.establecimiento = establecimiento;
//	}
//
//	public int getHrs_semanal() {
//		return hrs_semanal;
//	}
//
//	public void setHrs_semanal(int hrs_semanal) {
//		this.hrs_semanal = hrs_semanal;
//	}
//
//	public String getAjus_sueldo_base() {
//		return ajus_sueldo_base;
//	}
//
//	public void setAjus_sueldo_base(String ajus_sueldo_base) {
//		this.ajus_sueldo_base = ajus_sueldo_base;
//	}
//
//	public String getBene_semana_corrida() {
//		return bene_semana_corrida;
//	}
//
//	public void setBene_semana_corrida(String bene_semana_corrida) {
//		this.bene_semana_corrida = bene_semana_corrida;
//	}
//
//	public String getSeguro_cesantia() {
//		return seguro_cesantia;
//	}
//
//	public void setSeguro_cesantia(String seguro_cesantia) {
//		this.seguro_cesantia = seguro_cesantia;
//	}
//
//	public String getIn_per_seg_ces() {
//		return in_per_seg_ces;
//	}
//
//	public void setIn_per_seg_ces(String in_per_seg_ces) {
//		this.in_per_seg_ces = in_per_seg_ces;
//	}
//
//	public String getAefc_seg_accidentes() {
//		return aefc_seg_accidentes;
//	}
//
//	public void setAefc_seg_accidentes(String aefc_seg_accidentes) {
//		this.aefc_seg_accidentes = aefc_seg_accidentes;
//	}
//
//	public String getTipo_pacto() {
//		return tipo_pacto;
//	}
//
//	public void setTipo_pacto(String tipo_pacto) {
//		this.tipo_pacto = tipo_pacto;
//	}
//
//	public String getMoneda_mon_ges() {
//		return moneda_mon_ges;
//	}
//
//	public void setMoneda_mon_ges(String moneda_mon_ges) {
//		this.moneda_mon_ges = moneda_mon_ges;
//	}
//
//	public int getMonto_pactado() {
//		return monto_pactado;
//	}
//
//	public void setMonto_pactado(int monto_pactado) {
//		this.monto_pactado = monto_pactado;
//	}
//
//	public int getMonto_ges() {
//		return monto_ges;
//	}
//
//	public void setMonto_ges(int monto_ges) {
//		this.monto_ges = monto_ges;
//	}
//
//	public String getTipo_sueldo_base() {
//		return tipo_sueldo_base;
//	}
//
//	public void setTipo_sueldo_base(String tipo_sueldo_base) {
//		this.tipo_sueldo_base = tipo_sueldo_base;
//	}
//
//	public int getSueldo_mensual() {
//		return sueldo_mensual;
//	}
//
//	public void setSueldo_mensual(int sueldo_mensual) {
//		this.sueldo_mensual = sueldo_mensual;
//	}
//
//	public int getAsign_zona_extrema() {
//		return asign_zona_extrema;
//	}
//
//	public void setAsign_zona_extrema(int asign_zona_extrema) {
//		this.asign_zona_extrema = asign_zona_extrema;
//	}
//
//	public String getGratificacion_legal() {
//		return gratificacion_legal;
//	}
//
//	public void setGratificacion_legal(String gratificacion_legal) {
//		this.gratificacion_legal = gratificacion_legal;
//	}
//
//	public String getNo_cuenta() {
//		return no_cuenta;
//	}
//
//	public void setNo_cuenta(String no_cuenta) {
//		this.no_cuenta = no_cuenta;
//	}
//
//	public String getInstitucion_bco() {
//		return institucion_bco;
//	}
//
//	public void setInstitucion_bco(String institucion_bco) {
//		this.institucion_bco = institucion_bco;
//	}
//
//	public List<perfil_usuario> getPerfil() {
//		return perfil;
//	}
//
//	public void setPerfil(List<perfil_usuario> perfil) {
//		this.perfil = perfil;
//	}
//
//	public List<departamentos> getDepartamento() {
//		return departamento;
//	}
//
//	public void setDepartamento(List<departamentos> departamento) {
//		this.departamento = departamento;
//	}
//
//	public List<labores> getLabores() {
//		return labores;
//	}
//
//	public void setLabores(List<labores> labores) {
//		this.labores = labores;
//	}
//}

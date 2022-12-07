package lib.classSW.report;

public class InformeTrabajadores {

	private String nombreEmpresa;
	private String Trabajador;
	private String Nombre;
	private String RutTrabajador;
	private String idNacionalidad;
	private String Sexo;
	private String FechaNacimiento;
	private String EstadoCivil;
	private String Profesion;
	private String CodigoTrabajador;
	private String TipoContrato;
	private String Cargo;
	private String Comuna;
	private String Direccion;
	private String CentroCosto;
	private String Telefono;
	private String Afp;
	private String pensionados;
	private String TipoTrabajador;
	private String Isapre;
	private String sCesantia;
	private String Vigencia;
	private String horasSemanales;
	private String Banco;
	private String NumeroCuenta;
	private String FechaInicioContrato;
	private String FechaTerminoContrato;
	private String sueldoBase;
	
	public String getNombreEmpresa() {
		return nombreEmpresa;
	}
	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}
	public String getTrabajador() {
		return Trabajador;
	}
	public void setTrabajador(String trabajador) {
		Trabajador = trabajador;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getRutTrabajador() {
		return RutTrabajador;
	}
	public void setRutTrabajador(String rutTrabajador) {
		RutTrabajador = rutTrabajador;
	}
	public String getIdNacionalidad() {
		return idNacionalidad;
	}
	public void setIdNacionalidad(String idNacionalidad) {
		this.idNacionalidad = idNacionalidad;
	}
	public String getSexo() {
		return Sexo;
	}
	public void setSexo(String sexo) {
		Sexo = sexo;
	}
	public String getFechaNacimiento() {
		return FechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		FechaNacimiento = fechaNacimiento;
	}
	public String getEstadoCivil() {
		return EstadoCivil;
	}
	public void setEstadoCivil(String estadoCivil) {
		EstadoCivil = estadoCivil;
	}
	public String getProfesion() {
		return Profesion;
	}
	public void setProfesion(String profesion) {
		Profesion = profesion;
	}
	public String getCodigoTrabajador() {
		return CodigoTrabajador;
	}
	public void setCodigoTrabajador(String codigoTrabajador) {
		CodigoTrabajador = codigoTrabajador;
	}
	public String getTipoContrato() {
		return TipoContrato;
	}
	public void setTipoContrato(String tipoContrato) {
		TipoContrato = tipoContrato;
	}
	public String getCargo() {
		return Cargo;
	}
	public void setCargo(String cargo) {
		Cargo = cargo;
	}
	public String getComuna() {
		return Comuna;
	}
	public void setComuna(String comuna) {
		Comuna = comuna;
	}
	public String getDireccion() {
		return Direccion;
	}
	public void setDireccion(String direccion) {
		Direccion = direccion;
	}
	public String getCentroCosto() {
		return CentroCosto;
	}
	public void setCentroCosto(String centroCosto) {
		CentroCosto = centroCosto;
	}
	public String getTelefono() {
		return Telefono;
	}
	public void setTelefono(String telefono) {
		Telefono = telefono;
	}
	public String getAfp() {
		return Afp;
	}
	public void setAfp(String afp) {
		Afp = afp;
	}
	public String getPensionados() {
		return pensionados;
	}
	public void setPensionados(String pensionados) {
		this.pensionados = pensionados;
	}
	public String getTipoTrabajador() {
		return TipoTrabajador;
	}
	public void setTipoTrabajador(String tipoTrabajador) {
		TipoTrabajador = tipoTrabajador;
	}
	public String getIsapre() {
		return Isapre;
	}
	public void setIsapre(String isapre) {
		Isapre = isapre;
	}
	public String getsCesantia() {
		return sCesantia;
	}
	public void setsCesantia(String sCesantia) {
		this.sCesantia = sCesantia;
	}
	public String getVigencia() {
		return Vigencia;
	}
	public void setVigencia(String vigencia) {
		Vigencia = vigencia;
	}
	public String getHorasSemanales() {
		return horasSemanales;
	}
	public void setHorasSemanales(String horasSemanales) {
		this.horasSemanales = horasSemanales;
	}
	public String getBanco() {
		return Banco;
	}
	public void setBanco(String banco) {
		Banco = banco;
	}
	public String getNumeroCuenta() {
		return NumeroCuenta;
	}
	public void setNumeroCuenta(String numeroCuenta) {
		NumeroCuenta = numeroCuenta;
	}
	public String getFechaInicioContrato() {
		return FechaInicioContrato;
	}
	public void setFechaInicioContrato(String fechaInicioContrato) {
		FechaInicioContrato = fechaInicioContrato;
	}
	public String getFechaTerminoContrato() {
		return FechaTerminoContrato;
	}
	public void setFechaTerminoContrato(String fechaTerminoContrato) {
		FechaTerminoContrato = fechaTerminoContrato;
	}
	public String getSueldoBase() {
		return sueldoBase;
	}
	public void setSueldoBase(String sueldoBase) {
		this.sueldoBase = sueldoBase;
	}
	
	
	@Override
	public String toString() {
		return "InformeTrabajadores [nombreEmpresa=" + nombreEmpresa + ", Trabajador=" + Trabajador + ", Nombre="
				+ Nombre + ", RutTrabajador=" + RutTrabajador + ", idNacionalidad=" + idNacionalidad + ", Sexo=" + Sexo
				+ ", FechaNacimiento=" + FechaNacimiento + ", EstadoCivil=" + EstadoCivil + ", Profesion=" + Profesion
				+ ", CodigoTrabajador=" + CodigoTrabajador + ", TipoContrato=" + TipoContrato + ", Cargo=" + Cargo
				+ ", Comuna=" + Comuna + ", Direccion=" + Direccion + ", CentroCosto=" + CentroCosto + ", Telefono="
				+ Telefono + ", Afp=" + Afp + ", pensionados=" + pensionados + ", TipoTrabajador=" + TipoTrabajador
				+ ", Isapre=" + Isapre + ", sCesantia=" + sCesantia + ", Vigencia=" + Vigencia + ", horasSemanales="
				+ horasSemanales + ", Banco=" + Banco + ", NumeroCuenta=" + NumeroCuenta + ", FechaInicioContrato="
				+ FechaInicioContrato + ", FechaTerminoContrato=" + FechaTerminoContrato + ", sueldoBase=" + sueldoBase
				+ "]";
	}
	
	
	
}

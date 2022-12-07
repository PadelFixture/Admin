package lib.classSA;

public class RENDIMIENTO_GENERAL {
	
	public int codigo;
	public String fecha;
	public int especie;
	public String nespecie;
	public int variedad;
	public String nvariedad;
	public int cuartel;
	public String ncuartel;
	public int faena;
	public String nfaena;
	public int labor;
	public String nlabor;
	public float horas;
	public int tipo_pago;
	public int valor;
	public int base_piso_dia;
	public int codigo_cuadrilla;
	public String ncuadrilla;
	public int codigo_supervisor;
	public String nsupervisor;
	public String campo;
	public int estado;
	public float valor_x_hora;
	public int folio;
	public String contratista;
	public String ncontratista;
	public String trabajador;
	public String macro;
	public String ceco;
	public String ordenco;
	public int n_trab;
	public int tipo_permiso;
	
	
	
	public int getTipo_permiso() {
		return tipo_permiso;
	}
	public void setTipo_permiso(int tipo_permiso) {
		this.tipo_permiso = tipo_permiso;
	}
	public int getN_trab() {
		return n_trab;
	}
	public void setN_trab(int n_trab) {
		this.n_trab = n_trab;
	}
	public String getTrabajador() {
		return trabajador;
	}
	public void setTrabajador(String trabajador) {
		this.trabajador = trabajador;
	}
	public String getMacro() {
		return macro;
	}
	public void setMacro(String macro) {
		this.macro = macro;
	}
	public String getCeco() {
		return ceco;
	}
	public void setCeco(String ceco) {
		this.ceco = ceco;
	}
	public String getOrdenco() {
		return ordenco;
	}
	public void setOrdenco(String ordenco) {
		this.ordenco = ordenco;
	}
	public String getNcontratista() {
		return ncontratista;
	}
	public void setNcontratista(String ncontratista) {
		this.ncontratista = ncontratista;
	}
	public String getContratista() {
		return contratista;
	}
	public void setContratista(String contratista) {
		this.contratista = contratista;
	}
	public int getFolio() {
		return folio;
	}
	public void setFolio(int folio) {
		this.folio = folio;
	}
	public int getBase_piso_dia() {
		return base_piso_dia;
	}
	public void setBase_piso_dia(int base_piso_dia) {
		this.base_piso_dia = base_piso_dia;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	public String getNsupervisor() {
		return nsupervisor;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getNespecie() {
		return nespecie;
	}
	public void setNespecie(String nespecie) {
		this.nespecie = nespecie;
	}
	public String getNvariedad() {
		return nvariedad;
	}
	public void setNvariedad(String nvariedad) {
		this.nvariedad = nvariedad;
	}
	public String getNcuartel() {
		return ncuartel;
	}
	public void setNcuartel(String ncuartel) {
		this.ncuartel = ncuartel;
	}
	public String getNfaena() {
		return nfaena;
	}
	public void setNfaena(String nfaena) {
		this.nfaena = nfaena;
	}
	public String getNlabor() {
		return nlabor;
	}
	public void setNlabor(String nlabor) {
		this.nlabor = nlabor;
	}
	public String getNcuadrilla() {
		return ncuadrilla;
	}
	public void setNcuadrilla(String ncuadrilla) {
		this.ncuadrilla = ncuadrilla;
	}
	public String getNcodigo_supervisor() {
		return nsupervisor;
	}
	public void setNsupervisor(String nsupervisor) {
		this.nsupervisor = nsupervisor;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
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
	public int getCuartel() {
		return cuartel;
	}
	public void setCuartel(int cuartel) {
		this.cuartel = cuartel;
	}
	public int getFaena() {
		return faena;
	}
	public void setFaena(int faena) {
		this.faena = faena;
	}
	public int getLabor() {
		return labor;
	}
	public void setLabor(int labor) {
		this.labor = labor;
	}
	public float getHoras() {
		return horas;
	}
	public void setHoras(float horas) {
		this.horas = horas;
	}
	public int getTipo_pago() {
		return tipo_pago;
	}
	public void setTipo_pago(int tipo_pago) {
		this.tipo_pago = tipo_pago;
	}
	public int getCodigo_cuadrilla() {
		return codigo_cuadrilla;
	}
	public void setCodigo_cuadrilla(int codigo_cuadrilla) {
		this.codigo_cuadrilla = codigo_cuadrilla;
	}
	public int getCodigo_supervisor() {
		return codigo_supervisor;
	}
	public void setCodigo_supervisor(int codigo_supervisor) {
		this.codigo_supervisor = codigo_supervisor;
	}
	public float getValor_x_hora() {
		return valor_x_hora;
	}
	public void setValor_x_hora(float valor_x_hora) {
		this.valor_x_hora = valor_x_hora;
	}
	@Override
	public String toString() {
		return "{codigo:" + codigo + ", fecha:'" + fecha + "', especie:" + especie + ", nespecie:'"
				+ nespecie + "', variedad:" + variedad + ", nvariedad:'" + nvariedad + "', cuartel:" + cuartel
				+ ", ncuartel:'" + ncuartel + "', faena:" + faena + ", nfaena:'" + nfaena + "', labor:" + labor
				+ ", nlabor:'" + nlabor + "', horas:" + horas + ", tipo_pago:" + tipo_pago + ", valor:" + valor
				+ ", base_piso_dia:" + base_piso_dia + ", codigo_cuadrilla:" + codigo_cuadrilla + ", ncuadrilla:'"
				+ ncuadrilla + "', codigo_supervisor:" + codigo_supervisor + ", nsupervisor:'" + nsupervisor + "', campo:'"
				+ campo + "', estado:" + estado + ", valor_x_hora:" + valor_x_hora + ", folio:" + folio
				+ ", contratista:" + contratista + ", ncontratista:'" + ncontratista + "', trabajador:" + trabajador
				+ ", macro:'" + macro + "', ceco:'" + ceco + "', ordenco:'" + ordenco + "', n_trab:'" + n_trab + "'}";
	}
	
	
}

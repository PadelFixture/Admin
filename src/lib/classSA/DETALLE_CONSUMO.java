package lib.classSA;

public class DETALLE_CONSUMO {
	private int codigo_pf;
	private int idPrograma;
	private int idOrden;
	private String codigo_material;
	private double cantidad;
	private double cantidad_real;
	private String docConsumo;
	private double devolucion;
	private String docDevolucion;
	private double sobreConsumo;
	private String docSobreConsumo;
	private String campo;
	private String reserva;
	private String fecha;
	private int periodo;
	public CUARTEL cuartel;
	private int libroCampo;
	private String periodosc;
	private float porcentaje;
	private float porcentajeS;
	
	
	
	
	
	
	public float getPorcentajeS() {
		return porcentajeS;
	}
	public void setPorcentajeS(float porcentajeS) {
		this.porcentajeS = porcentajeS;
	}
	public float getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(float porcentaje) {
		this.porcentaje = porcentaje;
	}
	public String getPeriodosc() {
		return periodosc;
	}
	public void setPeriodosc(String periodosc) {
		this.periodosc = periodosc;
	}
	public int getLibroCampo() {
		return libroCampo;
	}
	public void setLibroCampo(int libroCampo) {
		this.libroCampo = libroCampo;
	}
	public CUARTEL getCuartel() {
		return cuartel;
	}
	public void setCuartel(CUARTEL cuartel) {
		this.cuartel = cuartel;
	}
	public int getPeriodo() {
		return periodo;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getReserva() {
		return reserva;
	}
	public void setReserva(String reserva) {
		this.reserva = reserva;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public int getCodigo_pf() {
		return codigo_pf;
	}
	public void setCodigo_pf(int codigo_pf) {
		this.codigo_pf = codigo_pf;
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
	public String getCodigo_material() {
		return codigo_material;
	}
	public void setCodigo_material(String codigo_material) {
		this.codigo_material = codigo_material;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public double getCantidad_real() {
		return cantidad_real;
	}
	public void setCantidad_real(double cantidad_real) {
		this.cantidad_real = cantidad_real;
	}
	public String getDocConsumo() {
		return docConsumo;
	}
	public void setDocConsumo(String docConsumo) {
		this.docConsumo = docConsumo;
	}
	public double getDevolucion() {
		return devolucion;
	}
	public void setDevolucion(double devolucion) {
		this.devolucion = devolucion;
	}
	public String getDocDevolucion() {
		return docDevolucion;
	}
	public void setDocDevolucion(String docDevolucion) {
		this.docDevolucion = docDevolucion;
	}
	public double getSobreConsumo() {
		return sobreConsumo;
	}
	public void setSobreConsumo(double sobreConsumo) {
		this.sobreConsumo = sobreConsumo;
	}
	public String getDocSobreConsumo() {
		return docSobreConsumo;
	}
	public void setDocSobreConsumo(String docSobreConsumo) {
		this.docSobreConsumo = docSobreConsumo;
	}
	
	
	
}

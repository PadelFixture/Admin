package lib.classSW;
import java.math.BigDecimal;

public class AFP {
	private int idAFP;
	private String nombreAFP;
	private BigDecimal tasaAFP;
	private BigDecimal sisAFP;
	private BigDecimal tasaTotalAFP;
	private String periodoAFP;
	private int idParametro;
	private String codigo;
	private String descripcion;
	private int activo;
	private String codPrevired;
	public int idCodPrevired;
	
	
	
	public int getIdCodPrevired() {
		return idCodPrevired;
	}
	public void setIdCodPrevired(int idCodPrevired) {
		this.idCodPrevired = idCodPrevired;
	}
	public int getIdParametro() {
		return idParametro;
	}
	public void setIdParametro(int idParametro) {
		this.idParametro = idParametro;
	}
	public String getCodPrevired() {
		return codPrevired;
	}
	public void setCodPrevired(String codprevired) {
		this.codPrevired = codprevired;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getActivo() {
		return activo;
	}
	public void setActivo(int activo) {
		this.activo = activo;
	}
	public int getIdAFP() {
		return idAFP;
	}
	public String getPeriodoAFP() {
		return periodoAFP;
	}
	public void setPeriodoAFP(String periodoAFP) {
		this.periodoAFP = periodoAFP;
	}
	public int getIdafp() {
		return idAFP;
	}
	public void setIdAFP(int idafp) {
		this.idAFP = idafp;
	}
	public String getNombreAFP() {
		return nombreAFP;
	}
	public void setNombreAFP(String nombreafp) {
		this.nombreAFP = nombreafp;
	}
	public BigDecimal getTasaAFP() {
		return tasaAFP;
	}
	public void setTasaAFP(BigDecimal tasaafp) {
		this.tasaAFP = tasaafp;
	}
	public BigDecimal getSisAFP() {
		return sisAFP;
	}
	public void setSisAFP(BigDecimal sisafp) {
		this.sisAFP = sisafp;
	}
	public BigDecimal getTasaTotalAFP() {
		return tasaTotalAFP;
	}
	public void setTasaTotalAFP(BigDecimal tasatotal) {
		this.tasaTotalAFP = tasatotal;
	}
	public AFP createBlankAFP() {
		this.idAFP=0;
		this.nombreAFP="";
		this.tasaAFP=null;
		this.sisAFP=null;
		this.tasaTotalAFP=null;
		
		return this;
	}
	
	

}

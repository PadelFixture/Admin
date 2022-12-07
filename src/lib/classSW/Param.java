package lib.classSW;
	
public class Param {
	public int id;
	public String codigo; 
	public String llave;
	public String descripcion;
	public int activo;
	
	public Param()
	{
		this.id=0;
		this.codigo="";
		this.llave="";
		this.descripcion="";
		this.activo=0;
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
	public String getLlave() {
		return llave;
	}
	public void setLlave(String llave) {
		this.llave = llave;
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
	public Param createBlankParam() {
		// TODO Auto-generated method stub
		return null;
	}
}

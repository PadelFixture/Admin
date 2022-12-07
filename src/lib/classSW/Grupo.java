package lib.classSW;

public class Grupo {
	private int idGrupo;
	private String nombreGrupo;
	private String descripcionGrupo;
	
	public String getDescripcionGrupo() {
		return descripcionGrupo;
	}
	public void setDescripcionGrupo(String descripcionGrupo) {
		this.descripcionGrupo = descripcionGrupo;
	}
	public int getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}
	public String getNombreGrupo() {
		return nombreGrupo;
	}
	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}
	public Grupo createBlankGroup() {
		this.idGrupo=0;
		this.nombreGrupo="";
		this.descripcionGrupo="";
		
		return this;
	}
	
	

}

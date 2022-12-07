package lib.ClassSASW;

public class TarjaPallet {
	
	String nombreArchivo;
	String pathDestino;
	String extension;
	String centro;
	String [][] datos;
	String[][] datosPrincipales;
	String[][] tablaDatos;
	String[] titulos;
	
	public String getCentro() {
		return centro;
	}
	public void setCentro(String centro) {
		this.centro = centro;
	}
	public String[] getTitulos() {
		return titulos;
	}
	public void setTitulos(String[] titulos) {
		this.titulos = titulos;
	}
	public TarjaPallet(){
		this.nombreArchivo="TarjaPallet";
		this.pathDestino="";
		this.extension="docx";
	}
	public String[][] getDatos() {
		return datos;
	}
	public void setDatos(String[][] datos) {
		this.datos = datos;
	}
	public String[][] getDatosPrincipales() {
		return datosPrincipales;
	}
	public void setDatosPrincipales(String[][] datosPrincipales) {
		this.datosPrincipales = datosPrincipales;
	}
	public String[][] getTablaDatos() {
		return tablaDatos;
	}
	public void setTablaDatos(String[][] tablaDatos) {
		this.tablaDatos = tablaDatos;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getPathDestino() {
		return pathDestino;
	}
	public void setPathDestino(String pathDestino) {
		this.pathDestino = pathDestino;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public void PrintObject(){
		System.out.println("-------datosPasados------------");
		System.out.println("Nombre Archivo: "+this.nombreArchivo);
		System.out.println("Path destino: "+this.pathDestino);
		System.out.println("Extension: "+this.extension);
		System.out.println("----------Datos-------------");
		for(int i=0;i<this.datos.length;i++){
				System.out.println("key: "+this.datos[i][0]+"/// value: "+this.datos[i][1]);
		}
		System.out.println("----------Datos Principales-------------");
		for(int j=0;j<this.datosPrincipales.length;j++){
				System.out.println("key: "+this.datosPrincipales[j][0]+"/// value: "+this.datosPrincipales[j][1]);
		}
		for(int k=0;k<this.tablaDatos.length;k++){
			for(int l=0;l<this.tablaDatos[k].length;l++){
				System.out.println("titulo: "+this.titulos[l]);
				System.out.println("Value: "+this.tablaDatos[k][l]);
			}
		}
	}
	
}

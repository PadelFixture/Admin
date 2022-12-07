package lib.classSW;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ExcelSW {

	LinkedHashMap<String, ArrayList<String>> datos;
	ArrayList<String> titulos;
	

	public LinkedHashMap<String, ArrayList<String>> getDatos() {
		return datos;
	}
	public void setDatos(LinkedHashMap<String, ArrayList<String>> datos) {
		this.datos = datos;
	}
	public ArrayList<String> getTitulos() {
		return titulos;
	}
	public void setTitulos(ArrayList<String> titulos) {
		this.titulos = titulos;
	}
	
}

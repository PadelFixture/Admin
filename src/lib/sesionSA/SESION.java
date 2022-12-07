package lib.sesionSA;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import lib.classSA.CAMPO;
import lib.classSA.CUARTEL;
import lib.classSA.ESPECIE;
import lib.classSA.SECTOR;
import lib.classSA.SESIONVAR;
import lib.classSA.VARIEDAD;
import lib.classSA.bloqueo_periodo;
import lib.classSA.incidencia;

public class SESION {
	private HttpSession session;
	SESIONVAR SA;
	
	public SESION(HttpSession httpSession) {
		session = httpSession;
		if(session.getAttribute("SA") == null) {
			SA = new SESIONVAR();
			ArrayList<CAMPO> campo = new ArrayList<CAMPO>();
			ArrayList<SECTOR> sector = new ArrayList<SECTOR>();
			ArrayList<ESPECIE> especie = new ArrayList<ESPECIE>();
			ArrayList<VARIEDAD> variedad = new ArrayList<VARIEDAD>();
			ArrayList<incidencia> incidencia = new ArrayList<incidencia>();
			ArrayList<CUARTEL> cuartel = new ArrayList<CUARTEL>();
			ArrayList<bloqueo_periodo> bloqueo = new ArrayList<bloqueo_periodo>();
			SA.setCampo(campo);
			SA.setSector(sector);
			SA.setEspecie(especie);
			SA.setVariedad(variedad);
			SA.setIncidencia(incidencia);
			SA.setCuartel(cuartel);
			SA.setBloqueo(bloqueo);
		}else{
			SA = (SESIONVAR) session.getAttribute("SA");
		}
	}
	public void addIdUser(int id){
		SA.setIdUser(id);
		save();
	}
	public void addUser(String user){
		SA.setUser(user);
		save();
	}
	public void addRolPrivado(int id){
		SA.setRolPrivado(id);
		save();
	}
	public int getIdUserSesion(){
		return SA.getIdUser();
	}
	public void addPass(String pass){
		SA.setPass(pass);
		save();
	}
	public void addGrupoCompra(String gc){
		SA.setGrupoCompra(gc);
		save();
	}
	public void addSolicitante(String s){
		SA.setSolicitante(s);
		save();
	}
	public void updateCampo(){
		SA.getCampo().clear();
	}
	public void addCuartel(CAMPO row, int id){
		if(!campoExist(id)){
			SA.getCampo().add(row);
		}
	}
	public void updCuartel(CUARTEL row, int id){
		ArrayList<CUARTEL> cuarteles = SA.getCuartel();
		for(CUARTEL r: cuarteles){
			if(r.getCodigo() == id){
				r.setCeco(row.getCeco());
				r.setClon(row.getClon());
				r.setDistancia_hancho(row.getDistancia_hancho());
				r.setDistancia_largo(row.getDistancia_largo());
				r.setEspecie(row.getEspecie());
				r.setVariedad(row.getVariedad());
				r.setDescripcion(row.getDescripcion());
				r.setCodigo_cuartel(row.getCodigo_cuartel());
				r.setNombre(row.getCodigo_cuartel()+" "+  row.getNombre());
				r.setEstado(row.getEstado());
				r.setAno_plantacion(row.getAno_plantacion());
				r.setPatron(row.getPatron());
				r.setMacroco(row.getMacroco());
				r.setOrdenco(row.getOrdenco());
				r.setSuperficie(row.getSuperficie());
				r.setPlantas(row.getPlantas());
				r.setFormacion(row.getFormacion());
				r.setVivero(row.getVivero());
				r.setTipo_planta(row.getTipo_planta());
				r.setTipo_control_heladas(row.getTipo_control_heladas());
				r.setTipo_proteccion(row.getTipo_proteccion());
				r.setLimitante_suelo(row.getLimitante_suelo());
				r.setPolinizante(row.getPolinizante());
				r.setTipo_plantacion(row.getTipo_plantacion());
				r.setGeoreferencia(row.getGeoreferencia());
				r.setCodigo_cuartel(row.getCodigo_cuartel());
				r.setSector(row.getSector());
			}
		}
	}
	public void addCuarteles(CUARTEL row, int id){
		if(!cuartelExist(id)){
			SA.getCuartel().add(row);
		}
	}
	public void addSector(SECTOR row, String sector){
		if(!sectorExist(sector)){
			SECTOR s = new SECTOR();
			s.setSector(row.getSector());
			s.setCampo(row.getCampo());
			s.setDescripcion(row.getDescripcion());
			SA.getSector().add(s);
		}
	}
	public void addEspecie(ESPECIE row, int especie){
		if(!especieExist(especie)){
			ESPECIE e = new ESPECIE();
			e.setCodigo(row.getCodigo());
			e.setEspecie(row.getEspecie());
			SA.getEspecie().add(e);
		}
	}
	public void addVariedad(VARIEDAD row, int codigo){
		if(!variedadExist(codigo)){
			SA.getVariedad().add(row);
		}
	}
	public void addIncidencia(incidencia row, int codigo){
		if(!incidenciaExist(codigo)){
			SA.getIncidencia().add(row);
		}
	}
	public void addBloqueo(bloqueo_periodo row, int codigo){
		if(!bloqueoExist(codigo)){
			SA.getBloqueo().add(row);
		}
	}
	private boolean bloqueoExist(int id) {
		boolean sw = false;
		ArrayList<bloqueo_periodo> rows = SA.getBloqueo();
		for(bloqueo_periodo row: rows){
			if(row.id == id){
				sw = true;
			}
		}
		return sw;
	}
	private boolean campoExist(int id) {
		boolean sw = false;
		ArrayList<CAMPO> rows = SA.getCampo();
		for(CAMPO row: rows){
			if(row.codigo == id){
				sw = true;
			}
		}
		return sw;
	}
	private boolean cuartelExist(int id) {
		boolean sw = false;
		ArrayList<CUARTEL> rows = SA.getCuartel();
		for(CUARTEL row: rows){
			if(row.codigo == id){
				sw = true;
			}
		}
		return sw;
	}
	private boolean sectorExist(String id) {
		boolean sw = false;
		ArrayList<SECTOR> rows = SA.getSector();
		for(SECTOR row: rows){
			if(row.sector.equals(id)){
				sw = true;
			}
		}
		return sw;
	}
	private boolean especieExist(int id) {
		boolean sw = false;
		ArrayList<ESPECIE> rows = SA.getEspecie();
		for(ESPECIE row: rows){
			if(row.codigo == id){
				sw = true;
			}
		}
		return sw;
	}
	private boolean variedadExist(int id) {
		boolean sw = false;
		ArrayList<VARIEDAD> rows = SA.getVariedad();
		for(VARIEDAD row: rows){
			if(row.codigo == id){
				sw = true;
			}
		}
		return sw;
	}
	private boolean incidenciaExist(int id) {
		boolean sw = false;
		ArrayList<incidencia> rows = SA.getIncidencia();
		for(incidencia row: rows){
			if(row.codigo == id){
				sw = true;
			}
		}
		return sw;
	}
	public void delIncidencia(int index){
		try {
			int count = 0;
			for (incidencia inci : SA.getIncidencia()) {
				if (inci.getCodigo() == index) {
					SA.getIncidencia().remove(count);
					break;
				}
				count++;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error: " + ex.getMessage());
		}
	}
	public void save() {
		session.setAttribute("SA", SA);
	}
	public SESIONVAR getView() {
		session.setAttribute("actualSesion", SA);
		session.getAttribute("actualSesion");
		return SA;
	}
	
	public void addEliminar(int eliminar){
		SA.setEliminar(eliminar);
		save();
	}
	
	public void addEditar(int editar){
		SA.setEditar(editar);
		save();
	}
	
	public void addVer(int ver){
		SA.setVer(ver);
		save();
	}
	
	
	
}

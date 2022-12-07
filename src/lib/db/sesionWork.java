//package lib.db;
//
//import java.util.ArrayList;
//
//import javax.servlet.http.HttpSession;
//
//import lib.jsonMap.jsonReclutamiento;
//import lib.struc.reclutamiento;
//import lib.struc.trabajadores;
//
//public class sesionWork {
//	private HttpSession sesion;
//	jsonReclutamiento jrc;
//	reclutamiento rc;
//	public sesionWork(HttpSession httpSession) {
//		sesion = httpSession;
//		if (sesion.getAttribute("reclutamiento") == null) {
//			jrc = new jsonReclutamiento();
//			rc = new reclutamiento();
////			ArrayList<trabajadores> trab = new ArrayList<trabajadores>();
////			rc.setTrab(trab);
//			ArrayList<reclutamiento> reclutas = new ArrayList<reclutamiento>();
//			reclutas.add(rc);
//			jrc.setJsonReclutamiento(reclutas);
//		} else{
//			jrc = (jsonReclutamiento) sesion.getAttribute("reclutamiento");
//		}
//	}
//	public void addTrab(trabajadores row,int idTrab)throws Exception{
//		if(!trabExist(idTrab)){
//			trabajadores e = new trabajadores();
//			rc.getTrab().add(row);
//			jrc.getJsonReclutamiento().add(rc);
//		}
//	}
//	private boolean trabExist(int id) {
//		boolean sw = false;
//		for(trabajadores row: rc.getTrab()){
//			if(row.id == id){
//				sw = true;
//			}
//		}
//		return sw;
//	}
//	public jsonReclutamiento getReclutamineto(){
//		return jrc;
//	}
//	public void saveReclutar() {
//		sesion.setAttribute("reclutamiento", jrc);
//	}
//}
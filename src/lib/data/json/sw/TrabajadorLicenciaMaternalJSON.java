package lib.data.json.sw;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.TrabajadorLicenciaMaternal;
import lib.db.sw.TrabajadorLicenciaMaternalDB;
import lib.security.session;

public class TrabajadorLicenciaMaternalJSON {

	
	//insert trabajador licencia maternal
			@RequestMapping(value = "/work/insertTrabajadorLicenciaMaternal/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
			public @ResponseBody boolean insertTrabajadorLicenciaMaternal(@RequestBody TrabajadorLicenciaMaternal TrabajadorLicenciaMaternal, HttpSession httpSession)
					throws Exception{

				session ses = new session(httpSession);
				if (ses.isValid()) {
					return false;
				}

				return TrabajadorLicenciaMaternalDB.insertTrabajadorLicenciaMaternal(TrabajadorLicenciaMaternal);
			}
	
	
			 // get licencia maternal
			@RequestMapping(value = "/work/getTrabajadorLicenciaMaternal/", method = {RequestMethod.GET})
			public @ResponseBody ArrayList<TrabajadorLicenciaMaternal> getTrabajadorLicenciaMaternal( HttpSession httpSession) throws Exception {

				session ses = new session(httpSession);
				ArrayList<TrabajadorLicenciaMaternal> TrabajadorLicenciaMaternal = new ArrayList<TrabajadorLicenciaMaternal>();

				if (ses.isValid()) {
					return TrabajadorLicenciaMaternal;
				}

				TrabajadorLicenciaMaternal = TrabajadorLicenciaMaternalDB.getTrabajadorLicenciaMaternal();
				return TrabajadorLicenciaMaternal;

			}// fin get
	
			 // get licencia maternal con id
			@RequestMapping(value = "/work/getTrabajadorLicenciaMaternalByIdTrabajador/{id}", method = {RequestMethod.GET})
			public @ResponseBody TrabajadorLicenciaMaternal getTrabajadorLicenciaMaternalByIdTrabajador(@PathVariable int id, HttpSession httpSession) throws Exception {

				session ses = new session(httpSession);
				TrabajadorLicenciaMaternal TrabajadorLicenciaMaternalByIdTrabajador = new TrabajadorLicenciaMaternal();

				if (ses.isValid()) {
					return TrabajadorLicenciaMaternalByIdTrabajador;
				}

				TrabajadorLicenciaMaternalByIdTrabajador = TrabajadorLicenciaMaternalDB.getTrabajadorLicenciaMaternalByIdTrabajador(id);
				return TrabajadorLicenciaMaternalByIdTrabajador;

			}// fin Get con id
	
	
			// update Trabajador licencia conducir
			@RequestMapping(value = "/work/updateTrabajadorLicenciaMaternal/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
			public @ResponseBody boolean updateTrabajadorLicenciaMaternal(@RequestBody TrabajadorLicenciaMaternal TrabajadorLicenciaMaternal, HttpSession httpSession)
					throws Exception {

				session ses = new session(httpSession);

				if (ses.isValid()) {
					return false;
				}
				return TrabajadorLicenciaMaternalDB.updateTrabajadorLicenciaMaternal(TrabajadorLicenciaMaternal);
			}// fin 
	
	
			
			// Eliminar trabajador licencia conducir
			@RequestMapping(value = "/work/deleteTrabajadorLicenciaMaternalById/{id}", method = {RequestMethod.PUT})
			public @ResponseBody boolean deleteTrabajadorLicenciaMaternal(@PathVariable int id ,HttpSession httpSession) throws Exception {
								
				session ses = new session(httpSession);
								
				if (ses.isValid()) {
					return false;
				}
								
				return TrabajadorLicenciaMaternalDB.deleteTrabajadorLicenciaMaternalById(id);
			}// fin metodo eliminar	
			
			
	
	
	
}// fin clase

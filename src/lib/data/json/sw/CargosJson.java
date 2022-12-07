package lib.data.json.sw;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.Cargo;
import lib.db.sw.CargosDB;
import lib.security.session;
@Controller
public class CargosJson {
		//Insertar un cargo 
		@RequestMapping(value = "/work/cargos/createCargo/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean insertCargo(@RequestBody Cargo cargo, HttpSession httpSession) throws Exception {
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}
			return CargosDB.createCargo(cargo);
		}
		//Obtener Todas los Cargos 
	@RequestMapping(value = "/work/cargos/getCargos/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<Cargo> getAllCargos(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<Cargo> cargos = new ArrayList<Cargo>();

		if (ses.isValid()) {
			return cargos;
		}
		return cargos = CargosDB.getAllCargos();

	}
	@RequestMapping(value = "/work/cargos/getCargoById/{id}", method = {RequestMethod.GET})
	public @ResponseBody Cargo getCargoById(@PathVariable int id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		Cargo c = new Cargo();

		if (ses.isValid()) {
			return c;
		}

		c = CargosDB.getCargoById(id);
		return c;

	}
	@RequestMapping(value = "/work/cargos/getCargoBySoc/{soc}", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<Cargo> getCargoBySoc(@PathVariable String soc, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<Cargo> cargos = new ArrayList<Cargo>();

		if (ses.isValid()) {
			return cargos;
		}
		return cargos = CargosDB.getCargoBySoc(soc);

	}
	
	@RequestMapping(value = "/work/cargos/getCargoByIdSociedad/{idSociedad}", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<Cargo> getCargoByIdSociedad(@PathVariable String idSociedad, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<Cargo> cargos = new ArrayList<Cargo>();

		if (ses.isValid()) {
			return cargos;
		}
		return cargos = CargosDB.getCargoByIdSociedad(idSociedad);

	}
	
	
	@RequestMapping(value = "/work/cargos/deleteCargo/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteCargo(@PathVariable int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
						
		if (ses.isValid()) {
			return false;
		}
						
		return CargosDB.deleteCargoById(id);

	}
	@RequestMapping(value = "/work/cargos/updateCargo/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateCargo(@RequestBody Cargo c,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
    	 
    	
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		
		resp = CargosDB.updateCargo(c);
        return resp;
	}



}

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

import lib.classSW.FamiliarRetroActivo;
import lib.db.sw.FamiliarRetroActivoDB;
import lib.security.session;

@Controller
public class FamiliarRetroActivoJSON {

	//get
	@RequestMapping(value = "/work/getFamiliarRetroActivoByCodTrabajador/{codTrabajador}", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<FamiliarRetroActivo> getFamiliarRetroActivoByCodTrabajador(@PathVariable int codTrabajador, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<FamiliarRetroActivo> familiarRetroActivo = new ArrayList<FamiliarRetroActivo>();

		if (ses.isValid()) {
			return familiarRetroActivo;
		}

		familiarRetroActivo = FamiliarRetroActivoDB.getFamiliarRetroActivoByCodTrabajador(codTrabajador);
		return familiarRetroActivo;

	}// fin Get con id
	
	
	//getAll
	
	//insert
		@RequestMapping(value = "/work/insertFamiliarRetroActivo/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean insertFamiliarRetroActivo(@RequestBody FamiliarRetroActivo retroActivo, HttpSession httpSession)
				throws Exception{

			session ses = new session(httpSession);
			
			if (ses.isValid()) {
				return false;
			}

			return FamiliarRetroActivoDB.insertFamiliarRetroActivo(retroActivo);
		}

	
	//update
	
	//delete
		@RequestMapping(value = "/work/deleteFamiliarRetroActivoById/{idFamiliarRetroActivo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean deleteFamiliarRetroActivoById(@PathVariable String idFamiliarRetroActivo, HttpSession httpSession)
				throws Exception{

			session ses = new session(httpSession);
			
			if (ses.isValid()) {
				return false;
			}

			return FamiliarRetroActivoDB.deleteFamiliarRetroActivoById(idFamiliarRetroActivo);
		}
	
	
	
}

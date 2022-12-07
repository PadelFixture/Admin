package lib.data.json.sw;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.AcademicosPostulantes;
import lib.db.sw.AcademicosPostulantesDB; 
import lib.security.session;

@Controller
public class AcademicosPostulantesJSON {
	//insert academico
	@RequestMapping(value = "/work/insertAcademicosPostulantes/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertAcademicosPostulantes(@RequestBody AcademicosPostulantes AcademicosPostulantes, HttpSession httpSession)
			throws Exception{

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}

		return AcademicosPostulantesDB.insertAcademicosPostulantes(AcademicosPostulantes);
	}

	// updateAcademicos
	@RequestMapping(value = "/work/updateAcademicosPostulantes/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateAcademicosPostulantes(@RequestBody AcademicosPostulantes AcademicosPostulantes, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return AcademicosPostulantesDB.updateAcademicosPostulantes(AcademicosPostulantes);
	}

	//get academico por idTrabajador
	@RequestMapping(value = "/work/getAcademicosPostulantesByIdTrabajador/{id}", method = {RequestMethod.GET})
	public @ResponseBody AcademicosPostulantes getAcademicosPostulantesByIdTrabajador(@PathVariable int id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		AcademicosPostulantes AcademicosPostulantes = new AcademicosPostulantes();

		if (ses.isValid()) {
			return AcademicosPostulantes;
		}

		AcademicosPostulantes = AcademicosPostulantesDB.getAcademicosPostulantesByIdTrabajador(id);
		return AcademicosPostulantes;

	}

}

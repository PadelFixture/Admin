package lib.data.json.sw;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.sw_academicos;
import lib.db.sw.academicosDB;
import lib.security.session;

@Controller
public class academicosJson {
	//insert academico
	@RequestMapping(value = "/work/insertAcademico/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertAcademico(@RequestBody sw_academicos sw_academicos, HttpSession httpSession)
			throws Exception{

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}

		return academicosDB.insertAcademicos(sw_academicos);
	}

	// updateAcademicos
	@RequestMapping(value = "/work/updateAcademicos/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateAcademicos(@RequestBody sw_academicos sw_academicos, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return academicosDB.updateAcademicos(sw_academicos);
	}

	//get academico por idTrabajador
	@RequestMapping(value = "/work/getAcademicosByIdTrabajador/{id}", method = {RequestMethod.GET})
	public @ResponseBody sw_academicos getAcademicosByIdTrabajador(@PathVariable int id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		sw_academicos sw_academicos = new sw_academicos();

		if (ses.isValid()) {
			return sw_academicos;
		}

		sw_academicos = academicosDB.getAcademicosByIdTrabajador(id);
		return sw_academicos;

	}

}

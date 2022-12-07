package lib.data.json.sw;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.LetraTerminoContrato;

import lib.db.sw.LetraTerminoContratoDB;
import lib.security.session;

@Controller
public class LetraTerminoContratoJSON {

	// get Letra termino contrato
	@RequestMapping(value = "/work/getLetraTerminoContrato/", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<LetraTerminoContrato> getLetraTerminoContrato(HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		ArrayList<LetraTerminoContrato> LetraTerminoContrato = new ArrayList<LetraTerminoContrato>();

		if (ses.isValid()) {
			return LetraTerminoContrato;
		}

		LetraTerminoContrato = LetraTerminoContratoDB.getLetraTerminoContrato();
		return LetraTerminoContrato;

	}// fin get

	// get Letra termino contrato con id
	@RequestMapping(value = "/work/getLetraTerminoContratoByIdLetra/{id}", method = { RequestMethod.GET })
	public @ResponseBody LetraTerminoContrato getLetraTerminoContratoByIdLetra(@PathVariable int id,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		LetraTerminoContrato LetraTerminoContratoByIdLetra = new LetraTerminoContrato();

		if (ses.isValid()) {
			return LetraTerminoContratoByIdLetra;
		}

		LetraTerminoContratoByIdLetra = LetraTerminoContratoDB.getLetraTerminoContratoByIdLetra(id);
		return LetraTerminoContratoByIdLetra;

	}// fin Get con id

	// get Letra termino contrato con id Inciso
	@RequestMapping(value = "/work/getLetraTerminoContratoByIdInciso/{id}", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<LetraTerminoContrato> getLetraTerminoContratoByIdInciso(@PathVariable int id,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<LetraTerminoContrato> getLetraTerminoContratoByIdInciso = new ArrayList<LetraTerminoContrato>();

		if (ses.isValid()) {
			return getLetraTerminoContratoByIdInciso;
		}

		getLetraTerminoContratoByIdInciso = LetraTerminoContratoDB.getLetraTerminoContratoByIdInciso(id);
		return getLetraTerminoContratoByIdInciso;

	}// fin Get con id

}

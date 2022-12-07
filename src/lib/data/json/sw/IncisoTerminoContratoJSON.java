package lib.data.json.sw;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.IncisoTerminoContrato;

import lib.db.sw.IncisoTerminoContratoDB;
import lib.security.session;

@Controller
public class IncisoTerminoContratoJSON {

	
	 // get inciso termino contrato
		@RequestMapping(value = "/work/getIncisoTerminoContrato/", method = {RequestMethod.GET})
		public @ResponseBody ArrayList<IncisoTerminoContrato> getIncisoTerminoContrato( HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<IncisoTerminoContrato> IncisoTerminoContrato = new ArrayList<IncisoTerminoContrato>();

			if (ses.isValid()) {
				return IncisoTerminoContrato;
			}

			IncisoTerminoContrato = IncisoTerminoContratoDB.getIncisoTerminoContrato();
			return IncisoTerminoContrato;

		}// fin get
		
		
		 // get inciso termino contrato con id
		@RequestMapping(value = "/work/getIncisoTerminoContratoByIdInciso/{id}", method = {RequestMethod.GET})
		public @ResponseBody IncisoTerminoContrato getIncisoTerminoContratoByIdInciso(@PathVariable int id, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			IncisoTerminoContrato IncisoTerminoContratoByIdInciso = new IncisoTerminoContrato();

			if (ses.isValid()) {
				return IncisoTerminoContratoByIdInciso;
			}

			IncisoTerminoContratoByIdInciso = IncisoTerminoContratoDB.getIncisoTerminoContratoByIdInciso(id);
			return IncisoTerminoContratoByIdInciso;

		}// fin Get con id
		
		 // get inciso termino contrato con id
		@RequestMapping(value = "/work/getIncisoTerminoContratoByIdArticulo/{id}", method = {RequestMethod.GET})
		public @ResponseBody ArrayList<IncisoTerminoContrato> getIncisoTerminoContratoByIdArticulo(@PathVariable int id, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<IncisoTerminoContrato> IncisoTerminoContratoByIdInciso = new ArrayList<IncisoTerminoContrato>();

			if (ses.isValid()) {
				return IncisoTerminoContratoByIdInciso;
			}

			IncisoTerminoContratoByIdInciso = IncisoTerminoContratoDB.getIncisoTerminoContratoByIdArticulo(id);
			return IncisoTerminoContratoByIdInciso;

		}// fin Get con id
	
}

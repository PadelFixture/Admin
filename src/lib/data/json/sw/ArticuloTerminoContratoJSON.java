package lib.data.json.sw;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.ArticuloTerminoContrato;
import lib.db.sw.ArticuloTerminoContratoDB;
import lib.security.session;


@Controller
public class ArticuloTerminoContratoJSON {

	 // get Articulo Termino Contrato
	@RequestMapping(value = "/work/getArticuloTerminoContrato/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<ArticuloTerminoContrato> getArticuloTerminoContrato( HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<ArticuloTerminoContrato> ArticuloTerminoContrato = new ArrayList<ArticuloTerminoContrato>();

		if (ses.isValid()) {
			return ArticuloTerminoContrato;
		}

		ArticuloTerminoContrato = ArticuloTerminoContratoDB.getArticuloTerminoContrato();
		return ArticuloTerminoContrato;

	}// fin get
	
	 // get Articulo Termino Contrato con id
	@RequestMapping(value = "/work/getArticuloTerminoContratoByIdArticulo/{id}", method = {RequestMethod.GET})
	public @ResponseBody ArticuloTerminoContrato getArticuloTerminoContratoByIdArticulo(@PathVariable int id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArticuloTerminoContrato ArticuloTerminoContratoByIdArticulo = new ArticuloTerminoContrato();

		if (ses.isValid()) {
			return ArticuloTerminoContratoByIdArticulo;
		}

		ArticuloTerminoContratoByIdArticulo = ArticuloTerminoContratoDB.getArticuloTerminoContratoByIdArticulo(id);
		return ArticuloTerminoContratoByIdArticulo;

	}// fin Get con id
	
	
	
}

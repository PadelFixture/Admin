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

import lib.classSW.cuentaBancaria;
import lib.db.sw.cuentaBancariaDB;
import lib.security.session;

@Controller
public class cuentaBancariaJson {

	//Insert Cuenta Bancaria
	@RequestMapping(value = "/work/insertCuentaBancaria/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertCuentaBancaria(@RequestBody cuentaBancaria cuentaBancaria, HttpSession httpSession) throws Exception{

		session ses = new session(httpSession);
		if(ses.isValid()){
			return false;
		}

		return cuentaBancariaDB.insertCuentaBancaria(cuentaBancaria);
	}

	//Actualizar Cuenta Bancaria
	@RequestMapping(value = "/work/updateCuentaBancaria/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateCuentaBancaria(@RequestBody cuentaBancaria cuentaBancaria,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return cuentaBancariaDB.updateCuentaBancaria(cuentaBancaria);
	}


	//Actualizar o Insertar Cuenta Bancaria
	@RequestMapping(value = "/work/updateOrInsertCuentaBancaria/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateOrInsertCuentaBancaria(@RequestBody cuentaBancaria cuentaBancaria,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return cuentaBancariaDB.updateOrInsertCuentaBancaria(cuentaBancaria);
	}


	//get cuenta bancaria por id
	@RequestMapping(value = "/work/getCuentaBancaria/{id}", method = {RequestMethod.GET})
	public @ResponseBody cuentaBancaria getCuentaBancaria(@PathVariable int id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		cuentaBancaria cuentaBancaria = new cuentaBancaria();

		if (ses.isValid()) {
			return cuentaBancaria;
		}

		cuentaBancaria = cuentaBancariaDB.getCuentaBancaria(id);
		return cuentaBancaria;

	}

	//get cuenta bancaria por id trabajador
	@RequestMapping(value = "/work/getCuentaBancariaByIdTrabajador/{id}", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<cuentaBancaria> getCuentaBancariaByIdTrabajador(@PathVariable String id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<cuentaBancaria> cuentaBancaria = new ArrayList<cuentaBancaria>();

		if (ses.isValid()) {
			return cuentaBancaria;
		}

		cuentaBancaria = cuentaBancariaDB.getCuentaBancariaByTrabajador(id);
		return cuentaBancaria;

	}

	
	//get cuenta bancaria por id trabajador
	@RequestMapping(value = "/work/getCuentaBancariaByCodTrabajador/{cod}", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<cuentaBancaria> getCuentaBancariaByCodTrabajador(@PathVariable String cod, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<cuentaBancaria> cuentaBancaria = new ArrayList<cuentaBancaria>();

			if (ses.isValid()) {
				return cuentaBancaria;
			}

			return cuentaBancariaDB.getCuentaBancariaByCodTrabajador(cod);

		}


}

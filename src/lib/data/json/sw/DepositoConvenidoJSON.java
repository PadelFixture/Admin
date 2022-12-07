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

import lib.ClassSASW.parametros;
import lib.db.sw.DepositoConvenidoDB;
import lib.security.session;
@Controller
public class DepositoConvenidoJSON {
	@RequestMapping(value = "/work/DepositosConvenidos/createDeposito/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createDeposito(@RequestBody parametros param,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }
          resp = DepositoConvenidoDB.createParam(param, "DEPOSITO_CONVENIDO");
        return resp;
    }
	//Obtener Todas las Empresas
    @RequestMapping(value = "/work/DepositosConvenidos/getDepositos/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<parametros> getDeposito( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<parametros> params = new ArrayList<parametros>();
		params = DepositoConvenidoDB.getDepositosConvenidos();
		if (ses.isValid()) {
			return params;
		}		
		return params;
	}
	@RequestMapping(value = "/work/DepositosConvenidos/getDepositoById/{id}", method = {RequestMethod.GET})
	public @ResponseBody parametros getDepositoById(@PathVariable int id , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		parametros params = new parametros();
		params = DepositoConvenidoDB.getDepositoConvenidoById(""+id);
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	@RequestMapping(value = "/work/DepositosConvenidos/updateDeposito/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateDeposito(@RequestBody parametros param,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
    	 param.setCodigo("DEPOSITO_CONVENIDO");
    	 
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		
		resp = DepositoConvenidoDB.updateParam(param);
        return resp;
	}
	@RequestMapping(value = "/work/DepositosConvenidos/deleteDeposito/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteDeposito(@PathVariable int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
						
		if (ses.isValid()) {
			return false;
		}
						
		return DepositoConvenidoDB.deleteParById("DEPOSITO_CONVENIDO", ""+id);

	}
}

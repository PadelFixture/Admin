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
import lib.classSW.Empresa;
import lib.db.sw.EmpresaDB;
import lib.security.session;

@Controller
public class EmpresaJSON {
	@RequestMapping(value = "/work/Empresas/createEmpresa/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createEmpresa(@RequestBody Empresa e,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }     
          resp = EmpresaDB.createEmpresa(e);
        return resp;
    }
	//Obtener Todas las Empresas
    @RequestMapping(value = "/work/Empresas/getEmpresas/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<Empresa> getEmpresas( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<Empresa> params = new ArrayList<Empresa>();
		params = EmpresaDB.getEmpresas();
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
  //Obtener Todas las Empresas
    @RequestMapping(value = "/work/Empresas/getEmpresasj/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<Empresa> getEmpresasj( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<Empresa> params = new ArrayList<Empresa>();
		params = EmpresaDB.getEmpresasj();
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	@RequestMapping(value = "/work/Empresas/getEmpresaById/{id}", method = {RequestMethod.GET})
	public @ResponseBody Empresa getEmpresaById(@PathVariable int id , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		Empresa e = new Empresa();
		e = EmpresaDB.getEmpresaById(id);
		if (ses.isValid()) {
			return e;
		}		
		return e;

	}
	@RequestMapping(value = "/work/Empresas/getEmpresaBySociedad/{id}", method = {RequestMethod.GET})
	public @ResponseBody Empresa getEmpresaBySociedad(@PathVariable String soc , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		Empresa e = new Empresa();
		e = EmpresaDB.getEmpresaBySociedad(soc);
		if (ses.isValid()) {
			return e;
		}		
		return e;

	}
	@RequestMapping(value = "/work/Empresas/updateEmpresa/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateEmpresa(@RequestBody Empresa e,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
    
    	 
		session ses = new session(httpSession);
		int idUsuario=ses.getIdUser();
		if (ses.isValid()) {
			return false;
		}
		
		resp = EmpresaDB.updateEmpresa(e, idUsuario);
        return resp;
	}
	/*@RequestMapping(value = "/work/Empresas/deleteEmpresaById/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteEmpresaById(@PathVariable int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
						
		if (ses.isValid()) {
			return false;
		}
						
		return EmpresaDB.deleteEmpresaById(id);

	}
	@RequestMapping(value = "/work/Empresas/deleteEmpresaBySociedad/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteEmpresaBySociedad(@PathVariable String soc ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
						
		if (ses.isValid()) {
			return false;
		}
						
		return EmpresaDB.deleteEmpresaBySociedad(soc);

	}*/
}

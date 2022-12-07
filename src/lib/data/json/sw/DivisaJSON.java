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

import lib.classSW.Divisas;

import lib.db.sw.DivisaDB;
import lib.security.session;

@Controller
public class DivisaJSON {
	@RequestMapping(value = "/work/divisas/createDivisa/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean insertDivisa(@RequestBody Divisas divisa,HttpSession httpSession) throws Exception 
    {
    	System.out.println("dentro del insert");
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {
                 return false;
          }     
          resp = DivisaDB.createDivisa(divisa);
        return resp;
    }
    @RequestMapping(value = "/work/divisas/updateDivisa/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateDivisa(@RequestBody Divisas divisa,HttpSession httpSession) throws Exception {
    	boolean resp = false;
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			return false;
		}
		System.out.println("pasé validación Divisa");
		 resp = DivisaDB.updateDivisa(divisa);
		 System.out.println("pasé update Divisa");
	     return resp;
	}
    
    //Obtener Divisa por Id
    @RequestMapping(value = "/work/divisas/readDivisa/{id}", method = {RequestMethod.GET})
		public @ResponseBody Divisas readAFP(@PathVariable("id") int id, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			
			Divisas divisa = DivisaDB.getDivisaById(id);
			if (ses.isValid()) {
				return divisa;
				}			
			return divisa;

		}
    @RequestMapping(value = "/work/divisas/getBlankDivisa/", method = {RequestMethod.GET})
		public @ResponseBody Divisas getBlankDivisa(HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			Divisas divisa=new Divisas();
			if (ses.isValid()) {
				return divisa;
				}		
			 divisa = DivisaDB.getBlankDivisa();

			return divisa;

		}
    //Obtener Todos los Documentos
    @RequestMapping(value = "/work/divisas/getDivisas/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<Divisas> getDivisas( HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<Divisas> divisas = new ArrayList<Divisas>();
		
		if (ses.isValid()) {
			return divisas;
		}
		divisas = DivisaDB.getAllDivisas();
		return divisas;
	}
    @RequestMapping(value = "/work/divisas/deleteDivisa/{id}", method = {RequestMethod.PUT})
   	public @ResponseBody boolean deleteDivisas(@PathVariable("id") int id ,HttpSession httpSession) throws Exception {
   						
   		session ses = new session(httpSession);
   						
   		if (ses.isValid()) {
   			return false;
   		}
   						
   		return DivisaDB.deleteDivisaById(id);

   	}
}

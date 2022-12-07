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



import lib.classSW.AFP;
import lib.classSW.TramoCarga;
import lib.classSW.periodoafp;
import lib.db.sw.AFPDB;
import lib.db.sw.TramoCargaDB;
import lib.security.session;

@Controller
public class AFPsJSON {
	   //Crear AFP
    @RequestMapping(value = "/work/AFPs/createAFP/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean insertAFP(@RequestBody AFP afp,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }     
          resp = AFPDB.createAFP(afp);
        return resp;
    }
    @RequestMapping(value = "/work/AFPs/updateAFP/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateAFP(@RequestBody AFP afp,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		
		resp = AFPDB.updateAFP(afp);
        return resp;
	}
    @RequestMapping(value = "/work/AFPs/updateAFPMas/{periodo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateAFPMas(@PathVariable("periodo") String periodo,@RequestBody ArrayList<AFP> afps,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		
		resp = AFPDB.updateAFPMas(afps,periodo);
        return resp;
	}
    //Obtener AFP por Id
    @RequestMapping(value = "/work/AFPs/readAFP/{id}", method = {RequestMethod.GET})
		public @ResponseBody AFP readAFP(@PathVariable("id") int id, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			
			AFP afp = AFPDB.getAFPById(id);
			if (ses.isValid()) {
				return afp;
				}			
			return afp;
		}
   
    //Obtener AFP por Id
    @RequestMapping(value = "/work/AFPs/getBlankAFP/", method = {RequestMethod.GET})
		public @ResponseBody AFP getBlankAFP(HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			AFP afp = AFPDB.getBlankAFP();
			
			if (ses.isValid()) {
				return afp;
				}			
			return afp;
		}
  //Obtener Todas las AFP
    @RequestMapping(value = "/work/AFPs/getAFPs/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<AFP> getAFPs( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<AFP> afps = new ArrayList<AFP>();
		afps = AFPDB.getAllAFPs();
		if (ses.isValid()) {
			return afps;
		}		
		return afps;
	}
    @RequestMapping(value = "/work/AFPs/getAFPsByLastPeriod/", method = {RequestMethod.GET})
   	public @ResponseBody ArrayList<AFP> getAFPsByLastPeriod( HttpSession httpSession) throws Exception {
       	
   		session ses = new session(httpSession);
   		ArrayList<AFP> afps = new ArrayList<AFP>();
   		afps = AFPDB.getAFPsByLastPeriod();
   		if (ses.isValid()) {
   			return afps;
   		}		
   		return afps;
   	}
    @RequestMapping(value = "/work/AFPs/getAFPsByPeriod/{periodo}", method = {RequestMethod.GET})
   	public @ResponseBody ArrayList<AFP> getAFPsByPeriod(@PathVariable("periodo") String periodo, HttpSession httpSession) throws Exception {
       	
   		session ses = new session(httpSession);
   		ArrayList<AFP> afps = new ArrayList<AFP>();
   		afps = AFPDB.getAFPsByPeriod(periodo);
   		if (ses.isValid()) {
   			return afps;
   		}		
   		return afps;
   	}
    @RequestMapping(value = "/work/AFPs/deleteAFPs/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteAFP(@PathVariable int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
						
		if (ses.isValid()) {
			return false;
		}
						
		return AFPDB.deleteAFPById(id);

	}
    @RequestMapping(value = "/work/AFPs/updateAFPsByPeriodo/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateTramosByPeriodo(@RequestBody ArrayList<AFP> afps,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 	
   		session ses = new session(httpSession);
   		if (ses.isValid()) {
   			return false;
   		}
   		
   		resp = AFPDB.updateAFPsByPeriodo(afps);
        return resp;
   	}
    @RequestMapping(value = "/work/AFPs/getPeriodos/", method = {RequestMethod.GET})
  	public @ResponseBody ArrayList<periodoafp> getPeriodos( HttpSession httpSession) throws Exception {
      	
  		session ses = new session(httpSession);
  		ArrayList<periodoafp> periodos = new ArrayList<periodoafp>();
  		periodos = AFPDB.getAllPeriodos();
  		if (ses.isValid()) {
  			return periodos;
  		}		
  		return periodos;

  	}
  	 @RequestMapping(value = "/work/AFPs/compareAFP/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
     public @ResponseBody boolean compareAFP(@RequestBody AFP afp,HttpSession httpSession) throws Exception {
     	 boolean resp = false;
     	 System.out.println("pase");
 		session ses = new session(httpSession);
 		if (ses.isValid()) {
 			return false;
 		}
 		resp = AFPDB.compareAFP(afp);
         return resp;
 	}
  	@RequestMapping(value = "/work/AFPs/compareAddAFP/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean compareAddAFP(@RequestBody AFP afp,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		resp = AFPDB.compareAddAFP(afp);
        return resp;
	}
  	
  	@RequestMapping(value = "/work/ListaafpsTASA/", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<AFP> getTipoLicencia(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<AFP> es = new ArrayList<AFP>();

		if (ses.isValid()) {
			return es;
		}

		es = AFPDB.getAllAFPantalla();
		return es;

	}
  	
    
 
}

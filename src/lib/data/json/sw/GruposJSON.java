package lib.data.json.sw;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import lib.classSW.Grupo;
import lib.classSW.trabajadores;
import lib.db.sw.GrupoDB;

import lib.security.session;
@Controller
public class GruposJSON {
	   //Crear Documento
    @RequestMapping(value = "/work/groups/insertGroup/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean insertGroup(@RequestBody Grupo group,HttpSession httpSession) throws Exception 
    {
    	System.out.println("dentro del insert");
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {
        	  
                 return false;
          }     
          resp = GrupoDB.createGroup(group);
          
        return resp;
           
          
    }
 @RequestMapping(value = "/work/groups/updateGroup/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    
    public @ResponseBody boolean updateGroup(@RequestBody Grupo group,HttpSession httpSession) throws Exception {
	 	boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		resp = GrupoDB.updateGroup(group);
        return resp;
	}
 
 	//Obtener Documento por Id
 		@RequestMapping(value = "/work/groups/getBlankGroup/", method = {RequestMethod.GET})
		public @ResponseBody Grupo getBlankGroup(HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			
			Grupo group = GrupoDB.getBlankGroup();
			if (ses.isValid()) {
				return group;
				}			
			return group;

		}
 		 @RequestMapping(value = "/work/groups/getGroups/", method = {RequestMethod.GET})
 		public @ResponseBody ArrayList<Grupo> getGroups( HttpSession httpSession) throws Exception {

 			session ses = new session(httpSession);
 			ArrayList<Grupo> grupos = new ArrayList<Grupo>();
 			grupos = GrupoDB.getAllGroups();
 			if (ses.isValid()) {
 				return grupos;
 			}
 			return grupos;
 		}
 		 
 		 
 /*--------------relaciones------------------*/
 		 
 		 @RequestMapping(value = "/work/groups/{name}/{workers}", method = {RequestMethod.POST})
  		public @ResponseBody boolean insertGroupWorkers(@PathVariable("name") String name,@PathVariable("workers") String[] workers,  HttpSession httpSession) throws Exception {

  			session ses = new session(httpSession);
  			System.out.println(Arrays.toString(workers));
  			
  			if (ses.isValid()) {
  				return false;
  			}
  		
  			return GrupoDB.insertGroupWorkers(name, workers);
  		
  		}
 		@RequestMapping(value = "/work/groups/getWorkersByGroup/{idgroup}", method = {RequestMethod.GET})
 		public @ResponseBody ArrayList<trabajadores> getWorkersByGroup(@PathVariable("idgroup") int idgroup, HttpSession httpSession) throws Exception {

 			session ses = new session(httpSession);
 			ArrayList<trabajadores> grupos = new ArrayList<trabajadores>();
 			grupos = GrupoDB.getTrabajadoresByGroup(idgroup);
 			if (ses.isValid()) {
 				return grupos;
 			}
 			return grupos;
 		}
		@RequestMapping(value = "/work/gruops/getTrabajadoresByIds/{ids}", method = {RequestMethod.GET})
		public @ResponseBody ArrayList<trabajadores> getTrabajadoresByIds(@PathVariable("ids") String[] ids ,HttpSession httpSession) throws Exception {
				
			session ses = new session(httpSession);
			ArrayList<trabajadores> trabajadores =new ArrayList<trabajadores>() ;
				
			if (ses.isValid()) {
				return trabajadores;
			}
				
			System.out.println(Arrays.toString(ids));

			return GrupoDB.getTrabajadoresByIds(ids);
		}
		@RequestMapping(value = "/work/gruops/getTrabajadorByCode/{code}", method = {RequestMethod.GET})
		public @ResponseBody trabajadores getTrabajadoresByCode(@PathVariable("code") String code ,HttpSession httpSession) throws Exception {
				
			session ses = new session(httpSession);
			trabajadores t=new trabajadores();
				
			if (ses.isValid()) {
				return t;
			}
				

			return GrupoDB.getTrabajadorByCode(code);
		}
		@RequestMapping(value = "/work/getTrabajadores/", method = {RequestMethod.GET})
		public @ResponseBody ArrayList<trabajadores> getTrabajadores(HttpSession httpSession) throws Exception {
				
			session ses = new session(httpSession);
			ArrayList<trabajadores> trabajadores =new ArrayList<trabajadores>() ;
				
			if (ses.isValid()) {
				return trabajadores;
			}
				
			

			return GrupoDB.getTrabajadores();
		}
	
		@RequestMapping(value = "/work/gruops/getTrabajadorByCodeFiniquito/{code}", method = {RequestMethod.GET})
		public @ResponseBody trabajadores getTrabajadoresByCodeFiniquito(@PathVariable int code ,HttpSession httpSession) throws Exception {
				
			session ses = new session(httpSession);
			trabajadores t=new trabajadores();
				
			if (ses.isValid()) {
				return t;
			}
				

			return GrupoDB.getTrabajadoresByCodeFiniquito(code);
		}
 		 
 		 
}

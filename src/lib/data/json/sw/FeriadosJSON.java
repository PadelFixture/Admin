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
import lib.classSW.Feriado;
import lib.db.sw.FeriadoDB;
import lib.security.session;
@Controller
public class FeriadosJSON {
	   //Crear Documento
    @RequestMapping(value = "/work/holidays/createHoliday/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean insertHoliday(@RequestBody Feriado holiday,HttpSession httpSession) throws Exception 
    {
    	
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {
                 return false;
          }     
          resp = FeriadoDB.createHoliday(holiday);
        return resp;
    }
    @RequestMapping(value = "/work/holidays/updateHoliday/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateHoliday(@RequestBody Feriado holiday,HttpSession httpSession) throws Exception {
    	boolean resp = false;
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			return false;
		}
	
		 resp = FeriadoDB.updateHoliday(holiday);
		
	     return resp;
	}
    
    
    
    //Obtener Feriado por Id
    @RequestMapping(value = "/work/holidays/readHoliday/{id}", method = {RequestMethod.GET})
		public @ResponseBody Feriado readAFP(@PathVariable("id") int id, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			
			Feriado holiday = FeriadoDB.getHolidayById(id);
			if (ses.isValid()) {
				return holiday;
				}			
			return holiday;

		}
    @RequestMapping(value = "/work/holidays/getBlankHoliday/", method = {RequestMethod.GET})
		public @ResponseBody Feriado getBlankHoliday(HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			Feriado holiday=new Feriado();
			if (ses.isValid()) {
				return holiday;
				}		
			 holiday = FeriadoDB.getBlankHoliday();

			return holiday;

		}
    //Obtener Todos los Documentos
    @RequestMapping(value = "/work/holidays/getHolidays/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<Feriado> getHolidays( HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<Feriado> holidays = new ArrayList<Feriado>();
		
		if (ses.isValid()) {
			return holidays;
		}
		holidays = FeriadoDB.getAllHolidays();
		return holidays;
	}
    @RequestMapping(value = "/work/holidays/getHolidaysByYear/{periodo}", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<Feriado> getHolidaysByYear(@PathVariable("periodo") String periodo , HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<Feriado> holidays = new ArrayList<Feriado>();
		
		if (ses.isValid()) {
			return holidays;
		}
		holidays = FeriadoDB.getAllHolidays();
		return holidays;
	}
    @RequestMapping(value = "/work/holidays/deleteHolidays/{id}", method = {RequestMethod.PUT})
   	public @ResponseBody boolean deleteHolidays(@PathVariable("id") int id ,HttpSession httpSession) throws Exception {
   						
   		session ses = new session(httpSession);
   						
   		if (ses.isValid()) {
   			return false;
   		}
   						
   		return FeriadoDB.deleteHolidayById(id);

   	}
    @RequestMapping(value = "/work/holidays/validateHoliday/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
   	public @ResponseBody boolean validateHolidays(@RequestBody Feriado holiday,HttpSession httpSession) throws Exception {
   						
   		session ses = new session(httpSession);
 
   		
   		
   		if (ses.isValid()) {
   			return false;
   		}
   						
   		return FeriadoDB.validateHoliday(holiday);

   	}
}

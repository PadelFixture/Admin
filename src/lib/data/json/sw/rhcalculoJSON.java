package lib.data.json.sw;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.RHCalculo;
import lib.classSW.UpdateTrabajadorHD;
import lib.db.sw.rhcalculoDB;
import lib.security.session;

@Controller
public class rhcalculoJSON {
	 @RequestMapping(value = "/work/rhcalculo/updateRHCalculo/{periodo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody boolean updateRHCalculo(@RequestBody RHCalculo calculo,HttpSession httpSession,@PathVariable int periodo) throws Exception {
		
		 boolean resp = false;
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}
			calculo.setIdUsuario(ses.getIdUser());
			Date d= new Date();
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
			String cadenaFecha= formato.format(d);
			System.out.println(cadenaFecha);
			calculo.setModificado(cadenaFecha);
			
			
			
			
			resp = rhcalculoDB.updateRHCalculo(calculo,periodo);
	        return resp;
		}
	    //Obtener AFP por Id
	    @RequestMapping(value = "/work/rhcalculo/readRHCalculo/{id}", method = {RequestMethod.GET})
			public @ResponseBody RHCalculo readRHCalculo(@PathVariable("id") int id, HttpSession httpSession) throws Exception {

				session ses = new session(httpSession);
				
				RHCalculo solicitud = rhcalculoDB.getRHCalculoById(id);
				if (ses.isValid()) {
					return solicitud;
					}			
				return solicitud;
			}
	    @RequestMapping(value = "/work/rhcalculo/getRHCalculos/{periodo}", method = {RequestMethod.GET})
		public @ResponseBody ArrayList<RHCalculo> getRHCalculos( HttpSession httpSession,@PathVariable int periodo) throws Exception {

			session ses = new session(httpSession);
			
			ArrayList<RHCalculo> rhcs = rhcalculoDB.getAllRHCalculos(periodo);
			if (ses.isValid()) {
				return rhcs;
				}			
			return rhcs;

		}
}

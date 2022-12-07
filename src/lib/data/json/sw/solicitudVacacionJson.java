package lib.data.json.sw;


import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
//import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.Campo;
import lib.classSW.SVExtended;
import lib.classSW.SolicitudVacacionesProgresivas;
import lib.classSW.cva;
import lib.classSW.sociedad;
import lib.classSW.solicitudVacacion;
import lib.db.sw.solicitudVacacionDB;
import lib.security.session;
import lib.struc.filterSql;

@Controller
public class solicitudVacacionJson {
	
	//Obtener Cantidad de Dias Entre dos Fechas
    @RequestMapping(value = "/work/solicitudVacacion/getMesesReconocidos/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<SolicitudVacacionesProgresivas> getMesesReconocidos(HttpServletRequest request) throws Exception {

		//Obtener todos los parametros del URL
		Map<String, String[]> parameters = request.getParameterMap();

		ArrayList<filterSql> filter = new ArrayList<filterSql>();

		//Obtener todos los parametros enviados por el URL
		for (String key : parameters.keySet()) {
			String[] vals = parameters.get(key);
			//Obtener cada uno de los parametros y valores
			for (String val : vals) {
				filterSql fil = new filterSql();
				fil.setCampo(key);
				fil.setValue(val);
				//Añadir campo y valor 
				filter.add(fil);
			}
		}
		
		ArrayList<SolicitudVacacionesProgresivas> vacaciones = new ArrayList<SolicitudVacacionesProgresivas>();
		vacaciones = solicitudVacacionDB.getMesesReconocidos(filter.get(0).getValue(),filter.get(1).getValue());
		return vacaciones;
		
	}
	
    
  //Obtener Cantidad de Dias Entre dos Fechas
    @RequestMapping(value = "/work/solicitudVacacion/getDiasProgresivos/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<SolicitudVacacionesProgresivas> getDiasProgresivos(HttpServletRequest request) throws Exception {

		//Obtener todos los parametros del URL
		Map<String, String[]> parameters = request.getParameterMap();

		ArrayList<filterSql> filter = new ArrayList<filterSql>();

		//Obtener todos los parametros enviados por el URL
		for (String key : parameters.keySet()) {
			String[] vals = parameters.get(key);
			//Obtener cada uno de los parametros y valores
			for (String val : vals) {
				filterSql fil = new filterSql();
				fil.setCampo(key);
				fil.setValue(val);
				//Añadir campo y valor 
				filter.add(fil);
			}
		}
		
		ArrayList<SolicitudVacacionesProgresivas> vacaciones = new ArrayList<SolicitudVacacionesProgresivas>();
		vacaciones = solicitudVacacionDB.getDiasProgresivos(filter.get(0).getValue(),filter.get(1).getValue(),filter.get(2).getValue());
		return vacaciones;
		
	}
	
    
  //Obtener Cantidad de Dias Entre dos Fechas
    @RequestMapping(value = "/work/solicitudVacacion/getMesesTranscurridos/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<SolicitudVacacionesProgresivas> getMesesTranscurridos(HttpServletRequest request) throws Exception {

		//Obtener todos los parametros del URL
		Map<String, String[]> parameters = request.getParameterMap();

		ArrayList<filterSql> filter = new ArrayList<filterSql>();

		//Obtener todos los parametros enviados por el URL
		for (String key : parameters.keySet()) {
			String[] vals = parameters.get(key);
			//Obtener cada uno de los parametros y valores
			for (String val : vals) {
				filterSql fil = new filterSql();
				fil.setCampo(key);
				fil.setValue(val);
				//Añadir campo y valor 
				filter.add(fil);
			}
		}
		
		ArrayList<SolicitudVacacionesProgresivas> vacaciones = new ArrayList<SolicitudVacacionesProgresivas>();
		vacaciones = solicitudVacacionDB.getMesesTranscurridos(filter.get(0).getValue(),"");
		return vacaciones;
		
	}
    
	
	/* Get actual class name to be printed on */
	//static Logger log = Logger.getLogger(solicitudVacacionJson.class.getName());
	
	
//	
//	 @RequestMapping(value = "/work/solicitudVacacion/updateSolicitudVacaciones/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	    public @ResponseBody boolean updateSolicitudVacacion(@RequestBody solicitudVacacion solicitud,HttpSession httpSession) throws Exception {
//	    	 boolean resp = false;
//	    	 
//			session ses = new session(httpSession);
//			if (ses.isValid()) {
//				return false;
//			}
//			
//			resp = solicitudVacacionDB.updateSolicitudVacacion(solicitud);
//	        return resp;
//		}
//	    //Obtener AFP por Id
//	    @RequestMapping(value = "/work/solicitudVacacion/readSolicitudVacaciones/{id}", method = {RequestMethod.GET})
//			public @ResponseBody solicitudVacacion readSolicitudVacacion(@PathVariable("id") int id, HttpSession httpSession) throws Exception {
//
//				session ses = new session(httpSession);
//				
//				solicitudVacacion solicitud = solicitudVacacionDB.getSolicitudVacacionById(id);
//				if (ses.isValid()) {
//					return solicitud;
//					}			
//				return solicitud;
//
//			}
//	    //Obtener AFP por Id
//	    @RequestMapping(value = "/work/solicitudVacacion/getBlankSolicitudVacaciones/", method = {RequestMethod.GET})
//			public @ResponseBody solicitudVacacion getBlankSolicitudVacacion(HttpSession httpSession) throws Exception {
//
//				session ses = new session(httpSession);
//				solicitudVacacion solicitud = solicitudVacacionDB.getBlankSolicitudVacacion();
//				
//				if (ses.isValid()) {
//					return solicitud;
//					}			
//				return solicitud;
//
//			}
//	  //Obtener Todas las solicitudes
	    @RequestMapping(value = "/work/solicitud/getSolicitudVacacionesWithFilter/", method = {RequestMethod.GET})
		public @ResponseBody ArrayList<solicitudVacacion> getSolicitudVacacionesWithFilter(HttpServletRequest request, HttpSession httpSession) throws Exception {

			//Obtener todos los parametros del URL
			Map<String, String[]> parameters = request.getParameterMap();

			ArrayList<filterSql> filter = new ArrayList<filterSql>();

			//Obtener todos los parametros enviados por el URL
			for (String key : parameters.keySet()) {
				String[] vals = parameters.get(key);
				//Obtener cada uno de los parametros y valores
				for (String val : vals) {
					filterSql fil = new filterSql();
					fil.setCampo(key);
					fil.setValue(val);
					//Añadir campo y valor 
					filter.add(fil);
				}
			}
			
			ArrayList<solicitudVacacion> vacaciones = new ArrayList<solicitudVacacion>();
			vacaciones = solicitudVacacionDB.getSolicitudVacacionesWithFilter(filter);
			return vacaciones;
			
		}
		
//		  //Obtener Todas las solicitudes progresivas
		    @RequestMapping(value = "/work/solicitud/getSolicitudVacacionesProgresivasWithFilter/", method = {RequestMethod.GET})
			public @ResponseBody ArrayList<SolicitudVacacionesProgresivas> getSolicitudVacacionesProgresivasWithFilter(HttpServletRequest request, HttpSession httpSession) throws Exception {

				//Obtener todos los parametros del URL
				Map<String, String[]> parameters = request.getParameterMap();

				ArrayList<filterSql> filter = new ArrayList<filterSql>();

				//Obtener todos los parametros enviados por el URL
				for (String key : parameters.keySet()) {
					String[] vals = parameters.get(key);
					//Obtener cada uno de los parametros y valores
					for (String val : vals) {
						filterSql fil = new filterSql();
						fil.setCampo(key);
						fil.setValue(val);
						//Añadir campo y valor 
						filter.add(fil);
					}
				}
				
				ArrayList<SolicitudVacacionesProgresivas> vacaciones = new ArrayList<SolicitudVacacionesProgresivas>();
				vacaciones = solicitudVacacionDB.getSolicitudVacacionesProgresivasWithFilter(filter);
				return vacaciones;
				
			}
		
		
		//Obtener Cantidad de Dias Entre dos Fechas
	    @RequestMapping(value = "/work/solicitudVacacion/getCantidadDiasVacaciones/", method = {RequestMethod.GET})
		public @ResponseBody ArrayList<solicitudVacacion> getCantidadDiasVacaciones(HttpServletRequest request) throws Exception {

			//Obtener todos los parametros del URL
			Map<String, String[]> parameters = request.getParameterMap();

			ArrayList<filterSql> filter = new ArrayList<filterSql>();

			//Obtener todos los parametros enviados por el URL
			for (String key : parameters.keySet()) {
				String[] vals = parameters.get(key);
				//Obtener cada uno de los parametros y valores
				for (String val : vals) {
					filterSql fil = new filterSql();
					fil.setCampo(key);
					fil.setValue(val);
					//Añadir campo y valor 
					filter.add(fil);
				}
			}
			
			ArrayList<solicitudVacacion> vacaciones = new ArrayList<solicitudVacacion>();
			vacaciones = solicitudVacacionDB.getCantidadDiasVacaciones(filter.get(0).getValue(),filter.get(1).getValue());
			return vacaciones;
			
		}
		
		
		//Subir Solicitud de Vacaciones con Comprobante
		@RequestMapping(value = "/work/SolicitudVacaciones/insertSolicitudVacacion/" , method = RequestMethod.PUT)
	    public @ResponseBody boolean insertSolicitudVacacion(@RequestBody solicitudVacacion solicitudVacacion) throws Exception 
	    {

//			log.debug("Hello this is a debug message");
//		    log.info("Hello this is an info message");
			
	        boolean resp = false;

	        resp = solicitudVacacionDB.createSolicitudVacacion(solicitudVacacion);
	        return resp;
	    }
		
		//Subir Solicitud de Vacaciones Progresivas sin Comprobante
		@RequestMapping(value = "/work/SolicitudVacaciones/insertSolicitudVacacionProgresivas/" , method = RequestMethod.PUT)
		public @ResponseBody boolean insertSolicitudVacacionProgresivas(@RequestBody SolicitudVacacionesProgresivas solicitudVacacion) throws Exception 
		{
			boolean resp = false;
			resp = solicitudVacacionDB.createSolicitudVacacionProgresivas(solicitudVacacion);
			return resp;
		}
		
		
		 @RequestMapping(value = "/work/solicitud/getSolicitudes/", method = {RequestMethod.GET})
		 public @ResponseBody ArrayList<solicitudVacacion> getSolicitudes( HttpSession httpSession) throws Exception {
		    	
				session ses = new session(httpSession);
				ArrayList<solicitudVacacion> sols = new ArrayList<solicitudVacacion>();
				sols = solicitudVacacionDB.getSolicitudes();
				if (ses.isValid()) {
					return sols;
				}		
				return sols;

			}
			@RequestMapping(value = "/work/solicitud/getEmpresas/", method = {RequestMethod.GET})
			public @ResponseBody ArrayList<sociedad> getEmpresas( HttpSession httpSession) throws Exception {
		    	
				session ses = new session(httpSession);
				ArrayList<sociedad> sols = new ArrayList<sociedad>();
				sols = solicitudVacacionDB.getEmpresas();
				if (ses.isValid()) {
					return sols;
				}		
				return sols;

			}
			@RequestMapping(value = "/work/solicitud/getZona/{Campo}", method = {RequestMethod.GET})
			public @ResponseBody ArrayList<Campo> getZona(@PathVariable("Campo") String Campo, HttpSession httpSession) throws Exception {
		    	
				session ses = new session(httpSession);
				ArrayList<Campo> zonas = new ArrayList<Campo>();
				zonas = solicitudVacacionDB.getZonas(Campo);
				if (ses.isValid()) {
					return zonas;
				}		
				return zonas;

			}
	    @RequestMapping(value = "/work/solicitudVacacion/deleteSolicitudVacaciones/{id}", method = {RequestMethod.GET})
		public @ResponseBody boolean deleteSolicitudVacacionById(@PathVariable("id") String id) throws Exception {
			return solicitudVacacionDB.deleteSolicitudVacacionById(id);
		}
	    
	    @RequestMapping(value = "/work/solicitudVacacion/deleteSolicitudVacacionesProgresivas/{id}", method = {RequestMethod.GET})
		public @ResponseBody boolean deleteSolicitudVacacionProgresivasById(@PathVariable("id") String id) throws Exception {
			return solicitudVacacionDB.deleteSolicitudVacacionProgresivasById(id);
		}
////relaciones
//
//	    @RequestMapping(value = "/work/solicitudVacacion/createSolicitudVTrabajador/{id}" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
//	    public @ResponseBody boolean insertSolicitudVTrabajador(@PathVariable("id") int id  ,HttpSession httpSession) throws Exception 
//	    {
//	          boolean resp = false;
//	         session ses= new session(httpSession);
//	          if (ses.isValid()) {        	  
//	                 return false;
//	          }     
//	          resp = solicitudVacacionDB.createSolicitudVacacionTrabajador(id);
//	        return resp;
//	    } 
//	    @RequestMapping(value = "/work/solicitudVacacion/deleteSolicitudVTrabajador/{id}", method = {RequestMethod.PUT})
//		public @ResponseBody boolean deleteSolicitudVTrabajador(@PathVariable("id") int id ,HttpSession httpSession) throws Exception {
//							
//			session ses = new session(httpSession);
//							
//			if (ses.isValid()) {
//				return false;
//			}
//							
//			return solicitudVacacionDB.deleteSolicitudVacacionById(id);
//
//		}
	    @RequestMapping(value = "/work/solicitud/GetTrabajadoresBy/{Empresa}/{Campo}/{Grupo}/{Ceco}", method = {RequestMethod.GET})
		public @ResponseBody ArrayList<SVExtended> GetTrabajadoresBy(@PathVariable("Empresa") String Empresa ,@PathVariable("Campo") String Campo ,@PathVariable("Grupo") String Grupo ,@PathVariable("Ceco") String Ceco , HttpSession httpSession) throws Exception {
	    	
			session ses = new session(httpSession);
			ArrayList<SVExtended> lista=new ArrayList<SVExtended>();
			if (ses.isValid()) {
				return lista;
			}
						
			lista= solicitudVacacionDB.getTrabajadoresFiltering(Empresa, Campo, Grupo, Ceco);
			return lista;

		}
	    @RequestMapping(value = "/work/solicitud/getFechaFin/", method = {RequestMethod.POST})
		public @ResponseBody String getFechaFinSolicitud(@RequestBody cva Cva, HttpSession httpSession) throws Exception {
	    	
			session ses = new session(httpSession);
			
			if (ses.isValid()) {
				return "";
			}
						
			return solicitudVacacionDB.getFechaFinal(Cva);

		}
	    
	    
	    @RequestMapping(value = "/work/solicitudVacacion/createSolicitudVacaciones/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody boolean insertSolicitudVacacion(@RequestBody solicitudVacacion solicitud,HttpSession httpSession) throws Exception 
	    {
	          boolean resp = false;
	         session ses= new session(httpSession);
	          if (ses.isValid()) {        	  
	                 return false;
	          }
	          System.out.println(solicitud.getIdTrabajador());
	          System.out.println(solicitud.getIdSolicitud());
	          System.out.println(solicitud.getFechaSolicitud());
	          System.out.println(solicitud.getFechaInicioSolicitud());
	          System.out.println(solicitud.getFechaFinSolicitud());
	          System.out.println(solicitud.getCodTrabajador());
	          
	          
	          
	          
	          
	          resp = solicitudVacacionDB.createSolicitudVacacion2(solicitud);
	        return resp;
	    }
	    
	    
	    
	    @RequestMapping(value = "/work/solicitudVacacion/imprimirSolicitudVacaciones/{id}", method = {RequestMethod.GET})
		public @ResponseBody boolean imprimirSolicitudVacaciones(@PathVariable("id") String id) throws Exception {
			return true;
		}
	    
	    
	    
}

package lib.data.file;

import java.io.InputStream;
import java.sql.Blob;

import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import lib.classSW.SolicitudVacacionesProgresivas;
import lib.classSW.solicitudVacacion;
import lib.db.sw.solicitudVacacionDB;

@Controller
public class SolicitudVacacionesJSON {

	//Subir Solicitud de Vacaciones con Comprobante
	@RequestMapping(value = "/work/SolicitudVacaciones/insertSolicitudVacacion/" ,method = RequestMethod.POST)
    public @ResponseBody boolean insertSolicitudVacacion(@RequestParam("solicitudVacacion") String solicitudVacacion, @RequestParam("comprobante") MultipartFile file) throws Exception 
    {
		
		ObjectMapper mapper = new ObjectMapper();
		solicitudVacacion vacaciones = mapper.readValue(solicitudVacacion, solicitudVacacion.class);

		//Obtener Archivo
		InputStream fileInputStream = file.getInputStream();
		byte[] contents;
		contents = IOUtils.toByteArray(fileInputStream);
		Blob comprobante = new javax.sql.rowset.serial.SerialBlob(contents);
		vacaciones.setComprobante(comprobante);
		
         boolean resp = false;
//         session ses= new session(httpSession);
//          if (ses.isValid()) {        	  
//                 return false;
//          }
        resp = solicitudVacacionDB.createSolicitudVacacion(vacaciones);
        return resp;
    }
	
	//Subir Solicitud de Vacaciones con Comprobante
		@RequestMapping(value = "/work/SolicitudVacaciones/insertSolicitudVacacionProgresivas/" ,method = RequestMethod.POST)
	    public @ResponseBody boolean insertSolicitudVacacionProgresivas(@RequestParam("solicitudVacacion") String SolicitudVacacionesProgresivas, @RequestParam("comprobante") MultipartFile file) throws Exception 
	    {
			
			ObjectMapper mapper = new ObjectMapper();
			SolicitudVacacionesProgresivas vacaciones = mapper.readValue(SolicitudVacacionesProgresivas, SolicitudVacacionesProgresivas.class);

			//Obtener Archivo
			InputStream fileInputStream = file.getInputStream();
			byte[] contents;
			contents = IOUtils.toByteArray(fileInputStream);
			Blob comprobante = new javax.sql.rowset.serial.SerialBlob(contents);
			vacaciones.setComprobante(comprobante);
			
	         boolean resp = false;
//	         session ses= new session(httpSession);
//	          if (ses.isValid()) {        	  
//	                 return false;
//	          }
	        resp = solicitudVacacionDB.createSolicitudVacacionProgresivas(vacaciones);
	        return resp;
	    }
	
	
}

package lib.data.json;

import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lib.SADB.RENDIMIENTO;
import lib.SADB.noIncidencia;
import lib.classSA.detalleNoIncidencia;
import lib.classSA.incidenciaClass;
import lib.classSW.Documentos;
import lib.db.sw.DocumentosDB;
import lib.security.session;
import pdfCreator.PdfToImage;

@Controller
public class IndcidenciaServices {
	@RequestMapping(value = "/AGRO/DETALLE_INCIDENCIA_CODIGO/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody detalleNoIncidencia DETALLE_INCIDENCIA_CODIGO(@PathVariable int id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		detalleNoIncidencia r = new detalleNoIncidencia();
		if (ses.isValid()) {
			return r;
		}
		r = noIncidencia.DETALLE_INCIDENCIA_CODIGO(id);
		return r;
	}
	@RequestMapping(value = "/AGRO/CERRAR_INCIDENCIA/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean CERRAR_INCIDENCIA(@RequestBody detalleNoIncidencia row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return noIncidencia.CERRAR_INCIDENCIA(row, httpSession);
	}
	@RequestMapping(value = "/AGRO/GET_ALL_INCIDENCIA/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<detalleNoIncidencia> GET_ALL_INCIDENCIA( HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<detalleNoIncidencia> r = new ArrayList<detalleNoIncidencia>();
		if (ses.isValid()) {
			return r;
		}
		r = noIncidencia.GET_ALL_INCIDENCIA();
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_INCIDENCIA_CAMPO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<detalleNoIncidencia> GET_RESUMEN_DIGITACION(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<detalleNoIncidencia> r = new ArrayList<detalleNoIncidencia>();
		if (ses.isValid()) {
			return r;
		}
		String campo = request.getParameter("CAMPO");
		r = noIncidencia.GET_INCIDENCIA_CAMPO(campo);
		return r;
	}
}

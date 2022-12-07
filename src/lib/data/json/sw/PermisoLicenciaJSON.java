package lib.data.json.sw;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import SWDB.PermisoLicenciaDB;
import SWDB.impexp_trabajador;
import lib.classSW.tablaPermisoLicencia;
import lib.security.session;

@Controller
public class PermisoLicenciaJSON {

	
	@RequestMapping(value = "/work/PermisoLicencia/LodtablaPermisoLicencia/{codigo},{idAccion},{idEmpresa},{huerto},{zona},{ceco}", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<tablaPermisoLicencia> getTablaPL(@PathVariable String codigo,
			@PathVariable int idAccion, @PathVariable int idEmpresa,
			@PathVariable String huerto,@PathVariable String zona,@PathVariable String ceco,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<tablaPermisoLicencia> r = new ArrayList<tablaPermisoLicencia>();

		if (ses.isValid()) {
			return r;
		}
		r = PermisoLicenciaDB.getTablaPL(codigo, idAccion, idEmpresa,huerto,zona,ceco);

		return r;

	}
	
	
	@RequestMapping(value = "/work/getPermisoLicenciaDocumento", method = RequestMethod.GET)
	public @ResponseBody String getAutorizacion(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		try {
			String idruta = request.getParameter("ruta");

			System.out.println("ruta: {}" + idruta);

			tablaPermisoLicencia var = impexp_trabajador.getRutaTablapermisoLicencia(idruta);

			String nombreArchivo = var.getRuta_archivo();

			String split[] = nombreArchivo.split("/");
			String nombreArchi = split[4];

			File file = new File(var.getRuta_archivo());

			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);
			response.addHeader("Content-disposition", "attachment; filename= " + nombreArchi + "");
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream out = response.getOutputStream();
			out.write(bytes);
			out.flush();
			out.close();

			return "1";

		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	
}

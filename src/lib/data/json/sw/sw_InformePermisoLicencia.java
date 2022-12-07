package lib.data.json.sw;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import SWDB.sw_InformePermisoLicenciaDB;
import lib.classSW.InformePermisoLi;
import lib.security.session;


@Controller
public class sw_InformePermisoLicencia {
	//---------------GENERAR EXCEL PERMISO LICENCIA--------------------------------------------------------
	@RequestMapping(value = "/work/crearExcelIPermisoLicencia/{nombre}/{empresa}/{periodo}/{huerto}/{rolPrivado}", method = { RequestMethod.PUT,
			RequestMethod.POST, RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String generarExcelAvisoInspeccion(
			@PathVariable String nombre,
			@PathVariable String empresa,
			@PathVariable String periodo,
			@PathVariable String huerto,
			@PathVariable int rolPrivado,
			HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);	
	

		String r = "";
		if (ses.isValid()) {
			return "";

		}
		
		
		ArrayList<InformePermisoLi> BuscarTrabajadoresP_L_F = new ArrayList<InformePermisoLi>();
		BuscarTrabajadoresP_L_F = sw_InformePermisoLicenciaDB.buscartrabajadoresInformeP_L_F(empresa, periodo,huerto,rolPrivado);
		
		
		
		if(BuscarTrabajadoresP_L_F.size() >= 1){
			r = sw_InformePermisoLicenciaDB.generarExcelplf(BuscarTrabajadoresP_L_F,nombre);
		}else
		{
			r = "NO DATA";
		}
		
		return r;

	}

}

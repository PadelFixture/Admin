package lib.data.json;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import lib.classSW.tablaPermisoLicencia;
import lib.security.session;

@Controller
public class sw_PermisoyLicencia {
    //// UPDATE FALTAS
	@RequestMapping(value = "/work/UpdateFaltas", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UpdateFaltas(@RequestBody tablaPermisoLicencia row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return SWDB.sw_PermisoyLicenciaDB.UpdateFaltas(row);
	}
	   //// UPDATE LICENCIAS
		@RequestMapping(value = "/work/updateLicencia", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean UpdateLicencias(@RequestBody tablaPermisoLicencia row, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			return SWDB.sw_PermisoyLicenciaDB.UpdateLicencias(row);
		}
		
	//// UPDATE PERMISO SIN GOCE DE SUELDO
			@RequestMapping(value = "/work/updatePermisoSinGoce", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
			public @ResponseBody boolean UpdatePermisoSinGoce(@RequestBody tablaPermisoLicencia row, HttpSession httpSession)
					throws Exception {

				session ses = new session(httpSession);

				if (ses.isValid()) {
					return false;
				}
				return SWDB.sw_PermisoyLicenciaDB.UpdatePermisoSinGoce(row);
			}
			
		//// UPDATE PERMISO CON GOCE DE SUELDO
					@RequestMapping(value = "/work/UpdateConGoceDeSueldo", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
					public @ResponseBody boolean UpdatePermisoConGoce(@RequestBody tablaPermisoLicencia row, HttpSession httpSession)
							throws Exception {

						session ses = new session(httpSession);

						if (ses.isValid()) {
							return false;
						}
						return SWDB.sw_PermisoyLicenciaDB.UpdatePermisoConGoce(row);
					}


}

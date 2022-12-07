package lib.data.json.sw;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lib.classSW.Usuario;
import lib.classSW.VacacionesTrabajador;
import lib.db.sw.UsuarioDB;
import lib.db.sw.VacacionesDB;
import lib.security.session;
import lib.struc.filterSql;

@Controller
public class UsuarioJSON {

	// Obtener Todos los Trabajador con sus respectivos contratos
		@RequestMapping(value = "/work/Usuario/getUsuarios", method = { RequestMethod.GET })
		public @ResponseBody ArrayList<Usuario> getUsuarios(HttpSession httpSession, HttpServletRequest request) throws Exception {

			//Crear la lista de Trabajadores vacia
			ArrayList<Usuario> usuario = new ArrayList<Usuario>();
			session ses = new session(httpSession);

			if (ses.isValid()) {
				return usuario;
			}

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

			usuario = UsuarioDB.getUsuarios(filter);
			return usuario;

		}
	
		
		@RequestMapping(value = "/work/Usuario/existUsuarios", method = { RequestMethod.GET })
		public String existUsuarios(HttpSession httpSession, HttpServletRequest request) throws Exception {

			//Crear la lista de Trabajadores vacia
			ArrayList<Usuario> usuario = new ArrayList<Usuario>();
			session ses = new session(httpSession);

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

			usuario = UsuarioDB.getUsuarios(filter);
			
			if(!usuario.isEmpty()){
				return "exist";
				//return new ModelAndView("redirect:/webApp/Intranet");
			}
			
			return "noexist";
			//return new ModelAndView("redirect:/webApp/login");

		}
	
	
	
	
}

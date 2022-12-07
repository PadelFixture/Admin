package lib.data.json.sw;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import lib.classSW.Tbjdr;
import lib.db.sw.SupervisoresDB;
import lib.db.sw.TbjdrDB;
import lib.security.session;


@Controller
public class TbjdrJSON {
	// Insert Trabajador
		@RequestMapping(value = "/work/Trabajador/CreateTrabajador/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean CreateTrabajador(@RequestBody Tbjdr trabajadores, HttpSession httpSession)
				throws Exception {
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}
			return TbjdrDB.CreateTrabajador(trabajadores);
		}

		// Actualizar Trabajador
		@RequestMapping(value = "/work/Trabajador/upgradeTrabajador/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean upgradeTrabajador(@RequestBody Tbjdr trabajadores, HttpSession httpSession)
				throws Exception {
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}
			return TbjdrDB.updateTrabajador(trabajadores);
		}

		// Borrar Trabajador por Id
		@RequestMapping(value = "/work/Trabajador/deleteTrabajadorByCodigo/{codigo}", method = { RequestMethod.DELETE })
		public @ResponseBody boolean deleteTrabajadorByCodigo(@PathVariable("codigo") String codigo, HttpSession httpSession)
				throws Exception {
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}
			return TbjdrDB.deleteTrabajadorByCodigo(codigo);
		}

		// Obtener Trabajador por Id
		@RequestMapping(value = "/work/Trabajador/getTrabajadorById/{id}", method = { RequestMethod.GET })
		public @ResponseBody Tbjdr getTrabajadorById(@PathVariable("id") String id, HttpSession httpSession)
				throws Exception {
			session ses = new session(httpSession);
			Tbjdr trabajador = new Tbjdr();
			if (ses.isValid()) {
				return trabajador;
			}
			trabajador = TbjdrDB.getTrabajadorById(id);
			return trabajador;

		}

		
		// Obtener idTrabajador por codigo Trabajador
		@RequestMapping(value = "/work/Trabajador/getTrabajadorByCodigo/{codigo}", method = { RequestMethod.GET })
		public @ResponseBody Tbjdr getTrabajadorByCodigo(@PathVariable String codigo, HttpSession httpSession)
				throws Exception {
			session ses = new session(httpSession);
			Tbjdr Trabajador = new Tbjdr();
			if (ses.isValid()) {
				return Trabajador;
			}
			Trabajador = TbjdrDB.getTrabajadorByCodigo(codigo);
			return Trabajador;
		}
		// Obtener trabajadores por Rut
		@RequestMapping(value = "/work/Trabajador/getTrabajadorByRut/{rut:.+}", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody Tbjdr getTrabajadorByRut(@PathVariable String rut, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);
			Tbjdr trabajador = new Tbjdr();
			if (ses.isValid()) {
				return trabajador;
			}
			trabajador = TbjdrDB.getTrabajadorByRut(rut);
			return trabajador;

		}


		// Obtener Todos los Trabajadores
		@RequestMapping(value = "/work/Trabajador/getAllTrabajador", method = { RequestMethod.POST, RequestMethod.GET })
		public @ResponseBody ArrayList<Tbjdr> getAllTrabajador(HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			if (ses.isValid()) {
				return null;
			}
			ArrayList<Tbjdr> trabajadores = new ArrayList<Tbjdr>();
			trabajadores = TbjdrDB.getTrabajadores();
			return trabajadores;

		}
		@RequestMapping(value = "/work/Trabajador/getTrabajadoresByIdGrupo/{idGrupo}", method = { RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE )
		public @ResponseBody ArrayList<Tbjdr> getTrabajadoresByIdGrupo(@PathVariable("idGrupo") int idGrupo,HttpServletRequest request, HttpSession httpSession) throws SQLException {
			session ses = new session(httpSession);
			ArrayList<Tbjdr> ss=new ArrayList<Tbjdr>();
			if (ses.isValid()) {
				return ss;
			}
			ss=TbjdrDB.getTrabajadoresByIdGrupo(idGrupo);
			return ss;
			
		}
		
}

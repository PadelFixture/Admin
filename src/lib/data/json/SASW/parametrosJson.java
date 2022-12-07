package lib.data.json.SASW;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.ClassSASW.MantenedorEspecie;
import lib.ClassSASW.MantenedorGenerico;
import lib.ClassSASW.parametro_especie;
import lib.ClassSASW.parametros;
import lib.SADB.Campo_ESPECIE;
import lib.SADB.Mantenedor_especie;
import lib.SADB.mantenedor_campo;
import lib.classSA.CAMPO;
import lib.classSA.Campo_Especie;
import lib.classSA.Especie_Campo;
import lib.classSA.Sueldos_Cargo;
import lib.classSW.Especie;
import lib.classSW.Parametros_campo;
import lib.classSW.variedades;
import lib.db.SASW.parametrosDB;
import lib.security.session;

@Controller
public class parametrosJson {
	// get
	@RequestMapping(value = "/work/getParametros/{codigo}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<parametros> getParametros(@PathVariable String codigo, HttpSession httpSession)
			throws Exception {
		session ses = new session(httpSession);
		ArrayList<parametros> pm = new ArrayList<parametros>();
		if (ses.isValid()) {
			return pm;
		}
		pm = parametrosDB.getParametros(codigo);
		return pm;
	}
	 @RequestMapping(value = "/work/compareParams/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
     public @ResponseBody boolean compareParams(@RequestBody parametros p,HttpSession httpSession) throws Exception {
     	 boolean resp = false;
     	 System.out.println("pase");
 		session ses = new session(httpSession);
 		if (ses.isValid()) {
 			return false;
 		}
 		resp = parametrosDB.compareParametros(p);
         return resp;
 	}
	// get Parametros por Codigo y Descripcion
	@RequestMapping(value = "/work/getParametrosByCodigoAndDescripcion", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ArrayList<parametros> getParametrosByCodigoAndDescripcion(
			@RequestParam(value = "param[]") String[] paramValues, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<parametros> pm = new ArrayList<parametros>();
		if (ses.isValid()) {
			return pm;
		}

		/* paramValues[1] = codigo , paramValues[2] = descripcion */
		pm = parametrosDB.getParametrosByCodigoAndDescripcion(paramValues[0], paramValues[1]);
		return pm;
	}

	// get Parametros por Codigo y Llave
	@RequestMapping(value = "/work/getParametrosByCodigoAndLlave/{codigo},{llave}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody parametros getParametrosByCodigoAndDescripcion(@PathVariable String codigo,
			@PathVariable String llave, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		parametros pm = new parametros();
		if (ses.isValid()) {
			return pm;
		}

		pm = parametrosDB.getParametrosByCodigoAndLlave(codigo, llave);
		return pm;
	}

	// get Parametros por Array de Codigos
	@RequestMapping(value = "/work/getParametrosByCodigos", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<parametros> getParametrosByCodigos(
			@RequestParam(value = "param[]") String[] arrayCodigos, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<parametros> pm = new ArrayList<parametros>();
		if (ses.isValid()) {
			return pm;
		}

		pm = parametrosDB.getParametrosByCodigos(arrayCodigos);
		return pm;
	}

	// update estado
	@RequestMapping(value = "/work/updateEstadoParam/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateEstadoParam(@RequestBody parametros parametros, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return parametrosDB.updateEstadoParam(parametros);
	}

	// update descripcion
	@RequestMapping(value = "/work/updateDescripcionParam/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateDescripcionParam(@RequestBody parametros parametros, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return parametrosDB.updateDescripcionParam(parametros);
	}
	
	//get Parametros de especies
	@RequestMapping(value = "/AGRO/getParametro_especie/{codigo}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<parametro_especie> getParametro_especie(@PathVariable String codigo, HttpSession httpSession)
			throws Exception {
		session ses = new session(httpSession);
		ArrayList<parametro_especie> pm = new ArrayList<parametro_especie>();
		if (ses.isValid()) {
			return pm;
		}
		pm = parametrosDB.getParametro_especie(codigo);
		return pm;
	}
	//get Mantetnedor Generico
		@RequestMapping(value = "/AGRO/getMantenedorGenerico/{codigo}", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody ArrayList<MantenedorGenerico> getMantenedorGenerico(@PathVariable String codigo, HttpSession httpSession)
				throws Exception {
			session ses = new session(httpSession);
			ArrayList<MantenedorGenerico> pm = new ArrayList<MantenedorGenerico>();
			if (ses.isValid()) {
				return pm;
			}
			pm = parametrosDB.getMantenedorGenerico(codigo);
			return pm;
		}
	//get parametro_especie
		@RequestMapping(value = "/AGRO/getMantenedorEspecie/{codigo}/{codigo2}", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody ArrayList<MantenedorEspecie> getMantenedorEspecie(@PathVariable String codigo, @PathVariable String codigo2, HttpSession httpSession)
				throws Exception {
			session ses = new session(httpSession);
			ArrayList<MantenedorEspecie> pm = new ArrayList<MantenedorEspecie>();
			if (ses.isValid()) {
				return pm;
			}
			pm = Mantenedor_especie.getMantenedorEspecie(codigo, codigo2);
			return pm;
		}
		@RequestMapping(value = "/AGRO/getTabla/{codigo}", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody ArrayList<Tabla> getTabla(@PathVariable String codigo, HttpSession httpSession)
				throws Exception {
			session ses = new session(httpSession);
			ArrayList<Tabla> pm = new ArrayList<Tabla>();
			if (ses.isValid()) {
				return pm;
			}
			pm = Mantenedor_especie.getTabla(codigo);
			return pm;
		}
		//get especie
		@RequestMapping(value = "/AGRO/getEspecie/{codigo}", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody ArrayList<Especie> getEspecie(@PathVariable String codigo, HttpSession httpSession)
				throws Exception {
			session ses = new session(httpSession);
			ArrayList<Especie> pm = new ArrayList<Especie>();
			if (ses.isValid()) {
				return pm;
			}
			pm = Mantenedor_especie.getEspecie(codigo);
			return pm;
		}
		@RequestMapping(value = "/AGRO/getEspecieByCampo/{codigo}", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody ArrayList<variedades> getEspecieByCampo(@PathVariable String codigo, HttpSession httpSession)
				throws Exception {
			session ses = new session(httpSession);
			ArrayList<variedades> pm = new ArrayList<variedades>();
			if (ses.isValid()) {
				return pm;
			}
			pm = Mantenedor_especie.getEspecieByCampo(codigo);
			return pm;
		}
		@RequestMapping(value = "/AGRO/getVariedades_campo_especie/{codigo}/{codigo2}", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody ArrayList<variedades> getVariedades_campo_especie(@PathVariable String codigo, @PathVariable String codigo2, HttpSession httpSession)
				throws Exception {
			session ses = new session(httpSession);
			ArrayList<variedades> pm = new ArrayList<variedades>();
			if (ses.isValid()) {
				return pm;
			}
			pm = Mantenedor_especie.getVariedades_campo_especie(codigo, codigo2);
			return pm;
		}
		@RequestMapping(value = "/work/updateVariedadVariedades/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean updateVariedadVariedades(@RequestBody variedades parametros, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			return Mantenedor_especie.updateVariedadVariedades(parametros);
		}
		@RequestMapping(value = "/AGRO/AddVariedadVariedades/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean AddVariedadVariedades(@RequestBody  variedades row, HttpSession httpSession) throws Exception {
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}
			return Mantenedor_especie.AddVariedadVariedades(row);
		}
		@RequestMapping(value = "/AGRO/updateEstadoVariedades/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean updateME(@RequestBody variedades parametros, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			return Mantenedor_especie.updateEstadoVariedades(parametros);
		}
		@RequestMapping(value = "/work/updateActivoEspecie/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean updateME(@RequestBody MantenedorEspecie parametros, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			return Mantenedor_especie.updateME(parametros);
		}
		@RequestMapping(value = "/work/updateDescripcionEspecie/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean updateDescripcionEspecie(@RequestBody parametros parametros, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			return Mantenedor_especie.updateDescripcionEspecie(parametros);
		}
		@RequestMapping(value = "/work/updateEstadoEspecie/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean updateEstadoEspecie(@RequestBody parametros parametros, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			return Mantenedor_especie.updateEstadoEspecie(parametros);
		}
		//-----------------MANTENEDOR ESPECIE---------
//		insert
		@RequestMapping(value = "/AGRO/ADDMANTENEDORESP/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean insertMG(@RequestBody  MantenedorEspecie row, HttpSession httpSession) throws Exception {
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}
			return Mantenedor_especie.insertME(row);
		}
		@RequestMapping(value = "/work/updateGeoAreaCampo/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean updateGeoAreaCampo(@RequestBody CAMPO CAMPO, HttpSession httpSession)
				throws Exception {
			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			return mantenedor_campo.updateGeoAreaCampo(CAMPO,httpSession);
		}
		@RequestMapping(value = "/AGRO/GETESPECIECAMPO/{codigo}", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody ArrayList<Campo_Especie> getEspecieCampo(@PathVariable String codigo, HttpSession httpSession)
				throws Exception {
			session ses = new session(httpSession);
			ArrayList<Campo_Especie> pm = new ArrayList<Campo_Especie>();
			if (ses.isValid()) {
				return pm;
			}
			pm = Campo_ESPECIE.getEspecieCampo(codigo);
			return pm;
		}
		@RequestMapping(value = "/work/updateEspecieCampo/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean updateEspecieCampo(@RequestBody parametros parametros, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			return Campo_ESPECIE.updateEspecieCampo(parametros);
		}
		@RequestMapping(value = "/AGRO/ADDESPECIECAMPO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean insertEC(@RequestBody  Especie_Campo row, HttpSession httpSession) throws Exception {
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}
			return Campo_ESPECIE.insertEC(row);
		}
		@RequestMapping(value = "/AGRO/GETSUELDOSCARGO/{codigo}", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody ArrayList<Sueldos_Cargo> getSueldosCargo(@PathVariable String codigo, HttpSession httpSession)
				throws Exception {
			session ses = new session(httpSession);
			ArrayList<Sueldos_Cargo> pm = new ArrayList<Sueldos_Cargo>();
			if (ses.isValid()) {
				return pm;
			}
			pm = parametrosDB.getSueldos_Cargo(codigo);
			return pm;
		}
		@RequestMapping(value = "/AGRO/UP_SUELDOSCARGO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean updateSueldosCargo(@RequestBody  Sueldos_Cargo row, HttpSession httpSession) throws Exception {
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}
			return parametrosDB.updateSueldosCargo(row);
		}
		@RequestMapping(value = "/AGRO/INSERT_SUELDOSCARGO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean insertSUELDOCARGO(@RequestBody  Sueldos_Cargo row, HttpSession httpSession) throws Exception {
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}
			return parametrosDB.insertSUELDOCARGO(row);
		}
		@RequestMapping(value = "/AGRO/getTablaParametros_campoByCampo/{campo}", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody ArrayList<Parametros_campo> getTablaParametros_campoByCampo(@PathVariable String campo, HttpSession httpSession)
				throws Exception {
			session ses = new session(httpSession);
			ArrayList<Parametros_campo> pm = new ArrayList<Parametros_campo>();
			if (ses.isValid()) {
				return pm;
			}
			pm = parametrosDB.getTablaParametros_campoByCampo(campo);
			return pm;
		}
		@RequestMapping(value = "/AGRO/getParametros_campoByCampo/{campo}/{tabla}", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody ArrayList<Parametros_campo> getParametros_campoByCampo(@PathVariable String campo, @PathVariable String tabla, HttpSession httpSession)
				throws Exception {
			session ses = new session(httpSession);
			ArrayList<Parametros_campo> pm = new ArrayList<Parametros_campo>();
			if (ses.isValid()) {
				return pm;
			}
			pm = parametrosDB.getParametros_campoByCampo(campo, tabla);
			return pm;
		}
		@RequestMapping(value = "/work/updateParametros_campoByCampo/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean updateParametros_campoByCampo(@RequestBody Parametros_campo parametros, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			return parametrosDB.updateParametros_campoByCampo(parametros);
		}
		@RequestMapping(value = "/AGRO/INSERT_PARAMETROS_CAMPO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean INSERT_PARAMETROS_CAMPO(@RequestBody  Parametros_campo parametros, HttpSession httpSession) throws Exception {
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}
			return parametrosDB.INSERT_PARAMETROS_CAMPO(parametros);
		}
		@RequestMapping(value = "/work/updateParametros_campo/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean updateParametros_campo(@RequestBody Parametros_campo parametros, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			return parametrosDB.updateParametros_campo(parametros);
		}
}

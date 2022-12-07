package cl.expled.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lib.SADB.RECORRIDO;
import lib.classSA.SECTOR;
import lib.classSA.recorrido;
import lib.classSW.AFP;
import lib.classSW.ArticuloTerminoContrato;
import lib.classSW.Comuna;
import lib.classSW.Division;
import lib.classSW.IncisoTerminoContrato;
import lib.classSW.LetraTerminoContrato;
import lib.classSW.Provincia;
import lib.classSW.Region;
import lib.classSW.SubDivision;
import lib.classSW.Turno;
import lib.classSW.sociedad;
import lib.db.simpleagroDB;
import lib.db.sw.AFPDB;
import lib.db.sw.ArticuloTerminoContratoDB;
import lib.db.sw.ComunaDB;
import lib.db.sw.DivisionDB;
import lib.db.sw.IncisoTerminoContratoDB;
import lib.db.sw.LetraTerminoContratoDB;
import lib.db.sw.ProvinciaDB;
import lib.db.sw.RegionDB;
import lib.db.sw.SubDivisionDB;
import lib.db.sw.TurnoDB;
import lib.db.sw.sociedadDB;
import lib.security.session;
import lib.struc.filterSql;

@Controller
public class TrabajadoresController {

	// DivisionDB divisionDB = new DivisionDB();

	@RequestMapping("/colaboradoresJSP")
	public ModelAndView colaboradoresJSP(Model model, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}

		model.addAttribute("menuActual", "Colaboradores");
		model.addAttribute("paginaActual", "Listado de Trabajadores");
		model.addAttribute("content", "colaboradoresJSP");
		model.addAttribute("javaScriptPage", "colaboradoresJs");
		model.addAttribute("idRandom", Math.random());

		return new ModelAndView("layout/_main");
	}

	@RequestMapping("/content/colaboradoresJSP")
	public ModelAndView colaboradoresJSPContent(Model model, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/colaboradoresJSP");
	}
	@RequestMapping("/DinamicaFichaColaboradorSG")
	public ModelAndView DinamicaFichaColaboradorSG(Model model, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}

		model.addAttribute("menuActual", "Colaboradores");
		model.addAttribute("paginaActual", "Dinamica Ficha Colaborador CG");
		model.addAttribute("content", "DinamicaFichaColaboradorSG");
		model.addAttribute("javaScriptPage", "DinamicaFichaColaboradorCG");
		model.addAttribute("idRandom", Math.random());

		return new ModelAndView("layout/_main");
	}

	@RequestMapping("/content/DinamicaFichaColaboradorSG")
	public ModelAndView DinamicaFichaColaboradorSGContent(Model model, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/DinamicaFichaColaboradorSG");
	}
	
	@RequestMapping("/addWorker")
	public ModelAndView addWorker(Model model, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("content", "addWorker");
		model.addAttribute("javaScriptPage", "addWorkerJS");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}

	@RequestMapping("/content/addWorker")
	public ModelAndView addWorkerContent(Model model, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/addWorker");
	}

	@RequestMapping("/detalleTrabajador")
	public ModelAndView detalleTrabajador(Model model, HttpSession httpSession) {

		model.addAttribute("menuActual", "Colaboradores");
		model.addAttribute("paginaActual", "Ficha de Personal");
		model.addAttribute("content", "detalleTrabajador");
		model.addAttribute("javaScriptPage", "detalleTrabajadorJS");
		model.addAttribute("idRandom", Math.random());

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("layout/_main");
	}

	@RequestMapping("/content/detalleTrabajador")
	public ModelAndView detalleTrabajadorContent(Model model, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);

		// sociedades
		List<sociedad> listaSociedad = new ArrayList<>();
		listaSociedad = sociedadDB.getSociedad(new ArrayList<filterSql>());
		// Division
		List<Division> listaDivision = new ArrayList<>();
		listaDivision = DivisionDB.getDivision();
		// SubDivision
		List<SubDivision> listSubDiv = new ArrayList<>();
		listSubDiv = SubDivisionDB.getSubDivision();
		// AFP
		List<AFP> listaAFP = new ArrayList<>();
		listaAFP = AFPDB.getAllAFPs();
		// regiones
		List<Region> listaRegion = new ArrayList<>();
		listaRegion = RegionDB.getAllRegion();
		// provincia
		List<Provincia> listaProvincia = new ArrayList<>();
		listaProvincia = ProvinciaDB.getAllProvincia();
		// comuna
		List<Comuna> listaComuna = new ArrayList<>();
		listaComuna = ComunaDB.getAllComuna();
		// recorrido
		List<recorrido> listaRecorrido = new ArrayList<>();
		listaRecorrido = RECORRIDO.GET_RECORRIDO();
		// sector
		List<SECTOR> listaSector = new ArrayList<>();
		listaSector = simpleagroDB.getSECTOR(httpSession);
		// turnos
		List<Turno> listTurno = TurnoDB.getAllTurnosSimpleWork();

		// Articulos para Termino de Contrato
		List<ArticuloTerminoContrato> listaArticuloTerminoContrato = new ArrayList<ArticuloTerminoContrato>();
		listaArticuloTerminoContrato = ArticuloTerminoContratoDB.getArticuloTerminoContrato();
		model.addAttribute("listaArticuloTerminoContrato", listaArticuloTerminoContrato);

		// Inciso para Termino de Contrato
		List<IncisoTerminoContrato> listaIncisoTerminoContrato = new ArrayList<IncisoTerminoContrato>();
		listaIncisoTerminoContrato = IncisoTerminoContratoDB.getIncisoTerminoContrato();
		model.addAttribute("listaIncisoTerminoContrato", listaIncisoTerminoContrato);

		// Letra para Termino de Contrato
		List<LetraTerminoContrato> listaLetraTerminoContrato = new ArrayList<LetraTerminoContrato>();
		listaLetraTerminoContrato = LetraTerminoContratoDB.getLetraTerminoContrato();
		model.addAttribute("listaLetraTerminoContrato", listaLetraTerminoContrato);

		model.addAttribute("listaDivision", listaDivision);
		model.addAttribute("listaSubDivision", listSubDiv);
		model.addAttribute("listaAFP", listaAFP);
		model.addAttribute("listaRegion", listaRegion);
		model.addAttribute("listaProvincia", listaProvincia);
		model.addAttribute("listaComuna", listaComuna);
		model.addAttribute("listaRecorrido", listaRecorrido);
		model.addAttribute("listaSector", listaSector);
		model.addAttribute("listaSociedad", listaSociedad);
		model.addAttribute("listaTurno", listTurno);

		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/detalleTrabajador");
	}
	
	@RequestMapping("/detallePostulante")
	public ModelAndView detallePostulante(Model model, HttpSession httpSession) {

		model.addAttribute("menuActual", "Reclutamiento");
		model.addAttribute("paginaActual", "Ficha de Postulante");
		model.addAttribute("content", "detallePostulante");
		model.addAttribute("javaScriptPage", "detallePostulante");
		model.addAttribute("idRandom", Math.random());

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("layout/_main");
	}

	@RequestMapping("/content/detallePostulante")
	public ModelAndView detallePostulanteContent(Model model, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);

		// sociedades
		List<sociedad> listaSociedad = new ArrayList<>();
		listaSociedad = sociedadDB.getSociedad(new ArrayList<filterSql>());
		// Division
		List<Division> listaDivision = new ArrayList<>();
		listaDivision = DivisionDB.getDivision();
		// SubDivision
		List<SubDivision> listSubDiv = new ArrayList<>();
		listSubDiv = SubDivisionDB.getSubDivision();
		// AFP
		List<AFP> listaAFP = new ArrayList<>();
		listaAFP = AFPDB.getAllAFPs();
		// regiones
		List<Region> listaRegion = new ArrayList<>();
		listaRegion = RegionDB.getAllRegion();
		// provincia
		List<Provincia> listaProvincia = new ArrayList<>();
		listaProvincia = ProvinciaDB.getAllProvincia();
		// comuna
		List<Comuna> listaComuna = new ArrayList<>();
		listaComuna = ComunaDB.getAllComuna();
		// recorrido
		List<recorrido> listaRecorrido = new ArrayList<>();
		listaRecorrido = RECORRIDO.GET_RECORRIDO();
		// sector
		List<SECTOR> listaSector = new ArrayList<>();
		listaSector = simpleagroDB.getSECTOR(httpSession);
		// turnos
		List<Turno> listTurno = TurnoDB.getAllTurnosSimpleWork();

		// Articulos para Termino de Contrato
		List<ArticuloTerminoContrato> listaArticuloTerminoContrato = new ArrayList<ArticuloTerminoContrato>();
		listaArticuloTerminoContrato = ArticuloTerminoContratoDB.getArticuloTerminoContrato();
		model.addAttribute("listaArticuloTerminoContrato", listaArticuloTerminoContrato);

		// Inciso para Termino de Contrato
		List<IncisoTerminoContrato> listaIncisoTerminoContrato = new ArrayList<IncisoTerminoContrato>();
		listaIncisoTerminoContrato = IncisoTerminoContratoDB.getIncisoTerminoContrato();
		model.addAttribute("listaIncisoTerminoContrato", listaIncisoTerminoContrato);

		// Letra para Termino de Contrato
		List<LetraTerminoContrato> listaLetraTerminoContrato = new ArrayList<LetraTerminoContrato>();
		listaLetraTerminoContrato = LetraTerminoContratoDB.getLetraTerminoContrato();
		model.addAttribute("listaLetraTerminoContrato", listaLetraTerminoContrato);

		model.addAttribute("listaDivision", listaDivision);
		model.addAttribute("listaSubDivision", listSubDiv);
		model.addAttribute("listaAFP", listaAFP);
		model.addAttribute("listaRegion", listaRegion);
		model.addAttribute("listaProvincia", listaProvincia);
		model.addAttribute("listaComuna", listaComuna);
		model.addAttribute("listaRecorrido", listaRecorrido);
		model.addAttribute("listaSector", listaSector);
		model.addAttribute("listaSociedad", listaSociedad);
		model.addAttribute("listaTurno", listTurno);

		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/detallePostulante");
	}

	@RequestMapping("/addTrabajador")
	public ModelAndView addTrabajador(Model model, HttpSession httpSession) {

		model.addAttribute("menuActual", "Colaboradores");
		model.addAttribute("paginaActual", "Agregar Trabajador");
		model.addAttribute("content", "addTrabajador");
		model.addAttribute("javaScriptPage", "addTrabajador");
		model.addAttribute("idRandom", Math.random());

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("layout/_main");
	}

	@RequestMapping("/content/addTrabajador")
	public ModelAndView addTrabajadorContent(Model model, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);

		// sociedades
		List<sociedad> listaSociedad = new ArrayList<>();
		listaSociedad = sociedadDB.getSociedad(new ArrayList<filterSql>());
		// AFP
		List<AFP> listaAFP = new ArrayList<>();
		listaAFP = AFPDB.getAllAFPs();
		// regiones
		List<Region> listaRegion = new ArrayList<>();
		listaRegion = RegionDB.getAllRegion();
		// recorrido
		List<recorrido> listaRecorrido = new ArrayList<>();
		listaRecorrido = RECORRIDO.GET_RECORRIDO();
		// sector
		List<SECTOR> listaSector = new ArrayList<>();
		listaSector = simpleagroDB.getSECTOR(httpSession);
		// Tabla Campo
		// List<Campo> listaCampo = new ArrayList<>();
		// listaCampo = CampoDB.getCampo();
		List<Turno> listTurno = TurnoDB.getAllTurnosSimpleWork();

		model.addAttribute("listaAFP", listaAFP);
		model.addAttribute("listaRegion", listaRegion);
		model.addAttribute("listaRecorrido", listaRecorrido);
		model.addAttribute("listaSector", listaSector);
		model.addAttribute("listaSociedad", listaSociedad);
		// model.addAttribute("listaCampo", listaCampo);
		model.addAttribute("listaTurno", listTurno);

		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/addTrabajador");
	}
	
	
	@RequestMapping("/addPostulanteToTrabajador")
	public ModelAndView addPostulanteToTrabajador(Model model, HttpSession httpSession) {

		model.addAttribute("menuActual", "Colaboradores");
		model.addAttribute("paginaActual", "Agregar Trabajador");
		model.addAttribute("content", "addPostulanteToTrabajador");
		model.addAttribute("javaScriptPage", "addPostulanteToTrabajador");
		model.addAttribute("idRandom", Math.random());

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("layout/_main");
	}

	@RequestMapping("/content/addPostulanteToTrabajador")
	public ModelAndView addPostulanteToTrabajadorContent(Model model, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);

		// sociedades
		List<sociedad> listaSociedad = new ArrayList<>();
		listaSociedad = sociedadDB.getSociedad(new ArrayList<filterSql>());
		// AFP
		List<AFP> listaAFP = new ArrayList<>();
		listaAFP = AFPDB.getAllAFPs();
		// regiones
		List<Region> listaRegion = new ArrayList<>();
		listaRegion = RegionDB.getAllRegion();
		// recorrido
		List<recorrido> listaRecorrido = new ArrayList<>();
		listaRecorrido = RECORRIDO.GET_RECORRIDO();
		// sector
		List<SECTOR> listaSector = new ArrayList<>();
		listaSector = simpleagroDB.getSECTOR(httpSession);
		// Tabla Campo
		// List<Campo> listaCampo = new ArrayList<>();
		// listaCampo = CampoDB.getCampo();
		List<Turno> listTurno = TurnoDB.getAllTurnosSimpleWork();

		model.addAttribute("listaAFP", listaAFP);
		model.addAttribute("listaRegion", listaRegion);
		model.addAttribute("listaRecorrido", listaRecorrido);
		model.addAttribute("listaSector", listaSector);
		model.addAttribute("listaSociedad", listaSociedad);
		// model.addAttribute("listaCampo", listaCampo);
		model.addAttribute("listaTurno", listTurno);

		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/addPostulanteToTrabajador");
	}
	
	
	//
	@RequestMapping("/addTrabajadorMultiEmpresa")
	public ModelAndView addTrabajadorMultiEmpresa(Model model, HttpSession httpSession) {

		model.addAttribute("menuActual", "Colaboradores");
		model.addAttribute("paginaActual", "Trabajador MultiEmpresa");
		model.addAttribute("content", "addTrabajadorMultiEmpresa");
		model.addAttribute("javaScriptPage", "addTrabajadorMultiEmpresa");
		model.addAttribute("idRandom", Math.random());

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("layout/_main");
	}

	@RequestMapping("/content/addTrabajadorMultiEmpresa")
	public ModelAndView addTrabajadorMultiEmpresaContent(Model model, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);

		// sociedades
		List<sociedad> listaSociedad = new ArrayList<>();
		listaSociedad = sociedadDB.getSociedad(new ArrayList<filterSql>());
		// AFP
		List<AFP> listaAFP = new ArrayList<>();
		listaAFP = AFPDB.getAllAFPs();
		// regiones
		List<Region> listaRegion = new ArrayList<>();
		listaRegion = RegionDB.getAllRegion();
		// recorrido
		List<recorrido> listaRecorrido = new ArrayList<>();
		listaRecorrido = RECORRIDO.GET_RECORRIDO();
		// sector
		List<SECTOR> listaSector = new ArrayList<>();
		listaSector = simpleagroDB.getSECTOR(httpSession);
		// Tabla Campo
		// List<Campo> listaCampo = new ArrayList<>();
		// listaCampo = CampoDB.getCampo();
		List<Turno> listTurno = TurnoDB.getAllTurnosSimpleWork();

		model.addAttribute("listaAFP", listaAFP);
		model.addAttribute("listaRegion", listaRegion);
		model.addAttribute("listaRecorrido", listaRecorrido);
		model.addAttribute("listaSector", listaSector);
		model.addAttribute("listaSociedad", listaSociedad);
		// model.addAttribute("listaCampo", listaCampo);
		model.addAttribute("listaTurno", listTurno);

		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/addTrabajadorMultiEmpresa");
	}
	//
	

	@RequestMapping("/DinamicaFichaColaborador")
	public ModelAndView DinamicaFichaColaborador(Model model, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}

		model.addAttribute("menuActual", "Dinamica Ficha Colaborador");
		model.addAttribute("paginaActual", "Dinamica Ficha Colaborador");
		model.addAttribute("content", "DinamicaFichaColaborador");
		model.addAttribute("javaScriptPage", "DinamicaFichaColaborador");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}

	@RequestMapping("/content/DinamicaFichaColaborador")
	public ModelAndView DinamicaFichaColaboradorContent(Model model, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}

		// sociedades
		List<sociedad> listaSociedad = new ArrayList<>();
		listaSociedad = sociedadDB.getSociedad(new ArrayList<filterSql>());
		model.addAttribute("listaSociedad", listaSociedad);

		return new ModelAndView("content/DinamicaFichaColaborador");

	}
	
	@RequestMapping("/addPostulantes")
	public ModelAndView addPostulantes(Model model, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}

		model.addAttribute("menuActual", "Reclutamiento");
		model.addAttribute("paginaActual", "Agregar Postulantes");
		model.addAttribute("content", "addPostulantes");
		model.addAttribute("javaScriptPage", "addPostulantes");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}

	@RequestMapping("/content/addPostulantes")
	public ModelAndView addPostulantesContent(Model model, HttpSession httpSession) throws Exception {

		// sociedades
				List<sociedad> listaSociedad = new ArrayList<>();
				listaSociedad = sociedadDB.getSociedad(new ArrayList<filterSql>());
				// AFP
				List<AFP> listaAFP = new ArrayList<>();
				listaAFP = AFPDB.getAllAFPs();
				// regiones
				List<Region> listaRegion = new ArrayList<>();
				listaRegion = RegionDB.getAllRegion();
				// recorrido
				List<recorrido> listaRecorrido = new ArrayList<>();
				listaRecorrido = RECORRIDO.GET_RECORRIDO();
				// sector
				List<SECTOR> listaSector = new ArrayList<>();
				listaSector = simpleagroDB.getSECTOR(httpSession);
				// Tabla Campo
				// List<Campo> listaCampo = new ArrayList<>();
				// listaCampo = CampoDB.getCampo();
				List<Turno> listTurno = TurnoDB.getAllTurnosSimpleWork();

				model.addAttribute("listaAFP", listaAFP);
				model.addAttribute("listaRegion", listaRegion);
				model.addAttribute("listaRecorrido", listaRecorrido);
				model.addAttribute("listaSector", listaSector);
				model.addAttribute("listaSociedad", listaSociedad);
				// model.addAttribute("listaCampo", listaCampo);
				model.addAttribute("listaTurno", listTurno);
		
		
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}

		return new ModelAndView("content/addPostulantes");

	}
	
	
	@RequestMapping("/ListaPostulantes")
	public ModelAndView ListaPostulantes(Model model, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}

		model.addAttribute("menuActual", "Reclutamiento");
		model.addAttribute("paginaActual", "Listado de Postulantes");
		model.addAttribute("content", "ListaPostulantes");
		model.addAttribute("javaScriptPage", "ListaPostulantes");
		model.addAttribute("idRandom", Math.random());

		return new ModelAndView("layout/_main");
	}

	@RequestMapping("/content/ListaPostulantes")
	public ModelAndView ListaPostulantesContent(Model model, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/ListaPostulantes");
	}
	
	

}

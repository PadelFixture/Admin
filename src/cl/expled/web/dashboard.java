package cl.expled.web;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lib.db.sw.ComunaDB;
import lib.db.sw.ProvinciaDB;
import lib.db.sw.RegionDB;
import lib.security.session;

import lib.classSW.Provincia;
import lib.classSW.Region;
import lib.classSW.Comuna;
/*
 * author: Crunchify.com
 * 
 */

@Controller
public class dashboard {
	
		
	@RequestMapping("/importarDatos")
	public ModelAndView importarDatos(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Importacion");
		model.addAttribute("paginaActual", "Empresas");
		model.addAttribute("content", "importarDatos");
		model.addAttribute("javaScriptPage","empresaJS");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/importarDatos")
	public ModelAndView importarDatosContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/importarDatos");
	}

	@RequestMapping("/exportar")
	public ModelAndView exportar(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("content", "exportar");
		model.addAttribute("javaScriptPage","exportarJS");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/exportar")
	public ModelAndView exportarContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/exportar");
	}
	
	@RequestMapping("/importar")
	public ModelAndView importar(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Importacion");
		model.addAttribute("paginaActual", "Importar Datos");
		model.addAttribute("content", "importar");
		model.addAttribute("javaScriptPage","importar");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/importar")
	public ModelAndView importarContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/importar");
	}
	
	@RequestMapping("/distribuidor")
	public ModelAndView parJSP(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Mantenedores");
		model.addAttribute("paginaActual", "Sub Distribuidor");
		model.addAttribute("content", "distribuidor");
		model.addAttribute("javaScriptPage","distribuidor");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/distribuidor")
	public ModelAndView parJSPContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/distribuidor");
	}
	
	@RequestMapping("/vendedores")
	public ModelAndView vendedores(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		System.out.println(httpSession.getAttribute("USER"));
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Mantenedores");
		model.addAttribute("paginaActual", "Vendedores");
		model.addAttribute("content", "vendedores");
		model.addAttribute("javaScriptPage","vendedores");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/vendedores")
	public ModelAndView vendedoresContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/vendedores");
	}
	
	@RequestMapping("/comercios")
	public ModelAndView comercios(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Comercios");
		model.addAttribute("paginaActual", "Comercios");
		model.addAttribute("content", "comercios");
		model.addAttribute("javaScriptPage","comercios");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/comercios")
	public ModelAndView ComerciosContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/comercios");
	}
	
	@RequestMapping("/usuarios")
	public ModelAndView usuarios(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Usuarios");
		model.addAttribute("paginaActual", "Usuarios");
		model.addAttribute("content", "usuarios");
		model.addAttribute("javaScriptPage","usuarios");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/usuarios")
	public ModelAndView usuariosContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/usuarios");
	}
	
	@RequestMapping("/stock_subdistribuidores")
	public ModelAndView stock_subdistribuidores(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Stock Sub Distribuidores");
		model.addAttribute("paginaActual", "stock_subdistribuidores");
		model.addAttribute("content", "stock_subdistribuidores");
		model.addAttribute("javaScriptPage","stock_subdistribuidores");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/stock_subdistribuidores")
	public ModelAndView stock_subdistribuidoresContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/stock_subdistribuidores");
	}
	
	@RequestMapping("/stock_vendedor")
	public ModelAndView stock_vendedores(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Stock Sub Vendedores");
		model.addAttribute("paginaActual", "stock_vendedor");
		model.addAttribute("content", "stock_vendedor");
		model.addAttribute("javaScriptPage","stock_vendedor");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/stock_vendedor")
	public ModelAndView stock_vendedoresContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/stock_vendedor");
	}
	
	@RequestMapping("/stock_comercio")
	public ModelAndView stock_comercio(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Stock Comercio");
		model.addAttribute("paginaActual", "stock_comercio");
		model.addAttribute("content", "stock_comercio");
		model.addAttribute("javaScriptPage","stock_comercio");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/stock_comercio")
	public ModelAndView stock_comercioContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/stock_comercio");
	}
	
	@RequestMapping("/importarActivacion")
	public ModelAndView araucana(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Importacion");
		model.addAttribute("paginaActual", "Datos Activacion");
		model.addAttribute("content", "importarActivacion");
		model.addAttribute("javaScriptPage","importarActivacion");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/importarActivacion")
	public ModelAndView importarActivacionContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/importarActivacion");
	}
	
	@RequestMapping("/conceptos")
	public ModelAndView andes(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("content", "conceptos");
		model.addAttribute("menuActual", "Mantenedores");
		model.addAttribute("paginaActual", "Conceptos");
		model.addAttribute("javaScriptPage","conceptos");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/conceptos")
	public ModelAndView conceptosContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/conceptos");
	}
	
	@RequestMapping("/puntos_venta")
	public ModelAndView previred(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Habilitaciones 356");
		model.addAttribute("content", "puntos_venta");
		model.addAttribute("javaScriptPage","puntos_venta");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/puntos_venta")
	public ModelAndView puntos_ventaContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/puntos_venta");
	}
	
	@RequestMapping("/crear_calificacion")
	public ModelAndView crear_calificacion(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Administracion");
		model.addAttribute("paginaActual", "Calificacion");
		model.addAttribute("content", "crear_calificacion");
		model.addAttribute("javaScriptPage","crear_calificacion");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/crear_calificacion")
	public ModelAndView crear_calificacionContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/crear_calificacion");
	}
	
	@RequestMapping("/importarStock")
	public ModelAndView importarStock(Model model, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}

		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Importacion");
		model.addAttribute("paginaActual", "Importar Stock");
		model.addAttribute("content", "importarStock");
		model.addAttribute("javaScriptPage", "importarStock");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
		
	}
	@RequestMapping("/content/importarStock")
	public ModelAndView importarStockContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/importarStock");
	}
	@RequestMapping("/map")
	public ModelAndView map(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("google_map", "src='https://maps.googleapis.com/maps/api/js?key=AIzaSyA-gfDufUPg8zkB-VRiVUiudqMfTYWa3GY&libraries=drawing,geometry,places&callback=initMap' async defer");
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Mapa");
		model.addAttribute("paginaActual", "Mapa");
		model.addAttribute("content", "map");
		model.addAttribute("javaScriptPage","map");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/map")
	public ModelAndView mapContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/map");
	}

	@RequestMapping("/dashboard2")
	public ModelAndView dashboard2(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		//model.addAttribute("google_map", "src='https://maps.googleapis.com/maps/api/js?key=AIzaSyA-gfDufUPg8zkB-VRiVUiudqMfTYWa3GY&libraries=drawing,geometry,places&callback=initMap' async defer");
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Puntos de Venta");
		model.addAttribute("content", "dashboard2");
		model.addAttribute("javaScriptPage","dashboard2");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/dashboard2")
	public ModelAndView dashboard2Content(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/dashboard2");
	}
	@RequestMapping("/MenuPerfil")
	public ModelAndView MenuPerfil(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Mantenedores");
		model.addAttribute("selectPicker", "src='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/js/bootstrap-select.min.js'");
		model.addAttribute("stackpath", "src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js'");
		model.addAttribute("paginaActual", "Menu Perfil");
		model.addAttribute("content", "MenuPerfil");
		model.addAttribute("javaScriptPage","MenuPerfil");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/MenuPerfil")
	public ModelAndView MenuPerfilContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/MenuPerfil");
	}
	@RequestMapping("/dashboard3")
	public ModelAndView dashboard3(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Habilitaciones 374");
		model.addAttribute("content", "dashboard3");
		model.addAttribute("javaScriptPage","dashboard3");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/dashboard3")
	public ModelAndView dashboard3Content(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/dashboard3");
	}
	
	@RequestMapping("/ranking")
	public ModelAndView ranking(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Ranking");
		model.addAttribute("paginaActual", "Ranking");
		model.addAttribute("content", "ranking");
		model.addAttribute("javaScriptPage","ranking");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/ranking")
	public ModelAndView rankingContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/ranking");
	}
	
	@RequestMapping("/venta_unica")
	public ModelAndView venta_unica(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Venta Sim individual");
		model.addAttribute("paginaActual", "Venta Sim individual");
		model.addAttribute("content", "venta_unica");
		model.addAttribute("javaScriptPage","venta_unica");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/venta_unica")
	public ModelAndView venta_unicaContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/venta_unica");
	}
	
	@RequestMapping("/venta_rango")
	public ModelAndView venta_rango(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Venta Sim rangos");
		model.addAttribute("paginaActual", "Venta Sim rangos");
		model.addAttribute("content", "venta_rango");
		model.addAttribute("javaScriptPage","venta_rango");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/venta_rango")
	public ModelAndView venta_rangoContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/venta_rango");
	}
	@RequestMapping("/distribuidor_stock")
	public ModelAndView distribuidor_stock(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Consulta Stock");
		model.addAttribute("paginaActual", "Stock Distribuidores");
		model.addAttribute("content", "distribuidor_stock");
		model.addAttribute("javaScriptPage","distribuidor_stock");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/distribuidor_stock")
	public ModelAndView distribuidor_stockContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/distribuidor_stock");
	}
	@RequestMapping("/importacion4")
	public ModelAndView importacion4(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Importacion");
		model.addAttribute("paginaActual", "Importacion");
		model.addAttribute("content", "importacion4");
		model.addAttribute("javaScriptPage","importacion4");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/importacion4")
	public ModelAndView importacion4Content(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/importacion4");
	}
	@RequestMapping("/perfil")
	public ModelAndView perfil(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Administracion");
		model.addAttribute("paginaActual", "Perfil");
		model.addAttribute("content", "perfil");
		model.addAttribute("javaScriptPage","perfil");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/perfil")
	public ModelAndView perfilContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/perfil");
	}
	
	@RequestMapping("/devolucion")
	public ModelAndView devolucion(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Devolucion");
		model.addAttribute("paginaActual", "Devolucion");
		model.addAttribute("content", "devolucion");
		model.addAttribute("javaScriptPage","devolucion");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/devolucion")
	public ModelAndView devolucionContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		} 
		return new ModelAndView("content/devolucion");
	}
	@RequestMapping("/CargaMasiva")
	public ModelAndView CargaMasiva(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Administracion");
		model.addAttribute("paginaActual", "Carga Masiva");
		model.addAttribute("content", "CargaMasiva");
		model.addAttribute("javaScriptPage","CargaMasiva");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/CargaMasiva")
	public ModelAndView CargaMasivaContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/CargaMasiva");
	}
	@RequestMapping("/ActivacionesDiarias")
	public ModelAndView ActivacionesDiarias(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Administracion");
		model.addAttribute("paginaActual", "Activaciones Diarias");
		model.addAttribute("content", "ActivacionesDiarias");
		model.addAttribute("javaScriptPage","ActivacionesDiarias");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/ActivacionesDiarias")
	public ModelAndView ActivacionesDiariasContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/ActivacionesDiarias");
	}
	@RequestMapping("/VentasOnline")
	public ModelAndView VentasOnline(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Ventas Online");
		model.addAttribute("content", "VentasOnline");
		model.addAttribute("javaScriptPage","VentasOnline");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/VentasOnline")
	public ModelAndView VentasOnlineContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/VentasOnline");
	}
	
	@RequestMapping("/VentasOnlineFull")
	public ModelAndView VentasOnlineFull(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Ventas Online Full");
		model.addAttribute("content", "VentasOnlineFull");
		model.addAttribute("javaScriptPage","VentasOnlineFull");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/VentasOnlineFull")
	public ModelAndView VentasOnlineFullContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/VentasOnlineFull");
	}
	@RequestMapping("/VentasOneClick")
	public ModelAndView VentasOneClick(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Ventas One Click");
		model.addAttribute("content", "VentasOneClick");
		model.addAttribute("javaScriptPage","VentasOneClick");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/VentasOneClick")
	public ModelAndView VentasOneClickContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/VentasOneClick");
	}
	@RequestMapping("/UbicacionComercioFull")
	public ModelAndView UbicacionComercioFull(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Ubicacion Comercio Full");
		model.addAttribute("content", "UbicacionComercioFull");
		model.addAttribute("javaScriptPage","UbicacionComercioFull");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/UbicacionComercioFull")//VentasComercios
	public ModelAndView UbicacionComercioFullContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/UbicacionComercioFull");
	}
	
	@RequestMapping("/UbicacionComercio")
	public ModelAndView UbicacionComercio(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Ubicacion Comercio");
		model.addAttribute("content", "UbicacionComercio");
		model.addAttribute("javaScriptPage","UbicacionComercio");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/UbicacionComercio")//VentasComercios
	public ModelAndView UbicacionComercioContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/UbicacionComercio");
	}
	
	@RequestMapping("/VentasComercios")
	public ModelAndView VentasComercios(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Ventas Comercios");
		model.addAttribute("content", "VentasComercios");
		model.addAttribute("javaScriptPage","VentasComercios");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/VentasComercios")
	public ModelAndView VentasComerciosContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/VentasComercios");
	}
	@RequestMapping("/VerStock")
	public ModelAndView VerStock(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Ver Stock");
		model.addAttribute("content", "VerStock");
		model.addAttribute("javaScriptPage","VerStock");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/VerStock")
	public ModelAndView VerStockContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/VerStock");
	}
	@RequestMapping("/ReporteActivacionesDiarias")
	public ModelAndView ReporteActivacionesDiarias(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Reporte Activacione sDiarias");
		model.addAttribute("content", "ReporteActivacionesDiarias");
		model.addAttribute("javaScriptPage","ReporteActivacionesDiarias");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/ReporteActivacionesDiarias")//distribuidorPrincipal
	public ModelAndView ReporteActivacionesDiariasContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/ReporteActivacionesDiarias");
	}
	@RequestMapping("/distribuidorPrincipal")
	public ModelAndView distribuidorPrincipal(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Administracion");
		model.addAttribute("paginaActual", "Dsitribuidor");
		model.addAttribute("content", "distribuidorPrincipal");
		model.addAttribute("javaScriptPage","distribuidorPrincipal");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/distribuidorPrincipal")//distribuidorPrincipal
	public ModelAndView distribuidorPrincipalContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/distribuidorPrincipal");
	}
	@RequestMapping("/CHComercio")
	public ModelAndView ConsultaHabilitados(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Administracion");
		model.addAttribute("paginaActual", "Consulta Habilitados Comercio");
		model.addAttribute("content", "CHComercio");
		model.addAttribute("javaScriptPage","CHComercio");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/CHComercio")//CHDistribuidor
	public ModelAndView CHComercioContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/CHComercio");
	}
	@RequestMapping("/CHDistribuidor")
	public ModelAndView CHDistribuidor(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Administracion");
		model.addAttribute("paginaActual", "Consulta Habilitados Distribuidor");
		model.addAttribute("content", "CHDistribuidor");
		model.addAttribute("javaScriptPage","CHDistribuidor");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/CHDistribuidor")//CHSubDistrinuidores
	public ModelAndView CHDistribuidorContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/CHDistribuidor");
	}
	@RequestMapping("/CHSubDistrinuidores")
	public ModelAndView CHSubDistrinuidores(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Administracion");
		model.addAttribute("paginaActual", "Consulta Habilitados SubDistrinuidores");
		model.addAttribute("content", "CHSubDistrinuidores");
		model.addAttribute("javaScriptPage","CHSubDistrinuidores");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/CHSubDistrinuidores")
	public ModelAndView CHSubDistrinuidoresContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/CHSubDistrinuidores");
	}
	@RequestMapping("/CHVendedor")
	public ModelAndView CHVendedor(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Administracion");
		model.addAttribute("paginaActual", "Consulta Habilitados Vendedor");
		model.addAttribute("content", "CHVendedor");
		model.addAttribute("javaScriptPage","CHVendedor");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/CHVendedor")
	public ModelAndView CHVendedorContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/CHVendedor");
	}
	@RequestMapping("/CSNComercio")
	public ModelAndView CSNComercio(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Administracion");
		model.addAttribute("paginaActual", "Consulta Stock");
		model.addAttribute("content", "CSNComercio");
		model.addAttribute("javaScriptPage","CSNComercio");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/CSNComercio")// CDSVendedor
	public ModelAndView CSNComercioContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/CSNComercio");
	}
	@RequestMapping("/CDSVendedor")
	public ModelAndView CDSVendedor(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Consulta Stock");
		model.addAttribute("paginaActual", "Vendedor");
		model.addAttribute("content", "CDSVendedor");
		model.addAttribute("javaScriptPage","CDSVendedor");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/CDSVendedor")// CSNDistribuidor
	public ModelAndView CDSVendedorContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/CDSVendedor");
	}
	@RequestMapping("/CSNDistribuidor")
	public ModelAndView CSNDistribuidor(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Consulta Stock");
		model.addAttribute("paginaActual", "Distribuidor");
		model.addAttribute("content", "CSNDistribuidor");
		model.addAttribute("javaScriptPage","CSNDistribuidor");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/CSNDistribuidor")// CSNSubDistribuidor
	public ModelAndView CSNDistribuidorContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/CSNDistribuidor");
	}
	@RequestMapping("/CSNSubDistribuidor")
	public ModelAndView CSNSubDistribuidor(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Consulta Stock");
		model.addAttribute("paginaActual", "Sub Distribuidor");
		model.addAttribute("content", "CSNSubDistribuidor");
		model.addAttribute("javaScriptPage","CSNSubDistribuidor");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/CSNSubDistribuidor")// CSNSubDistribuidor
	public ModelAndView CSNSubDistribuidorContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/CSNSubDistribuidor");
	}
	@RequestMapping("/CPFDistribuidor")
	public ModelAndView CPFDistribuidor(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Consulta por Fartura");
		model.addAttribute("paginaActual", "Distribuidor");
		model.addAttribute("content", "CPFDistribuidor");
		model.addAttribute("javaScriptPage","CPFDistribuidor");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/CPFDistribuidor")// CPFSubDistribuidor
	public ModelAndView CPFDistribuidorContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/CPFDistribuidor");
	}
	@RequestMapping("/CPFSubDistribuidor")
	public ModelAndView CPFSubDistribuidor(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Consulta por Fartura");
		model.addAttribute("paginaActual", "Sub Distribuidor");
		model.addAttribute("content", "CPFSubDistribuidor");
		model.addAttribute("javaScriptPage","CPFSubDistribuidor");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/CPFSubDistribuidor")// CPFVendedor
	public ModelAndView CPFSubDistribuidorContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/CPFSubDistribuidor");
	}
	@RequestMapping("/CPFVendedor")
	public ModelAndView CPFVendedor(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Consulta por Fartura");
		model.addAttribute("paginaActual", "Vendedor");
		model.addAttribute("content", "CPFVendedor");
		model.addAttribute("javaScriptPage","CPFVendedor");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/CPFVendedor")// CPFComercio
	public ModelAndView CPFVendedorContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/CPFVendedor");
	}
	@RequestMapping("/CPFComercio")
	public ModelAndView CPFComercio(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Consulta por Fartura");
		model.addAttribute("paginaActual", "Comercio");
		model.addAttribute("content", "CPFComercio");
		model.addAttribute("javaScriptPage","CPFComercio");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/CPFComercio")// HPDistribuidor
	public ModelAndView CPFComercioContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/CPFComercio");
	}
	@RequestMapping("/HPDistribuidor")
	public ModelAndView HPDistribuidor(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Recargas");
		model.addAttribute("content", "HPDistribuidor");
		model.addAttribute("javaScriptPage","HPDistribuidor");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/ResumenComercio")// HRTreintaSesentaDB
	public ModelAndView ResumenComercioContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/ResumenComercio");
	}
	@RequestMapping("/ResumenComercio")
	public ModelAndView ResumenComercio(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Recargas");
		model.addAttribute("content", "ResumenComercio");
		model.addAttribute("javaScriptPage","ResumenComercio");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/HPDistribuidor")// HRTreintaSesentaDB
	public ModelAndView HPDistribuidorContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/HPDistribuidor");
	}
	@RequestMapping("/HRTreintaSesentaDB")
	public ModelAndView HRTreintaSesentaDB(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Habilitaciones y recargas");
		model.addAttribute("content", "HRTreintaSesentaDB");
		model.addAttribute("javaScriptPage","HRTreintaSesentaDB");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/HRTreintaSesentaDB")// HPDComercio
	public ModelAndView HRTreintaSesentaDBContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/HRTreintaSesentaDB");
	}
	@RequestMapping("/HPDComercio")
	public ModelAndView HPDComercio(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("google_map", "src='https://maps.googleapis.com/maps/api/js?key=AIzaSyA-gfDufUPg8zkB-VRiVUiudqMfTYWa3GY&libraries=drawing,geometry,places&callback=initMap' async defer");
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Detalle Visita PDV");
		model.addAttribute("content", "HPDComercio");
		model.addAttribute("javaScriptPage","HPDComercio");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/HPDComercio")// Segmentacion
	public ModelAndView HPDComercioContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/HPDComercio");
	}
	@RequestMapping("/Segmentacion")
	public ModelAndView Segmentacion(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Gestion de Segmentacion");
		model.addAttribute("content", "Segmentacion");
		model.addAttribute("javaScriptPage","Segmentacion");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/Segmentacion")// ResumenPuntosVenta
	public ModelAndView SegmentacionContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/Segmentacion");
	}
	@RequestMapping("/ResumenPuntosVenta")
	public ModelAndView ResumenPuntosVenta(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Resumen Puntos Venta");
		model.addAttribute("content", "ResumenPuntosVenta");
		model.addAttribute("javaScriptPage","ResumenPuntosVenta");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/ResumenPuntosVenta")// CategorizacionPV
	public ModelAndView ResumenPuntosVentaContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/ResumenPuntosVenta");
	}
	@RequestMapping("/CategorizacionPV")
	public ModelAndView CategorizacionPV(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Categorizacion Puntos de Venta");
		model.addAttribute("content", "CategorizacionPV");
		model.addAttribute("javaScriptPage","CategorizacionPV");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/CategorizacionPV")// ResumenStock
	public ModelAndView CategorizacionPVContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/CategorizacionPV");
	}
	@RequestMapping("/ResumenStock")
	public ModelAndView ResumenStock(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Resumen de Puntos de Venta segun Stock");
		model.addAttribute("content", "ResumenStock");
		model.addAttribute("javaScriptPage","ResumenStock");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/ResumenStock")// ResumenStock
	public ModelAndView ResumenStockContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/ResumenStock");
	}
	
	@RequestMapping("/TrazabilidadPDV")
	public ModelAndView TrazabilidadPDV(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Trazabilidad PDV");
		model.addAttribute("content", "TrazabilidadPDV");
		model.addAttribute("javaScriptPage","TrazabilidadPDV");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/TrazabilidadPDV")// Circuito
	public ModelAndView TrazabilidadPDVContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/TrazabilidadPDV");
	}
	@RequestMapping("/Circuito")
	public ModelAndView Circuito(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Circuito");
		model.addAttribute("content", "Circuito");
		model.addAttribute("javaScriptPage","Circuito");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/Circuito")// Rutas
	public ModelAndView CircuitoContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/Circuito");
	}
	@RequestMapping("/Rutas")
	public ModelAndView Rutas(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("google_map", "src='https://maps.googleapis.com/maps/api/js?key=AIzaSyA-gfDufUPg8zkB-VRiVUiudqMfTYWa3GY&libraries=drawing,geometry,places&callback=initMap' async defer");
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Rutas");
		model.addAttribute("content", "Rutas");
		model.addAttribute("javaScriptPage","Rutas");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/Rutas")// AsignarRuta
	public ModelAndView RutasContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/Rutas");
	}
	@RequestMapping("/AsignarRuta")
	public ModelAndView AsignarRuta(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("google_map", "src='https://maps.googleapis.com/maps/api/js?key=AIzaSyA-gfDufUPg8zkB-VRiVUiudqMfTYWa3GY&libraries=drawing,geometry,places&callback=initMap' async defer");
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Asignar Ruta");
		model.addAttribute("content", "AsignarRuta");
		model.addAttribute("javaScriptPage","AsignarRuta");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/AsignarRuta")// RutasAsignadas
	public ModelAndView AsignarRutaContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/AsignarRuta");
	}
	@RequestMapping("/RutasAsignadas")
	public ModelAndView RutasAsignadas(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("google_map", "src='https://maps.googleapis.com/maps/api/js?key=AIzaSyA-gfDufUPg8zkB-VRiVUiudqMfTYWa3GY&libraries=drawing,geometry,places&callback=initMap' async defer");
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Dashboard");
		model.addAttribute("paginaActual", "Rutas Asignadas");
		model.addAttribute("content", "RutasAsignadas");
		model.addAttribute("javaScriptPage","RutasAsignadas");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/RutasAsignadas")// cargaOneClick
	public ModelAndView RutasAsignadasContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/RutasAsignadas");
	}
	@RequestMapping("/cargaOneClick")
	public ModelAndView cargaOneClick(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Importacion");
		model.addAttribute("paginaActual", "Carga One Click");
		model.addAttribute("content", "cargaOneClick");
		model.addAttribute("javaScriptPage","cargaOneClick");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/cargaOneClick")// cargaOneClick
	public ModelAndView cargaOneClickContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/cargaOneClick");
	}
	
	@RequestMapping("/DetalleCamada")
	public ModelAndView DetalleCamada(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Detalle Movimiento");
		model.addAttribute("paginaActual", "Camada");
		model.addAttribute("content", "DetalleCamada");
		model.addAttribute("javaScriptPage","DetalleCamada");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/DetalleCamada")// cargaOneClick
	public ModelAndView DetalleCamadaContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/DetalleCamada");
	}
	
	@RequestMapping("/misTorneos")
	public ModelAndView MisTorneos(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Mis Torneos");
		model.addAttribute("paginaActual", "misTorneos");
		model.addAttribute("content", "misTorneos");
		model.addAttribute("javaScriptPage","misTorneos");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/MisTorneos")
	public ModelAndView MisTorneosContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/misTorneos");
	}
	
	@RequestMapping("/detalleTorneo")
	public ModelAndView detalleTorneo(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("USER", httpSession.getAttribute("USER"));
		model.addAttribute("menuActual", "Detalle Torneo");
		model.addAttribute("paginaActual", "detelleTorneo");
		model.addAttribute("content", "detalleTorneo");
		model.addAttribute("javaScriptPage","detalleTorneo");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/detalleTorneo")
	public ModelAndView DetalleTorneoContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/detalleTorneo");
	}
		
}

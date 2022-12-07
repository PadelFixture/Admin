package lib.data.json;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import SWDB.impexp_trabajador;
import SWDB.sw_HorasAsistenciaDB;
import lib.classSW.AnticiposIndividuales;
import lib.classSW.CargarTipodePago;
import lib.classSW.EnviarMailNominaAnticipo;
import lib.classSW.ExportarCSV;
import lib.classSW.HorasAsistencia;
import lib.classSW.LoadTrabajadorSociedad;
import lib.classSW.NominaAnticipos;
import lib.classSW.PreseleccionDetalleVer;
import lib.classSW.TipoLicencia;
import lib.classSW.UpdateEstadoReclutamiento;
import lib.classSW.preseleccionados;
import lib.classSW.reclutamiento;
import lib.classSW.sw_CSVTotalBanco;
import lib.classSW.sw_haberesDescuentos;
import lib.classSW.tablaPermisoLicencia;
import lib.security.session;

@Controller
public class sw_NominaAnticipos {
	// -----------cargar Nomina
	// Anticipos-------------------------------------------------------------------------
	@RequestMapping(value = "/work/BuscarNominaPagoAnticipos/{fec},{periodo},{tipocuenta},{empresa},{division},{tipodivision},{grupo},{banco},{estado_proceso},{rolPrivado}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<NominaAnticipos> getBuscarNomina(@PathVariable String fec,
			@PathVariable String periodo, @PathVariable String tipocuenta, @PathVariable String empresa,
			@PathVariable String division, @PathVariable String tipodivision, @PathVariable String grupo,
		    @PathVariable String banco,
		    @PathVariable String estado_proceso,
		    @PathVariable int rolPrivado,
		    HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<NominaAnticipos> es = new ArrayList<NominaAnticipos>();

		if (ses.isValid()) {
			return es;
		}

		es = SWDB.sw_NominaAnticiposDB.getBuscarNomina(fec, periodo, tipocuenta, empresa, division, tipodivision, grupo,banco,estado_proceso,rolPrivado);
		return es;
		
	}

	// @RequestMapping(value = "/work/CrearCSV/", method = RequestMethod.PUT,
	// produces = MediaType.APPLICATION_JSON_VALUE)
	// public @ResponseBody boolean getscv(@RequestBody ArrayList<Empleado> row,
	// HttpSession httpSession)throws Exception {
	// boolean recc = false;
	// session ses = new session(httpSession);
	// if (ses.isValid()) {
	// return recc;
	// }
	//
	// for (Empleado rec : row) {
	//
	// recc = ExportarCSV.getscv(rec);
	// }
	//
	//
	// return recc;
	//
	// }
	// @RequestMapping(value = "/work/CrearCSV/", method = RequestMethod.PUT,
	// produces = MediaType.APPLICATION_JSON_VALUE)
	// public @ResponseBody boolean getscv(@RequestBody ArrayList<Empleado> row,
	// HttpSession httpSession) throws Exception {
	//
	// session ses = new session(httpSession);
	//// ArrayList<Empleado> es = new ArrayList<Empleado>();
	//
	// boolean recc = false;
	// if (ses.isValid()) {
	// return false;
	// }
	//
	//
	// recc = ExportarCSV.getscv(row);
	//
	//
	//// es = ExportarCSV.getscv(row);
	// return recc;
	//
	//
	//
	// }

	// @RequestMapping(value = "/work/CrearCSV/", method = RequestMethod.PUT,
	// produces = MediaType.APPLICATION_JSON_VALUE)
	// public @ResponseBody boolean getscv(@RequestBody ArrayList<Empleado> row,
	// HttpSession httpSession) throws Exception {
	//
	// boolean recc = false;
	//
	// session ses = new session(httpSession);
	// if(ses.isValid()){
	//
	// return recc;
	// }
	//
	//
	//
	// recc= ExportarCSV.getscv(row);
	//
	//
	// return recc;
	//
	// }

	// ----------------LOAD Lista
	// bancos------------------------------------------------
	@RequestMapping(value = "/work/ListaBanco/", method = { RequestMethod.GET, RequestMethod.POST })

	public @ResponseBody ArrayList<CargarTipodePago> getListaBancos(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<CargarTipodePago> es = new ArrayList<CargarTipodePago>();

		if (ses.isValid()) {
			return es;
		}

		es = SWDB.sw_NominaAnticiposDB.getListaBancos();
		return es;

	}
	
	@RequestMapping(value = "/work/ListaOficina/", method = { RequestMethod.GET, RequestMethod.POST })

	public @ResponseBody ArrayList<CargarTipodePago> getListaOficina(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<CargarTipodePago> es = new ArrayList<CargarTipodePago>();

		if (ses.isValid()) {
			return es;
		}

		es = SWDB.sw_NominaAnticiposDB.getListaOficina();
		return es;

	}

	// ----------------LOAD Lista tipo de
	// cuentas------------------------------------------------
	@RequestMapping(value = "/work/ListaTipoCuenta/", method = { RequestMethod.GET, RequestMethod.POST })

	public @ResponseBody ArrayList<CargarTipodePago> getListaTipoCuenta(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<CargarTipodePago> es = new ArrayList<CargarTipodePago>();

		if (ses.isValid()) {
			return es;
		}

		es = SWDB.sw_NominaAnticiposDB.getListaTipoCuenta();
		return es;

	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////

	@RequestMapping(value = "/work/AllNominaAnticipos/{empresa},{periodo},{estado},{tiponomina},{fechapago}", method = { RequestMethod.GET,
	RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	
	public @ResponseBody ArrayList<NominaAnticipos> getAllNominaAnticipos(
			@PathVariable int empresa,@PathVariable int periodo,@PathVariable String estado,
			@PathVariable String tiponomina,@PathVariable String fechapago,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<NominaAnticipos> es = new ArrayList<NominaAnticipos>();

		if (ses.isValid()) {
			return es;
		}

		es = SWDB.sw_NominaAnticiposDB.getAllNominaAnticipos(empresa,periodo,estado,tiponomina,fechapago);

		return es;

	}
	////////////////////////////// rechazar nomina
	////////////////////////////// anticipos/////////////////////////////////////////////////////////////////////////////////

	@RequestMapping(value = "/work/UpdateEstadoNominaAnticipo", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UpdateEstadoNomina(@RequestBody NominaAnticipos row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return SWDB.sw_NominaAnticiposDB.UpdateEstadoNomina(row);

	}
	////////////////////////////////// aprovar nomina
	////////////////////////////////// anticipos/////////////////////////////////////////////////////////////////////////////

	@RequestMapping(value = "/work/UpdateEstadoNominaAnticipoAprovar", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UpdateEstadoNominaAprovar(@RequestBody NominaAnticipos row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return SWDB.sw_NominaAnticiposDB.UpdateEstadoNominaAprovar(row);

	}

	/////////////////////////ver lista nomina Anticipo//////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/work/DetalleVerListaNomina/{entero}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)

	public @ResponseBody ArrayList<NominaAnticipos> DetalleVerNomina(@PathVariable int entero, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		ArrayList<NominaAnticipos> r = new ArrayList<NominaAnticipos>();

		if (ses.isValid()) {
			return r;
		}

		r = SWDB.sw_NominaAnticiposDB.DetalleVerNomina(entero);
		return r;

	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@RequestMapping(value = "/work/obtenerNominaPorMail/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<NominaAnticipos> getAllNominaCorreo(@PathVariable int id, HttpSession httpSession)
			throws Exception {
		System.out.println(System.getProperty("user.dir")); 
		session ses = new session(httpSession);
		ArrayList<NominaAnticipos> es = new ArrayList<NominaAnticipos>();

		if (ses.isValid()) {
			return es;
		}

		es = SWDB.sw_NominaAnticiposDB.getAllNominaCorreo(id);

		return es;

	}



	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// --------------------crear txt mas enviar mail
	//////////////////////////////////////////////////////////////////////////////////////////////////////////// campos-----------------------------------------------------------------
	@RequestMapping(value = "/work/CrearCSVMAsMail/", method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean getscvMAsEmail(@RequestBody ArrayList<NominaAnticipos> row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		boolean r = false;
		if (ses.isValid()) {
			return r;

		}

		r = ExportarCSV.insertTablaSwNomina(row.get(0));

		for (NominaAnticipos rec : row) {
			r = ExportarCSV.updateTablasw_asignacionAnticipos(rec);
		}
		r = ExportarCSV.getscvMAsEmail(row);
	

		return r;

	}
///////////////////////////////cargar select fecha pago por empresa y periodo////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/work/fechaAnticipoEmpresaYPeriodo/{empresa},{periodo}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)

	public @ResponseBody ArrayList<NominaAnticipos> SelectFechaPagoNominaAnticipo(@PathVariable int empresa,@PathVariable int periodo, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		ArrayList<NominaAnticipos> r = new ArrayList<NominaAnticipos>();

		if (ses.isValid()) {
			return r;
		}

		r = SWDB.sw_NominaAnticiposDB.SelectFechaPagoNominaAnticipo(empresa,periodo);
		return r;

	}
	
	@RequestMapping(value = "/work/fechaNominaEmpresaYPeriodo/{empresa},{periodo},{estado},{tiponomina}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)

	public @ResponseBody ArrayList<NominaAnticipos> SelectFechaPagoNominafilter(
			@PathVariable int empresa,@PathVariable int periodo,
			@PathVariable String estado,@PathVariable String tiponomina,HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		ArrayList<NominaAnticipos> r = new ArrayList<NominaAnticipos>();

		if (ses.isValid()) {
			return r;
		}

		r = SWDB.sw_NominaAnticiposDB.SelectFechaPagoNominafilter(empresa,periodo,estado,tiponomina);
		return r;

	}
	
	@RequestMapping(value = "/work/getRutaSWNominaExel", method = RequestMethod.GET)
	public @ResponseBody String getAutorizacionExcel(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		try {
			String idruta = request.getParameter("ruta");

			System.out.println("ruta: {}"+idruta);
			
			NominaAnticipos var = SWDB.sw_NominaAnticiposDB.getRutaTablaSWNominaExcel(idruta);
			
			String nombreArchivo = var.getRutaarchivo();
		    
			String split[]  = nombreArchivo.split("/");
			String nombreArchi = split[4];
		
			File file = new File(var.getRutaarchivo());
			
			
			FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);			
			response.addHeader("Content-disposition", "attachment; filename= "+nombreArchi+"");
			response.setContentType("application/octet-stream");
			response.setContentLength(bytes.length);
			response.setCharacterEncoding("iso-8859-1");
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
	@RequestMapping(value = "/work/getRutaSWNomina", method = RequestMethod.GET)
	public @ResponseBody String getAutorizacion(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		try {
			String idruta = request.getParameter("ruta");

			System.out.println("ruta: {}"+idruta);
			
			NominaAnticipos var = SWDB.sw_NominaAnticiposDB.getRutaTablaSWNomina(idruta);
			
			String nombreArchivo = var.getRutaarchivo();
		    
			String split[]  = nombreArchivo.split("/");
			String nombreArchi = split[4];
		
			File file = new File(var.getRutaarchivo());
			
			
			FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);			
			response.addHeader("Content-disposition", "attachment; filename= "+nombreArchi.toUpperCase()+"");
			response.setContentType("application/octet-stream");
			response.setContentLength(bytes.length);
			response.setCharacterEncoding("iso-8859-1");
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
// -----------cargar Nomina Finiquito-------------------------------------------------------------------------
	
	@RequestMapping(value = "/work/BuscarNominaPagoFiniquito/{fechatermino},{tipo_cuenta},{empresa},{division},{tipodivision},{grupo},{banco},{periodo},{estado_proceso},{rolPrivado}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<NominaAnticipos> getBuscarNominaFiniquito(
			@PathVariable String fechatermino,
			@PathVariable String tipo_cuenta, @PathVariable String empresa,
			@PathVariable String division, @PathVariable String tipodivision, @PathVariable String grupo,
		    @PathVariable String banco,@PathVariable String periodo,
		    @PathVariable String estado_proceso,
		    @PathVariable int rolPrivado, 
		    HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<NominaAnticipos> es = new ArrayList<NominaAnticipos>();

		if (ses.isValid()) {
			return es;
		}

		es = SWDB.sw_NominaAnticiposDB.getBuscarNominaFiniquito(fechatermino,tipo_cuenta, empresa, division, tipodivision, grupo,
				 banco,periodo,estado_proceso,rolPrivado);
		return es;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// --------------------crear txt mas enviar mail finiquito
	//////////////////////////////////////////////////////////////////////////////////////////////////////////// campos-----------------------------------------------------------------
	@RequestMapping(value = "/work/CrearTxtMAsMailFiniquito/", method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean getscvMAsEmailFiniquito(@RequestBody ArrayList<NominaAnticipos> row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		boolean r = false;
		if (ses.isValid()) {
			return r;

		}

		r = ExportarCSV.insertTablaSwNominaFiniquito(row.get(row.size()-1));

		for (NominaAnticipos rec : row) {
			r = ExportarCSV.updateTablasw_finiquito(rec);
		}
		r = ExportarCSV.getTxtMAsEmailFiniquitos(row);
	

		return r;

	}
	// -----------cargar Nomina Liquidacion-------------------------------------------------------------------------
	
		@RequestMapping(value = "/work/BuscarNominaPagoLiquidaciones/{fechapago},{tipo_cuenta},{empresa},{division},{subdivision},{grupo},{banco},{periodo},{estado_proceso},{rolPrivado}", method = {
				RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody ArrayList<NominaAnticipos> getBuscarNominaLiquidacion(
				@PathVariable String fechapago,
				@PathVariable String tipo_cuenta, @PathVariable String empresa,
				@PathVariable String division, @PathVariable String subdivision, @PathVariable String grupo,
			    @PathVariable String banco, @PathVariable String periodo,
			    @PathVariable String estado_proceso,
			    @PathVariable int rolPrivado,
			    HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<NominaAnticipos> es = new ArrayList<NominaAnticipos>();

			if (ses.isValid()) {
				return es;
			}

			es = SWDB.sw_NominaAnticiposDB.getBuscarNominaLiquidacion(fechapago,tipo_cuenta, empresa, division, subdivision, grupo, banco, periodo,estado_proceso,rolPrivado);
			return es;
		}
/////////////////////////////////nomina liquidacion////////////////////////////////////////////////////////
		@RequestMapping(value = "/work/CrearTxtMAsMailLiquidacion/", method = { RequestMethod.PUT,
				RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean getscvMAsEmailLiquidacion(@RequestBody ArrayList<NominaAnticipos> row, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			boolean r = false;
			if (ses.isValid()) {
				return r;

			}

			r = ExportarCSV.insertTablaSwNominaLiquidacion(row.get(0));

			for (NominaAnticipos rec : row) {
				r = ExportarCSV.updateTablasw_liquidacion(rec);
			}
			r = ExportarCSV.getTxtMAsEmailLiquidacion(row);
		

			return r;

		}
///////////////////////////////cargar select fecha pago por empresa y periodo////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/work/fechaAnticipoEmpresaYPeriodoLiquidacion/{empresa},{periodo}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)

	public @ResponseBody ArrayList<NominaAnticipos> SelectFechaPagoNominaAnticipoLiquidacion(@PathVariable int empresa,
			@PathVariable int periodo, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<NominaAnticipos> r = new ArrayList<NominaAnticipos>();

		if (ses.isValid()) {
			return r;
		}

		r = SWDB.sw_NominaAnticiposDB.SelectFechaPagoNominaAnticipoLiquidacion(empresa, periodo);
		return r;

	}
	/////////////////////////ver lista nomina Liquidacion//////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/work/DetalleVerListaNominaLiquidacion/{entero}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)

	public @ResponseBody ArrayList<NominaAnticipos> DetalleVerNominaLiquidacion(@PathVariable int entero, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		ArrayList<NominaAnticipos> r = new ArrayList<NominaAnticipos>();

		if (ses.isValid()) {
			return r;
		}

		r = SWDB.sw_NominaAnticiposDB.DetalleVerNominaLiquidacion(entero);
		return r;

	}
/////////////////////////ver lista nomina Finiquito//////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/work/DetalleVerListaNominaFiniquito/{entero}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)

	public @ResponseBody ArrayList<NominaAnticipos> DetalleVerNominaFiniquito(@PathVariable int entero, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		ArrayList<NominaAnticipos> r = new ArrayList<NominaAnticipos>();

		if (ses.isValid()) {
			return r;
		}

		r = SWDB.sw_NominaAnticiposDB.DetalleVerNominaFiniquito(entero);
		return r;

	}
///////////////////////////////cargar select fecha pago por empresa y periodo////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/work/fechaAnticipoEmpresaYPeriodoFiniquito/{empresa},{periodo}", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)

	public @ResponseBody ArrayList<NominaAnticipos> SelectFechaPagoNominaFiniquito(@PathVariable int empresa,
			@PathVariable int periodo, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<NominaAnticipos> r = new ArrayList<NominaAnticipos>();

		if (ses.isValid()) {
			return r;
		}

		r = SWDB.sw_NominaAnticiposDB.SelectFechaPagoNominaFiniquito(empresa, periodo);
		return r;

	}
	////////////////////////////// rechazar nomina
	////////////////////////////// Liquidacion/////////////////////////////////////////////////////////////////////////////////

	@RequestMapping(value = "/work/UpdateEstadoNominaLiquidacion", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UpdateEstadoNominaLiquidacion(@RequestBody NominaAnticipos row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return SWDB.sw_NominaAnticiposDB.UpdateEstadoNominaLiquidacion(row);

	}
	////////////////////////////// rechazar nomina
	////////////////////////////// Finiquito/////////////////////////////////////////////////////////////////////////////////

	@RequestMapping(value = "/work/UpdateEstadoNominaFiniquito", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UpdateEstadoNominaFiniquito(@RequestBody NominaAnticipos row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return SWDB.sw_NominaAnticiposDB.UpdateEstadoNominaFiniquito(row);

	}
	
	/// enviar mail desde el boton de la pantalla aprobacion de nomina
	
	@RequestMapping(value = "/work/EnviarMailBoton/{idtabla},{idconcepto}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean EnviarMailBoton(@PathVariable int idtabla,@PathVariable int idconcepto, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return SWDB.sw_NominaAnticiposDB.EnviarMailBoton(idtabla,idconcepto);

	}
	
	/// buscar de centralizacion //////////////////////////
	@RequestMapping(value = "/work/datoscentralizacion/{idnomina}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<NominaAnticipos> getBuscardatoscentralizacionNomina(@PathVariable int idnomina,
			 HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<NominaAnticipos> es = new ArrayList<NominaAnticipos>();

		if (ses.isValid()) {
			return es;
		}

		es = SWDB.sw_NominaAnticiposDB.getBuscardatoscentralizacionNomina(idnomina);
		return es;
	}
	
	
	/// insertar respuesta sap nomina ////
	
	@RequestMapping(value = "/work/insertRespuestaSapNomina/{asiento},{idnomina}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String insertarresSAPNomina(@PathVariable String asiento,@PathVariable int idnomina,
			HttpSession httpSession) throws Exception {
		
		
		session ses = new session(httpSession);
		String es = null;
	
		if (ses.isValid()) {
			return es;
		}
		
		String	recc = "";
		

			recc = SWDB.sw_NominaAnticiposDB.insertarresSAPNomina(asiento,idnomina);
	

		return recc;

	}
	
	
	@RequestMapping(value = "/work/getRutaPlanillaHoraExtraFalta", method = RequestMethod.GET)
	public @ResponseBody String getExcelHef(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		try {
			String nombreArchivo = request.getParameter("ruta");

			
			
	
		    
			String split[]  = nombreArchivo.split("/");
			String nombreArchi = split[4];
		
			File file = new File(nombreArchivo);
			
			
			FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);			
			response.addHeader("Content-disposition", "attachment; filename= "+nombreArchi+"");
			response.setContentType("application/octet-stream");
			response.setContentLength(bytes.length);
			response.setCharacterEncoding("iso-8859-1");
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
	
/////////////////////////////////nomina excel liquidacion////////////////////////////////////////////////////////
	@RequestMapping(value = "/work/CrearPreExcelLiquidacion/", method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getPreExcelLiquidacion(@RequestBody ArrayList<NominaAnticipos> row,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		

		String r = "";
		if (ses.isValid()) {
			return "";

		}

		r = ExportarCSV.getPreExcelLiquidacion(row);

		return r;

	}
/////////////////////////////////// nomina excel anticipo /////////////////////////////////////////////////
	@RequestMapping(value = "/work/CrearPreExcelAnticipos/", method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getPreExcelAnticipo(@RequestBody ArrayList<NominaAnticipos> row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		String r = "";
		if (ses.isValid()) {
			return "";

		}

		r = ExportarCSV.getPreExcelAnticipo(row);

		return r;

	}
//////////////////////////// nomina pre excel finiquitos ///////////////////////////////////////////////////////
	@RequestMapping(value = "/work/CrearPreExcelFiniquitos/", method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getPreExcelFiniquitos(@RequestBody ArrayList<NominaAnticipos> row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		String r = "";
		if (ses.isValid()) {
			return "";

		}

		
		r = ExportarCSV.getPreExcelFiniquitos(row);
	

		return r;

	}
	
////////////////////////////nomina pre excel Retenciones ///////////////////////////////////////////////////////
	@RequestMapping(value = "/work/CrearExcelRetenciones/", method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getexcelRetenciones(@RequestBody ArrayList<sw_haberesDescuentos> row,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		String r = "";
		if (ses.isValid()) {
			return "";

		}

		r = ExportarCSV.getexcelRetenciones(row);

		return r;

	}
	
}
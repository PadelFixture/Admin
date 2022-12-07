package lib.data.json.sw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import SWDB.sw_OrdenPagoPREVIREDB;
import lib.classSW.OrdenDePagoPREVIRED;
import lib.security.session;

@Controller
public class sw_OrdenPagoPREVIRED {
	
	//---------------GENERAR EXCEL IMPUESTO UNICO--------------------------------------------------------
		@RequestMapping(value = "/work/generateExcelOrdenDePagoPREVIRED/{idSociedad},{periodo}", method = { RequestMethod.PUT,
				RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody String generarExcelAvisoInspeccion(@PathVariable int idSociedad,
				@PathVariable int periodo,HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			String r = "";
			if (ses.isValid()) {
				return "";

			}
			
			ArrayList<OrdenDePagoPREVIRED> detalleEmpresa = new ArrayList<OrdenDePagoPREVIRED>();
		
			
				detalleEmpresa = sw_OrdenPagoPREVIREDB.buscarDatosEmpresa(idSociedad, periodo);
				
				if(detalleEmpresa.size() >= 1){
				r = sw_OrdenPagoPREVIREDB.generarExcelPREVIREDinstitucion(detalleEmpresa);
				}else
				{
					r = "NO DATA";
				}
		
	
			
			return r;

		}
		
		// obtener todos los periodo realizado de previred
		@RequestMapping(value = "/work/allPeridoOrdendepago/{empr}", method = {
				RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody ArrayList<OrdenDePagoPREVIRED> getallPeriodoOrdenDePago(@PathVariable String empr, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);

			ArrayList<OrdenDePagoPREVIRED> r = new ArrayList<OrdenDePagoPREVIRED>();

			if (ses.isValid()) {
				return r;
			}
			r = sw_OrdenPagoPREVIREDB.getallPeriodoOrdenDePago(empr);

			return r;

		}
		
		// obtener todos los periodo realizado de previred y centralizado
				@RequestMapping(value = "/work/allPeridoOrdendepagoEstado1/{empr}", method = {
						RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
				public @ResponseBody ArrayList<OrdenDePagoPREVIRED> allPeridoOrdendepagoEstado1(@PathVariable String empr, HttpSession httpSession) throws Exception {

					session ses = new session(httpSession);

					ArrayList<OrdenDePagoPREVIRED> r = new ArrayList<OrdenDePagoPREVIRED>();

					if (ses.isValid()) {
						return r;
					}
					r = sw_OrdenPagoPREVIREDB.allPeridoOrdendepagoEstado1(empr);

					return r;

				}
		
		// buscar detalle orden de pago previred
		@RequestMapping(value = "/work/detalleordendepagoPrevired/{soc},{period}", method = {
				RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody ArrayList<OrdenDePagoPREVIRED> DetalleordendepagoPrevired(@PathVariable String soc, 
		@PathVariable String period, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);

			ArrayList<OrdenDePagoPREVIRED> r = new ArrayList<OrdenDePagoPREVIRED>();

			if (ses.isValid()) {
				return r;
			}
			r = sw_OrdenPagoPREVIREDB.DetalleordendepagoPrevired(soc,period);

			return r;

		}
		
		@RequestMapping(value = "/work/numeroCuentaSAPPagoPREVIRED/", method = { RequestMethod.GET,
				RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody int obtenerNCuentaSapPagoPREVIRED(HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);

			int r = 0;
			if (ses.isValid()) {
				return 0;

			}

			r = SWDB.sw_OrdenPagoPREVIREDB.obtenerNCuentaSapPagoPREVIRED();

			return r;

		}
		
		@RequestMapping(value = "/work/numeroProveedorSAPPagoPREVIRED/", method = { RequestMethod.GET,
				RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody int obtenerProvvedorSapPagoPREVIRED(HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);

			int r = 0;
			if (ses.isValid()) {
				return 0;

			}

			r = SWDB.sw_OrdenPagoPREVIREDB.obtenerProvvedorSapPagoPREVIRED();

			return r;

		}
		
		
		
		@RequestMapping(value = "/work/buscarsiestacentralizadoPREVIRED", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody String buscarsiestacentralizadoPREVIRED(@RequestBody OrdenDePagoPREVIRED row,
				HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			
			String r = "";
			if (ses.isValid()) {
				return "";

			}
			
			r =  SWDB.sw_OrdenPagoPREVIREDB.buscarsiestacentralizadoPREVIRED(row);

			return r;
		}
		
		
		
		// buscar detalle orden de pago previred
				@RequestMapping(value = "/work/rowperidoCentralizacionPREVIRED/{soc},{period}", method = {
						RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
				public @ResponseBody ArrayList<OrdenDePagoPREVIRED> rowperidoCentralizacionPREVIRED(@PathVariable String soc, 
				@PathVariable String period, HttpSession httpSession) throws Exception {

					session ses = new session(httpSession);

					ArrayList<OrdenDePagoPREVIRED> r = new ArrayList<OrdenDePagoPREVIRED>();

					if (ses.isValid()) {
						return r;
					}
					r = sw_OrdenPagoPREVIREDB.rowperidoCentralizacionPREVIRED(soc,period);

					return r;

				}
				
				@RequestMapping(value = "/work/getRutaTXTPrevired", method = RequestMethod.GET)
				public @ResponseBody String getAutorizacionTXTPREVIRED(HttpServletRequest request, HttpServletResponse response,
						HttpSession session) {
					try {
						String periodo_ = request.getParameter("periodo");
						String empresa_ = request.getParameter("empresa");

						Blob txtPREVIRED = sw_OrdenPagoPREVIREDB.getTXTPREVIREDEmpresaPeriodo(periodo_,empresa_);
						InputStream in = txtPREVIRED.getBinaryStream();
						String nombreArchi = "PREVIRED"+periodo_+".txt";
						
			            byte[] bytes = IOUtils.toByteArray(in);			
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
				@RequestMapping(value = "/work/getRutaExelPrevired", method = RequestMethod.GET)
				public @ResponseBody String getAutorizacionExcelPREVIRED(HttpServletRequest request, HttpServletResponse response,
						HttpSession session) {
					try {
						String periodo_ = request.getParameter("periodo");
						String empresa_ = request.getParameter("empresa");
						
						Blob txtPREVIRED = sw_OrdenPagoPREVIREDB.getExcelPREVIREDEmpresaPeriodo(periodo_,empresa_);
						InputStream in = txtPREVIRED.getBinaryStream();
						String nombreArchi = "PREVIRED"+periodo_+".xlsx";
						
			            byte[] bytes = IOUtils.toByteArray(in);			
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
				
				@RequestMapping(value = "/work/UpdateEstadoCentralizacionPREVIRED", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
				public @ResponseBody boolean UpdateEstadoCentralizacionPREVIRED(@RequestBody OrdenDePagoPREVIRED row, HttpSession httpSession)
						throws Exception {

					session ses = new session(httpSession);

					if (ses.isValid()) {
						return false;
					}
					return SWDB.sw_OrdenPagoPREVIREDB.UpdateEstadoCentralizacionPREVIRED(row);

				}

}

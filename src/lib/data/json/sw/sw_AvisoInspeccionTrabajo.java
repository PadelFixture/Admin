package lib.data.json.sw;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

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

import SWDB.AvisoInspeccionTrabajoDB;
import SWDB.impexp_trabajador;
import lib.classSW.CCAFLosAndes;
import lib.classSW.DatosAvisoInspeccionTrabajo;
import lib.classSW.DeclaracionJurada;
import lib.classSW.Diferencialiquidacion;
import lib.classSW.Liquidacion;
import lib.classSW.LiquidacionPeriodo;
import lib.classSW.Previred;
import lib.classSW.reclutamiento;
import lib.security.session;
import wordCreator.utils;
@Controller
public class sw_AvisoInspeccionTrabajo {

	//---------------GENERAR EXCEL AVISO INSPECCION TRABAJADOR--------------------------------------------------------
		@RequestMapping(value = "/work/generateAvisoInspeccionTrabajo/{idSociedad},{fechainicio},{fechatermino},{huerto},{rolPrivado}", method = { RequestMethod.PUT,
				RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody String generarExcelAvisoInspeccion(
				@PathVariable int idSociedad,
				@PathVariable String fechainicio,
				@PathVariable String fechatermino,
				@PathVariable String huerto,
				@PathVariable int rolPrivado,
				HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			String r = "";
			if (ses.isValid()) {
				return "";

			}
			
			ArrayList<DatosAvisoInspeccionTrabajo> BuscarTrabajador = new ArrayList<DatosAvisoInspeccionTrabajo>();
			BuscarTrabajador = AvisoInspeccionTrabajoDB.buscartrabajadores(idSociedad, fechainicio, fechatermino,huerto,rolPrivado );
			ArrayList<Object> datosAvisoInspeccionTrabajoLista = new ArrayList<Object>(); 
			
			ArrayList<DatosAvisoInspeccionTrabajo> Trabajador = new ArrayList<DatosAvisoInspeccionTrabajo>();
			for (DatosAvisoInspeccionTrabajo rec : BuscarTrabajador) {
				
				datosAvisoInspeccionTrabajoLista.add(AvisoInspeccionTrabajoDB.obtenerDatosAviso(idSociedad,rec.getCodigotrabajador(),rec.getIdcontrato())); 
			
			} 
			
			if(BuscarTrabajador.size() >= 1){
			 r = AvisoInspeccionTrabajoDB.generarExcelAvisoInspeccion(idSociedad,datosAvisoInspeccionTrabajoLista);
			}else
			{
				r = "NO DATA";
			}
			return r;

		}
		
		@RequestMapping(value = "/work/descargarDocumetoAvisoTrabajo", method = RequestMethod.GET)
		public @ResponseBody String getAutorizacion(HttpServletRequest request, HttpServletResponse response,
				HttpSession session) {
			try {
				String fileName = request.getParameter("FILE");
				String fileName2 = request.getParameter("FILE");
				System.out.println(fileName);

				fileName = fileName.replaceAll("\"", "");
				fileName = fileName.replaceAll("/", "//");
				System.out.println("ruta: {}" + fileName);

				String urlDocGenerado = fileName;
				
				
				
				
				String split[]  = fileName.split("/");
				String nombreArchi = split[8];
				System.out.println("nombre archivo"+ nombreArchi);
				
				

				@SuppressWarnings("unused")
				File file = new File(urlDocGenerado);
				System.out.println("aqui   " + urlDocGenerado);

				FileInputStream fileInputStreamReader = new FileInputStream(urlDocGenerado);
				byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);
				response.addHeader("Content-disposition", "attachment; filename= " + nombreArchi + "");
				response.setContentType("application/octet-stream");
				response.setContentLength(bytes.length);
				response.setCharacterEncoding("iso-8859-1");
				ServletOutputStream out = response.getOutputStream();
				out.write(bytes);
				out.flush();
				out.close();
                
				String escaped = fileName2.replace("\\", "\\\\");
				File archivo = new File(escaped);

	            boolean estatus = archivo.delete();;

	            if (!estatus) {

	                System.out.println("Error no se ha podido eliminar el  archivo");

	           }else{

	                System.out.println("Se ha eliminado el archivo exitosamente");

	           }
				return "1";

			} catch (Exception e) {
				e.printStackTrace();
				return "0";
			}
		}
		
		@RequestMapping(value = "/work/descargarAfiliacionPREVIRED", method = RequestMethod.GET)
		public @ResponseBody String DownloadtxtAfiliacionPREVIRED(HttpServletRequest request, HttpServletResponse response,
				HttpSession session) {
			try {
				String fileName = request.getParameter("FILE");
				System.out.println("ruta: {}" + fileName);
				String fileName2 = request.getParameter("FILE");
				fileName = fileName.replaceAll("\"", "");
				System.out.println("ruta: {}" + fileName);

				String urlDocGenerado = fileName;

				@SuppressWarnings("unused")
				File file = new File(urlDocGenerado);
				System.out.println("aqui   " + urlDocGenerado);

				FileInputStream fileInputStreamReader = new FileInputStream(urlDocGenerado);
				byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);
				response.addHeader("Content-disposition", "attachment; filename= " + fileName + "");
				response.setContentType("application/octet-stream");
				response.setContentLength(bytes.length);
				response.setCharacterEncoding("iso-8859-1");
				ServletOutputStream out = response.getOutputStream();
				out.write(bytes);
				out.flush();
				out.close();
				
				String escaped = fileName2.replace("\\", "\\\\");
				File archivo = new File(escaped);

	            boolean estatus = archivo.delete();;

	            if (!estatus) {

	                System.out.println("Error no se ha podido eliminar el  archivo");

	           }else{

	                System.out.println("Se ha eliminado el archivo exitosamente");

	           }

				return "1";

			} catch (Exception e) {
				e.printStackTrace();
				return "0";
			}
		}
		
		//---------------GENERAR EXCEL Base de Datos CCAF--------------------------------------------------------
				@RequestMapping(value = "/work/generateArchivoBasedeDatosCCAF/{idSociedad},{periodo},{rolPrivado}", method = { RequestMethod.PUT,
						RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
				public @ResponseBody String generarExcelBDCCAF(
						@PathVariable int idSociedad,
						@PathVariable int periodo, 
						@PathVariable int rolPrivado, 
						HttpSession httpSession)
						throws Exception {

					session ses = new session(httpSession);

					String r = "";
					if (ses.isValid()) {
						return "";

					}
					
					ArrayList<CCAFLosAndes> BuscarTrabajadorCCAF = new ArrayList<CCAFLosAndes>();
					BuscarTrabajadorCCAF = AvisoInspeccionTrabajoDB.buscartrabajadoresCCAF(idSociedad ,periodo,rolPrivado);
				
					
					if(BuscarTrabajadorCCAF.size() >= 1){
					 r = AvisoInspeccionTrabajoDB.generarExcelBDCCAF(idSociedad,BuscarTrabajadorCCAF);
					 
					}else
					{
						r = "NO DATA";
					}
					return r;

				}
				
				//---------------GENERAR EXCEL SIN LIQUIDACION--------------------------------------------------------
				@RequestMapping(value = "/work/generateExcelSinLiquidacion/{idSociedad},{periodo},{rolPrivado}", method = { RequestMethod.PUT,
						RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
				public @ResponseBody String generarExcelSinLiqui(
						@PathVariable int idSociedad,
						@PathVariable String periodo,
						@PathVariable int rolPrivado,
						HttpSession httpSession)
						throws Exception {

					session ses = new session(httpSession);

					String r = "";
					if (ses.isValid()) {
						return "";

					}
					
					ArrayList<Liquidacion> BuscarTrabajadorSN = new ArrayList<Liquidacion>();
					BuscarTrabajadorSN = AvisoInspeccionTrabajoDB.buscartrabajoresSinLiqui(idSociedad ,periodo,rolPrivado);
				
					
					if(BuscarTrabajadorSN.size() == 1){
					
					 r = "NO DATA";
					 
					}else
					{
						 r = AvisoInspeccionTrabajoDB.generarExcelSinLiqui(idSociedad,BuscarTrabajadorSN);
					}
					return r;

				}
				
//---------------GENERAR EXCEL DIFERENCIA LIQUIDACION--------------------------------------------------------
				@RequestMapping(value = "/work/generateExcelDiferenciaLiquidacion/{idSociedad},{periodo},{rolPrivado}", method = { RequestMethod.PUT,
						RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
				public @ResponseBody String generarExcelDiferenciaLiqui(
						@PathVariable int idSociedad,
						@PathVariable int periodo, 
						@PathVariable int rolPrivado, 
						HttpSession httpSession)
						throws Exception {

					session ses = new session(httpSession);

					String r = "";
					if (ses.isValid()) {
						return "";

					}
					
					ArrayList<Diferencialiquidacion> BuscarTrabajadorDiferencia = new ArrayList<Diferencialiquidacion>();
					BuscarTrabajadorDiferencia = AvisoInspeccionTrabajoDB.buscartrabajadoresDiferenciaLiquidacion(idSociedad ,periodo,rolPrivado);
				
					
					if(BuscarTrabajadorDiferencia.size() >= 1){
					 r = AvisoInspeccionTrabajoDB.generarExcelDescuentosLiq(idSociedad,BuscarTrabajadorDiferencia);
					 
					}else
					{
						r = "NO DATA";
					}
					return r;

				}
				
				//---------------GENERAR EXCEL Base de Datos CCAF--------------------------------------------------------
				@RequestMapping(value = "/work/generateArchivoDeclaracionJurada/{idSociedad},{anio},{rolPrivado}", method = { RequestMethod.PUT,
						RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
				public @ResponseBody String generateArchivoDeclaracionJurada(
						@PathVariable int idSociedad,
						@PathVariable int anio, 
						@PathVariable int rolPrivado, 
						HttpSession httpSession)
						throws Exception {

					session ses = new session(httpSession);

					String r = "";
					if (ses.isValid()) {
						return "";

					}
					
					ArrayList<DeclaracionJurada> BuscarTrabajadorCCAF = new ArrayList<DeclaracionJurada>();
					BuscarTrabajadorCCAF = AvisoInspeccionTrabajoDB.buscartrabajadoresCCAFDeclaracion(idSociedad ,anio,rolPrivado);
				
					
					
					if(BuscarTrabajadorCCAF.size() >= 1){
						
						
						
					 r = AvisoInspeccionTrabajoDB.generarExcelBDeclaracion(idSociedad,BuscarTrabajadorCCAF);
					 
					}else
					{
						r = "NO DATA";
					}
					return r;

				}
}

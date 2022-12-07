package lib.data.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

import lib.SADB.DotacionDiaria;
import lib.SADB.INCIDENCIAS;
import lib.SADB.RENDIMIENTO;
import lib.SADB.cuadrilla;
import lib.SADB.noIncidencia;
import lib.classSA.BLOQUEO_LABOR;
import lib.classSA.CAMPO;
import lib.classSA.CUADRATURA_HORA;
import lib.classSA.CUADRILLA;
import lib.classSA.DOTACION_DIARIA;
import lib.classSA.FILTRO_CUADRAR_HR;
import lib.classSA.LABOR;
import lib.classSA.LIBRO_CAMPO;
import lib.classSA.LIQUIDACION;
import lib.classSA.RENDIMIENTO_DIARIO;
import lib.classSA.RENDIMIENTO_GENERAL;
import lib.classSA.SESIONVAR;
import lib.classSA.Trabajador;
import lib.classSA.asiento_contable;
import lib.classSA.calificacion_rendimiento;
import lib.classSA.cierre_mensual;
import lib.classSA.detalle_rendimiento;
import lib.classSA.faena;
import lib.classSA.incidencia;
import lib.classSA.recorrido;
import lib.classSA.respuesta;
import lib.classSW.CentralizacionDetalleTmp;
import lib.classSW.Documentos;
import lib.db.ConnectionDB;
import lib.db.sw.DocumentosDB;
import lib.excelSA.excelExportOrden;
import lib.excelSA.excelOrdenJson;
import lib.security.session;
import lib.sesionSA.SESION;
import lib.struc.trabajadores;
import pdfCreator.PdfToImage;
import wordCreator.utils;

@Controller
public class RendimientoServices {
	@RequestMapping(value = "/AGRO/GET_RENDIMIENTO_DIARIO_TRABAJADOR/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<RENDIMIENTO_DIARIO> GET_RENDIMIENTO_DIARIO_TRABAJADOR(@PathVariable int id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<RENDIMIENTO_DIARIO> r = new ArrayList<RENDIMIENTO_DIARIO>();
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_RENDIMIENTO_DIARIO_TRABAJADOR(id);
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_RENDIMIENTO_INDIVIDUAL/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_RENDIMIENTO_INDIVIDUAL(@RequestBody  RENDIMIENTO_GENERAL row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		ConnectionDB db = new ConnectionDB();
		return RENDIMIENTO.ADD_RENDIMIENTO_INDIVIDUAL(db, row);
	}
	@RequestMapping(value = "/AGRO/ADD_RENDIMIENTO_DIARIO_INDIVIDUAL/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_RENDIMIENTO_DIARIO_INDIVIDUAL(@RequestBody  RENDIMIENTO_DIARIO row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		ConnectionDB db = new ConnectionDB();
		return RENDIMIENTO.ADD_RENDIMIENTO_DIARIO_INDIVIDUAL(db, row);
	}
	@RequestMapping(value = "/AGRO/GET_RENDIMIENTO_GENERAL_CUADRILLA/{codigo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<RENDIMIENTO_GENERAL> GET_RENDIMIENTO_GENERAL_CUADRILLA(@PathVariable int codigo, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<RENDIMIENTO_GENERAL> r = new ArrayList<RENDIMIENTO_GENERAL>();
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_RENDIMIENTO_GENERAL_CUADRILLA(codigo);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_RENDIMIENTO_GENERAL", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<RENDIMIENTO_GENERAL> GET_RENDIMIENTO_GENERAL(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);

		String fecha = request.getParameter("FECHA");
		int supervisor = Integer.parseInt(request.getParameter("SUPERVISOR"));
		String cuartel = request.getParameter("CUARTEL");
		String tipo = request.getParameter("TIPO");
		ArrayList<RENDIMIENTO_GENERAL> r = new ArrayList<RENDIMIENTO_GENERAL>();
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_RENDIMIENTO_GENERAL(fecha, supervisor, cuartel, tipo);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_RENDIMIENTO_GENERAL_FECHA/{fecha}/{supervisor}/{cuartel}/{tipo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody CUADRILLA GET_RENDIMIENTO_GENERAL_FECHA(@PathVariable String fecha,@PathVariable int supervisor,@PathVariable int cuartel, @PathVariable String tipo, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		CUADRILLA r = new CUADRILLA();
		if (ses.isValid()) {
			return r;
		}
		r = cuadrilla.GET_CUADRILLA_TRABAJADOR_FECHA(fecha, supervisor, cuartel, tipo);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_REPLICAR_CUADRILLA/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody CUADRILLA GET_REPLICAR_CUADRILLA(@PathVariable int id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		CUADRILLA r = new CUADRILLA();
		if (ses.isValid()) {
			return r;
		}
		r = cuadrilla.GET_REPLICAR_CUADRILLA(id);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_REPLICAR_CUADRILLA_SUPERVISOR/{id}/{fecha}/{cuartel}/{tipo}/{contratista}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody CUADRILLA GET_REPLICAR_CUADRILLA_SUPERVISOR(@PathVariable int id,@PathVariable String fecha,@PathVariable int cuartel, @PathVariable String tipo, @PathVariable String contratista, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		CUADRILLA r = new CUADRILLA();
		if (ses.isValid()) {
			return r;
		}
		r = cuadrilla.GET_REPLICAR_CUADRILLA_SUPERVISOR(id, fecha,cuartel, tipo, contratista);
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_RENDIMIENTO_GENERAL/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_RENDIMIENTO_GENERAL(@RequestBody  RENDIMIENTO_GENERAL row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RENDIMIENTO.ADD_RENDIMIENTO_GENERAL(row);
	}
	@RequestMapping(value = "/AGRO/UPDATE_RENDIMIENTO_GENERAL/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPDATE_RENDIMIENTO_GENERAL(@RequestBody  RENDIMIENTO_GENERAL row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RENDIMIENTO.UPDATE_RENDIMIENTO_GENERAL(row);
	}
	@RequestMapping(value = "/AGRO/GET_CALIFICAR_RENDIMIENTO/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<calificacion_rendimiento> GET_CALIFICAR_RENDIMIENTO(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<calificacion_rendimiento> r = new ArrayList<calificacion_rendimiento>();
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_CALIFICAR_RENDIMIENTO();
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_CALIFICAR_RENDIMIENTO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_CALIFICAR_RENDIMIENTO(@RequestBody  calificacion_rendimiento row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RENDIMIENTO.ADD_CALIFICAR_RENDIMIENTO(row);
	}
	@RequestMapping(value = "/AGRO/GET_CUADRILLA/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<CUADRILLA> GET_CUADRILLA( HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<CUADRILLA> r = new ArrayList<CUADRILLA>();
		if (ses.isValid()) {
			return r;
		}
		r = cuadrilla.GETCUAD();
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_CUADRILLA_SUPERVISOR/{id}/{c}/{contratista}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<RENDIMIENTO_GENERAL> GET_CUADRILLA_SUPERVISOR(@PathVariable int id, @PathVariable String c, @PathVariable String contratista, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<RENDIMIENTO_GENERAL> r = new ArrayList<RENDIMIENTO_GENERAL>();
		if (ses.isValid()) {
			return r;
		}
		r = cuadrilla.GET_CUADRILLA_SUPERVISOR(id, c, contratista);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_CUADRILLA_TRABAJADOR/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody CUADRILLA GET_CUADRILLA_TRABAJADOR(@PathVariable  int id ,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		CUADRILLA r = new CUADRILLA();
		if (ses.isValid()) {
			return r;
		}
		r = cuadrilla.GET_CUADRILLA_TRABAJADOR(id);
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_CUADRILLA/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_CUADRILLA(@RequestBody  CUADRILLA row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return cuadrilla.ADD_CUADRILLA(row);
	}
	@RequestMapping(value = "/AGRO/GET_CUADRATURA_HORAS", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_CUADRATURA_HORAS(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return r;
		}
		String campo = request.getParameter("CAMPO");
		String fechasAux = request.getParameter("FECHAS");
		String[] fechas = fechasAux.split(",");
		double horas = Double.parseDouble(request.getParameter("HORAS"));
		r = RENDIMIENTO.GET_CUADRATURA_HORAS(campo, fechas, horas);
		return r;
	}
	@RequestMapping(value = "/AGRO/DETALLE_RENDIMIENTO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<CUADRATURA_HORA> DETALLE_RENDIMIENTO(@RequestBody  FILTRO_CUADRAR_HR row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<CUADRATURA_HORA> r = new ArrayList<CUADRATURA_HORA>();
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_DETALLE_RENDIMIENTO_RUT(row);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_RENDIMIENTOS_FECHA", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<RENDIMIENTO_GENERAL> GET_RENDIMIENTOS_FECHA(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<RENDIMIENTO_GENERAL> r = new ArrayList<RENDIMIENTO_GENERAL>();
		String campo = request.getParameter("CAMPO");
		String fecha_desde = request.getParameter("FECHADESDE");
		String fecha_hasta = request.getParameter("FECHAHASTA");
		String tipo = request.getParameter("TIPO");
		int estado = Integer.parseInt(request.getParameter("ESTADO"));
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_RENDIMIENTOS_FECHA(campo, fecha_desde, fecha_hasta, tipo, estado);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_RENDIMIENTOS_FECHA_INDIVIDUAL", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<RENDIMIENTO_GENERAL> GET_RENDIMIENTOS_FECHA_INDIVIDUAL(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<RENDIMIENTO_GENERAL> r = new ArrayList<RENDIMIENTO_GENERAL>();
		String campo = request.getParameter("CAMPO");
		String fecha_desde = request.getParameter("FECHADESDE");
		String fecha_hasta = request.getParameter("FECHAHASTA");
		String tipo = request.getParameter("TIPO");
		int estado = Integer.parseInt(request.getParameter("ESTADO"));
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_RENDIMIENTOS_FECHA_INDIVIDUAL(campo, fecha_desde, fecha_hasta, tipo, estado);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_RENDIMIENTOS_CODIGO/{codigo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RENDIMIENTO_GENERAL GET_RENDIMIENTOS_CODIGO(@PathVariable int codigo, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		RENDIMIENTO_GENERAL r = new RENDIMIENTO_GENERAL();
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_RENDIMIENTOS_CODIGO(codigo);
		return r;
	}
//	SELECT
//	@RequestMapping(value = "/AGRO/GETCC/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody ArrayList<calificacion_campo> GETCC(HttpSession httpSession) throws Exception {
//		session ses = new session(httpSession);
//		
//		ArrayList<calificacion_campo> r = new ArrayList<calificacion_campo>();
//		if (ses.isValid()) {
//			return r;
//		}
//		r = CALIFICACION_RENIDIMIENTO.GETCALIFICACION();
//		return r;
//	}
	@RequestMapping(value = "/AGRO/GET_LABOR_BLOQUEO/{campo}/{mes}/{especie}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<BLOQUEO_LABOR> GET_LABOR_BLOQUEO(@PathVariable String campo,@PathVariable String mes, @PathVariable String especie,  HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<BLOQUEO_LABOR> r = new ArrayList<BLOQUEO_LABOR>();
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_LABOR_BLOQUEO(campo, mes, especie);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_LABOR_ALL/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LABOR> GET_LABOR_ALL( HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<LABOR> r = new ArrayList<LABOR>();
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_LABOR_ALL();
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_DETALLE_RENDIMIENTO_DIARIO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<detalle_rendimiento> GET_DETALLE_RENDIMIENTO_DIARIO(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);

		String tipo = request.getParameter("TIPO");
		String codigo = request.getParameter("CODIGO");
		ArrayList<detalle_rendimiento> r = new ArrayList<detalle_rendimiento>();
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_DETALLE_RENDIMIENTO_DIARIO(tipo, codigo);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_DETALLE_RENDIMIENTO_GENERAL", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<detalle_rendimiento> GET_DETALLE_RENDIMIENTO_GENERAL(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);

		String tipo = request.getParameter("TIPO");
		String codigo = request.getParameter("CODIGO");
		ArrayList<detalle_rendimiento> r = new ArrayList<detalle_rendimiento>();
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_DETALLE_RENDIMIENTO_GENERAL(tipo, codigo);
		return r;
	}
	@RequestMapping(value = "/AGRO/UPDATE_RENDIMIENTO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPDATE_RENDIMIENTO(HttpServletRequest request,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		String tipo = request.getParameter("TIPO");
		int estado = Integer.parseInt(request.getParameter("ESTADO"));
		int codigo = Integer.parseInt(request.getParameter("CODIGO"));
		if (ses.isValid()) {
			return false;
		}
		return RENDIMIENTO.UPDATE_RENDIMIENTO(tipo, estado, codigo);

	}
	@RequestMapping(value = "/AGRO/CALIFICACION_RENDIMIENTO/{codigo_rg}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean CALIFICACION_RENDIMIENTO(@PathVariable  int codigo_rg,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			return false;
		}
		return RENDIMIENTO.calificacion_cuadrilla(codigo_rg);
	}
	@RequestMapping(value = "/AGRO/GET_FAENA_LABOR/{labor}/{zona}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody faena GET_FAENA_LABOR(@PathVariable  int labor , @PathVariable String zona, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		faena r = new faena();
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_FAENA_LABOR(labor, zona);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_HORAS_MES/{mes}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody int GET_HORAS_MES(@PathVariable  int mes ,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		int r = 0;
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_HORAS_MES(mes);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_RECORRIDO_CAMPO/{campo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<recorrido> GET_RECORRIDO_CAMPO(@PathVariable  String campo ,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<recorrido> r = new ArrayList<recorrido>();
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_RECORRIDO_CAMPO(campo);
		return r;
	}
	
	
	
//	SELECT
	@RequestMapping(value = "/AGRO/GETLISTADO/{fecha_desde}/{fecha_hasta}/{campo}/{especie}/{variedad}/{faena}/{labor}/{trabajador}/{tipo_trabajador}/{contratista}/{cuartel}/{estado}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<RENDIMIENTO_DIARIO> GET_Listado 	(@PathVariable String fecha_desde, @PathVariable String fecha_hasta, @PathVariable String campo, 	
			@PathVariable String especie, @PathVariable String variedad, @PathVariable String faena, 
			@PathVariable String labor, @PathVariable String trabajador, @PathVariable String tipo_trabajador, 
			@PathVariable String contratista, @PathVariable String cuartel, @PathVariable int estado,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<RENDIMIENTO_DIARIO> r = new ArrayList<RENDIMIENTO_DIARIO>();
		if (ses.isValid()) {
			return r;
		}
		System.out.println(campo);
		SESION mc = new SESION(httpSession);
		String sqlCampos = "";
		int valor = 0;
		if(campo.equals("0")){
			sqlCampos = "SELECT codigo_campo FROM usuario_campo WHERE codigo_usuario = "+mc.getIdUserSesion()+"";
			valor = 1;
		}else{
			String[] campos = campo.split(",");
			int count = 0;
			for(int i = 0; i < campos.length; i++){
				if(count == 0){
					sqlCampos += "'"+campos[i]+"'";
				}else{
					valor = 1;
					sqlCampos += ", '"+campos[i]+"'";
				}
				count++;
			}
		}
		r = RENDIMIENTO.GET_Listado(fecha_desde, fecha_hasta, sqlCampos,  especie, variedad, faena, 
				labor, trabajador,tipo_trabajador,contratista,cuartel,estado, valor,  httpSession);
		return r;
	}
	
	@RequestMapping(value = "/AGRO/GETTRABAJADOR/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Trabajador> GETTRABAJADOR(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<Trabajador> r = new ArrayList<Trabajador>();
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GETTRABAJADOR();
		return r;
	}
	
	@RequestMapping(value = "/AGRO/GET_DotacionDiaria/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<DOTACION_DIARIA> GET_DotacionDiaria(@RequestBody DOTACION_DIARIA row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<DOTACION_DIARIA> r = new ArrayList<DOTACION_DIARIA>();
		if (ses.isValid()) {
			return r;
		}
		r = DotacionDiaria.GET_DotacionDiaria(row);
		return r;
	}
	
	@RequestMapping(value = "/AGRO/GET_ListRendContratista/{CAMPO}/{CONTRATISTA}/{DESDE}/{HASTA}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<RENDIMIENTO_DIARIO> GET_ListRendContratista
	(@PathVariable String CAMPO, @PathVariable String CONTRATISTA,@PathVariable String DESDE,@PathVariable String HASTA
			, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<RENDIMIENTO_DIARIO> r = new ArrayList<RENDIMIENTO_DIARIO>();
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_ListRendContratista(CAMPO,CONTRATISTA,DESDE,HASTA); 
		return r;
	}
	
	@RequestMapping(value = "/AGRO/GENERAR_LIQUIDACION", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody int GENERAR_LIQUIDACION (@RequestBody  LIQUIDACION row,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		int r = 0;
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GENERAR_LIQUIDACION(row);
		return r;
	}
	
	@RequestMapping(value = "/AGRO/GET_LIQUIDACION", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LIQUIDACION> GET_LIQUIDACION (@RequestBody  LIQUIDACION row,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<LIQUIDACION> r = new ArrayList<LIQUIDACION>();
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_LIQUIDACION(row); 
		return r;
	}
	@RequestMapping(value = "/AGRO/ORDEN_PAGO/{codigo}/{orden}/{n_factura}/{valor_retencion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ORDEN_PAGO (@PathVariable String codigo, @PathVariable String orden, @PathVariable String n_factura, @PathVariable int valor_retencion, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RENDIMIENTO.ORDEN_PAGO(codigo,orden, n_factura, valor_retencion);  
	}
	@RequestMapping(value = "/AGRO/RECHAZAR/{codigo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean RECHAZAR (@PathVariable String codigo,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RENDIMIENTO.RECHAZAR(codigo);  
	}
	@RequestMapping(value = "/AGRO/GET_RD_INDIVIDUAL", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RENDIMIENTO_DIARIO GET_RD_INDIVIDUAL(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		RENDIMIENTO_DIARIO r = new RENDIMIENTO_DIARIO();
		int codigo_rd = Integer.parseInt(request.getParameter("CODIGO"));
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_RD_INDIVIDUAL(codigo_rd);
		return r;
	}
	
	@RequestMapping(value = "/AGRO/GET_RD_INDIVIDUAL_RG", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RENDIMIENTO_DIARIO GET_RD_INDIVIDUAL_RG(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		RENDIMIENTO_DIARIO r = new RENDIMIENTO_DIARIO();
		int codigo_rd = Integer.parseInt(request.getParameter("CODIGO"));
		int codigo_rg = Integer.parseInt(request.getParameter("CODIGO_RG"));
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_RD_INDIVIDUAL_RG(codigo_rd, codigo_rg);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_RENDIMIENTOS_GENERALES", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody CUADRILLA GET_RENDIMIENTOS_GENERALES(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		CUADRILLA r = new CUADRILLA();
		if (ses.isValid()) {
			return r;
		}
		int codigo_rg = Integer.parseInt(request.getParameter("CODIGO_RG"));
		r = RENDIMIENTO.GET_RENDIMIENTOS_GENERALES(codigo_rg);
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_CUADRILLA_REPLICADA/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_CUADRILLA_REPLICADA(@RequestBody  CUADRILLA row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		System.out.println("dsd");
		return cuadrilla.ADD_CUADRILLA_REPLICADA(row);
	}
	@RequestMapping(value = "/AGRO/GREN_CON_BY_ID", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<RENDIMIENTO_DIARIO> GREN_CON_BY_ID (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<RENDIMIENTO_DIARIO> r = new ArrayList<RENDIMIENTO_DIARIO>();
		if (ses.isValid()) {
			return r;
		}
		int codigo_liq = Integer.parseInt(request.getParameter("CODIGO_LIQ"));
		r = RENDIMIENTO.GREN_CON_BY_ID(codigo_liq); 
		return r;
	}
	@RequestMapping(value = "/AGRO/DELETE_RENDIMIENTO_LIQUIDACION/{codigo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean DELETE_RENDIMIENTO_LIQUIDACION (@PathVariable int codigo,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RENDIMIENTO.DELETE_RENDIMIENTO_LIQUIDACION(codigo);  
	}
	@RequestMapping(value = "/AGRO/UPD_RENDIMIENTO_LIQUIDACION/{codigo}/{n_factura}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPD_RENDIMIENTO_LIQUIDACION (@PathVariable int codigo, @PathVariable String n_factura,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RENDIMIENTO.UPD_RENDIMIENTO_LIQUIDACION(codigo, n_factura);  
	}
	@RequestMapping(value = "/AGRO/GET_DETALLE_HX_SEMANA", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<RENDIMIENTO_DIARIO> GET_DETALLE_HX_SEMANA(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<RENDIMIENTO_DIARIO> r = new ArrayList<RENDIMIENTO_DIARIO>();
		int trabajador = Integer.parseInt(request.getParameter("TRABAJADOR"));
		String fecha = request.getParameter("FECHA");
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_DETALLE_HX_SEMANA(trabajador, fecha);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_RENDIMIENTOS_VALIDADOS", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<RENDIMIENTO_GENERAL> GET_RENDIMIENTOS_VALIDADOS(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<RENDIMIENTO_GENERAL> r = new ArrayList<RENDIMIENTO_GENERAL>();
		String campo = request.getParameter("CAMPO");
		String fecha_desde = request.getParameter("FECHADESDE");
		String fecha_hasta = request.getParameter("FECHAHASTA");
		String tipo = request.getParameter("TIPO");
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_RENDIMIENTOS_VALIDADOS(campo, fecha_desde, fecha_hasta, tipo);
		return r;
	}
	@RequestMapping(value = "/AGRO/UPD_ORDEN_PAGO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPD_ORDEN_PAGO(@RequestBody  LIQUIDACION row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RENDIMIENTO.UPD_ORDEN_PAGO(row);
	}
	@RequestMapping(value = "/AGRO/UPD_ORDEN_PAGO_ASIENTO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPD_ORDEN_PAGO_ASIENTO(@RequestBody  LIQUIDACION row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RENDIMIENTO.UPD_ORDEN_PAGO_ASIENTO(row);
	}
	@RequestMapping(value = "/AGRO/GEN_ASIENTO_CONTABLE", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<asiento_contable> GEN_ASIENTO_CONTABLE (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<asiento_contable> r = new ArrayList<asiento_contable>();
		if (ses.isValid()) {
			return r;
		}
		int codigo_liq = Integer.parseInt(request.getParameter("CODIGO_LIQ"));
		r = RENDIMIENTO.GEN_ASIENTO_CONTABLE(codigo_liq); 
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_REND_MASIVO/{codigo_rg}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody CUADRILLA GET_REND_MASIVO(@PathVariable int codigo_rg, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		CUADRILLA r = new CUADRILLA();
		if (ses.isValid()) {
			return r;
		}
		r = cuadrilla.GET_REND_MASIVO(codigo_rg);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_REND_GNRAL/{codigo_rg}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RENDIMIENTO_GENERAL GET_REND_GNRAL(@PathVariable int codigo_rg, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		RENDIMIENTO_GENERAL r = new RENDIMIENTO_GENERAL();
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_REND_GNRAL(codigo_rg);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_NUMERO/{sql}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody int GET_NUMERO (@PathVariable String sql, HttpSession httpSession) throws Exception {
		return RENDIMIENTO.r_folio(sql);  
	}
	@RequestMapping(value = "/AGRO/UPD_RENDIMIENTO_DIARIO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<respuesta> UPD_RENDIMIENTO_DIARIO(@RequestBody  ArrayList<RENDIMIENTO_DIARIO> row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<respuesta> res = new ArrayList<respuesta>();
		if (ses.isValid()) {
			return res;
		}
		int estado = 8;
		int count = 0;
		for(RENDIMIENTO_DIARIO r: row){
			ConnectionDB db = new ConnectionDB();
			if(count == 0){
				if(r.getCodigo_rg() != 0){
					RENDIMIENTO.ADD_NOT_EXIST(row);
				}
				String tipo = "";
				if(r.getCodigo_rg() != 0){
					tipo += "MASIVO";
				}else{
					tipo += "INDIVIDUAL";
				}
				RENDIMIENTO.UPDATE_RENDIMIENTO(tipo, estado, r.getCodigo_rg());
			}
			count++;
			respuesta e = new respuesta();
			e = RENDIMIENTO.UPD_RENDIMIENTO_DIARIO(db, r);
			res.add(e);
		}
		return res;
	}
	@RequestMapping(value = "/AGRO/ADD_RENDIMIENTO_DIARIO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<respuesta> ADD_RENDIMIENTO_DIARIO_CONTRATISTA(@RequestBody  ArrayList<RENDIMIENTO_DIARIO> row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<respuesta> res = new ArrayList<respuesta>();
		if (ses.isValid()) {
			return res;
		}
		int estado = 8;
		int count = 0;
		for(RENDIMIENTO_DIARIO r: row){
			ConnectionDB db = new ConnectionDB();
			if(count == 0){
				if(r.getCodigo_rg() != 0){
					RENDIMIENTO.ADD_NOT_EXIST(row);
				}
				String tipo = "";
				if(r.getCodigo_rg() != 0){
					tipo += "MASIVO";
				}else{
					tipo += "INDIVIDUAL";
				}
				RENDIMIENTO.UPDATE_RENDIMIENTO(tipo, estado, r.getCodigo_rg());
			}
			count++;
			respuesta e = new respuesta();
			e = RENDIMIENTO.ADD_RENDIMIENTO_DIARIO(db, r,httpSession);
			res.add(e);
		}
		return res;
	}
	@RequestMapping(value = "/AGRO/DEL_RENDIMIENTO_DIARIO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<respuesta> DEL_RENDIMIENTO_DIARIO(@RequestBody  ArrayList<RENDIMIENTO_DIARIO> row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<respuesta> res = new ArrayList<respuesta>();
		if (ses.isValid()) {
			return res;
		}
		for(RENDIMIENTO_DIARIO r: row){
			ConnectionDB db = new ConnectionDB();
			respuesta e = new respuesta();
			e = RENDIMIENTO.DEL_RENDIMIENTO_DIARIO(db, r);
			res.add(e);
		}
		return res;
	}
	@RequestMapping(value = "/AGRO/GET_CIERRE_MENSUAL", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<cierre_mensual> GET_CIERRE_MENSUAL (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<cierre_mensual> r = new ArrayList<cierre_mensual>();
		if (ses.isValid()) {
			return r;
		}
		String campo = request.getParameter("CAMPO");
		String periodo = request.getParameter("PERIODO");
		r = RENDIMIENTO.GET_CIERRE_MENSUAL(campo, periodo); 
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_CIERRE_MENSUAL", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_CIERRE_MENSUAL(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		boolean e = false;
		if (ses.isValid()) {
			return false;
		}
		String sociedad = request.getParameter("CAMPO");
		String periodo = request.getParameter("PERIODO");
		ConnectionDB db = new ConnectionDB();
		RENDIMIENTO.DELETE_CIERRE_MENSUAL(db, periodo, sociedad);
		e = RENDIMIENTO.ADD_CIERRE_MENSUAL(db, periodo, sociedad);
		return e;
	}
	@RequestMapping(value = "/AGRO/GET_ALL_TRABAJADORES", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<trabajadores> GET_ALL_TRABAJADORES (HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
//		if (ses.isValid()) {
//			return false;
//		}
		ArrayList<trabajadores> r = new ArrayList<trabajadores>();
		r = RENDIMIENTO.GET_ALL_TRABAJADORES(ses.getIdUser());  
		return r;
	}
	@RequestMapping(value = "/AGRO/GETLISTADO_CODIFICADO/{fecha_desde}/{fecha_hasta}/{campo}/{especie}/{variedad}/{faena}/{labor}/{trabajador}/{tipo_trabajador}/{contratista}/{cuartel}/{estado}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<RENDIMIENTO_DIARIO> GETLISTADO_CODIFICADO 	(@PathVariable String fecha_desde, @PathVariable String fecha_hasta, @PathVariable String campo, 	
			@PathVariable String especie, @PathVariable String variedad, @PathVariable String faena, 
			@PathVariable String labor, @PathVariable String trabajador, @PathVariable String tipo_trabajador, 
			@PathVariable String contratista, @PathVariable String cuartel, @PathVariable int estado,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<RENDIMIENTO_DIARIO> r = new ArrayList<RENDIMIENTO_DIARIO>();
		if (ses.isValid()) {
			return r;
		}
		System.out.println(campo);
		SESION mc = new SESION(httpSession);
		String sqlCampos = "";
		if(campo.equals("0")){
			sqlCampos = "SELECT codigo_campo FROM usuario_campo WHERE codigo_usuario = "+ses.getIdUser()+"";
		}else{
			String[] campos = campo.split(",");
			System.out.println(campos);
			int count = 0;
			for(int i = 0; i < campos.length; i++){
				if(count == 0){
					sqlCampos += "'"+campos[i]+"'";
				}else{
					sqlCampos += ", '"+campos[i]+"'";
				}
				count++;
			}
		}
		r = RENDIMIENTO.GETLISTADO_CODIFICADO(fecha_desde, fecha_hasta, sqlCampos,  especie, variedad, faena, 
				labor, trabajador,tipo_trabajador,contratista,cuartel,estado);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_CIERRE_TERCEROS", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<cierre_mensual> GET_CIERRE_TERCEROS (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<cierre_mensual> r = new ArrayList<cierre_mensual>();
		if (ses.isValid()) {
			return r;
		}
		String campo = request.getParameter("CAMPO");
		String periodo = request.getParameter("PERIODO");
		r = RENDIMIENTO.GET_CIERRE_TERCEROS(campo, periodo); 
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_CIERRE_TERCEROS", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_CIERRE_TERCEROS(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		boolean e = false;
		if (ses.isValid()) {
			return false;
		}
		String sociedad = request.getParameter("CAMPO");
		String periodo = request.getParameter("PERIODO");
		ConnectionDB db = new ConnectionDB();
		RENDIMIENTO.DELETE_CIERRE_TERCEROS(db, periodo, sociedad);
		e = RENDIMIENTO.ADD_CIERRE_TERCEROS(db, periodo, sociedad);
		return e;
	}
	@RequestMapping(value = "/AGRO/GET_REVISION_ASISTENCIA", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<String[]> GET_REVISION_ASISTENCIA(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<String[]> r = new ArrayList<String[]>();
		if (ses.isValid()) {
			return r;
		}
		String campo = request.getParameter("CAMPO");
		String fechasAux = request.getParameter("FECHAS");
		String[] fechas = fechasAux.split(",");
		int estado = Integer.parseInt(request.getParameter("ESTADO"));
		r = RENDIMIENTO.GET_REVISION_ASISTENCIA(campo, fechas, estado);
		return r;
	}
	@RequestMapping(value = "/AGRO/DELETE_RENDIMIENTO_DUPLICADO/{codigo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean DELETE_RENDIMIENTO_DUPLICADO (@PathVariable int codigo,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RENDIMIENTO.DELETE_RENDIMIENTO_DUPLICADO(codigo);  
	}
	@RequestMapping(value = "/AGRO/GET_TR_SIN_RENDIMIENTO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<String[]> GET_TR_SIN_RENDIMIENTO(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<String[]> r = new ArrayList<String[]>();
		if (ses.isValid()) {
			return r;
		}
		String campo = request.getParameter("CAMPO");
		String periodo = request.getParameter("PERIODO");
		r = RENDIMIENTO.GET_TR_SIN_RENDIMIENTO(campo, periodo);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_RESUMEN_DIGITACION", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<String[]> GET_RESUMEN_DIGITACION(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<String[]> r = new ArrayList<String[]>();
		if (ses.isValid()) {
			return r;
		}
		String sociedad = request.getParameter("SOCIEDAD");
		String campo = request.getParameter("CAMPO");
		String periodo = request.getParameter("PERIODO");
		int estado = Integer.parseInt(request.getParameter("ESTADO"));
		r = RENDIMIENTO.GET_RESUMEN_DIGITACION(sociedad, campo, periodo, estado);
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_TR_SIN_DIGITACION", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_TR_SIN_DIGITACION(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		boolean e = false;
		if (ses.isValid()) {
			return false;
		}
		String fechasAux = request.getParameter("FECHAS");
		String[] fechas = fechasAux.split(",");
		RENDIMIENTO.DELETE_TR_SIN(fechas[0]);
		e = RENDIMIENTO.ADD_TR_SIN_DIGITACION(fechas);
		return e;
	}
	@RequestMapping(value = "/AGRO/GETLISTADO_TR_HUERTO/{fecha_desde}/{fecha_hasta}/{campo}/{especie}/{variedad}/{faena}/{labor}/{trabajador}/{tipo_trabajador}/{contratista}/{cuartel}/{estado}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<RENDIMIENTO_DIARIO> GETLISTADO_TR_HUERTO 	(@PathVariable String fecha_desde, @PathVariable String fecha_hasta, @PathVariable String campo, 	
			@PathVariable String especie, @PathVariable String variedad, @PathVariable String faena, 
			@PathVariable String labor, @PathVariable String trabajador, @PathVariable String tipo_trabajador, 
			@PathVariable String contratista, @PathVariable String cuartel, @PathVariable int estado,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<RENDIMIENTO_DIARIO> r = new ArrayList<RENDIMIENTO_DIARIO>();
		if (ses.isValid()) {
			return r;
		}
		System.out.println(campo);
		SESION mc = new SESION(httpSession);
		String sqlCampos = "";
		int valor = 0;
		if(campo.equals("0")){
			sqlCampos = "SELECT codigo_campo FROM usuario_campo WHERE codigo_usuario = "+mc.getIdUserSesion()+"";
			valor = 1;
		}else{
			String[] campos = campo.split(",");
			int count = 0;
			for(int i = 0; i < campos.length; i++){
				if(count == 0){
					sqlCampos += "'"+campos[i]+"'";
				}else{
					valor = 1;
					sqlCampos += ", '"+campos[i]+"'";
				}
				count++;
			}
		}
		r = RENDIMIENTO.GETLISTADO_TR_HUERTO(fecha_desde, fecha_hasta, sqlCampos,  especie, variedad, faena, 
				labor, trabajador,tipo_trabajador,contratista,cuartel,estado, valor,  httpSession);
		return r;
	}
	@RequestMapping(value = "/AGRO/DESCARGAR_EXCEL_LISTADO/{nombre}", method = RequestMethod.GET)
	@CrossOrigin(origins = {"*"})
	public @ResponseBody ResponseEntity<Set<String>> DESCARGAR_EXCEL_LISTADO(@PathVariable String nombre ,HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		System.out.println("pase");
		try {
			String fileName = nombre;
			fileName = fileName.replaceAll("\"", "");
			System.out.println("ruta: {}"+fileName);
			
			String urlDocGenerado = utils.reportesExcel() + fileName+".xlsx";
			
		
			System.out.println("aqui   "+urlDocGenerado);
			File file = new File(urlDocGenerado);
			FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);			
			response.addHeader("Content-disposition", "attachment; filename= "+fileName+".xlsx");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setContentLength(bytes.length);
			response.setCharacterEncoding("iso-8859-1");
			ServletOutputStream out = response.getOutputStream();
			
			out.write(bytes);
			out.flush();
			out.close();
			fileInputStreamReader.close();
			System.out.println("termine de hacer el archivo");
			return new ResponseEntity<>(Collections.singleton(urlDocGenerado), HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			Collections.singleton("");
			return null;
		}
	}
	@RequestMapping(value = "/AGRO/GET_ASISTENCIA_TRABAJADOR", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_ASISTENCIA_TRABAJADOR(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return r;
		}
		int periodo = Integer.parseInt(request.getParameter("PERIODO"));
		int trabajador = Integer.parseInt(request.getParameter("TRABAJADOR"));
		r = RENDIMIENTO.GET_ASISTENCIA_TRABAJADOR(periodo, trabajador);
		return r;
	}
	@RequestMapping(value = "/AGRO/GENERAR_WORD_ASISTENCIA", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GENERAR_WORD_ASISTENCIA(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		RENDIMIENTO r = new RENDIMIENTO();
		if (ses.isValid()) {
			return "0";
		}
		int id =  Integer.parseInt(request.getParameter("ID"));
		String periodo =  request.getParameter("PERIODO");
		String sociedad =  request.getParameter("SOCIEDAD");
		String campo =  request.getParameter("CAMPO");
		System.out.println(campo);
		String a = r.GENERAR_WORD_ASISTENCIA(id, periodo, sociedad, campo, httpSession);
		return a;
	}
	@RequestMapping(value = "/AGRO/DESCARGAR_WORD/{nombre}", method = RequestMethod.GET)
	@CrossOrigin(origins = {"*"})
	public @ResponseBody ResponseEntity<Set<String>> DESCARGAR_WORD(@PathVariable String nombre ,HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		System.out.println("pase");
		try {
			String fileName = nombre;
			fileName = fileName.replaceAll("\"", "");
			System.out.println("ruta: {}"+fileName);
			
			String urlDocGenerado = utils.reportesExcel() + fileName+".docx";
			
		
			System.out.println("aqui   "+urlDocGenerado);
			File file = new File(urlDocGenerado);
			FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);			
			response.addHeader("Content-disposition", "attachment; filename= "+fileName+".docx");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setContentLength(bytes.length);
			response.setCharacterEncoding("iso-8859-1");
			ServletOutputStream out = response.getOutputStream();
			
			out.write(bytes);
			out.flush();
			out.close();
			fileInputStreamReader.close();
			System.out.println("termine de hacer el archivo");
			return new ResponseEntity<>(Collections.singleton(urlDocGenerado), HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			Collections.singleton("");
			return null;
		}
	}
	@RequestMapping(value = "/AGRO/GET_CAMPO_CECO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_CAMPO_CECO(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return r;
		}
		String origen = request.getParameter("ORIGEN");
		String destino = request.getParameter("DESTINO");
		r = RENDIMIENTO.GET_CAMPO_CECO(origen, destino);
		System.out.println(r);
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_CAMPO_CECO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String ADD_CAMPO_CECO(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = new String();
		if (ses.isValid()) {
			return r;
		}
		String origen = request.getParameter("ORIGEN");
		String destino = request.getParameter("DESTINO");
		String ceco = request.getParameter("CECO");
		r = RENDIMIENTO.ADD_CAMPO_CECO(origen, destino, ceco);
		return r;
	}
	@RequestMapping(value = "/AGRO/GENERAR_WORD_MASIVO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GENERAR_WORD_MASIVO(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		RENDIMIENTO r = new RENDIMIENTO();
		if (ses.isValid()) {
			return "0";
		}
		String sociedad =  request.getParameter("SOCIEDAD");
		String periodo =  request.getParameter("PERIODO");
		String campo = request.getParameter("CAMPO");
		System.out.println(campo);
		String a = r.GENERAR_WORD_MASIVO(sociedad, periodo, campo,httpSession);
		return a;
	}
	@RequestMapping(value = "/AGRO/GET_REPORTE_CIERRE", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<cierre_mensual> GET_REPORTE_CIERRE (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<cierre_mensual> r = new ArrayList<cierre_mensual>();
		if (ses.isValid()) {
			return r;
		}
		String campo = request.getParameter("CAMPO");
		String periodo = request.getParameter("PERIODO");
		r = RENDIMIENTO.GET_REPORTE_CIERRE(campo, periodo); 
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_CONTRATISTAS_X", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<trabajadores> GET_CONTRATISTAS_X (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
//		if (ses.isValid()) {
//			return false;
//		}
		ArrayList<trabajadores> r = new ArrayList<trabajadores>();
		r = RENDIMIENTO.GET_CONTRATISTAS_X(ses.getIdUser());  
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_GASTOS_CECO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<CentralizacionDetalleTmp> GET_GASTOS_CECO (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<CentralizacionDetalleTmp> r = new ArrayList<CentralizacionDetalleTmp>();
		if (ses.isValid()) {
			return r;
		}
		String sociedad = request.getParameter("SOCIEDAD");
		String campo = request.getParameter("CAMPO");
		int periodo = Integer.parseInt(request.getParameter("PERIODO"));
		r = RENDIMIENTO.GET_GASTOS_CECO(sociedad, campo, periodo); 
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_DETALLE_GASTOS_CECO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<CentralizacionDetalleTmp> GET_DETALLE_GASTOS_CECO (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<CentralizacionDetalleTmp> r = new ArrayList<CentralizacionDetalleTmp>();
		if (ses.isValid()) {
			return r;
		}
		String cuenta = request.getParameter("CUENTA");
		String ceco = request.getParameter("CECO");
		String ordenco = request.getParameter("ORDENCO");
		r = RENDIMIENTO.GET_DETALLE_GASTOS_CECO(cuenta, ceco, ordenco); 
		return r;
	}
	@RequestMapping(value = "/AGRO/UPD_HORAS_TRABAJADAS/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<respuesta> UPD_HORAS_TRABAJADAS(@RequestBody  String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<respuesta> res = new ArrayList<respuesta>();
		if (ses.isValid()) {
			return res;
		}
		respuesta e = new respuesta();
		e = RENDIMIENTO.UPD_HORAS_TRABAJADAS(row);
		return res;
	}
	@RequestMapping(value = "/AGRO/GET_HORARIO_CAMPO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_HORARIO_CAMPO (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return r;
		}
		String campo = request.getParameter("CAMPO");
		r = RENDIMIENTO.GET_HORARIO_CAMPO(campo); 
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_HORARIO_CAMPO",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	//@CrossOrigin(origins = "",allowedHeaders="",exposedHeaders= "Access-Control-Allow-Origin, Access-Control-Allow-Credentials",methods= {RequestMethod.PUT})
	public @ResponseBody boolean ADD_HORARIO_CAMPO (@RequestBody String row,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RENDIMIENTO.ADD_HORARIO_CAMPO(row);
//		return false;
	}
	@RequestMapping(value = "/AGRO/ADD_HORARIO",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_HORARIO (@RequestBody String row,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RENDIMIENTO.ADD_HORARIO(row);
	}
	@RequestMapping(value = "/AGRO/UPD_HORARIO_CAMPO",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPD_HORARIO_CAMPO (@RequestBody String row,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RENDIMIENTO.UPD_HORARIO_CAMPO(row);
	}
	@RequestMapping(value = "/AGRO/DEL_HORARIO",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String DEL_HORARIO (HttpServletRequest request,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return "Fuera de Sesion";
		}
		String campo = request.getParameter("CAMPO");
		return RENDIMIENTO.DEL_HORARIO(campo);
	}
	@RequestMapping(value = "/AGRO/UPD_HORARIO",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPD_HORARIO (@RequestBody String row,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RENDIMIENTO.UPD_HORARIO(row);
	}
	@RequestMapping(value = "/AGRO/UPD_CECOS_CAMPO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPD_CECOS_CAMPO (@RequestBody String row,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RENDIMIENTO.UPD_CECOS_CAMPO(row, httpSession);  
	}
	@RequestMapping(value = "/AGRO/UPD_DATOS_CAMPO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPD_DATOS_CAMPO (@RequestBody String row,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RENDIMIENTO.UPD_DATOS_CAMPO(row, httpSession);  
	}
	@RequestMapping(value = "/AGRO/LIQ_CONTRATISTA_PDF/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String LIQ_CONTRATISTA_PDF(@PathVariable int id, @RequestBody  String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		excelExportOrden r = new excelExportOrden();
		if (ses.isValid()) {
			return "0";
		}
		String a = RENDIMIENTO.LIQ_CONTRATISTA_PDF(id, row);
		return a;
	}
	@RequestMapping(value = "/AGRO/INSERT_DOC_LIQ", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean INSERT_DOC_LIQ(@RequestBody String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return RENDIMIENTO.INSERT_DOC_LIQ(row);
	}
	@RequestMapping(value = "/AGRO/GET_GASTOS_TRABAJADOR", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_GASTOS_TRABAJADOR (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return r;
		}
		int periodo = Integer.parseInt(request.getParameter("PERIODO"));
		int sociedad = Integer.parseInt(request.getParameter("SOCIEDAD"));
		r = RENDIMIENTO.GET_GASTOS_TRABAJADOR(periodo, sociedad); 
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_TRABAJADORES_PERIODO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_TRABAJADORES_PERIODO (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return r;
		}
		String periodo = request.getParameter("PERIODO");
		r = RENDIMIENTO.GET_TRABAJADORES_PERIODO(periodo); 
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_TRABAJADORES_SOCIEDAD", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_TRABAJADORES_SOCIEDAD (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return r;
		}
		int sociedad = Integer.parseInt(request.getParameter("SOCIEDAD"));
		String periodo = request.getParameter("PERIODO");
		int haber = Integer.parseInt(request.getParameter("HABER"));
		r = RENDIMIENTO.GET_TRABAJADORES_SOCIEDAD(sociedad, periodo, haber); 
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_HABERES_TRABAJADOR_MES", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_HABERES_TRABAJADOR_MES (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return r;
		}
		int trabajador = Integer.parseInt(request.getParameter("TRABAJADOR"));
		int periodo = Integer.parseInt(request.getParameter("PERIODO"));
		r = RENDIMIENTO.GET_HABERES_TRABAJADOR_MES(trabajador, periodo); 
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_HABERES_CECO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_HABERES_CECO (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return r;
		}
		int trabajador = Integer.parseInt(request.getParameter("TRABAJADOR"));
		int periodo = Integer.parseInt(request.getParameter("PERIODO"));
		int haber = Integer.parseInt(request.getParameter("HABER"));
		int sociedad = Integer.parseInt(request.getParameter("SOCIEDAD"));
		r = RENDIMIENTO.GET_HABERES_CECO(trabajador, periodo, haber, sociedad); 
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_HABERES_AGRO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_HABERES_AGRO (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return r;
		}
		r = RENDIMIENTO.GET_HABERES_AGRO(); 
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_IMG_FACTURA", method = RequestMethod.POST)
	public @ResponseBody int insertDocumentos(HttpServletRequest request, @RequestParam("documento") MultipartFile multipartFile, HttpSession httpSession) throws Exception {

		InputStream fileInputStream = multipartFile.getInputStream();
		String nombreDocumento = request.getParameter("nombre");

		byte[] contents;
		contents = IOUtils.toByteArray(fileInputStream);
		Blob fileBlob = new javax.sql.rowset.serial.SerialBlob(contents);

		Documentos documentos = new Documentos();

		documentos.setCodTrabajador(Integer.parseInt(request.getParameter("id")));
		documentos.setNombreDocumento(nombreDocumento);
		documentos.setDocumento(fileBlob);

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return 0;
		}

		return noIncidencia.INSERT_DOC_FACTURA(documentos);
	}
}
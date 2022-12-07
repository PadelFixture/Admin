package lib.data.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.poi.util.IOUtils;
import org.json.JSONArray;
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
import org.springframework.web.bind.annotation.ResponseBody;

import lib.SADB.RENDIMIENTO;
import lib.classSW.Documentos;
import lib.db.ConnectionDB;
import lib.db.MantenedorBD;
import lib.db.dteBD;
import lib.db.recotecDB;
import lib.excelSA.excelExportOrden;
import lib.security.session;
import sun.misc.BASE64Encoder;
import wordCreator.utils;

@Controller
public class recotecServices {

	@RequestMapping(value = "/REC/UPDATE_RECEPTOR", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPDATE_RECEPTOR(@RequestBody String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return recotecDB.UPDATE_RECEPTOR(row);
	}
	@RequestMapping(value = "/REC/UPDATE_USUARIOS", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPDATE_USUARIOS(@RequestBody String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return recotecDB.UPDATE_USUARIOS(row);
	}
	@RequestMapping(value = "/REC/UPDATE_CONCEPTOS", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPDATE_CONCEPTOS(@RequestBody String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return recotecDB.UPDATE_CONCEPTOS(row);
	}
	@RequestMapping(value = "/REC/INSERT_RECEPTOR", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean INSERT_RECEPTOR(@RequestBody String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return recotecDB.INSERT_RECEPTOR(row);
	}
	@RequestMapping(value = "/REC/ADD_USUARIO", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_USUARIO(@RequestBody String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return recotecDB.ADD_USUARIO(row);
	}
	@RequestMapping(value = "/REC/ADD_CONCEPTO", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_CONCEPTO(@RequestBody String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return recotecDB.ADD_CONCEPTO(row);
	}
	@RequestMapping(value = "/REC/DELETE_RECEPTOR", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean DELETE_RECEPTOR(@RequestBody String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return recotecDB.DELETE_RECEPTOR(row);
	}
	@RequestMapping(value = "/REC/DELETE_USUARIO", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean DELETE_USUARIO(@RequestBody String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return recotecDB.DELETE_USUARIO(row);
	}
	@RequestMapping(value = "/REC/DELETE_CONCEPTO", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean DELETE_CONCEPTO(@RequestBody String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return recotecDB.DELETE_CONCEPTO(row);
	}
	@RequestMapping(value = "/REC/INSERT_VALIDACION_SIM", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String INSERT_VALIDACION_SIM(@RequestBody  String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return "0";
		}
		JSONArray arr = new JSONArray(row);
		ConnectionDB db = new ConnectionDB();
		for(int ix = 0; ix < arr.length(); ix++){
        	JSONObject e = new JSONObject(arr.getJSONObject(ix).toString());
        	
        	recotecDB.INSERT_VALIDACION_SIM(db, arr.getJSONObject(ix).toString());
		}

		db.close();
		String a = "Ok";
		return a;
	}
	@RequestMapping(value = "/REC/INSERT_STOCK_SIM", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String INSERT_STOCK_SIM(@RequestBody  String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return "0";
		}
    	String res = recotecDB.INSERT_STOCK_SIM(row);

		return res;
	}
	@RequestMapping(value = "/REC/GET_USUARIOS", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_USUARIOS (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return r;
		}
		
		r = recotecDB.GET_USUARIOS(ses.getRolPrivado(), ses.getIdUser()); 
		return r;
	}
	@RequestMapping(value = "/REC/GET_PERFIL", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_PERFIL (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return r;
		}
		r = recotecDB.GET_PERFIL(); 
		return r;
	}
	@RequestMapping(value = "/REC/GET_TIPO_CONCEPTO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_TIPO_CONCEPTO (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return r;
		}
		r = recotecDB.GET_TIPO_CONCEPTO(); 
		return r;
	}
	
	

	@RequestMapping(value = "/RECOTEC", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String RECOTEC(@RequestBody  String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return "0";
		}
		r = recotecDB.RECOTEC(row); 
		ConnectionDB db = new ConnectionDB();
		db.close();
		return r;
	}
	@RequestMapping(value = "/AGRO/DELETE_PERIODO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String DELETE_PERIODO(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return "Su sesion ha expirado";
		}
		String periodo = request.getParameter("PERIODO");
		String sql = "";
		sql +=	"DELETE FROM ACTIVACIONES_DIARIAS WHERE DATE_FORMAT(REQUESTDATE, '%Y%m') = DATE_FORMAT('"+periodo+"', '%Y%m');";
		System.out.println(sql);
		r = RENDIMIENTO.DELETE(sql);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_ACTIVACIONES_DIARIAS", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_ACTIVACIONES_DIARIAS(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return "Su sesion ha expirado";
		}
		String periodo = request.getParameter("PERIODO");
		String sql = "";
		sql +=	"SELECT *FROM ACTIVACIONES_DIARIAS WHERE DATE_FORMAT(REQUESTDATE, '%Y%m') = DATE_FORMAT('"+periodo+"', '%Y%m');";
		System.out.println(sql);
		r = RENDIMIENTO.GET_RECOTEC(sql);
		return r;
	}
	@RequestMapping(value = "/AGRO/VENTAS_ONLINE", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String VENTAS_ONLINE(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return "Su sesion ha expirado";
		}
		String desde = request.getParameter("_DESDE");
		String hasta = request.getParameter("_HASTA");
		String dis = request.getParameter("_DISTRIBUIDOR");
		String sub = request.getParameter("_SUBDISTRIBUIDOR");
		String vendedor = request.getParameter("_VENDEDOR");
		String comercio = request.getParameter("_COMERCIO");
		String sql = "";
		sql +=	"CALL VENTAS_ONLINE('"+desde+"', '"+hasta+"', '"+dis+"', '"+sub+"', '"+vendedor+"', '"+comercio+"')";
		System.out.println(sql);
		r = RENDIMIENTO.GET_RECOTEC(sql);
		return r;
	}
	@RequestMapping(value = "/AGRO/VENTAS_ONE_CLICK", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String VENTAS_ONE_CLICK(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return "Su sesion ha expirado";
		}
		String desde = request.getParameter("_DESDE");
		String hasta = request.getParameter("_HASTA");
		String dis = request.getParameter("_DISTRIBUIDOR");
		String sub = request.getParameter("_SUBDISTRIBUIDOR");
		String vendedor = request.getParameter("_VENDEDOR");
		String comercio = request.getParameter("_COMERCIO");
		String sql = "";
		sql +=	"CALL VENTAS_ONE_CLICK('"+desde+"', '"+hasta+"', '"+dis+"', '"+sub+"', '"+vendedor+"', '"+comercio+"')";
		System.out.println(sql);
		r = RENDIMIENTO.GET_RECOTEC(sql);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_COMERCIO_RUT", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_COMERCIO_RUT(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return "Su sesion ha expirado";
		}
		String rut = request.getParameter("RUT");
		String sql = "";
		sql +=	"CALL GET_COMERCIO_RUT('"+rut+"')";
		System.out.println(sql);
		r = RENDIMIENTO.GET_RECOTEC(sql);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_DETALLE_COMERCIO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_DETALLE_COMERCIO(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return "Su sesion ha expirado";
		}
		String region = request.getParameter("REGION");
		String provincia = request.getParameter("PROVINCIA");
		String comuna = request.getParameter("COMUNA");
		String desde = request.getParameter("DESDE");
		String hasta = request.getParameter("HASTA");
		String pp = request.getParameter("PP");
		String sub = request.getParameter("SUB");
		String sql = "";
		sql +=	"CALL GET_DETALLE_COMERCIO('"+region+"', '"+provincia+"', '"+comuna+"', '"+desde+"', '"+hasta+"', '"+pp+"','"+sub+"')";
		System.out.println(sql);
		r = RENDIMIENTO.GET_RECOTEC(sql);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_RESUMEN_PUNTOS_DE_VENTA", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_RESUMEN_PUNTOS_DE_VENTA(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return "Su sesion ha expirado";
		}
		String sql = "";
		String desde = request.getParameter("DESDE");
		String hasta = request.getParameter("HASTA");
		sql +=	"CALL GET_RESUMEN_PUNTOS_DE_VENTA('"+desde+"','"+hasta+"')";
		System.out.println(sql);
		r = RENDIMIENTO.GET_RECOTEC(sql);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_RESUMEN_PUNTOS_DE_VENTA2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_RESUMEN_PUNTOS_DE_VENTA2(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return "Su sesion ha expirado";
		}
		String sql = "";
		String desde = request.getParameter("DESDE");
		String hasta = request.getParameter("HASTA");
		sql +=	"CALL GET_RESUMEN_PUNTOS_DE_VENTA2('"+desde+"','"+hasta+"')";
		System.out.println(sql);
		r = RENDIMIENTO.GET_RECOTEC(sql);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_DETALLE_VISITA", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_DETALLE_VISITA(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return "Su sesion ha expirado";
		}
		String sql = "";
		String comercio = request.getParameter("COMERCIO");
		sql +=	"SELECT DISTINCT ";
		sql +=		"DATE_FORMAT(FECHA, '%Y-%m-%d') AS fecha, ";
		sql +=		"ID_COMERCIO, ";
		sql +=		"PUBLICIDAD, ";
		sql +=		"IF(vc.FOTO != '', 1, 0) AS FOTO, ";
		sql +=		"fv.idVisita "; 
		sql +=	"FROM ";
		sql +=		"VISITA_COMERCIO vc ";
		sql +=		"LEFT JOIN fotoVisitaComercio fv ON(vc.ID = fv.idVisita) ";
		sql +=	"WHERE ";
		sql +=		"ID_COMERCIO = "+comercio+";";	
		System.out.println(sql);
		r = RENDIMIENTO.GET_RECOTEC(sql);
		return r;
	}
	@RequestMapping(value = "/AGRO/STOCK_CANTIDAD", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String STOCK_CANTIDAD(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return "Su sesion ha expirado";
		}
		String sql = "";
		String region = request.getParameter("_REGION");
		String provincia = request.getParameter("_PROVINCIA");
		String comuna = request.getParameter("_COMUNA");
		String desde = request.getParameter("_DESDE");
		String hasta = request.getParameter("_HASTA");
		sql +=	"CALL STOCK_CANTIDAD('"+region+"', '"+provincia+"', '"+comuna+"','"+desde+"','"+hasta+"')";
		System.out.println(sql);
		r = RENDIMIENTO.GET_RECOTEC(sql);
		return r;
	}
	@RequestMapping(value = "/AGRO/RESUMEN_COMERCIO_CATEGORIZACION", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String RESUMEN_COMERCIO_CATEGORIZACION(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return "Su sesion ha expirado";
		}
		String sql = "";
		String region = request.getParameter("_REGION");
		String provincia = request.getParameter("_PROVINCIA");
		String comuna = request.getParameter("_COMUNA");
		String desde = request.getParameter("_DESDE");
		String hasta = request.getParameter("_HASTA");
		sql +=	"CALL RESUMEN_COMERCIO_CATEGORIZACION('"+region+"', '"+provincia+"', '"+comuna+"','"+desde+"','"+hasta+"')";
		System.out.println(sql);
		r = RENDIMIENTO.GET_RECOTEC(sql);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_FOTO_COMERCIO", method = {RequestMethod.GET }, produces = MediaType.IMAGE_PNG_VALUE + "; charset=utf-8")
	public @ResponseBody String GET_FOTO_COMERCIO(HttpSession httpSession, HttpServletRequest request,HttpServletResponse response) throws Exception {

		Documentos documentos = new Documentos();
		session ses = new session(httpSession);

		if (ses.isValid()) {
			return null;
		}
		String fecha = request.getParameter("FECHA");
		String codigo = request.getParameter("CODIGO");
		System.out.println(fecha);System.out.println(codigo);
		String foto = RENDIMIENTO.GET_FOTO_COMERCIO(codigo, fecha);

//		if(documentos.getDocumento() == null){
//			return "";
//		}
//		System.out.println(documentos);
//		Blob documentoBlob = documentos.getDocumento();
//		InputStream in = documentoBlob.getBinaryStream();
////		if(documentos.getNombreDocumento().contains(".pdf")){
////			in = PdfToImage.convertPDFInputStreamToPNG(in);
////		}
//		byte[] bytes = IOUtils.toByteArray(in);
//		String encoded = DatatypeConverter.printBase64Binary(bytes);
//		System.out.println(foto);
//		BASE64Encoder encoder = new BASE64Encoder();
//		String imagenCode = encoder.encode(bytes);

		return foto;

	}
	@RequestMapping(value = "/AGRO/Resumen_periodo_PDV", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String Resumen_periodo_PDV(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return "Su sesion ha expirado";
		}
		String sociedad = request.getParameter("SOCIEDAD");
		String ano = request.getParameter("ANO");
		String getMeses = "SELECT DISTINCT DATE_FORMAT(fecha, '%Y%m') AS mes FROM sw_fechas WHERE fecha BETWEEN NOW() - INTERVAL 6 MONTH AND NOW();";
		String getMeses2 = "SELECT DISTINCT DATE_FORMAT(fecha, '%Y%m') AS mes FROM sw_fechas WHERE fecha BETWEEN NOW() - INTERVAL 9 MONTH AND NOW() - INTERVAL 3 MONTH;";
		String getMeses3 = "SELECT DISTINCT DATE_FORMAT(fecha, '%Y%m') AS mes FROM sw_fechas WHERE fecha BETWEEN NOW() - INTERVAL 10 MONTH AND NOW() - INTERVAL 4 MONTH;";
		String getMeses4 = "SELECT DISTINCT DATE_FORMAT(fecha, '%Y%m') AS mes FROM sw_fechas WHERE fecha BETWEEN NOW() - INTERVAL 7 MONTH AND NOW() - INTERVAL 1 MONTH;";
		String res = RENDIMIENTO.GET_RECOTEC(getMeses);
		String res2 = RENDIMIENTO.GET_RECOTEC(getMeses2);
		String res3 = RENDIMIENTO.GET_RECOTEC(getMeses3);
		String res4 = RENDIMIENTO.GET_RECOTEC(getMeses4);
		JSONObject jo = new JSONObject(res);
		JSONArray ja = jo.getJSONArray("data");
		JSONObject jo2 = new JSONObject(res2);
		JSONArray ja2 = jo2.getJSONArray("data");
		JSONObject jo3 = new JSONObject(res3);
		JSONArray ja3 = jo3.getJSONArray("data");
		JSONObject jo4 = new JSONObject(res4);
		JSONArray ja4 = jo4.getJSONArray("data");
		String auxSql = "";
		String auxSql2 = "";
		String auxSql3 = "";
		String auxSql4 = "";
		String auxSql5 = "";
		for(int i = 0; i < ja.length(); i++){
			JSONObject e = ja.getJSONObject(i);
			JSONObject e2 = ja2.getJSONObject(i);
			JSONObject e3 = ja3.getJSONObject(i);
			JSONObject e4 = ja4.getJSONObject(i);
			auxSql += 	", SUM(IF(t.periodo <= "+e.getInt("MES")+", t.valor, 0 )) as '"+e.getInt("MES")+"' ";
			auxSql2 += 	",(SELECT COUNT(DISTINCT RECEPTOR) FROM MOVIMIENTO WHERE TIPO_RECEPTOR = 3 AND Date_format(FECHA,'%Y%m') BETWEEN "+e2.getInt("MES")+" AND "+e.getInt("MES")+") '"+e.getInt("MES")+"' ";
			auxSql3 += 	",(SELECT COUNT(DISTINCT RECEPTOR) FROM MOVIMIENTO WHERE TIPO_RECEPTOR = 3 AND Date_format(FECHA,'%Y%m') BETWEEN "+e3.getInt("MES")+" AND "+e4.getInt("MES")+") '"+e.getInt("MES")+"' ";
			auxSql4 += 	",(SELECT COUNT(DISTINCT RECEPTOR) FROM MOVIMIENTO WHERE TIPO_RECEPTOR = 3 AND Date_format(FECHA,'%Y%m') = "+e.getInt("MES")+" ) '"+e.getInt("MES")+"' ";
			auxSql5 += 	",SUM(CASE WHEN Date_format(FECHA_CREACION,'%Y%m') <= "+e.getInt("MES")+" THEN 1 ELSE 0 END) '"+e.getInt("MES")+"' ";
		}
		String sql = "";
		sql += 	"SELECT ";
		sql += 		" 'Potencial de PDV Trazados' DESCRIPCION ";
		sql += 		auxSql;
		sql += 	"FROM (select count(*) as valor, date_format(FECHA_CREACION, '%Y%m') as periodo from COMERCIO group by 2 )t ";
		sql += 	"UNION ";
		sql += 	"SELECT ";
		sql += 		" 'Q PDV Activos' DESCRIPCION ";
		sql += 		auxSql2;
		sql += 	"UNION ";
		sql += 	"SELECT ";
		sql += 		"'Q PDV Antiguos' DESCRIPCION ";
		sql += 		auxSql3;
		sql += 	"UNION ";
		sql += 	"SELECT ";
		sql += 		"'Q PDV Activo en el mes' DESCRIPCION ";
		sql += 		auxSql4;
		sql += 	"UNION ";
		sql += 	"SELECT ";
		sql += 		"'Q PDV Activos en Venta' DESCRIPCION ";
		sql += 		auxSql5;
		sql += 	" FROM COMERCIO; ";
		System.out.println(sql);
		r = RENDIMIENTO.GET_RECOTEC(sql);
		return r;
	}
	@RequestMapping(value = "/AGRO/VER_FOTO_FILE", method = RequestMethod.GET)
	@CrossOrigin(origins = {"*"})
	public @ResponseBody String VER_FOTO_FILE(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject resp = new JSONObject();
		JSONArray data = new JSONArray();
		try {
			String codigo = request.getParameter("CODIGO");
			String sql = "SELECT *FROM fotoVisitaComercio WHERE idVisita = "+codigo;
			String r = RENDIMIENTO.GET_RECOTEC(sql);
			JSONArray res = new JSONArray(new JSONObject(r).get("data").toString());
			for(int i = 0; i < res.length(); i++){
				JSONObject e = res.getJSONObject(i);
				JSONObject j = new JSONObject();
				String fileName = e.getString("FOTO");
				String urlDocGenerado = utils.Licencia() + fileName;
				byte[] fileContent = FileUtils.readFileToByteArray(new File(urlDocGenerado));
				String encodedString = Base64.getEncoder().encodeToString(fileContent);
				j.put("image", encodedString);
				data.put(j);
			}
			resp.put("error", 0);
			resp.put("message", "OK");
			resp.put("data", data);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			Collections.singleton("");
			resp.put("error", 500);
			resp.put("message", e.getMessage());
			resp.put("data", data);
		}
		return resp.toString();
	}
	@RequestMapping(value = "/AGRO/INSERTAR_FOTOS", method = RequestMethod.GET)
	@CrossOrigin(origins = {"*"})
	public @ResponseBody String INSERTAR_FOTOS(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject resp = new JSONObject();
		JSONObject in = new JSONObject();
		JSONArray data = new JSONArray();
		try {
		    final String UPLOAD_DIRECTORY = utils.Licencia();
		    in.put("TABLE", "fotoVisitaComercio");
			JSONArray datos = new JSONArray();
			String sql = "SELECT CAST(FOTO AS CHAR(1000000) CHARACTER SET utf8) FOTO, ID FROM VISITA_COMERCIO WHERE FOTO != '' and ID NOT IN(SELECT idVisita FROM fotoVisitaComercio) ORDER BY 2 DESC LIMIT 40;";
			String r = RENDIMIENTO.GET_RECOTEC(sql);
			JSONArray res = new JSONArray(new JSONObject(r).get("data").toString());
			for(int i = 0; i < res.length(); i++){
				JSONObject e = res.getJSONObject(i);
				JSONObject ex = new JSONObject();
				JSONObject j = new JSONObject();
				String idrequest = UUID.randomUUID().toString();
				String timeStampId = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
				String fileName=timeStampId+"_"+idrequest;
				String file = e.getString("FOTO");
				int id = e.getInt("ID");
				byte[] decodedImg = Base64.getDecoder().decode(file.getBytes(StandardCharsets.UTF_8));
				Path destinationFile = Paths.get(UPLOAD_DIRECTORY, fileName+".png");
				Files.write(destinationFile, decodedImg);
				ex.put("idVisita", id);
				ex.put("foto", fileName+".png");
				datos.put(ex);
				j.put("image", destinationFile);
				data.put(j);
			}
			in.put("VALUES", datos);
			MantenedorBD.Insert(in.toString());
			resp.put("error", 0);
			resp.put("message", "OK");
			resp.put("data", data);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			Collections.singleton("");
			resp.put("error", 500);
			resp.put("message", e.getMessage());
			resp.put("data", data);
		}
		return resp.toString();
	}
}

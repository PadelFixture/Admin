package lib.data.json;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.streaming.SXSSFRow;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSA.LIQUIDACION;
import lib.classSA.RENDIMIENTO_DIARIO;
import lib.classSA.cierre_mensual;
import lib.db.ConnectionDB;
import lib.excelSA.excelAgro;
import lib.excelSA.excelExportOrden;
import lib.excelSA.excelOrdenJson;
import lib.security.session;
import wordCreator.utils;

@Controller
public class excelService {

	@RequestMapping(value = "/AGRO/EXCEL_RECOTEC", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String EXCEL_RECOTEC(@RequestBody  String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		excelExportOrden r = new excelExportOrden();
		if (ses.isValid()) {
			return "0";
		}
		String a = r.EXCEL_RECOTEC(row);
		return a;
	}
	@RequestMapping(value = "/AGRO/JSON_EXCEL", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String JSON_EXCEL(@RequestBody  String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		excelExportOrden r = new excelExportOrden();
		if (ses.isValid()) {
			return "0";
		}
		String a = r.JSON_EXCEL(row);
		return a;
	}
}

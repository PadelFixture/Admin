package lib.data.json.sw;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.report.sw.InformeTrabajadoresExcel;
import lib.security.session;
import lib.struc.filterSql;
import wordCreator.utils;

@Controller
public class ExcelReportJSON {

	
	//Imprimir Sociedad
	@RequestMapping(value = "/work/ExcelReport/excelReportListaTrabajadores/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Set<String> excelReportWorkerList(HttpServletRequest request , HttpSession httpSession) throws Exception{
	
		session ses = new session(httpSession);
		if(ses.isValid()){
			return null;
		}
		
		//Obtener todos los parametros del URL
		Map<String, String[]> parameters = request.getParameterMap();

		ArrayList<filterSql> filter = new ArrayList<filterSql>();

		//Obtener todos los parametros enviados por el URL
		for (String key : parameters.keySet()) {
			String[] vals = parameters.get(key);
			//Obtener cada uno de los parametros y valores
			for (String val : vals) {
				filterSql fil = new filterSql();
				fil.setCampo(key);
				fil.setValue(val);
				//Añadir campo y valor 
				filter.add(fil);
			}
		}
		
		InformeTrabajadoresExcel informacion = new InformeTrabajadoresExcel();
		
		
		
		return Collections.singleton(informacion.prepare(filter));
	}
	
	
	@RequestMapping(value = "/work/showExcelReport", method = RequestMethod.GET)
	public @ResponseBody String showExcelReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		try {
			String fileName = request.getParameter("FILE");
		
			String urlDocGenerado = utils.getServerFolder("ReporteExcel") + File.separator;
			String nombreDoc = fileName;
			File file = new File(urlDocGenerado + nombreDoc);
			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);
			response.addHeader("Content-disposition", "attachment; filename= " + nombreDoc + "");
			response.setContentType("application/xls");
			response.setContentLength(bytes.length);
			ServletOutputStream out = response.getOutputStream();
			out.write(bytes);
			out.flush();
			out.close();
			fileInputStreamReader.close();

			if (file.exists()) {
				file.delete();
			}

			return "Descargando Archivo...";

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

	}
	
	
	
	
}

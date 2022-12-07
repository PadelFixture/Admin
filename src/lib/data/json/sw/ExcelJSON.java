package lib.data.json.sw;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import wordCreator.utils;

@Controller
public class ExcelJSON {

	
	// Mostrar PDF de la Liquidacion
		@RequestMapping(value = "/work/excel/showExcel/{nombre}/", method = RequestMethod.GET)
		public @ResponseBody String showExcel(HttpServletRequest request, @PathVariable String nombre, HttpServletResponse response,
				HttpSession session) throws Exception {

			
			String fileName = nombre;
			fileName = fileName.replaceAll("\"", "");
			System.out.println("ruta: {}"+fileName);
			
			String urlDocGenerado = utils.reportesExcel() + fileName;
			
			try {
					
				if(!urlDocGenerado.endsWith(".xlsx")){
					urlDocGenerado = urlDocGenerado.concat("xlsx");
				}
			
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
				return urlDocGenerado;

			} catch (Exception e) {
				e.printStackTrace();
				return urlDocGenerado;
			}
			
		}
	
	
}

package lib.data.json.sw;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import SWDB.sw_ImpuestoUnicoExcelDB;
import lib.classSW.ImpuestoUnicoExcel;
import lib.security.session;

@Controller
public class sw_ImpuestoUnicoDetalle {
	//---------------GENERAR EXCEL IMPUESTO UNICO--------------------------------------------------------
	@RequestMapping(value = "/work/generateExcelImpuestoUnico/{idSociedad},{periodo},{opcion},{mes},{anio},{rolPrivado}", method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String generarExcelAvisoInspeccion(
			@PathVariable int idSociedad,
			@PathVariable int periodo,
			@PathVariable int opcion,
			@PathVariable String mes,
			@PathVariable int anio,
			@PathVariable int rolPrivado,
			HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		String r = "";
		if (ses.isValid()) {
			return "";

		}
		
		ArrayList<ImpuestoUnicoExcel> detalleEmpresa = new ArrayList<ImpuestoUnicoExcel>();
		ArrayList<ImpuestoUnicoExcel> Empresa = new ArrayList<ImpuestoUnicoExcel>();
		if(idSociedad != 0 && opcion == 2){
			detalleEmpresa = sw_ImpuestoUnicoExcelDB.buscartrabajadores(idSociedad, periodo,rolPrivado);
			
			if(detalleEmpresa.size() >= 1){
			r = sw_ImpuestoUnicoExcelDB.generarExcelImpuestoUnico(detalleEmpresa);
			}else
			{
				r = "NO DATA";
			}
		}else if(opcion == 1){
			Empresa = sw_ImpuestoUnicoExcelDB.Empresas(idSociedad, periodo,mes,anio);
			
			if(Empresa.size() >= 1)
			{
				r = sw_ImpuestoUnicoExcelDB.generarExcelImpuestoUnicoEmpresa(Empresa);
			}else
				{
					r = "NO DATA";
				}
			
		}
		
		return r;

	}
}

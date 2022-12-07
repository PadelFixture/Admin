package lib.data.json.sw;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.CentraRow;
import lib.classSW.CentralizacionDetalleTmp;
import lib.classSW.Centralizar;
import lib.db.sw.CentralizacionDetalleTmpDB;
import lib.security.session;
import lib.struc.filterSql;

@Controller
public class CentralizacionDetalleTmpJSON {

	//InsertOrUpdate
		@RequestMapping(value = "/work/CentralizacionDetalleTmp/insertOrUpdateCentralizacionDetalleTmp/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody ArrayList<CentralizacionDetalleTmp> insertOrUpdateCentralizacionDetalleTmp(@RequestBody ArrayList<CentralizacionDetalleTmp> CentralizacionDetalleTmp, HttpSession httpSession) throws Exception{

			session ses = new session(httpSession);
			if (ses.isValid()) {
				return null;
			}

			try {
				CentralizacionDetalleTmp = CentralizacionDetalleTmpDB.insertOrUpdateCentralizacionDetalleTmp(CentralizacionDetalleTmp);
			} catch (Exception e) {
				return null;
			}
		
			return CentralizacionDetalleTmp; 
		} // fin insertar
	
	
		// Get
		@RequestMapping(value = "/work/CentralizacionDetalleTmp/getCentralizacionDetalleTmp/", method = { RequestMethod.GET })
		public @ResponseBody ArrayList<CentralizacionDetalleTmp> getCentralizacionDetalleTmp(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) throws Exception {
			
			// Obtener todos los parametros del URL
			Map<String, String[]> parameters = request.getParameterMap();

			ArrayList<filterSql> filter = new ArrayList<filterSql>();

			// Obtener todos los parametros enviados por el URL
			for (String key : parameters.keySet()) {
				String[] vals = parameters.get(key);
				// Obtener cada uno de los parametros y valores
				for (String val : vals) {
					filterSql fil = new filterSql();
					fil.setCampo(key);
					fil.setValue(val);
					// Añadir campo y valor
					filter.add(fil);
				}
			}

			
			return CentralizacionDetalleTmpDB.getCentralizacionDetalleTmp(filter);

		}
		
		@RequestMapping(value = "/work/CentralizacionDetalleTmp/Centralizar/", method = { RequestMethod.GET })
		public @ResponseBody String Centralizar(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) throws Exception {

			ArrayList<CentraRow> rows = new ArrayList<CentraRow>();
			ArrayList<CentralizacionDetalleTmp> centralizacionDetalle = new ArrayList<CentralizacionDetalleTmp>();

			// Obtener todos los parametros del URL
			Map<String, String[]> parameters = request.getParameterMap();
			
			String soc = request.getParameter("id_sociedad");
			Integer periodo = Integer.parseInt(request.getParameter("periodo"));
			String date = request.getParameter("fecha_proceso");
			String usuario = request.getParameter("usuario");
		
			ArrayList<filterSql> filter = new ArrayList<filterSql>();

			// Obtener todos los parametros enviados por el URL
			for (String key : parameters.keySet()) {
					String[] vals = parameters.get(key);
			// Obtener cada uno de los parametros y valores
					for (String val : vals) {
						filterSql fil = new filterSql();
						fil.setCampo(key);
						fil.setValue(val);
						// Añadir campo y valor
						filter.add(fil);
					}
			}
			
			centralizacionDetalle = CentralizacionDetalleTmpDB.getCentralizacionDetalleTmp(filter);
			
			rows = CentralizacionDetalleTmpDB.centralizacionDetalleToCentraRow(centralizacionDetalle);

			Centralizar c = new Centralizar();
			String ObjetoJSON = c.CentralizarDatosExcel(rows, soc, periodo, date, usuario);
			System.out.println(ObjetoJSON);

			return ObjetoJSON;
		}
		
		
	
}

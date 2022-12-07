package lib.data.json.sw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lib.classSW.DatosLiquidacion;
import lib.classSW.DocLiquidacion;
import lib.classSW.GetDatosContratoTrabajador;
import lib.classSW.Liquidacion;
import lib.classSW.LiquidacionDetalle;
import lib.db.sw.InformacionDocumentoDB;
import lib.db.sw.LiquidacionDetalleDB;
import lib.db.sw.LiquidacionesDB;
import lib.security.session;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;
import wordCreator.DocBuilder;
import wordCreator.utils;

@Controller
public class LiquidacionDetalleJSON {

	private final static Logger LOG = LoggerFactory.getLogger(LiquidacionDetalleJSON.class);

	// Para generar La liquidacion se necesita pasar el codigoTrabajador de la
	// tabla trabajador
	@RequestMapping(value = "/work/LiquidacionDetalle/generateLiquidacionDetalle/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Set<String>> generateLiquidacionDetalle(HttpServletRequest request)
			throws Exception {

		String documentName = "";
		int codTrabajador = 0;

		try {

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
			
			// Obtener ruta para guardar liquidacion
			String ruta = utils.getServerFolder("Liquidacion");
			LOG.info("Ruta donde se guarda el Archivo de Liquidacion: " + ruta);

			LOG.info("Obtener Datos del Trabajador");
			GetDatosContratoTrabajador datosContratoTrabajador = new GetDatosContratoTrabajador();
			datosContratoTrabajador = InformacionDocumentoDB
					.obtenerDatosTrabajadorByCod2(request.getParameter("codTrabajador"),request.getParameter("idContrato"),request.getParameter("periodo"));

			if (datosContratoTrabajador.getCodigoTrabajador() != null) {
				codTrabajador = Integer.valueOf(datosContratoTrabajador.getCodigoTrabajador());
				//Setear Fecha de Pago
				datosContratoTrabajador.setFechaPagoFiniquito(GeneralUtility.convertStringDDMMYYToDateInWord(request.getParameter("fechaPago")));
			}

			DatosLiquidacion datosLiquidacion = new DatosLiquidacion();

			// Transformar la Tabla Liquidacion Detalle por Datos de La
			// Liquidacion para Impresion
			datosLiquidacion = LiquidacionDetalleDB.getLiquidacionDetalleToDatosLiquidacion(filter);

			// Agregamos Periodo en letra
			String periodoToDate = GeneralUtility.convertStringMMDDToDDMMYYY(request.getParameter("periodo"));
			datosContratoTrabajador.setPeriodoTrabajador(
					GeneralUtility.convertStringDDMMYYToDateInWord(periodoToDate).replace("1 de ", ""));

			// Transformar los datos a JSON
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String datosContratoTrabajadorJSON = ow.writeValueAsString(datosContratoTrabajador);
System.out.println("////////////////////////////////////aqui");
			LOG.info("Generando Reporte en Word de Liquidacion...");
			DocBuilder docBuilder = new DocBuilder();
			Blob liquidacion = LiquidacionesDB.getLiquidacion();

			File file = File.createTempFile("Liquidacion_" + datosContratoTrabajador.getCodigoTrabajador() + "_"
					+ request.getParameter("periodo") + "_" + request.getParameter("idContrato")  +  "@", ".docx", null);

			InputStream in = liquidacion.getBinaryStream();
			OutputStream out = new FileOutputStream(file);

			// Copiar datos en nuevo File
			IOUtils.copy(in, out);

			// Cerrar Input/Output Stream
			in.close();
			out.close();

			// Crear nuevo objeto File Input Stream del Archivo
			FileInputStream fi = new FileInputStream(file.getAbsolutePath());

			// Modificacion de Documento para Introducir Datos de Trabajador
			String pathDocument = docBuilder.createLiquidacionDoc(ruta.replaceAll("\"", ""), file.getName(),
					 datosContratoTrabajador, datosContratoTrabajadorJSON, fi, datosLiquidacion);

			// Borrar Archivo Temporal
			file.delete();

			if (pathDocument != null) {

				File archivoFinal = new File(pathDocument);

				if (archivoFinal.exists()) {
					documentName = archivoFinal.getName();
				} else {
					return new ResponseEntity<>(Collections.singleton(documentName), HttpStatus.BAD_REQUEST);
				}
			}

		} catch (Exception e) {
			return new ResponseEntity<>(Collections.singleton("Error al generar el documento para el Trabajador: " + codTrabajador + " <br></br> "+
					e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}

		// return Collections.singleton(file.getName());
		return new ResponseEntity<>(Collections.singleton(documentName), HttpStatus.OK);

	}
	
	// insert Liquidacion detalle
	@RequestMapping(value = "/work/insertLiquidacionDetalle/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertTrabajadorLicenciaConducir(@RequestBody LiquidacionDetalle LiquidacionDetalle,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}

		return LiquidacionDetalleDB.insertLiquidacion(LiquidacionDetalle);
	} // fin insertar

	// get Liquidacion detalle
	@RequestMapping(value = "/work/getLiquidacionDetalle/", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<LiquidacionDetalle> getLiquidacionDetalle(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<LiquidacionDetalle> LiquidacionDetalle = new ArrayList<LiquidacionDetalle>();

		if (ses.isValid()) {
			return LiquidacionDetalle;
		}

		LiquidacionDetalle = LiquidacionDetalleDB.getLiquidacionDetalle();
		return LiquidacionDetalle;

	}// fin get

	// get Liquidacion detalle con id
	@RequestMapping(value = "/work/getLiquidacionDetalleByIdContrato/{id}", method = { RequestMethod.GET })
	public @ResponseBody LiquidacionDetalle getLiquidacionDetalleByIdContrato(@PathVariable int id,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		LiquidacionDetalle LiquidacionDetalleByidContrato = new LiquidacionDetalle();

		if (ses.isValid()) {
			return LiquidacionDetalleByidContrato;
		}

		LiquidacionDetalleByidContrato = LiquidacionDetalleDB.getLiquidacionDetalleByIdContrato(id);
		return LiquidacionDetalleByidContrato;

	}// fin Get con id

	// update Liquidacion detalle
	@RequestMapping(value = "/work/updateLiquidacionDetalle/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateLiquidacion(@RequestBody LiquidacionDetalle LiquidacionDetalle,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return LiquidacionDetalleDB.updateLiquidacionDetalle(LiquidacionDetalle);
	}// fin

	// Eliminar
	@RequestMapping(value = "/work/deleteLiquidacionDetalleById/{id}", method = { RequestMethod.PUT })
	public @ResponseBody boolean deleteLiquidacion(@PathVariable int id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}

		return LiquidacionDetalleDB.deleteLiquidacionDetalleById(id);
	}// fin metodo eliminar

	// Obtener Todas las sociedades
	@RequestMapping(value = "/work/getLiquidacionDetalleWithFilter/", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ArrayList<LiquidacionDetalle> getLiquidacionDetalleWithFilter(HttpSession httpSession,
			HttpServletRequest request) throws Exception {

		session ses = new session(httpSession);

		ArrayList<LiquidacionDetalle> liquidacionDetalle = new ArrayList<LiquidacionDetalle>();

		if (ses.isValid()) {
			return null;
		}

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

		liquidacionDetalle = LiquidacionDetalleDB.getLiquidacionDetalleWithFilter(filter);
		return liquidacionDetalle;

	}

	// Crear (Tabla - Contrato)
	@RequestMapping(value = "/work/liquidacionDetalle/insertLiquidacionDetalle/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Set<String>> insertLiquidacionDetalle(@RequestBody Liquidacion liquidacion,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return new ResponseEntity<>(Collections.singleton("La sesion esta cerrada"), HttpStatus.FORBIDDEN);
		}

		try {
			LOG.info("Generando la Liquidacion");
			LiquidacionDetalleDB.insertLiquidacionDetalle(liquidacion);
			return new ResponseEntity<>(
					Collections.singleton("Liquidaciones generada del trabajador: " + liquidacion.getCod_trabajador()),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(Collections.singleton(e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}

	// Prueba Liquidacion
	@RequestMapping(value = "/work/liquidacionDetalle/pruebaLiquidacion/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DocLiquidacion pruebaLiquidacion(HttpServletRequest request) throws Exception {

		// Obtener todos los parametros del URL
		//Map<String, String[]> parameters = request.getParameterMap();

		ArrayList<filterSql> filter = new ArrayList<filterSql>();

		filterSql dato1 = new filterSql();
		dato1.setCampo("codTrabajador");
		dato1.setValue("1");
		filterSql dato2 = new filterSql();
		dato2.setCampo("idContrato");
		dato2.setValue("295");
		filterSql dato3 = new filterSql();
		dato3.setCampo("periodo");
		dato3.setValue("201808");

		// Setear Parametros para Pruebas
		filter.add(dato1);
		filter.add(dato2);
		filter.add(dato3);

		// Transformar la Tabla Liquidacion Detalle por Datos de La Liquidacion
		// para Impresion
		DatosLiquidacion datosLiquidacion = LiquidacionDetalleDB.getLiquidacionDetalleToDatosLiquidacion(filter);
		
		//Obtener datos Trabajador
		GetDatosContratoTrabajador datosContratoTrabajador = InformacionDocumentoDB.obtenerDatosTrabajadorByCod2("1","295","201908");
		
		DocLiquidacion docLiquidacion = LiquidacionDetalleDB.pruebaDocLiquidacion(datosLiquidacion, datosContratoTrabajador);

		return docLiquidacion;

	}

	
	
}

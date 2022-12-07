package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import lib.classSW.Contratos;
import lib.classSW.Finiquito;
import lib.classSW.contrato;
import lib.classSW.trabajadores;
//import lib.db.ConnectionAutoClosableDB;
import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;
import lib.utils.TimeUtility;

public class ProcedimientoDB {

	
	public static LinkedList<Contratos> getAllContract(String codigoDuplicado) throws Exception{
		
		LinkedList<Contratos> contratosList = new LinkedList<>();
		
		String sql = " SELECT * FROM contratos WHERE codigo_trabajador = ? order by id asc ";
		
//		try( ConnectionAutoClosableDB db = new ConnectionAutoClosableDB();
//			 PreparedStatement ps = db.conn.prepareStatement(sql);
//			){
//			
//			ps.setString(1, codigoDuplicado);
//			ResultSet rs = ps.executeQuery();
//			
//			while (rs.next()){
//				//Obtener Contrato
//				Contratos ct = contratoDB.setObjectContratos(rs);
//				contratosList.add(ct);
//			}
//			
//		}
//		
		return contratosList;
	}
	
	
	public static Contratos verifyMoreThanOneContractActive(LinkedList<Contratos> ctList) throws Exception{
		
		int nActivos = 0;
		
		for (Contratos contratos : ctList) {
			
			if(contratos.getEstadoContrato() == 1){
				nActivos++;
			}
			
		}
	
		if(nActivos > 2){
			return ctList.pollLast();
		}
		
		return null;
	}
	
	
//	public static Contratos deleteDuplicatedActive(Contratos lastContrato) throws Exception{
//	
//		String sql = " DELETE * FROM contratos WHERE id = ? ";
//		
//		try( ConnectionAutoClosableDB db = new ConnectionAutoClosableDB();
//			 PreparedStatement ps = db.conn.prepareStatement(sql);
//			){
//				ps.setInt(1, lastContrato.getId());
//				ps.executeUpdate();
//		}
//		
//		return null;
//	
//	}
//	
//	
	
	
	public static String modifyDataTrabajadorPerProcess(LinkedHashMap<Integer,LinkedList<String>> datos, String process_error) throws Exception{
		
		//PreparedStatement ps = null;
		String sql = "";
		String sqlInsert = "";
		//ConnectionDB db = new ConnectionDB();
		//ResultSet rs = null;
		int key = 0;
		int i = 1;
		
		for( Entry<Integer, LinkedList<String>> entry : datos.entrySet()){
			
			 LinkedList<String> value = entry.getValue();
			 
			 //Parametros Excel
			 //Datos de Excel
			 System.out.println("RUT: " + value.get(0) + " Fecha Inicio Activida:"+ value.get(1) + " Fecha Termino Actividad: " +value.get(2));
			 String periodoExcel = TimeUtility.convertStringToYYYYMM(TimeUtility.convertStringToDDMMYYYY(value.get(1)));
			 String periodoExcelFin = TimeUtility.convertStringToYYYYMM(TimeUtility.convertStringToDDMMYYYY(value.get(2)));
			 String fechaInicioActividadExcel = GeneralUtility.convertStringToYYYYMMDD(value.get(1));
			 String fechaTerminoActividadExcel = GeneralUtility.convertStringToYYYYMMDD(value.get(2));
			// String sueldoBaseExcel = "";
			// String horasSemanalesExcel = "";
			
			 //Parametros Contratos
			 contrato datosContratos = new contrato();
			 contrato datosContratos_per = new contrato();
			 contrato datosUltimoContrato = new contrato();
			 
			 //Obtener el Trabajador
			 trabajadores datosTrabajador  = trabajadoresDB.getTrabajadorByRut(value.get(0)/*rut*/);
			 
			 ArrayList<filterSql> where = new ArrayList<filterSql>();
			 filterSql filter1 = new filterSql("codigo_trabajador",datosTrabajador.getCodigo());
			 filterSql filter2 = new filterSql("fechaInicio_actividad_date",value.get(1).replaceAll("/", "-")/*fechaInicio_actividad*/);
			 filterSql filter3 = new filterSql("_historial","true"/*contratos_per*/);
			 
			 where.add(filter1);
			
			 //Obtener los datos del ultimo contrato del trabajador activo o no
			 datosUltimoContrato = contratoDB.getUltimoContrato(where);
			 
			 where.add(filter2);
			
			 //Obtener todos los Contratos del Trabajador ordenador por fecha de Inicio
			 ArrayList<contrato> listDatosContratos = contratoDB.getAllContratoTrabajadorWithFilter(where);
			 
			 ConnectionDB db = new ConnectionDB();
			 
			 try {
				 
			 PreparedStatement ps = null;
			 sql = "";
			 ResultSet rs = null;
			 db.conn.setAutoCommit(false);
				 
			 if(datosUltimoContrato.getFecha_inicio_actividad().equals(fechaInicioActividadExcel.trim())){
				 
				 modificarContratosPer(datosTrabajador, fechaInicioActividadExcel, fechaTerminoActividadExcel, db);
				 inactivarContratosAnteriores(datosTrabajador, fechaInicioActividadExcel, db);
				 modificarContratos(datosTrabajador, fechaInicioActividadExcel, fechaTerminoActividadExcel, db);
				 continue;
			 }
			 //Si el Periodo del Excel Es enero y No tiene contratos
			 else if("201901".equals(periodoExcel) && listDatosContratos.size() == 0 ){
				 //Insertar un Nuevo contrato para la fecha de Enero del Excel
				sqlInsert = ""; 
				sqlInsert += " INSERT INTO contratos ( id, codigo_trabajador, idSociedad, tipoTrabajador , fechaInicio_actividad, fechaContrato_emitido, FechaTerminoContrato, EstadoContrato, cargo, tipoContrato, horasSemanales, sueldoBase, idHuertoContrato, idCECOContrato, idFaenaContrato, fechaPago, paraFiniquitar ) " 
						  + " VALUES ( null, ?, ?, 1, ?, now(), ?, 1, ?, ?, ?, ?, ?, ?, ?, ?, 1 ) "; 
				 ps = db.conn.prepareStatement(sqlInsert);
				 i = 1;
				 ps.setString(i++, datosTrabajador.getCodigo());
				 ps.setInt(i++, datosUltimoContrato.getId_sociedad());
				 ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(fechaInicioActividadExcel));
				 ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(fechaTerminoActividadExcel));
				 ps.setInt(i++, datosUltimoContrato.getCargo());
				 ps.setInt(i++, datosUltimoContrato.getTipoContrato());
				 ps.setInt(i++, datosUltimoContrato.getHorasSemanales());
				 ps.setDouble(i++, datosUltimoContrato.getSueldoBase());
				 ps.setString(i++, datosUltimoContrato.getIdHuertoContrato());
				 ps.setString(i++, datosUltimoContrato.getIdCECOContrato());
				 ps.setInt(i++, datosUltimoContrato.getIdFaenaContrato());
				 ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD("07-02-2019"));
				 ps.executeUpdate();
				 rs = ps.getGeneratedKeys();
				 if(rs.next()){
						key = rs.getInt(1);
						
						//Modificar Rendimientos Diarios en el periodo de Enero
						modificarRendimientosDiarios(key, datosTrabajador, db);
						//Modificar Anticipos en Enero
						modificarAnticipos(key, datosTrabajador, db);
						//Modificar Haberes y Descuentos en Enero
						modificarHaberesDescuentos(key, datosTrabajador, db);
						//Inactivar los Contratos Anteriores
						inactivarContratosAnteriores(datosTrabajador, fechaInicioActividadExcel, db);
							
				 }else{
					 throw new Exception("No se pudo insertar contrato");
				 }
			 }
			 
			 
			 
			 where.add(filter3);
			//Obtener todos los Contratos de Historial del Trabajador ordenador por fecha de Inicio
			 ArrayList<contrato> listDatosContratos_per = contratoDB.getAllContratoTrabajadorWithFilter(where);

			 System.out.println("Datos Actuales");
			 for (contrato contrato : listDatosContratos) {
				//Solo tiene un Contrato para este periodo
				if(listDatosContratos.size() == 1){
					datosContratos = contrato;
				}
			 }
			 System.out.println(datosContratos);
			 String periodoGlobal = TimeUtility.convertStringToYYYYMM(TimeUtility.convertStringToDDMMYYYY(datosContratos.getFecha_inicio_actividad()));
			 
			 System.out.println("Datos Historicos");
			 for (contrato contrato : listDatosContratos_per) {
				 if(String.valueOf(contrato.getPeriodo()).equals(periodoGlobal)){
					 datosContratos_per = contrato;
				 }
			 }
			 
			 //Si tiene finiquito
			 boolean hasFiniquito = false;
//			 ArrayList<Finiquito> listFiniquitos = FiniquitosBD.getFiniquitosByCodTrabajador(datosTrabajador.getCodigo());
//			 for (Finiquito finiquito : listFiniquitos) {
//				 if(periodoGlobal.equals(finiquito.getPeriodo())){
//					hasFiniquito = true; 
//				 }
//			 }
			 
			 
			 
//			 if(!hasFiniquito){
//				 sql = " UPDATE contratos_per SET fecha_termino_actividad = ?, estado_contrato = ? WHERE periodo = ? and id = ? ";
//				 ps = db.conn.prepareStatement(sql);
//				 i = 1;
//				 ps.setString(i++, null);
//				 ps.setInt(i++, 1);
//				 ps.setString(i++, periodoGlobal);
//				 ps.setInt(i++, datosContratos.getId());
//				 ps.execute();
//			 }

			 //Si tienen la misma fecha de Inicio, Si es el mismo Id, 
			 //Si la fecha de Termino es null en la tabla historia, 
			 //Si los periodos corresponden con la fecha Inicio y esta finiquitado
			 //Si solo tiene un Contrato para ese periodo
//			 if(datosContratos.getFecha_inicio_actividad().equals(datosContratos_per.getFecha_inicio_actividad()) 
//				&& datosContratos_per.getFecha_termino_actividad() == null
//				&& datosContratos.getFiniquitado() == 1
//				&& listDatosContratos.size() == 1){
//					 
//				sql = " UPDATE contratos_per SET fecha_termino_actividad = ?, estado_contrato = ? WHERE periodo = ? and id = ? ";
//				ps = db.conn.prepareStatement(sql);
//				i = 1;
//				ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(datosContratos.getFecha_termino_actividad()));
//				ps.setInt(i++, datosContratos.getEstado_contrato());
//				ps.setString(i++, TimeUtility.convertStringToYYYYMM(datosContratos.getFecha_inicio_actividad()));
//				ps.setInt(i++, datosContratos.getId());
//			 }
//				 
//				 
//				 ps.execute();
				 db.conn.commit();
//				 
//				 ps.close();
//				 db.close();
				 
				 
			} catch (Exception e) {
				db.conn.rollback();
				process_error += " No Se proceso el trabajador : " + datosTrabajador.getRut() + " <br> "; 
			}
			 
			 
			 
			
			
			 
			 
			 
			
			 
			
		}
		
		return process_error;
			
	}

	
public static String modifyDataTrabajadorPerProcess2(LinkedHashMap<String,LinkedList<String>> datos, String process_error) throws Exception{
		
		//PreparedStatement ps = null;
		String sql = "";
		String sqlInsert = "";
		//ConnectionDB db = new ConnectionDB();
		//ResultSet rs = null;
		int key = 0;
		int i = 1;
		
		for( Entry<String, LinkedList<String>> entry : datos.entrySet()){
			
			boolean saltar = false;
			
			//Obtener Primer Dato
			LinkedList<String> value = entry.getValue();
			 
			String fechaInicioActividadExcel = GeneralUtility.convertStringToYYYYMMDD(value.get(1));
			String fechaTerminoActividadExcel = GeneralUtility.convertStringToYYYYMMDD(value.get(2));
			 
			ArrayList<trabajadores> listaTrabajadores = new ArrayList<>();
			
			//Obtener Codigo By Rut
			trabajadores datosTrabajador  = trabajadoresDB.getTrabajadorByRut(GeneralUtility.FormatearRUT(value.get(0)/*rut*/));

			
			
			//Obtener Trabajadores Extranjeros
			if(datosTrabajador.getCodigo() == null){
				
				listaTrabajadores = trabajadoresDB.getTrabajadorByColumnAndValue("rutTemporal", "'"+GeneralUtility.FormatearRUT(value.get(0)/*rut*/)+"'");
				
				if(listaTrabajadores.size() != 0){
					datosTrabajador = listaTrabajadores.get(0);
				}else{
					
					System.out.println("No se Encuentra Trabajador:");
					saltar = true;
					
				}
			}
			
			if(datosTrabajador.getCodigo() != null){
			if(datosTrabajador.getCodigo().equals("1909")){
				System.out.println("1909");
			}
			}
			
			if(saltar == false){
			
			//Where para Contrato
			ArrayList<filterSql> where = new ArrayList<filterSql>();
			ArrayList<filterSql> whereCodTrabajador = new ArrayList<filterSql>();
			filterSql filter1 = new filterSql("codigo_trabajador",datosTrabajador.getCodigo());
			
			if(datosTrabajador.getCodigo() == null){
				System.out.println("Trabajador Nulo");
			}
			
			filterSql filter2 = new filterSql("fechaInicio_actividad_date",value.get(1).replaceAll("/", "-")/*fechaInicio_actividad*/);
			
			where.add(filter1);
			whereCodTrabajador.add(filter1);
			where.add(filter2);
			
			ArrayList<Contratos> contratos = new ArrayList<>();
			Contratos datosContratos = new Contratos();
			
			//Obtener los datos del ultimo contrato del trabajador activo o no
			contratos = contratoDB.getContratoWithFilter(where);
			
			if(contratos.size() != 0){
				datosContratos = contratoDB.getContratoWithFilter(where).get(0);
			}
			
			if(datosContratos.getId() == 0){
				System.out.println("Insertar Nuevo Contrato...");
				//Crear Contrato Para Esa Fecha
				datosContratos = contratoDB.getContratoWithFilter(whereCodTrabajador).get(0);
				insertContrato(datosTrabajador, datosContratos, fechaInicioActividadExcel, fechaTerminoActividadExcel);
				
				//Volver a Obtener Mismo Contrato
				contratos = contratoDB.getContratoWithFilter(where);
				if(contratos.size() != 0){
					datosContratos = contratoDB.getContratoWithFilter(where).get(0);
				}
				
			}else{
				
				String sinNULLFechaTermino1 = fechaTerminoActividadExcel == null ? "" : fechaTerminoActividadExcel;
				String sinNULLFechaTermino2 = datosContratos.getFechaTerminoContrato() == null ? "" : datosContratos.getFechaTerminoContrato(); 
				
				if(!sinNULLFechaTermino1.equals(sinNULLFechaTermino2)){
					System.out.println("Modificar Fecha Fin Contrato...");	
					modificarContratos(datosTrabajador, fechaInicioActividadExcel, fechaTerminoActividadExcel, new ConnectionDB());
					
					//Volver a Obtener Mismo Contrato
					contratos = contratoDB.getContratoWithFilter(where);
					if(contratos.size() != 0){
						datosContratos = contratoDB.getContratoWithFilter(where).get(0);
					}
					
				}
				
				
				
				
			}
			
			System.out.println(datosTrabajador.getCodigo()+" : "+ datosTrabajador.getRut() + " : " +  datosContratos);
			 
			}
			 
			saltar = false;
			 
			 
			 
			 
			 
			 
//			 
//			 
//			 //Parametros Excel
//			 //Datos de Excel
//			 System.out.println("RUT: " + value.get(0) + " Fecha Inicio Activida:"+ value.get(1) + " Fecha Termino Actividad: " +value.get(2));
//			 String periodoExcel = TimeUtility.convertStringToYYYYMM(TimeUtility.convertStringToDDMMYYYY(value.get(1)));
//			 String periodoExcelFin = TimeUtility.convertStringToYYYYMM(TimeUtility.convertStringToDDMMYYYY(value.get(2)));
//			 String fechaInicioActividadExcel = GeneralUtility.convertStringToYYYYMMDD(value.get(1));
//			 String fechaTerminoActividadExcel = GeneralUtility.convertStringToYYYYMMDD(value.get(2));
//			// String sueldoBaseExcel = "";
//			// String horasSemanalesExcel = "";
//			
//			 //Parametros Contratos
//			 contrato datosContratos = new contrato();
//			 contrato datosContratos_per = new contrato();
//			 contrato datosUltimoContrato = new contrato();
//			 
//			 //Obtener el Trabajador
//			 trabajadores datosTrabajador  = trabajadoresDB.getTrabajadorByRut(value.get(0)/*rut*/);
//			 
//			 ArrayList<filterSql> where = new ArrayList<filterSql>();
//			 filterSql filter1 = new filterSql("codigo_trabajador",datosTrabajador.getCodigo());
//			 filterSql filter2 = new filterSql("fechaInicio_actividad_date",value.get(1).replaceAll("/", "-")/*fechaInicio_actividad*/);
//			 filterSql filter3 = new filterSql("_historial","true"/*contratos_per*/);
//			 
//			 where.add(filter1);
//			
//			 //Obtener los datos del ultimo contrato del trabajador activo o no
//			 datosUltimoContrato = contratoDB.getUltimoContrato(where);
//			 
//			 where.add(filter2);
//			
//			 //Obtener todos los Contratos del Trabajador ordenador por fecha de Inicio
//			 ArrayList<contrato> listDatosContratos = contratoDB.getAllContratoTrabajadorWithFilter(where);
//			 
//			 ConnectionDB db = new ConnectionDB();
//			 
//			 try {
//				 
//			 PreparedStatement ps = null;
//			 sql = "";
//			 ResultSet rs = null;
//			 db.conn.setAutoCommit(false);
//				 
//			 if(datosUltimoContrato.getFecha_inicio_actividad().equals(fechaInicioActividadExcel.trim())){
//				 
//				 modificarContratosPer(datosTrabajador, fechaInicioActividadExcel, fechaTerminoActividadExcel, db);
//				 inactivarContratosAnteriores(datosTrabajador, fechaInicioActividadExcel, db);
//				 modificarContratos(datosTrabajador, fechaInicioActividadExcel, fechaTerminoActividadExcel, db);
//				 continue;
//			 }
//			 //Si el Periodo del Excel Es enero y No tiene contratos
//			 else if("201901".equals(periodoExcel) && listDatosContratos.size() == 0 ){
//				 //Insertar un Nuevo contrato para la fecha de Enero del Excel
//				sqlInsert = ""; 
//				sqlInsert += " INSERT INTO contratos ( id, codigo_trabajador, idSociedad, tipoTrabajador , fechaInicio_actividad, fechaContrato_emitido, FechaTerminoContrato, EstadoContrato, cargo, tipoContrato, horasSemanales, sueldoBase, idHuertoContrato, idCECOContrato, idFaenaContrato, fechaPago, paraFiniquitar ) " 
//						  + " VALUES ( null, ?, ?, 1, ?, now(), ?, 1, ?, ?, ?, ?, ?, ?, ?, ?, 1 ) "; 
//				 ps = db.conn.prepareStatement(sqlInsert);
//				 i = 1;
//				 ps.setString(i++, datosTrabajador.getCodigo());
//				 ps.setInt(i++, datosUltimoContrato.getId_sociedad());
//				 ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(fechaInicioActividadExcel));
//				 ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(fechaTerminoActividadExcel));
//				 ps.setInt(i++, datosUltimoContrato.getCargo());
//				 ps.setInt(i++, datosUltimoContrato.getTipoContrato());
//				 ps.setInt(i++, datosUltimoContrato.getHorasSemanales());
//				 ps.setDouble(i++, datosUltimoContrato.getSueldoBase());
//				 ps.setString(i++, datosUltimoContrato.getIdHuertoContrato());
//				 ps.setString(i++, datosUltimoContrato.getIdCECOContrato());
//				 ps.setInt(i++, datosUltimoContrato.getIdFaenaContrato());
//				 ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD("07-02-2019"));
//				 ps.executeUpdate();
//				 rs = ps.getGeneratedKeys();
//				 if(rs.next()){
//						key = rs.getInt(1);
//						
//						//Modificar Rendimientos Diarios en el periodo de Enero
//						modificarRendimientosDiarios(key, datosTrabajador, db);
//						//Modificar Anticipos en Enero
//						modificarAnticipos(key, datosTrabajador, db);
//						//Modificar Haberes y Descuentos en Enero
//						modificarHaberesDescuentos(key, datosTrabajador, db);
//						//Inactivar los Contratos Anteriores
//						inactivarContratosAnteriores(datosTrabajador, fechaInicioActividadExcel, db);
//							
//				 }else{
//					 throw new Exception("No se pudo insertar contrato");
//				 }
//			 }
//			 
//			 
//			 
//			 where.add(filter3);
//			//Obtener todos los Contratos de Historial del Trabajador ordenador por fecha de Inicio
//			 ArrayList<contrato> listDatosContratos_per = contratoDB.getAllContratoTrabajadorWithFilter(where);
//
//			 System.out.println("Datos Actuales");
//			 for (contrato contrato : listDatosContratos) {
//				//Solo tiene un Contrato para este periodo
//				if(listDatosContratos.size() == 1){
//					datosContratos = contrato;
//				}
//			 }
//			 System.out.println(datosContratos);
//			 String periodoGlobal = TimeUtility.convertStringToYYYYMM(TimeUtility.convertStringToDDMMYYYY(datosContratos.getFecha_inicio_actividad()));
//			 
//			 System.out.println("Datos Historicos");
//			 for (contrato contrato : listDatosContratos_per) {
//				 if(String.valueOf(contrato.getPeriodo()).equals(periodoGlobal)){
//					 datosContratos_per = contrato;
//				 }
//			 }
//			 
//			 //Si tiene finiquito
//			 boolean hasFiniquito = false;
////			 ArrayList<Finiquito> listFiniquitos = FiniquitosBD.getFiniquitosByCodTrabajador(datosTrabajador.getCodigo());
////			 for (Finiquito finiquito : listFiniquitos) {
////				 if(periodoGlobal.equals(finiquito.getPeriodo())){
////					hasFiniquito = true; 
////				 }
////			 }
//			 
//			 
//			 
////			 if(!hasFiniquito){
////				 sql = " UPDATE contratos_per SET fecha_termino_actividad = ?, estado_contrato = ? WHERE periodo = ? and id = ? ";
////				 ps = db.conn.prepareStatement(sql);
////				 i = 1;
////				 ps.setString(i++, null);
////				 ps.setInt(i++, 1);
////				 ps.setString(i++, periodoGlobal);
////				 ps.setInt(i++, datosContratos.getId());
////				 ps.execute();
////			 }
//
//			 //Si tienen la misma fecha de Inicio, Si es el mismo Id, 
//			 //Si la fecha de Termino es null en la tabla historia, 
//			 //Si los periodos corresponden con la fecha Inicio y esta finiquitado
//			 //Si solo tiene un Contrato para ese periodo
////			 if(datosContratos.getFecha_inicio_actividad().equals(datosContratos_per.getFecha_inicio_actividad()) 
////				&& datosContratos_per.getFecha_termino_actividad() == null
////				&& datosContratos.getFiniquitado() == 1
////				&& listDatosContratos.size() == 1){
////					 
////				sql = " UPDATE contratos_per SET fecha_termino_actividad = ?, estado_contrato = ? WHERE periodo = ? and id = ? ";
////				ps = db.conn.prepareStatement(sql);
////				i = 1;
////				ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(datosContratos.getFecha_termino_actividad()));
////				ps.setInt(i++, datosContratos.getEstado_contrato());
////				ps.setString(i++, TimeUtility.convertStringToYYYYMM(datosContratos.getFecha_inicio_actividad()));
////				ps.setInt(i++, datosContratos.getId());
////			 }
////				 
////				 
////				 ps.execute();
//				 db.conn.commit();
////				 
////				 ps.close();
////				 db.close();
//				 
//				 
//			} catch (Exception e) {
//				db.conn.rollback();
//				process_error += " No Se proceso el trabajador : " + datosTrabajador.getRut() + " <br> "; 
//			}
			 
			 
			 
			
			
			 
			 
			 
			
			 
			
		}
		
		return process_error;
			
	}
	

	private static boolean inactivarContratosAnteriores(trabajadores datosTrabajador, String fechaInicioActividad, ConnectionDB db) throws Exception {
		
		String sql = "";
		
		int i = 1;
		
		try{
			
		PreparedStatement ps = null;

		sql = " UPDATE contratos SET EstadoContrato = ? WHERE codigo_trabajador = ? and fechaInicio_actividad < ? ";

		ps = db.conn.prepareStatement(sql);
		i = 1;
		ps.setInt(i++, 0);
		ps.setString(i++, datosTrabajador.getCodigo());
		ps.setString(i++, fechaInicioActividad);
		ps.execute();
		ps.close();
		
		}catch(Exception e){
			throw new Exception();
		}
			
		
		try{
			
			PreparedStatement ps = null;

			sql = " UPDATE contratos_per SET EstadoContrato = ? WHERE codigo_trabajador = ? and fechaInicio_actividad < ? ";

			ps = db.conn.prepareStatement(sql);
			i = 1;
			ps.setInt(i++, 0);
			ps.setString(i++, datosTrabajador.getCodigo());
			ps.setString(i++, fechaInicioActividad);
			ps.execute();
			ps.close();
			
			}catch(Exception e){
				throw new  Exception();
			}
		
		return true;

	}
	
//private static boolean inactivarContratosAnteriores2(int key , trabajadores datosTrabajador, String fechaInicioActividad, ConnectionDB db) throws SQLException {
//		
//		String sql = "";
//		ConnectionDB db1 = new ConnectionDB();
//		ConnectionDB db2 = new ConnectionDB();
//		
//		int i = 1;
//		
//		
//		try{
//			
//		PreparedStatement ps = null;
//		db1.conn.setAutoCommit(false);
//
//		sql = " UPDATE contratos SET EstadoContrato = ? WHERE codigo_trabajador = ? and id < ? ";
//
//		ps = db1.conn.prepareStatement(sql);
//		i = 1;
//		ps.setInt(i++, 0);
//		ps.setString(i++, datosTrabajador.getCodigo());
//		ps.setInt(i++, key);
//		ps.execute();
//		db1.conn.commit();
//		ps.close();
//		db1.close();
//		
//		}catch(Exception e){
//			db1.conn.rollback();
//			db1.close();
//			return false;
//		}
//			
//		
//		try{
//			
//			PreparedStatement ps = null;
//			db2.conn.setAutoCommit(false);
//
//			sql = " UPDATE contratos_per SET EstadoContrato = ? WHERE codigo_trabajador = ? and id < ?  ";
//
//			ps = db2.conn.prepareStatement(sql);
//			i = 1;
//			ps.setInt(i++, 0);
//			ps.setString(i++, datosTrabajador.getCodigo());
//			ps.setInt(i++, key);
//			ps.execute();
//			db2.conn.commit();
//			ps.close();
//			db2.close();
//			
//			}catch(Exception e){
//				db2.conn.rollback();
//				db2.close();
//				return false;
//			}
//		
//		return true;
//
//	}


	private static boolean modificarHaberesDescuentos(int key, trabajadores datosTrabajador, ConnectionDB db) throws Exception {
		
		String sql = "";
		
		int i = 1;
		
		try{
			
		PreparedStatement ps = null;

		sql = " UPDATE sw_haberesDescuentos SET idContrato = ? WHERE codigo_trabajador = ? and fecha_inicio = ? ";

		ps = db.conn.prepareStatement(sql);
		ps.setInt(i++, key);
		ps.setString(i++, datosTrabajador.getCodigo());
		ps.setString(i++, "201901");
		ps.execute();
		ps.close();
		
		}catch(Exception e){
			throw new Exception();
		}
			
		return true;
	}


	private static boolean modificarAnticipos(int key, trabajadores datosTrabajador, ConnectionDB db) throws Exception {
		
		String sql = "";
		
		int i = 1;
		
		try{
			
		PreparedStatement ps = null;

		sql = " UPDATE sw_asignacionAnticipos SET idContrato = ? WHERE cod_trabajador = ? and DATE_FORMAT(fecha, '%Y%m') = ? ";

		ps = db.conn.prepareStatement(sql);
		ps.setInt(i++, key);
		ps.setString(i++, datosTrabajador.getCodigo());
		ps.setString(i++, "201901");
		ps.execute();
		ps.close();
		
		}catch(Exception e){
			throw new Exception();
		}
		
		return true;
	}


	private static boolean modificarRendimientosDiarios(int key, trabajadores datosTrabajador, ConnectionDB db) throws Exception {
		
		String sql = "";
		
		int i = 1;
		
		try{
			
		PreparedStatement ps = null;

		sql = " UPDATE rendimiento_diario SET idContrato = ? WHERE trabajador = ? and DATE_FORMAT(fecha_i, '%Y%m') = ? ";

		ps = db.conn.prepareStatement(sql);
		ps.setInt(i++, key);
		ps.setInt(i++, datosTrabajador.getId());
		ps.setString(i++, "201901");
		ps.execute();
		ps.close();
		
		}catch(Exception e){
			throw new Exception();
		}
		
		
		return true;
	}


	
	private static boolean modificarContratosPer(trabajadores datosTrabajador, String fechaInicioExcel, String FechaTerminoExcel, ConnectionDB db) throws Exception {
		
		String sql = "";
		
		int i = 1;
		
		try{
			
		PreparedStatement ps = null;

		sql = " UPDATE contratos_per SET FechaTerminoContrato = ? WHERE codigo_trabajador = ? and periodo = ? and fechaInicio_actividad = ? ";

		ps = db.conn.prepareStatement(sql);
		ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(FechaTerminoExcel));
		ps.setString(i++, datosTrabajador.getCodigo());
		ps.setString(i++, "201901");
		ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(fechaInicioExcel));
		ps.execute();		
		ps.close();
		
		}catch(Exception e){
			throw new Exception();
		}
		
		return true;
	}

	
private static boolean modificarContratos(trabajadores datosTrabajador, String fechaInicioExcel, String FechaTerminoExcel, ConnectionDB db) throws Exception {
		
		String sql = "";
		int i = 1;
		
		try{
			
		PreparedStatement ps = null;
		
		String activo = " 0 ";
		//Esta activo
		if(FechaTerminoExcel == null){
			activo = " 1 ";
		}
		

		sql = " UPDATE contratos SET FechaTerminoContrato = ?, EstadoContrato = "+activo+" WHERE codigo_trabajador = ? and fechaInicio_actividad = ? ";

		ps = db.conn.prepareStatement(sql);
		ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(FechaTerminoExcel));
		ps.setString(i++, datosTrabajador.getCodigo());
		ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(fechaInicioExcel));
		ps.execute();
		ps.close();
		db.close();
		activo = " 0 ";
		
		}catch(Exception e){
			throw new Exception();
		}
		
		return true;
	}
	

private static boolean insertContrato(trabajadores datosTrabajador, Contratos datosContrato, String fechaInicioActividadExcel, String fechaTerminoActividadExcel ) throws SQLException, ParseException{
	
	//Insertar un Nuevo contrato para la fecha de Enero del Excel
	
	String sqlInsert = ""; 
	ConnectionDB db = new ConnectionDB();
	PreparedStatement ps = null;
	int i = 1;
	
	sqlInsert += " INSERT INTO contratos ( id, codigo_trabajador, idSociedad, tipoTrabajador , fechaInicio_actividad, fechaContrato_emitido, FechaTerminoContrato, EstadoContrato, cargo, tipoContrato, horasSemanales, sueldoBase, idHuertoContrato, idCECOContrato, idFaenaContrato, fechaPago, paraFiniquitar ) " 
			  + " VALUES ( null, ?, ?, 1, ?, now(), ?, 0, ?, ?, ?, ?, ?, ?, ?, ?, 1 ) "; 
	 ps = db.conn.prepareStatement(sqlInsert);
	 i = 1;
	 ps.setString(i++, datosTrabajador.getCodigo());
	 ps.setInt(i++, datosContrato.getIdSociedad());
	 ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(fechaInicioActividadExcel));
	 ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(fechaTerminoActividadExcel));
	 ps.setInt(i++, datosContrato.getCargo());
	 ps.setInt(i++, datosContrato.getTipoContrato());
	 ps.setInt(i++, datosContrato.getHorasSemanales());
	 ps.setDouble(i++, datosContrato.getSueldoBase());
	 ps.setString(i++, datosContrato.getIdHuertoContrato());
	 ps.setString(i++, datosContrato.getIdCECOContrato());
	 ps.setInt(i++, datosContrato.getIdFaenaContrato());
	 ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD("13-02-2019"));
	 ps.executeUpdate();
	
	 ps.close();
	 db.close();
	return true;
	 
	
}


	
}

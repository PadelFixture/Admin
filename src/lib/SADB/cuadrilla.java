package lib.SADB;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import lib.classSA.CUADRILLA;
import lib.classSA.RENDIMIENTO_DIARIO;
import lib.classSA.RENDIMIENTO_GENERAL;
import lib.classSA.TrabajadoresAgro;
import lib.classSW.trabajador;
import lib.db.ConnectionDB;

public class cuadrilla {
	public static ArrayList<CUADRILLA> GETCUAD () throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CUADRILLA> lista =  new ArrayList<CUADRILLA>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT * FROM cuadrilla";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				CUADRILLA ob = new CUADRILLA();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setNombre_cuadrilla(rs.getString("nombre_cuadrilla"));
				ob.setSupervisor(rs.getInt("supervisor"));
				ob.setFecha_creacion(rs.getString("fecha_creacion"));
				ob.setEstado(rs.getInt("estado"));					
				lista.add(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error GETCUAD:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error GETCUAD:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
	public static ArrayList<RENDIMIENTO_GENERAL> GET_CUADRILLA_SUPERVISOR (int id, String c, String contratista) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<RENDIMIENTO_GENERAL> lista =  new ArrayList<RENDIMIENTO_GENERAL>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT DISTINCT rg.codigo_rg AS codigo_rg, rg.fecha, CONCAT(t.apellidoPaterno, ' ', t.apellidoMaterno, ' ', t.nombre) AS nombre, c.correlativo FROM cuadrilla c ";
			sql += "RIGHT JOIN rendimiento_general rg on(c.codigo = rg.codigo_cuadrilla) ";
			sql += "INNER JOIN trabajadores t on(rg.codigo_supervisor = t.id) ";
			sql += "WHERE c.supervisor = "+id+" AND rg.codigo_cuadrilla != 0";
			if(c.equals("contratista")){
				sql += " AND rg.contratista != 0 AND rg.contratista = '"+contratista+"'";
			}else{
				sql += " AND rg.contratista IS NULL ";
			}
			sql += "AND c.fecha_creacion BETWEEN DATE_SUB(CURDATE(), INTERVAL 1 MONTH) AND CURDATE()";
			sql += "ORDER BY 2 DESC;";
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				RENDIMIENTO_GENERAL ob = new RENDIMIENTO_GENERAL();
				ob.setCodigo_supervisor(rs.getInt("codigo_rg"));
				ob.setFecha(rs.getString("fecha"));			
				ob.setNsupervisor(rs.getString("nombre"));
				ob.setCodigo(rs.getInt("correlativo"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error GETCUAD:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error GETCUAD:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
//	public static boolean UPDATE_CUADRILLA (CUADRILLA c) throws Exception{
//		PreparedStatement ps = null;
//		String sql = "";
//		ConnectionDB db = new ConnectionDB();
//		try {
//			delete_cuadrilla(c.getCodigo());
//			sql = "UPDATE cuadrilla set nombre_cuadrilla = ?, supervisor = ?, estado = ?, where codigo = ?";
//			ps = db.conn.prepareStatement(sql);
//			ps.setString(1, c.getNombre_cuadrilla());
//			ps.setInt(2, c.getSupervisor());
//			ps.setInt(3, c.getEstado());
//			ps.setInt(4, c.getCodigo());
//			ps.execute();
//			for(trabajador trab: c.getTrab()){
//				inCuadrilla(c.getCodigo(),trab.rut);
//			}
//			return true;
//		} catch (SQLException e) {
//			System.out.println("Error:" + e.getMessage());
//		} catch (Exception e) {
//			System.out.println("Error:" + e.getMessage());
//		} finally {
//			ps.close();
//			db.close();
//		}
//		return false;
//	}
	public static boolean delete_cuadrilla(int codigo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "DELETE FROM cuadrilla_trabajador WHERE codigo_cuadrilla = "+codigo+"";
			ps = db.conn.prepareStatement(sql);
			ps.execute();	
		}catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return true;
	}
	public static  boolean inCuadrilla(String rut, int asistencia, int motivo, String observacion) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO cuadrilla_trabajador (codigo_cuadrilla, rut_trabajador, asistencia, motivo, observacion) VALUES ((SELECT MAX(codigo) from cuadrilla),?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, rut);
			ps.setInt(2, asistencia);
			ps.setInt(3, motivo);
			ps.setString(4, observacion);
			
			ps.execute();				
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return true;
	}
	public static boolean ADD_CUADRILLA (CUADRILLA c)throws Exception{
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			String fechaRG = "";
			ArrayList<RENDIMIENTO_GENERAL> rg = c.getRendimiento_general();
			for(RENDIMIENTO_GENERAL rgs: rg){
				fechaRG = rgs.getFecha();
			}
			String sqlFolio = "SELECT COUNT(codigo_rg)+1 AS folio FROM rendimiento_general WHERE codigo_supervisor = "+c.getSupervisor()+" AND fecha = '"+fechaRG+"'";
			int folio = RENDIMIENTO.r_folio(sqlFolio);
			if(folio == 0){
				folio = 1;
			}
			sql = "INSERT INTO cuadrilla(nombre_cuadrilla, supervisor, fecha_creacion, estado, correlativo) VALUES (?,?,?,1, "+folio+")";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, c.getNombre_cuadrilla());
			ps.setInt(2, c.getSupervisor());
			ps.setString(3, c.getFecha_creacion());
			ps.execute();
			String sql2 = "";
			for(RENDIMIENTO_GENERAL rgs: rg){
				sql2 = "UPDATE rendimiento_general set codigo_cuadrilla =  (SELECT max(codigo) from cuadrilla) WHERE codigo_supervisor = "+rgs.getCodigo_supervisor()+" AND fecha = '"+rgs.getFecha()+"'";
			}
			ps2 = db.conn.prepareStatement(sql2);
			ps2.execute();
			
			for(TrabajadoresAgro trab: c.getTrab()){
				inCuadrilla(trab.getRut(), trab.getIdSociedad(), trab.getCargo(), trab.getDescripcionTipoTrabajador());
			}
			return true;
		} catch (SQLException e) {
			System.out.println("Error ADD_CUADRILLA:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error ADD_CUADRILLA:" + e.getMessage());
		} finally {
			ps2.close();
			ps.close();
			db.close();
		}
		return false;
	}
	public static CUADRILLA GET_CUADRILLA_TRABAJADOR(int codigo) throws Exception{
		PreparedStatement ps =  null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		CUADRILLA CU = new CUADRILLA();
		try {
			sql = "SELECT c.*,ct.codigo_cuadrilla, t.*,rg.* ";
			sql += 	"CASE ";
			sql += 		"WHEN (cts.tipoTrabajador = 1) THEN ";
			sql += 			"(sueldoBase/30) ";
			sql += 		"WHEN (cts.tipoTrabajador = 2) ";
			sql += 			"THEN (sueldoBase/30) ";
			sql += 		"ELSE (sueldoBase/30) ";
			sql += 		"END AS sueldoDiario, ";
			sql += 	"CASE ";
			sql += 		"WHEN (cts.tipoTrabajador = 6) ";
			sql += 			"THEN (horasSemanales/5)";
			sql += 		"WHEN (cts.tipoTrabajador = 7) ";
			sql += 			"THEN (horasSemanales/6) ";
			sql += 		"ELSE (horasSemanales/5) ";
			sql += 		"END AS jornadaDiaria, ";
			sql += 		"(SELECT ";
			sql += 		"CASE ";
			sql += 			"WHEN (cts.tipoTrabajador = 4) ";
			sql += 				"THEN 'Contratista' ";
			sql += 			"ELSE 'Planta' ";
			sql += 		"END) AS descripcionTipoTrabajador, ";
			sql += 	"CASE ";
			sql += 		"WHEN (cts.tipoTrabajador = 6) ";
			sql += 			"THEN (5) ";
			sql += 		"WHEN (cts.tipoTrabajador = 7) ";
			sql += 			"THEN (6) ";
			sql += 		"ELSE (5) ";
			sql += 		"END AS calculoJornadaSemanal, cts.* ";
			sql += "FROM cuadrilla c ";
			sql += "LEFT JOIN cuadrilla_trabajador ct ON c.codigo = ct.codigo_cuadrilla  ";
			sql += "RIGHT JOIN trabajadores t ON ct.rut_trabajador = t.rut ";
			sql += "LEFT JOIN rendimiento_general rg ON (c.codigo = rg.codigo_cuadrilla)";
			sql += "WHERE c.codigo = "+codigo;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			ArrayList<TrabajadoresAgro> trabajador = new ArrayList<TrabajadoresAgro>();
			ArrayList<RENDIMIENTO_GENERAL> rendimiento_general = new ArrayList<RENDIMIENTO_GENERAL>();
			int count = 1;
			while (rs.next()) {
				TrabajadoresAgro tr = new TrabajadoresAgro();
				RENDIMIENTO_GENERAL rg = new RENDIMIENTO_GENERAL();
				if (count == 1){
					CU.setCodigo(rs.getInt("codigo"));	
					CU.setNombre_cuadrilla(rs.getString("nombre_cuadrilla"));
					CU.setSupervisor(rs.getInt("supervisor"));
					CU.setFecha_creacion(rs.getString("fecha_creacion"));
					CU.setEstado(rs.getInt("estado"));
					rg.setCodigo(rs.getInt("codigo_rg"));
					rg.setFecha(rs.getString("fecha"));
					rg.setEspecie(rs.getInt("especie"));
					rg.setVariedad(rs.getInt("variedad"));
					rg.setCuartel(rs.getInt("cuartel"));
					rg.setFaena(rs.getInt("faena"));
					rg.setLabor(rs.getInt("labor"));
					rg.setHoras(rs.getFloat("horas"));
					rg.setTipo_pago(rs.getInt("tipo_pago"));
					rg.setCodigo_cuadrilla(rs.getInt("codigo_cuadrilla"));
					rg.setCodigo_supervisor(rs.getInt("codigo_supervisor"));
					rendimiento_general.add(rg);
				}
				tr.setCodigo(rs.getString("codigo"));
				tr.setIdTrabajador(rs.getInt("id"));
				tr.setRut(rs.getString("rut"));
				tr.setNombre(rs.getString("nombre")+" "+rs.getString("apellidoPaterno"));
				tr.setApellidoPaterno(rs.getString("apellidoPaterno"));
				tr.setIdSociedad(rs.getInt("idSociedad"));
				tr.setAgro(rs.getInt("agro"));
				tr.setSueldoBase(rs.getInt("sueldoBase"));
				tr.setJornadaSemanal(rs.getInt("horasSemanales"));
				tr.setCargo(rs.getInt("cts.cargo"));
				tr.setTipoTrabajador(rs.getInt("cts.tipoTrabajador"));
				tr.setJornadaDiaria(rs.getDouble("jornadaDiaria"));
				tr.setSueldoDiario(rs.getDouble("sueldoDiario"));
				tr.setDescripcionTipoTrabajador(rs.getString("descripcionTipoTrabajador"));
				tr.setCalculoJornadaSemanal(rs.getString("calculoJornadaSemanal"));
				trabajador.add(tr);
				count ++;		
			} 
			CU.setTrab(trabajador);
			CU.setRendimiento_general(rendimiento_general);
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error GET_CUADRILLA_TRABAJADOR:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error GET_CUADRILLA_TRABAJADOR:" + e.getMessage());
		} finally {
			db.close();
		}
		return CU;
	}
	public static CUADRILLA GET_CUADRILLA_TRABAJADOR_FECHA(String fecha, int idUser, int cuartel, String tipo) throws Exception{
		PreparedStatement ps =  null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		CUADRILLA CU = new CUADRILLA();
		try {
			sql = "SELECT c.*,ct.codigo_cuadrilla, t.*, rg.*, rg.estado as estado_rg, rd.*, rd.codigo AS codigo_rd, rd.codigo_rg AS codigo_rg_rd, rd.labor AS labor_rd, rd.valor AS valor_rd, rd.estado AS estado_rd, rd.cuartel AS cuartel_rd, ";
			sql += 	"GETLIQUIDO(cts.codigo_trabajador, cts.id,";
			sql += 		"DATE_FORMAT('"+fecha+"', '%Y%m'), 0) AS sueldoDiario,";
			sql += 	"GETHORASEXTRAS('"+fecha+"', t.rut) AS hx_semana,";
			sql += 	"CASE ";
			sql += 		"WHEN (cts.tipoTrabajador = 6) ";
			sql += 			"THEN (horasSemanales/5)";
			sql += 		"WHEN (cts.tipoTrabajador = 7) ";
			sql += 			"THEN (horasSemanales/6) ";
			sql += 		"ELSE (horasSemanales/5) ";
			sql += 		"END AS jornadaDiaria, ";
			sql += 	"CASE ";
			sql += 		"WHEN (cts.tipoTrabajador = 4) ";
			sql += 			"THEN 'Contratista' ";
			sql += 		"ELSE 'Planta' ";
			sql += 	"END AS descripcionTipoTrabajador, ";
			sql += 	"CASE ";
			sql += 		"WHEN (cts.tipoTrabajador = 6) ";
			sql += 			"THEN (5) ";
			sql += 		"WHEN (cts.tipoTrabajador = 7) ";
			sql += 			"THEN (6) ";
			sql += 		"ELSE (5) ";
			sql += 		"END AS calculoJornadaSemanal, cts.* ";
			sql += "FROM cuadrilla c ";
			sql += "LEFT JOIN cuadrilla_trabajador ct ON (c.codigo = ct.codigo_cuadrilla) ";
			sql += "RIGHT JOIN trabajadores t ON (ct.rut_trabajador = t.rut OR ct.rut_trabajador = t.rutTemporal) ";
			sql += "INNER JOIN contratos cts ON (t.codigo = cts.codigo_trabajador)";
			sql += "LEFT JOIN rendimiento_general rg ON (c.codigo = rg.codigo_cuadrilla) ";
			sql += "LEFT JOIN rendimiento_diario rd ON(rg.codigo_rg = rd.codigo_rg and rd.trabajador = t.id) ";
			sql += "WHERE c.supervisor = "+idUser+" and rg.fecha = '"+fecha+"' AND ct.asistencia IN (1, 5) AND rg.cuartel = "+cuartel+" AND t.agro = 1 AND cts.EstadoContrato = 1  AND rg.estado != 7 ";
			if(tipo.equals("masivo")){
				sql += " AND rg.contratista IS NULL";
			}else{
				sql += " AND rg.contratista IS NOT NULL";
			}
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			ArrayList<TrabajadoresAgro> trabajador = new ArrayList<TrabajadoresAgro>();
			ArrayList<RENDIMIENTO_GENERAL> rendimiento_general = new ArrayList<RENDIMIENTO_GENERAL>();
			ArrayList<RENDIMIENTO_DIARIO> rendimiento_diario = new ArrayList<RENDIMIENTO_DIARIO>();
			int count = 1;
			while (rs.next()) {
				TrabajadoresAgro tr = new TrabajadoresAgro();
				RENDIMIENTO_GENERAL rg = new RENDIMIENTO_GENERAL();
				RENDIMIENTO_DIARIO rd = new RENDIMIENTO_DIARIO();
				if (count == 1){
					CU.setCodigo(rs.getInt("codigo"));	
					CU.setNombre_cuadrilla(rs.getString("nombre_cuadrilla"));
					CU.setSupervisor(rs.getInt("supervisor"));
					CU.setFecha_creacion(rs.getString("fecha_creacion"));
					CU.setEstado(rs.getInt("estado"));
					rg.setCodigo(rs.getInt("codigo_rg"));
					rg.setFecha(rs.getString("fecha"));
					rg.setEspecie(rs.getInt("especie"));
					rg.setVariedad(rs.getInt("variedad"));
					rg.setCuartel(rs.getInt("cuartel"));
					rg.setFaena(rs.getInt("faena"));
					rg.setLabor(rs.getInt("labor"));
					rg.setHoras(rs.getFloat("horas"));
					rg.setTipo_pago(rs.getInt("tipo_pago"));
					rg.setValor(rs.getInt("valor"));
					rg.setBase_piso_dia(rs.getInt("base_piso_dia"));
					rg.setCodigo_cuadrilla(rs.getInt("codigo_cuadrilla"));
					rg.setCodigo_supervisor(rs.getInt("codigo_supervisor"));
					rg.setEstado(rs.getInt("estado_rg"));
					float valorHora = rg.getBase_piso_dia()/rg.getHoras();
					rg.setValor_x_hora(valorHora);
					rg.setContratista(rs.getString("contratista"));
					rendimiento_general.add(rg);
				}
				rd.setCodigo(rs.getInt("codigo_rd"));
				rd.setTrabajador(rs.getInt("trabajador"));
				rd.setBase_piso_hora(rs.getFloat("base_piso_hora"));
				rd.setSubsidio(rs.getInt("subsidio"));
				rd.setCuartel(rs.getInt("cuartel_rd"));
				rd.setLabor(rs.getInt("labor_rd"));
				rd.setValor(rs.getInt("valor_rd"));
				rd.setTipo_trato(rs.getInt("tipo_trato"));
				rd.setRendimiento(rs.getFloat("rendimiento"));
				rd.setHoras_trabajadas(rs.getFloat("horas_trabajadas"));
				rd.setHoras_extras(rs.getFloat("horas_extras"));
				rd.setBono1(rs.getInt("bono1"));
				rd.setBono2(rs.getInt("bono2"));
				rd.setValor_liquido(rs.getFloat("valor_liquido"));
				rd.setMaquinaria(rs.getInt("maquinaria"));
				rd.setImplemento(rs.getInt("implemento"));
				rd.setBus(rs.getInt("bus"));
				rd.setEstado(rs.getInt("estado_rd"));
				rd.setCodigo_rg(rs.getInt("codigo_rg_rd"));
				rd.setValor_hx(rs.getFloat("valor_hx"));
				rd.setMonto_hx(rs.getFloat("monto_hx"));
				rd.setValor_hx_dos(rs.getFloat("valor_hx_dos"));
				rd.setHx_dos(rs.getFloat("hx_dos"));
				
				int sueldo = rs.getInt("sueldoDiario");
				
				tr.setCodigo(rs.getString("codigo"));
				tr.setIdTrabajador(rs.getInt("id"));
				tr.setRut(rs.getString("rut"));
				if(tr.getRut().equals("")){
					tr.setRut(rs.getString("rutTemporal"));
				}
				tr.setHx((sueldo*0.0077777));
				tr.setNombre(rs.getString("nombre")+" "+rs.getString("apellidoPaterno"));
				tr.setApellidoPaterno(rs.getString("apellidoPaterno"));
				tr.setIdSociedad(rs.getInt("idSociedad"));
				tr.setAgro(rs.getInt("agro"));
				tr.setSueldoBase(rs.getInt("sueldoBase"));
				tr.setJornadaSemanal(rs.getInt("horasSemanales"));
				tr.setCargo(rs.getInt("cts.cargo"));
				tr.setTipoTrabajador(rs.getInt("cts.tipoTrabajador"));
				tr.setJornadaDiaria(rs.getDouble("jornadaDiaria"));
				tr.setSueldoDiario(rs.getDouble("sueldoDiario"));
				tr.setDescripcionTipoTrabajador(rs.getString("descripcionTipoTrabajador"));
				tr.setCalculoJornadaSemanal(rs.getString("calculoJornadaSemanal"));
				tr.setIdContratista(rs.getString("idContratista"));
				if(return_horas_restantes(fecha, rs.getInt("id")) == 100){
					tr.setHoras_restantes(rs.getDouble("jornadaDiaria"));
				}else{
					tr.setHoras_restantes(return_horas_restantes(fecha, rs.getInt("id")));
				}
				tr.setHx_semana(rs.getFloat("hx_semana"));
				trabajador.add(tr);
				rendimiento_diario.add(rd);
				count ++;		
			} 
			CU.setTrab(trabajador);
			CU.setRendimiento_general(rendimiento_general);
			CU.setRd(rendimiento_diario);
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error GET_CUADRILLA_TRABAJADOR_FECHA:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error GET_CUADRILLA_TRABAJADOR_FECHA:" + e.getMessage());
		} finally {
			db.close();
		}
		return CU;
	}
	public static double return_horas_restantes(String fecha, int codigo) throws Exception{
		PreparedStatement ps =  null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		float horas_restantes = 0;
		try{
			sql = 	"SELECT DISTINCT ";
			sql += 		"CASE ";
			sql += 			"WHEN (cts.tipoTrabajador = 6) ";
			sql += 				"THEN (horasSemanales/5) ";
			sql += 			"WHEN (cts.tipoTrabajador = 7) ";
			sql += 				"THEN (horasSemanales/6) ";
			sql += 			"ELSE (horasSemanales/5) ";
			sql += 			"END AS jornadaDiaria, ";
			sql += 		"(SELECT ";
			sql += 			"CASE ";
			sql += 				"WHEN (rd.horas_trabajadas IS NULL) ";
			sql += 					"THEN (jornadaDiaria) ";
			sql += 				"ELSE jornadaDiaria - rd.horas_trabajadas ";
			sql += 				"END) AS horas_restantes ";
			sql += 	"FROM rendimiento_diario rd ";
			sql += 	"RIGHT JOIN trabajadores t ON rd.trabajador = t.id ";
			sql += 	"INNER JOIN contratos cts ON (t.codigo = cts.codigo_trabajador) ";
			sql += 	"INNER JOIN rendimiento_general rg ";
			sql += 	"WHERE rd.trabajador = "+codigo+" AND rg.fecha = '"+fecha+"'";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			if(rs.next()){
				horas_restantes = rs.getFloat("horas_restantes");
			}else{
				horas_restantes = 100;
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch (SQLException e) {
			System.out.println("Error GET_CUADRILLA_TRABAJADOR_FECHA:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error GET_CUADRILLA_TRABAJADOR_FECHA:" + e.getMessage());
		} finally {
			db.close();
		}
		return horas_restantes;
	}
	public static CUADRILLA GET_REPLICAR_CUADRILLA(int id) throws Exception{
		PreparedStatement ps =  null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		CUADRILLA CU = new CUADRILLA();
		try {
			sql = "SELECT c.*,ct.codigo_cuadrilla, t.*,rg.* ";
			sql += 	"CASE ";
			sql += 		"WHEN (cts.tipoTrabajador = 1) THEN ";
			sql += 			"(sueldoBase/30) ";
			sql += 		"WHEN (cts.tipoTrabajador = 2) ";
			sql += 			"THEN (sueldoBase/30) ";
			sql += 		"ELSE (sueldoBase/30) ";
			sql += 		"END AS sueldoDiario, ";
			sql += 	"CASE ";
			sql += 		"WHEN (cts.tipoTrabajador = 6) ";
			sql += 			"THEN (horasSemanales/5)";
			sql += 		"WHEN (cts.tipoTrabajador = 7) ";
			sql += 			"THEN (horasSemanales/6) ";
			sql += 		"ELSE (horasSemanales/5) ";
			sql += 		"END AS jornadaDiaria, ";
			sql += 		"(SELECT ";
			sql += 		"CASE ";
			sql += 			"WHEN (cts.tipoTrabajador = 4) ";
			sql += 				"THEN 'Contratista' ";
			sql += 			"ELSE 'Planta' ";
			sql += 		"END) AS descripcionTipoTrabajador, ";
			sql += 	"CASE ";
			sql += 		"WHEN (cts.tipoTrabajador = 6) ";
			sql += 			"THEN (5) ";
			sql += 		"WHEN (cts.tipoTrabajador = 7) ";
			sql += 			"THEN (6) ";
			sql += 		"ELSE (5) ";
			sql += 		"END AS calculoJornadaSemanal, cts.* ";
			sql += "FROM cuadrilla c ";
			sql += "LEFT JOIN cuadrilla_trabajador ct ON c.codigo = ct.codigo_cuadrilla  ";
			sql += "RIGHT JOIN trabajadores t ON ct.rut_trabajador = t.rut ";
			sql += "INNER JOIN contratos cts ON (t.codigo = cts.codigo_trabajador)";
			sql += "LEFT JOIN rendimiento_general rg ON (c.codigo = rg.codigo_cuadrilla and c.fecha_creacion = rg.fecha)";
			sql += "WHERE c.codigo = (SELECT MAX(codigo) FROM cuadrilla WHERE supervisor = "+id+") AND t.agro = 1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			ArrayList<TrabajadoresAgro> trabajador = new ArrayList<TrabajadoresAgro>();
			ArrayList<RENDIMIENTO_GENERAL> rendimiento_general = new ArrayList<RENDIMIENTO_GENERAL>();
			int count = 1;
			while (rs.next()) {
				TrabajadoresAgro tr = new TrabajadoresAgro();
				RENDIMIENTO_GENERAL rg = new RENDIMIENTO_GENERAL();
				if (count == 1){
					CU.setCodigo(rs.getInt("codigo"));	
					CU.setNombre_cuadrilla(rs.getString("nombre_cuadrilla"));
					CU.setSupervisor(rs.getInt("supervisor"));
					CU.setFecha_creacion(rs.getString("fecha_creacion"));
					CU.setEstado(rs.getInt("estado"));
					rg.setCodigo(rs.getInt("codigo_rg"));
					rg.setFecha(rs.getString("fecha"));
					rg.setEspecie(rs.getInt("especie"));
					rg.setVariedad(rs.getInt("variedad"));
					rg.setCuartel(rs.getInt("cuartel"));
					rg.setFaena(rs.getInt("faena"));
					rg.setLabor(rs.getInt("labor"));
					rg.setHoras(rs.getFloat("horas"));
					rg.setTipo_pago(rs.getInt("tipo_pago"));
					rg.setValor(rs.getInt("valor"));
					rg.setCodigo_cuadrilla(rs.getInt("codigo_cuadrilla"));
					rg.setCodigo_supervisor(rs.getInt("codigo_supervisor"));
					rendimiento_general.add(rg);
				}
				tr.setCodigo(rs.getString("codigo"));
				tr.setIdTrabajador(rs.getInt("id"));
				tr.setRut(rs.getString("rut"));
				tr.setNombre(rs.getString("nombre")+" "+rs.getString("apellidoPaterno"));
				tr.setApellidoPaterno(rs.getString("apellidoPaterno"));
				tr.setIdSociedad(rs.getInt("idSociedad"));
				tr.setAgro(rs.getInt("agro"));
				tr.setSueldoBase(rs.getInt("sueldoBase"));
				tr.setJornadaSemanal(rs.getInt("horasSemanales"));
				tr.setCargo(rs.getInt("cts.cargo"));
				tr.setTipoTrabajador(rs.getInt("cts.tipoTrabajador"));
				tr.setJornadaDiaria(rs.getDouble("jornadaDiaria"));
				tr.setSueldoDiario(rs.getDouble("sueldoDiario"));
				tr.setDescripcionTipoTrabajador(rs.getString("descripcionTipoTrabajador"));
				tr.setCalculoJornadaSemanal(rs.getString("calculoJornadaSemanal"));
				trabajador.add(tr);
				count ++;		
			} 
			CU.setTrab(trabajador);
			CU.setRendimiento_general(rendimiento_general);
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error GET_CUADRILLA_TRABAJADOR:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error GET_CUADRILLA_TRABAJADOR:" + e.getMessage());
		} finally {
			db.close();
		}
		return CU;
	}
	public static CUADRILLA GET_REPLICAR_CUADRILLA_SUPERVISOR(int id, String fecha, int cuartel, String tipo, String contratista) throws Exception{
		PreparedStatement ps =  null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		CUADRILLA CU = new CUADRILLA();
		try {
			if(fecha.equals("1969-01-01")){
				sql = "SELECT DISTINCT c.codigo, c.nombre_cuadrilla, c.supervisor, c.fecha_creacion, c.estado, ";
				sql += 	"ct.codigo_cuadrilla, ";
				sql += 	"t.codigo, t.id, t.rut, t.nombre, t.apellidoPaterno, t.agro,";
				sql += 	"rg.codigo_rg, rg.fecha, rg.especie, rg.variedad, rg.cuartel, rg.faena, rg.labor, rg.horas, rg.tipo_pago, rg.valor, rg.codigo_cuadrilla, rg.codigo_supervisor,";
				sql += 	"CASE ";
				sql += 		"WHEN (cts.tipoTrabajador = 1) THEN ";
				sql += 			"(sueldoBase/30) ";
				sql += 		"WHEN (cts.tipoTrabajador = 2) ";
				sql += 			"THEN (sueldoBase/30) ";
				sql += 		"ELSE (sueldoBase/30) ";
				sql += 		"END AS sueldoDiario, ";
				sql += 	"CASE ";
				sql += 		"WHEN (cts.tipoTrabajador = 6) ";
				sql += 			"THEN (horasSemanales/5)";
				sql += 		"WHEN (cts.tipoTrabajador = 7) ";
				sql += 			"THEN (horasSemanales/6) ";
				sql += 		"ELSE (horasSemanales/5) ";
				sql += 		"END AS jornadaDiaria, ";
				sql += 		"(SELECT ";
				sql += 		"CASE ";
				sql += 			"WHEN (cts.tipoTrabajador = 4) ";
				sql += 				"THEN 'Contratista' ";
				sql += 			"ELSE 'Planta' ";
				sql += 		"END) AS descripcionTipoTrabajador, ";
				sql += 	"CASE ";
				sql += 		"WHEN (cts.tipoTrabajador = 6) ";
				sql += 			"THEN (5) ";
				sql += 		"WHEN (cts.tipoTrabajador = 7) ";
				sql += 			"THEN (6) ";
				sql += 		"ELSE (5) ";
				sql += 		"END AS calculoJornadaSemanal, cts.* ";
				sql += "FROM cuadrilla c ";
				sql += "LEFT JOIN cuadrilla_trabajador ct ON c.codigo = ct.codigo_cuadrilla  ";
				sql += "RIGHT JOIN trabajadores t ON ct.rut_trabajador = t.rut ";
				sql += "INNER JOIN contratos cts ON (t.codigo = cts.codigo_trabajador)";
				sql += "LEFT JOIN rendimiento_general rg ON (c.codigo = rg.codigo_cuadrilla)";
				sql += "WHERE c.supervisor = "+id+" AND c.codigo = (SELECT MAX(codigo) FROM cuadrilla WHERE supervisor = "+id+") AND t.agro = 1 ";
				sql += "AND rg.codigo_rg = (SELECT MAX(codigo_rg) FROM rendimiento_general WHERE codigo_cuadrilla != 0) AND ct.asistencia IN (1, 4)";
				if(tipo.equals("masivo")){
					sql += " AND rg.contratista IS NULL";
				}else{
					sql += " AND rg.contratista IS NOT NULL AND rg.contratista = '"+contratista+"'";
				}
			}else{
				sql = "SELECT DISTINCT c.*,ct.codigo_cuadrilla, t.*,rg.*, ";
				sql += 	"CASE ";
				sql += 		"WHEN (cts.tipoTrabajador = 1) THEN ";
				sql += 			"(sueldoBase/30) ";
				sql += 		"WHEN (cts.tipoTrabajador = 2) ";
				sql += 			"THEN (sueldoBase/30) ";
				sql += 		"ELSE (sueldoBase/30) ";
				sql += 		"END AS sueldoDiario, ";
				sql += 	"CASE ";
				sql += 		"WHEN (cts.tipoTrabajador = 6) ";
				sql += 			"THEN (horasSemanales/5)";
				sql += 		"WHEN (cts.tipoTrabajador = 7) ";
				sql += 			"THEN (horasSemanales/6) ";
				sql += 		"ELSE (horasSemanales/5) ";
				sql += 		"END AS jornadaDiaria, ";
				sql += 		"(SELECT ";
				sql += 		"CASE ";
				sql += 			"WHEN (cts.tipoTrabajador = 4) ";
				sql += 				"THEN 'Contratista' ";
				sql += 			"ELSE 'Planta' ";
				sql += 		"END) AS descripcionTipoTrabajador, ";
				sql += 	"CASE ";
				sql += 		"WHEN (cts.tipoTrabajador = 6) ";
				sql += 			"THEN (5) ";
				sql += 		"WHEN (cts.tipoTrabajador = 7) ";
				sql += 			"THEN (6) ";
				sql += 		"ELSE (5) ";
				sql += 		"END AS calculoJornadaSemanal, cts.* ";
				sql += "FROM cuadrilla c ";
				sql += "LEFT JOIN cuadrilla_trabajador ct ON c.codigo = ct.codigo_cuadrilla  ";
				sql += "RIGHT JOIN trabajadores t ON ct.rut_trabajador = t.rut ";
				sql += "INNER JOIN contratos cts ON (t.codigo = cts.codigo_trabajador)";
				sql += "LEFT JOIN rendimiento_general rg ON (c.codigo = rg.codigo_cuadrilla)";
				sql += "WHERE c.supervisor = "+id+" AND rg.fecha = '"+fecha+"' AND t.agro = 1  AND ct.asistencia IN (1, 4)";
				if(tipo.equals("masivo")){
					sql += " AND rg.contratista IS NULL";
				}else{
					sql += " AND rg.contratista AND rg.contratista = '"+contratista+"'";
				}
			}
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			ArrayList<TrabajadoresAgro> trabajador = new ArrayList<TrabajadoresAgro>();
			ArrayList<RENDIMIENTO_GENERAL> rendimiento_general = new ArrayList<RENDIMIENTO_GENERAL>();
			int count = 1;
			while (rs.next()) {
				TrabajadoresAgro tr = new TrabajadoresAgro();
				RENDIMIENTO_GENERAL rg = new RENDIMIENTO_GENERAL();
				if (count == 1){
					CU.setCodigo(rs.getInt("codigo"));	
					CU.setNombre_cuadrilla(rs.getString("nombre_cuadrilla"));
					CU.setSupervisor(rs.getInt("supervisor"));
					CU.setFecha_creacion(rs.getString("fecha_creacion"));
					CU.setEstado(rs.getInt("estado"));
					rg.setCodigo(rs.getInt("codigo_rg"));
					rg.setFecha(rs.getString("fecha"));
					rg.setEspecie(rs.getInt("especie"));
					rg.setVariedad(rs.getInt("variedad"));
					rg.setCuartel(rs.getInt("cuartel"));
					rg.setFaena(rs.getInt("faena"));
					rg.setLabor(rs.getInt("labor"));
					rg.setHoras(rs.getFloat("horas"));
					rg.setTipo_pago(rs.getInt("tipo_pago"));
					rg.setValor(rs.getInt("valor"));
					rg.setCodigo_cuadrilla(rs.getInt("codigo_cuadrilla"));
					rg.setCodigo_supervisor(rs.getInt("codigo_supervisor"));
					rendimiento_general.add(rg);
				}
				tr.setCodigo(rs.getString("codigo"));
				tr.setIdTrabajador(rs.getInt("id"));
				tr.setRut(rs.getString("rut"));
				tr.setNombre(rs.getString("nombre")+" "+rs.getString("apellidoPaterno"));
				tr.setApellidoPaterno(rs.getString("apellidoPaterno"));
				tr.setIdSociedad(rs.getInt("idSociedad"));
				tr.setAgro(rs.getInt("agro"));
				tr.setSueldoBase(rs.getInt("sueldoBase"));
				tr.setJornadaSemanal(rs.getInt("horasSemanales"));
				tr.setCargo(rs.getInt("cts.cargo"));
				tr.setTipoTrabajador(rs.getInt("cts.tipoTrabajador"));
				tr.setJornadaDiaria(rs.getDouble("jornadaDiaria"));
				tr.setSueldoDiario(rs.getDouble("sueldoDiario"));
				tr.setDescripcionTipoTrabajador(rs.getString("descripcionTipoTrabajador"));
				tr.setCalculoJornadaSemanal(rs.getString("calculoJornadaSemanal"));
				trabajador.add(tr);
				count ++;		
			} 
			CU.setTrab(trabajador);
			CU.setRendimiento_general(rendimiento_general);
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error GET_CUADRILLA_TRABAJADOR:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error GET_CUADRILLA_TRABAJADOR:" + e.getMessage());
		} finally {
			db.close();
		}
		return CU;
	}
	public static boolean ADD_CUADRILLA_REPLICADA (CUADRILLA c)throws Exception{
		ConnectionDB db = new ConnectionDB();
		try {
			String fechaRG = "";
			int supervisor = 0;
			ArrayList<RENDIMIENTO_GENERAL> rg = c.getRendimiento_general();
			for(RENDIMIENTO_GENERAL rgs: rg){
				fechaRG = rgs.getFecha();
				supervisor = rgs.getCodigo_supervisor();
			}
			CallableStatement cs = db.conn.prepareCall("{call sa_crearCuadrilla(?, ?, ?, ?)}");
			cs.setString(1, fechaRG);
			cs.setInt(2, supervisor);
			cs.setString(3, c.getNombre_cuadrilla());
			cs.setString(4, c.getFecha_creacion());
			cs.execute();
			
			for(TrabajadoresAgro trab: c.getTrab()){
				inCuadrilla(trab.getRut(), trab.getIdSociedad(), trab.getCargo(), trab.getDescripcionTipoTrabajador());
			}
			return true;
		} catch (SQLException e) {
			System.out.println("Error ADD_CUADRILLA:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error ADD_CUADRILLA:" + e.getMessage());
		} finally {
			db.close();
		}
		return false;
	}
	public static CUADRILLA GET_REND_MASIVO(int codigo_rg) throws Exception{
		PreparedStatement ps =  null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		CUADRILLA CU = new CUADRILLA();
		try {
			sql = 	"SELECT c.*,ct.codigo_cuadrilla, t.*, rg.*, rg.estado as estado_rg, rd.*, rd.codigo AS codigo_rd, rd.codigo_rg AS codigo_rg_rd, rd.labor AS labor_rd, rd.valor AS valor_rd, rd.estado AS estado_rd, rd.cuartel AS cuartel_rd, ";
			sql += 	"GETLIQUIDO(cts.codigo_trabajador, cts.id,";
			sql += 		"DATE_FORMAT(rg.fecha, '%Y%m'), 0) AS sueldoDiario,";
			sql += 	"GETHORASEXTRAS(rg.fecha, t.codigo) AS hx_semana,";
			sql += 	"CASE ";
			sql += 		"WHEN (cts.tipoTrabajador = 6) ";
			sql += 			"THEN (horasSemanales/5)";
			sql += 		"WHEN (cts.tipoTrabajador = 7) ";
			sql += 			"THEN (horasSemanales/6) ";
			sql += 		"ELSE (horasSemanales/5) ";
			sql += 		"END AS jornadaDiaria, ";
			sql += 	"CASE ";
			sql += 		"WHEN (cts.tipoTrabajador = 4) ";
			sql += 			"THEN 'Contratista' ";
			sql += 		"ELSE 'Planta' ";
			sql += 	"END AS descripcionTipoTrabajador, ";
			sql += 	"CASE ";
			sql += 		"WHEN (cts.tipoTrabajador = 6) ";
			sql += 			"THEN (5) ";
			sql += 		"WHEN (cts.tipoTrabajador = 7) ";
			sql += 			"THEN (6) ";
			sql += 		"ELSE (5) ";
			sql += 		"END AS calculoJornadaSemanal, cts.*, ";
			sql += 	"cam.descripcion, e.codigo, v.codigo, ";
			sql += 	"CONCAT(t.apellidoPaterno, ' ', t.apellidoMaterno, ' ', t.nombre) AS nombreTrabajador, ";
			sql += 	"CONCAT(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ', tr.nombre) AS nsupervisor, ";
			sql += 	"case when(rd.ceco is null)then '' else rd.ceco end as cecoRd ";
			sql += 	"FROM cuadrilla c ";
			sql += 		"LEFT JOIN cuadrilla_trabajador ct ON (c.codigo = ct.codigo_cuadrilla) ";
			sql += 		"RIGHT JOIN trabajadores t ON (ct.rut_trabajador = t.rut OR ct.rut_trabajador = t.rutTemporal) ";
			sql += 		"INNER JOIN contratos cts ON (t.codigo = cts.codigo_trabajador)";
			sql += 		"LEFT JOIN rendimiento_general rg ON (c.codigo = rg.codigo_cuadrilla) ";
			sql += 		"LEFT JOIN rendimiento_diario rd ON(rg.codigo_rg = rd.codigo_rg and rd.trabajador = t.id) ";
			sql += 		"LEFT JOIN trabajadores tr ON(tr.id = rg.codigo_supervisor)";
			sql += 		"LEFT JOIN cuartel cl ON(cl.codigo = rd.cuartel) ";
			sql += 		"LEFT JOIN especie e ON(e.codigo = cl.especie) ";
			sql += 		"LEFT JOIN variedad v ON(v.codigo = cl.variedad) ";
			sql += 		"LEFT JOIN campo cam ON(rg.campo = cam.campo) ";
			sql += 	"WHERE rg.codigo_rg = "+codigo_rg+"  ";
			sql += 		"AND ct.asistencia IN(1,5) ";
			sql += 		"AND cts.fechaInicio_actividad <= rg.fecha ";
			sql += 		"AND (cts.FechaTerminoContrato >= rg.fecha OR cts.FechaTerminoContrato IS NULL) ";
			sql += 	"ORDER BY nombreTrabajador;";
//			if(tipo.equals("masivo")){
//				sql += " AND rg.contratista IS NULL";
//			}else{
//				sql += " AND rg.contratista IS NOT NULL";
//			}
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			ArrayList<TrabajadoresAgro> trabajador = new ArrayList<TrabajadoresAgro>();
			ArrayList<RENDIMIENTO_GENERAL> rendimiento_general = new ArrayList<RENDIMIENTO_GENERAL>();
			ArrayList<RENDIMIENTO_DIARIO> rendimiento_diario = new ArrayList<RENDIMIENTO_DIARIO>();
			int count = 1;
			while (rs.next()) {
				TrabajadoresAgro tr = new TrabajadoresAgro();
				RENDIMIENTO_GENERAL rg = new RENDIMIENTO_GENERAL();
				RENDIMIENTO_DIARIO rd = new RENDIMIENTO_DIARIO();
				if (count == 1){
					CU.setCodigo(rs.getInt("codigo"));	
					CU.setNombre_cuadrilla(rs.getString("nombre_cuadrilla"));
					CU.setSupervisor(rs.getInt("supervisor"));
					CU.setFecha_creacion(rs.getString("fecha_creacion"));
					CU.setEstado(rs.getInt("estado"));
					
					rg.setCodigo(rs.getInt("codigo_rg"));
					rg.setFecha(rs.getString("fecha"));
					rg.setEspecie(rs.getInt("especie"));
					rg.setVariedad(rs.getInt("variedad"));
					rg.setCuartel(rs.getInt("cuartel"));
					rg.setFaena(rs.getInt("faena"));
					rg.setLabor(rs.getInt("labor"));
					rg.setHoras(rs.getFloat("horas"));
					rg.setTipo_pago(rs.getInt("tipo_pago"));
					rg.setValor(rs.getInt("valor"));
					rg.setBase_piso_dia(rs.getInt("base_piso_dia"));
					rg.setCodigo_cuadrilla(rs.getInt("codigo_cuadrilla"));
					rg.setCodigo_supervisor(rs.getInt("codigo_supervisor"));
					rg.setEstado(rs.getInt("estado_rg"));
					float valorHora = rg.getBase_piso_dia()/rg.getHoras();
					rg.setValor_x_hora(valorHora);
					rg.setContratista(rs.getString("contratista"));
					rg.setMacro(rs.getString("rg.macro"));
					rg.setCeco(rs.getString("rg.ceco"));
					rg.setOrdenco(rs.getString("rg.ordenco"));
					rg.setCampo(rs.getString("cam.descripcion"));
					rg.setNsupervisor(rs.getString("nsupervisor"));
					rendimiento_general.add(rg);
				}
				rd.setCodigo(rs.getInt("codigo_rd"));
				rd.setTrabajador(rs.getInt("trabajador"));
				rd.setBase_piso_hora(rs.getFloat("base_piso_hora"));
				rd.setSubsidio(rs.getInt("subsidio"));
				rd.setCuartel(rs.getInt("cuartel_rd"));
				rd.setLabor(rs.getInt("labor_rd"));
				rd.setValor(rs.getInt("valor_rd"));
				rd.setTipo_trato(rs.getInt("tipo_trato"));
				rd.setRendimiento(rs.getFloat("rendimiento"));
				rd.setHoras_trabajadas(rs.getFloat("horas_trabajadas"));
				rd.setHoras_extras(rs.getFloat("horas_extras"));
				rd.setBono1(rs.getInt("bono1"));
				rd.setBono2(rs.getInt("bono2"));
				rd.setValor_liquido(rs.getFloat("valor_liquido"));
				rd.setMaquinaria(rs.getInt("maquinaria"));
				rd.setImplemento(rs.getInt("implemento"));
				rd.setBus(rs.getInt("bus"));
				rd.setEstado(rs.getInt("estado_rd"));
				rd.setCodigo_rg(rs.getInt("codigo_rg_rd"));
				rd.setValor_hx(rs.getFloat("valor_hx"));
				rd.setMonto_hx(rs.getFloat("monto_hx"));
				rd.setValor_hx_dos(rs.getFloat("valor_hx_dos"));
				rd.setHx_dos(rs.getFloat("hx_dos"));
				rd.setMacroco(rs.getString("rd.macroco"));
				System.out.println(rd.getCeco());
				if(rs.getString("cecoRd").equals("")){
					rd.setCeco("'"+rs.getString("cecoRd")+"'");
				}else{
					rd.setCeco("'"+rs.getString("cecoRd")+"'");
				}
//				if(rd.getCeco() == null || rd.getCeco().equals("")){
//					rd.setCeco("''");
//				}
				rd.setEspecie(rs.getInt("e.codigo"));
				rd.setVariedad(rs.getInt("v.codigo"));
				rd.setOrdenco(rs.getString("rd.ordenco"));
				rd.setRes_hx(rs.getInt("res_hx"));
				
				int sueldo = rs.getInt("sueldoDiario");
				
				tr.setCodigo(rs.getString("codigo"));
				tr.setIdTrabajador(rs.getInt("id"));
				tr.setRut(rs.getString("rut"));
				if(tr.getRut().equals("")){
					tr.setRut(rs.getString("rutTemporal"));
				}
				tr.setHx((sueldo*0.0077777));
				tr.setNombre(rs.getString("nombreTrabajador"));
				tr.setApellidoPaterno(rs.getString("apellidoPaterno"));
				tr.setIdSociedad(rs.getInt("idSociedad"));
				tr.setAgro(rs.getInt("agro"));
				tr.setSueldoBase(rs.getInt("sueldoBase"));
				tr.setJornadaSemanal(rs.getInt("horasSemanales"));
				tr.setCargo(rs.getInt("cts.cargo"));
				tr.setTipoTrabajador(rs.getInt("cts.tipoTrabajador"));
				tr.setJornadaDiaria(rs.getDouble("jornadaDiaria"));
				tr.setSueldoDiario(rs.getDouble("sueldoDiario"));
				tr.setDescripcionTipoTrabajador(rs.getString("descripcionTipoTrabajador"));
				tr.setCalculoJornadaSemanal(rs.getString("calculoJornadaSemanal"));
				tr.setIdContratista(rs.getString("idContratista"));
//				if(return_horas_restantes(fecha, rs.getInt("id")) == 100){
//					tr.setHoras_restantes(rs.getDouble("jornadaDiaria"));
//				}else{
//					tr.setHoras_restantes(return_horas_restantes(fecha, rs.getInt("id")));
//				}
				tr.setHx_semana(rs.getFloat("hx_semana"));
				trabajador.add(tr);
				rendimiento_diario.add(rd);
				count ++;		
			} 
			CU.setTrab(trabajador);
			CU.setRendimiento_general(rendimiento_general);
			CU.setRd(rendimiento_diario);
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error GET_CUADRILLA_TRABAJADOR_FECHA:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error GET_CUADRILLA_TRABAJADOR_FECHA:" + e.getMessage());
		} finally {
			db.close();
		}
		return CU;
	}
	public static String JSON_RENDIMIENTO_GENERAL(int codigo_rg) throws Exception{
		PreparedStatement ps =  null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		JSONObject json = new JSONObject();
		try {
			sql = 	"SELECT c.*,ct.codigo_cuadrilla, t.*, rg.*, rg.estado as estado_rg, rd.*, rd.codigo AS codigo_rd, rd.codigo_rg AS codigo_rg_rd, rd.labor AS labor_rd, rd.valor AS valor_rd, rd.estado AS estado_rd, rd.cuartel AS cuartel_rd, ";
			sql += 	"GETLIQUIDO(cts.codigo_trabajador, cts.id,";
			sql += 		"DATE_FORMAT(rg.fecha, '%Y%m'), 0) AS sueldoDiario,";
			sql += 	"GETHORASEXTRAS(rg.fecha, t.codigo) AS hx_semana,";
			sql += 	"CASE ";
			sql += 		"WHEN (cts.tipoTrabajador = 6) ";
			sql += 			"THEN (horasSemanales/5)";
			sql += 		"WHEN (cts.tipoTrabajador = 7) ";
			sql += 			"THEN (horasSemanales/6) ";
			sql += 		"ELSE (horasSemanales/5) ";
			sql += 		"END AS jornadaDiaria, ";
			sql += 	"CASE ";
			sql += 		"WHEN (cts.tipoTrabajador = 4) ";
			sql += 			"THEN 'Contratista' ";
			sql += 		"ELSE 'Planta' ";
			sql += 	"END AS descripcionTipoTrabajador, ";
			sql += 	"CASE ";
			sql += 		"WHEN (cts.tipoTrabajador = 6) ";
			sql += 			"THEN (5) ";
			sql += 		"WHEN (cts.tipoTrabajador = 7) ";
			sql += 			"THEN (6) ";
			sql += 		"ELSE (5) ";
			sql += 		"END AS calculoJornadaSemanal, cts.*, ";
			sql += 	"cam.descripcion, e.codigo, v.codigo, ";
			sql += 	"CONCAT(t.apellidoPaterno, ' ', t.apellidoMaterno, ' ', t.nombre) AS nombreTrabajador, ";
			sql += 	"CONCAT(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ', tr.nombre) AS nsupervisor, ";
			sql += 	"case when(rd.ceco is null)then '' else rd.ceco end as cecoRd, rg.base_piso_dia/rg.horas AS valorHora ";
			sql += 	"FROM cuadrilla c ";
			sql += 		"LEFT JOIN cuadrilla_trabajador ct ON (c.codigo = ct.codigo_cuadrilla) ";
			sql += 		"RIGHT JOIN trabajadores t ON (ct.rut_trabajador = t.rut OR ct.rut_trabajador = t.rutTemporal) ";
			sql += 		"INNER JOIN contratos cts ON (t.codigo = cts.codigo_trabajador)";
			sql += 		"LEFT JOIN rendimiento_general rg ON (c.codigo = rg.codigo_cuadrilla) ";
			sql += 		"LEFT JOIN rendimiento_diario rd ON(rg.codigo_rg = rd.codigo_rg and rd.trabajador = t.id) ";
			sql += 		"LEFT JOIN trabajadores tr ON(tr.id = rg.codigo_supervisor)";
			sql += 		"LEFT JOIN cuartel cl ON(cl.codigo = rd.cuartel) ";
			sql += 		"LEFT JOIN especie e ON(e.codigo = cl.especie) ";
			sql += 		"LEFT JOIN variedad v ON(v.codigo = cl.variedad) ";
			sql += 		"LEFT JOIN campo cam ON(rg.campo = cam.campo) ";
			sql += 	"WHERE rg.codigo_rg = "+codigo_rg+"  ";
			sql += 		"AND ct.asistencia IN(1,5) ";
			sql += 		"AND cts.fechaInicio_actividad <= rg.fecha ";
			sql += 		"AND (cts.FechaTerminoContrato >= rg.fecha OR cts.FechaTerminoContrato IS NULL) ";
			sql += 	"ORDER BY nombreTrabajador;";
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			JSONArray arrRG = new JSONArray();
			JSONArray arrRD = new JSONArray();
			JSONArray arrTR = new JSONArray();
			
			int count = 1;
			while (rs.next()) {
				JSONObject rg = new JSONObject();
				JSONObject rd = new JSONObject();
				JSONObject tr = new JSONObject();
				if (count == 1){
					
					json.put("codigo", rs.getInt("codigo"));
					json.put("nombre_cuadrilla", rs.getString("nombre_cuadrilla"));
					json.put("supervisor", rs.getInt("supervisor"));
					json.put("fecha_creacion", rs.getString("fecha_creacion"));
					json.put("estado", rs.getInt("estado"));
					
					rg.put("codigo", rs.getInt("codigo_rg"));
					rg.put("fecha", rs.getString("fecha"));
					rg.put("especie", rs.getInt("especie"));
					rg.put("variedad", rs.getInt("variedad"));
					rg.put("cuartel", rs.getInt("cuartel"));
					rg.put("faena", rs.getInt("faena"));
					rg.put("labor", rs.getInt("labor"));
					rg.put("tipo_pago", rs.getFloat("tipo_pago"));
					rg.put("valor", rs.getInt("valor"));
					rg.put("horas", rs.getInt("horas"));
					rg.put("base_piso_dia", rs.getInt("base_piso_dia"));
					rg.put("codigo_cuadrilla", rs.getInt("codigo_cuadrilla"));
					rg.put("codigo_supervisor", rs.getInt("codigo_supervisor"));
					rg.put("estado", rs.getInt("estado_rg"));
					rg.put("valor_x_hora", rs.getFloat("valorHora"));
					rg.put("contratista", rs.getString("contratista"));
					rg.put("macro", rs.getString("rg.macro"));
					rg.put("ordenco", rs.getString("ordenco"));
					rg.put("campo", rs.getString("cam.descripcion"));
					rg.put("nsupervisor", rs.getString("nsupervisor"));
					arrRG.put(rg);
//					float valorHora = rg.getBase_piso_dia()/rg.getHoras();
				}
				rd.put("codigo", rs.getInt("codigo_rd"));
				rd.put("trabajador", rs.getInt("trabajador"));
				rd.put("base_piso_hora", rs.getFloat("base_piso_hora"));
				rd.put("subsidio", rs.getInt("subsidio"));
				rd.put("cuartel", rs.getInt("cuartel_rd"));
				rd.put("labor", rs.getInt("labor_rd"));
				rd.put("valor", rs.getInt("valor_rd"));
				rd.put("tipo_trato", rs.getInt("tipo_trato"));
				rd.put("rendimiento", rs.getFloat("rendimiento"));
				rd.put("horas_trabajadas", rs.getFloat("horas_trabajadas"));
				rd.put("horas_extras", rs.getFloat("horas_extras"));
				rd.put("bono1", rs.getInt("bono1"));
				rd.put("bono2", rs.getInt("bono2"));
				rd.put("valor_liquido", rs.getFloat("valor_liquido"));
				rd.put("maquinaria", rs.getInt("maquinaria"));
				rd.put("implemento", rs.getInt("implemento"));
				rd.put("bus", rs.getInt("bus"));
				rd.put("estado", rs.getInt("estado_rd"));
				rd.put("codigo_rg", rs.getInt("codigo_rg_rd"));
				rd.put("valor_hx", rs.getInt("valor_hx"));
				rd.put("monto_hx", rs.getInt("monto_hx"));
				rd.put("valor_hx_dos", rs.getInt("valor_hx_dos"));
				rd.put("hx_dos", rs.getInt("hx_dos"));
				rd.put("macroco", rs.getInt("rd.macroco"));
				rd.put("ceco", rs.getString("cecoRd"));
				rd.put("especie", rs.getInt("e.codigo"));
				rd.put("variedad", rs.getInt("v.codigo"));
				rd.put("ordenco", rs.getString("rd.ordenco"));
				rd.put("res_hx", rs.getInt("res_hx"));
				
				int sueldo = rs.getInt("sueldoDiario");
				tr.put("codigo", rs.getString("codigo"));
				tr.put("idTrabajador", rs.getInt("id"));
				tr.put("rut", rs.getString("rut"));
				tr.put("hx", sueldo);
				tr.put("nombre", rs.getString("nombreTrabajador"));
				tr.put("apellidoPaterno", rs.getString("apellidoPaterno"));
				tr.put("idSociedad", rs.getInt("idSociedad"));
				tr.put("agro", rs.getInt("agro"));
				tr.put("sueldoBase", rs.getInt("sueldoBase"));
				tr.put("jornadaSemanal", rs.getInt("horasSemanales"));
				tr.put("cargo", rs.getString("cts.cargo"));
				tr.put("tipoTrabajador", rs.getString("cts.tipoTrabajador"));
				tr.put("jornadaDiaria", rs.getDouble("jornadaDiaria"));
				tr.put("sueldoDiario", rs.getDouble("sueldoDiario"));
				tr.put("descripcionTipoTrabajador", rs.getString("descripcionTipoTrabajador"));
				tr.put("calculoJornadaSemanal", rs.getString("calculoJornadaSemanal"));
				tr.put("idContratista", rs.getString("idContratista"));
				tr.put("hx_semana", rs.getFloat("hx_semana"));
				arrRD.put(rd);
				arrTR.put(tr);
				count ++;		
			} 
			json.put("rendimiento_general", arrRG);
			json.put("trab", arrTR);
			json.put("rd", arrRD);
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error GET_CUADRILLA_TRABAJADOR_FECHA:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error GET_CUADRILLA_TRABAJADOR_FECHA:" + e.getMessage());
		} finally {
			db.close();
		}
		return json.toString();
	}
}

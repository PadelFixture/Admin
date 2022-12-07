package lib.db.SASW;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.json.JSONObject;

import lib.ClassSASW.parametros;
import lib.SADB.cuadrilla;
import lib.classSA.TrabajadoresAgro;
import lib.db.ConnectionDB;
import lib.security.session;

public class TrabajadoresAgroDB {

	
	public static ArrayList<TrabajadoresAgro> getTrabajadoresAgro (String trabajadorAgricola, String campo, String rut, String cargo, String fecha, String contratista) throws Exception{
		
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<TrabajadoresAgro> lista = new ArrayList<TrabajadoresAgro>();
		ConnectionDB db = new ConnectionDB();
		
		try {
			

			sql +=	"SELECT DISTINCT ";
			sql +=		"GETLIQUIDO(ct.codigo_trabajador, ct.id, DATE_FORMAT('"+fecha+"', \"%Y%m\"),0) as sueldoDiario, ";
			sql +=		"CONCAT(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ', tr.nombre) AS nombreTrabajador, ";
			sql +=		"CASE ";
			sql +=			"WHEN(ct.tipoTrabajador = 6) THEN (horasSemanales / 5) ";
			sql +=			"WHEN (ct.tipoTrabajador = 7) THEN (horasSemanales/6) ";
			sql +=		"END AS jornadaDiaria, ";
			sql +=		"CASE ";
			sql +=			"WHEN (ct.tipoTrabajador = 4) THEN 'Contratista' ";
			sql +=			"ELSE 'Planta' ";
			sql +=		"END AS descripcionTipoTrabajador, ";
			sql +=		"CASE ";
			sql +=			"WHEN (ct.tipoTrabajador = 6) THEN (5) ";
			sql +=			"WHEN (ct.tipoTrabajador = 7) THEN (6) ";
			sql +=			"ELSE (5) ";
			sql +=		"END AS calculoJornadaSemanal, ";
			sql +=		"tr.*, ct.codigo_trabajador, ct.cargo, ct.tipoTrabajador, ct.sueldoBase, ct.horasSemanales ";
			sql +=		"FROM trabajadores tr ";
			sql +=			"INNER JOIN contratos ct ON (tr.codigo = ct.codigo_trabajador) ";
			sql +=			"LEFT JOIN campo cam on cam.campo = tr.idHuerto ";
			String rutFormateado = FormatearRUT(rut.replace("-", ""));
			sql +=		"WHERE (tr.rut = '"+rutFormateado+"' OR tr.rutTemporal = '"+rutFormateado+"') ";
//			if((campo != null) && !"*".equals(campo) ) {
//				sql += " AND cam.codigo = '"+campo+"' ";
//			}
//			if((rut != null) && !"*".equals(rut)) {	
//				String rutFormateado = FormatearRUT(rut.replace("-", ""));
//				sql += " AND tr.rut = '"+rutFormateado+"' OR tr.rutTemporal = '"+rutFormateado+"'";
//			}	
//			if((cargo != null) && !"*".equals(cargo)) {	
//				sql += " AND ct.cargo = '"+cargo+"' ";
//			}
//			if((contratista != null) && !"*".equals(contratista)) {	
//				sql += " AND tr.idContratista = "+contratista+"";				
//			} else {
//				sql += " AND (tr.idContratista = '' or tr.idContratista is null)";
//			}
			ps = db.conn.prepareStatement(sql);

			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				int sueldo = rs.getInt("jornadaDiaria");
				
				TrabajadoresAgro tr = new TrabajadoresAgro();
				tr.setHx((sueldo/9)*1.5);
				tr.setCodigo(rs.getString("codigo"));
				tr.setIdTrabajador(rs.getInt("id"));
				tr.setRut(rs.getString("rut"));
				if(tr.getRut().equals("")){
					tr.setRut(rs.getString("rutTemporal"));
				}
				tr.setNombre(rs.getString("nombreTrabajador"));
				tr.setApellidoPaterno(rs.getString("apellidoPaterno"));
				tr.setIdSociedad(0);
				tr.setAgro(rs.getInt("agro"));
				tr.setSueldoBase(rs.getInt("sueldoBase"));
				tr.setJornadaSemanal(rs.getInt("horasSemanales"));
				tr.setCargo(rs.getInt("ct.cargo"));
				tr.setTipoTrabajador(rs.getInt("ct.tipoTrabajador"));
				tr.setJornadaDiaria(rs.getDouble("jornadaDiaria"));
				tr.setSueldoDiario(rs.getDouble("sueldoDiario"));
				tr.setDescripcionTipoTrabajador(rs.getString("descripcionTipoTrabajador"));
				tr.setCalculoJornadaSemanal(rs.getString("calculoJornadaSemanal"));
				tr.setIdContratista(rs.getString("idContratista"));
				
				if(cuadrilla.return_horas_restantes(fecha, rs.getInt("id")) == 100){
					tr.setHoras_restantes(rs.getDouble("jornadaDiaria"));
				}else{
					tr.setHoras_restantes(cuadrilla.return_horas_restantes(fecha, rs.getInt("id")));
				}
				
				lista.add(tr);
					
			}
			rs.close();
			ps.close();
			db.conn.close();
		
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
	
public static ArrayList<TrabajadoresAgro> getTrabajadoresAgro2 (String trabajadorAgricola, String campo, String rut, String cargo, String fecha, String contratista,int digitador) throws Exception{
		
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<TrabajadoresAgro> lista = new ArrayList<TrabajadoresAgro>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql +=	"SELECT DISTINCT ";
			sql +=		"CONCAT(tr.codigo, ' | ', tr.rut, ' | ',  tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ', tr.nombre) AS nombreTrabajador, ";
			sql +=		"CASE ";
			sql +=			"WHEN(ct.tipoTrabajador = 6) THEN (horasSemanales / 5) ";
			sql +=			"WHEN (ct.tipoTrabajador = 7) THEN (horasSemanales/6) ";
			sql +=		"END AS jornadaDiaria, ";
			sql +=		"CASE ";
			sql +=			"WHEN (ct.tipoTrabajador = 4) THEN 'Contratista' ";
			sql +=			"ELSE 'Planta' ";
			sql +=		"END AS descripcionTipoTrabajador, ";
			sql +=		"CASE ";
			sql +=			"WHEN (ct.tipoTrabajador = 6) THEN (5) ";
			sql +=			"WHEN (ct.tipoTrabajador = 7) THEN (6) ";
			sql +=			"ELSE (5) ";
			sql +=		"END AS calculoJornadaSemanal, ";
			sql +=		"tr.id,tr.rut,tr.rutTemporal,tr.agro,tr.apellidoPaterno,tr.codigo,tr.idContratista,ct.codigo_trabajador,ct.cargo,ct.tipoTrabajador,ct.sueldoBase,ct.horasSemanales, t.horas_restantes ";
			sql +=	"FROM trabajadores tr ";
			sql +=		"INNER JOIN contratos ct ON (tr.codigo = ct.codigo_trabajador) ";
			sql +=		"LEFT JOIN (SELECT DISTINCT ";
			sql +=				"rd.trabajador, ";
			sql +=				"CASE (cts.tipoTrabajador) ";
			sql +=					"WHEN (6) THEN (horasSemanales / 5) ";
			sql +=					"WHEN (7) THEN (horasSemanales / 6) ";
			sql +=					"ELSE (horasSemanales / 5) ";
			sql +=				"END AS jornadaDiaria, ";
			sql +=				"(SELECT ";
			sql +=					"CASE ";
			sql +=						"WHEN (rd.horas_trabajadas IS NULL) THEN (jornadaDiaria) ";
			sql +=						"ELSE jornadaDiaria - rd.horas_trabajadas ";
			sql +=					"END) AS horas_restantes ";
			sql +=			"FROM ";
			sql +=				"rendimiento_diario rd ";
			sql +=				"RIGHT JOIN trabajadores t ON rd.trabajador = t.id ";
			sql +=				"INNER JOIN contratos cts ON (t.codigo = cts.codigo_trabajador) ";
			sql +=				"INNER JOIN rendimiento_general rg on(rd.codigo_rg = rg.codigo_rg) ";
			sql +=			"WHERE ";
			sql +=				"rg.fecha = '"+fecha+"') t ON(t.trabajador = tr.id) ";
			sql +=	"WHERE ";
			sql +=		"ct.fechaInicio_actividad <= '"+fecha+"' ";
			sql +=		"AND (ct.FechaTerminoContrato >= '"+fecha+"'  OR ct.FechaTerminoContrato IS NULL) ";
			if(!cargo.equals("8")){
				sql += "AND tr.agro = 1 ";
//				if((contratista == null) || "*".equals(contratista)){
//					sql += "AND ct.periodo = DATE_FORMAT('"+fecha+"', '%Y%m') ";
//					sql +=	"AND tr.periodo = DATE_FORMAT('"+fecha+"', '%Y%m') ";
//					sql +=	"AND ct.fechaInicio_actividad <= '"+fecha+"' ";
//					sql +=	"AND (ct.FechaTerminoContrato >= '"+fecha+"' OR ct.FechaTerminoContrato IS NULL) ";
//				}
			}
			System.out.println(campo);
			if(digitador== 0){
				if( campo != null && !campo.equals("*") ) {
					sql += " AND (cam.codigo = '"+campo+"' || cam.campo = '"+campo+"') ";
				}
			} else {
				if( campo != null && !campo.equals("*") ) {
//					sql += " AND cam.sociedad =  (select sociedad from campo where (codigo = '"+campo+"' || campo = '"+campo+"')) ";
				}
				sql += " AND tr.idHuerto IN ( select codigo_campo FROM usuario_campo where codigo_usuario = '"+digitador+"') ";
			}
			if((rut != null) && !"*".equals(rut)) {	
				String rutFormateado = FormatearRUT(rut.replace("-", ""));
				sql += " AND tr.rut = '"+rutFormateado+"' OR tr.rutTemporal = '"+rutFormateado+"'";
			}	
			if((cargo != null) && !"*".equals(cargo)) {	
				if(cargo.equals("8")) {
					sql += " and  ct.supervisor = 1 ";					
				} else {
					if(cargo.equals("7")) {
						sql += " and  ct.maquinista = 1 ";					
					} else {
						sql += " AND ct.cargo = '"+cargo+"' ";
					}
				}
			}
			if(!"-1".equals(contratista)) {
				if((contratista != null) && !"*".equals(contratista)) {	
					sql += " AND tr.idContratista = '"+contratista+"' ";				
				} else {
					sql += " AND (tr.idContratista = '' or tr.idContratista is null)";
				}
			}
			sql += "ORDER BY tr.apellidoPaterno;";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			if(cargo.equals("7")){
				TrabajadoresAgro e = new TrabajadoresAgro();
				
				e.setCodigo("0");
				e.setIdTrabajador(0);
				e.setRut("00.000.000-0");
				e.setNombre("Operador Externo");
				lista.add(e);
			}
			while(rs.next()){
				TrabajadoresAgro tr = new TrabajadoresAgro();
				
				tr.setCodigo(rs.getString("codigo"));
				tr.setIdTrabajador(rs.getInt("id"));
				tr.setRut(rs.getString("rut"));
				if(tr.getRut().equals("")){
					tr.setRut(rs.getString("rutTemporal"));
				}
				tr.setNombre(rs.getString("nombreTrabajador"));
				tr.setApellidoPaterno(rs.getString("apellidoPaterno"));
				tr.setIdSociedad(0);
				tr.setAgro(rs.getInt("agro"));
				tr.setSueldoBase(rs.getInt("sueldoBase"));
				tr.setJornadaSemanal(rs.getInt("horasSemanales"));
				tr.setCargo(rs.getInt("ct.cargo"));
				tr.setTipoTrabajador(rs.getInt("ct.tipoTrabajador"));
				tr.setJornadaDiaria(rs.getDouble("jornadaDiaria"));
				//tr.setSueldoDiario(rs.getDouble("sueldoDiario"));
				tr.setDescripcionTipoTrabajador(rs.getString("descripcionTipoTrabajador"));
				tr.setCalculoJornadaSemanal(rs.getString("calculoJornadaSemanal"));
				tr.setIdContratista(rs.getString("idContratista"));
				tr.setHoras_restantes(rs.getDouble("horas_restantes"));
				lista.add(tr);
					
			}
			rs.close();
			ps.close();
			db.conn.close();
		
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
	
	
	
	
	public static String FormatearRUT(String rut) {
        int cont = 0;
        String format;
        rut = rut.replace(".", "");
        rut = rut.replace("-", "");
        format = "-" + rut.substring(rut.length() - 1);
        for (int i = rut.length() - 2; i >= 0; i--) {
            format = rut.substring(i, i + 1) + format;
            cont++;
            if (cont == 3 && i != 0) {
                format = "." + format;
                cont = 0;
            }
        }
        return format;
	}
	public static ArrayList<parametros> GET_CARGOS() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<parametros> data = new ArrayList<parametros>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT * FROM cargos";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				parametros e = new parametros();
				e.setId(rs.getInt("id_cargo"));
				e.setCodigo(rs.getString("cargos"));
				e.setDescripcion(rs.getString("sueldoBase"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error getCAMPO: "+ex.getMessage());
		}
		finally{
			ps.close();
			db.close();
		}
		return data;
	}
	public static ArrayList<TrabajadoresAgro> getDetalleTrabajadorAgro (String trabajadorAgricola, String campo, int codigo, String cargo, String fecha, String contratista) throws Exception{
		
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<TrabajadoresAgro> lista = new ArrayList<TrabajadoresAgro>();
		ConnectionDB db = new ConnectionDB();
		
		try {
			sql = 	"SELECT DISTINCT ";
			sql += 		"GETLIQUIDO(ct.codigo_trabajador, ct.id, DATE_FORMAT('"+fecha+"', '%Y%m'),0) AS sueldoDiario, ";
			sql +=		"CONCAT(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ', tr.nombre) AS nombreTrabajador, ";
			sql += 		"GETHORASEXTRAS('"+fecha+"', "+codigo+") AS hx_semana, ";
			sql += 		"CASE ";
			sql += 			"WHEN (ct.tipoTrabajador = 6) THEN (horasSemanales / 5) ";
			sql += 			"WHEN (ct.tipoTrabajador = 7) THEN (horasSemanales / 6) ";
			sql += 			"ELSE (horasSemanales / 5) ";
			sql += 		"END AS jornadaDiaria, ";
			sql += 		"CASE ";
			sql += 			"WHEN (ct.tipoTrabajador = 4) THEN 'Contratista' ";
			sql += 			"ELSE 'Planta' ";
			sql += 		"END AS descripcionTipoTrabajador, ";
			sql += 		"CASE ";
			sql += 			"WHEN (ct.tipoTrabajador = 6) THEN (5) ";
			sql += 			"WHEN (ct.tipoTrabajador = 7) THEN (6) ";
			sql += 			"ELSE (5) ";
			sql += 		"END AS calculoJornadaSemanal, ";
			sql += 		"tr.*, ct.codigo_trabajador, ct.cargo, ct.tipoTrabajador, ct.sueldoBase, ct.horasSemanales ";
			sql += 	"FROM trabajadores tr ";
			sql += 	"INNER JOIN contratos ct ON (tr.codigo = ct.codigo_trabajador) ";
			sql += 	"LEFT JOIN campo cam ON cam.campo = tr.idHuerto ";
			//String rutFormateado = FormatearRUT(rut.replace("-", ""));
			sql +=		"WHERE (tr.codigo = "+codigo+") AND ct.EstadoContrato = 1 ";
//			sql += "AND ct.periodo = DATE_FORMAT('"+fecha+"', '%Y%m') ";
//			sql +=	"AND tr.periodo = DATE_FORMAT('"+fecha+"', '%Y%m') ";
			sql +=	"AND ct.fechaInicio_actividad <= '"+fecha+"' ";
			sql +=	"AND (ct.FechaTerminoContrato >= '"+fecha+"' OR ct.FechaTerminoContrato IS NULL) ";
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				System.out.println(rs.getDouble("sueldoDiario"));
				int sueldo = rs.getInt("sueldoDiario");
				
				TrabajadoresAgro tr = new TrabajadoresAgro();
				tr.setHx((sueldo*0.0077777));
				tr.setCodigo(rs.getString("codigo"));
				tr.setIdTrabajador(rs.getInt("id"));
				tr.setRut(rs.getString("rut"));
				if(tr.getRut().equals("")){
					tr.setRut(rs.getString("rutTemporal"));
				}
				tr.setNombre(rs.getString("nombreTrabajador"));
				tr.setApellidoPaterno(rs.getString("apellidoPaterno"));
				tr.setIdSociedad(0);
				tr.setAgro(rs.getInt("agro"));
				tr.setSueldoBase(rs.getInt("sueldoBase"));
				tr.setHx_semana(rs.getFloat("hx_semana"));
				tr.setJornadaSemanal(rs.getInt("horasSemanales"));
				tr.setCargo(rs.getInt("ct.cargo"));
				tr.setTipoTrabajador(rs.getInt("ct.tipoTrabajador"));
				tr.setJornadaDiaria(rs.getDouble("jornadaDiaria"));
				tr.setSueldoDiario(rs.getDouble("sueldoDiario"));
				tr.setDescripcionTipoTrabajador(rs.getString("descripcionTipoTrabajador"));
				tr.setCalculoJornadaSemanal(rs.getString("calculoJornadaSemanal"));
				tr.setIdContratista(rs.getString("idContratista"));
				
				if(cuadrilla.return_horas_restantes(fecha, rs.getInt("id")) == 100){
					tr.setHoras_restantes(rs.getDouble("jornadaDiaria"));
				}else{
					tr.setHoras_restantes(cuadrilla.return_horas_restantes(fecha, rs.getInt("id")));
				}
				lista.add(tr);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
	public static ArrayList<TrabajadoresAgro> GET_TRABAJADORES_AGRO (String fecha, int user) throws Exception{
			
		CallableStatement cStmt = null;
		String sql = "";
		ArrayList<TrabajadoresAgro> lista = new ArrayList<TrabajadoresAgro>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "call sa_GET_TRABAJADORES_AGRO('"+fecha+"', "+user+")";
			System.out.println(sql);
			cStmt = db.conn.prepareCall(sql);
			ResultSet rs = cStmt.executeQuery(sql);
			
			while(rs.next()){
				TrabajadoresAgro tr = new TrabajadoresAgro();
				tr.setCodigo(rs.getString("codigo"));
				tr.setIdTrabajador(rs.getInt("id"));
				tr.setRut(rs.getString("rut"));
				if(tr.getRut().equals("")){
					tr.setRut(rs.getString("rutTemporal"));
				}
				tr.setNombre(rs.getString("nombreTrabajador"));
				tr.setPermiso(rs.getInt("permiso"));
				lista.add(tr);
			}
			rs.close();
			db.conn.close();
		
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}

	public static String SA_GETGETALLETRABAJADOR (int codigo, String fecha) throws Exception{
			
		CallableStatement cStmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		JSONObject row = new JSONObject();
		try {
			sql = "call sa_getDetalleTRabajador('"+fecha+"', '"+codigo+"')";
			System.out.println(sql);
			cStmt = db.conn.prepareCall(sql);
			ResultSet rs = cStmt.executeQuery(sql);
			
			if(rs.next()){
				int sueldo = rs.getInt("sueldoDiario");
				row.put("hx", (sueldo*0.0077777));
				row.put("codigo", rs.getInt("codigo"));
				row.put("idTrabajador", rs.getInt("id"));
				row.put("rut", rs.getString("rutDB"));
				row.put("nombre", rs.getString("nombreTrabajador"));
				row.put("apellidoPaterno", rs.getString("apellidoPaterno"));
				row.put("idSociedad", 0);
				row.put("agro", rs.getInt("agro"));
				row.put("sueldoBase", rs.getInt("sueldoBase"));
				row.put("hx_semana", rs.getFloat("hx_semana"));
				row.put("jornadaSemanal", rs.getInt("horasSemanales"));
				row.put("tipoTrabajador", rs.getInt("ct.tipoTrabajador"));
				row.put("jornadaDiaria", rs.getFloat("jornadaDiaria"));
				row.put("sueldoDiario", rs.getFloat("sueldoDiario"));
				row.put("descripcionTipoTrabajador", rs.getString("descripcionTipoTrabajador"));
				row.put("calculoJornadaSemanal", rs.getString("calculoJornadaSemanal"));
				row.put("idContratista", rs.getString("idContratista"));
				row.put("hr_dia", rs.getFloat("hr_dia"));
				row.put("jornadaDiaria", 0);
			}
			rs.close();
			db.conn.close();
		
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return row.toString();
	}
}

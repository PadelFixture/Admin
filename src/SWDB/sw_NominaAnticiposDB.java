package SWDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSW.CargarTipodePago;
import lib.classSW.EnviarMailNominaAnticipo;
import lib.classSW.EnviarMailNominaFiniquito;
import lib.classSW.EnviarMailNominaLiquidacion;
import lib.classSW.NominaAnticipos;
import lib.classSW.PreseleccionDetalleVer;
import lib.classSW.TipoLicencia;
import lib.classSW.UpdateEstadoReclutamiento;
import lib.classSW.tablaPermisoLicencia;
import lib.db.ConnectionDB;

public class sw_NominaAnticiposDB {

	//------------buscar nomina Antyicipos-------------------------------------------------------------------------
	public static ArrayList<NominaAnticipos> getBuscarNomina(
			String fec , 
			String periodo , 
			String tipocuenta ,
			String empresa , 
			String division ,
			String tipodivision ,
			String grupo  ,
			String banco,
			String estado_proceso,
			int rolPrivado) throws Exception{
		
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<NominaAnticipos> lista = new ArrayList<NominaAnticipos>();
		ConnectionDB db = new ConnectionDB();
		
		try{
			sql ="select  sw.id as idTablaSW_A, CA.nCuenta,CA.idBanco,sw.cod_trabajador as codigoTrabajador,"
					+ "sw.periodo as periodo_anticipo, sw.fecha as fecha_anticipo,"
					+ "sw.monto_ingresado,sw.empresa ,tr.nombre,tr.apellidoPaterno,tr.idHuerto,"
					+ "tr.apellidoMaterno,(select descripcion from parametros "
					+ "where codigo = 'TIPO_DE_CUENTA' and llave = CA.idTipoCuenta) as nombre_tipo_cuenta,"
					+ "(select descripcion from parametros where codigo = 'BANCO' and llave = CA.idBanco) as nombre_banco,"
					+ "case when tr.rut = '' then tr.rutTemporal else tr.rut end as rut,tr.direccion,sw.monto_ingresado,CA.idTipoCuenta, "
					+ "(select descripcion from campo where campo = tr.idHuerto) as nombreHuerto "
					+ "from sw_asignacionAnticipos sw "
					+ "INNER JOIN cuentaBancaria CA on sw.cod_trabajador = CA.codigoTrabajador "
					+ "JOIN  trabajadores tr on tr.codigo = CA.codigoTrabajador where 1 = 1 ";
			        
			        if("null".equals(empresa)){}else{sql += " and sw.empresa = "+empresa+"";}
					if("null".equals(periodo)){}else{sql += " and sw.periodo = "+periodo+"";}
					if("null".equals(fec)){}else{sql += " and sw.fecha = '"+fec+"'";}
					if("null".equals(tipocuenta)){}else if("0".equals(tipocuenta)){sql += " AND CA.idTipoCuenta  NOT IN (2,4,3)";}else{sql += " and CA.idTipoCuenta = "+tipocuenta+"";}
					if("null".equals(division)){}else{sql += " and tr.idHuerto = '"+division+"'";}
					if("null".equals(grupo)){}else{sql += " and tr.idCECO = '"+grupo+"'";}
					if("null".equals(banco)){}else{sql += " and CA.idBanco = "+banco+"";}
					if("null".equals(tipodivision)){}else{sql += " and tr.idZona = '"+tipodivision+"'";}
					if("null".equals(estado_proceso)){}else{sql += " and tr.estadoProceso = "+estado_proceso+" ";}
					
					if(rolPrivado == 1){
						sql += " and  tr.rolPrivado in (1,0) ";
					}else{
						sql += " and  tr.rolPrivado in (0) ";
					}
					
					sql += " and CA.cuentaPrimaria = 1 and sw.estado = 0 order by tr.apellidoPaterno; ";
		             
					System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				NominaAnticipos hd = new NominaAnticipos();
				
				
				hd.setIdtabla_sw_asignacionAnticipos(rs.getInt("idTablaSW_A"));
				hd.setNumero_cuenta_string(rs.getString("nCuenta"));
				hd.setIdbanco(rs.getInt("idBanco"));
				hd.setCod_trabajador(rs.getInt("codigoTrabajador"));
				hd.setPeriodo(rs.getInt("periodo_anticipo"));
				hd.setFechaanticipo(rs.getString("fecha_anticipo"));
				hd.setMontoingresado(rs.getInt("monto_ingresado"));
				hd.setEmpresa(rs.getInt("empresa"));
				hd.setNombre(rs.getString("nombre"));
				hd.setApellidopaterno(rs.getString("apellidoPaterno"));
				hd.setApellidomaterno(rs.getString("apellidoMaterno"));
				hd.setNombretipocuenta(rs.getString("nombre_tipo_cuenta"));
				hd.setNombrebanco(rs.getString("nombre_banco"));
				hd.setRut(rs.getString("rut"));
				hd.setDireccion(rs.getString("direccion"));
				hd.setIdtipocuenta(rs.getInt("idTipoCuenta"));
				hd.setNombrehuerto(rs.getString("nombreHuerto"));

				lista.add(hd);
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return lista;
	}
//-------------------Load lista bancos-----------------------------------------------
	public static ArrayList<CargarTipodePago> getListaBancos()  throws Exception{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<CargarTipodePago> lista = new ArrayList<CargarTipodePago>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select * from parametros where codigo = 'BANCO'  AND activo = 1  ORDER BY descripcion;";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				CargarTipodePago cr = new CargarTipodePago();
				cr.setId(rs.getInt("id"));
				cr.setDescripcion(rs.getString("descripcion"));
				cr.setLlave(rs.getInt("llave"));

				
				lista.add(cr);
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
//-------------------Load lista tipos de cuentas-----------------------------------------------
		public static ArrayList<CargarTipodePago> getListaTipoCuenta()  throws Exception{
			PreparedStatement ps = null;
			String sql="";
			ArrayList<CargarTipodePago> lista = new ArrayList<CargarTipodePago>();
			ConnectionDB db = new ConnectionDB();
			try{
				sql = "select * from parametros where codigo = 'TIPO_DE_CUENTA'  AND activo = 1  ORDER BY descripcion;";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				while(rs.next()){
					CargarTipodePago cr = new CargarTipodePago();
					cr.setLlave(rs.getInt("llave"));
					cr.setDescripcion(rs.getString("descripcion"));

					
					lista.add(cr);
				}			
			}catch (SQLException e){
				System.out.println("Error: " + e.getMessage());
			}catch (Exception e){
				System.out.println("Error: " + e.getMessage());
			}finally {
				ps.close();
				db.close();
			}		
			return lista;
		}
		
////////////////////////////////////////////////////////////////////////////////////
		public static ArrayList<NominaAnticipos> getAllNominaAnticipos(int empresa,int periodo, String estado, String tiponomina,String fechapago)  throws Exception{
			PreparedStatement ps = null;
			String sql="";
			ArrayList<NominaAnticipos> lista = new ArrayList<NominaAnticipos>();
			ConnectionDB db = new ConnectionDB();
			try{
				sql = "SELECT *,"
				+ "(select descripcion from parametros where codigo ="
				+ " 'ESTADO_NOMINA_ANTICIPO' and llave = estado ) as nombreEstado "
				+ "FROM sw_nomina "
				+ "where idsociedad =  "+empresa+" and periodo = "+periodo+" ";
				if("null".equals(estado)){
		        	
		        }else{sql += " and estado  = "+estado+"";
		        }
				if("null".equals(tiponomina)){
		        	
		        }else{sql += " and id_concepto  = "+tiponomina+"";
		        }
				if("null".equals(fechapago)){
		        	
		        }else{sql += " and fecha_pago  = '"+fechapago+"'";
		        }
				
				
				sql += " ORDER BY id_nomina DESC";
				System.out.println("aqui");
				System.out.println(sql);
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				while(rs.next()){
					NominaAnticipos cr = new NominaAnticipos();
					cr.setIdtablaswnomina(rs.getInt("id_nomina"));
					cr.setTotalmonto(rs.getInt("total_monto"));
					cr.setFechaanticipo(rs.getString("fecha_pago"));
					cr.setPeriodo(rs.getInt("periodo"));
					cr.setConcepto(rs.getString("concepto"));
					cr.setEstado(rs.getString("nombreEstado"));
					cr.setNumeroestado(rs.getInt("estado"));
					cr.setIdconcepto(rs.getInt("id_concepto"));
				
					lista.add(cr);
				}			
			}catch (SQLException e){
				System.out.println("Error: " + e.getMessage());
			}catch (Exception e){
				System.out.println("Error: " + e.getMessage());
			}finally {
				ps.close();
				db.close();
			}		
			return lista;
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public static boolean UpdateEstadoNomina(NominaAnticipos map) throws Exception{
			PreparedStatement ps = null;
			PreparedStatement ps2 = null;
			String sql = "";
			String sql2 = "";
			ConnectionDB db = new ConnectionDB();
			try{
			
				sql = "UPDATE sw_nomina SET estado = 2, observacion = ? WHERE id_nomina = ? ";
				ps = db.conn.prepareStatement(sql);
				
				ps.setString(1, map.getObservacion());
				ps.setInt(2, map.getIdtablaswnomina());
				
				ps.execute();
				
				sql2 = "UPDATE sw_asignacionAnticipos SET estado = 0, id_nomina = null WHERE id_nomina = ? ";
				ps2 = db.conn.prepareStatement(sql2);
				
				
				ps2.setInt(1, map.getIdtablaswnomina());
				
				ps2.execute();
				return true;
			}catch(Exception ex){
				System.out.println("Error: "+ex.getMessage());
			}finally{
				ps.close();
				ps2.close();
				db.conn.close();
			}
			return false;
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static boolean UpdateEstadoNominaAprovar(NominaAnticipos map) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			sql = "UPDATE sw_nomina SET estado = 1, observacion = ? WHERE id_nomina = ? ";
			ps = db.conn.prepareStatement(sql);

			ps.setString(1, map.getObservacion());
			ps.setInt(2, map.getIdtablaswnomina());

			ps.execute();
			return true;
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<NominaAnticipos> DetalleVerNomina(int entero ) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<NominaAnticipos> data = new ArrayList<NominaAnticipos>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="SELECT  sw.id,sw.periodo,sw.fecha,sw.cod_trabajador,sw.monto_ingresado,sw.empresa,"
					+ "(select descripcion from parametros where codigo = 'BANCO' and llave = CA.idBanco and activo = 1) as nombrebanco,"
					+ "(select nombre from trabajadores where codigo = sw.cod_trabajador) as nombre,"
					+ "(select apellidoPaterno from trabajadores where codigo = sw.cod_trabajador) as apellidopaterno,"
					+ "(select apellidoMaterno from trabajadores where codigo = sw.cod_trabajador) as apellidomaterno,"
					+ "(select SUM(monto_ingresado) from sw_asignacionAnticipos where id_nomina = "+entero+" ) total FROM sw_asignacionAnticipos sw "
					+ "right JOIN cuentaBancaria CA on CA.codigoTrabajador = sw.cod_trabajador "
					+ "where sw.id_nomina = "+entero+" and CA.cuentaPrimaria = 1";
           System.out.println(sql);
           
           
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				NominaAnticipos e = new NominaAnticipos();
				e.setIdtabla_sw_asignacionAnticipos(rs.getInt("id"));
				e.setPeriodo(rs.getInt("periodo"));
				e.setFechaanticipo(rs.getString("fecha"));
				e.setCod_trabajador(rs.getInt("cod_trabajador"));
				e.setMontoingresado(rs.getInt("monto_ingresado"));
				e.setEmpresa(rs.getInt("empresa"));
				e.setNombrebanco(rs.getString("nombrebanco"));
				e.setNombre(rs.getString("nombre"));
				e.setApellidopaterno(rs.getString("apellidopaterno"));
				e.setApellidomaterno(rs.getString("apellidomaterno"));
				e.setTotalmonto(rs.getInt("total"));
				
			
				
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
/////////////////////////////obtener nomina por url correo//////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<NominaAnticipos> getAllNominaCorreo(int id)  throws Exception{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<NominaAnticipos> lista = new ArrayList<NominaAnticipos>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *,(select descripcion from parametros where codigo = 'ESTADO_NOMINA_ANTICIPO' and llave = estado ) as nombreEstado FROM sw_nomina where estado = 0 and id_nomina = "+id+"";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				NominaAnticipos cr = new NominaAnticipos();
				cr.setIdtablaswnomina(rs.getInt("id_nomina"));
				cr.setTotalmonto(rs.getInt("total_monto"));
				cr.setFechaanticipo(rs.getString("fecha_pago"));
				cr.setPeriodo(rs.getInt("periodo"));
				cr.setConcepto(rs.getString("concepto"));
				cr.setEstado(rs.getString("nombreEstado"));
				cr.setNumeroestado(rs.getInt("estado"));
				
			
				lista.add(cr);
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
///////////////////////////obtener correo para quien recibe nomina anticipos///////////////////////////////////////////////////////////////////////////////////
	public static NominaAnticipos getTOCorreoAnticipos() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		NominaAnticipos permiso = new NominaAnticipos();
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="select descripcion from parametros where codigo = 'CORREO_NOMINA_PAGO_ANTICIPO' and llave = 1 and activo = 1;";

			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			if(rs.next()){
				
				permiso.setCorreo_to_nomina_anticipo(rs.getString("descripcion"));
				
				
			}
			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return permiso;
		
	}
///////////////////////////obtener correo para quien Envia nomina anticipos///////////////////////////////////////////////////////////////////////////////////
	public static NominaAnticipos getFromCorreoAnticipos() throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		NominaAnticipos permiso = new NominaAnticipos();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select descripcion from parametros where codigo = 'CORREO_NOMINA_PAGO_ANTICIPO' and llave = 2 and activo = 1;";

			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			if (rs.next()) {

				permiso.setCorreo_from_nomina_anticipo(rs.getString("descripcion"));

			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return permiso;

	}
///////////////////////////obtener contraseña correo para quien Envia nomina anticipos///////////////////////////////////////////////////////////////////////////////////
	public static NominaAnticipos getPasswordCorreoAnticipos() throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		NominaAnticipos permiso = new NominaAnticipos();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select descripcion from parametros where codigo = 'CORREO_NOMINA_PAGO_ANTICIPO' and llave = 3 and activo = 1;";

			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			if (rs.next()) {

				permiso.setCorreo_contrasena(rs.getString("descripcion"));

			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return permiso;

	}
///////////////////////////obtener Rut Empresa///////////////////////////////////////////////////////////////////////////////////
	public static NominaAnticipos getRutEmpresa(int id) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		NominaAnticipos permiso = new NominaAnticipos();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select rut from sociedad where idSociedad = "+id+"";

			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			if (rs.next()) {

				permiso.setRut(rs.getString("rut"));

			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return permiso;

	}
///////////////////////////obtener Rut Empresa///////////////////////////////////////////////////////////////////////////////////
	public static NominaAnticipos getALLSociedad(int id) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		NominaAnticipos permiso = new NominaAnticipos();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select * from sociedad where idSociedad = " + id + "";
System.out.println(sql);
			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			if (rs.next()) {

				permiso.setNumeroconvenio(rs.getString("numeroConvenio"));
				permiso.setNumeronomina(rs.getString("numeroNomina"));
				permiso.setTiponomina(rs.getString("tipoNomina"));

			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return permiso;

	}
/////////////////////////////////cargar select fecha pago por empresa y periodo///////////////////////////////////////////////////////////////////////////////
	public static ArrayList<NominaAnticipos> SelectFechaPagoNominaAnticipo(int empresa,int periodo) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<NominaAnticipos> data = new ArrayList<NominaAnticipos>();
		ConnectionDB db = new ConnectionDB();
		try {
			
		
			
			sql = "select fecha from sw_asignacionAnticipos where empresa = "+empresa+" and periodo = "+periodo+"  and estado = 0 group by fecha;";
					
			

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				NominaAnticipos e = new NominaAnticipos();
				
				e.setFechaanticipo(rs.getString("fecha"));
			

				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			db.close();
		}
		return data;
	}
	
	public static ArrayList<NominaAnticipos> SelectFechaPagoNominafilter(int empresa,int periodo,String estado,String tiponomina) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<NominaAnticipos> data = new ArrayList<NominaAnticipos>();
		ConnectionDB db = new ConnectionDB();
		try {
			
		
			
			sql = "select fecha_pago from sw_nomina where idsociedad = "
					+ ""+empresa+" and periodo = "+periodo+" ";
			if("null".equals(estado)){
	        	
	        }else{sql += " and estado  = "+estado+"";
	        }
			if("null".equals(tiponomina)){
	        	
	        }else{sql += " and id_concepto  = "+tiponomina+"";
	        }
			sql +=  "  group by fecha_pago;";
			
			
			System.out.println(sql);		
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				NominaAnticipos e = new NominaAnticipos();
				
				e.setFechaanticipo(rs.getString("fecha_pago"));
			

				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			db.close();
		}
		return data;
	}
/////////////////ruta archivo par descargar Excel///////////////////////////////////////////////////
	public static NominaAnticipos getRutaTablaSWNominaExcel(String idruta) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		NominaAnticipos permiso = new NominaAnticipos();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT ruta_excel FROM sw_nomina where id_nomina = " + idruta + "";

			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			if (rs.next()) {

				permiso.setRutaarchivo(rs.getString("ruta_excel"));

			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return permiso;

	}
/////////////////ruta archivo par descargar ///////////////////////////////////////////////////
	public static NominaAnticipos getRutaTablaSWNomina(String idruta) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		NominaAnticipos permiso = new NominaAnticipos();
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="SELECT ruta_detalle FROM sw_nomina where id_nomina = "+idruta+"";

			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			if(rs.next()){
				
				permiso.setRutaarchivo(rs.getString("ruta_detalle"));
				
				
			}
			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return permiso;
		
	}
///////////////////////////obtener CODIGO SBIF EMPRESA///////////////////////////////////////////////////////////////////////////////////
	public static NominaAnticipos getCodigoSbif(int id) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		NominaAnticipos permiso = new NominaAnticipos();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select descripcion from parametros where codigo = 'SBIF' and activo = 1 and llave = " + id + "";
            System.out.println(sql);
			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			if (rs.next()) {

				permiso.setCodigosbif(rs.getString("descripcion"));

			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return permiso;

	}
///////////////////////////obtener Numero Cuenta Trabajador///////////////////////////////////////////////////////////////////////////////////
	public static NominaAnticipos getNumeroCuenta(String codTrabajador) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		NominaAnticipos permiso = new NominaAnticipos();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select nCuenta, idTipoCuenta from cuentaBancaria where cuentaPrimaria = 1 and codigoTrabajador = " + codTrabajador + "";
            System.out.println(sql);
			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			if (rs.next()) {

				permiso.setNumero_cuenta_string(rs.getString("nCuenta"));
				permiso.setIdtipocuenta(rs.getInt("idTipoCuenta"));

			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return permiso;

	}
	//------------buscar nomina Finiquito-------------------------------------------------------------------------
	public static ArrayList<NominaAnticipos> getBuscarNominaFiniquito(String fechatermino , String tipo_cuenta ,String empresa , String division ,String tipodivision ,String grupo ,String banco,String periodo,String estado_proceso,int rolPrivado) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<NominaAnticipos> lista = new ArrayList<NominaAnticipos>();
		ConnectionDB db = new ConnectionDB();
		
		try{
			sql ="select *,"
					+ "sw.id_finiquito as idTablaSW_F,"
					+ "sw.fecha_termino_contrato as fecha_termino,"
					+ "sw.total_pago_finiquito, sw.periodo as periodo_liquidacion, "
					+ "sw.id_sociedad as empresa_finiquito,"
					+ "(select descripcion from parametros where codigo = 'TIPO_DE_CUENTA' and llave = ca.idTipoCuenta) as nombre_tipo_cuenta,"
					+ "(select descripcion from parametros where codigo = 'BANCO' and llave = ca.idBanco) as nombre_banco,"
					+ "case when tr.rut = '' then tr.rutTemporal else tr.rut end as rut2,tr.direccion,tr.idHuerto,"
					+ "(select descripcion from campo where campo = tr.idHuerto) as nombreHuerto "
					+ "from cuentaBancaria ca "
					+ "INNER JOIN trabajadores tr on ca.codigoTrabajador = tr.codigo "
					+ "inner join sw_finiquitos sw on sw.cod_trabajador = tr.codigo "
					+ "INNER JOIN contratos co on sw.id_contrato = co.id "
					+ "where ";
			        
			        if("null".equals(empresa)){}else{sql += "sw.id_sociedad = "+empresa+"";}
					if("null".equals(fechatermino)){}else{sql += " and sw.fecha_pago = '"+fechatermino+"'";}
					if("null".equals(tipo_cuenta)){}else if("0".equals(tipo_cuenta)){sql += " AND ca.idTipoCuenta  NOT IN (2,4,3)";}else{sql += " and ca.idTipoCuenta = "+tipo_cuenta+"";}
					if("null".equals(division)){}else{sql += " and co.idHuertoContrato = '"+division+"'";}
					if("null".equals(grupo)){}else{sql += " and tr.idCECO = '"+grupo+"'";}
					if("null".equals(banco)){}else{sql += " and ca.idBanco = "+banco+"";}
					if("null".equals(tipodivision)){}else{sql += " and tr.idZona = '"+tipodivision+"'";}
					if("null".equals(periodo)){}else{sql += " and sw.periodo = "+periodo+"";}
					if("null".equals(estado_proceso)){}else{sql += " and tr.estadoProceso = "+estado_proceso+" ";}
					
					if(rolPrivado == 1){
						sql += " and  tr.rolPrivado in (1,0) ";
					}else{
						sql += " and  tr.rolPrivado in (0) ";
					}
					
					sql += " and ca.cuentaPrimaria = 1 and sw.estado_finiquito = 0 order by tr.apellidoPaterno; ";
		             System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				NominaAnticipos hd = new NominaAnticipos();
				
				
			
				hd.setFechaPago(rs.getString("fecha_pago"));
				hd.setCod_trabajador(rs.getInt("codigoTrabajador"));
				hd.setIdtipocuenta(rs.getInt("idTipoCuenta"));
				hd.setNumero_cuenta_string(rs.getString("nCuenta"));
				hd.setIdbanco(rs.getInt("idBanco"));
				hd.setNombre(rs.getString("nombre"));
				hd.setApellidopaterno(rs.getString("apellidoPaterno"));
				hd.setApellidomaterno(rs.getString("apellidoMaterno"));
				hd.setTotalpagofiniquito(rs.getInt("total_pago_finiquito"));
				hd.setIdtablafiniquito(rs.getInt("idTablaSW_F"));
				hd.setNombrebanco(rs.getString("nombre_banco"));
				hd.setNombretipocuenta(rs.getString("nombre_tipo_cuenta"));
				hd.setRut(rs.getString("rut2"));
				hd.setEmpresa(rs.getInt("id_sociedad"));
				hd.setDireccion(rs.getString("direccion"));
				hd.setPeriodo(rs.getInt("periodo_liquidacion"));
				hd.setDireccion(rs.getString("direccion"));
				hd.setNombrehuerto(rs.getString("nombreHuerto"));

				lista.add(hd);
				
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return lista;
	}

///////////////////////////obtener correo para quien recibe nomina Finiquito///////////////////////////////////////////////////////////////////////////////////
	public static NominaAnticipos getTOCorreoFiniquito() throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		NominaAnticipos permiso = new NominaAnticipos();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select descripcion from parametros where codigo = 'CORREO_NOMINA_PAGO_FINIQUITO_TO' and llave = 1 and activo = 1;";

			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			if (rs.next()) {

				permiso.setCorreo_to_nomina_finiquito(rs.getString("descripcion"));

			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return permiso;

	}
	public static ArrayList<NominaAnticipos> getBuscarNominaLiquidacion(String fechapago , String tipo_cuenta ,String empresa , String division ,String subdivision ,String grupo ,String banco,String periodo,String estado_proceso,int rolPrivado) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<NominaAnticipos> lista = new ArrayList<NominaAnticipos>();
		ConnectionDB db = new ConnectionDB();
		
		try{
			sql ="select *,"
					+ "sw.id_liquidacion as idTablaSW_L,"
					+ "sw.fecha_pago,"
					+ "sw.total_pago,"
					+ "sw.id_sociedad,"
					+ "sw.periodo,"
					+ "(select descripcion from parametros where codigo = 'TIPO_DE_CUENTA' and llave = ca.idTipoCuenta) as nombre_tipo_cuenta,"
					+ "(select descripcion from parametros where codigo = 'BANCO' and llave = ca.idBanco) as nombre_banco,"
					+ "case when tr.rut = '' then tr.rutTemporal else tr.rut end as rut2,tr.direccion,tr.idHuerto,"
					+ "(select descripcion from campo where campo = tr.idHuerto) as nombreHuerto "
					+ "from cuentaBancaria ca "
					+ "INNER JOIN trabajadores tr on ca.codigoTrabajador = tr.codigo "
					+ "inner join sw_liquidacion sw on sw.cod_trabajador = tr.codigo "
					+ "INNER JOIN contratos co on sw.id_contrato = co.id "
					+ "where ";
			        
			        if("null".equals(empresa)){}else{sql += "sw.id_sociedad = "+empresa+"";}
					if("null".equals(fechapago)){}else{sql += " and sw.fecha_pago = '"+fechapago+"'";}
					if("null".equals(periodo)){}else{sql += " and sw.periodo = "+periodo+"";}
					if("null".equals(tipo_cuenta)){}else if("0".equals(tipo_cuenta)){sql += " AND ca.idTipoCuenta  NOT IN (2,4,3)";}else{sql += " and ca.idTipoCuenta = "+tipo_cuenta+"";}
					if("null".equals(division)){}else{sql += " and co.idHuertoContrato = '"+division+"'";}
					if("null".equals(grupo)){}else{sql += " and tr.idCECO = '"+grupo+"'";}
					if("null".equals(banco)){}else{sql += " and ca.idBanco = "+banco+"";}
					if("null".equals(subdivision)){}else{sql += " and tr.idZona = '"+subdivision+"'";}
					if("null".equals(estado_proceso)){}else{sql += " and tr.estadoProceso = "+estado_proceso+" ";}
					
					if(rolPrivado == 1){
						sql += " and  tr.rolPrivado in (1,0) ";
					}else{
						sql += " and  tr.rolPrivado in (0) ";
					}
					
					sql += " and ca.cuentaPrimaria = 1 and sw.estado_liquidacion = 0 order by tr.apellidoPaterno; ";
		
					System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				NominaAnticipos hd = new NominaAnticipos();
				
				
			
				hd.setFechaPago(rs.getString("fecha_pago"));
				hd.setCod_trabajador(rs.getInt("codigoTrabajador"));
				hd.setIdtipocuenta(rs.getInt("idTipoCuenta"));
				hd.setNumero_cuenta_string(rs.getString("nCuenta"));
				hd.setIdbanco(rs.getInt("idBanco"));
				hd.setNombre(rs.getString("nombre"));
				hd.setApellidopaterno(rs.getString("apellidoPaterno"));
				hd.setApellidomaterno(rs.getString("apellidoMaterno"));
				hd.setTotalpagoliquidacion(rs.getInt("total_pago"));
				hd.setIdtablaliquidacion(rs.getInt("idTablaSW_L"));
				hd.setNombrebanco(rs.getString("nombre_banco"));
				hd.setNombretipocuenta(rs.getString("nombre_tipo_cuenta"));
				hd.setRut(rs.getString("rut2"));
				hd.setEmpresa(rs.getInt("id_sociedad"));
				hd.setDireccion(rs.getString("direccion"));
				hd.setPeriodo(rs.getInt("periodo"));
				hd.setNombrehuerto(rs.getString("nombreHuerto"));

				lista.add(hd);
				
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return lista;
	}
/////////////////////////////////cargar select fecha pago por empresa y periodo liquidacion///////////////////////////////////////////////////////////////////////////////
	public static ArrayList<NominaAnticipos> SelectFechaPagoNominaAnticipoLiquidacion(int empresa, int periodo) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<NominaAnticipos> data = new ArrayList<NominaAnticipos>();
		ConnectionDB db = new ConnectionDB();
		try {

			sql = "select fecha_pago from sw_liquidacion where id_sociedad = " + empresa + " and periodo = " + periodo
					+ "  and estado_liquidacion = 0 group by fecha_pago;";
            System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				NominaAnticipos e = new NominaAnticipos();

				e.setFechaanticipo(rs.getString("fecha_pago"));

				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			db.close();
		}
		return data;
	}
///////////////////////////obtener correo para quien recibe nomina Liquidacion///////////////////////////////////////////////////////////////////////////////////
	public static NominaAnticipos getTOCorreoLiquidacion() throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		NominaAnticipos permiso = new NominaAnticipos();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select descripcion from parametros where codigo = 'CORREO_NOMINA_PAGO_LIQUIDACION_TO' and llave = 1 and activo = 1;";

			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			if (rs.next()) {

				permiso.setCorreo_to_nomina_liquidacion(rs.getString("descripcion"));

			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return permiso;

	}
///////////////////////////////////ver lista nomina Liquidacion////////////////////////////////////////////////////////////////////////////
	public static ArrayList<NominaAnticipos> DetalleVerNominaLiquidacion(int entero) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<NominaAnticipos> data = new ArrayList<NominaAnticipos>();
		ConnectionDB db = new ConnectionDB();
		try {
		
			
			sql = "SELECT *,"
					+ "(select descripcion from parametros where codigo = 'BANCO' and llave = CA.idBanco and activo = 1) as nombrebanco,"
					+ "(select nombre from trabajadores where codigo = sw.cod_trabajador) as nombre,"
					+ "(select apellidoPaterno from trabajadores where codigo = sw.cod_trabajador) as apellidopaterno,"
					+ "(select apellidoMaterno from trabajadores where codigo = sw.cod_trabajador) as apellidomaterno,"
					+ "(select SUM(total_pago) from sw_liquidacion where id_nomina = "+entero+" ) total "
					+ "FROM sw_liquidacion sw "
					+ "right JOIN cuentaBancaria CA on CA.codigoTrabajador = sw.cod_trabajador "
					+ "where sw.id_nomina = "+entero+" and CA.cuentaPrimaria = 1";
			
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				NominaAnticipos e = new NominaAnticipos();
				e.setIdtabla_sw_asignacionAnticipos(rs.getInt("id_liquidacion"));
				e.setPeriodo(rs.getInt("periodo"));
				e.setFechaanticipo(rs.getString("fecha_pago"));
				e.setCod_trabajador(rs.getInt("cod_trabajador"));
				e.setMontoingresado(rs.getInt("total_pago"));
				e.setEmpresa(rs.getInt("id_sociedad"));
				e.setNombre(rs.getString("nombre"));
				e.setApellidopaterno(rs.getString("apellidopaterno"));
				e.setApellidomaterno(rs.getString("apellidomaterno"));
				e.setTotalmonto(rs.getInt("total"));
				e.setNombrebanco(rs.getString("nombrebanco"));
			
			
				
				

				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			db.close();
		}
		return data;
	}
///////////////////////////////////ver lista nomina Finiquito////////////////////////////////////////////////////////////////////////////
	public static ArrayList<NominaAnticipos> DetalleVerNominaFiniquito(int entero) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<NominaAnticipos> data = new ArrayList<NominaAnticipos>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT *,"
			+ "(select descripcion from parametros where codigo = 'BANCO' and llave = CA.idBanco and activo = 1) as nombrebanco,"
			+ "(select nombre from trabajadores where codigo = sw.cod_trabajador) as nombre,"
			+ "(select apellidoPaterno from trabajadores where codigo = sw.cod_trabajador) as apellidopaterno,"
			+ "(select apellidoMaterno from trabajadores where codigo = sw.cod_trabajador) as apellidomaterno,"
			+ "(select total_monto from sw_nomina where id_nomina = "+entero+" ) total,"
			+ "(select sum(retenciones) from  sw_finiquitos  where id_nomina = "+entero+") as suma_retenciones "
			+ "FROM sw_finiquitos sw "
			+ "right JOIN cuentaBancaria CA on CA.codigoTrabajador = sw.cod_trabajador "
			+ "where sw.id_nomina = "+entero+" and CA.cuentaPrimaria = 1";
					
			
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				NominaAnticipos e = new NominaAnticipos();
				e.setIdtabla_sw_asignacionAnticipos(rs.getInt("id_finiquito"));
				e.setPeriodo(rs.getInt("periodo"));
				e.setFechaanticipo(rs.getString("fecha_pago"));
				e.setCod_trabajador(rs.getInt("cod_trabajador"));
				e.setMontoingresado(rs.getInt("total_pago_finiquito"));
				e.setEmpresa(rs.getInt("id_sociedad"));
				e.setNombre(rs.getString("nombre"));
				e.setApellidopaterno(rs.getString("apellidopaterno"));
				e.setApellidomaterno(rs.getString("apellidomaterno"));
				e.setTotalmonto(rs.getInt("total"));
				e.setNombrebanco(rs.getString("nombrebanco"));
				e.setRetencion(rs.getInt("retenciones"));
				e.setMontomenosretenciones(rs.getInt("suma_retenciones"));
				

				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			db.close();
		}
		return data;
	}
/////////////////////////////////cargar select fecha pago por empresa y periodo Finiquito///////////////////////////////////////////////////////////////////////////////
	public static ArrayList<NominaAnticipos> SelectFechaPagoNominaFiniquito(int empresa, int periodo)
			throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<NominaAnticipos> data = new ArrayList<NominaAnticipos>();
		ConnectionDB db = new ConnectionDB();
		try {

			sql = "select fecha_pago from sw_finiquitos where id_sociedad = " + empresa + " and periodo = " + periodo
					+ "  and estado_finiquito = 0 and fecha_pago is not null group by fecha_pago;";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				NominaAnticipos e = new NominaAnticipos();

				e.setFechaanticipo(rs.getString("fecha_pago"));

				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			db.close();
		}
		return data;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static boolean UpdateEstadoNominaLiquidacion(NominaAnticipos map) throws Exception {
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		String sql = "";
		String sql2 = "";
		ConnectionDB db = new ConnectionDB();
		try {

			sql = "UPDATE sw_nomina SET estado = 2, observacion = ? WHERE id_nomina = ? ";
			ps = db.conn.prepareStatement(sql);

			ps.setString(1, map.getObservacion());
			ps.setInt(2, map.getIdtablaswnomina());

			ps.execute();

			sql2 = "UPDATE sw_liquidacion SET estado_liquidacion = 0, id_nomina = null WHERE id_nomina = ? ";
			ps2 = db.conn.prepareStatement(sql2);

			ps2.setInt(1, map.getIdtablaswnomina());

			ps2.execute();
			return true;
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			ps.close();
			ps2.close();
			db.conn.close();
		}
		return false;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static boolean UpdateEstadoNominaFiniquito(NominaAnticipos map) throws Exception {
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		String sql = "";
		String sql2 = "";
		ConnectionDB db = new ConnectionDB();
		try {

			sql = "UPDATE sw_nomina SET estado = 2, observacion = ? WHERE id_nomina = ? ";
			ps = db.conn.prepareStatement(sql);

			ps.setString(1, map.getObservacion());
			ps.setInt(2, map.getIdtablaswnomina());

			ps.execute();

			sql2 = "UPDATE sw_finiquitos SET estado_finiquito = 0, retenciones = null, id_nomina = null WHERE id_nomina = ? ";
			ps2 = db.conn.prepareStatement(sql2);
            System.out.println(sql2);
			ps2.setInt(1, map.getIdtablaswnomina());

			ps2.execute();
			return true;
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			ps.close();
			ps2.close();
			db.conn.close();
		}
		return false;
	}
///////////////////////////obtener contraseña correo para quien Envia nomina anticipos///////////////////////////////////////////////////////////////////////////////////
	public static NominaAnticipos getURLNominaPago() throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		NominaAnticipos permiso = new NominaAnticipos();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select descripcion from parametros where codigo = 'URL_NOMINA' and llave = 1 and activo = 1;";

			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			if (rs.next()) {

				permiso.setUrlnomina(rs.getString("descripcion"));

			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return permiso;

	}
	
	public static ArrayList<CargarTipodePago> getListaOficina()  throws Exception{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<CargarTipodePago> lista = new ArrayList<CargarTipodePago>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select * from parametros where codigo = 'CODIGOS_DE_OFICINAS'  AND activo = 1  ORDER BY descripcion;";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				CargarTipodePago cr = new CargarTipodePago();
				cr.setId(rs.getInt("id"));
				cr.setDescripcion(rs.getString("descripcion"));
				cr.setLlave(rs.getInt("llave"));

				
				lista.add(cr);
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	
	public static boolean EnviarMailBoton(int idtabla, int idconcepto) throws Exception{
		
		try{
		
			
			
			NominaAnticipos var = SWDB.sw_NominaAnticiposDB.getRutaArchivo(idtabla);
			String outputFile = var.getRutaarchivo();
			
			System.out.println(outputFile);
			
			String split[]  = outputFile.split("/");
			String nombreArchi = split[4];
			
			System.out.println(nombreArchi);
			
			if(idconcepto == 1)
			{
			EnviarMailNominaAnticipo obj = new EnviarMailNominaAnticipo();
		  	obj.EnviarMailAnticipos(idtabla,outputFile,nombreArchi);
			}
			else if (idconcepto == 2){
				
				EnviarMailNominaLiquidacion obj = new EnviarMailNominaLiquidacion();
				obj.EnviarMailLiquidaciones(idtabla, outputFile, nombreArchi);
				
			}
			else if (idconcepto == 3){
				EnviarMailNominaFiniquito obj = new EnviarMailNominaFiniquito();
				obj.EnviarMailFiniquitos(idtabla, outputFile, nombreArchi);
			}
			
			
		
			return true;
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			
		}
		return false;
	}
	
	public static NominaAnticipos getRutaArchivo(int idtabla) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		NominaAnticipos permiso = new NominaAnticipos();
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="select ruta_excel from sw_nomina where id_nomina = "+idtabla+"";

			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			if(rs.next()){
				
				permiso.setRutaarchivo(rs.getString("ruta_excel"));
				
				
			}
			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return permiso;
		
	}
///////////////////////////obtener correo para quien recibe nomina Finiquito///////////////////////////////////////////////////////////////////////////////////
	public static NominaAnticipos getTOCorreoPreNomina() throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		NominaAnticipos permiso = new NominaAnticipos();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select descripcion from parametros where codigo = 'CORREO_PRE_NOMINA_ANTICIPO_TO' and llave = 1 and activo = 1;";

			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			if (rs.next()) {

				permiso.setCorreo_to_pre_nomina_anticipo(rs.getString("descripcion"));

			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return permiso;

	}
	
	//------------buscar nomina Antyicipos-------------------------------------------------------------------------
		public static ArrayList<NominaAnticipos> getBuscardatoscentralizacionNomina(int idnomina) throws Exception{
			PreparedStatement ps = null;
			String sql = "";
			ArrayList<NominaAnticipos> lista = new ArrayList<NominaAnticipos>();
			ConnectionDB db = new ConnectionDB();
			
			try{
				sql ="SELECT tr.idCECO, anticipo.empresa, sum(monto_ingresado) as montocentralizar from sw_asignacionAnticipos as anticipo"
					+" inner join trabajadores tr on tr.codigo = anticipo.cod_trabajador "
					+" where id_nomina = "+idnomina+" group by tr.idCECO,anticipo.empresa ";
				       
						System.out.println(sql);
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				while(rs.next()){
					NominaAnticipos hd = new NominaAnticipos();
					
					
					hd.setEmpresa_c(rs.getInt("empresa"));
					hd.setMontocentralizar_c(rs.getInt("montocentralizar"));
					hd.setIdCECO_c(rs.getString("idCECO"));
					

					lista.add(hd);
					
				}			
			}catch (SQLException e){
				System.out.println("Error: " + e.getMessage());
			}catch (Exception e){
				System.out.println("Error: " + e.getMessage());
			}finally {
				ps.close();
				db.close();
			}
			return lista;
		}
		
		
		/// insertar respuesta sap asiento ////////
		

		  public static String insertarresSAPNomina (String asiento, int idnomina) throws Exception{
				
				PreparedStatement ps = null;

				String sql="";

				String respuesta = "";
				ConnectionDB db = new ConnectionDB();
				
				
				try{
					 
					
						
						sql = "UPDATE sw_nomina SET E_BELNR='"+asiento+"' WHERE id_nomina="+idnomina+"";
						ps = db.conn.prepareStatement(sql);
							
						
						System.out.println(sql);
						ps.execute(sql);
						
						respuesta ="Enviado";
						
					
					
					
					return respuesta;
				}catch (SQLException e){
					System.out.println("Error: "+ e.getMessage());
				}catch (Exception e){
					System.out.println("Error: "+ e.getMessage());
				}finally {
				
				
					db.close();
				}
				return "No se Inserto en Base de datos";
			}
		  

}

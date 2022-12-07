package lib.db.sw;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import lib.classSW.Liquidacion;
import lib.classSW.LiquidacionTrabajador;

import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;

public class LiquidacionesDB {

	public static Blob getLiquidacion() throws Exception {

		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try {

			ps = db.conn.createStatement();
			sql = "SELECT file FROM sw_template WHERE UPPER(documento) = 'LIQUIDACION' ";
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				Blob liquidacion = rs.getBlob(1);
				return liquidacion;
			}
			rs.close();
			ps.close();
			db.conn.close();

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLiquidacion: " + e.getMessage());
		} finally {
			db.close();
		}

		return null;
	}

	// insertar Liquidaciuon
	public static boolean insertLiquidacion(Liquidacion Liquidacion) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try {
			sql = " INSERT into sw_liquidacion " + " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, Liquidacion.getId_liquidacion());
			ps.setInt(2, Liquidacion.getCod_trabajador());
			ps.setInt(3, Liquidacion.getId_contrato());
			ps.setInt(4, Liquidacion.getId_sociedad());
			ps.setString(5, Liquidacion.getRut());
			ps.setInt(6, Liquidacion.getPeriodo());
			ps.setString(7, Liquidacion.getFecha_pago());
			ps.setString(8, Liquidacion.getLugar_pago());
			ps.setInt(9, Liquidacion.getId_nomina());
			ps.setString(10, Liquidacion.getTotal_pago());
			ps.setInt(11, Liquidacion.getEstado_liquidacion());

			ps.execute();
			return true;
		} catch (Exception e) {

			System.out.println("Error al ingresar Liquidacion:" + e.getMessage());
			e.printStackTrace();

		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}// fin clase insert

	public static boolean calculoMensualLiquidaciones(String liquidacion) throws Exception {

		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();
		int i = 0;

		try {

			String sql = "INSERT INTO sw_liquidacion ( id_liquidacion, cod_trabajador, id_contrato, id_sociedad, "
					+ " rut, periodo, fecha_pago, lugar_pago, " + " id_nomina, total_pago, estado_liquidacion ) "
					+ " VALUES (?, ?, ?, ?, " + "         ?, ?, ?, ?," + "         ?, ?, ? ) ";

			ps = db.conn.prepareStatement(sql);

			ps.setString(i++, liquidacion.getClass().toString());
			ps.setString(i++, liquidacion.getClass().toString());
			ps.setString(i++, liquidacion.getClass().toString());
			ps.setString(i++, liquidacion.getClass().toString());

			ps.setString(i++, liquidacion.getClass().toString());
			ps.setString(i++, liquidacion.getClass().toString());
			ps.setString(i++, liquidacion.getClass().toString());
			ps.setString(i++, liquidacion.getClass().toString());

			ps.setString(i++, liquidacion.getClass().toString());
			ps.setString(i++, liquidacion.getClass().toString());
			ps.setString(i++, liquidacion.getClass().toString());

			ps.execute(sql);

			return true;

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();

			db.close();
		}
		return false;
	}

	// get liquidacion
	public static ArrayList<Liquidacion> getLiquidacionLista() throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<Liquidacion> lista = new ArrayList<Liquidacion>();

		try {
			sql = "select * from sw_liquidacion";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				Liquidacion Liquidacion = new Liquidacion();

				Liquidacion.setId_liquidacion(rs.getInt("id_liquidacion"));
				Liquidacion.setCod_trabajador(rs.getInt("cod_trabajador"));
				Liquidacion.setId_contrato(rs.getInt("id_contrato"));
				Liquidacion.setId_sociedad(rs.getInt("id_sociedad"));
				Liquidacion.setRut(rs.getString("rut"));
				Liquidacion.setPeriodo(rs.getInt("periodo"));
				Liquidacion.setFecha_pago(rs.getString("fecha_pago"));
				Liquidacion.setLugar_pago(rs.getString("lugar_pago"));
				Liquidacion.setId_nomina(rs.getInt("id_nomina"));
				Liquidacion.setTotal_pago(rs.getString("total_pago"));
				Liquidacion.setEstado_liquidacion(rs.getInt("estado_liquidacion"));

				lista.add(Liquidacion);

			}

		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}// fin metodo get
	
	
	// get liquidacion
		public static ArrayList<Liquidacion> getLiquidacionWithFilter(ArrayList<filterSql> filter) throws Exception {

			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			ArrayList<Liquidacion> lista = new ArrayList<Liquidacion>();

			try {
				sql = " SELECT * FROM sw_liquidacion ";
				
				
				// Si contiene datos asignarlo al WHERE
				if (filter.size() > 0) {
					String andSql = "";
					andSql += " WHERE ";
					Iterator<filterSql> f = filter.iterator();

					while (f.hasNext()) {
						filterSql row = f.next();

						if (!row.getValue().equals("")) {

							if (row.getCampo().endsWith("_to")) {

								SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
								SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
								sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='"
										+ sqlDate.format(formatter.parse(row.getValue())) + "'";
							}

							else if (row.getCampo().endsWith("_from")) {

								SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
								SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
								sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='"
										+ sqlDate.format(formatter.parse(row.getValue())) + "'";
							}
							else if (GeneralUtility.isArray(row.getValue())){
								sql += andSql + row.getCampo() + " in ( "+GeneralUtility.convertJSONArrayToArray(row.getValue())+" ) ";
							}
							else if (GeneralUtility.isNumeric(row.getValue())){
								sql += andSql + row.getCampo() + " = '" + row.getValue() + "'";
							}
							else{
								sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
							}
							andSql = " and ";
						}
					} // Fin While

				}
				
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);

				while (rs.next()) {

					Liquidacion Liquidacion = new Liquidacion();

					Liquidacion.setId_liquidacion(rs.getInt("id_liquidacion"));
					Liquidacion.setCod_trabajador(rs.getInt("cod_trabajador"));
					Liquidacion.setId_contrato(rs.getInt("id_contrato"));
					Liquidacion.setId_sociedad(rs.getInt("id_sociedad"));
					Liquidacion.setRut(rs.getString("rut"));
					Liquidacion.setPeriodo(rs.getInt("periodo"));
					Liquidacion.setFecha_pago(rs.getString("fecha_pago"));
					Liquidacion.setLugar_pago(rs.getString("lugar_pago"));
					Liquidacion.setId_nomina(rs.getInt("id_nomina"));
					Liquidacion.setTotal_pago(rs.getString("total_pago"));
					Liquidacion.setEstado_liquidacion(rs.getInt("estado_liquidacion"));

					lista.add(Liquidacion);

				}

			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			} finally {
				ps.close();
				db.close();
			}
			return lista;
		}// fin metodo get
	

	public static Liquidacion getLiquidacionByCod_trabajador(int cod_trabajador) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		Liquidacion Liquidacion = new Liquidacion();

		try {
			sql = "select * from sw_liquidacion " + " where cod_trabajador = '" + cod_trabajador + "' ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				Liquidacion.setId_liquidacion(rs.getInt("id_liquidacion"));
				Liquidacion.setCod_trabajador(rs.getInt("cod_trabajador"));
				Liquidacion.setId_contrato(rs.getInt("id_contrato"));
				Liquidacion.setId_sociedad(rs.getInt("id_sociedad"));
				Liquidacion.setRut(rs.getString("rut"));
				Liquidacion.setPeriodo(rs.getInt("periodo"));
				Liquidacion.setFecha_pago(rs.getString("fecha_pago"));
				Liquidacion.setLugar_pago(rs.getString("lugar_pago"));
				Liquidacion.setId_nomina(rs.getInt("id_nomina"));
				Liquidacion.setTotal_pago(rs.getString("total_pago"));
				Liquidacion.setEstado_liquidacion(rs.getInt("estado_liquidacion"));

			}

		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return Liquidacion;
	}// fin metodo

	// get por id trabajdor con inner join
	public static LiquidacionTrabajador getLiquidacionTrabajadorByCod_trabajador(int id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		LiquidacionTrabajador LiquidacionTrabajador = new LiquidacionTrabajador();

		try {
			sql = "select * from sw_liquidacion liq" + "inner join trabajadores tr on (liq.cod_trabajador = tr.codigo)"
					+ "inner join contratos cont on (liq.id_contrato = cont.id)" + " where tr.codigo = '" + id + "' ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				LiquidacionTrabajador.setId_liquidacion(rs.getInt("id_liquidacion"));
				LiquidacionTrabajador.setCod_trabajador(rs.getInt("cod_trabajador"));
				LiquidacionTrabajador.setId_contrato(rs.getInt("id_contrato"));
				LiquidacionTrabajador.setId_sociedad(rs.getInt("id_sociedad"));
				LiquidacionTrabajador.setRut(rs.getString("rut"));
				LiquidacionTrabajador.setPeriodo(rs.getInt("periodo"));
				LiquidacionTrabajador.setFecha_pago(rs.getString("fecha_pago"));
				LiquidacionTrabajador.setLugar_pago(rs.getString("lugar_pago"));
				LiquidacionTrabajador.setId_nomina(rs.getInt("id_nomina"));
				LiquidacionTrabajador.setTotal_pago(rs.getString("total_pago"));
				LiquidacionTrabajador.setEstado_liquidacion(rs.getInt("estado_liquidacion"));

			}

		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return LiquidacionTrabajador;

	}// fin metodo

	// get liquidaciones de trabajadores con filtros
	public static ArrayList<LiquidacionTrabajador> getLiquidacionTrabajadorWithFilter(ArrayList<filterSql> filter)
			throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<LiquidacionTrabajador> lista = new ArrayList<LiquidacionTrabajador>();

		try {

			sql = "SELECT * FROM sw_liquidacion liq INNER JOIN trabajadores tr ON (liq.cod_trabajador = tr.codigo)"
					+ " LEFT JOIN sw_liquidacionDetalle ld ON (liq.id_contrato = ld.idContrato and liq.periodo = ld.periodo and ld.idConcepto = 101) "
					+ " LEFT JOIN contratos cont ON (liq.id_contrato = cont.id)"
					+ " LEFT JOIN sw_finiquitos fiq ON (fiq.periodo = liq.periodo and liq.id_contrato = fiq.id_contrato)   ";

			String sql_orderBy = "";
			
			// Si contiene datos asignarlo al WHERE
			if (filter.size() > 0) {
				String andSql = "";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext()) {
					filterSql row = f.next();

					if (!row.getValue().equals("")) {

						if (row.getCampo().endsWith("periodo")) {

							SimpleDateFormat formatter = new SimpleDateFormat("MM-yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMM");
							sql += andSql + row.getCampo() + " ='"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						}

						else if (row.getCampo().endsWith("fecha_pago")) {

							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
							sql += andSql + row.getCampo() + " ='"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						}
						else if (GeneralUtility.isNumeric(row.getValue())){
							sql += andSql + row.getCampo() + " = '" + row.getValue() + "'";
						}
						else if (GeneralUtility.isArray(row.getValue())){
							sql += andSql + row.getCampo() + " in ( " + row.getValue() + " )";
						}
						else if( "_orderBy".equals(row.getCampo())){
							sql_orderBy = " ORDER BY " + row.getValue() + " DESC " ;
						}
						else{
							sql += andSql + row.getCampo() + " like '%" + row.getValue() + "'";
						}

						andSql = " and ";
					}
				} // Fin While

			}

			sql += sql_orderBy;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
            System.out.println(sql);  
			while (rs.next()) {

				LiquidacionTrabajador LiquidacionTrabajador = new LiquidacionTrabajador();

				LiquidacionTrabajador.setId_liquidacion(rs.getInt("id_liquidacion"));
				LiquidacionTrabajador.setCod_trabajador(rs.getInt("cod_trabajador"));
				LiquidacionTrabajador.setId_contrato(rs.getInt("id_contrato"));
				LiquidacionTrabajador.setId_sociedad(rs.getInt("id_sociedad"));
				LiquidacionTrabajador.setRut(rs.getString("rut"));
				LiquidacionTrabajador.setPeriodo(rs.getInt("periodo"));
				LiquidacionTrabajador.setFecha_pago(rs.getString("fecha_pago"));
				LiquidacionTrabajador.setLugar_pago(rs.getString("lugar_pago"));
				LiquidacionTrabajador.setId_nomina(rs.getInt("id_nomina"));
				LiquidacionTrabajador.setTotal_pago(rs.getString("total_pago"));
				LiquidacionTrabajador.setEstado_liquidacion(rs.getInt("estado_liquidacion"));
				LiquidacionTrabajador.setNombre(rs.getString("nombre"));
				LiquidacionTrabajador.setApellidoPaterno(rs.getString("apellidoPaterno"));
				LiquidacionTrabajador.setApellidoMaterno(rs.getString("apellidoMaterno"));
				LiquidacionTrabajador.setId(rs.getInt("id") );
				LiquidacionTrabajador.setIdHuertoContrato(rs.getString("idHuertoContrato"));
				LiquidacionTrabajador.setIdCECOContrato(rs.getString("idCECOContrato"));
				LiquidacionTrabajador.setCodigo(rs.getInt("codigo"));
				LiquidacionTrabajador.setFechaInicioActividad(rs.getString("fechaInicio_actividad"));
				LiquidacionTrabajador.setTotalLiquidoMes(rs.getString("valor"));
				LiquidacionTrabajador.setId_finiquito(rs.getString("id_finiquito"));
				LiquidacionTrabajador.setTotal_pago_finiquito(rs.getString("total_pago_finiquito"));

				
				lista.add(LiquidacionTrabajador);

			}

		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;

	}// fin metodo

	// get todo liquidacion trabajador con inner join
	public static ArrayList<LiquidacionTrabajador> getLiquidacionTrabajador() throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<LiquidacionTrabajador> lista = new ArrayList<LiquidacionTrabajador>();

		try {
			sql = "select * from sw_liquidacion liq" + "inner join trabajadores tr on (liq.cod_trabajador = tr.codigo)"
					+ "inner join contratos cont on (liq.id_contrato = cont.id)";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				LiquidacionTrabajador LiquidacionTrabajador = new LiquidacionTrabajador();

				LiquidacionTrabajador.setId_liquidacion(rs.getInt("id_liquidacion"));
				LiquidacionTrabajador.setCod_trabajador(rs.getInt("cod_trabajador"));
				LiquidacionTrabajador.setId_contrato(rs.getInt("id_contrato"));
				LiquidacionTrabajador.setId_sociedad(rs.getInt("id_sociedad"));
				LiquidacionTrabajador.setRut(rs.getString("rut"));
				LiquidacionTrabajador.setPeriodo(rs.getInt("periodo"));
				LiquidacionTrabajador.setFecha_pago(rs.getString("fecha_pago"));
				LiquidacionTrabajador.setLugar_pago(rs.getString("lugar_pago"));
				LiquidacionTrabajador.setId_nomina(rs.getInt("id_nomina"));
				LiquidacionTrabajador.setTotal_pago(rs.getString("total_pago"));
				LiquidacionTrabajador.setEstado_liquidacion(rs.getInt("estado_liquidacion"));

				lista.add(LiquidacionTrabajador);

			}

		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}// fin metodo

	// update Liquidacion
	public static boolean updateLiquidacion(Liquidacion Liquidacion) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ResultSet rs = null;

		try {

			int i = 1;

			sql = " SELECT id_liquidacion FROM sw_liquidacion  WHERE id_liquidacion = ? ";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++, Liquidacion.getId_liquidacion());
			rs = ps.executeQuery();

			if (!rs.next()) {
				i = 1;
				rs.close();
				ps.close();

				sql = " INSERT INTO sw_liquidacion (id_liquidacion) VALUES (?) ";
				ps = db.conn.prepareStatement(sql);
				ps.setInt(i++, Liquidacion.getId_liquidacion());
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				int key = 0;
				if (rs.next()) {
					key = rs.getInt(1);
				}

				Liquidacion.setId_liquidacion(key);
			}

			i = 1;
			rs.close();
			ps.close();

			sql = " UPDATE sw_liquidacion SET codigo_trabajador = ? , "
					+ " id_contrato = ? , id_sociedad = ? , rut = ? , periodo = ?, fecha_pago = ?, lugar_pago = ?, id_nomina = ?, total_pago = ?, estado_liquidacion = ? WHERE "
					+ " id_liquidacion = ? ";

			ps = db.conn.prepareStatement(sql);

			ps.setInt(i++, Liquidacion.getCod_trabajador());
			ps.setInt(i++, Liquidacion.getId_contrato());
			ps.setInt(i++, Liquidacion.getId_sociedad());
			ps.setString(i++, Liquidacion.getRut());
			ps.setInt(i++, Liquidacion.getPeriodo());
			ps.setString(i++, Liquidacion.getFecha_pago());
			ps.setString(i++, Liquidacion.getLugar_pago());
			ps.setInt(i++, Liquidacion.getId_nomina());
			ps.setString(i++, Liquidacion.getTotal_pago());
			ps.setInt(i++, Liquidacion.getEstado_liquidacion());
			ps.setInt(i++, Liquidacion.getId_liquidacion());

			ps.execute();

			return true;

		} catch (Exception e) {
			System.out.println("Error update Liquidacion: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}// fin metodo update

	// delete
	public static boolean deleteLiquidacionById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "DELETE FROM sw_liquidacion WHERE id_liquidacion=" + id;
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (Exception ex) {
			return false;
		} finally {
			db.conn.close();
		}
	}// fin metodo eliminar
	
	
	public static ArrayList<Liquidacion> getallTrabajaConLiqui(int cod)  throws Exception{
	PreparedStatement ps = null;
	String sql="";
	ArrayList<Liquidacion> lista = new ArrayList<Liquidacion>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql = "SELECT "
				+"sw.cod_trabajador, TR.nombre, TR.apellidoPaterno, TR.apellidoMaterno "
				+"FROM "
				+"sw_liquidacion sw "
				+"INNER JOIN "
				+"trabajadores TR ON TR.codigo = sw.cod_trabajador "
				+"WHERE "
				+"sw.estado_liquidacion = 0 "
				+"AND id_sociedad = "+cod+" "
				+"GROUP BY sw.cod_trabajador order by sw.cod_trabajador asc";
			
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			Liquidacion tr = new Liquidacion();
			tr.setCodigo(rs.getString("codigo"));
			tr.setNombre(rs.getString("nombre"));
			tr.setAp_paterno(rs.getString("apellidoPaterno"));
			tr.setAp_materno(rs.getString("apellidoMaterno"));
			lista.add(tr);
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
	
	
	public static ArrayList<Liquidacion> LoadSelectIdContratoLIqui(int cod ) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<Liquidacion> data = new ArrayList<Liquidacion>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="select distinct sw.id_contrato as idcontrat, co.fechaInicio_actividad from sw_liquidacion sw "
					+"inner join contratos co on co.id = sw.id_contrato "
					+"where sw.cod_trabajador = "+cod+"";
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				Liquidacion e = new Liquidacion();
				
				e.setIdcontra(rs.getInt("idcontrat"));
				e.setFecha_inicio_actividad(rs.getString("fechaInicio_actividad"));
				
	
			
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
	
	
	public static ArrayList<Liquidacion> LoadLiquidacionEliminar(String soci,String periodo,String idcontrato,String codtrabajador,String tipo_division, String tipo_subdivision,String grupo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<Liquidacion> data = new ArrayList<Liquidacion>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="select "
			+"sw.id_liquidacion,sw.id_contrato, sw.periodo,sw.cod_trabajador,"
			+"UPPER(TRIM(CONCAT(tr.apellidoPaterno, ' ', tr.apellidoMaterno,' ', tr.nombre))) as nombrecompleto,"
			+"DATE_FORMAT(sw.fecha_pago, '%d-%m-%Y') as fechaPago,"
			+"sw.total_pago "
			+"from sw_liquidacion sw "
			+"inner join trabajadores tr on tr.codigo = sw.cod_trabajador "
			+"where 1 = 1 ";

			if("null".equals(periodo)){}else{sql += " and sw.id_sociedad = "+soci+"";}
			if("null".equals(codtrabajador)){}else{sql += " and sw.cod_trabajador = "+codtrabajador+"";}
			if("null".equals(periodo)){}else{sql += " and sw.periodo = "+periodo+"";}
			
			if("null".equals(idcontrato)){}else{sql += " and sw.id_contrato = "+idcontrato+"";}
			if("null".equals(tipo_division)){}else{sql += " and tr.idHuerto = '"+tipo_division+"'";}
			if("null".equals(tipo_subdivision)){}else{sql += " and tr.idZona = '"+tipo_subdivision+"'";}
			if("null".equals(grupo)){}else{sql += " and tr.idCECO = '"+grupo+"'";}
			
			
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				Liquidacion e = new Liquidacion();
				
				e.setId_liquidacion(rs.getInt("id_liquidacion"));
				e.setId_contrato(rs.getInt("id_contrato"));
				e.setPeriodo(rs.getInt("periodo"));
				e.setCod_trabajador(rs.getInt("cod_trabajador"));
				e.setNombre(rs.getString("nombrecompleto"));
				e.setFecha_pago(rs.getString("fechaPago"));
				e.setTotalapago(rs.getInt("total_pago"));
				
				
	
			
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
	
	public static boolean eliminarLIQUITRAB(int periodo, int id_liquidacion, int cod_trabajador, int id_contrato) throws  Exception{
	PreparedStatement ps = null;
	PreparedStatement ps2 = null;
	String sql = "";
	String sql2 = "";
	ConnectionDB  db = new ConnectionDB();	
	try {
		sql = "delete from sw_liquidacionDetalle  where codTrabajador = "+cod_trabajador+" and periodo = "+periodo+" and idContrato = "+id_contrato+"";
		ps = db.conn.prepareStatement(sql);
		ps.execute();
		
		sql2 = "delete from sw_liquidacion where id_liquidacion = "+id_liquidacion+" ";
		ps2 = db.conn.prepareStatement(sql2);
		ps2.execute();
				
		return true;
	} catch (SQLException e) {
		System.out.println("Error:" + e.getMessage());
		e.printStackTrace();
	}catch (Exception e) {
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		ps2.close();
		db.close();
	}		
	return false;
}
	
	// get liquidaciones de trabajadores con filtros
		public static ArrayList<LiquidacionTrabajador> getLiquidacionTrabajadorWithFilter2(ArrayList<filterSql> filter)
				throws Exception {

			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();

			ArrayList<LiquidacionTrabajador> lista = new ArrayList<LiquidacionTrabajador>();

			try {

				sql = "SELECT * FROM sw_liquidacion liq INNER JOIN trabajadores tr ON (liq.cod_trabajador = tr.codigo)"
						+ " LEFT JOIN sw_liquidacionDetalle ld ON (liq.id_contrato = ld.idContrato and liq.periodo = ld.periodo and ld.idConcepto = 101) "
						+ " LEFT JOIN contratos cont ON (liq.id_contrato = cont.id)"
						+ " LEFT JOIN sw_finiquitos fiq ON (fiq.periodo = liq.periodo and liq.id_contrato = fiq.id_contrato)   ";

				String sql_orderBy = "";
				
				// Si contiene datos asignarlo al WHERE
				if (filter.size() > 0) {
					String andSql = "";
					andSql += " WHERE ";
					Iterator<filterSql> f = filter.iterator();

					while (f.hasNext()) {
						filterSql row = f.next();

						if (!row.getValue().equals("")) {
							
							if (row.getCampo().endsWith("liqufini")) {
								
								if("null".equals(row.getValue())){
						        	
						        }else{
						        	if("1".equals(row.getValue())){
						        		sql += andSql + "cont.EstadoContrato = 1 ";
						        	}else if("2".equals(row.getValue())){
						        		sql += andSql + "cont.EstadoContrato = 0 ";
						        	}
						        	
						        	
						        }
							}

							else if (row.getCampo().endsWith("periodo")) {

								SimpleDateFormat formatter = new SimpleDateFormat("MM-yyyy");
								SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMM");
								sql += andSql + row.getCampo() + " ='"
										+ sqlDate.format(formatter.parse(row.getValue())) + "'";
							}
							
							else if (row.getCampo().endsWith("fecha_pago")) {

								SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
								SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
								sql += andSql + row.getCampo() + " ='"
										+ sqlDate.format(formatter.parse(row.getValue())) + "'";
							}
							else if (GeneralUtility.isNumeric(row.getValue())){
								sql += andSql + row.getCampo() + " = '" + row.getValue() + "'";
							}
							else if (GeneralUtility.isArray(row.getValue())){
								sql += andSql + row.getCampo() + " in ( " + row.getValue() + " )";
							}
							else if( "_orderBy".equals(row.getCampo())){
								sql_orderBy = " ORDER BY " + row.getValue() + " DESC " ;
							}
							else{
								sql += andSql + row.getCampo() + " like '%" + row.getValue() + "'";
							}

							andSql = " and ";
						}
					} // Fin While

				}
				 System.out.println(sql);  
				sql += sql_orderBy;
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
	          
				while (rs.next()) {

					LiquidacionTrabajador LiquidacionTrabajador = new LiquidacionTrabajador();

					LiquidacionTrabajador.setId_liquidacion(rs.getInt("id_liquidacion"));
					LiquidacionTrabajador.setCod_trabajador(rs.getInt("cod_trabajador"));
					LiquidacionTrabajador.setId_contrato(rs.getInt("id_contrato"));
					LiquidacionTrabajador.setId_sociedad(rs.getInt("id_sociedad"));
					LiquidacionTrabajador.setRut(rs.getString("rut"));
					LiquidacionTrabajador.setPeriodo(rs.getInt("periodo"));
					LiquidacionTrabajador.setFecha_pago(rs.getString("fecha_pago"));
					LiquidacionTrabajador.setLugar_pago(rs.getString("lugar_pago"));
					LiquidacionTrabajador.setId_nomina(rs.getInt("id_nomina"));
					LiquidacionTrabajador.setTotal_pago(rs.getString("total_pago"));
					LiquidacionTrabajador.setEstado_liquidacion(rs.getInt("estado_liquidacion"));
					LiquidacionTrabajador.setNombre(rs.getString("nombre"));
					LiquidacionTrabajador.setApellidoPaterno(rs.getString("apellidoPaterno"));
					LiquidacionTrabajador.setApellidoMaterno(rs.getString("apellidoMaterno"));
					LiquidacionTrabajador.setId(rs.getInt("id") );
					LiquidacionTrabajador.setIdHuertoContrato(rs.getString("idHuertoContrato"));
					LiquidacionTrabajador.setIdCECOContrato(rs.getString("idCECOContrato"));
					LiquidacionTrabajador.setCodigo(rs.getInt("codigo"));
					LiquidacionTrabajador.setFechaInicioActividad(rs.getString("fechaInicio_actividad"));
					LiquidacionTrabajador.setTotalLiquidoMes(rs.getString("valor"));
					LiquidacionTrabajador.setId_finiquito(rs.getString("id_finiquito"));
					LiquidacionTrabajador.setTotal_pago_finiquito(rs.getString("total_pago_finiquito"));

					
					lista.add(LiquidacionTrabajador);

				}

			} catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			} finally {
				ps.close();
				db.close();
			}
			return lista;

		}// fin metodo

}
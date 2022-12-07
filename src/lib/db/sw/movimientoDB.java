package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import lib.classSW.movimiento;
import lib.db.ConnectionDB;
import lib.struc.filterSql;

public class movimientoDB {

	//Insert movimiento
	public static boolean insertMovimiento(movimiento movimiento) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try{

			sql = " INSERT INTO movimientos ( idTrabajador, fechaIngreso, fechaTermino, "
					+ " idTiposMovimiento, idContrato, sociedad ) "
					+ " VALUES (?,?,?,?,?,?) ";

			ps = db.conn.prepareStatement(sql);
			
			ps.setInt(1, movimiento.getIdTrabajador());
			ps.setString(2, convertStringToYYYYMMDD(movimiento.getFechaIngreso()));
			ps.setString(3, convertStringToYYYYMMDD(movimiento.getFechaTermino()));
			ps.setInt(4, movimiento.getIdTiposMovimiento());
			ps.setInt(5, movimiento.getIdContrato());
			ps.setInt(6, movimiento.getSociedad());
			
			ps.execute();

			return true;

		}catch(Exception e){

			System.out.println("Error insertMovimiento:" + e.getMessage());
			e.printStackTrace();

		}finally{
			db.conn.close();
		}

		return false;
	}



	//Actualizar movimiento
	public static boolean updateMovimiento(movimiento movimiento) throws  Exception{

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		int i = 1;

		try {

			sql = ""
					+ " UPDATE movimientos "
					+ " SET "
					+ " idTrabajador = ?, "
					+ " fechaIngreso = ?, "
					+ " fechaTermino = ?, "
					+ " sociedad = ?, "
					+ " idContrato = ?, "
					+ " idTiposMovimiento = ?, "
					+ " WHERE idMovimiento = ?";
			
			
			ps = db.conn.prepareStatement(sql);

			ps.setInt(i++, movimiento.getIdTrabajador());
			ps.setString(i++, convertStringToYYYYMMDD(movimiento.getFechaIngreso()));
			ps.setString(i++, convertStringToYYYYMMDD(movimiento.getFechaTermino()));
			ps.setInt(i++, movimiento.getSociedad());
			ps.setInt(i++, (movimiento.getIdContrato()));
			ps.setInt(i++, movimiento.getIdTiposMovimiento());
			ps.setInt(i++, movimiento.getIdMovimiento());
			
			ps.execute();

			return true;

		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return false;
	}

	//Borrar movimiento por Id

	//Obtener movimiento por Id

	//Obtener Todos los movimientos

	//Obtener movimientos por Id Trabajador

	//Obtener Todos los movimientos con filtros
	public static ArrayList<movimiento> getAllMovimiento(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {

		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<movimiento> lista = new ArrayList<movimiento>();

		try{

			stmt = db.conn.createStatement();
			sql = " SELECT * FROM movimientos ";

			if(filter.size() > 0){

				String andSql="";
				andSql += " WHERE ";

				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext()){

					filterSql row = f.next();

					if(!row.getValue().equals("")){

						if(row.getCampo().endsWith("_to")){
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0,  row.getCampo().length() - 3)+" <='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}

						else if(row.getCampo().endsWith("_from")){
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 5)+" >= '"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}

						else
							sql+=andSql+row.getCampo()+" like '%"+row.getValue()+"%'";
						andSql=" and ";

					}//Fin While

				}

			}//Fin if (filter size)

			if (!order.equals("")){
				sql += " order by ";
			}

			if (length > 0){
				sql += " limit " + start + "," + length + " ";
			}

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()){

				movimiento ct = new movimiento();

				ct.setIdMovimiento(rs.getInt("idMovimiento"));
				ct.setIdTrabajador(rs.getInt("idTrabajador"));
				ct.setFechaIngreso(rs.getString("fechaIngreso"));
				ct.setFechaTermino(rs.getString("fechaTermino"));
				ct.setIdTiposMovimiento(rs.getInt("idTiposMovimiento"));
				ct.setIdContrato(rs.getInt("idContrato"));
				ct.setSociedad(rs.getInt("sociedad"));
				
				lista.add(ct);

			}
			rs.close();
			stmt.close();
			db.conn.close();
			//Fin Try
		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getAllMovimiento: " + e.getMessage());

		}finally{
			db.close();
		}

		return lista;
	}

	//Obtener todos los movimientos totales
	public static int getAllMovimiento(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT count(1) FROM movimientos ";

			if (filter.size() > 0) {
				String andSql="";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext())
				{
					filterSql row = f.next();
					if (!row.getValue().equals(""))
					{
						if (row.getCampo().endsWith("_to"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 3)+" <='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else if(row.getCampo().endsWith("_from"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 5)+" >='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else
							sql+=andSql+row.getCampo()+" like'%"+row.getValue()+"%'";
						andSql=" and ";
					}
				}

			}

			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				total = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			db.conn.close();


		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getTrabajadorAll: " + e.getMessage());
		} finally {
			db.close();
		}

		return total;
	}

	//Obtener Todos los movimientos By IdTrabajador con filtros
	public static ArrayList<movimiento> getMovimientoByIdTrabajador(String id) throws Exception {

		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<movimiento> lista = new ArrayList<movimiento>();

		try{

			stmt = db.conn.createStatement();
			sql = " SELECT * FROM movimientos WHERE idTrabajador = "+id+" ";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()){

				movimiento ct = new movimiento();

				ct.setIdMovimiento(rs.getInt("idMovimiento"));
				ct.setIdTrabajador(rs.getInt("idTrabajador"));
				ct.setFechaIngreso(rs.getString("fechaIngreso"));
				ct.setFechaTermino(rs.getString("fechaTermino"));
				ct.setIdTiposMovimiento(rs.getInt("idTiposMovimiento"));
				ct.setIdContrato(rs.getInt("idContrato"));
				ct.setSociedad(rs.getInt("sociedad"));
				
				lista.add(ct);

			}
			rs.close();
			stmt.close();
			db.conn.close();
			//Fin Try
		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getmovimientoByIdTrabajador: " + e.getMessage());

		}finally{
			db.close();
		}

		return lista;
	}


	//Obtener Todos los movimientos By IdTrabajador para Cambio Empresa
	public static ArrayList<movimiento> getMovimientoByIdTrabajadorToCambioEmpresa(String id, ArrayList<filterSql> filter, String order, int start, int length) throws Exception {

		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<movimiento> lista = new ArrayList<movimiento>();

		try{

			stmt = db.conn.createStatement();
			sql = " SELECT * FROM movimientos WHERE idTrabajador = "+id+" ";


			sql += " ORDER BY fechaIngreso DESC";



			sql += " LIMIT " + 0 + "," + 1 + " ";


			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()){

				movimiento ct = new movimiento();

				ct.setIdMovimiento(rs.getInt("idMovimiento"));
				ct.setIdTrabajador(rs.getInt("idTrabajador"));
				ct.setFechaIngreso(rs.getString("fechaIngreso"));
				ct.setFechaTermino(rs.getString("fechaTermino"));
				ct.setIdTiposMovimiento(rs.getInt("idTiposMovimiento"));
				ct.setIdContrato(rs.getInt("idContrato"));
				ct.setSociedad(rs.getInt("sociedad"));
				
				lista.add(ct);

			}
			rs.close();
			stmt.close();
			db.conn.close();
			//Fin Try
		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getmovimientoByIdTrabajadorToCambioEmpresa: " + e.getMessage());

		}finally{
			db.close();
		}

		return lista;
	}

	//Obtener Todos los movimientos By IdTrabajador para Separacion
	public static ArrayList<movimiento> getMovimientoByIdTrabajadorToSeparacion(String id, ArrayList<filterSql> filter, String order, int start, int length) throws Exception {

		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<movimiento> lista = new ArrayList<movimiento>();

		try{

			stmt = db.conn.createStatement();
			sql = " SELECT * FROM movimientos WHERE idTrabajador = "+id+ " "
					+ " AND fechaTermino IS NULL ";


			sql += " ORDER BY fechaIngreso DESC";



			sql += " LIMIT " + 0 + "," + 1 + " ";


			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()){

				movimiento ct = new movimiento();

				ct.setIdMovimiento(rs.getInt("idMovimiento"));
				ct.setIdTrabajador(rs.getInt("idTrabajador"));
				ct.setFechaIngreso(rs.getString("fechaIngreso"));
				ct.setFechaTermino(rs.getString("fechaTermino"));
				ct.setIdTiposMovimiento(rs.getInt("idTiposMovimiento"));
				ct.setIdContrato(rs.getInt("idContrato"));
				ct.setSociedad(rs.getInt("sociedad"));
				
				lista.add(ct);

			}
			rs.close();
			stmt.close();
			db.conn.close();
			//Fin Try
		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getmovimientoByIdTrabajadorSeparacion: " + e.getMessage());

		}finally{
			db.close();
		}

		return lista;
	}


	

	//Obtener movimiento por Id
	public static movimiento getMovimientoById(String id)  throws Exception{

		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();

		movimiento ct = new movimiento();

		try{
			sql = "SELECT * FROM movimientos WHERE idMovimiento = '"+id+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){

				ct.setIdMovimiento(rs.getInt("idMovimiento"));
				ct.setIdTrabajador(rs.getInt("idTrabajador"));
				ct.setFechaIngreso(rs.getString("fechaIngreso"));
				ct.setFechaTermino(rs.getString("fechaTermino"));
				ct.setIdTiposMovimiento(rs.getInt("idTiposMovimiento"));
				ct.setIdContrato(rs.getInt("idContrato"));
				ct.setSociedad(rs.getInt("sociedad"));
				
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return ct;
	}


	//Obtener Ultimo movimiento activo por Id del Trabajador
	public static movimiento getUltimoMovimientoActivoByIdTrabajador(String id)  throws Exception{

		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();

		movimiento ct = new movimiento();

		try{

			sql = " SELECT * FROM movimientos WHERE idTrabajador = "+id+" ";
			sql += " ORDER BY fechaIngreso DESC";
			sql += " LIMIT " + 0 + "," + 1 + " ";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){

				ct.setIdMovimiento(rs.getInt("idMovimiento"));
				ct.setIdTrabajador(rs.getInt("idTrabajador"));
				ct.setFechaIngreso(rs.getString("fechaIngreso"));
				ct.setFechaTermino(rs.getString("fechaTermino"));
				ct.setIdTiposMovimiento(rs.getInt("idTiposMovimiento"));
				ct.setIdContrato(rs.getInt("idContrato"));
				ct.setSociedad(rs.getInt("sociedad"));
				
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return ct;
	}



	/**
	 * Retorna String Convertido en YYYY-MM-DD Si el valor es null o vacio retorna null
	 * @param fecha
	 * @return String
	 * @throws ParseException
	 */
	public static String convertStringToYYYYMMDD(String fecha) throws ParseException{

		if(fecha == null || fecha.isEmpty()){
			return null;
		}
		
	    
		 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		 SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
		 java.util.Date date = output.parse(fecha.replace("/", "-"));
		 
		 if (fecha.equals(output.format(date))) {
		        return fecha;
		    }
		 
		 java.util.Date data = sdf.parse(fecha.replace("/", "-"));
		 String formattedDate = output.format(data);
		 
		 return formattedDate;

	}
	
	
	
	
}

package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import lib.classSW.sociedad;
import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;

public class sociedadDB {


	//Insert sociedad
	public static boolean insertSociedad(sociedad sociedad) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try{

			sql = "INSERT INTO sociedad ( sociedad, denominacionSociedad ) "
					+ " VALUES (?,?) ";

			ps = db.conn.prepareStatement(sql);
			ps.setString(1, sociedad.getSociedad());
			ps.setString(2, sociedad.getDenominacionSociedad());


			ps.execute();

			return true;

		}catch(Exception ex){

		}finally{
			db.conn.close();
		}

		return false;
	}

	//Actualizar sociedad

	//Borrar sociedad por Id

	//Obtener sociedad por Id
	public static sociedad getSociedadById(String id)  throws Exception{

		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();

		sociedad sc = new sociedad();

		try{
			sql = "SELECT * FROM sociedad WHERE idSociedad = '"+id+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){

				sc.setIdSociedad(rs.getInt("idSociedad"));
				sc.setSociedad(rs.getString("sociedad"));
				sc.setDenominacionSociedad(rs.getString("DenominacionSociedad"));
				
			}			
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return sc;
	}

	//Obtener Todos las sociedades
	public static ArrayList<sociedad> getSociedad(ArrayList<filterSql> filter) throws Exception {
		
		PreparedStatement ps = null;
		String sql="";
		ArrayList<sociedad> lista = new ArrayList<sociedad>();
		ConnectionDB db = new ConnectionDB();
		
		try{
			
			sql = " SELECT * FROM sociedad ";
			
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
									} else if (row.getCampo().endsWith("_from")) {

										SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
										SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
										sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='"
												+ sqlDate.format(formatter.parse(row.getValue())) + "'";
									} else if (row.getValue().split(",").length >= 2) {
										sql += andSql + row.getCampo() + " in ( " + row.getValue() + ") ";
									} else {
										if ( GeneralUtility.isNumeric(row.getValue()) ){
											sql += andSql + row.getCampo() + " = " + row.getValue() + "";
										}else{
										sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
										}
									}

									andSql = " and ";
								}
							} // Fin While

						}
			
			sql += " ORDER BY denominacionSociedad ASC ";
						
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){
				sociedad sc = new sociedad();
				sc.setIdSociedad(rs.getInt("idSociedad"));
				sc.setSociedad(rs.getString("sociedad"));
				sc.setSociedad(rs.getString("denominacionSociedad"));
				sc.setCodigoSociedad(rs.getString("sociedad"));
				lista.add(sc);
			}
			

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		
		return lista;
		
	}
	
	//Obtener Todos las sociedades
	public static ArrayList<sociedad> getSociedad2(ArrayList<filterSql> filter) throws Exception {
		
		PreparedStatement ps = null;
		String sql="";
		ArrayList<sociedad> lista = new ArrayList<sociedad>();
		ConnectionDB db = new ConnectionDB();
		
		try{
			
			sql = " SELECT * FROM sociedad ";
			
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
									} else if (row.getCampo().endsWith("_from")) {

										SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
										SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
										sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='"
												+ sqlDate.format(formatter.parse(row.getValue())) + "'";
									} else if (row.getValue().split(",").length >= 2) {
										sql += andSql + row.getCampo() + " in ( " + row.getValue() + ") ";
									} else {
										if ( GeneralUtility.isNumeric(row.getValue()) ){
											sql += andSql + row.getCampo() + " = " + row.getValue() + "";
										}else{
										sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
										}
									}

									andSql = " and ";
								}
							} // Fin While

						}
			
			sql += " ORDER BY denominacionSociedad ASC ";
						
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){
				sociedad sc = new sociedad();
				sc.setIdSociedad(rs.getInt("idSociedad"));
				sc.setSociedad(rs.getString("sociedad"));
				sc.setDenominacionSociedad(rs.getString("denominacionSociedad"));
				lista.add(sc);
			}
			

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		
		return lista;
		
	}

	public static sociedad getSociedadByCod(String soc) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();

		sociedad sc = new sociedad();

		try{
			sql = "SELECT * FROM sociedad WHERE sociedad = '"+soc+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){

				sc.setIdSociedad(rs.getInt("idSociedad"));
				sc.setSociedad(rs.getString("sociedad"));
				sc.setDenominacionSociedad(rs.getString("DenominacionSociedad"));
				
			}			
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return sc;
	}

	

	//Obtener Todos las sociedades con Filtros

	//Obtener Todos los sociedades totales



}

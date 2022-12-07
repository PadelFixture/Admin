package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import lib.classSW.sw_documentos;
import lib.db.ConnectionDB;
import lib.struc.filterSql;

public class sw_documentosDB {
	
	//update Documentos
		public static boolean updateDocumentos(sw_documentos sw_documentos) throws  Exception{

			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB  db = new ConnectionDB();	

			try {
				sql = "UPDATE sw_documentos SET"
						+ " nombre = '"+sw_documentos.getNombre()+"' "
						//+ " file = '"+sw_documentos.getFile()+"' "
						+ " where idTemplate = '"+sw_documentos.getIdTemplate()+"'";
				
				ps = db.conn.prepareStatement(sql);
				ps.execute();

				return true;

			}catch (SQLException e) {
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
		//Insert Documentos
		public static boolean insertDocumentos(sw_documentos sw_documentos) throws Exception{

			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			
			try{
				sql = "INSERT INTO sw_documentos (nombre)"
						+ " VALUES (?)";
				
				ps = db.conn.prepareStatement(sql);
				ps.setString(1, sw_documentos.getNombre());
				
				ps.execute();
				
				return true;
				
			}catch(Exception e){

				System.out.println("Error insertDocumentos:" + e.getMessage());
				e.printStackTrace();

			}finally{
				db.conn.close();
				ps.close();
			}
			return false;
		}
		//delete Documentos
		public static boolean deleteDocumentos(int id) throws Exception{
			PreparedStatement ps = null;
			String sql= "";
			ConnectionDB db = new ConnectionDB();
			
			try{
				sql = "DELETE FROM sw_documentos WHERE idTemplate = ?";
				
				ps = db.conn.prepareStatement(sql);
				ps.setInt(1, id);
				
				ps.execute();
				
				return true;
			}catch(Exception e){

				System.out.println("Error insertDocumentos:" + e.getMessage());
				e.printStackTrace();

			}finally{
				db.conn.close();
				ps.close();
			}
			return false;
		}
		
		//get all documentos
	public static ArrayList<sw_documentos> getAllDocu() {
		ConnectionDB db = new ConnectionDB();
		ArrayList<sw_documentos> lista = new ArrayList<sw_documentos>();
		Statement stmt = null;
		String sql = "";
		try {
			sql = "Select * from user";
			stmt = db.conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				sw_documentos docu = new sw_documentos();
				docu.setIdTemplate(rs.getInt("idTemplate"));
				docu.setNombre(rs.getString("nombre"));
				lista.add(docu);
			}
			stmt.close();
			rs.close();
		} catch (Exception ex) {
			System.out.println("getAllUsers: " + ex.getMessage());
		}
		return lista;
	}
		
	
	//Obtener Todos los documentos con filtros
		public static ArrayList<sw_documentos> getAllDocumentos(ArrayList<filterSql> filter, String order, int start, int length)  throws Exception{

			Statement stmt = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();

			ArrayList<sw_documentos> lista = new ArrayList<sw_documentos>();


			try{

				stmt = db.conn.createStatement();
				sql = " SELECT * FROM sw_documentos ";

				if (filter.size() > 0) {

					String andSql="";

					andSql += " WHERE ";

					Iterator<filterSql> f = filter.iterator();



					while (f.hasNext()){

						filterSql row = f.next();

						if (!row.getValue().equals("")){

							if (row.getCampo().endsWith("_to")){

								SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
								SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
								sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 3)+" <='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
							}

							else if(row.getCampo().endsWith("_from")){

								SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
								SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
								sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 5)+" >='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
							}

							else

								sql+=andSql+row.getCampo()+" like '%"+row.getValue()+"%'";

							andSql=" and ";
						}
					}//Fin While



				}

				if (!order.equals("")) {
					sql += " order by ";
				}



				if (length > 0) {
					sql += " limit " + start + "," + length + " ";
					
				}

				ResultSet rs = stmt.executeQuery(sql);

				while (rs.next()) {

					sw_documentos dc = new sw_documentos();

					dc.setIdTemplate(rs.getInt("idTemplate"));
					dc.setNombre(rs.getString("nombre"));
					
					
					lista.add(dc);

				}
				rs.close();
				stmt.close();
				db.conn.close();
				//Fin Try
			} catch (SQLException e) {

				System.out.println("Error: " + e.getMessage());
				System.out.println("sql: " + sql);
				throw new Exception("getTrabajadores: " + e.getMessage());

			} finally {
				db.close();
			}

			//Retornar Lista de Documentos
			return lista;

		}//Fin getAllDocumentos
	
	
}






















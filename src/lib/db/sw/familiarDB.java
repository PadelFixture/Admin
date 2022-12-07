package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Iterator;

import lib.classSW.contrato;
import lib.classSW.sw_familiar;
import lib.db.ConnectionDB;
import lib.struc.filterSql;

public class familiarDB {

	//insert familiar
	public static boolean insertFamiliar (sw_familiar sw_familiar) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try{
			sql = "INSERT into sw_familiar "
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, sw_familiar.getIdFamiliar());
			ps.setString(2, sw_familiar.getRutFamiliar());
			ps.setInt(3, sw_familiar.getTipoCarga());
			ps.setString(4, sw_familiar.getApellidoPaternoFamiliar());
			ps.setString(5, sw_familiar.getTramoFamiliar());
			ps.setString(6, sw_familiar.getApellidoMaternoFamiliar());
			ps.setInt(7, sw_familiar.getMontoFamiliar());
			ps.setString(8, sw_familiar.getNombreFamiliar());
			ps.setInt(9, sw_familiar.getRetroActivoFamiliar());
			ps.setInt(10, sw_familiar.getPeriodo());
			ps.setString(11, convertStringToYYYYMMDD(sw_familiar.getfNacimientoFamiliar()));
			ps.setString(12, convertStringToYYYYMMDD(sw_familiar.getFechaInicioFamiliar()));
			ps.setString(13, convertStringToYYYYMMDD(sw_familiar.getFechaFinFamiliar()));
			ps.setInt(14, sw_familiar.getParentesco());
			ps.setInt(15, sw_familiar.getCodTrabajador());
			ps.setInt(16, sw_familiar.getRegistro());
			ps.setInt(17, sw_familiar.getDuplo());
			ps.setInt(18, sw_familiar.getNormal());
			ps.setInt(19, 0);
			
			ps.execute();

			return true;
		}catch(Exception e){

			System.out.println("Error insertFamiliar:" + e.getMessage());
			e.printStackTrace();

		}finally{
			ps.close();
			db.conn.close();
		}
		return false;
	}
	
	
	public static boolean actualizarFamiliarContrato(sw_familiar sw_familiar) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;
		sw_familiar familiar = familiarDB.getFamiliarById(sw_familiar.getIdFamiliar()+"");
		contrato ultimoContrato = contratoDB.getUltimoContratoActivoByIdTrabajador(familiar.getCodTrabajador()+"");
		
		try{
			
			sql = " UPDATE sw_familiar SET idContrato = ? WHERE idFamiliar = ? ";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, ultimoContrato.getId());
			ps.setString(2, sw_familiar.getIdFamiliar()+"");
			ps.execute();

			return true;
		}catch(Exception e){

			System.out.println("Error insertFamiliar:" + e.getMessage());
			e.printStackTrace();

		}finally{
			ps.close();
			db.conn.close();
		}
		return false;
	}
	
	


	//Obtener Todos los Familiares By IdTrabajador con filtros
	public static ArrayList<sw_familiar> getFamiliarByIdTrabajador(String id, ArrayList<filterSql> filter) throws Exception {

		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<sw_familiar> lista = new ArrayList<sw_familiar>();

		try{

			stmt = db.conn.createStatement();
			sql = " SELECT * FROM sw_familiar WHERE codTrabajador = "+id+" ORDER BY apellidoPaternoFamiliar ASC";

//			if(filter.size() > 0){
//
//				String andSql="";
//				andSql += " ";
//
//				Iterator<filterSql> f = filter.iterator();
//
//				while (f.hasNext()){
//
//					filterSql row = f.next();
//
//					if(!row.getValue().equals("")){
//
//						if(row.getCampo().endsWith("_to")){
//							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//							SimpleDateFormat sqlString = new SimpleDateFormat("yyyyMMdd");
//							sql+=andSql+row.getCampo().substring(0,  row.getCampo().length() - 3)+" <='"+ sqlString.format(formatter.parse(row.getValue()))+"'";
//						}
//
//						else if(row.getCampo().endsWith("_from")){
//							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//							SimpleDateFormat sqlString = new SimpleDateFormat("yyyyMMdd");
//							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 5)+" >= '"+ sqlString.format(formatter.parse(row.getValue()))+"'";
//						}
//
//						else
//							sql+=andSql+row.getCampo()+" like '%"+row.getValue()+"%'";
//						andSql=" and ";
//
//					}//Fin While
//
//				}
//
//			}//Fin if (filter size)

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()){

				sw_familiar familiar = new sw_familiar();

				familiar.setApellidoMaternoFamiliar(rs.getString("apellidoMaternoFamiliar"));
				familiar.setApellidoPaternoFamiliar(rs.getString("apellidoPaternoFamiliar"));
				familiar.setFechaFinFamiliar(rs.getString("fechaFinFamiliar"));
				familiar.setFechaInicioFamiliar(rs.getString("fechaInicioFamiliar"));
				familiar.setfNacimientoFamiliar(rs.getString("fNacimientoFamiliar")); 
				familiar.setIdFamiliar(rs.getInt("idFamiliar"));
				familiar.setCodTrabajador(rs.getInt("codTrabajador"));
				familiar.setMontoFamiliar(rs.getInt("montoFamiliar"));
				familiar.setNombreFamiliar(rs.getString("nombreFamiliar"));
				familiar.setParentesco(rs.getInt("parentesco"));
				familiar.setPeriodo(rs.getInt("periodo"));
				familiar.setRetroActivoFamiliar(rs.getInt("retroActivoFamiliar"));
				familiar.setRutFamiliar(rs.getString("rutFamiliar"));
				familiar.setTipoCarga(rs.getInt("tipoCarga"));
				familiar.setTramoFamiliar(rs.getString("tramoFamiliar"));
				familiar.setRegistro(rs.getInt("registro"));
				familiar.setDuplo(rs.getInt("duplo"));
				familiar.setNormal(rs.getInt("normal"));
				familiar.setIdContrato(rs.getInt("idContrato"));
				
				lista.add(familiar);

			}
			rs.close();
			stmt.close();
			db.conn.close();
			//Fin Try
		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getFamiliarByIdTrabajador: " + e.getMessage());

		}finally{
			db.close();
		}

		return lista;
	}




	//Update familiar
		public static boolean updateFamiliar(sw_familiar sw_familiar) throws  Exception{

			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB  db = new ConnectionDB();	
			int i = 1;

			try {

				sql = " UPDATE sw_familiar SET "
					+ " rutFamiliar = ?, "
					+ " tipoCarga = ?, "
					+ " apellidoPaternoFamiliar = ?, "
					+ " tramoFamiliar = ?, "
					+ " apellidoMaternoFamiliar = ?, "
					+ " montoFamiliar = ?, "
					+ " nombreFamiliar = ?, "
					+ " retroActivoFamiliar = ?, "
					+ " periodo = ?, "
					+ " fNacimientoFamiliar = ?, "
					+ " fechaInicioFamiliar = ?, "
					+ " fechaFinFamiliar = ?, "
					+ " parentesco = ?, "
					+ " codTrabajador = ?, "
					+ " normal = ?, "
					+ " registro = ?, "
					+ " duplo = ? "
					+ " WHERE idFamiliar = ?";
					
					ps = db.conn.prepareStatement(sql);
					
					ps.setString(i++, sw_familiar.getRutFamiliar());
					ps.setInt(i++, sw_familiar.getTipoCarga());
					ps.setString(i++, sw_familiar.getApellidoPaternoFamiliar());
					ps.setString(i++, sw_familiar.getTramoFamiliar());
					ps.setString(i++, sw_familiar.getApellidoMaternoFamiliar());
					ps.setInt(i++, sw_familiar.getMontoFamiliar());
					ps.setString(i++, sw_familiar.getNombreFamiliar());
					ps.setInt(i++, sw_familiar.getRetroActivoFamiliar());
					ps.setInt(i++, sw_familiar.getPeriodo());
					ps.setString(i++, convertStringToYYYYMMDD(sw_familiar.getfNacimientoFamiliar()));
					ps.setString(i++, convertStringToYYYYMMDD(sw_familiar.getFechaInicioFamiliar()));
					ps.setString(i++, convertStringToYYYYMMDD(sw_familiar.getFechaFinFamiliar()));
					ps.setInt(i++, sw_familiar.getParentesco());
					ps.setInt(i++, sw_familiar.getCodTrabajador());
					ps.setInt(i++, sw_familiar.getNormal());
					ps.setInt(i++, sw_familiar.getRegistro());
					ps.setInt(i++, sw_familiar.getDuplo());
					ps.setInt(i++, sw_familiar.getIdFamiliar());
				
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
	
	
		//Get femiliar por Idfamiliar
		public static sw_familiar getFamiliarById(String idFamiliar)  throws Exception{

			PreparedStatement ps = null;
			String sql="";
			ConnectionDB db = new ConnectionDB();

			sw_familiar familiar = new sw_familiar();

			try{
				sql = "SELECT * FROM sw_familiar WHERE idFamiliar = '"+idFamiliar+"' ORDER BY apellidoPaternoFamiliar ASC";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);

				while(rs.next()){

					familiar = familiarDB.setObjectFamiliar(rs);
					
				}		

			}catch (Exception e){
				System.out.println("Error: " + e.getMessage());
			}finally {
				ps.close();
				db.close();
			}		
			return familiar;
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

		
		public static sw_familiar setObjectFamiliar(ResultSet rs) throws SQLException {

			sw_familiar familiar = new sw_familiar();

			familiar.setApellidoMaternoFamiliar(rs.getString("apellidoMaternoFamiliar"));
			familiar.setApellidoPaternoFamiliar(rs.getString("apellidoPaternoFamiliar"));
			familiar.setFechaFinFamiliar(rs.getString("fechaFinFamiliar"));
			familiar.setFechaInicioFamiliar(rs.getString("fechaInicioFamiliar"));
			familiar.setfNacimientoFamiliar(rs.getString("fNacimientoFamiliar")); 
			familiar.setIdFamiliar(rs.getInt("idFamiliar"));
			familiar.setCodTrabajador(rs.getInt("codTrabajador"));
			familiar.setMontoFamiliar(rs.getInt("montoFamiliar"));
			familiar.setNombreFamiliar(rs.getString("nombreFamiliar"));
			familiar.setParentesco(rs.getInt("parentesco"));
			familiar.setPeriodo(rs.getInt("periodo"));
			familiar.setRetroActivoFamiliar(rs.getInt("retroActivoFamiliar"));
			familiar.setRutFamiliar(rs.getString("rutFamiliar"));
			familiar.setTipoCarga(rs.getInt("tipoCarga"));
			familiar.setTramoFamiliar(rs.getString("tramoFamiliar"));
			familiar.setRegistro(rs.getInt("registro"));
			familiar.setDuplo(rs.getInt("duplo"));
			familiar.setNormal(rs.getInt("normal"));
			

			return familiar;

		}
		
		
		// Obtener familiar por rut
		public static sw_familiar getFamiliarByRut(String rut) throws Exception {

			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();

			sw_familiar familiar = new sw_familiar();

			try {
				sql = "SELECT * FROM sw_familiar WHERE rutFamiliar = '" + rut + "'";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);

				while (rs.next()) {

					familiar = setObjectFamiliar(rs);
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			} finally {
				ps.close();
				db.close();
			}
			return familiar;
		}
		
		
		



}

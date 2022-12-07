package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;

import lib.classSW.BackTrabajadores;
import lib.db.ConnectionDB;


public class BackTrabajadoresDB {

	//Insert BackTrabajador 
	public static boolean insertBackTrabajador(BackTrabajadores backTrabajador) throws Exception{

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;

		try{
			sql =  	  " INSERT INTO sw_b_trabajadores ( "
					+ " idBackTrabajadores, "
					+ " fechaBack, "
					+ " idTrabajadores, "
					+ " idAFP, "
					+ " idIsapre, "
					+ " idMonedaPlan, "
					+ " idMonedaAFP, "
					+ " idMonedaAdicionalAFP, "
					+ " valorPlan, "
					+ " valorAFP, "
					+ " adicionalAFP, "
					+ " fechaAfiliacionAFP, "
					+ " capacidades, "
					+ " subsidio, "
					+ " mayor11anos, "
					+ " agro, "
					+ " trabajadorAgricola, "
					+ " institucionAPV, "
					+ " idMonedaAPV, "
					+ " valorDepositoAPV, "
					+ " valorAPV, "
					+ " nContrato "
					+ "	) "
					+ " VALUES ( ?,?,?,?,?, "
					+ "			 ?,?,?,?,?, "
					+ "			 ?,?,?,?,?, "
					+ "			 ?,?,?,?,?, "
					+ "			 ?,? ) ";	


			ps = db.conn.prepareStatement(sql);

			ps.setInt	(i++, backTrabajador.getIdBackTrabajadores());
			ps.setString(i++, convertStringToYYYYMMDD(backTrabajador.getFechaBack()));
			ps.setInt(i++, backTrabajador.getIdTrabajadores());
			ps.setInt(i++, backTrabajador.getIdAFP());
			ps.setInt(i++, backTrabajador.getIdIsapre());
			ps.setInt(i++, backTrabajador.getIdMonedaPlan());
			ps.setInt(i++, backTrabajador.getIdMonedaAFP());
			ps.setInt(i++, backTrabajador.getIdMonedaAdicionalAFP());
			ps.setDouble(i++, backTrabajador.getValorPlan());
			ps.setDouble(i++, backTrabajador.getValorAFP());
			ps.setDouble(i++, backTrabajador.getAdicionalAFP());
			ps.setString(i++, convertStringToYYYYMMDD(backTrabajador.getFechaAfiliacionAFP()));
			ps.setInt(i++, backTrabajador.getCapacidades());
			ps.setInt(i++, backTrabajador.getSubsidio());
			ps.setInt(i++, backTrabajador.getMayor11anos());
			ps.setInt(i++, backTrabajador.getAgro());
			ps.setInt(i++, backTrabajador.getTrabajadorAgricola());
			ps.setInt(i++, backTrabajador.getInstitucionAPV());
			ps.setInt(i++, backTrabajador.getIdMonedaAPV());
			ps.setDouble(i++, backTrabajador.getValorDepositoAPV());
			ps.setDouble(i++, backTrabajador.getValorAPV());
			ps.setInt(i++, backTrabajador.getnContrat());

			ps.execute();

			return true;

		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.conn.close();
		}
		return false;
	}


	//Actualizar BackTrabajador
	public static boolean updateBackTrabajador(BackTrabajadores backTrabajador) throws  Exception{

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	

		int i = 1;

		try {
			

			sql = " UPDATE sw_b_trabajadores SET "
					+ " idBackTrabajadores = ?, "
					+ " fechaBack = ?, "
					+ " idTrabajadores = ?, "
					+ " idAFP = ?, "
					+ " idIsapre = ?, "
					+ " idMonedaPlan = ?, "
					+ " idMonedaAFP = ?, "
					+ " idMonedaAdicionalAFP = ?, "
					+ " valorPlan = ?, "
					+ " valorAFP = ?, "
					+ " adicionalAFP = ?, "
					+ " fechaAfiliacionAFP = ?, "
					+ " capacidades = ?, "
					+ " subsidio = ?, "
					+ " mayor11anos = ?, "
					+ " agro = ?, "
					+ " trabajadorAgricola = ?, "
					+ " institucionAPV = ?, "
					+ " idMonedaAPV = ?, "
					+ " valorDepositoAPV = ?, "
					+ " valorAPV = ?, "
					+ " nContrato = ? "
					+ " WHERE idBackTrabajadores = ? ";

			ps = db.conn.prepareStatement(sql);

			
			ps.setString(i++, backTrabajador.getFechaBack());
			ps.setInt	(i++, backTrabajador.getIdTrabajadores());
			ps.setInt	(i++, backTrabajador.getIdAFP());
			ps.setInt	(i++, backTrabajador.getIdIsapre());
			ps.setInt	(i++, backTrabajador.getIdMonedaPlan());
			ps.setInt	(i++, backTrabajador.getIdMonedaAFP());
			ps.setInt	(i++, backTrabajador.getIdMonedaAdicionalAFP());
			ps.setDouble(i++, backTrabajador.getValorPlan());
			ps.setDouble(i++, backTrabajador.getValorAFP());
			ps.setDouble(i++, backTrabajador.getAdicionalAFP());
			ps.setString(i++, backTrabajador.getFechaAfiliacionAFP());
			ps.setInt	(i++, backTrabajador.getCapacidades());
			ps.setInt	(i++, backTrabajador.getSubsidio());
			ps.setInt	(i++, backTrabajador.getMayor11anos());
			ps.setInt	(i++, backTrabajador.getAgro());
			ps.setInt	(i++, backTrabajador.getTrabajadorAgricola());
			ps.setInt	(i++, backTrabajador.getInstitucionAPV());
			ps.setInt	(i++, backTrabajador.getIdMonedaAPV());
			ps.setDouble(i++, backTrabajador.getValorDepositoAPV());
			ps.setDouble(i++, backTrabajador.getValorAPV());
			ps.setInt	(i++, backTrabajador.getnContrat());
			ps.setInt	(i++, backTrabajador.getIdBackTrabajadores());


			ps.execute();

			return true;

		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return false;
	}


	//Borrar BackTrabajador por Id
	public static boolean deleteBackTrabajadorById(String id) throws  Exception{

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	

		try {
			sql = " DELETE FROM sw_b_trabajadores WHERE id = "+id+"' ";

			ps = db.conn.prepareStatement(sql);
			ps.execute();

			return true;

		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return false;
	}


	//Obtener BackTrabajador por Id
	public static BackTrabajadores getBackTrabajadorById(String id)  throws Exception{

		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();

		BackTrabajadores tr = new BackTrabajadores();

		try{
			sql = "SELECT * FROM sw_b_trabajadores WHERE idBackTrabajadores = '"+id+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){

				tr.setIdBackTrabajadores(rs.getInt("idBackTrabajadores"));
				tr.setFechaBack(rs.getString("fechaBack"));
				tr.setIdTrabajadores(rs.getInt("idTrabajadores"));
				tr.setIdAFP(rs.getInt("idAFP"));
				tr.setIdIsapre(rs.getInt("idIsapre"));
				tr.setIdMonedaPlan(rs.getInt("idMonedaPlan"));
				tr.setIdMonedaAFP(rs.getInt("idMonedaAFP"));
				tr.setIdMonedaAdicionalAFP(rs.getInt("idMonedaAdicionalAFP"));
				tr.setValorPlan(rs.getDouble("valorPlan"));
				tr.setValorAFP(rs.getDouble("valorAFP"));
				tr.setAdicionalAFP(rs.getDouble("adicionalAFP"));
				tr.setFechaAfiliacionAFP(rs.getString("fechaAfiliacionAFP"));
				tr.setCapacidades(rs.getInt("capacidades"));
				tr.setSubsidio(rs.getInt("subsidio"));
				tr.setMayor11anos(rs.getInt("mayor11anos"));
				tr.setAgro(rs.getInt("agro"));
				tr.setTrabajadorAgricola(rs.getInt("trabajadorAgricola"));
				tr.setInstitucionAPV(rs.getInt("institucionAPV"));
				tr.setIdMonedaAPV(rs.getInt("idMonedaAPV"));
				tr.setValorDepositoAPV(rs.getDouble("valorDepositoAPV"));
				tr.setValorAPV(rs.getDouble("valorAPV"));
				tr.setnContrat(rs.getInt("nContrato"));

			}			
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return tr;
	}


	public static String getFechaBackByIdTrabajador(String idTrabajador) throws Exception {
		
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		Gson gson = new Gson(); 
		String json = "";
		
		try{

			stmt = db.conn.createStatement();
			sql = " SELECT fechaBack,idBackTrabajadores FROM sw_b_trabajadores WHERE idTrabajadores = "+idTrabajador+" order by fechaBack desc ";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			
			

			while (rs.next()){

				Integer idBackTrabajadores = new Integer(rs.getInt("idBackTrabajadores"));
				String fechaBack = rs.getString("fechaBack");
				
				map.put(idBackTrabajadores,fechaBack);

			}
			
			json = gson.toJson(map); 
			rs.close();
			stmt.close();
			db.conn.close();
			//Fin Try
		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getFechaBackByIdTrabajador: " + e.getMessage());

		}finally{
			db.close();
		}

		return json;
		
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

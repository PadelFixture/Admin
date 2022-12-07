package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.BLOQUEO_LABOR;
import lib.db.ConnectionDB;

public class bloqueo_labor {
	public static ArrayList<BLOQUEO_LABOR> GET_BLOQUEO_LABOR(BLOQUEO_LABOR l)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<BLOQUEO_LABOR> lista = new ArrayList<BLOQUEO_LABOR>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT * FROM bloqueo_labor  WHERE  temporada='"+l.getTemporada()+"' "
				+ " and id_especie='"+l.getId_especie()+"' and id_faena='"+l.getId_faena()+"' "
				+ " and id_campo='"+l.getId_campo()+"'";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				BLOQUEO_LABOR ob = new BLOQUEO_LABOR();
				ob.setId_campo(rs.getString("id_campo"));
				ob.setEnero(rs.getInt("enero"));
				ob.setFebrero(rs.getInt("febrero"));
				ob.setMarzo(rs.getInt("marzo"));
				ob.setAbril(rs.getInt("abril"));
				ob.setMayo(rs.getInt("mayo"));
				ob.setJunio(rs.getInt("junio"));
				ob.setJulio(rs.getInt("julio"));
				ob.setAgosto(rs.getInt("agosto"));
				ob.setSeptiembre(rs.getInt("septiembre"));
				ob.setOctubre(rs.getInt("octubre"));
				ob.setNoviembre(rs.getInt("noviembre"));
				ob.setDiciembre(rs.getInt("diciembre"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
			 db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
	
	
	public static ArrayList<BLOQUEO_LABOR> GET_BLOQUEOLABOR()throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<BLOQUEO_LABOR> lista = new ArrayList<BLOQUEO_LABOR>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT * FROM bloqueo_labor";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				BLOQUEO_LABOR ob = new BLOQUEO_LABOR();
				ob.setId_campo(rs.getString("id_campo"));
				ob.setEnero(rs.getInt("enero"));
				ob.setFebrero(rs.getInt("febrero"));
				ob.setMarzo(rs.getInt("marzo"));
				ob.setAbril(rs.getInt("abril"));
				ob.setMayo(rs.getInt("mayo"));
				ob.setJunio(rs.getInt("junio"));
				ob.setJulio(rs.getInt("julio"));
				ob.setAgosto(rs.getInt("agosto"));
				ob.setSeptiembre(rs.getInt("septiembre"));
				ob.setOctubre(rs.getInt("octubre"));
				ob.setNoviembre(rs.getInt("noviembre"));
				ob.setDiciembre(rs.getInt("diciembre"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
			 db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
	
	
	public static ArrayList<BLOQUEO_LABOR> GET_BLOQUEO_LABOR_ID()throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<BLOQUEO_LABOR> lista = new ArrayList<BLOQUEO_LABOR>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT * FROM bloqueo_labor";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				BLOQUEO_LABOR ob = new BLOQUEO_LABOR();
				ob.setId_campo(rs.getString("id_campo"));
				ob.setId_faena(rs.getInt("id_faena"));
				ob.setId_especie(rs.getString("id_especie"));
				ob.setTemporada(rs.getInt("temporada"));
				ob.setEnero(rs.getInt("enero"));
				ob.setFebrero(rs.getInt("febrero"));
				ob.setMarzo(rs.getInt("marzo"));
				ob.setAbril(rs.getInt("abril"));
				ob.setMayo(rs.getInt("mayo"));
				ob.setJunio(rs.getInt("junio"));
				ob.setJulio(rs.getInt("julio"));
				ob.setAgosto(rs.getInt("agosto"));
				ob.setSeptiembre(rs.getInt("septiembre"));
				ob.setOctubre(rs.getInt("octubre"));
				ob.setNoviembre(rs.getInt("noviembre"));
				ob.setDiciembre(rs.getInt("diciembre"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
	
	
	public static boolean insertBLOQUEO_Labor(BLOQUEO_LABOR l) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO bloqueo_labor"
					+ "(id_campo,  id_faena, id_especie, temporada, enero, febrero, marzo, abril, mayo,"
					+ " junio, julio, agosto, septiembre, octubre, noviembre, diciembre)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);	
			ps.setString(1, l.getId_campo());
			ps.setInt(2, l.getId_faena());
			ps.setString(3, l.getId_especie());
			ps.setInt(4, l.getTemporada());
			ps.setInt(5, l.getEnero());
			ps.setInt(6, l.getFebrero());
			ps.setInt(7, l.getMarzo());
			ps.setInt(8, l.getAbril());
			ps.setInt(9, l.getMayo());
			ps.setInt(10, l.getJunio());
			ps.setInt(11, l.getJulio());
			ps.setInt(12, l.getAgosto());
			ps.setInt(13, l.getSeptiembre());
			ps.setInt(14, l.getOctubre());
			ps.setInt(15, l.getNoviembre());
			ps.setInt(16, l.getDiciembre());
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}

	public static boolean updateBLOQUEO_LABOR(BLOQUEO_LABOR l) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = " UPDATE bloqueo_labor SET enero='"+l.getEnero()+"', febrero='"+l.getFebrero()+"',"
				+ " marzo='"+l.getMarzo()+"', abril='"+l.getAbril()+"', mayo='"+l.getMayo()+"',"
				+ " junio='"+l.getJunio()+"', julio='"+l.getJulio()+"', agosto='"+l.getAgosto()+"', "
				+ " septiembre='"+l.getSeptiembre()+"', octubre='"+l.getOctubre()+"', "
				+ " noviembre='"+l.getNoviembre()+"', diciembre='"+l.getDiciembre()+"'"
				+ " WHERE  temporada='"+l.getTemporada()+"' "
				+ " and id_especie='"+l.getId_especie()+"' and id_faena='"+l.getId_faena()+"' "
				+ " and id_campo='"+l.getId_campo()+"'";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
}

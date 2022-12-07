package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.CALIBRE;
import lib.classSA.FACTOR;
import lib.classSW.EQUIPO;
import lib.db.ConnectionDB;

public class FACTOR_DECISION {

	public static ArrayList<FACTOR> GETFACTORBYCAMPO(String codigo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<FACTOR> lista = new ArrayList<FACTOR>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select cu.*, p.nombre AS nombre_bloque from bloque p left join factor_decision cu on cu.cod_bloque = p.codigo where cu.cod_campo = '"+codigo+"'";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				FACTOR ob = new FACTOR();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setCod_campo(rs.getString("cod_campo"));
				ob.setCod_bloque(rs.getString("cod_bloque"));
				ob.setEnero(rs.getString("enero"));
				ob.setFebrero(rs.getString("febrero"));
				ob.setMarzo(rs.getString("marzo"));
				ob.setAbril(rs.getString("abril"));
				ob.setMayo(rs.getString("mayo"));
				ob.setJunio(rs.getString("junio"));
				ob.setJulio(rs.getString("julio"));
				ob.setAgosto(rs.getString("agosto"));
				ob.setSeptiembre(rs.getString("septiembre"));
				ob.setOctubre(rs.getString("octubre"));
				ob.setNoviembre(rs.getString("noviembre"));
				ob.setDiciembre(rs.getString("diciembre"));
				ob.setNombre_bloque(rs.getString("nombre_bloque"));
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
	public static ArrayList<FACTOR> GETFACTORBYMES(String codigo, String codigo2) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<FACTOR> lista = new ArrayList<FACTOR>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select "+codigo+" from factor_decision where cod_bloque = '"+codigo2+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while (rs.next()){
				FACTOR ob = new FACTOR();
				ob.setMes(rs.getString(codigo));
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
	public static ArrayList<EQUIPO> GETFACTORBYEQUIPO(String codigo, String codigo2) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<EQUIPO> lista = new ArrayList<EQUIPO>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select * from equipo_riego where codigo_campo = '"+codigo+"' and codigo_equipo = '"+codigo2+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				EQUIPO ob = new EQUIPO();
				ob.setCodigo_bloque(rs.getString("codigo_bloque"));

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

	public static ArrayList<FACTOR> GET_FACTORES(String[] codigos) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<FACTOR> list = new ArrayList<FACTOR>();
		ConnectionDB db = new ConnectionDB();
		try{
			String sqlCodigos = "";
			int c = 0;
			for(int i = 0; i < codigos.length; i++){
				if(c == 0){
					sqlCodigos += " '"+codigos[i]+"'";
				}else{
					sqlCodigos += ", '"+codigos[i]+"'";
				}
				c++;
			}
			sql = "select cu.*, p.nombre AS nombre_bloque from bloque p left join factor_decision cu on cu.cod_bloque = p.codigo where cu.cod_bloque in ("+sqlCodigos+")";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				FACTOR ob = new FACTOR();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setCod_campo(rs.getString("cod_campo"));
				ob.setCod_bloque(rs.getString("cod_bloque"));
				ob.setEnero(rs.getString("enero"));
				ob.setFebrero(rs.getString("febrero"));
				ob.setMarzo(rs.getString("marzo"));
				ob.setAbril(rs.getString("abril"));
				ob.setMayo(rs.getString("mayo"));
				ob.setJunio(rs.getString("junio"));
				ob.setJulio(rs.getString("julio"));
				ob.setAgosto(rs.getString("agosto"));
				ob.setSeptiembre(rs.getString("septiembre"));
				ob.setOctubre(rs.getString("octubre"));
				ob.setNoviembre(rs.getString("noviembre"));
				ob.setDiciembre(rs.getString("diciembre"));
				ob.setNombre_bloque(rs.getString("nombre_bloque"));
				list.add(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {
			db.close();
		}
		return list;
		
	}
	
	public static boolean UPDATE_FactorDecision(FACTOR c) throws Exception {
		PreparedStatement ps = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 try {
			sql = " UPDATE factor_decision set enero='" +c.getEnero()+ "', "
				+ " febrero="+c.getFebrero()
				+ ", marzo="+c.getMarzo()
				+ ", abril="+c.getAbril()
				+ ", mayo="+c.getMayo()
				+ ", junio="+c.getJunio()
				+ ", julio="+c.getJulio()
				+ ", agosto="+c.getAgosto()
				+ ", septiembre="+c.getSeptiembre()
				+ ", octubre="+c.getOctubre()
				+ ", noviembre="+c.getNoviembre()
				+ ", diciembre="+c.getDiciembre()
				+" where codigo=" +c.getCodigo();

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
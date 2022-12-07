package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.ClassSASW.MantenedorEspecie;
import lib.ClassSASW.parametros;
import lib.classSA.MANTENEDOR_GEN;
import lib.classSW.Especie;
import lib.classSW.variedades;
import lib.data.json.SASW.Tabla;
import lib.db.ConnectionDB;

public class Mantenedor_especie {

	public static ArrayList<MantenedorEspecie> getMantenedorEspecie(String codigo, String codigo2) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<MantenedorEspecie> lista = new ArrayList<MantenedorEspecie>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT * from parametro_especie "
					+ "where (especie = '"+codigo+"' or '"+codigo+"' = 'ALL'  ) "
					+ "and tabla = '"+codigo2+"' "
					+ "and activo = 1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				MantenedorEspecie pm = new MantenedorEspecie();
				pm.setCodigo(rs.getInt("codigo"));
				pm.setId(rs.getInt("id"));
				pm.setEspecie(rs.getInt("especie"));
				pm.setTabla(rs.getString("tabla"));
				pm.setDescripcion(rs.getString("descripcion"));
				lista.add(pm);
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
	}
	public static ArrayList<Especie> getEspecie(String codigo) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<Especie> lista = new ArrayList<Especie>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT * from especie";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				Especie pm = new Especie();
				pm.setCodigo(rs.getInt("codigo"));
				pm.setEspecie(rs.getString("especie"));
				pm.setActivo(rs.getInt("activo"));
				lista.add(pm);
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
	}
	public static ArrayList<variedades> getEspecieByCampo(String codigo) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<variedades> lista = new ArrayList<variedades>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT distinct(especie) from variedades_campo_especie where campo = '"+codigo+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				variedades pm = new variedades();
//				pm.setCodigo(rs.getInt("codigo"));
//				pm.setCampo(rs.getString("campo"));
				pm.setEspecie(rs.getString("especie"));
//				pm.setVariedad(rs.getString("variedad"));
				lista.add(pm);
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
	}
	public static ArrayList<variedades> getVariedades_campo_especie(String codigo, String codigo2) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<variedades> lista = new ArrayList<variedades>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT cu.*, p.variedad AS nombre_variedad from variedad p left join variedades_campo_especie cu on cu.variedad = p.codigo where cu.especie = '"+codigo+"' and cu.activo = '1' and cu.activo = '1' and cu.campo = '"+codigo2+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				variedades pm = new variedades();
				pm.setCodigo(rs.getInt("codigo"));
				pm.setCampo(rs.getString("campo"));
				pm.setEspecie(rs.getString("especie"));
				pm.setVariedad(rs.getString("variedad"));
				pm.setNombre_variedad(rs.getString("nombre_variedad"));
				lista.add(pm);
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
	}
	public static boolean updateVariedadVariedades(variedades param) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = " UPDATE variedades_campo_especie SET variedad='" + param.getVariedad() + "' where codigo='" + param.getCodigo() + "'";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {

			ps.close();
		
			db.close();
		}
		return false;
	}
	public static boolean updateEstadoVariedades(variedades param) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE variedades_campo_especie SET activo=0 where codigo='" + param.getCodigo() + "'";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	public static boolean AddVariedadVariedades (variedades fa) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO variedades_campo_especie (campo, especie, variedad, activo) " + "VALUES(?, ?, ?, '1')";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, fa.getCampo());
			ps.setString(2, fa.getEspecie());
			ps.setString(3, fa.getVariedad());
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
	public static boolean updateME(MantenedorEspecie param) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE especie SET activo=0 where codigo='" + param.getCodigo() + "'";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	public static ArrayList<Tabla> getTabla(String codigo) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<Tabla> lista = new ArrayList<Tabla>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT distinct tabla from parametro_especie";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				Tabla pm = new Tabla();
				pm.setTabla(rs.getString("tabla"));
				lista.add(pm);
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
	}
	public static boolean updateDescripcionEspecie(parametros param) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = " UPDATE parametro_especie SET descripcion='" + param.getDescripcion() + "' where codigo='" + param.getCodigo()
					+ "'";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {

			ps.close();
		
			db.close();
		}
		return false;
	}
	public static boolean updateEstadoEspecie(parametros param) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE parametro_especie SET activo=0 where codigo='" + param.getId() + "'";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	public static boolean insertME (MantenedorEspecie fa) throws Exception {
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		String sql = "";
		String sql2 = "";
		ConnectionDB db = new ConnectionDB();
		int id = 0;
		try {
			sql = "INSERT INTO parametro_especie (id, especie, tabla, descripcion, activo) " + "VALUES(?, ?, ?, ?, '1')";
			sql2 = "SELECT MAX(id) FROM parametro_especie where especie = " + "'" + fa.getEspecie() + "' and tabla = " + "'" + fa.getTabla() + "'";
			ps2 = db.conn.prepareStatement(sql2);
			ResultSet rs = ps2.executeQuery(sql2);
			while (rs.next()){
				id = rs.getInt("MAX(id)");
				id+=1;
			}
			rs.close();
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, fa.getEspecie());
			ps.setString(3, fa.getTabla());
			ps.setString(4, fa.getDescripcion());
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
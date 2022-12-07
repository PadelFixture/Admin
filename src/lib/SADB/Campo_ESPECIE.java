package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import lib.ClassSASW.MantenedorEspecie;
import lib.ClassSASW.parametros;
import lib.classSA.Campo_Especie;
import lib.classSA.Campo_Variedad;
import lib.classSA.Especie_Campo;
import lib.classSA.Sueldos_Cargo;
import lib.classSW.Especie;
import lib.db.ConnectionDB;
import lib.security.session;

public class Campo_ESPECIE {

	public static ArrayList<Campo_Especie> Get_CampoEspecie() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<Campo_Especie> lista = new ArrayList<Campo_Especie>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT cu.*, p.especie AS nombre_especie from especie p left join campo_especie cu on cu.codigo_especie = p.codigo";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				Campo_Especie ob = new Campo_Especie();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setCodigo_campo(rs.getString("codigo_campo"));
				ob.setCodigo_especie(rs.getInt("codigo_especie"));
				ob.setNombre_especie(rs.getString("nombre_especie"));
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
	
	public static ArrayList<Campo_Variedad> Get_CampoVariedad(int user) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<Campo_Variedad> lista = new ArrayList<Campo_Variedad>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql +=	"SELECT DISTINCT ";
			sql +=		"v.*, cam.campo ";
			sql +=	"FROM variedad v ";
			sql +=		"LEFT JOIN cuartel c ON(c.variedad = v.codigo) ";
			sql +=		"LEFT JOIN sector s ON(s.sector = c.sector) ";
			sql +=		"LEFT JOIN campo cam ON(cam.campo = s.campo) ";
			sql +=	"WHERE cam.campo IN(SELECT codigo_campo FROM usuario_campo WHERE codigo_usuario = "+user+")";
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				Campo_Variedad ob = new Campo_Variedad();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setCodigo_campo(rs.getString("campo"));
				ob.setCodigo_variedad(rs.getString("codigo"));
				ob.setVariedad(rs.getString("variedad"));
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
	
	public static ArrayList<Campo_Variedad> Get_CampoVariedad2(int user) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<Campo_Variedad> lista = new ArrayList<Campo_Variedad>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql +=	"SELECT DISTINCT v.*, cam.campo "
					+ "FROM variedades_campo_especie vce "
					+ "left join variedad v on v.codigo = vce.variedad "
					+ "left join campo cam on cam.campo = vce.campo  "
					+ "WHERE cam.campo IN(SELECT codigo_campo FROM usuario_campo WHERE codigo_usuario = "+user+") "
					+ " and v.codigo is not null";
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				Campo_Variedad ob = new Campo_Variedad();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setCodigo_campo(rs.getString("campo"));
				ob.setCodigo_variedad(rs.getString("codigo"));
				ob.setVariedad(rs.getString("variedad"));
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
	
	public static ArrayList<Campo_Especie> getEspecieCampo(String codigo) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<Campo_Especie> lista = new ArrayList<Campo_Especie>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT * from campo_especie where codigo_campo = '"+codigo+"' and activo = 1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				Campo_Especie pm = new Campo_Especie();
				pm.setCodigo(rs.getInt("codigo"));
				pm.setCodigo_campo(rs.getString("codigo_campo"));
				pm.setCodigo_especie(rs.getInt("codigo_especie"));
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
	public static boolean updateEspecieCampo(parametros param) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE campo_especie SET activo = 0 where codigo='" + param.getCodigo() + "'";
			
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
	public static boolean insertEC (Especie_Campo fa) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO campo_especie (codigo_campo, codigo_especie, activo) " + "VALUES(?, ?, 1)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, fa.getCodigo_campo());
			ps.setString(2, fa.getCodigo_especie());
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

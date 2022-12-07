package lib.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



import lib.classSW.MenuPerfil;

import lib.struc.systemMenu;

public class systemMenuDB {
	
	
	public static ArrayList<systemMenu> getMenu(int idPadre,int idPerfil)
			throws Exception {

		ArrayList<systemMenu> menus = new ArrayList<systemMenu>();
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			sql = "SELECT m.*,c.numero FROM systemMenu m "
					+ "inner join systemMenuPerfil p on (m.idMenu=p.idMenu and p.idPerfil=? )"
					+ "left join (SELECT idPadre,count(1) as numero FROM systemMenu group by idPadre) c on (m.idMenu=c.idPadre)"

					+ " where m.idPadre=? order by orden ";
			
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, idPerfil);
			ps.setInt(2, idPadre);
			
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				systemMenu o = new systemMenu();
				
				o.setIdMenu(rs.getInt("idMenu"));
				o.setIdPadre(rs.getInt("idPadre"));
				o.setMenu(rs.getString("menu"));
				o.setIcono(rs.getString("icono"));
				o.setUrl(rs.getString("url"));
				o.setCount(rs.getInt("numero"));
				o.setAdm(rs.getString("adm"));
				
				menus.add(o);
			}
			rs.close();
			ps.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getMenu: " + e.getMessage());
		} finally {
			db.close();
		}

		return menus;
	}
	
	public static systemMenu getMenuUrl(String  id)
			throws Exception {

		systemMenu o = new systemMenu();
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			

			sql = "SELECT m.* FROM systemMenu m "
					+ " where m.url=?";
			
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, id);
			
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				
				
				o.setIdMenu(rs.getInt("idMenu"));
				o.setIdPadre(rs.getInt("idPadre"));
				o.setMenu(rs.getString("menu"));
				o.setIcono(rs.getString("icono"));
				o.setUrl(rs.getString("url"));
		
				o.setAdm(rs.getString("adm"));
				o.setProceso(rs.getString("proceso"));
				

			}
			rs.close();
			ps.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getMenu: " + e.getMessage());
		} finally {
			db.close();
		}

		return o;
	}
	
	
	public static MenuPerfil getSystemMenuPerfil(String idMenu, int idPerfil) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		MenuPerfil menuperfil = new MenuPerfil();
		
		try {

			
			
			sql = "SELECT * FROM systemMenuPerfil mp "
					+ " INNER JOIN systemMenu m ON (m.idMenu = mp.idMenu)  "
					+ " WHERE m.url = ? and mp.idPerfil = ? LIMIT 1 ";
			
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, idMenu);
			ps.setInt(2, idPerfil);
			
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				menuperfil.setEditar(rs.getInt("editar"));
				menuperfil.setEliminar(rs.getInt("eliminar"));
				menuperfil.setVer(rs.getInt("ver"));
			}
			rs.close();
			ps.close();
			db.conn.close();

		} catch (SQLException e) {
			throw new Exception("getMenu: " + e.getMessage());
		} finally {
			db.close();
		}
		
		
		return menuperfil;
	}
	
	
	
	
	
	

}

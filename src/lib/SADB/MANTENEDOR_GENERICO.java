package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.ESTADO;
import lib.classSA.FORMA_APLICA;
import lib.classSA.MANTENEDOR_GEN;
import lib.db.ConnectionDB;


public class MANTENEDOR_GENERICO {
	
//	Insert
		public static boolean insertMG (MANTENEDOR_GEN fa) throws Exception {
			PreparedStatement ps = null;
			PreparedStatement ps2 = null;
			String sql = "";
			String sql2 = "";
			ConnectionDB db = new ConnectionDB();
			int llave = 0;
			try {
				sql = "INSERT INTO parametros (codigo, llave, descripcion, activo) " + "VALUES(?, ?, ?, '1')";
				sql2 = "SELECT MAX(llave) FROM parametros where codigo = " + "'" + fa.getCodigo() + "'";
				ps2 = db.conn.prepareStatement(sql2);
				ResultSet rs = ps2.executeQuery(sql2);
				while (rs.next()){
					llave = rs.getInt("MAX(llave)");
					llave+=1;
				}
				rs.close();
				ps = db.conn.prepareStatement(sql);
				ps.setString(1, fa.getCodigo());
				ps.setInt(2, llave);
				ps.setString(3, fa.getDescripcion());
				System.out.println(ps);
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
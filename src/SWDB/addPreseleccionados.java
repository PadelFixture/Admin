package SWDB;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lib.classSW.trabajador;
import lib.classSW.trabajadores;
import lib.classSW.Preseleccion;
import lib.db.ConnectionDB;

public class addPreseleccionados {

	public static boolean insertPreseleccion(Preseleccion trab) throws Exception {
		PreparedStatement ps = null;
		PreparedStatement pp2 = null;
		PreparedStatement pp3 = null;
		String sql = "";
		String sql2 = "";
		String sql3 = "";
		ConnectionDB db = new ConnectionDB();
		try {
			for (trabajadores traba : trab.getCodigo_trabajador()) {
				sql = "INSERT INTO preseleccionados (codigo_trabajador, codigo_peticion) VALUES (?,?)";
				ps = db.conn.prepareStatement(sql);
				ps.setInt(1, traba.getId());
				ps.setInt(2, trab.getCodigo_peticion());
				ps.execute();
			}
			// int aux = 0;
			// for (Integer i: trab.getCodigo_trabajador()) {
			// sql = "INSERT INTO preseleccionados (codigo_trabajador,
			// codigo_peticion) VALUES (?,?)";
			// ps = db.conn.prepareStatement(sql);
			// ps.setInt(1, i);
			// ps.setInt(2, trab.getCodigo_peticion());
			//
			// ps.execute();
			//
			// aux++;
			// }

			sql3 = "select count(cantidad_temporal), cantidad from peticion_trabajador where id_peticion = '"
					+ trab.getCodigo_peticion() + "'";
			pp3 = db.conn.prepareStatement(sql3);
			ResultSet rss = pp3.executeQuery(sql3);
			System.out.println(rss);
			if (rss.getInt("cantidad_temporal") == rss.getInt("cantidad")) {
				sql2 = "UPDATE peticion_trabajador SET cantidad_temporal=0 WHERE id_peticion=?";
				pp2 = db.conn.prepareStatement(sql2);
				pp2.setInt(1, trab.getCodigo_peticion());
				pp2.execute();
			}
			// aux+= rss.getInt("cantidad_temporal");

			return true;

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			pp2.close();
			pp3.close();
			db.close();
		}
		return false;
	}

}

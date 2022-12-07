package lib.db.sw;

import java.sql.Blob;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import lib.db.ConnectionDB;


public class CartaTerminoDB {


	public static Blob getCartaTermino() throws Exception {

		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try {

			ps = db.conn.createStatement();
			sql = "SELECT file FROM sw_template WHERE documento = 'Liquidacion' ";
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				Blob liquidacion = rs.getBlob(1);
				return liquidacion;
			}
			rs.close();
			ps.close();
			db.conn.close();

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLiquidacion: " + e.getMessage());
		} finally {
			db.close();
		}

		return null;
	}
	
	
}



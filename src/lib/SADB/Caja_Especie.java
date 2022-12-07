package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.CAJA_ESPECIE;
import lib.db.ConnectionDB;

public class Caja_Especie {

	public static ArrayList<CAJA_ESPECIE> Get_CajaEspecie(CAJA_ESPECIE row) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CAJA_ESPECIE> lista = new ArrayList<CAJA_ESPECIE>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = " select c.campo, c.especie, c.variedad, c.peso from caja_especie "
				+ " where campo='"+row.getCampo()+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				CAJA_ESPECIE ob = new CAJA_ESPECIE();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setCampo(rs.getString("campo"));
				ob.setEspecie(rs.getInt("especie"));
				ob.setVariedad(rs.getInt("variedad"));
				ob.setPeso(rs.getFloat("peso"));
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
}

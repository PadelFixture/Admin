package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import lib.classSA.Mantenedor_SA;
import lib.db.ConnectionDB;

public class MANTENEDOR_SA {
	
	public static ArrayList<Mantenedor_SA> GETMantenedor_SA(String categoria) throws Exception{
		PreparedStatement ps = null;
		String sql ="";
		ArrayList<Mantenedor_SA> lista = new ArrayList<Mantenedor_SA>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT m.codigo, m.categoria,m.descripcion, m.cod_campo, m.cod_especie,"
				+ " e.especie nvEspecie from mantenedor_sa m "
				+ "left join especie e on m.cod_especie = e.codigo "
				+ "WHERE categoria='"+categoria+"' and estado=1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				Mantenedor_SA ob = new Mantenedor_SA();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setCategoria(rs.getString("categoria"));
				ob.setDescripcion(rs.getString("descripcion"));
				ob.setCod_campo(rs.getInt("cod_campo"));
				ob.setCod_especie(rs.getInt("cod_especie"));
				ob.setNvEspecie(rs.getString("nvEspecie"));
				lista.add(ob);
			}
		}catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return lista;
	}
}

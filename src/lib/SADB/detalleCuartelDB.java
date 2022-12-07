package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import lib.classSA.detalleCuartel;
import lib.db.ConnectionDB;

public class detalleCuartelDB {
	public static ArrayList<detalleCuartel> getDetalleCuartel() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<detalleCuartel> data = new ArrayList<detalleCuartel>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT cam.campo, s.sector,c.codigo, c.codigo_cuartel, c.nombre, e.codigo AS cod_especie, e.especie, v.codigo AS cod_variedad, v.variedad, c.superficie, c.ano_plantacion, c.ceco ";
			sql += "FROM campo cam INNER JOIN sector s on(cam.campo = s.campo) ";
			sql += "INNER JOIN cuartel c ON (c.sector = s.sector) ";
			sql += "INNER JOIN variedad v ON(c.variedad = v.codigo) ";
			sql += "INNER JOIN especie e ON(e.codigo = v.especie)";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				detalleCuartel e = new detalleCuartel();
				e.setCampo(rs.getString("campo"));
				e.setSector(rs.getString("sector"));
				e.setCodigo(rs.getInt("codigo"));
				e.setCodigo_cuartel(rs.getString("codigo_cuartel"));
				e.setNombre(rs.getString("nombre"));
				e.setCod_especie(rs.getInt("cod_especie"));
				e.setEspecie(rs.getString("especie"));
				e.setCod_variedad(rs.getInt("cod_variedad"));
				e.setVariedad(rs.getString("variedad"));
				e.setSuperficie(rs.getFloat("superficie"));
				e.setAno_plantacion(rs.getInt("ano_plantacion"));
				e.setCeco(rs.getString("ceco"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
}

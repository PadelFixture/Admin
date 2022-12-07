package SWDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import lib.classSW.LoginTest;
import lib.db.ConexionBD;

public class LoginTestDB {
	
	public static Connection db;
	
	public static  List<LoginTest> obtenerListadoLogintest() throws Exception {
		
		PreparedStatement ps = null;
		String sql="";
		List<LoginTest> lista = new ArrayList<>();
		try{
		    sql = "select id, TRIM(upper(usuario)) as usuario, sociedad, grupo_compra, solicitante, rolPrivado from loginTest";
		    System.out.println(sql);
			db = ConexionBD.getConnection();
			ps = db.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){
				LoginTest p = new LoginTest();
				
				p.setId(rs.getInt("id"));
				p.setUsername(rs.getString("usuario"));
				p.setPerfil(rs.getInt("sociedad"));
				p.setRolprivado(rs.getInt("rolPrivado"));
				p.setGrupocompra(rs.getString("grupo_compra"));
				p.setSolicitante(rs.getString("solicitante"));
	
				lista.add(p);
			}

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}
		return lista;	
	}
}
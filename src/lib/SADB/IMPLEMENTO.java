package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.IMPLEMENTO_OBJ;
import lib.classSA.MAQUINARIA_OBJ;
import lib.classSA.MERCADO_OBJ;
import lib.classSA.estado_fenologico;
import lib.db.ConnectionDB;

public class IMPLEMENTO {
	
	

	//------------------------MERCADOS-------------------------
//			SELECT
			public static ArrayList<IMPLEMENTO_OBJ> getImplementos () throws Exception{
				PreparedStatement ps = null;
				String sql = "";
				ArrayList<IMPLEMENTO_OBJ> lista = new ArrayList<IMPLEMENTO_OBJ>();
				ConnectionDB db = new ConnectionDB();
				try{
				sql = "select * from implemento";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				while (rs.next()) {
					IMPLEMENTO_OBJ ob = new IMPLEMENTO_OBJ();
					ob.codigo = rs.getInt("codigo");
					ob.descripcion = rs.getString("descripcion");
					ob.marca = rs.getString("marca");
					ob.modelo = rs.getString("modelo");
					ob.color = rs.getString("color");					
					ob.ano = rs.getString("ano");
					ob.estado = rs.getString("estado");
					lista.add(ob);
				}
				}catch (SQLException e){
					System.out.println("Erro:" + e.getMessage());
				}catch (Exception e){
					System.out.println("Error:" + e.getMessage());
				}finally {
					ps.close();
					db.close();
				}
				return lista;
			}
				//--------------FIN ESTADO_FENOLOGICO-----------------------------------------
}

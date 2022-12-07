package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.MERCADO_OBJ;
import lib.classSA.estado_fenologico;
import lib.db.ConnectionDB;

public class MERCADO {
	
	

	//------------------------MERCADOS-------------------------
//			SELECT
			public static ArrayList<MERCADO_OBJ> getMercados () throws Exception{
				PreparedStatement ps = null;
				String sql = "";
				ArrayList<MERCADO_OBJ> lista = new ArrayList<MERCADO_OBJ>();
				ConnectionDB db = new ConnectionDB();
				try{
				sql = "select * from mercado";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				while (rs.next()) {
					MERCADO_OBJ ob = new MERCADO_OBJ();
					ob.codigo = rs.getInt("codigo");
					ob.descripcion = rs.getString("descripcion");
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
//			INSERT
				//--------------FIN ESTADO_FENOLOGICO-----------------------------------------
}

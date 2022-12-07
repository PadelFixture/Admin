package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.MAQUINARIA_OBJ;
import lib.classSA.MAQUINARIA_PF;
import lib.classSA.MERCADO_OBJ;
import lib.classSA.estado_fenologico;
import lib.db.ConnectionDB;

public class MAQUINARIA {
	
	

	//------------------------MERCADOS-------------------------
//			SELECT
			public static ArrayList<MAQUINARIA_OBJ> getMaquinarias () throws Exception{
				PreparedStatement ps = null;
				String sql = "";
				ArrayList<MAQUINARIA_OBJ> lista = new ArrayList<MAQUINARIA_OBJ>();
				ConnectionDB db = new ConnectionDB();
				try{
				sql = "select * from maquinaria";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				while (rs.next()) {
					MAQUINARIA_OBJ ob = new MAQUINARIA_OBJ();
					ob.codigo = rs.getInt("codigo");
					ob.descripcion = rs.getString("descripcion");
					ob.marca = rs.getString("marca");
					ob.modelo = rs.getString("modelo");
					ob.color = rs.getString("color");
					ob.chasis = rs.getString("chasis");
					ob.motor = rs.getString("motor");
					ob.cilindrada = rs.getString("cilindrada");
					ob.ano = rs.getInt("ano");
					ob.estado = rs.getInt("estado");
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
			
			public static ArrayList<MAQUINARIA_PF> getMaquinarias_pf (String campo) throws Exception{
				PreparedStatement ps = null;
				String sql = "";
				ArrayList<MAQUINARIA_PF> lista = new ArrayList<MAQUINARIA_PF>();
				ConnectionDB db = new ConnectionDB();
				try{
				sql = "select * from maquinaria_pf where codigo_pf in (select codigo from programa_fitosanitario where campo = '"+campo+"')";
				System.out.println(sql);
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				while (rs.next()) {
					MAQUINARIA_PF ob = new MAQUINARIA_PF();
					ob.codigo     = rs.getInt("codigo");
					ob.codigo_pf  = rs.getInt("codigo_pf");
					ob.maquinaria = rs.getInt("maquinaria");
					ob.implemento = rs.getInt("implemento");
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
}

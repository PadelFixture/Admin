package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.CONTROL_AP;
import lib.classSA.estado_fenologico;
import lib.db.ConnectionDB;

public class ESTADO_FENOLOGICO {
	
	

	//------------------------ESTADO_FENOLOGICOS-------------------------
//			SELECT
			public static ArrayList<estado_fenologico> getEstadoFenologico () throws Exception{
				PreparedStatement ps = null;
				String sql = "";
				ArrayList<estado_fenologico> lista = new ArrayList<estado_fenologico>();
				ConnectionDB db = new ConnectionDB();
				try{
				sql = " select e.codigo, e.especie, e.estado, e.estado_fenologicos, esp.especie nvEspecie from estado_fenologico e "
					+ " left join especie esp on e.especie = esp.codigo where e.estado = 1";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				while (rs.next()) {
					estado_fenologico ob = new estado_fenologico();
					ob.setCodigo(rs.getInt("codigo"));
					ob.setEstado_fenologicos(rs.getString("estado_fenologicos"));
					ob.setEstado(rs.getInt("estado"));
					ob.setEspecie(rs.getInt("especie"));
					ob.setNvEspecie(rs.getString("nvEspecie"));
					lista.add(ob);
				}
				rs.close();
				ps.close();
				}catch (SQLException e){
					System.out.println("Erro:" + e.getMessage());
				}catch (Exception e){
					System.out.println("Error:" + e.getMessage());
				}finally {
					db.close();
				}
				return lista;
			}
			
			
			
//			INSERT
			public static boolean insertEF (estado_fenologico ef) throws Exception{
				PreparedStatement ps = null;
				 String sql = "";
				 ConnectionDB db = new ConnectionDB();
				 try {
					 sql = "INSERT INTO estado_fenologico(estado_fenologicos, especie, estado) VALUES(?,?,'1')";
					 System.out.println(sql);
					 ps = db.conn.prepareStatement(sql);
					 ps.setString(1, ef.getEstado_fenologicos());
					 ps.setInt(2, ef.getEspecie());
					 ps.execute();
					 return true;
				} catch (SQLException e) {
					System.out.println("Error:" + e.getMessage());;
				} catch (Exception e){
					System.out.println("Error:" + e.getMessage());
				}finally {
					ps.close();
					db.close();
				}
				 return false;
			}
//			UPDATE
			public static boolean updateEF (estado_fenologico ef) throws Exception {
				PreparedStatement ps = null;
				 String sql = "";
				 ConnectionDB db = new ConnectionDB();
				 try {
					sql = " UPDATE estado_fenologico set estado_fenologicos='" +ef.getEstado_fenologicos()+ "', "
						+ " especie='"+ef.getEspecie()+"' where codigo='" +ef.getCodigo()+ "'";
					System.out.println(sql);
					ps = db.conn.prepareStatement(sql);
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
			
			public static boolean UP_EstadoFenologico_Estado(estado_fenologico es) throws Exception {
				PreparedStatement ps = null;
				String sql = "";
				ConnectionDB db = new ConnectionDB();
				try {
					sql = "UPDATE estado_fenologico set estado=0 where codigo='"+es.getCodigo()+"'";
					ps = db.conn.prepareStatement(sql);
					ps.execute();
					System.out.println(ps);
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
	//--------------FIN ESTADO_FENOLOGICO-----------------------------------------
}

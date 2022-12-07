package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSW.ArticuloTerminoContrato;
import lib.db.ConnectionDB;

public class ArticuloTerminoContratoDB {

	
	// get Articulo Termino Contrato
		public static ArrayList<ArticuloTerminoContrato> getArticuloTerminoContrato() throws Exception 
		{
			
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
	        ArrayList<ArticuloTerminoContrato> lista = new ArrayList<ArticuloTerminoContrato>();
			
			try{
				sql = "select * from sw_m_articuloTerminoContrato ";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);

				while(rs.next()){
				
					ArticuloTerminoContrato ArticuloTerminoContrato = new ArticuloTerminoContrato(); 
					
					ArticuloTerminoContrato.setIdArticuloTerminoContrato(rs.getInt("idArticuloTerminoContrato"));
					ArticuloTerminoContrato.setNombre(rs.getString("nombre"));
					ArticuloTerminoContrato.setDescripcion(rs.getString("descripcion"));
					ArticuloTerminoContrato.setArticuloTerminoContrato(rs.getString("articuloTerminoContrato"));
			
					lista.add(ArticuloTerminoContrato);

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
		}// fin metodo get
	
		public static ArticuloTerminoContrato getArticuloTerminoContratoByIdArticulo(int id) throws Exception  {
			
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();

			ArticuloTerminoContrato ArticuloTerminoContrato = new ArticuloTerminoContrato(); 

			try{
				sql = "select * from sw_m_articuloTerminoContrato "
				+ " where idArticuloTerminoContrato = '"+id+"' ";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);

				while(rs.next()){
				
					
					ArticuloTerminoContrato.setIdArticuloTerminoContrato(rs.getInt("idArticuloTerminoContrato"));
					ArticuloTerminoContrato.setNombre(rs.getString("nombre"));
					ArticuloTerminoContrato.setDescripcion(rs.getString("descripcion"));
					ArticuloTerminoContrato.setArticuloTerminoContrato(rs.getString("articuloTerminoContrato"));

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
			return ArticuloTerminoContrato;
			
		}// fin metodo
		
		
		
}

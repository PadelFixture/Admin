package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSW.IncisoTerminoContrato;
import lib.db.ConnectionDB;

public class IncisoTerminoContratoDB {

	
	// get Articulo Termino Contrato
			public static ArrayList<IncisoTerminoContrato> getIncisoTerminoContrato() throws Exception 
			{
				
				PreparedStatement ps = null;
				String sql = "";
				ConnectionDB db = new ConnectionDB();
		        ArrayList<IncisoTerminoContrato> lista = new ArrayList<IncisoTerminoContrato>();
				
				try{
					sql = "select * from sw_m_incisoTerminoContrato ";
					ps = db.conn.prepareStatement(sql);
					ResultSet rs = ps.executeQuery(sql);

					while(rs.next()){
					
						IncisoTerminoContrato IncisoTerminoContrato = new IncisoTerminoContrato(); 
						
						IncisoTerminoContrato.setIdIncisoTerminoContrato(rs.getInt("idIncisoTerminoContrato"));
						IncisoTerminoContrato.setNombre(rs.getString("nombre"));
						IncisoTerminoContrato.setDescripcion(rs.getString("descripcion"));
						IncisoTerminoContrato.setIncisoTerminoContrato(rs.getString("incisoTerminoContrato"));
						IncisoTerminoContrato.setIdArticuloTerminoContrato(rs.getInt("idArticuloTerminoContrato"));
					
				
						lista.add(IncisoTerminoContrato);

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
	
	
			public static IncisoTerminoContrato getIncisoTerminoContratoByIdInciso(int id) throws Exception  {
				
				PreparedStatement ps = null;
				String sql = "";
				ConnectionDB db = new ConnectionDB();

				IncisoTerminoContrato IncisoTerminoContrato = new IncisoTerminoContrato(); 

				try{
					sql = "select * from sw_m_incisoTerminoContrato "
					+ " where idIncisoTerminoContrato = '"+id+"' ";
					ps = db.conn.prepareStatement(sql);
					ResultSet rs = ps.executeQuery(sql);

					while(rs.next()){
					
						IncisoTerminoContrato.setIdIncisoTerminoContrato(rs.getInt("idIncisoTerminoContrato"));
						IncisoTerminoContrato.setNombre(rs.getString("nombre"));
						IncisoTerminoContrato.setDescripcion(rs.getString("descripcion"));
						IncisoTerminoContrato.setIncisoTerminoContrato(rs.getString("incisoTerminoContrato"));
						IncisoTerminoContrato.setIdArticuloTerminoContrato(rs.getInt("idArticuloTerminoContrato"));

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
				return IncisoTerminoContrato;
				
			}// fin metodo	
			
			
public static ArrayList<IncisoTerminoContrato> getIncisoTerminoContratoByIdArticulo(int id) throws Exception  {
				
				PreparedStatement ps = null;
				String sql = "";
				ConnectionDB db = new ConnectionDB();

				ArrayList<IncisoTerminoContrato> lista = new ArrayList<IncisoTerminoContrato>(); 
				

				try{
					sql = "select * from sw_m_incisoTerminoContrato "
					+ " where idArticuloTerminoContrato = '"+id+"' ";
					ps = db.conn.prepareStatement(sql);
					ResultSet rs = ps.executeQuery(sql);

					while(rs.next()){
						
						IncisoTerminoContrato IncisoTerminoContrato = new IncisoTerminoContrato(); 
					
						IncisoTerminoContrato.setIdIncisoTerminoContrato(rs.getInt("idIncisoTerminoContrato"));
						IncisoTerminoContrato.setNombre(rs.getString("nombre"));
						IncisoTerminoContrato.setDescripcion(rs.getString("descripcion"));
						IncisoTerminoContrato.setIncisoTerminoContrato(rs.getString("incisoTerminoContrato"));
						IncisoTerminoContrato.setIdArticuloTerminoContrato(rs.getInt("idArticuloTerminoContrato"));

						lista.add(IncisoTerminoContrato);
						
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
				
			}// fin metodo	
			
	
}

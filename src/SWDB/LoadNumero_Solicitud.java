package SWDB;

import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;




import lib.db.ConnectionDB;


import lib.classSW.LoadNumeroSolicitud;
import lib.db.ConnectionDB;

public class LoadNumero_Solicitud {
	
	public static ArrayList<LoadNumeroSolicitud> LoadNumeroSolicitudes(int entero ) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<LoadNumeroSolicitud> data = new ArrayList<LoadNumeroSolicitud>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select * from peticion_trabajador where id_peticion ="+entero;
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				LoadNumeroSolicitud e = new LoadNumeroSolicitud();
				e.setId_peticion(rs.getInt("id_peticion"));
				e.setFaena(rs.getString("faena"));
				e.setFecha_inicio(rs.getString("fecha_inicio"));
				//e.setUsuario(rs.getString("usuario"));
				
	
			
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

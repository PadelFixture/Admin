package SWDB;



import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;

import lib.classSW.notificacionPreseleccion;
import lib.db.ConnectionDB;

public class notificacionSolicitud {
	
	public static ArrayList<notificacionPreseleccion> loadNotificacionPreseleccion() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<notificacionPreseleccion> data = new ArrayList<notificacionPreseleccion>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select P.id_reclutamiento, sum(P.cantidad) as cantidad_total, P.obra as obra,P.fecha_inicio, R.empresa from peticion_trabajador P join reclutamiento_c R on P.id_reclutamiento = R.id_reclutamiento where R.estado = 1 group by P.obra,P.id_reclutamiento,P.fecha_inicio order by P.id_reclutamiento desc; ";
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				notificacionPreseleccion e = new notificacionPreseleccion();
				e.setId_orden(rs.getInt("id_reclutamiento"));
				e.setCantidad_total(rs.getInt("cantidad_total"));
				e.setObra(rs.getString("obra"));
				e.setFecha_inicio(rs.getString("fecha_inicio"));
				e.setEmpresa(rs.getString("empresa"));
				
				
			
					
					
					
					
				
				data.add(e); 
				
			}
			
			
			
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

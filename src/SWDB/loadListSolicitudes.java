package SWDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;



import lib.classSW.ListaSolicitudes;
import lib.db.ConnectionDB;

public class loadListSolicitudes {
	
	public static ArrayList<ListaSolicitudes> loadListaSolicitudes(String reclutamiento) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<ListaSolicitudes> data = new ArrayList<ListaSolicitudes>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select P.id_reclutamiento,sum(P.cantidad) as cantidad_total,"
					+ " (select faena from faena where codigo =  P.obra )AS faena,"
					+ " P.fecha_inicio,R.usuario,(select count(*) from preseleccionados "
					+ "where codigo_peticion = P.id_reclutamiento and status = 'Seleccionado') "
					+ "as seleccionado, (select count(*) from preseleccionados where codigo_peticion ="
					+ "P.id_reclutamiento and status = 'Preseleccionado') as preseleccionado, sum(P.cantidad) - "
					+ "(select sum((SELECT count(status) from preseleccionados where status ="
					+ " 'Contratado' and id_peticion =  P.id_reclutamiento and codigo_peticion ="
					+ " P.id_peticion)))as saldo from peticion_trabajador P join reclutamiento_c"
					+ " R on P.id_reclutamiento = R.id_reclutamiento where R.estado = 1 ";
					if ("null".equals(reclutamiento)) {
					} else {
						sql += " and R.id_reclutamiento = " + reclutamiento + "";
					}
					sql += " group by"
					+ " P.obra,P.id_reclutamiento,P.fecha_inicio order by P.id_reclutamiento desc";
			
			
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				ListaSolicitudes e = new ListaSolicitudes();
				e.setId_reclutamiento(rs.getInt("id_reclutamiento"));
				e.setFaena(rs.getString("faena"));
				e.setCantidad_total(rs.getInt("cantidad_total"));
				
				e.setUsuario(rs.getString("usuario"));
				e.setPreseleccionado(rs.getInt("preseleccionado"));
				e.setSeleccionado(rs.getInt("seleccionado"));
				e.setTotal_saldo(rs.getInt("saldo"));
				
				
			
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

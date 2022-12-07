package lib.db.sw;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lib.classSW.CentraRowReporte;
import lib.data.json.sw.Periodos;
import lib.db.ConnectionDB;

public class CentralizacionReporteDB {
	
	private final static Logger LOG = LoggerFactory.getLogger(CentralizacionReporteDB.class);

	public static ArrayList<CentraRowReporte> getCentralizacionReporte(String soc, int periodo) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		
		ArrayList<CentraRowReporte> lista = new ArrayList<CentraRowReporte>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "call SAN_CLEMENTE.sw_createCentralizacionReporte( ? , ? )";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, soc);
			ps.setInt(2, periodo);
			ResultSet rs = ps.executeQuery();
			
			Integer total = 0; 
			
			while(rs.next()){
				CentraRowReporte c = new CentraRowReporte();
				c.setSociedad(rs.getString("sociedad"));
				c.setConcepto(rs.getString("concepto"));
				c.setDescripcion(rs.getString("descripcion"));
				c.setProveedor(rs.getString("proveedor"));
				String a = "0";
				try{
				a = rs.getString("valor"); 
				a=a.replace(".", "");
				}catch(Exception e){ 
				a = "0";	
				} 
				
				total += Integer.parseInt(a);
				
				
				
				c.setMonto(new BigDecimal(a));
				//c.setCodTrabajador(rs.getInt("codTrabajador")); Se removio codTrabajador
				c.setIdCECO(rs.getString("idCECO"));
				c.setCuenta(rs.getString("cuenta"));
				c.setOrdenco(rs.getString("ordenco"));
				c.setCodTrabajador(rs.getInt("codTrabajador"));
				c.setTipoContrato(rs.getString("tipoContrato"));
				
				lista.add(c);
			}
			
			
			boolean costoAgro = false;
			int nCostoAgro = 0;
			int nTotalHaberes = 0;
			
			for (CentraRowReporte CentraRowReporte : lista) {
				if("COSTO AGRO".equals(CentraRowReporte.getConcepto())){
					costoAgro = true;
				}
			}
			
			if(costoAgro == true){
				//Si existe una diferencia en el total de la CentralizacionReporte
				//restar esa diferencia en el "TOTAL DE HABERES";
				if(total <= -1000 || total >= 1000) {
					LOG.info("Error CentralizacionReporte con "+total+ " pesos de diferencia");
				}
				else if(total < 0){			
					for (int i = 0; i < lista.size(); i++) {
						if("COSTO AGRO".equals(lista.get(i).getConcepto())){
							if(nCostoAgro < 1){
								
							lista.get(i).setMonto(lista.get(i).getMonto().subtract(new BigDecimal(total)));
							
							}
							nCostoAgro++;
						}
					}
		
				}else if(total > 0){
					for (int i = 0; i < lista.size(); i++) {
						if("COSTO AGRO".equals(lista.get(i).getConcepto())){
							if(nCostoAgro < 1){
							lista.get(i).setMonto(lista.get(i).getMonto().subtract(new BigDecimal(total)));
							}
							nCostoAgro++;
						}
					}
				}
			}else{
				//Si existe una diferencia en el total de la CentralizacionReporte
				//restar esa diferencia en el "TOTAL DE HABERES";
				if(total <= -1000 || total >= 1000) {
					LOG.info("Error CentralizacionReporte con "+total+ " pesos de diferencia");
				}
				else if(total < 0){			
					for (int i = 0; i < lista.size(); i++) {
						if("TOTAL HABERES".equals(lista.get(i).getConcepto())){
							if(nTotalHaberes < 1){
							lista.get(i).setMonto(lista.get(i).getMonto().subtract(new BigDecimal(total)));
							}
							nTotalHaberes++;
						}
					}
		
				}else if(total > 0){
					for (int i = 0; i < lista.size(); i++) {
						if("TOTAL HABERES".equals(lista.get(i).getConcepto())){
							if(nTotalHaberes < 1){
							lista.get(i).setMonto(lista.get(i).getMonto().subtract(new BigDecimal(total)));
							}
							nTotalHaberes++;
						}
					}
				}	
			}		
			
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
			LOG.info("Error CentralizacionReporteDB con: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}

	public static ArrayList<Periodos> getPeriodosBy(String soc) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Periodos> lista = new ArrayList<Periodos>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select sociedad.sociedad as sociedad, sw_liquidacion.periodo from sw_liquidacion "+
					"inner join sociedad on sociedad.idSociedad=sw_liquidacion.id_sociedad "+
					"where sociedad.sociedad='"+soc+"' "+
					"group by sociedad.sociedad, sw_liquidacion.periodo order by periodo DESC";
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				Periodos p = new Periodos();
				p.setSociedad(rs.getString("sociedad"));
				p.setPeriodo(rs.getInt("periodo"));
				lista.add(p);
			}
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}

	public static void updateEstadoCentralizacionReporte(String id_CentralizacionReporte) throws SQLException {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;
		
		try {

			sql = " UPDATE sw_CentralizacionReporte SET estado = 0 WHERE id_CentralizacionReporte = ? ";
			ps = db.conn.prepareStatement(sql);
			ps.setString(i++, id_CentralizacionReporte);
			ps.execute();
			
		}catch (Exception e) {
			System.out.println("Error Al Actualizar Informacion de La CentralizacionReporte: " + e.getMessage());
			
		}finally {
			ps.close();
			db.close();
		}		
		
		
		
		
	}

}

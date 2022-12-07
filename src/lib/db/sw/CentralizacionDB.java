package lib.db.sw;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lib.classSW.CentraRow;
import lib.classSW.Centralizacion;
import lib.data.json.sw.Periodos;
import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;

public class CentralizacionDB {
	
	private final static Logger LOG = LoggerFactory.getLogger(CentralizacionDB.class);

	public static ArrayList<CentraRow> getCentralizacion(String soc, int periodo) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		
		ArrayList<CentraRow> lista = new ArrayList<CentraRow>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "call SAN_CLEMENTE.sw_createCentralizacion( ? , ? )";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, soc);
			ps.setInt(2, periodo);
			ResultSet rs = ps.executeQuery();
			
			Integer total = 0; 
			
			while(rs.next()){
				CentraRow c = new CentraRow();
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
				
				lista.add(c);
			}
			
			
			boolean costoAgro = false;
			int nCostoAgro = 0;
			int nTotalHaberes = 0;
			
			for (CentraRow centraRow : lista) {
				if("COSTO AGRO".equals(centraRow.getConcepto())){
					costoAgro = true;
				}
			}
			
			if(costoAgro == true){
				//Si existe una diferencia en el total de la centralizacion
				//restar esa diferencia en el "TOTAL DE HABERES";
				if(total <= -1000 || total >= 1000) {
					LOG.info("Error centralizacion con "+total+ " pesos de diferencia");
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
				//Si existe una diferencia en el total de la centralizacion
				//restar esa diferencia en el "TOTAL DE HABERES";
				if(total <= -1000 || total >= 1000) {
					LOG.info("Error centralizacion con "+total+ " pesos de diferencia");
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
			LOG.info("Error CentralizacionDB con: " + e.getMessage());
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

	public static Centralizacion insertOrUpdateCentralizacion(Centralizacion centralizacion) throws Exception {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ResultSet rs = null;

		try{

			int i = 1;

			sql = " SELECT id_centralizacion FROM sw_centralizacion  WHERE periodo = ? and id_sociedad = ? ";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++, centralizacion.getPeriodo());
			ps.setString(i++, centralizacion.getId_sociedad());
			rs = ps.executeQuery();

			if(!rs.next()){
				i=1;
				rs.close();
				ps.close();

				sql= " INSERT INTO sw_centralizacion (id_centralizacion) VALUES (?) ";
				ps = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(i++, centralizacion.getId_centralizacion());
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				int key = 0;
				if(rs.next()){
					key = rs.getInt(1);
				}

				centralizacion.setId_centralizacion(key);
			}

			i=1;
			centralizacion.setId_centralizacion(rs.getInt(1));
			rs.close();
			ps.close();

			sql = " UPDATE sw_centralizacion SET periodo = ? , id_sociedad = ? , "
											  + " fecha_envio = ? , SAP = ?, entrada_sap = ?, salida_sap = ?,  "
											  + " fecha_proceso = ? "
					+ " WHERE id_centralizacion = ? ";

			ps = db.conn.prepareStatement(sql);

			ps.setInt(i++, centralizacion.getPeriodo());
			ps.setString(i++, centralizacion.getId_sociedad());
			ps.setString(i++, GeneralUtility.getTodayDate());
			ps.setString(i++, centralizacion.getSap());
			ps.setString(i++, centralizacion.getEntrada_sap());
			ps.setString(i++, centralizacion.getSalida_sap());
			ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(centralizacion.getFecha_proceso()));
			ps.setInt(i++, centralizacion.getId_centralizacion());
			
			ps.toString();
			
			ps.execute();

			return centralizacion;

		}catch (Exception e) {
			System.out.println("Error Al guardar Informacion de La Centralizacion: " + e.getMessage());
			return null;
		}finally {
			ps.close();
			db.close();
		}		
		
	}

	public static ArrayList<Centralizacion> getCentralizacionWithFilter(ArrayList<filterSql> filter) {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try {

			// Crear sentencia en Sql
			sql = " SELECT * FROM sw_centralizacion c LEFT JOIN sociedad sc ON ( c.id_sociedad = sc.sociedad ) ";
			ps = db.conn.prepareStatement(sql);

			// Si contiene datos asignarlo al WHERE
			if (filter.size() > 0) {
				String andSql = "";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext()) {
					filterSql row = f.next();

					if (!row.getValue().equals("")) {

						if (row.getCampo().endsWith("fecha_proceso")) {

							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
							sql += andSql + row.getCampo() + " ='"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						}

						else if (GeneralUtility.isNumeric(row.getValue())) {
							sql += andSql + row.getCampo() + " = '" + row.getValue() + "'";
						}
						else{
							sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
						}
						andSql = " and ";
					}
				} // Fin While

			}

			ResultSet rs = ps.executeQuery(sql);
		
		ArrayList<Centralizacion> lista = new ArrayList<>(); 

			while (rs.next()) {

				Centralizacion centralizacion = new Centralizacion(); 
				
				centralizacion.setEntrada_sap(rs.getString("entrada_sap"));
				centralizacion.setFecha_envio(rs.getString("fecha_envio"));
				centralizacion.setFecha_proceso(rs.getString("fecha_proceso"));
				centralizacion.setId_centralizacion(rs.getInt("id_centralizacion"));
				centralizacion.setId_sociedad(rs.getString("id_sociedad"));
				centralizacion.setPeriodo(rs.getInt("periodo"));
				centralizacion.setSalida_sap(rs.getString("salida_sap"));
				centralizacion.setSap(rs.getString("SAP"));
				centralizacion.setNombre_sociedad(rs.getString("denominacionSociedad"));
				
				lista.add(centralizacion);
				
			}

			ps.close();
			return lista;
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		} finally {
		
			db.close();
		}
		
		
	}

	public static void updateEstadoCentralizacion(String id_centralizacion) throws SQLException {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;
		
		try {

			sql = " UPDATE sw_centralizacion SET estado = 0 WHERE id_centralizacion = ? ";
			ps = db.conn.prepareStatement(sql);
			ps.setString(i++, id_centralizacion);
			ps.execute();
			
		}catch (Exception e) {
			System.out.println("Error Al Actualizar Informacion de La Centralizacion: " + e.getMessage());
			
		}finally {
			ps.close();
			db.close();
		}		
		
		
		
		
	}

}

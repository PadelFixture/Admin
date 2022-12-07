package lib.db.sw;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import lib.classSW.CentraRow;
import lib.classSW.CentralizacionDetalleTmp;
import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;

public class CentralizacionDetalleTmpDB {

public static ArrayList<CentralizacionDetalleTmp> insertOrUpdateCentralizacionDetalleTmp(ArrayList<CentralizacionDetalleTmp> centralizacionDetalleTmpList) throws Exception {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ResultSet rs = null;

		try{

		for (CentralizacionDetalleTmp centralizacionDetalleTmp : centralizacionDetalleTmpList) {
			
			int i = 1;
			sql = " SELECT id_centralizacionDetalleTmp FROM sw_centralizacionDetalle_tmp  WHERE 1 = 1 ";
			
			if(centralizacionDetalleTmp.getPeriodo() != 0){
				sql += " AND periodo = ? ";
			}
			if(centralizacionDetalleTmp.getId_sociedad() != null){
				sql += " AND id_sociedad = ? ";
			}
			if(centralizacionDetalleTmp.getConcepto() != null){
				sql += " AND concepto = ? ";
			}
			if(centralizacionDetalleTmp.getDescripcion() != null){
				sql += " AND descripcion = ? ";
			}
			if(centralizacionDetalleTmp.getProveedor() != null){
				sql += " AND proveedor = ? ";
			}
			if(centralizacionDetalleTmp.getCuenta() != null){
				sql += " AND cuenta = ? ";
			}
			if(centralizacionDetalleTmp.getCodTrabajador() != null){
				sql += " AND codTrabajador = ? ";
			}
			
			ps = db.conn.prepareStatement(sql);
			
			if(centralizacionDetalleTmp.getPeriodo() != 0){
				ps.setInt(i++, centralizacionDetalleTmp.getPeriodo());
			}
			if(centralizacionDetalleTmp.getId_sociedad() != null){
				ps.setString(i++, centralizacionDetalleTmp.getId_sociedad());
			}
			if(centralizacionDetalleTmp.getConcepto() != null){
				ps.setString(i++, centralizacionDetalleTmp.getConcepto());
			}
			if(centralizacionDetalleTmp.getDescripcion() != null){
				ps.setString(i++, centralizacionDetalleTmp.getDescripcion());
			}
			if(centralizacionDetalleTmp.getProveedor() != null){
				ps.setString(i++, centralizacionDetalleTmp.getProveedor());
			}
			if(centralizacionDetalleTmp.getCuenta() != null){
				ps.setString(i++, centralizacionDetalleTmp.getCuenta());
			}
			if(centralizacionDetalleTmp.getCodTrabajador() != null){
				ps.setString(i++, centralizacionDetalleTmp.getCodTrabajador());
			}
			
			rs = ps.executeQuery();
			
			if(!rs.next()){
				i=1;
				rs.close();
				ps.close();

				sql= " INSERT INTO sw_centralizacionDetalle_tmp (id_sociedad, periodo) VALUES (?) ";
				ps = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(i++, centralizacionDetalleTmp.getId_sociedad());
				ps.setInt(i++, centralizacionDetalleTmp.getPeriodo());
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

			}
			
			i=1;
		
			rs.close();
			ps.close();
			
			sql = " UPDATE sw_centralizacionDetalleTmp SET    "
					  + " concepto = ?, descripcion = ?, proveedor = ?, "
					  + " cuenta = ? , valor = ?, codTrabajador = ?, idCECO = ?, fecha_proceso = ? "
					  + " WHERE id_sociedad = ? AND periodo = ? ";

			ps = db.conn.prepareStatement(sql);		
		
			ps.setInt(i++, centralizacionDetalleTmp.getPeriodo());
			ps.setString(i++, centralizacionDetalleTmp.getId_sociedad());
			ps.setString(i++, centralizacionDetalleTmp.getConcepto());
			ps.setString(i++, centralizacionDetalleTmp.getDescripcion());
			ps.setString(i++, centralizacionDetalleTmp.getProveedor());
			ps.setString(i++, centralizacionDetalleTmp.getCuenta());
			ps.setString(i++, centralizacionDetalleTmp.getValor());
			ps.setString(i++, centralizacionDetalleTmp.getCodTrabajador());
			ps.setString(i++, centralizacionDetalleTmp.getIdCECO());
			ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(centralizacionDetalleTmp.getFecha_proceso()));
			
			ps.toString();
			
			ps.execute();
			
			rs.close();
			ps.close();
				
		}
		
			return centralizacionDetalleTmpList;

		}catch (Exception e) {
			System.out.println("Error Al guardar Informacion de La CentralizacionDetalle: " + e.getMessage());
			return null;
		}finally {
			ps.close();
			db.close();
		}		
		
	}
	
	//get
public static ArrayList<CentralizacionDetalleTmp> getCentralizacionDetalleTmp(ArrayList<filterSql> filter) {
	
	PreparedStatement ps = null;
	String sql = "";
	ConnectionDB db = new ConnectionDB();

	try {

		// Crear sentencia en Sql
		sql = "SELECT * FROM sw_centralizacionDetalle_tmp ";
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
					else if ("usuario".equals(row.getCampo())) {
						sql += andSql + " 1 = 1 ";
					}
					else{
						sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
					}
					andSql = " and ";
				}
			} // Fin While

		}

		ResultSet rs = ps.executeQuery(sql);
	
	ArrayList<CentralizacionDetalleTmp> lista = new ArrayList<>(); 

		while (rs.next()) {

			CentralizacionDetalleTmp centralizacionDetalleTmp = new CentralizacionDetalleTmp(); 
			
			centralizacionDetalleTmp.setCodTrabajador(rs.getString("codTrabajador"));
			centralizacionDetalleTmp.setConcepto(rs.getString("concepto"));
			centralizacionDetalleTmp.setCuenta(rs.getString("cuenta"));
			centralizacionDetalleTmp.setDescripcion(rs.getString("descripcion"));
			centralizacionDetalleTmp.setFecha_proceso(rs.getString("fecha_proceso"));
			centralizacionDetalleTmp.setId_sociedad(rs.getString("id_sociedad"));
			centralizacionDetalleTmp.setIdCECO(rs.getString("idCECO"));
			centralizacionDetalleTmp.setPeriodo(rs.getInt("periodo"));
			centralizacionDetalleTmp.setProveedor(rs.getString("proveedor"));
			centralizacionDetalleTmp.setValor(rs.getString("valor"));
			centralizacionDetalleTmp.setOrdenCO(rs.getString("ordenCO"));
			
			lista.add(centralizacionDetalleTmp);
			
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


public static boolean deleteCentralizacionDetalleTmp(ArrayList<filterSql> filter) {
	
	PreparedStatement ps = null;
	String sql = "";
	ConnectionDB db = new ConnectionDB();

	try {

		// Crear sentencia en Sql
		sql = "DELETE FROM sw_centralizacionDetalle_tmp ";
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
					else if ("usuario".equals(row.getCampo())) {
						sql += andSql + " 1 = 1 ";
					}
					else{
						sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
					}
					andSql = " and ";
				}
			} // Fin While

		}

		ps.execute();
	
	return true;
		
	} catch (Exception e) {
		System.out.println("Error: " + e.getMessage());
		return false;
	} finally {
	
		db.close();
	}
	
	
}

public static ArrayList<CentraRow> centralizacionDetalleToCentraRow(
		ArrayList<CentralizacionDetalleTmp> centralizacionDetalle) {
	
	
	ArrayList<CentraRow> rows = new ArrayList<CentraRow>(); 
	
	for (CentralizacionDetalleTmp centraDetalle : centralizacionDetalle) {
		
		CentraRow row = new CentraRow();
		
		row.setSociedad(centraDetalle.getId_sociedad());
		row.setConcepto(centraDetalle.getConcepto());
		row.setDescripcion(centraDetalle.getDescripcion());
		row.setProveedor(centraDetalle.getProveedor());
		
		BigDecimal monto;
		String valor;
		
		try {
			
			if(centraDetalle.getValor().indexOf(".") != -1){
				valor = centraDetalle.getValor().substring(0, centraDetalle.getValor().indexOf("."));
			}else{
				valor = centraDetalle.getValor();	
			}
		     monto = new BigDecimal(valor);
		} catch (Exception e) {
			monto = new BigDecimal(0.0);
		}
		
		row.setMonto(monto);
		row.setIdCECO(centraDetalle.getIdCECO());
		row.setCuenta(centraDetalle.getCuenta());
		row.setOrdenco(centraDetalle.getOrdenCO());
		
		rows.add(row);
		
	}
	
	return rows;
	
}





	
}

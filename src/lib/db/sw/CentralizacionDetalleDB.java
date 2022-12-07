package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import lib.classSW.CentralizacionDetalle;
import lib.db.ConnectionDB;
import lib.utils.GeneralUtility;

public class CentralizacionDetalleDB {

public static ArrayList<CentralizacionDetalle> insertOrUpdateCentralizacionDetalle(ArrayList<CentralizacionDetalle> centralizacionDetalleList) throws Exception {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ResultSet rs = null;

		try{

		for (CentralizacionDetalle centralizacionDetalle : centralizacionDetalleList) {
			
			int i = 1;
			sql = " SELECT id_centralizacionDetalle FROM sw_centralizacionDetalle  WHERE id_centralizacion = ? ";
			
			if(centralizacionDetalle.getPeriodo() != 0){
				sql += " AND periodo = ? ";
			}
			if(centralizacionDetalle.getId_sociedad() != null){
				sql += " AND id_sociedad = ? ";
			}
			if(centralizacionDetalle.getConcepto() != null){
				sql += " AND concepto = ? ";
			}
			if(centralizacionDetalle.getDescripcion() != null){
				sql += " AND descripcion = ? ";
			}
			if(centralizacionDetalle.getProveedor() != null){
				sql += " AND proveedor = ? ";
			}
			if(centralizacionDetalle.getCuenta() != null){
				sql += " AND cuenta = ? ";
			}
			
			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++, centralizacionDetalle.getId_centralizacion());
			
			if(centralizacionDetalle.getPeriodo() != 0){
				ps.setInt(i++, centralizacionDetalle.getPeriodo());
			}
			if(centralizacionDetalle.getId_sociedad() != null){
				ps.setString(i++, centralizacionDetalle.getId_sociedad());
			}
			if(centralizacionDetalle.getConcepto() != null){
				ps.setString(i++, centralizacionDetalle.getConcepto());
			}
			if(centralizacionDetalle.getDescripcion() != null){
				ps.setString(i++, centralizacionDetalle.getDescripcion());
			}
			if(centralizacionDetalle.getProveedor() != null){
				ps.setString(i++, centralizacionDetalle.getProveedor());
			}
			if(centralizacionDetalle.getCuenta() != null){
				ps.setString(i++, centralizacionDetalle.getCuenta());
			}
			
			rs = ps.executeQuery();
			
			if(!rs.next()){
				i=1;
				rs.close();
				ps.close();

				sql= " INSERT INTO sw_centralizacionDetalle (id_centralizacionDetalle) VALUES (?) ";
				ps = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(i++, centralizacionDetalle.getId_centralizacionDetalle());
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				int key = 0;
				if(rs.next()){
					key = rs.getInt(1);
				}

				centralizacionDetalle.setId_centralizacionDetalle(key);
			}
			
			i=1;
			centralizacionDetalle.setId_centralizacionDetalle(rs.getInt(1));
			rs.close();
			ps.close();
			
			sql = " UPDATE sw_centralizacionDetalle SET  id_centralizacion = ?, cod_sap = ?,  periodo = ?,  "
					  +"  id_sociedad = ?, concepto = ?, descripcion = ?, proveedor = ?, "
					  + " cuenta = ? , valor = ?, idCECO = ?, fecha_proceso = ?, ordenco = ? "
					  + " WHERE id_centralizacionDetalle = ? ";

			ps = db.conn.prepareStatement(sql);		
		
			ps.setInt(i++, centralizacionDetalle.getId_centralizacion());
			ps.setString(i++, centralizacionDetalle.getCod_sap());
			ps.setInt(i++, centralizacionDetalle.getPeriodo());
			ps.setString(i++, centralizacionDetalle.getId_sociedad());
			ps.setString(i++, centralizacionDetalle.getConcepto());
			ps.setString(i++, centralizacionDetalle.getDescripcion());
			ps.setString(i++, centralizacionDetalle.getProveedor());
			ps.setString(i++, centralizacionDetalle.getCuenta());
			ps.setString(i++, centralizacionDetalle.getValor());
			ps.setString(i++, centralizacionDetalle.getIdCECO());
			ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(centralizacionDetalle.getFecha_proceso()));
			ps.setString(i++, centralizacionDetalle.getOrdenco());
			ps.setInt(i++, centralizacionDetalle.getId_centralizacionDetalle());
			
			ps.toString();
			
			ps.execute();
			
			rs.close();
			ps.close();
				
		}
		
			return centralizacionDetalleList;

		}catch (Exception e) {
			System.out.println("Error Al guardar Informacion de La CentralizacionDetalle: " + e.getMessage());
			return null;
		}finally {
			ps.close();
			db.close();
		}		
		
	}
	
	
}

package lib.SADB;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lib.classSW.Contratos;
import lib.db.ConnectionDB;
import lib.utils.GeneralUtility;

public class SeparacionDB {

	
	public static String updateSeparacion(Contratos contrato) throws Exception{
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;
		
		try {

			sql = "" + " UPDATE contratos " + " SET " 
			+ " FechaTerminoContrato = ?, " //+ " EstadoContrato = ?, "
			+ " articuloTerminoContrato = ?, "
			+ " incisoTerminoContrato = ?, " 
			+ " letraTerminoContrato = ?, " 
			+ " fechaPago = ?, "
			+ " lugarPago = ?, " 
			+ " horaPago = ?, "
			+ " horaPago2 = ?, " 
			+ " paraFiniquitar = ?, " 
			+ " fechaNotificacion = ?, " 
			+ " descripcion = ? " 
			+ " WHERE codigo_trabajador = ? AND id = ? ";

			ps = db.conn.prepareStatement(sql);
		
			ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(contrato.getFechaTerminoContrato()));
			ps.setInt(i++, contrato.getArticuloTerminoContrato());
			ps.setInt(i++, contrato.getIncisoTerminoContrato());
			ps.setInt(i++, contrato.getLetraTerminoContrato());
			ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(contrato.getFechaPago()));
			ps.setString(i++, contrato.getLugarPago());
			ps.setString(i++, contrato.getHoraPago());
			ps.setString(i++, contrato.getHoraPago2());
			ps.setInt(i++, contrato.getParaFiniquitar());
			ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(contrato.getFechaNotificacion()));
			ps.setString(i++, contrato.getDescripcion());
			ps.setInt(i++, contrato.getCodigo_trabajador());
			ps.setInt(i++, contrato.getId());

			System.out.println(ps.toString());
			
			ps.execute();

			return "Actualizacion Exitosa";

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			throw new Exception(e);
		} finally {
			ps.close();
			db.close();
		}
	
	};
	
	
public static String deleteSeparacion(Contratos contrato) throws Exception{
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;
		
		try {

			sql = "" + " UPDATE contratos " + " SET " 
			+ " FechaTerminoContrato = ?, " //+ " EstadoContrato = ?, "
			+ " articuloTerminoContrato = ?, "
			+ " incisoTerminoContrato = ?, " 
			+ " letraTerminoContrato = ?, " 
			+ " fechaPago = ?, "
			+ " lugarPago = ?, " 
			+ " horaPago = ?, "
			+ " horaPago2 = ?, " 
			+ " paraFiniquitar = ?, " 
			+ " fechaNotificacion = ?, " 
			+ " descripcion = ? " 
			+ " WHERE codigo_trabajador = ? AND id = ? ";

			ps = db.conn.prepareStatement(sql);
		
			ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(null));
			ps.setInt(i++, 0);
			ps.setInt(i++, 0);
			ps.setInt(i++, 0);
			ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(null));
			ps.setString(i++, null);
			ps.setString(i++, null);
			ps.setString(i++, null);
			ps.setInt(i++, 0);
			ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(null));
			ps.setString(i++, null);
			ps.setInt(i++, contrato.getCodigo_trabajador());
			ps.setInt(i++, contrato.getId());

			System.out.println(ps.toString());
			
			ps.execute();

			return "Datos de Separacion Eliminada";

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return e.getMessage();
		} finally {
			ps.close();
			db.close();
		}
	
	};
	
	
	
	
	public static Blob getCartaTermino() throws Exception {

		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		
		try {

			ps = db.conn.createStatement();
			sql = "SELECT file FROM sw_template WHERE documento = 'Carta de Termino' " ;
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				Blob cartaTermino = rs.getBlob(1);
				return cartaTermino;
			}
			rs.close();
			ps.close();
			db.conn.close();

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getTrabajadorAll: " + e.getMessage());
		} finally {
			db.close();
		}

		return null;
	}
	
	
	
	
}

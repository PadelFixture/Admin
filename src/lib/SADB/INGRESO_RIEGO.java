package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.INGRESORIEGO;
import lib.classSA.taller;
import lib.db.ConnectionDB;

public class INGRESO_RIEGO {

	public static ArrayList<INGRESORIEGO> GET_Ingreso_Riego(String campo)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<INGRESORIEGO> lista = new ArrayList<INGRESORIEGO>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select ir.*, mi.descripcion motivo from ingreso_riego ir "
					+ "left join maestro_motivos_ingreso mi on mi.codigo = ir.motivo_ingreso"
					+ " where ir.estado = 0 and (campo = '"+campo+"' or '"+campo+"' = '0')";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				INGRESORIEGO ob = new INGRESORIEGO();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setCampo(rs.getString("campo"));
				ob.setCaseta(rs.getString("caseta"));
				ob.setEquipo(rs.getString("equipo"));
				ob.setMotivo_ingreso(rs.getString("motivo"));
				ob.setFecha(rs.getString("fecha"));
				ob.setDiagnostico_preliminar(rs.getString("diagnostico_preliminar"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}

	public static boolean ADD_Ingreso_Riego(INGRESORIEGO i) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO ingreso_riego(campo, caseta, equipo, motivo_ingreso, fecha, diagnostico_preliminar, nreserva,estado)"
				+ " VALUES(?,?,?,?,?,?,?,0)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, i.getCampo());
			ps.setString(2, i.getCaseta());
			ps.setString(3, i.getEquipo());
			ps.setString(4, i.getMotivo_ingreso());
			ps.setString(5, i.getFecha());
			ps.setString(6, i.getDiagnostico_preliminar());
			ps.setString(7, i.getNreserva());
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	
	public static boolean CERRAR_INGRESO_RIEGO(INGRESORIEGO tl) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE ingreso_riego set estado = 1, fecha_cierre = '"+tl.getFechaCierre()+ "'"
					 +" where codigo = "+ tl.getCodigo();
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	
}

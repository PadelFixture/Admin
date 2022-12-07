package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.Packing_Frigorifico;
import lib.classSA.taller;
import lib.db.ConnectionDB;

public class PackingFrigorifico {
	
	public static ArrayList<Packing_Frigorifico> GET_PackingFrigorifico(String campo)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<Packing_Frigorifico> lista = new ArrayList<Packing_Frigorifico>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select pf.*, mi.descripcion motivo from packing_frigorifico pf "
					+ "left join maestro_motivos_ingreso mi on mi.codigo = pf.motivo_ingreso "
					+ "where pf.estado = 0 and (campo = '"+campo+"' or '"+campo+"' = '0')";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				Packing_Frigorifico ob = new Packing_Frigorifico();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setCampo(rs.getString("campo"));
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

	public static boolean ADD_PackingFrigorifico(Packing_Frigorifico p) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO packing_frigorifico(campo, equipo, motivo_ingreso, fecha, diagnostico_preliminar,nreserva,estado)"
				+ " VALUES(?,?,?,?,?,?,0)";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, p.getCampo());
			ps.setString(2, p.getEquipo());
			ps.setString(3, p.getMotivo_ingreso());
			ps.setString(4, p.getFecha());
			ps.setString(5, p.getDiagnostico_preliminar());
			ps.setString(6, p.getNreserva());
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
	
	public static boolean CERRAR_INGRESO_PACKING(Packing_Frigorifico tl) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE packing_frigorifico set estado = 1, fecha_cierre = '"+tl.getFechaCierre()+ "' "
					+ " where codigo = "+ tl.getCodigo();
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

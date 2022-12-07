package SWDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSW.LoadCargoPreseleccion;
import lib.db.ConnectionDB;

public class sw_ReclutamientoJSONDB {
	public static ArrayList<LoadCargoPreseleccion> getselectBuscarPEticionReclutamientoModificar(int nReclutamiento) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<LoadCargoPreseleccion> data = new ArrayList<LoadCargoPreseleccion>();
		ConnectionDB db = new ConnectionDB();

		try {

			sql = "select "
					+"upper((select cargos from cargos where sociedad = "
					+"(select sociedad from sociedad where denominacionSociedad = "
					+"(select empresa from reclutamiento_c where id_reclutamiento = PE.id_reclutamiento)) "
					+"AND id_cargo = PE.cargo )) "
					+"as nombrecargo, "
				  + "PE.cantidad_Hombre,PE.cantidad_Mujer,PE.id_reclutamiento,PE.id_peticion,PE.fecha_inicio,PE.cargo,RE.obrafaena from peticion_trabajador PE "
				  +"inner join reclutamiento_c RE on PE.id_reclutamiento = RE.id_reclutamiento "
				  +"where PE.id_reclutamiento = "+nReclutamiento+"";

			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				LoadCargoPreseleccion e = new LoadCargoPreseleccion();

				e.setId_peticion(rs.getInt("id_peticion"));
				e.setCantidadhombre(rs.getInt("cantidad_Hombre"));
				e.setCantidadmujer(rs.getInt("cantidad_Mujer"));
				e.setFechainicio(rs.getString("fecha_inicio"));
				e.setCargoint(rs.getInt("cargo"));
				e.setObrafaena(rs.getInt("obrafaena"));
				e.setIdreclutamineto(rs.getInt("id_reclutamiento"));
				e.setNombrecargo(rs.getString("nombrecargo"));

				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			db.close();
		}
		return data;
	}
	
	public static boolean updateReclutamiento(LoadCargoPreseleccion r) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			sql = "Update reclutamiento_c set cantidad = " + r.getCantidad() + " where id_reclutamiento = "
					+ r.getIdreclutamineto() + "";
			
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			
			

			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	
	public static boolean updatePeticion(LoadCargoPreseleccion r) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			sql = "Update peticion_trabajador set cantidad_Hombre = " + r.getCantidadhombre() + ",cantidad_Mujer = " +r.getCantidadmujer()+" where id_reclutamiento = "
					+ r.getIdreclutamineto() + " and id_peticion = " +r.getId_peticion();
			
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			
			

			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
}

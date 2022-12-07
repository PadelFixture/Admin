package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSW.LetraTerminoContrato;
import lib.db.ConnectionDB;

public class LetraTerminoContratoDB {

	// get Articulo Termino Contrato
	public static ArrayList<LetraTerminoContrato> getLetraTerminoContrato() throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<LetraTerminoContrato> lista = new ArrayList<LetraTerminoContrato>();

		try {
			sql = "select * from sw_m_letraTerminoContrato ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				LetraTerminoContrato LetraTerminoContrato = new LetraTerminoContrato();

				LetraTerminoContrato.setIdLetraTerminoContrato(rs.getInt("idLetraTerminoContrato"));
				LetraTerminoContrato.setNombre(rs.getString("nombre"));
				LetraTerminoContrato.setDescripcion(rs.getString("descripcion"));
				LetraTerminoContrato.setLetraTerminoContrato(rs.getString("letraTerminoContrato"));
				LetraTerminoContrato.setIdIncisoTerminoContrato(rs.getInt("idIncisoTerminoContrato"));

				lista.add(LetraTerminoContrato);

			}

		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}// fin metodo get

	public static LetraTerminoContrato getLetraTerminoContratoByIdLetra(int id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		LetraTerminoContrato LetraTerminoContrato = new LetraTerminoContrato();

		try {
			sql = "select * from sw_m_letraTerminoContrato " + " where idLetraTerminoContrato = '" + id + "' ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				LetraTerminoContrato.setIdIncisoTerminoContrato(rs.getInt("idIncisoTerminoContrato"));
				LetraTerminoContrato.setNombre(rs.getString("nombre"));
				LetraTerminoContrato.setDescripcion(rs.getString("descripcion"));
				LetraTerminoContrato.setLetraTerminoContrato(rs.getString("letraTerminoContrato"));
				LetraTerminoContrato.setIdIncisoTerminoContrato(rs.getInt("idIncisoTerminoContrato"));

			}

		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return LetraTerminoContrato;

	}// fin metodo

	public static ArrayList<LetraTerminoContrato> getLetraTerminoContratoByIdInciso(int id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<LetraTerminoContrato> lista = new ArrayList<LetraTerminoContrato>();
		

		try {
			sql = "select * from sw_m_letraTerminoContrato " + " where idIncisoTerminoContrato = '" + id + "' ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				LetraTerminoContrato LetraTerminoContrato = new LetraTerminoContrato();
				
				LetraTerminoContrato.setIdIncisoTerminoContrato(rs.getInt("idIncisoTerminoContrato"));
				LetraTerminoContrato.setNombre(rs.getString("nombre"));
				LetraTerminoContrato.setDescripcion(rs.getString("descripcion"));
				LetraTerminoContrato.setLetraTerminoContrato(rs.getString("letraTerminoContrato"));
				LetraTerminoContrato.setIdIncisoTerminoContrato(rs.getInt("idIncisoTerminoContrato"));
				
				lista.add(LetraTerminoContrato);

			}

		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;

	}// fin metodo

}

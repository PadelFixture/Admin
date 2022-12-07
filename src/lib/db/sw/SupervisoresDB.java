package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSW.GrupoSupervisor;
import lib.classSW.Supervisor;
import lib.classSW.Tbjdr;
import lib.db.ConnectionDB;

public class SupervisoresDB {

	public static boolean crearSupervisor(Supervisor s) {
		// TODO Auto-generated method stub
		return false;
	}
	public static Supervisor obtenerSupervisorPorId(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		Supervisor s = new Supervisor();
		try {
			sql = "";
			ps = db.conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return s;
	}

	public static Supervisor obtenerSupervisorPorCodigo(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<Supervisor> ObtenerSupervisores() {
		// TODO Auto-generated method stub
		return null;
	}
	public static ArrayList<Supervisor> ObtenerSupervisoresPorEmpresa(String sociedad) {
		// TODO Auto-generated method stub
		return null;
	}
	public static boolean actualizarEstadoDeSupervisor(Supervisor s){
		// TODO Auto-generated method stub
		return false;		
	}
	public static ArrayList<GrupoSupervisor> getGrupoSupervisores() throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<GrupoSupervisor> Lista = new ArrayList<GrupoSupervisor>();
		try {
			sql = "SELECT sw_r_grupoSupervisor.*, trabajadores.id, trabajadores.codigo, trabajadores.apellidoPaterno, trabajadores.apellidoMaterno,  trabajadores.nombre, trabajadores.idHuerto, campo.descripcion, sociedad.idSociedad, sociedad.sociedad,  sociedad.denominacionSociedad FROM trabajadores "+
					"inner join sw_r_grupoSupervisor on trabajadores.codigo=sw_r_grupoSupervisor.codSupervisor "+
					"inner join campo on campo.campo=trabajadores.idHuerto "+
					"inner join sociedad on campo.sociedad=sociedad.sociedad;";
			ps = db.conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Supervisor s= new Supervisor();
				GrupoSupervisor gs= new GrupoSupervisor();
				gs.setIdGrupoSupervisor(rs.getInt("idGrupoSupervisor"));
				gs.setNombreGrupo(rs.getString("nombreGrupo"));
				s.setIdSupervisor(rs.getInt("codSupervisor"));
				s.setNombre(rs.getString("nombre"));
				s.setApellidoPaterno(rs.getString("apellidoPaterno"));
				s.setApellidoMaterno(rs.getString("apellidoMaterno"));
				s.setIdHuerto(rs.getString("idHuerto"));
				s.setNombreHuerto(rs.getString("descripcion"));
				s.setSociedad(rs.getString("sociedad"));
				s.setNombreSociedad(rs.getString("denominacionSociedad"));
				gs.setSupervisor(s);
				Lista.add(gs);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return Lista;
	}
	public static ArrayList<Tbjdr> getGrupoSupervisoresByIdGrupo(int idGrupo) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<Tbjdr> Lista = new ArrayList<Tbjdr>();
		try {
			sql = "";
			ps = db.conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				
				
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return Lista;
	}

}

package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSW.Tbjdr;
import lib.db.ConnectionDB;

public class TbjdrDB {

	public static ArrayList<Tbjdr> getTrabajadores() throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<Tbjdr> Lista = new ArrayList<Tbjdr>();
		try {
			sql = "SELECT sw_r_grupoTrabajador.*, trabajadores.id, trabajadores.codigo, "+
					"trabajadores.apellidoPaterno, trabajadores.apellidoMaterno, "+
					"trabajadores.nombre, trabajadores.idHuerto, campo.descripcion, "+ 
					"sociedad.idSociedad, sociedad.sociedad,  sociedad.denominacionSociedad "+
					"FROM trabajadores "+
					"inner join sw_r_grupoTrabajador on trabajadores.codigo=sw_r_grupoTrabajador.idTrabajador "+
					"inner join campo on campo.campo=trabajadores.idHuerto "+
					"inner join sociedad on campo.sociedad=sociedad.sociedad";
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

	public static Tbjdr getTrabajadorByRut(String rut) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Tbjdr getTrabajadorByCodigo(String codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Tbjdr getTrabajadorById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public static boolean deleteTrabajadorByCodigo(String codigo) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean updateTrabajador(Tbjdr trabajadores) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean CreateTrabajador(Tbjdr trabajadores) {
		// TODO Auto-generated method stub
		return false;
	}

	public static ArrayList<Tbjdr> getTrabajadoresByIdGrupo(int idGrupo) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<Tbjdr> Lista = new ArrayList<Tbjdr>();
		try {
			sql = "SELECT sw_r_grupoTrabajador.*, trabajadores.id, trabajadores.codigo, "+
					"trabajadores.apellidoPaterno, trabajadores.apellidoMaterno, "+
					"trabajadores.nombre, trabajadores.idHuerto, campo.descripcion, "+ 
					"sociedad.idSociedad, sociedad.sociedad,  sociedad.denominacionSociedad "+
					"FROM trabajadores "+
					"inner join sw_r_grupoTrabajador on trabajadores.codigo=sw_r_grupoTrabajador.idTrabajador "+
					"inner join campo on campo.campo=trabajadores.idHuerto "+
					"inner join sociedad on campo.sociedad=sociedad.sociedad "+
					"where sw_r_grupoTrabajador.idGrupo="+idGrupo;
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

package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.CUARTEL_PF;
import lib.classSA.Consumo_Combustible;
import lib.db.ConnectionDB;

public class ConsumoCombustible {
	
	public static ArrayList<Consumo_Combustible> GET_ConsumoCombustible()throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<Consumo_Combustible> lista = new ArrayList<Consumo_Combustible>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select * from consumo_combustible";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				Consumo_Combustible ob = new Consumo_Combustible();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setCampo(rs.getString("campo"));
				ob.setTipo(rs.getString("tipo"));
				ob.setVehiculo(rs.getInt("vehiculo"));
				ob.setFecha(rs.getString("fecha"));
				ob.setLitro(rs.getFloat("litro"));
				ob.setOperador(rs.getString("operador"));
				ob.setHorometro(rs.getFloat("horometro"));
				ob.setImplemento(rs.getString("implemento"));
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

	public static boolean ADD_ConsumoCombustible(ArrayList<Consumo_Combustible> CC) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			for(Consumo_Combustible c: CC){
				sql = "INSERT INTO consumo_combustible(campo, tipo, vehiculo, fecha, litro, operador, horometro, implemento, material_document)";
				sql	+= " VALUES(?,?,?,?,?,?,?,?,?)";
				ps = db.conn.prepareStatement(sql);
				ps.setString(1, c.getCampo());
				ps.setString(2, c.getTipo());
				ps.setInt(3, c.getVehiculo());
				ps.setString(4, c.getFecha());
				ps.setFloat(5, c.getLitro());
				ps.setString(6, c.getOperador());
				ps.setFloat(7, c.getHorometro());
				ps.setString(8, c.getImplemento());
				ps.setString(9, c.getMaterial_document());
				ps.execute();
				System.out.println(ps);
			}
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


}

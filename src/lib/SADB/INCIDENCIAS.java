package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import lib.classSA.ESPECIE;
import lib.classSA.incidencia;
import lib.db.ConnectionDB;
import lib.sesionSA.SESION;

public class INCIDENCIAS {
	
	//------------------INCIDENCIAS-------------------
		
		public static ArrayList<incidencia> GET_INCIDENCIA(HttpSession httpSession) throws Exception{
			PreparedStatement ps = null;
			String sql = "";
			ArrayList<incidencia> lista = new ArrayList<incidencia>();
			ConnectionDB db = new ConnectionDB();
			try{
				sql = "SELECT * FROM incidencia WHERE estado = 1";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				while(rs.next()){
					incidencia ob = new incidencia();
					ob.setCodigo(rs.getInt("codigo"));
					ob.setFecha_ingreso(rs.getString("fecha_ingreso"));
					ob.setTipo_incidencia(rs.getString("tipo_incidencia"));
					ob.setGeoreferencia(rs.getString("georeferencia"));
					ob.setUsuario_ingreso(rs.getInt("usuario_ingreso"));
//					ob.setEspecie(rs.getInt("especie"));
//					ob.setVariedad(rs.getInt("variedad"));
					ob.setCuartel(rs.getInt("cuartel"));
					ob.setObservacion(rs.getString("observacion"));
					ob.setUsuario_cierre(rs.getInt("usuario_cierre"));
					ob.setAccion(rs.getString("accion"));
					ob.setId_accion(rs.getInt("id_accion"));
					ob.setObservacion_cierre(rs.getString("observacion_cierre"));
					ob.setFecha_cierre(rs.getString("fecha_cierre"));
					ob.setEstado(rs.getInt("estado"));
					lista.add(ob);
				}
				rs.close();
				ps.close();
				db.conn.close();
			}catch(SQLException e){
				System.out.println("Error:" + e.getMessage());
			}catch (Exception e) {
				System.out.println("Error:" + e.getMessage());
			}finally{
				db.close();
			}
			SESION mc= new SESION(httpSession);
			for(incidencia inci: lista){
				mc.addIncidencia(inci, inci.codigo);
			}
			mc.save();
			return lista;
		}
		
		
//		INSERT
		public static boolean insertINCIDENCIA (incidencia i)throws Exception{
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			try {
				sql ="INSERT INTO incidencia (fecha_ingreso, tipo_incidencia, georeferencia, ";
				sql += "usuario_ingreso, cuartel, observacion, estado,foto)";
				sql += " VALUES(?,?,?,?,?,?,1,?)";
				ps = db.conn.prepareStatement(sql);
				ps.setString(1, i.getFecha_ingreso());
				ps.setString(2, i.getTipo_incidencia());
				ps.setString(3, i.getGeoreferencia());
				ps.setInt(4, i.getUsuario_ingreso());
				ps.setInt(5, i.getCuartel());
				ps.setString(6, i.getObservacion());
				ps.setString(7, i.getImg());
				ps.execute();
				return true;
			} catch (SQLException e) {
				System.out.println("Error: insertN" + e.getMessage());
			} catch (Exception e){
				System.out.println("Error: insertN" + e.getMessage());
			} finally {
				ps.close();
				db.close();
			}
			return false;
		}
		
//		INSERT
		public static boolean updateINCIDENCIA (incidencia i)throws Exception{
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			try {
				sql ="UPDATE incidencia SET usuario_cierre='"+i.getUsuario_cierre()+"', accion='"+i.getAccion()
					+"', observacion_cierre='"+i.getObservacion_cierre()+"', fecha_cierre='"+i.getFecha_cierre()
					+"', estado='"+i.getEstado()+"' where id_accion='"+i.getId_accion()+"'";
				ps = db.conn.prepareStatement(sql);
				ps.execute();
				return true;
			} catch (SQLException e) {
				System.out.println("Error: insertN" + e.getMessage());
			} catch (Exception e){
				System.out.println("Error: insertN" + e.getMessage());
			} finally {
				ps.close();
				db.close();
			}
			return false;
		}
		


	}
	//------------------------FIN INCIDENCIAS--------------------

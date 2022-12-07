package lib.SADB;

import java.security.spec.PSSParameterSpec;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import lib.classSA.CUARTEL_PF;
import lib.classSA.MATERIAL;
import lib.classSA.MATERIAL_PF;
import lib.classSA.PROGRA_FITOSANITARIO;
import lib.db.ConnectionDB;

public class material {
	
	
//	------------------MATERIAL----------------------------
		public static ArrayList<MATERIAL> GETMA()throws Exception{
			PreparedStatement ps = null;
			String sql = "";
			ArrayList<MATERIAL> lista = new ArrayList<MATERIAL>();
			ConnectionDB db = new ConnectionDB();
			try {
				sql = "SELECT * FROM material";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				while(rs.next()){
					MATERIAL ob = new MATERIAL();
					ob.setCodigo(rs.getInt("codigo"));
					ob.setDescripcion(rs.getString("descripcion"));
					ob.setTipo(rs.getString("tipo"));
					ob.setNitrogeno(rs.getInt("nitrogeno"));
					ob.setFosforo(rs.getInt("fosforo"));
					ob.setPotasio(rs.getInt("potasio"));
					ob.setAzufre(rs.getInt("azufre"));
					ob.setCalcio(rs.getInt("calcio"));
					ob.setZinc(rs.getInt("zinc"));
					ob.setFierro(rs.getInt("fierro"));
					ob.setCobre(rs.getInt("cobre"));
					ob.setManganeso(rs.getInt("manganeso"));
					ob.setOtros(rs.getInt("otros"));
					ob.setMateria_organica(rs.getString("materia_organica"));
					ob.setRecomendacion_especie("recomendacion_especie");
					ob.setCarencia(rs.getInt("carencia"));
					ob.setReingreso(rs.getInt("reingreso"));
					ob.setIngrediente_activo(rs.getString("ingrediente_activo"));
					ob.familia = rs.getInt("familia");
					ob.subfamilia = rs.getInt("subfamilia");
					ob.UM = rs.getString("UM");
					lista.add(ob);
					}	
				rs.close();
				ps.close();
				db.conn.close();
			} catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
			} catch (Exception e) {
				System.out.println("Error:" + e.getMessage());
			} finally {
				db.close();
			}
			return lista;
		}
	
//	------------------FIN MATERIAL------------------------


		
//		------------------MATERIAL----------------------------
			public static MATERIAL GETMATDETALLE(int codigo)throws Exception{
				PreparedStatement ps = null;
				String sql = "";
				MATERIAL ob = new MATERIAL();
				ConnectionDB db = new ConnectionDB();
				try {
					sql = "SELECT * FROM material where codigo = " + codigo;
					ps = db.conn.prepareStatement(sql);
					ResultSet rs = ps.executeQuery(sql);
					while(rs.next()){
						
						ob.setCodigo(rs.getInt("codigo"));
						ob.setDescripcion(rs.getString("descripcion"));
						ob.setTipo(rs.getString("tipo"));
						ob.setNitrogeno(rs.getInt("nitrogeno"));
						ob.setFosforo(rs.getInt("fosforo"));
						ob.setPotasio(rs.getInt("potasio"));
						ob.setAzufre(rs.getInt("azufre"));
						ob.setCalcio(rs.getInt("calcio"));
						ob.setZinc(rs.getInt("zinc"));
						ob.setFierro(rs.getInt("fierro"));
						ob.setCobre(rs.getInt("cobre"));
						ob.setManganeso(rs.getInt("manganeso"));
						ob.setOtros(rs.getInt("otros"));
						ob.setMateria_organica(rs.getString("materia_organica"));
						ob.setRecomendacion_especie("recomendacion_especie");
						ob.setCarencia(rs.getInt("carencia"));
						ob.setReingreso(rs.getInt("reingreso"));
						ob.setIngrediente_activo(rs.getString("ingrediente_activo"));
						ob.familia = rs.getInt("familia");
						ob.subfamilia = rs.getInt("subfamilia");
						ob.UM = rs.getString("UM");
					}				
				} catch (SQLException e) {
					System.out.println("Error:" + e.getMessage());
				} catch (Exception e) {
					System.out.println("Error:" + e.getMessage());
				} finally {
					ps.close();
					db.conn.close();
				}
				return ob;
			}
		
//		------------------FIN MATERIAL------------------------
		 
	
	
//	------------------MATERIAL_PF--------------------------
//	SELECT
	public static ArrayList<MATERIAL_PF> getMPF (int codigo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<MATERIAL_PF> lista = new ArrayList<MATERIAL_PF>();
		DecimalFormat format = new DecimalFormat("#.00");
		ConnectionDB db =  new ConnectionDB();
		try {
			sql = 	"SELECT mp.*, ca.control_aplicacion as controlDesc FROM material_pf mp ";
			sql += 	"left join control_aplicacion ca on(mp.control = ca.codigo) where codigo_pf= " +codigo;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				MATERIAL_PF ob = new MATERIAL_PF();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setCodigo_pf(rs.getInt("codigo_pf"));
				ob.setCodigo_material(rs.getInt("codigo_material"));
				ob.setCantidad(rs.getFloat("cantidad"));
				ob.setDosis_100(rs.getFloat("dosis_100"));
				ob.setDosis_has(rs.getFloat("dosis_has"));
				ob.setNitrogeno(rs.getFloat("nitrogeno"));
				ob.setFosforo(rs.getFloat("fosforo"));
				ob.setPotasio(rs.getFloat("potasio"));
				ob.setAzufre(rs.getFloat("azufre"));
				ob.setCalcio(rs.getFloat("calcio"));
				ob.setZinc(rs.getFloat("zinc"));
				ob.setFierro(rs.getFloat("fierro"));
				ob.setCobre(rs.getFloat("cobre"));
				ob.setManganeso(rs.getFloat("manganeso"));
				ob.setOtros(rs.getFloat("otros"));
				ob.setReserva(rs.getInt("reserva"));
				ob.setAlmacen(rs.getString("almacen"));
				ob.setDevolucion(rs.getFloat("devolucion"));
				ob.setDiferencia(rs.getFloat("diferencia"));
				ob.setCantidad_real(rs.getFloat("cantidad_real"));
				ob.setCantidad_devolucion(rs.getDouble("devolucion_real"));
				ob.detalle_material = GETMATDETALLE(ob.codigo_material);
				ob.setDosis_bombada(rs.getDouble("dosis_bombada"));
				ob.setControsDesc(rs.getString("controlDesc"));
				lista.add(ob);
			}
			ps.close();
			rs.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			db.close();
		}
		return lista;
	}
//	INSERT 
		public static boolean insertMPF(MATERIAL_PF m) throws Exception{
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			try {
				sql = "INSERT INTO material_pf (codigo_pf, codigo_material, cantidad, dosis_100, dosis_has, nitrogeno, "
						+ "fosforo, potasio, azufre, calcio, zinc, fierro, cobre, manganeso, otros,almacen, control) "
						+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				ps = db.conn.prepareStatement(sql);
				ps.setInt(1, m.getCodigo_pf());
				ps.setInt(2, m.getCodigo_material());
				ps.setDouble(3, m.getCantidad());  
				ps.setDouble(4, m.getDosis_100());
				ps.setDouble(5, m.getDosis_has());
				ps.setDouble(6, m.getNitrogeno());
				ps.setDouble(7, m.getFosforo());
				ps.setDouble(8, m.getPotasio());
				ps.setDouble(9, m.getAzufre());
				ps.setDouble(10, m.getCalcio());
				ps.setDouble(11, m.getZinc());
				ps.setDouble(12, m.getFierro());
				ps.setDouble(13, m.getCobre());
				ps.setDouble(14, m.getManganeso());
				ps.setDouble(15, m.getOtros());
				ps.setString(16, m.getAlmacen());
				ps.setInt(17, m.getControl());
				ps.execute();
				return true;				
			} catch(SQLException e){
				System.out.println("Erro:" + e.getMessage());
			}catch (Exception e) {
				System.out.println("Error:" + e.getMessage());
			}finally {
				ps.close();
				db.conn.close();
			}			
			return false;
		}
//CONFIRMACIÓN MATERIALES PF
		public static boolean confirmMPF(MATERIAL_PF m) throws Exception{
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			try {
				sql =  "UPDATE material_pf SET cantidad_real = "+m.cantidad_real+" , devolucion = "+m.devolucion+" , diferencia = "+ m.diferencia + ", devolucion_real = 0, dosis_has_real = "+m.getDosis_has();
				sql += " WHERE codigo = "+m.codigo;
				System.out.println(sql);
				ps = db.conn.prepareStatement(sql);
				ps.execute();
				return true;				
			} catch(SQLException e){
				System.out.println("Erro:" + e.getMessage());
			}catch (Exception e) {
				System.out.println("Error:" + e.getMessage());
			}finally {
				ps.close();
				db.conn.close();
			}			
			return false;
		}
		
		public static boolean confirmMPFdevolucion(MATERIAL_PF m) throws Exception{
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			try {
				sql =  "UPDATE material_pf SET  devolucion_real = (devolucion_real + "+m.devolucion+") , diferencia = (diferencia + "+m.nueva_diferencia+") "
						+ " , devolucion = (devolucion - "+m.nueva_diferencia+") ";
				sql += " WHERE codigo = "+m.codigo;
				System.out.println(sql);
				ps = db.conn.prepareStatement(sql);
				ps.execute();
				return true;				
			} catch(SQLException e){
				System.out.println("Erro:" + e.getMessage());
			}catch (Exception e) {
				System.out.println("Error:" + e.getMessage());
			}finally {
				ps.close();
				db.conn.close();
			}			
			return false;
		}

//		DELETE 
		public static boolean deleteMPF (int id)throws Exception{
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			try {
				sql = "DELETE FROM material_pf where codigo_pf='"+id+"'"; 
				System.out.println(sql);
				ps = db.conn.prepareStatement(sql);
				ps.execute();
				return true;
			} catch (SQLException e){
				System.out.println("Error:" + e.getMessage());		
			} catch (Exception e) {
				System.out.println("Error:" + e.getMessage());
			}finally {
				ps.close();
				db.conn.close();
			}
			return false;
		}
		
		
		public static boolean reserva_material(ArrayList<PROGRA_FITOSANITARIO> programa) throws Exception{
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			try {
				for(PROGRA_FITOSANITARIO pf: programa){
					if(pf.getReserva() !=  null){
						sql =  "UPDATE programa_fitosanitario SET nreserva = "+pf.getReserva();
						sql += " WHERE codigo = "+pf.getCodigo();
						ps = db.conn.prepareStatement(sql);
						PROGRAMA_FITOSANITARIO.reprogramaPF(pf.getCodigo(),pf.getFecha_estimada());
						ps.execute();
					} else {
						sql =  "UPDATE programa_fitosanitario SET solped = '"+pf.getSolped()+"'";
						sql += " WHERE codigo = "+pf.getCodigo();
						ps = db.conn.prepareStatement(sql);
						System.out.println(sql);
						ps.execute();
					}
				}
				return true;				
			} catch(SQLException e){
				System.out.println("Erro:" + e.getMessage());
			}catch (Exception e) {
				System.out.println("Error:" + e.getMessage());
			}finally {
				ps.close();
				db.conn.close();
			}			
			return false;
		}	
		
		
//----------------------------FIN MATERIAL_PF-------------------------------


}

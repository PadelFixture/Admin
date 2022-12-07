package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import lib.classSA.CONFIRMACION_APLICACION;
import lib.classSA.CUARTEL_PF;
import lib.classSA.MAQUINARIA_PF;
import lib.classSA.MATERIAL_PF;
import lib.classSA.ORDEN_APLICACION;
import lib.db.ConnectionDB;

public class ORDEN_APLICA {
	
//----------------------ORDEN_APLICACION---------------------------
//	SELECT
	public static ArrayList<ORDEN_APLICACION> GETOA() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<ORDEN_APLICACION> lista = new ArrayList<ORDEN_APLICACION>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT * FROM orden_aplicacion ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				ORDEN_APLICACION ob = new  ORDEN_APLICACION();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setCodigo_pf(rs.getInt("codigo_pf"));
				ob.setAplicador(rs.getString("aplicador"));
				ob.setFecha_programa(rs.getString("fecha_programa"));
				ob.setEstado_fenologico(rs.getString("estado_fenologico"));
				ob.setFecha_estimada_cosecha(rs.getString("fecha_estimada_cosecha"));
				ob.setMercado(rs.getString("mercado"));
				ob.setCodigo_fa(rs.getInt("codigo_fa"));
				ob.setFecha_inicio(rs.getString("fecha_inicio"));
				ob.setJefe_aplicacion(rs.getString("jefe_aplicacion"));
				ob.setDosis_bombada(rs.getInt("dosis_bombada"));
				ob.setCambio_tractor(rs.getString("cambio_tractor"));
				ob.setPresion_bomba(rs.getInt("presion_bomba"));
				ob.setMarcha_tractor(rs.getString("marcha_tractor"));
				ob.setDias_cosecha(rs.getInt("dias_cosecha"));
				ob.setFecha_viable_cosecha(rs.getString("fecha_viable_cosecha"));
				//ob.setMaquinaria(rs.getInt("maquinaria"));
				//ob.setImplemento(rs.getInt("implemento"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
////	INSERT
	public static int insertOA (ORDEN_APLICACION o) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			
			
			
			sql = "INSERT INTO orden_aplicacion (codigo_pf, aplicador, fecha_programa,estado_fenologico,fecha_estimada_cosecha, mercado, "
					+ "codigo_fa, fecha_inicio, jefe_aplicacion, dosis_bombada,cambio_tractor,presion_bomba,marcha_tractor,dias_cosecha, "
					+ "fecha_viable_cosecha,maquinaria,implemento,id, um, capacidad_maquina, dosificador)";
			sql += "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0,0,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			
			String sql3 = "";
			sql3 = "SELECT MAX(oa.id) as id from orden_aplicacion  oa"
					+ " left join programa_fitosanitario pf on pf.codigo = oa.codigo_pf"
					+ " where pf.campo = '"+o.getCampo() +"' and pf.temporada = '" +o.getTemporada()+ "' ";
			System.out.println(sql3);
			ResultSet idNew2 = ps.executeQuery(sql3);
			int idPF = 0;
			while (idNew2.next()) { 
				idPF = idNew2.getInt("id"); 
			}
			idPF++;
			
			ps.setInt(1, o.getCodigo_pf());
			ps.setString(2, o.getAplicador());
			ps.setString(3, o.getFecha_programa());
			ps.setString(4, o.getEstado_fenologico());
			ps.setString(5, "");
			ps.setString(6, o.getMercado());
			ps.setInt(7, o.getCodigo_fa());
			ps.setString(8, o.getFecha_inicio());
			ps.setString(9, o.getJefe_aplicacion());
			ps.setDouble(10, o.getDosis_bombada());
			ps.setString(11, o.getCambio_tractor());
			ps.setInt(12, o.getPresion_bomba());
			ps.setString(13, o.getMarcha_tractor());
			ps.setInt(14, o.getDias_cosecha());
			ps.setString(15, o.getFecha_viable_cosecha());
			ps.setInt(16, idPF);
			ps.setString(17, o.getUm());
			ps.setDouble(18, o.getCapacidad_maquina());
			ps.setInt(19, o.getDosificador());
			ps.execute();
			cambia_estado_Notificacion(o.getCodigo_pf());
			cambia_estado_PF(o.getCodigo_pf(),2);
			
//			String sql2 = "";
//			sql2 = "SELECT MAX(codigo_pf) as codigo from orden_aplicacion";
//			ResultSet idNew = ps.executeQuery(sql2);
//			int codPF = 0;
//			while (idNew.next()) { 
//				codPF = idNew.getInt("codigo"); 
//			}
			guardar_dosis_bombada(o.getCodigo_pf(),o.getCapacidad_maquina());
			for(MAQUINARIA_PF mpf: o.getMaquinaria_pf()){
				mpf.setCodigo_pf(o.getCodigo_pf());
				insertMaquinara_PF(mpf);
			}			
			return idPF;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return 0;
	}
	
	public static boolean guardar_dosis_bombada (int codigo_pf, double capacidad_maquina) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {			
			sql = "update material_pf set dosis_bombada = (("+capacidad_maquina+"/ 100) * dosis_100) / 1000 where codigo_pf = "+codigo_pf;
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	
	public static boolean updateOA (ORDEN_APLICACION o) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {			
			sql = "update orden_aplicacion set aplicador = '"+o.getAplicador()+"', "
					+ " mercado = '"+o.getMercado()+"', "
					+ "codigo_fa = '"+o.getCodigo_fa()+"', fecha_inicio = '"+o.getFecha_inicio()+"', "
					+ "jefe_aplicacion = '"+o.getJefe_aplicacion()+"', dosis_bombada = '"+o.getDosis_bombada()+"',"
					+ "cambio_tractor = '"+o.getCambio_tractor()+"',presion_bomba = '"+o.getPresion_bomba()+"',"
					+ "marcha_tractor = '"+o.getMarcha_tractor()+"',dias_cosecha ='"+o.getDias_cosecha()+"'"
					+ ",fecha_viable_cosecha = '"+o.getFecha_viable_cosecha()+"' , um = '"+o.getUm()+"', capacidad_maquina = '"+o.getCapacidad_maquina()+"', "
					+ " dosificador = "+o.getDosificador()+" where codigo = "+o.getCodigo();
			System.out.println(sql);
			guardar_dosis_bombada(o.getCodigo_pf(),o.getCapacidad_maquina());
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	
	public static boolean insertMaquinara_PF(MAQUINARIA_PF mpf) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO maquinaria_pf (codigo_pf, maquinaria, implemento,cambio_tractor,marcha_tractor,velocidad_tractor,presion_bomba,responsable) "
				+ " values ("+mpf.getCodigo_pf()+",'"+mpf.getMaquinaria()+"','"+mpf.getImplemento()+"','"+mpf.getCambio()+"','"+mpf.getMarcha()+"','"
					+mpf.getVelocidad()+"','"+mpf.getPresion()+"','"+mpf.getResponsable()+"' )";
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
			db.conn.close();
		}
		return false;
		
	}
	
	
	//Cambiar estado a programa fitosanitario a orden creada
	
	public static boolean cambia_estado_PF (int pf, int estado) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE programa_fitosanitario SET "
				+ " estado_pf = "+estado+" where codigo = '"+pf+"'";
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
			db.conn.close();
		}
		return false;
	}
	
	public static boolean cambia_estado_Notificacion (int pf) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE notificaciones SET "
				+ " estado = 2 where codigo_tarea = '"+pf+"'";
			ps = db.conn.prepareStatement(sql);
			//System.out.println(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	
////UPDATE
//	public static boolean updateOA(ORDEN_APLICACION o) throws Exception{
//		PreparedStatement ps = null;
//		String sql = "";
//		ConnectionDB db = new ConnectionDB();
//		try {
//			sql = "UPDATE orden_aplicacion set "
//				+ "codigo_pf='" +o.getCodigo_pf()+ "', codigo_fa='" +o.getCodigo_fa()
//				+ "', presion_bomba='" +o.getPresion_bomba()+ "', codigo_mq='" +o.getCodigo_mq()
//				+ "', codigo_implemento='" +o.getCodigo_implemento()+ "', aplicador='" +o.getAplicador()
//				+ "', mojamiento='" +o.getMojamiento()+ "', estado='" +o.getEstado()+ "', where codigo='" +o.getCodigo()+ "'";
//			ps = db.conn.prepareStatement(sql);
//			ps.execute();
//			return true;
//		} catch (SQLException e) {
//			System.out.println("Error:" + e.getMessage());
//		} catch (Exception e){
//			System.out.println("Error:" + e.getMessage());
//		} finally {
//			ps.close();
//			db.conn.close();
//		}
//		return false;
//	}
////----------------------------FIN ORDEN_APLICACION-----------------
	
	
	
	
//----------------------CONFIRMACION_APLICACION-------------------
//	SELECT
	public static ArrayList<CONFIRMACION_APLICACION> GETCAP() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CONFIRMACION_APLICACION> lista = new ArrayList<CONFIRMACION_APLICACION>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT * FROM confirmacion_aplicacion";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				CONFIRMACION_APLICACION ob = new CONFIRMACION_APLICACION();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setCodigo_orden(rs.getInt("codigo_orden"));
				ob.setFecha_termino(rs.getString("fecha_termino"));
				ob.setMojamiento(rs.getInt("mojamiento"));
				ob.setTractor_real(rs.getString("tractor_real"));
				ob.setBomba_real(rs.getInt("bomba_real"));
				ob.setBoquilla_real(rs.getString("boquilla_real"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
//	INSERT
	public static boolean insertCAP (CONFIRMACION_APLICACION c) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO confirmacion_apicaion(codigo, codigo_orden, fecha_termino, mojamiento, "
					+ "tractor_real, bomba_real, boquilla_real)"
				+ " values (?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, c.getCodigo());
			ps.setInt(2, c.getCodigo_orden());
			ps.setString(3, c.getFecha_termino());
			ps.setInt(4, c.getMojamiento());
			ps.setString(5, c.getTractor_real());
			ps.setInt(6, c.getBomba_real());
			ps.setString(7, c.getBoquilla_real());
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
//	UPDATE
	public static boolean updateCAP (CONFIRMACION_APLICACION c) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE confirmacion_apicaion SET "
				+ "codigo_orden='"+c.getCodigo_orden()+ "', fecha_termino='" +c.getFecha_termino()
				+ "', mojamiento='"+c.getMojamiento()+ "', tractor_real='"+c.getTractor_real()
				+ "', bomba_real='"+c.getBomba_real()+ "', '"+c.getBoquilla_real()
				+ "' where codigo='"+c.getCodigo()+"')";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	
//---------------------FIN CONFIRMACION_APLICACION----------------
	
	
	public static boolean ADDOrdenAplicacion (ORDEN_APLICACION fo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO orden_aplicacion (codigo_pf, aplicador, fecha_programa, estado_fenologico,"
				+ " fecha_estimada_cosecha, mercado, codigo_fa, fecha_inicio, jefe_aplicacion, dosis_bombada,"
				+ " combinacion_boquilla, presion_bomba, marcha_tractor, dias_cosecha, fecha_viable_cosecha, maquinaria, "
				+ "implemento) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				ps = db.conn.prepareStatement(sql);
				ps.setInt(1, fo.getCodigo_pf());
				ps.setString(2, fo.getAplicador());
				ps.setString(3, fo.getFecha_programa());
				ps.setString(4, fo.getEstado_fenologico());
				ps.setString(5, fo.getFecha_estimada_cosecha());
				ps.setString(6, fo.getMercado());
				ps.setInt(7, fo.getCodigo_fa());
				ps.setString(8, fo.getFecha_inicio());
				ps.setString(9, fo.getJefe_aplicacion());
				ps.setDouble(10, fo.getDosis_bombada());
				ps.setString(11, fo.getCambio_tractor());
				ps.setInt(12, fo.getPresion_bomba());
				ps.setString(13, fo.getMarcha_tractor());
				ps.setInt(14, fo.getDias_cosecha());
				ps.setString(15, fo.getFecha_viable_cosecha());
				ps.execute();
				return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	
	
	
//	public static boolean updateOrdenAplicacion(ORDEN_APLICACION f) throws Exception{
//		PreparedStatement ps = null;
//		String sql = "";
//		ConnectionDB db = new ConnectionDB();
//		try {
//			sql = "UPDATE orden_aplicacion set "
//				+ "   aplicador='"+f.getAplicador()
//				+ "', fecha_programa='"+f.getFecha_programa()+"', estado_fenologico='"+f.getEstado_fenologico()
//				+ "', fecha_estimada_cosecha='"+f.getFecha_estimada_cosecha()+"', mercado='"+f.getMercado()
//				+ "', codigo_fa='"+f.getCodigo_fa()+"', fecha_inicio='"+f.getFecha_inicio()
//				+ "', jefe_aplicacion='"+f.getJefe_aplicacion()+"', dosis_bombada='"+f.getDosis_bombada()
//				+ "', combinacion_boquilla='"+f.getCombinacion_boquilla()+"', presion_bomba='"+f.getPresion_bomba()
//				+ "', marcha_tractor='"+f.getMarcha_tractor()+"', dias_cosecha='"+f.getDias_cosecha()
//				+ "', fecha_viable_cosecha='"+f.getFecha_viable_cosecha()+"', maquinaria='"+f.getMaquinaria()
//				+ "', implemento='"+f.getImplemento()+"', where codigo_pf= '"+f.getCodigo_pf()+"'";
//			ps = db.conn.prepareStatement(sql);
//			ps.execute();
//			return true;
//		} catch (SQLException e) {
//			System.out.println("Error:" + e.getMessage());
//		} catch (Exception e){
//			System.out.println("Error:" + e.getMessage());
//		} finally {
//			ps.close();
//			db.conn.close();
//		}
//		return false;
//	}
	
	public static boolean confirmarAplicacion (CONFIRMACION_APLICACION ca) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO confirmacion_aplicacion "
				+ "      (codigo_orden           , fecha_termino           , mojamiento           , fecha_inicio, hora_inicio, hora_termino, "
				+ "       velocidad_viento        ,orientacion_viento , diferencia, consumo ,temperatura , mojamiento_total,observacion   ) "
				+ "VALUES("+ca.getCodigo_orden()+",'"+ca.getFecha_termino()+"','"+ca.getMojamiento()+"','"+ca.getFecha_inicio()+"','"+ca.getHora_inicio()+"',"
						+ "'"+ca.getHora_termino()+"','"+ca.getVelocidad_viento()+"','"+ca.getOrientacion_viento()+"'," 
								+ "'"+ca.getDiferencia()+"','"+ca.getConsumo()+"','"+ca.getTemperatura()+"','"+ca.getMojamiento_total()+"', '"+ca.getObservacion()+"')";
				System.out.println(sql);
				ps = db.conn.prepareStatement(sql);	
				ps.execute();
				
				String sql2 = "";
				sql2 = "SELECT MAX(codigo) as codigo from confirmacion_aplicacion";
				ResultSet idNew = ps.executeQuery(sql2);
				int cod = 0;
				while (idNew.next()) { 
					cod = idNew.getInt("codigo"); 
				}
				
				for(CUARTEL_PF cpf: ca.getLista_cuarteles()){
					cuartel.confirmCPF(cpf);
				}
				for(MATERIAL_PF mpf: ca.getLista_materiales()){
					material.confirmMPF(mpf);
				}
				for(MAQUINARIA_PF mapf: ca.getLista_maquinaria()){
					insertMaquinara_PF(mapf);
				}
				confirmacion_sap(ca.getCodigo_orden(),ca.getConsumo(), ca.getDiferencia(), null, ca.getPeriodo());
				int estado = 4;
				if(ca.getCantidad_devolucion() > 0){
					estado = 3;
				}
				cambia_estado_PF(GET_COD_PF(ca.getCodigo_orden()),estado);
				return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	
	
	public static boolean confirmarDecolucion (CONFIRMACION_APLICACION ca) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE confirmacion_aplicacion "
					+ "   SET  devolucion = '"+ca.getDevolucion()+"' where codigo_orden = "+ca.getCodigo_orden();
				System.out.println(sql);
				ps = db.conn.prepareStatement(sql);	
				ps.execute();
				float c = 0;
				for(MATERIAL_PF mpf: ca.getLista_materiales()){
					material.confirmMPFdevolucion(mpf);
					c += mpf.getDiferencia();
				}	
				int estado = 3;
				System.out.println("Suma diferencia" + c);
				if(c == 0){
					
					estado = ca.getTipo();
				}
				/*String sql2 = "";
				sql2 = "SELECT MAX(codigo) as codigo from confirmacion_aplicacion";
				ResultSet idNew = ps.executeQuery(sql2);
				int cod = 0;
				while (idNew.next()) { 
					cod = idNew.getInt("codigo"); 
				}*/
				cambia_estado_PF(ca.getCodigo(),estado);
				confirmacion_sap(ca.getCodigo_orden(),null, ca.getDiferencia(), ca.getDevolucion(), ca.getPeriodo());
				return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	
	public static boolean confirmacion_sap (int codigo,String consumo,String sobreconsumo, String devolucion, String periodo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO confirmacion_documento_sap (codigo_confirmacion,consumo, sobreconsumo, devolucion, periodo) "
				+ "VALUES(?,?,?,?,?)";
				ps = db.conn.prepareStatement(sql);
				ps.setInt(1, codigo);
				ps.setString(2, consumo);
				ps.setString(3, sobreconsumo);
				ps.setString(4, devolucion);
				ps.setString(5, periodo);
				System.out.println(ps);
				ps.execute();
				return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	
	//GET PF POR ORDEN DE APLICACION
	public static int GET_COD_PF(int codigo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		
		ConnectionDB db = new ConnectionDB();
		int codigo_pf = 0;
		try {
			sql = "SELECT codigo_pf FROM orden_aplicacion where codigo = "+codigo;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				codigo_pf = rs.getInt("codigo_pf");
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return codigo_pf;
	}
	
	public static CONFIRMACION_APLICACION getConfirmacion(String codigo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		CONFIRMACION_APLICACION row = new CONFIRMACION_APLICACION();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT ca.fecha_inicio, ca.hora_inicio, ca.fecha_termino, ca.hora_termino, ca.orientacion_viento, ca.velocidad_viento, ca.temperatura, oa.codigo_pf,ca.mojamiento "
					+ "FROM confirmacion_aplicacion ca "					
					+ "LEFT JOIN orden_aplicacion oa on oa.codigo = ca.codigo_orden "
					+ "WHERE ca.codigo_orden ="+codigo;
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while (rs.next()){
				row.setCodigo(rs.getInt("codigo_pf"));
				row.setFecha_inicio(rs.getString("fecha_inicio"));
				row.setFecha_termino(rs.getString("fecha_termino"));
				row.setHora_termino(rs.getString("hora_termino"));
				row.setHora_inicio(rs.getString("hora_inicio"));
				row.setVelocidad_viento(rs.getString("velocidad_viento"));
				row.setOrientacion_viento(rs.getString("orientacion_viento"));
				row.setMojamiento(rs.getInt("mojamiento"));
				row.setTemperatura(rs.getString("temperatura"));
				//row.lista_materiales = material.getMPF(rs.getInt("codigo_pf"));
				//row.lista_cuarteles = cuartel.getCPF(rs.getInt("codigo_pf"));
				//row.setLista_maquinaria(LISTA_APLICACIONES.getMaquinaria(rs.getInt("codigo_pf")));
			}
			rs.close();
			ps.close();
			db.conn.close();
			return row;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return row;
	}
	public static String GET_DOFICADORES_CAMPO(String campo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		JSONArray array = new JSONArray();
		
		try{
			sql +=	"SELECT *FROM parametros_campo WHERE campo = '"+campo+"' AND activo = 1 AND tabla = 'Dosificador'";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				JSONObject ob = new JSONObject();
				ob.put("codigo", rs.getInt("codigo"));
				ob.put("id", rs.getInt("id"));
				ob.put("campo", rs.getString("campo"));
				ob.put("descripcion", rs.getString("descripcion"));
				ob.put("activo", rs.getInt("activo"));
				ob.put("tabla", rs.getString("tabla"));
				array.put(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return array.toString();
	}
	
	
	
}


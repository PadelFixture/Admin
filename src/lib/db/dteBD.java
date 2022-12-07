package lib.db; 

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

//import lib.jsonMap.purchaseIncidencia;
//import lib.jsonMap.purchaseMap;
import lib.struc.IN_INCIDENCIA_ASIGNADA;
import lib.struc.M_FORMA_APLICACION;
import lib.struc.M_MERCADO;
import lib.struc.TIPO_PAGO; 
import lib.struc.bodegaConection;
import lib.struc.comuna;
import lib.struc.departamentos;
import lib.struc.files;
import lib.struc.incidencia;
import lib.struc.labores;
import lib.struc.loginApp;
import lib.struc.mapConection;
import lib.struc.material;
//import lib.struc.notificacionPreseleccion;
import lib.struc.perfil_usuario;
import lib.struc.peticion;
import lib.struc.productConection;
import lib.struc.provincia;
//import lib.struc.region;
import lib.struc.trabajadores;
//import lib.jsonMap.innerTrabajadores;



public class dteBD {
	
	public static boolean setmapConection(mapConection map) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "INSERT INTO test (coordenada, valor1, color, especie, variedad, hectarias, cantidad, plantacion, status) VALUES (?,?,?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			
			ps.setString(1, map.getCoordenada());
			ps.setString(2, map.getValor1());
			ps.setString(3, map.getColor());
			ps.setString(4, map.getEspecie());
			ps.setString(5, map.getVariedad());
			ps.setFloat(6, map.getHectarias());
			ps.setInt(7, map.getCantidad());
			ps.setString(8, map.getPlantacion());
			ps.setString(9, map.getStatus());
			ps.execute();
			return true;
		}
		catch(SQLException e){
			System.out.println("Error: " +e.getMessage());
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
		finally{
			ps.close();
			db.conn.close();
		}
		return false;
	}
	public static ArrayList<mapConection> getMapData(HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<mapConection> mapas = new ArrayList<mapConection>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM test";
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				mapConection e = new mapConection();
				e.setIdtest(rs.getInt("idtest"));
				e.setValor1(rs.getString("valor1"));
				e.setCoordenada(rs.getString("coordenada"));
				e.setColor(rs.getString("color"));
				e.setEspecie(rs.getString("especie"));
				e.setHectarias(rs.getFloat("hectarias"));
				e.setVariedad(rs.getString("variedad"));
				e.setPlantacion(rs.getString("plantacion"));
				e.setCantidad(rs.getInt("cantidad"));
				e.setStatus(rs.getString("status"));
				mapas.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
			
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		sesionVar mc= new sesionVar(httpSession);
		for(mapConection campo: mapas){
			mc.addCampo(campo, campo.idtest);
		}
		mc.saveCampo();
		getM_forma_aplicacion(httpSession);
		return mapas;
	}
	public static ArrayList<M_FORMA_APLICACION> getM_forma_aplicacion(HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<M_FORMA_APLICACION> m_f_a = new ArrayList<M_FORMA_APLICACION>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM FORMA_APLICACION";
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				M_FORMA_APLICACION e = new M_FORMA_APLICACION();
				e.setCodigo(rs.getInt("codigo"));
				e.setDescripcion(rs.getString("descripcion"));
				m_f_a.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
			
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		sesionVar mc= new sesionVar(httpSession);
		mc.addMFormaAplicacion(m_f_a);
		mc.saveCampo();
		getM_Mercado(httpSession);
		return m_f_a;
	}
	public static ArrayList<M_MERCADO> getM_Mercado(HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<M_MERCADO> mercado = new ArrayList<M_MERCADO>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM MERCADO";
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				M_MERCADO e = new M_MERCADO();
				e.setCodigo(rs.getInt("codigo"));
				e.setDescripcion(rs.getString("descripcion"));
				mercado.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
			
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		sesionVar mc= new sesionVar(httpSession);
		mc.addMercado(mercado);
		mc.saveCampo();
		getTipo_Pago(httpSession);
		return mercado;
	}
	public static ArrayList<TIPO_PAGO> getTipo_Pago(HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<TIPO_PAGO> tipo_pago = new ArrayList<TIPO_PAGO>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM TIPO_PAGO";
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				TIPO_PAGO e = new TIPO_PAGO();
				e.setCodigo(rs.getInt("codigo"));
				e.setDescripcion(rs.getString("descripcion"));
				tipo_pago.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
			
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		sesionVar mc= new sesionVar(httpSession);
		mc.addTipoPago(tipo_pago);
		mc.saveCampo();
		return tipo_pago;
	}
	public static ArrayList<mapConection> setCoordenada(int id) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<mapConection> data = new ArrayList<mapConection>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM test WHERE idtest = "+id+"";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				mapConection e = new mapConection();
				e.setCoordenada(rs.getString("coordenada"));
				e.setColor(rs.getString("color"));
				e.setCantidad(rs.getInt("cantidad"));
				e.setEspecie(rs.getString("especie"));
				e.setHectarias(rs.getFloat("hectarias"));
				e.setPlantacion(rs.getString("plantacion"));
				e.setValor1(rs.getString("valor1"));
				e.setVariedad(rs.getString("variedad"));
				e.setIdtest(rs.getInt("idtest"));
				e.setStatus(rs.getString("status"));
				data.add(e);
			}
			rs.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			ps.close();
			db.conn.close();
		}
		return data;
	}
	public static ArrayList<mapConection> setInfo() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<mapConection> data = new ArrayList<mapConection>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM test";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				mapConection e = new mapConection();
				e.setIdtest(rs.getInt("idtest"));
				e.setValor1(rs.getString("valor1"));
				e.setCoordenada(rs.getString("coordenada"));
				e.setColor(rs.getString("color"));
				e.setEspecie(rs.getString("especie"));
				e.setVariedad(rs.getString("variedad"));
				e.setHectarias(rs.getFloat("hectarias"));
				e.setCantidad(rs.getInt("cantidad"));
				e.setPlantacion(rs.getString("plantacion"));
				e.setStatus(rs.getString("status"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
	public static boolean updMap(mapConection map) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "UPDATE test SET valor1 = ?, color = ?, especie = ?, variedad = ?, hectarias = ?, cantidad = ?, plantacion = ?, status = ? WHERE idtest = ?";
			ps = db.conn.prepareStatement(sql);
			
			ps.setString(1, map.getValor1());
			ps.setString(2, map.getColor());
			ps.setString(3, map.getEspecie());
			ps.setString(4, map.getVariedad());
			ps.setFloat(5, map.getHectarias());
			ps.setInt(6, map.getCantidad());
			ps.setString(7, map.getPlantacion());
			ps.setString(8, map.getStatus());
			ps.setInt(9, map.getIdtest());
			
			ps.execute();
			return true;
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			ps.close();
			db.conn.close();
		}
		return false;
	}
	public static ArrayList<bodegaConection> loadCellar() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<bodegaConection> data = new ArrayList<bodegaConection>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM bodega";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				bodegaConection e = new bodegaConection();
				e.setIdbodega(rs.getInt("idbodega"));
				e.setNombreBodega(rs.getString("nombreBodega"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
	public static ArrayList<productConection> loadProduct() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<productConection> data = new ArrayList<productConection>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM productos";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				productConection e = new productConection();
				e.setIdproductos(rs.getInt("idproductos"));
				e.setNomProductos(rs.getString("nomProductos"));
				e.setIdbodega(rs.getInt("idbodega"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
	public void saveRuta(String ruta, int idtest){
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "INSERT INTO files (ruta, idtest) VALUES ('"+ruta+"', "+idtest+")";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
	}
	public static ArrayList<files> loadRuta(int idInput) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<files> data = new ArrayList<files>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM files WHERE idtest = "+idInput+" ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				files e = new files();
				e.setIdfiles(rs.getInt("idfiles"));
				e.setRuta(rs.getString("ruta"));
				e.setIdtest(rs.getInt("idtest"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error id: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
	public static trabajadores consultaTrabajadores(int codigo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		//ArrayList<trabajadores> data = new ArrayList<trabajadores>();
		trabajadores e = new trabajadores();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM trabajadores where codigo="+codigo;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			if(rs.next()){
				
				e.setId(rs.getInt("id"));
				e.setCodigo(rs.getString("codigo"));
				e.setRut(rs.getString("rut"));
				e.setNombre(rs.getString("nombre"));
				e.setfNacimineto(rs.getString("fNacimiento"));
				e.setDireccion(rs.getString("direccion"));
				e.setTelefono(rs.getString("telefono"));
				
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error id: "+ex.getMessage());
		}finally{
			db.close();
		}
		return e;
	}
	public static ArrayList<trabajadores> loadPersonal() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<trabajadores> data = new ArrayList<trabajadores>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM trabajadores WHERE est_contrato = 'Activo'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				trabajadores e = new trabajadores();
				e.setCodigo(rs.getString("codigo"));
				e.setRut(rs.getString("rut"));
				e.setNombre(rs.getString("nombre"));
				e.setfNacimineto(rs.getString("fNacimiento"));
				e.setDireccion(rs.getString("direccion"));
				e.setTelefono(rs.getString("telefono"));
				e.setId(rs.getInt("id"));
				e.setId_perfil(rs.getInt("id_perfil"));
				e.setF_ingreso(rs.getString("f_ingreso"));
				e.setNacionalidad(rs.getString("nacionalidad"));
				e.setEstado_civil(rs.getString("estado_civil"));
				e.setEmail(rs.getString("email"));
				e.setIsapre(rs.getString("isapre"));
				e.setPrevision(rs.getString("prevision"));
				e.setC_normales(rs.getInt("c_normales"));
				e.setC_invalidas(rs.getInt("c_invalidas"));
				e.setT_asign_familiar(rs.getString("t_asign_familiar"));
				e.setF_termino(rs.getString("f_termino"));
				e.setEst_contrato(rs.getString("est_contrato"));
				e.setTipo_contrato(rs.getString("tipo_contrato"));
				e.setId_departamento(rs.getInt("id_departamento"));
				e.setEstablecimiento(rs.getString("establecimiento"));
				e.setHrs_semanal(rs.getInt("hrs_semanal"));
				e.setAjus_sueldo_base(rs.getString("ajus_sueldo_base"));
				e.setBene_semana_corrida(rs.getString("bene_semana_corrida"));
				e.setSeguro_cesantia(rs.getString("seguro_cesantia"));
				e.setIn_per_seg_ces(rs.getString("in_per_seg_ces"));
				e.setAefc_seg_accidentes(rs.getString("afec_seg_accidentes"));
				e.setTipo_pacto(rs.getString("tipo_pacto"));
				e.setMoneda_mon_ges(rs.getString("moneda_mon_ges"));
				e.setMonto_pactado(rs.getInt("monto_pactado"));
				e.setMonto_ges(rs.getInt("monto_ges"));
				e.setTipo_sueldo_base(rs.getString("tipo_sueldo_base"));
				e.setSueldo_mensual(rs.getInt("sueldo_mensual"));
				e.setAsign_zona_extrema(rs.getInt("asign_zona_extrema"));
				e.setGratificacion_legal(rs.getString("gratificacion_legal"));
				e.setNo_cuenta(rs.getString("no_cuenta"));
				e.setInstitucion_bco(rs.getString("institucion_bco"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
	
	

	/*public static ArrayList<notificacionPreseleccion> loadNotificacionPreseleccion() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<notificacionPreseleccion> data = new ArrayList<notificacionPreseleccion>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM peticion_trabajador WHERE estado_peticion = 1 ";
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				notificacionPreseleccion e = new notificacionPreseleccion();
				e.setId_peticion(rs.getInt("id_peticion"));
				e.setFaena(rs.getString("faena"));
				e.setCantidad(rs.getInt("cantidad"));
				e.setFecha_inicio(rs.getString("fecha_inicio"));
				e.setObservacion(rs.getString("observacion"));
				e.setUsuario(rs.getString("usuario"));
				e.setEstado_peticion(rs.getInt("estado_peticion"));
				
			
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}*/

//	public static ArrayList<notificacionPreseleccion> loadNotificacionPreseleccion() throws Exception{
//		PreparedStatement ps = null;
//		String sql = "";
//		ArrayList<notificacionPreseleccion> data = new ArrayList<notificacionPreseleccion>();
//		ConnectionDB db = new ConnectionDB();
//		try{
//			sql = "SELECT *FROM peticion_trabajador WHERE estado_peticion = 1 ";
//			
//			ps = db.conn.prepareStatement(sql);
//			ResultSet rs = ps.executeQuery(sql);
//			while(rs.next()){
//				notificacionPreseleccion e = new notificacionPreseleccion();
//				e.setId_peticion(rs.getInt("id_peticion"));
//				e.setFaena(rs.getString("faena"));
//				e.setCantidad(rs.getInt("cantidad"));
//				e.setFecha_inicio(rs.getString("fecha_inicio"));
//				e.setObservacion(rs.getString("observacion"));
//				e.setUsuario(rs.getString("usuario"));
//				e.setEstado_peticion(rs.getInt("estado_peticion"));
//				
//			
//				data.add(e);
//			}
//			rs.close();
//			ps.close();
//			db.conn.close();
//		}catch(Exception ex){
//			System.out.println("Error: "+ex.getMessage());
//		}finally{
//			db.close();
//		}
//		return data;
//	}

	public static ArrayList<perfil_usuario> loadCargo() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<perfil_usuario> data = new ArrayList<perfil_usuario>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM perfil_Usuario";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				perfil_usuario e = new perfil_usuario();
				e.setId_perfil(rs.getInt("id_perfil"));
				e.setDescripcion(rs.getString("descripcion"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
	public static ArrayList<departamentos> loadDepartamentos() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<departamentos> data = new ArrayList<departamentos>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM departamentos";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				departamentos e = new departamentos();
				e.setId(rs.getInt("id"));
				e.setDescripcion(rs.getString("descripcion"));
				e.setCod_costos(rs.getString("cod_costos"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
	public static boolean insertTrabajador(trabajadores trab) throws Exception{
		ConnectionDB db = new ConnectionDB();
		try{
			CallableStatement cs = db.conn.prepareCall("{CALL INSERT_TRABAJADOR (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			cs.setString(1, trab.getCodigo());
			cs.setString(2, trab.getRut());
			cs.setString(3, trab.getNombre());
			cs.setString(4, trab.getfNacimineto());
			cs.setString(5, trab.getDireccion());
			cs.setString(6, trab.getTelefono());
			cs.setInt(7, trab.getId_perfil());
			cs.setString(8, trab.getNacionalidad());
			cs.setString(9, trab.getEstado_civil());
			cs.setString(10, trab.getPrevision());
			cs.setString(11, trab.getF_ingreso());
			cs.setString(12, trab.getF_termino());
			cs.setString(13, trab.getEst_contrato());
			cs.setString(14, trab.getTipo_contrato());
			cs.setInt(15, trab.getId_departamento());
			cs.setString(16, trab.getEstablecimiento());
			cs.setInt(17, trab.getHrs_semanal());
			cs.setString(18, trab.getAjus_sueldo_base());
			cs.setString(19, trab.getBene_semana_corrida());
			cs.setString(20, trab.getIsapre());
			cs.setString(21, trab.getEmail());
			cs.setInt(22, trab.getC_normales());
			cs.setInt(23, trab.getC_invalidas());
			cs.setString(24, trab.getT_asign_familiar());
			cs.setString(25, trab.getSeguro_cesantia());
			cs.setString(26, trab.getIn_per_seg_ces());
			cs.setString(27, trab.getAefc_seg_accidentes());
			cs.setString(28, trab.getTipo_pacto());
			cs.setString(29, trab.getMoneda_mon_ges());
			cs.setInt(30, trab.getMonto_pactado());
			cs.setInt(31, trab.getMonto_ges());
			cs.setString(32, trab.getTipo_sueldo_base());
			cs.setInt(33, trab.getSueldo_mensual());
			cs.setInt(34, trab.getAsign_zona_extrema());
			cs.setString(35, trab.getGratificacion_legal());
			cs.setString(36, trab.getNo_cuenta());
			cs.setString(37, trab.getInstitucion_bco());
			cs.execute();
			return true;
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.conn.close();
		}
		return false;
	}
	
	
	// Peticion de Trabajador(es) 
	/*public static boolean insertPeticion(peticion trab) throws Exception{
		ConnectionDB db = new ConnectionDB();
		try{
			CallableStatement cs = db.conn.prepareCall("{CALL INSERT_PETICION (?,?,?,?)}");
			
			cs.setInt(1, trab.getC_trabajadores());
			cs.setString(2, trab.getObra_faena());
			cs.setString(3, trab.getF_inicio());
			cs.setString(4, trab.getObservacion());
			
			
			cs.execute();
			return true;
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.conn.close();
		}
		return false;
	}*/
	// Peticion de Trabajador(es) 
			public static boolean insertPeticion(peticion trab) throws Exception{
				
				PreparedStatement ps = null;
				String sql = "";
				ConnectionDB db = new ConnectionDB();
				try{
					sql = "INSERT INTO peticion_trabajador (faena, cantidad, fecha_inicio, observacion, usuario, estado_peticion) VALUES (?,?,?,?,?,1)";
					ps = db.conn.prepareStatement(sql);
					ps.setString(1, trab.getObra_faena());
					ps.setInt(2, trab.getC_trabajadores());
					ps.setString(3, trab.getF_inicio());
					ps.setString(4, trab.getObservacion());
					ps.setString(5, "jose");
					
					
					ps.execute();
					return true;
				}
				catch(SQLException e){
					System.out.println("Error: " +e.getMessage());
				}
				catch(Exception e) {
					System.out.println("Error: "+e.getMessage());
				}
				finally{
					ps.close();
					db.conn.close();
				}
				return false;
			}
	
	
	public static boolean delTrabjador(int id) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		
		try{
			sql = "UPDATE trabajadores SET est_contrato = 'Inactivo' WHERE id = "+id+"";
			ps = db.conn.prepareStatement(sql);
			
			ps.execute();
			ps.close();
			db.conn.close();
			return true;
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return false;
	}
	public static boolean insertLogin(loginApp log) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "INSERT INTO login(id, usuario, pass) values(?,?,?)";
			ps = db.conn.prepareStatement(sql);
			
			ps.setInt(1, log.getId());
			ps.setString(2, log.getUsuario());
			ps.setString(3, log.getPass());
			ps.execute();
			ps.close();
			db.conn.close();
			return true;
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}db.close();
		return false;
	}
	public static ArrayList<trabajadores> loadPersonalById(int id) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<trabajadores> data = new ArrayList<trabajadores>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM trabajadores where id="+id+"";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				trabajadores e = new trabajadores();
				e.setCodigo(rs.getString("codigo"));
				e.setRut(rs.getString("rut"));
				e.setNombre(rs.getString("nombre"));
				e.setfNacimineto(rs.getString("fNacimiento"));
				e.setDireccion(rs.getString("direccion"));
				e.setTelefono(rs.getString("telefono"));
				e.setId(rs.getInt("id"));
				e.setId_perfil(rs.getInt("id_perfil"));
				e.setF_ingreso(rs.getString("f_ingreso"));
				e.setNacionalidad(rs.getString("nacionalidad"));
				e.setEstado_civil(rs.getString("estado_civil"));
				e.setEmail(rs.getString("email"));
				e.setIsapre(rs.getString("isapre"));
				e.setPrevision(rs.getString("prevision"));
				e.setC_normales(rs.getInt("c_normales"));
				e.setC_invalidas(rs.getInt("c_invalidas"));
				e.setT_asign_familiar(rs.getString("t_asign_familiar"));
				e.setF_termino(rs.getString("f_termino"));
				e.setEst_contrato(rs.getString("est_contrato"));
				e.setTipo_contrato(rs.getString("tipo_contrato"));
				e.setId_departamento(rs.getInt("id_departamento"));
				e.setEstablecimiento(rs.getString("establecimiento"));
				e.setHrs_semanal(rs.getInt("hrs_semanal"));
				e.setAjus_sueldo_base(rs.getString("ajus_sueldo_base"));
				e.setBene_semana_corrida(rs.getString("bene_semana_corrida"));
				e.setSeguro_cesantia(rs.getString("seguro_cesantia"));
				e.setIn_per_seg_ces(rs.getString("in_per_seg_ces"));
				e.setAefc_seg_accidentes(rs.getString("afec_seg_accidentes"));
				e.setTipo_pacto(rs.getString("tipo_pacto"));
				e.setMoneda_mon_ges(rs.getString("moneda_mon_ges"));
				e.setMonto_pactado(rs.getInt("monto_pactado"));
				e.setMonto_ges(rs.getInt("monto_ges"));
				e.setTipo_sueldo_base(rs.getString("tipo_sueldo_base"));
				e.setSueldo_mensual(rs.getInt("sueldo_mensual"));
				e.setAsign_zona_extrema(rs.getInt("asign_zona_extrema"));
				e.setGratificacion_legal(rs.getString("gratificacion_legal"));
				e.setNo_cuenta(rs.getString("no_cuenta"));
				e.setInstitucion_bco(rs.getString("institucion_bco"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
	public static boolean addIncidencia(incidencia inc) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "INSERT INTO INCIDENCIAS (id_campo, descripcion, coordenadas, sector_afectado, urgencia, tipo_incidencia, observaciones, estado, fecha) VALUES (?,?,?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, inc.getId_campo());
			ps.setString(2, inc.getDescripcion());
			ps.setString(3, inc.getCoordenadas());
			ps.setString(4, inc.getSector_afectado());
			ps.setString(5, inc.getUrgencia());
			ps.setString(6, inc.getTipo_incidencia());
			ps.setString(7, inc.getObservaciones());
			ps.setString(8, inc.getEstado());
			ps.setString(9, inc.getFecha());
			
			ps.execute();
			return true;
		}
		catch(SQLException e){
			System.out.println("Error: " +e.getMessage());
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
		finally{
			ps.close();
			db.conn.close();
		}
		return false;
	}
//	public static purchaseMap purchaseMap(int id) throws Exception{
//		PreparedStatement ps = null;
//		PreparedStatement ps2 = null;
//		String sql = "";
//		String sql2 = "";
//		purchaseMap map = new purchaseMap();
//		ConnectionDB db = new ConnectionDB();
//		try{
//			sql = "SELECT i.*, it.*, t.nombre, c.* FROM INCIDENCIA_TRABAJADOR it RIGHT JOIN INCIDENCIAS i on (it.id_incidencia = i.id_incidencia) LEFT JOIN trabajadores t on(t.id = it.id_trabajador) RIGHT JOIN test c on(c.idtest = i.id_campo) where c.idtest = "+id+"";
//			sql2 = "SELECT c.idtest, l.*, t.nombre FROM test c LEFT JOIN LABORES l on (c.idtest = l.id_campo) INNER JOIN trabajadores as t on(t.id = l.id_trabajador) WHERE c.idtest = "+id+"";
//			ps = db.conn.prepareStatement(sql);
//			ps2 = db.conn.prepareStatement(sql2);
//			ResultSet rs = ps.executeQuery(sql);
//			ResultSet rs2 = ps2.executeQuery(sql2);
//			ArrayList<purchaseIncidencia> Incidencias = new ArrayList<purchaseIncidencia>();
//			ArrayList<labores> Labores = new ArrayList<labores>();
//			int count = 1;
//			while(rs.next()){
//				purchaseIncidencia pi = new purchaseIncidencia();
//				if(count==1){
//					map.setIdtest(rs.getInt("idtest"));
//					map.setCoordenada(rs.getString("coordenada"));
//					map.setValor1(rs.getString("valor1"));
//					map.setColor(rs.getString("color"));
//					map.setEspecie(rs.getString("especie"));
//					map.setVariedad(rs.getString("variedad"));
//					map.setHectarias(rs.getString("hectarias"));
//					map.setCantidad(rs.getInt("cantidad"));
//					map.setPlantacion(rs.getString("plantacion"));
//					map.setStatus(rs.getString("status"));
//				}
//				pi.setId_incidencia(rs.getInt("id_incidencia"));
//				pi.setId_campo(rs.getInt("id_campo"));
//				pi.setDescripcion(rs.getString("descripcion"));
//				pi.setCoordenadas(rs.getString("coordenadas"));
//				pi.setSector_afectado(rs.getString("sector_afectado"));
//				pi.setUrgencia(rs.getString("urgencia"));
//				pi.setTipo_incidencia(rs.getString("tipo_incidencia"));
//				pi.setObservaciones(rs.getString("observaciones"));
//				pi.setEstado(rs.getString("estado"));
//				pi.setFecha(rs.getString("fecha"));
//				pi.setEncargado(rs.getString("nombre"));
//				Incidencias.add(pi);
//				count++;
//			}
//			while(rs2.next()){
//				labores l = new labores();
//				l.setId_labores(rs2.getInt("id_labores"));
//				l.setId_campo(rs2.getInt("id_campo"));
//				l.setId_trabajador(rs2.getInt("id_trabajador"));
//				l.setActividad(rs2.getString("actividad"));
//				l.setCantidad(rs2.getInt("cantidad"));
//				l.setFecha(rs2.getString("fecha"));
//				l.setUnidad(rs2.getString("unidad"));
//				l.setObservaciones(rs2.getString("observaciones"));
//				l.setTrabajador(rs2.getString("nombre"));
//				Labores.add(l);
//			}
//			map.setPurchaseIncidencia(Incidencias);
//			map.setLabores(Labores);
//			rs.close();
//			rs2.close();
//		}catch(Exception ex){
//			System.out.println("Error: "+ex.getMessage());
//		}finally{
//			ps.close();
//			db.conn.close();
//		}
//		return map;
//	}
	public static ArrayList<trabajadores> innerPersonal() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<trabajadores> data = new ArrayList<trabajadores>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select t.id, t.nombre, p.descripcion from trabajadores t inner join perfil_Usuario p on t.id_perfil = p.id_perfil WHERE t.est_contrato = 'Activo' order by 1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				trabajadores e = new trabajadores();
				e.setId(rs.getInt("id"));
				e.setNombre(rs.getString("nombre"));
				e.setAefc_seg_accidentes(rs.getString("descripcion"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
	public static boolean updTrabajador(trabajadores trab) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "UPDATE trabajadores SET codigo = , rut = , nombre = , fNacimiento = , direccion = , telefono = , id_perfil = , nacionalidad = , estado_civil = , prevision = , f_ingreso = , f_termino = , est_contrato = , tipo_contrato = , id_departamento = , establecimiento = , hrs_semanal = , ajus_sueldo_base = , bene_semana_corrida = , isapre = , email = , c_normales = , c_invalidas = , t_asign_familiar = , seguro_cesantia = , in_per_seg_ces = , afec_seg_accidentes = , tipo_pacto = , moneda_mon_ges = , monto_pactado = , monto_ges = , tipo_sueldo_base = , sueldo_mensual = , asign_zona_extrema = , gratificacion_legal = , no_cuenta = , institucion_bco = ";
			ps = db.conn.prepareStatement(sql);
			
			ps.setString(1, trab.getCodigo());
			ps.setString(2, trab.getRut());
			ps.setString(3, trab.getNombre());
			ps.setString(4, trab.getfNacimineto());
			ps.setString(5, trab.getDireccion());
			ps.setString(6, trab.getTelefono());
			ps.setInt(7, trab.getId_perfil());
			ps.setString(8, trab.getNacionalidad());
			ps.setString(9, trab.getEstado_civil());
			ps.setString(10, trab.getPrevision());
			ps.setString(11, trab.getF_ingreso());
			ps.setString(12, trab.getF_termino());
			ps.setString(13, trab.getEst_contrato());
			ps.setString(14, trab.getTipo_contrato());
			ps.setInt(15, trab.getId_departamento());
			ps.setString(16, trab.getEstablecimiento());
			ps.setInt(17, trab.getHrs_semanal());
			ps.setString(18, trab.getAjus_sueldo_base());
			ps.setString(19, trab.getBene_semana_corrida());
			ps.setString(20, trab.getIsapre());
			ps.setString(21, trab.getEmail());
			ps.setInt(22, trab.getC_normales());
			ps.setInt(23, trab.getC_invalidas());
			ps.setString(24, trab.getT_asign_familiar());
			ps.setString(25, trab.getSeguro_cesantia());
			ps.setString(26, trab.getIn_per_seg_ces());
			ps.setString(27, trab.getAefc_seg_accidentes());
			ps.setString(28, trab.getTipo_pacto());
			ps.setString(29, trab.getMoneda_mon_ges());
			ps.setInt(30, trab.getMonto_pactado());
			ps.setInt(31, trab.getMonto_ges());
			ps.setString(32, trab.getTipo_sueldo_base());
			ps.setInt(33, trab.getSueldo_mensual());
			ps.setInt(34, trab.getAsign_zona_extrema());
			ps.setString(35, trab.getGratificacion_legal());
			ps.setString(36, trab.getNo_cuenta());
			ps.setString(37, trab.getInstitucion_bco());
			ps.execute();
			return true;
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			ps.close();
			db.conn.close();
		}
		return false;
	}
//	public static ArrayList<region> loadRegiones() throws Exception{
//		PreparedStatement ps = null;
//		String sql = "";
//		ArrayList<region> data = new ArrayList<region>();
//		ConnectionDB db = new ConnectionDB();
//		try{
//			sql = "SELECT *FROM REGION";
//			ps = db.conn.prepareStatement(sql);
//			ResultSet rs = ps.executeQuery(sql);
//			while(rs.next()){
//				region e = new region();
//				e.setRegion_id(rs.getInt("region_id"));
//				e.setRegion_nombre(rs.getString("region_nombre"));
//				e.setRegion_ordinal(rs.getString("region_ordinal"));
//				data.add(e);
//			}
//			rs.close();
//			ps.close();
//			db.conn.close();
//		}catch(Exception ex){
//			System.out.println("Error: "+ex.getMessage());
//		}finally{
//			db.close();
//		}
//		return data;
//	}
	public static ArrayList<provincia> loadProvincias() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<provincia> data = new ArrayList<provincia>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM PROVINCIA";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				provincia e = new provincia();
				e.setProvincia_id(rs.getInt("provincia_id"));
				e.setProvincia_nombre(rs.getString("provincia_nombre"));
				e.setRegion_id(rs.getInt("region_id"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
	public static ArrayList<comuna> loadComunas() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<comuna> data = new ArrayList<comuna>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM COMUNA";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				comuna e = new comuna();
				e.setComuna_id(rs.getInt("comuna_id"));
				e.setComuna_nombre(rs.getString("comuna_nombre"));
				e.setProvincia_id(rs.getInt("provincia_id"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
	public static boolean addLabores(labores lab) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "INSERT INTO LABORES(id_campo,id_trabajador,actividad,cantidad,fecha,unidad,observaciones) VALUES (?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, lab.getId_campo());
			ps.setInt(2, lab.getId_trabajador());
			ps.setString(3, lab.getActividad());
			ps.setInt(4, lab.getCantidad());
			ps.setString(5, lab.getFecha());
			ps.setString(6, lab.getUnidad());
			ps.setString(7, lab.getObservaciones());
			ps.execute();
			return true;
		}
		catch(SQLException e){
			System.out.println("Error: " +e.getMessage());
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
		finally{
			ps.close();
			db.conn.close();
		}
		return false;
	}
	public static ArrayList<material> loadMaterial() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<material> data = new ArrayList<material>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM MATERIAL";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				material e = new material();
				e.setId_material(rs.getInt("id_material"));
				e.setNombre_material(rs.getString("nombre_material"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
	public static boolean AsignarIncidencia(IN_INCIDENCIA_ASIGNADA iia) throws Exception{
		CallableStatement cs = null;
		ConnectionDB db = new ConnectionDB();
		try{
			cs = db.conn.prepareCall("{CALL IN_INCIDENCIA_ASIGNADA (?,?,?,?,?,'"+String.valueOf(iia.getCantidad())+"',?,?)}");
			cs.setInt(1, iia.getId_trabajador());
			cs.setInt(2, iia.getId_incidencia());
			cs.setString(3, iia.getFecha());
			cs.setString(4, iia.getDescripcion());
			cs.setInt(5, iia.getId_material());
			cs.setString(6, iia.getUnidad());
			cs.setString(7, iia.getUpd());
			cs.execute();
			return true;
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			cs.close();
			db.conn.close();
		}
		return false;
	}
//	public static innerTrabajadores innerTrabajadores(int id) throws Exception{
//		PreparedStatement ps = null;
//		String sql = "";
//		innerTrabajadores dataTrab = new innerTrabajadores();
//		ConnectionDB db = new ConnectionDB();
//		try{
//			sql = "select trab.*, d.descripcion as d_descripcion, p.descripcion as p_descripcion, l.* from trabajadores trab left join departamentos d on trab.id_departamento = d.id left join perfil_Usuario p on trab.id_perfil = p.id_perfil left join LABORES l on(trab.id = l.id_trabajador) where trab.id ="+id+"";
//			ps = db.conn.prepareStatement(sql);
//			ResultSet rs = ps.executeQuery(sql);
//			ArrayList<perfil_usuario> perfil = new ArrayList<perfil_usuario>();
//			ArrayList<departamentos> departamentos = new ArrayList<departamentos>();
//			ArrayList<labores> labores = new ArrayList<labores>();
//			int count = 1;
//			while(rs.next()){
//				perfil_usuario p = new perfil_usuario();
//				departamentos d = new departamentos();
//				labores l = new labores();
//				if(count==1){
//					dataTrab.setCodigo(rs.getString("codigo"));
//					dataTrab.setRut(rs.getString("rut"));
//					dataTrab.setNombre(rs.getString("nombre"));
//					dataTrab.setfNacimineto(rs.getString("fNacimiento"));
//					dataTrab.setDireccion(rs.getString("direccion"));
//					dataTrab.setTelefono(rs.getString("telefono"));
//					dataTrab.setId(rs.getInt("id"));
//					dataTrab.setF_ingreso(rs.getString("f_ingreso"));
//					dataTrab.setNacionalidad(rs.getString("nacionalidad"));
//					dataTrab.setEstado_civil(rs.getString("estado_civil"));
//					dataTrab.setEmail(rs.getString("email"));
//					dataTrab.setIsapre(rs.getString("isapre"));
//					dataTrab.setPrevision(rs.getString("prevision"));
//					dataTrab.setC_normales(rs.getInt("c_normales"));
//					dataTrab.setC_invalidas(rs.getInt("c_invalidas"));
//					dataTrab.setT_asign_familiar(rs.getString("t_asign_familiar"));
//					dataTrab.setF_termino(rs.getString("f_termino"));
//					dataTrab.setEst_contrato(rs.getString("est_contrato"));
//					dataTrab.setTipo_contrato(rs.getString("tipo_contrato"));
//					dataTrab.setEstablecimiento(rs.getString("establecimiento"));
//					dataTrab.setHrs_semanal(rs.getInt("hrs_semanal"));
//					dataTrab.setAjus_sueldo_base(rs.getString("ajus_sueldo_base"));
//					dataTrab.setBene_semana_corrida(rs.getString("bene_semana_corrida"));
//					dataTrab.setSeguro_cesantia(rs.getString("seguro_cesantia"));
//					dataTrab.setIn_per_seg_ces(rs.getString("in_per_seg_ces"));
//					dataTrab.setAefc_seg_accidentes(rs.getString("afec_seg_accidentes"));
//					dataTrab.setTipo_pacto(rs.getString("tipo_pacto"));
//					dataTrab.setMoneda_mon_ges(rs.getString("moneda_mon_ges"));
//					dataTrab.setMonto_pactado(rs.getInt("monto_pactado"));
//					dataTrab.setMonto_ges(rs.getInt("monto_ges"));
//					dataTrab.setTipo_sueldo_base(rs.getString("tipo_sueldo_base"));
//					dataTrab.setSueldo_mensual(rs.getInt("sueldo_mensual"));
//					dataTrab.setAsign_zona_extrema(rs.getInt("asign_zona_extrema"));
//					dataTrab.setGratificacion_legal(rs.getString("gratificacion_legal"));
//					dataTrab.setNo_cuenta(rs.getString("no_cuenta"));
//					dataTrab.setInstitucion_bco(rs.getString("institucion_bco"));
//					p.setDescripcion(rs.getString("p_descripcion"));
//					d.setDescripcion(rs.getString("d_descripcion"));
//					perfil.add(p);
//					departamentos.add(d);
//				}
//				l.setId_labores(rs.getInt("id_labores"));
//				l.setId_campo(rs.getInt("id_campo"));
//				l.setId_trabajador(rs.getInt("id_trabajador"));
//				l.setActividad(rs.getString("actividad"));
//				l.setCantidad(rs.getInt("cantidad"));
//				l.setFecha(rs.getString("fecha"));
//				l.setUnidad(rs.getString("unidad"));
//				l.setObservaciones(rs.getString("observaciones"));
//				labores.add(l);
//				count++;
//			}
//			dataTrab.setPerfil(perfil);
//			dataTrab.setDepartamento(departamentos);
//			dataTrab.setLabores(labores);
//			rs.close();
//		}catch(Exception ex){
//			System.out.println("Error: "+ex.getMessage());
//		}finally{
//			ps.close();
//			db.conn.close();
//		}
//		return dataTrab;
//	}
	public static boolean updEstadoIncidencia(incidencia inc) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "UPDATE INCIDENCIAS SET observaciones = ?, estado = ? WHERE id_incidencia = ?;";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, inc.getObservaciones());
			ps.setString(2, inc.getEstado());
			ps.setInt(3, inc.getId_incidencia());
			
			ps.execute();
			return true;
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			ps.close();
			db.conn.close();
		}
		return false;
	}
}

package lib.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import lib.classSA.CAMPO;
import lib.classSA.CUARTEL;
import lib.classSA.ESPECIE;
import lib.classSA.LABOR;
import lib.classSA.VARIEDAD;
import lib.classSA.bloqueo_periodo;
import lib.classSA.calificacion_campo;
import lib.classSA.faena;
import lib.sesionSA.SESION;
import lib.struc.loginApp;
import lib.struc.mapConection;
import lib.classSA.SECTOR;
import lib.classSA.SESIONVAR;
import lib.classSA.TEMPORADA;

public class simpleagroDB { 
	public static ArrayList<CAMPO> getCAMPO(HttpSession httpSession, int id) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CAMPO> data = new ArrayList<CAMPO>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT cam.*, s.idSociedad FROM campo cam ";
			sql += "left join usuario_campo uc on uc.codigo_campo = cam.campo ";	
			sql += "left join sociedad s on(s.sociedad = cam.sociedad) ";
			sql += "where uc.codigo_usuario ="+id;
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				CAMPO e = new CAMPO();
				e.setCodigo(rs.getInt("codigo"));
				e.setCampo(rs.getString("campo"));
				e.setSociedad(rs.getString("sociedad"));
				e.setNombre(rs.getString("nombre"));
				e.setDescripcion(rs.getString("descripcion"));
				e.setDirecion(rs.getString("direcion"));
				e.setTelefiono(rs.getString("telefono"));
				e.setGerente_zonal(rs.getString("gerente_zonal"));
				e.setAdm_campo(rs.getString("adm_campo"));
				e.setZona(rs.getString("zona"));
				e.setSubsidio(rs.getInt("subsidio"));
				e.setGeoreferencia(rs.getString("georeferencia"));
				e.setGrupo(rs.getString("grupo"));
				e.setArea(rs.getInt("area"));
				e.setCecoMaq(rs.getString("grupo_maquinaria"));
				e.setGrupo_ceco_work(rs.getString("grupo_ceco_work"));
				e.setIdSociedad(rs.getInt("IdSociedad"));
				e.setGrupo_co(rs.getString("grupo_co"));
				e.setCecos(rs.getString("cecos"));
				e.setCampos_maq(rs.getString("campos_maq"));
				e.setTipo_maq(rs.getInt("tipo_maq"));
				e.setGrupo_repuesto(rs.getString("grupo_repuesto"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error getCAMPO: "+ex.getMessage());
		}
		finally{
			ps.close();
			db.close();
		}
		SESION mc= new SESION(httpSession);
		mc.updateCampo();
		for(CAMPO campo: data){
			mc.addCuartel(campo, campo.codigo);
		}
		mc.save();
		return data;
	}
	public static boolean updatesubsidioCampo (CAMPO c) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new  ConnectionDB();
		try {			
			sql = " UPDATE campo set subsidio='"+c.getSubsidio()+"' where codigo='"+c.getCodigo()+"'";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return false;
	}
	
	public static ArrayList<ESPECIE> getESPECIE(HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<ESPECIE> data = new ArrayList<ESPECIE>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT * FROM especie";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				ESPECIE e = new ESPECIE();
				e.setCodigo(rs.getInt("codigo"));
				e.setEspecie(rs.getString("especie"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error getCAMPO: "+ex.getMessage());
		}
		finally{
			ps.close();
			db.close();
		}
		SESION mc= new SESION(httpSession);
		for(ESPECIE especie: data){
			mc.addEspecie(especie, especie.codigo);
		}
		mc.save();
		return data;
	}
	
	public static ArrayList<CUARTEL> GET_ALL_CUARTEL() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CUARTEL> data = new ArrayList<CUARTEL>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT * FROM SAN_CLEMENTE.cuartel";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				CUARTEL e = new CUARTEL();
				e.setCodigo(rs.getInt("codigo"));
				e.setCodigo_cuartel(rs.getString("codigo_cuartel"));
				e.setNombre(rs.getString("nombre"));
				e.setSector(rs.getString("sector"));
				e.setVariedad(rs.getInt("variedad"));
				e.setPatron(rs.getString("patron"));
				e.setAno_plantacion(rs.getString("ano_plantacion"));
				e.setSuperficie(rs.getInt("superficie"));
				e.setPlantas(rs.getInt("plantas"));
				e.setDistancia_largo(rs.getFloat("distancia_largo"));
				e.setDistancia_hancho(rs.getFloat("distancia_hancho"));
				e.setFormacion(rs.getString("formacion"));
				e.setVivero(rs.getString("vivero"));
				e.setTipo_planta(rs.getString("tipo_planta"));
				e.setTipo_control_heladas(rs.getString("tipo_control_heladas"));
				e.setTipo_proteccion(rs.getString("tipo_proteccion"));
				e.setLimitante_suelo(rs.getString("limitante_suelo"));
				e.setPolinizante(rs.getString("polinizante"));
				e.setEstado(rs.getInt("estado"));
				e.setTipo_plantacion(rs.getString("tipo_plantacion"));
				e.setClon(rs.getString("clon"));
				e.setGeoreferencia(rs.getString("georeferencia"));
				e.setCeco(rs.getString("ceco"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error getCUARTEL: "+ex.getMessage());
		}
		finally{
			ps.close();
			db.close();
		}
		return data;
	}
	//public static ArrayList<VARIEDAD> getVARIEDAD(int id) throws Exception{

	public static ArrayList<VARIEDAD> getVARIEDAD(HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<VARIEDAD> data = new ArrayList<VARIEDAD>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT * FROM variedad";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				VARIEDAD e = new VARIEDAD();
				e.setCodigo(rs.getInt("codigo"));
				e.setEspecie(rs.getInt("especie"));
				e.setVariedad(rs.getString("variedad"));
				e.setFecha_estimada_cosecha(rs.getString("fecha_estimada_cosecha"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error id: "+ex.getMessage());
		}
		finally{
			ps.close();
			db.close(); 
		}
		SESION mc= new SESION(httpSession);
		SESIONVAR SA = mc.getView();
		SA.getVariedad().clear();
		for(VARIEDAD variedad: data){
			mc.addVariedad(variedad, variedad.codigo);
		}
		mc.save();
		return data;
	}
	
	public static String getSector(String Campo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		String sector = "";
		try{
			sql = "SELECT MAX(sector) sector from sector where campo = '"+Campo+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				sector = rs.getString("sector");
			}
			int codSector = Integer.parseInt(sector.substring ( 4 , 6 ));
			codSector++;
			String newSector = Integer.toString(codSector);
			if(codSector < 10){
				newSector = "0"+newSector;
			}
			sector = Campo + newSector;
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error id: "+ex.getMessage());
		}
		finally{
			ps.close();
			db.close(); 
		}
		return sector;
	}
	public static boolean ADDSECTOR_SECTOR(SECTOR c,HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "INSERT INTO sector (campo, sector, descripcion) VALUES (?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, c.getCampo());
			ps.setString(2, getSector(c.getCampo()));
			ps.setString(3, c.getDescripcion());
			ps.execute();
			SESION mc= new SESION(httpSession);
			simpleagroDB.getSECTOR(httpSession);
			mc.save();
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
			db.close();
		}
		return false;
	}
	
	
	public static boolean updateSECTOR(SECTOR s) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new  ConnectionDB();
		try {			
			sql = " UPDATE sector SET descripcion='"+s.getDescripcion()+"' where codigo='"+s.getCodigo()+"'";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return false;
	}
	
	public static ArrayList<SECTOR> getSECTOR(HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<SECTOR> data = new ArrayList<SECTOR>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT * FROM sector";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				SECTOR e = new SECTOR();
				e.setCodigo(rs.getInt("codigo"));
				e.setSector(rs.getString("sector"));
				e.setDescripcion(rs.getString("descripcion"));
				e.setCampo(rs.getString("campo"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error getCAMPO: "+ex.getMessage());
		}
		finally{
			ps.close();
			db.close();
		}
		SESION mc= new SESION(httpSession);
		for(SECTOR sector: data){
			mc.addSector(sector, sector.sector);
		}
		mc.save();
		return data;
	}
	public static ArrayList<SECTOR> getSECTOR(String codigo) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<SECTOR> lista = new ArrayList<SECTOR>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT * from sector where campo = '"+codigo+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				SECTOR pm = new SECTOR();
				pm.setCodigo(rs.getInt("codigo"));
				pm.setCampo(rs.getString("campo"));
				pm.setSector(rs.getString("sector"));
				pm.setDescripcion(rs.getString("descripcion"));
				lista.add(pm);
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
	}
	
	public static ArrayList<CUARTEL> getCUARTEL(HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CUARTEL> data = new ArrayList<CUARTEL>();
		ConnectionDB db = new ConnectionDB();
		SESION mc = new SESION(httpSession);
		try{
			SESIONVAR global = mc.getView();
			String inSql = "";
			int count = 1;
			for(CAMPO c: global.getCampo()){
				if(count == 1){
					inSql += "'"+c.getCampo()+"'";
				}else{
					inSql += ", '"+c.getCampo()+"'";
				}
				count++;
			}
			sql = "SELECT     c.*, CONCAT(c.codigo_cuartel, ' ', c.nombre) AS codigo_nombre,    s.descripcion,    e.especie nespecie,    v.variedad,    v.variedad nvariedad,    s.campo,    cam.zona,    ls.descripcion nlimitanteSuelo,    tp.descripcion ntipoPlanta,    "
					+ "tch.descripcion ncontrolHeladas,    tpr.descripcion ntipoProteccion,    tpn.descripcion ntipoPlantacion,    f.descripcion formacion2 "
					+ "FROM    cuartel c        "
					+ "LEFT JOIN    sector s ON c.sector = s.sector         "
					+ "LEFT JOIN    especie e ON c.especie = e.codigo "
					+ "LEFT JOIN    variedad v ON (c.variedad = v.codigo)  "
					+ "LEFT JOIN     campo cam ON s.campo = cam.campo    "
					+ "left join (SELECT * from parametros where  codigo = 'Limitantes de Suelo') ls     on ls.llave = c.limitante_suelo "
					+ "left join (SELECT * from parametros where  codigo = 'Tipo Plantacion') tpn     on tpn.llave = c.tipo_plantacion "
					+ "left join (SELECT * from parametro_especie where  tabla = 'Formacion') f     on f.id = c.formacion and f.especie = c.especie "
					+ "left join (SELECT * from parametros where  codigo = 'Tipo Control Heladas') tch     on tch.llave = c.tipo_control_heladas "
					+ "left join (SELECT * from parametros where  codigo = 'Tipo de planta') tp      on tp.llave = c.tipo_planta "
					+ "left join (SELECT * from parametros where  codigo = 'Tipo Proteccion') tpr     on tpr.llave = c.tipo_proteccion "
					+ "WHERE cam.campo IN ("+inSql+") AND c.estado != 3";
			ps = db.conn.prepareStatement(sql);
			System.out.print(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				CUARTEL e = new CUARTEL();
				e.setCampo(rs.getString("campo"));
				e.setCodigo(rs.getInt("codigo"));
				e.setCodigo_cuartel(rs.getString("codigo_cuartel"));
				e.setNombre(rs.getString("codigo_nombre"));
				e.setDescripcion(rs.getString("descripcion"));
				e.setEspecie(rs.getInt("especie"));
				e.setSector(rs.getString("sector"));
				e.setVariedad(rs.getInt("variedad"));
				e.setPatron(rs.getString("patron"));
				e.setAno_plantacion(rs.getString("ano_plantacion"));
				e.setSuperficie(rs.getFloat("superficie"));
				e.setPlantas(rs.getInt("plantas"));
				e.setDistancia_largo(rs.getFloat("distancia_largo"));
				e.setDistancia_hancho(rs.getFloat("distancia_hancho"));
				e.setFormacion(rs.getString("formacion"));
				e.setNformacion(rs.getString("formacion2"));
				e.setVivero(rs.getString("vivero"));
				e.setTipo_planta(rs.getString("tipo_planta"));
				e.setNtipo_planta(rs.getString("ntipoPlanta"));
				e.setTipo_control_heladas(rs.getString("tipo_control_heladas"));
				e.setNtipo_control_heladas(rs.getString("ncontrolHeladas"));
				e.setTipo_proteccion(rs.getString("tipo_proteccion"));
				e.setNtipo_proteccion(rs.getString("ntipoProteccion"));
				e.setLimitante_suelo(rs.getString("limitante_suelo"));
				e.setNlimitante_suelo(rs.getString("nlimitanteSuelo"));
				e.setPolinizante(rs.getString("polinizante"));
				e.setEstado(rs.getInt("estado"));
				e.setTipo_plantacion(rs.getString("tipo_plantacion"));
				e.setNtipo_plantacion(rs.getString("ntipoPlantacion"));
				e.setClon(rs.getString("clon"));
				e.setGeoreferencia(rs.getString("georeferencia"));
				e.setCeco(rs.getString("ceco"));
				e.setNvariedad(rs.getString("nvariedad"));
				e.setNespecie(rs.getString("nespecie"));
				e.setZona(rs.getString("zona"));
				e.setOrdenco(rs.getString("ordenco"));
				e.setMacroco(rs.getString("macroco"));
				
//				e.settipoPlanta(rs.getString("tipoPlanta"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error id: "+ex.getMessage());
		}
		finally{
			ps.close();
			db.close();
		}
		for(CUARTEL cuartel: data){
			mc.addCuarteles(cuartel, cuartel.codigo);
		}
		mc.save();
		return data;
	}
	
	public static boolean updateCUARTEL(CUARTEL c) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new  ConnectionDB();
		try {			
			sql = " UPDATE cuartel set superficie='"+c.getSuperficie()+"', plantas='"+c.getPlantas()+"', "
				+ " tipo_planta='"+c.getTipo_planta()+"', patron='"+c.getPatron()+"', "
				+ " distancia_largo='"+c.getDistancia_largo()+"', distancia_hancho='"+c.getDistancia_hancho()+"',"
				+ " formacion='"+c.getFormacion()+"', vivero='"+c.getVivero()+"',"
				+ " tipo_control_heladas='"+c.getTipo_control_heladas()+"',"
				+ " tipo_proteccion='"+c.getTipo_proteccion()+"', limitante_suelo='"+c.getLimitante_suelo()+"',"
				+ " polinizante='"+c.getPolinizante()+"', tipo_plantacion='"+c.getTipo_plantacion()+"',"
				+ " clon='"+c.getClon()+"', ano_plantacion='"+c.getAno_plantacion()+"', ceco='"+c.getCeco()+"',"
				+ " estado='"+c.getEstado()+"' where codigo='"+c.getCodigo()+"'";		
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return false;
	}
	
	public static ArrayList<CAMPO> GET_SOCIEDAD(int id) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CAMPO> lista = new ArrayList<CAMPO>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT distinct (s.sociedad), denominacionSociedad, idSociedad "
					+ "FROM SAN_CLEMENTE.sociedad s "
					+ "JOIN campo c ON c.sociedad = s.sociedad "
					+ "where c.campo in (SELECT codigo_campo FROM SAN_CLEMENTE.usuario_campo where codigo_usuario = "+id+");";
			System.out.println(sql); 
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				CAMPO pm = new CAMPO();
				pm.setIdSociedad(rs.getInt("idSociedad"));
				pm.setSociedad(rs.getString("sociedad"));
				pm.setDescripcion(rs.getString("denominacionSociedad"));
				lista.add(pm);
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
	}
	
	public static ArrayList<LABOR> GET_LABORYFAENA() throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<LABOR> lista = new ArrayList<LABOR>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT fa.codigo as codigoFaena, fa.faena, lb.codigo as codigoLabor, lb.labor, "
					+ " case when lb.maquinaria = 0 then 'NO' ELSE 'SI' END maquinaria, "
					+ " case when lb.rebaja = 1 then 'BINS' when lb.rebaja = 2 then 'PLANTAS' ELSE 'NO APLICA' END rebaja, "
					+ " lb.zona, fa.clasificacion "
					+ " FROM labor lb "
					+ " left join faena fa on fa.codigo = lb.faena "
					+ " where lb.estado = 1";
			System.out.println(sql); 
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				LABOR pm = new LABOR();
				pm.setFaena(rs.getInt("codigoFaena"));
				pm.setNfaena(rs.getString("faena"));
				pm.setCodigo(rs.getInt("codigoLabor"));
				pm.setLabor(rs.getString("labor"));
				pm.setNmaquinaria(rs.getString("maquinaria"));
				pm.setNrebaja(rs.getString("rebaja"));
				pm.setZona(rs.getString("zona"));
				pm.setClasificacion(rs.getString("clasificacion"));
				lista.add(pm);
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
	}
	
	
	public static ArrayList<bloqueo_periodo> getBloqueo(HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<bloqueo_periodo> data = new ArrayList<bloqueo_periodo>();
		ConnectionDB db = new ConnectionDB();
		SESION mc = new SESION(httpSession);
		try{
			sql = "SELECT *FROM sw_cierre_periodos;";
			ps = db.conn.prepareStatement(sql);
			System.out.print(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				bloqueo_periodo e = new bloqueo_periodo();
				e.setId(rs.getInt("id"));
				e.setEmpresa(rs.getInt("empresa"));
				e.setCampo(rs.getString("huerto"));
				e.setPeriodo(rs.getInt("periodo"));
				e.setWork_agro(rs.getInt("work_agro"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error id: "+ex.getMessage());
		}
		finally{
			ps.close();
			db.close();
		}
		for(bloqueo_periodo b: data){
			mc.addBloqueo(b, b.id);
		}
		mc.save();
		return data;
	}
	public static ArrayList<calificacion_campo> GET_CALIFICACION(String campo, int labor) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<calificacion_campo> lista = new ArrayList<calificacion_campo>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql += 	"SELECT *FROM calificacion_campo ";
			sql +=	"WHERE campo = '"+campo+"' AND labor = "+labor+";";
			System.out.println(sql); 
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				calificacion_campo e = new calificacion_campo();
				e.setCodigo(rs.getInt("codigo"));
				e.setCampo(rs.getString("campo"));
				e.setBajo_max(rs.getFloat("bajo_max"));
				e.setPromedio_max(rs.getFloat("promedio_max"));
				e.setBueno_max(rs.getFloat("bueno_max"));
				e.setLabor(rs.getInt("labor"));
				e.setEspecie(rs.getInt("especie"));
				e.setFaena(rs.getInt("faena"));
				lista.add(e);
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
	}
	public static ArrayList<bloqueo_periodo> GET_BLOQUEO_M_P(int periodo, String campo, int tipo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<bloqueo_periodo> data = new ArrayList<bloqueo_periodo>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM sw_cierre_periodos WHERE huerto = '"+campo+"' AND periodo = "+periodo+" AND work_agro = "+tipo+";";
			ps = db.conn.prepareStatement(sql);
			System.out.print(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				bloqueo_periodo e = new bloqueo_periodo();
				e.setId(rs.getInt("id"));
				e.setEmpresa(rs.getInt("empresa"));
				e.setCampo(rs.getString("huerto"));
				e.setPeriodo(rs.getInt("periodo"));
				e.setWork_agro(rs.getInt("work_agro"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error id: "+ex.getMessage());
		}
		finally{
			ps.close();
			db.close();
		}
		return data;
	}
	public static boolean ADD_VARIEDAD(VARIEDAD c,HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "INSERT INTO variedad (especie, variedad, fecha_estimada_cosecha) VALUES (?,CONCAT(UPPER(LEFT('"+c.getVariedad()+"', 1)), LOWER(SUBSTRING('"+c.getVariedad()+"', 2))),?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, c.getEspecie());
			ps.setString(2, c.getFecha_estimada_cosecha());
			ps.execute();
			SESION mc= new SESION(httpSession);
			simpleagroDB.getVARIEDAD(httpSession);
			mc.save();
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
			db.close();
		}
		return false;
	}
	public static boolean UPD_VARIEDAD(VARIEDAD c,HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		System.out.println(c.getCodigo());
		try{
			sql = "UPDATE variedad SET variedad = ?, fecha_estimada_cosecha = ? WHERE codigo = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, c.getVariedad());
			ps.setString(2, c.getFecha_estimada_cosecha());
			ps.setInt(3, c.getCodigo());
			System.out.println(ps.toString());
			ps.execute();
			SESION mc= new SESION(httpSession);
			simpleagroDB.getVARIEDAD(httpSession);
			mc.save();
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
			db.close();
		}
		return false;
	}
	public static ArrayList<CAMPO> GET_ANNOS_TEMPORADAS() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<CAMPO> data = new ArrayList<CAMPO>();
		try{
			sql += 	"SELECT DISTINCT(DATE_FORMAT(fecha, '%Y')) AS ano ";
			sql += 	"FROM sw_fechas ";
			sql += 	"WHERE ";
			sql += 		"DATE_FORMAT(fecha, '%Y') <= DATE_FORMAT(CURDATE(), '%Y') + 3 ";
			sql += 		"AND DATE_FORMAT(fecha, '%Y') >= DATE_FORMAT(CURDATE(), '%Y') - 3;";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				CAMPO e = new CAMPO();
				e.setCodigo(rs.getInt("ano"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}
		catch(SQLException e){
			System.out.println("Error: " +e.getMessage());
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
		finally{
			ps.close();
			db.close();
		}
		return data;
	}
	public static boolean ADD_TEMPORADA(TEMPORADA t) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "INSERT INTO temporada (temporada, estado, ano_inicio, ano_termino, fecha_inicio, fecha_termino) VALUES (?,1,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, t.getDescripcion());
			ps.setInt(2, t.getAno_inicio());
			ps.setInt(3, t.getAno_termino());
			ps.setString(4, t.getFecha_inicio());
			ps.setString(5, t.getFecha_termino());
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
			db.close();
		}
		return false;
	}
	public static SESIONVAR GET_LOGIN_APP(String user, HttpSession httpSession) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		SESIONVAR lista = new SESIONVAR();
		ConnectionDB db = new ConnectionDB();
		try {
			sql ="SELECT * FROM loginTest where usuario= '"+user+"' ";
			System.out.println(sql); 
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			if(rs.next()){
				lista.setIdUser(rs.getInt("id"));
				lista.setUser(rs.getString("usuario"));
				lista.setPass(rs.getString("pass"));
				lista.setGrupoCompra(rs.getString("grupo_compra"));
				lista.setRolPrivado(rs.getInt("rolPrivado"));
				lista.setSolicitante(rs.getString("solicitante"));
				lista.setCampo(simpleagroDB.getCAMPO(httpSession, rs.getInt("id")));
				lista.setEspecie(simpleagroDB.getESPECIE(httpSession));
				lista.setSector(simpleagroDB.getSECTOR(httpSession));
				lista.setVariedad(simpleagroDB.getVARIEDAD(httpSession));
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
	}
	public static String GET_DOSIFICADORES(String campo) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		JSONArray data = new  JSONArray();
		ConnectionDB db = new ConnectionDB();
		try {
			sql ="SELECT *FROM parametros_campo WHERE campo = '"+campo+"' AND tabla = 'Dosificador'";
			System.out.println(sql); 
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				JSONObject e = new JSONObject();
				e.put("codigo", rs.getInt("codigo"));
				e.put("id", rs.getInt("id"));
				e.put("campo", rs.getString("campo"));
				e.put("nombre", rs.getString("descripcion"));
				e.put("activo", rs.getInt("activo"));
				e.put("tabla", rs.getString("tabla"));
				data.put(e);
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
		return data.toString();
	}
	public static boolean ADD_DOSIFICADOR(String data) throws Exception{
		PreparedStatement ps = null;
		JSONObject e = new JSONObject(data);
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "INSERT INTO parametros_campo (id, campo, descripcion, activo, tabla) VALUES ((SELECT IFNULL(MAX(id),0)+1 as id from (SELECT *FROM parametros_campo) s where campo = '"+e.getString("campo")+"' AND tabla = 'Dosificador'),?,?,?,'Dosificador')";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, e.getString("campo"));
			ps.setString(2, e.getString("nombre"));
			ps.setInt(3, e.getInt("activo"));
			ps.execute();
			return true;
		}
		catch(SQLException ex){
			System.out.println("Error: " +ex.getMessage());
		}
		catch(Exception ex) {
			System.out.println("Error: "+ex.getMessage());
		}
		finally{
			ps.close();
			db.close();
		}
		return false;
	}
	public static boolean UPD_DOSIFICADOR(String data) throws Exception{
		PreparedStatement ps = null;
		JSONObject e = new JSONObject(data);
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "UPDATE parametros_campo SET descripcion = ?, activo = ? WHERE codigo = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, e.getString("nombre"));
			ps.setInt(2, e.getInt("activo"));
			ps.setInt(3, e.getInt("codigo"));
			ps.execute();
			return true;
		}
		catch(SQLException ex){
			System.out.println("Error: " +ex.getMessage());
		}
		catch(Exception ex) {
			System.out.println("Error: "+ex.getMessage());
		}
		finally{
			ps.close();
			db.close();
		}
		return false;
	}
	public static boolean DEL_DOSIFICADOR(String data) throws Exception{
		PreparedStatement ps = null;
		JSONObject e = new JSONObject(data);
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "DELETE FROM parametros_campo WHERE codigo = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, e.getInt("codigo"));
			ps.execute();
			return true;
		}
		catch(SQLException ex){
			System.out.println("Error: " +ex.getMessage());
		}
		catch(Exception ex) {
			System.out.println("Error: "+ex.getMessage());
		}
		finally{
			ps.close();
			db.close();
		}
		return false;
	}
	public static String GET_VARIEDAD_CAMPO(int variedad) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		JSONArray data = new  JSONArray();
		ConnectionDB db = new ConnectionDB();
		try {
			sql +=	"SELECT DISTINCT ";
			sql +=		"cm.campo, cm.descripcion, ";
			sql +=		"IF((SELECT id FROM variedad_campo WHERE variedad = "+variedad+" AND campo = cm.campo LIMIT 1) IS NOT NULL, 1, 0) AS existe, ";
			sql +=		"IFNULL(vc.fecha_cosecha, 'No especificada') AS fecha_cosecha ";
			sql +=	"FROM ";
			sql +=		"campo cm ";
			sql +=		"LEFT JOIN sector s ON (s.campo = cm.campo) ";
			sql +=		"LEFT JOIN cuartel c ON (s.sector = c.sector) ";
			sql +=		"LEFT JOIN variedad_campo vc ON(vc.campo = cm.campo AND vc.variedad = "+variedad+")";
			sql +=	"WHERE ";
			sql +=		"c.variedad = "+variedad+" ";
			System.out.println(sql); 
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				JSONObject e = new JSONObject();
				e.put("campo", rs.getString("campo"));
				e.put("nombre", rs.getString("descripcion"));
				e.put("existe", rs.getInt("existe"));
				e.put("fecha_cosecha", rs.getString("fecha_cosecha"));
				data.put(e);
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
		return data.toString();
	}
	public static boolean UPD_FECHAS_VARIEDAD(String data, ConnectionDB db) throws Exception{
		PreparedStatement ps = null;
		JSONObject e = new JSONObject(data);
		String sql = "";
		try{
			sql = "CALL sa_updFechas_variedad(?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, e.getString("fecha"));
			ps.setInt(2, e.getInt("variedad"));
			ps.setString(3, e.getString("campo"));
			ps.execute();
			return true;
		}
		catch(SQLException ex){
			System.out.println("Error: " +ex.getMessage());
		}
		catch(Exception ex) {
			System.out.println("Error: "+ex.getMessage());
		}
		finally{
			ps.close();
			db.close();
		}
		return false;
	}
}
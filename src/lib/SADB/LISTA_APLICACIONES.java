package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

import lib.classSA.CUARTEL_PF;
import lib.classSA.FILTRO_PF;
import lib.classSA.MATERIAL_PF;
import lib.classSA.PERFIL;
import lib.classSA.calificacion_campo;
import lib.classSA.filtro_cc;
import lib.classSA.notificacion;
import lib.classSA.programa_aplicacion;
import lib.db.ConnectionDB;
import lib.classSA.LISTA_APLICACIONES_OBJ;
import lib.classSA.MAQUINARIA_PF;

public class LISTA_APLICACIONES { 
	
	

//	----------LISTA APLICACIONES---------------
//	SELECT
	public static ArrayList<LISTA_APLICACIONES_OBJ> getListaAplicaciones() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<LISTA_APLICACIONES_OBJ> list = new ArrayList<LISTA_APLICACIONES_OBJ>();
		ConnectionDB db = new ConnectionDB();
		try{  
			sql = "select pf.codigo as numero_orden, oa.codigo, CONCAT(trA.nombre,' ',trA.apellidoPaterno) nombre_aplicador, pf.campo, pf.nreserva, pf.solped, "
					+ "CONCAT(trJA.nombre,' ',trJA.apellidoPaterno) jefe_aplicacion, "
					+ " pf.fecha_estimada, e.especie, v.variedad, ef.estado_fenologicos, me.descripcion nmercado, epf.descripcion estado, cm.descripcion ncampo,"
					+ "v.fecha_estimada_cosecha, me.descripcion as mercado, fa.descripcion forma_aplicacion, pf.id idPrograma, oa.id idOrden "
					+ "from orden_aplicacion oa inner join programa_fitosanitario pf on oa.codigo_pf = pf.codigo "
					+ "left join especie e on e.codigo = pf.especie "
					+ "left join campo cm on cm.campo = pf.campo "
					+ "left join estado_pf epf on epf.codigo = pf.estado_pf "
					+ "left join variedad v on v.codigo = pf.variedad "
					+ "left join estado_fenologico ef on ef.codigo = pf.estado_fenologico "
					+ "left join trabajadores trJA on trJA.id = oa.jefe_aplicacion "
					+ "left join mercado me on me.codigo = oa.mercado "
					+ "left join forma_aplicacion fa on oa.codigo_fa = fa.codigo "
					+ "left join trabajadores trA on trA.id = oa.aplicador "
					//+ " LEFT JOIN usuario u on u.codigo =  oa.aplicador "
					+ " order by oa.id desc";
//					"' and  fecha_estimada between '" +filtro.getFecha_desde()+ "' and '" +filtro.getFecha_hasta()+ 
//					"' and temporada='" +filtro.getTemporada()+ "' and estado_pf != 5"
					
			System.out.println(sql);  
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				LISTA_APLICACIONES_OBJ ob = new LISTA_APLICACIONES_OBJ();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setEspecie(rs.getString("especie"));
				ob.setEstado_fenologico(rs.getString("estado_fenologicos"));
				ob.setFecha_estimada_aplicacion(rs.getDate("fecha_estimada"));
				ob.setFecha_estimada_cosecha(rs.getDate("fecha_estimada_cosecha"));
				ob.setForma_aplicacion(rs.getString("forma_aplicacion"));
				ob.setMercado(rs.getString("mercado"));
				ob.setNombre_aplicador(rs.getString("nombre_aplicador"));
				ob.setJefe_aplicacion(rs.getString("jefe_aplicacion"));
				ob.setNumero_orden(rs.getInt("numero_orden"));
				ob.setVariedad(rs.getString("variedad"));
				ob.setIdPrograma(rs.getInt("idPrograma"));
				ob.setIdorden(rs.getInt("idOrden"));
				ob.setCampo(rs.getString("ncampo"));
				ob.setCodCampo(rs.getString("campo"));
				ob.setEstado(rs.getString("estado"));
				ob.setNreserva(rs.getString("nreserva"));
				ob.setSolped(rs.getString("solped"));
				list.add(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {
			db.close();
		}
		return list;
		
	}
	
	
	public static ArrayList<LISTA_APLICACIONES_OBJ> getListaAplicacionesPendientes() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<LISTA_APLICACIONES_OBJ> list = new ArrayList<LISTA_APLICACIONES_OBJ>();
		ConnectionDB db = new ConnectionDB();
		try{  
			sql = "select pf.codigo as numero_orden, oa.codigo, CONCAT(trA.nombre,' ',trA.apellidoPaterno) nombre_aplicador, pf.campo, pf.nreserva, pf.solped, "
					+ "CONCAT(trJA.nombre,' ',trJA.apellidoPaterno) jefe_aplicacion, "
					+ " pf.fecha_estimada, e.especie, v.variedad, ef.estado_fenologicos, me.descripcion nmercado, epf.descripcion estado, cm.descripcion ncampo,"
					+ "v.fecha_estimada_cosecha, me.descripcion as mercado, fa.descripcion forma_aplicacion, pf.id idPrograma, oa.id idOrden "
					+ "from orden_aplicacion oa inner join programa_fitosanitario pf on oa.codigo_pf = pf.codigo "
					+ "left join especie e on e.codigo = pf.especie "
					+ "left join campo cm on cm.campo = pf.campo "
					+ "left join estado_pf epf on epf.codigo = pf.estado_pf "
					+ "left join variedad v on v.codigo = pf.variedad "
					+ "left join estado_fenologico ef on ef.codigo = pf.estado_fenologico "
					+ "left join trabajadores trJA on trJA.id = oa.jefe_aplicacion "
					+ "left join mercado me on me.codigo = oa.mercado "
					+ "left join forma_aplicacion fa on oa.codigo_fa = fa.codigo "
					+ "left join trabajadores trA on trA.id = oa.aplicador "
					+ "where (pf.estado_pf = 3 or pf.estado_pf = 6)"
					+ " order by oa.id desc";
					
			System.out.println(sql);  
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				LISTA_APLICACIONES_OBJ ob = new LISTA_APLICACIONES_OBJ();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setEspecie(rs.getString("especie"));
				ob.setEstado_fenologico(rs.getString("estado_fenologicos"));
				ob.setFecha_estimada_aplicacion(rs.getDate("fecha_estimada"));
				ob.setFecha_estimada_cosecha(rs.getDate("fecha_estimada_cosecha"));
				ob.setForma_aplicacion(rs.getString("forma_aplicacion"));
				ob.setMercado(rs.getString("mercado"));
				ob.setNombre_aplicador(rs.getString("nombre_aplicador"));
				ob.setJefe_aplicacion(rs.getString("jefe_aplicacion"));
				ob.setNumero_orden(rs.getInt("numero_orden"));
				ob.setVariedad(rs.getString("variedad"));
				ob.setIdPrograma(rs.getInt("idPrograma"));
				ob.setIdorden(rs.getInt("idOrden"));
				ob.setCampo(rs.getString("ncampo"));
				ob.setCodCampo(rs.getString("campo"));
				ob.setEstado(rs.getString("estado"));
				ob.setNreserva(rs.getString("nreserva"));
				ob.setSolped(rs.getString("solped"));
				list.add(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {
			db.close();
		}
		return list;
		
	}
	
//	SELECT
	public static LISTA_APLICACIONES_OBJ getDetalleAplicacion(int id) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		LISTA_APLICACIONES_OBJ ob = new LISTA_APLICACIONES_OBJ();
		ConnectionDB db = new ConnectionDB();
		try{  
			sql = "select pf.codigo as numero_orden, oa.codigo, oa.aplicador, oa.jefe_aplicacion, pf.campo, oa.id idOrden, oa.codigo_fa, oa.um, "
					+ " pf.fecha_estimada, e.especie, v.variedad, ef.estado_fenologicos, cm.descripcion ncampo, oa.mercado codMercado, oa.capacidad_maquina, "
					+ "v.fecha_estimada_cosecha, me.descripcion as mercado, fa.descripcion forma_aplicacion, epf.descripcion estado, oa.fecha_viable_cosecha, "
					+ "ma.descripcion as maquinaria, im.descripcion as implemento, oa.cambio_tractor, oa.marcha_tractor, oa.presion_bomba, cm.campos_maq "
					+ "from orden_aplicacion oa inner join programa_fitosanitario pf on oa.codigo_pf = pf.codigo "
					+ "left join especie e on e.codigo = pf.especie "
					+ "left join variedad v on v.codigo = pf.variedad "
					+ "left join campo cm on cm.campo = pf.campo "
					+ "left join estado_fenologico ef on ef.codigo = pf.estado_fenologico "
					+ "left join usuario us on us.codigo = pf.usuario_ja "
					+ "left join forma_aplicacion fa on oa.codigo_fa=fa.codigo "
					+ "left join maquinaria ma on ma.codigo = oa.maquinaria "
					+ "left join implemento im on im.codigo = oa.implemento "
					+ "left join mercado me on me.codigo = oa.mercado "
					+ "left join estado_pf epf on epf.codigo = pf.estado_pf "
					+ "where oa.codigo=" + id; 
//					+ " where campo ='" +filtro.getCampo()+ 
//					"' and  fecha_estimada between '" +filtro.getFecha_desde()+ "' and '" +filtro.getFecha_hasta()+ 
//					"' and temporada='" +filtro.getTemporada()+ "' and estado_pf != 5"
			//System.out.println(sql);   
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				
				ob.setCodigo(rs.getInt("idOrden"));
				ob.setEspecie(rs.getString("especie"));
				ob.setEstado_fenologico(rs.getString("estado_fenologicos"));
				ob.setFecha_estimada_aplicacion(rs.getDate("fecha_estimada"));
				ob.setFecha_estimada_cosecha(rs.getDate("fecha_estimada_cosecha"));
				ob.setForma_aplicacion(rs.getString("forma_aplicacion"));
				ob.setFecha_viable(rs.getString("fecha_viable_cosecha"));
				ob.setMercado(rs.getString("mercado"));
				ob.setNombre_aplicador(rs.getString("aplicador"));
				ob.setNumero_orden(rs.getInt("numero_orden"));
				ob.setVariedad(rs.getString("variedad"));
				ob.setMaquinaria(rs.getString("maquinaria"));
				ob.setImplemento(rs.getString("implemento")); 
				ob.setJefe_aplicacion(rs.getString("jefe_aplicacion"));
				ob.setCodMercado(rs.getString("codMercado"));
				ob.setImplemento(rs.getString("codigo_fa"));
				//ob.lista_materiales = material.getMPF(33);
				//ob.lista_cuarteles = cuartel.getCPF(33);
				ob.setCambio_tractor(rs.getString("cambio_tractor"));
				ob.setMarcha_tractor(rs.getString("marcha_tractor"));
				ob.setPresion_bomba(rs.getString("presion_bomba"));
				ob.setLista_maquinaria(getMaquinariaPF(rs.getInt("codigo")));
				ob.setCampo(rs.getString("ncampo"));
				ob.setCodCampo(rs.getString("campo"));
				ob.setEstado(rs.getString("estado"));
				ob.lista_materiales = material.getMPF(rs.getInt("numero_orden"));
				ob.lista_cuarteles = cuartel.getCPF(rs.getInt("numero_orden"));
				ob.setLista_maquinaria(getMaquinaria(rs.getInt("numero_orden")));
				ob.setCapacidad_maquina(rs.getDouble("capacidad_maquina"));
				ob.setUm(rs.getString("um"));
				ob.setCampos_maq(rs.getString("campos_maq"));
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception e) {
			System.out.println("Error: getDetalleAplicacion" + e.getMessage());
		}finally {
			db.close();
		}
		return ob;
		
	}
	
	public static ArrayList<MAQUINARIA_PF> getMaquinariaPF(int pf) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<MAQUINARIA_PF> lista = new ArrayList<MAQUINARIA_PF>();
		ConnectionDB db = new ConnectionDB();
		try{  
			sql = "select maquinaria, implemento from maquinaria_pf where codigo_pf =" + pf; 
			//System.out.println(sql);   
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				MAQUINARIA_PF ob = new MAQUINARIA_PF();
				ob.setMaquinaria(rs.getInt("maquinaria"));
				ob.setImplemento(rs.getInt("implemento"));
				lista.add(ob);
			} 
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {
			db.close();
		}
		return lista;
		
	}
	
	public static LISTA_APLICACIONES_OBJ getDetalleAplicacionbyOrden(int id) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		LISTA_APLICACIONES_OBJ ob = new LISTA_APLICACIONES_OBJ();
		ConnectionDB db = new ConnectionDB();
		try{  
			sql = "select pf.codigo as numero_orden, pf.especie codEspecie,pf.variedad,pf.estado_fenologico codestado_fenologico, pf.observacion, pf.mojamiento,ca.mojamiento mojamiento_real, "
					+ " pf.tipo_programa, pf.nreserva, pf.solped, oa.id idOrden, fecha_viable_cosecha, pf.programa_aplicacion, "
					+ " pf.fecha_estimada, e.especie nespecie, ef.estado_fenologicos, pa.descripcion nproapli, pf.id idPrograma, "
					+ " tc.control_aplicacion nombretipocontrol, cm.descripcion campo, cm.campo codCampo, oa.codigo_fa codfa,"
					+ " pf.tipo_control codControl, oa.jefe_aplicacion codJefe, oa.mercado codMercado,oa.presion_bomba, oa.marcha_tractor, oa.cambio_tractor, oa.um, "
					+ " oa.codigo codOrden, ja.descripcion jefe_Aplicacion, CONCAT(trA.nombre , ' ', trA.apellidoPaterno) nombre_aplicador, oa.capacidad_maquina, "
					+ " fa.descripcion forma_aplicacion, mer.descripcion mercado, oa.aplicador codAplicador, pf.libro_campo, cm.gerente_zonal "					
					+ " from programa_fitosanitario pf "
					+ " left join campo cm on cm.campo = pf.campo"
					+ " left join especie e on e.codigo = pf.especie "
					+ " left join programa_aplicacion pa on pa.codigo = pf.programa_aplicacion "
					+ " left join control_aplicacion tc on tc.codigo = pf.tipo_control "
					+ " left join variedad v on v.codigo = pf.variedad "
					+ " left join estado_fenologico ef on ef.codigo = pf.estado_fenologico "
					+ " left join usuario us on us.codigo = pf.usuario_ja "	
					+ " left join orden_aplicacion oa on oa.codigo_pf = pf.codigo "
					+ " LEFT JOIN parametros_campo ja ON ja.codigo = oa.jefe_aplicacion "
					+ " LEFT JOIN trabajadores trA ON trA.id = oa.aplicador "
					+ " LEFT JOIN forma_aplicacion fa ON fa.codigo = oa.codigo_fa "
					+ " LEFT JOIN mercado mer ON mer.codigo = oa.mercado "
					+ " LEFT JOIN confirmacion_aplicacion ca on ca.codigo_orden = oa.codigo"
					+ " where pf.codigo=" + id;
//					+ " where campo ='" +filtro.getCampo()+ 
//					"' and  fecha_estimada between '" +filtro.getFecha_desde()+ "' and '" +filtro.getFecha_hasta()+ 
//					"' and temporada='" +filtro.getTemporada()+ "' and estado_pf != 5"
					; 
			System.out.println(sql);   
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				
				ob.setCodigo(rs.getInt("idOrden"));
				ob.setEspecie(rs.getString("nespecie"));
				ob.setEstado_fenologico(rs.getString("estado_fenologicos"));
				ob.setFecha_estimada_aplicacion(rs.getDate("fecha_estimada"));
				//ob.setFecha_estimada_cosecha(rs.getDate("fecha_estimada_cosecha"));
				ob.setNforma_aplicacion(rs.getString("forma_aplicacion"));
				ob.setMercado(rs.getString("mercado"));
				ob.setNombre_aplicador(rs.getString("nombre_aplicador"));
				ob.setNumero_orden(rs.getInt("numero_orden"));
				ob.setCodCampo(rs.getString("codCampo"));
				ob.setIdorden(rs.getInt("codOrden"));
				ob.setFecha_viable(rs.getString("fecha_viable_cosecha"));
				//ob.setVariedad(rs.getString("variedad"));
				//ob.setMaquinaria(rs.getString("maquinaria"));
				//ob.setImplemento(rs.getString("implemento"));
				ob.setJefe_aplicacion(rs.getString("jefe_Aplicacion"));
				ob.setIdPrograma(rs.getInt("idPrograma"));
				ob.setNreserva(rs.getString("nreserva"));
				ob.setIdProgramaAplicacion(rs.getInt("programa_aplicacion"));
				ob.setMojamiento(rs.getInt("mojamiento"));
				ob.setCampo(rs.getString("campo"));
				ob.lista_materiales = material.getMPF(id);
				ob.lista_cuarteles = cuartel.getCPF(id);
				ob.setLista_maquinaria(getMaquinaria(id));
				ob.programa_aplicacion = rs.getString("nproapli");
				ob.tipo_control = rs.getString("nombretipocontrol");
				ob.setTipo_programa(rs.getInt("tipo_programa"));
				ob.setObservacion(rs.getString("observacion"));
				ob.setSolped(rs.getString("solped"));
				ob.setCapacidad_maquina(rs.getDouble("capacidad_maquina"));
				ob.setMojamiento_real(rs.getDouble("mojamiento_real"));
				ob.setCodEspecie(rs.getString("codEspecie"));
				ob.setVariedad(rs.getString("variedad"));
				ob.setCodEstadoFenologico(rs.getString("codestado_fenologico"));
				ob.setCodControl(rs.getString("codControl"));
				ob.setCodJefeAplicacion(rs.getString("codJefe"));
				ob.setCodNombreAplicador(rs.getString("codAplicador"));
				ob.setCodMercado(rs.getString("codMercado"));
				ob.setForma_aplicacion(rs.getString("forma_aplicacion"));
				ob.setPresion_bomba(rs.getString("presion_bomba"));
				ob.setUm(rs.getString("um"));
				ob.setCambio_tractor(rs.getString("cambio_tractor"));
				ob.setMarcha_tractor(rs.getString("marcha_tractor"));
				ob.setCodLibro(rs.getString("libro_campo"));
				ob.setAdm_campo(rs.getString("adm_campo"));
				
			} 
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {
			db.close();
		}
		return ob;
		
	}
	
	public static LISTA_APLICACIONES_OBJ getDetalleAplicacionbyOrden_report(int id) throws Exception{
		LISTA_APLICACIONES_OBJ detalle = new LISTA_APLICACIONES_OBJ();
		try{  
			detalle = getDetalleAplicacionbyOrden(id);
			
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return detalle;
		
	}
	
	public static LISTA_APLICACIONES_OBJ getDetalleAplicacionbyOrden2(int id) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		LISTA_APLICACIONES_OBJ ob = new LISTA_APLICACIONES_OBJ();
		ConnectionDB db = new ConnectionDB();
		try{  
			sql = "select pf.codigo as numero_orden, pf.observacion, pf.mojamiento,ca.mojamiento mojamiento_real, pf.tipo_programa, pf.nreserva, pf.solped, oa.id idOrden, fecha_viable_cosecha,  "
					+ " ca.fecha_inicio fecha_estimada, e.especie nespecie, ef.estado_fenologicos, pa.descripcion nproapli, pf.id idPrograma, "
					+ " pf.programa_aplicacion, tc.control_aplicacion nombretipocontrol, cm.descripcion campo, cm.campo codCampo, "
					+ " oa.codigo codOrden, ja.descripcion jefe_Aplicacion, CONCAT(trA.nombre , ' ', trA.apellidoPaterno) nombre_aplicador, oa.capacidad_maquina, ca.velocidad_viento, ca.orientacion_viento, ca.temperatura, "
					+ " fa.descripcion forma_aplicacion, mer.descripcion mercado, cm.adm_campo,ca.fecha_termino "					
					+ " from programa_fitosanitario pf "
					+ " left join campo cm on cm.campo = pf.campo"
					+ " left join especie e on e.codigo = pf.especie "
					+ " left join programa_aplicacion pa on pa.codigo = pf.programa_aplicacion "
					+ " left join control_aplicacion tc on tc.codigo = pf.tipo_control "
					+ " left join variedad v on v.codigo = pf.variedad "
					+ " left join estado_fenologico ef on ef.codigo = pf.estado_fenologico "
					+ " left join usuario us on us.codigo = pf.usuario_ja "	
					+ " left join orden_aplicacion oa on oa.codigo_pf = pf.codigo "
					+ " LEFT JOIN parametros_campo ja ON ja.codigo = oa.jefe_aplicacion "
					+ " LEFT JOIN trabajadores trA ON trA.id = oa.aplicador "
					+ " LEFT JOIN forma_aplicacion fa ON fa.codigo = oa.codigo_fa "
					+ " LEFT JOIN mercado mer ON mer.codigo = oa.mercado "
					+ " LEFT JOIN confirmacion_aplicacion ca on ca.codigo_orden = oa.codigo "
					+ " where pf.codigo=" + id;
//					+ " where campo ='" +filtro.getCampo()+ 
//					"' and  fecha_estimada between '" +filtro.getFecha_desde()+ "' and '" +filtro.getFecha_hasta()+ 
//					"' and temporada='" +filtro.getTemporada()+ "' and estado_pf != 5"
					; 
			System.out.println(sql);   
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				
				ob.setCodigo(rs.getInt("idOrden"));
				ob.setEspecie(rs.getString("nespecie"));
				ob.setEstado_fenologico(rs.getString("estado_fenologicos"));
				ob.setFecha_estimada_aplicacion(rs.getDate("fecha_estimada"));
				//ob.setFecha_estimada_cosecha(rs.getDate("fecha_estimada_cosecha"));
				ob.setForma_aplicacion(rs.getString("forma_aplicacion"));
				ob.setMercado(rs.getString("mercado"));
				ob.setNombre_aplicador(rs.getString("nombre_aplicador"));
				ob.setNumero_orden(rs.getInt("numero_orden"));
				ob.setCodCampo(rs.getString("codCampo"));
				ob.setIdorden(rs.getInt("codOrden"));
				ob.setFecha_viable(rs.getString("fecha_viable_cosecha"));
				//ob.setVariedad(rs.getString("variedad"));
				//ob.setMaquinaria(rs.getString("maquinaria"));
				//ob.setImplemento(rs.getString("implemento"));
				ob.setJefe_aplicacion(rs.getString("jefe_Aplicacion"));
				ob.setIdPrograma(rs.getInt("idPrograma"));
				ob.setNreserva(rs.getString("nreserva"));
				ob.setIdProgramaAplicacion(rs.getInt("programa_aplicacion"));
				ob.setMojamiento(rs.getInt("mojamiento"));
				ob.setCampo(rs.getString("campo"));
				ob.lista_materiales = material.getMPF(id);
				ob.lista_cuarteles = cuartel.getCPF(id);
				ob.setLista_maquinaria(getMaquinaria(id));
				ob.programa_aplicacion = rs.getString("nproapli");
				ob.tipo_control = rs.getString("nombretipocontrol");
				ob.setTipo_programa(rs.getInt("tipo_programa"));
				ob.setObservacion(rs.getString("observacion"));
				ob.setSolped(rs.getString("solped"));
				ob.setCapacidad_maquina(rs.getDouble("capacidad_maquina"));
				ob.setMojamiento_real(rs.getInt("mojamiento_real"));
				ob.setAdm_campo(rs.getString("adm_campo"));
				ob.setVelocidad_viento(rs.getString("velocidad_viento"));
				ob.setOrientacion_viento(rs.getString("orientacion_viento"));
				ob.setTemperatura(rs.getString("temperatura"));
				ob.setFecha_termino(rs.getString("fecha_termino"));
			} 
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {
			db.close();
		}
		return ob;
		
	}
	
	public static ArrayList<MAQUINARIA_PF> getMaquinaria (int codigo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<MAQUINARIA_PF> lista = new ArrayList<MAQUINARIA_PF>();
		ConnectionDB db =  new ConnectionDB();
		try {
			sql = "SELECT mpf.*, concat(tr.nombre, ' ', tr.apellidoPaterno) nresponsable "
					+ "FROM maquinaria_pf mpf "
					+ "left join trabajadores tr on tr.id = mpf.responsable "
					+ "where mpf.codigo_pf=" +codigo;
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				MAQUINARIA_PF ob = new MAQUINARIA_PF();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setMaquinaria(rs.getInt("maquinaria"));
				ob.setImplemento(rs.getInt("implemento"));
				ob.setResponsable(rs.getString("responsable"));
				ob.setPresion(rs.getString("presion_bomba"));
				ob.setVelocidad(rs.getString("velocidad_tractor"));
				ob.setMarcha(rs.getString("marcha_tractor"));
				ob.setCambio(rs.getString("cambio_tractor"));
				ob.setNresponsable(rs.getString("nresponsable"));
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
//	public static boolean insertPF (PROGRA_FITOSANITARIO c) throws Exception{ 
//		PreparedStatement ps = null;
//		String sql = "";
//		ConnectionDB db = new ConnectionDB();
//		try {
//			sql = "INSERT INTO programa_fitosanitario (fecha_estimada, fecha_alerta, tipo_control, estado_pf, "
//					+ "programa_aplicacion, usuario, usuario_ja, observacion, temporada, campo, especie, variedad,"
//					+ " estado_fenologico, mojamiento)";
//			sql += "VALUES "
//					+"('"+c.getFecha_estimada() +"', '"+c.getFecha_alerta()+"', "
//					+"'"+c.getTipo_control()+"', '"+c.getEstado_pf()+"', '"
//					+c.getPrograma_aplicacion()+"', '"+c.getUsuario()+"', '"+c.getUsuario_ja()+"', '"
//					+c.getObservacion()+"', '"+c.getTemporada()+"', '"+c.getCampo()+"', '"+c.especie+"', '"
//					+c.getVariedad()+"', '"+c.getEstado_fenologico()+"', '"+c.getMojamiento()+"')";
//
//ps = db.conn.prepareStatement(sql);
//			ps.execute();
//			String sql2 = "";
//			sql2 = "SELECT MAX(codigo) as codigo from programa_fitosanitario";
//			ResultSet idNew = ps.executeQuery(sql2);
//			int codPF = 0;
//			System.out.println(idNew);
//			while (idNew.next()) { 
//				codPF = idNew.getInt("codigo"); 
//			}
//			
//			cuartel.deleteCPF(codPF);
//			for(CUARTEL_PF cpf: c.getCuart_PF()){
//				cpf.setCodigo_pf(codPF);
//				cuartel.insertCPF(cpf);
//			}
//			
//			material.deleteMPF(codPF);
//
//for(MATERIAL_PF mpf: c.getMater_PF()){
//				mpf.setCodigo_pf(codPF);
//				material.insertMPF(mpf);
//			}
//			notificacion n = new notificacion();
//			n.setCodigo_tarea(codPF);
//			n.setTipo(1);
//			n.setUsuario_origen(1);
//			n.setUsuario_receptor(c.getUsuario_ja());
//			n.setFecha_alerta(c.getFecha_alerta());
//			n.setEstado(0);			
//			NOTIFICACIONES.insertN(n);
//			return true;	
//		} catch (SQLException e) {
//			System.out.println("Error: " + e.getMessage());
//		}catch (Exception e) {
//			System.out.println("Error: " + e.getMessage());
//		}finally {
//			ps.close();
//			db.close();
//		}
//		
//		return false;
//	}
////	UPDATE
//	public static boolean updatePF(PROGRA_FITOSANITARIO c) throws  Exception{
//		PreparedStatement ps = null;
//		String sql = "";
//		ConnectionDB  db = new ConnectionDB();	
//		try {
//			sql = "Update programa_fitosanitario set "
//				+ "estado_fenologico= '"+ c.getEstado_fenologico()+"', "
//				+ "mojamiento='" +c.getMojamiento()+ "', fecha_estimada='" +c.getFecha_estimada()+ "', "
//				+ "fecha_alerta='" +c.getFecha_alerta()+ "', programa_aplicacion='" +c.getPrograma_aplicacion()+ "', "
//				+ "tipo_control='" +c.getTipo_control()+ "', observacion='" +c.getObservacion()+ "' where codigo = '"+c.getCodigo()+"'";  
//			ps = db.conn.prepareStatement(sql);
//			ps.execute();
//			
//			int codPF = c.getCodigo();
//			cuartel.deleteCPF(codPF);
//			for(CUARTEL_PF cpf: c.getCuart_PF()){
//				cpf.setCodigo_pf(codPF);
//				cuartel.insertCPF(cpf);
//			}
//			material.deleteMPF(codPF);
//			for(MATERIAL_PF mpf: c.getMater_PF()){
//				mpf.setCodigo_pf(codPF);
//				material.insertMPF(mpf);
//			}			
//			return true;
//		} catch (SQLException e) {
//			System.out.println("Error:" + e.getMessage());
//			e.printStackTrace();
//		}catch (Exception e) {
//			System.out.println("Error: " + e.getMessage());
//		}finally {
//			ps.close();
//			db.close();
//		}		
//		return false;
//	}
//	------------FIN PROGRAMA FITOSANITARIO-----------------
	
	
}

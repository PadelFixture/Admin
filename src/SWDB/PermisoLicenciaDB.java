package SWDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import lib.classSW.tablaPermisoLicencia;
import lib.db.ConnectionDB;

public class PermisoLicenciaDB {
	
	///cargar tabla pantalla permiso licencia
	public static ArrayList<tablaPermisoLicencia> getTablaPL(String codigo,int idAccion,int idEmpresa,String huerto, String zona, String ceco ) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<tablaPermisoLicencia> data = new ArrayList<tablaPermisoLicencia>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="select p.id,"
			   + "id_empresa,"
			   + "codigo_trabajador,"
			   + "(select nombre from trabajadores where codigo = codigo_trabajador)as nombretrab,"
			   + "(select apellidoPaterno from trabajadores where codigo = codigo_trabajador)as appaterno,"
			   + "(select apellidoMaterno from trabajadores where codigo = codigo_trabajador)as apmaterno,"
			   + "accion,"
			   + "(select descripcion from parametros where id = tipo_licencia) as tipo_licencia,"
			   + "(select descripcion from parametros where id = subtipo_licencia) as subtipo_licencia,"
			   + "(select descripcion from parametros where id = reposo) as nombre_reposo,"
			   + "reposo,"
			   + "incluye_feriados,"
			   + "fecha_desde,"
			   + "fecha_hasta,"
			   + "fecha_creacion,"
			   + "horas_inasistencia,"
			   + "dias_corridos,"
			   + "reposo,"
			   + "tipo_reposo,"
			   + "doctor,"
			   + "especialidad,"
			   + "tipo_licencia AS TipoLicenciaId,"
			   + "subtipo_licencia AS subTipoLicenciaId,"
			   + "ruta_archivo "
			   + "from permiso_licencia p "
			   + "INNER JOIN trabajadores tr on tr.codigo = p.codigo_trabajador where accion = "+idAccion+" ";
			   if("null".equals(codigo)){}else{sql += "and codigo_trabajador = "+codigo+" ";}
			   if("null".equals(huerto)){}else{sql += " and tr.idHuerto = '"+huerto+"' ";}
			   if("null".equals(zona)){}else{sql += " and tr.idZona = '"+zona+"' ";}
			   if("null".equals(ceco)){}else{sql += " and tr.idCECO = '"+ceco+"' ";}
			   sql+= " and id_empresa ="+idEmpresa+" ORDER BY p.id DESC";
			
		
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				tablaPermisoLicencia e = new tablaPermisoLicencia();
				
				e.setId_empresa(rs.getInt("id_empresa"));
				e.setCodigo_trabajador(rs.getInt("codigo_trabajador"));
				e.setAccion(rs.getInt("accion"));
				e.setTipo_licencia(rs.getString("tipo_licencia"));
				e.setSubtipo_licencia(rs.getString("subtipo_licencia"));
				e.setIncluye_feriados(rs.getInt("incluye_feriados"));
				e.setFecha_desde(rs.getString("fecha_desde"));
				e.setFecha_hasta(rs.getString("fecha_hasta"));
				e.setFecha_creacion(rs.getString("fecha_creacion"));
				e.setHoras_inasistencia(rs.getInt("horas_inasistencia"));
				e.setDias_corridos(rs.getInt("dias_corridos"));
				e.setRuta_archivo(rs.getString("ruta_archivo"));
				e.setNombre_reposo(rs.getString("nombre_reposo"));
				e.setId(rs.getInt("id"));
				e.setSubtipo_licenciaid(rs.getInt("subTipoLicenciaId"));
				e.setTipo_licenciaid(rs.getInt("TipoLicenciaId"));
				e.setReposo(rs.getInt("reposo"));
				e.setDoctor(rs.getString("doctor"));
				e.setEspecialidad(rs.getString("especialidad"));
				e.setTipo_reposo(rs.getInt("tipo_reposo"));
				e.setNombrecompleto(rs.getString("appaterno")+" "+rs.getString("apmaterno")+" "+rs.getString("nombretrab"));
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
	
	
	

}

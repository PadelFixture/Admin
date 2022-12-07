package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.DOTACION_DIARIA;
import lib.db.ConnectionDB;

public class DotacionDiaria {

	public static ArrayList<DOTACION_DIARIA> GET_DotacionDiaria(DOTACION_DIARIA row) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
//		String[] campoArr = row.getDescripcion().split("-");
		ArrayList<DOTACION_DIARIA> lista = new ArrayList<DOTACION_DIARIA>();
		ConnectionDB db = new ConnectionDB();
		try {
//			String sqlCampos = "(";
//			int c = 0;
//			for(int i = 0; i< campoArr.length; i++){
//				
//				c++;
//				if(c == campoArr.length){
//					sqlCampos += campoArr[i];
//				}else{
//					sqlCampos += campoArr[i]+",";
//				}
//			}
//			sqlCampos += ")";
			sql = " SELECT ct.codigo_cuadrilla, ct.asistencia, ct.motivo, e.especie,"
				+ " v.variedad, c.nombre, f.faena, l.labor , ca.descripcion,"
				+ " count(distinct(ct.rut_trabajador)) as CantidadTrabajadores,"
				+ " t.nombre as trabajador, t.apellidoPaterno, t.apellidoMaterno,"
				+ " sum(ct.motivo = 2)as licencia, sum(ct.motivo = 1) as permiso,"
				+ " sum(ct.motivo = 3)as falta"
				+ " FROM cuadrilla_trabajador ct "
				+ " left join rendimiento_general rg on ct.codigo_cuadrilla = rg.codigo_cuadrilla "
				+ " left join rendimiento_diario rd on rg.codigo_rg = rd.codigo_rg "
				+ " left join trabajadores t on rd.trabajador = t.codigo "
                + " left join especie e on rg.especie = e.codigo "
                + " left join variedad v on rg.variedad = v.codigo "
                + " left join faena f on rg.faena = f.codigo "
                + " left join labor l on rg.labor = l.codigo "
				+ " left join cuartel c on rg.cuartel = c.codigo "
                + " left join sector s on c.sector = s.sector "
                + " left join campo ca on s.campo = ca.campo "
                + " where ca.campo ='"+row.getDescripcion()+"' and rg.labor = '"+row.getLabor()+"'"
//              + " where ca.campo in "+sqlCampos+" and rg.labor = '"+row.getLabor()+"'"
        		+ " and rg.faena='"+row.getFaena()+"' and fecha='"+row.getFecha()+"'"
				+ " and rd.trabajador = t.codigo"
                + " group by  ct.codigo_cuadrilla, ct.asistencia, ct.motivo, e.especie, v.variedad,"
                + " c.nombre, f.faena, l.labor, ca.descripcion, t.nombre , t.apellidoPaterno, t.apellidoMaterno"
                + " order by ca.descripcion, e.especie, v.variedad, c.nombre, f.faena, l.labor";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				DOTACION_DIARIA ob = new DOTACION_DIARIA();
				ob.setCodigo_cuadrilla(rs.getInt("codigo_cuadrilla"));
				ob.setEspecie(rs.getString("especie"));
				ob.setVariedad(rs.getString("variedad"));
				ob.setNombre(rs.getString("nombre"));
				ob.setFaena(rs.getString("faena"));
				ob.setLabor(rs.getString("labor"));
				ob.setAsistencia(rs.getInt("asistencia"));
				ob.setMotivo(rs.getInt("motivo"));
				ob.setDescripcion(rs.getString("descripcion"));
				ob.setCantidadTrabajadores(rs.getInt("CantidadTrabajadores"));
				ob.setTrabajador(rs.getString("trabajador"));
				ob.setApellidoPaterno(rs.getString("apellidoPaterno"));
				ob.setApellidoMaterno(rs.getString("apellidoMaterno"));
				ob.setPermiso(rs.getInt("permiso"));
				ob.setLicencia(rs.getInt("licencia"));
				ob.setFalta(rs.getInt("falta"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();	
		} catch (SQLException e) {
			System.out.println("Error" + e.getMessage());
		}catch (Exception e) {
			System.out.println("Error" + e.getMessage());
		}
		return lista;
	}	
}

package SWDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



import lib.classSW.Representanteslegales;
import lib.classSW.TurnosEmpresa;
import lib.db.ConnectionDB;

public class sw_MantenedorRepresentantesDB {
	
	public static ArrayList<Representanteslegales> getrepresentantes(String huerto,String soci) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<Representanteslegales> data = new ArrayList<Representanteslegales>();
		ConnectionDB db = new ConnectionDB();
		
		try{
			
			sql = "select "
					+ "UPPER((select denominacionSociedad from sociedad  where sociedad = ca.sociedad)) as nombresociedad,"
					+ "UPPER( ca.descripcion) AS Nombre_campo,"
					+ "UPPER(ca.direccion_huerto) as direccion_huerto,"
					+ "CASE WHEN ca.representante_legal_nombre is null THEN ' ' ELSE UPPER(ca.representante_legal_nombre) END representante_legal_nombre,"
					+ "CASE WHEN ca.representante_legal_apPaterno is null THEN ' ' ELSE UPPER(ca.representante_legal_apPaterno) END representante_legal_apPaterno,"
					+ "CASE WHEN ca.representante_legal_apMaterno is null THEN ' ' ELSE UPPER(ca.representante_legal_apMaterno) END representante_legal_apMaterno,"
					+ "CASE WHEN ca.representante_legal_rut is null THEN ' ' ELSE UPPER(ca.representante_legal_rut) END representante_legal_rut,"
					+ "CASE WHEN ca.numero_telefono is null THEN ' ' ELSE UPPER(ca.numero_telefono) END numero_telefono,"
					+ "CASE WHEN ca.ciudad_huerto is null THEN ' ' ELSE UPPER(ca.ciudad_huerto) END ciudad_huerto,"
					+ "CASE WHEN ca.comuna_huerto is null THEN ' ' ELSE UPPER(ca.comuna_huerto) END comuna_huerto,"
					+ "CASE WHEN ca.centro_costo_previred is null THEN ' ' ELSE ca.centro_costo_previred END centro_costo_previred,"
					+ "ca.codigo "
					+ "from campo ca where  1 = 1 ";
					
					if("null".equals(soci)){}else{sql += " and ca.sociedad =  (select sociedad from sociedad where idSociedad = '"+soci+"')";}
			        if("null".equals(huerto)){}else{sql += " and ca.campo = '"+huerto+"'";}
			
			
			System.out.println(sql);
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				Representanteslegales e = new Representanteslegales();
				e.setDireccion(rs.getString("direccion_huerto"));
				e.setNombre(rs.getString("representante_legal_nombre"));
				e.setApPaterno(rs.getString("representante_legal_apPaterno"));
				e.setApMaterno(rs.getString("representante_legal_apMaterno"));
				e.setRut(rs.getString("representante_legal_rut"));
				e.setNumeroTelefono(rs.getString("numero_telefono"));
				e.setCiudadHuerto(rs.getString("ciudad_huerto"));
				e.setComunaHuerto(rs.getString("comuna_huerto"));
				e.setCentroCostoPrevired(rs.getString("centro_costo_previred"));
				e.setCodigoLinea(rs.getInt("codigo"));
				e.setNombreEmpresa(rs.getString("nombresociedad"));
				e.setNombrehuerto(rs.getString("Nombre_campo"));
				
				
				
				
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

	public static boolean updateRepresentantesLegales(Representanteslegales r) throws  Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		try {

			sql = "Update campo set "
					+ "direccion_huerto= '"+r.getDireccion()+"', "
					+ "representante_legal_nombre= '"+r.getNombre()+"',"
					+ "representante_legal_apPaterno= '"+r.getApPaterno()+"',"
					+ "representante_legal_apMaterno= '"+r.getApMaterno()+"',"
					+ "representante_legal_rut= '" +r.getRut()+ "',"	
					+ "numero_telefono= '"+r.getNumeroTelefono()+"',"	
					+ "ciudad_huerto= '" +r.getCiudadHuerto()+ "',"	
					+ "comuna_huerto= '" +r.getComunaHuerto()+ "',"
					+ "centro_costo_previred= '" +r.getCentroCostoPrevired()+ "' "	
					+ " where codigo = "+r.getCodigoLinea()+"";  
				ps = db.conn.prepareStatement(sql);
				System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.execute();
					
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return false;
	}
	
	
}

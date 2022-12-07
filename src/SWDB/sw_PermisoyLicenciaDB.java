package SWDB;

import java.sql.PreparedStatement;

import com.sun.org.apache.bcel.internal.generic.GETFIELD;

import lib.classSW.tablaPermisoLicencia;
import lib.db.ConnectionDB;

public class sw_PermisoyLicenciaDB {
	
	//-----------Update FALTAS-----------------------------------------
	public static boolean UpdateFaltas(tablaPermisoLicencia row) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "UPDATE permiso_licencia SET fecha_desde = ?, fecha_hasta = ?, dias_corridos = ?, incluye_feriados = ? WHERE id= ?";
			ps = db.conn.prepareStatement(sql);

			ps.setString(1,row.getFecha_desde());
			ps.setString(2,row.getFecha_hasta());
			ps.setInt(3,row.getDias_corridos());
			ps.setInt(4, row.getIncluye_feriados());
			ps.setInt(5, row.getId());
			
			System.out.println(sql);
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
	//-----------Update LICENCIAS-----------------------------------------
		public static boolean UpdateLicencias(tablaPermisoLicencia row) throws Exception{
			PreparedStatement ps = null;
			String sql = "";
			String rutaver = row.getRuta_archivo();
		
			ConnectionDB db = new ConnectionDB();
			try{
				
				if(rutaver == null){
					sql = "UPDATE permiso_licencia SET"
							+ " tipo_licencia = ?,"
							+ " subtipo_licencia = ?,"
							+ " fecha_desde = ?,"
							+ " fecha_hasta = ?,"
							+ " dias_corridos = ?,"
							+ " incluye_feriados = ?,"
							+ " reposo = ?,"
							+ " doctor = ?,"
							+ " especialidad = ?,"
							+ " tipo_reposo = ? WHERE id= ?";
					ps = db.conn.prepareStatement(sql);

					ps.setInt(1,row.getTipo_licenciaid());
					ps.setInt(2,row.getSubtipo_licenciaid());
					ps.setString(3,row.getFecha_desde());
					ps.setString(4, row.getFecha_hasta());
					ps.setInt(5,row.getDias_corridos());
					ps.setInt(6,row.getIncluye_feriados());
					ps.setInt(7,row.getReposo());
					ps.setString(8, row.getDoctor());
					ps.setString(9, row.getEspecialidad());
					ps.setInt(10,row.getTipo_reposo());
					ps.setInt(11, row.getId());
				}else{
					sql = "UPDATE permiso_licencia SET"
							+ " tipo_licencia = ?,"
							+ " subtipo_licencia = ?,"
							+ " fecha_desde = ?,"
							+ " fecha_hasta = ?,"
							+ " dias_corridos = ?,"
							+ " incluye_feriados = ?,"
							+ " reposo = ?,"
							+ " ruta_archivo = ?,"
							+ " doctor = ?,"
							+ " especialidad = ?,"
							+ " tipo_reposo = ? WHERE id= ?";
					ps = db.conn.prepareStatement(sql);

					ps.setInt(1,row.getTipo_licenciaid());
					ps.setInt(2,row.getSubtipo_licenciaid());
					ps.setString(3,row.getFecha_desde());
					ps.setString(4, row.getFecha_hasta());
					ps.setInt(5,row.getDias_corridos());
					ps.setInt(6,row.getIncluye_feriados());
					ps.setInt(7,row.getReposo());
					ps.setString(8, row.getRuta_archivo());
					ps.setString(9, row.getDoctor());
					ps.setString(10, row.getEspecialidad());
					ps.setInt(11,row.getTipo_reposo());
					ps.setInt(12, row.getId());
				}
			
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
		
		//-----------Update PERMISO SIN GOCE-----------------------------------------
				public static boolean UpdatePermisoSinGoce(tablaPermisoLicencia row) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					String rutaver = row.getRuta_archivo();
				
					ConnectionDB db = new ConnectionDB();
					try{
						
						if(rutaver == null){
							sql = "UPDATE permiso_licencia SET"
									+ " fecha_desde = ?,"
									+ " fecha_hasta = ?,"
									+ " dias_corridos = ?,"
									+ " incluye_feriados = ?"
									+ " WHERE id= ?";
							ps = db.conn.prepareStatement(sql);

						
							ps.setString(1,row.getFecha_desde());
							ps.setString(2, row.getFecha_hasta());
							ps.setInt(3,row.getDias_corridos());
							ps.setInt(4,row.getIncluye_feriados());
							ps.setInt(5, row.getId());
									
						}else{
							sql = "UPDATE permiso_licencia SET"
									+ " fecha_desde = ?,"
									+ " fecha_hasta = ?,"
									+ " dias_corridos = ?,"
									+ " incluye_feriados = ?,"
									+ " ruta_archivo = ?"
									+ " WHERE id= ?";
							ps = db.conn.prepareStatement(sql);

							ps.setString(1,row.getFecha_desde());
							ps.setString(2, row.getFecha_hasta());
							ps.setInt(3,row.getDias_corridos());
							ps.setInt(4,row.getIncluye_feriados());
							ps.setString(5, row.getRuta_archivo());
							ps.setInt(6, row.getId());
							
							
						
						}
					System.out.println(sql);
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
				
				//-----------Update PERMISO CON GOCE-----------------------------------------
				public static boolean UpdatePermisoConGoce(tablaPermisoLicencia row) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					String rutaver = row.getRuta_archivo();
				
					ConnectionDB db = new ConnectionDB();
					try{
						
						if(rutaver == null){
							sql = "UPDATE permiso_licencia SET"
									+ " fecha_desde = ?,"
									+ " fecha_hasta = ?,"
									+ " dias_corridos = ?,"
									+ " incluye_feriados = ?"
									+ " WHERE id= ?";
							ps = db.conn.prepareStatement(sql);

						
							ps.setString(1,row.getFecha_desde());
							ps.setString(2, row.getFecha_hasta());
							ps.setInt(3,row.getDias_corridos());
							ps.setInt(4,row.getIncluye_feriados());
							ps.setInt(5, row.getId());
									
						}else{
							sql = "UPDATE permiso_licencia SET"
									+ " fecha_desde = ?,"
									+ " fecha_hasta = ?,"
									+ " dias_corridos = ?,"
									+ " incluye_feriados = ?,"
									+ " ruta_archivo = ?"
									+ " WHERE id= ?";
							ps = db.conn.prepareStatement(sql);

							ps.setString(1,row.getFecha_desde());
							ps.setString(2, row.getFecha_hasta());
							ps.setInt(3,row.getDias_corridos());
							ps.setInt(4,row.getIncluye_feriados());
							ps.setString(5, row.getRuta_archivo());
							ps.setInt(6, row.getId());
							
							
						
						}
					System.out.println(sql);
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

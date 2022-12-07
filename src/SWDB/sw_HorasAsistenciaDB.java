package SWDB;

import java.io.File;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lib.classSW.CargarTipodePago;
import lib.classSW.DatosTrabajadorFiniquito;
import lib.classSW.HorasAsistencia;
import lib.classSW.LoadTrabajadorSociedad;
import lib.classSW.NominaAnticipos;
import lib.classSW.UpdateTrabajadorHD;
import lib.db.ConnectionDB;
import lib.struc.trabajadores;

public class sw_HorasAsistenciaDB {

	// ---------------------------------------------------------------------------------
	public static String insertarHoraLicencia(HorasAsistencia r, int agro) throws Exception {

		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		String sql = "";
		String sql2 = "";
		String sql3 = "";
		String respuesta = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<HorasAsistencia> lista = new ArrayList<HorasAsistencia>();
		try {
			System.out.println(agro + " agro es");
			if (agro == 1) {
				sql2 = "SELECT sum(nHoras) as horas,fecha,codTrabajador FROM SAN_CLEMENTE.sw_horasAsistencia where codTrabajador = "
						+ r.getCod_trabajador() + " and fecha = '" + r.getFecha() + "' group by  idContrato";
				System.out.println(sql2);
				ps2 = db.conn.prepareStatement(sql2);
				ResultSet rs2 = ps2.executeQuery(sql2);

				int existeData = 0;
				if (!rs2.isBeforeFirst()) {

					sql = "INSERT INTO sw_horasAsistencia (codTrabajador, idContrato, concepto, nHoras, periodo, fecha) VALUES (?,?,?,?,?,?)";
					ps = db.conn.prepareStatement(sql);

					ps.setInt(1, r.getCod_trabajador());
					ps.setInt(2, r.getId_contrato());
					ps.setInt(3, r.getConcepto());
					ps.setDouble(4, r.getHora());
					ps.setInt(5, r.getPeriodo());
					ps.setString(6, r.getFecha());

					System.out.println(ps);
					ps.execute();

					respuesta = "Enviado";

				} else {
					while (rs2.next()) {
						HorasAsistencia cr = new HorasAsistencia();

						cr.setHora(rs2.getDouble("horas"));
						cr.setFecha(rs2.getString("fecha"));
						cr.setCod_trabajador(rs2.getInt("codTrabajador"));
						lista.add(cr);
					}

					double totalhora = lista.get(0).getHora() + r.getHora();

					if (totalhora > 9) {

						String[] fechaSplit = lista.get(0).getFecha().split("-");
						String fechaFormat = fechaSplit[2] + "-" + fechaSplit[1] + "-" + fechaSplit[0];
						respuesta = "El Trabajador N° " + lista.get(0).getCod_trabajador() + " A la Fecha "
								+ fechaFormat + " ya Tiene Ingresadas " + lista.get(0).getHora() + " Horas"
								+ "<br> Registro No Ingresado Supera el Limite de 9 Horas";
					} else {
						sql = "INSERT INTO sw_horasAsistencia (codTrabajador, idContrato, concepto, nHoras, periodo, fecha) VALUES (?,?,?,?,?,?)";
						ps = db.conn.prepareStatement(sql);
						System.out.println(r.getCod_trabajador());
						System.out.println(sql);

						ps.setInt(1, r.getCod_trabajador());
						ps.setInt(2, r.getId_contrato());
						ps.setInt(3, r.getConcepto());
						ps.setDouble(4, r.getHora());
						ps.setInt(5, r.getPeriodo());
						ps.setString(6, r.getFecha());

						ps.execute();

						respuesta = "Enviado";
					}
				} // end else
			} // end if si es agro
			else {

				sql3 = "INSERT INTO sw_horasAsistencia (codTrabajador, idContrato, concepto, nHoras, periodo, fecha) VALUES (?,?,?,?,?,?)";
				ps3 = db.conn.prepareStatement(sql3);

				ps3.setInt(1, r.getCod_trabajador());
				ps3.setInt(2, r.getId_contrato());
				ps3.setInt(3, r.getConcepto());
				ps3.setDouble(4, r.getHora());
				ps3.setInt(5, r.getPeriodo());
				ps3.setString(6, r.getFecha());

				ps3.execute();

				respuesta = "Enviado";

			} // end else si mo es agro

			return respuesta;
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {

			db.close();
		}
		return "no";
	}

	public static ArrayList<LoadTrabajadorSociedad> getallTrabajaCodNomHorasAsistencia(String empr, String div,
			String subdiv, String gru, String concepto, String periodo,String estado_proceso, int rolPrivado) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<LoadTrabajadorSociedad> data = new ArrayList<LoadTrabajadorSociedad>();
		ConnectionDB db = new ConnectionDB();

		try {

			sql = "select  TR.codigo,TR.nombre,TR.apellidoPaterno,TR.apellidoMaterno,TR.rut from contratos CO  inner join trabajadores TR on TR.codigo = CO.codigo_trabajador join sw_horasAsistencia sw on sw.codTrabajador = CO.codigo_trabajador where 1 = 1 ";

			if ("null".equals(empr)) {
			} else {
				sql += "and CO.idSociedad = " + empr + "";
			}
			if ("null".equals(div)) {
			} else {
				sql += " and TR.idHuerto = '" + div + "'";
			}
			if ("null".equals(subdiv)) {
			} else {
				sql += " and TR.idZona = '" + subdiv + "'";
			}
			if ("null".equals(gru)) {
			} else {
				sql += " and TR.idCECO = '" + gru + "'";
			}
			if ("null".equals(concepto)) {
			} else {
				sql += " and sw.concepto = " + concepto + "";
			}
			if ("null".equals(periodo)) {
			} else {
				sql += " and sw.periodo = " + periodo + "";
			}
			
			if ("null".equals(estado_proceso)) {
			} else {
				sql += " and TR.estadoProceso = " + estado_proceso + "";
			}
			
			if(rolPrivado == 1){
				sql += " and  TR.rolPrivado in (1,0) ";
			}else{
				sql += " and  TR.rolPrivado in (0) ";
			}

			sql += " and CO.EstadoContrato =  1 group by codigo,nombre,apellidoPaterno,apellidoMaterno";

			System.out.println(sql);

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				LoadTrabajadorSociedad e = new LoadTrabajadorSociedad();

				e.setCodigotrabajador(rs.getInt("codigo"));
				e.setNombre(rs.getString("nombre"));
				e.setApellidoPaterno(rs.getString("apellidoPaterno"));
				e.setApellidoMaterno(rs.getString("apellidoMaterno"));
				e.setRut(rs.getString("rut"));

				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			db.close();
		}
		return data;
	}

	public static ArrayList<LoadTrabajadorSociedad> getselectBuscarHorasAsistencia(String soc, String cod, String div,
			String subdiv, String gru, String concepto, String periodo,String estado_proceso) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<LoadTrabajadorSociedad> data = new ArrayList<LoadTrabajadorSociedad>();
		ConnectionDB db = new ConnectionDB();

		try {

			sql = "select  TR.codigo,TR.nombre,TR.apellidoPaterno," + "TR.apellidoMaterno,TR.rut, TR.division,"
					+ " TR.idSubDivision,TR.grupo,TR.idSubgrupo,sw.periodo,"
					+ " sw.concepto,sw.nHoras,sw.fecha, sw.id as id_sw_horasAsistencia,"
					+ "(SELECT descripcion FROM parametros WHERE codigo = 'CONCEPTO_HORA_ASISTENCIA' and llave = sw.concepto) as nombre_concepto"
					+ " from contratos CO  inner join trabajadores TR on TR.codigo = CO.codigo_trabajador join sw_horasAsistencia sw on sw.codTrabajador = CO.codigo_trabajador "
					+ "where 1 = 1 ";

			if ("null".equals(soc)) {
			} else {
				sql += " and CO.idSociedad = " + soc + "";
			}
			if ("null".equals(cod)) {
			} else {
				sql += " and codigo = " + cod + "";
			}
			if ("null".equals(div)) {
			} else {
				sql += " and TR.idHuerto = '" + div + "'";
			}
			if ("null".equals(subdiv)) {
			} else {
				sql += " and TR.idZona = '" + subdiv + "'";
			}
			if ("null".equals(gru)) {
			} else {
				sql += " and TR.idCECO = '" + gru + "'";
			}
			if ("null".equals(concepto)) {
			} else {
				sql += " and sw.concepto = " + concepto + "";
			}
			if ("null".equals(periodo)) {
			} else {
				sql += " and sw.periodo = " + periodo + "";
			}
			if ("null".equals(estado_proceso)) {
			} else {
				sql += " and TR.estadoProceso = '" + estado_proceso + "'";
			}
			
			

			sql += " and CO.EstadoContrato =  1 group by CO.codigo_trabajador,"
					+ "sw.periodo,sw.concepto,sw.codTrabajador,sw.nHoras,sw.fecha,sw.id;";

			System.out.println(sql);

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				LoadTrabajadorSociedad e = new LoadTrabajadorSociedad();

				e.setCodigotrabajador(rs.getInt("codigo"));
				e.setNombre(rs.getString("nombre"));
				e.setApellidoPaterno(rs.getString("apellidoPaterno"));
				e.setApellidoMaterno(rs.getString("apellidoMaterno"));
				e.setFecha(rs.getString("fecha"));
				e.setNhoras(rs.getDouble("nHoras"));
				e.setPeriodoint(rs.getInt("periodo"));
				e.setConceptoint(rs.getInt("concepto"));
				e.setId_sw_horasAsistencia(rs.getInt("id_sw_horasAsistencia"));
				e.setNombre_concepto(rs.getString("nombre_concepto"));

				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			db.close();
		}
		return data;
	}

	public static boolean eliminarHoraAsistencia(int id) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "DELETE FROM sw_horasAsistencia WHERE id=" + id + "";
			ps = db.conn.prepareStatement(sql);
			ps.execute();

			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}

	public static ArrayList<CargarTipodePago> getListaconceptoHA() throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CargarTipodePago> lista = new ArrayList<CargarTipodePago>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select * from parametros where codigo = 'CONCEPTO_HORA_ASISTENCIA'  AND activo = 1  ORDER BY descripcion;";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				CargarTipodePago cr = new CargarTipodePago();
				cr.setId(rs.getInt("llave"));
				cr.setDescripcion(rs.getString("descripcion"));

				lista.add(cr);
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	public static boolean updateHoraAsistencia(HorasAsistencia r) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			sql = "Update sw_horasAsistencia set " + "concepto= " + r.getConcepto() + ", " + "nHoras='" + r.getHora()
					+ "'," + "periodo= " + r.getPeriodo() + "," + "fecha= '" + r.getFecha() + "'" + " where id = "
					+ r.getId_horasasistencia() + "";
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.execute();

			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}

	public static String insertExcelHoraAsistencia(HorasAsistencia r) throws Exception {

		// String escaped = r.getRuta().replaceAll("\\\\", "//");
		String escaped = r.getRuta().replace("\\", "\\\\");
		System.out.println(escaped);
		String respuesta = "";
		String codigosTrabajadores = "";
		ConnectionDB db = new ConnectionDB();

		try {

			db.conn.setAutoCommit(false);
			PreparedStatement ps = null;
			OPCPackage pkg = OPCPackage.open(new File(escaped));

			XSSFWorkbook wb = new XSSFWorkbook(pkg);
			XSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);

				int codTrabajador = (int) row.getCell(0).getNumericCellValue();

				int concepto = (int) row.getCell(1).getNumericCellValue();
				double nHoras = row.getCell(2).getNumericCellValue();
				String result = String.format("%.2f", nHoras);
				result = result.replace(',', '.');

				int periodo = (int) row.getCell(3).getNumericCellValue();

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String fechaformat = sdf.format(row.getCell(4).getDateCellValue());

				double nHoras2 = row.getCell(5).getNumericCellValue();
				String horaf = String.format("%.2f", nHoras2);
				horaf = horaf.replace(',', '.');

				HorasAsistencia idContratos = sw_HorasAsistenciaDB.getIdContratoSleccion(codTrabajador, fechaformat);
				int idContrato2 = idContratos.getId_contrato();

				if (idContrato2 == 0) {

					codigosTrabajadores = codigosTrabajadores + " " + codTrabajador;
				} else {

					String sql = "INSERT INTO sw_horasAsistencia (codTrabajador,idContrato,concepto,nHoras,periodo,fecha,horaOrdinaria) VALUES"
							+ "  (" + codTrabajador + ", " + idContrato2 + ", " + concepto + ", " + result + ", "
							+ periodo + ", '" + fechaformat + "'," + horaf + ")";

					System.out.println(sql);
					ps = (PreparedStatement) db.conn.prepareStatement(sql);
					ps.execute();
					System.out.println("Import rows " + i);
				}

			}
			db.conn.commit();
			ps.close();
			db.close();
			pkg.close();

			File archivo = new File(escaped);

			boolean estatus = archivo.delete();
			;

			if (!estatus) {

				System.out.println("Error no se ha podido eliminar el  archivo");

			} else {

				System.out.println("Se ha eliminado el archivo exitosamente");

			}

			if (codigosTrabajadores == "") {
				return respuesta = "Datos Ingresado con Exito";
			} else {
				return respuesta = "Codigo Trabajador " + codigosTrabajadores
						+ " no se encuentran Registrado o la Fecha Ingresada No Corresponde a su Contrato";
			}

		} catch (SQLException e) {
			respuesta = "Error: " + e.getMessage();
		} catch (Exception e) {
			respuesta = "Error: " + e.getMessage();
		} finally {

			db.close();
		}
		return respuesta;
	}

	public static HorasAsistencia getIdContratoSleccion(int codTrabajador, String fechaformat) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		HorasAsistencia idcon = new HorasAsistencia();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT id,idSociedad FROM contratos WHERE codigo_trabajador = " + codTrabajador
					+ " AND fechaInicio_actividad " + "<= DATE ('" + fechaformat
					+ "') AND ( FechaTerminoContrato IS NULL " + "OR FechaTerminoContrato >= DATE ('" + fechaformat
					+ "') )";

			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			if (rs.next()) {

				idcon.setId_contrato(rs.getInt("id"));
				idcon.setEmpresa(rs.getInt("idSociedad"));

			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return idcon;

	}

	public static String insertExcelHoraDiasFalta(HorasAsistencia r) throws Exception {

		// String escaped = r.getRuta().replaceAll("\\\\", "//");
		String escaped = r.getRuta().replace("\\", "\\\\");
		System.out.println(escaped);
		String respuesta = "";
		String codigosTrabajadores = "";
		ConnectionDB db = new ConnectionDB();

		try {

			db.conn.setAutoCommit(false);
			PreparedStatement ps = null;
			OPCPackage pkg = OPCPackage.open(new File(escaped));

			XSSFWorkbook wb = new XSSFWorkbook(pkg);
			XSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);

				int codTrabajador = (int) row.getCell(0).getNumericCellValue();
				int incluyeFeriados = (int) row.getCell(1).getNumericCellValue();

				SimpleDateFormat fechadesde = new SimpleDateFormat("yyyy-MM-dd");
				String fecha_de = fechadesde.format(row.getCell(2).getDateCellValue());

				SimpleDateFormat fechahasta = new SimpleDateFormat("yyyy-MM-dd");
				String fecha_ha = fechahasta.format(row.getCell(3).getDateCellValue());

				int dias_cori = (int) row.getCell(4).getNumericCellValue();

				HorasAsistencia idContratos = sw_HorasAsistenciaDB.getIdContratoSleccion(codTrabajador, fecha_ha);
				int idContrato2 = idContratos.getId_contrato();
				int idSociedad = idContratos.getEmpresa();

				if (idContrato2 == 0) {

					codigosTrabajadores = codigosTrabajadores + " " + codTrabajador;
				} else {

					// fecha_creacion now() idContrato
					String sql = "INSERT INTO permiso_licencia (id_empresa,codigo_trabajador,accion,incluye_feriados,fecha_desde,fecha_hasta,fecha_creacion,dias_corridos,idContrato) VALUES"
							+ "  (" + idSociedad + ", " + codTrabajador + ",3, " + incluyeFeriados + ", '" + fecha_de
							+ "','" + fecha_ha + "',now()," + dias_cori + "," + idContrato2 + ")";

					System.out.println(sql);
					ps = (PreparedStatement) db.conn.prepareStatement(sql);
					ps.execute();
					System.out.println("Import rows " + i);
				}

			}
			db.conn.commit();
			ps.close();
			db.close();
			pkg.close();

			File archivo = new File(escaped);

			boolean estatus = archivo.delete();
			;

			if (!estatus) {

				System.out.println("Error no se ha podido eliminar el  archivo");

			} else {

				System.out.println("Se ha eliminado el archivo exitosamente");

			}

			if (codigosTrabajadores == "") {
				return respuesta = "Datos Ingresado con Exito";
			} else {
				return respuesta = "Codigo Trabajador " + codigosTrabajadores
						+ " no se encuentra(n) Registrado o la Fecha Ingresada No Corresponde a su Contrato";
			}

		} catch (SQLException e) {
			respuesta = "Error: " + e.getMessage();
		} catch (Exception e) {
			respuesta = "Error: " + e.getMessage();
		} finally {

			db.close();
		}
		return respuesta;
	}

}

package lib.db.sw;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lib.classSW.DatosLiquidacion;
import lib.classSW.DocLiquidacion;
import lib.classSW.GetDatosContratoTrabajador;
import lib.classSW.Liquidacion;
import lib.classSW.LiquidacionDetalle;
import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;
import wordCreator.JsonStringParser;

public class LiquidacionDetalleDB {

	private final static Logger LOG = LoggerFactory.getLogger(LiquidacionDetalleDB.class);
	
	// insertar Liquidacion detalle
	public static boolean insertLiquidacion(LiquidacionDetalle LiquidacionDetalle) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try {
			sql = " INSERT into sw_liquidacion " + " VALUES (?,?,?,?,?,?,)";
			ps = db.conn.prepareStatement(sql);

			ps.setInt(1, LiquidacionDetalle.getCodTrabajador());
			ps.setInt(2, LiquidacionDetalle.getIdContrato());
			ps.setInt(3, LiquidacionDetalle.getIdConcepto());
			ps.setString(4, LiquidacionDetalle.getPeriodo());
			ps.setString(5, LiquidacionDetalle.getDescripcion());
			ps.setString(6, LiquidacionDetalle.getValor());

			ps.execute();
			return true;
		} catch (Exception e) {

			System.out.println("Error al ingresar el detalle Liquidacion :" + e.getMessage());
			e.printStackTrace();

		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}// fin clase insert

	// get liquidacion detalle
	public static ArrayList<LiquidacionDetalle> getLiquidacionDetalle() throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<LiquidacionDetalle> lista = new ArrayList<LiquidacionDetalle>();

		try {
			sql = "select * sw_liquidacionDetalle";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				LiquidacionDetalle LiquidacionDetalle = new LiquidacionDetalle();

				LiquidacionDetalle.setCodTrabajador(rs.getInt("cod_Trabajador"));
				LiquidacionDetalle.setIdContrato(rs.getInt("idContrato"));
				LiquidacionDetalle.setIdConcepto(rs.getInt("idConcepto"));
				LiquidacionDetalle.setPeriodo(rs.getString("periodo"));
				LiquidacionDetalle.setDescripcion(rs.getString("descripcion"));
				LiquidacionDetalle.setValor(rs.getString("valor"));

				lista.add(LiquidacionDetalle);

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
	}// fin metodo get

	// get liquidacion detalle por id
	public static LiquidacionDetalle getLiquidacionDetalleByIdContrato(int id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		LiquidacionDetalle LiquidacionDetalle = new LiquidacionDetalle();

		try {
			sql = "select * from sw_liquidacionDetalle " + " where idContrato = '" + id + "' ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				LiquidacionDetalle.setCodTrabajador(rs.getInt("codTrabajador"));
				LiquidacionDetalle.setIdContrato(rs.getInt("idContrato"));
				LiquidacionDetalle.setIdConcepto(rs.getInt("idConcepto"));
				LiquidacionDetalle.setPeriodo(rs.getString("periodo"));
				LiquidacionDetalle.setDescripcion(rs.getString("descripcion"));
				LiquidacionDetalle.setValor(rs.getString("valor"));

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
		return LiquidacionDetalle;

	}// fin metodo

	// update Liquidacion
	public static boolean updateLiquidacionDetalle(LiquidacionDetalle LiquidacionDetalle) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ResultSet rs = null;

		try {

			int i = 1;

			sql = " SELECT codTrabajador FROM sw_liquidacionDetalle  WHERE codTrabajador = ? ";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++, LiquidacionDetalle.getCodTrabajador());
			rs = ps.executeQuery();

			if (!rs.next()) {
				i = 1;
				rs.close();
				ps.close();

				sql = " INSERT INTO sw_liquidacionDetalle (codTrabajador) VALUES (?) ";
				ps = db.conn.prepareStatement(sql);
				ps.setInt(i++, LiquidacionDetalle.getCodTrabajador());
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				int key = 0;
				if (rs.next()) {
					key = rs.getInt(1);
				}

				LiquidacionDetalle.setCodTrabajador(key);
			}

			i = 1;
			rs.close();
			ps.close();

			sql = " UPDATE sw_liquidacionDetalle SET codigo_trabajador = ? , "
					+ " id_contrato = ? ,idConsepto = ? , periodo = ?, descripcion = ?, valor = ?, ";

			ps = db.conn.prepareStatement(sql);

			ps.setInt(1, LiquidacionDetalle.getCodTrabajador());
			ps.setInt(2, LiquidacionDetalle.getIdContrato());
			ps.setInt(3, LiquidacionDetalle.getIdConcepto());
			ps.setString(4, LiquidacionDetalle.getPeriodo());
			ps.setString(5, LiquidacionDetalle.getDescripcion());
			ps.setString(6, LiquidacionDetalle.getValor());

			ps.execute();

			return true;

		} catch (Exception e) {
			System.out.println("Error update Liquidacion detalle: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}// fin metodo update

	// delete
	public static boolean deleteLiquidacionDetalleById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "DELETE FROM sw_liquidacionDetalle WHERE codTrabajador=" + id;
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (Exception ex) {
			return false;
		} finally {
			db.conn.close();
		}
	}// fin metodo eliminar

	public static ArrayList<LiquidacionDetalle> createLiquidacionDetalle(int cod, int idcontrato, int periodo)
			throws Exception {

		ArrayList<LiquidacionDetalle> data = new ArrayList<LiquidacionDetalle>();
		ConnectionDB db = new ConnectionDB();
		try {
			CallableStatement cStmt = db.conn.prepareCall("{call SAN_CLEMENTE.sw_createLiquidacion(?, ?, ?,0,1)}");

			cStmt.setInt(1, cod);
			cStmt.setInt(2, periodo);
			cStmt.setInt(3, idcontrato);
			System.out.println(cStmt);
			cStmt.execute();
			ResultSet rs = cStmt.getResultSet();

			while (rs.next()) {
				LiquidacionDetalle liquidacionDetalle = new LiquidacionDetalle();

				liquidacionDetalle.setCodTrabajador(rs.getInt("codTrabajador"));
				liquidacionDetalle.setIdContrato(rs.getInt("idContrato"));
				liquidacionDetalle.setIdConcepto(rs.getInt("idConcepto"));
				liquidacionDetalle.setDescripcion(rs.getString("descripcion"));
				liquidacionDetalle.setValor(rs.getString("valor"));

				data.add(liquidacionDetalle);
			}
			rs.close();
			// ps.close();
			db.conn.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			db.close();
		}
		return data;
	}
	
	public static String insertLiquidacionDetalle(Liquidacion liquidacion)
			throws Exception {

		ArrayList<LiquidacionDetalle> data = new ArrayList<LiquidacionDetalle>();
		ConnectionDB db = new ConnectionDB();
		try {
			
			//Obtener Datos de Codigo Id_Contrato y Periodo para los Filtros
			ArrayList<filterSql> filter = new ArrayList<>();
			
			filterSql campo_valor1 = new filterSql();
			filterSql campo_valor2 = new filterSql();
			filterSql campo_valor3 = new filterSql();
			campo_valor1.setCampo("cod_trabajador");
			campo_valor1.setValue(String.valueOf(liquidacion.getCod_trabajador()));
			filter.add(campo_valor1);
			campo_valor2.setCampo("id_contrato");
			campo_valor2.setValue(String.valueOf(liquidacion.getId_contrato()));
			filter.add(campo_valor2);
			campo_valor3.setCampo("periodo");
			campo_valor3.setValue(String.valueOf(GeneralUtility.convertYYYYMMToInt(liquidacion.getPeriodo())));
			filter.add(campo_valor3);
			
		
			//Genero La LiquidacionDetalle y la Liquidacion Cabezera	
			CallableStatement cStmt = db.conn.prepareCall("{call SAN_CLEMENTE.sw_createLiquidacion(?, ?, ?,0,1)}");

			cStmt.setInt(1, liquidacion.getCod_trabajador());
			cStmt.setInt(2, liquidacion.getId_contrato());
			cStmt.setInt(3, GeneralUtility.convertYYYYMMToInt(liquidacion.getPeriodo()));
			System.out.println(cStmt);
			cStmt.execute();
			ResultSet rs = cStmt.getResultSet();
									
			while (rs.next()) {
				LiquidacionDetalle liquidacionDetalle = new LiquidacionDetalle();

				liquidacionDetalle.setCodTrabajador(rs.getInt("codTrabajador"));
				liquidacionDetalle.setIdContrato(rs.getInt("idContrato"));
				liquidacionDetalle.setIdConcepto(rs.getInt("idConcepto"));
				liquidacionDetalle.setDescripcion(rs.getString("descripcion"));
				liquidacionDetalle.setValor(rs.getString("valor"));

				data.add(liquidacionDetalle);
			}
			rs.close();
			
			//Si se tiene fecha de Pago setear en la cabezera de Liquidacion
			if(liquidacion.getFecha_pago() != null){
				
				//Obtener id de Liquidacion
				ArrayList<Liquidacion> liquidacionCabecera = LiquidacionesDB.getLiquidacionWithFilter(filter);
				
				//Modificar Fecha de Pago
				PreparedStatement ps = null;
				String sql = "";
				
				//Actualizar Fecha de Pago
				sql = " UPDATE sw_liquidacion SET fecha_pago = ? WHERE id_liquidacion = ? ";
				ps = db.conn.prepareStatement(sql);
				
				ps.setString(1, GeneralUtility.convertStringToYYYYMMDD(liquidacion.getFecha_pago()));
				ps.setInt(2, liquidacionCabecera.get(0).getId_liquidacion());
				
				ps.execute();
				ps.close();
				
			}
			
			db.conn.close();
		} 
		catch (IndexOutOfBoundsException e){
			throw new Exception("No se pudo generar liquidacion para el periodo selecionado con el Trabajador: " + liquidacion.getCod_trabajador()
			+" -- presione aceptar para seguir procesando las liquidaciones --");
		}
		catch (Exception e) {
			throw new Exception(e);
		} finally {
			db.close();
		}
		return "Liquidacion Generada";
	}

	// get datos Liquidacion de la LiquidacionDetalle por codTrabajador, idContrato, periodo
	public static DatosLiquidacion getLiquidacionDetalleToDatosLiquidacion(ArrayList<filterSql> filter) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try {

			// Crear sentencia en Sql
			sql = "SELECT * FROM sw_liquidacionDetalle ";
			ps = db.conn.prepareStatement(sql);

			// Si contiene datos asignarlo al WHERE
			if (filter.size() > 0) {
				String andSql = "";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext()) {
					filterSql row = f.next();

					if (!row.getValue().equals("") && "fechaPago".equals(row.getCampo()) == false ) {

						if (row.getCampo().endsWith("_to")) {

							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						}

						else if (row.getCampo().endsWith("_from")) {

							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						}

						else

							sql += andSql + row.getCampo() + " = " + row.getValue() + "";

						andSql = " and ";
					}
				} // Fin While

			}

			ResultSet rs = ps.executeQuery(sql);
			System.out.println("#######################get datos Liquidacion de la LiquidacionDetalle por codTrabajador, idContrato, periodo");
			System.out.println(sql);
		
		ArrayList<LiquidacionDetalle> lista = new ArrayList<>();
		DatosLiquidacion datosLiquidacion = new DatosLiquidacion(); 

		String codTrabajador = null;
		String idContrato = null;
		String periodo = null;
		
		
			while (rs.next()) {

				LiquidacionDetalle LiquidacionDetalle = new LiquidacionDetalle(); 
				
				LiquidacionDetalle.setCodTrabajador(rs.getInt("codTrabajador"));
				LiquidacionDetalle.setIdContrato(rs.getInt("idContrato"));
				LiquidacionDetalle.setIdConcepto(rs.getInt("idConcepto"));
				LiquidacionDetalle.setPeriodo(rs.getString("periodo"));
				LiquidacionDetalle.setDescripcion(rs.getString("descripcion"));
				LiquidacionDetalle.setValor(rs.getString("valor"));
				
				lista.add(LiquidacionDetalle);
				
				codTrabajador = String.valueOf(rs.getInt("codTrabajador"));
				idContrato    = String.valueOf(rs.getInt("idContrato"));
				periodo       = String.valueOf(rs.getInt("periodo"));
				
			}

			//Convertir Liquidacion detalle a Datos para Archivo de Liquidacion TODO:
			datosLiquidacion = LiquidacionDetalleDB.liquidacionDetalleToDatosLiquidacion(lista);
			
			//Datos de La Cabezera de Liquidacion
			datosLiquidacion.setCod_trabajador(codTrabajador);
			datosLiquidacion.setId_contrato(idContrato);
			datosLiquidacion.setPeriodo(periodo);
			
			System.out.println(lista);
			return datosLiquidacion; //datosLiquidacion
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		} finally {
			ps.close();
			db.close();
		}
		
		

	}// fin metodo

	
	// get datos Liquidacion de la LiquidacionDetalle por codTrabajador, idContrato, periodo
		public static ArrayList<LiquidacionDetalle> getLiquidacionDetalleWithFilter(ArrayList<filterSql> filter) throws Exception {

			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();

			try {

				// Crear sentencia en Sql
				sql = "SELECT * FROM sw_liquidacionDetalle ";
				ps = db.conn.prepareStatement(sql);

				// Si contiene datos asignarlo al WHERE
				if (filter.size() > 0) {
					String andSql = "";
					andSql += " WHERE ";
					Iterator<filterSql> f = filter.iterator();

					while (f.hasNext()) {
						filterSql row = f.next();

						if (!row.getValue().equals("")) {

							if (row.getCampo().endsWith("_to")) {

								SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
								SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
								sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='"
										+ sqlDate.format(formatter.parse(row.getValue())) + "'";
							}

							else if (row.getCampo().endsWith("_from")) {

								SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
								SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
								sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='"
										+ sqlDate.format(formatter.parse(row.getValue())) + "'";
							}
							else if (GeneralUtility.isArray(row.getValue())){
								sql += andSql + row.getCampo() + " in ( "+GeneralUtility.convertJSONArrayToArray(row.getValue())+" ) ";
							}
							else if (GeneralUtility.isNumeric(row.getValue())){
								sql += andSql + row.getCampo() + " = '" + row.getValue() + "'";
							}
							else
								sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";

							andSql = " and ";
						}
					} // Fin While

				}

				ResultSet rs = ps.executeQuery(sql);
			
			ArrayList<LiquidacionDetalle> lista = new ArrayList<>(); 

				while (rs.next()) {

					LiquidacionDetalle LiquidacionDetalle = new LiquidacionDetalle(); 
					
					LiquidacionDetalle.setCodTrabajador(rs.getInt("codTrabajador"));
					LiquidacionDetalle.setIdContrato(rs.getInt("idContrato"));
					LiquidacionDetalle.setIdConcepto(rs.getInt("idConcepto"));
					LiquidacionDetalle.setPeriodo(rs.getString("periodo"));
					LiquidacionDetalle.setDescripcion(rs.getString("descripcion"));
					LiquidacionDetalle.setValor(rs.getString("valor"));
					
					lista.add(LiquidacionDetalle);
				}

				return lista;
				
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				return null;
			} finally {
				ps.close();
				db.close();
			}
			

		}// fin metodo

	
	
	
	
	public static DatosLiquidacion liquidacionDetalleToDatosLiquidacion(ArrayList<LiquidacionDetalle> listaLiquidacionDetalle){
		
		DatosLiquidacion datosLiquidacion = new DatosLiquidacion();
		//int CARGA_FAMILIAR_CANTIDAD_SIMPLE = 0;
		//int CARGA_FAMILIAR_CANTIDAD_MATERNAL = 0;
		int CARGA_FAMILIAR_CANTIDAD_RETROACTIVO = 0;
		
		Double CARGA_FAMILIAR_RETROACTIVO_VALOR = 0.0;
		
	 	ArrayList<String> BONO = new ArrayList<>();
    	ArrayList<String> BONO_NOMBRE = new ArrayList<>();
    	
	 	ArrayList<String> BONO_NO_IMPONIBLE = new ArrayList<>();
    	ArrayList<String> BONO_NO_IMPONIBLE_NOMBRE = new ArrayList<>();
    	
    	ArrayList<String> HORAS_FALTA = new ArrayList<>(); //CODIGO 6
    	ArrayList<String> HORAS_FALTA_NHORAS = new ArrayList<>(); //CODIGO 6
    	ArrayList<String> HORAS_FALTA_VHORAS = new ArrayList<>(); //CODIGO 6
    	ArrayList<String> HORAS_FALTA_NOMBRE = new ArrayList<>(); //CODIGO 6
    	ArrayList<String> HORAS_EXTRA = new ArrayList<>(); //CODIGO 7
    	ArrayList<String> HORAS_EXTRA_NHORAS = new ArrayList<>(); //CODIGO 7
    	ArrayList<String> HORAS_EXTRA_VHORAS = new ArrayList<>(); //CODIGO 7
    	ArrayList<String> HORAS_EXTRA_NOMBRE = new ArrayList<>(); //CODIGO 7
    	
    	ArrayList<String> ANTICIPO = new ArrayList<>();
    	ArrayList<String> ANTICIPO_NOMBRE = new ArrayList<>();
    	
    	ArrayList<String> IMPUESTO_UNICO = new ArrayList<>();
    	ArrayList<String> IMPUESTO_UNICO_NOMBRE = new ArrayList<>();
    	
    	ArrayList<String> DESCUENTOS = new ArrayList<>();
    	ArrayList<String> DESCUENTOS_NOMBRE = new ArrayList<>();
    	
    	ArrayList<String> CONCEPTO_8 = new ArrayList<>();
    	ArrayList<String> CONCEPTO_8_NOMBRE = new ArrayList<>();
    	
    	ArrayList<String> SALDO_ANTERIOR = new ArrayList<>();
    	ArrayList<String> SALDO_ANTERIOR_NOMBRE = new ArrayList<>();
    	
    	ArrayList<String> PLAN_ADICIONAL_NO_TRIBUTABLE = new ArrayList<>();
    	ArrayList<String> PLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE = new ArrayList<>();
    	//ArrayList<String> PLAN_ADICIONAL_NO_TRIBUTABLE_UF = new ArrayList<>();
    	
    	
    	ArrayList<String> PLAN_ADICIONAL_TRIBUTABLE = new ArrayList<>();
    	ArrayList<String> PLAN_ADICIONAL_TRIBUTABLE_NOMBRE = new ArrayList<>();
    	
    	ArrayList<String> CONCEPTO_MEDICO = new ArrayList<>();
    	ArrayList<String> CONCEPTO_MEDICO_NOMBRE = new ArrayList<>();
    	
    	ArrayList<String> SOBREGIRO = new ArrayList<>();
    	ArrayList<String> SOBREGIRO_NOMBRE = new ArrayList<>();
    	
    	
		for (LiquidacionDetalle liq : listaLiquidacionDetalle) {
			
			try{
			
			
			//Obtener el Numero de Concepto
			int c = liq.getIdConcepto();

			switch(c)
			{
				case 0: //Tipo Trabajador
					if(liq.getDescripcion().contains("Tipo Trabajador")){
						datosLiquidacion.setTIPO_TRABAJADOR(liq.getValor().substring(0, liq.getValor().indexOf(".")));
					}
					else if(liq.getDescripcion().contains("Tope Imponible")){
						datosLiquidacion.setTOPE_IMPONIBLE(liq.getValor().substring(0, liq.getValor().indexOf(".")));
					}
					break;
			    case 1: //Sueldo Base Contrato
			    	datosLiquidacion.setSUELDO_BASE_CONTRATO(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 2:
			        datosLiquidacion.setDIAS_TRAJADOS(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 3:
			    	datosLiquidacion.setSUELDO_BASE(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 4:
			    	BONO.add(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			    	BONO_NOMBRE.add(liq.getDescripcion());
			        break;
			    case 6: //HORA FALTA
			    	HORAS_FALTA.add(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			    	HORAS_FALTA_NHORAS.add(liq.getDescripcion().substring(liq.getDescripcion().indexOf("(") + 1, liq.getDescripcion().indexOf(")") - 1));
			    	HORAS_FALTA_VHORAS.add(liq.getDescripcion().substring(liq.getDescripcion().indexOf(")") + 1, liq.getDescripcion().length() - 1));
			    	HORAS_FALTA_NOMBRE.add(liq.getDescripcion().substring(0, liq.getDescripcion().indexOf("(") - 1).toUpperCase());
			    	break;
			    case 7: //HORA EXTRA
			    	HORAS_EXTRA.add(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			    	HORAS_EXTRA_NHORAS.add(liq.getDescripcion().substring(liq.getDescripcion().indexOf("(") + 1 , liq.getDescripcion().indexOf(")") - 1));
			    	HORAS_EXTRA_VHORAS.add(liq.getDescripcion().substring(liq.getDescripcion().indexOf(")") + 1, liq.getDescripcion().length() - 1));
			    	HORAS_EXTRA_NOMBRE.add(liq.getDescripcion().substring(0, liq.getDescripcion().indexOf("(") - 1).toUpperCase());
			    	break;
			    case 8:
			    	CONCEPTO_8.add(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			    	CONCEPTO_8_NOMBRE.add(liq.getDescripcion());
			    	break;
			    case 9:
			    	datosLiquidacion.setGRATIFICACION(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 10:
			    	datosLiquidacion.setTOTAL_HAB_IMPONIBLE(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 11:
			    	BONO_NO_IMPONIBLE.add(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			    	BONO_NO_IMPONIBLE_NOMBRE.add(liq.getDescripcion());
			        break;
			    //case 14: 
			    case 15:
			    	//int NCARGAS1 = ++CARGA_FAMILIAR_CANTIDAD_SIMPLE;
			    	datosLiquidacion.setCARGA_FAMILIAR_SIMPLE_NOMBRE(liq.getDescripcion());
			    	datosLiquidacion.setCARGA_FAMILIAR_SIMPLE(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 14:
			    	//int NCARGAS2 = ++CARGA_FAMILIAR_CANTIDAD_MATERNAL;
			    	datosLiquidacion.setCARGA_FAMILIAR_MATERNAL_NOMBRE(liq.getDescripcion());
			    	datosLiquidacion.setCARGA_FAMILIAR_MATERNAL(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 17:
			    	int NCARGAS3 = ++CARGA_FAMILIAR_CANTIDAD_RETROACTIVO;
			    	datosLiquidacion.setCARGA_FAMILIAR_RETROACTIVO_NOMBRE(liq.getDescripcion());
			    	datosLiquidacion.setCARGA_FAMILIAR_RETROACTIVO_CANTIDAD(String.valueOf(NCARGAS3));
			    	CARGA_FAMILIAR_RETROACTIVO_VALOR = Double.valueOf(liq.getValor()) + CARGA_FAMILIAR_RETROACTIVO_VALOR;
			    	datosLiquidacion.setCARGA_FAMILIAR_RETROACTIVO(String.valueOf(CARGA_FAMILIAR_RETROACTIVO_VALOR).substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 20:
			    	datosLiquidacion.setTOTAL_HAB_NO_IMPONIBLE(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 21:
			    	datosLiquidacion.setTOTAL_HABERES(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 22:
			    	datosLiquidacion.setBASE_TRIBUTABLE(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 31:
			    	datosLiquidacion.setAFP_NOMBRE(liq.getDescripcion().substring(0, liq.getDescripcion().indexOf("(")));
			    	datosLiquidacion.setAFP_PORCENTAJE(liq.getDescripcion().substring(liq.getDescripcion().indexOf("(")+1, liq.getDescripcion().indexOf("%")));
			    	datosLiquidacion.setAFP(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			    	break;
			    case 32:
			    	if(liq.getDescripcion().contains("CAJA")){
			    		datosLiquidacion.setCAJA(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			    		datosLiquidacion.setCAJA_PORCENTAJE(liq.getDescripcion().substring(liq.getDescripcion().indexOf("(")+1, liq.getDescripcion().indexOf("%")));
			    	}else{
			    		if(liq.getDescripcion().contains("Fonasa")){
			    			datosLiquidacion.setSALUD_NOMBRE(liq.getDescripcion().substring(0, liq.getDescripcion().indexOf(" ")));
				    		datosLiquidacion.setSALUD_PORCENTAJE(liq.getDescripcion().substring(liq.getDescripcion().indexOf("(")+1, liq.getDescripcion().indexOf("%")));
				    		datosLiquidacion.setSALUD(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			    		}else if(liq.getDescripcion().contains("%") && liq.getDescripcion().contains("valor plan")){
			    			
			    			if( ( liq.getDescripcion().contains("TRIB1") && liq.getValor().equals("0.0000") ) ){
			    				break;
			    			}
			    				
			    			if( ( liq.getDescripcion().contains("TRIB2") && liq.getValor().equals("0.0000") ) ){
			    				break;
			    			}
			    					
			    			datosLiquidacion.setSALUD_NOMBRE(liq.getDescripcion().substring(0, liq.getDescripcion().indexOf("valor")));
					    	datosLiquidacion.setSALUD_PORCENTAJE(liq.getDescripcion().substring(liq.getDescripcion().indexOf("(")+1, liq.getDescripcion().indexOf(")")-1));
						    datosLiquidacion.setSALUD(liq.getValor().substring(0, liq.getValor().indexOf(".")));
						    	if(liq.getDescripcion().contains("UF")){
						    	datosLiquidacion.setSALUD_UF(liq.getDescripcion().substring(liq.getDescripcion().indexOf("plan") + 4, liq.getDescripcion().indexOf("UF")).trim());
						    }
			    		}else if(liq.getDescripcion().contains("Adicional")){
			    			PLAN_ADICIONAL_TRIBUTABLE.add(liq.getValor().substring(0, liq.getValor().indexOf(".")));
		    				PLAN_ADICIONAL_TRIBUTABLE_NOMBRE.add(liq.getDescripcion().toUpperCase());
//		    				if(liq.getDescripcion().contains("UF")){
//				    			datosLiquidacion.setSALUD_UF(liq.getDescripcion().substring(liq.getDescripcion().indexOf("PLAN"), liq.getDescripcion().indexOf("UF")));
//				    		}
			    		}else if(liq.getDescripcion().contains("UF")){
			    			datosLiquidacion.setSALUD_UF(liq.getDescripcion().substring(liq.getDescripcion().indexOf("PLAN"), liq.getDescripcion().indexOf("UF")));
			    		}
			    		else{
			    			LOG.info(("SIN PLAN CONCEPTO 32"));
			    		}
			    	}
			        break;
			    case 33:
			    	datosLiquidacion.setSEGURO_CESANTIA_AFC_TRABAJADOR(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 34:
			    	datosLiquidacion.setAPV(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 39:
			    	IMPUESTO_UNICO_NOMBRE.add((liq.getDescripcion()));
			    	IMPUESTO_UNICO.add((liq.getValor().substring(0, liq.getValor().indexOf("."))));
			        break;
			    case 40:
			    	datosLiquidacion.setTOTAL_DESCUENTOS_IMP(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 42:
			    	PLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE.add((liq.getDescripcion().toUpperCase()));
			    	PLAN_ADICIONAL_NO_TRIBUTABLE.add((liq.getValor().substring(0, liq.getValor().indexOf("."))));
			    	break;
			    case 44:
			    	datosLiquidacion.setAPV_ADICIONAL(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			    	break;
			    case 43:
			    	ANTICIPO.add((liq.getValor().substring(0, liq.getValor().indexOf("."))));
			    	ANTICIPO_NOMBRE.add(liq.getDescripcion());
			    	break;
			    case 45:
			    	DESCUENTOS.add((liq.getValor().substring(0, liq.getValor().indexOf("."))));
			    	DESCUENTOS_NOMBRE.add(liq.getDescripcion().toUpperCase());
			    	break;
			    case 48:
			    	datosLiquidacion.setAHORRO_VOLUNTARIO_AFP(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 50:
			    	datosLiquidacion.setTOTAL_DESCUENTOS_NO_IMP(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 51:
			    	datosLiquidacion.setTOTAL_DESCUENTOS(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 80:
			    	SALDO_ANTERIOR.add(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			    	SALDO_ANTERIOR_NOMBRE.add(liq.getDescripcion().toUpperCase());
			    	break;
			    case 81:
			    	SOBREGIRO.add(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			    	SOBREGIRO_NOMBRE.add(liq.getDescripcion().toUpperCase());
			    	break;
			    case 92:
			    	datosLiquidacion.setSEGURO_CESANTIA_AFC_EMPLEADOR(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 94:
			    	datosLiquidacion.setCOTIZACION_BASICA(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 95:
			    	datosLiquidacion.setCOTIZACION_ADICIONAL(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 96:
			    	datosLiquidacion.setCOTIZACION_EXTRAORDINARIA(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 97:
			    	datosLiquidacion.setSANNA(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 98:
			    	CONCEPTO_MEDICO.add(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			    	CONCEPTO_MEDICO_NOMBRE.add(liq.getDescripcion().toUpperCase());
			    	break;
			    case 100:
			    	datosLiquidacion.setTOTAL_PAGO(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 101:
			    	datosLiquidacion.setTOTAL_LIQUIDO_MES(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			        break;
			    case 202:
			    	datosLiquidacion.setCARGA_FAMILIAR_MATERNAL_CANTIDAD(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			    	datosLiquidacion.setCARGA_FAMILIAR_SIMPLE_CANTIDAD(liq.getValor().substring(0, liq.getValor().indexOf(".")));
			    	break;
			    default:
			        System.out.println("No se encuentra concepto asociado");
			        break;
			}

			}catch(Exception e){
				throw e;
			}
			
		}
		
		//Agregar Listado de BONOS
		if(!BONO.isEmpty()){
			datosLiquidacion.setBONO_NOMBRE(BONO_NOMBRE);
			datosLiquidacion.setBONO(BONO);
		}
		
		//Agregar Listado de BONOS
		if(!BONO_NO_IMPONIBLE.isEmpty()){
			datosLiquidacion.setBONO_NO_IMPONIBLE_NOMBRE(BONO_NO_IMPONIBLE_NOMBRE);
			datosLiquidacion.setBONO_NO_IMPONIBLE(BONO_NO_IMPONIBLE);
		}
		
		//Agregar Listado de BONOS
		if(!HORAS_EXTRA.isEmpty()){
			datosLiquidacion.setHORAS_EXTRA(HORAS_EXTRA);
			datosLiquidacion.setHORAS_EXTRA_NHORAS(HORAS_EXTRA_NHORAS);
			datosLiquidacion.setHORAS_EXTRA_VHORAS(HORAS_EXTRA_VHORAS);
			datosLiquidacion.setHORAS_EXTRA_NOMBRE(HORAS_EXTRA_NOMBRE);
		}
		
		if(!HORAS_FALTA.isEmpty()){
			datosLiquidacion.setHORAS_FALTA(HORAS_FALTA);
			datosLiquidacion.setHORAS_FALTA_NHORAS(HORAS_FALTA_NHORAS);
			datosLiquidacion.setHORAS_FALTA_VHORAS(HORAS_FALTA_VHORAS);
			datosLiquidacion.setHORAS_FALTA_NOMBRE(HORAS_FALTA_NOMBRE);
		}
		
		if(!ANTICIPO.isEmpty()){
			datosLiquidacion.setANTICIPO(ANTICIPO);
			datosLiquidacion.setANTICIPO_NOMBRE(ANTICIPO_NOMBRE);
		}
		
		if(!IMPUESTO_UNICO.isEmpty()){
			datosLiquidacion.setIMPUESTO_UNICO(IMPUESTO_UNICO);
			datosLiquidacion.setIMPUESTO_UNICO_NOMBRE(IMPUESTO_UNICO_NOMBRE);
		}
		
		if(!DESCUENTOS.isEmpty()){
			datosLiquidacion.setDESCUENTOS(DESCUENTOS);
			datosLiquidacion.setDESCUENTOS_NOMBRE(DESCUENTOS_NOMBRE);
		}
		
		if(!CONCEPTO_8.isEmpty()){
			datosLiquidacion.setCONCEPTO_8(CONCEPTO_8);
			datosLiquidacion.setCONCEPTO_8_NOMBRE(CONCEPTO_8_NOMBRE);
		}
		
		if(!SALDO_ANTERIOR.isEmpty()){
			datosLiquidacion.setSALDO_ANTERIOR(SALDO_ANTERIOR);
			datosLiquidacion.setSALDO_ANTERIOR_NOMBRE(SALDO_ANTERIOR_NOMBRE);
		}
		
		if(!PLAN_ADICIONAL_NO_TRIBUTABLE.isEmpty()){
			datosLiquidacion.setPLAN_ADICIONAL_NO_TRIBUTABLE(PLAN_ADICIONAL_NO_TRIBUTABLE);
			datosLiquidacion.setPLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE(PLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE);
		}
		
		if(!PLAN_ADICIONAL_TRIBUTABLE.isEmpty()){
			datosLiquidacion.setPLAN_ADICIONAL_TRIBUTABLE(PLAN_ADICIONAL_TRIBUTABLE);
			datosLiquidacion.setPLAN_ADICIONAL_TRIBUTABLE_NOMBRE(PLAN_ADICIONAL_TRIBUTABLE_NOMBRE);
		}
		
		if(!CONCEPTO_MEDICO.isEmpty()){
			datosLiquidacion.setCONCEPTO_MEDICO(CONCEPTO_MEDICO);
			datosLiquidacion.setCONCEPTO_MEDICO_NOMBRE(CONCEPTO_MEDICO_NOMBRE);
		}
		
		if(!SOBREGIRO.isEmpty()){
			datosLiquidacion.setSOBREGIRO(SOBREGIRO);
			datosLiquidacion.setSOBREGIRO_NOMBRE(SOBREGIRO_NOMBRE);
		}
		
		return datosLiquidacion;
		
		
	}
	
	
	public static DocLiquidacion pruebaDocLiquidacion(DatosLiquidacion datos, GetDatosContratoTrabajador datosDuros) throws JsonProcessingException{
	
		
		DocLiquidacion docLiquidacion = new DocLiquidacion();
		
		// Transformar los datos a JSON
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String datosContratoTrabajadorJSON = ow.writeValueAsString(datosDuros);
		
		JsonStringParser j = new JsonStringParser();
		String[][] datosDuros2 = j.parser(datosContratoTrabajadorJSON.replaceAll("\"", "").replaceAll("\n", "").replaceAll("\r", "").trim());
		
		for (int i = 0; i < datosDuros2.length; i++) {
			
			String parameter = datosDuros2[i][0];
			String aux = "$$"+parameter.trim()+"$$";
			datosDuros2[i][0] = aux;
			
		}
		
		docLiquidacion.setDatosFijos(datosDuros2);
		
	
		//----------------------HABERES------------------------------//
	
			String[][] haberes = new String[10][3]; 
			
			haberes[0][0] = "1. HABERES IMPONIBLES";
			haberes[0][1] = "";
			haberes[0][2] = "TOTAL";
			
			haberes[1][0] = "SUELDO BASE";
			haberes[1][1] = "";
			haberes[1][2] = datos.getSUELDO_BASE();
			
			haberes[2][0] = "GRATIFICACION";
			haberes[2][1] = "";
			haberes[2][2] = datos.getGRATIFICACION();
			
			haberes[3][0] = "TOTAL HABERES IMPONIBLES";
			haberes[3][1] = "";
			haberes[3][2] = datos.getTOTAL_HAB_IMPONIBLE();
			
			haberes[4][0] = "2. HABERES NO IMPONIBLES";
			haberes[4][1] = "";
			haberes[4][2] = "TOTAL";
			
			haberes[5][0] = "TOTAL HABERES NO IMPONIBLES";
			haberes[5][1] = "";
			haberes[5][2] = datos.getTOTAL_HAB_NO_IMPONIBLE();
			
			haberes[6][0] = "TOTAL HABERES";
			haberes[6][1] = "";
			haberes[6][2] = datos.getTOTAL_HABERES();
			
			docLiquidacion.setTablaHaberes(haberes);
			
			//----------------------DESCUENTOS------------------------------//
			
			String[][] descuentos = new String[10][3];
			
			descuentos[0][0] = "1. DESCUENTOS LEGALES";
			descuentos[0][1] = "";
			descuentos[0][2] = "TOTAL";
			
			descuentos[1][0] = "DESCUENTOS PREVISIONAL";
			descuentos[1][1] = "AFP";
			descuentos[1][2] = datos.getAFP();
			
			String salud = String.valueOf(Double.valueOf(datos.getSALUD()) + Double.valueOf(datos.getCAJA()));
			String salud_porcentaje = String.valueOf(Double.valueOf(datos.getSALUD_PORCENTAJE().replace(",", ".")) + Double.valueOf(datos.getCAJA_PORCENTAJE().replace(",", ".")));
			String salud_nombre = datos.getSALUD_NOMBRE()+ " " +"("+salud_porcentaje+"%)";
			
			descuentos[2][0] = "DESCUENTOS SALUD";
			descuentos[2][1] = salud_nombre;
			descuentos[2][2] = salud.substring(0, salud.indexOf("."));
			
			descuentos[3][0] = "SEGURO CESANTIA";
			descuentos[3][1] = "";
			descuentos[3][2] = datos.getSEGURO_CESANTIA_AFC_TRABAJADOR();
			
			descuentos[4][0] = "TOTAL DESCUENTOS";
			descuentos[4][1] = "";
			descuentos[4][2] = datos.getTOTAL_DESCUENTOS();
			
			docLiquidacion.setTablaDescuentos(descuentos);
			
			//-----------------------TOTAL----------------------//
			
			String[][] total = new String[10][3]; 
			
			total[0][0] = "LIQUIDO A PAGAR";
			total[0][1] = "";
			total[0][2] = datos.getTOTAL_PAGO();
			
			total[1][0] = "SON: ";
			total[1][1] = "";
			total[1][2] = GeneralUtility.cantidadConLetra(datos.getTOTAL_PAGO().replace(".", ""));
			
			docLiquidacion.setTablaTotal(total);
			
			return docLiquidacion;
	
	}
	
	
}// fin clase

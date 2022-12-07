package SWDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lib.classSW.ImpuestoUnicoExcel;
import lib.classSW.OrdenDePagoPREVIRED;
import lib.db.ConnectionDB;
import wordCreator.utils;

public class sw_OrdenPagoPREVIREDB {
	
	public static ArrayList<OrdenDePagoPREVIRED> buscarDatosEmpresa(int idSociedad, int periodo) throws Exception {
		Statement ps = null;
		String sql = "";
		
		ConnectionDB db = new ConnectionDB();
		ArrayList<OrdenDePagoPREVIRED> Lista = new ArrayList<OrdenDePagoPREVIRED>();

		try {

			sql = "select upper((select denominacionSociedad from sociedad where idSociedad = "+idSociedad+" )) as SOCIEDAD,concepto AS CONCEPTO,descripcion AS DESCRIPCION ,proveedor as PROVEEDOR,"
					
					 +" CASE "
					    +"WHEN  UPPER((SELECT descripcion FROM parametros WHERE codSap = proveedor LIMIT 1)) is null THEN '' "
					    +"ELSE  UPPER((SELECT descripcion FROM parametros WHERE codSap = proveedor LIMIT 1)) "
					 +"END as  NOMBRE_PROVEEDOR, "
					
					+ "cast(sum(valor) as signed) as VALOR "
					+"from ( SELECT d.descripcion,sociedad,idConcepto, CASE WHEN idConcepto in (31,91,48) THEN 'AFP' "
					+"WHEN idConcepto in (92,33) and p.descripcion!='S.S.S.'  THEN 'AFP' "
					+"WHEN idConcepto in (92,33) and p.descripcion='S.S.S.' then  'AFC' "
					+"WHEN idConcepto in (32,42) THEN 'SALUD' "
					+"WHEN  idConcepto in (94,95,96,97,98) THEN 'COSTO PATRONAL' "
					+"WHEN idConcepto in (34) THEN 'APV' "
					+"WHEN idConcepto in (14,15,16,17) THEN 'ASIGNACIONES FAMILIARES' "
					+ "WHEN idConcepto IN (45) AND d.descripcion = 'CCAF LOS ANDES AHORRO' THEN 'AHORRO' "
					+ "WHEN idConcepto IN (45) AND d.descripcion = 'CCAF LOS ANDES CREDITOS' THEN 'CREDITOS' "
					+ "WHEN idConcepto IN (45) AND d.descripcion = 'SEGURO DE VIDA' THEN 'SEGURO DE VIDA' "
					+"else "
					+"idConcepto "
					+"END as concepto,"
					
					+"CASE " 
					+"WHEN  idConcepto in (-1) THEN 'COSTO PATRONAL' "
//					+"WHEN  idConcepto in (34) THEN p2.descripcion "
//					+ "WHEN  idConcepto in (45) and d.codSap!=2 THEN d.descripcion WHEN  idConcepto in (45) and d.codSap=2 THEN null WHEN  idConcepto in (45) and d.codSap is null then  d.descripcion WHEN  idConcepto in (98) THEN  d.descripcion "
					+" WHEN idConcepto IN (45) AND d.descripcion = 'CCAF LOS ANDES AHORRO' THEN d.proveedor "
					+ "WHEN idConcepto IN (45) AND d.descripcion = 'CCAF LOS ANDES CREDITOS' THEN d.proveedor "
					+ "WHEN idConcepto IN (45) AND d.descripcion = 'SEGURO DE VIDA' THEN d.proveedor "
					+"else "

					+"CASE "
					+"WHEN idConcepto=34 THEN  p2.codSap "
//					+"WHEN idConcepto in (45) and d.codSap!=2 then  d.proveedor WHEN idConcepto in (45) and d.codSap=2 then  null WHEN idConcepto in (45) and d.codSap is null then d.proveedor WHEN idConcepto in (92,33) and p.descripcion='S.S.S.' then  '0010001643' "
					+"WHEN  idConcepto in (98) THEN d.proveedor "
					
					+"else "

					+"p.codSap "
					+"END "
					+"END as proveedor, "
					+"valor " 
					
					+"FROM trabajadores t "
					+"inner join sw_liquidacionDetalle  d on (periodo= "+periodo+" "
//					+ "and d.idConcepto in (14,15,16,17,31,32,94,95,96,97,91,92,33,34,42,48) "
					+"AND (d.idConcepto IN (14 , 15, 16, 17, 31, 32, 94, 95, 96, 97, 91, 92, 33, 34, 42, 48) or (d.idConcepto = 45   and d.descripcion = 'CCAF LOS ANDES AHORRO')  or (d.idConcepto = 45 and d.descripcion = 'CCAF LOS ANDES CREDITOS')"
					+ " or (d.idConcepto = 45 and d.descripcion = 'SEGURO DE VIDA ')) "
					+"and t.codigo=d.codTrabajador) inner join contratos  c on (c.codigo_trabajador=d.codTrabajador and c.id = d.idContrato ) "
					+"inner join sociedad s on ( s.idSociedad=c.idSociedad and sociedad= (select sociedad from sociedad where idSociedad = "+idSociedad+")) "  
					+"left join parametros p on (p.codigo in ('AFP','ISAPRE','CAJA_COMPENSACION','MUTUALES') and p.rutParametro=d.proveedor ) "
					+"left join parametros p2 on (p2.codigo in ('APV') and p2.rutParametro=d.proveedor ) "
					
//					+"where periodo= "+periodo+" and idConcepto in (14,15,16,17,31,32,94,95,96,97,91,92,33,34,42,48) "
					+"where periodo= "+periodo+" AND (idConcepto IN (14 , 15, 16, 17, 31, 32, 94, 95, 96, 97, 91, 92, 33, 34, 42, 48,45) or (d.idConcepto = 45 and d.descripcion = 'CCAF LOS ANDES AHORRO')  or (d.idConcepto = 45 and d.descripcion = 'CCAF LOS ANDES CREDITOS')"
							+ " or (d.idConcepto = 45 and d.descripcion = 'SEGURO DE VIDA ') ) "


					+"and sociedad=  concat('',(select sociedad from sociedad where idSociedad = "+idSociedad+"),'')  and valor>0 )as m "
					+ "group by sociedad,concepto, descripcion,proveedor order by concepto ";

			
				
			
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int columnCount = rsmd.getColumnCount();

			OrdenDePagoPREVIRED tr1 = new OrdenDePagoPREVIRED();
			for (int i = 1; i <= columnCount; i++ ) {
			  String name = rsmd.getColumnName(i);
			  System.out.println(name);
			  	
			    
				if(i == 1){tr1.setSociedad(name);}
				else if(i == 2){tr1.setConcepto(name);}
				else if(i == 3){tr1.setDescripcion(name);}
				else if(i == 4){tr1.setProveedor(name);}
				else if(i == 5){tr1.setNombreproveedor(name);}
				else if(i == 6){tr1.setValor(name);}
				
				
			}
			
			Lista.add(tr1);

			while (rs.next()) {

				
				OrdenDePagoPREVIRED tr = new OrdenDePagoPREVIRED();
				
				tr.setSociedad(rs.getString("SOCIEDAD"));
				tr.setConcepto(rs.getString("CONCEPTO"));
				tr.setDescripcion(rs.getString("DESCRIPCION"));
				tr.setProveedor(rs.getString("PROVEEDOR"));
				tr.setNombreproveedor(rs.getString("NOMBRE_PROVEEDOR"));
				tr.setValor(rs.getString("VALOR"));
				

				Lista.add(tr);

			}
		


		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return Lista;
	}
	
	public static String generarExcelPREVIREDinstitucion(ArrayList<OrdenDePagoPREVIRED> detalleEmpresa) throws Exception {

		String RutaArchivo = "";
		
		String ruta = utils.AvisoInspeccionTrabajo();


		try {

			String Nombrearchivo = "ordendepago.xlsx";
			File archivo = new File("ordendepago.xlsx");

			// Creamos el libro de trabajo de Excel formato OOXML
			Workbook workbook = new XSSFWorkbook();
            
			CellStyle style2 = workbook.createCellStyle();
			 DataFormat format=workbook.createDataFormat();
			style2.setDataFormat(format.getFormat("#,##0"));
			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			
			//style2.setAlignment(CellStyle.ALIGN_RIGHT);
	       
			
			// La hoja donde pondremos los datos
			Sheet pagina = workbook.createSheet("Hoja1");
			pagina.getPrintSetup().setLandscape(true);
			pagina.setFitToPage(true);
			PrintSetup ps = pagina.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);

			Row fila = pagina.createRow(0);

			
			
			   	int numeroFor = 0;
			    
			   	String 	sociedad_ = "";
			   	String  concepto_;
				String  proveedor_;
				String  nombreproveedor_;
				String  valor_;
			
				for (OrdenDePagoPREVIRED emplista : detalleEmpresa) {
					
					sociedad_= emplista.getSociedad();
					concepto_ = emplista.getConcepto();
					proveedor_ = emplista.getProveedor();
					valor_= emplista.getValor();
					nombreproveedor_ = emplista.getNombreproveedor();
					
					
					
					String[] titulo7 = { "" + sociedad_ + "", "" + concepto_ + "",
							"" + proveedor_ + "", "" + nombreproveedor_ + "", "" + valor_ + "" };
					
					if(numeroFor == 0){
					Cell celda = fila.createCell(0);
					celda.setCellValue(titulo7[0]);
					celda.setCellStyle(style);
					
					
					}else{
					Cell celda = fila.createCell(0);
					celda.setCellValue(titulo7[0]);
					
				    }
					
					if(numeroFor == 0){
					Cell celda1 = fila.createCell(1);
					celda1.setCellValue(titulo7[1]);
					celda1.setCellStyle(style);
					}else{
					Cell celda1 = fila.createCell(1);
					celda1.setCellValue(titulo7[1]);
					}
				
					
					
					
					if(numeroFor == 0){
						Cell celda3 = fila.createCell(2);
						celda3.setCellValue(titulo7[2]);
						celda3.setCellStyle(style);
						
						
						}else{
						Cell celda3 = fila.createCell(2);
						celda3.setCellValue(titulo7[2]);
						
					    }
					
					if(numeroFor == 0){
						Cell celda4 = fila.createCell(3);
						celda4.setCellValue(titulo7[3]);
						celda4.setCellStyle(style);
						
						
						}else{
						Cell celda4 = fila.createCell(3);
						celda4.setCellValue(titulo7[3]);
						
					    }
					
					
					if(numeroFor == 0){
					Cell celda5 = fila.createCell(4);
					celda5.setCellValue(titulo7[4]);
					celda5.setCellStyle(style);
					}else{
					Cell celda5 = fila.createCell(4);
					
					if("ASIGNACIONES FAMILIARES".equals(titulo7[1])){
						celda5.setCellValue(-Integer.parseInt(titulo7[4]));
						celda5.setCellStyle(style2);
					}else{
						celda5.setCellValue(Integer.parseInt(titulo7[4]));
						celda5.setCellStyle(style2);
					}
					}
					
					
					numeroFor = numeroFor + 1;
					fila = pagina.createRow(numeroFor);
				}

			pagina.autoSizeColumn(0);
			pagina.autoSizeColumn(1);
			pagina.autoSizeColumn(2);
			pagina.autoSizeColumn(3);
			pagina.autoSizeColumn(4);
		

			FileOutputStream salida = new FileOutputStream(ruta + archivo);
			RutaArchivo = ruta + Nombrearchivo;

			// Almacenamos el libro de
			// Excel via ese
			// flujo de datos
			workbook.write(salida);

			// Cerramos el libro para concluir operaciones
			workbook.close();

			// LOGGER.log(Level.INFO, "Archivo creado existosamente en {0}",
			// archivo.getAbsolutePath());
		} catch (FileNotFoundException ex) {
			// LOGGER.log(Level.SEVERE, "Archivo no localizable en sistema de
			// archivos");
		} catch (IOException e) {
			e.printStackTrace();
			// LOGGER.log(Level.SEVERE, "Error de entrada/salida");
			return "";
		}

		return RutaArchivo;
		
	}
	
	
	public static ArrayList<OrdenDePagoPREVIRED> getallPeriodoOrdenDePago(String empr) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<OrdenDePagoPREVIRED> data = new ArrayList<OrdenDePagoPREVIRED>();
		ConnectionDB db = new ConnectionDB();

		try {

			sql = "SELECT distinct periodo FROM Previred_txt_periodo WHERE sociedad = "+empr+" and centralizado = 0";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				OrdenDePagoPREVIRED e = new OrdenDePagoPREVIRED();

				e.setPeriodo(rs.getInt("periodo"));

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
	
	public static ArrayList<OrdenDePagoPREVIRED> allPeridoOrdendepagoEstado1(String empr) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<OrdenDePagoPREVIRED> data = new ArrayList<OrdenDePagoPREVIRED>();
		ConnectionDB db = new ConnectionDB();

		try {

			sql = "SELECT distinct periodo FROM Previred_txt_periodo WHERE sociedad = "+empr+" and centralizado = 1 ORDER BY periodo DESC";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				OrdenDePagoPREVIRED e = new OrdenDePagoPREVIRED();

				e.setPeriodo(rs.getInt("periodo"));

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
	

	
	public static ArrayList<OrdenDePagoPREVIRED> DetalleordendepagoPrevired(String soc, String peri) throws Exception {
	PreparedStatement ps = null;
	String sql = "";
	ArrayList<OrdenDePagoPREVIRED> data = new ArrayList<OrdenDePagoPREVIRED>();
	ConnectionDB db = new ConnectionDB();

	try {

		sql = "SELECT "
				+ "(select UPPER(denominacionSociedad) from sociedad where idSociedad = "+soc+") as sociedad,"
				+ "(select sociedad from sociedad where idSociedad = "+soc+") as sociedad_cod,"
				+ " periodo as pe, concepto, nombre_proveedor,proveedor, valor,"
				+ "(select sum(valor) FROM Previred_txt_periodo WHERE sociedad = "+soc+" and periodo = "+peri+") as total"
				+ " FROM Previred_txt_periodo WHERE sociedad = "+soc+" and periodo = "+peri+" ";
		
        System.out.println(sql);
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while (rs.next()) {
			OrdenDePagoPREVIRED e = new OrdenDePagoPREVIRED();

			e.setSociedad(rs.getString("sociedad"));
			e.setPeriodo(rs.getInt("pe"));
			e.setConcepto(rs.getString("concepto"));
			e.setNombreproveedor(rs.getString("nombre_proveedor"));
			e.setValorInt(rs.getInt("valor"));
			e.setTotal(rs.getInt("total"));
			e.setProveedor(rs.getString("proveedor"));
			e.setSociedadCod(rs.getString("sociedad_cod"));

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
	
	public static int obtenerNCuentaSapPagoPREVIRED() throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "select n_cuenta from Cuentas_SAP where id = 2";

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			db.conn.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLimitesAll: " + e.getMessage());
		} finally {
			db.close();
		}
		return total;
	}
	
	public static int obtenerProvvedorSapPagoPREVIRED() throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "select n_cuenta from Cuentas_SAP where id = 3";

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			db.conn.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLimitesAll: " + e.getMessage());
		} finally {
			db.close();
		}
		return total;
	}
	
	
	
	
	public static String buscarsiestacentralizadoPREVIRED (OrdenDePagoPREVIRED r) throws Exception{
		Statement ps4 = null;
		String respuesta = "";
		String sql4="";
		ConnectionDB db = new ConnectionDB();
		
		try{
			
			sql4 = "select * from Previred_txt_periodo where sociedad = "+r.getSociedad()+" and periodo = "+r.getPeriodo()+" and centralizado = 1 ";
			ps4 = db.conn.prepareStatement(sql4);
			System.out.println(sql4);
			ResultSet rs4 = ps4.executeQuery(sql4);
			
			if (!rs4.isBeforeFirst()) {
				respuesta = "no data";
				return respuesta;
				
			}else{
				respuesta = "centralizado";
				return respuesta;
			}
			
			
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
			
			db.close();
		}
		respuesta = "no";
		return respuesta;
	}
	
	public static ArrayList<OrdenDePagoPREVIRED> rowperidoCentralizacionPREVIRED(String soc, String peri) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<OrdenDePagoPREVIRED> data = new ArrayList<OrdenDePagoPREVIRED>();
		ConnectionDB db = new ConnectionDB();

		try {

			sql = "SELECT "
					+"(select upper(denominacionSociedad) from sociedad where idSociedad = PTP.sociedad) as nombresociedad,"
					+"PTP.sociedad,"
					+"PTP.periodo,"
					+"PTP.documento_sap,"
					+"sum(IF( PTP.concepto = 'ASIGNACIONES FAMILIARES', -PTP.valor, PTP.valor ) ) as valor FROM Previred_txt_periodo PTP WHERE PTP.sociedad = "+soc+" and PTP.centralizado = 1 and PTP.periodo = "+peri+" "
					+"group by PTP.documento_sap "
					+"order by PTP.periodo desc";
			
	        System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				OrdenDePagoPREVIRED e = new OrdenDePagoPREVIRED();

				e.setSociedad(rs.getString("nombresociedad"));
				e.setPeriodo(rs.getInt("periodo"));
				e.setValorInt(rs.getInt("valor"));
				e.setNumerodocumento(rs.getString("documento_sap"));
				e.setSociedadint(rs.getInt("sociedad"));
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
	
	public static Blob getTXTPREVIREDEmpresaPeriodo(String periodo_,String empresa_) throws Exception {

		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try {

			ps = db.conn.createStatement();
			sql = "SELECT archivo FROM sw_previred_file WHERE concepto = 'TXT' and sociedad ="+empresa_+" and periodo = "+periodo_+"";
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				Blob liquidacion = rs.getBlob(1);
				return liquidacion;
			}
			rs.close();
			ps.close();
			db.conn.close();

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLiquidacion: " + e.getMessage());
		} finally {
			db.close();
		}

		return null;
	}
	
	public static Blob getExcelPREVIREDEmpresaPeriodo(String periodo_,String empresa_) throws Exception {

		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try {

			ps = db.conn.createStatement();
			sql = "SELECT archivo FROM sw_previred_file WHERE concepto = 'EXCEL' and sociedad ="+empresa_+" and periodo = "+periodo_+"";
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				Blob liquidacion = rs.getBlob(1);
				return liquidacion;
			}
			rs.close();
			ps.close();
			db.conn.close();

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLiquidacion: " + e.getMessage());
		} finally {
			db.close();
		}

		return null;
	}
	
	public static boolean UpdateEstadoCentralizacionPREVIRED(OrdenDePagoPREVIRED map) throws Exception{
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		String sql = "";
		String sql2 = "";
		ConnectionDB db = new ConnectionDB();
		try{
		
			sql = "UPDATE sw_previred_file SET centralizado = 0  WHERE sociedad = ? and periodo = ?";
			ps = db.conn.prepareStatement(sql);
			
			ps.setInt(1, map.getSociedadint());
			ps.setInt(2, map.getPeriodo());
			
			ps.execute();
			
			sql2 = "UPDATE Previred_txt_periodo SET centralizado = 0  WHERE sociedad = ? and periodo = ? ";
			ps2 = db.conn.prepareStatement(sql2);
			
			
			ps2.setInt(1, map.getSociedadint());
			ps2.setInt(2, map.getPeriodo());
			
			ps2.execute();
			return true;
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			ps.close();
			ps2.close();
			db.conn.close();
		}
		return false;
	}

}

package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lib.classSW.Documentos;
import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;

public class DocumentosDB {

	private final static Logger LOG = LoggerFactory.getLogger(DocumentosDB.class);
	
	public static int insertDocumentos(Documentos documentos) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ResultSet rs = null;
		int i = 1;

		try {

			sql = " SELECT idTrabajadorDocumentos FROM sw_documentos WHERE codTrabajador = ? and tipoDocumento = ? ";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++, documentos.getCodTrabajador());
			ps.setInt(i++, documentos.getTipoDocumento());
			rs = ps.executeQuery();
			
			if (!rs.next()) {
				i = 1;
				rs.close();
				ps.close();

				sql = " INSERT INTO sw_documentos (idTrabajadorDocumentos) VALUES (?) ";
				ps = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(i++, documentos.getIdTrabajadorDocumentos());
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				int key = 0;
				if (rs.next()) {
					key = rs.getInt(1);
				}
				
				documentos.setIdTrabajadorDocumentos(key);

			}

			i = 1;
			documentos.setIdTrabajadorDocumentos(rs.getInt(1));
			rs.close();
			ps.close();
			
			sql = " UPDATE sw_documentos SET " + " codTrabajador = ?, " + " tipoDocumento = ?, " + " documento = ?, "
					+ " nombreDocumento = ? " + " WHERE idTrabajadorDocumentos = ? ";

			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++, documentos.getCodTrabajador());
			ps.setInt(i++, documentos.getTipoDocumento());
			ps.setBlob(i++, documentos.getDocumento());
			ps.setString(i++, documentos.getNombreDocumento());
			ps.setInt(i++, documentos.getIdTrabajadorDocumentos());

			ps.executeUpdate();

			rs.close();
			ps.close();

			return documentos.getIdTrabajadorDocumentos();

		} catch (Exception ex) {
			LOG.error("ERRORES AL SUBIR IMAGEN : "+ ps.toString());
			rs.close();
			ps.close();
		}

		return 0;
	}

	// TODO: NO LO USO, BORRAR
	public static boolean updateDocumento(Documentos documentos) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		// ResultSet rs = null;
		int i = 1;

		try {
			sql = " UPDATE sw_documentos SET " + " documento = ? " + " WHERE idTrabajadorDocumentos = ? ";

			ps = db.conn.prepareStatement(sql);

			ps.setBlob(i++, documentos.getDocumento());
			ps.setInt(i++, documentos.getIdTrabajadorDocumentos());

			// rs.close();
			ps.close();

		} catch (Exception e) {
			System.out.println("Error a Subir el Archivo a la Tabla" + e);
			// rs.close();
			ps.close();

		}

		return false;
	}

	// Obtener documento de la tabla sw_documentos
	public static Documentos getDocumentosWithFilter(ArrayList<filterSql> filter) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		Documentos documentos = new Documentos();

		try {

			// Crear sentencia en Sql
			sql = " SELECT * FROM sw_documentos ";
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

						else if (GeneralUtility.isNumeric(row.getValue())) {
							sql += andSql + row.getCampo() + " = '" + row.getValue() + "'";
						}
						else{
							sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
						}
						andSql = " and ";
					}
				} // Fin While

			}

			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				documentos = DocumentosDB.setObjectDocumento(rs);

			}

			// Retornar Lista de Trabajadores
			return documentos;

			// Fin Try
		} catch (Exception e) {
			throw new Exception("getAllTrabajadoresWithFilter: " + e.getMessage());
		} finally {
			db.close();
		}

	}// Fin getAllTrabajadors

	
	public static boolean deleteDocumentos(ArrayList<filterSql> filter) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		// ResultSet rs = null;

		try {
			sql = " DELETE FROM sw_documentos WHERE ";
			
			// Si contiene datos asignarlo al WHERE
						if (filter.size() > 0) {
							String andSql = "";
							andSql += " ";
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

									else if (GeneralUtility.isNumeric(row.getValue())) {
										sql += andSql + row.getCampo() + " = '" + row.getValue() + "'";
									}
									else{
										sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
									}
									
									andSql = " and ";
								}
							} // Fin While

						}

			ps = db.conn.prepareStatement(sql);
			ps.execute();

		} catch (Exception e) {
			System.out.println("Error al Eliminar documento al trabajador" + e);
			// rs.close();
			ps.close();

		}

		return false;
	}
	
	
	
	
	
	
	
	// Metodo para Obtener todos los datos del Objeto documento
	public static Documentos setObjectDocumento(ResultSet rs) throws Exception {

		Documentos documentos = new Documentos();

		documentos.setCodTrabajador(rs.getInt("codTrabajador"));
		documentos.setDocumento(rs.getBlob("documento"));
		documentos.setIdTrabajadorDocumentos(rs.getInt("idTrabajadorDocumentos"));
		documentos.setNombreDocumento(rs.getString("nombreDocumento"));
		documentos.setTipoDocumento(rs.getInt("tipoDocumento"));

		return documentos;

	}
	
	
	public static Documentos setObjectDocumentoPrevired(ResultSet rs) throws Exception {

		Documentos documentos = new Documentos();

		
		documentos.setDocumento(rs.getBlob("archivo"));
		

		return documentos;

	}
	
	
	public static Documentos getDocumentosWithFilter2(String soc, String per) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		Documentos documentos = new Documentos();

		try {

			// Crear sentencia en Sql
			sql = " SELECT archivo FROM sw_previred_file WHERE sociedad = "+soc+" and periodo = "+per+" ";
			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				
				documentos = DocumentosDB.setObjectDocumentoPrevired(rs);

			}

			return documentos;

			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			db.close();
		}

	}
}

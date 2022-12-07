package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import lib.classSW.TrabajadorDocumentos;
import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;

public class TrabajadorDocumentosDB {

	// Obtener todos los Documentos con Filtros
	public static ArrayList<TrabajadorDocumentos> getAllTrabajadorDocumentosWithFilter(ArrayList<filterSql> filter) throws Exception {

		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<TrabajadorDocumentos> lista = new ArrayList<TrabajadorDocumentos>();

		try {

			stmt = db.conn.createStatement();
			sql = " SELECT * FROM sw_r_trabajadorDocumentos ";

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
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >= '"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						}

						else
							sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
						andSql = " and ";

					} // Fin While

				}

			} // Fin if (filter size)

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				TrabajadorDocumentos doc = new TrabajadorDocumentos();
				doc = setObjectTrabajadorDocumentos(rs);
				lista.add(doc);

			}
			rs.close();
			stmt.close();
			db.conn.close();
			// Fin Try
		} catch (Exception e) {

			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getAllContrato: " + e.getMessage());

		} finally {
			db.close();
		}

		return lista;
	}

	private static TrabajadorDocumentos setObjectTrabajadorDocumentos(ResultSet rs) throws SQLException {

		TrabajadorDocumentos doc = new TrabajadorDocumentos();

		doc.setIdTrabajadorDocumentos(rs.getInt("idTrabajadorDocumentos"));
		doc.setIdTrabajador(rs.getInt("idTrabajador"));
		doc.setIdTemplate(rs.getInt("idTemplate"));
		doc.setCodTrabajador(rs.getInt("codTrabajador"));
		doc.setImpreso(rs.getInt("impreso"));
		doc.setFechaImpresion(rs.getString("fechaImpresion"));
		doc.setGeneradoPor(rs.getInt("generadoPor"));

		return doc;

	}
	
	// Insertar Trabajador Documentos
		public static boolean insertTrabajadorDocumentos(TrabajadorDocumentos trabajadorDocumentos) throws Exception{
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			int i = 0;

			try{
				sql = " INSERT into sw_r_trabajadorDocumentos "
						+ " VALUES (?,?,?,?,?,?,?)";
				ps = db.conn.prepareStatement(sql);
				
				ps.setInt(i++, trabajadorDocumentos.getIdTrabajadorDocumentos());
				ps.setInt(i++, trabajadorDocumentos.getIdTrabajador());
				ps.setInt(i++, trabajadorDocumentos.getIdTemplate());
				ps.setInt(i++, trabajadorDocumentos.getCodTrabajador());
				ps.setInt(i++, trabajadorDocumentos.getImpreso());
				ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(trabajadorDocumentos.getFechaImpresion()));
				ps.setInt(i++, trabajadorDocumentos.getGeneradoPor());			
				
				ps.execute();
				return true;
			}catch(Exception e){

				System.out.println("Error al ingresar Trabajador licencia maternal:" + e.getMessage());
				e.printStackTrace();

			}finally{
				ps.close();
				db.conn.close();
			}
			return false;
		}// fin clase insert
	
	
		//update trabajador licencia conducir
		public static boolean updateTrabajadorDocumentos(TrabajadorDocumentos TrabajadorDocumentos) throws Exception{

			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			ResultSet rs = null;

			try{

				int i = 1;

				sql = " SELECT codTrabajador, idTemplate FROM sw_r_trabajadorDocumentos  WHERE codTrabajador = ? and idTemplate = ? ";
				ps = db.conn.prepareStatement(sql);
				ps.setInt(i++, TrabajadorDocumentos.getCodTrabajador());
				ps.setInt(i++, TrabajadorDocumentos.getIdTemplate());
				rs = ps.executeQuery();

				if(!rs.next()){
					i=1;
					rs.close();
					ps.close();

					sql= " INSERT INTO sw_r_trabajadorDocumentos (idTrabajadorDocumentos,idTrabajador,idTemplate,codTrabajador,impreso,fechaImpresion,generadoPor) VALUES (?,?,?,?,?,now(),?) ";
					ps = db.conn.prepareStatement(sql);
					ps.setInt(i++, TrabajadorDocumentos.getIdTrabajadorDocumentos());
					ps.setInt(i++, TrabajadorDocumentos.getIdTrabajador());
					ps.setInt(i++, TrabajadorDocumentos.getIdTemplate());
				    ps.setInt(i++, TrabajadorDocumentos.getCodTrabajador());
				    ps.setInt(i++, TrabajadorDocumentos.getImpreso());
				    ps.setInt(i++, TrabajadorDocumentos.getGeneradoPor());
					ps.executeUpdate();
					
				}

				i=1;
				rs.close();
				ps.close();

				return true;

			}catch (Exception e) {
				System.out.println("Error update Tabajador Documentos: " + e.getMessage());
			}finally {
				ps.close();
				db.close();
			}		
			return false;
		}// fin metodo update
		
	
	

}

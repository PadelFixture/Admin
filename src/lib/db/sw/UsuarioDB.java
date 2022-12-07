package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lib.classSW.Usuario;
import lib.classSW.VacacionesTrabajador;
import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;

public class UsuarioDB {

	
	private final static Logger LOG = LoggerFactory.getLogger(UsuarioDB.class);

	// Obtener Todos los Trabajadores con filtros
	public static ArrayList<Usuario> getUsuarios(ArrayList<filterSql> filter) throws Exception {

			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();

			ArrayList<Usuario> lista = new ArrayList<Usuario>();

			try {

				// Crear sentencia en Sql
				sql = " SELECT * FROM usuario ";
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
							else if (row.getCampo().endsWith("usuario")) {
								sql += andSql + row.getCampo() + " like '" + GeneralUtility.FormatearRUT(row.getValue()) + "'";
							}
							else

								sql += andSql + row.getCampo() + " like '" + row.getValue() + "'";

							andSql = " and ";
						}
					} // Fin While

				}

				ResultSet rs = ps.executeQuery(sql);

				while (rs.next()) {

					Usuario usuario = UsuarioDB.setObjectUsuario(rs);
					lista.add(usuario);

				}
				
				//Retornar Lista de Trabajadores
				return lista;
				
				// Fin Try
			} catch (Exception e) {
				LOG.error("getUsuarios:  " + e.getMessage() + " SQL: " + ps.toString());
				throw new Exception("getUsuarios: " + e.getMessage());
			} finally {
				ps.close();
				db.close();
			}

		}// get Usuarios

	private static Usuario setObjectUsuario(ResultSet rs) throws SQLException {
		
		Usuario usuario = new Usuario();
		
		usuario.setCodigo(rs.getString("codigo"));
		usuario.setUsuario(rs.getString("usuario"));
		usuario.setClave(rs.getString("clave"));
		usuario.setPerfil(rs.getString("perfil"));
		usuario.setCodigo_persona(rs.getString("codigo_persona"));
	
		return usuario;
		
	}
	
	
	
	
}

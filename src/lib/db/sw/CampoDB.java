package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import lib.classSA.CampoEspecie;
import lib.classSA.VARIEDAD;
import lib.classSA.CUARTEL;
import lib.classSW.Campo;
import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;

public class CampoDB {

	// Obtener Campo por Id
	public static ArrayList<Campo> getCampoBySociedad(int sociedad) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;

		ArrayList<Campo> lista = new ArrayList<Campo>();

		try {
			sql = "SELECT * FROM campo INNER JOIN sociedad ON campo.sociedad = sociedad.sociedad WHERE sociedad.idSociedad = ? ";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++, sociedad);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Campo c = new Campo();
				c = setObjectCampo(rs);
				lista.add(c);

			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	// Obtener Campo por Id
	public static ArrayList<Campo> getCampoWithFilter(ArrayList<filterSql> filter) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<Campo> lista = new ArrayList<Campo>();

		try {

			sql = "SELECT * FROM campo ";

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
						} else if (row.getCampo().endsWith("_from")) {

							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						} else if (row.getValue().split(",").length >= 2) {
							sql += andSql + row.getCampo() + " in ( " + row.getValue() + " ) ";
						} else {
							if (GeneralUtility.isNumeric(row.getValue())) {
								sql += andSql + row.getCampo() + " = " + row.getValue() + "";
							} else {
								sql += andSql + row.getCampo() + " like '%" + row.getValue().replace("\"", "") + "%'";
							}
						}

						andSql = " and ";
					}
				} // Fin While

			}

			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Campo c = new Campo();
				c = setObjectCampo(rs);
				lista.add(c);

			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	// Obtener Campo por Grupo
	public static Campo getCampoByGrupo(String grupo) throws Exception {

		String sql = "SELECT * FROM campo WHERE grupo = ? ";
		ConnectionDB db = new ConnectionDB();

		try (PreparedStatement ps = db.conn.prepareStatement(sql)) {

			int i = 1;
			Campo c = new Campo();
			ps.setString(i++, grupo);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				c = setObjectCampo(rs);
			}

			return c;

		}

	}

	// Obtener Campo por codigo del Campo
	public static ArrayList<Campo> getCampoByCampo(String codCampo) throws Exception {

		String sql = "SELECT * FROM campo WHERE campo = ? ";
		ConnectionDB db = new ConnectionDB();

		try (PreparedStatement ps = db.conn.prepareStatement(sql)) {

			int i = 1;
			ArrayList<Campo> lista = new ArrayList<Campo>();
			ps.setString(i++, codCampo);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Campo c = new Campo();
				c = setObjectCampo(rs);
				lista.add(c);
			}

			return lista;

		}

	}

	// Obtener Campo por Id
	public static Campo getCampoById(String id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;

		Campo c = new Campo();

		try {
			sql = "SELECT * FROM campo WHERE codigo = ? ";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++, Integer.parseInt(id));
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				c = setObjectCampo(rs);

			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return c;
	}

	// Obtener Todos las Campo
	public static ArrayList<Campo> getCampo() throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ArrayList<Campo> lista = new ArrayList<Campo>();
		ConnectionDB db = new ConnectionDB();

		try {

			sql = "select * from campo";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Campo c = new Campo();
				c = setObjectCampo(rs);
				lista.add(c);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}

		return lista;

	}

	public static Campo setObjectCampo(ResultSet rs) throws SQLException {

		Campo c = new Campo();

		c.setCodigo(rs.getInt("codigo"));
		c.setCampo(rs.getString("campo"));
		c.setSociedad(rs.getString("sociedad"));
		c.setDescripcion(rs.getString("descripcion"));
		c.setNombre(rs.getString("nombre"));
		c.setDirecion(rs.getString("direcion"));
		c.setTelefono(rs.getString("telefono"));
		c.setGerente_zonal(rs.getString("gerente_zonal"));
		c.setAdm_campo(rs.getString("adm_campo"));
		c.setZona(rs.getString("zona"));
		c.setSubsidio(rs.getString("subsidio"));
		c.setGeoreferencia(rs.getString("georeferencia"));
		c.setGeoreferencia(rs.getString("georeferencia"));
		c.setCecos(rs.getString("cecos"));
		c.setGrupo_ceco_work(rs.getString("grupo_ceco_work"));
		c.setGrupo_co(rs.getString("grupo_co"));
		

		String grupo = "";
		try {
			grupo = rs.getString("grupo_ceco_work");
			if (grupo == null || grupo == "") {
				grupo = rs.getString("grupo");
			}
		} catch (Exception e) {
			grupo = rs.getString("grupo");
		}

		c.setGrupo(grupo);
		c.setGrupo_maquinaria(rs.getString("grupo_maquinaria"));
		c.setArea(rs.getString("area"));

		return c;

	}

	public static ArrayList<CampoEspecie> getEspeciesByCampo(String Campo) {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CampoEspecie> lista = new ArrayList<CampoEspecie>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select  distinct(especie.codigo), especie.especie  from campo, cuartel "
					+ "inner join especie on cuartel.especie=especie.codigo "
					+ "WHERE sector like concat(campo.campo,'','%') and campo.campo='" + Campo + "'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				CampoEspecie ob = new CampoEspecie();

				ob.setCodigo_especie(rs.getInt("codigo"));
				ob.setNombre_especie(rs.getString("especie"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}

	public static ArrayList<VARIEDAD> getVariedadByCampoEspecie(String codigoCampo, String codigoEspecie) {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<VARIEDAD> lista = new ArrayList<VARIEDAD>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select distinct(cuartel.variedad) as codigo, variedad.variedad  from campo,cuartel "
					+ "inner join especie on cuartel.especie=especie.codigo "
					+ "left join variedad on cuartel.variedad=variedad.codigo " + "WHERE sector like concat('"
					+ codigoCampo + "','','%') and (especie.codigo in " + codigoEspecie + "  or " + codigoEspecie
					+ " = '')";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				VARIEDAD ob = new VARIEDAD();
				ob.setCodigo(rs.getInt("codigo"));

				ob.setVariedad(rs.getString("variedad"));

				lista.add(ob);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}

	public static ArrayList<Campo> getCampoWithCuartel() throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<Campo> lista = new ArrayList<Campo>();
		ConnectionDB db = new ConnectionDB();

		try {

			sql = "select distinct(campo.campo),descripcion from campo, cuartel "
					+ "WHERE sector like concat(campo.campo,'','%')";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Campo c = new Campo();
				c.setCampo(rs.getString("campo"));
				c.setDescripcion(rs.getString("descripcion"));
				lista.add(c);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}

		return lista;
	}

	public static ArrayList<CUARTEL> getCuartelByVariedadCampoEspecie(String codigoCampo, String codigoEspecie,
			String codigoVariedad) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CUARTEL> lista = new ArrayList<CUARTEL>();
		ConnectionDB db = new ConnectionDB();

		try {
			System.out.println(codigoVariedad);
			sql = "select *,cuartel.estado estadoP, CONCAT(codigo_cuartel, ' ', nombre) as nombreCuartel from cuartel "
					+ "inner join (select especie.codigo as codigoEspecie, especie.especie as nombreEspecie from especie) as e on cuartel.especie=e.codigoEspecie "
					+ "inner join (select codigo as codigoVariedad, variedad as nombreVariedad from variedad  "
					+ codigoVariedad + " )as c on cuartel.variedad=c.codigoVariedad " + "WHERE sector like concat('"
					+ codigoCampo + "','','%') and e.codigoEspecie='" + codigoEspecie + "'";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				CUARTEL c = new CUARTEL();
				c.setCodigo(rs.getInt("codigo"));
				c.setCodigo_cuartel(rs.getString("codigo_cuartel"));
				c.setNombre(rs.getString("nombreCuartel"));
				c.setSector(rs.getString("sector"));
				c.setVariedad(rs.getInt("variedad"));
				c.setNvariedad(rs.getString("nombreVariedad"));
				c.setEspecie(rs.getInt("codigoEspecie"));
				c.setNespecie(rs.getString("nombreEspecie"));
				c.setPatron(rs.getString("patron"));
				c.setAno_plantacion(rs.getString("ano_plantacion"));
				c.setSuperficie(rs.getFloat("superficie"));
				c.setPlantas(rs.getInt("plantas"));
				c.setDistancia_largo(rs.getFloat("distancia_largo"));
				c.setDistancia_hancho(rs.getFloat("distancia_hancho"));
				c.setFormacion(rs.getString("formacion"));
				c.setVivero(rs.getString("vivero"));
				c.setTipo_planta(rs.getString("tipo_planta"));
				c.setTipo_control_heladas(rs.getString("tipo_control_heladas"));
				c.setTipo_proteccion(rs.getString("tipo_proteccion"));
				c.setLimitante_suelo(rs.getString("limitante_suelo"));
				c.setPolinizante(rs.getString("polinizante"));
				c.setEstado(rs.getInt("estado"));
				c.setTipo_plantacion(rs.getString("tipo_plantacion"));
				c.setClon(rs.getString("clon"));
				c.setCeco(rs.getString("ceco"));
				if (rs.getString("estadoP").equals("1")) {
					c.setCeco(rs.getString("ceco"));
				} else {
					c.setCeco(rs.getString("ordenco"));
				}

				lista.add(c);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}

		return lista;
	}

	public static ArrayList<Campo> getCampoBySoc(String sociedad) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		//int i = 1;

		ArrayList<Campo> lista = new ArrayList<Campo>();

		try {
			sql = "SELECT * FROM campo INNER JOIN sociedad ON campo.sociedad = sociedad.sociedad WHERE sociedad.sociedad = '"+sociedad+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Campo c = new Campo();
				c = setObjectCampo(rs);
				lista.add(c);

			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}
	
	public static ArrayList<Campo> getCampoByCodigoSociedad(String sociedad) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;

		ArrayList<Campo> lista = new ArrayList<Campo>();

		try {
			sql = "SELECT * FROM campo INNER JOIN sociedad ON campo.sociedad = sociedad.sociedad WHERE sociedad.sociedad = ? ";
			ps = db.conn.prepareStatement(sql);
			ps.setString(i++, sociedad);
           System.out.println(sql);
           System.out.println(sociedad);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Campo c = new Campo();
				c = setObjectCampo(rs);
				lista.add(c);

			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

}

package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSW.CuentaBancariaPostulantes;
import lib.db.ConnectionDB;

public class CuentaBancariaPostulantesDB {


	//Insert Cuenta Bancaria
	public static boolean insertCuentaBancariaPostulantes(CuentaBancariaPostulantes cb) throws Exception{

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try{
			sql = "INSERT INTO sw_cuentaBancariaPostulantes (idCuentaBancaria, idTrabajador, idTipoCuenta, nCuenta, idBanco, cuentaPrimaria, estado, codigoTrabajador )"
					+ " VALUES (?,?,?,?,?,?,?,?)";

			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, cb.getIdCuentaBancaria());
			ps.setInt(2, cb.getIdTrabajador());
			ps.setInt(3, cb.getIdTipoCuenta());
			ps.setString(4, cb.getnCuenta());
			ps.setInt(5, cb.getIdBanco());
			ps.setInt(6, cb.getCuentaPrimaria());
			ps.setInt(7, cb.getEstado());
			ps.setInt(8, cb.getCodigoTrabajador());
			
			ps.execute();

			return true;

		}catch(Exception e){

			System.out.println("Error insertCuentaBancariaPostulantes:" + e.getMessage());
			e.printStackTrace();

		}finally{
			db.conn.close();
		}
		return false;

	}

	//update or insert Cuenta Bancaria
	public static boolean updateOrInsertCuentaBancariaPostulantes(CuentaBancariaPostulantes cuentaBancaria) throws  Exception{

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ResultSet rs = null;


		try{

			int i = 1;

			sql = " SELECT idCuentaBancaria FROM sw_cuentaBancariaPostulantes WHERE idCuentaBancaria = ? ";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++, cuentaBancaria.getIdCuentaBancaria());
			rs = ps.executeQuery();

			if(!rs.next()){
				i=1;
				rs.close();
				ps.close();

				sql= " INSERT INTO sw_cuentaBancariaPostulantes (idTrabajador) VALUES (?) ";
				ps = db.conn.prepareStatement(sql);
				ps.setInt(i++, cuentaBancaria.getIdTrabajador());
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				int key = 0;
				if(rs.next()){
					key = rs.getInt(1);
				}

				cuentaBancaria.setIdCuentaBancaria(key);

			}

			i=1;
			rs.close();
			ps.close();

			sql = " UPDATE sw_cuentaBancariaPostulantes SET "
					+" idTrabajador = ?, "
					+" idTipoCuenta = ?, "
					+" nCuenta = ?, "
					+" idBanco = ?, "
					+" estado = ?, "
					+" cuentaPrimaria = ?, "
					+" codigoTrabajador = ? "
					+" WHERE idCuentaBancaria = ? ";


			ps = db.conn.prepareStatement(sql);

			ps.setInt	(i++, cuentaBancaria.getIdTrabajador());
			ps.setInt	(i++, cuentaBancaria.getIdTipoCuenta());
			ps.setString(i++, cuentaBancaria.getnCuenta());
			ps.setInt	(i++, cuentaBancaria.getIdBanco());
			ps.setInt	(i++, cuentaBancaria.getEstado());
			ps.setInt	(i++, cuentaBancaria.getCuentaPrimaria());
			ps.setInt	(i++, cuentaBancaria.getCodigoTrabajador());
			ps.setInt	(i++, cuentaBancaria.getIdCuentaBancaria());

			ps.execute();

			return true;

		}catch (Exception e) {
			System.out.println("Error updateOrInsertCuentaBancariaPostulantes: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return false;
	}


	//update Cuenta Bancaria
	public static boolean updateCuentaBancariaPostulantes(CuentaBancariaPostulantes cuentaBancaria) throws  Exception{

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	

		try {
			sql = "UPDATE sw_cuentaBancariaPostulantes SET"
					+ " idTrabajador = '"+cuentaBancaria.getIdTrabajador()+"', "
					+ " idTipoCuenta = '"+cuentaBancaria.getIdTipoCuenta()+"', "
					+ " nCuenta = '"+cuentaBancaria.getnCuenta()+"', "
					+ " idBanco = '"+cuentaBancaria.getIdBanco()+"', "
					+ " cuentaPrimaria = '"+cuentaBancaria.getCuentaPrimaria()+"', "
					+ " estado = '"+cuentaBancaria.getEstado()+"', "
					+ " codigoTrabajador = '"+cuentaBancaria.getCodigoTrabajador()+"' "

					+ " where idCuentaBancaria = '"+cuentaBancaria.getIdCuentaBancaria()+"' ";

			ps = db.conn.prepareStatement(sql);
			ps.execute();

			return true;

		}catch (SQLException e) {
			System.out.println("updateCuentaBancariaPostulantes:" + e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("updateCuentaBancariaPostulantes: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return false;
	}

	//get cuenta bancaria
	public static CuentaBancariaPostulantes getCuentaBancariaPostulantes(int id) throws  Exception{

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		CuentaBancariaPostulantes cuentaBancaria = new CuentaBancariaPostulantes();

		try{
			sql = "SELECT * FROM sw_cuentaBancariaPostulantes where idCuentaBancaria= "+id+" ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){

				cuentaBancaria.setIdCuentaBancaria(rs.getInt("idCuentaBancaria"));
				cuentaBancaria.setIdTipoCuenta(rs.getInt("idTipoCuenta"));
				cuentaBancaria.setnCuenta(rs.getString("nCuenta"));
				cuentaBancaria.setIdBanco(rs.getInt("idBanco"));
				cuentaBancaria.setCuentaPrimaria(rs.getInt("cuentaPrimaria"));
				cuentaBancaria.setEstado(rs.getInt("estado"));
				cuentaBancaria.setCodigoTrabajador(rs.getInt("codigoTrabajador"));

			}

		}catch (SQLException e) {
			System.out.println("getCuentaBancariaPostulantes:" + e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("getCuentaBancariaPostulantes: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return cuentaBancaria;
	}


	//getCuentasBancariasByTrabajador
	public static ArrayList<CuentaBancariaPostulantes> getCuentaBancariaByTrabajadorPostulantes(String id) throws  Exception{

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<CuentaBancariaPostulantes> lista = new ArrayList<CuentaBancariaPostulantes>();


		try{
			sql = "SELECT * FROM sw_cuentaBancariaPostulantes where idTrabajador = "+id+" ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){

				CuentaBancariaPostulantes cuentaBancaria = new CuentaBancariaPostulantes();

				cuentaBancaria.setIdCuentaBancaria(rs.getInt("idCuentaBancaria"));
				cuentaBancaria.setIdTipoCuenta(rs.getInt("idTipoCuenta"));
				cuentaBancaria.setnCuenta(rs.getString("nCuenta"));
				cuentaBancaria.setIdBanco(rs.getInt("idBanco"));
				cuentaBancaria.setCuentaPrimaria(rs.getInt("cuentaPrimaria"));
				cuentaBancaria.setEstado(rs.getInt("estado"));
				cuentaBancaria.setCodigoTrabajador(rs.getInt("codigoTrabajador"));
				lista.add(cuentaBancaria);

			}

		}catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}

	
	//getCuentasBancariasByTrabajador
		public static ArrayList<CuentaBancariaPostulantes> getCuentaBancariaByCodTrabajadorPostulantes(String cod) throws Exception{

			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();

			ArrayList<CuentaBancariaPostulantes> lista = new ArrayList<CuentaBancariaPostulantes>();


			try{
				sql = "SELECT * FROM sw_cuentaBancariaPostulantes where codigoTrabajador = "+cod+" ";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);

				while(rs.next()){

					CuentaBancariaPostulantes cuentaBancaria = new CuentaBancariaPostulantes();

					cuentaBancaria.setIdCuentaBancaria(rs.getInt("idCuentaBancaria"));
					cuentaBancaria.setIdTipoCuenta(rs.getInt("idTipoCuenta"));
					cuentaBancaria.setnCuenta(rs.getString("nCuenta"));
					cuentaBancaria.setIdBanco(rs.getInt("idBanco"));
					cuentaBancaria.setCuentaPrimaria(rs.getInt("cuentaPrimaria"));
					cuentaBancaria.setEstado(rs.getInt("estado"));
					cuentaBancaria.setCodigoTrabajador(rs.getInt("codigoTrabajador"));
					lista.add(cuentaBancaria);

				}

			}catch (SQLException e) {
				System.out.println("getCuentaBancariaByCodTrabajadorPostulante:" + e.getMessage());
				e.printStackTrace();
			}catch (Exception e) {
				System.out.println("getCuentaBancariaByCodTrabajadorPostulante: " + e.getMessage());
			}finally {
				ps.close();
				db.close();
			}		
			return lista;
		}









}
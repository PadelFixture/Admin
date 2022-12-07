package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import lib.ClassSASW.parametros;
import lib.classSA.CAMPO;
import lib.db.ConnectionDB;
import lib.db.simpleagroDB;
import lib.security.session;
import lib.sesionSA.SESION;

public class mantenedor_campo {

	public static boolean updateGeoAreaCampo(CAMPO campo,HttpSession httpSession) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			session ses = new session(httpSession);
			int id = ses.getIdUser();
			sql = "UPDATE campo SET georeferencia = '"+campo.getGeoreferencia()+"',area = "+campo.getArea()+" where codigo=" + campo.getCodigo();
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			SESION mc= new SESION(httpSession);
			simpleagroDB.getCAMPO(httpSession, id);
			mc.save();
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

}

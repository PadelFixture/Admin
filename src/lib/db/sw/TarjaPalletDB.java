package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import lib.ClassSASW.TarjaPallet;
import lib.db.ConnectionDB;

public class TarjaPalletDB {
   
	
	
	public static boolean CreateTarja(TarjaPallet tp) throws SQLException{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		
		String centro=tp.getCentro();
		String ruta=tp.getPathDestino()+tp.getNombreArchivo()+".docx";
		String folio="";
		String usuario="";
		String nombreArchivo=tp.getNombreArchivo();
		
		for(int i=0;i<tp.getDatos().length;i++){
			if(tp.getDatos()[i][0].equals("$$usuario$$")){
				usuario=tp.getDatos()[i][1];
			}
			else if(tp.getDatos()[i][0].equals("$$numeroFolio$$")){
				folio=tp.getDatos()[i][1];
				
			}
		}
		
		
		
		
		

		try{

			sql = "INSERT INTO PROCESOS_SCLEM.tarjas_pt ( numeroPallet, usuario, nombreArchivo, ruta, centro ) "
					+ " VALUES (?,?,?,?,?) ";

			ps = db.conn.prepareStatement(sql);
			ps.setString(1, folio);
			ps.setString(2, usuario);
			ps.setString(3, nombreArchivo);
			ps.setString(4, ruta);
			ps.setString(5, centro);

			ps.execute();

			return true;

		}catch(Exception ex){

		}finally{
			db.conn.close();
		}

		return false;
		
		
		
		
	}
}

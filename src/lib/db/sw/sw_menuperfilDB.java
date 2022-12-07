package lib.db.sw;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

//import com.sap.conn.jco.JCoDestination;
//import com.sap.conn.jco.JCoFunction;
//import com.sap.conn.jco.JCoFunctionTemplate;
//import com.sap.conn.jco.JCoRepository;

import SWDB.LoginTestDB;
import lib.classSW.LoginTest;
import lib.classSW.MenuPerfil;
import lib.classSW.MenuPerfiljson;
//import lib.data.json.sw.SapConection;
import lib.db.ConnectionDB;

public class sw_menuperfilDB {
	
	public static ArrayList<MenuPerfil> getRolUsuario(int idusuario)  throws Exception{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<MenuPerfil> lista = new ArrayList<MenuPerfil>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select rolPrivado from loginTest where id = "+idusuario+" ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				MenuPerfil tr = new MenuPerfil();
				tr.setChecked(rs.getInt("rolPrivado"));
				lista.add(tr);
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	
	// obtener listado de perfiles x menu 
	public static ArrayList<MenuPerfil> getperfilxmenu()  throws Exception{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<MenuPerfil> lista = new ArrayList<MenuPerfil>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT idPerfil,upper(nombre) as nombre FROM systemPerfil;";
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				MenuPerfil tr = new MenuPerfil();
				tr.setIdperfil(rs.getInt("idPerfil"));
				tr.setNombreperfil(rs.getString("nombre"));
				lista.add(tr);
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	
	
	public static String  gettablamenuperfil(MenuPerfil r, int idperfil)  throws Exception{
		String respuesta = "";
		ArrayList<MenuPerfil> lista = new ArrayList<MenuPerfil>();
		ConnectionDB db = new ConnectionDB();
		try{

			CallableStatement cStmt = db.conn.prepareCall("{call SAN_CLEMENTE.getMenu("+idperfil+")}");
			
			 cStmt.execute();
	         ResultSet rs = cStmt.getResultSet();

			while(rs.next()){
				MenuPerfil tr = new MenuPerfil();
				tr.setIdmenu(rs.getInt("idMenu"));
				tr.setNombremenu(rs.getString("menu"));
				tr.setIdpadre(rs.getInt("idPadre"));
				tr.setOrden(rs.getString("orden"));
				tr.setNivel(rs.getInt("arbol"));
				tr.setExiste(rs.getInt("existe"));
				tr.setVer(rs.getInt("ver"));
				tr.setEditar(rs.getInt("editar"));
				tr.setEliminar(rs.getInt("eliminar"));
				lista.add(tr);
			}			
			
			 MenuPerfiljson v1 = new MenuPerfiljson();
			 // estructura para generar un array []
			 JSONArray ja = new JSONArray();
			 List<MenuPerfiljson> Nodes = new ArrayList<MenuPerfiljson>();
			
			 MenuPerfiljson c2 = new MenuPerfiljson();
			 MenuPerfiljson c3 = new MenuPerfiljson();
			 
			 // si el perfil a consultar tiene privilegios sobre el menu en base de datos true o false 
			 // para que se active el checkbox en el front del sitio
			 MenuPerfiljson checkTrue = new MenuPerfiljson();
			 checkTrue.setChecked(true);
			 
			 MenuPerfiljson checkFalse = new MenuPerfiljson();
			 checkFalse.setChecked(false);
				 
			 for(MenuPerfil p : lista){
				
				    MenuPerfiljson c1 = new MenuPerfiljson();
				    String ver_ = "";
	    		 	String editar_ = "";
	    		 	String eliminar_ = "";
	    		 	
				    if(p.getNivel() == 0 && p.getIdmenu() == 1 ){
				    	
				    	 if(p.getExiste() == 1){
				    		 
				    		 
				    		 	if (p.getVer() == 1) {
									ver_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='ver"+p.getIdmenu()+"' onclick='ver("+p.getIdmenu()+",1"+")' class='glyphicon glyphicon-eye-open' style='color:blue; z-index: 100000;font-size: 20px;'></span>";
								} else {
									ver_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='ver"+p.getIdmenu()+"' onclick='ver("+p.getIdmenu()+",0"+")' class='glyphicon glyphicon-eye-open' style='color:gray; z-index: 100000;font-size: 20px;'></span>";
								}
								;
								if (p.getEditar() == 1) {
									editar_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='editar"+p.getIdmenu()+"' onclick='editar("+p.getIdmenu()+",1"+")' class='glyphicon glyphicon-pencil' style='color:brown; z-index: 100000;font-size: 20px;'></span>";
								} else {
									editar_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;;<span id='editar"+p.getIdmenu()+"' onclick='editar("+p.getIdmenu()+",0"+")' class='glyphicon glyphicon-pencil' style='color:gray; z-index: 100000;font-size: 20px;'></span>";
								}
								;
								if (p.getEliminar() == 1) {
									eliminar_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;;<span id='eliminar"+p.getIdmenu()+"' onclick='eliminar("+p.getIdmenu()+",1"+")' class='glyphicon glyphicon-trash' style='color:red; z-index: 100000;font-size: 20px;'></span>";
								} else {
									eliminar_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;;<span id='eliminar"+p.getIdmenu()+"' onclick='eliminar("+p.getIdmenu()+",0"+")' class='glyphicon glyphicon-trash' style='color:gray; z-index: 100000;font-size: 20px;'></span>";
								}
				    		 
				    		 
				    		 v1.setText("<h5 style='display:none'> "+p.getIdmenu()+" </h5>"+p.getNombremenu());
				    		 v1.setState(checkTrue);
				    	 }else{
				    		 v1.setText("<h5 style='display:none'> "+p.getIdmenu()+" </h5>"+p.getNombremenu());
				    		 v1.setState(checkFalse);
				    	 }
				    }
				   
				    if(p.getNivel() == 1 && p.getIdpadre() != 87){
			    		 
				    	 if(p.getExiste() == 1){
				    		 c1.setText("<h5 style='display:none'> "+p.getIdmenu()+" </h5>"+p.getNombremenu()+ver_+editar_+eliminar_);
				    		 c1.setState(checkTrue);
				    	 }else{
				    		 c1.setText("<h5 style='display:none'> "+p.getIdmenu()+" </h5>"+p.getNombremenu()+ver_+editar_+eliminar_);
				    		 c1.setState(checkFalse);
				    	 }
				    	
						 
				    	 List<MenuPerfiljson> Nodes2 = new ArrayList<MenuPerfiljson>();
						  for(MenuPerfil p2 : lista)
						  {
							  	String ver2_ = "";
				    		 	String editar2_ = "";
				    		 	String eliminar2_ = "";
							  c2 = new MenuPerfiljson();
							  
							  if (p2.getVer() == 1) {
									ver2_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='ver"+p2.getIdmenu()+"' onclick='ver("+p2.getIdmenu()+",1"+")' class='glyphicon glyphicon-eye-open' style='color:blue; z-index: 100000;font-size: 20px;'></span>";
								} else {
									ver2_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='ver"+p2.getIdmenu()+"' onclick='ver("+p2.getIdmenu()+",0"+")' class='glyphicon glyphicon-eye-open' style='color:gray; z-index: 100000;font-size: 20px;'></span>";
								}
								;
								if (p2.getEditar() == 1) {
									editar2_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='editar"+p2.getIdmenu()+"' onclick='editar("+p2.getIdmenu()+",1"+")' class='glyphicon glyphicon-pencil' style='color:brown; z-index: 100000;font-size: 20px;'></span>";
								} else {
									editar2_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='editar"+p2.getIdmenu()+"' onclick='editar("+p2.getIdmenu()+",0"+")' class='glyphicon glyphicon-pencil' style='color:gray; z-index: 100000;font-size: 20px;'></span>";
								}
								;
								if (p2.getEliminar() == 1) {
									eliminar2_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='eliminar"+p2.getIdmenu()+"' onclick='eliminar("+p2.getIdmenu()+",1"+")' class='glyphicon glyphicon-trash' style='color:red; z-index: 100000;font-size: 20px;'></span>";
								} else {
									eliminar2_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='eliminar"+p2.getIdmenu()+"' onclick='eliminar("+p2.getIdmenu()+",0"+")' class='glyphicon glyphicon-trash' style='color:gray; z-index: 100000;font-size: 20px;'></span>";
								}
							  
							  
							  if(p.getIdmenu() == p2.getIdpadre())
							  {
								  if(p2.getExiste() == 1){
									  c2.setText("<h5 style='display:none'> "+p2.getIdmenu()+" </h5>"+p2.getNombremenu()+ver2_+editar2_+eliminar2_);
									  c2.setState(checkTrue);
								  }else{
									  c2.setText("<h5 style='display:none'> "+p2.getIdmenu()+" </h5>"+p2.getNombremenu()+ver2_+editar2_+eliminar2_);
									  c2.setState(checkFalse);
								  }
								 
								  Nodes2.add(c2);
								  
								  List<MenuPerfiljson> Nodes3 = new ArrayList<MenuPerfiljson>();
								  for(MenuPerfil p3 : lista)
								  {
									  	String ver3_ = "";
						    		 	String editar3_ = "";
						    		 	String eliminar3_ = "";
									  c3 = new MenuPerfiljson();
									  
									  if (p3.getVer() == 1) {
											ver3_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='ver"+p3.getIdmenu()+"' onclick='ver("+p3.getIdmenu()+",1"+")' class='glyphicon glyphicon-eye-open' style='color:blue; z-index: 100000;font-size: 20px;'></span>";
										} else {
											ver3_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='ver"+p3.getIdmenu()+"' onclick='ver("+p3.getIdmenu()+",0"+")' class='glyphicon glyphicon-eye-open' style='color:gray; z-index: 100000;font-size: 20px;'></span>";
										}
										;
										if (p3.getEditar() == 1) {
											editar3_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='editar"+p3.getIdmenu()+"' onclick='editar("+p3.getIdmenu()+",1"+")' class='glyphicon glyphicon-pencil' style='color:brown; z-index: 100000;font-size: 20px;'></span>";
										} else {
											editar3_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='editar"+p3.getIdmenu()+"' onclick='editar("+p3.getIdmenu()+",0"+")' class='glyphicon glyphicon-pencil' style='color:gray; z-index: 100000;font-size: 20px;'></span>";
										}
										;
										if (p3.getEliminar() == 1) {
											eliminar3_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='eliminar"+p3.getIdmenu()+"' onclick='eliminar("+p3.getIdmenu()+",1"+")' class='glyphicon glyphicon-trash' style='color:red; z-index: 100000;font-size: 20px;'></span>";
										} else {
											eliminar3_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='eliminar"+p3.getIdmenu()+"' onclick='eliminar("+p3.getIdmenu()+",0"+")' class='glyphicon glyphicon-trash' style='color:gray; z-index: 100000;font-size: 20px;'></span>";
										}
									  
									  
									  if(p3.getIdpadre() == 188 && p2.getIdmenu() == 188){
										  
										  if(p3.getExiste() == 1){
											  c3.setText("<h5 style='display:none'> "+p3.getIdmenu()+" </h5>"+p3.getNombremenu()+ver3_+editar3_+eliminar3_);
											  c2.setState(checkTrue);
										  }else{
											  c3.setText("<h5 style='display:none'> "+p3.getIdmenu()+" </h5>"+p3.getNombremenu()+ver3_+editar3_+eliminar3_);  
											  c2.setState(checkFalse);
										  }
										 
										  Nodes3.add(c3);
										  c2.setNodes(Nodes3);
									  }
								  } // end for nivel 3
								  c1.setNodes(Nodes2);
							  }
						  }// end for nivel 2
						  
						  Nodes.add(c1);
			    	 }
			 }// end for principal
		  
		  v1.setNodes(Nodes);
   		  JSONObject obj2 = new JSONObject(v1);
   		  // al ojeto creado dejarlo en un array ejemplo [{..........}]
   		  ja.put(obj2);
		  System.out.println(ja);
          respuesta = String.valueOf(ja);
			
			return respuesta;
		
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			
			db.close();
		}		
		return "";
	}
	
	
	
	public static String  gettablamenuperfilAgro(MenuPerfil r, int idperfil)  throws Exception{
		String respuesta = "";
		ArrayList<MenuPerfil> lista = new ArrayList<MenuPerfil>();
		ConnectionDB db = new ConnectionDB();
		try{
			CallableStatement cStmt = db.conn.prepareCall("{call getMenu("+idperfil+")}");
			System.out.println("****************");
			
			 cStmt.execute();
	         ResultSet rs = cStmt.getResultSet();

			while(rs.next()){
				MenuPerfil tr = new MenuPerfil();
				tr.setIdmenu(rs.getInt("idMenu"));
				tr.setNombremenu(rs.getString("menu"));
				tr.setIdpadre(rs.getInt("idPadre"));
				tr.setOrden(rs.getString("orden"));
				tr.setNivel(rs.getInt("arbol"));
				tr.setExiste(rs.getInt("existe"));
				
				lista.add(tr);
			}			
			
			 MenuPerfiljson v1 = new MenuPerfiljson();
			 // estructura para generar un array []
			 JSONArray ja = new JSONArray();
			 List<MenuPerfiljson> Nodes = new ArrayList<MenuPerfiljson>();
			
			 MenuPerfiljson c2 = new MenuPerfiljson();
			 MenuPerfiljson c3 = new MenuPerfiljson();
			 
			 // si el perfil a consultar tiene privilegios sobre el menu en base de datos true o false 
			 // para que se active el checkbox en el front del sitio
			 MenuPerfiljson checkTrue = new MenuPerfiljson();
			 checkTrue.setChecked(true);
			 
			 MenuPerfiljson checkFalse = new MenuPerfiljson();
			 checkFalse.setChecked(false);
				 
			 for(MenuPerfil p : lista){
				 
				 	String ver_ = "";
	    		 	String editar_ = "";
	    		 	String eliminar_ = "";
	    		 	
	    		 	if (p.getVer() == 1) {
//						ver_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='ver"+p.getIdmenu()+"' onclick='ver("+p.getIdmenu()+",1"+")' class='glyphicon glyphicon-eye-open' style='color:blue; z-index: 100000;font-size: 20px;'></span>";
					} else {
//						ver_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='ver"+p.getIdmenu()+"' onclick='ver("+p.getIdmenu()+",0"+")' class='glyphicon glyphicon-eye-open' style='color:gray; z-index: 100000;font-size: 20px;'></span>";
					}
					;
					if (p.getEditar() == 1) {
//						editar_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='editar"+p.getIdmenu()+"' onclick='editar("+p.getIdmenu()+",1"+")' class='glyphicon glyphicon-pencil' style='color:brown; z-index: 100000;font-size: 20px;'></span>";
					} else {
//						editar_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='editar"+p.getIdmenu()+"' onclick='editar("+p.getIdmenu()+",0"+")' class='glyphicon glyphicon-pencil' style='color:gray; z-index: 100000;font-size: 20px;'></span>";
					}
					;
					if (p.getEliminar() == 1) {
//						eliminar_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='eliminar"+p.getIdmenu()+"' onclick='eliminar("+p.getIdmenu()+",1"+")' class='glyphicon glyphicon-trash' style='color:red; z-index: 100000;font-size: 20px;'></span>";
					} else {
//						eliminar_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='eliminar"+p.getIdmenu()+"' onclick='eliminar("+p.getIdmenu()+",0"+")' class='glyphicon glyphicon-trash' style='color:gray; z-index: 100000;font-size: 20px;'></span>";
					}
				
				    MenuPerfiljson c1 = new MenuPerfiljson();
				    
				    if(p.getNivel() == 0 && p.getIdmenu() == 87 ){
				    	
				    	 if(p.getExiste() == 1){
				    		 v1.setText("<h5 style='display:none'> "+p.getIdmenu()+" </h5>"+p.getNombremenu());
				    		 v1.setState(checkTrue);
				    	 }else{
				    		 v1.setText("<h5 style='display:none'> "+p.getIdmenu()+" </h5>"+p.getNombremenu());
				    		 v1.setState(checkFalse);
				    	 }
				    }
				   
				    if(p.getNivel() == 1 && p.getIdpadre() != 1){
			    		 
				    	 if(p.getExiste() == 1){
				    		 
				    		 if(p.getIdmenu() == 189){
				    			 c1.setText("<h5 style='display:none'> "+p.getIdmenu()+"  </h5>"+p.getNombremenu()+ver_+editar_+eliminar_);
					    		 c1.setState(checkTrue);
				    		 }else{
				    			 c1.setText("<h5 style='display:none'> "+p.getIdmenu()+"  </h5>"+p.getNombremenu());
					    		 c1.setState(checkTrue);
				    		 }
				    		 
				    		
				    	 }else{
				    		 if(p.getIdmenu() == 189){
				    			 c1.setText("<h5 style='display:none'> "+p.getIdmenu()+"  </h5>"+p.getNombremenu()+ver_+editar_+eliminar_);
					    		 c1.setState(checkFalse);
				    		 }else{
				    			 c1.setText("<h5 style='display:none'> "+p.getIdmenu()+"  </h5>"+p.getNombremenu());
					    		 c1.setState(checkFalse);
				    		 }
				    		 
				    	 }
				    	
						 
				    	 List<MenuPerfiljson> Nodes2 = new ArrayList<MenuPerfiljson>();
						  for(MenuPerfil p2 : lista)
						  {
							  	String ver2_ = "";
				    		 	String editar2_ = "";
				    		 	String eliminar2_ = "";
							  c2 = new MenuPerfiljson();
							  
							  if (p2.getVer() == 1) {
//									ver2_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='ver"+p2.getIdmenu()+"' onclick='ver("+p2.getIdmenu()+",1"+")' class='glyphicon glyphicon-eye-open' style='color:blue; z-index: 100000;font-size: 20px;'></span>";
								} else {
//									ver2_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='ver"+p2.getIdmenu()+"' onclick='ver("+p2.getIdmenu()+",0"+")' class='glyphicon glyphicon-eye-open' style='color:gray; z-index: 100000;font-size: 20px;'></span>";
								}
								;
								if (p2.getEditar() == 1) {
//									editar2_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='editar"+p2.getIdmenu()+"' onclick='editar("+p2.getIdmenu()+",1"+")' class='glyphicon glyphicon-pencil' style='color:brown; z-index: 100000;font-size: 20px;'></span>";
								} else {
//									editar2_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='editar"+p2.getIdmenu()+"' onclick='editar("+p2.getIdmenu()+",0"+")' class='glyphicon glyphicon-pencil' style='color:gray; z-index: 100000;font-size: 20px;'></span>";
								}
								;
								if (p2.getEliminar() == 1) {
//									eliminar2_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='eliminar"+p2.getIdmenu()+"' onclick='eliminar("+p2.getIdmenu()+",1"+")' class='glyphicon glyphicon-trash' style='color:red' z-index: 100000;font-size: 20px;'></span>";
								} else {
//									eliminar2_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='eliminar"+p2.getIdmenu()+"' onclick='eliminar("+p2.getIdmenu()+",0"+")' class='glyphicon glyphicon-trash' style='color:gray' z-index: 100000;font-size: 20px;'></span>";
								}
							  
							  if(p.getIdmenu() == p2.getIdpadre())
							  {
								  if(p2.getExiste() == 1){
									  c2.setText("<h5 style='display:none'> "+p2.getIdmenu()+" </h5>"+p2.getNombremenu()+ver2_+editar2_+eliminar2_);
									  c2.setState(checkTrue);
								  }else{
									  c2.setText("<h5 style='display:none'> "+p2.getIdmenu()+" </h5>"+p2.getNombremenu()+ver2_+editar2_+eliminar2_);
									  c2.setState(checkFalse);
								  }
								 
								  Nodes2.add(c2);
								  
								  List<MenuPerfiljson> Nodes3 = new ArrayList<MenuPerfiljson>();
								  for(MenuPerfil p3 : lista)
								  {
									  	String ver3_ = "";
						    		 	String editar3_ = "";
						    		 	String eliminar3_ = "";
									  c3 = new MenuPerfiljson();
									  
									  if (p3.getVer() == 1) {
//											ver3_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='ver"+p3.getIdmenu()+"' onclick='ver("+p3.getIdmenu()+",1"+")' class='glyphicon glyphicon-eye-open' style='color:blue; z-index: 100000;font-size: 20px;'></span>";
										} else {
//											ver3_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='ver"+p3.getIdmenu()+"' onclick='ver("+p3.getIdmenu()+",0"+")' class='glyphicon glyphicon-eye-open' style='color:gray; z-index: 100000;font-size: 20px;'></span>";
										}
										;
										if (p3.getEditar() == 1) {
//											editar3_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='editar"+p3.getIdmenu()+"' onclick='editar("+p3.getIdmenu()+",1"+")' class='glyphicon glyphicon-pencil' style='color:brown; z-index: 100000;font-size: 20px;'></span>";
										} else {
//											editar3_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='editar"+p3.getIdmenu()+"' onclick='editar("+p3.getIdmenu()+",0"+")' class='glyphicon glyphicon-pencil' style='color:gray; z-index: 100000;font-size: 20px;'></span>";
										}
										;
										if (p3.getEliminar() == 1) {
//											eliminar3_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='eliminar"+p3.getIdmenu()+"' onclick='eliminar("+p3.getIdmenu()+",1"+")' class='glyphicon glyphicon-trash' style='color:red' z-index: 100000;font-size: 20px;'></span>";
										} else {
//											eliminar3_ = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id='eliminar"+p3.getIdmenu()+"' onclick='eliminar("+p3.getIdmenu()+",0"+")' class='glyphicon glyphicon-trash' style='color:gray' z-index: 100000;font-size: 20px;'></span>";
										}
										
									  if(p3.getIdpadre() == 188 && p2.getIdmenu() == 188){
										  
										  if(p3.getExiste() == 1){
											  c3.setText("<h5 style='display:none'> "+p3.getIdmenu()+" </h5>"+p3.getNombremenu()+ver3_+editar3_+eliminar3_);
											  c2.setState(checkTrue);
										  }else{
											  c3.setText("<h5 style='display:none'> "+p3.getIdmenu()+" </h5>"+p3.getNombremenu()+ver3_+editar3_+eliminar3_);
											  c2.setState(checkFalse);
										  }
										 
										  Nodes3.add(c3);
										  c2.setNodes(Nodes3);
									  }
								  } // end for nivel 3
								  c1.setNodes(Nodes2);
							  }
						  }// end for nivel 2
						  
						  Nodes.add(c1);
			    	 }
			 }// end for principal
		  
		  v1.setNodes(Nodes);
   		  JSONObject obj2 = new JSONObject(v1);
   		  // al ojeto creado dejarlo en un array ejemplo [{..........}]
   		  ja.put(obj2);
		  System.out.println(ja);
          respuesta = String.valueOf(ja);
			
			return respuesta;
		
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			
			db.close();
		}		
		return "";
	}
	
	
	
	
	
	
	
	
	
	// eliminar menu con id de perfil
	public static boolean eliminar_menu_perfil(int id) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "DELETE FROM systemMenuPerfil WHERE idMenuPerfil=" + id + "";
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
	
	
//	public static List<LoginTest> getAllLoginTest()  throws Exception{
//		
//		SapConection sap = new SapConection();
//		JSONObject data= new JSONObject();
//		
//		
//		JCoDestination destination = sap.connect();
//		
//		JCoRepository repositorio = destination.getRepository();
//		// aqui se deja el nombre de la rfc ZMOV_50011
//				JCoFunctionTemplate ftemplate = repositorio.getFunctionTemplate("BAPI_USER_GETLIST".toUpperCase());
//				
//				System.out.println(ftemplate);
//				
//				JCoFunction funcion = ftemplate.getFunction();
//				System.out.println("//////////////////////");
//				System.out.println(funcion.toXML());
//		        //Pasamos parametros a la funcion
//				
//				System.out.println("//////////////////////");
//				// aqui obtengo la data de la RFC sin enviar parzametros
//				data = XML.toJSONObject(funcion.toXML().toString());
//				System.out.println(data);
//				
////				String[] cars = {"MZ SEMIELABORADO", "MATNR","10CTP10586", "CHARG" ,"2018", "MJAHR_I", "2019", "MJAHR_F"};
//				String[] cars = {"0", "MAX_ROWS","X", "WITH_USERNAME"};
//				//Pasamos parametros a la funcion
//				
//				funcion.getImportParameterList().setValue(cars[1], cars[0]);
//				funcion.getImportParameterList().setValue(cars[3], cars[2]);
////				funcion.getImportParameterList().setValue(cars[5], cars[4]);
////				funcion.getImportParameterList().setValue(cars[7], cars[6]);
//				
//				//Ejecutamos la funcion
//		        funcion.execute(destination);
//		        
//		        // obtenemos la data en JSON 
//		        data = XML.toJSONObject(funcion.toXML().toString());
//		        System.out.println(data);
//		       
//		      
//		        JSONArray input2 = data.getJSONObject("BAPI_USER_GETLIST").getJSONObject("TABLES").getJSONObject("USERLIST").getJSONArray("item");
//		        System.out.println(input2);
//		        
//		        
//		        List<LoginTest> lista = new ArrayList<>();
//		        
//		        List<LoginTest> lista5 = LoginTestDB.obtenerListadoLogintest();
//		        
//				for (LoginTest datos : lista5) 
//		        {
//					 Iterator<Object> iobj= input2.iterator();
//						while(iobj.hasNext()) {
//							LoginTest p = new LoginTest();
//							JSONObject itemKey = (JSONObject)iobj.next();
//					    	if(itemKey.getString("USERNAME").equals( datos.getUsername())){
//					    		
//					    		System.out.println("trabajador "+datos.getUsername() + " Listo");
//					    		p.setId(datos.getId());
//					    		p.setUsername(datos.getUsername());
//					    		p.setPerfil(datos.getPerfil());
//					    		p.setGrupocompra(datos.getGrupocompra());
//					    		p.setSolicitante(datos.getSolicitante());
//					    		p.setRolprivado(datos.getRolprivado());
//					    		lista.add(p);
//					    	}
//					    }
//		        }
//		return lista;
//	}
	
	
	
	public static boolean actualizarMenuPerfil (MenuPerfil r,int rolPrivado, int perfil) throws Exception{
		
	
		String sql="";
		String sql2="";
		String sql3="";
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		
		ConnectionDB db = new ConnectionDB();
		
		int id_menu = r.getIdmenu();
		int id_perfil = perfil;
		int check = r.getChecked();
		
		try{
			sql = "select * from systemMenuPerfil where idMenu = "+id_menu+" and idPerfil = "+id_perfil+"";
			System.out.println(sql);				
			ps = db.conn.prepareStatement(sql);
			ResultSet rs2 = ps.executeQuery(sql);
			
			if (!rs2.isBeforeFirst()) {
				if(check == 1){
				sql3 = "INSERT systemMenuPerfil(idMenu,idPerfil) VALUES ("+id_menu+", "+id_perfil+")";
				ps3 = db.conn.prepareStatement(sql3);
				ps3.execute();
				System.out.println(sql3);		
			}

			}else{
				if(check == 0){
				 sql2 = "DELETE FROM systemMenuPerfil WHERE idMenu = "+id_menu+" and idPerfil = "+id_perfil+"";
				 ps2 = db.conn.prepareStatement(sql2);
				 ps2.execute();
				 System.out.println(sql2);		
				}
			}
			
			return true;
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
			ps.close();
		
			db.close();
		}
		return false;
	}
	
	
	// actualizar usuario campo 
	
	public static boolean actualizarUsuarioCAmpo (MenuPerfil r, int perfil) throws Exception{
		
		
		String sql="";
		String sql2="";
		String sql3="";
		
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		
		
		ConnectionDB db = new ConnectionDB();
		
		String numHuerto = r.getHuerto();
		int check = r.getChecked();
		
		try{
			sql = "select * from usuario_campo where codigo_usuario = "+perfil+" and codigo_campo = '"+numHuerto+"'";
							
			ps = db.conn.prepareStatement(sql);
			ResultSet rs2 = ps.executeQuery(sql);
			System.out.println(sql);
			if (!rs2.isBeforeFirst()) {
				if(check == 1){
				sql3 = "INSERT INTO usuario_campo(codigo_usuario, codigo_campo) VALUES ("+perfil+", '"+numHuerto+"');";
				ps3 = db.conn.prepareStatement(sql3);
				ps3.execute();
				System.out.println(sql3);
			}

			}else{
				if(check == 0){
				 sql2 = "DELETE FROM usuario_campo WHERE codigo_usuario = "+perfil+" and codigo_campo = '"+numHuerto+"'";
				 ps2 = db.conn.prepareStatement(sql2);
				 ps2.execute();
				 System.out.println(sql2);
				}
			}
			
			return true;
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
			ps.close();
		
			db.close();
		}
		return false;
	}
	
	// obtener todos los campo por usuario
	public static ArrayList<MenuPerfil> getallCampoxUsuario(int idusuario)  throws Exception{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<MenuPerfil> lista = new ArrayList<MenuPerfil>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT "
					    +"codigo_campo,"
					    +"(SELECT "
					            +"so.idSociedad "
					        +"FROM "
					            +"campo ca "
					                +"INNER JOIN "
					            +"sociedad so ON ca.sociedad = so.sociedad "
					        +"WHERE "
					            +"ca.campo = codigo_campo) AS idsociedad "
					+"FROM "
					    +"usuario_campo "
					+"WHERE "
					    +"codigo_usuario = "+idusuario+"";
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				MenuPerfil tr = new MenuPerfil();
				tr.setHuerto(rs.getString("codigo_campo"));
				tr.setIdsociedad(rs.getInt("idsociedad"));
				lista.add(tr);
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	
// actualizar rol privado
public static boolean actualizarRolPrivado (int rolPrivado, int perfil) throws Exception{
		
		String sql="";
		PreparedStatement ps = null;
		
		ConnectionDB db = new ConnectionDB();
		
		try{
			sql = "UPDATE loginTest SET rolPrivado = "+rolPrivado+" WHERE id = "+perfil+"";
							
			
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			
			return true;
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
			ps.close();
		
			db.close();
		}
		return false;
	}
	
   // verificar si usuario existe

public static boolean insertUsuarioyPerfiles (List<MenuPerfil> array1_,List<MenuPerfil> array2_,String usuarionew,int rolPrivado) throws Exception{
	
	String sql="";
	String sql1="";
	PreparedStatement ps = null;
	PreparedStatement ps1 = null;
	int newperfil_ = 0;
	ConnectionDB db = new ConnectionDB();
	
	try{
		sql = "select usuario from loginTest where usuario = '"+usuarionew+"'";
		
		ps = db.conn.prepareStatement(sql);
		ResultSet rs2 = ps.executeQuery(sql);
		System.out.println(sql);
		if (!rs2.isBeforeFirst()) {
			
			PreparedStatement getLastInsertId = db.conn.prepareStatement("SELECT max(id) + 1 as id from loginTest");
			ResultSet rs4 = getLastInsertId.executeQuery();
			if (rs4.next())
			{
				newperfil_ = Integer.parseInt(rs4.getString("id"));
				sql1 = "INSERT INTO loginTest (usuario, sociedad, rolPrivado) VALUES ('"+usuarionew+"',"+rs4.getString("id")+"  , "+rolPrivado+")";
				System.out.println(sql1);
				ps1 = db.conn.prepareStatement(sql1);
				ps1.execute();
			}
			
			
	         for (MenuPerfil rec1 : array2_) {

	        	 sw_menuperfilDB.actualizarUsuarioCAmpo(rec1,newperfil_);
	        	 System.out.println("aqui estoy ");
				}               
			
			for (MenuPerfil rec : array1_) {

				 sw_menuperfilDB.actualizarMenuPerfil(rec,rolPrivado,newperfil_);
				 System.out.println("aqui estoy2 ");
			}
			
			
		}else{
			
		}
		
		return true;
	}catch (SQLException e){
		System.out.println("Error: "+ e.getMessage());
	}catch (Exception e){
		System.out.println("Error: "+ e.getMessage());
	}finally {
		
		
	
		db.close();
	}
	return false;
}

public static boolean eliminarcampoxempresaMenuperfil (MenuPerfil r, int perfil) throws Exception{
	
	String sql2="";
	PreparedStatement ps2 = null;
	
	ConnectionDB db = new ConnectionDB();
	
	try{
		
			if(r.getChecked() == 0){
			 sql2 = "DELETE FROM usuario_campo WHERE codigo_campo in "
			 		+ "(select campo from campo where sociedad = "
			 		+ "(select sociedad from sociedad where idSociedad = "+r.getIdsociedad()+") ) and codigo_usuario = "+perfil+"";
			 ps2 = db.conn.prepareStatement(sql2);
			 ps2.execute();
			 System.out.println(sql2);
			}
		
		
		return true;
	}catch (SQLException e){
		System.out.println("Error: "+ e.getMessage());
	}catch (Exception e){
		System.out.println("Error: "+ e.getMessage());
	}finally {
		
	
		db.close();
	}
	return false;
}

public static boolean actualizarveridmenu (MenuPerfil r) throws Exception{
	
	String sql2="";
	PreparedStatement ps2 = null;
	
	ConnectionDB db = new ConnectionDB();
	
	try{
		
		
			 sql2 = "UPDATE systemMenuPerfil SET ver ="+r.getEstado()+" WHERE idPerfil = '"+r.getCodigotrabajador()+"' and idMenu = "+r.getIdmenu()+"";
			 ps2 = db.conn.prepareStatement(sql2);
			 ps2.execute();
			 System.out.println(sql2);
			
		
		
		return true;
	}catch (SQLException e){
		System.out.println("Error: "+ e.getMessage());
	}catch (Exception e){
		System.out.println("Error: "+ e.getMessage());
	}finally {
		
	
		db.close();
	}
	return false;
}

public static boolean actualizareditaridmenu (MenuPerfil r) throws Exception{
	
	String sql2="";
	PreparedStatement ps2 = null;
	
	ConnectionDB db = new ConnectionDB();
	
	try{
		
		
			 sql2 = "UPDATE systemMenuPerfil SET editar ="+r.getEstado()+" WHERE idPerfil = '"+r.getCodigotrabajador()+"' and idMenu = "+r.getIdmenu()+"";
			 ps2 = db.conn.prepareStatement(sql2);
			 ps2.execute();
			 System.out.println(sql2);
			
		
		
		return true;
	}catch (SQLException e){
		System.out.println("Error: "+ e.getMessage());
	}catch (Exception e){
		System.out.println("Error: "+ e.getMessage());
	}finally {
		
	
		db.close();
	}
	return false;
}
public static boolean actualizareliminaridmenu (MenuPerfil r) throws Exception{
	
	String sql2="";
	PreparedStatement ps2 = null;
	
	ConnectionDB db = new ConnectionDB();
	
	try{
		
			 sql2 = "UPDATE systemMenuPerfil SET eliminar ="+r.getEstado()+" WHERE idPerfil = '"+r.getCodigotrabajador()+"' and idMenu = "+r.getIdmenu()+"";
			 ps2 = db.conn.prepareStatement(sql2);
			 ps2.execute();
			 System.out.println(sql2);
		
		return true;
	}catch (SQLException e){
		System.out.println("Error: "+ e.getMessage());
	}catch (Exception e){
		System.out.println("Error: "+ e.getMessage());
	}finally {
		
	
		db.close();
	}
	return false;
}
}

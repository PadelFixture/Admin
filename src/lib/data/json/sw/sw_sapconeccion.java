//package lib.data.json.sw;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.json.XML;
//
//import com.sap.conn.jco.*;
//
//import SWDB.LoginTestDB;
//import lib.classSW.LoginTest;
//import lib.db.ConnectionDB;
//
//public class sw_sapconeccion {
//	
//	//todos los trabajadores codigo y nombre SAP
//		public static ArrayList<LoginTest> getallTrabajaCodNomSAP()  throws JCoException {
//			PreparedStatement ps = null;
//			String sql="";
//			ArrayList<LoginTest> lista = new ArrayList<LoginTest>();
//			ConnectionDB db = new ConnectionDB();
//			
//			
//			
//			try{
//				
//				SapConection sap = new SapConection();
//				JSONObject data= new JSONObject();
//				JCoDestination destination = sap.connect();
//				
//				JCoRepository repositorio = destination.getRepository();
//				// aqui se deja el nombre de la rfc ZMOV_50011
//						JCoFunctionTemplate ftemplate = repositorio.getFunctionTemplate("BAPI_USER_GETLIST".toUpperCase());
//						
//						System.out.println(ftemplate);
//						
//						JCoFunction funcion = ftemplate.getFunction();
//						System.out.println("//////////////////////");
//						System.out.println(funcion.toXML());
//				        //Pasamos parametros a la funcion
//						
//						System.out.println("//////////////////////");
//						// aqui obtengo la data de la RFC sin enviar parzametros
//						data = XML.toJSONObject(funcion.toXML().toString());
//						System.out.println(data);
//						
////						String[] cars = {"MZ SEMIELABORADO", "MATNR","10CTP10586", "CHARG" ,"2018", "MJAHR_I", "2019", "MJAHR_F"};
//						String[] cars = {"0", "MAX_ROWS","X", "WITH_USERNAME"};
//						//Pasamos parametros a la funcion
//						
//						funcion.getImportParameterList().setValue(cars[1], cars[0]);
//						funcion.getImportParameterList().setValue(cars[3], cars[2]);
////						funcion.getImportParameterList().setValue(cars[5], cars[4]);
////						funcion.getImportParameterList().setValue(cars[7], cars[6]);
//						
//						//Ejecutamos la funcion
//				        funcion.execute(destination);
//				        
//				        // obtenemos la data en JSON 
//				        data = XML.toJSONObject(funcion.toXML().toString());
////				        System.out.println(data);
//				       
//				      
//				        JSONArray input2 = data.getJSONObject("BAPI_USER_GETLIST").getJSONObject("TABLES").getJSONObject("USERLIST").getJSONArray("item");
//			            System.out.println(input2);
//				        
//				        
//				      
//				        
//				        List<LoginTest> lista5 = LoginTestDB.obtenerListadoLogintest();
//				        
//						
//							 Iterator<Object> iobj= input2.iterator();
//								while(iobj.hasNext()) {
//									LoginTest p = new LoginTest();
//									JSONObject itemKey = (JSONObject)iobj.next();
//									System.out.println("///////////");
//									System.out.println(itemKey);
//		     						System.out.println((itemKey.getString("USERNAME")));
//									
//		     						
//		     						
//									for (LoginTest datos : lista5) 
//							        {
//									
//									
//							    	if(itemKey.getString("USERNAME").equals( datos.getUsername())){
//							    		
////							    		System.out.println("trabajador "+datos.getUsername() + " Listo");
//							    		p.setId(datos.getId());
//							    		p.setUsername(datos.getUsername());
//							    		p.setPerfil(datos.getPerfil());
//							    		p.setGrupocompra(datos.getGrupocompra());
//							    		p.setSolicitante(datos.getSolicitante());
//							    		p.setRolprivado(datos.getRolprivado());
//							    		p.setNombrecompleto(itemKey.getString("FULLNAME").toUpperCase());
//							    		lista.add(p);
//							    	}
//							        }
//							    }
//			
//		}
//		catch(Exception ex) {
//			
//		}
//			return lista;
//		}
//}

package lib.data.json.sw;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lib.classSW.LoginTest;
import lib.classSW.MenuPerfil;
import lib.db.sw.sw_menuperfilDB;
import lib.security.session;

@Controller
public class sw_menuPerfil {
	
	@RequestMapping(value = "/work/getRolUsuario/{idusuario}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<MenuPerfil> getAllMenuPerfil(@PathVariable int idusuario ,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<MenuPerfil> es = new ArrayList<MenuPerfil>();

		if (ses.isValid()) {
			return es;
		}

		es = sw_menuperfilDB.getRolUsuario(idusuario);
		return es;

	}
	
	@RequestMapping(value = "/work/allCampoxUsuario/{idusuario}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<MenuPerfil> getallCampoxUsuario(@PathVariable int idusuario ,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<MenuPerfil> es = new ArrayList<MenuPerfil>();

		if (ses.isValid()) {
			return es;
		}

		es = sw_menuperfilDB.getallCampoxUsuario(idusuario);
		return es;

	}
	
	// buscar perfiles por menu 
	@RequestMapping(value = "/work/buscarperfilesxmenu/", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<MenuPerfil> getperfilxmenu(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<MenuPerfil> r = new ArrayList<MenuPerfil>();

		if (ses.isValid()) {
			return r;
		}
		r = sw_menuperfilDB.getperfilxmenu();

		return r;

	}
	

	
	
	@RequestMapping(value = "/work/buscarmenuyperfiles/{perfil}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String gettablamenuperfil(@RequestBody ArrayList<MenuPerfil> row,
			@PathVariable int perfil,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		String es = null;
	
		if (ses.isValid()) {
			return es;
		}
		
		String	recc = "";
		for (MenuPerfil rec : row) {

		 recc = sw_menuperfilDB.gettablamenuperfil(rec,perfil);
		}

		return recc;

	}
	
	
	@RequestMapping(value = "/work/buscarmenuyperfilesAgro/{perfil}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String gettablamenuperfilAgro(@RequestBody ArrayList<MenuPerfil> row,
			@PathVariable int perfil,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		String es = null;
	
		if (ses.isValid()) {
			return es;
		}
		
		String	recc = "";
		for (MenuPerfil rec : row) {

		 recc = sw_menuperfilDB.gettablamenuperfilAgro(rec,perfil);
		}

		return recc;

	}
	
		// eliminar menu con id de perfil
		
		@RequestMapping(value = "/work/Eliminarmenuconiddeperfil/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean eliminar_menu_perfil(@PathVariable int id, HttpSession httpSession) throws Exception {
			boolean recc = false;
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return recc;
			}

			recc = sw_menuperfilDB.eliminar_menu_perfil(id);

			return recc;

		}
		
//		@RequestMapping(value = "/work/buscarLogintest/", method = {
//				RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
//		public @ResponseBody List<LoginTest> getAllLoginTest(
//				HttpSession httpSession) throws Exception {
//
//			session ses = new session(httpSession);
//
//			List<LoginTest> r = new ArrayList<LoginTest>();
//
//			if (ses.isValid()) {
//				return r;
//			}
//			r = sw_menuperfilDB.getAllLoginTest();
//
//			return r;
//
//		}
		
		
		@RequestMapping(value = "/work/actualizarMenuPerfil/{rolPrivado},{perfil}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean actualizarMenuPerfil(@RequestBody ArrayList<MenuPerfil> row,@PathVariable int rolPrivado,@PathVariable int perfil, HttpSession httpSession)
				throws Exception {
			boolean recc = false;
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return recc;
			}
			
			// actualizar rol privado
			 recc = sw_menuperfilDB.actualizarRolPrivado(rolPrivado,perfil);
			
			 String JsonArray2 =  new Gson().toJson(row.get(0).getArray2());
			 String JsonArray1 =  new Gson().toJson(row.get(0).getArray1());
			 String JsonArray3 =  new Gson().toJson(row.get(0).getArray3());
			 String JsonArray4 =  new Gson().toJson(row.get(0).getArray4());
			 String JsonArray5 =  new Gson().toJson(row.get(0).getArray5());
			 String JsonArray6 =  new Gson().toJson(row.get(0).getArray6());
	         
	         ObjectMapper objectMapper = new ObjectMapper();

	         List<MenuPerfil> array2_ 
	         = objectMapper.readValue(JsonArray2,objectMapper.getTypeFactory().constructCollectionType( List.class, MenuPerfil.class));
	        		 
	         ObjectMapper objectMapper2 = new ObjectMapper();

	         List<MenuPerfil> array1_ 
	         = objectMapper2.readValue(JsonArray1,objectMapper2.getTypeFactory().constructCollectionType( List.class, MenuPerfil.class));
	         
	         ObjectMapper objectMapper3 = new ObjectMapper();
	         List<MenuPerfil> array3_ 
	         = objectMapper3.readValue(JsonArray3,objectMapper3.getTypeFactory().constructCollectionType( List.class, MenuPerfil.class));
	        	
	         ObjectMapper objectMapper4 = new ObjectMapper();
	         List<MenuPerfil> array4_ 
	         = objectMapper4.readValue(JsonArray4,objectMapper4.getTypeFactory().constructCollectionType( List.class, MenuPerfil.class));
	        	
	         ObjectMapper objectMapper5 = new ObjectMapper();
	         List<MenuPerfil> array5_ 
	         = objectMapper5.readValue(JsonArray5,objectMapper5.getTypeFactory().constructCollectionType( List.class, MenuPerfil.class));
	        	
	         ObjectMapper objectMapper6 = new ObjectMapper();
	         List<MenuPerfil> array6_ 
	         = objectMapper6.readValue(JsonArray6,objectMapper6.getTypeFactory().constructCollectionType( List.class, MenuPerfil.class));
	        	
	         
	         for (MenuPerfil rec4 : array4_) {

					recc = sw_menuperfilDB.actualizarveridmenu(rec4);
				}    
	         
	         for (MenuPerfil rec5 : array5_) {

					recc = sw_menuperfilDB.actualizareditaridmenu(rec5);
				} 
	         
	         for (MenuPerfil rec6 : array6_) {

					recc = sw_menuperfilDB.actualizareliminaridmenu(rec6);
				} 
	         
	         for (MenuPerfil rec1 : array2_) {

					recc = sw_menuperfilDB.actualizarUsuarioCAmpo(rec1,perfil);
				}               
			
			for (MenuPerfil rec : array1_) {

				recc = sw_menuperfilDB.actualizarMenuPerfil(rec,rolPrivado,perfil);
			}
			
			for (MenuPerfil rec2 : array3_) {

				recc = sw_menuperfilDB.eliminarcampoxempresaMenuperfil(rec2,perfil);
			}

			return recc;

		}
		
		
//		@RequestMapping(value = "/work/allTrabajadoresCodNomSAP/", method = { RequestMethod.GET, RequestMethod.POST })
//		public @ResponseBody ArrayList<LoginTest> getallTrabajaCodNomSAP( HttpSession httpSession)
//				throws Exception {
//
//			session ses = new session(httpSession);
//			ArrayList<LoginTest> es = new ArrayList<LoginTest>();
//
//			if (ses.isValid()) {
//				return es;
//			}
//
//			es = sw_sapconeccion.getallTrabajaCodNomSAP();
//			return es;
//
//		}
		
		// insertar usuarios y perfiles masivo
		@RequestMapping(value = "/work/insertMenuPerfilNewUser/{rolPrivado}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean InsertMenuPerfilUserNew(@RequestBody ArrayList<MenuPerfil> row,@PathVariable int rolPrivado, HttpSession httpSession)
				throws Exception {
			boolean recc = false;
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return recc;
			}
			
			// actualizar rol privado
//			 recc = sw_menuperfilDB.actualizarRolPrivado(rolPrivado,perfil);
			
			 String JsonArray2 =  new Gson().toJson(row.get(0).getArray2());
			 String JsonArray1 =  new Gson().toJson(row.get(0).getArray1());
			 String JsonArrayUsuarios =  new Gson().toJson(row.get(0).getArrayuser());
	         
	         ObjectMapper objectMapper = new ObjectMapper();

	         List<MenuPerfil> array2_ 
	         = objectMapper.readValue(JsonArray2,objectMapper.getTypeFactory().constructCollectionType( List.class, MenuPerfil.class));
	        		 
	         ObjectMapper objectMapper2 = new ObjectMapper();

	         List<MenuPerfil> array1_ 
	         = objectMapper2.readValue(JsonArray1,objectMapper2.getTypeFactory().constructCollectionType( List.class, MenuPerfil.class));
	        
	         ObjectMapper objectMapper3 = new ObjectMapper();
	         List<MenuPerfil> array_user 
	         = objectMapper3.readValue(JsonArrayUsuarios,objectMapper3.getTypeFactory().constructCollectionType( List.class, MenuPerfil.class));
	         
	         for (MenuPerfil rec3 : array_user) {

	        	 recc = sw_menuperfilDB.insertUsuarioyPerfiles(array1_,array2_,rec3.getUsuario(),rolPrivado);   
			}    
	         
			return recc;

		}

}

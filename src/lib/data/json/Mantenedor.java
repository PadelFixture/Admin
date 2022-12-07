package lib.data.json;


import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lib.db.MantenedorBD;
import lib.db.recotecDB;
import lib.security.session;

@RestController
public class Mantenedor {

	@RequestMapping(value = "/AGRO/MANTENEDOR", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = {"*"})
	public @ResponseBody String MANTENEDOR(@RequestBody  String row, HttpSession httpSession) throws Exception {
		String r = MantenedorBD.Insert(row);
		return r;
	}
	@RequestMapping(value = "/AGRO/UPDATE", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = {"*"})
	public @ResponseBody String UPDATE(@RequestBody  String row, HttpSession httpSession) throws Exception {
		String r = MantenedorBD.Update(row);
		return r;
	}
	@RequestMapping(value = "/AGRO/DELETE", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = {"*"})
	public @ResponseBody String DELETE(@RequestBody  String row, HttpSession httpSession) throws Exception {
		String r = MantenedorBD.Delete(row);
		return r;
	}
	@RequestMapping(value = "/AGRO/SELECT", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = {"*"})
	public @ResponseBody String SELECT(@RequestBody  String row, HttpSession httpSession) throws Exception {
		String r = MantenedorBD.Select(row);
		return r;
	}
	@RequestMapping(value = "/CALLSP", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = {"*"})
	public @ResponseBody String CALLSP(@RequestBody  String row, HttpSession httpSession) throws Exception {
		String r = MantenedorBD.CallSp(row);
		return r;
	}
	@RequestMapping(value = "/UPLOAD_PHOTO", method = RequestMethod.POST)
	@CrossOrigin(origins = {"*"})
	public @ResponseBody String UPLOAD_PHOTO(HttpServletRequest request,@RequestParam("documento") MultipartFile multipartFile) throws Exception {
    	return recotecDB.UPLOAD_PHOTO(request, multipartFile);
	}
	@RequestMapping(value = "/recycle/save-img", method = { RequestMethod.OPTIONS ,RequestMethod.POST }, produces = "application/json")
	@CrossOrigin(origins = "*")
	public String RecycledSaveImage(@RequestBody String json) throws IOException {
		JSONObject r = new JSONObject();//objeto salida
		recotecDB img = new recotecDB();
		r = img.saveImageBase64FromWeb(json);
		System.out.println(r);
		return r.toString();
	}
	@RequestMapping(value = "/recycle/save-file", method = { RequestMethod.OPTIONS ,RequestMethod.POST }, produces = "application/json")
	@CrossOrigin(origins = "*")
	public String saveFileWebRecotec(@RequestBody String json) throws IOException {
		JSONObject r = new JSONObject();//objeto salida
		recotecDB img = new recotecDB();
		r = img.saveImageBase64FromWeb(json);
		System.out.println(r);
		return r.toString();
	}
}

package lib.data.json.sw;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.trabajadores;
import lib.db.sw.trabajadoresDB;
import lib.utils.PasswordGenerator;

@Controller
public class ProcessJSON {

	@RequestMapping(value = "/work/insertTrabajador4/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String insertTrabajador4(HttpSession httpSession){
			
			PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
			        .useDigits(true)
			        .useLower(true)
			        .useUpper(true)
			        .build();
			
			return passwordGenerator.generate(5); 
			
		}
	
	
}

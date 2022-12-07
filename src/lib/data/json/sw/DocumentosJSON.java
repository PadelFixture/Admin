package lib.data.json.sw;

//import java.io.IOException;
//import java.io.InputStream;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import javax.servlet.http.Part;
//
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//
//import lib.classSW.Documentos;
//import lib.db.sw.DocumentosDB;
//import lib.security.session;

//@Controller
public class DocumentosJSON {


//	@RequestMapping(value = "/work/insertDocumentos/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody boolean insertDocumentos(HttpSession httpSession, HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		
//		
//		//System.out.println(documentos);
//		
//		// gets values of text fields
//		String firstName = request.getParameter("codTrabajador");
//		String lastName = request.getParameter("tipoDocumento");
//
//		InputStream inputStream = null; // input stream of the upload file
//
//		// obtains the upload file part in this multipart request
//		Part filePart = request.getPart("documento");
//		if (filePart != null) {
//			// prints out some information for debugging
//			System.out.println(filePart.getName());
//			System.out.println(filePart.getSize());
//			System.out.println(filePart.getContentType());
//
//			// obtains input stream of the upload file
//			inputStream = filePart.getInputStream();
//		}
//
//		String message = null; // message will be sent back to client
//		return false;
//		
//		//getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
//		
//	}

	// // get licencia conducir
	// @RequestMapping(value = "/work/getTrabajadorDocumentos/", method = {
	// RequestMethod.GET })
	// public @ResponseBody ArrayList<TrabajadorDocumentos>
	// getTrabajadorDocumentos(HttpSession httpSession)
	// throws Exception {
	//
	// session ses = new session(httpSession);
	// ArrayList<TrabajadorDocumentos> TrabajadorDocumentos = new
	// ArrayList<TrabajadorDocumentos>();
	//
	// if (ses.isValid()) {
	// return TrabajadorDocumentos;
	// }
	//
	// TrabajadorDocumentos = TrabajadorDocumentosDB.getTrabajadorDocumentos();
	// return TrabajadorDocumentos;
	//
	// }// fin get
	// // get licencia conducir con id
	//
	// @RequestMapping(value =
	// "/work/getTrabajadorDocumentosByIdTrabajador/{id}", method = {
	// RequestMethod.GET })
	// public @ResponseBody TrabajadorDocumentos
	// getTrabajadorDocumentosByIdTrabajador(@PathVariable int id,
	// HttpSession httpSession) throws Exception {
	//
	// session ses = new session(httpSession);
	// TrabajadorDocumentos TrabajadorDocumentosByIdTrabajador = new
	// TrabajadorDocumentos();
	//
	// if (ses.isValid()) {
	// return TrabajadorDocumentosByIdTrabajador;
	// }
	//
	// TrabajadorDocumentosByIdTrabajador = TrabajadorDocumentosDB
	// .getTrabajadorDocumentosByIdTrabajador(id);
	// return TrabajadorDocumentosByIdTrabajador;
	//
	// }// fin Get con id
	//
	// // update Trabajador licencia conducir
	// @RequestMapping(value = "/work/updateTrabajadorDocumentos/", method =
	// RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	// public @ResponseBody boolean updateTrabajadorDocumentos(
	// @RequestBody TrabajadorDocumentos TrabajadorDocumentos, HttpSession
	// httpSession)
	// throws Exception {
	//
	// session ses = new session(httpSession);
	//
	// if (ses.isValid()) {
	// return false;
	// }
	// return
	// TrabajadorDocumentosDB.updateTrabajadorDocumentos(TrabajadorDocumentos);
	// }// fin
	//
	// // Eliminar trabajador licencia conducir
	// @RequestMapping(value = "/work/deleteTrabajadorDocumentosById/{id}",
	// method = { RequestMethod.PUT })
	// public @ResponseBody boolean deleteTrabajadorDocumentos(@PathVariable int
	// id, HttpSession httpSession)
	// throws Exception {
	//
	// session ses = new session(httpSession);
	//
	// if (ses.isValid()) {
	// return false;
	// }
	//
	// return TrabajadorDocumentosDB.deleteTrabajadorDocumentosById(id);
	// }// fin metodo eliminar

}

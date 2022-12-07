package lib.db;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.bind.annotation.CrossOrigin;

import wordCreator.utils;

public class  UploadExcelAnticipos extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final String UPLOAD_DIRECTORY = utils.Licencia();
    
 

    @CrossOrigin(origins = {"*"})
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	setAccessControlHeaders(response);
    	boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    	if (isMultipart) {
	        FileItemFactory factory = new DiskFileItemFactory();
	        ServletFileUpload upload = new ServletFileUpload(factory);
	        try {
	        	            	
	        	// Parse the request
	            List<FileItem> multiparts = upload.parseRequest(request);
	            Object[] aux = multiparts.toArray(); 
	            FileItem iaux = (FileItem)aux[1];
	            FileItem iaux2 = (FileItem)aux[2];
	            int idtest = Integer.parseInt(iaux.getFieldName());
	            String nombre = String.valueOf(iaux2.getFieldName());
	            for (FileItem item : multiparts) {
	                if (!item.isFormField()) {
	                	dteBD e = new dteBD();
		                String[] name = new File(item.getName()).getName().split("\\.");
		                String nm = name[0]+nombre+"."+name[1];
		                item.write(new File(UPLOAD_DIRECTORY + File.separator + nm));
		                String ruta = UPLOAD_DIRECTORY + name;
	                }
	            }
	            
	        } 
	        
	        catch (Exception e) 
	        {
	        	System.out.println("Error: "+e.getMessage());
	        }
	    }
	}
    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "*");
    }
}
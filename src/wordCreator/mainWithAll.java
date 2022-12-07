package wordCreator;

import java.io.File;
import java.io.FileInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class mainWithAll {
	
	private final static Logger LOG = LoggerFactory.getLogger(mainWithAll.class);
	private final static String UPLOAD_DIRECTORY = utils.obtenerCarpetaServidor();

	@SuppressWarnings("unused")
	public static void main(String[] args)throws Exception  {
		
		LOG.info("Iniciamos generacion de contrato");
		//¿Cuánto me demoro?
		long startTime = System.currentTimeMillis();
		DocxCreator dx= new DocxCreator();
		FileInputStream fi= new FileInputStream("contrato.docx");
		LOG.info("Generamos contrato");
		String pathTemporalDeSalida2=UPLOAD_DIRECTORY+"outputs/";
		String pathTemporalDeSalida="outputs/output.docx";
		
		String JSONConVariables="{'codigoTrabajador':'ASC-123456','ciudadContrato':'Talca','nombreEmpresa':'cyber','rutSinDVEmpresa':'16855117','digitoVerificadorEmpresa':'7','rutCompletoEmpresa':'76428956-0','appPatTrabajador':'Caris','appMaternoTrabajador':'Roman','nombreTrabajador':'Mauricio Nicolas','rutSinDvTrabajador':'16855117','digitoVerificadorTrabajador':'7','rutCompletoTrabajador':'16855117-7','estadoCivil':'soltero','fechaNacimientoTrabajador':'26 de noviembre de 1987','nacionalidadTrabajador':'Chileno','direccionTrabajador':'14 de Diciembre','comuna':'Melipilla','cargoTrabajador':'Temporero','faenaContratacion':'marzo 2018','temporadaContratacion':'segunda Temporada','sueldoCPuntosTrabajador':'560.000','sueldoEnPalabrasTrabajador':'Quinientos sesenta mil','fechaInicio':'6 de junio','cuidadContrato':'Santiago'}";
		//revision de la cadena, ojo no debe tener espacios ni saltos de linea, el string debe ser totalmente formateado en JSON
		//System.out.println(JSONConVariables);
		/*--------------------Inicio Del volcado de variables------------------------------*/
		//metodo que ejecuta la conversion de variables del JSON al Archivo, esto lo hace Variable a Variable y solo las que fueron formateadas en el JSON
		 String pathSalidaDocx= dx.modifyDocument(pathTemporalDeSalida2,JSONConVariables, fi);
         // 1) Load docx with POI XWPFDocument				
		FileInputStream pdfconvert=new FileInputStream(pathSalidaDocx);
		dx.convertToPdf(pathSalidaDocx, pdfconvert);
        
		
		//deleteOnExit
         File temp= new File(pathSalidaDocx);
         
         System.out.println("PDF: "+temp.getAbsolutePath());		
         //temp.deleteOnExit();
         //----------------------------------------------------
         
         
     System.out.println(( System.currentTimeMillis() - startTime ) + " ms." );		
	
	}

}

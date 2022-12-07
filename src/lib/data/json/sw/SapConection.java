//package lib.data.json.sw;
//
//import java.io.File;
//import java.io.FileOutputStream;
//
//import java.util.Properties;
//
//import com.sap.conn.jco.JCoDestination;
//import com.sap.conn.jco.JCoDestinationManager;
//import com.sap.conn.jco.JCoException;
//
//import com.sap.conn.jco.ext.DestinationDataProvider;
//
//import lib.io.ConfigProperties;
//
//public class SapConection {
//	
//	private String JCO_ASHOST=ConfigProperties.getProperty("JCO_ASHOST");//: 192.168.10.62
//	private String JCO_SYSNR=ConfigProperties.getProperty("JCO_SYSNR");//: 00
//	private String JCO_CLIENT=ConfigProperties.getProperty("JCO_CLIENT");//: 200
//	private String JCO_USER=ConfigProperties.getProperty("JCO_USER");;//: MNAVA
//	private String JCO_PASSWD=ConfigProperties.getProperty("JCO_PASSWD");//: Miguel@2018
//	private String JCO_LANG=ConfigProperties.getProperty("JCO_LANG");//: EN*/
//	//<add NAME="SCLEM" USER="soporte" PASSWD="Wultu@2018" CLIENT="300" LANG="ES" 
//	//		ASHOST="/H/200.54.27.10/H/192.168.10.62" SYSNR="00" POOL_SIZE="5" MAX_POOL_SIZE="100" IDLE_TIMEOUT="1000" />
//   
//	public static String OS = System.getProperty("os.name").toLowerCase();
//	
//	public JCoDestination connect() throws JCoException
//	{
//		JCoDestination destination;
//		String linuxPath="";
//		
//
//		try {
//			
//			Properties connectProperties = new Properties();
//			connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, JCO_ASHOST);
//			connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, JCO_SYSNR);
//			connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, JCO_CLIENT);
//			connectProperties.setProperty(DestinationDataProvider.JCO_USER, JCO_USER);
//			connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, JCO_PASSWD);
//			connectProperties.setProperty(DestinationDataProvider.JCO_LANG, JCO_LANG);
//			File destCfg;
//			if(!OS.contains("win")) {
//				destCfg = new File("jcoDestinations/simpleAgro.jcoDestination");
//				linuxPath=destCfg.getAbsolutePath();
//			}else {
//				destCfg = new File("sclemCalidad.jcoDestination");
//			}
//			
//
//			try {
//				FileOutputStream fos = new FileOutputStream(destCfg, false);
//				connectProperties.store(fos, "for tests only !");
//				fos.close();
//			} catch (Exception ee) {
//				throw new RuntimeException("Unable to create the  destination files: "+ee.getMessage(), ee);
//			}
// 
//			System.out.println(destCfg.getAbsolutePath());
//			
//			
//		} catch (Exception e) {
//			throw new RuntimeException(e.getMessage());
//		}
//		
//		
//		if(!OS.contains("win")) {
//			destination = JCoDestinationManager.getDestination("jcoDestinations/simpleAgro");
//		}else {
//			destination = JCoDestinationManager.getDestination("sclemCalidad");
//			
//		}
//		System.out.println("Conexion Preparada");
//		System.out.println("repositorio listo");
//
//		return destination;
//		
//	}
//	
//
//}

package lib.db.SASW;

import java.util.ArrayList;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import lib.ClassSASW.ArrayCentro;
import lib.ClassSASW.CentroCosto;

public class CentroCostoDB {
	public static ArrayList<CentroCosto> getCentrosCostosByCECO(String ceco, String version){
		ArrayList<CentroCosto> Lista=new ArrayList<CentroCosto>();
		try{
			String IPSAP="";
			if(version.equals("p")){
				IPSAP="http://200.55.206.140/SCLEM/";
			}
			else if(version.equals("d")){
				IPSAP="http://200.54.43.156/SCLEM/";
			}
			//System.out.println(ceco+"-");
		if(ceco==null){
			ceco="";
		}
		URL url = new URL(IPSAP+"JSON_BAPI_COSTCENTER_GETLIST1.aspx?SOCIEDAD=''&GRUPO=&CECO="+ceco);//your url i.e fetch data from .
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        InputStreamReader in = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(in);
        String output;
        
        Gson g= new Gson();
        String out="";
        while ((output = br.readLine()) != null) {
            out=out+output;
        }
        byte[] bytes=out.getBytes();
        out=new String(bytes);
        ArrayCentro c=g.fromJson(out, ArrayCentro.class);
        	for(CentroCosto cc: c.getCOSTCENTERLIST()){
        		CentroCosto cdc= new CentroCosto();
        		cdc=cc;
        		cdc.setNAME(cdc.getNAME().replace("Ã¡", "á"));
        		cdc.setNAME(cdc.getNAME().replace("Ã?", "Á"));
        		cdc.setNAME(cdc.getNAME().replace("Ã³", "ó"));
        		cdc.setNAME(cdc.getNAME().replace("Ãº", "ú"));
        		cdc.setNAME(cdc.getNAME().replace("Ã¼", "ü"));
        		cdc.setNAME(cdc.getNAME().replace("Ã©", "é"));
        		cdc.setNAME(cdc.getNAME().replace("Ã", "í"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ã¡", "á"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ã?", "Á"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ã³", "ó"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ãº", "ú"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ã¼", "ü"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ã©", "é"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ã", "í"));
        		Lista.add(cdc);
        	}
        conn.disconnect();
		}
		catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
		return Lista;
	}
	public static ArrayList<CentroCosto> getCentrosCostosBySoc(String soc,String version){
		ArrayList<CentroCosto> Lista=new ArrayList<CentroCosto>();
		try{
			String IPSAP="";
			
			if(version.equals("p")){
				IPSAP="http://200.55.206.140/SCLEM/";
			}
			else if(version.equals("d")){
				IPSAP="http://200.54.43.156/SCLEM/";
			}
			System.out.println(soc+"-");
		if(soc==null){
			soc="";
		}
		URL url = new URL(IPSAP+"JSON_BAPI_COSTCENTER_GETLIST1.aspx?SOCIEDAD="+soc+"&GRUPO=&CECO=");//your url i.e fetch data from .
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        InputStreamReader in = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(in);
        String output;
        Gson g= new Gson();
        String out="";
        while ((output = br.readLine()) != null) {
            out=out+output;
        }
        byte[] bytes=out.getBytes();
        out=new String(bytes);
        
        
        
        ArrayCentro c=g.fromJson(out, ArrayCentro.class);
        	for(CentroCosto cc: c.getCOSTCENTERLIST()){
        		CentroCosto cdc= new CentroCosto();
        		cdc=cc;
        		cdc.setNAME(cdc.getNAME().replace("Ã¡", "á"));
        		cdc.setNAME(cdc.getNAME().replace("Ã?", "Á"));
        		cdc.setNAME(cdc.getNAME().replace("Ã³", "ó"));
        		cdc.setNAME(cdc.getNAME().replace("Ãº", "ú"));
        		cdc.setNAME(cdc.getNAME().replace("Ã¼", "ü"));
        		cdc.setNAME(cdc.getNAME().replace("Ã©", "é"));
        		cdc.setNAME(cdc.getNAME().replace("Ã", "í"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ã¡", "á"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ã?", "Á"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ã³", "ó"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ãº", "ú"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ã¼", "ü"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ã©", "é"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ã", "í"));
        		Lista.add(cdc);
        	}	
        conn.disconnect();
		}
		catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
		return Lista;
	}
	public static ArrayList<CentroCosto> getCentrosCostos(String soc, String gru, String ceco, String version) {
		ArrayList<CentroCosto> Lista=new ArrayList<CentroCosto>();
		try{
			String IPSAP="";
			
			if(version.equals("p")){
				IPSAP="http://200.55.206.140/SCLEM/";
			}
			else if(version.equals("d")){
				IPSAP="http://200.54.43.156/SCLEM/";
			}
			
			System.out.println(soc+"-"+gru+"-"+ceco);
			
			
		if(soc==null){
			soc="";
		}
		if(gru==null){
			gru="";
		}
		if(ceco==null){
			ceco="";
		}	
		URL url = new URL(IPSAP+"JSON_BAPI_COSTCENTER_GETLIST1.aspx?SOCIEDAD=soc&GRUPO=gru&CECO=ceco");//your url i.e fetch data from .
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        InputStreamReader in = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(in);
        String output;
        Gson g= new Gson();
        String out="";
        while ((output = br.readLine()) != null) {
            out=out+output;
        }
        byte[] bytes=out.getBytes();
        out=new String(bytes);
        ArrayCentro c=g.fromJson(out, ArrayCentro.class);
        	for(CentroCosto cc: c.getCOSTCENTERLIST()){
        		CentroCosto cdc= new CentroCosto();
        		cdc=cc;
        		cdc.setNAME(cdc.getNAME().replace("Ã¡", "á"));
        		cdc.setNAME(cdc.getNAME().replace("Ã?", "Á"));
        		cdc.setNAME(cdc.getNAME().replace("Ã³", "ó"));
        		cdc.setNAME(cdc.getNAME().replace("Ãº", "ú"));
        		cdc.setNAME(cdc.getNAME().replace("Ã¼", "ü"));
        		cdc.setNAME(cdc.getNAME().replace("Ã©", "é"));
        		cdc.setNAME(cdc.getNAME().replace("Ã", "í"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ã¡", "á"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ã?", "Á"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ã³", "ó"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ãº", "ú"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ã¼", "ü"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ã©", "é"));
        		cdc.setDESCRIPT(cdc.getDESCRIPT().replace("Ã", "í"));
        		Lista.add(cdc);
        	}
        conn.disconnect();
		}
		catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
		return Lista;
	}

}

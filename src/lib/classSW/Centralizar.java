package lib.classSW;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.google.gson.Gson;



public class Centralizar {

	public String CentralizarDatos(ArrayList<CentraRow> rows, String soc, int periodo, String date, String usuario) {
		// TODO Auto-generated method stub
		//fecha Actual
		
	    LocalDate localDate = LocalDate.parse(date);
        String n =DateTimeFormatter.ofPattern("yyyyMMdd").format(localDate);
        int fecha=Integer.parseInt(n);
		String nofiscal=""+periodo;
		nofiscal=nofiscal.substring(0, 4);
		int nofis=Integer.parseInt(nofiscal);
		CentraJSON cj=new CentraJSON();
		cj.setBAPI("BAPI_ACC_DOCUMENT_POST");
		cj.setRUNTEST("false");
		CentraObject co=new CentraObject();
		co.setBUS_ACT("RFBU");
		co.setTIPO_DOC("KA");
		co.setTEXTO("ANT. SA CONT");
		co.setUSUARIO(usuario);
		co.setSOCIEDAD(soc);
		co.setFECHA(fecha);
		co.setANOFISCAL(nofis);
		co.setREFERENCIA("TEXTO");
		ArrayList<CuentaCentra> CUENTAS=new ArrayList<CuentaCentra>();
		for(int i=0;i<rows.size();i++){
			CentraRow cr=rows.get(i);
			CuentaCentra cc= new CuentaCentra();
			if( (rows.get(i).getProveedor()==null || "".equals(rows.get(i).getProveedor())) && (rows.get(i).getDescripcion()==null || "".equals(rows.get(i).getDescripcion())  ) ){
				
				System.out.println("El item "+i+" va en null");
				System.out.println("/-------------------------"+i+"----------------------------/");
				System.out.println("Sociedad:    "+cr.getSociedad()+"\n"+
								   "Concepto:    "+cr.getConcepto()+"\n"+
								   "Descripcion: "+cr.getDescripcion()+"\n"+
								   "Proveedor:   "+cr.getProveedor()+"\n"+
								   "idCECO:   	 "+cr.getIdCECO()+"\n"+
								   "Monto:       "+cr.getMonto()+"\n"+
								   "Proveedor:   "+cr.getProveedor());
				System.out.println("/----------------------------------------------------------/");
				cc.setITEM(i+1);;
				cc.setCUENTA(rows.get(i).getCuenta());
				cc.setCENTROCOSTO(rows.get(i).getIdCECO());
				cc.setORDENCO(rows.get(i).getOrdenco());
				cc.setVALOR(""+rows.get(i).getMonto());
				cc.setPROVEEDOR("");
				cc.setALLOC_NMBR(rows.get(i).getConcepto());
				CUENTAS.add(cc);
			}
			else if(rows.get(i).getProveedor()!=null && rows.get(i).getProveedor()!="" ){
				
				System.out.println("El item "+i+" no le pases cuenta ni ceco");
				System.out.println("/-------------------------"+i+"----------------------------/");
				System.out.println("Sociedad:    "+cr.getSociedad()+"\n"+
								   "Concepto:    "+cr.getConcepto()+"\n"+
								   "Descripcion: "+cr.getDescripcion()+"\n"+
								   "Proveedor:   "+cr.getProveedor()+"\n"+
								   "idCECO:   	 "+cr.getIdCECO()+"\n"+
								   "Monto:       "+cr.getMonto()+"\n"+
								   "Proveedor:   "+cr.getProveedor());
				System.out.println("/----------------------------------------------------------/");
				cc.setITEM(i+1);;
				cc.setCUENTA("");
				cc.setCENTROCOSTO("");
				cc.setORDENCO("");
				cc.setVALOR(""+rows.get(i).getMonto());
				cc.setPROVEEDOR(""+rows.get(i).getProveedor());
				cc.setALLOC_NMBR(""+rows.get(i).getDescripcion());
				CUENTAS.add(cc);
				
			}
			else{
				System.out.println("El item "+i+" no va en null");
				System.out.println("/-------------------------"+i+"----------------------------/");
				System.out.println("Sociedad:    "+cr.getSociedad()+"\n"+
								   "Concepto:    "+cr.getConcepto()+"\n"+
								   "Descripcion: "+cr.getDescripcion()+"\n"+
								   "Proveedor:   "+cr.getProveedor()+"\n"+
								   "idCECO:   	 "+cr.getIdCECO()+"\n"+
								   "Monto:       "+cr.getMonto()+"\n"+
								   "Proveedor:   "+cr.getProveedor());
				System.out.println("/----------------------------------------------------------/");
				cc.setITEM(i+1);;
				cc.setCUENTA(rows.get(i).getCuenta());
				cc.setCENTROCOSTO(rows.get(i).getIdCECO());
				cc.setORDENCO(rows.get(i).getOrdenco());
				cc.setVALOR(""+rows.get(i).getMonto());
				cc.setPROVEEDOR(""+rows.get(i).getProveedor());
				cc.setALLOC_NMBR(""+rows.get(i).getDescripcion());
				CUENTAS.add(cc);
			}			
		}
		co.setCUENTAS(CUENTAS);
		cj.setPARAMETROS(co);
		
		Gson g= new Gson();
		
		String d= g.toJson(cj);
		
		return d;
	}
	
	
	public String CentralizarDatosExcel(ArrayList<CentraRow> rows, String soc, int periodo, String date, String usuario) {
		// TODO Auto-generated method stub
		//fecha Actual
		
	    LocalDate localDate = LocalDate.parse(date);
        String n =DateTimeFormatter.ofPattern("yyyyMMdd").format(localDate);
        int fecha=Integer.parseInt(n);
		String nofiscal=""+periodo;
		nofiscal=nofiscal.substring(0, 4);
		int nofis=Integer.parseInt(nofiscal);
		CentraJSON cj=new CentraJSON();
		cj.setBAPI("BAPI_ACC_DOCUMENT_POST");
		cj.setRUNTEST("false");
		CentraObject co=new CentraObject();
		co.setBUS_ACT("RFBU");
		co.setTIPO_DOC("KA");
		co.setTEXTO("ANT. SA CONT");
		co.setUSUARIO(usuario);
		co.setSOCIEDAD(soc);
		co.setFECHA(fecha);
		co.setANOFISCAL(nofis);
		co.setREFERENCIA("TEXTO");
		ArrayList<CuentaCentra> CUENTAS=new ArrayList<CuentaCentra>();
		for(int i=0;i<rows.size();i++){
			CuentaCentra cc= new CuentaCentra();
				cc.setITEM(i+1);;
				cc.setCUENTA(rows.get(i).getCuenta());
				cc.setCENTROCOSTO(rows.get(i).getIdCECO());
				cc.setORDENCO(rows.get(i).getOrdenco());
				cc.setVALOR(rows.get(i).getMonto()+"");
				cc.setPROVEEDOR(""+rows.get(i).getProveedor());
				cc.setALLOC_NMBR(rows.get(i).getConcepto());
				CUENTAS.add(cc);
		}
		co.setCUENTAS(CUENTAS);
		cj.setPARAMETROS(co);
		
		Gson g= new Gson();
		
		String d= g.toJson(cj);
		
		return d;
	}

	
}

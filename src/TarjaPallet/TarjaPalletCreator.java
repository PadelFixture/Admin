package TarjaPallet;


import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.InputStream;
import java.lang.reflect.Field;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.google.gson.Gson;

import lib.ClassSASW.ResponseObject;
import lib.ClassSASW.TarjaPallet;
import lib.db.sw.DocumentsDB;
import lib.db.sw.TarjaPalletDB;

public class TarjaPalletCreator {
	XWPFDocument wordx;
	FileOutputStream out;
	FileInputStream in;

	public TarjaPalletCreator() {
		wordx = new XWPFDocument();// Constructor de la clase, solo instancia

	}

	public XWPFDocument CreateDocument(TarjaPallet tp) throws Exception {
		tp.PrintObject();
		ResponseObject ro= new ResponseObject();
		Blob Contrato = DocumentsDB.getFileById("73");
		InputStream in=Contrato.getBinaryStream();
		XWPFDocument doc = new XWPFDocument(OPCPackage.open(in));
		System.out.println("numero de parrafos: "+doc.getParagraphs().size());
		
		for (XWPFParagraph p : doc.getParagraphs()) {
		    System.out.println(p.getText());
		}
		System.out.println("numero de tablas: "+ doc.getTables().size());
		
		
		for (XWPFTable tbl : doc.getTables()) {
			
			   for (XWPFTableRow row : tbl.getRows()) {
			      for (XWPFTableCell cell : row.getTableCells()) {
			         for (XWPFParagraph p : cell.getParagraphs()) {
			            System.out.println(p.getText());
			         }
			      }
			   }
			}
		
		return doc;
		
	}
	public String CreateDocumento2(String string, String string2, TarjaPallet tp, FileInputStream fi) throws Exception {
		String OutPath="";
		
		tp.PrintObject();
		ResponseObject ro= new ResponseObject();
		Blob Contrato = DocumentsDB.getFileById("73");
		InputStream in=Contrato.getBinaryStream();
		XWPFDocument doc = new XWPFDocument(OPCPackage.open(in));
		System.out.println("numero de parrafos: "+doc.getParagraphs().size());
		
		for (XWPFParagraph p : doc.getParagraphs()) {
		    System.out.println(p.getText());
		}
		System.out.println("numero de tablas: "+ doc.getTables().size());
		
		
		for (XWPFTable tbl : doc.getTables()) {
			
			   for (XWPFTableRow row : tbl.getRows()) {
			      for (XWPFTableCell cell : row.getTableCells()) {
			         for (XWPFParagraph p : cell.getParagraphs()) {
			            System.out.println(p.getText());
			         }
			      }
			   }
			}
		
		return OutPath;
		
	}

	public void CreateDocumento(String pathSalida, String string2, TarjaPallet tp) throws Exception {
		System.out.println("estoy aca");
		Field pathField = FileInputStream.class.getDeclaredField("path");
		pathField.setAccessible(true);
		// String ext= FilenameUtils.getExtension(pathfinal);
		// recupera el nombre del archivo
		String fileName = "";
		Blob Contrato = DocumentsDB.getFileById("75");
		System.out.println("saque la template de la DB");
		InputStream in=Contrato.getBinaryStream();
		OPCPackage pkg = OPCPackage.open(in);
		XWPFDocument doc = new XWPFDocument(pkg);
		
		System.out.println("numero de tablas: "+ doc.getTables().size());
		Gson g= new Gson();
		System.out.println(g.toJson(tp));
		try{
		int counter=0;
		for (XWPFTable tbl : doc.getTables()) {
			if(counter==0 || counter==1 || counter==4){
				 for (XWPFTableRow row : tbl.getRows()) {
				      for (XWPFTableCell cell : row.getTableCells()) {
				    	 for(XWPFParagraph p: cell.getParagraphs()){
				    		 String tex=p.getText();
				 			for (int i = 0; i < tp.getDatos().length; i++) {
								if (tex != null && tex.contains(tp.getDatos()[i][0])) {
									String salvador=tp.getDatos()[i][1]==null?"":tp.getDatos()[i][1];
									tex = tex.replace(tp.getDatos()[i][0].trim(),
											salvador);
									//
								}
							}
				 			int a = 0;
				 			if (p.getRuns() != null) {
				 				for (XWPFRun r : p.getRuns()) {
				 					if (a == 0) {
				 						r.setText(tex, 0);
				 					} else {
				 						r.setText("", 0);
				 					}
				 					a++;
				 				}
				 			}
				    	 }
				      }
				   }
			}
			else if(counter==2){
				 for (XWPFTableRow row : tbl.getRows()) {
				      for (XWPFTableCell cell : row.getTableCells()) {
				    	 for(XWPFParagraph p: cell.getParagraphs()){
				    		 String tex=p.getText();
				 			for (int i = 0; i < tp.getDatosPrincipales().length; i++) {
								
								if (tex != null && tex.contains(tp.getDatosPrincipales()[i][0])) {
									String salvador=tp.getDatosPrincipales()[i][1]==null?"":tp.getDatosPrincipales()[i][1];
									tex = tex.replace(tp.getDatosPrincipales()[i][0].trim(),
											salvador);
									//
									
									
								}
								
							}
				 			int a = 0;
				 			if (p.getRuns() != null) {
				 				for (XWPFRun r : p.getRuns()) {
				 					if (a == 0) {
				 						r.setText(tex, 0);
				 					} else {
				 						r.setText("", 0);
				 					}
				 					a++;
				 				}
				 			}
				    		 
				    	 }
				         
				      }
				   }
				
			}
			else if(counter==3){
				for(int i=0;i<tp.getTablaDatos().length;i++){
					tbl.createRow();
					XWPFTableRow row =tbl.getRow(i+1);
					int celda=0;
					for (XWPFTableCell cell : row.getTableCells()) {
						cell.setText(tp.getTablaDatos()[i][celda]);
				         celda++;
				      }
					celda=0;
				}
			}
			counter++;
			  
			}
		/* Constructor del path */
		
		fileName = string2 + ".docx";
		System.out.println("hola estoy casi terminando");
		
		String outPath = pathSalida + fileName;
		System.out.println("out: "+outPath);
		Path path = Paths.get(outPath);
		System.out.println("out: "+path);
		//Files.createFile(path);
		System.out.println("outPath "+outPath);
		FileOutputStream ou=new FileOutputStream(outPath);
		doc.write(ou);
		
		
		
		
		
		doc.close();
		ou.close();		
		 System.out.println("Documento creado");
		//
		//
		 TarjaPalletDB.CreateTarja(tp);
		 
		 
		 
		 
	}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	public void CreateDocumentoB(String pathSalida, String string2, TarjaPallet tp) throws Exception {
		System.out.println("estoy aca");
		Field pathField = FileInputStream.class.getDeclaredField("path");
		pathField.setAccessible(true);
		// String ext= FilenameUtils.getExtension(pathfinal);
		// recupera el nombre del archivo
		String fileName = "";
		Blob Contrato = DocumentsDB.getFileById("75");
		System.out.println("saque la template de la DB");
		InputStream in=Contrato.getBinaryStream();
		OPCPackage pkg = OPCPackage.open(in);
		XWPFDocument doc = new XWPFDocument(pkg);
		
		System.out.println("numero de tablas: "+ doc.getTables().size());
		Gson g= new Gson();
		System.out.println(g.toJson(tp));
		int counter=0;
		for (XWPFTable tbl : doc.getTables()) {
			if(counter==0 || counter==1 || counter==4){
				 for (XWPFTableRow row : tbl.getRows()) {
				      for (XWPFTableCell cell : row.getTableCells()) {
				    	 for(XWPFParagraph p: cell.getParagraphs()){
				    		 String tex=p.getText();
				 			for (int i = 0; i < tp.getDatos().length; i++) {
								if (tex != null && tex.contains(tp.getDatos()[i][0])) {
									String salvador=tp.getDatos()[i][1]==null?"":tp.getDatos()[i][1];
									tex = tex.replace(tp.getDatos()[i][0].trim(),
											salvador);
									//
								}
							}
				 			int a = 0;
				 			if (p.getRuns() != null) {
				 				for (XWPFRun r : p.getRuns()) {
				 					if (a == 0) {
				 						r.setText(tex, 0);
				 					} else {
				 						r.setText("", 0);
				 					}
				 					a++;
				 				}
				 			}
				    	 }
				      }
				   }
			}
			else if(counter==2){
				 for (XWPFTableRow row : tbl.getRows()) {
				      for (XWPFTableCell cell : row.getTableCells()) {
				    	 for(XWPFParagraph p: cell.getParagraphs()){
				    		 String tex=p.getText();
				 			for (int i = 0; i < tp.getDatosPrincipales().length; i++) {
								
								if (tex != null && tex.contains(tp.getDatosPrincipales()[i][0])) {
									String salvador=tp.getDatosPrincipales()[i][1]==null?"":tp.getDatosPrincipales()[i][1];
									tex = tex.replace(tp.getDatosPrincipales()[i][0].trim(),
											salvador);
									//
								}
							}
				 			int a = 0;
				 			if (p.getRuns() != null) {
				 				for (XWPFRun r : p.getRuns()) {
				 					if (a == 0) {
				 						r.setText(tex, 0);
				 					} else {
				 						r.setText("", 0);
				 					}
				 					a++;
				 				}
				 			}
				    	 }
				      }
				   }
			}
			else if(counter==3){
				
				for(int i=0;i<tp.getTablaDatos().length;i++){
					tbl.createRow();
					XWPFTableRow row =tbl.getRow(i+1);
					int celda=0;
					for (XWPFTableCell cell : row.getTableCells()) {
						cell.setText(tp.getTablaDatos()[i][celda]);
				         celda++;
				      }
					celda=0;
				}
			}
			counter++;
			  
			}
		/* Constructor del path */
		
		fileName = string2 + ".docx";
		System.out.println("hola estoy casi terminando");
		
		String outPath = pathSalida + fileName;
		System.out.println("out: "+outPath);
		Path path = Paths.get(outPath);
		System.out.println("out: "+path);
		//Files.createFile(path);
		System.out.println("outPath "+outPath);
		FileOutputStream ou=new FileOutputStream(outPath);
		doc.write(ou);
		doc.close();
		ou.close();		
		 System.out.println("Documento creado");
		//
		//
		
		
	}
}

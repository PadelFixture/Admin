package excelCreator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import lib.classSW.CentraRow;
import lib.classSW.CentraRowReporte;
import lib.classSW.LibroRemuneraciones;

import wordCreator.utils;
public class Excel2 {
	@SuppressWarnings("unused")
	private String nombre;
	@SuppressWarnings("unused")
	private String output;
	public Connection conn = null;
	
	public Excel2(String nombre, String output){
		this.nombre=nombre;
		this.output=output;
		
	}
	public Excel2(){
		this.nombre="";
		this.output="";
		
	}
@SuppressWarnings("deprecation")
public void CrearLibroDeRemuneraciones(ArrayList<LibroRemuneraciones> l,String nombreArchivo) throws SQLException
{
	ArrayList<LibroRemuneraciones> libro= l;
	
	File archivo = new File(nombreArchivo+".xlsx");
    Workbook workbook = new XSSFWorkbook(); 
    Sheet pagina = workbook.createSheet("Reporte");
    CellStyle style = workbook.createCellStyle();
    style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
    style.setFillPattern(CellStyle.SOLID_FOREGROUND);

    String[] titulos= new String[libro.size()];
    titulos[0]="Cod. Trabajador";
    titulos[1]="Nombre";
    titulos[2]="Apellido Paterno";
    titulos[3]="Apellido Materno";
    titulos[4]="id Contrato";
    titulos[5]="Periodo";
    titulos[6]="Tipo Trabajador";
    titulos[7]="Sueldo Base";
    titulos[8]="Días Trabajados";
    titulos[9]="Sueldo Base 2";
    titulos[10]="Bonos Imponibles";
    titulos[11]="Hora Extra N";
    titulos[12]="Hora Extra Monto";
    titulos[13]="Hora Falta N";
    titulos[14]="Hora Falta Monto";
    titulos[15]="Gratificación";
    titulos[16]="Total Haberes Imponibles";
    titulos[17]="Bonos No Imponibles";
    titulos[18]="Carga Familiar Simple";
    titulos[19]="Carga Familiar Maternal";
    titulos[20]="Carga Familiars Retroactivos";
    titulos[21]="Total Haberes No Imponibles";
    titulos[22]="Total Haberes";
    titulos[23]="Base Tributable";
    titulos[24]="AFP";
    titulos[25]="Salud";
    titulos[26]="Caja";
    titulos[27]="Seguro Cesant�a AFC Trabajador";
    titulos[28]="APV";
    titulos[29]="Impuesto �nico";
    titulos[30]="Total Descuentos Imponibles";    
    titulos[31]="Anticipos";
    titulos[32]="Descuentos";
    titulos[33]="Ahorro Voluntario AFP";
    titulos[34]="Total Descuentos No Imponibles";
    titulos[35]="Total Descuentos";
    titulos[36]="Cotizaci�n SIS";
    titulos[37]="Seguro Cesant�a AFC";
    titulos[38]="Cotización B�sica";
    titulos[39]="Cotización Adicional";
    titulos[40]="Cotización Extraordinaria";
    titulos[41]="SANNA";
    titulos[42]="Total Pago";
    titulos[43]="Total Liquido Mes";
    titulos[44]="Cod Empresa";
    titulos[45]="Nombre Empresa";
    
    Row fila = pagina.createRow(0);
    for(int i = 0; i < titulos.length; i++) {
        Cell celda = fila.createCell(i);
        celda.setCellStyle(style); 
        celda.setCellValue(titulos[i]);
    }
    for(int i = 0; i <libro.size() ; i++) {
    	fila = pagina.createRow((i+1));
    	Cell celda = fila.createCell(0);
        celda.setCellValue(libro.get(i).getCodTrabajador());        
        celda = fila.createCell(1);
        celda.setCellValue(libro.get(i).getNombre());
        celda = fila.createCell(2);
        celda.setCellValue(libro.get(i).getApellidoPaterno());
        celda = fila.createCell(3);
        celda.setCellValue(libro.get(i).getApellidoMaterno());
        celda = fila.createCell(4);
        celda.setCellValue(libro.get(i).getIdContrato());
        celda = fila.createCell(5);
        celda.setCellValue(libro.get(i).getPeriodo());
        celda = fila.createCell(6);
        celda.setCellValue(""+libro.get(i).getTipoTrabajador());
        celda = fila.createCell(7);
        celda.setCellValue(""+libro.get(i).getSueldoBase());
        celda = fila.createCell(8);
        celda.setCellValue(""+libro.get(i).getDiasTrabajados());
        celda = fila.createCell(9);
        celda.setCellValue(""+libro.get(i).getSueldoBase2());
        celda = fila.createCell(10);
        celda.setCellValue(""+libro.get(i).getBonosImponibles());
        
        celda = fila.createCell(11);
        celda.setCellValue(""+libro.get(i).getHoraExtra());
        celda = fila.createCell(12);
        celda.setCellValue(""+libro.get(i).getHoraExtra());
        
        celda = fila.createCell(13);
        celda.setCellValue(""+libro.get(i).getHoraFalta());
        celda = fila.createCell(14);
        celda.setCellValue(""+libro.get(i).getMontoHorafalta());
        
       
        celda = fila.createCell(15);
        celda.setCellValue(""+libro.get(i).getGratificacion());
        celda = fila.createCell(16);
        celda.setCellValue(""+libro.get(i).getTotalHaberesImponibles());
        celda = fila.createCell(17);
        celda.setCellValue(""+libro.get(i).getBonosNoImponibles());
        celda = fila.createCell(18);
        celda.setCellValue(""+libro.get(i).getCargaFamiliarSimple());
        celda = fila.createCell(19);
        celda.setCellValue(""+libro.get(i).getCargaFamiliarMaternal());
        celda = fila.createCell(20);
        celda.setCellValue(""+libro.get(i).getCargaFamiliaresRetro());
        celda = fila.createCell(21);
        celda.setCellValue(""+libro.get(i).getTotalHabNoImponible());
        celda = fila.createCell(22);
        celda.setCellValue(""+libro.get(i).getTotalHaberes());
        celda = fila.createCell(23);
        celda.setCellValue(""+libro.get(i).getBaseTributable());
        celda = fila.createCell(24);
        celda.setCellValue(""+libro.get(i).getAFP());
        celda = fila.createCell(25);
        celda.setCellValue(""+libro.get(i).getSalud());
        celda = fila.createCell(26);
        celda.setCellValue(""+libro.get(i).getCaja());
        celda = fila.createCell(27);
        celda.setCellValue(""+libro.get(i).getSeguroCesantiaAFCTrabajador());
        celda = fila.createCell(28);
        celda.setCellValue(""+libro.get(i).getAPV());
        celda = fila.createCell(29);
        celda.setCellValue(""+libro.get(i).getImpuestoUnico());
        celda = fila.createCell(30);
        celda.setCellValue(""+libro.get(i).getTotalDescuentosImp());
        celda = fila.createCell(31);
        celda.setCellValue(""+libro.get(i).getAnticipo());
        celda = fila.createCell(32);
        celda.setCellValue(""+libro.get(i).getDescuentos());
        celda = fila.createCell(33);
        celda.setCellValue(""+libro.get(i).getAhoroVoluntarioAfp());
        celda = fila.createCell(34);
        celda.setCellValue(""+libro.get(i).getTotalDescuentosNoImp());
        celda = fila.createCell(35);
        celda.setCellValue(""+libro.get(i).getTotalDescuentos());
        celda = fila.createCell(36);
        celda.setCellValue(""+libro.get(i).getCotizacionSIS());
        celda = fila.createCell(37);
        celda.setCellValue(""+libro.get(i).getSeguroSesantiaAFC());
        celda = fila.createCell(38);
        celda.setCellValue(""+libro.get(i).getCotizacionBasica());
        celda = fila.createCell(39);
        celda.setCellValue(""+libro.get(i).getCotizacionAdicional());
        celda = fila.createCell(40);
        celda.setCellValue(""+libro.get(i).getCotizacionExtraordinaria());
        celda = fila.createCell(41);
        celda.setCellValue(""+libro.get(i).getSANNA());
        celda = fila.createCell(42);
        celda.setCellValue(""+libro.get(i).getTotalPago());
        celda = fila.createCell(43);
        celda.setCellValue(""+libro.get(i).getTotalLiquidoMes());
        celda = fila.createCell(44);
        celda.setCellValue(""+libro.get(i).getSociedad());
        celda = fila.createCell(45);
        celda.setCellValue(""+libro.get(i).getDenominacionSociedad());
    }
    try {
        FileOutputStream salida = new FileOutputStream(archivo);
        workbook.write(salida);
        workbook.close();
        System.out.println("Archivo creado existosamente");
    } catch (FileNotFoundException ex) {
       System.out.println("Archivo no localizable en sistema de archivos");
    } catch (IOException ex) {
        System.out.println("Error de entrada/salida");
    }
}
@SuppressWarnings("deprecation")
public void CrearLibroRemuneracionesByEmpresaPeriodo(ArrayList<LibroRemuneraciones> l, String nombre2) {
ArrayList<LibroRemuneraciones> libro= l;
	
	File archivo = new File(nombre2+".xlsx");
    Workbook workbook = new XSSFWorkbook(); 
    Sheet pagina = workbook.createSheet("Reporte");
    CellStyle style = workbook.createCellStyle();
    style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
    style.setFillPattern(CellStyle.SOLID_FOREGROUND);

    String[] titulos= new String[libro.size()];
    titulos[0]="Cod. Trabajador";
    titulos[1]="Nombre";
    titulos[2]="Apellido Paterno";
    titulos[3]="Apellido Materno";
    titulos[4]="id Contrato";
    titulos[5]="Periodo";
    titulos[6]="Tipo Trabajador";
    titulos[7]="Sueldo Base";
    titulos[8]="Días Trabajados";
    titulos[9]="Sueldo Base 2";
    titulos[10]="Bonos Imponibles";
    titulos[11]="Hora Extra N";
    titulos[12]="Hora Extra Monto";
    titulos[13]="Hora Falta N";
    titulos[14]="Hora Falta Monto";
    titulos[15]="Gratificación";
    titulos[16]="Total Haberes Imponibles";
    titulos[17]="Bonos No Imponibles";
    titulos[18]="Carga Familiar Simple";
    titulos[19]="Carga Familiar Maternal";
    titulos[20]="Carga Familiars Retroactivos";
    titulos[21]="Total Haberes No Imponibles";
    titulos[22]="Total Haberes";
    titulos[23]="Base Tributable";
    titulos[24]="AFP";
    titulos[25]="Salud";
    titulos[26]="Caja";
    titulos[27]="Seguro Cesant�a AFC Trabajador";
    titulos[28]="APV";
    titulos[29]="Impuesto �nico";
    titulos[30]="Total Descuentos Imponibles";    
    titulos[31]="Anticipos";
    titulos[32]="Descuentos";
    titulos[33]="Ahorro Voluntario AFP";
    titulos[34]="Total Descuentos No Imponibles";
    titulos[35]="Total Descuentos";
    titulos[36]="Cotizaci�n SIS";
    titulos[37]="Seguro Cesant�a AFC";
    titulos[38]="Cotización Basica";
    titulos[39]="Cotización Adicional";
    titulos[40]="Cotización Extraordinaria";
    titulos[41]="SANNA";
    titulos[42]="Total Pago";
    titulos[43]="Total Liquido Mes";
    titulos[44]="Cod Empresa";
    titulos[45]="Nombre Empresa";
    
    Row fila = pagina.createRow(0);
    for(int i = 0; i < titulos.length; i++) {
        Cell celda = fila.createCell(i);
        celda.setCellStyle(style); 
        celda.setCellValue(titulos[i]);
    }
    for(int i = 0; i <libro.size() ; i++) {
    	fila = pagina.createRow((i+1));
    	Cell celda = fila.createCell(0);
        celda.setCellValue(libro.get(i).getCodTrabajador());        
        celda = fila.createCell(1);
        celda.setCellValue(libro.get(i).getNombre());
        celda = fila.createCell(2);
        celda.setCellValue(libro.get(i).getApellidoPaterno());
        celda = fila.createCell(3);
        celda.setCellValue(libro.get(i).getApellidoMaterno());
        celda = fila.createCell(4);
        celda.setCellValue(libro.get(i).getIdContrato());
        celda = fila.createCell(5);
        celda.setCellValue(libro.get(i).getPeriodo());
        celda = fila.createCell(6);
        celda.setCellValue(""+libro.get(i).getTipoTrabajador());
        celda = fila.createCell(7);
        celda.setCellValue(""+libro.get(i).getSueldoBase());
        celda = fila.createCell(8);
        celda.setCellValue(""+libro.get(i).getDiasTrabajados());
        celda = fila.createCell(9);
        celda.setCellValue(""+libro.get(i).getSueldoBase2());
        celda = fila.createCell(10);
        celda.setCellValue(""+libro.get(i).getBonosImponibles());
        celda = fila.createCell(11);
        celda.setCellValue(""+libro.get(i).getHoraExtra());
        celda = fila.createCell(12);
        celda.setCellValue(""+libro.get(i).getHoraExtra());
        
        celda = fila.createCell(13);
        celda.setCellValue(""+libro.get(i).getHoraFalta());
        celda = fila.createCell(14);
        celda.setCellValue(""+libro.get(i).getMontoHorafalta());
        
        celda = fila.createCell(15);
        celda.setCellValue(""+libro.get(i).getGratificacion());
        celda = fila.createCell(16);
        celda.setCellValue(""+libro.get(i).getTotalHaberesImponibles());
        celda = fila.createCell(17);
        celda.setCellValue(""+libro.get(i).getBonosNoImponibles());
        celda = fila.createCell(18);
        celda.setCellValue(""+libro.get(i).getCargaFamiliarSimple());
        celda = fila.createCell(19);
        celda.setCellValue(""+libro.get(i).getCargaFamiliarMaternal());
        celda = fila.createCell(20);
        celda.setCellValue(""+libro.get(i).getCargaFamiliaresRetro());
        celda = fila.createCell(21);
        celda.setCellValue(""+libro.get(i).getTotalHabNoImponible());
        celda = fila.createCell(22);
        celda.setCellValue(""+libro.get(i).getTotalHaberes());
        celda = fila.createCell(23);
        celda.setCellValue(""+libro.get(i).getBaseTributable());
        celda = fila.createCell(24);
        celda.setCellValue(""+libro.get(i).getAFP());
        celda = fila.createCell(25);
        celda.setCellValue(""+libro.get(i).getSalud());
        celda = fila.createCell(26);
        celda.setCellValue(""+libro.get(i).getCaja());
        celda = fila.createCell(27);
        celda.setCellValue(""+libro.get(i).getSeguroCesantiaAFCTrabajador());
        celda = fila.createCell(28);
        celda.setCellValue(""+libro.get(i).getAPV());
        celda = fila.createCell(29);
        celda.setCellValue(""+libro.get(i).getImpuestoUnico());
        celda = fila.createCell(30);
        celda.setCellValue(""+libro.get(i).getTotalDescuentosImp());
        celda = fila.createCell(31);
        celda.setCellValue(""+libro.get(i).getAnticipo());
        celda = fila.createCell(32);
        celda.setCellValue(""+libro.get(i).getDescuentos());
        celda = fila.createCell(33);
        celda.setCellValue(""+libro.get(i).getAhoroVoluntarioAfp());
        celda = fila.createCell(34);
        celda.setCellValue(""+libro.get(i).getTotalDescuentosNoImp());
        celda = fila.createCell(35);
        celda.setCellValue(""+libro.get(i).getTotalDescuentos());
        celda = fila.createCell(36);
        celda.setCellValue(""+libro.get(i).getCotizacionSIS());
        celda = fila.createCell(37);
        celda.setCellValue(""+libro.get(i).getSeguroSesantiaAFC());
        celda = fila.createCell(38);
        celda.setCellValue(""+libro.get(i).getCotizacionBasica());
        celda = fila.createCell(39);
        celda.setCellValue(""+libro.get(i).getCotizacionAdicional());
        celda = fila.createCell(40);
        celda.setCellValue(""+libro.get(i).getCotizacionExtraordinaria());
        celda = fila.createCell(41);
        celda.setCellValue(""+libro.get(i).getSANNA());
        celda = fila.createCell(42);
        celda.setCellValue(""+libro.get(i).getTotalPago());
        celda = fila.createCell(43);
        celda.setCellValue(""+libro.get(i).getTotalLiquidoMes());
        celda = fila.createCell(44);
        celda.setCellValue(""+libro.get(i).getSociedad());
        celda = fila.createCell(45);
        celda.setCellValue(""+libro.get(i).getDenominacionSociedad());
    }
    try {
        FileOutputStream salida = new FileOutputStream(archivo);
        workbook.write(salida);
        workbook.close();
        System.out.println("Archivo creado existosamente");
    } catch (FileNotFoundException ex) {
       System.out.println("Archivo no localizable en sistema de archivos");
    } catch (IOException ex) {
        System.out.println("Error de entrada/salida");
    }
}
@SuppressWarnings({ "deprecation", "unused" })
public String CrearLibroRemuneraciones(ArrayList<LibroRemuneraciones> l, String nombre2) throws SQLException {
ArrayList<LibroRemuneraciones> libro= l;
//System.out.println(l.size());
	if(l.size()==0){
		return "0";
	}
	else{
	LibroRemuneraciones r=new LibroRemuneraciones();
	r=l.get(0);
	
	// CANTIDAD HABERES IMPONIBLES FINIQUITO
	int variablef=l.get(0).getHaberesImponiblesF().size();
	// CANTIDAD HABERES NO IMPONIBLES FINIQUITO
	int variableHaberNoImponibleF =l.get(0).getHaberesNoImponiblesF().size();
	// CANTIDAD DESCUENTOS FINIQUITO
	int variableDescuentoF =l.get(0).getDescuentoF().size();
	// COSTO EMPRESA FINIQUITO
	int variableCostoEmpresaF =l.get(0).getCostoempresaF().size();
	
	// total item columna en blanco + COSTO EMPRESA + TOTAL AFP
	int cantidadItemFinal = 3;
	
	
	
	String urlDocGenerado = utils.reportesExcel();
	
	urlDocGenerado=urlDocGenerado+nombre2+".xlsx";
	
	File archivo = new File(urlDocGenerado);
    Workbook workbook = new XSSFWorkbook(); 
    Sheet pagina = workbook.createSheet("Reporte");
    CellStyle style0=workbook.createCellStyle();

    CellStyle style = workbook.createCellStyle();
    style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	
    
    int variables=l.get(0).getHaberesImponibles().size();
	variables=variables+l.get(0).getHaberesNoImponibles2().size();
	
   
    int variableDescuentos =l.get(0).getDescuento2().size();
    System.out.println("variables descuento = "+variableDescuentos);
   
	variables=variables+59;
	String[] Titulos=new String[variableCostoEmpresaF+variableDescuentoF+variableHaberNoImponibleF+variablef+variables+3+cantidadItemFinal+variableDescuentos];
	Titulos[0]="#";
	Titulos[1]="Cod. Trabajador";
	Titulos[2]="Apellido Paterno";
	Titulos[3]="Apellido Materno";
	Titulos[4]="Nombre";
	Titulos[5]="Rut Trab.";
	Titulos[6]="Cargo";
	Titulos[7]="FECHA DE INICIO";
	Titulos[8]="FECHA DE TERMINO";
	Titulos[9]="ARTICULO";
	Titulos[10]="CAUSAL";
				
	Titulos[11]="Huerto";
	Titulos[12]="CECO";
	Titulos[13]="Faena";
	Titulos[14]="Tipo De Cuenta";
	Titulos[15]="Fecha de Pago";
	
	Titulos[16]="Tipo Contrato";
	Titulos[17]="Dias Trabajados";
	Titulos[18]="Sueldo Base";
	Titulos[19]="Liquido AGRO";
	Titulos[20]="Total Bonos Imponibles";
	int add=1;
	
	
	for(int a=0;a<l.get(0).getHaberesImponibles().size();a++)
	{
		Titulos[20+a+1]=l.get(0).getHaberesImponibles().get(a).getDescripcion();
		add++;
	}
	
	int x=add+20;
	
	add=0;
	System.out.println(Titulos);
	Titulos[x]="BONO PRODUCCION(AGRO)";
	Titulos[x+1]="Hora Extra N�";
	Titulos[x+2]="Hora Extra Monto";
	Titulos[x+3]="Hora Falta N�";
	Titulos[x+4]="Hora Falta Monto";
	Titulos[x+5]="Gratificacion";
	
	int addIm=1;
	int totaltitu = x+5 + addIm;
	System.out.println("total " +totaltitu);
   // haberes imponibles finiquito
	if(variablef == 0){
		
	}else
	{
	for(int b=0;b < variablef;b++)
	{
		
		
		Titulos[totaltitu] = "FINIQUITO "+l.get(0).getHaberesImponiblesF().get(b).getDescripcion();
		addIm++;
		totaltitu ++;
		
	}
	
	}
	
	
	
	//System.out.println("total totaltu " +totaltitu+1);
	Titulos[totaltitu]="Total Haberes Imponibles";
	Titulos[totaltitu+1]="Total Bonos No Imponibles";
	x=totaltitu+1;

	add=1;
	
	System.out.println("total " +totaltitu+1);
	
	for(int b=0;b<l.get(0).getHaberesNoImponibles2().size();b++)
	{
		Titulos[x+b+1]=l.get(0).getHaberesNoImponibles2().get(b).getDescripcion();
		add++;
	}
	
	

     x=x+add;
	System.out.println("total x "+x);
	
	
   if(variableHaberNoImponibleF == 0){
		
	}else
	{
	for(int b=0;b < variableHaberNoImponibleF;b++)
	{
		
		
		Titulos[x] = "FINIQUITO "+l.get(0).getHaberesNoImponiblesF().get(b).getDescripcion();
		x++;
		
		
	}
	
	}
   System.out.println("total x2 "+x);
	
	Titulos[x]="Carga Familiar Simple";
	Titulos[x+1]="Carga Familiar Maternal";
	Titulos[x+2]="Carga Familiar Retroactivo";
	Titulos[x+3]="Total Haberes No Imponibles";
	Titulos[x+4]="Total Haberes";
	Titulos[x+5]="Nombre AFP";
	Titulos[x+6]="Valor AFP";
	Titulos[x+7]="Institucion Salud";
	Titulos[x+8]="Valor Salud";
	Titulos[x+9]="valor Caja";
	Titulos[x+10]="Adicional Salud";
	Titulos[x+11]="Seguro Cesantia AFC Trabajador";
	Titulos[x+12]="Institucion APV";
	Titulos[x+13]="APV";
	Titulos[x+14]="Impuesto Unico";
	Titulos[x+15]="Base Tributable";
	Titulos[x+16]="Total Descuentos Imponibles";
	Titulos[x+17]="Anticipos";
//	Titulos[x+19]="Descuentos";
	Titulos[x+18]="Ahorro Voluntario";
	
	int totalHastaAhorro =  x+19;
	
			if (variableDescuentoF == 0) {

			} else {
				for (int b = 0; b < variableDescuentoF; b++) {

					
					Titulos[totalHastaAhorro] = "FINIQUITO DESCUENTO "
							+ l.get(0).getDescuentoF().get(b).getDescripcion();
					totalHastaAhorro++;

				}

			}
	
	
	 
	int xxxxx = totalHastaAhorro;
	    

	int totalHastaDesc_F = xxxxx ;
	
	if(variableDescuentos == 0){
		
	}else
	{
	for(int b=0;b < variableDescuentos;b++)
	{
		Titulos[totalHastaDesc_F] = l.get(0).getDescuento2().get(b).getDescripcion();
		totalHastaDesc_F++;
		
	}
	
	}
	
	Titulos[totalHastaDesc_F]="Total Descuentos No Imponibles";
	
	int xx = totalHastaDesc_F +1;
	Titulos[xx]="Total Descuentos";
	Titulos[xx+1]="Total Pago";
	Titulos[xx+2]="Total Liquido Mes";
	Titulos[xx+3]="Cotizacion SIS";
	Titulos[xx+4]="Seguro Cesantia AFC Emp.";
	Titulos[xx+5]="Cotizacion Basica";
	Titulos[xx+6]="Cotizacion Adicional";
	Titulos[xx+7]="Cotizacion Extraordinaria";
	Titulos[xx+8]="SANNA";
	Titulos[xx+9]="Total ACHS";
	Titulos[xx+10]="COSTO E� VIDA CAMARA";
	int xxe = xx+11;
	// costo empresa finiquito
		if(variableCostoEmpresaF == 0){
			
		}else
		{
		for(int b=0;b < variableCostoEmpresaF;b++)
		{
			
			
			Titulos[xxe] = "FINIQUITO C.E"+l.get(0).getCostoempresaF().get(b).getDescripcion();
			xxe++;
			
			
		}
		
		}
		// columna en blanco
		Titulos[xxe]="";
		// COSTO EMPRESA
		Titulos[xxe+1]="COSTO EMPRESA";
		// TOTAL AFP
		Titulos[xxe+2]="TOTAL AFP";
		
		
		
	
	Row fila = pagina.createRow(0);
	Cell celda = fila.createCell(1);
	celda.setCellValue(l.get(0).getDenominacionSociedad().toUpperCase());
	 fila = pagina.createRow(1);
	 celda = fila.createCell(1);
	 
	 String p0=""+l.get(0).getPeriodo();
	 String p1="";
	 p1=p0.substring(4);
	 String p2=p0.substring(0, 4);
	 if(p1.equals("01")){
		 p1="ENERO DE ";
	 }
	 else if(p1.equals("02")){
		 p1="FEBRERO DE ";
	 }
	 else if(p1.equals("03")){
		 p1="MARZO DE ";
	 }else if(p1.equals("04")){
		 p1="ABRIL DE ";
	 }else if(p1.equals("05")){
		 p1="MAYO DE ";
	 }else if(p1.equals("06")){
		 p1="JUNIO DE ";
	 }else if(p1.equals("07")){
		 p1="JULIO DE ";
	 }else if(p1.equals("08")){
		 p1="AGOSTO DE ";
	 }else if(p1.equals("09")){
		 p1="SEPTIEMBRE DE ";
	 }else if(p1.equals("10")){
		 p1="OCTUBRE DE ";
	 }else if(p1.equals("11")){
		 p1="NOVIEMBRE DE ";
	 }else if(p1.equals("12")){
		 p1="DICIEMBRE DE ";
	 }
	 String pp=p1+p2;
	 
	 
	celda.setCellValue(pp);
	
	fila = pagina.createRow(2);
	fila = pagina.createRow(3);
	fila = pagina.createRow(4);
	 for(int i = 0; i < Titulos.length; i++) {
//		 System.out.println(i);
	        Cell celda1 = fila.createCell(i);
	        
	        celda1.setCellStyle(style); 
	        Titulos[i]=Titulos[i]!=null? Titulos[i].toUpperCase():Titulos[i];
	        celda1.setCellValue(Titulos[i]);
	    }
	
	 for(int i = 0; i <libro.size() ; i++) {
		 
		 
		 
	    	fila = pagina.createRow((i+5));
	    	Cell celda2 = fila.createCell(0);
	        celda2.setCellValue((i+1));
	        celda2 = fila.createCell(1);
	        celda2.setCellValue(libro.get(i).getCodTrabajador()); 
	        celda2 = fila.createCell(2);
	        celda2.setCellValue(libro.get(i).getApellidoPaterno());
	        celda2 = fila.createCell(3);
	        celda2.setCellValue(libro.get(i).getApellidoMaterno());
	        celda2 = fila.createCell(4);
	        celda2.setCellValue(libro.get(i).getNombre());
	        celda2 = fila.createCell(5);
	        celda2.setCellValue(libro.get(i).getRut());
	        celda2 = fila.createCell(6);
	        celda2.setCellValue(libro.get(i).getCargo());
	        celda2 = fila.createCell(7);
	        celda2.setCellValue(libro.get(i).getFechaInicio_actividad().toUpperCase());
	        celda2 = fila.createCell(8);
	        if(libro.get(i).getFechaTerminoContrato() == null || libro.get(i).getFechaTerminoContrato() == "null")
	        {
	         celda2.setCellValue("");
	        }else
	        {
	        	celda2.setCellValue(libro.get(i).getFechaTerminoContrato().toUpperCase());
	        }
	        
	        celda2 = fila.createCell(9);
	        
	        if(libro.get(i).getArticulo() == 0 )
	        {
	         celda2.setCellValue("");
	        }else
	        {
	        	celda2.setCellValue(libro.get(i).getArticulo());
	        }
	        
	        celda2 = fila.createCell(10);
	        if(libro.get(i).getCausal() == null || libro.get(i).getCausal() == "null")
	        {
	         celda2.setCellValue("");
	        }else
	        {
	        	celda2.setCellValue(libro.get(i).getCausal().toUpperCase());
	        }
	        
	        celda2 = fila.createCell(11);
	        
	        
	        String huertoexiste=libro.get(i).getHuerto()==null?"SIN HUERTO":libro.get(i).getHuerto();
	        celda2.setCellValue(huertoexiste.toUpperCase());
	        
	        celda2 = fila.createCell(12);
	        celda2.setCellValue(""+libro.get(i).getNombreCECO());
	        celda2 = fila.createCell(13);
	        
	        String Faena=libro.get(i).getFaena()==null?"SIN FAENA":libro.get(i).getFaena();
	        
	        celda2.setCellValue(Faena.toUpperCase());
	        celda2 = fila.createCell(14);
	        
	        //nombre cuenta 
	        String NombreCuentaU=libro.get(i).getTipoCuenta()==null?"SIN CUENTA":libro.get(i).getTipoCuenta();
	        celda2.setCellValue(NombreCuentaU.toUpperCase());
	        celda2 = fila.createCell(15);
	        if(libro.get(i).getFechaPago() == null || libro.get(i).getFechaPago() == "null")
	        {
	         celda2.setCellValue("");
	        }else
	        {
	        	String fecha=""+libro.get(i).getFechaPago();
		        String[] D=fecha.split("-");
		        String Formato=D[2]+"-"+D[1]+"-"+D[0];
		        
		        celda2.setCellValue(Formato);
	        }
	        
	        
	        celda2= fila.createCell(16);
	        celda2.setCellValue(""+libro.get(i).getNombreTipoTrabajador());
	        celda2 = fila.createCell(17);
	        // dias trabajados
	        celda2.setCellValue(""+libro.get(i).getDiasTrabajados());
	        celda2 = fila.createCell(18);
	        
	        CellStyle style1;
	        style1=workbook.createCellStyle();

	        DataFormat format=workbook.createDataFormat();
	        style1.setDataFormat(format.getFormat("#,##0"));
	        
	        CellStyle styleDecimal;
	        styleDecimal=workbook.createCellStyle();

	        DataFormat formatDecimal=workbook.createDataFormat();

	        styleDecimal.setDataFormat(formatDecimal.getFormat("0.00"));
	        
	        
	        int sueldoBase2TA=libro.get(i).getSueldoBase2().intValue();
	       
	        // sueldo base 
	        celda2.setCellValue(sueldoBase2TA);
	        celda2.setCellStyle(style1);
	        
	        // liquido AGRO
	        celda2 = fila.createCell(19);
	        int liquido_agro=libro.get(i).getLiquidoAgro().intValue();
	        celda2.setCellValue(liquido_agro);
	        celda2.setCellStyle(style1);

	        celda2 = fila.createCell(20);
	        
	        // total bonos imponibles
	        int bonosImponibles=libro.get(i).getBonosImponibles().intValue();
	        celda2.setCellValue(bonosImponibles);
	        celda2.setCellStyle(style1);

	        //haberes imponibles!!!!	        
	        add=21;
	        int add3 = 0;
	        int add2 = 0;
	        x=0;

	        for(int a=0;a<libro.get(i).getHaberesImponibles().size();a++){
	        	celda2=fila.createCell(add + add2);
	            
	        	
	        	int HIij=libro.get(i).getHaberesImponibles().get(a).getValor().intValue();
	        	celda2.setCellValue(HIij);
	        	
	        	celda2.setCellStyle(style1);
	        	add3++;
	        	add2++;
	        }
	        
	        add = add+(add3);
	        
	     // BONO PRODUCCION AGRO
	        celda2 = fila.createCell(add);
	        int BONO_agro=libro.get(i).getBonoProduccionAgro().intValue();
	        celda2.setCellValue(BONO_agro);
	        celda2.setCellStyle(style1);
	        
	        // HORA EXTRA
	        celda2 = fila.createCell(add+1);
	        celda2.setCellValue(libro.get(i).getHoraExtraJ().doubleValue());
	        //celda2.setCellValue(libro.get(i).getNumeroHorafalta());
	        celda2.setCellStyle(styleDecimal);
	        
	        
	        // MONTO HORA EXTRA
	        celda2 = fila.createCell(add+2);
	        celda2.setCellValue(libro.get(i).getMontoHoraExtra().doubleValue());
	        celda2.setCellStyle(style1);
	        
	        // HORA FALTA
	        celda2 = fila.createCell(add+3);
	        celda2.setCellValue(libro.get(i).getHoraFalta().doubleValue());
	        celda2.setCellStyle(styleDecimal);
	        
	        // MONTO HORA FALTA
	        celda2 = fila.createCell(add+4);
	        celda2.setCellValue(libro.get(i).getMontoHorafalta().doubleValue());
	        celda2.setCellStyle(style1);
	        
	        // GRATIFICACION
	        celda2 = fila.createCell(add+5);
	        int grat=libro.get(i).getGratificacion().intValue();
	        celda2.setCellValue(grat);
	        celda2.setCellStyle(style1);
	        
	        
	        int forhif = add+6;
	        
	        // HABERES IMPONIBLE FINIQUITO
	     	if(variablef == 0){
	     		
	     	}else
	     	{
	     	for(int b=0;b < variablef;b++)
	     	{
	     		
	     		celda2 = fila.createCell(forhif);
	     		if(libro.get(i).getHaberesImponiblesF().get(b).getValor() != null){
	     			
	     		}
	     		
	 	        int valor_f_hi=libro.get(i).getHaberesImponiblesF().get(b).getValor().intValue();
	 	        
	 	        celda2.setCellValue(valor_f_hi);
	 	        celda2.setCellStyle(style1);
	     		
	     
	 	       forhif ++;
	     		
	     	}
	     	
	     	}
	        
	        
	        
	        // TOTAL HABERES IMPONIBLES
	        celda2 = fila.createCell(forhif);
	        int thi=libro.get(i).getTotalHaberesImponibles().intValue();
	      
	        //TOTAL BONOS NO IMPONIBLES
	        celda2.setCellValue(thi);
	        celda2.setCellStyle(style1);
	        celda2 = fila.createCell(forhif+1);
	        int bni=libro.get(i).getBonosNoImponibles().intValue();
	        celda2.setCellValue(bni);
	        celda2.setCellStyle(style1);
	        x=forhif+2;
//	        if(i==0){
//	        System.out.println("add:"+x);
//	        }
	     //extraigo los datos del detalle de la liquidacion y los reemplazo por los valores de correspondiente haber y descuento segun corresponda   
//	        ArrayList<HaberYDescuento> DLHDNI=new ArrayList<HaberYDescuento>();
//	        DLHDNI=  HaberDescuentoDB.getHaberesYDescuentosByCTCCPClass(libro.get(i).getCodTrabajador(), libro.get(i).getIdContrato(), libro.get(i).getPeriodo(), 2);
//	        
	        
	        for(int b=0;b<libro.get(i).getHaberesNoImponibles2().size();b++){
	        	
	        	celda2=fila.createCell(x);
	        	int HNIij=libro.get(i).getHaberesNoImponibles2().get(b).getValor().intValue();
	        	celda2.setCellValue(HNIij);
//	        	if(!DLHDNI.isEmpty()){
//	        		int codigo=libro.get(i).getHaberesNoImponibles2().get(b).getCodigo();
//	        		for(int aa=0;aa<DLHDNI.size();aa++){
//	        			if(DLHDNI.get(aa).getCodigo()==codigo){
//	        				HNIij=DLHDNI.get(aa).getValor();
//	        				celda2.setCellValue(HNIij);
//	        			}
//	        		}
//	        	}
//	        	else{
//	        	
//	        		
//	        		celda2.setCellValue(HNIij);
//	        	}
	        	
	        	celda2.setCellStyle(style1);
	        	x++;
	        }
	        
	        
	       
	        int totalHastaHNIF = x;
	        int valorFeProFiniquito = 0;
	        int valorINDEMNIZACIONANOSERVICIO = 0;
	        int valorINDEMNIZACIONMESDENOAVISO = 0;
	     // HABERES NO IMPONIBLE FINIQUITO
	     	if(variableHaberNoImponibleF == 0){
	     		
	     	}else
	     	{
	     		
	     		if(libro.get(i).getCodTrabajador() == 2258) {
	     			System.out.println("");
	     		}
	     	for(int b=0;b < variableHaberNoImponibleF;b++)
	     	{
	     		
	     		celda2 = fila.createCell(totalHastaHNIF);
	     		if(libro.get(i).getHaberesNoImponiblesF().get(b).getValor() != null){
	     			
	     		}
	     		
	     		
	 	        int valor_f_hiN=libro.get(i).getHaberesNoImponiblesF().get(b).getValor().intValue();
	 	        
	 	        int valorFeriadoProNormal = libro.get(i).getValorFeriadoProporcional()==null?0:libro.get(i).getValorFeriadoProporcional().intValue();
	 	       
	 	        
	 	        
	 	        
	 	       if(libro.get(i).getHaberesNoImponiblesF().get(b).getDescripcion().equals("FERIADO PROPORCIONAL") )
	 	       {
	 	    	  valor_f_hiN =  valorFeriadoProNormal;
	 	    	   if(valorFeriadoProNormal <= 0 )
	 	    	   {
	 	    		   valorFeProFiniquito = libro.get(i).getHaberesNoImponiblesF().get(b).getValor().intValue();
	 	    		   valor_f_hiN = valorFeProFiniquito;
	 	    	   }
	 	    	     
	 	       }

	 	       
	 	       if(libro.get(i).getHaberesNoImponiblesF().get(b).getCodigo() == 2007 ){
	 	    	  valorINDEMNIZACIONANOSERVICIO = libro.get(i).getHaberesNoImponiblesF().get(b).getValor().intValue();
	 	        }
	 	      if(libro.get(i).getHaberesNoImponiblesF().get(b).getCodigo() == 2008 ){
	 	    	 valorINDEMNIZACIONMESDENOAVISO = libro.get(i).getHaberesNoImponiblesF().get(b).getValor().intValue();
	 	        }
	 	       
	 	       
	 	        
	 	        celda2.setCellValue(valor_f_hiN);
	 	        celda2.setCellStyle(style1);
	 	        totalHastaHNIF ++;
	     		
	     	}
	     	
	     	}
	     	
	     	
	     	
	        
	        
	        x = totalHastaHNIF;
	        
	        
	        // CARGA FAMILIAR SIMPLE
	        celda2 = fila.createCell(x);
	        celda2.setCellValue(libro.get(i).getCargaFamiliarSimple().intValue());
	        celda2.setCellStyle(style1);
	        // CARGA FAMILIAR MATERNAL
	        celda2 = fila.createCell(x+1);
	        celda2.setCellValue(libro.get(i).getCargaFamiliarMaternal().intValue());
	        celda2.setCellStyle(style1);
	        // CARGA FAMILIAR RETROACTIVA
	        celda2 = fila.createCell(x+2);
	        celda2.setCellValue(libro.get(i).getCargaFamiliaresRetro().intValue());
	        celda2.setCellStyle(style1);
	        
	        int totalItemCargas = libro.get(i).getCargaFamiliarSimple().intValue() + libro.get(i).getCargaFamiliarMaternal().intValue() + libro.get(i).getCargaFamiliaresRetro().intValue();
	        
	        // FERIADO PROPORCIONAL
//	        celda2 = fila.createCell(x+3);
	        
	        int valorFeriadoPro = libro.get(i).getValorFeriadoProporcional()==null?0:libro.get(i).getValorFeriadoProporcional().intValue();
	        int feriadop        = libro.get(i).getValorFeriadoProporcional()==null?0:libro.get(i).getValorFeriadoProporcional().intValue();
//	        celda2.setCellValue(feriadop);
//	        celda2.setCellStyle(style1);
	       
	        // TOTAL HABERES NO IMPONIBLES
	        celda2 = fila.createCell(x+3);
	        celda2.setCellValue(libro.get(i).getTotalHabNoImponible().intValue()+feriadop);
	        celda2.setCellStyle(style1);
	        
	        // TOTAL HABERES
	        celda2 = fila.createCell(x+4);
	        celda2.setCellValue(libro.get(i).getTotalHaberes().intValue()+valorFeProFiniquito + valorFeriadoPro - totalItemCargas);
	        celda2.setCellStyle(style1);
	        
	        // NOMBRE AFP
	        celda2 = fila.createCell(x+5);
	        celda2.setCellValue(libro.get(i).getNombreAFP().toUpperCase());
	        
	        // VALOR AFP
	        celda2 = fila.createCell(x+6);
	        celda2.setCellValue(libro.get(i).getAFP().intValue());
	        celda2.setCellStyle(style1);
	        celda2 = fila.createCell(x+7);
	        celda2.setCellValue(libro.get(i).getNombreSalud().toUpperCase());
	        int adisaluTri = libro.get(i).getAdicionalTribu().intValue();
	        
	        // valor salud
	        celda2 = fila.createCell(x+8);
	        celda2.setCellValue(libro.get(i).getSalud().intValue()-adisaluTri);
	        celda2.setCellStyle(style1);
	        celda2 = fila.createCell(x+9);
	        celda2.setCellValue(libro.get(i).getCaja().intValue());
	        celda2.setCellStyle(style1);
	        
	        // adicional salud
	        celda2 = fila.createCell(x+10);
	        int adisalunoTri = libro.get(i).getAdicionalSalud().intValue();
	        
	        celda2.setCellValue(adisalunoTri + adisaluTri);
	        celda2.setCellStyle(style1);
	        
	        // SEGURO CESANTIA AFC TRABAJADOR
	        celda2 = fila.createCell(x+11);
	        celda2.setCellValue(libro.get(i).getSeguroCesantiaAFCTrabajador().intValue());
	        celda2.setCellStyle(style1);
	        
	        celda2 = fila.createCell(x+12);
	        celda2.setCellValue(libro.get(i).getNombreAPV()!=null?libro.get(i).getNombreAPV().toUpperCase():"");
	        
	        celda2 = fila.createCell(x+13);
	        celda2.setCellValue(libro.get(i).getAPV().intValue());
	        celda2.setCellStyle(style1);
	        
	        celda2 = fila.createCell(x+14);
	        celda2.setCellValue(libro.get(i).getImpuestoUnico().intValue());
	        celda2.setCellStyle(style1);
	        
	        celda2 = fila.createCell(x+15);
	        celda2.setCellValue(libro.get(i).getBaseTributable().intValue());
	        celda2.setCellStyle(style1);
	        
	        // TOTAL DESCUENTOS 
	        celda2 = fila.createCell(x+16);
	        celda2.setCellValue(libro.get(i).getTotalDescuentosImp().intValue());
	        celda2.setCellStyle(style1);
	        
	        celda2 = fila.createCell(x+17);
	        celda2.setCellValue(libro.get(i).getAnticipo().intValue());
	        celda2.setCellStyle(style1);
	        
//	        celda2 = fila.createCell(xxx+2);
//	        celda2.setCellValue(libro.get(i).getDescuentos().intValue());
//	        celda2.setCellStyle(style1);
	        
	     // AHORRO VOLUNTARIO AFP
	        celda2 = fila.createCell(x+18);
	        celda2.setCellValue(libro.get(i).getAhoroVoluntarioAfp().intValue());
	        celda2.setCellStyle(style1);
	        
	       // DESCUENTOS FINIQUITO
	        int aporteAFCEmpleador = 0;
	        int totalHastaAnticipo = x+19;
	     	if(variableDescuentoF == 0){
	     		
	     	}else
	     	{
	     	for(int b=0;b < variableDescuentoF;b++)
	     	{
	     		
	     		celda2 = fila.createCell(totalHastaAnticipo);
	     		if(libro.get(i).getDescuentoF().get(b).getValor() != null){
	     			
	     		}
	     		
	 	        int valor_f_descuento=libro.get(i).getDescuentoF().get(b).getValor().intValue();
	 	       
	 	        if(libro.get(i).getDescuentoF().get(b).getCodigo() == 3002 ){
	 	        	aporteAFCEmpleador = libro.get(i).getDescuentoF().get(b).getValor().intValue();
		 	        }
	 	        
	 	        celda2.setCellValue(valor_f_descuento);
	 	        celda2.setCellStyle(style1);
	 	       totalHastaAnticipo ++;
	     		
	     	}
	     	
	     	}
	        
	     	
	        
	        
	        
	        
	        int totalHastaAHorro_v = totalHastaAnticipo;
		
		     	if(variableDescuentos == 0){
		     		
		     	}else
		     	{
		     	for(int b=0;b < variableDescuentos;b++)
		     	{
		     		
		     		celda2 = fila.createCell(totalHastaAHorro_v);
		     		if(libro.get(i).getDescuento2().get(b).getValor() != null){
		     			
		     		}
		     		
		 	        int valor_HD_descuento_LIQ=libro.get(i).getDescuento2().get(b).getValor().intValue();
		 	        
		 	        celda2.setCellValue(valor_HD_descuento_LIQ);
		 	        celda2.setCellStyle(style1);
		 	        totalHastaAHorro_v ++;
		     		
		     	}
		     	
		     	}
		     	
		    int xxxy =  totalHastaAHorro_v;
	        
	        celda2 = fila.createCell(xxxy);
	        celda2.setCellValue(libro.get(i).getTotalDescuentosNoImp().intValue());
	        celda2.setCellStyle(style1);
	        
	        celda2 = fila.createCell(xxxy+1);
	        celda2.setCellValue(libro.get(i).getTotalDescuentos().intValue());
	        celda2.setCellStyle(style1);
	        
	        celda2 = fila.createCell(xxxy+2);
	        celda2.setCellValue(libro.get(i).getTotalPago().intValue()+valorFeProFiniquito + valorFeriadoPro + valorINDEMNIZACIONANOSERVICIO + valorINDEMNIZACIONMESDENOAVISO - aporteAFCEmpleador);
	        celda2.setCellStyle(style1);
	        
	        celda2= fila.createCell(xxxy+3);
	        celda2.setCellValue(libro.get(i).getTotalLiquidoMes().intValue()+valorFeProFiniquito + valorFeriadoPro + valorINDEMNIZACIONANOSERVICIO + valorINDEMNIZACIONMESDENOAVISO - aporteAFCEmpleador);
	        celda2.setCellStyle(style1);
	        
	        // COTIZACION SIS
	        celda2 = fila.createCell(xxxy+4);
	        celda2.setCellValue(libro.get(i).getCotizacionSIS().intValue());
	        celda2.setCellStyle(style1);
	        
	        // SEGURO CESANTIA AFC
	        celda2 = fila.createCell(xxxy+5);
	        celda2.setCellValue(libro.get(i).getSeguroSesantiaAFC().intValue());
	        celda2.setCellStyle(style1);
	        
	        celda2 = fila.createCell(xxxy+6);
	        celda2.setCellValue(libro.get(i).getCotizacionBasica().intValue());
	        celda2.setCellStyle(style1);
	        
	        celda2 = fila.createCell(xxxy+7);
	        celda2.setCellValue(libro.get(i).getCotizacionAdicional().intValue());
	        celda2.setCellStyle(style1);
	        
	        celda2 = fila.createCell(xxxy+8);
	        celda2.setCellValue(libro.get(i).getCotizacionExtraordinaria().intValue());
	        celda2.setCellStyle(style1);
	        
	        // SANA
	        celda2 = fila.createCell(xxxy+9);
	        celda2.setCellValue(libro.get(i).getSANNA().intValue());
	        celda2.setCellStyle(style1);
	        
	       
	        celda2 = fila.createCell(xxxy+10);
	        int  sumaC=libro.get(i).getCotizacionBasica().intValue()+libro.get(i).getCotizacionAdicional().intValue()+
	        libro.get(i).getCotizacionExtraordinaria().intValue()+libro.get(i).getSANNA().intValue();
	        celda2.setCellValue(sumaC);
	        celda2.setCellStyle(style1);
	        
	        celda2 = fila.createCell(xxxy+11);
	        celda2.setCellValue(libro.get(i).getCostoVidaCamara().intValue());
	        celda2.setCellStyle(style1);
	        
	        
	        
	        int totalHastaCO_Fi = xxxy+12;
	   	 // COSTO EMPRESA FINIQUITO
	     	if(variableCostoEmpresaF == 0){
	     		
	     	}else
	     	{
	     	for(int b=0;b < variableCostoEmpresaF;b++)
	     	{
	     		
	     		celda2 = fila.createCell(totalHastaCO_Fi);
	     		if(libro.get(i).getCostoempresaF().get(b).getValor() != null){
	     			
	     		}
	     		
	   	        int valor_f_descuento=libro.get(i).getCostoempresaF().get(b).getValor().intValue();
	   	        
	   	        celda2.setCellValue(valor_f_descuento);
	   	        celda2.setCellStyle(style1);
	   	        totalHastaCO_Fi ++;
	     		
	     	}
	     	
	     	}
	     	
	     	
	     // columna en blanco
	     	celda2 = fila.createCell(totalHastaCO_Fi);
	        celda2.setCellValue("");
			// COSTO EMPRESA
	        int total_COSTO_E = ((((
	        		libro.get(i).getTotalHaberesImponibles().intValue() +
	        		libro.get(i).getTotalHabNoImponible().intValue()+feriadop 
	        		) 
	        		- libro.get(i).getCargaFamiliarSimple().intValue())
	        		- libro.get(i).getCargaFamiliarMaternal().intValue())
	        		- libro.get(i).getCargaFamiliaresRetro().intValue()) 
	        		+ (
	        		libro.get(i).getCotizacionSIS().intValue() + 
	        		libro.get(i).getSeguroSesantiaAFC().intValue() + sumaC +
	        		libro.get(i).getCostoVidaCamara().intValue()

	        		);
	        celda2 = fila.createCell(totalHastaCO_Fi+1);
	        celda2.setCellValue(total_COSTO_E);
	        celda2.setCellStyle(style1);
			// TOTAL AFP
	        int total_AFP = libro.get(i).getAFP().intValue() +
	        		libro.get(i).getAhoroVoluntarioAfp().intValue() +
	        		libro.get(i).getCotizacionSIS().intValue() +
	        		libro.get(i).getSeguroSesantiaAFC().intValue() +
	        		libro.get(i).getSeguroCesantiaAFCTrabajador().intValue();
	        
	        celda2 = fila.createCell(totalHastaCO_Fi+2);
	        celda2.setCellValue(total_AFP);
	        celda2.setCellStyle(style1);
	    }
	 
	
	 
	 //suma:::
	 //espacio en blanco 
	 CellStyle style1;
     style1=workbook.createCellStyle();

     DataFormat format=workbook.createDataFormat();
     style1.setDataFormat(format.getFormat("#,##0"));
     
     CellStyle styleDecimal;
     styleDecimal=workbook.createCellStyle();

     DataFormat formatDecimal=workbook.createDataFormat();

     styleDecimal.setDataFormat(formatDecimal.getFormat("0.00"));
     
     
	 int lineaBlanca=libro.size()+5;
	 fila= pagina.createRow((lineaBlanca));
	 fila=pagina.createRow((lineaBlanca+1));
	 
	 Cell celda2 = fila.createCell(0);
	 celda2 = fila.createCell(1);
	 celda2 = fila.createCell(2);
	 celda2.setCellValue("Total General");
	 int aaa=0;
	 int bbb=0;
	 int ccc=0;
	 int libroSize=libro.size()+5;
	 for(int i=3;i<17;i++){
		 celda2 = fila.createCell(i);
	 }
//	 celda2=fila.createCell(18);
//	 celda2.setCellType(CellType.FORMULA);
//	 String formu="SUM(O6:O"+libroSize+")";
//	 celda2.setCellFormula(formu);
//	 celda2.setCellStyle(style1);
	 
	 celda2=fila.createCell(18);
	 celda2.setCellType(CellType.FORMULA);
	 String formu="SUM(O6:S"+libroSize+")";
	 celda2.setCellFormula(formu);
	 celda2.setCellStyle(style1);
	 //----------------------------------------------------------------
	 celda2=fila.createCell(19);
	 celda2.setCellType(CellType.FORMULA);
	 String re=CellReference.convertNumToColString(19);
	 System.out.println(re);
	 
	 formu="SUM("+re+"6:"+re+libroSize+")";
	 celda2.setCellFormula(formu);
	 celda2.setCellStyle(style1);
	 
	 
	 int totalFor = 0;
	 System.out.println("///////////////// 20 ////////////////////////////");
	 System.out.println("/////////////////haberes imponibles ////////////////////////////");
	 for(int j=20;j<libro.get(0).getHaberesImponibles().size()+20;j++){
		 celda2=fila.createCell(j);
		 celda2.setCellType(CellType.FORMULA);
		 re=CellReference.convertNumToColString(j);
		 System.out.println(re);
		 formu="SUM("+re+"6:"+re+""+libroSize+")";
		 celda2.setCellFormula(formu);
		 celda2.setCellStyle(style1);
		 totalFor ++;
	 }
	 
	 int total_columna = totalFor + 20;
	 System.out.println("/////////////////"+total_columna+ " ////////////////////////////");
	 
	 celda2=fila.createCell(total_columna);
	 celda2.setCellType(CellType.FORMULA);
	 re=CellReference.convertNumToColString(total_columna);
	 //System.out.println("imprimimo decimal "+re);
	 formu="SUM("+re+"6:"+re+""+libroSize+")";
	 celda2.setCellFormula(formu);
	 celda2.setCellStyle(styleDecimal);
	 
	 celda2=fila.createCell(total_columna +1);
	 celda2.setCellType(CellType.FORMULA);
	 re=CellReference.convertNumToColString(total_columna +1 );
	 System.out.println(re);
	 formu="SUM("+re+"6:"+re+""+libroSize+")";
	 celda2.setCellFormula(formu);
	 celda2.setCellStyle(style1);
	 
	 celda2=fila.createCell(total_columna +2);
	 celda2.setCellType(CellType.FORMULA);
	 re=CellReference.convertNumToColString(total_columna +2 );
	 //System.out.println("imprimimo decimal "+re);
	 formu="SUM("+re+"6:"+re+""+libroSize+")";
	 celda2.setCellFormula(formu);
	 celda2.setCellStyle(styleDecimal);
	 
	 
	  
	 celda2=fila.createCell(total_columna +3);
	 celda2.setCellType(CellType.FORMULA);
	 re=CellReference.convertNumToColString(total_columna + 3);
	 System.out.println("aqui 1 AC"+re);
	 formu="SUM("+re+"6:"+re+""+libroSize+")";
	 celda2.setCellFormula(formu);
	 celda2.setCellStyle(style1);
	 
	// gratificacion 29
	 int sumahastaHaberes=libro.get(0).getHaberesImponibles().size()+24;
	 System.out.println(libro.get(0).getHaberesImponibles().size());
	 System.out.println("suma h"+ sumahastaHaberes);
	 celda2=fila.createCell(sumahastaHaberes);
	 celda2.setCellType(CellType.FORMULA);
	 re=CellReference.convertNumToColString(sumahastaHaberes);
	 System.out.println("aqui AD "+re);
	 formu="SUM("+re+"6:"+re+""+libroSize+")";
	 celda2.setCellFormula(formu);
	 celda2.setCellStyle(style1);
	 
	 //30
	 //-----------------------------------------------------------
	 celda2=fila.createCell(sumahastaHaberes+1);
	 celda2.setCellType(CellType.FORMULA);
	 re=CellReference.convertNumToColString(sumahastaHaberes+1);
	 System.out.println(re);
	 formu="SUM("+re+"6:"+re+""+libroSize+")";
	 celda2.setCellFormula(formu);
	 celda2.setCellStyle(style1);
	 
	 // 31
	 //---------------------------------------------------------
	 celda2=fila.createCell(sumahastaHaberes+2);
	 celda2.setCellType(CellType.FORMULA);
	 re=CellReference.convertNumToColString(sumahastaHaberes+2);
	 System.out.println("aqui antes del for"+ re);
	 formu="SUM("+re+"6:"+re+""+libroSize+")";
	 celda2.setCellFormula(formu);
	 celda2.setCellStyle(style1);
	 
	 //---------------------------------------------------------
	 
	 // 32
	 int sumHastaNoIm=sumahastaHaberes+3;
	 for(int j=sumHastaNoIm;j<libro.get(0).getHaberesNoImponibles2().size()+sumHastaNoIm;j++){
		 celda2=fila.createCell(j);
		 celda2.setCellType(CellType.FORMULA);
		 re=CellReference.convertNumToColString(j);
		 System.out.println("letra inicio no imponible " +re);
		 formu="SUM("+re+"6:"+re+""+libroSize+")";
		 celda2.setCellFormula(formu);
		 celda2.setCellStyle(style1);
	 }
	 //------------------------------------------------------------
	 // CARGA FAMILIAR SIMPLE
	 //System.out.println("//////////no imponibles ///////////////////////////");
	 int sss=libro.get(0).getHaberesNoImponibles2().size()+sumHastaNoIm;
	 //System.out.println("aqui llevo "+sss);
	 celda2=fila.createCell(sss);
	 celda2.setCellType(CellType.FORMULA);
	 re=CellReference.convertNumToColString(sss);
	 System.out.println("letra inicio no imponible " +re);
	 formu="SUM("+re+"6:"+re+""+libroSize+")";
	 celda2.setCellFormula(formu);
	 celda2.setCellStyle(style1);
	 //-------------------------------------------------------------
	 
	 // CARGA FAMILIAR MATERNAL
	 celda2=fila.createCell(sss+1);
	 celda2.setCellType(CellType.FORMULA);
	 re=CellReference.convertNumToColString(sss+1);
	 System.out.println(re);
	 formu="SUM("+re+"6:"+re+""+libroSize+")";
	 celda2.setCellFormula(formu);
	 celda2.setCellStyle(style1);
	 //-------------------------------------------------------------
	 
	 // CARGA FAMILIAR RETROACTIVO
	 celda2=fila.createCell(sss+2);
	 celda2.setCellType(CellType.FORMULA);
	 re=CellReference.convertNumToColString(sss+2);
	 System.out.println(re);
	 formu="SUM("+re+"6:"+re+""+libroSize+")";
	 celda2.setCellFormula(formu);
	 celda2.setCellStyle(style1);
	 //-------------------------------------------------------------
	 // TOTAL HABERES NO IMPONIBLES
	 celda2=fila.createCell(sss+3);
	 celda2.setCellType(CellType.FORMULA);
	 re=CellReference.convertNumToColString(sss+3);
	 System.out.println(re);
	 formu="SUM("+re+"6:"+re+""+libroSize+")";
	 celda2.setCellFormula(formu);
	 celda2.setCellStyle(style1);
	 //-------------------------------------------------------------
	 
	 // TOTAL HABERES
	 celda2=fila.createCell(sss+4);
	 celda2.setCellType(CellType.FORMULA);
	 re=CellReference.convertNumToColString(sss+4);
	 System.out.println(re);
	 formu="SUM("+re+"6:"+re+""+libroSize+")";
	 celda2.setCellFormula(formu);
	 celda2.setCellStyle(style1);
	 //-------------------------------------------------------------
	 
	 // NOMBRE AFP
	 celda2=fila.createCell(sss+5);
	 
	 // VALOR AFP
	 celda2=fila.createCell(sss+6);
	 celda2.setCellType(CellType.FORMULA);
	 re=CellReference.convertNumToColString(sss+6);
	 System.out.println(re);
	 formu="SUM("+re+"6:"+re+""+libroSize+")";
	 celda2.setCellFormula(formu);
	 celda2.setCellStyle(style1);
	 //-------------------------------------------------------------
	 
	// INSTITUCION SALUD
	 celda2=fila.createCell(sss+7);
	 
	 // VALOR SALUD
	 celda2=fila.createCell(sss+8);
	 celda2.setCellType(CellType.FORMULA);
	 re=CellReference.convertNumToColString(sss+8);
	 System.out.println(re);
	 formu="SUM("+re+"6:"+re+""+libroSize+")";
	 celda2.setCellFormula(formu);
	 celda2.setCellStyle(style1);
	//-------------------------------------------------------------
		 celda2=fila.createCell(sss+9);
	//-------------------------------------------------------------
	// VALOR CAJA
	int da=sss+9;
		 
		 // ADICIONAL SALUD  hasta TOTAL ACHS
		 for(int i=da;i<da+156;i++){
			 
//			 if(i == 44){
//				 celda2=fila.createCell(i);
			
			 celda2=fila.createCell(i);
			 celda2.setCellType(CellType.FORMULA);
			 re=CellReference.convertNumToColString(i);
			 System.out.println(re);
			 formu="SUM("+re+"6:"+re+""+libroSize+")";
			 celda2.setCellFormula(formu);
			 celda2.setCellStyle(style1);
			 
			 
		 }
	 
	 
	 try {
	        FileOutputStream salida = new FileOutputStream(archivo);
	        workbook.write(salida);
	        workbook.close();
	        salida.close();
	        System.out.println("Archivo creado existosamente");
	        return archivo.getName();
	    } catch (FileNotFoundException ex) {
	       System.out.println("Archivo no localizable en sistema de archivos");
	       return "0";
	    } catch (IOException ex) {
	        System.out.println("Error de entrada/salida");
	        return "0";
	    }
	 
	}	
}
public Workbook generarCentralizacionReporte(ArrayList<CentraRowReporte> cr,String Empresa, int periodo) {
	// TODO Auto-generated method stub
	Workbook workbook = new XSSFWorkbook(); 
    Sheet pagina = workbook.createSheet("Reporte");
    CellStyle style0=workbook.createCellStyle();
    
    BorderStyle b=BorderStyle.THIN;
    style0.setBorderBottom(b);
    style0.setBorderTop(b);
    style0.setBorderRight(b);
    style0.setBorderLeft(b);
    
    
    Row fila = pagina.createRow(0);
	Cell celda = fila.createCell(1);
	celda.setCellValue(Empresa);
	 fila = pagina.createRow(1);
	 celda = fila.createCell(1);
	 
	 String p0=""+periodo;
	 String p1="";
	 p1=p0.substring(4);
	 String p2=p0.substring(0, 4);
	 if(p1.equals("01")){
		 p1="ENERO DE ";
	 }
	 else if(p1.equals("02")){
		 p1="FEBRERO DE ";
	 }
	 else if(p1.equals("03")){
		 p1="MARZO DE ";
	 }else if(p1.equals("04")){
		 p1="ABRIL DE ";
	 }else if(p1.equals("05")){
		 p1="MAYO DE ";
	 }else if(p1.equals("06")){
		 p1="JUNIO DE ";
	 }else if(p1.equals("07")){
		 p1="JULIO DE ";
	 }else if(p1.equals("08")){
		 p1="AGOSTO DE ";
	 }else if(p1.equals("09")){
		 p1="SEPTIEMBRE DE ";
	 }else if(p1.equals("10")){
		 p1="OCTUBRE DE ";
	 }else if(p1.equals("11")){
		 p1="NOVIEMBRE DE ";
	 }else if(p1.equals("12")){
		 p1="DICIEMBRE DE ";
	 }
	 String pp=p1+p2;
	celda.setCellValue(pp);
    for(int i = 0; i <cr.size() ; i++) {
    fila = pagina.createRow((i+2));
    Cell Celda2=fila.createCell(0);
    	 
	     Celda2.setCellValue(cr.get(i).getConcepto());
	     Celda2.setCellStyle(style0);
	     Celda2=fila.createCell(1);
	     Celda2.setCellValue(cr.get(i).getDescripcion());
	     Celda2.setCellStyle(style0);
	     Celda2=fila.createCell(2);
	     Celda2.setCellValue(cr.get(i).getIdCECO());
	     Celda2.setCellStyle(style0);
	     Celda2=fila.createCell(3);
	     Celda2.setCellValue(cr.get(i).getOrdenco());
	     Celda2.setCellStyle(style0);
	     Celda2=fila.createCell(4);
	     Celda2.setCellValue(cr.get(i).getCuenta());
	     Celda2.setCellStyle(style0);
	     Celda2=fila.createCell(5);
	     Celda2.setCellValue(cr.get(i).getProveedor());
	     Celda2=fila.createCell(6);
	     Celda2.setCellStyle(style0);
	     Celda2.setCellValue(cr.get(i).getCodTrabajador());
	     Celda2.setCellStyle(style0);
	     Celda2=fila.createCell(7);
	     Celda2.setCellValue(cr.get(i).getMonto()+"");
	     Celda2.setCellStyle(style0);
    }
    fila = pagina.createRow(cr.size()+2);
    
    Cell Celda2=fila.createCell(0);
    Celda2.setCellValue("");
    Celda2.setCellStyle(style0);
    Celda2=fila.createCell(1);
    Celda2.setCellValue("");
    Celda2.setCellStyle(style0);
    Celda2=fila.createCell(2);
    Celda2.setCellValue("");
    Celda2.setCellStyle(style0);
    Celda2=fila.createCell(3);
    Celda2.setCellValue("");
    Celda2.setCellStyle(style0);
    Celda2=fila.createCell(4);
    Celda2.setCellStyle(style0);
    Celda2=fila.createCell(5);
    Celda2.setCellStyle(style0);
    Celda2=fila.createCell(6);
    Celda2.setCellValue("TOTAL");
    Celda2.setCellStyle(style0);
    Celda2=fila.createCell(7);
    Celda2.setCellType(CellType.FORMULA);
    String re=CellReference.convertNumToColString(7);
	 int libroSize=cr.size()+2;
	 String formu="SUM("+re+"3:"+re+""+libroSize+")";
	 Celda2.setCellFormula(formu);
	 Celda2.setCellStyle(style0);
    
    pagina.addMergedRegion(new CellRangeAddress(0,0,1,2));
    pagina.autoSizeColumn(0);
    pagina.autoSizeColumn(1);
    pagina.autoSizeColumn(2);
    pagina.autoSizeColumn(3);
    pagina.autoSizeColumn(4);
    pagina.autoSizeColumn(5);
    pagina.autoSizeColumn(6);
    pagina.autoSizeColumn(7);
	return workbook;
}

}

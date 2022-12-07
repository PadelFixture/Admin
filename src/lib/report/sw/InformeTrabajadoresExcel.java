package lib.report.sw;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import lib.ClassSASW.CentroCosto;
import lib.db.ConnectionDB;
import lib.db.SASW.CentroCostoDB;
import lib.db.sw.sociedadDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;
import wordCreator.utils;

public class InformeTrabajadoresExcel {

	/** File name output */
	private FileOutputStream m_FileOutput = null;
	private String m_FileXLS = null;
	private String m_FileName = null;
	/** Start - End */
	

	/** Struct Unit */


	/** Report Fields Managed */
	private int m_Records = 0;
	private int m_RowLine = 0;


	/** Excel fields */
	private HSSFWorkbook m_WorkBook;
	private HSSFSheet m_Sheet;
	private HSSFRow m_row;
	private HSSFCell m_Cell;
	private HSSFCellStyle m_CellStyleTitle;
	private HSSFCellStyle m_CellStyleNumeric;
	private HSSFCellStyle m_CellStyleString;
	private HSSFCellStyle m_CellStyleStringRight;
	private HSSFCellStyle m_CellStyleStringNotAdjust;
	private HSSFCellStyle m_CellStyleLevel0;
	private HSSFCellStyle m_CellStyleLevel1;
	private HSSFCellStyle m_CellStyleLevel2;
	private HSSFCellStyle m_CellStyleDate;
	private HSSFFont m_FontTitle;
	private HSSFFont m_Font;
	private HSSFFont m_FontLevel;
	private HSSFDataFormat m_DataFormat;
	private HSSFCellStyle m_CellStyleTitleColumn;
	private HSSFFont m_FontTitleColumn;
	
	private StringBuffer m_parametros;
	private String m_empresa;
	private String m_empresaName;
	
	private int identationD = 0;

	public String prepare(ArrayList<filterSql> filter) throws Exception {

		m_parametros = new StringBuffer("");

		// Obtener los Parametros
		for (filterSql filterSql : filter) {
			if (filterSql.getValue() != "") {
				m_parametros.append(filterSql.getCampo() + " : " + filterSql.getValue() + " , ");
			}

			if ("idSociedad".equals(filterSql.getCampo())) {
				m_empresa = filterSql.getValue();
			}

		}

		m_empresaName = sociedadDB.getSociedadById(m_empresa).getDenominacionSociedad();

		m_FileName = "DinamicaFichaColaborador_" + GeneralUtility.getCurrentDate() + ".xls";
		
		// Crear archivos
		m_FileXLS = utils.getServerFolder("ReporteExcel") + m_FileName;

		File pruebaFile = new File(m_FileXLS);
		if (pruebaFile.exists()) {
			pruebaFile.deleteOnExit();
		}

		m_FileOutput = new FileOutputStream(m_FileXLS);

		// Parametros a P
		doIt(filter);

		return m_FileName;

	} // prepare

	protected String doIt(ArrayList<filterSql> filter) throws Exception {
		StringBuffer sql = null;

		createWorkBook();
		createSheet("Informe Trabajadores");
		sql = new StringBuffer("" + " SELECT " + " upper(soc.denominacionSociedad) as nombreEmpresa, "
				+ " upper(concat(tr.apellidoPaterno, ' ', tr.apellidoMaterno)) as Trabajador,  "
				+ " upper(tr.nombre) as Nombre,  " + " CASE WHEN tr.rut = '' THEN tr.rutTemporal ELSE tr.rut END as RutTrabajador,   "
				+ " upper(nac.descripcion) as idNacionalidad,         "
				+ " substr(upper(sexo.descripcion),1,1) as Sexo, "
				+ " DATE_FORMAT(tr.fNacimiento,'%d-%m-%Y') as FechaNacimiento, "
				+ " upper(estadoCivil.descripcion) as EstadoCivil, " 
				+ " upper(ifnull(nombreCarrera.descripcion,'')) Profesion, "
				+ " tr.codigo as CodigoTrabajador, " + " upper(tipoCntt.descripcion) as TipoContrato, "
				+ " upper(nombreCargo.cargos) as Cargo, " 
				//
				+ " upper(ifnull(rg.region,'')) as Region, "
				+ " upper(ifnull(pr.nombre,'')) as Provincia, "
				//
				+ " upper(ifnull(com.nombre,'')) as Comuna, "
				+ " upper(ifnull(tr.direccion, concat(tr.calle,' ',tr.ndireccion,' ',tr.depto,' ',tr.poblacion) )) as Direccion, "
				+ " upper(ifnull(c1.descripcion,'')) as Huerto, "
				+ " upper(ifnull(c1.zona,'')) as Zona,  "
				+ " ifnull(tr.idCECO,'') as CentroCosto, "
				+ " ifnull(faena.nombreFaena,''), "
				+ " ifnull(etnia.descripcion,'') as Etnia, "
				+ " CASE WHEN tr.telefono = '' THEN tr.celular ELSE tr.telefono END as Telefono,  " + " afp.descripcion as Afp, "
				+ " tr.pensionados, " + " upper(tipoT.descripcion) as TipoTrabajador, "
				+ " upper(salud.descripcion) as Isapre, " + " tr.sCesantia, "
				+ " CASE WHEN cntt.EstadoContrato = 1 THEN 'SI' ELSE 'NO' END as Vigencia, " + " cntt.horasSemanales, "
				+ " upper(ifnull(banco.descripcion,'')) as Banco, " 
				+ " upper(cuenta.nCuenta) as NumeroCuenta, "
				+ " ifnull(DATE_FORMAT(cntt.fechaInicio_actividad,'%d-%m-%Y'),'') as FechaInicioContrato, "
				+ " ifnull(DATE_FORMAT(cntt.FechaTerminoContrato,'%d-%m-%Y'),'') as FechaTerminoContrato, "
				+ " cntt.sueldoBase as sueldoBase, "
				//Where Condition
				+ " cntt.idSociedad as idSociedad,  "
				+ " cntt.idCECOContrato as idCECO,  "
				+ " cntt.tipoContrato as tipoContrato, "
				+ " cntt.idHuertoContrato, "
				+ " tr.idZona, "
				+ " tr.agro,"
				+ " cntt.supervisor"
				// -----------------------
				// -----------------------
				+ " from trabajadores tr inner join contratos cntt on tr.codigo = cntt.codigo_trabajador  "
				+ " left join cuentaBancaria cuenta on tr.codigo = cuenta.codigoTrabajador and cuenta.cuentaPrimaria = 1  "
				+ " left join sociedad soc on cntt.idSociedad = soc.idSociedad  "
				+ " left join parametros sexo on tr.idGenero = sexo.llave and sexo.codigo = 'SEXO' "
				+ " left join parametros nac on tr.idNacionalidad = nac.llave and nac.codigo = 'NACIONALIDAD'  "
				+ " left join parametros estadoCivil on tr.idEstadoCivil = estadoCivil.llave and estadoCivil.codigo = 'ESTADO_CIVIL' "
				+ " left join parametros salud on tr.idIsapre = salud.llave and salud.codigo = 'ISAPRE' "
				+ " left join parametros afp on tr.idAFP = afp.llave and afp.codigo = 'AFP' "
				+ " left join parametros tipoCntt on cntt.tipoContrato = tipoCntt.llave and tipoCntt.codigo = 'TIPO_CONTRATO'  "
				+ " left join parametros tipoCuenta on cuenta.idTipoCuenta = tipoCuenta.llave and tipoCuenta.codigo = 'TIPO_DE_CUENTA'  "
				+ " left join parametros banco on cuenta.idBanco = banco.llave and banco.codigo = 'BANCO' "
				+ " left join parametros etnia on tr.idEtnia = etnia.llave and etnia.codigo = 'ETNIA' "
				+ " left join comuna com on tr.idcomuna = com.id "
				+ " left join provincia pr on tr.idProvincia = pr.id "
				+ " left join region rg on tr.idRegion = rg.idregion "
				+ " left join cargos nombreCargo on (cntt.cargo = nombreCargo.id_cargo) "
				+ " left join sw_m_faena faena on (tr.idFaena = faena.idFaena)  "
				+ " left join sw_academicos educacion on tr.id = educacion.idTrabajador  "
				+ " left join parametros nombreCarrera on educacion.carrera = nombreCarrera.llave and nombreCarrera.codigo = 'CARRERA_OFICIO' "
				+ " left join parametros tipoT on cntt.tipoTrabajador = tipoT.llave and tipoT.codigo = 'TIPO_TRABAJADOR' "
				+ " left join campo c1 on cntt.idHuertoContrato = c1.campo "
				+ " ");

		// Si contiene datos asignarlo al WHERE
		if (filter.size() > 0) {
			String andSql = "";
			andSql += " WHERE ";
			Iterator<filterSql> f = filter.iterator();

			while (f.hasNext()) {
				filterSql row = f.next();

				if (!row.getValue().equals("")) {

					if (row.getCampo().endsWith("_to")) {

						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
						sql.append(andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='"
								+ sqlDate.format(formatter.parse(row.getValue())) + "'");
					}

					else if (row.getCampo().endsWith("_from")) {

						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
						sql.append(andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='"
								+ sqlDate.format(formatter.parse(row.getValue())) + "'");
					}
					else if (row.getValue().split(",").length >= 2) {
						
						if("idSociedad".equals(row.getCampo())){
							sql.append(andSql + "cntt."+ row.getCampo() + " in ( " + row.getValue() + ") ");
						}else{
							sql.append(andSql + row.getCampo() + " in ( " + row.getValue() + ") ");
						}
					}
					else {
						if("idSociedad".equals(row.getCampo())){
							sql.append(andSql + "cntt."+ row.getCampo() + " = '" + row.getValue().replace("\\\"", "") + "'");
						}else{
							sql.append(andSql + row.getCampo() + " = '" + row.getValue().replace("\"", "") + "'");
						}
					}

					andSql = " and ";
				}
			} // Fin While

		}

		try {

			ConnectionDB db = new ConnectionDB();
			System.out.println(sql.toString());
			PreparedStatement pstmt = db.conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			m_RowLine = 5;

			while (rs.next()) {
				printDetail(rs);
			}

			rs.close();
			pstmt.close();

			m_WorkBook.write(m_FileOutput);
			m_FileOutput.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}

		return m_FileName;

	} // doIt

	/*
	 * Creación de Libro
	 */
	@SuppressWarnings("deprecation")
	protected void createWorkBook() {

		m_WorkBook = new HSSFWorkbook();
		// ------------------ Initials Values ----------------------------//
		m_FontTitle = m_WorkBook.createFont();
		m_FontTitle.setFontName("Arial");
		m_FontTitle.setColor(HSSFColor.BLUE.index);
		m_FontTitle.setFontHeightInPoints((short) 9);
		m_CellStyleTitle = m_WorkBook.createCellStyle();
		m_CellStyleTitle.setFont(m_FontTitle);
		m_CellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		m_FontTitleColumn = m_WorkBook.createFont();
		m_FontTitleColumn.setFontName("Arial");
		m_FontTitleColumn.setFontHeightInPoints((short) 9);
		m_FontTitleColumn.setColor(HSSFColor.BLUE.index);

		m_CellStyleTitleColumn = m_WorkBook.createCellStyle();
		m_CellStyleTitleColumn.setFont(m_FontTitleColumn);
		m_CellStyleTitleColumn.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		m_CellStyleTitleColumn.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
		m_CellStyleTitleColumn.setBottomBorderColor(HSSFColor.BLUE.index);

		m_FontLevel = m_WorkBook.createFont();
		m_FontLevel.setFontName("Arial");
		m_FontLevel.setFontHeightInPoints((short) 9);
		m_CellStyleLevel0 = m_WorkBook.createCellStyle();
		m_CellStyleLevel0.setFont(m_FontLevel);
		m_CellStyleLevel0.setAlignment(HSSFCellStyle.ALIGN_LEFT);

		m_CellStyleLevel1 = m_WorkBook.createCellStyle();
		m_CellStyleLevel1.setFont(m_FontLevel);
		m_CellStyleLevel1.setAlignment(HSSFCellStyle.ALIGN_LEFT);

		m_CellStyleLevel2 = m_WorkBook.createCellStyle();
		m_CellStyleLevel2.setFont(m_FontLevel);
		m_CellStyleLevel2.setAlignment(HSSFCellStyle.ALIGN_LEFT);

		m_Font = m_WorkBook.createFont();
		m_Font.setFontName("Arial");
		m_Font.setFontHeightInPoints((short) 9);
		m_CellStyleNumeric = m_WorkBook.createCellStyle();
		m_CellStyleNumeric.setFont(m_Font);
		m_CellStyleString = m_WorkBook.createCellStyle();
		m_CellStyleString.setFont(m_Font);
		m_CellStyleString.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		m_CellStyleString.setWrapText(true);
		m_CellStyleStringRight = m_WorkBook.createCellStyle();
		m_CellStyleStringRight.setFont(m_Font);
		m_CellStyleStringRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		m_CellStyleStringRight.setWrapText(true);
		m_CellStyleStringNotAdjust = m_WorkBook.createCellStyle();
		m_CellStyleStringNotAdjust.setFont(m_Font);
		m_CellStyleStringNotAdjust.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		m_CellStyleStringNotAdjust.setWrapText(false);

		m_DataFormat = m_WorkBook.createDataFormat();
		m_CellStyleDate = m_WorkBook.createCellStyle();
		m_CellStyleDate.setFont(m_Font);
		m_CellStyleDate.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		m_CellStyleDate.setDataFormat(m_DataFormat.getFormat("dd/mm/yyyy"));
	}

	protected void createSheet(String sheetName) {
		// Create Sheet
		int lastColumn = 9;

		int baseColumn = 0; // Dos columnas de Subtitulos (0 y 1)

		m_Sheet = m_WorkBook.createSheet(sheetName);

		m_row = m_Sheet.createRow(0);
		m_Cell = m_row.createCell(0);
		m_Cell.setCellValue(new HSSFRichTextString("EMPRESA: ") + m_empresaName == null ? "TODAS" : m_empresaName );
		m_Cell.setCellStyle(m_CellStyleStringNotAdjust);

		m_Sheet.setColumnWidth(lastColumn, (short) (10 * 256));
		m_Cell = m_row.createCell(lastColumn);
		m_Cell.setCellValue(new HSSFRichTextString("FECHA: ") + GeneralUtility.getCurrentDateDDMMYYYY());
		m_Cell.setCellStyle(m_CellStyleDate);

		m_row = m_Sheet.createRow(1);
		m_Cell = m_row.createCell(0);
		m_Cell.setCellValue("");
		m_Cell.setCellStyle(m_CellStyleStringNotAdjust);

		m_row = m_Sheet.createRow(2);
		m_Cell = m_row.createCell(0);

		m_Sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, lastColumn));
		m_Cell.setCellValue("DINAMICA FICHA DE COLABORADOR");
		m_Cell.setCellStyle(m_CellStyleTitle);

		// Parameters para titulo
		StringBuffer m_Parameters = new StringBuffer("FILTROS ").append(": ").append(m_parametros.toString());

		m_row = m_Sheet.createRow(3);
		m_Cell = m_row.createCell(0);
		m_Sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, lastColumn));
		m_Cell.setCellValue(m_Parameters.toString());
		m_Cell.setCellStyle(m_CellStyleTitle);

		// Columns Name for Report
		m_row = m_Sheet.createRow(4);

		m_Sheet.setColumnWidth(baseColumn, 30 * 256);
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("NOMBRE EMPRESA"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, 30 * 256);
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("APELLIDOS"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, 30 * 256);
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("NOMBRES"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, 15 * 256);
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("RUT TRABAJADOR"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, 15 * 256);
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("NACIONALIDAD"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, 15 * 256);
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("GENERO"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (15 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("F_NACIMIENTO"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (15 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("ESTADO CIVIL"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (15 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("PROFESION"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, 15 * 256);
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("CODIGO"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (15 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("TIPO CONTRATO"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (30 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("CARGO"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (30 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("REGION"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;
		
		m_Sheet.setColumnWidth(baseColumn, (30 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("PROVINCIA"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;
		
		m_Sheet.setColumnWidth(baseColumn, (30 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("COMUNA"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (30 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("DIRECCION"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (17 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("HUERTO"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;
		
		m_Sheet.setColumnWidth(baseColumn, (17 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("ZONA"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;
		
		m_Sheet.setColumnWidth(baseColumn, (17 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("CENTRO COSTO"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;
		
		m_Sheet.setColumnWidth(baseColumn, (17 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("FAENA"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;
		
		m_Sheet.setColumnWidth(baseColumn, (17 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("ETNIA"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (15 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("TELEFONO"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (15 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("AFP"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (15 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("PENSIONADOS"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (15 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("TIPO TRABAJADOR"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (17 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("ISAPRE"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (15 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("SEGURO CESANTIA"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (15 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("VIGENCIA"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (15 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("H_SEMANALES"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (17 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("BANCO"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (17 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("NUMERO DE CUENTA"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (30 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("F_INICIO CONTRATO"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (30 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("F_TERMINO CONTRATO"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.setColumnWidth(baseColumn, (17 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("SUELDO BASE"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;
		
		m_Sheet.setColumnWidth(baseColumn, (17 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("AGRO"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;
		
		m_Sheet.setColumnWidth(baseColumn, (17 * 256));
		m_Cell = m_row.createCell(baseColumn);
		m_Cell.setCellValue(new HSSFRichTextString("SUPERVISOR"));
		m_Cell.setCellStyle(m_CellStyleTitleColumn);
		baseColumn++;

		m_Sheet.createFreezePane(0, 5, 3, 5);

	}

	/**
	 * Imprime el Detalle del Reporte
	 * 
	 * @param rs
	 */
	@SuppressWarnings("deprecation")
	protected void printDetail(ResultSet rs) {
		try {

			m_row = m_Sheet.createRow(m_RowLine);
			identationD = 0;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(1)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(2)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(rs.getString(3));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(rs.getString(4));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(5)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_NUMERIC);
			m_Cell.setCellStyle(m_CellStyleNumeric);
			m_Cell.setCellValue(rs.getString(6));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleStringRight);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(7)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(8)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(9)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(10)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(11)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(12)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(13)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(14)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(15)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(16)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(17)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(18)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			
			//Agregar CECOS *PD: Gracias Mauricio :C te extrañare
			ArrayList<CentroCosto> cecos = new ArrayList<CentroCosto>();
			cecos=CentroCostoDB.getCentrosCostosByCECO(rs.getString(19), "p");
			m_Cell.setCellValue(new HSSFRichTextString(cecos.get(0).getNAME()));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(20)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(21)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(22)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(23)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(24)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(25)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(26)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(27)));
			identationD++;

			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(28)));
			identationD++;
			
			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(29)));
			identationD++;
			
			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(30)));
			identationD++;
			
			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(31)));
			identationD++;
			
			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(32)));
			identationD++;
			
			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(33)));
			identationD++;
			
			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(34)));
			identationD++;
			
			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(40)));
			identationD++;
			
			m_Cell = m_row.createCell(identationD, HSSFCell.CELL_TYPE_STRING);
			m_Cell.setCellStyle(m_CellStyleString);
			m_Cell.setCellValue(new HSSFRichTextString(rs.getString(41)));
			identationD++;

			m_RowLine++;
			m_Records++;
		} catch (Exception e) {
			System.out.println("Sucedio Error imprimiento detalle" + e.getMessage());
		}
	}

	/**
	 * Imprime el Nivel Final/Total del Reporte
	 */
	@SuppressWarnings("deprecation")
	protected void printLevel() {

		m_RowLine++;
		m_row = m_Sheet.createRow(m_RowLine);
		m_Cell = m_row.createCell(0, HSSFCell.CELL_TYPE_STRING);
		m_Cell.setCellStyle(m_CellStyleLevel0);
		m_Cell.setCellValue(new HSSFRichTextString("TOTAL: "));

		m_RowLine++;
		m_row = m_Sheet.createRow(m_RowLine);
		m_Cell = m_row.createCell(0);
		m_Cell.setCellValue(new HSSFRichTextString("TOTAL: "));
		m_Cell.setCellStyle(m_CellStyleString);
		m_Cell = m_row.createCell(2);
		m_Cell.setCellValue(m_Records);
	}

	// ---------------------------------Otro Metodo-----------------//

	//
	// public void prepare() {
	//
	// // Creamos el archivo donde almacenaremos la hoja
	// //de calculo, recuerde usar la extension correcta, en este caso .xlsx
	// File archivo = new File("reporte.xlsx");
	//
	// // Creamos el libro de trabajo de Excel formato OOXML
	// Workbook book = new XSSFWorkbook();
	//
	// //Creamos Hoja
	// Sheet sheet = book.createSheet("DATA");
	//
	// CellStyle tituloEstilo = book.createCellStyle();
	// tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
	// tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);
	// Font fuenteTitulo = book.createFont();
	// fuenteTitulo.setBold(true);
	// fuenteTitulo.setFontHeightInPoints((short) 14);
	// tituloEstilo.setFont(fuenteTitulo);
	//
	// Row filaTitulo = sheet.createRow(0);
	// Cell celdaTitulo = filaTitulo.createCell(0);
	// celdaTitulo.setCellStyle(tituloEstilo);
	// celdaTitulo.setCellValue("INFORMES TRABAJADORES");
	// sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 15));
	//
	// CellStyle headerStyle = book.createCellStyle();
	// headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
	// headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	// headerStyle.setBorderBottom(BorderStyle.THIN);
	// headerStyle.setBorderLeft(BorderStyle.THIN);
	// headerStyle.setBorderRight(BorderStyle.THIN);
	// headerStyle.setBorderTop(BorderStyle.THIN);
	//
	// Font font = book.createFont();
	// font.setFontName("Arial");
	// font.setBold(true);
	// font.setColor(IndexedColors.WHITE.getIndex());
	// headerStyle.setFont(font);
	//
	// Row filaEncabezado = sheet.createRow(3);
	//
	// Cell celdaEncabezado0 = filaEncabezado.createCell(0);
	// celdaEncabezado0.setCellStyle(headerStyle);
	// celdaEncabezado0.setCellValue("NombreEmpresa");
	//
	// Cell celdaEncabezado1 = filaEncabezado.createCell(1);
	// celdaEncabezado1.setCellStyle(headerStyle);
	// celdaEncabezado1.setCellValue("Trabajador");
	//
	// Cell celdaEncabezado2 = filaEncabezado.createCell(2);
	// celdaEncabezado2.setCellStyle(headerStyle);
	// celdaEncabezado2.setCellValue("Nombre");
	//
	// Cell celdaEncabezado3 = filaEncabezado.createCell(3);
	// celdaEncabezado3.setCellStyle(headerStyle);
	// celdaEncabezado3.setCellValue("RutTrabajador");
	//
	// Cell celdaEncabezado4 = filaEncabezado.createCell(4);
	// celdaEncabezado4.setCellStyle(headerStyle);
	// celdaEncabezado4.setCellValue("IdNacionalidad");
	//
	// Cell celdaEncabezado5 = filaEncabezado.createCell(5);
	// celdaEncabezado5.setCellStyle(headerStyle);
	// celdaEncabezado5.setCellValue("Sexo");
	//
	// Cell celdaEncabezado6 = filaEncabezado.createCell(6);
	// celdaEncabezado6.setCellStyle(headerStyle);
	// celdaEncabezado6.setCellValue("FechaNacimiento");
	//
	// Cell celdaEncabezado7 = filaEncabezado.createCell(7);
	// celdaEncabezado7.setCellStyle(headerStyle);
	// celdaEncabezado7.setCellValue("EstadoCivil");
	//
	// Cell celdaEncabezado8 = filaEncabezado.createCell(8);
	// celdaEncabezado8.setCellStyle(headerStyle);
	// celdaEncabezado8.setCellValue("Profesion");
	//
	// Cell celdaEncabezado9 = filaEncabezado.createCell(9);
	// celdaEncabezado9.setCellStyle(headerStyle);
	// celdaEncabezado9.setCellValue("CodigoTrabajador");
	//
	// Cell celdaEncabezado10 = filaEncabezado.createCell(10);
	// celdaEncabezado10.setCellStyle(headerStyle);
	// celdaEncabezado10.setCellValue("TipoContrato");
	//
	// Cell celdaEncabezado11 = filaEncabezado.createCell(11);
	// celdaEncabezado11.setCellStyle(headerStyle);
	// celdaEncabezado11.setCellValue("Cargo");
	//
	// Cell celdaEncabezado12 = filaEncabezado.createCell(12);
	// celdaEncabezado12.setCellStyle(headerStyle);
	// celdaEncabezado12.setCellValue("Comuna");
	//
	// Cell celdaEncabezado13 = filaEncabezado.createCell(13);
	// celdaEncabezado13.setCellStyle(headerStyle);
	// celdaEncabezado13.setCellValue("Direccion");
	//
	// Cell celdaEncabezado14 = filaEncabezado.createCell(14);
	// celdaEncabezado14.setCellStyle(headerStyle);
	// celdaEncabezado14.setCellValue("CentroCosto");
	//
	// Cell celdaEncabezado15 = filaEncabezado.createCell(15);
	// celdaEncabezado15.setCellStyle(headerStyle);
	// celdaEncabezado15.setCellValue("Telefono");
	//
	// Cell celdaEncabezado16 = filaEncabezado.createCell(16);
	// celdaEncabezado16.setCellStyle(headerStyle);
	// celdaEncabezado16.setCellValue("Afp");
	//
	//
	// Cell celdaEncabezado17 = filaEncabezado.createCell(17);
	// celdaEncabezado17.setCellStyle(headerStyle);
	// celdaEncabezado17.setCellValue("Pensionados");
	//
	// Cell celdaEncabezado18 = filaEncabezado.createCell(18);
	// celdaEncabezado18.setCellStyle(headerStyle);
	// celdaEncabezado18.setCellValue("TipoTrabajador");
	//
	// Cell celdaEncabezado19 = filaEncabezado.createCell(19);
	// celdaEncabezado19.setCellStyle(headerStyle);
	// celdaEncabezado19.setCellValue("Isapre");
	//
	// Cell celdaEncabezado20 = filaEncabezado.createCell(20);
	// celdaEncabezado20.setCellStyle(headerStyle);
	// celdaEncabezado20.setCellValue("SCesantia");
	//
	// Cell celdaEncabezado21 = filaEncabezado.createCell(21);
	// celdaEncabezado21.setCellStyle(headerStyle);
	// celdaEncabezado21.setCellValue("Vigencia");
	//
	// Cell celdaEncabezado22 = filaEncabezado.createCell(22);
	// celdaEncabezado22.setCellStyle(headerStyle);
	// celdaEncabezado22.setCellValue("HorasSemanales");
	//
	// Cell celdaEncabezado23 = filaEncabezado.createCell(23);
	// celdaEncabezado23.setCellStyle(headerStyle);
	// celdaEncabezado23.setCellValue("Banco");
	//
	// Cell celdaEncabezado24 = filaEncabezado.createCell(21);
	// celdaEncabezado24.setCellStyle(headerStyle);
	// celdaEncabezado24.setCellValue("NumeroCuenta");
	//
	// Cell celdaEncabezado25 = filaEncabezado.createCell(21);
	// celdaEncabezado25.setCellStyle(headerStyle);
	// celdaEncabezado25.setCellValue("FechaInicioContrato");
	//
	// Cell celdaEncabezado26 = filaEncabezado.createCell(21);
	// celdaEncabezado26.setCellStyle(headerStyle);
	// celdaEncabezado26.setCellValue("FechaTerminoContrato");
	//
	// Cell celdaEncabezado27 = filaEncabezado.createCell(21);
	// celdaEncabezado27.setCellStyle(headerStyle);
	// celdaEncabezado27.setCellValue("SueldoBase");
	//
	// //INGRESAMOS LA DATA DEL EXCEL
	// CellStyle datoStyle = book.createCellStyle();
	// datoStyle.setBorderBottom(BorderStyle.THIN);
	// datoStyle.setBorderLeft(BorderStyle.THIN);
	// datoStyle.setBorderRight(BorderStyle.THIN);
	// datoStyle.setBorderTop(BorderStyle.THIN);
	//
	// CellStyle siStyle = book.createCellStyle();
	// siStyle.setBorderBottom(BorderStyle.THIN);
	// siStyle.setBorderLeft(BorderStyle.THIN);
	// siStyle.setBorderRight(BorderStyle.THIN);
	// siStyle.setBorderTop(BorderStyle.THIN);
	//
	// Font font2 = book.createFont();
	// font2.setFontName("Arial");
	//
	// font2.setColor(IndexedColors.BLUE.getIndex());
	// siStyle.setFont(font2);
	//
	// CellStyle noStyle = book.createCellStyle();
	// noStyle.setBorderBottom(BorderStyle.THIN);
	// noStyle.setBorderLeft(BorderStyle.THIN);
	// noStyle.setBorderRight(BorderStyle.THIN);
	// noStyle.setBorderTop(BorderStyle.THIN);
	//
	// Font font3 = book.createFont();
	// font3.setFontName("Arial");
	//
	// font3.setColor(IndexedColors.RED.getIndex());
	// noStyle.setFont(font3);
	// ArrayList<filterSql> filter = new ArrayList<filterSql>();
	// //ArrayList<restriccion> datas;
	// try {
	//
	// ArrayList<InformeTrabajadores> datas =
	// InformeTrabajadoresDB.getInformeTrabajadores();
	//
	// int a = 4;
	// int x = 0;
	//
	//
	// for (InformeTrabajadores informeTrabajadores : datas) {
	//
	// String[] r = {
	// informeTrabajadores.getNombreEmpresa(),
	// informeTrabajadores.getTrabajador(),
	// informeTrabajadores.getNombre(),
	// informeTrabajadores.getRutTrabajador(),
	// informeTrabajadores.getIdNacionalidad(),
	// informeTrabajadores.getSexo(),
	// informeTrabajadores.getFechaNacimiento(),
	// informeTrabajadores.getEstadoCivil(),
	// informeTrabajadores.getProfesion(),
	// informeTrabajadores.getCodigoTrabajador(),
	// informeTrabajadores.getTipoContrato(),
	// informeTrabajadores.getCargo(),
	// informeTrabajadores.getComuna(),
	// informeTrabajadores.getDireccion(),
	// informeTrabajadores.getCentroCosto(),
	// informeTrabajadores.getTelefono(),
	// informeTrabajadores.getAfp(),
	// informeTrabajadores.getPensionados(),
	// informeTrabajadores.getTipoTrabajador(),
	// informeTrabajadores.getIsapre(),
	// informeTrabajadores.getsCesantia(),
	// informeTrabajadores.getVigencia(),
	// informeTrabajadores.getHorasSemanales(),
	// informeTrabajadores.getBanco(),
	// informeTrabajadores.getNumeroCuenta(),
	// informeTrabajadores.getFechaInicioContrato(),
	// informeTrabajadores.getFechaTerminoContrato(),
	// informeTrabajadores.getSueldoBase()};
	//
	// //restriccion row = f.next();
	// //String[] r = { row.getCodProductor(), row.getFecha(),
	// row.getCodProducto(), row.getLimite(),
	// //row.getMercado(), row.getEspecie(),row.getnMuestra(),
	// row.getAutomatica() };
	//
	//
	// sheet.autoSizeColumn((short) x);
	// Row filas = sheet.createRow(a);
	//
	// int i = 0;
	//
	// Cell col = filas.createCell(i);
	//
	// col.setCellStyle(datoStyle);
	//
	// a++;
	// x++;
	//
	// }
	//
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// //FIN DE EXCEL
	//
	// //FileOutputStream fileout = new
	// FileOutputStream("/eDte/hf/Reporte.xlsx");
	// //book.write(fileout);
	// //fileout.close();
	//
	// File file = new File("/eDte/hf/Reporte.xlsx");
	// //FileInputStream fis = new FileInputStream(file);
	// //FileCopyUtils.copy(fis, response.getOutputStream());
	// //fis.close();
	//
	//
	//
	//
	// }
	//
	//
	// private void generacionMail(){
	//
	//
	// // Creamos el archivo donde almacenaremos la hoja
	// // de calculo, recuerde usar la extension correcta,
	// // en este caso .xlsx
	// File archivo = new File("reporte.xlsx");
	//
	// // Creamos el libro de trabajo de Excel formato OOXML
	// Workbook workbook = new XSSFWorkbook();
	//
	// // La hoja donde pondremos los datos
	// Sheet pagina = workbook.createSheet("Reporte de productos");
	//
	// // Creamos el estilo paga las celdas del encabezado
	// CellStyle style = workbook.createCellStyle();
	// // Indicamos que tendra un fondo azul aqua
	// // con patron solido del color indicado
	// style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
	// style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	//
	// String[] titulos = {"Identificador", "Consumos",
	// "Precio Venta", "Precio Compra" };
	// Double[] datos = {1.0, 10.0, 45.5, 25.50};
	//
	// // Creamos una fila en la hoja en la posicion 0
	// Row fila = pagina.createRow(0);
	//
	// // Creamos el encabezado
	// for(int i = 0; i < titulos.length; i++) {
	// // Creamos una celda en esa fila, en la posicion
	// // indicada por el contador del ciclo
	// Cell celda = fila.createCell(i);
	//
	// // Indicamos el estilo que deseamos
	// // usar en la celda, en este caso el unico
	// // que hemos creado
	// celda.setCellStyle(style);
	// celda.setCellValue(titulos[i]);
	// }
	//
	// // Ahora creamos una fila en la posicion 1
	// fila = pagina.createRow(1);
	//
	// // Y colocamos los datos en esa fila
	// for(int i = 0; i < datos.length; i++) {
	// // Creamos una celda en esa fila, en la
	// // posicion indicada por el contador del ciclo
	// Cell celda = fila.createCell(i);
	//
	// celda.setCellValue( datos[i] );
	// }
	//
	// // Ahora guardaremos el archivo
	// try {
	// // Creamos el flujo de salida de datos,
	// // apuntando al archivo donde queremos
	// // almacenar el libro de Excel
	// FileOutputStream salida = new FileOutputStream(archivo);
	//
	// // Almacenamos el libro de
	// // Excel via ese
	// // flujo de datos
	// workbook.write(salida);
	//
	// // Cerramos el libro para concluir operaciones
	// workbook.close();
	//
	// LOGGER.log(Level.INFO, "Archivo creado existosamente en {0}",
	// archivo.getAbsolutePath());
	//
	// } catch (FileNotFoundException ex) {
	// LOGGER.log(Level.SEVERE, "Archivo no localizable en sistema de
	// archivos");
	// } catch (IOException ex) {
	// LOGGER.log(Level.SEVERE, "Error de entrada/salida");
	// }
	// }
	// }
	//
	// }
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

}

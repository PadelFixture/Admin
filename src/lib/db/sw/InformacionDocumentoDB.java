package lib.db.sw;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lib.classSW.GetDatosCartaTerminoTrabajador;
import lib.classSW.GetDatosContratoTrabajador;
import lib.db.ConnectionDB;
import lib.utils.GeneralUtility;

public class InformacionDocumentoDB {

	public static GetDatosContratoTrabajador obtenerDatosTrabajador(String idTrabajador) throws Exception{
		Statement ps = null;		
		String sql="";
		ConnectionDB db = new ConnectionDB();
		GetDatosContratoTrabajador tr = new GetDatosContratoTrabajador();
		
		try{
			sql = "select tr.codigo as codigoTrabajador, soc.denominacionSociedad as nombreEmpresa, "
				+ "tr.apellidoPaterno as appPatTrabajador,tr.apellidoMaterno as appMaternoTrabajador, "
				+ " CASE WHEN tr.rut = '' THEN tr.rutTemporal ELSE tr.rut END as rutCompletoTrabajador ,tr.nombre as nombreTrabajador, estadoCivil.descripcion as estadoCivil, "
				+ "DATE_FORMAT(tr.fNacimiento,'%d-%m-%Y') as fechaNacimientoTrabajador, nac.descripcion as nacionalidadTrabajador, "
				+ "ifnull(tr.direccion, concat(tr.calle, ' ', tr.ndireccion, ' ', tr.depto, ' ', tr.poblacion) ) as direccionTrabajador, "
				+ "com.nombre as comuna, "
				+ "nombreCargo.cargos as cargoTrabajador, 'PODA' as faenaContratacion, "
				+ "ifnull(DATE_FORMAT(cntt.fechaInicio_actividad,'%Y'), '2018') as temporadaContratacion, cntt.sueldoBase as sueldoCpuntosTrabajador,  "
				+ " com.nombre as ciudadContrato, soc.rut as rutEmpresa, 'Trescientos Mil Pesos' as sueldoEnPalabrasTrabajador, "
				+ " ifnull(DATE_FORMAT(cntt.fechaInicio_actividad,'%d-%m-%Y'), '01-01-2018') as fechaInicio "
				+" ,DATE_FORMAT(cntt.FechaTerminoContrato,'%d-%m-%Y') as fechaTerminoContratoFaena "
				+" ,cntt.articuloTerminoContrato as articulo "
				+" ,cntt.incisoTerminoContrato as incisoArticulo "
				+" ,cntt.lugarPago as lugarPagoFiniquito "
				+" ,DATE_FORMAT(cntt.fechaPago,'%d-%m-%Y') as fechaPagoFiniquito "
				+" ,DATE_FORMAT(cntt.horaPago,'%h@%i@%s') as horaInicioPagoFiniquito "
				+" ,DATE_FORMAT(cntt.horaPago,'%h@%i@%s') as horaFinPagoFiniquito "
				+" ,salud.descripcion as salud "
				+" ,afp.descripcion as afp "
				+" ,faena.nombreFaena as faenaTrabajador "
				+" ,tipoCntt.descripcion as tipoContrato "
				+" ,tipoCuenta.descripcion as tipoPago "
				+ "from trabajadores tr "
				+ "inner join contratos cntt on tr.codigo = cntt.codigo_trabajador and cntt.id in  "
				+ "		(select max(id) from contratos where codigo_trabajador = tr.codigo) "
				+ "left join cuentaBancaria cuenta on tr.codigo = cuenta.codigoTrabajador and cuenta.cuentaPrimaria = 1 "
				+ "left join sociedad soc on cntt.idSociedad = soc.idSociedad "
				+ "left join parametros nac on tr.idNacionalidad = nac.llave and nac.codigo = 'NACIONALIDAD' "
				+ "left join parametros estadoCivil on tr.idEstadoCivil = estadoCivil.llave and estadoCivil.codigo = 'ESTADO_CIVIL' "
				+ "left join parametros salud on tr.idIsapre = salud.llave and salud.codigo = 'ISAPRE' "
				+ "left join parametros afp on tr.idAFP = afp.llave and afp.codigo = 'AFP' "
				+ "left join parametros tipoCntt on cntt.tipoContrato = tipoCntt.llave and tipoCntt.codigo = 'TIPO_CONTRATO' "
				+ "left join parametros tipoCuenta on cuenta.idTipoCuenta = tipoCuenta.llave and tipoCuenta.codigo = 'TIPO_DE_CUENTA' "
				+ "left join comuna com on tr.idcomuna = com.id "
				+ "left join cargos nombreCargo on (cntt.cargo = nombreCargo.id_cargo) "
				+ "left join sw_m_faena faena on (tr.idFaena = faena.idFaena) "
				+ "where tr.id = "+ idTrabajador;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){				
				tr.setCodigoTrabajador(rs.getString("codigoTrabajador").toUpperCase());
				tr.setCiudadContrato(rs.getString("ciudadContrato").toUpperCase());
				tr.setNombreEmpresa(rs.getString("nombreEmpresa").toUpperCase());
				tr.setAppPatTrabajador(rs.getString("appPatTrabajador").toUpperCase());
				tr.setAppMaternoTrabajador(rs.getString("appMaternoTrabajador").toUpperCase());
				tr.setNombreTrabajador(rs.getString("nombreTrabajador").toUpperCase());
				tr.setRutCompletoTrabajador(rs.getString("rutCompletoTrabajador").toUpperCase());
				tr.setEstadoCivil(rs.getString("estadoCivil").toUpperCase());
				tr.setFechaNacimientoTrabajador(GeneralUtility.convertStringDDMMYYToDateInWord(rs.getString("fechaNacimientoTrabajador")));
				tr.setNacionalidadTrabajador(rs.getString("nacionalidadTrabajador").toUpperCase());
				tr.setDireccionTrabajador(rs.getString("direccionTrabajador").toUpperCase().replaceAll(",", "&"));
				tr.setComunaTrabajador(rs.getString("comuna").toUpperCase());
				tr.setCargoTrabajador(rs.getString("cargoTrabajador").toUpperCase());
				tr.setFaenaContratacion(rs.getString("faenaContratacion").toUpperCase());
				tr.setTemporadaContratacion(rs.getString("temporadaContratacion").toUpperCase());
				tr.setSueldoCPuntosTrabajador(GeneralUtility.formatStringNumberWithDotAndDecimal(rs.getString("SueldoCpuntosTrabajador")));
				tr.setSueldoEnPalabrasTrabajador(GeneralUtility.capitalizeFirstLetter(GeneralUtility.cantidadConLetra(rs.getString("SueldoCpuntosTrabajador")).toLowerCase() + "pesos"));
				tr.setFechaInicio(GeneralUtility.convertStringDDMMYYToDateInWord(rs.getString("fechaInicio")));
				tr.setCiudadContrato(rs.getString("ciudadContrato").toUpperCase());
				tr.setRutEmpresa(rs.getString("rutEmpresa").toUpperCase());
				tr.setRutCompletoEmpresa(rs.getString("rutEmpresa").toUpperCase());
				tr.setFechaTerminoContratoFaena(GeneralUtility.convertStringDDMMYYToDateInWord(rs.getString("fechaTerminoContratoFaena")));
				tr.setArticuloContrato(rs.getString("articulo"));
				tr.setIncisoContrato(rs.getString("incisoArticulo"));
				tr.setLugarPagoFiniquito(rs.getString("lugarPagoFiniquito"));
				tr.setFechaPagoFiniquito(rs.getString("fechaPagoFiniquito"));
				tr.setHoraInicioPagoFiniquito(rs.getString("horaInicioPagoFiniquito"));
				tr.setHoraFinPagoFiniquito(rs.getString("horaFinPagoFiniquito"));
				tr.setSaludTrabajador(rs.getString("salud").toUpperCase());
				tr.setAFPTrabajador(rs.getString("afp").toUpperCase());
				tr.setFaenaTrabajador(rs.getString("faenaTrabajador") == null ? "FAENAS GENERALES" : rs.getString("faenaTrabajador").toUpperCase() );
				tr.setTipoContrato(rs.getString("tipoContrato"));
				tr.setTipoPago(rs.getString("tipoPago"));
				tr.setDiasTrabajadosTrabajador("30");
				
				return tr;
			}
			
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return tr;
	}
	
	
	public static GetDatosContratoTrabajador obtenerDatosTrabajadorForContrato(String idTrabajador, String idContrato) throws Exception{
		Statement ps = null;		
		String sql="";
		ConnectionDB db = new ConnectionDB();
		GetDatosContratoTrabajador tr = new GetDatosContratoTrabajador();
		
		try{
			sql = " select tr.codigo as codigoTrabajador, soc.denominacionSociedad as nombreEmpresa, "
				+ " UPPER(concat(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ', tr.nombre)) as nombreCompletoTrabajador ,  "	
				+ " tr.apellidoPaterno as appPatTrabajador,tr.apellidoMaterno as appMaternoTrabajador, "
				+ " CASE WHEN tr.rut = '' THEN tr.rutTemporal ELSE tr.rut END as rutCompletoTrabajador ,tr.nombre as nombreTrabajador, estadoCivil.descripcion as estadoCivil, "
				+ " DATE_FORMAT(tr.fNacimiento,'%d-%m-%Y') as fechaNacimientoTrabajador, nac.descripcion as nacionalidadTrabajador, "
				+ " ifnull(tr.direccion, concat(tr.calle, ' ', tr.ndireccion, ' ', tr.depto, ' ', tr.poblacion) ) as direccionTrabajador, "
				+ " com.nombre as comuna, "
				+ " nombreCargo.cargos as cargoTrabajador, 'PODA' as faenaContratacion, "
				+ " ifnull(DATE_FORMAT(cntt.fechaInicio_actividad,'%Y'), '2018') as temporadaContratacion, cntt.sueldoBase as sueldoCpuntosTrabajador,  "
				+ " com.nombre as ciudadContrato, soc.rut as rutEmpresa, 'Trescientos Mil Pesos' as sueldoEnPalabrasTrabajador, "
				+ " ifnull(DATE_FORMAT(cntt.fechaInicio_actividad,'%d-%m-%Y'), '01-01-2018') as fechaInicio "
				+" ,DATE_FORMAT(cntt.FechaTerminoContrato,'%d-%m-%Y') as fechaTerminoContratoFaena "
				+" ,cntt.articuloTerminoContrato as articulo "
				+" ,cntt.incisoTerminoContrato as incisoArticulo "
				+" ,cntt.lugarPago as lugarPagoFiniquito "
				+" ,DATE_FORMAT(cntt.fechaPago,'%d-%m-%Y') as fechaPagoFiniquito "
				+" ,DATE_FORMAT(cntt.horaPago,'%h@%i@%s') as horaInicioPagoFiniquito "
				+" ,DATE_FORMAT(cntt.horaPago,'%h@%i@%s') as horaFinPagoFiniquito "
				+" ,salud.descripcion as salud "
				+" ,afp.descripcion as afp "
				+" ,faena.nombreFaena as faenaTrabajador "
				+" ,tipoCntt.descripcion as tipoContrato "
				+" ,tipoCuenta.descripcion as tipoPago "
				+ " from trabajadores tr "
				+ " inner join contratos cntt on tr.codigo = cntt.codigo_trabajador and cntt.id in  "
				+ "		(select max(id) from contratos where codigo_trabajador = tr.codigo) "
				+ " left join cuentaBancaria cuenta on tr.codigo = cuenta.codigoTrabajador and cuenta.cuentaPrimaria = 1 "
				+ " left join sociedad soc on cntt.idSociedad = soc.idSociedad "
				+ " left join parametros nac on tr.idNacionalidad = nac.llave and nac.codigo = 'NACIONALIDAD' "
				+ " left join parametros estadoCivil on tr.idEstadoCivil = estadoCivil.llave and estadoCivil.codigo = 'ESTADO_CIVIL' "
				+ " left join parametros salud on tr.idIsapre = salud.llave and salud.codigo = 'ISAPRE' "
				+ " left join parametros afp on tr.idAFP = afp.llave and afp.codigo = 'AFP' "
				+ " left join parametros tipoCntt on cntt.tipoContrato = tipoCntt.llave and tipoCntt.codigo = 'TIPO_CONTRATO' "
				+ " left join parametros tipoCuenta on cuenta.idTipoCuenta = tipoCuenta.llave and tipoCuenta.codigo = 'TIPO_DE_CUENTA' "
				+ " left join comuna com on tr.idcomuna = com.id "
				+ " left join cargos nombreCargo on (cntt.cargo = nombreCargo.id_cargo) "
				+ " left join sw_m_faena faena on (tr.idFaena = faena.idFaena) "
				+ " where tr.id = "+ idTrabajador + " and ct.id = " + idContrato;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){				
				tr.setCodigoTrabajador(rs.getString("codigoTrabajador").toUpperCase());
				tr.setCiudadContrato(rs.getString("ciudadContrato").toUpperCase());
				tr.setNombreEmpresa(rs.getString("nombreEmpresa").toUpperCase());
				tr.setAppPatTrabajador(rs.getString("appPatTrabajador").toUpperCase());
				tr.setAppMaternoTrabajador(rs.getString("appMaternoTrabajador").toUpperCase());
				tr.setNombreTrabajador(rs.getString("nombreTrabajador").toUpperCase());
				tr.setRutCompletoTrabajador(rs.getString("rutCompletoTrabajador").toUpperCase());
				tr.setEstadoCivil(rs.getString("estadoCivil").toUpperCase());
				tr.setFechaNacimientoTrabajador(GeneralUtility.convertStringDDMMYYToDateInWord(rs.getString("fechaNacimientoTrabajador")));
				tr.setNacionalidadTrabajador(rs.getString("nacionalidadTrabajador").toUpperCase());
				tr.setDireccionTrabajador(rs.getString("direccionTrabajador").toUpperCase().replaceAll(",", "&"));
				tr.setComunaTrabajador(rs.getString("comuna").toUpperCase());
				tr.setCargoTrabajador(rs.getString("cargoTrabajador").toUpperCase());
				tr.setFaenaContratacion(rs.getString("faenaContratacion").toUpperCase());
				tr.setTemporadaContratacion(rs.getString("temporadaContratacion").toUpperCase());
				tr.setSueldoCPuntosTrabajador(GeneralUtility.formatStringNumberWithDotAndDecimal(rs.getString("SueldoCpuntosTrabajador")));
				tr.setSueldoEnPalabrasTrabajador(GeneralUtility.capitalizeFirstLetter(GeneralUtility.cantidadConLetra(rs.getString("SueldoCpuntosTrabajador")).toLowerCase() + "pesos"));
				tr.setFechaInicio(GeneralUtility.convertStringDDMMYYToDateInWord(rs.getString("fechaInicio")));
				tr.setCiudadContrato(rs.getString("ciudadContrato").toUpperCase());
				tr.setRutEmpresa(rs.getString("rutEmpresa").toUpperCase());
				tr.setRutCompletoEmpresa(rs.getString("rutEmpresa").toUpperCase());
				tr.setFechaTerminoContratoFaena(GeneralUtility.convertStringDDMMYYToDateInWord(rs.getString("fechaTerminoContratoFaena")));
				tr.setArticuloContrato(rs.getString("articulo"));
				tr.setIncisoContrato(rs.getString("incisoArticulo"));
				tr.setLugarPagoFiniquito(rs.getString("lugarPagoFiniquito"));
				tr.setFechaPagoFiniquito(rs.getString("fechaPagoFiniquito"));
				tr.setHoraInicioPagoFiniquito(rs.getString("horaInicioPagoFiniquito"));
				tr.setHoraFinPagoFiniquito(rs.getString("horaFinPagoFiniquito"));
				tr.setSaludTrabajador(rs.getString("salud").toUpperCase());
				tr.setAFPTrabajador(rs.getString("afp").toUpperCase());
				tr.setFaenaTrabajador(rs.getString("faenaTrabajador") == null ? "FAENAS GENERALES" : rs.getString("faenaTrabajador").toUpperCase() );
				tr.setTipoContrato(rs.getString("tipoContrato"));
				tr.setTipoPago(rs.getString("tipoPago"));
				tr.setDiasTrabajadosTrabajador("30");
				tr.setNombreCompletoTrabajador(rs.getString("nombreCompletoTrabajador"));				
				return tr;
			}
			
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return tr;
	}
	
	
	public static GetDatosContratoTrabajador obtenerDatosTrabajadorByCodAndContrato(String codTrabajador, String idContrato) throws Exception{
		Statement ps = null;		
		String sql="";
		ConnectionDB db = new ConnectionDB();
		GetDatosContratoTrabajador tr = new GetDatosContratoTrabajador();
		
		try{
			sql = "select tr.codigo as codigoTrabajador, soc.denominacionSociedad as nombreEmpresa, "
				+ "tr.apellidoPaterno as appPatTrabajador,tr.apellidoMaterno as appMaternoTrabajador, "
				+ " CASE WHEN tr.rut = '' THEN tr.rutTemporal ELSE tr.rut END as rutCompletoTrabajador ,tr.nombre as nombreTrabajador, estadoCivil.descripcion as estadoCivil, "
				+ "DATE_FORMAT(tr.fNacimiento,'%d-%m-%Y') as fechaNacimientoTrabajador, nac.descripcion as nacionalidadTrabajador, "
				+ "ifnull(tr.direccion, concat(tr.calle, ' ', tr.ndireccion, ' ', tr.depto, ' ', tr.poblacion) ) as direccionTrabajador, "
				+ "com.nombre as comuna, "
				+ "nombreCargo.cargos as cargoTrabajador, 'PODA' as faenaContratacion, "
				+ "ifnull(DATE_FORMAT(cntt.fechaInicio_actividad,'%Y'), '2018') as temporadaContratacion, cntt.sueldoBase as sueldoCpuntosTrabajador,  "
				+ " com.nombre as ciudadContrato, soc.rut as rutEmpresa, 'Trescientos Mil Pesos' as sueldoEnPalabrasTrabajador, "
				+ " ifnull(DATE_FORMAT(cntt.fechaInicio_actividad,'%d-%m-%Y'), '01-01-2018') as fechaInicio "
				+" ,DATE_FORMAT(cntt.FechaTerminoContrato,'%d-%m-%Y') as fechaTerminoContratoFaena "
				+" ,cntt.articuloTerminoContrato as articulo "
				+" ,cntt.incisoTerminoContrato as incisoArticulo "
				+" ,cntt.lugarPago as lugarPagoFiniquito "
				+" ,DATE_FORMAT(cntt.fechaPago,'%d-%m-%Y') as fechaPagoFiniquito "
				+" ,DATE_FORMAT(cntt.horaPago,'%h@%i@%s') as horaInicioPagoFiniquito "
				+" ,DATE_FORMAT(cntt.horaPago,'%h@%i@%s') as horaFinPagoFiniquito "
				+" ,salud.descripcion as salud "
				+" ,afp.descripcion as afp "
				+" ,faena.nombreFaena as faenaTrabajador "
				+" ,tipoCntt.descripcion as tipoContrato "
				+" ,tipoCuenta.descripcion as tipoPago "
				+ "from trabajadores tr "
				+ "inner join contratos cntt on tr.codigo = cntt.codigo_trabajador  "
				+ "left join cuentaBancaria cuenta on tr.codigo = cuenta.codigoTrabajador and cuenta.cuentaPrimaria = 1 "
				+ "left join sociedad soc on cntt.idSociedad = soc.idSociedad "
				+ "left join parametros nac on tr.idNacionalidad = nac.llave and nac.codigo = 'NACIONALIDAD' "
				+ "left join parametros estadoCivil on tr.idEstadoCivil = estadoCivil.llave and estadoCivil.codigo = 'ESTADO_CIVIL' "
				+ "left join parametros salud on tr.idIsapre = salud.llave and salud.codigo = 'ISAPRE' "
				+ "left join parametros afp on tr.idAFP = afp.llave and afp.codigo = 'AFP' "
				+ "left join parametros tipoCntt on cntt.tipoContrato = tipoCntt.llave and tipoCntt.codigo = 'TIPO_CONTRATO' "
				+ "left join parametros tipoCuenta on cuenta.idTipoCuenta = tipoCuenta.llave and tipoCuenta.codigo = 'TIPO_DE_CUENTA' "
				+ "left join comuna com on tr.idcomuna = com.id "
				+ "left join cargos nombreCargo on (cntt.cargo = nombreCargo.id_cargo) "
				+ "left join sw_m_faena faena on (tr.idFaena = faena.idFaena) "
				+ "where tr.codigo = "+ codTrabajador + " and cntt.id = " + idContrato; 
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){				
				tr.setCodigoTrabajador(rs.getString("codigoTrabajador").toUpperCase());
				tr.setCiudadContrato(rs.getString("ciudadContrato").toUpperCase());
				tr.setNombreEmpresa(rs.getString("nombreEmpresa").toUpperCase());
				tr.setAppPatTrabajador(rs.getString("appPatTrabajador").toUpperCase());
				tr.setAppMaternoTrabajador(rs.getString("appMaternoTrabajador").toUpperCase());
				tr.setNombreTrabajador(rs.getString("nombreTrabajador").toUpperCase());
				tr.setRutCompletoTrabajador(rs.getString("rutCompletoTrabajador").toUpperCase());
				tr.setEstadoCivil(rs.getString("estadoCivil").toUpperCase());
				tr.setFechaNacimientoTrabajador(GeneralUtility.convertStringDDMMYYToDateInWord(rs.getString("fechaNacimientoTrabajador")));
				tr.setNacionalidadTrabajador(rs.getString("nacionalidadTrabajador").toUpperCase());
				tr.setDireccionTrabajador(rs.getString("direccionTrabajador").toUpperCase().replaceAll(",", "&"));
				tr.setComunaTrabajador(rs.getString("comuna").toUpperCase());
				tr.setCargoTrabajador(rs.getString("cargoTrabajador").toUpperCase());
				tr.setFaenaContratacion(rs.getString("faenaContratacion").toUpperCase());
				tr.setTemporadaContratacion(rs.getString("temporadaContratacion").toUpperCase());
				tr.setSueldoCPuntosTrabajador(GeneralUtility.formatStringNumberWithDotAndDecimal(rs.getString("SueldoCpuntosTrabajador")));
				tr.setSueldoEnPalabrasTrabajador(GeneralUtility.capitalizeFirstLetter(GeneralUtility.cantidadConLetra(rs.getString("SueldoCpuntosTrabajador")).toLowerCase() + "pesos"));
				tr.setFechaInicio(GeneralUtility.convertStringDDMMYYToDateInWord(rs.getString("fechaInicio")));
				tr.setCiudadContrato(rs.getString("ciudadContrato").toUpperCase());
				tr.setRutEmpresa(rs.getString("rutEmpresa").toUpperCase());
				tr.setRutCompletoEmpresa(rs.getString("rutEmpresa").toUpperCase());
				tr.setFechaTerminoContratoFaena(GeneralUtility.convertStringDDMMYYToDateInWord(rs.getString("fechaTerminoContratoFaena")));
				tr.setArticuloContrato(rs.getString("articulo"));
				tr.setIncisoContrato(rs.getString("incisoArticulo"));
				tr.setLugarPagoFiniquito(rs.getString("lugarPagoFiniquito"));
				tr.setFechaPagoFiniquito(rs.getString("fechaPagoFiniquito"));
				tr.setHoraInicioPagoFiniquito(rs.getString("horaInicioPagoFiniquito"));
				tr.setHoraFinPagoFiniquito(rs.getString("horaFinPagoFiniquito"));
				tr.setSaludTrabajador(rs.getString("salud").toUpperCase());
				tr.setAFPTrabajador(rs.getString("afp").toUpperCase());
				tr.setFaenaTrabajador(rs.getString("faenaTrabajador") == null ? "FAENAS GENERALES" : rs.getString("faenaTrabajador").toUpperCase() );
				tr.setTipoContrato(rs.getString("tipoContrato"));
				tr.setTipoPago(rs.getString("tipoPago"));
				tr.setDiasTrabajadosTrabajador("30");
				
				return tr;
			}
			
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return tr;
	}
	
	

	public static GetDatosContratoTrabajador obtenerDatosTrabajadorByCodAndContratoForContrato(String codTrabajador, String idContrato) throws Exception{
		Statement ps = null;		
		String sql="";
		ConnectionDB db = new ConnectionDB();
		GetDatosContratoTrabajador tr = new GetDatosContratoTrabajador();
		
		try{
			sql = "select tr.codigo as codigoTrabajador, soc.denominacionSociedad as nombreEmpresa, "
				+ " concat(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ', tr.nombre ) as nombreCompletoTrabajador, "
				+ "tr.apellidoPaterno as appPatTrabajador,tr.apellidoMaterno as appMaternoTrabajador, "
				+ " CASE WHEN tr.rut = '' THEN tr.rutTemporal ELSE tr.rut END as rutCompletoTrabajador ,tr.nombre as nombreTrabajador, estadoCivil.descripcion as estadoCivil, "
				+ "DATE_FORMAT(tr.fNacimiento,'%d-%m-%Y') as fechaNacimientoTrabajador, nac.descripcion as nacionalidadTrabajador, "
				+ "ifnull(tr.direccion, concat(tr.calle, ' ', tr.ndireccion, ' ', tr.depto, ' ', tr.poblacion) ) as direccionTrabajador, "
				+ "com.nombre as comuna, "
				+ "nombreCargo.cargos as cargoTrabajador, 'PODA' as faenaContratacion, "
				+ "ifnull(DATE_FORMAT(cntt.fechaInicio_actividad,'%Y'), '2018') as temporadaContratacion, cntt.sueldoBase as sueldoCpuntosTrabajador,  "
				+ " com.nombre as ciudadContrato, soc.rut as rutEmpresa, 'Trescientos Mil Pesos' as sueldoEnPalabrasTrabajador, "
				+ " ifnull(DATE_FORMAT(cntt.fechaInicio_actividad,'%d-%m-%Y'), '01-01-2018') as fechaInicio "
				+" ,DATE_FORMAT(cntt.FechaTerminoContrato,'%d-%m-%Y') as fechaTerminoContratoFaena "
				+" ,cntt.articuloTerminoContrato as articulo "
				+" ,cntt.incisoTerminoContrato as incisoArticulo "
				+" ,cntt.lugarPago as lugarPagoFiniquito "
				+" ,DATE_FORMAT(cntt.fechaPago,'%d-%m-%Y') as fechaPagoFiniquito "
				+" ,DATE_FORMAT(cntt.horaPago,'%h@%i@%s') as horaInicioPagoFiniquito "
				+" ,DATE_FORMAT(cntt.horaPago,'%h@%i@%s') as horaFinPagoFiniquito "
				+" ,salud.descripcion as salud "
				+" ,afp.descripcion as afp "
				+" ,faena.nombreFaena as faenaTrabajador "
				+" ,tipoCntt.descripcion as tipoContrato "
				+" ,tipoCuenta.descripcion as tipoPago "
				//MODULO REPRESENTANTE LEGAL
				+" ,c.direccion_huerto as direccionEmpresa "
				+" ,concat(c.representante_legal_apPaterno,' ',c.representante_legal_apMaterno,' ',c.representante_legal_nombre) as representanteNombre "
				+" ,c.representante_legal_rut as representanteRut "
				+" ,c.numero_telefono as representanteTelefono "
				+" ,c.ciudad_huerto as ciudadEmpresa "
				+" ,c.comuna_huerto as comunaEmpresa "
				
				+ "from trabajadores tr "
				+ "inner join contratos cntt on tr.codigo = cntt.codigo_trabajador  "
				+ "left join cuentaBancaria cuenta on tr.codigo = cuenta.codigoTrabajador and cuenta.cuentaPrimaria = 1 "
				+ "left join sociedad soc on cntt.idSociedad = soc.idSociedad "
				+ "left join parametros nac on tr.idNacionalidad = nac.llave and nac.codigo = 'NACIONALIDAD' "
				+ "left join parametros estadoCivil on tr.idEstadoCivil = estadoCivil.llave and estadoCivil.codigo = 'ESTADO_CIVIL' "
				+ "left join parametros salud on tr.idIsapre = salud.llave and salud.codigo = 'ISAPRE' "
				+ "left join parametros afp on tr.idAFP = afp.llave and afp.codigo = 'AFP' "
				+ "left join parametros tipoCntt on cntt.tipoContrato = tipoCntt.llave and tipoCntt.codigo = 'TIPO_CONTRATO' "
				+ "left join parametros tipoCuenta on cuenta.idTipoCuenta = tipoCuenta.llave and tipoCuenta.codigo = 'TIPO_DE_CUENTA' "
				+ "left join comuna com on tr.idcomuna = com.id "
				+ "left join cargos nombreCargo on (cntt.cargo = nombreCargo.id_cargo) "
				+ "left join sw_m_faena faena on (tr.idFaena = faena.idFaena) "
				+ "left join campo c on ( tr.idHuerto = c.campo  ) "
				+ "where tr.codigo = "+ codTrabajador + " and cntt.id = " + idContrato; 
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){				
				tr.setCodigoTrabajador(rs.getString("codigoTrabajador").toUpperCase());
				tr.setCiudadContrato(rs.getString("ciudadContrato").toUpperCase());
				tr.setNombreEmpresa(rs.getString("nombreEmpresa").toUpperCase());
				tr.setAppPatTrabajador(rs.getString("appPatTrabajador").toUpperCase());
				tr.setAppMaternoTrabajador(rs.getString("appMaternoTrabajador").toUpperCase());
				tr.setNombreTrabajador(rs.getString("nombreTrabajador").toUpperCase());
				tr.setRutCompletoTrabajador(rs.getString("rutCompletoTrabajador").toUpperCase());
				tr.setEstadoCivil(rs.getString("estadoCivil").toUpperCase());
				tr.setFechaNacimientoTrabajador(GeneralUtility.convertStringDDMMYYToDateInWord(rs.getString("fechaNacimientoTrabajador")));
				tr.setNacionalidadTrabajador(rs.getString("nacionalidadTrabajador").toUpperCase());
				tr.setDireccionTrabajador(rs.getString("direccionTrabajador").toUpperCase().replaceAll(",", "&"));
				tr.setComunaTrabajador(rs.getString("comuna").toUpperCase());
				tr.setCargoTrabajador(rs.getString("cargoTrabajador").toUpperCase());
				tr.setFaenaContratacion(rs.getString("faenaContratacion").toUpperCase());
				tr.setTemporadaContratacion(rs.getString("temporadaContratacion").toUpperCase());
				tr.setSueldoCPuntosTrabajador(GeneralUtility.formatStringNumberWithDotAndDecimal(rs.getString("SueldoCpuntosTrabajador")));
				tr.setSueldoEnPalabrasTrabajador(GeneralUtility.capitalizeFirstLetter(GeneralUtility.cantidadConLetra(rs.getString("SueldoCpuntosTrabajador")).toLowerCase() + "pesos"));
				tr.setFechaInicio(GeneralUtility.convertStringDDMMYYToDateInWord(rs.getString("fechaInicio")));
				tr.setCiudadContrato(rs.getString("ciudadContrato").toUpperCase());
				tr.setRutEmpresa(rs.getString("rutEmpresa").toUpperCase());
				tr.setRutCompletoEmpresa(rs.getString("rutEmpresa").toUpperCase());
				tr.setFechaTerminoContratoFaena(GeneralUtility.convertStringDDMMYYToDateInWord(rs.getString("fechaTerminoContratoFaena")));
				tr.setArticuloContrato(rs.getString("articulo"));
				tr.setIncisoContrato(rs.getString("incisoArticulo"));
				tr.setLugarPagoFiniquito(rs.getString("lugarPagoFiniquito"));
				tr.setFechaPagoFiniquito(rs.getString("fechaPagoFiniquito"));
				tr.setHoraInicioPagoFiniquito(rs.getString("horaInicioPagoFiniquito"));
				tr.setHoraFinPagoFiniquito(rs.getString("horaFinPagoFiniquito"));
				tr.setSaludTrabajador(rs.getString("salud").toUpperCase());
				tr.setAFPTrabajador(rs.getString("afp").toUpperCase());
				tr.setFaenaTrabajador(rs.getString("faenaTrabajador") == null ? "FAENAS GENERALES" : rs.getString("faenaTrabajador").toUpperCase() );
				tr.setTipoContrato(rs.getString("tipoContrato"));
				tr.setTipoPago(rs.getString("tipoPago"));
				tr.setDiasTrabajadosTrabajador("30");
				tr.setNombreCompletoTrabajador(rs.getString("nombreCompletoTrabajador"));
				tr.setCiudadEmpresa(rs.getString("ciudadEmpresa"));
				tr.setDireccionEmpresa(rs.getString("direccionEmpresa"));
				//tr.setRepresentanteEmpresa(rs.getString("representanteEmpresa"));
				tr.setRepresentanteTelefono(rs.getString("representanteTelefono"));
				tr.setRepresentanteRut(rs.getString("representanteRut"));
				tr.setComunaEmpresa(rs.getString("comunaEmpresa"));
				tr.setRepresentanteNombre(rs.getString("representanteNombre"));
				
				return tr;
			}
			
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return tr;
	}
	
	
	
	
	
	public static GetDatosContratoTrabajador obtenerDatosTrabajadorByCod2(String codigo, String idContrato, String periodo) throws Exception{
		Statement ps = null;		
		String sql="";
		ConnectionDB db = new ConnectionDB();
		GetDatosContratoTrabajador tr = new GetDatosContratoTrabajador();
		
		try{
			sql = "select ifnull((select descripcion from parametros where codigo = 'APV' and codPrevired = xx.numeroAPV), '')  as apvTrabajador ,tr.codigo as codigoTrabajador, soc.denominacionSociedad as nombreEmpresa, "
				+ " ifnull(tr.apellidoPaterno, '') as appPatTrabajador, ifnull(tr.apellidoMaterno,'') as appMaternoTrabajador, "
				+ " CASE WHEN tr.rut = '' THEN tr.rutTemporal ELSE tr.rut END as rutCompletoTrabajador,tr.nombre as nombreTrabajador, ifnull(estadoCivil.descripcion, '') as estadoCivil, "
				+ " ifnull(DATE_FORMAT(tr.fNacimiento,'%d-%m-%Y'), '') as fechaNacimientoTrabajador, ifnull(nac.descripcion,'') as nacionalidadTrabajador, "
				+ " ifnull(tr.direccion, concat(tr.calle, ' ', tr.ndireccion, ' ', tr.depto, ' ', tr.poblacion) ) as direccionTrabajador, "
				+ " ifnull(com.nombre, '') as comuna, "
				+ " ifnull(nombreCargo.cargos, '') as cargoTrabajador, 'PODA' as faenaContratacion, "
				+ " ifnull(DATE_FORMAT(cntt.fechaInicio_actividad,'%Y'), '2018') as temporadaContratacion, ifnull(cntt.sueldoBase, '') as sueldoCpuntosTrabajador,  "
				+ " ifnull(com.nombre, '') as ciudadContrato, ifnull(soc.rut,'') as rutEmpresa, 'Trescientos Mil Pesos' as sueldoEnPalabrasTrabajador, "
				+ " DATE_FORMAT(cntt.fechaInicio_actividad,'%d-%m-%Y') as fechaInicio "
				+" ,DATE_FORMAT(cntt.FechaTerminoContrato,'%d-%m-%Y') as fechaTerminoContratoFaena "
				+" ,ifnull(cntt.articuloTerminoContrato, '') as articulo "
				+" ,ifnull(cntt.incisoTerminoContrato, '') as incisoArticulo "
				+" ,ifnull(cntt.lugarPago, '') as lugarPagoFiniquito "
				+" ,ifnull(DATE_FORMAT(cntt.fechaPago,'%d-%m-%Y'), '') as fechaPagoFiniquito "
				+" ,ifnull(DATE_FORMAT(cntt.horaPago,'%h@%i@%s'), '') as horaInicioPagoFiniquito "
				+" ,ifnull(DATE_FORMAT(cntt.horaPago,'%h@%i@%s'), '') as horaFinPagoFiniquito "
				+" ,ifnull(salud.descripcion, '') as salud "
				+" ,ifnull(afp.descripcion, '') as afp "
				+" ,ifnull(faena.nombreFaena, '') as faenaTrabajador "
				+" ,ifnull(tipoCntt.descripcion, '') as tipoContrato "
				+" ,ifnull(CASE WHEN tipoCuenta.llave = '07' THEN tipoBanco.descripcion ELSE tipoCuenta.descripcion END, '') as tipoPago "
				+ "from trabajadores tr "
				+ "inner join contratos cntt on tr.codigo = cntt.codigo_trabajador  "
				+" LEFT JOIN "
				    +"(select codTrabajador,idContrato,periodo,SUM(CASE " 
						                +"WHEN " 
						                    +"idConcepto = 213 "
						                +"THEN " 
						                    +"valor " 
						                +"ELSE 0 "
						            +"END) numeroAPV " 
					+"from "
				    +"sw_liquidacionDetalle liq group by  codTrabajador,idContrato, periodo)  as xx on xx.idContrato = cntt.id and xx.periodo = "+periodo+" " 
				+ "left join cuentaBancaria cuenta on tr.codigo = cuenta.codigoTrabajador and cuenta.cuentaPrimaria = 1 "
				+ "left join sociedad soc on cntt.idSociedad = soc.idSociedad "
				+ "left join parametros apv on tr.institucionAPV = apv.llave and apv.codigo = 'APV' "
				+ "left join parametros nac on tr.idNacionalidad = nac.llave and nac.codigo = 'NACIONALIDAD' "
				+ "left join parametros estadoCivil on tr.idEstadoCivil = estadoCivil.llave and estadoCivil.codigo = 'ESTADO_CIVIL' "
				+ "left join parametros salud on tr.idIsapre = salud.llave and salud.codigo = 'ISAPRE' "
				+ "left join parametros afp on tr.idAFP = afp.llave and afp.codigo = 'AFP' "
				+ "left join parametros tipoCntt on cntt.tipoContrato = tipoCntt.llave and tipoCntt.codigo = 'TIPO_CONTRATO' "
				+ "left join parametros tipoCuenta on cuenta.idTipoCuenta = tipoCuenta.llave and tipoCuenta.codigo = 'TIPO_DE_CUENTA' "
				+ "left join parametros tipoBanco on cuenta.idBanco = tipoBanco.llave and tipoBanco.codigo = 'BANCO' "
				+ "left join comuna com on tr.idcomuna = com.id "
				+ "left join cargos nombreCargo on (cntt.cargo = nombreCargo.id_cargo) "
				+ "left join sw_m_faena faena on (tr.idFaena = faena.idFaena) "
				+ "where tr.codigo = "+ codigo
			 	+ " and cntt.id = "+ idContrato;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			System.out.println("////////////////////aqui2");
			System.out.println(sql);
			while(rs.next()){		
				tr.setApvTrabajador(rs.getString("apvTrabajador").toUpperCase());
				tr.setCodigoTrabajador(rs.getString("codigoTrabajador").toUpperCase());
				tr.setCiudadContrato(rs.getString("ciudadContrato").toUpperCase());
				tr.setNombreEmpresa(rs.getString("nombreEmpresa").toUpperCase());
				tr.setAppPatTrabajador(rs.getString("appPatTrabajador").toUpperCase());
				tr.setAppMaternoTrabajador(rs.getString("appMaternoTrabajador").toUpperCase());
				tr.setNombreTrabajador(rs.getString("nombreTrabajador").toUpperCase());
				tr.setRutCompletoTrabajador(rs.getString("rutCompletoTrabajador").toUpperCase());
				tr.setEstadoCivil(rs.getString("estadoCivil").toUpperCase());
				tr.setFechaNacimientoTrabajador(GeneralUtility.convertStringDDMMYYToDateInWord(rs.getString("fechaNacimientoTrabajador")));
				tr.setNacionalidadTrabajador(rs.getString("nacionalidadTrabajador").toUpperCase());
				tr.setDireccionTrabajador(rs.getString("direccionTrabajador").toUpperCase().replaceAll(",", "&"));
				tr.setComunaTrabajador(rs.getString("comuna").toUpperCase());
				tr.setCargoTrabajador(rs.getString("cargoTrabajador").toUpperCase());
				tr.setFaenaContratacion(rs.getString("faenaContratacion").toUpperCase());
				tr.setTemporadaContratacion(rs.getString("temporadaContratacion").toUpperCase());
				tr.setSueldoCPuntosTrabajador(GeneralUtility.formatStringNumberWithDotAndDecimal(rs.getString("SueldoCpuntosTrabajador")));
				tr.setSueldoEnPalabrasTrabajador(GeneralUtility.capitalizeFirstLetter(GeneralUtility.cantidadConLetra(rs.getString("SueldoCpuntosTrabajador")).toLowerCase() + "pesos"));
				tr.setFechaInicio(GeneralUtility.convertStringDDMMYYToDateInWord(rs.getString("fechaInicio")));
				tr.setCiudadContrato(rs.getString("ciudadContrato").toUpperCase());
				tr.setRutEmpresa(rs.getString("rutEmpresa").toUpperCase());
				tr.setRutCompletoEmpresa(rs.getString("rutEmpresa").toUpperCase());
				tr.setFechaTerminoContratoFaena(GeneralUtility.convertStringDDMMYYToDateInWord(rs.getString("fechaTerminoContratoFaena")));
				tr.setArticuloContrato(rs.getString("articulo"));
				tr.setIncisoContrato(rs.getString("incisoArticulo"));
				tr.setLugarPagoFiniquito(rs.getString("lugarPagoFiniquito"));
				tr.setFechaPagoFiniquito(rs.getString("fechaPagoFiniquito"));
				tr.setHoraInicioPagoFiniquito(rs.getString("horaInicioPagoFiniquito"));
				tr.setHoraFinPagoFiniquito(rs.getString("horaFinPagoFiniquito"));
				tr.setSaludTrabajador(rs.getString("salud").toUpperCase());
				tr.setAFPTrabajador(rs.getString("afp").toUpperCase());
				tr.setFaenaTrabajador(rs.getString("faenaTrabajador") == null ? "FAENAS GENERALES" : rs.getString("faenaTrabajador").toUpperCase() );
				tr.setTipoContrato(rs.getString("tipoContrato"));
				tr.setTipoPago(rs.getString("tipoPago"));
				tr.setDiasTrabajadosTrabajador("30");
				
				return tr;
			}
			
		}catch (SQLException e){
			throw new Exception ("Error al Obtener datos del Trabajador:" + e.getMessage());
		}catch (Exception e){
			throw new Exception ("Error al Obtener datos del Trabajador:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return tr;
	}
	
	
	public static GetDatosCartaTerminoTrabajador obtenerDatosTrabajadorByCod(String codTrabajador) throws Exception{
		Statement ps = null;		
		String sql="";
		ConnectionDB db = new ConnectionDB();
		GetDatosCartaTerminoTrabajador tr = new GetDatosCartaTerminoTrabajador();
		
		try{
			sql = "select tr.codigo as codigoTrabajador, soc.denominacionSociedad as nombreEmpresa, "
				+ "tr.apellidoPaterno as appPatTrabajador,tr.apellidoMaterno as appMaternoTrabajador, "
				+ " CASE WHEN tr.rut = '' THEN tr.rutTemporal ELSE tr.rut END as rutCompletoTrabajador ,tr.nombre as nombreTrabajador, estadoCivil.descripcion as estadoCivil, "
				+ "DATE_FORMAT(tr.fNacimiento,'%d-%m-%Y') as fechaNacimientoTrabajador, nac.descripcion as nacionalidadTrabajador, "
				+ "ifnull(tr.direccion, concat(tr.calle, ' ', tr.ndireccion, ' ', tr.depto, ' ', tr.poblacion) ) as direccionTrabajador, "
				+ "com.nombre as comuna, "
				+ "nombreCargo.cargos as cargoTrabajador, 'PODA' as faenaContratacion, "
				+ "ifnull(DATE_FORMAT(cntt.fechaInicio_actividad,'%Y'), '2018') as temporadaContratacion, cntt.sueldoBase as sueldoCpuntosTrabajador,  "
				+ " com.nombre as ciudadContrato, soc.rut as rutEmpresa, 'Trescientos Mil Pesos' as sueldoEnPalabrasTrabajador, "
				+ " ifnull(DATE_FORMAT(cntt.fechaInicio_actividad,'%d-%m-%Y'), '01-01-2018') as fechaInicio "
				+" ,DATE_FORMAT(cntt.FechaTerminoContrato,'%d-%m-%Y') as fechaTerminoContratoFaena "
				+" ,atc.nombre as articulo "
				+" ,itc.descripcion as incisoArticulo "
				+" ,cntt.lugarPago as lugarPagoFiniquito "
				+" ,DATE_FORMAT(cntt.fechaPago,'%d-%m-%Y') as fechaPagoFiniquito "
				+" ,DATE_FORMAT(cntt.horaPago,'%h@%i@%s') as horaInicioPagoFiniquito "
				+" ,DATE_FORMAT(cntt.horaPago2,'%h@%i@%s') as horaFinPagoFiniquito "
				+" ,salud.descripcion as salud "
				+" ,afp.descripcion as afp "
				+" ,faena.nombreFaena as faenaTrabajador "
				+" ,tipoCntt.descripcion as tipoContrato "
				+" ,tipoCuenta.descripcion as tipoPago "
				+" ,cntt.fechaNotificacion as fechaNotificacion "
				+ "from trabajadores tr "
				+ "inner join contratos cntt on tr.codigo = cntt.codigo_trabajador and cntt.id in  "
				+ "		( select id from contratos where codigo_trabajador = tr.codigo and fechaInicio_actividad = (SELECT max(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = tr.codigo ) ) "
				+ "left join cuentaBancaria cuenta on tr.codigo = cuenta.codigoTrabajador and cuenta.cuentaPrimaria = 1 "
				+ "left join sociedad soc on cntt.idSociedad = soc.idSociedad "
				+ "left join parametros nac on tr.idNacionalidad = nac.llave and nac.codigo = 'NACIONALIDAD' "
				+ "left join parametros estadoCivil on tr.idEstadoCivil = estadoCivil.llave and estadoCivil.codigo = 'ESTADO_CIVIL' "
				+ "left join parametros salud on tr.idIsapre = salud.llave and salud.codigo = 'ISAPRE' "
				+ "left join parametros afp on tr.idAFP = afp.llave and afp.codigo = 'AFP' "
				+ "left join parametros tipoCntt on cntt.tipoContrato = tipoCntt.llave and tipoCntt.codigo = 'TIPO_CONTRATO' "
				+ "left join parametros tipoCuenta on cuenta.idTipoCuenta = tipoCuenta.llave and tipoCuenta.codigo = 'TIPO_DE_CUENTA' "
				+ "left join comuna com on tr.idcomuna = com.id "
				+ "left join cargos nombreCargo on (cntt.cargo = nombreCargo.id_cargo) "
				+ "left join sw_m_faena faena on (tr.idFaena = faena.idFaena) "
				+ " INNER JOIN sw_m_articuloTerminoContrato atc ON (cntt.articuloTerminoContrato = atc.idArticuloTerminoContrato) " 
				+ " INNER JOIN sw_m_incisoTerminoContrato itc ON (cntt.incisoTerminoContrato = itc.idIncisoTerminoContrato) "
				
				
				+ "where tr.codigo = "+ codTrabajador;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){				
				tr.setCodigoTrabajador(rs.getString("codigoTrabajador").toUpperCase());
				tr.setCiudadContrato(rs.getString("ciudadContrato").toUpperCase());
				tr.setNombreEmpresa(rs.getString("nombreEmpresa").toUpperCase());
				tr.setAppPatTrabajador(rs.getString("appPatTrabajador").toUpperCase());
				tr.setAppMaternoTrabajador(rs.getString("appMaternoTrabajador").toUpperCase());
				tr.setNombreTrabajador(rs.getString("nombreTrabajador").toUpperCase());
				tr.setRutCompletoTrabajador(rs.getString("rutCompletoTrabajador").toUpperCase());
				tr.setEstadoCivil(rs.getString("estadoCivil").toUpperCase());
				tr.setFechaNacimientoTrabajador(GeneralUtility.convertStringDDMMYYToDateInWord(rs.getString("fechaNacimientoTrabajador")));
				tr.setNacionalidadTrabajador(rs.getString("nacionalidadTrabajador").toUpperCase());
				tr.setDireccionTrabajador(rs.getString("direccionTrabajador").toUpperCase().replaceAll(",", "&"));
				tr.setComunaTrabajador(rs.getString("comuna").toUpperCase());
				tr.setCargoTrabajador(rs.getString("cargoTrabajador").toUpperCase());
				tr.setFaenaContratacion(rs.getString("faenaContratacion").toUpperCase());
				tr.setTemporadaContratacion(rs.getString("temporadaContratacion").toUpperCase());
				tr.setSueldoCPuntosTrabajador(GeneralUtility.formatStringNumberWithDotAndDecimal(rs.getString("SueldoCpuntosTrabajador")));
				tr.setSueldoEnPalabrasTrabajador(GeneralUtility.capitalizeFirstLetter(GeneralUtility.cantidadConLetra(rs.getString("SueldoCpuntosTrabajador")).toLowerCase() + "pesos"));
				tr.setFechaInicio(GeneralUtility.convertStringDDMMYYToDateInWord(rs.getString("fechaInicio")));
				tr.setCiudadContrato(rs.getString("ciudadContrato").toUpperCase());
				tr.setRutEmpresa(rs.getString("rutEmpresa").toUpperCase());
				tr.setRutCompletoEmpresa(rs.getString("rutEmpresa").toUpperCase());
				tr.setFechaTerminoContratoFaena(GeneralUtility.convertStringDDMMYYToDateInWord(rs.getString("fechaTerminoContratoFaena")));
				tr.setArticuloContrato(rs.getString("articulo"));
				tr.setIncisoContrato(rs.getString("incisoArticulo"));
				tr.setLugarPagoFiniquito(rs.getString("lugarPagoFiniquito"));
				tr.setFechaPagoFiniquito(rs.getString("fechaPagoFiniquito"));
				tr.setHoraInicioPagoFiniquito(rs.getString("horaInicioPagoFiniquito"));
				tr.setHoraFinPagoFiniquito(rs.getString("horaFinPagoFiniquito"));
				tr.setSaludTrabajador(rs.getString("salud").toUpperCase());
				tr.setAFPTrabajador(rs.getString("afp").toUpperCase());
				tr.setFaenaTrabajador(rs.getString("faenaTrabajador") == null ? "FAENAS GENERALES" : rs.getString("faenaTrabajador").toUpperCase() );
				tr.setTipoContrato(rs.getString("tipoContrato"));
				tr.setTipoPago(rs.getString("tipoPago"));
				tr.setDiasTrabajadosTrabajador("30");
				tr.setFechaInicio(GeneralUtility.convertStringDDMMYYToDateInWord(rs.getString("fechaInicio")));
				tr.setTipoContrato(rs.getString("tipoContrato"));
				tr.setFechaPagoFiniquito(GeneralUtility.convertStringDDMMYYToDateInWord(rs.getString("fechaPagoFiniquito")));
				tr.setIncisoDescripcionContrato(rs.getString("incisoArticulo"));
				tr.setFechaNotificacion(rs.getString("fechaNotificacion"));
				
				return tr;
			}
			
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return tr;
	}
	
	
	
}

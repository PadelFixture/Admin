package wordCreator;

public class JsonParser {

	public static void main(String[] args) {
		
		
		String JSONConVariables="{'ciudadContrato': 'Talca','nombreEmpresa':'cyber','rutSinDVEmpresa':'16855117','digitoVerificadorEmpresa':'7','rutCompletoEmpresa':'76428956-0','appPatTrabajador':'Caris','appMaternoTrabajador':'Roman','nombreTrabajador':'Mauricio Nicolas','rutSinDvTrabajador':'16855117','digitoVerificadorTrabajador':'7','rutCompletoTrabajador':'16855117-7','estadoCivil':'soltero','fechaNacimientoTrabajador':'26 de noviembre de 1987','nacionalidadTrabajador':'Chileno','direccionTrabajador':'14 de Diciembre','comuna':'Melipilla','cargoTrabajador':'Temporero','faenaContratacion':'marzo 2018','temporadaContratacion':'segunda Temporada','sueldoCPuntosTrabajador':'560.000','sueldoEnPalabrasTrabajador':'Quinientos sesenta mil','fechaInicio':'6 de junio','cuidadContrato':'Santiago'}";
		
		JsonStringParser j= new JsonStringParser();
		j.parser(JSONConVariables);
	}

}

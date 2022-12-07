package wordCreator;

import java.io.File;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lib.io.config;



public class utils {
	
	private final static Logger LOG = LoggerFactory.getLogger(utils.class);
	
	public static String CambiarKey(String text, String Key, String value) {
		text = text.replace(Key, value);
		return text;
	}

	public static String obtenerCarpetaServidor() {
		File dir = new File(config.getProperty("Permiso"));
		// attempt to create the directory here
		boolean successful = dir.mkdirs();
		if (successful) {
			// LOG.info("Directorio creado --> {}",dir.getAbsolutePath());
			System.out.println("Directorio creado --> {}" + dir.getAbsolutePath());
		} else {
			if (dir.exists()) {
				System.out.println("Directorio existe --> {}" + dir.getAbsolutePath());
				// LOG.info("Directorio existe --> {}",dir.getAbsolutePath());
			} else {
				// LOG.info("Directorio no creado");
				System.out.println("Directorio no creado");
			}
		}

		// String path = System.getProperty("user.home");
		// String separator = System.getProperty("file.separator");
		//
		// path = path + separator + "temp" + separator;
		// File dir2 = new File(path+Config.getProperty("directorioBase"));
		// // attempt to create the directory here
		// boolean successful2 = dir2.mkdirs();
		// if (successful2) {
		// LOG.info("Directorio creado --> {}",dir2.getAbsolutePath());
		// } else {
		// if(dir.exists()){
		// LOG.info("Directorio existe --> {}",dir2.getAbsolutePath());
		// }else{
		// LOG.info("Directorio no creado");
		// }
		// }

		return dir.getAbsolutePath() + File.separator;
	}

	////////////////////// RUTA CSV DETALLE NOMINA
	////////////////////// ANTICIPO///////////////////////////////////////////////////////
	public static String csvDetalleNomina() {
		File dir = new File(config.getProperty("detallecsvNominaAnticipo"));
		// attempt to create the directory here
		boolean successful = dir.mkdirs();
		if (successful) {
			// LOG.info("Directorio creado --> {}",dir.getAbsolutePath());
			System.out.println("Directorio creado --> {}" + dir.getAbsolutePath());
		} else {
			if (dir.exists()) {
				System.out.println("Directorio existe --> {}" + dir.getAbsolutePath());
				// LOG.info("Directorio existe --> {}",dir.getAbsolutePath());
			} else {
				// LOG.info("Directorio no creado");
				System.out.println("Directorio no creado");
			}
		}

		return dir.getAbsolutePath() + File.separator;
	}

	public static String FormatearRUT(String rut) {
		
		String format = "";
		
		try{
			
			 int cont = 0;
		        
		        rut = rut.replace(".", "");
		        rut = rut.replace("-", "");
		        format = "-" + rut.substring(rut.length() - 1);
		        for (int i = rut.length() - 2; i >= 0; i--) {
		            format = rut.substring(i, i + 1) + format;
		            cont++;
		            if (cont == 3 && i != 0) {
		                format = "." + format;
		                cont = 0;
		            }
		        }
			
		}catch(Exception e){
			return "";
		}
		return format;
        
}
	
	////////////////////// RUTA CSV TOTAL
	////////////////////// BANCO///////////////////////////////////////////////////////
	public static String csvTotalBancoNominaAnticipos() {
		File dir = new File(config.getProperty("CSVTotalBancoNominaAnticipo"));
		// attempt to create the directory here
		boolean successful = dir.mkdirs();
		if (successful) {
			// LOG.info("Directorio creado --> {}",dir.getAbsolutePath());
			System.out.println("Directorio creado --> {}" + dir.getAbsolutePath());
		} else {
			if (dir.exists()) {
				System.out.println("Directorio existe --> {}" + dir.getAbsolutePath());
				// LOG.info("Directorio existe --> {}",dir.getAbsolutePath());
			} else {
				// LOG.info("Directorio no creado");
				System.out.println("Directorio no creado");
			}
		}

		return dir.getAbsolutePath() + File.separator;
	}

	////////////////////// RUTA Permiso Sin Goce de
	////////////////////// Sueldo///////////////////////////////////////////////////////
	public static String PermisoSGS() {
		File dir = new File(config.getProperty("PermisoSingoceDeSueldo"));
		// attempt to create the directory here
		boolean successful = dir.mkdirs();
		if (successful) {
			// LOG.info("Directorio creado --> {}",dir.getAbsolutePath());
			System.out.println("Directorio creado --> {}" + dir.getAbsolutePath());
		} else {
			if (dir.exists()) {
				System.out.println("Directorio existe --> {}" + dir.getAbsolutePath());
				// LOG.info("Directorio existe --> {}",dir.getAbsolutePath());
			} else {
				// LOG.info("Directorio no creado");
				System.out.println("Directorio no creado");
			}
		}

		return dir.getAbsolutePath() + File.separator;
	}

	//////////////////////////////// ruta Permiso
	//////////////////////////////// /////////////////////////////////////////////////
	public static String Permiso() {
		File dir = new File(config.getProperty("Permiso"));
		// attempt to create the directory here
		boolean successful = dir.mkdirs();
		if (successful) {
			// LOG.info("Directorio creado --> {}",dir.getAbsolutePath());
			System.out.println("Directorio creado --> {}" + dir.getAbsolutePath());
		} else {
			if (dir.exists()) {
				System.out.println("Directorio existe --> {}" + dir.getAbsolutePath());
				// LOG.info("Directorio existe --> {}",dir.getAbsolutePath());
			} else {
				// LOG.info("Directorio no creado");
				System.out.println("Directorio no creado");
			}
		}
		return dir.getAbsolutePath() + File.separator;
	}
	/////////////////////////// Licencia////////////////////////////////////////////////////////////////////

	public static String Licencia() {
		File dir = new File(config.getProperty("VisitaComercio"));
		// attempt to create the directory here
		boolean successful = dir.mkdirs();
		if (successful) {
			// LOG.info("Directorio creado --> {}",dir.getAbsolutePath());
			System.out.println("Directorio creado --> {}" + dir.getAbsolutePath());
		} else {
			if (dir.exists()) {
				System.out.println("Directorio existe --> {}" + dir.getAbsolutePath());
				// LOG.info("Directorio existe --> {}",dir.getAbsolutePath());
			} else {
				// LOG.info("Directorio no creado");
				System.out.println("Directorio no creado");
			}
		}
		return dir.getAbsolutePath() + File.separator;
	}
	/////////////////////////// RUTA FINIQUITO
	/////////////////////////// TXT////////////////////////////////////////////////////////////////////

	public static String FiniquitoTxt() {
		File dir = new File(config.getProperty("FiniquitoTxt"));
		// attempt to create the directory here
		boolean successful = dir.mkdirs();
		if (successful) {
			// LOG.info("Directorio creado --> {}",dir.getAbsolutePath());
			System.out.println("Directorio creado --> {}" + dir.getAbsolutePath());
		} else {
			if (dir.exists()) {
				System.out.println("Directorio existe --> {}" + dir.getAbsolutePath());
				// LOG.info("Directorio existe --> {}",dir.getAbsolutePath());
			} else {
				// LOG.info("Directorio no creado");
				System.out.println("Directorio no creado");
			}
		}
		return dir.getAbsolutePath() + File.separator;
	}
	/////////////////////////// RUTA FINIQUITO
	/////////////////////////// TXT////////////////////////////////////////////////////////////////////

	public static String LiquidacionTxt() {
		File dir = new File(config.getProperty("LiquidacionTxt"));
		// attempt to create the directory here
		boolean successful = dir.mkdirs();
		if (successful) {
			// LOG.info("Directorio creado --> {}",dir.getAbsolutePath());
			System.out.println("Directorio creado --> {}" + dir.getAbsolutePath());
		} else {
			if (dir.exists()) {
				System.out.println("Directorio existe --> {}" + dir.getAbsolutePath());
				// LOG.info("Directorio existe --> {}",dir.getAbsolutePath());
			} else {
				// LOG.info("Directorio no creado");
				System.out.println("Directorio no creado");
			}
		}
		return dir.getAbsolutePath() + File.separator;
	}
	public static String Incidencia() {
		File dir = new File(config.getProperty("Incidencia"));
		// attempt to create the directory here
		boolean successful = dir.mkdirs();
		if (successful) {
			// LOG.info("Directorio creado --> {}",dir.getAbsolutePath());
			System.out.println("Directorio creado --> {}" + dir.getAbsolutePath());
		} else {
			if (dir.exists()) {
				System.out.println("Directorio existe --> {}" + dir.getAbsolutePath());
				// LOG.info("Directorio existe --> {}",dir.getAbsolutePath());
			} else {
				// LOG.info("Directorio no creado");
				System.out.println("Directorio no creado");
			}
		}
		return dir.getAbsolutePath() + File.separator;
	}
	
	
	public static String getServerFolder(String nameFolder) {
		File dir = new File(config.getProperty(nameFolder));
		
		// Intenta Crear el directorio
		boolean successful = dir.mkdirs();
		if (successful) {
			 LOG.info("Directorio creado --> {}",dir.getAbsolutePath());
		} else {
			if (dir.exists()) {
				LOG.info("Directorio existe --> {}",dir.getAbsolutePath());
			} else {
				LOG.info("Directorio no creado");
			}
		}

		return dir.getAbsolutePath() + File.separator;
	}
	
	
	private static final String UNIDADES[] = {"", "Un ", "Dos ", "Tres ", "Cuatro ", "Cinco ", "Seis ", "Siete ", "Ocho ", "Nueve "};
    private static final String DECENAS[]  = {"", "Dieci", "Veinti", "Treinta ", "Cuarenta ", "Cincuenta ", "Sesenta ", "Setenta ", "Ochenta ", "Noventa "};
    private static final String CENTENAS[] = {"", "Ciento ", "Doscientos ", "Trescientos ", "Cuatrocientos ", "Quinientos ", "Seiscientos ", "Setecientos ", "Ochocientos ", "Novecientos "};
	
	/**
     * Convierte el número que recibe como argumento a su representación escrita con letra.
     * 
     * @param s Una cadena de texto que contiene los dígitos de un número.
     * @return  Una cadena de texto que contiene la representación con letra de
     *          la parte entera del número que se recibió como argumento.
     */
    public static String cantidadConLetra(String s) {
        StringBuilder result = new StringBuilder();
        BigDecimal totalBigDecimal = new BigDecimal(s).setScale(2, BigDecimal.ROUND_DOWN);
        long parteEntera = totalBigDecimal.toBigInteger().longValue();
        int triUnidades      = (int)((parteEntera % 1000));
        int triMiles         = (int)((parteEntera / 1000) % 1000);
        int triMillones      = (int)((parteEntera / 1000000) % 1000);
        int triMilMillones   = (int)((parteEntera / 1000000000) % 1000);
        
        if (parteEntera == 0) {
            result.append("Cero ");
            return result.toString();
        }
        
        if (triMilMillones > 0) result.append(triTexto(triMilMillones).toString() + "Mil ");
        if (triMillones > 0)    result.append(triTexto(triMillones).toString());
        
        if (triMilMillones == 0 && triMillones == 1) result.append("Millón ");
        else if (triMilMillones > 0 || triMillones > 0) result.append("Millones ");
        
        if (triMiles > 0)       result.append(triTexto(triMiles).toString() + "Mil ");
        if (triUnidades > 0)    result.append(triTexto(triUnidades).toString());
        
        return result.toString();
    }

	
    /**
     * Convierte una cantidad de tres cifras a su representación escrita con letra.
     * 
     * @param n La cantidad a convertir.
     * @return  Una cadena de texto que contiene la representación con letra 
     *          del número que se recibió como argumento.
     */
    private static StringBuilder triTexto(int n) {
        StringBuilder result = new StringBuilder();
        int centenas = n / 100;
        int decenas  = (n % 100) / 10;
        int unidades = (n % 10);
        
        if (n == 100) {
            result.append("Cien ");
            return result;
        }
        else result.append(CENTENAS[centenas]);
        
        if (decenas == 1 && unidades <= 5) {
            if (unidades == 0) result.append("Diez ");
            else if (unidades == 1) result.append("Once ");
            else if (unidades == 2) result.append("Doce ");
            else if (unidades == 3) result.append("Trece ");
            else if (unidades == 4) result.append("Catorce ");
            else if (unidades == 5) result.append("Quince ");
            return result;
        }
        else if (decenas == 2 && unidades == 0) {
            result.append("Veinte ");
            return result;
        }
        else result.append(DECENAS[decenas]);
        
        if (decenas > 2 && unidades > 0)
            result.append("y ");
        
        result.append(UNIDADES[unidades]);
        
        return result;
    }
	
	
	
    /// ruta PRE NOMINA ANTICIPO
    
	public static String PreNominaAnticipo() {
		File dir = new File(config.getProperty("PreNominaAnticipos"));
		// attempt to create the directory here
		boolean successful = dir.mkdirs();
		if (successful) {
			// LOG.info("Directorio creado --> {}",dir.getAbsolutePath());
			System.out.println("Directorio creado --> {}" + dir.getAbsolutePath());
		} else {
			if (dir.exists()) {
				System.out.println("Directorio existe --> {}" + dir.getAbsolutePath());
				// LOG.info("Directorio existe --> {}",dir.getAbsolutePath());
			} else {
				// LOG.info("Directorio no creado");
				System.out.println("Directorio no creado");
			}
		}

		return dir.getAbsolutePath() + File.separator;
	}
	
	// ruta Documento Anticipo 
	
		public static String DocumentoAnticipos() {
			File dir = new File(config.getProperty("DocumentoAnticipo"));
			// attempt to create the directory here
			boolean successful = dir.mkdirs();
			if (successful) {
				// LOG.info("Directorio creado --> {}",dir.getAbsolutePath());
				System.out.println("Directorio creado --> {}" + dir.getAbsolutePath());
			} else {
				if (dir.exists()) {
					System.out.println("Directorio existe --> {}" + dir.getAbsolutePath());
					// LOG.info("Directorio existe --> {}",dir.getAbsolutePath());
				} else {
					// LOG.info("Directorio no creado");
					System.out.println("Directorio no creado");
				}
			}

			return dir.getAbsolutePath() + File.separator;
		}
		public static String TarjaPallet() {
			File dir = new File(config.getProperty("TarjaPallet"));
			// attempt to create the directory here
			boolean successful = dir.mkdirs();
			if (successful) {
				// LOG.info("Directorio creado --> {}",dir.getAbsolutePath());
				System.out.println("Directorio creado --> {}" + dir.getAbsolutePath());
			} else {
				if (dir.exists()) {
					System.out.println("Directorio existe --> {}" + dir.getAbsolutePath());
					// LOG.info("Directorio existe --> {}",dir.getAbsolutePath());
				} else {
					// LOG.info("Directorio no creado");
					System.out.println("Directorio no creado");
				}
			}

			return dir.getAbsolutePath() + File.separator;
		}
		public static String reportesExcel() {
			File dir = new File(config.getProperty("ReporteExcel"));
			// attempt to create the directory here
			boolean successful = dir.mkdirs();
			if (successful) {
				// LOG.info("Directorio creado --> {}",dir.getAbsolutePath());
				System.out.println("Directorio creado --> {}" + dir.getAbsolutePath());
			} else {
				if (dir.exists()) {
					System.out.println("Directorio existe --> {}" + dir.getAbsolutePath());
					// LOG.info("Directorio existe --> {}",dir.getAbsolutePath());
				} else {
					// LOG.info("Directorio no creado");
					System.out.println("Directorio no creado");
				}
			}

			return dir.getAbsolutePath() + File.separator;
		}
		
		public static String DocumentoFiniquitos() {
			File dir = new File(config.getProperty("DocumentoFiniquito"));
			// attempt to create the directory here
			boolean successful = dir.mkdirs();
			if (successful) {
				// LOG.info("Directorio creado --> {}",dir.getAbsolutePath());
				System.out.println("Directorio creado --> {}" + dir.getAbsolutePath());
			} else {
				if (dir.exists()) {
					System.out.println("Directorio existe --> {}" + dir.getAbsolutePath());
					// LOG.info("Directorio existe --> {}",dir.getAbsolutePath());
				} else {
					// LOG.info("Directorio no creado");
					System.out.println("Directorio no creado");
				}
			}

			return dir.getAbsolutePath() + File.separator;
		}
	
		
		public static String AvisoInspeccionTrabajo() {
			File dir = new File(config.getProperty("AvisoInspeccionTrabajo"));
			// attempt to create the directory here
			boolean successful = dir.mkdirs();
			if (successful) {
				// LOG.info("Directorio creado --> {}",dir.getAbsolutePath());
				System.out.println("Directorio creado --> {}" + dir.getAbsolutePath());
			} else {
				if (dir.exists()) {
					System.out.println("Directorio existe --> {}" + dir.getAbsolutePath());
					// LOG.info("Directorio existe --> {}",dir.getAbsolutePath());
				} else {
					// LOG.info("Directorio no creado");
					System.out.println("Directorio no creado");
				}
			}

			return dir.getAbsolutePath() + File.separator;
		}
		
		public static String NominaAFC() {
			File dir = new File(config.getProperty("NominaAFC"));
			// attempt to create the directory here
			boolean successful = dir.mkdirs();
			if (successful) {
				// LOG.info("Directorio creado --> {}",dir.getAbsolutePath());
				System.out.println("Directorio creado --> {}" + dir.getAbsolutePath());
			} else {
				if (dir.exists()) {
					System.out.println("Directorio existe --> {}" + dir.getAbsolutePath());
					// LOG.info("Directorio existe --> {}",dir.getAbsolutePath());
				} else {
					// LOG.info("Directorio no creado");
					System.out.println("Directorio no creado");
				}
			}

			return dir.getAbsolutePath() + File.separator;
		}
		
		
}

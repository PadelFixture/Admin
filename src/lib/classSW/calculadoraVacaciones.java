package lib.classSW;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class calculadoraVacaciones {


	String fechaInicial;
	int cantidadDias;
	String[] feriados;
	ArrayList<String> f;
	
	
	public calculadoraVacaciones() {
		this.fechaInicial="";
		this.cantidadDias=0;
		this.f=new ArrayList<String>();
		this.feriados=new String[0];
	}

	public String getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public int getCantidadDias() {
		return cantidadDias;
	}

	public void setCantidadDias(int cantidadDias) {
		this.cantidadDias = cantidadDias;
	}
	public void setFeriados(String[] holidays)
	{
		this.feriados=holidays;
	}
	public void getFeriados()
	{
		this.feriados=this.f.toArray(this.feriados);
		
	}
	public void addFeriado(String fecha)
	{
		this.f.add(fecha);
	}
	
	
	public String CalcularFechaFin() throws ParseException
	{	
		//algoritmo de calculo de día final
		int dias=1;
		String dt = this.fechaInicial;  // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar c = Calendar.getInstance();
		
		
		
		while(dias<=this.cantidadDias)
		{
			if(isWeek(dt)&& !isHoliday(dt,this.feriados))
			{
				if(dias==this.cantidadDias)
				{
					while(!isWeek(dt) || isHoliday(dt,this.feriados))
					{
						c.setTime(sdf.parse(dt));
						c.add(Calendar.DATE, 1);  // number of days to add
						dt = sdf.format(c.getTime());
						
					}
				}
				else
				{
					c.setTime(sdf.parse(dt));
					c.add(Calendar.DATE, 1);  // number of days to add
					dt = sdf.format(c.getTime());
					
				}
				dias++;	
			}
			else
			{
				c.setTime(sdf.parse(dt));
				c.add(Calendar.DATE, 1);  // number of days to add
				dt = sdf.format(c.getTime());		
				
			}		
		}
		
		return dt;
	}
	@SuppressWarnings("deprecation")
	public boolean isWeek(String date) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
		String dateInString = date;
		Date d = sdf.parse(dateInString);
	
		
		
		
		
		if(d.getDay()==1||d.getDay()==2||d.getDay()==3||d.getDay()==4||d.getDay()==5)
		{
			return true;
		}
		else{
			return false;
		}
		
		
	}
	@SuppressWarnings("deprecation")
	public boolean isFriday(String date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
		String dateInString = date;
		Date d = sdf.parse(dateInString);
	
		
		if(d.getDay()==5)
		{
			return true;
		}
		else{
			return false;
		}
	}
	public boolean isHoliday(String date,String[] holidays)
	{
	
		boolean isholiday=false;
		
		for(int i=0;i<holidays.length;i++)
		{
			if(date.equals(holidays[i]))
			{
				isholiday=true;
			}
		}
		return isholiday;		
	}
	
}

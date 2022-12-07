package wordCreator;

public class JsonStringParser {
	
	
	public JsonStringParser() {
		
	}
	public String[][] parser(String JSONFormaterString) 
{	
//	System.out.println("json: "+JSONFormaterString);	
		
		String[] a=JSONFormaterString.split(",");
		String[][] b=new String[a.length][2];		
		for(int i=0; i<a.length;i++) {
			
			if(i==0 || i==a.length-1) {
				
				a[i]=a[i].replace("{", "");
				a[i]=a[i].replace(" ", "");
				a[i]=a[i].replace("}","");
				a[i]=a[i].replace("'", "");
//				System.out.println(a[i]);
				b[i]=a[i].split(":");
			}
			else {
				a[i]=a[i].replace("'", "");
				b[i]=a[i].split(":");
			}
			
		}		
	return b;		
}
	
	
	

}

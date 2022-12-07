package lib.struc;

public class filterSql {

	public filterSql(String campo, String value) {
		super();
		this.campo = campo;
		this.value = value;
	}
	
	
	public filterSql() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String campo;
	private String value;

	public String getCampo() {
		return campo;
	}

	public void setCampo(String value) {
		this.campo = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String val) {
		this.value = val;
	}
}
package lib.classSW;

public class CalculoFiniquito {
	
	private String item;
	private Integer valorMes1;
	private Integer valorMes2;
	private Integer valorMes3;
	private Integer valorPromedio;
	
	public CalculoFiniquito (){
		this.valorMes1 = 0;
		this.valorMes2 = 0;
		this.valorMes3 = 0;
		this.valorPromedio = 0;
	}
	
	@Override
	public String toString() {
		return "CalculoFiniquito [item=" + item + ", valorMes1=" + valorMes1 + ", valorMes2=" + valorMes2
				+ ", valorMes3=" + valorMes3 + ", valorPromedio=" + valorPromedio + "]";
	}
	
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public Integer getValorMes1() {
		return valorMes1;
	}
	public void setValorMes1(Integer valorMes1) {
		this.valorMes1 = valorMes1;
	}
	public Integer getValorMes2() {
		return valorMes2;
	}
	public void setValorMes2(Integer valorMes2) {
		this.valorMes2 = valorMes2;
	}
	public Integer getValorMes3() {
		return valorMes3;
	}
	public void setValorMes3(Integer valorMes3) {
		this.valorMes3 = valorMes3;
	}
	public Integer getValorPromedio() {
		return valorPromedio;
	}
	public void setValorPromedio(Integer valorPromedio) {
		this.valorPromedio = valorPromedio;
	}
}

package lib.classSW;

import java.util.List;

public class MenuPerfiljson {
	
	
	public String text;
	public boolean checked;
	public Object state;
	public List<MenuPerfiljson> nodes;
	
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Object getState() {
		return state;
	}
	public void setState(Object v6) {
		this.state = v6;
	}

	public List<MenuPerfiljson> getNodes() {
		return nodes;
	}
	public void setNodes(List<MenuPerfiljson> nodes) {
		this.nodes = nodes;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	 
	
}

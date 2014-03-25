package transformador.formatoFramenet;

import org.w3c.dom.Element;

public class Label {
	
	int 	start;
	int 	end;
	String	name;
	String itype; //a veces aparece cuando no esta el FE explicito. INI, CNI, DNI
	
	public Label(Element label) {
		if(label.hasAttribute("itype")){
			this.itype=label.getAttribute("itype");
		}else{
			this.start = Integer.valueOf(label.getAttribute("start"));
			this.end = Integer.valueOf(label.getAttribute("end"));
		}
		this.name = label.getAttribute("name");
	}

	public String getName() {
		return name;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}
}

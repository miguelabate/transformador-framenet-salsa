package transformador.formatoFramenet;

import org.w3c.dom.Element;

public class Label {
	
	int 	start;
	int 	end;
	String	name;
	
	public Label(Element label) {
		this.start = Integer.valueOf(label.getAttribute("start"));
		this.end = Integer.valueOf(label.getAttribute("end"));
		this.name = label.getAttribute("name");
	}

	public String getName() {
		return name;
	}
}

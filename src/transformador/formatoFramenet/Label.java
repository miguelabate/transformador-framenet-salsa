package transformador.formatoFramenet;

import org.w3c.dom.Element;

public class Label {
	
	int 	start;//son el start y end explicito que aparece en framenet. El end siempre termina uno antes.
	int 	end;
	String	name;
	String itype; //a veces aparece cuando no esta el FE explicito. INI, CNI, DNI
	Frame	framePadre;//el frame al que pertenece el label
	
	public Label(Element label, Frame frame) {
		if(label.hasAttribute("itype")){
			this.itype=label.getAttribute("itype");
		}else{
			this.start = Integer.valueOf(label.getAttribute("start"));
			this.end = Integer.valueOf(label.getAttribute("end"));
		}
		this.name = label.getAttribute("name");
		this.framePadre=frame;
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

	public Frame getFramePadre() {
		return framePadre;
	}
}

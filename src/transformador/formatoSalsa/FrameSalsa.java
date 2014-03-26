package transformador.formatoSalsa;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class FrameSalsa {
	private String name;
	private String id;
	private ArrayList<FeSalsa> frameElementList = new ArrayList<FeSalsa>();
	private TargetSalsa target;
	
	public FrameSalsa(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}

	public Node obtenerNodo(Document doc){
		Element frameElement = doc.createElement("frame");
		frameElement.setAttribute("id", this.id);
		frameElement.setAttribute("name", this.name);
		
		for(FeSalsa fe:frameElementList)
			frameElement.appendChild(fe.obtenerNodo(doc));
		
		frameElement.appendChild(target.obtenerNodo(doc));
		return frameElement;
	}
	
	public void agregarFrameElement(FeSalsa fe){
		this.frameElementList.add(fe);
	}

	public void setTarget(TargetSalsa target) {
		this.target = target;
	}
	
}

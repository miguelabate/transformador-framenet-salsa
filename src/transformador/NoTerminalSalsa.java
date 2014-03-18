package transformador;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class NoTerminalSalsa implements ReferenciablePorUnEdgeSalsa{
	
	private String id;
	private String cat="";
	private String head="";
	private ArrayList<EdgeSalsa> listaEdgesSalsa=new ArrayList<EdgeSalsa>();
	
	public NoTerminalSalsa(String id, String cat, String head) {
		super();
		this.id = id;
		this.cat = cat;
		this.head = head;
	}
	
	public NoTerminalSalsa(String id, String cat, String head,
			ArrayList<EdgeSalsa> listaEdgesSalsa) {
		super();
		this.id = id;
		this.cat = cat;
		this.head = head;
		this.listaEdgesSalsa = listaEdgesSalsa;
	}

	public Node obtenerNodo(Document doc) {
		Element ntElement = doc.createElement("nt");
		ntElement.setAttribute("id", id);
		ntElement.setAttribute("head", head);
		ntElement.setAttribute("cat", cat);
		
		for(EdgeSalsa edge:listaEdgesSalsa)
			ntElement.appendChild(edge.obtenerNodo(doc));
		
		return ntElement;
	}

	public void agregarEdge(EdgeSalsa edge){
		this.listaEdgesSalsa.add(edge);
	}
	
	@Override
	public String getId() {
		return id;
	}
}

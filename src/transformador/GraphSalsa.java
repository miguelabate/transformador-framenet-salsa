package transformador;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class GraphSalsa {
	private String root;//id de referncia 
	private OracionSalsa oracion;//necesito la referncia la oracion para reconstruirla cuando agrego los terminos 
	private ArrayList<TerminalSalsa> listaNodosTerminales=new ArrayList<TerminalSalsa>();
	private ArrayList<NoTerminalSalsa> listaNodosNoTerminales=new ArrayList<NoTerminalSalsa>();
	
	public GraphSalsa() {
		super();
	}
	
	public Node obtenerNodo(Document doc){
		Element graphElement = doc.createElement("graph");
		graphElement.setAttribute("root", root);
		
		Element terminals = doc.createElement("terminals");
		for(TerminalSalsa t:listaNodosTerminales)
			terminals.appendChild(t.obtenerNodo(doc));
		graphElement.appendChild(terminals);
		
		Element nonTerminals = doc.createElement("nonterminals");
		for(NoTerminalSalsa nt:listaNodosNoTerminales)
			nonTerminals.appendChild(nt.obtenerNodo(doc));
		graphElement.appendChild(nonTerminals);
		
		return graphElement;
	}
	
	public void agregarNodoTerminal(TerminalSalsa t){
		this.listaNodosTerminales.add(t);
		this.oracion.actualizarMapa(t.getWord(), t.getId());
	}
	
	public void agregarNodoNoTerminal(NoTerminalSalsa nt){
		this.listaNodosNoTerminales.add(nt);
	}

	public OracionSalsa getOracion() {
		return oracion;
	}

	public void setOracion(OracionSalsa oracion) {
		this.oracion = oracion;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}
}

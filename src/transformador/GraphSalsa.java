package transformador;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class GraphSalsa {
	private String root;//id
	private ArrayList<TerminalSalsa> listaNodosTerminales=new ArrayList<TerminalSalsa>();;
	private ArrayList<NoTerminalSalsa> listaNodosNoTerminales=new ArrayList<NoTerminalSalsa>();
	
	public GraphSalsa(String root,
			ArrayList<TerminalSalsa> listaNodosTerminales,
			ArrayList<NoTerminalSalsa> listaNodosNoTerminales) {
		super();
		this.root = root;
		this.listaNodosTerminales = listaNodosTerminales;
		this.listaNodosNoTerminales = listaNodosNoTerminales;
	}

	public GraphSalsa(String root) {
		super();
		this.root = root;
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
	}
	
	public void agregarNodoNoTerminal(NoTerminalSalsa nt){
		this.listaNodosNoTerminales.add(nt);
	}
}

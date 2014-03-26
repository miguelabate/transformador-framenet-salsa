package transformador.formatoSalsa;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import transformador.ReferenciablePorUnEdgeSalsa;

public class TerminalSalsa implements ReferenciablePorUnEdgeSalsa{

	private String id;
	private String lemma="";
	private String pos="";
	private String word;
	
	
	public TerminalSalsa(String id, String lemma, String pos, String word) {
		super();
		this.id = id;
		this.lemma = lemma;
		this.pos = pos;
		this.word = word;
	}


	public Node obtenerNodo(Document doc) {
		Element tElement = doc.createElement("t");
		tElement.setAttribute("id", id);
		tElement.setAttribute("lemma", lemma);
		tElement.setAttribute("pos", pos);
		tElement.setAttribute("word", word);
		
		return tElement;
	}


	@Override
	public String getId() {
		return id;
	}


	public String getWord() {
		return word;
	}


	@Override
	public String toString() {
		return "TerminalSalsa [id=" + id + ", word=" + word + "]";
	}
}

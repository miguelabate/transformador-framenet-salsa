package transformador;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class OracionSalsa {

	private String id;
	private GraphSalsa graphSalsa;
	private SemSalsa semSalsa;
	
	private String oracion="";
	private HashMap<String, String> mapaIdPalabra = new HashMap<String, String>();//pruebaTimeMlSem._0_0 -> Palabra
	private HashMap<String, String> mapaIndiceId = new HashMap<String, String>();//12 -> pruebaTimeMlSem._0_0
	
	public OracionSalsa(String id, GraphSalsa graphSalsa, SemSalsa semSalsa) {
		super();
		this.id = id;
		this.graphSalsa = graphSalsa;
		this.semSalsa = semSalsa;
	}
	
	public Node obtenerNodo(Document doc){
		Element sElement = doc.createElement("s");
		sElement.setAttribute("id", id);
		sElement.appendChild(graphSalsa.obtenerNodo(doc));
		sElement.appendChild(semSalsa.obtenerNodo(doc));
		
		return sElement;
	}
	
	public String getOracion() {
		return oracion;
	}
	
	public String obtenerPalabraIndice(int start){
		String clave=String.valueOf(start);
		return this.mapaIdPalabra.get(this.mapaIndiceId.get(clave));
	}
	
	/**
	 * Obtener ID pruebaTimeMlSem._0_0
	 * @param start
	 * @return
	 */
	public String obtenerIdSegunIndice(int start){
		String clave=String.valueOf(start);
		return this.mapaIndiceId.get(clave);
	}
	
	public void actualizarMapa(String palabra, String id){
		//completo mapas
		this.mapaIndiceId.put(String.valueOf((oracion.length())),id);
		this.oracion+=palabra+" ";
		palabra=palabra.replaceAll("'"," '");
		this.mapaIdPalabra.put(id, palabra+" ");
	}
}

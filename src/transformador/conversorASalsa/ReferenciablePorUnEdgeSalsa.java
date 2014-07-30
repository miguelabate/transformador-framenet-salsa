package transformador.conversorASalsa;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public interface ReferenciablePorUnEdgeSalsa {

	public String getId();

	public Node obtenerNodo(Document doc);
}

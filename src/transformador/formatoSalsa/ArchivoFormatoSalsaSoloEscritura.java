package transformador.formatoSalsa;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class ArchivoFormatoSalsaSoloEscritura {

	private Document doc;
	
	public ArchivoFormatoSalsaSoloEscritura() {
		super();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(new File("src/transformador/formatoSalsa/baseArchivoSalsa.xml"));
			doc.getDocumentElement().normalize();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void imprimirArbolXML(){
		NodeList nodosRaiz = doc.getDocumentElement().getChildNodes();
		imprimirRecursivo(nodosRaiz);
	}
	
	private void imprimirRecursivo(NodeList nodeList){
		for(int i = 0; i < nodeList.getLength(); i++) {
			if(nodeList.item(i).getNodeName().equals("frame"))System.out.println(((Element)nodeList.item(i)).getAttribute("name"));
			if(nodeList.item(i).hasChildNodes()) 
				imprimirRecursivo(nodeList.item(i).getChildNodes());
			
			
		}
	}
	
	public void guardarAArchivo(String path) throws TransformerException{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(path));
		transformer.transform(source, result);
	}

	public Document getDoc() {
		return doc;
	}
	
	public void agregarOracion(OracionSalsa s){
		Element bodyElement = (Element) doc.getElementsByTagName("body").item(0);
		bodyElement.appendChild(s.obtenerNodo(doc));
	}
}

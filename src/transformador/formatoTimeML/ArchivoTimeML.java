package transformador.formatoTimeML;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class ArchivoTimeML {

	public ArchivoTimeML(String pathArchivo) {
		cargarDatos(pathArchivo);
	}

	private void cargarDatos(String pathArchivo) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(pathArchivo);
			doc.getDocumentElement().normalize();
			//frame
			Element raiz = doc.getDocumentElement();
//			DefinicionFrame defFrame = new DefinicionFrame(frame.getAttribute("ID"), frame.getAttribute("name"));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

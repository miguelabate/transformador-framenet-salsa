package transformador.formatoFramenet;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import transformador.ArchivoFormatoSalsa;
import transformador.EdgeSalsa;
import transformador.FeNodeSalsa;
import transformador.FeSalsa;
import transformador.FrameSalsa;
import transformador.GraphSalsa;
import transformador.NoTerminalSalsa;
import transformador.OracionSalsa;
import transformador.SemSalsa;
import transformador.TargetSalsa;
import transformador.TerminalSalsa;


public class ArchivoFormatoFramenetFullText {
	ArrayList<Oracion> listaDocumentos = new ArrayList<Oracion>();
	
	public ArchivoFormatoFramenetFullText(String pathArchivoXML) {
		super();
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(pathArchivoXML));
			doc.getDocumentElement().normalize();
			NodeList oraciones = doc.getDocumentElement().getElementsByTagName("sentence");
			for(int i=0;i<oraciones.getLength();i++){
				this.listaDocumentos.add(new Oracion((Element)oraciones.item(i)));
				
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Oracion> getListaOraciones() {
		return listaDocumentos;
	}


}

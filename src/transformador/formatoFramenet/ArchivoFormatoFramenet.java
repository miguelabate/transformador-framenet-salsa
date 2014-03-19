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


public class ArchivoFormatoFramenet {
	ArrayList<Documento> listaDocumentos = new ArrayList<Documento>();
	
	public ArchivoFormatoFramenet(String pathArchivoXML) {
		super();
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(pathArchivoXML));
			doc.getDocumentElement().normalize();
			NodeList documentos = doc.getDocumentElement().getElementsByTagName("documents");
			for(int i=0;i<documentos.getLength();i++){
				this.listaDocumentos.add(new Documento((Element)documentos.item(i)));
				
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Documento> getListaDocumentos() {
		return listaDocumentos;
	}


}

package transformador.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import transformador.formatoFramenet.Documento;
import transformador.formatoTimeML.ArchivoTimeML;

/**
 * leeo archivos con anotaciones extras de timel. la que uso es <s></s> para sacar las oraciones.
 * @author miguel
 *
 */
public class PrincipalConversorTimeMLExtraTextoPlano {

	public static void main(String[] args) {
		String outputDir=args[1];
		File directorioTimeML=new File(args[0]);
		File[] listarchivosAnotadosTimeML = directorioTimeML.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith("tml");
			}
		});

		for(File archivoTimeML:listarchivosAnotadosTimeML){
				
				try {
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					DocumentBuilder db = dbf.newDocumentBuilder();
					Document doc = db.parse(archivoTimeML);
					doc.getDocumentElement().normalize();
					NodeList sentence = doc.getElementsByTagName("s");
					StringBuilder resultado=new StringBuilder();
					for(int i=0;i<sentence.getLength();i++){
						resultado.append(((Element)sentence.item(i)).getTextContent().replaceAll("\n", ""));
						resultado.append("\n");
					}
					PrintWriter out = new PrintWriter(outputDir+archivoTimeML.getName()+".txt");
					out.print(resultado.toString());
					out.close();
					
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
		}
	}

}

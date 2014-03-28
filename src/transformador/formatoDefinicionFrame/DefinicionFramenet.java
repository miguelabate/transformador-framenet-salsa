package transformador.formatoDefinicionFrame;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DefinicionFramenet {

	private static String PATH_FRAMENET_DATA="/home/miguel/fndata-1.5";
	private static String PATH_FRAMES = PATH_FRAMENET_DATA+"/frame";
	private static String PATH_RELACIONES = PATH_FRAMENET_DATA+"/frRelation.xml";
	
	private Hashtable<String, DefinicionFrame> tableFrame = new Hashtable<String, DefinicionFrame>();
	private Hashtable<String, DefinicionFrameElement> tableFrameElement = new Hashtable<String, DefinicionFrameElement>();
	private Hashtable<String, DefinicionRelationType> tableRelationType = new Hashtable<String, DefinicionRelationType>();
	private Hashtable<Long, DefinicionFrameRelation> tableFrameRelation = new Hashtable<Long, DefinicionFrameRelation>();
	private Hashtable<Long, DefinicionFERelation> tableFERelation = new Hashtable<Long, DefinicionFERelation>();
	
	public DefinicionFramenet(){
		cargarDatos();
	}

	private void cargarDatos() {
		File directorioFrames=new File(PATH_FRAMES);
		File[] listaFrames = directorioFrames.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith("xml");
			}
		});
		
		for(File unArchivoFrame:listaFrames){
			cargarFramesyFE(unArchivoFrame);
		}
		
		cargarRelaciones(new File(PATH_RELACIONES));
		
	}

	private void cargarFramesyFE(File archivoXmlFrame) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(archivoXmlFrame);
			doc.getDocumentElement().normalize();
			//frame
			Element frame = doc.getDocumentElement();
			DefinicionFrame defFrame = new DefinicionFrame(frame.getAttribute("ID"), frame.getAttribute("name"));
			tableFrame.put(defFrame.getName(),defFrame);
			//frame elements
			ArrayList<DefinicionFrameElement> frameElements = new ArrayList<DefinicionFrameElement>();
			NodeList listaFE = frame.getElementsByTagName("FE");
			for(int i=0;i<listaFE.getLength();i++){
				Element feXml=(Element)listaFE.item(i);
				DefinicionFrameElement definicionFE = new DefinicionFrameElement(feXml.getAttribute("ID"),feXml.getAttribute("abbrev"),feXml.getAttribute("name"), feXml.getAttribute("coreType"),defFrame);
				this.tableFrameElement.put(defFrame.getName()+"-"+definicionFE.getName(), definicionFE);
				frameElements.add(definicionFE);
			}
			defFrame.setFrameElements(frameElements);//le seteo al frame la lista de FE
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void cargarRelaciones(File file) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			//frame elements
			NodeList listaRelType = doc.getDocumentElement().getElementsByTagName("frameRelationType");
			for(int i=0;i<listaRelType.getLength();i++){
				//Relation Type
				Element relTypeXml=(Element)listaRelType.item(i);
				DefinicionRelationType definicionRelType = new DefinicionRelationType(relTypeXml.getAttribute("ID"),relTypeXml.getAttribute("name"),relTypeXml.getAttribute("superFrameName"), relTypeXml.getAttribute("subFrameName"));
				this.tableRelationType.put(definicionRelType.getName(), definicionRelType);
				//frame relation
				NodeList listaFrameRel = relTypeXml.getElementsByTagName("frameRelation");
				for(int j=0;j<listaFrameRel.getLength();j++){
					Element frameRelXml=(Element)listaFrameRel.item(j);
					DefinicionFrameRelation definicionFrameRelation = new DefinicionFrameRelation(frameRelXml.getAttribute("ID"),definicionRelType,this.tableFrame.get(frameRelXml.getAttribute("subFrameName")), this.tableFrame.get(frameRelXml.getAttribute("superFrameName")));
					this.tableFrameRelation.put(definicionFrameRelation.getID(), definicionFrameRelation);
					
					//frame element relation
					NodeList listaFERel = frameRelXml.getElementsByTagName("FERelation");
					for(int k=0;k<listaFERel.getLength();k++){
						Element FERelXml=(Element)listaFERel.item(k);
						DefinicionFERelation definicionFERelation = new DefinicionFERelation(FERelXml.getAttribute("ID"),definicionFrameRelation,this.tableFrameElement.get(definicionFrameRelation.getSubFrame().getName()+"-"+FERelXml.getAttribute("subFEName")), this.tableFrameElement.get(definicionFrameRelation.getSuperFrame().getName()+"-"+FERelXml.getAttribute("superFEName")));
						this.tableFERelation.put(definicionFERelation.getID(), definicionFERelation);
						
					}
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	////****Metodos especiales de consulta*****////
	
	public Integer getCantidadDeFrames(){
		return this.tableFrame.size();
	}
	
	public Integer getCantidadDeFrameElements(){
		return this.tableFrameElement.size();
	}
	
	public DefinicionFrame getFrame(String nombreFrame){
		return this.tableFrame.get(nombreFrame);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<DefinicionFrame> findFramesLike(final String nombreFrame){
		return	CollectionUtils.select(this.tableFrame.values(), new Predicate() {
			
			@Override
			public boolean evaluate(final Object arg0) {
				final DefinicionFrame frame = (DefinicionFrame) arg0;
				return frame.getName().contains(nombreFrame);
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public Collection<DefinicionFrameElement> findFrameElementsLike(final String nombreFrameElement){
		return	CollectionUtils.select(this.tableFrameElement.values(), new Predicate() {
			
			@Override
			public boolean evaluate(final Object arg0) {
				final DefinicionFrameElement frameElement = (DefinicionFrameElement) arg0;
				return frameElement.getName().contains(nombreFrameElement);
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public Collection<DefinicionFrame> getFramesHijos(final String nombreFramePadre){
		return CollectionUtils.collect(
		CollectionUtils.select(this.tableFrameRelation.values(), new Predicate() {
			
			@Override
			public boolean evaluate(final Object arg0) {
				final DefinicionFrameRelation frameRel = (DefinicionFrameRelation) arg0;
				return frameRel.getType().getName().equals("Inheritance")&&frameRel.getSuperFrame().getName().equals(nombreFramePadre);
			}
		}),
		new Transformer() {
			
			@Override
			public Object transform(final Object arg0) {
				final DefinicionFrameRelation frameRel= (DefinicionFrameRelation) arg0;
				return frameRel.getSubFrame();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public Collection<DefinicionFrame> getFramesHijosTotal(final String nombreFramePadre){
		ArrayList<DefinicionFrame> resultado = new ArrayList<DefinicionFrame>();
		for(DefinicionFrame frameHijo:getFramesHijos(nombreFramePadre)){
			resultado.add(frameHijo);
			resultado.addAll(getFramesHijosTotal(frameHijo.getName()));
		}
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<DefinicionFrame> getFramesPreceden(final String nombreFrameEarlier){
		return CollectionUtils.collect(
		CollectionUtils.select(this.tableFrameRelation.values(), new Predicate() {
			
			@Override
			public boolean evaluate(final Object arg0) {
				final DefinicionFrameRelation frameRel = (DefinicionFrameRelation) arg0;
				return frameRel.getType().getName().equals("Precedes")&&frameRel.getSuperFrame().getName().equals(nombreFrameEarlier);
			}
		}),
		new Transformer() {
			
			@Override
			public Object transform(final Object arg0) {
				final DefinicionFrameRelation frameRel= (DefinicionFrameRelation) arg0;
				return frameRel.getSubFrame();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public Collection<DefinicionFrameRelation> getRelacionesPrecedes(){
		return	CollectionUtils.select(this.tableFrameRelation.values(), new Predicate() {
			
			@Override
			public boolean evaluate(final Object arg0) {
				final DefinicionFrameRelation frameRel = (DefinicionFrameRelation) arg0;
				return frameRel.getType().getName().equals("Precedes");
			}
		});
	}
}

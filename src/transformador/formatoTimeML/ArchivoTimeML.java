package transformador.formatoTimeML;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import transformador.ClaveDeReferenciable;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class ArchivoTimeML {

	private String textoPlano="";
//	private HashMap<String, TextoSinMarcarTimeML> textoSinMarcarTabla= new HashMap<String, TextoSinMarcarTimeML>();
	private HashMap<String, Timex3> timex3Tabla= new HashMap<String, Timex3>();
	private HashMap<String, Signal> signalTabla= new HashMap<String, Signal>();
	private HashMap<String, Event> eventTabla= new HashMap<String, Event>();
	private HashMap<String, MakeInstance> makeinstanceTabla= new HashMap<String, MakeInstance>();
	private HashMap<String, TLink> tlinkTabla= new HashMap<String, TLink>();
	private HashMap<String, SLink> slinkTabla= new HashMap<String, SLink>();
	private HashMap<String, ALink> alinkTabla= new HashMap<String, ALink>();
	private HashMap<ClaveDeReferenciable, ConsumidorTexto> tablaReferenciables = new HashMap<ClaveDeReferenciable, ConsumidorTexto>();
	private ArrayList<ConsumidorTexto> listaConsumidores = new ArrayList<ConsumidorTexto>();//guardo los consumidores de texto para luego corregir sus index
	
	public ArchivoTimeML(String pathArchivo) {
		cargarDatos(pathArchivo);
	}

	private void cargarDatos(String pathArchivo) {
		StringBuilder textoBufferPlano=new StringBuilder();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
//			Document doc = db.parse(new ByteArrayInputStream(convertirAString(pathArchivo).getBytes()));
			Document doc = db.parse(pathArchivo);
			doc.getDocumentElement().normalize();
			Node nodoOrigen=((NodeList)doc.getDocumentElement().getChildNodes()).item(0);
			while(nodoOrigen!=null){
				switch(nodoOrigen.getNodeType()){
					case Node.TEXT_NODE:
					{
						agregarElementoTextoSinMarcar(nodoOrigen.getNodeValue(), textoBufferPlano.length());
						textoBufferPlano.append(nodoOrigen.getNodeValue());
						break;
					}
					case Node.ELEMENT_NODE:
					{
						String tipoElemento=nodoOrigen.getNodeName();
						if(tipoElemento.equals("TIMEX3"))agregarElementoTimex3(nodoOrigen,textoBufferPlano.length());//ok
						if(tipoElemento.equals("EVENT"))agregarElementoEvent(nodoOrigen,textoBufferPlano.length());//ok
						if(tipoElemento.equals("SIGNAL"))agregarElementoSignal(nodoOrigen,textoBufferPlano.length());//ok
						if(tipoElemento.equals("MAKEINSTANCE"))agregarElementoMakeInstance(nodoOrigen);//ok
						if(tipoElemento.equals("TLINK"))agregarElementoTLink(nodoOrigen);//ok
						if(tipoElemento.equals("SLINK"))agregarElementoSLink(nodoOrigen);
						if(tipoElemento.equals("ALINK"))agregarElementoALink(nodoOrigen);
						textoBufferPlano.append(nodoOrigen.getTextContent());
						break;
					}
				}
				nodoOrigen=nodoOrigen.getNextSibling();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.textoPlano=tokenizarCorrigiendoIndex(textoBufferPlano.toString(),this.listaConsumidores);
		
		//cargo la tabla para poder traer los referenciables
		for(ConsumidorTexto unConsumidor:this.listaConsumidores){
			this.tablaReferenciables.put(new ClaveDeReferenciable(unConsumidor.getStart(), unConsumidor.getEnd()), unConsumidor);
		}
	}

	@SuppressWarnings("unchecked")
	public static String tokenizarCorrigiendoIndex(String textContent, ArrayList<ConsumidorTexto> listaConsumidores2) {
		String result="";
		ArrayList<ConsumidorTexto> resultadoConsumidoresMod=new ArrayList<ConsumidorTexto>();
		PTBTokenizer ptbt = new PTBTokenizer(new StringReader(textContent),
		          new CoreLabelTokenFactory(), "");
		  for (CoreLabel label; ptbt.hasNext(); ) {
		    label = (CoreLabel) ptbt.next();
		    actualizarIndices(label,listaConsumidores2,resultadoConsumidoresMod,result.length());
		    result+=label.toString()+" ";
		  }
//		  System.out.println("En la tokinazion/reindexacion se perdieron: "+listaConsumidores2.size()+" elementos timeML");
		  listaConsumidores2.clear();
		  listaConsumidores2.addAll(resultadoConsumidoresMod);//ver porque quedan algunos sin modificar
		  return result;
	}
	
	private static void actualizarIndices(CoreLabel label, ArrayList<ConsumidorTexto> listaConsumidores2,ArrayList<ConsumidorTexto> resultadoConsumidoresMod,Integer indiceActual) {
		Integer offset=-1;
		for(ConsumidorTexto unConsumidor:listaConsumidores2){
			if(unConsumidor.getStart().intValue()==label.beginPosition()){
				offset=indiceActual-unConsumidor.getStart();
				unConsumidor.setStart(unConsumidor.getStart()+offset);
				unConsumidor.setEnd(unConsumidor.getEnd()+offset);
				resultadoConsumidoresMod.add(unConsumidor);
			}
		}
		listaConsumidores2.removeAll(resultadoConsumidoresMod);
		
	}

	/**
	 * Metodo que normaliza los textos para que queden como en framenet. "palabra," -> "palabra ,"
	 * @param textContent
	 * @return
	 */
	public static String tokenizar(String textContent) {
		String result="";
		boolean espacioAlFinal=false,espacioAlPrincipio=false;
//		if(!textContent.isEmpty()){
//			espacioAlFinal=textContent.substring(textContent.length()-1).equals(" ");
//			espacioAlPrincipio= textContent.substring(0,1).equals(" ");
//		}
		PTBTokenizer ptbt = new PTBTokenizer(new StringReader(textContent),
		          new CoreLabelTokenFactory(), "");
		  for (CoreLabel label; ptbt.hasNext(); ) {
		    label = (CoreLabel) ptbt.next();
//		    System.out.println(label);
		    result+=label.toString()+" ";
		  }
		  return result;
			
	}

	private void agregarElementoALink(Node nodoOrigen) {
		ALink alink = new ALink((Element)nodoOrigen, this.makeinstanceTabla, this.signalTabla);
		alinkTabla.put(alink.getLid(), alink);
		
	}

	private void agregarElementoSLink(Node nodoOrigen) {
		SLink slink = new SLink((Element)nodoOrigen, this.makeinstanceTabla, this.signalTabla);
		slinkTabla.put(slink.getLid(), slink);
	}

	private void agregarElementoTLink(Node nodoOrigen) {
		TLink tlink = new TLink((Element)nodoOrigen, this.makeinstanceTabla, this.timex3Tabla, this.signalTabla);
		tlinkTabla.put(tlink.getLid(), tlink);
	}

	private void agregarElementoMakeInstance(Node nodoOrigen) {
		MakeInstance makeinstance =  new MakeInstance((Element) nodoOrigen, this.eventTabla, this.signalTabla);
		this.makeinstanceTabla.put(makeinstance.getEiid(), makeinstance);
		
	}

	private void agregarElementoSignal(Node nodoOrigen, Integer index) {
		Signal signal = new Signal((Element)nodoOrigen,index);
		this.signalTabla.put(signal.getSid(), signal);
//		this.tablaReferenciables.put(new ClaveDeReferenciable(signal.getStart(), signal.getEnd()), signal);
		this.listaConsumidores.add(signal);
	}

	private void agregarElementoEvent(Node nodoOrigen, Integer index) {
		Event evento = new Event((Element) nodoOrigen,index);
		eventTabla.put(evento.getEid(), evento);
//		this.tablaReferenciables.put(new ClaveDeReferenciable(evento.getStart(), evento.getEnd()), evento);
		this.listaConsumidores.add(evento);
	}

	private void agregarElementoTimex3(Node nodoOrigen, Integer index) {
		Timex3 timex=new Timex3((Element)nodoOrigen,this.timex3Tabla,index);
		this.timex3Tabla.put(timex.getTid(), timex);
//		this.tablaReferenciables.put(new ClaveDeReferenciable(timex.getStart(), timex.getEnd()), timex);
		this.listaConsumidores.add(timex);
	}

	private void agregarElementoTextoSinMarcar(String contenido, Integer index) {
		TextoSinMarcarTimeML textoSinMarc=new TextoSinMarcarTimeML(contenido,index);
//		this.timex3Tabla.put(timex.getTid(), timex);
//		this.tablaReferenciables.put(new ClaveDeReferenciable(timex.getStart(), timex.getEnd()), timex);
//		this.listaConsumidores.add(textoSinMarc);
	}
	
	public HashMap<String, Timex3> getTimex3Tabla() {
		return timex3Tabla;
	}

	public String getTextoPlano() {
		return textoPlano;
	}

	public HashMap<String, Signal> getSignalTabla() {
		return signalTabla;
	}

	public HashMap<String, Event> getEventTabla() {
		return eventTabla;
	}

	public HashMap<String, MakeInstance> getMakeinstanceTabla() {
		return makeinstanceTabla;
	}

	public HashMap<String, TLink> getTlinkTabla() {
		return tlinkTabla;
	}

	public HashMap<String, SLink> getSlinkTabla() {
		return slinkTabla;
	}

	public HashMap<String, ALink> getAlinkTabla() {
		return alinkTabla;
	}

	public void guardarComoArchivoDeTextoPlano(String path) throws FileNotFoundException{
		PrintWriter out = new PrintWriter(path);
		out.print(this.textoPlano);
		out.close();
		
	}
	
	/**
	 * Devuelve el consumidor de texto coincidente en ese rango. Puede ser Event, Timex3 o Signal. O null.
	 * @param comienzo
	 * @param fin
	 * @return
	 */
	public ConsumidorTexto obtenerConsumidorDeTextoEn(Integer comienzo, Integer fin){
		ClaveDeReferenciable clave=new ClaveDeReferenciable(comienzo,fin);
		return this.tablaReferenciables.get(clave);
	}
}

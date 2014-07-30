package transformador.formatoTimeML;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import transformador.common.ClaveDeReferenciable;

public class ParserArchivoTimeML {
	private ArchivoTimeML archivoTimeML;
	private ArrayList<ConsumidorTexto> listaConsumidores = new ArrayList<ConsumidorTexto>();//guardo los consumidores de texto para luego corregir sus index
	
	public ParserArchivoTimeML(ArchivoTimeML archivoTimeML) {
		super();
		this.archivoTimeML = archivoTimeML;
	}

	public void cargarDatos(String pathArchivo) {
			StringBuilder textoBufferPlano=new StringBuilder();
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
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
			archivoTimeML.setTextoPlano(tokenizarCorrigiendoIndex(textoBufferPlano.toString(),this.listaConsumidores));
			
			//cargo la tabla para poder traer los referenciables
			for(ConsumidorTexto unConsumidor:this.listaConsumidores){
				archivoTimeML.getTablaReferenciables().put(new ClaveDeReferenciable(unConsumidor.getStart(), unConsumidor.getEnd()), unConsumidor);
			}
		}

	@SuppressWarnings("unchecked")
	public static String tokenizarCorrigiendoIndex(String textContent, ArrayList<ConsumidorTexto> listaConsumidores2) {
//		String result="";
//		ArrayList<ConsumidorTexto> resultadoConsumidoresMod=new ArrayList<ConsumidorTexto>();
//		PTBTokenizer ptbt = new PTBTokenizer(new StringReader(textContent),
//		          new CoreLabelTokenFactory(), "");
//		  for (CoreLabel label; ptbt.hasNext(); ) {
//		    label = (CoreLabel) ptbt.next();
//		    actualizarIndices(label,listaConsumidores2,resultadoConsumidoresMod,result.length());
//		    result+=label.toString()+" ";
//		  }
//		  System.out.println("En la tokinazion/reindexacion se perdieron: "+listaConsumidores2.size()+" elementos timeML");
//		  listaConsumidores2.clear();
//		  listaConsumidores2.addAll(resultadoConsumidoresMod);//ver porque quedan algunos sin modificar
//		  return result;
		String result="";
		final ArrayList<CoreLabel> corelabels=new ArrayList<CoreLabel>();
		PTBTokenizer ptbt = new PTBTokenizer(new StringReader(textContent),
		        new CoreLabelTokenFactory(), "");
		for (CoreLabel label; ptbt.hasNext(); ) {
		  label = (CoreLabel) ptbt.next();
		  corelabels.add(label);
		}
		
		CollectionUtils.forAllDo(listaConsumidores2, new Closure() {
			
			@Override
			public void execute(Object arg0) {
				ConsumidorTexto consum= (ConsumidorTexto)arg0;
				final int start=consum.getStart().intValue();
				final int end=consum.getEnd().intValue();
				Collection<CoreLabel>  coresIncluidos=CollectionUtils.select(corelabels, new Predicate() {
					
					@Override
					public boolean evaluate(Object arg0) {
						return start<=((CoreLabel)arg0).beginPosition()&&
								end>=((CoreLabel)arg0).endPosition();
					}
				});
				//mdoifico internamente al cosnumidor de texto
				String contenidoMod="";
				for(CoreLabel unCore:coresIncluidos){
					contenidoMod+=unCore.toString()+" ";
				}
				if(!contenidoMod.isEmpty()){
					consum.setContenido(contenidoMod.substring(0,contenidoMod.length()-1));
					consum.setEnd(consum.getStart()+consum.getContenido().length());
				}
			}
		});
		
		  ////////////debug
		  final ArrayList<ConsumidorTexto> listaConsumidoresInmut=new ArrayList<ConsumidorTexto>();
		  listaConsumidoresInmut.addAll(listaConsumidores2);
		  
		  final ArrayList<CoreLabel> corelabels2=new ArrayList<CoreLabel>();
			PTBTokenizer ptbt3 = new PTBTokenizer(new StringReader(textContent),
			        new CoreLabelTokenFactory(), "");
			for (CoreLabel label; ptbt3.hasNext(); ) {
			  label = (CoreLabel) ptbt3.next();
			  corelabels2.add(label);
			}
			
			CollectionUtils.forAllDo(corelabels2, new Closure() {
				
				@Override
				public void execute(Object arg0) {
					CoreLabel corel= (CoreLabel)arg0;
					final int start=corel.beginPosition();
					final int end=corel.endPosition();
					
					Collection<ConsumidorTexto>  consumidoresIncluidos=CollectionUtils.select(listaConsumidoresInmut, new Predicate() {
						
						@Override
						public boolean evaluate(Object arg0) {
							return start<=((ConsumidorTexto)arg0).getStart().intValue()&&
									end>=((ConsumidorTexto)arg0).getEnd().intValue();
						}
					});
					if(consumidoresIncluidos.size()>1){
						throw new RuntimeException("ERROR: no puede haber mas de un consumidor dentro del core");
					}
					if(consumidoresIncluidos.size()==0) return;
					ConsumidorTexto elConsumidor=consumidoresIncluidos.iterator().next();
					elConsumidor.setStart(start);
					elConsumidor.setEnd(end);
				}
			});
		  //////////////
		
		//el global
		ArrayList<ConsumidorTexto> resultadoConsumidoresMod=new ArrayList<ConsumidorTexto>();
		PTBTokenizer ptbt2 = new PTBTokenizer(new StringReader(textContent),
		          new CoreLabelTokenFactory(), "");
		  for (CoreLabel label; ptbt2.hasNext(); ) {
		    label = (CoreLabel) ptbt2.next();
		    actualizarIndices(label,listaConsumidores2,resultadoConsumidoresMod,result.length());
		    result+=label.toString()+" ";
		  }
		  System.out.println("En la tokinazion/reindexacion se perdieron: "+listaConsumidores2.size()+" elementos timeML");
		  ////debug2
		  final String resultInmutable=result;
		  //busc los elementos que su tamno no coincide con los limites
		  CollectionUtils.forAllDo(resultadoConsumidoresMod, new Closure() {

			@Override
			public void execute(Object arg0) {
				ConsumidorTexto unConsumidor = (ConsumidorTexto)arg0;
				if(unConsumidor.end-unConsumidor.start-unConsumidor.getContenido().length()!=0){
					unConsumidor.setStart(resultInmutable.indexOf(unConsumidor.getContenido(), unConsumidor.start));
					unConsumidor.setEnd(unConsumidor.getStart()+unConsumidor.getContenido().length());
				}
				
			}
			  
		  	
		  });
		  /////
//		  listaConsumidores2.clear();
		  listaConsumidores2.addAll(resultadoConsumidoresMod);//ver porque quedan algunos sin modificar
		  return result;
	}
	
	private static void actualizarIndices(final CoreLabel label, ArrayList<ConsumidorTexto> listaConsumidores2,ArrayList<ConsumidorTexto> resultadoConsumidoresMod,Integer indiceActual) {
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

	private void agregarElementoALink(Node nodoOrigen) {
		ALink alink = new ALink((Element)nodoOrigen, archivoTimeML.getMakeinstanceTabla(), archivoTimeML.getSignalTabla());
		archivoTimeML.getAlinkTabla().put(alink.getLid(), alink);
		
	}

	private void agregarElementoSLink(Node nodoOrigen) {
		SLink slink = new SLink((Element)nodoOrigen, archivoTimeML.getMakeinstanceTabla(), archivoTimeML.getSignalTabla());
		archivoTimeML.getSlinkTabla().put(slink.getLid(), slink);
	}

	private void agregarElementoTLink(Node nodoOrigen) {
		TLink tlink = new TLink((Element)nodoOrigen, archivoTimeML.getMakeinstanceTabla(), archivoTimeML.getTimex3Tabla(), archivoTimeML.getSignalTabla());
		archivoTimeML.getTlinkTabla().put(tlink.getLid(), tlink);
	}

	private void agregarElementoMakeInstance(Node nodoOrigen) {
		MakeInstance makeinstance =  new MakeInstance((Element) nodoOrigen, archivoTimeML.getEventTabla(), archivoTimeML.getSignalTabla());
		archivoTimeML.getMakeinstanceTabla().put(makeinstance.getEiid(), makeinstance);
	}

	private void agregarElementoSignal(Node nodoOrigen, Integer index) {
		Signal signal = new Signal((Element)nodoOrigen,index);
		archivoTimeML.getSignalTabla().put(signal.getSid(), signal);
		this.listaConsumidores.add(signal);
	}

	private void agregarElementoEvent(Node nodoOrigen, Integer index) {
		Event evento = new Event((Element) nodoOrigen,index);
		archivoTimeML.getEventTabla().put(evento.getEid(), evento);
		this.listaConsumidores.add(evento);
	}

	private void agregarElementoTimex3(Node nodoOrigen, Integer index) {
		Timex3 timex=new Timex3((Element)nodoOrigen,archivoTimeML.getTimex3Tabla(),index);
		archivoTimeML.getTimex3Tabla().put(timex.getTid(), timex);
		this.listaConsumidores.add(timex);
	}

	private void agregarElementoTextoSinMarcar(String contenido, Integer index) {
		TextoSinMarcarTimeML textoSinMarc=new TextoSinMarcarTimeML(contenido,index);
//		this.listaConsumidores.add(textoSinMarc);
	}
}

package transformador.generadorFeatures.links;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import transformador.formatoDefinicionFrame.DefinicionFramenet;
import transformador.formatoFramenet.ArchivoFormatoFramenet;
import transformador.formatoFramenet.Documento;
import transformador.formatoFramenet.Label;
import transformador.formatoFramenet.Oracion;
import transformador.formatoFramenet.Parrafo;
import transformador.formatoTimeML.ALink;
import transformador.formatoTimeML.ArchivoTimeML;
import transformador.formatoTimeML.Event;
import transformador.formatoTimeML.Link;
import transformador.formatoTimeML.MakeInstance;
import transformador.formatoTimeML.ReferenciablePorLink;
import transformador.formatoTimeML.SLink;
import transformador.formatoTimeML.TLink;
import transformador.formatoTimeML.Timex3;
import transformador.formatoTimeML.types.LinkType;
import transformador.generadorFeatures.ArchivosInconsistentesGeneradorFeaturesException;

public class GeneradorFeaturesParaLinks {
	protected ArchivoTimeML archivoTimeML;//archivo timeml que mria ucando quiere sacar el atributo clase, entrenaimiento
	private ArchivoFormatoFramenet archivoFramenet;//archivo framenet de donde 
	protected DefinicionFramenet defFramenet;
	private String pathArchivoSalida;
	private float porcentajeATest=0;
	private HashMap<Integer, Oracion> tablaIndicesOracionTimeMLenFramenet = new HashMap<Integer, Oracion>();
	private HashMap<PosibleAsociacion, HashMap<String,String>> tablaFeaturesAsoc= new HashMap<PosibleAsociacion, HashMap<String,String>>();
	
	public GeneradorFeaturesParaLinks(ArchivoTimeML archivoTimeML,
			ArchivoFormatoFramenet archivoFramenet,DefinicionFramenet defFramenet) throws ArchivosInconsistentesGeneradorFeaturesException {
		super();
		this.archivoTimeML = archivoTimeML;
		this.archivoFramenet = archivoFramenet;
		this.defFramenet=defFramenet;
		if(!isArchivosConsistentes()) throw new ArchivosInconsistentesGeneradorFeaturesException();
		
		//lleno la tabla de indices para luego usar
		for(Documento doc:archivoFramenet.getListaDocumentos()){
			for(Parrafo parrafo:doc.getListaParrafos()){
				for(Oracion oracion:parrafo.getListaOraciones()){
					int indice = obtenerIndiceDeOracionAbsoluto(oracion.getTexto());
					if(indice!=-1){
						tablaIndicesOracionTimeMLenFramenet.put(indice, oracion);
					}
				}
			}
		}
	}
	
	public GeneradorFeaturesParaLinks(ArchivoTimeML archivoTimeML2,
			ArchivoFormatoFramenet archFrameNet,
			DefinicionFramenet defFramenet2, String pathArchivoSalida, float porcentajeATest) throws ArchivosInconsistentesGeneradorFeaturesException {
		this(archivoTimeML2,archFrameNet,defFramenet2);
		this.pathArchivoSalida=pathArchivoSalida;
		this.porcentajeATest=porcentajeATest;
	}
	
	@SuppressWarnings("unchecked")
	public void generarFeatures() throws ArchivosInconsistentesGeneradorFeaturesException{
		ArrayList<PosibleAsociacion> resultadoAsociaciones=new ArrayList<PosibleAsociacion>();
		
		//entre eventos
		ArrayList<MakeInstance> listaEventosMK=new ArrayList<MakeInstance>(archivoTimeML.getMakeinstanceTabla().values());
		
		for(int i=0;i<listaEventosMK.size();i++){
			for(int j=i+1;j<listaEventosMK.size();j++){
				resultadoAsociaciones.add(new PosibleAsociacion(listaEventosMK.get(i), listaEventosMK.get(j)));
			}
		}
		
		//con los timex
		ArrayList<Timex3> listatimex3=new ArrayList<Timex3>(this.archivoTimeML.getTimex3Tabla().values());
		for(int i=0;i<listaEventosMK.size();i++){
			for(int j=0;j<listatimex3.size();j++){
				resultadoAsociaciones.add(new PosibleAsociacion(listaEventosMK.get(i), listatimex3.get(j)));
			}
		}
		
		//entre timex
		for(int i=0;i<listatimex3.size();i++){
			for(int j=i+1;j<listatimex3.size();j++){
				resultadoAsociaciones.add(new PosibleAsociacion(listatimex3.get(i), listatimex3.get(j)));
			}
		}
		
		resultadoAsociaciones=(ArrayList<PosibleAsociacion>) CollectionUtils.select(resultadoAsociaciones, new Predicate() {
			
			@Override
			public boolean evaluate(Object arg0) {
				PosibleAsociacion asociacion=(PosibleAsociacion) arg0;
				int dist=distanciaOraciones(asociacion);
				return (dist<=2)&&(dist>=0);
			}
		});
		
		Iterator<PosibleAsociacion> iteradorAsociaciones=resultadoAsociaciones.iterator();
		int links=0,nolinks=0;
		HashSet<Link> listaLinks = new HashSet<Link>();
		while(iteradorAsociaciones.hasNext()){
			PosibleAsociacion asociacion=iteradorAsociaciones.next();
			Link unLink=this.archivoTimeML.obtenerLink(asociacion);
			if(unLink!=null){
				links++;
				listaLinks.add(unLink);
				guardarFeature(asociacion,"Id",unLink.getLid());
				guardarFeature(asociacion,"Clase","LINK");
				guardarFeature(asociacion,"Distancia_oraciones", this.distanciaOraciones(asociacion).toString());
				guardarFeature(asociacion,"Tipo",unLink.obtenerTipoDeLink().name());
//				System.out.println("LINK: Id="+unLink.getLid()+" - - "+asociacion.getreferenciablePorLinkA().toString()+" - - "+asociacion.getreferenciablePorLinkB().toString()+" "+asociacion.getDistanciaEntreReferencias()+" -- "+this.distanciaOraciones(asociacion)+" - Tipo: "+unLink.obtenerTipoDeLink().name());
				featuresPosibleAsociacion(asociacion);
//				if(unLink.obtenerTipoDeLink().equals(LinkType.TLINK)){
//					featuresTLink((TLink)unLink);
//				}
//				
//				if(unLink.obtenerTipoDeLink().equals(LinkType.SLINK)){
//					featuresSLink((SLink)unLink);
//				}
//				
//				if(unLink.obtenerTipoDeLink().equals(LinkType.ALINK)){
//					featuresALink((ALink)unLink);
//				}
			}else{
				nolinks++;
				guardarFeature(asociacion,"Clase","NO_LINK");
				guardarFeature(asociacion,"Distancia_oraciones", this.distanciaOraciones(asociacion).toString());
				featuresPosibleAsociacion(asociacion);
//				System.out.println("NO_LINK: "+asociacion.getreferenciablePorLinkA().toString()+" - - "+asociacion.getreferenciablePorLinkB().toString()+" "+asociacion.getDistanciaEntreReferencias());
			}
		}
		System.out.println("Nolinks: "+nolinks+" link: "+links);
//		Iterator<Link> it = listaLinks.iterator();
//		while(it.hasNext())System.out.println(it.next().getLid());
		
	}



	private void guardarFeature(PosibleAsociacion asociacion, String nombreFeature,
			String valor) {
		if(this.tablaFeaturesAsoc.containsKey(asociacion)){//si ya existe  una tabla de features  para la asociacion
			HashMap<String, String> features=this.tablaFeaturesAsoc.get(asociacion);
			features.put(nombreFeature, valor);
		}else{//no existe una tabla de features  para la asociacion
			HashMap<String, String> features= new HashMap<String, String>();
			features.put(nombreFeature, valor);
			this.tablaFeaturesAsoc.put(asociacion, features);
		}
	}

//	private void featuresALink(ALink unLink)
//			throws ArchivosInconsistentesGeneradorFeaturesException {
//		//parte A
//		Oracion laOracion = null;
//		laOracion=this.buscarOracionEn(unLink.getEventInstanceID().getEventID().getStart());
//		
//		generarFeaturesFramenetParaEvento(laOracion,unLink.getEventInstanceID().getEventID(),"A");
//		
//		System.out.println();
//		//parte B
//		Oracion otraOracion = null;
//		otraOracion=this.buscarOracionEn(unLink.getRelatedToEventInstanceID().getEventID().getStart());
//		
//		generarFeaturesFramenetParaEvento(otraOracion,unLink.getRelatedToEventInstanceID().getEventID(),"B");
//		System.out.println();
//			
//		
//		if(laOracion.equals(otraOracion))System.out.println("Misma_Oracion=SI");
//		else System.out.println("Misma_Oracion=NO");
//	}
	
//	private void featuresSLink(SLink unLink)
//			throws ArchivosInconsistentesGeneradorFeaturesException {
//		//parte A
//		Oracion laOracion = null;
//		laOracion=this.buscarOracionEn(unLink.getEventInstanceID().getEventID().getStart());
//		
//		generarFeaturesFramenetParaEvento(laOracion,unLink.getEventInstanceID().getEventID(),"A");
//		
//		System.out.println();
//		//parte B
//		Oracion otraOracion = null;
//		otraOracion=this.buscarOracionEn(unLink.getSubordinatedEventInstanceID().getEventID().getStart());
//		
//		generarFeaturesFramenetParaEvento(otraOracion,unLink.getSubordinatedEventInstanceID().getEventID(),"B");
//		System.out.println();
//			
//		
//		if(laOracion.equals(otraOracion))System.out.println("Misma_Oracion=SI");
//		else System.out.println("Misma_Oracion=NO");
//	}
	
//	private void featuresTLink(TLink unLink)
//			throws ArchivosInconsistentesGeneradorFeaturesException {
//		//parte A
//		ReferenciablePorLink unReferenciable=unLink.getReferenciaEventTime();
//		Oracion laOracion = null;
//		if(unReferenciable instanceof MakeInstance){
//			laOracion=this.buscarOracionEn(((MakeInstance)unReferenciable).getEventID().getStart());
//			
//			generarFeaturesFramenetParaEvento(laOracion,((MakeInstance)unReferenciable).getEventID(),"A");
//			
//			System.out.println();
//		}else if(unReferenciable instanceof Timex3){
//			laOracion=this.buscarOracionEn(((Timex3)unReferenciable).getStart());
//			
//			generarFeaturesFramenetParaTimex3(laOracion,((Timex3)unReferenciable),"A");
//				
//			System.out.println();
//		}
//		//parte B
//		ReferenciablePorLink otroReferenciable=unLink.getRelatedEventTime();
//		Oracion otraOracion = null;
//		if(otroReferenciable instanceof MakeInstance){
//			otraOracion=this.buscarOracionEn(((MakeInstance)otroReferenciable).getEventID().getStart());
//			
//			generarFeaturesFramenetParaEvento(otraOracion,((MakeInstance)otroReferenciable).getEventID(),"B");
//			System.out.println();
//			
//		}else if(otroReferenciable instanceof Timex3){
//			otraOracion=this.buscarOracionEn(((Timex3)otroReferenciable).getStart());
//			generarFeaturesFramenetParaTimex3(otraOracion,((Timex3)otroReferenciable),"B");
//			System.out.println();
//		}
//		
//		if(laOracion.equals(otraOracion))System.out.println("Misma_Oracion=SI");
//		else System.out.println("Misma_Oracion=NO");
//	}
	
	private void featuresPosibleAsociacion(PosibleAsociacion asociacion)
			throws ArchivosInconsistentesGeneradorFeaturesException {
		//parte A
		ReferenciablePorLink unReferenciable=asociacion.getreferenciablePorLinkA();
		Oracion laOracion = null;
		ArrayList<Label> labelsDeParteA = null;
		if(unReferenciable instanceof MakeInstance){
			laOracion=this.buscarOracionEn(((MakeInstance)unReferenciable).getEventID().getStart());
			
			labelsDeParteA=obtenerLabelsFramenetParaEvento(laOracion,((MakeInstance)unReferenciable).getEventID(),"A");
			
			System.out.println();
		}else if(unReferenciable instanceof Timex3){
			laOracion=this.buscarOracionEn(((Timex3)unReferenciable).getStart());
			
			labelsDeParteA=obtenerLabelsFramenetParaTimex3(laOracion,((Timex3)unReferenciable),"A");
				
			System.out.println();
		}
		//parte B
		ReferenciablePorLink otroReferenciable=asociacion.getreferenciablePorLinkB();
		Oracion otraOracion = null;
		ArrayList<Label> labelsDeParteB = null;
		if(otroReferenciable instanceof MakeInstance){
			otraOracion=this.buscarOracionEn(((MakeInstance)otroReferenciable).getEventID().getStart());
			
			labelsDeParteB = obtenerLabelsFramenetParaEvento(otraOracion,((MakeInstance)otroReferenciable).getEventID(),"B");
			System.out.println();
			
		}else if(otroReferenciable instanceof Timex3){
			otraOracion=this.buscarOracionEn(((Timex3)otroReferenciable).getStart());
			labelsDeParteB = obtenerLabelsFramenetParaTimex3(otraOracion,((Timex3)otroReferenciable),"B");
			System.out.println();
		}
		generarFeaturesSemanticosFramenet(asociacion,labelsDeParteA,labelsDeParteB);
		guardarFeature(asociacion,"Misma_Oracion", laOracion.equals(otraOracion)?"SI":"NO");
//		if(laOracion.equals(otraOracion))System.out.println("Misma_Oracion=SI");
//		else System.out.println("Misma_Oracion=NO");
	}
	
	private void generarFeaturesSemanticosFramenet(PosibleAsociacion asociacion,
		ArrayList<Label> labelsDeParteA, ArrayList<Label> labelsDeParteB) {
	//primero:si comparten mismo frame Z. genera LabelA=X LabelB=Y FrameA=Z FrameB=Z. FIN
		Iterator<Label> itA=labelsDeParteA.iterator();
		while(itA.hasNext()){
			Label unLabelDeA=itA.next();
			Iterator<Label> itB=labelsDeParteB.iterator();
			while(itB.hasNext()){
				Label unLabelDeB=itB.next();
				if(unLabelDeA.getFramePadre().getFrameName().equals(unLabelDeB.getFramePadre().getFrameName())){
					guardarFeature(asociacion,"LabelA",unLabelDeA.getName());
					guardarFeature(asociacion,"LabelB",unLabelDeB.getName());
					guardarFeature(asociacion,"FrameA",unLabelDeA.getFramePadre().getFrameName());
					guardarFeature(asociacion,"FrameB",unLabelDeB.getFramePadre().getFrameName());
					return;//fin
				}
			}
		}
	//segundo:tienen frames diferentes que tienen alguna relacion. genera LabelA=X LabelB=Y FrameA=Z FrameB=V Relacion=W RelRolA=U RelRolB=H
		
	//tercero: toma cualquier frame para cada parte. Optimamente el de mayor jerarquia.
	
	}

	private ArrayList<Label> obtenerLabelsFramenetParaTimex3(Oracion laOracion,
			Timex3 elTimex, String tag) {
		if(laOracion==null)return null;
		Integer indiceAbs=obtenerIndiceDeOracionAbsoluto(laOracion.getTexto());
		Collection<Label> listaLabels=laOracion.obtenerLabelsFlexibleEn(elTimex.getStart()-indiceAbs,elTimex.getEnd()-indiceAbs);
//		for(Label unLabel:listaLabels){
//			System.out.print("Label"+tag+"="+unLabel.getName()+" ");
//			System.out.print("Frame"+tag+"="+unLabel.getFramePadre().getFrameName()+" ");
//		}
		return new ArrayList<Label>(listaLabels);
	}

	/**
	 * Devuelve una lista de los labels que etiquetan al evento
	 * @param laOracion
	 * @param elEvento
	 * @param tag
	 */
	private ArrayList<Label> obtenerLabelsFramenetParaEvento(Oracion laOracion,
			Event elEvento, String tag) {
		if(laOracion==null)return null;
		Integer indiceAbs=obtenerIndiceDeOracionAbsoluto(laOracion.getTexto());
		Collection<Label> listaLabels=laOracion.obtenerLabelsFlexibleEn(elEvento.getStart()-indiceAbs,elEvento.getEnd()-indiceAbs);
//		for(Label unLabel:listaLabels){
//			System.out.print("Label"+tag+"="+unLabel.getName()+" ");
//			System.out.print("Frame"+tag+"="+unLabel.getFramePadre().getFrameName()+" ");
//		}
		return new ArrayList<Label>(listaLabels);
	}

	/**TODO:Este codigo se repite. sacar!!!!
	 * Devuelve true si todas las oraciones del archivo en formato framenet pueden ser encontradas en el archivo timeml
	 * false en caso contrario.
	 * @return
	 */
	public Boolean isArchivosConsistentes(){
		for(Documento doc:archivoFramenet.getListaDocumentos()){
			for(Parrafo parrafo:doc.getListaParrafos()){
				for(Oracion oracion:parrafo.getListaOraciones()){
					if(obtenerIndiceDeOracionAbsoluto(oracion.getTexto())==-1){
						System.out.println("Oracion: "+oracion.getTexto());
						System.out.println(archivoTimeML.getTextoPlano());
						return false;
					}
				}
			}
		}
		
		return true;
		
	}
	
	/**
	 * Dada una oracion de framenet, devuelve el indice en el archivo TimeML. Considera algunas conversiones de formato y le saca el f=punto al final
	 * @param oracion
	 * @return
	 */
	private int obtenerIndiceDeOracionAbsoluto(String oracion) {
		return archivoTimeML.getTextoPlano().indexOf(oracion);///le saco el punto del final
	}
	
	/**
	 * se le pasa un indice absoluto de timeml y devuelve la oracion de framenet que corresponde
	 * @param indice
	 * @throws ArchivosInconsistentesGeneradorFeaturesException 
	 * @returnSet
	 */
	private Oracion buscarOracionEn(Integer indice) throws ArchivosInconsistentesGeneradorFeaturesException{
		ArrayList<Integer> listaIndices= new ArrayList<Integer>();
		listaIndices.addAll(tablaIndicesOracionTimeMLenFramenet.keySet());
		Collections.sort(listaIndices);
		if(listaIndices.get(0)>indice)return null;//es menor que la primer oracion... no pertenece a ninguna oracion. null
		for(int i=0;i<listaIndices.size();i++){
			if(listaIndices.get(i)>indice)return tablaIndicesOracionTimeMLenFramenet.get(listaIndices.get(i-1));
		}
		return null;//. no pertenece a ninguna oracion. null
	}
	
	/**
	 * Cantidad de oraciones de distancia. Si es la misma, 0. Si no aplica, -1.
	 * @param asociacion
	 * @return
	 */
	private Integer distanciaOraciones(PosibleAsociacion asociacion) {
		Integer distancia = asociacion.getDistanciaEntreReferencias();
		Integer oracionesDistancia=0;
		Integer min=asociacion.getreferenciablePorLinkA().getStart()<=asociacion.getreferenciablePorLinkB().getStart()?asociacion.getreferenciablePorLinkA().getStart():asociacion.getreferenciablePorLinkB().getStart();
		Integer max=asociacion.getreferenciablePorLinkA().getStart()<=asociacion.getreferenciablePorLinkB().getStart()?asociacion.getreferenciablePorLinkB().getStart():asociacion.getreferenciablePorLinkA().getStart();
		
		ArrayList<Integer> listaIndices= new ArrayList<Integer>();
		listaIndices.addAll(tablaIndicesOracionTimeMLenFramenet.keySet());
		Collections.sort(listaIndices);
		
		if((listaIndices.get(0)>min)||(listaIndices.get(0)>max))return -1;
		
		for(int i=0;i<listaIndices.size();i++){
			if((min<=listaIndices.get(i))&&(listaIndices.get(i)<=max))oracionesDistancia++;
		}
		
		return oracionesDistancia;
	}
}

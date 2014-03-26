package transformador;

import java.util.ArrayList;
import java.util.HashMap;

import transformador.formatoFramenet.ArchivoFormatoFramenet;
import transformador.formatoFramenet.Frame;
import transformador.formatoFramenet.Label;
import transformador.formatoFramenet.Oracion;
import transformador.formatoFramenet.Utils;
import transformador.formatoSalsa.ArchivoFormatoSalsaSoloEscritura;
import transformador.formatoSalsa.EdgeSalsa;
import transformador.formatoSalsa.FeNodeSalsa;
import transformador.formatoSalsa.FeSalsa;
import transformador.formatoSalsa.FrameSalsa;
import transformador.formatoSalsa.GraphSalsa;
import transformador.formatoSalsa.NoTerminalSalsa;
import transformador.formatoSalsa.OracionSalsa;
import transformador.formatoSalsa.SemSalsa;
import transformador.formatoSalsa.TargetSalsa;
import transformador.formatoSalsa.TerminalSalsa;

public class ConversorArchivoSalsa {
	private ArchivoFormatoFramenet archivoFramenet;
	private HashMap<ClaveDeReferenciable, ReferenciablePorUnEdgeSalsa> tablaReferenciables = new HashMap<ClaveDeReferenciable, ReferenciablePorUnEdgeSalsa>();
	private ArrayList<NoTerminalSalsa> listaNoTerminales = new ArrayList<NoTerminalSalsa>();
	
	public ConversorArchivoSalsa(ArchivoFormatoFramenet archivoFramenet) {
		super();
		this.archivoFramenet = archivoFramenet;
	}

	public ArchivoFormatoSalsaSoloEscritura obtenerEnformatoSalsa(){
		ArchivoFormatoSalsaSoloEscritura archivoSalsa2 = new ArchivoFormatoSalsaSoloEscritura();
		
		//creo el graphico
		GraphSalsa graph= new GraphSalsa();
		SemSalsa sem = new SemSalsa();
		//creo la oracion asociada al grafico
		OracionSalsa s=new OracionSalsa(Utils.getSId(), graph, sem);
		graph.setOracion(s);
		//agrego un nodo terminal, osea una palabra
		 ArrayList<TerminalSalsa> listaTerminales= crearListaDeNodosTerminales(archivoFramenet.getListaDocumentos().get(0).getListaParrafos().get(0).getListaOraciones().get(0));
		TerminalSalsa t=listaTerminales.get(0);
		for(TerminalSalsa terminal:listaTerminales)
			graph.agregarNodoTerminal(terminal);
		//agrego a la tabla de referenciables
		agregarATablaDeReferenciables(listaTerminales);
		//seteo el root
		graph.setRoot(t.getId());

		
		//agrego los frames
		ArrayList<FrameSalsa> listaFramesSalsa = crearListaFramesSalsa(archivoFramenet.getListaDocumentos().get(0).getListaParrafos().get(0).getListaOraciones().get(0));
		for(FrameSalsa unFrameSalsa:listaFramesSalsa){
			sem.agregarFrame(unFrameSalsa);
		}
		
		//agrego nodo no terminal
		for(NoTerminalSalsa ntSalsa:this.listaNoTerminales){
			graph.agregarNodoNoTerminal(ntSalsa);
		}
		archivoSalsa2.agregarOracion(s);
		
		return archivoSalsa2;
	}
	
	private void agregarATablaDeReferenciables(
			ArrayList<TerminalSalsa> listaTerminales) {
		Integer miIndice=0;
		for(TerminalSalsa terminal:listaTerminales){
			String palabra=terminal.getWord();
			Integer indiceFinal=miIndice+palabra.length();
			this.tablaReferenciables.put(new ClaveDeReferenciable(miIndice,indiceFinal), terminal);
			miIndice=indiceFinal+1;
		}
		
	}

	private ArrayList<FrameSalsa> crearListaFramesSalsa(Oracion oracionFramenet) {
		ArrayList<Frame> listaFramesFramenet=oracionFramenet.getListaDeFramesAnotados();
		ArrayList<FrameSalsa> resultado = new ArrayList<FrameSalsa>();
		FrameSalsa unFrameSalsa;
		for(Frame unFrame:listaFramesFramenet){
			unFrameSalsa=new FrameSalsa(unFrame.getFrameName(), Utils.getFrameId());
			//Frame elements salsa
			for(Label frameElement:unFrame.getListaFE()){
				FeSalsa unFeSalsa=new FeSalsa(Utils.getFeId(), frameElement.getName());
				FeNodeSalsa unFeNodeSalsa = new FeNodeSalsa();
				ClaveDeReferenciable clave= new ClaveDeReferenciable(frameElement.getStart(),frameElement.getEnd()+1);
				if(!this.tablaReferenciables.containsKey(clave)){
					NoTerminalSalsa noTerminalSalsa=new NoTerminalSalsa(Utils.getNtId(), "", "");
					//creo los edges y se los agrego al noterminal TODO
					ArrayList<EdgeSalsa> listaEdges = obtenerListaEdges(clave);
					for(EdgeSalsa unEdgeSalsa:listaEdges)noTerminalSalsa.agregarEdge(unEdgeSalsa);
					//this.tablaReferenciables.put(clave, noTerminalSalsa);//lo agrego, por ahora no
					listaNoTerminales.add(noTerminalSalsa);
					unFeNodeSalsa.setReferencia(noTerminalSalsa);
				}else//si esta en la tabla
				{
					unFeNodeSalsa.setReferencia(this.tablaReferenciables.get(clave));
				}
				unFeSalsa.setFenode(unFeNodeSalsa);
				unFrameSalsa.agregarFrameElement(unFeSalsa);
			}
			//Target de salsa
			Label target=unFrame.getTarget();
			TargetSalsa targetSalsa = new TargetSalsa(Utils.getTargetId(), "", target.getName());
			FeNodeSalsa unFeNodeSalsa = new FeNodeSalsa();
			ClaveDeReferenciable clave= new ClaveDeReferenciable(target.getStart(),target.getEnd()+1);
			if(!this.tablaReferenciables.containsKey(clave)){
				NoTerminalSalsa noTerminalSalsa=new NoTerminalSalsa(Utils.getNtId(), "", "");
				//creo los edges y se los agrego al noterminal TODO
				ArrayList<EdgeSalsa> listaEdges = obtenerListaEdges(clave);
				for(EdgeSalsa unEdgeSalsa:listaEdges)noTerminalSalsa.agregarEdge(unEdgeSalsa);
				//this.tablaReferenciables.put(clave, noTerminalSalsa);//POR AHORA NO AGREGO NODOS COMPUESTOS, SIEMPRE REFIERO AL TERMINAL. Hacer lista de No terminales, no usar. pero agregarlos al xml final.
				listaNoTerminales.add(noTerminalSalsa);
				unFeNodeSalsa.setReferencia(noTerminalSalsa);
			}else//si esta en la tabla
			{
				unFeNodeSalsa.setReferencia(this.tablaReferenciables.get(clave));
			}
			targetSalsa.setFenode(unFeNodeSalsa);
			unFrameSalsa.setTarget(targetSalsa);
			//agrego a los resultados
			resultado.add(unFrameSalsa);
		}
		return resultado;
	}

	private ArrayList<EdgeSalsa> obtenerListaEdges(ClaveDeReferenciable clave) {
		ArrayList<EdgeSalsa> resultado = new ArrayList<EdgeSalsa>();
		for(ClaveDeReferenciable unaClaveDeLaTabla:this.tablaReferenciables.keySet()){
			if(unaClaveDeLaTabla.estaIncluidoEn(clave))resultado.add(new EdgeSalsa(this.tablaReferenciables.get(unaClaveDeLaTabla),"-"));
		}
		return resultado;
	}

	/**
	 * metodo que crea una lista de nodos terminales (palabras) dada una oracion
	 * @param oracionFramenet
	 * @return
	 */
	private ArrayList<TerminalSalsa> crearListaDeNodosTerminales(Oracion oracionFramenet){
		ArrayList<TerminalSalsa> resultado = new ArrayList<TerminalSalsa>();
		for(String palabra:oracionFramenet.getTexto().split(" ")){
			TerminalSalsa unTerminal=new TerminalSalsa(Utils.getTId(), palabra, "", palabra);
			resultado.add(unTerminal);
		}
		return resultado;
	}
}

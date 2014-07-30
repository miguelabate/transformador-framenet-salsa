package transformador.conversorASalsa;

import java.util.ArrayList;
import java.util.HashMap;

import transformador.common.ClaveDeReferenciable;
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
	
	public ConversorArchivoSalsa(ArchivoFormatoFramenet archivoFramenet) {
		super();
		this.archivoFramenet = archivoFramenet;
	}

	public ArchivoFormatoSalsaSoloEscritura obtenerEnformatoSalsa(){
		ArchivoFormatoSalsaSoloEscritura archivoSalsa2 = new ArchivoFormatoSalsaSoloEscritura();
		
		
		for(Oracion oracionFramenet:archivoFramenet.getListaDocumentos().get(0).getListaParrafos().get(0).getListaOraciones()){
			limpiarNodos();
			//creo el graphico
			GraphSalsa graph= new GraphSalsa();
			SemSalsa sem = new SemSalsa();
			//creo la oracion asociada al grafico
			OracionSalsa s=new OracionSalsa(Utils.getSId(), graph, sem);
			graph.setOracion(s);
			//agrego un nodo terminal, osea una palabra
			 ArrayList<TerminalSalsa> listaTerminales= crearListaDeNodosTerminales(oracionFramenet);
			TerminalSalsa t=listaTerminales.get(0);
			for(TerminalSalsa terminal:listaTerminales)
				graph.agregarNodoTerminal(terminal);
			//agrego a la tabla de referenciables
			agregarATablaDeReferenciables(listaTerminales);
			//seteo el root
			graph.setRoot(t.getId());
	
			
			//agrego los frames
			ArrayList<FrameSalsa> listaFramesSalsa = crearListaFramesSalsa(oracionFramenet);
			for(FrameSalsa unFrameSalsa:listaFramesSalsa){
				sem.agregarFrame(unFrameSalsa);
			}
			
			//agrego nodos no terminal
			for(ClaveDeReferenciable unaClaveDeLaTabla:this.tablaReferenciables.keySet()){
				ReferenciablePorUnEdgeSalsa referenciablePorEdge=this.tablaReferenciables.get(unaClaveDeLaTabla);
				if(referenciablePorEdge instanceof NoTerminalSalsa) graph.agregarNodoNoTerminal((NoTerminalSalsa) referenciablePorEdge);
			}
			archivoSalsa2.agregarOracion(s);
		}
		return archivoSalsa2;
	}
	
	/**
	 * crea nueva tabla
	 */
	private void limpiarNodos() {
		 tablaReferenciables = new HashMap<ClaveDeReferenciable, ReferenciablePorUnEdgeSalsa>();
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
					this.tablaReferenciables.put(clave, noTerminalSalsa);
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
				this.tablaReferenciables.put(clave, noTerminalSalsa);
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

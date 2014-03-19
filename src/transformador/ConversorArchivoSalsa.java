package transformador;

import java.util.ArrayList;
import java.util.HashMap;

import transformador.formatoFramenet.ArchivoFormatoFramenet;
import transformador.formatoFramenet.Frame;
import transformador.formatoFramenet.Label;
import transformador.formatoFramenet.Oracion;
import transformador.formatoFramenet.Utils;

public class ConversorArchivoSalsa {
	private ArchivoFormatoFramenet archivoFramenet;
	private HashMap<String, ReferenciablePorUnEdgeSalsa> tablaReferenciables = new HashMap<String, ReferenciablePorUnEdgeSalsa>();
	
	public ConversorArchivoSalsa(ArchivoFormatoFramenet archivoFramenet) {
		super();
		this.archivoFramenet = archivoFramenet;
	}

	public ArchivoFormatoSalsa obtenerEnformatoSalsa(){
		ArchivoFormatoSalsa archivoSalsa2 = new ArchivoFormatoSalsa();
		
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
		graph.setRoot(t.getId());
		//agrego nodo no terminal
//		NoTerminalSalsa nt=new NoTerminalSalsa(Utils.getNtId(), "", "");
		//agrego un vertice al no terminal
//		EdgeSalsa edge = new EdgeSalsa(t, "");
//		nt.agregarEdge(edge);
//		graph.agregarNodoNoTerminal(nt);
		
		//agrego los frames
		ArrayList<FrameSalsa> listaFramesSalsa = crearListaFramesSalsa(archivoFramenet.getListaDocumentos().get(0).getListaParrafos().get(0).getListaOraciones().get(0));
//		FrameSalsa unFrame=new FrameSalsa("Mi_Frame", Utils.getFrameId());
//		sem.agregarFrame(unFrame);
//		FeSalsa unFeSalsa = new FeSalsa(Utils.getFeId(), "un_fe");
//		unFrame.agregarFrameElement(unFeSalsa);
		//le agrego el nodoref
//		FeNodeSalsa unFenode =new FeNodeSalsa();
//		unFenode.setReferencia(nt);
//		unFeSalsa.setFenode(unFenode);
		
		//agrego el target
//		TargetSalsa target = new TargetSalsa(Utils.getTargetId(), "", "");
//		unFrame.setTarget(target);
		//le agrego el nodoref
//		FeNodeSalsa unFenode2 =new FeNodeSalsa();
//		unFenode2.setReferencia(t);
//		target.setFenode(unFenode2);
		archivoSalsa2.agregarOracion(s);
		
		return archivoSalsa2;
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
				unFrameSalsa.agregarFrameElement(unFeSalsa);
			}
			//Target de salsa
			
			//agrego a los resultados
			resultado.add(unFrameSalsa);
		}
		return resultado;
	}

	private ArrayList<TerminalSalsa> crearListaDeNodosTerminales(Oracion oracionFramenet){
		ArrayList<TerminalSalsa> resultado = new ArrayList<TerminalSalsa>();
		for(String palabra:oracionFramenet.getTexto().split(" ")){
			resultado.add(new TerminalSalsa(Utils.getTId(), palabra, "", palabra));
		}
		return resultado;
	}
}

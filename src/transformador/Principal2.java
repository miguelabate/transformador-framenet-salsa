package transformador;
import javax.xml.transform.TransformerException;

import transformador.formatoFramenet.Utils;


public class Principal2 {

	/**
	 * @param args
	 * @throws TransformerException 
	 */
	public static void main(String[] args) throws TransformerException {
		ArchivoFormatoSalsa archivoSalsa2 = new ArchivoFormatoSalsa();
		
		//creo el graphico
		GraphSalsa graph= new GraphSalsa();
		SemSalsa sem = new SemSalsa();
		//creo la oracion asociada al grafico
		OracionSalsa s=new OracionSalsa(Utils.getSId(), graph, sem);
		graph.setOracion(s);
		//agrego un nodo terminal, osea una palabra
		TerminalSalsa t=new TerminalSalsa(Utils.getTId(), "", "", "Hola");
		TerminalSalsa t2=new TerminalSalsa(Utils.getTId(), "", "", ",");
		TerminalSalsa t3=new TerminalSalsa(Utils.getTId(), "", "", "Miguel");
		graph.agregarNodoTerminal(t);
		graph.agregarNodoTerminal(t2);
		graph.agregarNodoTerminal(t3);
		graph.setRoot(t.getId());
		//agrego nodo no terminal
		NoTerminalSalsa nt=new NoTerminalSalsa(Utils.getNtId(), "", "");
		//agrego un vertice al no terminal
		EdgeSalsa edge = new EdgeSalsa(t, "");
		nt.agregarEdge(edge);
		graph.agregarNodoNoTerminal(nt);
		
		//agrego los frames
		FrameSalsa unFrame=new FrameSalsa("Mi_Frame", Utils.getFrameId());
		sem.agregarFrame(unFrame);
		FeSalsa unFeSalsa = new FeSalsa(Utils.getFeId(), "un_fe");
		unFrame.agregarFrameElement(unFeSalsa);
		//le agrego el nodoref
		FeNodeSalsa unFenode =new FeNodeSalsa();
		unFenode.setReferencia(nt);
		unFeSalsa.setFenode(unFenode);
		
		//agrego el target
		TargetSalsa target = new TargetSalsa(Utils.getTargetId(), "", "");
		unFrame.setTarget(target);
		//le agrego el nodoref
		FeNodeSalsa unFenode2 =new FeNodeSalsa();
		unFenode2.setReferencia(t);
		target.setFenode(unFenode2);
		archivoSalsa2.agregarOracion(s);
		
		
		archivoSalsa2.guardarAArchivo("base.xml");
	}

}

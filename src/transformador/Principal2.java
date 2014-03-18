package transformador;
import javax.xml.transform.TransformerException;

import transformador.formatoFramenet.Utils;


public class Principal2 {

	/**
	 * @param args
	 * @throws TransformerException 
	 */
	public static void main(String[] args) throws TransformerException {
//		ArchivoFormatoFramenet archivFrame = new ArchivoFormatoFramenet("prueba", "/home/miguel/Escritorio/miguel/Documentos/PruebaTimeML4/eisenRawText.txt.out");
//		ArrayList<Documento> docs =archivFrame.getListaDocumentos();
//		ArrayList<Parrafo> parrafos=docs.get(0).getListaParrafos();
//		ArrayList<Oracion> oraciones=parrafos.get(0).getListaOraciones();
		
		ArchivoFormatoSalsa archivoSalsa2 = new ArchivoFormatoSalsa();
		
		GraphSalsa graph= new GraphSalsa(Utils.getGraphId());
		TerminalSalsa t=new TerminalSalsa(Utils.getTId(), "", "", "Hola");
		graph.agregarNodoTerminal(t);
		NoTerminalSalsa nt=new NoTerminalSalsa(Utils.getNtId(), "", "");
		EdgeSalsa edge = new EdgeSalsa(t, "");
		nt.agregarEdge(edge);
		graph.agregarNodoNoTerminal(nt);
		
		OracionSalsa s = new OracionSalsa(Utils.getSId(), graph, null);
		archivoSalsa2.agregarOracion(s);
		
		
		archivoSalsa2.guardarAArchivo("base.xml");
	}

}

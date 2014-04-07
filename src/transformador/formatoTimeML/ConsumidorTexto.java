package transformador.formatoTimeML;

/**
 * Superclase que implementan los elementos que consumen texto. Estos son EVENT, TIMEX3, SIGNAL. Tiene la caractetristica de tener contenido textual
 * @author miguel
 *
 */
public abstract class ConsumidorTexto {

	protected 	String contenido;
	protected	long 	start;
	protected	long 	end;
	
	public ConsumidorTexto(String contenido, long indice) {
		super();
		this.contenido = contenido;
		this.start = indice;
		this.end = indice+contenido.length();
	}

	public String getContenido() {
		return contenido;
	}

	public long getStart() {
		return start;
	}

	public long getEnd() {
		return end;
	}


}

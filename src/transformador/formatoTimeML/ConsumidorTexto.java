package transformador.formatoTimeML;

/**
 * Superclase que implementan los elementos que consumen texto. Estos son EVENT, TIMEX3, SIGNAL. Tiene la caractetristica de tener contenido textual
 * @author miguel
 *
 */
public abstract class ConsumidorTexto {

	protected 	String contenido;
	protected	Integer 	start;
	protected	Integer 	end;
	
	public ConsumidorTexto(String contenido, Integer indice) {
		super();
		this.contenido = contenido;
		this.start = indice;
		this.end = indice+contenido.length();
	}

	public String getContenido() {
		return contenido;
	}

	public Integer getStart() {
		return start;
	}

	public Integer getEnd() {
		return end;
	}


}

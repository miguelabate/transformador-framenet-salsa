package transformador.formatoTimeML;

public class TextoSinMarcarTimeML extends ConsumidorTexto {

	public TextoSinMarcarTimeML(String contenido, Integer indice) {
		super(contenido, indice);
	}

	@Override
	public TipoConsumidorTexto getTipoConsumidorTexto() {
		return TipoConsumidorTexto.TEXTO_SIN_MARCAR;
	}
	
	@Override
	public void generarTagTimeML(StringBuilder resultado, int indiceTexto,char caracterEnIndice,
			ConsumidorTexto consumidorEncontrado) {
		if(consumidorEncontrado.getStart()==indiceTexto){
			resultado.append("");
			resultado.append(caracterEnIndice);
		} else if(consumidorEncontrado.getEnd()-1==indiceTexto){
			resultado.append(caracterEnIndice);
			resultado.append("");
		}else{//estoy en el medio de un elemento event
			resultado.append(caracterEnIndice);
		}
	}
}

package transformador.formatoTimeML;

public interface ReferenciablePorLink {

	public Boolean esIgualA(ReferenciablePorLink otroReferenciable);
	public Integer distanciaA(ReferenciablePorLink otroReferenciable);
	public Integer getStart();
}

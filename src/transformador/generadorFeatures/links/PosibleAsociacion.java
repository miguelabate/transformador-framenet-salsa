package transformador.generadorFeatures.links;

import transformador.formatoTimeML.ReferenciablePorLink;

public class PosibleAsociacion {
	private ReferenciablePorLink referenciablePorLinkA;
	private ReferenciablePorLink referenciablePorLinkB;
	
	public PosibleAsociacion(ReferenciablePorLink referenciablePorLinkA,
			ReferenciablePorLink referenciablePorLinkB) {
		super();
		this.referenciablePorLinkA = referenciablePorLinkA;
		this.referenciablePorLinkB = referenciablePorLinkB;
	}

	public ReferenciablePorLink getreferenciablePorLinkA() {
		return referenciablePorLinkA;
	}

	public void setreferenciablePorLinkA(ReferenciablePorLink referenciablePorLinkA) {
		this.referenciablePorLinkA = referenciablePorLinkA;
	}

	public ReferenciablePorLink getreferenciablePorLinkB() {
		return referenciablePorLinkB;
	}

	public void setreferenciablePorLinkB(ReferenciablePorLink referenciablePorLinkB) {
		this.referenciablePorLinkB = referenciablePorLinkB;
	}
	
	/**
	 * si es positivo es que referenciablePorLinkB esta adelante de A. negativo, lo contrario.
	 * @return
	 */
	public Integer getDistanciaEntreReferencias(){
		return this.referenciablePorLinkA.distanciaA(referenciablePorLinkB);
	}
}

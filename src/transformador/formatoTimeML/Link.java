package transformador.formatoTimeML;

import transformador.formatoTimeML.types.LinkType;

public abstract class Link {

	protected String lid;

	public Link(String lid) {
		super();
		this.lid = lid;
	}

	public String getLid(){
		return this.lid;
	}

	public abstract LinkType obtenerTipoDeLink();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lid == null) ? 0 : lid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Link other = (Link) obj;
		if (lid == null) {
			if (other.lid != null)
				return false;
		} else if (!lid.equals(other.lid))
			return false;
		return true;
	}
}

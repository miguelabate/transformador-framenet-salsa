package transformador.common;

public class ClaveDeReferenciable implements Comparable<ClaveDeReferenciable>{

	private Integer start;
	private Integer end;
	
	public ClaveDeReferenciable(Integer start, Integer end) {
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	public String toString() {
		return start + "-" + end;
	}

	public boolean estaIncluidoEn(ClaveDeReferenciable o) {
		if(o.getStart()<=this.getStart()&&o.getEnd()>=this.getEnd())return true;
		return false;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
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
		ClaveDeReferenciable other = (ClaveDeReferenciable) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

	/**
	 * Ordena por el indice de inicio
	 */
	@Override
	public int compareTo(ClaveDeReferenciable o) {
		if(this.start<o.start)return -1;
		else if(this.start>o.start)return 1;
		else
		return 0;
	}
	
}

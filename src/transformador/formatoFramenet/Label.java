package transformador.formatoFramenet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.w3c.dom.Element;

import transformador.ClaveDeReferenciable;

public class Label {
	
	int 	start;//son el start y end explicito que aparece en framenet. El end siempre termina uno antes.
	int 	end;
	String	name;
	String itype; //a veces aparece cuando no esta el FE explicito. INI, CNI, DNI
	Frame	framePadre;//el frame al que pertenece el label
	
	public Label(Element label, Frame frame) {
		if(label.hasAttribute("itype")){
			this.itype=label.getAttribute("itype");
		}else{
			this.start = Integer.valueOf(label.getAttribute("start"));
			this.end = Integer.valueOf(label.getAttribute("end"));
		}
		this.name = label.getAttribute("name");
		this.framePadre=frame;
	}

	public String getName() {
		return name;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public Frame getFramePadre() {
		return framePadre;
	}

	/**
	 * un equals a medias que solo compara indices... sirve para el generador de features
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + end;
		result = prime * result + start;
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
		Label other = (Label) obj;
		if (end != other.end)
			return false;
		if (start != other.start)
			return false;
		return true;
	}
	
	/**
	 * Obtiene todos los labesl que engloban a este label
	 * @param unLabel
	 * @return
	 */
	public ArrayList<Label> obtenerLabelsQueEngloban(){
		ArrayList<Label> resultado = new ArrayList<Label>();
		ClaveDeReferenciable clave=new ClaveDeReferenciable(this.getStart(),this.getEnd()+1);
		Iterator<ClaveDeReferenciable> it=this.framePadre.getOracionALaQuePertenece().getTablaReferenciables().keySet().iterator();
		while (it.hasNext()) {
			ClaveDeReferenciable claveTabla = it.next();
			if(clave.estaIncluidoEn(claveTabla))resultado.addAll((List) this.framePadre.getOracionALaQuePertenece().getTablaReferenciables().get(claveTabla));
		}
		CollectionUtils.filter(resultado, new Predicate() {
			
			@Override
			public boolean evaluate(Object arg0) {
				return ((Label)arg0).getName()!=Label.this.getName();
			}
		});
//		resultado.remove(this);
		return resultado;
	}

	@Override
	public String toString() {
		return name;
	}



}

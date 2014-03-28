package transformador.formatoDefinicionFrame;

public class DefinicionFERelation {
	private long ID;
	private DefinicionFrameElement subFrameElement;
	private DefinicionFrameElement superFrameElement;
	private DefinicionFrameRelation frameRelation;
	
	public DefinicionFERelation(String id,DefinicionFrameRelation frameRelation,
			DefinicionFrameElement subFrameElement,
			DefinicionFrameElement superFrameElement) {
		super();
		ID = Long.valueOf(id);
		this.subFrameElement = subFrameElement;
		this.superFrameElement = superFrameElement;
		this.frameRelation = frameRelation;
	}

	public long getID() {
		return ID;
	}

	public DefinicionFrameElement getSubFrameElement() {
		return subFrameElement;
	}

	public DefinicionFrameElement getSuperFrameElement() {
		return superFrameElement;
	}

	public DefinicionFrameRelation getFrameRelation() {
		return frameRelation;
	}
}

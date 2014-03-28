package transformador.formatoDefinicionFrame;

public class DefinicionFrameRelation {
	private long ID;
	private DefinicionRelationType type;
	private DefinicionFrame subFrame;
	private DefinicionFrame superFrame;

	public DefinicionFrameRelation(String id, DefinicionRelationType type,
			DefinicionFrame subFrame, DefinicionFrame superFrame) {
		ID = Long.valueOf(id);
		this.type = type;
		this.subFrame = subFrame;
		this.superFrame = superFrame;
	}

	public long getID() {
		return ID;
	}

	public DefinicionRelationType getType() {
		return type;
	}

	public DefinicionFrame getSubFrame() {
		return subFrame;
	}

	public DefinicionFrame getSuperFrame() {
		return superFrame;
	}

	@Override
	public String toString() {
		return "type=" + type.getName() + ", "+type.getSubFrameName()+"="
				+ subFrame + ", "+type.getSuperFrameName()+"=" + superFrame;
	}
}

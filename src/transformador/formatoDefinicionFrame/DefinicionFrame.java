package transformador.formatoDefinicionFrame;

import java.util.ArrayList;


public class DefinicionFrame {

	private long ID;
	private String name;
	private ArrayList<DefinicionFrameElement> frameElements;
	
	public DefinicionFrame(String id, String name) {
		this.ID=Long.valueOf(id);
		this.name=name;
	}

	public long getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public ArrayList<DefinicionFrameElement> getFrameElements() {
		return frameElements;
	}

	public void setFrameElements(ArrayList<DefinicionFrameElement> frameElements) {
		this.frameElements = frameElements;
	}

	@Override
	public String toString() {
		return  name ;
	}
}

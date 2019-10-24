package pcbe1;

import java.util.HashMap;

public class Settlement extends Objective {

	public Settlement() {
		this.points = 1;
		this.objectiveResourceMap = new HashMap<Resource, Integer>();
		this.objectiveResourceMap.put(new Wood(), 1);
		this.objectiveResourceMap.put(new Stone(), 1);
	}

}

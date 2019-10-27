package pcbe1;

import java.util.HashMap;

public class Town extends Objective {

	public Town() {
		this.points = 2;
		this.objectiveResourceMap = new HashMap<Resource, Integer>();
		this.objectiveResourceMap.put(new Brick(), 1);
		this.objectiveResourceMap.put(new Wood(), 1);
		this.objectiveResourceMap.put(new Stone(), 1);
	}

}

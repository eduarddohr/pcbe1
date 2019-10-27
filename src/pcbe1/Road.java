package pcbe1;

import java.util.HashMap;
import java.util.Map;

public class Road extends Objective{

	public Road() {
		this.points = 0;
		this.objectiveResourceMap = new HashMap<Resource, Integer>();
		this.objectiveResourceMap.put(new Brick(), 1);
		this.objectiveResourceMap.put(new Wood(), 1);
	}

}

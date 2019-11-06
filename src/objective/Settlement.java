package objective;

import java.util.ArrayList;

import resource.Stone;
import resource.Wood;

public class Settlement extends Objective {

	public Settlement() {
		this.points = 1;
		this.objectiveResources = new ArrayList<>();
		this.objectiveResources.add(new Stone());
		this.objectiveResources.add(new Wood());
	}

}

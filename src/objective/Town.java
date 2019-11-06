package objective;

import java.util.ArrayList;

import resource.Brick;
import resource.Resource;
import resource.Stone;
import resource.Wood;

public class Town extends Objective {

	public Town() {
		this.points = 2;
		this.objectiveResources = new ArrayList<Resource>();
		this.objectiveResources.add(new Brick());
		this.objectiveResources.add(new Wood());
		this.objectiveResources.add(new Stone());
		
	}

}

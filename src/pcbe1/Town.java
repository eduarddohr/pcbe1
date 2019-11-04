package pcbe1;

import java.util.ArrayList;

public class Town extends Objective {

	public Town() {
		this.points = 2;
		this.objectiveResources = new ArrayList<Resource>();
		this.objectiveResources.add(new Brick());
		this.objectiveResources.add(new Wood());
		this.objectiveResources.add(new Stone());
		
	}

}

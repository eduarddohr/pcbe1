package pcbe1;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Player extends Thread{
	
	private String name;
	private Map<Objective, Integer> objectives;
	private ArrayList<Resource> resources;
	
	private void recieveResource(Resource res){
		resources.add(res);
	}
	
	private void giveResource(Resource res){
		resources.remove(resources.indexOf(res));
		
	}
	
	private int calculatePoints(){
		int totalPoints = 0;
		for (Map.Entry<Objective, Integer> entry : objectives.entrySet()) {
			totalPoints += entry.getKey().getPoints() * entry.getValue();
		}
		return totalPoints;
	}
	//asta e practic takeTurn
	public void run(){
		
		try {
			
		}
		catch(Exception e) {
			 System.out.println ("Exception is caught"); 
		}
	}
	
	private void getRandomResources(){ 
		Random r = new Random();
		for (int nr : r.ints(2, 0, 3).toArray()) {
			switch(nr) {
			case 0:
				giveResource(new Brick());
				break;
			case 1:
				giveResource(new Stone());
				break;
			case 2:
				giveResource(new Wood());
				break;
			}
		}
	}

}

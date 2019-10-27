package pcbe1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Player extends Thread{
	
	private String name;
	private Map<Objective, Integer> objectives;
	private ArrayList<Resource> resources;
	
	public Player(String name) {
		this.name=name;
		this.objectives =  new HashMap<Objective, Integer>();
		//dam 3 resurse random la inceput la fiecare jucator
		getRandomResources(3);
	}
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
	//cred ca asta e practic takeTurn, dar nu stiu :))
	public void run(){
		
		try {
			
		}
		catch(Exception e) {
			 System.out.println ("Exception is caught"); 
		}
	}
	
	private void getRandomResources(int nrRes){ 
		Random r = new Random();
		for (int nr : r.ints(nrRes, 0, 3).toArray()) {
			switch(nr) {
			case 0:
				recieveResource(new Brick());
				break;
			case 1:
				recieveResource(new Stone());
				break;
			case 2:
				recieveResource(new Wood());
				break;
			}
		}
	}

}

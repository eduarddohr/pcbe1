package pcbe1;

import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Semaphore;
import java.util.*;


public class Game {

	public static ArrayList<Resource> bricks = new ArrayList<Resource>();
	public static ArrayList<Resource> wood = new ArrayList<Resource>();
	public static ArrayList<Resource> stones = new ArrayList<Resource>();
	//lista IMUTABILA cu obiective puse in ordinea importantei lor
	final public static ArrayList<Objective> objectiveslist = new ArrayList<Objective>(Arrays.asList(new Town(),new Settlement(),new Road())); 
	private ArrayList<Trade> marketplace;
	final static int nrPlayers = 4;
	
	private boolean checkIfCanTrade(){
		return true;
	}
	
	public Game() {
		for(int i = 0; i < 20; i++) {
			bricks.add(new Brick());
			wood.add(new Wood());
			stones.add(new Stone());
		}
	}
	

	
	public static void main(String[] argv){
		Game game = new Game();
		Semaphore semaphore = new Semaphore(1);
		for(int i = 0; i < nrPlayers; i++) {
			Player player = new Player(semaphore, "player " + (i+1));
			player.start();
		}	
		
	}
	
	//test
	
//	public static void main(String[] argv) {
//		ArrayList<Resource> having = new ArrayList<>();
//		Town town = new Town();
//		having.add(new Brick());
//		having.add(new Wood());
//		Map<String,ArrayList<Resource>> can = town.checkIfCanBuild(having);
//		System.out.println(can.get("needed").toString());
//		System.out.println(can.get("locked").toString());
//	}
}

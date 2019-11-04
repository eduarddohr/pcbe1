package pcbe1;

import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Game {

	public static ArrayList<Resource> bricks;
	public static ArrayList<Resource> wood;
	public static ArrayList<Resource> stones;
	//lista IMUTABILA cu obiective puse in ordinea importantei lor
	final public static ArrayList<Objective> objectiveslist = (ArrayList<Objective>) Collections.unmodifiableList(new ArrayList<Objective>(Arrays.asList(new Town(),new Settlement(),new Road()))); 
	private ArrayList<Trade> marketplace;
	final static int nrPlayers = 4;
	
	private boolean checkIfCanTrade(){
		return true;
	}
	
	public static void main(String[] argv){
		
		for(int i = 0; i < nrPlayers; i++) {
			Player player = new Player("cineva");
			player.start();
		}
	}
}

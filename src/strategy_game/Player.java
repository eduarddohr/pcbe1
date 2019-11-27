package strategy_game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Semaphore;

import objective.Objective;
import resource.Resource;

public class Player extends Thread {

	private String name;
	private Semaphore semaphore;
	private Map<Objective, Integer> objectives;
	private ArrayList<Resource> resources = new ArrayList<Resource>();

	public Player(Semaphore semaphore, String name) {
		this.semaphore = semaphore;
		this.name = name;
		this.objectives = new HashMap<Objective, Integer>();
		for (Objective objective : Game.objectiveslist)
			try {
				this.objectives.put(objective.getClass().newInstance(), 0);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Opa");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Oopsie");
			}
	}

	private void giveResource(Resource res) {
		resources.remove(resources.indexOf(res));
	}

	private int calculatePoints() {
		int totalPoints = 0;
		for (Map.Entry<Objective, Integer> entry : objectives.entrySet()) {
			totalPoints += entry.getKey().getPoints() * entry.getValue();
		}
		return totalPoints;
	}

	public void run() {

//			 System.out.println("Hello, me is player and will implement the whole game -"
//			 + name);
		ArrayList<Objective> objectiveslist = Game.objectiveslist;
		Map<String, ArrayList<Resource>> nLResorcesMap;

		while (!Thread.currentThread().isInterrupted() && Game.getWon() == false) {
			try {
				// System.out.println("resorces: " + name + " " + resources.toString());
				ArrayList<Objective> list = new ArrayList<Objective>(Game.objectiveslist);
				Collections.shuffle(list);
				System.out.println(name + " " + list);
				for (Objective objective : list) {
					getRandomResources(1);
					if (Game.getWon()) {
						break;
					}
					System.out.println(
							System.currentTimeMillis() + " " + name + " tries to build " + objective.toString());
					nLResorcesMap = objective.checkIfCanBuild(new ArrayList<Resource>(resources));
					ArrayList<Resource> needed = nLResorcesMap.get("needed");
					ArrayList<Resource> locked = nLResorcesMap.get("locked");
					ArrayList<Resource> remaining = nLResorcesMap.get("remaining");
					// System.out.println(name);
					if (needed.isEmpty() && Game.getWon() == false) {
						System.out.println(
								System.currentTimeMillis() + " " + name + " can build " + objective.toString());
						buildObjective(remaining, locked, objective);
					} else {
						for (Resource res : needed) {
							if (remaining.isEmpty()) {
								// nu plaseaza cerere, e sarac
							} else {
								Resource exchangeResource = Game.getExchangeResource(res);
								if (exchangeResource == null) {
									// plaseaza cerere
									Trade trade = new Trade(this.name, remaining.get(0), res); // adaugam trade cu ne
																								// trebuie si dam prima
																								// care e in plus
									System.out.println(trade);
									Game.addTrade(trade);
									Random rand = new Random();
									sleep(rand.nextInt(7000));
									if (Game.wasTradeUsed(trade)) {
										System.out.println(name + " has: " + resources.toString());
										System.out.println("-----" + name + " gave " + remaining.get(0) + " for " + res);
										this.resources.add(res);
										this.resources.remove(remaining.get(0));
										System.out.println(name + " has: " + resources.toString());
									}
								} else {
									boolean exchangeResonse = decideIfCanExchange(res, exchangeResource, remaining);
									if (exchangeResonse) {
										System.out.println(name + " has: " + resources.toString());
										System.out.println("-----" + name + " recieved " + res + "and gave " + exchangeResource + " in return");
										this.resources.add(res);
										this.resources.remove(exchangeResource);
										System.out.println(name + " has: " + resources.toString());
									}
								}
							}
						}
					}
				}
			}

			catch (InterruptedException ex) {
				System.out.println(this.name + "exited the game");
				return;
			} catch (Exception e) {
				System.out.println("ERRROOOORRRR" + e.getClass() + " " + this.name + e.getStackTrace()[0].toString());
				return;
			}
		}
		System.out.println(this.name + "exited the game");
	}

	private boolean decideIfCanExchange(Resource needed, Resource requestResource, ArrayList<Resource> remaining)
			throws InterruptedException {
		boolean response = false;
		if (remaining.contains(requestResource)) {
			Trade trade = new Trade(this.name, requestResource, needed);
			response = Game.makeTrade(trade);
		}

		return response;
	}


	private void getRandomResources(int nrRes) throws InterruptedException {
		ArrayList<Resource> newResources = Game.getRandomResources(nrRes);
		resources.addAll(newResources);
	}

	private void buildObjective(ArrayList<Resource> remaining, ArrayList<Resource> locked, Objective objective)
			throws InterruptedException {
		this.resources = remaining;
		int value = objectives.get(objective);
		objectives.put(objective, value + 1);
		Game.giveBackResources(locked);
		System.out.println(System.currentTimeMillis() + " " + this.name + " built " + objective.toString());

		int points = calculatePoints();
		if (points >= Game.necessaryPointsToWin && Game.getWon() == false)
			Game.wonGame(this.name, points);

	}

	public void cancel() {
		interrupt();
	}
}

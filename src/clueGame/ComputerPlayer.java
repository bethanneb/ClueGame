/*
 * author: Elizabeth Bauch
 * author: Danella Bunavi
 */

package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {

	private Set<BoardCell> seen = new HashSet<>();
	private Set<Card> seenCards = new HashSet<>();

	//NEW
	Solution createdSoln = new Solution(); 
	private Solution accusation = new Solution();

	public ComputerPlayer() {
		super();
	}

	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}

	public ComputerPlayer(String name, String color, int r, int c) {
		super(name, color, r, c);
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {

		ArrayList<BoardCell> availablePaths = new ArrayList<BoardCell>();
		ArrayList<BoardCell> availableDoorways = new ArrayList<BoardCell>();
		
		for (BoardCell cell : targets)
		{
			if (cell.isDoorway())
			{
				availableDoorways.add(cell);
			}
			if (cell.isWalkway() || cell.isRoom())
			{
				availablePaths.add(cell);
			}
		}

		if (!availableDoorways.isEmpty()){
			int options = availableDoorways.size();
			Random rand = new Random(); 
			int selected = rand.nextInt(options); 
			//System.out.println( options + " doorways exist");
			return availableDoorways.get(selected);
			
		}else{
			int options = availablePaths.size();
			Random rand = new Random();
			int selected = rand.nextInt(options);
			//System.out.println("A pathway is available");
			return availablePaths.get(selected);
		}
	} 

	public void createSuggestion(BoardCell cell, ArrayList<Card> peopleArray, ArrayList<Card> weaponsArray, Set<String> rooms, ComputerPlayer player) {
		//selecting the room suggestion based on the current location of the player
		ArrayList<Card> people = new ArrayList<Card>();
		ArrayList<Card> weapons = new ArrayList<Card>();
		people = peopleArray;
		weapons = weaponsArray;
		char roomInitial = cell.getInitial();
		String room = "";
		//System.out.println("room size" + rooms.size());
		for (String r : rooms)
		{
			if (roomInitial == r.charAt(0))
			{
				room = r;
			} 
		}
		Card person = new Card("", CardType.PERSON);
		Card weapon = new Card("", CardType.PERSON);
		Set<Card> seen = new HashSet<Card>();
		seen = player.getSeenCards();
		boolean exit = true;
		while (exit)
		{
			Random rand = new Random();
			if (people.size() > 0)
			{
				int select = rand.nextInt(people.size());
				person = people.get(select);
			}
			else person = null;

			if ( weapons.size() > 0)
			{
				int select = rand.nextInt(weapons.size());
				weapon = weapons.get(select);
			}
			else weapon = null;
			//Hello

			// handle looking at seenCards and making sure to not 
			if ( seen.contains(person)) people.remove(person);
			else exit = false;
			if ( seen.contains(weapon)) weapons.remove(weapon);
			else exit = false;

			if ( seen.size() == 12)
			{
				//you can't pick a card you have already seen and there are only 12 cards total
				createdSoln.setAnswerKeyRoom(room);
				createdSoln.setAnswerKeyPerson(null);
				createdSoln.setAnswerKeyWeapon(null);
			}
		}
		// make the suggestion using the Solution class
		if ( person != null) createdSoln.setAnswerKeyPerson(person.getCardName());
		else  createdSoln.setAnswerKeyPerson(null);
		if (weapon != null ) createdSoln.setAnswerKeyWeapon(weapon.getCardName());
		else createdSoln.setAnswerKeyWeapon(null);
		createdSoln.setAnswerKeyRoom(room);
		String done = " done";
		//System.out.println(done);
	}
	@Override
	public Card disproveSuggestion(Solution soln) {

		//String p1, p2, w1, w2, r1, r2;  

		Set<Card> myCards = new HashSet<Card>(); 
		myCards = getMyCards(); 
		ArrayList<Card> cardsFound = new ArrayList<Card>();
		//System.out.println(myCards.size());
		for (Card found: myCards) {
			if (soln.getPerson() == found.getCardName()) {
				//System.out.println("1st");
				cardsFound.add(found); 
			}
			if (soln.getWeapon() == found.getCardName()) {
				//System.out.println("2nd");
				cardsFound.add(found); 
			}
			if (soln.getRoom() == found.getCardName()) {
				//System.out.println("3rd");
				cardsFound.add(found); 
			}

		}

		if (cardsFound.size() == 0) {
			return null;
		}

		else if (cardsFound.size() == 1) { 
			return cardsFound.get(0); 
		}

		else {
			Random rand = new Random(); 
			int possition = rand.nextInt(cardsFound.size());

			return cardsFound.get(possition); 
		}

	}

	public void setAccusation(Solution nextAccusation)
	{
		this.accusation.setAnswerKeyPerson(nextAccusation.getPerson());
		this.accusation.setAnswerKeyRoom(nextAccusation.getRoom());
		this.accusation.setAnswerKeyWeapon(nextAccusation.getWeapon());
	}

	public Solution getAccusation() { return this.accusation; }
	
	public Solution getCreatedSoln() { 
		return createdSoln; 
	}
	
	public boolean isAccusationReady()
	{
		if( accusation.getRoom() != "" && accusation.getPerson() != "" && accusation.getWeapon() != "") { return true; }
		return false;
	}

	
	//OLD
	public void addSeenCard(Card card) {
		seenCards.add(card);
	}

	@Override
	public boolean isComputer() {
		return true;
	}
	//I also commented this out because we don't know what this is suppose to do yet
	//	public createSuggestion(TBD) {
	//		
	//	}


}
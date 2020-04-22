/*
 * author: Elizabeth Bauch
 * author: Danella Bunavi
 */

package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {

	private Set<BoardCell> seen = new HashSet<>();
	private Set<Card> seenCards = new HashSet<>();

	//NEW
	Solution createdSoln = new Solution(); 
	private Solution accusation = new Solution();
	private char lastRoom;

	public ComputerPlayer() {
		super();
	}

	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}

	public ComputerPlayer(String name, String color, int r, int c) {
		super(name, color, r, c);
	}

	@Override
	public BoardCell pickLocation(Set<BoardCell> targets) {

		ArrayList<BoardCell> availablePaths = new ArrayList<BoardCell>();
		ArrayList<BoardCell> availableDoorways = new ArrayList<BoardCell>();
		

		for (BoardCell cell : targets)
		{
			//if it is the room it was just in, will not go back in
			if (cell.getInitial() == lastRoom) {
				continue;
			}
			//if just getting to the door, will go into the room
			else if (cell.isDoorway())
			{
				availableDoorways.add(cell);
			}
			else
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
	
	@Override
	public void setLastRoom(BoardCell cell) {
		lastRoom = cell.getInitial();
	}
	
	public char getLastRoom() {
		return lastRoom;
	}

	@Override
	public void createSuggestion(BoardCell cell, ArrayList<Card> peopleArray, ArrayList<Card> weaponsArray, Map<Character, String> legend, Player player) {
		//selecting the room suggestion based on the current location of the player
		char roomInitial = cell.getInitial();
		String room = legend.get(roomInitial);

		Card person;// = new Card("", CardType.PERSON);
		Card weapon;// = new Card("", CardType.PERSON);
		Set<Card> seen = new HashSet<Card>();
		seen = player.getSeenCards();
		boolean exit = true;
		while (exit)
		{
			Random rand = new Random();
			if(peopleArray.size() > 0){
				int select = rand.nextInt(peopleArray.size());
				person = peopleArray.get(select);
				createdSoln.setPerson(person.getCardName());
			}
			else person = null;

			if(weaponsArray.size() > 0){
				int select = rand.nextInt(weaponsArray.size());
				weapon = weaponsArray.get(select);
				createdSoln.setWeapon(weapon.getCardName());
			}
			else weapon = null;

			// handle looking at seenCards and making sure to not 
			if(seen.contains(person)) {
				peopleArray.remove(person);
			}
			else exit = false;
			if(seen.contains(weapon)) {
				weaponsArray.remove(weapon);
			}
			else exit = false;


			if (seen.size() == 16)
			{
				//you can't pick a card you have already seen and there are only 12 cards total
				createdSoln.setRoom(room);
				createdSoln.setPerson(null);
				createdSoln.setWeapon(null);
			}
		}
		// make the suggestion using the Solution class
		//if ( person != null) c
		//else  createdSoln.setAnswerKeyPerson(null);
		//if (weapon != null ) 
		//else createdSoln.setAnswerKeyWeapon(null);
		createdSoln.setRoom(room);
		//String done = " done";
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
		this.accusation.setPerson(nextAccusation.getPerson());
		this.accusation.setRoom(nextAccusation.getRoom());
		this.accusation.setWeapon(nextAccusation.getWeapon());
	}


	public Solution getAccusation() { return this.accusation; }
	
	@Override
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


}
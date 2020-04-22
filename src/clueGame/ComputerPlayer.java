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

		Card person;
		Card weapon;
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
		createdSoln.setRoom(room);

	}

	@Override
	public void setAccusation(Solution nextAccusation)
	{
		accusation.setPerson(nextAccusation.getPerson());
		accusation.setRoom(nextAccusation.getRoom());
		accusation.setWeapon(nextAccusation.getWeapon());
	}

	@Override
	public Solution getAccusation() {
		return accusation; 
	}
	
	@Override
	public Solution getCreatedSoln() { 
		return createdSoln; 
	}
	
	@Override
	public boolean isAccusationReady()
	{
		if(accusation.getRoom() != "" && accusation.getPerson() != "" && accusation.getWeapon() != "") { 
			return true; 
		}
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
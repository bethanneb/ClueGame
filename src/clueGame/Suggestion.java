package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.ItemSelectable;

public class Suggestion extends JPanel {

	private Board board;
	JComboBox peopleList;  
	JComboBox weaponsList; 
	String roomName; 
	int state; 
	private String peopleAnswer = "";
	private String roomAnswer = "";
	private String weaponAnswer = "";
	private String currentResults;
	String[] people = {"Michael Scott", "Dwight Schrute", "Jim Halpert", "Pam Halpert", "Kevin Malone", "Andy Bernard", "Oscar Martinez", "Angela Martin"};
	String[] weapons = {"Poison Dart", "Knife", "Taser", "Boomerang", "Samurai Sword", "Pepper Spray", "Crossbow", "Nunchucks"};
	Solution humanSuggestedSolution = new Solution();

	public Suggestion(String r) { 

		board = Board.getInstance();
//		board.setConfigFiles("OurClueLayout.csv", "OurClueLegend.txt");
//		board.setCardFiles("Players.txt", "Cards.txt");
//		board.initialize();
		//board.buildGamePlayers();

		roomName = r; // Get current room from board 

		peopleList = new JComboBox(people);
		weaponsList = new JComboBox(weapons);

		setBorder(new TitledBorder (new EtchedBorder(), "Suggestion"));
		setLayout(new GridLayout(4,1));
		
		JPanel panel = peopleGuess();
		JPanel panel1 = weaponsGuess();
		JPanel panel2 = roomsGuess(); 
		panel.setPreferredSize(new Dimension(400, 100));
		panel1.setPreferredSize(new Dimension(400, 100));
		panel2.setPreferredSize(new Dimension(400, 100));

		add(panel);
		add(panel1);		
		add(panel2);

		JPanel buttons = buttonPanel();
		add(buttons);
	}

	public void setCurrentRoom(String r) { 
		this.roomName = r; 
	}
	
	private JPanel peopleGuess() {
		JComboBox peopleList = new JComboBox(people); 
		JPanel panel = new JPanel();
		panel.add(peopleList);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People Guess"));
		return panel;

//		JPanel panel = new JPanel();
//		panel.add(peopleList);
//		panel.setBorder(new TitledBorder (new EtchedBorder(), "People Guess"));
//		return panel;
	}

	private JPanel weaponsGuess() { 
		JComboBox weaponsList = new JComboBox(weapons); 
		JPanel panel = new JPanel();
		panel.add(weaponsList);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		return panel; 

//		JPanel panel = new JPanel();
//		panel.add(weaponsList);
//		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
//		return panel; 
	}

	private JPanel roomsGuess() {
		JPanel panel = new JPanel();

		JTextField textName = new JTextField(roomName); 
		textName.setEditable(false);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		panel.add(textName);

		return panel; 		
	}

	private JPanel buttonPanel() {

		JButton accept = new JButton("Submit"); 
		accept.addActionListener(new submitButtonListener() );
		JButton cancel = new JButton("Cancel"); 
		cancel.addActionListener(new cancelButtonListener());   

		JPanel panel = new JPanel(); 
		panel.add(accept);
		panel.add(cancel);
		return panel; 
	}
	
	public void setSuggestionInternal( String people, String room, String weapon) {
		System.out.println(" PLEASE: " + people);
		this.peopleAnswer = people;
		System.out.println(" WHY: " + this.peopleAnswer);
		this.roomAnswer = room;
		this.weaponAnswer = weapon;
	}

	private class cancelButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) { 
			System.out.println("Canceling suggestion class "); 
			board.closeMyFrame();

		}
	}

	public class submitButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) { 


			int foundP = peopleList.getSelectedIndex();
			peopleAnswer = people[foundP]; 
			int foundW = weaponsList.getSelectedIndex();
			weaponAnswer = weapons[foundW];
			roomAnswer = roomName; 
			setSuggestionInternal(peopleAnswer, roomAnswer, weaponAnswer);
			System.out.println("Answer Found: " + peopleAnswer + ", " + roomAnswer + " room, " + weaponAnswer);
			
			humanSuggestedSolution = new Solution (peopleAnswer, roomAnswer, weaponAnswer);

			// 1. Show the current suggestion on the ControlGUI , X
			// 2. Disprove the Human suggestion
			// need the current computer players that are active in Board
			Set<ComputerPlayer> computer = new HashSet();
			ArrayList<Card> foundCards = new ArrayList<Card>(); 
			computer = board.getComputerPlayers();
			for(ComputerPlayer tempPlayer: computer) {
				if (tempPlayer == computer) {
					continue;  
				}
				else { 
					// if a card is found by another player, the card is added to the ArrayList of cards
					Card temp = tempPlayer.disproveSuggestion(humanSuggestedSolution); 
					if ( temp == null) {}
					else { foundCards.add(temp); }
					
				}
			}
			
			// selecting a random number for selecting a found Card

			if (foundCards.size() == 0) { /* if the size of FoundCards = 0, that means not cards were found to disprove the suggestion */
				// store the suggestion that was found to be the next accusation. 
				currentResults = "no new clue";
			}
			else { 
				Random rand = new Random(); 
				int location = rand.nextInt(foundCards.size()); 
				//System.out.println("Found other cards that disprove the suggestion. ArrayList size: " + foundCards.size() );
				if (foundCards.get(location) != null) {
					currentResults = foundCards.get(location).getCardName();
					//foundCards.get(location); 
				}
				else {
					// if null, need to choose another location to go to
					currentResults = "no new clue"; 
				}
			}
			// 3. If the suggestion is disproved, show the card that disproved it
			// 		otherwise, display "no new clue"
			// 4. Display results to the ControlGUI
			
			board.closeMyFrame();

		}
		
		
	}

	public String getCurrentHumanGuess() {
		//System.out.println(this.humanSuggestedSolution.getPerson() + ", " + this.humanSuggestedSolution + ", " + this.humanSuggestedSolution);
		System.out.println("Answer Found: " + peopleAnswer + ", " + roomName + " " + weaponAnswer);
		return  peopleAnswer + ", " + roomName + " room, " + weaponAnswer;
	}


	public String getCurrentHumanResult() {
		return this.currentResults;
	}

}

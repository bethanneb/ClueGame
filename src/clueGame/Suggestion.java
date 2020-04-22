package clueGame;

import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.metal.MetalIconFactory;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

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

		//JTextField suggestion = new JTextField();
		//setBorder(new TitledBorder (new EtchedBorder(), "Suggestion"));
		setBorder(BorderFactory.createTitledBorder(null, "Suggestion", TitledBorder.CENTER, TitledBorder.TOP, new Font("Lucida Grande",Font.PLAIN,16), Color.black));
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
		peopleList.setFont(new java.awt.Font("Lucida Grande", Font.PLAIN, 20));
		JPanel panel = new JPanel();
		panel.add(peopleList);
		//panel.setBorder(new TitledBorder (new EtchedBorder(), "People Guess"));
		
		//panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWEED), "People Guess"));
		panel.setBorder(BorderFactory.createTitledBorder(null, "People Guess", TitledBorder.LEFT, TitledBorder.TOP, new Font("Lucida Grande",Font.PLAIN,16), Color.black));
		
		return panel;
<<<<<<< HEAD

=======
>>>>>>> refs/remotes/origin/master
	}

	private JPanel weaponsGuess() { 
		weaponsList.setFont(new java.awt.Font("Lucida Grande", Font.PLAIN, 20));
		JPanel panel = new JPanel();
		panel.add(weaponsList);
		//panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		panel.setBorder(BorderFactory.createTitledBorder(null, "Weapon Guess", TitledBorder.LEFT, TitledBorder.TOP, new Font("Lucida Grande",Font.PLAIN,16), Color.black));
		return panel; 
<<<<<<< HEAD

=======
>>>>>>> refs/remotes/origin/master
	}

	private JPanel roomsGuess() {
		JPanel panel = new JPanel();
		JTextField textName = new JTextField(roomName); 
		textName.setFont(new java.awt.Font("Lucida Grande", Font.PLAIN, 20));
		textName.setEditable(false);
		//panel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		panel.setBorder(BorderFactory.createTitledBorder(null, "Room Guess", TitledBorder.LEFT, TitledBorder.TOP, new Font("Lucida Grande",Font.PLAIN,16), Color.black));
		
		panel.add(textName);
		return panel; 		
	}

	private JPanel buttonPanel() {

		JButton accept = new JButton("Submit"); 
		accept.addActionListener(new submitButtonListener());
		JButton cancel = new JButton("Cancel"); 
		cancel.addActionListener(new cancelButtonListener());   

		JPanel panel = new JPanel(); 
		panel.add(accept);
		panel.add(cancel);
		return panel; 
	}
	
	public void setSuggestionInternal( String people, String room, String weapon) {
		peopleAnswer = people;
		roomAnswer = room;
		weaponAnswer = weapon;
	}

	private class cancelButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) { 
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
			
			board.whoIsTheCurrentPLayer().updateGuess(getCurrentHumanGuess());
			
			humanSuggestedSolution = new Solution(peopleAnswer, roomAnswer, weaponAnswer);
			board.whoIsTheCurrentPLayer().setSuggestion(humanSuggestedSolution);
			board.handleSuggestion(board.whoIsTheCurrentPLayer());
			
			board.doneWithHuman = true;
			
			
			board.closeMyFrame();
		}
		
	}

	public String getCurrentHumanGuess() {
		return  peopleAnswer + ", " + roomName + " room, " + weaponAnswer;
	}


	public String getCurrentHumanResult() {
		return this.currentResults;
	}
	
}
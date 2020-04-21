package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Accusation extends JPanel {

	private Board board; 
	private String peopleAnswer;
	private String roomAnswer;
	private String weaponAnswer;
	private JComboBox peopleList;
	private String[] people = {"Michael Scott", "Dwight Schrute", "Jim Halpert", "Pam Halpert", "Kevin Malone", "Andy Bernard", "Oscar Martinez", "Angela Martin"}; 
	private JComboBox weaponsList;
	private String[] weapons = {"Poison Dart", "Knife", "Taser", "Boomerang", "Samurai Sword", "Pepper Spray", "Crossbow", "Nunchucks"}; 
	private JComboBox roomsList;
	private String[] rooms = {"Conference Room", "Warehouse", "Front Desk", "Vance Refrigeration", "Break Room", "Kitchen", "Jim’s Desk", "Michael’s House", "Dwight’s Desk"}; 
	
	public JFrame accusationFrame = new JFrame();
	
	public Accusation() { 
		board = Board.getInstance();
		board.setConfigFiles("OurClueLayout.csv", "OurClueLegend.txt");	
		board.setCardFiles("Players.txt", "Cards.txt");
		board.initialize();
		peopleAnswer = "";
		roomAnswer = "";
		weaponAnswer = "";
		
		peopleList = new JComboBox(people);
		weaponsList = new JComboBox(weapons); 
		roomsList = new JComboBox(rooms); 
		
		setBorder(new TitledBorder (new EtchedBorder(), "Accusation"));
		setLayout(new GridLayout(4,1));
		JPanel panel = peopleGuess();
		JPanel panel1 = weaponsGuess();
		JPanel panel2 = roomsGuess(); 
		 
		add(panel);
		add(panel1);		
		add(panel2);
		
		JPanel buttons = buttonPanel();
		add(buttons);
	}	

	private JPanel peopleGuess() {
		JPanel panel = new JPanel();
		panel.add(peopleList);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People Guess"));
		return panel;
	}
	
	private JPanel weaponsGuess() { 

		JPanel panel = new JPanel();
		panel.add(weaponsList);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		return panel; 
	}
	
	private JPanel roomsGuess() { 
		
		JPanel panel = new JPanel();
		panel.add(roomsList);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		return panel;
	}

	private JPanel buttonPanel() {
		JButton accept = new JButton("Submit"); 
		accept.addActionListener(new submitButtonListener() );    //FIXME
		JButton cancel = new JButton("Cancel"); 
		cancel.addActionListener(new cancelButtonListener());  //FIXME 
		
		JPanel panel = new JPanel(); 
		panel.add(accept);
		panel.add(cancel);
		return panel; 
	}
	
	private class cancelButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{ 
			System.out.println("Canceling suggestion class "); 
			accusationFrame.setVisible(false);
			accusationFrame.dispose();
		}
	}
	
	public void passFrame(JFrame accusationFrame)
	{ 
		this.accusationFrame = accusationFrame;
	}
	
	private class submitButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{

			int foundP = peopleList.getSelectedIndex();
			peopleAnswer = people[foundP]; 
			int foundW = weaponsList.getSelectedIndex();
			weaponAnswer = weapons[foundW];
			int foundR = roomsList.getSelectedIndex();
			roomAnswer = rooms[foundR]; 
			System.out.println("Answer Found: " + peopleAnswer + ", " + roomAnswer + ", " + weaponAnswer);
			
			Solution soln = new Solution(peopleAnswer, weaponAnswer, roomAnswer);
			
			if (board.checkAccusation(soln) == false ) { 
				board.incorrectAccusation(soln);  
			}
			else { 
				board.correctAccusation(soln);
			}
		
			accusationFrame.dispose(); 
			
		}
	}
	

}
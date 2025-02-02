  
/*
 * author: Elizabeth Bauch
 * author: Danella Bunavi
 */
package clueGame;

import javax.swing.JButton;
import javax.swing.JFrame;   //need for creating JFrame
import javax.swing.JPanel;

//import clueGame.ControlGUI.ButtonListener;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.util.*; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClueGame extends JFrame {
	private final static int HEIGHT = 700;
	private final static int WIDTH = 800;
	private static Board board;
	JPanel panel;
	int dieRoll = 0;
	private static JFrame frame;

	public ClueGame()
	{
		setTitle("Clue Game");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("OurClueLayout.csv", "OurClueLegend.txt");	
		board.setCardFiles("Players.txt", "Cards.txt");
		board.initialize();
		
		
		//file menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());

		//paintComponent is automatically be called 1 time
		panel = board;
		add(panel, BorderLayout.CENTER);

		//control panel
		JPanel control = new JPanel();
		ControlGUI guiControl = new ControlGUI(board);
		control = guiControl;
		add(control, BorderLayout.SOUTH);
		
		//card display
		CardDisplayGUI guiCard = new CardDisplayGUI(board); 
		JPanel cards = new JPanel(); 
		cards = guiCard; 
		cards.setSize(10,5);
		add(cards, BorderLayout.EAST);
	}
	
	public JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		//detective note option
		JMenuItem noteItem = new JMenuItem("Detective Notes");
		DetectiveNotesGUI notes = new DetectiveNotesGUI();
		class NoteItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				notes.setVisible(true);
			}
		}
		noteItem.addActionListener(new NoteItemListener());
		menu.add(noteItem);
		
		//exit option
		JMenuItem exitItem = new JMenuItem("Exit");
		class ExitItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		}
		exitItem.addActionListener(new ExitItemListener());
		menu.add(exitItem);
		return menu;
	}


	public JFrame returnClueBoardFrame()
	{ return this.frame; } 
	
	public static void main(String[] args) {	
		ClueGame clueObject = new ClueGame();
		frame = clueObject;
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
		Set<HumanPlayer>  humanPlayer = new HashSet<HumanPlayer>();
		humanPlayer = board.getHumanPlayer();
		String humanName = "";

		for (HumanPlayer human: humanPlayer) { 
			humanName = human.getPlayerName(); 
		}

		String message = "You are " + humanName + ", press Next Player to begin play";
		JOptionPane.showMessageDialog(frame, message, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		 
	}

}
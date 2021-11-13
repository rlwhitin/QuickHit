package main;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * This is the overarching GUI for the program window, although some of the more complicated individual UI elements, such as units, will have their own classes.
 */

public class MainGUI extends JPanel{
	static ArrayList<Unit> units;
	JFrame mainFrame;
	GridBagConstraints constraints;
	public static ChainVisualizer chainVisualizer;
	public static MacroMaker macroMaker;

	/*
	 * Just running various subfunctions to get things set up.
	 */
	MainGUI() {
		this.setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		constraints.insets.top = 15;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weighty = .5;
		mainFrame = new JFrame("QuickHit");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initializeHelpButton();
		initializeUnits();
		initializeChainVisualizer();
		initializeMacroMaker();
		mainFrame.add(this);
		mainFrame.pack();
		//mainFrame.setSize(mainFrame.getWidth(), mainFrame.getHeight()+100);
		mainFrame.setLocationRelativeTo(null);
	}

	/*
	 * Adds a help button that links to the GitHub page. 
	 */
	private void initializeHelpButton() {
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets.left = 10;
		constraints.insets.right = 10;
		JButton helpButton = new JButton("Help");
		helpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URL("https://github.com/Muspelful/QuickHit#QuickHit").toURI());
				}
				catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		this.add(helpButton, constraints);
	}

	/*
	 * Initializes the ArrayList of Units that make up the party.
	 */
	private void initializeUnits() {
		constraints.insets.left = 0;
		constraints.insets.right = 10;
		units = new ArrayList<Unit>(Constants.MAX_NUMBER_OF_UNITS);
		for(int count = 0; count < Constants.MAX_NUMBER_OF_UNITS; count++) {
			units.add(new Unit(count));
		}
		for(Unit thisUnit: units) {
			constraints.gridx += 1;
			this.add(thisUnit, constraints);
		}
	}

	/*
	 * Initializes the chain visualizer
	 */
	private void initializeChainVisualizer() {
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets.left = 20;
		constraints.insets.right = 20;
		constraints.gridheight = 25;
		constraints.gridx = 0;
		constraints.gridy = 1;
		chainVisualizer = new ChainVisualizer();
		this.add(chainVisualizer, constraints);

	}

	/*
	 * Initializes the MacroMaker
	 */
	private void initializeMacroMaker() {
		constraints.gridy = 26;
		macroMaker = new MacroMaker();
		this.add(macroMaker, constraints);
	}

	/**
	 * @return the units
	 */
	public static ArrayList<Unit> getUnits() {
		return units;
	}
	
	/*
	 * Shows the main UI window.
	 */
	private void showWindow() {
		mainFrame.setVisible(true);
	}

	/*
	 * Really just calls the constructor and then launched a window.
	 */
	public static void main(String[] args) {
		MainGUI main = new MainGUI();
		main.showWindow();
	}
}
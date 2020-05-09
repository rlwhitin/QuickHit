package src.main;

import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/*
 * This class implements the overall GUI for the program.
 */
public class QuickHitGUI {
	JFrame mainWindow;
	Unit[] unitPanels = new Unit[Constants.MAX_NUMBER_OF_UNITS];
	JPanel combinedUnitPanel, chainVisualPanel;
	MacroMaker macroPanel;
	ChainVisualizer chainVisualizer;
	JTextArea macroOutputField;
	
	public static void main(String[] args) {
		QuickHitGUI chainTool = new QuickHitGUI();
		chainTool.run();
	}
	
	/*
	 * Driver for the program.
	 */
	private void run() {
		initializeUnitPanels();
		initializeChainPanel();
		Unit.setChainVisualizer(chainVisualizer);
		initializeMacroPanel();
		initializeMainWindow();
	}
	
	/*
	 * Sets up the main window for the program.
	 */
	private void initializeMainWindow() {
		mainWindow = new JFrame("Quick Hit");
		mainWindow.setSize(1400, 800);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setVisible(true);
		mainWindow.setLayout(new BorderLayout());
		mainWindow.add(combinedUnitPanel, BorderLayout.NORTH);
		mainWindow.add(macroPanel, BorderLayout.SOUTH);
		mainWindow.add(Box.createHorizontalStrut(50), BorderLayout.WEST);
		mainWindow.add(Box.createHorizontalStrut(50), BorderLayout.EAST);
		mainWindow.add(chainVisualizer, BorderLayout.CENTER);
	}
	
	/*
	 * Initializes the unit panels.
	 */
	private void initializeUnitPanels() {
		combinedUnitPanel = new JPanel();
		for(int count = 0; count < Constants.MAX_NUMBER_OF_UNITS; count++) {
			unitPanels[count] = new Unit(count);
			combinedUnitPanel.add(unitPanels[count]);
		}
	}
	
	/*
	 * Initializes the chain visualizer.
	 */
	private void initializeChainPanel() {
		chainVisualizer = new ChainVisualizer(unitPanels);
	}
	
	
	/*
	 * Sets up the panel containing the macro output.
	 */
	private void initializeMacroPanel() {
		macroPanel = new MacroMaker(unitPanels);
	}
}
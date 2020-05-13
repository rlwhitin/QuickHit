package main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MacroMaker extends JPanel {
	private static JTextArea macroOutputField;
	
	/*
	 * Just sets up the text field.
	 */
	MacroMaker() {
		macroOutputField = new JTextArea(14, 34);
		macroOutputField.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(macroOutputField);
	}
	
	/*
	 * Updates the macro output field. 
	 */
	public static void updateMacro() {
		macroOutputField.setText("");
		ArrayList<String> macroLines = new ArrayList<String>();
		for(Unit thisUnit : MainGUI.getUnits()) {
			if(thisUnit.getNumberOfCasts() > 0) {
				int sendTime = Constants.MEMU_MACRO_START + (thisUnit.getSendTime() * Constants.MEMU_FRAME_LENGTH);
				String xPos = Integer.toString(Constants.MEMU_UNIT_X_COORDS[thisUnit.getSlot()]);
				String yPos = Integer.toString(Constants.MEMU_UNIT_Y_COORDS[thisUnit.getSlot()]);
				String thisMacroLine = Integer.toString(sendTime) + Constants.MEMU_CLICK_DOWN_HEADER + xPos + ":" + yPos + Constants.MEMU_CLICK_DOWN_TAIL;
				thisMacroLine += Integer.toString(sendTime + Constants.MEMU_CLICK_RELEASE_DELAY) + Constants.MEMU_CLICK_RELEASE_TAIL;
				macroLines.add(thisMacroLine);
			}
		}
		Collections.sort(macroLines);
		for(String thisLine : macroLines) {
			macroOutputField.append(thisLine);
		}
	}
}
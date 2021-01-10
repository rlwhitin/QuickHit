package main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MacroMaker extends JPanel {
	private static JTextArea macroOutputField;
	private static ArrayList<MacroLine> macroLines;
	
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
		macroLines = new ArrayList<MacroLine>(Constants.MAX_NUMBER_OF_UNITS);
		for(Unit thisUnit : MainGUI.getUnits()) {
			if(thisUnit.getNumberOfCasts() > 0) {
				int macroSendTime = (int)Math.round(Constants.MEMU_MACRO_START + (thisUnit.getSendTime() * Constants.MEMU_FRAME_LENGTH));
				String xPos = Integer.toString(Constants.MEMU_UNIT_X_COORDS[thisUnit.getSlot()]);
				String yPos = Integer.toString(Constants.MEMU_UNIT_Y_COORDS[thisUnit.getSlot()]);
				String thisMacroLine = Integer.toString(macroSendTime) + Constants.MEMU_CLICK_DOWN_HEADER + xPos + ":" + yPos + Constants.MEMU_CLICK_DOWN_TAIL;
				thisMacroLine += Integer.toString(macroSendTime + Constants.MEMU_CLICK_RELEASE_DELAY) + Constants.MEMU_CLICK_RELEASE_TAIL;
				macroLines.add(new MacroLine(thisMacroLine, thisUnit.getSendTime()));
			}
		}
		Collections.sort(macroLines);
		for(MacroLine thisLine : macroLines) {
			macroOutputField.append(thisLine.toString());
		}
	}
	static class MacroLine implements Comparable<MacroLine>{
		String macroLine;
		int sendTime;
		
		MacroLine(String newMacroLine, int newSendTime) {
			macroLine = newMacroLine;
			sendTime = newSendTime;
		}
		
		@Override
		public String toString() {
			return macroLine;
		}

		@Override
		public int compareTo(MacroLine otherLine) {
			if(sendTime < otherLine.sendTime) {
				return -1;
			}
			else if(sendTime > otherLine.sendTime) {
				return 1;
			}
			else {
				return 0;
			}
		}
	}
}
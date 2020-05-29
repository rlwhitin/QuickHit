package main;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;

public class ChainVisualizer extends JPanel {
	private ArrayList<ChainHit> chainHits;
	//JCheckBox showCastStart, showDeathCheck;
	
	ChainVisualizer() {
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEtchedBorder((EtchedBorder.RAISED)));
		setPreferredSize(new Dimension(1200, 260));
	}
	
	/*
	 * This method will be called by Unit any time that frames need to be recalculated.
	 */
	public void update() {
		repaint();
		MacroMaker.updateMacro();
	}

	/*
	 * Calls various methods to draw the various parts of this panel.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D canvas = (Graphics2D) g;
		populateChainHits();
		calcChainBreaks();
		drawRuler(canvas);
		drawUnitNames(canvas);
		drawChainHits(canvas);
		drawCastStartFrames(canvas);
	}
	
	/*
	 * Draws the unit names.
	 */
	private void drawUnitNames(Graphics2D canvas) {
		int xPos = 10;
		int yPos = 30;
		for(Unit thisUnit: MainGUI.getUnits()) {
			if(thisUnit.getNumberOfCasts() > 0) {
				canvas.drawString(thisUnit.getUnitName(), xPos, yPos);
				yPos += 40;
			}
		}
	}
	
	/*
	 * Draws the chain hits for this turn, color coding them as needed.
	 */
	private void drawChainHits(Graphics2D canvas) {
		ArrayList<Integer> filledUnitSlots = new ArrayList<Integer>();
		for(Unit thisUnit : MainGUI.getUnits()) {
			if(thisUnit.getNumberOfCasts() > 0) {
				if(!filledUnitSlots.contains(thisUnit.getSlot())) {
					filledUnitSlots.add(thisUnit.getSlot());
				}
			}
		}
		Collections.sort(filledUnitSlots);
		for(ChainHit thisChainHit : chainHits) {
			int xPos = 100 + (2 * thisChainHit.getFrame());
			int yPos = 10 + (40 * filledUnitSlots.indexOf(thisChainHit.getOriginatingUnit()));
			if(thisChainHit.breaks()) {
				canvas.setColor(Color.RED);
				canvas.drawLine(xPos, yPos, xPos, yPos + 30);
				canvas.setColor(Color.BLACK);
			}
			else {
				canvas.drawLine(xPos, yPos, xPos, yPos + 30);
			}
		}
	}
	
	/*
	 * Draws the green dots for each unit's cast start time.
	 */
	private void drawCastStartFrames(Graphics2D canvas) {
		canvas.setColor(Color.BLUE);
		int yPos = 23;
		for(Unit thisUnit: MainGUI.getUnits()) {
			for(int castCount = 0; castCount < thisUnit.getNumberOfCasts(); castCount++) {
				int currentCastFrame = thisUnit.getUnitCastFrames().get(castCount);
				/*
				 * Including offset
				 * int currentCastFrame = units[unitCount].getCastStartFrames().get(castCount) + units[unitCount].getSkills()[castCount].getOffset();
				 */
				int xPos = 100 + (2 * currentCastFrame);
				canvas.fillOval(xPos, yPos, 5, 5);
			}
			if(thisUnit.getNumberOfCasts() > 0) {
				yPos += 40;
			}

		}
		canvas.setColor(Color.BLACK);
	}
	
	/*
	 * Draws ticks along the top and bottom of the macro window.
	 */
	private void drawRuler(Graphics2D canvas) {
		for(int count = 0; count < 47; count++) {
			int xPos = 103 + (50 * count);
			canvas.drawLine(xPos, 0, xPos, 6);
			canvas.drawLine(xPos, 252, xPos, 260);
		}
	}

	/*
	 * Grabs the hits from each unit, sticks them into chainHits, and then sorts it by frame.
	 */
	private void populateChainHits() {
		chainHits = new ArrayList<ChainHit>();
		for(Unit thisUnit : MainGUI.getUnits()) {
			for(Integer thisHit : thisUnit.getUnitHitFrames()) {
				chainHits.add(new ChainHit(thisUnit.getSlot(), thisHit));
			}
		}
		Collections.sort(chainHits);
	}
	
	/*
	 * Goes through chainHits and identifies any spots where the chain will break.
	 */
	private void calcChainBreaks() {
		if(chainHits.size() > 2) {
			ChainHit prevChainHit = chainHits.get(0);
			for(int count = 1; count < chainHits.size(); count++) {
				if(chainHits.get(count).getFrame() - prevChainHit.getFrame() > 20) {
					chainHits.get(count).breaksChain = true;
				}
				else if(chainHits.get(count).getOriginatingUnit() == prevChainHit.getOriginatingUnit()) {
					chainHits.get(count).breaksChain = true;
				}
				prevChainHit = chainHits.get(count);
			}
		}
	}
	
	/*
	 * ChainVisualizer uses an arraylist of ChainHits to more easily calculate breaks.
	 * 
	 * It allows us to sort every hit in order of frame while still keeping track of which unit they came from, and then individually tag hits that cause a chain break.
	 */
	class ChainHit implements Comparable<ChainHit> {
		int originatingUnit;
		int frame;
		boolean breaksChain;
		
		ChainHit(int newUnit, int newFrame) {
			originatingUnit = newUnit;
			frame = newFrame;
			breaksChain = false;
		}
		
		/*
		 * Accessor method for originatingUnit
		 */
		public int getOriginatingUnit() {
			return originatingUnit;
		}
		/*
		 * Accessor method for hitFrame.
		 */
		public int getFrame() {
			return frame;
		}

		/*
		 * Accessor method for breaksChain.
		 */
		public boolean breaks() {
			return breaksChain;
		}
		
		/*
		 * Used so that we can sort the ChainHit ArrayList. Unit slot is used as a tiebreaker.
		 */
		@Override
		public int compareTo(ChainHit otherHit) {
			if(this.frame == otherHit.getFrame()) {
				if(this.originatingUnit == otherHit.getOriginatingUnit()) {
					return 0;
				}
				else if(this.originatingUnit > otherHit.getOriginatingUnit()) {
					return 1;
				}
				else {
					return -1;
				}
			}
			else if(this.frame > otherHit.getFrame()) {
				return 1;
			}
			else {
				return -1;
			}
		}
	}
}
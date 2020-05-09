package src.main;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;

/*
 * A 2d graphic that displays the chains for the turn.
 */
public class ChainVisualizer extends JPanel {
	Unit[] units;
	ArrayList<ChainHit> chainHits;
	int firstFrame, lastFrame;
	
	/*
	 * Regular constructor, sets up the necessary visuals.
	 */
	ChainVisualizer(Unit[] newUnits) {
		units = newUnits;
		chainHits = new ArrayList<ChainHit>();
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEtchedBorder((EtchedBorder.RAISED)));
	}
	
	/*
	 * Calls various methods to draw the various parts of this panel.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D canvas = (Graphics2D) g;
		drawRuler(canvas);
		drawUnitNames(canvas);
		drawCastStartFrames(canvas);
		drawChainHits(canvas);
		MacroMaker.updateMacro();
	}

	/*
	 * Updates the chainVisualizer image.
	 */
	public void updateChains() {
		populateChainHits();
		calcChainBreaks();
		repaint();
	}
	
	
	/*
	 * Checks the chains for this turn to see if there are any breaks.
	 */
	private void calcChainBreaks() {
		int prevHitFrame;
		int prevHitUnit;
		if(chainHits.size() >= 2) {
			prevHitFrame = chainHits.get(0).getFrame();
			prevHitUnit = chainHits.get(0).getSlot();
			for(int count = 1; count < chainHits.size(); count++) {
				if(chainHits.get(count).getFrame() - prevHitFrame > 20) {
					chainHits.get(count).setChainBreak(true);
				}
				else if(chainHits.get(count).getSlot() == prevHitUnit) {
					chainHits.get(count).setChainBreak(true);
				}
				prevHitFrame = chainHits.get(count).getFrame();
				prevHitUnit = chainHits.get(count).getSlot();
			}
		}
	}	
	
	/*
	 * Draws the unit names.
	 */
	private void drawUnitNames(Graphics2D canvas) {
		int xPos = 10;
		int yPos = 30;
		for(int unitCount = 0; unitCount < units.length; unitCount++) {
			if(units[unitCount].getNumberOfCasts() > 0) {
				canvas.drawString(units[unitCount].getUnitName(), xPos, yPos);
				yPos += 40;
			}
		}
	}
	/*
	 * Draws the green dots for each unit's cast start time.
	 */
	private void drawCastStartFrames(Graphics2D canvas) {
		canvas.setColor(Color.BLUE);
		int yPos = 23;
		for(int unitCount = 0; unitCount < units.length; unitCount++) {
			if(units[unitCount].getCastStartFrames() != null) {
				for(int castCount = 0; castCount < units[unitCount].getCastStartFrames().size(); castCount++) {
					int currentCastFrame = units[unitCount].getCastStartFrames().get(castCount);
					/*
					 * Including offset
					 * int currentCastFrame = units[unitCount].getCastStartFrames().get(castCount) + units[unitCount].getSkills()[castCount].getOffset();
					 */
					int xPos = 100 + (2 * currentCastFrame);
					canvas.fillOval(xPos, yPos, 5, 5);
				}
				yPos += 40;
			}
		}
	}
	
	/*
	 * Draws ticks along the top and bottom of the macro window.
	 */
	private void drawRuler(Graphics2D canvas) {
		for(int count = 0; count < 47; count++) {
			int xPos = 103 + (25 * count);
			canvas.drawLine(xPos, 0, xPos, 6);
		}
	}
	
	/*
	 * Draws the chain hits for this turn, color coding them as needed.
	 */
	private void drawChainHits(Graphics2D canvas) {
		ArrayList<Integer> filledUnitSlots = new ArrayList<Integer>();
		for(ChainHit thisChainHit : chainHits) {
			if(!filledUnitSlots.contains(thisChainHit.getSlot())) {
				filledUnitSlots.add(thisChainHit.getSlot());
			}
		}
		Collections.sort(filledUnitSlots);
		for(ChainHit thisChainHit : chainHits) {
			int xPos = 100 + (2 * thisChainHit.getFrame());
			int yPos = 10 + (40 * filledUnitSlots.indexOf(thisChainHit.getSlot()));
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
	 * Fills out chainHits with all of the hits from each units.
	 */
	private void populateChainHits() {
		chainHits = new ArrayList<ChainHit>();
		for(int unitCount = 0; unitCount < units.length; unitCount++) {
			if(units[unitCount].getHitFrames() != null) {
				for(int hitCount = 0; hitCount < units[unitCount].getHitFrames().size(); hitCount++) {
					chainHits.add(new ChainHit(unitCount, units[unitCount].getHitFrames().get(hitCount)));
				}
			}
		}
		Collections.sort(chainHits);
	}
}
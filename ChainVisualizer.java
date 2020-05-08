import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;

/*
 * A 2d graphic that displays the chains for the turn.
 */
public class ChainVisualizer extends JPanel {
	Unit[] units;
	ArrayList<ChainHit> chainHits;
	ArrayList<Integer> chainBreaks;
	int firstFrame, lastFrame;
	
	/*
	 * Regular constructor, sets up the necessary visuals.
	 */
	ChainVisualizer(Unit[] newUnits) {
		units = newUnits;
		chainHits = null;
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
		drawUnitNames(canvas);
		drawCastStartFrames(canvas);
		drawChainHits(canvas);
	}
	
	
	/*
	 * Updates the chainVisualizer image.
	 */
	public void updateChains() {
		populateChainHits();
		calcChainBreaks();
		findFirstAndLastFrames();
		repaint();
	}

	/*
	 * Checks the chains for this turn to see if there are any breaks.
	 */
	private void calcChainBreaks() {
		chainBreaks = new ArrayList<Integer>();
		int prevHitFrame;
		int prevHitUnit;
		if(chainHits.size() >= 2) {
			prevHitFrame = chainHits.get(0).getFrame();
			prevHitUnit = chainHits.get(0).getSlot();
			for(int count = 1; count < chainHits.size(); count++) {
				if(chainHits.get(count).getFrame() - prevHitFrame > 20) {
					chainBreaks.add(chainHits.get(count).getFrame());
				}
				else if(chainHits.get(count).getSlot() == prevHitUnit) {
					chainBreaks.add(chainHits.get(count).getFrame());
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
		int yPos = 25;
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
		int yPos = 18;
		for(int unitCount = 0; unitCount < units.length; unitCount++) {
			if(units[unitCount].getCastStartFrames() != null) {
				for(int castCount = 0; castCount < units[unitCount].getCastStartFrames().size(); castCount++) {
					int currentCastFrame = units[unitCount].getCastStartFrames().get(castCount);
					int xPos = 100 + currentCastFrame;
					canvas.fillOval(xPos, yPos, 5, 5);
				}
				yPos += 40;
			}
		}
	}
	
	/*
	 * Draws the chain hits for this turn, color coding them as needed.
	 */
	private void drawChainHits(Graphics2D canvas) {
		if(chainHits != null) {
			canvas.setColor(Color.BLACK);
			for(ChainHit thisChainHit : chainHits) {
				int xPos = 100 + (2 * thisChainHit.getFrame());
				int yPos = 5+ (40 * thisChainHit.getSlot());
				canvas.drawLine(xPos, yPos, xPos, yPos + 30);
			}
		}
	}
	
	/*
	 * Finds the earliest and latest frame that the chainVisualizer will have to draw.
	 */
	private void findFirstAndLastFrames() {
		firstFrame = 1500;
		lastFrame = -1500;
		for(int unitCount = 0; unitCount < units.length; unitCount++) {
			if(units[unitCount].getCastStartFrames() != null) {
				for(int castCount = 0; castCount < units[unitCount].getCastStartFrames().size(); castCount++) {
					if(firstFrame > units[unitCount].getCastStartFrames().get(castCount)) {
						firstFrame = units[unitCount].getCastStartFrames().get(castCount);
					}
					if(lastFrame > units[unitCount].getCastStartFrames().get(castCount)) {
						lastFrame = units[unitCount].getCastStartFrames().get(castCount);
					}
				}
			}
		}
		
		for(ChainHit thisChainHit : chainHits) {
			if(firstFrame > thisChainHit.getFrame()) {
				firstFrame = thisChainHit.getFrame();
			}
			if(lastFrame < thisChainHit.getFrame()) {
				lastFrame = thisChainHit.getFrame();
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
package src.main;

/*
 * Each hit has the unit from which it was sourced and the frame on which it lands.
 */
public class ChainHit implements Comparable<ChainHit> {
	private int unitSlot;
	private int hitFrame;
	private boolean breaksChain;
	
	/*
	 * Basic constructor.
	 */
	ChainHit(int newUnitSlot, int newHitFrame) {
		unitSlot = newUnitSlot;
		hitFrame = newHitFrame;
		breaksChain = false;
	}
	
	/*
	 * Mutator method for breaksChain.
	 */
	public void setChainBreak(boolean newBreaksChain) {
		breaksChain = newBreaksChain;
	}
	
	/*
	 * Accessor method for unitSlot.
	 */
	public int getSlot() {
		return unitSlot;
	}
	/*
	 * Accessor method for hitFrame.
	 */
	public int getFrame() {
		return hitFrame;
	}

	/*
	 * Accessor method for breaksChain.
	 */
	public boolean breaks() {
		return breaksChain;
	}
	
	/*
	 * Used so that we can sort the src.main.ChainHit ArrayList.
	 */
	@Override
	public int compareTo(ChainHit otherHit) {
		if(this.getFrame() == otherHit.getFrame()) {
			return 0;
		}
		else if(this.getFrame() > otherHit.getFrame()) {
			return 1;
		}
		else {
			return -1;
		}
	}
}
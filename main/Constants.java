package main;
/*
 * Just various constants in case I ever need to change them.
 * 
 * If the max number of units ever changes (which is highly unlikely), in addition to changing the constant here, the macro generator would need to be rewritten for the new positions of unit buttons for various slots.
 * The chain visualizer would also need to be made vertically larger to accomodate more units.
 * The window would need to be made wider to so that the additional units aren't off the screen. (Consider the width of most monitors, this would probably require a complete redesign of the UI.)
 * 
 * To add a new family, three things must be done:
 * 1. Add the new family's name to both CHAIN_FAMILY_NAMES and FIRST_CAST_CHAIN_FAMILY_NAMES.
 * 2. Add the new family's frames and cast time as constants.
 * 3. Add a new case to the switch statement in itemStateChanged in Skill.java with the family name as the case, and make it so that said case sets the frames and the cast time to the appropriate constants. 
 */
public class Constants {
	public static final int MAX_CAST_COUNT = 5;
	public static final int MAX_NUMBER_OF_UNITS = 6;
	public static final String[] CHAIN_FAMILY_NAMES = {"None", "Copy previous skill", "Custom skill", "Absolute Mirror of Equity", "Absolute Zero", "Aureole Ray", "Bolting Strike", "Chaos Wave", "Chaos Wave Awakened", "Disorder",
			"Divine Ruination", "Flood", "Freeze", "Graviton Cannon", "Kingsglaive", "Octaslash", "Stardust Ray", "Tornado"};
	public static final String[] FIRST_CAST_CHAIN_FAMILY_NAMES = {"None", "Custom skill", "Absolute Mirror of Equity", "Absolute Zero", "Aureole Ray", "Bolting Strike", "Chaos Wave", "Chaos Wave Awakened", "Disorder",
			"Divine Ruination", "Flood", "Freeze", "Graviton Cannon", "Kingsglaive", "Octaslash", "Stardust Ray", "Tornado"};
	public static final Integer[] AR_FRAMES = {42, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
	public static final Integer[] AT_FRAMES = {70, 6, 6, 6, 6, 6, 6, 6};
	public static final Integer[] AZ_FRAMES = {40, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
	public static final Integer[] BS_FRAMES = {42, 6, 6, 6, 6, 6, 6, 6, 6};
	public static final Integer[] CW_FRAMES = {52, 20, 20, 20, 20};
	public static final Integer[] CWA_FRAMES = {42, 20, 20, 20, 20, 20, 20, 20};
	public static final Integer[] DISORDER_FRAMES = {42, 5, 5, 5, 5, 5, 5, 5, 5, 5};
	public static final Integer[] DR_FRAMES = { 70, 7, 5, 7, 7, 7, 7};
	public static final Integer[] GC_FRAMES = {80, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };
	public static final Integer[] FLD_FRAMES = {133, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};
	public static final Integer[] FREEZE_FRAMES = {140, 14, 14, 14, 14, 14, 15, 15};
	public static final Integer[] KG_FRAMES = {82, 8, 8, 8, 8, 8, 8, 8};
	public static final Integer[] OCTA_FRAMES = {42, 10, 10, 10, 10, 10, 10, 10};
	public static final Integer[] SR_FRAMES = {110, 10, 10, 10, 10, 10, 10, 10, 10, 10};
	public static final Integer[] TORNADO_FRAMES = {80, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};
	public static final int AR_CAST = 40;
	public static final int AT_CAST = 40;
	public static final int AZ_CAST = 39;
	public static final int BS_CAST = 38;
	public static final int CW_CAST = 50;
	public static final int CWA_CAST = 76;
	public static final int DISORDER_CAST = 40;
	public static final int DR_CAST = 40;
	public static final int GC_CAST = 40;
	public static final int FLD_CAST = 40;
	public static final int FREEZE_CAST = 40;
	public static final int KG_CAST = 40;
	public static final int OCTA_CAST = 40;
	public static final int SR_CAST = 40;
	public static final int TORNADO_CAST = 40;
	public static final int FIRST_CAST_OFFSET = 13;
	public static final int SUBSEQUENT_CAST_OFFSET = 14;
	public static final int MEMU_FRAME_LENGTH = 16667;
	public static final int MEMU_MACRO_START = 1000;
	public static final int MEMU_CLICK_RELEASE_DELAY = 100;
	public static final int[] MEMU_UNIT_X_COORDS = {166, 166, 166, 504, 504, 504};
	public static final int[] MEMU_UNIT_Y_COORDS = {832, 960, 1088, 832, 960, 1088};
	public static final String MEMU_CLICK_RELEASE_TAIL = "--VINPUT--MULTI2:1:0:-1:-1:-2:2\n";
	public static final String MEMU_CLICK_DOWN_HEADER = "--VINPUT--MULTI2:1:0:0:";
	public static final String MEMU_CLICK_DOWN_TAIL = ":0\n";
}
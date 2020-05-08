import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * Each skill has a certain number of hits, a cast time, a cast number, and an offset.
 */
public class Skill extends JPanel implements ItemListener, ActionListener {
	private Unit parentUnit;
	private int[] hits;
	private JFrame customSkillInputFrame;
	private JTextField customCastTimeBox, customFramesBox, customOffsetBox, customCGDelayBox;
	private Integer castTime;
	private int castNumber;		//This is the skill's order. For instance, if a unit casts a DR skill followed by a QH skill, then the QH skill's cast number is 2.
	private int cgDelay;		//Only applicable for CG LBs.
	private int offset;
	private JComboBox<String> skillSelectBox;
	
	/*
	 * Default constructor for skills, uses the typical offset numbers for idle movement.
	 */
	Skill(int newCastNumber, Unit newParentUnit) {
		parentUnit = newParentUnit;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		hits = null;
		castTime = null;
		cgDelay = 0;
		castNumber = newCastNumber;
		if(castNumber == 1) {
			offset = Constants.FIRST_CAST_OFFSET;
		}
		else {
			offset = Constants.SUBSEQUENT_CAST_OFFSET;
		}
		if(castNumber == 1) {
			skillSelectBox = new JComboBox<String>(Constants.FIRST_CAST_CHAIN_FAMILY_NAMES);
		}
		else {
			skillSelectBox = new JComboBox<String>(Constants.CHAIN_FAMILY_NAMES);
		}
		skillSelectBox.addItemListener(this);
		this.add(skillSelectBox);
	}
	
	Skill (int newCastNumber, Unit newParentUnit, Skill skillToCopy) {
		this(newCastNumber, newParentUnit);
		hits = skillToCopy.getHits();
		cgDelay = skillToCopy.getCGDelay();
		castTime = skillToCopy.getCastTime();
		offset = skillToCopy.getOffset();
	}
	
	/*
	 * Accessor method for castTime.
	 */
	public Integer getCastTime() {
		return castTime;
	}
	
	/*
	 * Accessor method for castNumber. 
	 */
	public int getCastNumber() {
		return castNumber;
	}
	
	/*
	 * Accessor method for offset.
	 */
	public int getOffset() {
		return offset;
	}
	
	/*
	 * Accessor method for cgDelay.
	 */
	public Integer getCGDelay() {
		return cgDelay;
	}
	
	/*
	 * Accessor method for hits.
	 */
	public int[] getHits() {
		return hits;
	}
	
	/*
	 * Creates a window that allows the user to input a custom skill's information.
	 */
	private void initializeCustomSkill() {
		customSkillInputFrame = new JFrame("Custom skill input");
		customSkillInputFrame.setLayout(new BorderLayout());
		JPanel customSkillInputPanel = new JPanel();
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		
		customSkillInputPanel.setLayout(new BoxLayout(customSkillInputPanel, BoxLayout.Y_AXIS));
		JLabel castTimeBoxLabel, frameBoxLabel, offsetBoxLabel, cgDelayBoxLabel;
		castTimeBoxLabel = new JLabel("Cast time (AKA cast delay)");
		frameBoxLabel = new JLabel("Frames (e.g. \"70-7-5-7-7-7-7\"). Leave blank if skill does not deal damage.");
		cgDelayBoxLabel = new JLabel("CG LB delay (only for CG LBs)");
		offsetBoxLabel = new JLabel("Offset (usually just stick to the default for this)");
		customSkillInputFrame.setSize(500, 220);
		customSkillInputFrame.setLocationRelativeTo(null);
		customCastTimeBox = new JTextField(3);
		customOffsetBox = new JTextField(3);
		if(castNumber == 1) {
			customOffsetBox.setText("13");
		}
		else {
			customOffsetBox.setText("14");
		}
		customFramesBox = new JTextField(100);
		customCGDelayBox = new JTextField(3);
		customCGDelayBox.setText("0");
		
		customSkillInputPanel.add(castTimeBoxLabel);
		customSkillInputPanel.add(customCastTimeBox);
		customSkillInputPanel.add(frameBoxLabel);
		customSkillInputPanel.add(customFramesBox);
		customSkillInputPanel.add(offsetBoxLabel);
		customSkillInputPanel.add(customOffsetBox);
		customSkillInputPanel.add(cgDelayBoxLabel);
		customSkillInputPanel.add(customCGDelayBox);
		
		customSkillInputFrame.add(customSkillInputPanel, BorderLayout.CENTER);
		customSkillInputFrame.add(buttonPanel, BorderLayout.SOUTH);
		customSkillInputFrame.setVisible(true);
	}
	
	/*
	 * Handles the skill selection drop down menu.
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			switch((String)e.getItem()) {
			case "None":
				hits = null;
				break;
			case "Custom skill":
				initializeCustomSkill();
				break;
			case "Absolute Mirror of Equity":
				hits = Constants.AT_FRAMES;
				castTime = Constants.AT_CAST;
				break;
			case "Absolute Zero":
				hits = Constants.AT_FRAMES;
				castTime = Constants.AZ_CAST;
				break;
			case "Aureole Ray":
				hits = Constants.AR_FRAMES;
				castTime = Constants.AR_CAST;
				break;
			case "Bolting Strike":
				hits = Constants.BS_FRAMES;
				castTime = Constants.BS_CAST;
				break;
			case "Chaos Wave":
				hits = Constants.CW_FRAMES;
				castTime = Constants.CW_CAST;
				break;
			case "Chaos Wave Awakened":
				hits = Constants.CWA_FRAMES;
				castTime = Constants.CWA_CAST;
				break;
			case "Disorder":
				hits = Constants.DISORDER_FRAMES;
				castTime = Constants.DISORDER_CAST;
				break;
			case "Divine Ruination":
				hits = Constants.DR_FRAMES;
				castTime = Constants.DR_CAST;
				break;
			case "Flood":
				hits = Constants.FLD_FRAMES;
				castTime = Constants.FLD_CAST;
				break;
			case "Freeze":
				hits = Constants.FREEZE_FRAMES;
				castTime = Constants.FREEZE_CAST;
				break;
			case "Graviton Cannon":
				hits = Constants.GC_FRAMES;
				castTime = Constants.GC_CAST;
				break;
			case "Kingsglaive":
				hits = Constants.KG_FRAMES;
				castTime = Constants.KG_CAST;
				break;
			case "Octaslash":
				hits = Constants.OCTA_FRAMES;
				castTime = Constants.OCTA_CAST;
				break;
			case "Stardust Ray":
				hits = Constants.SR_FRAMES;
				castTime = Constants.SR_CAST;
				break;
			case "Tornado":
				hits = Constants.TORNADO_FRAMES;
				castTime = Constants.TORNADO_CAST;
				break;
			}
			parentUnit.calcFrames();
		}
	}
	
	/*
	 * Takes care of what happens when you click on the "Save" button for a custom skill.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Save")) {
			castTime = Integer.parseInt(customCastTimeBox.getText());
			offset = Integer.parseInt(customOffsetBox.getText());
			cgDelay = Integer.parseInt(customCGDelayBox.getText());
			String newFramesString = customFramesBox.getText().replaceAll("-", " ");
			Scanner frameReader = new Scanner(newFramesString);
			ArrayList<Integer> newFrames = new ArrayList<Integer>();
			while(frameReader.hasNextInt()) {
				int nextFrame = frameReader.nextInt();
				newFrames.add(nextFrame);
			}
			if(newFrames.size() > 0) {
				hits = new int[newFrames.size()];
				for(int count = 0; count < hits.length; count++) {
					hits[count] = newFrames.get(count);
				}
			}
			frameReader.close();
		}
		customSkillInputFrame.dispose();
	}
}
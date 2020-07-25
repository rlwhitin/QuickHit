package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * This class contains all of the information pertaining to units and, via the Skills nested class, their skills.
 */

public class Unit extends JPanel {
	private ArrayList<Skill> skills;
	private ArrayList<Integer> unitCastFrames, unitHitFrames;
	private String unitName;
	private int slot, sendTime;
	private JSlider sendTimeSlider;
	private JTextField unitNameField, sendTimeField;

	/*
	 * Setting up the default values and basic UI elements.
	 */
	Unit(int newSlot) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		unitCastFrames = new ArrayList<Integer>(5);
		unitHitFrames = new ArrayList<Integer>();
		slot = newSlot;
		sendTime = 0;
		unitName = "Unit " + Integer.toString(slot + 1);
		unitNameField = new JTextField(unitName);
		unitNameField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				unitName = unitNameField.getText();
				updateFrames();
			}
		});
		this.add(unitNameField);
		skills = new ArrayList<Skill>(Constants.MAX_CAST_COUNT);
		for(int count = 0; count < Constants.MAX_CAST_COUNT; count++) {
			skills.add(new Skill(count));
			this.add(skills.get(count));
		}
		sendTimeSlider = new JSlider(0, 200, 0);
		sendTimeSlider.setPreferredSize(new Dimension(100, 20));
		sendTimeSlider.setMajorTickSpacing(25);
		sendTimeSlider.setMinorTickSpacing(5);
		sendTimeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				sendTime = sendTimeSlider.getValue();
				sendTimeField.setText(Integer.toString(sendTime));
				updateFrames();
			}
		});
		this.add(sendTimeSlider);
		sendTimeField = new JTextField("0");
		sendTimeField.setHorizontalAlignment(JTextField.CENTER);
		sendTimeField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int newSendTime = Integer.parseInt(sendTimeField.getText());
					if(newSendTime > sendTimeSlider.getMaximum()) {
						sendTimeSlider.setMaximum(newSendTime);
						sendTime = newSendTime;
						sendTimeSlider.setValue(sendTime);
						sendTimeField.setText(Integer.toString(sendTime));
					}
					else if (newSendTime < 0) {
						sendTime = 0;
						sendTimeField.setText("0");
						sendTimeSlider.setValue(0);
					}
					else {
						sendTime = newSendTime;
						sendTimeSlider.setValue(sendTime);
						sendTimeField.setText(Integer.toString(sendTime));
					}
				}
				catch (NumberFormatException error) {
					sendTime = 0;
					sendTimeField.setText("0");
					sendTimeSlider.setValue(0);
				}
			}
		});
		this.add(sendTimeField);
	}
	
	/**
	 * This method will be called any time a unit's send time or skills change.
	 */
	private void updateFrames() {
		calcUnitHitFrames();
		calcUnitCastFrames();
		MainGUI.chainVisualizer.update();
	}

	/**
	 * @return the unitHitFrames
	 */
	private void calcUnitHitFrames() {
		unitHitFrames = new ArrayList<Integer>();
		int currentCastFrame = sendTime;
		for(Skill thisSkill : skills) {
			int currentHitFrame = currentCastFrame + thisSkill.offset + thisSkill.cgDelay;
			if(thisSkill.isEmpty) {
				return;
			}
			else {
				for(Integer thisHit : thisSkill.skillHitFrames) {
					currentHitFrame += thisHit;
					unitHitFrames.add(currentHitFrame);
				}
			}
			currentCastFrame += thisSkill.castTime + thisSkill.offset + thisSkill.cgDelay;
		}
	}

	/**
	 * @return the unitCastFrames
	 */
	private void calcUnitCastFrames() {
		unitCastFrames = new ArrayList<Integer>(5);
		int currentFrame = sendTime;
		for(Skill thisSkill : skills) {
			//We want to stop as soon as there's a single skill that is set to "none".
			if(thisSkill.isEmpty) {
				return;
			}
			else {
				unitCastFrames.add(currentFrame);
				currentFrame += thisSkill.castTime + thisSkill.offset + thisSkill.cgDelay;
			}
		}
	}
	
	/*
	 * Returns the unit's number of casts.
	 */
	public int getNumberOfCasts() {
		int numberOfCasts = 0;
		for(Skill thisSkill : skills) {
			if(thisSkill.isEmpty) {
				return numberOfCasts;
			}
			else {
				numberOfCasts++;
			}
		}
		return numberOfCasts;
	}

	/**
	 * @return the unitName
	 */
	public String getUnitName() {
		return unitName;
	}

	/**
	 * @return the slot
	 */
	public int getSlot() {
		return slot;
	}
	
	/**
	 * @return the unitCastFrames
	 */
	public ArrayList<Integer> getUnitCastFrames() {
		return unitCastFrames;
	}
	
	/**
	 * @return the unitHitFrames
	 */
	public ArrayList<Integer> getUnitHitFrames() {
		return unitHitFrames;
	}

	/**
	 * @return the sendTime
	 */
	public int getSendTime() {
		return sendTime;
	}

	/**
	 * This will handle all of the stuff for individual skills.
	 */
	class Skill extends JPanel {
		JComboBox<String> skillSelectBox;
		ArrayList<Integer> skillHitFrames;
		int castNumber, offset, cgDelay, castTime;
		boolean isEmpty;

		/**
		 * Set a few default values.
		 */
		Skill(int newCastNumber) {
			skillHitFrames = new ArrayList<Integer>();
			castNumber = newCastNumber;
			cgDelay = 0;
			if(castNumber == 0) {
				offset = 13;
				skillSelectBox = new JComboBox<String>(Constants.FIRST_CAST_CHAIN_FAMILY_NAMES);
			}
			else {
				offset = 14;
				skillSelectBox = new JComboBox<String>(Constants.CHAIN_FAMILY_NAMES);
			}
			initializeSkillSelectBox();
			isEmpty = true;
			this.add(skillSelectBox);
		}

		/**
		 * This has the listener for the skill selection box.
		 */
		private void initializeSkillSelectBox() {
			skillSelectBox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.SELECTED) {
						switch((String)e.getItem()) {
						case "None":
							skillHitFrames.clear();
							isEmpty = true;
							break;
						case "Custom skill":
							/* 
							 * Notably, we do *not* set "isEmpty" to false here. That will happen if/when they click on "save" in the custom skill input frame.
							 * Otherwise, there might be a crash if a user opens the custom skill input window, then closes it without saving, because other functions might try to access uninitialized cast times.
							 * Similarly, we want to clear out skillHitFrames so that whatever was in this slot before doesn't stick around.
							 */
							skillHitFrames = new ArrayList<Integer>(30);
							isEmpty = true;
							initializeCustomSkill();
							break;
						case "Copy previous skill":
							skillHitFrames = new ArrayList<Integer>();
							if(!skills.get(castNumber - 1).isEmpty) {
								if((castNumber - 1 == 0) && skills.get(castNumber - 1).offset == 13) {
									offset = 14;
								}
								else {
									offset = skills.get(castNumber - 1).offset;
								}
								castTime = skills.get(castNumber - 1).castTime;
								cgDelay = skills.get(castNumber - 1).cgDelay;
								skillHitFrames.addAll(skills.get(castNumber - 1).skillHitFrames);
								isEmpty = false;
							}
							else {
								JFrame errorFrame = new JFrame();
								JOptionPane.showMessageDialog(errorFrame, "Previous skill is set incorrectly.", "Error", JOptionPane.ERROR_MESSAGE);
								isEmpty = true;
							}
							break;
						case "Absolute Mirror of Equity":
							skillHitFrames = new ArrayList<Integer>(Arrays.asList(Constants.AT_FRAMES));
							castTime = Constants.AT_CAST;
							isEmpty = false;
							break;
						case "Absolute Zero":
							skillHitFrames = new ArrayList<Integer>(Arrays.asList(Constants.AZ_FRAMES));
							castTime = Constants.AZ_CAST;
							isEmpty = false;
							break;
						case "Aureole Ray":
							skillHitFrames = new ArrayList<Integer>(Arrays.asList(Constants.AR_FRAMES));
							castTime = Constants.AR_CAST;
							isEmpty = false;
							break;
						case "Bolting Strike":
							skillHitFrames = new ArrayList<Integer>(Arrays.asList(Constants.BS_FRAMES));
							castTime = Constants.BS_CAST;
							isEmpty = false;
							break;
						case "Chaos Wave":
							skillHitFrames = new ArrayList<Integer>(Arrays.asList(Constants.CW_FRAMES));
							castTime = Constants.CW_CAST;
							isEmpty = false;
							break;
						case "Chaos Wave Awakened":
							skillHitFrames = new ArrayList<Integer>(Arrays.asList(Constants.CWA_FRAMES));
							castTime = Constants.CWA_CAST;
							isEmpty = false;
							break;
						case "Disorder":
							skillHitFrames = new ArrayList<Integer>(Arrays.asList(Constants.DISORDER_FRAMES));
							castTime = Constants.DISORDER_CAST;
							isEmpty = false;
							break;
						case "Divine Ruination":
							skillHitFrames = new ArrayList<Integer>(Arrays.asList(Constants.DR_FRAMES));
							castTime = Constants.DR_CAST;
							isEmpty = false;
							break;
						case "Flood":
							skillHitFrames = new ArrayList<Integer>(Arrays.asList(Constants.FLD_FRAMES));
							castTime = Constants.FLD_CAST;
							isEmpty = false;
							break;
						case "Freeze":
							skillHitFrames = new ArrayList<Integer>(Arrays.asList(Constants.FREEZE_FRAMES));
							castTime = Constants.FREEZE_CAST;
							isEmpty = false;
							break;
						case "Graviton Cannon":
							skillHitFrames = new ArrayList<Integer>(Arrays.asList(Constants.GC_FRAMES));
							castTime = Constants.GC_CAST;
							isEmpty = false;
							break;
						case "Kingsglaive":
							skillHitFrames = new ArrayList<Integer>(Arrays.asList(Constants.KG_FRAMES));
							castTime = Constants.KG_CAST;
							isEmpty = false;
							break;
						case "Octaslash":
							skillHitFrames = new ArrayList<Integer>(Arrays.asList(Constants.OCTA_FRAMES));
							castTime = Constants.OCTA_CAST;
							isEmpty = false;
							break;
						case "Stardust Ray":
							skillHitFrames = new ArrayList<Integer>(Arrays.asList(Constants.SR_FRAMES));
							castTime = Constants.SR_CAST;
							isEmpty = false;
							break;
						case "Tornado":
							skillHitFrames = new ArrayList<Integer>(Arrays.asList(Constants.TORNADO_FRAMES));
							castTime = Constants.TORNADO_CAST;
							isEmpty = false;
							break;
						}
						updateFrames();
					}
				}
			});
		}

		/*
		 * Creates a custom skill input window.
		 */
		private void initializeCustomSkill() {
			JFrame customSkillFrame = new JFrame("Custom skill");
			JPanel inputFieldPanel = new JPanel();
			JPanel buttonPanel = new JPanel();

			customSkillFrame.setLayout(new BorderLayout());

			inputFieldPanel.setLayout(new BoxLayout(inputFieldPanel, BoxLayout.Y_AXIS));
			JLabel castTimeBoxLabel, frameBoxLabel, offsetBoxLabel, cgDelayBoxLabel;
			JTextField castTimeBox, frameBox, offsetBox, cgDelayBox;
			customSkillFrame.setSize(500, 220);
			customSkillFrame.setLocationRelativeTo(null);
			JButton saveButton = new JButton("Save");

			castTimeBox = new JTextField();
			castTimeBoxLabel = new JLabel("Cast time (AKA cast delay)");
			inputFieldPanel.add(castTimeBoxLabel, BorderLayout.NORTH);
			inputFieldPanel.add(castTimeBox, BorderLayout.NORTH);

			frameBox = new JTextField();
			frameBoxLabel = new JLabel("Frames (e.g. \"70-7-5-7-7-7-7\"). Leave blank if skill does not deal damage.");
			inputFieldPanel.add(frameBoxLabel);
			inputFieldPanel.add(frameBox);

			offsetBox = new JTextField();
			offsetBoxLabel = new JLabel("Offset (usually just stick to the default for this)");
			if(castNumber == 0) {
				offsetBox.setText("13");
			}
			else {
				offsetBox.setText("14");
			}
			inputFieldPanel.add(offsetBoxLabel);
			inputFieldPanel.add(offsetBox);

			cgDelayBox = new JTextField("0");
			cgDelayBoxLabel = new JLabel("CG LB delay (only for CG LBs)");
			inputFieldPanel.add(cgDelayBoxLabel);
			inputFieldPanel.add(cgDelayBox);

			saveButton.addActionListener(new ActionListener() {
				/*
				 * Saves the custom skill.
				 */
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						castTime = Integer.parseInt(castTimeBox.getText());
						offset = Integer.parseInt(offsetBox.getText());
						cgDelay = Integer.parseInt(cgDelayBox.getText());
						String newFramesString = frameBox.getText().replaceAll("-", " ");
						Scanner frameReader = new Scanner(newFramesString);
						while(frameReader.hasNextInt()) {
							skillHitFrames.add(frameReader.nextInt());
						}
						isEmpty = false;
						updateFrames();
						frameReader.close();
						customSkillFrame.dispose();
					}
					catch(NumberFormatException error) {
						isEmpty = true;
						JFrame errorFrame = new JFrame();
						JOptionPane.showMessageDialog(errorFrame, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			});

			buttonPanel.add(saveButton);

			customSkillFrame.add(inputFieldPanel, BorderLayout.CENTER);
			customSkillFrame.add(buttonPanel, BorderLayout.SOUTH);

			customSkillFrame.setVisible(true);
		}
	}
}

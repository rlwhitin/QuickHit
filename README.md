# **Quick Hit**
QuickHit is a tool for the mobile game [Final Fantasy Brave Exvius][1] which will show a visual representation of chains, showing when and where they will break.

![QH Image](https://i.imgur.com/vWsFKtx.png)
## Running Quick Hit
For most people, unless they want to look at my very messy source code, you only need to download QuickHit.jar, which you can find at the [Releases page][4]-- just download it from the most recent release. The other files are unnecessary.

It comes pre-loaded with all of the most common chaining families, and also allows you to input custom skills.

## Using Quick Hit
By default, you can use the slider to adjust a unit's send time between 0 and 200 frames. If you find that the slider is too imprecise, you can also set a specific send time by typing it into the text box below the slider and pressing enter. If you type in a value that is greater than the normal maximum of 200, it will increase the maximum value of the slider, which can be useful if you need a particularly wide range of send times.

In the chain visualizer section, any hit which breaks a chain will be colored in red. There are also green dots, which indicate when each cast starts-- this can be helpful to know for something like Dark Visions, in case you want to know how late in a chain the last cast will start if you're concerned about overkill cancelling, or if one of the skills applies an imbue or break and you want to make sure it's up before your "payload" goes off.

## Custom Frames
For custom skills, after you select "Custom skill" from the drop down menu, you can find the information you need to fill out the window by joining the [FFBE discord][2], going to the #bot-stuff channel, and typing in "!skill <skill name>" (for instance, "!skill Flood" for Flood). There is a bot on the server that will immediately respond with a whole block of text, but for our purposes, we care about the bit under "Frames".

Going back to the Flood example, we see this:

**Frames**
```
133-12-12-12-12-12-12-12-12-12-12-12 (40)
```

The number in parenthesis is the cast time, and the rest of the numbers, split up by dashes, are the skill's hit frames. You can paste the hit frames (including the dashes) into the custom skill input window.

If a skill uses something other than a typical cast animation, you could try to simulate that by increasing the cast time, but that's going to be fairly imprecise.

## Delays
CG delay is something that is only relevant for units that have CG LBs. Supposedly, this number can be datamined, but I haven't yet been able to find a place where you can look up the CG delay of each LB. However, the game's wiki does have a page for the **[Extreme Nova][3]** family, which lists the animation delay for each LB that has Extreme Nova frames.

If you have the actual CG animation turned on, things become slightly more complicated. This will increase the CG delay by two, but if you activate multiple CG LBs in rapid succession, only the first one will show its CG animation, so the others won't have that 2 frame delay, so you'll have to adjust your inputs appropriately. For this reason, I would suggest leaving CG animations off. It simplifies things.

## Skills with movement
For most skills, you can just leave the offset at its default value. However, if you're using a skill that has walking frames, then you should try to adjust this to include however many frames you think it will take the unit to walk up to the enemy. This is more of an art than a science.

Please do not try to type things that are not numbers into any of the custom skill text fields (aside from the dashes in the frames text box), or the program will glitch out until you close and reopen it.

## Macros
This program will also create a Memu macro which you can use to perform the desired chain in-game. 
Three things to keep in mind: 
 - Macro output is designed for Memu version 6 or higher. It will not work on older versions.
 - Macros will only work in a Memu window with a resolution of 720x1280
 - Make sure that the unit slots in-game match the ones that you used in this tool, because otherwise the macro will tap the wrong units.

[1]: https://www.finalfantasyexvius.com/
[2]: https://discord.gg/ffbraveexvius
[3]: https://exvius.gamepedia.com/Chaining/Extreme_Nova
[4]: https://github.com/Muspelful/QuickHit/releases

package net.combatroll.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "client")
public class ClientConfig implements ConfigData {
    public boolean playCooldownSound = true;
    public boolean playCooldownFlash = true;
    public boolean playRollSound = true;
    @ConfigEntry.ColorPicker
    public int hudArrowColor = 0x5488e3;
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int hudBackgroundOpacity = 75;
    public boolean showWhenFull = true;
    public boolean showHUDInCreative = false;
    public boolean showKeybinding = true;
    public enum LabelPosition { TOP, LEFT }
    public LabelPosition keybindingLabelPosition = LabelPosition.LEFT;
}

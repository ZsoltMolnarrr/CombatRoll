package net.rolling.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "client")
public class ClientConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean playCooldownSound = true;
    @ConfigEntry.Gui.Tooltip
    public boolean playRollSound = true;
    @ConfigEntry.ColorPicker
    @ConfigEntry.Gui.Tooltip
    public int hudArrowColor = 0x5488e3;
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int hudBackgroundOpacity = 75;

//    @ConfigEntry.Gui.Tooltip
//    public boolean isHoldToAttackEnabled = true;
//    @ConfigEntry.Gui.Tooltip
//    public boolean isMiningWithWeaponsEnabled = true;
//    @ConfigEntry.Gui.Tooltip
//    public boolean isSwingThruGrassEnabled = true;
//    @ConfigEntry.Gui.Tooltip
//    public boolean isHighlightCrosshairEnabled = true;
//    @ConfigEntry.ColorPicker
//    @ConfigEntry.Gui.Tooltip
//    public int hudHighlightColor = 0xFF0000;
//    @ConfigEntry.Gui.Tooltip
//    public boolean isShowingArmsInFirstPerson = false;
//    @ConfigEntry.Gui.Tooltip
//    public boolean isShowingOtherHandFirstPerson = true;
//    @ConfigEntry.Gui.Tooltip
//    public boolean isSweepingParticleEnabled = true;
//    @ConfigEntry.Gui.Tooltip
//    public boolean isTooltipAttackRangeEnabled = true;
//    @ConfigEntry.Gui.Tooltip
//    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
//    public int weaponSwingSoundVolume = 100;
}

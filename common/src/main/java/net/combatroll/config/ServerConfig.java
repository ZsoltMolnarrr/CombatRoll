package net.combatroll.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "server")
public class ServerConfig implements ConfigData {
    @Comment("Allows combatroll while the player has its weapon on cooldown")
    public boolean allow_rolling_while_weapon_cooldown = false;
    @Comment("The amount of exhaust (hunger) to be added to the player on every roll")
    public float exhaust_on_roll = 0.1F;
    @Comment("The cooldown duration of the combat roll ability expressed in seconds")
    public float roll_cooldown = 4F;
    @Comment("Default roll distance attribute is `3`. Settings this to `1` will make it `4`. Warning! Attribute based scaling does not effect this.")
    public float additional_roll_distance = 0;
}

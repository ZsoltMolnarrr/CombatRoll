package net.combatroll.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "server")
public class ServerConfig implements ConfigData {
    @Comment("The number of game ticks players become invulnerable upon rolling")
    public int invulnerable_ticks_upon_roll = 0;
    @Comment("The duration of the roll ability, expressed as a number of ticks, during which the player cannot use item/attack/jump etc...")
    public int roll_duration = 8;
    @Comment("Allows Vanilla Minecraft auto jump feature to work while rolling")
    public boolean allow_auto_jump_while_rolling = true;
    @Comment("Allows jumping while rolling. WARNING! Setting this to `true` breaks roll distance attribute and enchantment")
    public boolean allow_jump_while_rolling = false;
    @Comment("Allows combat roll while the player has its weapon on cooldown")
    public boolean allow_rolling_while_weapon_cooldown = false;
    @Comment("Allows combat roll while the player is in the air. WARNING! Setting this to `true` breaks roll distance attribute and enchantment")
    public boolean allow_rolling_while_airborn = false;
    @Comment("The amount of exhaust (hunger) to be added to the player on every roll")
    public float exhaust_on_roll = 0.1F;
    @Comment("The amount of food level above which players can do a roll")
    public float food_level_required = 6;
    @Comment("The cooldown duration of the combat roll ability expressed in seconds")
    public float roll_cooldown = 4F;
    @Comment("Default roll distance attribute is `3`. Settings this to `1` will make it `4`. Warning! Attribute based scaling does not effect this.")
    public float additional_roll_distance = 0;
}

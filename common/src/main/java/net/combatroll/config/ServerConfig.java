package net.combatroll.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "server")
public class ServerConfig implements ConfigData {
    @Comment("The duration of the roll ability, expressed as a number of ticks, during which the player cannot use item/attack/jump etc...")
    public int roll_duration = 10;
    @Comment("Allows Vanilla Minecraft auto jump feature to work while rolling")
    public boolean allow_auto_jump_while_rolling = true;
    @Comment("Allows combatroll while the player has its weapon on cooldown")
    public boolean allow_rolling_while_weapon_cooldown = false;
    @Comment("The amount of exhaust (hunger) to be added to the player on every roll")
    public float exhaust_on_roll = 0.1F;
    @Comment("The cooldown duration of the combat roll ability expressed in seconds")
    public float roll_cooldown = 4F;
    @Comment("Default roll distance attribute is `3`. Settings this to `1` will make it `4`. Warning! Attribute based scaling does not effect this.")
    public float additional_roll_distance = 0;
    @Comment("Determines how much extra distance one level of Longfooted enchant grants. For exmple 1.0 results in 1 block")
    public float enchantment_longfooted_distance_per_level = 1F;
    @Comment("Determines how much recharge speed boost one level of Acrobat's chest enchant grants. For exmple 0.1 results in +10%")
    public float enchantment_acrobat_chest_recharge_per_level = 0.1F;
    @Comment("Determines how much recharge speed boost one level of Acrobat's chest enchant grants. For exmple 0.1 results in +10%")
    public float enchantment_acrobat_legs_recharge_per_level = 0.1F;
    @Comment("Determines how much many extra rolls the Multi-Roll enchant grants. Only integer value is accepted!")
    public int enchantment_multi_roll_count_per_level = 1;
}

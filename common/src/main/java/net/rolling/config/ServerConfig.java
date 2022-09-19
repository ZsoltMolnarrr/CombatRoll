package net.rolling.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "server")
public class ServerConfig implements ConfigData {
    @Comment("Allows rolling while the player has its weapon on cooldown")
    public boolean allow_rolling_while_weapon_cooldown = false;
    @Comment("The amount of exhaust (hunger) to be added to the player on every roll")
    public float exhaust_on_roll = 0.1F;
}

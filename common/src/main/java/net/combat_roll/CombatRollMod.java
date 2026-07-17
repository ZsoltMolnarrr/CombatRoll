package net.combat_roll;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.combat_roll.api.CombatRoll;
import net.combat_roll.config.ServerConfig;
import net.combat_roll.config.ServerConfigWrapper;
import net.combat_roll.network.ServerNetwork;
import net.minecraft.client.resources.language.I18n;

public class CombatRollMod {
    public static final String ID = CombatRoll.NAMESPACE;
    public static String modName() {
        return I18n.get(CombatRoll.NAMESPACE + ".mod_name");
    }
    public static ServerConfig config;

    public static void init() {
        AutoConfig.register(ServerConfigWrapper.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
        config = AutoConfig.getConfigHolder(ServerConfigWrapper.class).getConfig().server;
        ServerNetwork.initializeHandlers();
    }
}
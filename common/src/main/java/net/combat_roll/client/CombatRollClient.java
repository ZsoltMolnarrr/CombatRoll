package net.combat_roll.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.combat_roll.CombatRollMod;
import net.combat_roll.config.ClientConfig;
import net.combat_roll.config.ClientConfigWrapper;
import net.combat_roll.config.HudConfig;
import net.tiny_config.ConfigManager;

public class CombatRollClient {
    public static ClientConfig config;
    public static ConfigManager<HudConfig> hudConfig = new ConfigManager<HudConfig>
            ("hud_config", HudConfig.createDefault())
            .builder()
            .setDirectory(CombatRollMod.ID)
            .sanitize(true)
            .build();

    public static void initialize() {
        AutoConfig.register(ClientConfigWrapper.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
        config = AutoConfig.getConfigHolder(ClientConfigWrapper.class).getConfig().client;
        hudConfig.refresh();
    }
}

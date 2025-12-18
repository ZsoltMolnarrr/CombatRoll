package net.combat_roll.client;

import com.zigythebird.playeranim.api.PlayerAnimationFactory;
import com.zigythebird.playeranimcore.enums.PlayState;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.combat_roll.CombatRollMod;
import net.combat_roll.client.animation.RollAnimationController;
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

    /**
     * Sets up player animation system.
     * Must be called during client initialization AFTER resources are available.
     * For Fabric: Call directly in onInitializeClient
     * For NeoForge: Call via event.enqueueWork() in FMLClientSetupEvent
     */
    public static void setupAnimations() {
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
            RollAnimationController.ID,
            1000,  // Priority (matches old implementation)
            player -> new RollAnimationController(player,
                (controller, state, animSetter) -> PlayState.STOP
            )
        );
    }
}

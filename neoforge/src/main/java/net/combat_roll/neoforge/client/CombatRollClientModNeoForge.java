package net.combat_roll.neoforge.client;

import net.combat_roll.client.CombatRollClient;
import net.combat_roll.client.gui.ConfigMenuScreen;
import net.combat_roll.CombatRollMod;
import net.combat_roll.client.Keybindings;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@EventBusSubscriber(modid = CombatRollMod.ID, value = Dist.CLIENT)
public class CombatRollClientModNeoForge {
    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event){
        Keybindings.all.forEach(event::register);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){
        CombatRollClient.initialize();

        // Setup animations on main thread for thread safety
        event.enqueueWork(() -> {
            CombatRollClient.setupAnimations();
        });

        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> {
            return (IConfigScreenFactory) (modContainer, parent) -> new ConfigMenuScreen(parent);
        });
    }
}
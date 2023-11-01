package net.combatroll.forge.client;

import net.combatroll.client.gui.ConfigMenuScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.combatroll.CombatRoll;
import net.combatroll.client.CombatRollClient;
import net.combatroll.client.Keybindings;

@Mod.EventBusSubscriber(modid = CombatRoll.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeClientModEvents {
    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event){
        Keybindings.all.forEach(event::register);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){
        CombatRollClient.initialize();
        ClientLifecycleEvents.onClientStarted.forEach((action) -> action.onClientStarted(MinecraftClient.getInstance()));
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> {
            return new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> {
                return new ConfigMenuScreen(screen);
            });
        });
    }
}
package net.combat_roll.neoforge.client;

import net.combat_roll.CombatRollMod;
import net.combat_roll.client.gui.HudRenderHelper;
import net.minecraft.client.MinecraftClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = CombatRollMod.ID, value = Dist.CLIENT)
public class ClientEventsNeoForge {
    @SubscribeEvent
    public static void onRenderHud(RenderGuiEvent.Post event){
        if (!MinecraftClient.getInstance().options.hudHidden) {
            HudRenderHelper.render(event.getGuiGraphics(), event.getPartialTick().getTickProgress(true));
        }
    }
}

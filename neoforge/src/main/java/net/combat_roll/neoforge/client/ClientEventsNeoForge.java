package net.combat_roll.neoforge.client;

import net.combat_roll.CombatRollMod;
import net.combat_roll.client.gui.HudRenderHelper;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = CombatRollMod.ID, value = Dist.CLIENT)
public class ClientEventsNeoForge {
    @SubscribeEvent
    public static void onRenderHud(RenderGuiEvent.Post event){
        if (!Minecraft.getInstance().gui.hud.isHidden()) {
            HudRenderHelper.render(event.getGuiGraphics(), event.getPartialTick().getGameTimeDeltaPartialTick(true));
        }
    }
}

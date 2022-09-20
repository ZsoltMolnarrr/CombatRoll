package net.combatroll.forge.client;

import net.combatroll.CombatRoll;
import net.combatroll.client.gui.HudRenderHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CombatRoll.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClientEvents {
    @SubscribeEvent
    public static void onRenderHud(RenderGameOverlayEvent.Post event) {
        HudRenderHelper.render(event.getMatrixStack(), event.getPartialTicks());
    }
}

package net.combatroll.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.combatroll.client.CombatRollClient;
import net.combatroll.client.RollKeybings;
import net.combatroll.client.gui.HudRenderHelper;

public class FabricClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CombatRollClient.initialize();
        for(var keybinding: RollKeybings.all) {
            KeyBindingHelper.registerKeyBinding(keybinding);
        }

        HudRenderCallback.EVENT.register((DrawContext context, float tickDelta) -> {
            HudRenderHelper.render(context, tickDelta);
        });
    }
}

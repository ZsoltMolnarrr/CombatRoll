package net.combatroll.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.combatroll.client.CombatRollClient;
import net.combatroll.client.Keybindings;
import net.combatroll.client.gui.HudRenderHelper;

public class FabricClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CombatRollClient.initialize();
        for(var keybinding: Keybindings.all) {
            KeyBindingHelper.registerKeyBinding(keybinding);
        }

        HudRenderCallback.EVENT.register((DrawContext context, float tickDelta) -> {
            HudRenderHelper.render(context, tickDelta);
        });
    }
}

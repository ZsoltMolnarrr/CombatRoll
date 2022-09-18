package net.rolling.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.util.math.MatrixStack;
import net.rolling.client.RollingClient;
import net.rolling.client.RollingKeybings;
import net.rolling.client.gui.HudRenderHelper;

public class FabricClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        RollingClient.initialize();
        for(var keybinding: RollingKeybings.all) {
            KeyBindingHelper.registerKeyBinding(keybinding);
        }
        HudRenderCallback.EVENT.register((MatrixStack matrixStack, float tickDelta) -> {
            HudRenderHelper.render(matrixStack, tickDelta);
        });
    }
}

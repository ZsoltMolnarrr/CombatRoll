package net.combat_roll.fabric.client;

import net.combat_roll.CombatRollMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.combat_roll.client.CombatRollClient;
import net.combat_roll.client.Keybindings;
import net.combat_roll.client.gui.HudRenderHelper;
import net.fabricmc.fabric.impl.client.rendering.hud.HudElementRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class CombatRollClientModFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CombatRollClient.initialize();
        CombatRollClient.setupAnimations();
        for(var keybinding: Keybindings.all) {
            KeyBindingHelper.registerKeyBinding(keybinding);
        }

        HudElementRegistryImpl.addFirst(Identifier.of(CombatRollMod.ID, "recharge"), (context, tickCounter) -> {
            if (!MinecraftClient.getInstance().options.hudHidden) {
                HudRenderHelper.render(context, tickCounter.getTickProgress(true));
            }
        });

        FabricClientNetwork.init();
    }
}

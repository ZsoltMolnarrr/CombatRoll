package net.combat_roll.fabric.client;

import net.combat_roll.CombatRollMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.combat_roll.client.CombatRollClient;
import net.combat_roll.client.Keybindings;
import net.combat_roll.client.gui.HudRenderHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

public class CombatRollClientModFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CombatRollClient.initialize();
        CombatRollClient.setupAnimations();
        for(var keybinding: Keybindings.all) {
            KeyMappingHelper.registerKeyMapping(keybinding);
        }

        HudElementRegistry.addFirst(Identifier.fromNamespaceAndPath(CombatRollMod.ID, "recharge"), (context, tickCounter) -> {
            if (!Minecraft.getInstance().options.hideGui) {
                HudRenderHelper.render(context, tickCounter.getGameTimeDeltaPartialTick(true));
            }
        });

        FabricClientNetwork.init();
    }
}

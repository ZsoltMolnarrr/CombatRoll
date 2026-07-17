package net.combat_roll.compatibility;

import net.bettercombat.api.MinecraftClient_BetterCombat;
import net.combat_roll.Platform;
import net.minecraft.client.Minecraft;

public class BetterCombatHelper {
    public static void cancelUpswing() {
        if (Platform.isModLoaded("bettercombat")) {
            var client = Minecraft.getInstance();
            ((MinecraftClient_BetterCombat)client).cancelUpswing();
        }
    }

    public static boolean isDoingUpswing() {
        if (Platform.isModLoaded("bettercombat")) {
            var client = Minecraft.getInstance();
            return ((MinecraftClient_BetterCombat)client).getUpswingTicks() > 0;
        }
        return false;
    }
}

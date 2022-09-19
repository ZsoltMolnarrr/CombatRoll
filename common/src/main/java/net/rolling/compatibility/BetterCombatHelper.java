package net.rolling.compatibility;

import net.bettercombat.api.MinecraftClient_BetterCombat;
import net.minecraft.client.MinecraftClient;
import net.rolling.Platform;

public class BetterCombatHelper {
    public static void cancelUpswing() {
        if (Platform.isModLoaded("bettercombat")) {
            var client = MinecraftClient.getInstance();
            ((MinecraftClient_BetterCombat)client).cancelUpswing();
        }
    }

    public static boolean isDoingUpswing() {
        if (Platform.isModLoaded("bettercombat")) {
            var client = MinecraftClient.getInstance();
            return ((MinecraftClient_BetterCombat)client).getUpswingTicks() > 0;
        }
        return false;
    }
}

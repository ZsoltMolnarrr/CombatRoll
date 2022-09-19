package net.combatroll.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.combatroll.Platform;

import static net.combatroll.Platform.Type.FABRIC;

public class PlatformImpl {
    public static Platform.Type getPlatformType() {
        return FABRIC;
    }

    public static boolean isModLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }
}

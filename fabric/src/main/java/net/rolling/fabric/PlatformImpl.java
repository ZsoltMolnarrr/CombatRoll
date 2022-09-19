package net.rolling.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.rolling.Platform;

import static net.rolling.Platform.Type.FABRIC;

public class PlatformImpl {
    public static Platform.Type getPlatformType() {
        return FABRIC;
    }

    public static boolean isModLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }
}

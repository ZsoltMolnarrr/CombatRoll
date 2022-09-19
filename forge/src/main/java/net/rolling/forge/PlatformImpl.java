package net.rolling.forge;

import net.minecraftforge.fml.ModList;
import net.rolling.Platform;

import static net.rolling.Platform.Type.FORGE;

public class PlatformImpl {
    public static Platform.Type getPlatformType() {
        return FORGE;
    }

    public static boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }
}

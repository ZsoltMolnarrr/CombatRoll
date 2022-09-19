package net.combatroll.forge;

import net.minecraftforge.fml.ModList;
import net.combatroll.Platform;

import static net.combatroll.Platform.Type.FORGE;

public class PlatformImpl {
    public static Platform.Type getPlatformType() {
        return FORGE;
    }

    public static boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }
}

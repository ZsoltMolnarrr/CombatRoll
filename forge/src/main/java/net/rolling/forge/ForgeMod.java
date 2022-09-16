package net.rolling.forge;

import net.rolling.Rolling;
import net.minecraftforge.fml.common.Mod;

@Mod(Rolling.MOD_ID)
public class ForgeMod {
    public ForgeMod() {
        // Submit our event bus to let architectury register our content on the right time
        // EventBuses.registerModEventBus(Rolling.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Rolling.init();
    }
}
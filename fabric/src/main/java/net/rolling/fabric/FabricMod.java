package net.rolling.fabric;

import net.rolling.Rolling;
import net.fabricmc.api.ModInitializer;

public class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        Rolling.init();
    }
}
package net.rolling.fabric;

import net.fabricmc.api.ModInitializer;
import net.rolling.Rolling;
import net.rolling.utils.SoundHelper;

public class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        Rolling.init();
        Rolling.registerAttributes();
        SoundHelper.registerSounds();
    }
}
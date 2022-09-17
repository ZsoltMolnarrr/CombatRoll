package net.rolling.fabric;

import net.rolling.Rolling;
import net.fabricmc.api.ModInitializer;
import net.rolling.api.EntityAttributes_Rolling;
import net.rolling.utils.SoundHelper;

public class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        Rolling.init();
        var register = EntityAttributes_Rolling.all;
        SoundHelper.registerSounds();
    }
}
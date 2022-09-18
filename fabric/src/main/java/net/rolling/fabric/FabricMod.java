package net.rolling.fabric;

import net.minecraft.util.registry.Registry;
import net.rolling.Rolling;
import net.fabricmc.api.ModInitializer;
import net.rolling.api.EntityAttributes_Rolling;
import net.rolling.utils.SoundHelper;

public class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        Rolling.init();
        Registry.register(Registry.ATTRIBUTE, EntityAttributes_Rolling.distanceId, EntityAttributes_Rolling.DISTANCE);
        Registry.register(Registry.ATTRIBUTE, EntityAttributes_Rolling.rechargeId, EntityAttributes_Rolling.RECHARGE);
        Registry.register(Registry.ATTRIBUTE, EntityAttributes_Rolling.countId, EntityAttributes_Rolling.COUNT);
        SoundHelper.registerSounds();
    }
}
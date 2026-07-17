package net.combat_roll.fabric;

import net.combat_roll.CombatRollMod;
import net.combat_roll.fabric.platform.FabricServerNetwork;
import net.fabricmc.api.ModInitializer;
import net.combat_roll.utils.SoundHelper;

public class CombatRollModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CombatRollMod.init();
        SoundHelper.registerSounds();
        FabricServerNetwork.init();
    }
}
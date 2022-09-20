package net.combatroll.fabric;

import net.combatroll.CombatRoll;
import net.fabricmc.api.ModInitializer;
import net.combatroll.utils.SoundHelper;

public class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        CombatRoll.init();
        CombatRoll.registerAttributes();
        CombatRoll.registerEnchantments();
        SoundHelper.registerSounds();
    }
}
package net.combat_roll.neoforge;

import net.combat_roll.CombatRollMod;
import net.combat_roll.utils.SoundHelper;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(CombatRollMod.ID)
public final class CombatRollModNeoForge {
    public CombatRollModNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        CombatRollMod.init();
        SOUND_EVENTS.register(modEventBus);
    }

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, CombatRollMod.ID);

    static {
        SoundHelper.soundKeys.forEach(soundKey -> SOUND_EVENTS.register(soundKey, () -> SoundEvent.of(Identifier.of(CombatRollMod.ID, soundKey))));
    }
}

package net.combat_roll.mixin;

import net.combat_roll.stat.CombatRollStats;
import net.minecraft.stats.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Stats.class)
public class StatsMixin {
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void static_tail_combat_roll(CallbackInfo ci) {
        CombatRollStats.all.forEach(CombatRollStats.Entry::register);
    }
}

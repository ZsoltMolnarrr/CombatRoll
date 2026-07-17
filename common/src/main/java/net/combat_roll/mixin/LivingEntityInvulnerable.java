package net.combat_roll.mixin;

import net.combat_roll.api.RollInvulnerable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityInvulnerable implements RollInvulnerable {
    private int invulnerableTicks = 0;

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick_TAIL_combat_roll(CallbackInfo ci) {
        if (invulnerableTicks > 0) {
            invulnerableTicks -= 1;
        }
    }

    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    private void damage_HEAD_combat_roll(ServerLevel world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (invulnerableTicks > 0) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Override
    public void setRollInvulnerableTicks(int ticks) {
        invulnerableTicks = ticks;
    }
}

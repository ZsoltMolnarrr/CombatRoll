package net.combatroll.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PlayerEntity.class)
public interface PlayerEntityAccessor {
    @Invoker("isImmobile")
    boolean invokeIsImmobile_CombatRoll();
}
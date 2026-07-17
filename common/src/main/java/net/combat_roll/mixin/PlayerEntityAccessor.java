package net.combat_roll.mixin;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Player.class)
public interface PlayerEntityAccessor {
    @Invoker("isImmobile")
    boolean invokeIsImmobile_combat_roll();
}
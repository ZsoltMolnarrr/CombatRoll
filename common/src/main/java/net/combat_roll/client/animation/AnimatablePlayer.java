package net.combat_roll.client.animation;

import net.minecraft.world.phys.Vec3;

public interface AnimatablePlayer {
    void playRollAnimation(String animationName, Vec3 direction);
}

package net.combat_roll.mixin;

import com.mojang.authlib.GameProfile;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import net.combat_roll.CombatRollMod;
import net.combat_roll.client.animation.AnimatablePlayer;
import net.combat_roll.client.animation.RollAnimationController;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity implements AnimatablePlayer {
    public AbstractClientPlayerEntityMixin(World world, GameProfile gameProfile) {
        super(world, gameProfile);
    }

    @Override
    public void playRollAnimation(String animationName, Vec3d direction) {
        var controller = (RollAnimationController) PlayerAnimationAccess.getPlayerAnimationLayer(
            (AbstractClientPlayerEntity)(Object)this,
            RollAnimationController.ID
        );

        if (controller != null) {
            controller.playRoll(animationName, direction, CombatRollMod.config.roll_duration);
        }
    }
}

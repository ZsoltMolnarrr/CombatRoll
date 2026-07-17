package net.combat_roll.mixin;

import com.mojang.authlib.GameProfile;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import net.combat_roll.CombatRollMod;
import net.combat_roll.client.animation.AnimatablePlayer;
import net.combat_roll.client.animation.RollAnimationController;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerEntityMixin extends Player implements AnimatablePlayer {
    public AbstractClientPlayerEntityMixin(Level world, GameProfile gameProfile) {
        super(world, gameProfile);
    }

    @Override
    public void playRollAnimation(String animationName, Vec3 direction) {
        var controller = (RollAnimationController) PlayerAnimationAccess.getPlayerAnimationLayer(
            (AbstractClientPlayer)(Object)this,
            RollAnimationController.ID
        );

        if (controller != null) {
            controller.playRoll(animationName, direction, CombatRollMod.config.roll_duration);
        }
    }
}

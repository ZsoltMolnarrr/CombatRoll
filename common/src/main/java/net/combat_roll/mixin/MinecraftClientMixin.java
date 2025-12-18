package net.combat_roll.mixin;

import net.combat_roll.CombatRollMod;
import net.combat_roll.Platform;
import net.combat_roll.api.CombatRoll;
import net.combat_roll.client.Keybindings;
import net.combat_roll.client.RollEffect;
import net.combat_roll.compatibility.BetterCombatHelper;
import net.combat_roll.internals.RollingEntity;
import net.combat_roll.network.Packets;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.combat_roll.client.RollEffect.Particles.PUFF;

@Mixin(value = MinecraftClient.class, priority = 449)
public abstract class MinecraftClientMixin {
    @Shadow private int itemUseCooldown;
    @Shadow @Nullable public ClientPlayerEntity player;

//    @Inject(method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V",at = @At("TAIL"))
//    private void disconnect_TAIL(Screen screen, CallbackInfo ci) {
//        var rollingPlayer = ((RollingEntity)player);
//        if (rollingPlayer != null) {
//            rollingPlayer.getRollManager().isEnabled = false;
//        }
//    }

    @Inject(method = "doAttack", at = @At("HEAD"), cancellable = true)
    private void doAttack_HEAD(CallbackInfoReturnable<Boolean> info) {
        var rollingPlayer = ((RollingEntity)player);
        if (rollingPlayer != null && rollingPlayer.getRollManager().isRolling()) {
            info.setReturnValue(false);
            info.cancel();
        }
    }

    @Inject(method = "handleBlockBreaking", at = @At("HEAD"), cancellable = true)
    private void handleBlockBreaking_HEAD(boolean bl, CallbackInfo ci) {
        var rollingPlayer = ((RollingEntity)player);
        if (rollingPlayer != null && rollingPlayer.getRollManager().isRolling()) {
            ci.cancel();
        }
    }

    @Inject(method = "doItemUse", at = @At("HEAD"), cancellable = true)
    private void doItemUse_HEAD(CallbackInfo ci) {
        var rollingPlayer = ((RollingEntity)player);
        if (rollingPlayer != null && rollingPlayer.getRollManager().isRolling()) {
            ci.cancel();
        }
    }

    @Inject(method = "handleInputEvents", at = @At("TAIL"))
    private void handleInputEvents_TAIL(CallbackInfo ci) {
        tryRolling();
    }

    private void tryRolling() {
        var client = (MinecraftClient) ((Object)this);
        if (player == null || client.isPaused() || client.currentScreen != null) {
            return;
        }
        var rollingPlayer = ((RollingEntity)player);
        var rollManager = rollingPlayer.getRollManager();
        if (Keybindings.roll.isPressed()) {
            if(!rollManager.isRollAvailable(player)) {
                return;
            }
            if(!CombatRollMod.config.allow_rolling_while_airborn && !player.isOnGround()) {
                return;
            }
            if(player.getHungerManager().getFoodLevel() <= CombatRollMod.config.food_level_required) {
                return;
            }
            if(player.isSwimming() || player.isCrawling()) {
                return;
            }
            if(player.getVehicle() != null) {
                return;
            }
            if(player.isUsingItem() || player.isBlocking()) {
                return;
            }
            if (!CombatRollMod.config.allow_rolling_while_weapon_cooldown && player.getAttackCooldownProgress(0) < 0.95) {
                return;
            }
            if (BetterCombatHelper.isDoingUpswing()) {
                BetterCombatHelper.cancelUpswing();
            } else {
                if (client.options.attackKey.isPressed()) {
                    return;
                }
            }
            if (itemUseCooldown > 0) {
                return;
            }

            var forward = player.forwardSpeed;
            var sideways = player.sidewaysSpeed;
            Vec3d direction;
            if (forward == 0 && sideways == 0) {
                direction = new Vec3d(0, 0, 1);
            } else  {
                direction = new Vec3d(sideways, 0, forward).normalize();
            }
            direction = direction.rotateY((float) Math.toRadians((-1.0) * player.getYaw()));
            var distance = 0.475 *
                    (player.getAttributeValue(CombatRoll.Attributes.DISTANCE.entry)
                    + CombatRollMod.config.additional_roll_distance);
            direction = direction.multiply(distance);

            if (player.isTouchingWater()) {
                var liquidHeight = player.getFluidHeight(FluidTags.WATER);
                liquidHeight = Math.min(liquidHeight, 1F);
                var multiplier = Math.max(1 - (liquidHeight*3), 0.3);
                // System.out.println("Water! " + multiplier + " liquidHeight: " + liquidHeight);
                direction = direction.multiply(multiplier);
            }

            if (player.isInLava()) {
                var liquidHeight = player.getFluidHeight(FluidTags.LAVA);
                liquidHeight = Math.min(liquidHeight, 1F);
                // System.out.println("Lava! " + liquidHeight * 0.3);
                direction = direction.multiply(0.3);
            }

            var block = player.getEntityWorld().getBlockState(player.getBlockPos().down()).getBlock();
            var slipperiness = block.getSlipperiness();
            var defaultSlipperiness = Blocks.GRASS_BLOCK.getSlipperiness();
            if (slipperiness > defaultSlipperiness) {
                var multiplier = defaultSlipperiness / slipperiness;
                direction = direction.multiply(multiplier * multiplier);
            }

            player.addVelocity(direction.x, direction.y, direction.z);
            rollManager.onRoll(player);

            var rollVisuals = new RollEffect.Visuals(CombatRollMod.ID + ":roll", PUFF);
            Platform.networkC2S_Send(new Packets.RollPublish(player.getId(), rollVisuals, direction));
            RollEffect.playVisuals(rollVisuals, player, direction);
        }
    }
}

package net.combatroll.mixin;

import net.combatroll.CombatRoll;
import net.combatroll.Platform;
import net.combatroll.api.EntityAttributes_CombatRoll;
import net.combatroll.client.MinecraftClientExtension;
import net.combatroll.client.RollEffect;
import net.combatroll.client.Keybindings;
import net.combatroll.client.RollManager;
import net.combatroll.compatibility.BetterCombatHelper;
import net.combatroll.network.Packets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
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

import static net.combatroll.api.EntityAttributes_CombatRoll.Type.DISTANCE;
import static net.combatroll.client.RollEffect.Particles.PUFF;

@Mixin(value = MinecraftClient.class, priority = 449)
public abstract class MinecraftClientMixin implements MinecraftClientExtension {
    @Shadow private int itemUseCooldown;
    @Shadow @Nullable public ClientPlayerEntity player;
    @Shadow @Nullable public Screen currentScreen;
    private RollManager rollManager = new RollManager();

    public RollManager getRollManager() {
        return rollManager;
    }

    @Inject(method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V",at = @At("TAIL"))
    private void disconnect_TAIL(Screen screen, CallbackInfo ci) {
        rollManager.isEnabled = false;
    }

    @Inject(method = "doAttack", at = @At("HEAD"), cancellable = true)
    private void doAttack_HEAD(CallbackInfoReturnable<Boolean> info) {
        if (rollManager.isRolling()) {
            info.setReturnValue(false);
            info.cancel();
        }
    }

    @Inject(method = "handleBlockBreaking", at = @At("HEAD"), cancellable = true)
    private void handleBlockBreaking_HEAD(boolean bl, CallbackInfo ci) {
        if (rollManager.isRolling()) {
            ci.cancel();
        }
    }

    @Inject(method = "doItemUse", at = @At("HEAD"), cancellable = true)
    private void doItemUse_HEAD(CallbackInfo ci) {
        if (rollManager.isRolling()) {
            ci.cancel();
        }
    }

    @Inject(method = "handleInputEvents", at = @At("TAIL"))
    private void handleInputEvents_TAIL(CallbackInfo ci) {
        tryRolling();
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick_TAIL(CallbackInfo ci) {
        if (player != null) {
            rollManager.tick(player);
        }
    }

    private void tryRolling() {
        var client = (MinecraftClient) ((Object)this);
        if (player == null || client.isPaused() || client.currentScreen != null) {
            return;
        }
        if (Keybindings.roll.isPressed()) {
            if(!rollManager.isRollAvailable(player)) {
                return;
            }
            if(!CombatRoll.config.allow_rolling_while_airborn && !player.isOnGround()) {
                return;
            }
            if(player.getHungerManager().getFoodLevel() <= CombatRoll.config.food_level_required) {
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
            if (!CombatRoll.config.allow_rolling_while_weapon_cooldown && player.getAttackCooldownProgress(0) < 0.95) {
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

            var forward = player.input.movementForward;
            var sideways = player.input.movementSideways;
            Vec3d direction;
            if (forward == 0 && sideways == 0) {
                direction = new Vec3d(0, 0, 1);
            } else  {
                direction = new Vec3d(sideways, 0, forward).normalize();
            }

            var RelativeDirection = direction; //We will use this later to determine which animation we have to play

            direction = direction.rotateY((float) Math.toRadians((-1.0) * player.getYaw()));
            var distance = 0.475 *
                    (EntityAttributes_CombatRoll.getAttributeValue(player, DISTANCE)
                            + CombatRoll.config.additional_roll_distance);
            direction = direction.multiply(distance);

            if (player.isTouchingWater()) {
                var liquidHeight = player.getFluidHeight(FluidTags.WATER);
                liquidHeight = Math.min(liquidHeight, 1F);
                // System.out.println("Water! " + liquidHeight * 0.5);
                direction = direction.multiply(liquidHeight * 0.5);
            }

            if (player.isInLava()) {
                var liquidHeight = player.getFluidHeight(FluidTags.LAVA);
                liquidHeight = Math.min(liquidHeight, 1F);
                // System.out.println("Lava! " + liquidHeight * 0.3);
                direction = direction.multiply(0.3);
            }

            var block = player.getWorld().getBlockState(player.getBlockPos().down()).getBlock();
            var slipperiness = block.getSlipperiness();
            var defaultSlipperiness = Blocks.GRASS.getSlipperiness();
            if (slipperiness > defaultSlipperiness) {
                var multiplier = defaultSlipperiness / slipperiness;
                direction = direction.multiply(multiplier * multiplier);
            }

            player.addVelocity(direction.x, direction.y, direction.z);
            rollManager.onRoll(player);

            var fwdDir = RelativeDirection.getZ();
            var sideDir = RelativeDirection.getX();
            var rollAngle = Math.toDegrees(Math.acos(fwdDir) * (sideDir >= 0 ? 1.0 : -1.0));

            var animName = "";

            if (rollAngle >= -22.5 && rollAngle <= 22.5) {
                animName = "forwards";

            } else if (rollAngle > 22.5 && rollAngle < 67.5) {
                animName = "forwardsleft";

            } else if (rollAngle >= 67.5 && rollAngle <= 112.5) {
                animName = "left";

            } else if (rollAngle > 112.5 && rollAngle < 157.5) {
                animName = "backwardsleft";

            } else if (rollAngle >= 157.5 && rollAngle <= 202.5) {
                animName = "backwards";

            } else if (rollAngle > -157.5 && rollAngle < -112.5) {
                animName = "backwardsright";

            } else if (rollAngle >= -112.5 && rollAngle <= -67.5) {
                animName = "right";

            } else {
                animName = "forwardsright";
            }

            var rollVisuals = new RollEffect.Visuals(CombatRoll.MOD_ID + ":" + animName + "roll", PUFF);
            ClientPlayNetworking.send(
                    Packets.RollPublish.ID,
                    new Packets.RollPublish(player.getId(), rollVisuals, direction).write());
            RollEffect.playVisuals(rollVisuals, player, direction);
        }
    }
}

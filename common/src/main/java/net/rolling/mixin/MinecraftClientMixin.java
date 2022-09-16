package net.rolling.mixin;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.rolling.api.EntityAttributes_Rolling;
import net.rolling.client.AnimatablePlayer;
import net.rolling.client.RollManager;
import net.rolling.client.RollingKeybings;
import net.rolling.network.Packets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow @Nullable public ClientPlayerEntity player;
    private RollManager rollManager = new RollManager();

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick_TAIL(CallbackInfo ci) {
        var player = MinecraftClient.getInstance().player;
        if (player == null) {
            return;
        }
        rollManager.tick(player);

//        var cooldown = rollManager.getCooldown();
//        System.out.println("Roll cd: " + cooldown);

        if (RollingKeybings.roll.wasPressed()) {
            if(!rollManager.isRollAvailable()) {
//                 var cooldown = rollManager.getCooldown();
//                 System.out.println("Roll not ready " + cooldown);
                return;
            }
            if(!player.isOnGround()) {
                // System.out.println("Not on the ground");
                return;
            }
            if(player.getVehicle() != null) {
                // System.out.println("Mounted");
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
            direction = direction.rotateY((float) Math.toRadians((-1.0) * player.getYaw()));
            var distance = 0.475 * player.getAttributeValue(EntityAttributes_Rolling.DISTANCE);
            direction = direction.multiply(distance);
            player.addVelocity(direction.x, direction.y, direction.z);
            rollManager.onRoll(player);
            var animation = "rolling:roll";
            ClientPlayNetworking.send(
                    Packets.RollAnimation.ID,
                    new Packets.RollAnimation(player.getId(), animation).write());
            ((AnimatablePlayer)player).playRollAnimation(animation);
        }
    }
}

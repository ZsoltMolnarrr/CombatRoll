package net.rolling.mixin;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.rolling.api.EntityAttributes_Rolling;
import net.rolling.client.RollEffect;
import net.rolling.client.RollManager;
import net.rolling.client.RollingKeybings;
import net.rolling.network.Packets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

import static net.rolling.client.RollEffect.Particles.PUFF;

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
                return;
            }
            if(!player.isOnGround()) {
                return;
            }
            if(player.getVehicle() != null) {
                return;
            }
            if (player.isUsingItem() || player.isBlocking()) {
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

            var rollVisuals = new RollEffect.Visuals("rolling:roll", PUFF);
            ClientPlayNetworking.send(
                    Packets.RollPublish.ID,
                    new Packets.RollPublish(player.getId(), rollVisuals, direction).write());
            RollEffect.playVisuals(rollVisuals, player);
        }
    }
}

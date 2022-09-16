package net.rolling.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import net.rolling.api.EntityAttributes_Rolling;
import net.rolling.client.RollManager;
import net.rolling.client.RollingKeybings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    private RollManager rollManager = new RollManager();

    @Inject(method = "tick",at = @At("TAIL"))
    private void tick_TAIL(CallbackInfo ci) {
        var player = MinecraftClient.getInstance().player;
        if (player == null) {
            return;
        }
        rollManager.tick(player);
        if (RollingKeybings.roll.wasPressed()) {
            if(!rollManager.isRollAvailable()) {
                // var cooldown = rollManager.getCooldown();
                // System.out.println("Roll not ready " + cooldown);
                return;
            }
            if(!player.isOnGround()) {
                // System.out.println("Not on the ground");
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
            var distance = player.getAttributeValue(EntityAttributes_Rolling.DISTANCE);
            direction = direction.multiply(0.475 * distance);
            player.addVelocity(direction.x, direction.y, direction.z);
            rollManager.onRoll(player);
        }
    }
}

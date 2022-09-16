package net.rolling.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import net.rolling.client.RollingKeybings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(method = "tick",at = @At("TAIL"))
    private void tick_TAIL(CallbackInfo ci) {
        if (RollingKeybings.roll.wasPressed()) {
            var player = MinecraftClient.getInstance().player;
            var forward = player.input.movementForward;
            var sideways = player.input.movementSideways;
            Vec3d direction;
            if (forward == 0 && sideways == 0) {
                direction = new Vec3d(0, 0, 1);
            } else  {
                direction = new Vec3d(sideways, 0, forward).normalize();
            }
            System.out.println("Direction: " + direction);
            direction = direction.rotateY((float) Math.toRadians((-1.0) * player.getYaw()));
            System.out.println("Rotated by: " + player.getYaw() + " direction: " + direction);
            direction = direction.multiply(3);
            player.addVelocity(direction.x, direction.y, direction.z);
        }
    }
}

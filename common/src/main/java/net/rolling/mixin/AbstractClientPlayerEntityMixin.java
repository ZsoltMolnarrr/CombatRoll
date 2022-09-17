package net.rolling.mixin;

import com.mojang.authlib.GameProfile;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.util.Vec3f;
import dev.kosmx.playerAnim.impl.IAnimatedPlayer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rolling.client.animation.AdjustmentModifier;
import net.rolling.client.animation.AnimatablePlayer;
import net.rolling.client.animation.AnimationRegistry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity implements AnimatablePlayer {
    public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile, @Nullable PlayerPublicKey publicKey) {
        super(world, pos, yaw, gameProfile, publicKey);
    }

    private final ModifierLayer base = new ModifierLayer(null);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void postInit(ClientWorld world, GameProfile profile, PlayerPublicKey publicKey, CallbackInfo ci) {
        var stack = ((IAnimatedPlayer) this).getAnimationStack();
        base.addModifier(createAdjustmentModifier(), 0);
        stack.addAnimLayer(1000, base);
    }

    private Vec3d lastRollDirection;

    public void playRollAnimation(String animationName, Vec3d direction) {
        try {
            var player = (PlayerEntity) ((Object)this);
            KeyframeAnimation animation = AnimationRegistry.animations.get(animationName);
            var copy = animation.mutableCopy();
            lastRollDirection = direction;

            var fadeIn = copy.beginTick;
            base.setAnimation(new KeyframeAnimationPlayer(copy.build(), 0));
//            base.replaceAnimationWithFade(
//                    AbstractFadeModifier.standardFadeIn(fadeIn, Ease.INOUTSINE),
//                    new KeyframeAnimationPlayer(copy.build(), 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AdjustmentModifier createAdjustmentModifier() {
        var player = (PlayerEntity)this;
        return new AdjustmentModifier((partName) -> {
            float rotationX = 0;
            float rotationY = 0;
            float rotationZ = 0;
            float offsetX = 0;
            float offsetY = 0;
            float offsetZ = 0;

            switch (partName) {
                case "body" -> {
                    if (lastRollDirection != null) {
                        var absoluteOrientation = new Vec3d(0,0,1).rotateY((float) Math.toRadians(-1.0 * player.bodyYaw));
                        float angle = (float) angleWithSignBetween(absoluteOrientation, lastRollDirection, new Vec3d(0,1,0));
                        System.out.println("AdjustmentModifier body angle: " + angle);
                        rotationY = (float) Math.toRadians(angle); // + 180;
                    } else {
                        return Optional.empty();
                    }
                }
                default -> {
                    return Optional.empty();
                }
            }

            return Optional.of(new AdjustmentModifier.PartModifier(
                    new Vec3f(rotationX, rotationY, rotationZ),
                    new Vec3f(offsetX, offsetY, offsetZ))
            );
        });
    }
    private double angleWithSignBetween(Vec3d a, Vec3d b, Vec3d planeNormal) {
        var cosineTheta = a.dotProduct(b) / (a.length() * b.length());
        var angle = Math.toDegrees(Math.acos(cosineTheta));
        var cross = a.crossProduct(b);
        angle *= Math.signum(cross.dotProduct(planeNormal));
        if (Double.isNaN(angle)) {
            return 0;
        }
        return angle;
    }
}

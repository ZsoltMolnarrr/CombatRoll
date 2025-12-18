package net.combat_roll.client.animation;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.animation.PlayerAnimResources;
import com.zigythebird.playeranimcore.animation.layered.modifier.AdjustmentModifier;
import com.zigythebird.playeranimcore.animation.layered.modifier.SpeedModifier;
import com.zigythebird.playeranimcore.math.Vec3f;
import net.combat_roll.CombatRollMod;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class RollAnimationController extends PlayerAnimationController {
    public static final Identifier ID = Identifier.of(CombatRollMod.ID, "roll");

    private final SpeedModifier speedModifier;
    private Vec3d lastRollDirection;

    public RollAnimationController(AbstractClientPlayerEntity player, AnimationStateHandler animationHandler) {
        super(player, animationHandler);
        this.speedModifier = new SpeedModifier(1.2f);
        postInit();
    }

    private void postInit() {
        this.addModifier(speedModifier, 0);
        this.addModifierLast(createAdjustmentModifier());
    }

    public void playRoll(String animationName, Vec3d direction, int duration) {
        try {
            this.lastRollDirection = direction;
            var animation = PlayerAnimResources.getAnimation(Identifier.of(animationName));
            float length = animation.length();
            speedModifier.speed = length / ((float) duration);
            this.triggerAnimation(animation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AdjustmentModifier createAdjustmentModifier() {
        return new AdjustmentModifier((partName) -> {
            if (!partName.equals("body")) {
                return java.util.Optional.empty();
            }
            if (lastRollDirection == null) {
                return java.util.Optional.empty();
            }

            var player = this.getPlayer();
//            var absoluteOrientation = new Vec3d(0, 0, 1)
//                .rotateY((float) Math.toRadians(-1.0 * player.bodyYaw));
//            float angle = (float) angleWithSignBetween(
//                absoluteOrientation, lastRollDirection, new Vec3d(0, 1, 0)
//            );

            System.out.println("Player Yaw: " + player.getYaw() + ", Body Yaw: " + player.getBodyYaw() + ", Head Yaw: " + player.getHeadYaw());
            var absoluteOrientation = new Vec3d(0,0,1).rotateY((float) Math.toRadians(-1F * player.getYaw()));
            float angle = (float) angleWithSignBetween(absoluteOrientation, lastRollDirection.normalize(), new Vec3d(0,1,0));
            System.out.println("Absolute Orientation: " + absoluteOrientation + ", Last Roll Direction: " + lastRollDirection + ", Angle: " + angle);

            var rotationY = Math.abs(angle) > 100 ? (float) Math.toRadians(angle) : 0; // + 180;
            return java.util.Optional.of(new AdjustmentModifier.PartModifier(
                new Vec3f(0, rotationY, 0),
                new Vec3f(0, 0, 0)
            ));
        });
    }

    private double angleWithSignBetween(Vec3d a, Vec3d b, Vec3d planeNormal) {
        var cosineTheta = a.dotProduct(b) / (a.length() * b.length());
        var angle = Math.toDegrees(Math.acos(cosineTheta));
        var cross = a.crossProduct(b);
        angle *= Math.signum(cross.dotProduct(planeNormal));
        return Double.isNaN(angle) ? 0 : angle;
    }
}

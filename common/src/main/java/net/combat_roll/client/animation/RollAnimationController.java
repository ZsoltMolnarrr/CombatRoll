package net.combat_roll.client.animation;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.animation.PlayerAnimResources;
import com.zigythebird.playeranimcore.animation.layered.modifier.AdjustmentModifier;
import com.zigythebird.playeranimcore.animation.layered.modifier.SpeedModifier;
import com.zigythebird.playeranimcore.math.Vec3f;
import net.combat_roll.CombatRollMod;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.phys.Vec3;

public class RollAnimationController extends PlayerAnimationController {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(CombatRollMod.ID, "roll");

    private final SpeedModifier speedModifier;
    private Vec3 lastRollDirection;

    public RollAnimationController(Avatar player, AnimationStateHandler animationHandler) {
        super(player, animationHandler);
        this.speedModifier = new SpeedModifier(1.2f);
        postInit();
    }

    private void postInit() {
        this.addModifier(speedModifier, 0);
        this.addModifierLast(createAdjustmentModifier());
    }

    public void playRoll(String animationName, Vec3 direction, int duration) {
        try {
            this.lastRollDirection = direction;
            var animation = PlayerAnimResources.getAnimation(Identifier.parse(animationName));
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

            var player = this.getAvatar();
            var absoluteOrientation = new Vec3(0,0,1).yRot((float) Math.toRadians(-1F * player.getYRot()));
            float angle = (float) angleWithSignBetween(absoluteOrientation, lastRollDirection, new Vec3(0,1,0));

            var rotationY = Math.abs(angle) > 100 ? (float) Math.toRadians(angle) : 0; // + 180;
            return java.util.Optional.of(new AdjustmentModifier.PartModifier(
                new Vec3f(0, rotationY, 0),
                new Vec3f(0, 0, 0)
            ));
        });
    }

    private double angleWithSignBetween(Vec3 a, Vec3 b, Vec3 planeNormal) {
        // Normalize vectors to ensure magnitude doesn't affect angle calculation
        Vec3 normalizedA = a.normalize();
        Vec3 normalizedB = b.normalize();

        // Calculate cosine of angle using normalized vectors
        var cosineTheta = normalizedA.dot(normalizedB);

        // Clamp to valid domain [-1, 1] to handle floating-point precision errors
        cosineTheta = Math.max(-1.0, Math.min(1.0, cosineTheta));

        // Calculate unsigned angle
        var angle = Math.toDegrees(Math.acos(cosineTheta));

        // Determine sign using cross product
        var cross = normalizedA.cross(normalizedB);
        var crossDotPlane = cross.dot(planeNormal);

        // Handle degenerate case: when vectors are parallel/anti-parallel, cross product is ~0
        // In this case, return the unsigned angle (0° or 180°)
        if (Math.abs(crossDotPlane) < 1e-6) {
            return angle;
        }

        // Apply sign to angle
        angle *= Math.signum(crossDotPlane);

        return angle;
    }
}

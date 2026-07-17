package net.combat_roll.client;

import net.combat_roll.client.animation.AnimatablePlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import java.util.Random;

public record RollEffect(Visuals visuals, String soundId) {
    public record Visuals(String animationName, Particles particles) { }
    public enum Particles {
        PUFF
    }

    private static Random random = new Random();
    public static void playVisuals(Visuals visuals, Player player, Vec3 direction) {
        ((AnimatablePlayer)player).playRollAnimation(visuals.animationName(), direction);
        if (CombatRollClient.config.playRollSound) {
            var sound = BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("combat_roll:roll"));
            if (sound != null) {
                player.level().playLocalSound(player.getX(), player.getY(), player.getZ(), sound, SoundSource.PLAYERS, 1, 1, true);
            }
        }
        switch (visuals.particles()) {
            case PUFF -> {
                for(int i = 0; i < 15; ++i) {
                    double d = random.nextGaussian() * 0.02;
                    double e = random.nextGaussian() * 0.02;
                    double f = random.nextGaussian() * 0.02;
                    player.level().addParticle(ParticleTypes.POOF,
                            player.getRandomX(1.5),
                            player.getY(random.nextGaussian() * 0.3),
                            player.getRandomZ(1.5), d, e, f);
                }
            }
        }
    }
}

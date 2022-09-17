package net.rolling.client;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.rolling.api.EntityAttributes_Rolling;

public class RollManager {
    public static final int rollDuration = 10;
    private int timeSinceLastRoll = rollDuration;
    private int currentCooldownLength = 0;
    private int maxRolls = 1;
    private int availableRolls = 0;

    public record CooldownInfo(int elapsed, int total, int availableRolls, int maxRolls) { }

    public CooldownInfo getCooldown() {
        return new CooldownInfo(timeSinceLastRoll, currentCooldownLength, availableRolls, maxRolls);
    }

    public boolean isRollAvailable() {
        return !isRolling() && availableRolls > 0;
    }

    public boolean isRolling() {
        return timeSinceLastRoll <= rollDuration;
    }

    public void onRoll(ClientPlayerEntity player) {
        availableRolls -= 1;
        timeSinceLastRoll = 0;
        updateCooldownLength(player);
    }

    public void tick(ClientPlayerEntity player) {
        maxRolls = (int) player.getAttributeValue(EntityAttributes_Rolling.COUNT);
        timeSinceLastRoll += 1;
        if (availableRolls < maxRolls) {
            if (timeSinceLastRoll >= currentCooldownLength) {
                rechargeRoll(player);
            }
        }
        if (availableRolls > maxRolls) {
            availableRolls = maxRolls;
        }
    }

//    private static SoundEvent cooldownReady = Registry.SOUND_EVENT.get(new Identifier("rolling:roll_cooldown_ready"));

    private void rechargeRoll(ClientPlayerEntity player) {
        availableRolls += 1;
        timeSinceLastRoll = 0;
        updateCooldownLength(player);
        var cooldownReady = Registry.SOUND_EVENT.get(new Identifier("rolling:roll_cooldown_ready"));
        if (cooldownReady != null) {
            player.world.playSound(player.getX(), player.getY(), player.getZ(), cooldownReady, SoundCategory.PLAYERS, 1, 1, false);
        }
    }

    private void updateCooldownLength(ClientPlayerEntity player) {
        currentCooldownLength = (int) Math.round(4F * 20F * (20F / player.getAttributeValue(EntityAttributes_Rolling.RECHARGE)));
    }
}

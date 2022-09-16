package net.rolling.client;

import net.minecraft.client.network.ClientPlayerEntity;
import net.rolling.api.EntityAttributes_Rolling;

public class RollManager {
    private int timeSinceLastRoll = 0;
    private int currentCooldownLength = 0;
    private int maxRolls = 1;
    private int availableRolls = 0;

    public record CooldownInfo(int elapsed, int total, int availableRolls, int maxRolls) { }

    public CooldownInfo getCooldown() {
        return new CooldownInfo(timeSinceLastRoll, currentCooldownLength, availableRolls, maxRolls);
    }

    public boolean isRollAvailable() {
        return timeSinceLastRoll > 5 && availableRolls > 0;
    }

    public void onRoll(ClientPlayerEntity player) {
        availableRolls -= 1;
        timeSinceLastRoll = 0;
        currentCooldownLength = (int) Math.round(player.getAttributeValue(EntityAttributes_Rolling.COOLDOWN) * 20F);
    }

    public void tick(ClientPlayerEntity player) {
        maxRolls = (int) player.getAttributeValue(EntityAttributes_Rolling.COUNT);
        if (availableRolls <= maxRolls) {
            timeSinceLastRoll += 1;
            if (timeSinceLastRoll >= currentCooldownLength) {
                availableRolls += 1;
            }
        } else {
            availableRolls = maxRolls;
        }
    }
}

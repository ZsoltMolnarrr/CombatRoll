package net.combat_roll.internals;

import net.combat_roll.CombatRollMod;
import net.combat_roll.api.CombatRoll;
import net.combat_roll.client.CombatRollClient;
import net.combat_roll.mixin.PlayerEntityAccessor;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;

public class RollManager {
    public boolean isEnabled = true;
    public static int rollDuration() {
        return CombatRollMod.config.roll_duration;
    }
    private int timeSinceLastRoll = 10;
    private float currentCooldownProgress = 0;
    private int currentCooldownLength = 0;
    private int maxRolls = 1;
    private int availableRolls = 0;

    public RollManager() { }

    public record CooldownInfo(int elapsed, int total, int availableRolls, int maxRolls) { }

    public CooldownInfo getCooldown() {
        return new CooldownInfo((int)currentCooldownProgress, currentCooldownLength, availableRolls, maxRolls);
    }

    public boolean isRollAvailable(PlayerEntity player) {
        return isEnabled
                && !isRolling()
                && availableRolls > 0
                && !((PlayerEntityAccessor)player).invokeIsImmobile_combat_roll()
                && player.canMoveVoluntarily()
                && player.getAttributeValue(EntityAttributes.MOVEMENT_SPEED) > 0;
    }

    public boolean isRolling() {
        return timeSinceLastRoll <= rollDuration();
    }

    public void onRoll(ClientPlayerEntity player) {
        availableRolls -= 1;
        timeSinceLastRoll = 0;
        updateCooldownLength(player);
    }

    public void tick(ClientPlayerEntity player) {
        maxRolls = (int) player.getAttributeValue(CombatRoll.Attributes.COUNT.entry);
        timeSinceLastRoll += 1;
        if (availableRolls < maxRolls) {
            currentCooldownProgress += increment(player);
            if (currentCooldownProgress >= currentCooldownLength) {
                rechargeRoll(player);
            }
        }
        if (availableRolls == maxRolls) {
            currentCooldownProgress = 0;
        }
        if (availableRolls > maxRolls) {
            availableRolls = maxRolls;
        }
    }

    private float increment(PlayerEntity player) {
        return (float) (player.getAttributeValue(CombatRoll.Attributes.RECHARGE.entry) / 20F);
    }

    private void rechargeRoll(ClientPlayerEntity player) {
        availableRolls += 1;
        currentCooldownProgress = Math.max(currentCooldownProgress - currentCooldownLength, 0);
        updateCooldownLength(player);
        if (CombatRollClient.config.playCooldownSound) {
            var cooldownReady = Registries.SOUND_EVENT.get(Identifier.of("combat_roll:roll_cooldown_ready"));
            if (cooldownReady != null) {
                player.getEntityWorld().playSoundClient(player.getX(), player.getY(), player.getZ(), cooldownReady, SoundCategory.PLAYERS, 1, 1, false);
            }
        }
    }

    private void updateCooldownLength(ClientPlayerEntity player) {
        var duration = CombatRollMod.config.roll_cooldown;
        currentCooldownLength = Math.round(duration * 20F);
    }
}

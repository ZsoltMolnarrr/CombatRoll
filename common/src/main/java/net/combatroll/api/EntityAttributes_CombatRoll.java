package net.combatroll.api;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class EntityAttributes_CombatRoll {
    public static final String distanceId = "combat_roll.distance";
    public static final EntityAttribute DISTANCE = (new ClampedEntityAttribute("attribute.name." + distanceId, 3.0, 1, 24.0)).setTracked(true);
    public static final String rechargeId = "combat_roll.recharge";
    public static final EntityAttribute RECHARGE = (new ClampedEntityAttribute("attribute.name." + rechargeId, 20, 0.1, 200)).setTracked(true);
    public static final String countId = "combat_roll.count";
    public static final EntityAttribute COUNT = (new ClampedEntityAttribute("attribute.name." + countId, 1, 1, 20.0)).setTracked(true);

    public static List<EntityAttribute> all;
    static {
        all = List.of(DISTANCE, RECHARGE, COUNT);
    }

    // Helper

    public enum Type {
        DISTANCE, RECHARGE, COUNT
    }

    public static double getAttributeValue(PlayerEntity player, Type type) {
        switch (type) {
            case DISTANCE -> {
                var value = player.getAttributeValue(DISTANCE);
                var level = EnchantmentHelper.getEquipmentLevel(Enchantments_CombatRoll.DISTANCE, player);
                value += Enchantments_CombatRoll.DISTANCE.getAdditionValue(level);
                return value;
            }
            case RECHARGE -> {
                var value = player.getAttributeValue(RECHARGE);
                var chestLevel = EnchantmentHelper.getEquipmentLevel(Enchantments_CombatRoll.RECHARGE_CHEST, player);
                value *= Enchantments_CombatRoll.RECHARGE_CHEST.getMultiplierValue(chestLevel);
                var legsLevel = EnchantmentHelper.getEquipmentLevel(Enchantments_CombatRoll.RECHARGE_LEGS, player);
                value *= Enchantments_CombatRoll.RECHARGE_LEGS.getMultiplierValue(legsLevel);
                return value;
            }
            case COUNT -> {
                var value = player.getAttributeValue(COUNT);
                var level = EnchantmentHelper.getEquipmentLevel(Enchantments_CombatRoll.COUNT, player);
                value += Enchantments_CombatRoll.COUNT.getAdditionValue(level);
                return value;
            }
        }
        return 1; // Should never happen
    }
}

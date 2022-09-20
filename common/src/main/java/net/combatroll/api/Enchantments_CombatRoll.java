package net.combatroll.api;

import net.combatroll.enchantments.AdditionEnchantment;
import net.combatroll.enchantments.MultiplierEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;

import static net.minecraft.enchantment.EnchantmentTarget.*;

public class Enchantments_CombatRoll {
    // Longfooted
    public static final String distanceId = "longfooted";
    public static final AdditionEnchantment DISTANCE = new AdditionEnchantment(
            Enchantment.Rarity.UNCOMMON,
            5, 10, 9,
            ARMOR_FEET,
            new EquipmentSlot[]{ EquipmentSlot.FEET });

    // Acrobat
    public static final String rechargeChestId = "acrobat_chest";
    public static final MultiplierEnchantment RECHARGE_CHEST = new MultiplierEnchantment(
            Enchantment.Rarity.RARE,
            10, 15, 9,
            ARMOR_CHEST,
            new EquipmentSlot[]{ EquipmentSlot.CHEST });
    public static final String rechargeLegsId = "acrobat_legs";
    public static final MultiplierEnchantment RECHARGE_LEGS = new MultiplierEnchantment(
            Enchantment.Rarity.RARE,
            10, 15, 9,
            ARMOR_LEGS,
            new EquipmentSlot[]{ EquipmentSlot.LEGS });

    // Multi-Roll
    public static final String countId = "multi_roll";
    public static final AdditionEnchantment COUNT = new AdditionEnchantment(
            Enchantment.Rarity.VERY_RARE,
            4, 15, 9,
            ARMOR_HEAD,
            new EquipmentSlot[]{ EquipmentSlot.HEAD });
}

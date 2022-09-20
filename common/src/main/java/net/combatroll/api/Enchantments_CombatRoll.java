package net.combatroll.api;

import net.combatroll.CombatRoll;
import net.combatroll.enchantments.AdditionEnchantment;
import net.combatroll.enchantments.MultiplierEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;

import static net.minecraft.enchantment.EnchantmentTarget.*;

public class Enchantments_CombatRoll {
    // Longfooted
    public static final String distanceName = "longfooted";
    public static final Identifier distanceId = new Identifier(CombatRoll.MOD_ID + ":" + distanceName);
    public static final AdditionEnchantment DISTANCE = new AdditionEnchantment(
            Enchantment.Rarity.UNCOMMON,
            5, 10, 9,
            ARMOR_FEET,
            new EquipmentSlot[]{ EquipmentSlot.FEET });

    // Acrobat
    public static final String rechargeChestName = "acrobat_chest";
    public static final Identifier rechargeChestId = new Identifier(CombatRoll.MOD_ID + ":" + rechargeChestName);
    public static final MultiplierEnchantment RECHARGE_CHEST = new MultiplierEnchantment(
            Enchantment.Rarity.RARE,
            10, 15, 9,
            ARMOR_CHEST,
            new EquipmentSlot[]{ EquipmentSlot.CHEST });

    public static final String rechargeLegsName = "acrobat_legs";
    public static final Identifier rechargeLegsId = new Identifier(CombatRoll.MOD_ID + ":" + rechargeLegsName);
    public static final MultiplierEnchantment RECHARGE_LEGS = new MultiplierEnchantment(
            Enchantment.Rarity.RARE,
            10, 15, 9,
            ARMOR_LEGS,
            new EquipmentSlot[]{ EquipmentSlot.LEGS });

    // Multi-Roll
    public static final String countName = "multi_roll";
    public static final Identifier countId = new Identifier(CombatRoll.MOD_ID + ":" + countName);
    public static final AdditionEnchantment COUNT = new AdditionEnchantment(
            Enchantment.Rarity.VERY_RARE,
            4, 15, 9,
            ARMOR_HEAD,
            new EquipmentSlot[]{ EquipmentSlot.HEAD });
}

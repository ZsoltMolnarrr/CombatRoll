package net.combatroll.api;

import net.combatroll.CombatRoll;
import net.combatroll.enchantments.AmplifierEnchantment;
import net.combatroll.enchantments.CustomConditionalEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import static net.combatroll.enchantments.AmplifierEnchantment.Operation.ADD;
import static net.combatroll.enchantments.AmplifierEnchantment.Operation.MULTIPLY;
import static net.minecraft.enchantment.EnchantmentTarget.*;

public class Enchantments_CombatRoll {
    // Longfooted
    public static final String distanceName = "longfooted";
    public static final Identifier distanceId = new Identifier(CombatRoll.MOD_ID + ":" + distanceName);
    public static final AmplifierEnchantment DISTANCE = new AmplifierEnchantment(
            Enchantment.Rarity.RARE,
            ADD,
            CombatRoll.enchantmentConfig.currentConfig.longfooted,
            ARMOR_FEET,
            new EquipmentSlot[]{ EquipmentSlot.FEET });

    // Acrobat
    public static final String rechargeName = "acrobat";
    public static final Identifier rechargeId = new Identifier(CombatRoll.MOD_ID + ":" + rechargeName);
    public static final AmplifierEnchantment RECHARGE = new AmplifierEnchantment(
            Enchantment.Rarity.RARE,
            MULTIPLY,
            CombatRoll.enchantmentConfig.currentConfig.acrobat,
            WEARABLE,
            new EquipmentSlot[]{ EquipmentSlot.CHEST, EquipmentSlot.LEGS })
            .condition(stack ->
                    ARMOR_CHEST.isAcceptableItem(stack.getItem())
                    || ARMOR_LEGS.isAcceptableItem(stack.getItem())
            );

    // Multi-Roll
    public static final String countName = "multi_roll";
    public static final Identifier countId = new Identifier(CombatRoll.MOD_ID + ":" + countName);
    public static final AmplifierEnchantment COUNT = new AmplifierEnchantment(
            Enchantment.Rarity.VERY_RARE,
            ADD,
            CombatRoll.enchantmentConfig.currentConfig.multi_roll,
            ARMOR_HEAD,
            new EquipmentSlot[]{ EquipmentSlot.HEAD });
}

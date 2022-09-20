package net.combatroll.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class AdditionEnchantment extends Enchantment {
    private int maxLevel;
    private float scaleFactor = 1F;
    private int minCost;
    private int stepCost;

    public float getAdditionValue(int level) {
        return ((float)level) * scaleFactor;
    }

    public AdditionEnchantment(Rarity weight, int maxLevel, int minCost, int powerStep, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
        this.maxLevel = maxLevel;
        this.minCost = minCost;
        this.stepCost = powerStep;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getMinPower(int level) {
        return minCost + (level - 1) * stepCost;
    }

    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }
}

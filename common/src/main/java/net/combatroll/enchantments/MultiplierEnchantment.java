package net.combatroll.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class MultiplierEnchantment extends Enchantment {
    private int maxLevel;
    private int minCost;
    private int stepCost;
    private float scaleFactor = 0.1F;

    public float getMultiplierValue(int level) {
        return 1F + ((float)level) * scaleFactor;
    }

    public MultiplierEnchantment(Rarity weight, int maxLevel, int minCost, int stepCost, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
        this.maxLevel = maxLevel;
        this.minCost = minCost;
        this.stepCost = stepCost;
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

package net.combatroll.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class AmplifierEnchantment extends Enchantment {
    private int maxLevel;
    private int minCost;
    private int stepCost;
    public float amplifierBase = 1F;
    public float amplifierScale = 0.1F;

    public float getAmplifierValue(int level) {
        return amplifierBase + ((float)level) * amplifierScale;
    }

    public AmplifierEnchantment(Rarity weight, int maxLevel, int minCost, int stepCost, float amplifierBase, float amplifierScale, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
        this.maxLevel = maxLevel;
        this.minCost = minCost;
        this.stepCost = stepCost;
        this.amplifierBase = amplifierBase;
        this.amplifierScale = amplifierScale;
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

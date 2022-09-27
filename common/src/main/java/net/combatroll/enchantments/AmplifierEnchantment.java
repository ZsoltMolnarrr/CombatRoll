package net.combatroll.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class AmplifierEnchantment extends Enchantment {
    public Operation operation;
    public enum Operation {
        ADD, MULTIPLY;
    }

    public Properties properties;
    public static class Properties {
        public boolean enabled = true;
        public int max_level = 0;
        public int min_cost = 0;
        public int step_cost = 0;
        public float bonus_per_level = 0;

        public Properties() { }

        public Properties(int max_level, int min_cost, int step_cost, float bonus_per_level) {
            this.max_level = max_level;
            this.min_cost = min_cost;
            this.step_cost = step_cost;
            this.bonus_per_level = bonus_per_level;
        }
    }

    public double apply(double value, int level) {
        switch (operation) {
            case ADD -> {
                return value += ((float)level) * properties.bonus_per_level;
            }
            case MULTIPLY -> {
                return value *= 1F + ((float)level) * properties.bonus_per_level;
            }
        }
        assert true;
        return 0F;
    }

    public AmplifierEnchantment(Rarity weight, Operation operation, Properties properties, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
        this.operation = operation;
        this.properties = properties;
    }

    public int getMaxLevel() {
        return properties.max_level;
    }

    public int getMinPower(int level) {
        return properties.min_cost + (level - 1) * properties.step_cost;
    }

    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }
}

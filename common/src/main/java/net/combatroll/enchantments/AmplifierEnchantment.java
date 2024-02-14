package net.combatroll.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.tinyconfig.models.EnchantmentConfig;

public class AmplifierEnchantment extends Enchantment implements CustomConditionalEnchantment {
    public Operation operation;
    public enum Operation {
        ADD, MULTIPLY;
    }

    public EnchantmentConfig properties;

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

    public AmplifierEnchantment(Rarity weight, Operation operation, EnchantmentConfig properties, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
        this.operation = operation;
        this.properties = properties;
    }

    public int getMaxLevel() {
        if (!properties.enabled) {
            return 0;
        }
        return properties.max_level;
    }

    public int getMinPower(int level) {
        return properties.min_cost + (level - 1) * properties.step_cost;
    }

    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return properties.enabled;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return properties.enabled;
    }

    // MARK: CustomConditionalEnchantment

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        if (condition != null) {
            return condition.isAcceptableItem(stack);
        }
        return super.isAcceptableItem(stack);
    }

    private Condition condition;

    @Override
    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public AmplifierEnchantment condition(Condition condition) {
        setCondition(condition);
        return this;
    }
}

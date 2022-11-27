package net.combatroll.enchantments;

import net.minecraft.item.ItemStack;

public interface CustomConditionalEnchantment {
    interface Condition {
        boolean isAcceptableItem(ItemStack stack);
    }
    void setCondition(Condition condition);
}

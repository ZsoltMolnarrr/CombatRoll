package net.combatroll.mixin;

import net.combatroll.enchantments.CustomConditionalEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(method = "getPossibleEntries", at = @At("RETURN"))
    private static void getPossibleEntries_Return(int power, ItemStack stack, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir) {
        var entries = cir.getReturnValue();
        var toRemove = new ArrayList<EnchantmentLevelEntry>();
        for (var entry: entries) {
            var enchantment = entry.enchantment;
            if (enchantment instanceof CustomConditionalEnchantment) {
                if (!entry.enchantment.isAcceptableItem(stack)) {
                    toRemove.add(entry);
                }
            }
        }
        entries.removeAll(toRemove);
    }
}

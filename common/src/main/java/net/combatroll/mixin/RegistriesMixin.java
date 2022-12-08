package net.combatroll.mixin;

import net.combatroll.CombatRoll;
import net.minecraft.registry.Registries;
import net.combatroll.Platform;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Registries.class)
public class RegistriesMixin {
    @Inject(method = "freezeRegistries", at = @At("HEAD"))
    private static void freezeRegistries_HEAD_CombatRoll(CallbackInfo ci) {
        if (Platform.Forge) {
            CombatRoll.registerAttributes();
            CombatRoll.registerEnchantments();
        }
    }
}

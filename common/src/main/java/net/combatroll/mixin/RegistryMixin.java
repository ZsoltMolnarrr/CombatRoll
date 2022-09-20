package net.combatroll.mixin;

import net.combatroll.CombatRoll;
import net.minecraft.util.registry.Registry;
import net.combatroll.Platform;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Registry.class)
public class RegistryMixin {
    @Inject(method = "freezeRegistries", at = @At("HEAD"))
    private static void pre_freezeRegistries(CallbackInfo ci) {
        if (Platform.Forge) {
            CombatRoll.registerAttributes();
            CombatRoll.registerEnchantments();
        }
    }
}

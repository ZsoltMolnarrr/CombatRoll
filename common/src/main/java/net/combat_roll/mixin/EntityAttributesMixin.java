package net.combat_roll.mixin;

import net.combat_roll.api.CombatRoll;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Attributes.class) // Low priority to be applied as last
public class EntityAttributesMixin {
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void static_tail_combat_roll(CallbackInfo ci) {
        CombatRoll.Attributes.all.forEach(CombatRoll.Attributes.Entry::register);
    }
}

package net.combatroll.mixin;

import net.combatroll.api.EntityAttributes_CombatRoll;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin {
    @Inject(
            method = "createPlayerAttributes",
            require = 1, allow = 1, at = @At("RETURN"))
    private static void addAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        for (var attribute: EntityAttributes_CombatRoll.all) {
            cir.getReturnValue().add(attribute);
        }
    }
}
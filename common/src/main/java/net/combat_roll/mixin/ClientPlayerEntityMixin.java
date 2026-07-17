package net.combat_roll.mixin;

import net.combat_roll.CombatRollMod;
import net.combat_roll.internals.RollManager;
import net.combat_roll.internals.RollingEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Input;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public class ClientPlayerEntityMixin implements RollingEntity {
    private RollManager rollManager = new RollManager();
    public RollManager getRollManager() {
        return rollManager;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick_TAIL(CallbackInfo ci) {
        var player = (LocalPlayer) ((Object)this);
        if (player != null) {
            rollManager.tick(player);
        }
    }

    @Shadow
    @Final
    protected Minecraft minecraft;

    @Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/ClientInput;tick()V", shift = At.Shift.AFTER))
    private void tickMovement_ModifyInput(CallbackInfo ci) {
        var clientPlayer = (LocalPlayer) ((Object) this);
        var config = CombatRollMod.config;
        if (!config.allow_jump_while_rolling && rollManager.isRolling()) {
            var input = clientPlayer.input.keyPresses;
            clientPlayer.input.keyPresses = new Input(
                    input.forward(),
                    input.backward(),
                    input.left(),
                    input.right(),
                    false,
                    input.shift(),
                    input.sprint()
            );
        }
    }

    @Inject(method = "canAutoJump", at = @At("HEAD"), cancellable = true)
    public void shouldAutoJump_HEAD(CallbackInfoReturnable<Boolean> cir) {
        var config = CombatRollMod.config;
        if (config != null) {
            if (rollManager.isRolling() && !config.allow_auto_jump_while_rolling) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
}
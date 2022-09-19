package net.combatroll.forge;

import net.combatroll.api.EntityAttributes_CombatRoll;
import net.fabricmc.fabric.api.networking.v1.NetworkHandler;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.combatroll.CombatRoll;
import net.minecraftforge.fml.common.Mod;
import net.combatroll.client.gui.ConfigMenuScreen;
import net.combatroll.utils.SoundHelper;

@Mod(CombatRoll.MOD_ID)
public class ForgeMod {

    public static DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CombatRoll.MOD_ID);

    public ForgeMod() {
        // EventBuses.registerModEventBus(Rolling.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        CombatRoll.init();
        NetworkHandler.registerMessages();
        registerSounds();
        SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> {
            return new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> {
                return new ConfigMenuScreen(screen);
            });
        });
    }

    @SubscribeEvent
    public void register(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.ATTRIBUTES,
            helper -> {
                helper.register(EntityAttributes_CombatRoll.distanceId, EntityAttributes_CombatRoll.DISTANCE);
                helper.register(EntityAttributes_CombatRoll.rechargeId, EntityAttributes_CombatRoll.RECHARGE);
                helper.register(EntityAttributes_CombatRoll.countId, EntityAttributes_CombatRoll.COUNT);
            }
        );
    }

    private void registerSounds() {
        for (var soundKey: SoundHelper.soundKeys) {
            SOUNDS.register(soundKey, () -> new SoundEvent(new Identifier(CombatRoll.MOD_ID, soundKey)));
        }
    }
}
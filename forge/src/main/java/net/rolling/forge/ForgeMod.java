package net.rolling.forge;

import net.fabricmc.fabric.api.networking.v1.NetworkHandler;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.rolling.Rolling;
import net.minecraftforge.fml.common.Mod;
import net.rolling.api.EntityAttributes_Rolling;
import net.rolling.utils.SoundHelper;

@Mod(Rolling.MOD_ID)
public class ForgeMod {

    public static DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Rolling.MOD_ID);

    public ForgeMod() {
        // EventBuses.registerModEventBus(Rolling.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Rolling.init();
        NetworkHandler.registerMessages();
        registerSounds();
        SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public void register(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.ATTRIBUTES,
            helper -> {
                helper.register(EntityAttributes_Rolling.distanceId, EntityAttributes_Rolling.DISTANCE);
                helper.register(EntityAttributes_Rolling.rechargeId, EntityAttributes_Rolling.RECHARGE);
                helper.register(EntityAttributes_Rolling.countId, EntityAttributes_Rolling.COUNT);
            }
        );
    }

    private void registerSounds() {
        for (var soundKey: SoundHelper.soundKeys) {
            SOUNDS.register(soundKey, () -> new SoundEvent(new Identifier(Rolling.MOD_ID, soundKey)));
        }
    }
}
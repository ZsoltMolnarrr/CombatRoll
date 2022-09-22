package net.combatroll.forge;

import net.combatroll.CombatRoll;
import net.combatroll.utils.SoundHelper;
import net.fabricmc.fabric.api.networking.v1.NetworkHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(CombatRoll.MOD_ID)
public class ForgeMod {

    public static DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CombatRoll.MOD_ID);

    public ForgeMod() {
        // EventBuses.registerModEventBus(Rolling.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        CombatRoll.init();
        NetworkHandler.registerMessages();
        registerSounds();
        SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public void registerAttributes(RegistryEvent.Register<EntityAttribute> event) {
        CombatRoll.registerAttributes();
    }

    @SubscribeEvent
    public void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
        CombatRoll.registerEnchantments();
    }

    private void registerSounds() {
        for (var soundKey: SoundHelper.soundKeys) {
            SOUNDS.register(soundKey, () -> new SoundEvent(new Identifier(CombatRoll.MOD_ID, soundKey)));
        }
    }
}
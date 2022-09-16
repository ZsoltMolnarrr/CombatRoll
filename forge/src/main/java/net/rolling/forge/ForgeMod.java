package net.rolling.forge;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.rolling.Rolling;
import net.minecraftforge.fml.common.Mod;
import net.rolling.api.EntityAttributes_Rolling;

@Mod(Rolling.MOD_ID)
public class ForgeMod {
    public ForgeMod() {
        // EventBuses.registerModEventBus(Rolling.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Rolling.init();
    }

    @SubscribeEvent
    public void register(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.ATTRIBUTES,
            helper -> {
                var asd = EntityAttributes_Rolling.all;
            }
        );
    }
}
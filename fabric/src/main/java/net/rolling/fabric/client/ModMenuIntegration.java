package net.rolling.fabric.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.clothconfig2.api.ConfigScreen;
import me.shedaniel.clothconfig2.gui.ClothConfigScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.rolling.client.hud.ConfigWrapperScreen;
import net.rolling.client.hud.HudConfigScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.rolling.config.ClientConfigWrapper;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
//        return { parent -> {
//            var asd = AutoConfig.getConfigScreen(ClientConfigWrapper.class, parent);
//            return asd.get();
//        }};
//        return parent -> AutoConfig.getConfigScreen(ClientConfigWrapper.class, parent).get();

        return (parent) -> {
            return new HudConfigScreen(parent);
        };

//        return (parent) -> {
//            return new ConfigWrapperScreen(new HudConfigScreen(parent));
//        };

//        return parent -> {
//            var screen = AutoConfig.getConfigScreen(ClientConfigWrapper.class, parent).get();
//            return screen;
//        };
    }
}
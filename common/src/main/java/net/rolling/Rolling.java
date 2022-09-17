package net.rolling;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.minecraft.client.resource.language.I18n;
import net.rolling.config.ServerConfig;
import net.rolling.config.ServerConfigWrapper;
import net.rolling.network.ServerNetwork;

public class Rolling {
    public static final String MOD_ID = "rolling";
    public static String modName() {
        return I18n.translate("rolling.mod_name");
    }

    public static ServerConfig config;

    public static void init() {
        AutoConfig.register(ServerConfigWrapper.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
        config = AutoConfig.getConfigHolder(ServerConfigWrapper.class).getConfig().server;
        ServerNetwork.initializeHandlers();
    }
}
package net.rolling;

import net.minecraft.client.resource.language.I18n;
import net.rolling.network.ServerNetwork;

public class Rolling {
    public static final String MOD_ID = "rolling";

    public static String modName() {
        return I18n.translate("rolling.mod_name");
    }

    public static void init() {
        ServerNetwork.initializeHandlers();
    }
}
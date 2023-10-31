package net.fabricmc.fabric.api.networking.v1;

import net.combatroll.CombatRoll;
import net.minecraft.util.Identifier;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;

public class NetworkHandler {
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static void registerMessages(){
        INSTANCE = ChannelBuilder.named(new Identifier(CombatRoll.MOD_ID, "network"))
                .networkProtocolVersion(1)
                .acceptedVersions((s, v) -> true)
                .simpleChannel();
        INSTANCE.messageBuilder(PacketWrapper.class)
                .encoder(PacketWrapper::encode)
                .decoder(PacketWrapper::decode)
                .consumerNetworkThread(PacketWrapper::handle)
                .add();

    }
}

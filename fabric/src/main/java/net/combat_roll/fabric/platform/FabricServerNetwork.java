package net.combat_roll.fabric.platform;

import net.combat_roll.CombatRollMod;
import net.combat_roll.network.Packets;
import net.combat_roll.network.ServerNetwork;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayerConfigurationTask;

import java.util.function.Consumer;

public class FabricServerNetwork {
    public static void init() {
        // Config stage
        PayloadTypeRegistry.configurationS2C().register(Packets.ConfigSync.PACKET_ID, Packets.ConfigSync.CODEC);
        PayloadTypeRegistry.configurationC2S().register(Packets.Ack.PACKET_ID, Packets.Ack.CODEC);

        ServerConfigurationConnectionEvents.CONFIGURE.register((handler, server) -> {
            // This if block is required! Otherwise the client gets stuck in connection screen
            // if the client cannot handle the packet.
            if (ServerConfigurationNetworking.canSend(handler, Packets.ConfigSync.ID)) {
                handler.addTask(new ConfigurationTask(Packets.ConfigSync.serialize(CombatRollMod.config)));
            }
        });

        ServerConfigurationNetworking.registerGlobalReceiver(Packets.Ack.PACKET_ID, (packet, context) -> {
            if (packet.code().equals(ConfigurationTask.name)) {
                context.networkHandler().completeTask(ConfigurationTask.KEY);
            }
        });

        // Play stage
        PayloadTypeRegistry.playC2S().register(Packets.RollPublish.PACKET_ID, Packets.RollPublish.CODEC);
        PayloadTypeRegistry.playS2C().register(Packets.RollAnimation.PACKET_ID, Packets.RollAnimation.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(Packets.RollPublish.PACKET_ID, (packet, context) -> {
            ServerNetwork.handleRollPublish(packet, context.server(), context.player());
        });
    }

    public record ConfigurationTask(String configString) implements ServerPlayerConfigurationTask {
        public static final String name = CombatRollMod.ID + ":" + "config";
        public static final ServerPlayerConfigurationTask.Key KEY = new ServerPlayerConfigurationTask.Key(name);

        @Override
        public ServerPlayerConfigurationTask.Key getKey() {
            return KEY;
        }

        @Override
        public void sendPacket(Consumer<Packet<?>> sender) {
            sender.accept(ServerConfigurationNetworking.createS2CPacket(new Packets.ConfigSync(this.configString)));
        }
    }
}

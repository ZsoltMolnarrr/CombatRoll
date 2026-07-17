package net.combat_roll.fabric.platform;

import net.combat_roll.CombatRollMod;
import net.combat_roll.network.Packets;
import net.combat_roll.network.ServerNetwork;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.protocol.Packet;
import java.util.function.Consumer;

public class FabricServerNetwork {
    public static void init() {
        // Config stage
        PayloadTypeRegistry.clientboundConfiguration().register(Packets.ConfigSync.PACKET_ID, Packets.ConfigSync.CODEC);
        PayloadTypeRegistry.serverboundConfiguration().register(Packets.Ack.PACKET_ID, Packets.Ack.CODEC);

        ServerConfigurationConnectionEvents.CONFIGURE.register((handler, server) -> {
            // This if block is required! Otherwise the client gets stuck in connection screen
            // if the client cannot handle the packet.
            if (ServerConfigurationNetworking.canSend(handler, Packets.ConfigSync.ID)) {
                handler.addTask(new ConfigurationTask(Packets.ConfigSync.serialize(CombatRollMod.config)));
            }
        });

        ServerConfigurationNetworking.registerGlobalReceiver(Packets.Ack.PACKET_ID, (packet, context) -> {
            if (packet.code().equals(ConfigurationTask.name)) {
                context.packetListener().completeTask(ConfigurationTask.KEY);
            }
        });

        // Play stage
        PayloadTypeRegistry.serverboundPlay().register(Packets.RollPublish.PACKET_ID, Packets.RollPublish.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(Packets.RollAnimation.PACKET_ID, Packets.RollAnimation.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(Packets.RollPublish.PACKET_ID, (packet, context) -> {
            ServerNetwork.handleRollPublish(packet, context.server(), context.player());
        });
    }

    public record ConfigurationTask(String configString) implements net.minecraft.server.network.ConfigurationTask {
        public static final String name = CombatRollMod.ID + ":" + "config";
        public static final net.minecraft.server.network.ConfigurationTask.Type KEY = new net.minecraft.server.network.ConfigurationTask.Type(name);

        @Override
        public net.minecraft.server.network.ConfigurationTask.Type type() {
            return KEY;
        }

        @Override
        public void start(Consumer<Packet<?>> sender) {
            sender.accept(ServerConfigurationNetworking.createClientboundPacket(new Packets.ConfigSync(this.configString)));
        }
    }
}

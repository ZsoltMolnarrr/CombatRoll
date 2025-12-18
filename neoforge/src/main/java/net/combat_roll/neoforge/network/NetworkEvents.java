package net.combat_roll.neoforge.network;

import com.google.gson.Gson;
import net.combat_roll.CombatRollMod;
import net.combat_roll.client.ClientNetwork;
import net.combat_roll.network.Packets;
import net.combat_roll.network.ServerNetwork;
import net.minecraft.network.listener.ServerConfigurationPacketListener;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerConfigurationTask;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.configuration.ICustomConfigurationTask;
import net.neoforged.neoforge.network.event.RegisterConfigurationTasksEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.function.Consumer;

@EventBusSubscriber(modid = CombatRollMod.ID)
public class NetworkEvents {
    @SubscribeEvent
    public static void register(final RegisterConfigurationTasksEvent event) {
        event.register(new ConfigurationTask(event.getListener()));
    }

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        // Server

        registrar.configurationToServer(Packets.Ack.PACKET_ID, Packets.Ack.CODEC, (payload, context) -> {
            if (payload.code().equals(ConfigurationTask.ID.toString())) {
                context.finishCurrentTask(ConfigurationTask.KEY);
            }
        });

        registrar.playToServer(Packets.RollPublish.PACKET_ID, Packets.RollPublish.CODEC, (packet, context) -> {
            var player = (ServerPlayerEntity)context.player();
            var server = player.getEntityWorld().getServer();
            ServerNetwork.handleRollPublish(packet, server, player);
        });

        // Client

        registrar.configurationToClient(Packets.ConfigSync.PACKET_ID, Packets.ConfigSync.CODEC, (packet, context) -> {
            ClientNetwork.handleConfigSync(packet);
            context.reply(new Packets.Ack(ConfigurationTask.ID.toString()));
        });

        registrar.playToClient(Packets.RollAnimation.PACKET_ID, Packets.RollAnimation.CODEC, (packet, context) -> {
            ClientNetwork.handleRollAnimation(packet);
        });
    }

    public record ConfigurationTask(ServerConfigurationPacketListener listener) implements ICustomConfigurationTask {
        public static final Identifier ID = Identifier.of(CombatRollMod.ID, "config");
        public static final ServerPlayerConfigurationTask.Key KEY = new ServerPlayerConfigurationTask.Key(Identifier.of(CombatRollMod.ID, "config"));
        @Override
        public void run(Consumer<CustomPayload> sender) {
            var gson = new Gson();
            var configString = gson.toJson(CombatRollMod.config);
            var configPayload = new Packets.ConfigSync(configString);
            sender.accept(configPayload);
        }

        @Override
        public Key getKey() {
            return KEY;
        }
    }
}

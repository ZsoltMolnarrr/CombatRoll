package net.rolling.client;

import com.google.gson.Gson;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.rolling.Rolling;
import net.rolling.network.Packets;

public class ClientNetwork {
    public static void initializeHandlers() {
        ClientPlayNetworking.registerGlobalReceiver(Packets.RollAnimation.ID, (client, handler, buf, responseSender) -> {
            final var packet = Packets.RollAnimation.read(buf);
            client.execute(() -> {
                var entity = client.world.getEntityById(packet.playerId());
                if (entity instanceof PlayerEntity player) {
                    RollEffect.playVisuals(packet.visuals(), player, packet.velocity());
                }
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(Packets.ConfigSync.ID, (client, handler, buf, responseSender) -> {
            var config = Packets.ConfigSync.read(buf);
             var gson = new Gson();
             System.out.println("Received server config: " + gson.toJson(config));
            ((MinecraftClientExtension)client).getRollManager().isEnabled = true;
            Rolling.config = config;
        });
    }
}

package net.rolling.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
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
    }
}

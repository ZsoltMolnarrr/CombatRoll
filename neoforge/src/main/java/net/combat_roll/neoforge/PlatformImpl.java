package net.combat_roll.neoforge;

import net.combat_roll.Platform;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Collection;

import static net.combat_roll.Platform.Type.NEOFORGE;

public class PlatformImpl {
    public static Platform.Type getPlatformType() {
        return NEOFORGE;
    }

    public static boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }

    public static Collection<ServerPlayer> tracking(ServerPlayer player) {
        return (Collection<ServerPlayer>) player.level().players();
    }

    public static Collection<ServerPlayer> around(ServerLevel world, Vec3 origin, double distance) {
        return world.getPlayers((player) -> player.position().distanceToSqr(origin) <= (distance*distance));
    }

    public static boolean networkS2C_CanSend(ServerPlayer player, CustomPacketPayload.Type<?> packetId) {
        return true;
    }

    public static void networkS2C_Send(ServerPlayer player, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayer(player, payload);
    }

    public static void networkC2S_Send(CustomPacketPayload payload) {
        ClientPacketDistributor.sendToServer(payload);
    }
}

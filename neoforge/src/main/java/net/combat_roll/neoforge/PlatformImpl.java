package net.combat_roll.neoforge;

import net.combat_roll.Platform;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
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

    public static Collection<ServerPlayerEntity> tracking(ServerPlayerEntity player) {
        return (Collection<ServerPlayerEntity>) player.getEntityWorld().getPlayers();
    }

    public static Collection<ServerPlayerEntity> around(ServerWorld world, Vec3d origin, double distance) {
        return world.getPlayers((player) -> player.getEntityPos().squaredDistanceTo(origin) <= (distance*distance));
    }

    public static boolean networkS2C_CanSend(ServerPlayerEntity player, CustomPayload.Id<?> packetId) {
        return true;
    }

    public static void networkS2C_Send(ServerPlayerEntity player, CustomPayload payload) {
        PacketDistributor.sendToPlayer(player, payload);
    }

    public static void networkC2S_Send(CustomPayload payload) {
        ClientPacketDistributor.sendToServer(payload);
    }
}

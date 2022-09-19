package net.combatroll.api.event;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class ServerSideRollEvents {
    public static final Event<PlayerStartRolling> PLAYER_START_ROLLING = new Event.Proxy<PlayerStartRolling>();

    public interface PlayerStartRolling {
        void onPlayerStartedRolling(ServerPlayerEntity player, Vec3d velocity);
    }
}
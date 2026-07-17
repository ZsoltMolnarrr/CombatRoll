package net.combat_roll.api.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class ServerSideRollEvents {
    public static final Event<PlayerStartRolling> PLAYER_START_ROLLING = new Event.Proxy<PlayerStartRolling>();

    public interface PlayerStartRolling {
        void onPlayerStartedRolling(ServerPlayer player, Vec3 velocity);
    }
}
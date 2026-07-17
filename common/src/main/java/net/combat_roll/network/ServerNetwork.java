package net.combat_roll.network;

import com.google.common.collect.Iterables;
import net.combat_roll.CombatRollMod;
import net.combat_roll.Platform;
import net.combat_roll.api.RollInvulnerable;
import net.combat_roll.api.event.Event;
import net.combat_roll.api.event.ServerSideRollEvents;
import net.combat_roll.stat.CombatRollStats;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class ServerNetwork {
    public static String configSerialized = "";

    public static void initializeHandlers() {
        configSerialized = Packets.ConfigSync.serialize(CombatRollMod.config);
    }

    public static void handleRollPublish(Packets.RollPublish packet, MinecraftServer server, ServerPlayer player) {
        ServerLevel world = Iterables.tryFind(server.getAllLevels(), (element) -> element == player.level())
                .orNull();
        final var velocity = packet.velocity();
        final var forwardPacket = new Packets.RollAnimation(player.getId(), packet.visuals(), packet.velocity());
        Platform.tracking(player).forEach(serverPlayer -> {
            try {
                if (serverPlayer.getId() != player.getId() && Platform.networkS2C_CanSend(serverPlayer, Packets.RollAnimation.PACKET_ID)) {
                    Platform.networkS2C_Send(serverPlayer, forwardPacket);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        world.getServer().executeIfPossible(() -> {
            ((RollInvulnerable)player).setRollInvulnerableTicks(CombatRollMod.config.invulnerable_ticks_upon_roll);
            player.causeFoodExhaustion(CombatRollMod.config.exhaust_on_roll);
            player.awardStat(CombatRollStats.ROLL.stat);
            // 1.5D to approximate difference between velocity and actual travel
            player.awardStat(CombatRollStats.ROLL_CM.stat, (int)(velocity.length() * 100 * 1.5D));
            var proxy = (Event.Proxy<ServerSideRollEvents.PlayerStartRolling>)ServerSideRollEvents.PLAYER_START_ROLLING;
            proxy.handlers.forEach(hander -> { hander.onPlayerStartedRolling(player, velocity);});
        });
    }
}

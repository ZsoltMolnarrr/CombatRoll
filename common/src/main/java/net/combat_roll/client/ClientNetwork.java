package net.combat_roll.client;

import com.google.gson.Gson;
import net.combat_roll.CombatRollMod;
import net.combat_roll.config.ServerConfig;
import net.combat_roll.internals.RollingEntity;
import net.combat_roll.network.Packets;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class ClientNetwork {
    public static void handleRollAnimation(Packets.RollAnimation packet) {
        var client = Minecraft.getInstance(); // NeoForge network handler context doesn't provide a client instance
        client.execute(() -> {
            var entity = client.level.getEntity(packet.playerId());
            if (entity instanceof Player player) {
                RollEffect.playVisuals(packet.visuals(), player, packet.velocity());
            }
        });
    }

    public static void handleConfigSync(Packets.ConfigSync packet) {
        var client = Minecraft.getInstance(); // NeoForge network handler context doesn't provide a client instance
        var rollingPlayer = ((RollingEntity)client.player);
        if (rollingPlayer != null) {
            rollingPlayer.getRollManager().isEnabled = true;
        }
        var gson = new Gson();
        var config = gson.fromJson(packet.json(), ServerConfig.class);
        CombatRollMod.config = config;
    }
}

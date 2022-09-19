package net.combatroll.network;

import com.google.gson.Gson;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.combatroll.CombatRoll;
import net.combatroll.client.RollEffect;
import net.combatroll.config.ServerConfig;

public class Packets {
    public record RollPublish(int playerId, RollEffect.Visuals visuals, Vec3d velocity) {
        public static Identifier ID = new Identifier(CombatRoll.MOD_ID, "publish");

        public PacketByteBuf write() {
            PacketByteBuf buffer = PacketByteBufs.create();
            buffer.writeInt(playerId);
            buffer.writeString(visuals.animationName());
            buffer.writeString(visuals.particles().toString());
            buffer.writeDouble(velocity.x);
            buffer.writeDouble(velocity.y);
            buffer.writeDouble(velocity.z);
            return buffer;
        }

        public static RollPublish read(PacketByteBuf buffer) {
            int playerId = buffer.readInt();
            var visuals = new RollEffect.Visuals(
                    buffer.readString(),
                    RollEffect.Particles.valueOf(buffer.readString()));
            Vec3d velocity = new Vec3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
            return new RollPublish(playerId, visuals, velocity);
        }
    }

    public record RollAnimation(int playerId, RollEffect.Visuals visuals, Vec3d velocity) {
        public static Identifier ID = new Identifier(CombatRoll.MOD_ID, "animation");

        public PacketByteBuf write() {
            PacketByteBuf buffer = PacketByteBufs.create();
            buffer.writeInt(playerId);
            buffer.writeString(visuals.animationName());
            buffer.writeString(visuals.particles().toString());
            buffer.writeDouble(velocity.x);
            buffer.writeDouble(velocity.y);
            buffer.writeDouble(velocity.z);
            return buffer;
        }

        public static RollAnimation read(PacketByteBuf buffer) {
            int playerId = buffer.readInt();
            var visuals = new RollEffect.Visuals(
                    buffer.readString(),
                    RollEffect.Particles.valueOf(buffer.readString()));
            Vec3d velocity = new Vec3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
            return new RollAnimation(playerId, visuals, velocity);
        }
    }

    public static class ConfigSync {
        public static Identifier ID = new Identifier(CombatRoll.MOD_ID, "config_sync");

        public static PacketByteBuf write(ServerConfig config) {
            var gson = new Gson();
            var json = gson.toJson(config);
            var buffer = PacketByteBufs.create();
            buffer.writeString(json);
            return buffer;
        }

        public static ServerConfig read(PacketByteBuf buffer) {
            var gson = new Gson();
            var json = buffer.readString();
            var config = gson.fromJson(json, ServerConfig.class);
            return config;
        }
    }
}

package net.rolling.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.rolling.Rolling;
import net.rolling.client.RollEffect;

public class Packets {
    public record RollPublish(int playerId, RollEffect.Visuals visuals, Vec3d velocity) {
        public static Identifier ID = new Identifier(Rolling.MOD_ID, "publish");

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

    public record RollAnimation(int playerId, RollEffect.Visuals visuals) {
        public static Identifier ID = new Identifier(Rolling.MOD_ID, "animation");

        public PacketByteBuf write() {
            PacketByteBuf buffer = PacketByteBufs.create();
            buffer.writeInt(playerId);
            buffer.writeString(visuals.animationName());
            buffer.writeString(visuals.particles().toString());
            return buffer;
        }

        public static RollAnimation read(PacketByteBuf buffer) {
            int playerId = buffer.readInt();
            var visuals = new RollEffect.Visuals(
                    buffer.readString(),
                    RollEffect.Particles.valueOf(buffer.readString()));
            return new RollAnimation(playerId, visuals);
        }
    }
}

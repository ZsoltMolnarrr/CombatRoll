package net.rolling.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.rolling.Rolling;

public class Packets {
    public record RollAnimation(int playerId, String animationName) {
        public static Identifier ID = new Identifier(Rolling.MOD_ID, "animation");

        public PacketByteBuf write() {
            PacketByteBuf buffer = PacketByteBufs.create();
            buffer.writeInt(playerId);
            buffer.writeString(animationName);
            return buffer;
        }

        public static RollAnimation read(PacketByteBuf buffer) {
            int playerId = buffer.readInt();
            String animationName = buffer.readString();
            return new RollAnimation(playerId, animationName);
        }
    }
}

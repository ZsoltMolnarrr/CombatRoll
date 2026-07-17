package net.combat_roll.network;

import com.google.gson.Gson;
import net.combat_roll.CombatRollMod;
import net.combat_roll.client.RollEffect;
import net.combat_roll.config.ServerConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

public class Packets {
    public record RollPublish(int playerId, RollEffect.Visuals visuals, Vec3 velocity) implements CustomPacketPayload {
        public static final Identifier ID = Identifier.fromNamespaceAndPath(CombatRollMod.ID, "publish");
        public static final CustomPacketPayload.Type<RollPublish> PACKET_ID = new CustomPacketPayload.Type<>(ID);
        public static final StreamCodec<RegistryFriendlyByteBuf, RollPublish> CODEC = StreamCodec.ofMember(RollPublish::write, RollPublish::read);

        public static RollPublish read(RegistryFriendlyByteBuf buffer) {
            int playerId = buffer.readInt();
            var visuals = new RollEffect.Visuals(
                    buffer.readUtf(),
                    RollEffect.Particles.valueOf(buffer.readUtf()));
            Vec3 velocity = new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
            return new RollPublish(playerId, visuals, velocity);
        }

        public void write(RegistryFriendlyByteBuf buffer) {
            buffer.writeInt(playerId);
            buffer.writeUtf(visuals.animationName());
            buffer.writeUtf(visuals.particles().toString());
            buffer.writeDouble(velocity.x);
            buffer.writeDouble(velocity.y);
            buffer.writeDouble(velocity.z);
        }

        public Type<? extends CustomPacketPayload> type() {
            return PACKET_ID;
        }
    }

    public record RollAnimation(int playerId, RollEffect.Visuals visuals, Vec3 velocity) implements CustomPacketPayload {
        public static final Identifier ID = Identifier.fromNamespaceAndPath(CombatRollMod.ID, "animation");
        public static final CustomPacketPayload.Type<RollAnimation> PACKET_ID = new CustomPacketPayload.Type<>(ID);
        public static final StreamCodec<RegistryFriendlyByteBuf, RollAnimation> CODEC = StreamCodec.ofMember(RollAnimation::write, RollAnimation::read);

        public static RollAnimation read(RegistryFriendlyByteBuf buffer) {
            int playerId = buffer.readInt();
            var visuals = new RollEffect.Visuals(
                    buffer.readUtf(),
                    RollEffect.Particles.valueOf(buffer.readUtf()));
            Vec3 velocity = new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
            return new RollAnimation(playerId, visuals, velocity);
        }

        public void write(RegistryFriendlyByteBuf buffer) {
            buffer.writeInt(playerId);
            buffer.writeUtf(visuals.animationName());
            buffer.writeUtf(visuals.particles().toString());
            buffer.writeDouble(velocity.x);
            buffer.writeDouble(velocity.y);
            buffer.writeDouble(velocity.z);
        }

        public Type<? extends CustomPacketPayload> type() {
            return PACKET_ID;
        }
    }

    public record ConfigSync(String json) implements CustomPacketPayload {
        public static final Identifier ID = Identifier.fromNamespaceAndPath(CombatRollMod.ID, "config_sync");
        public static final CustomPacketPayload.Type<ConfigSync> PACKET_ID = new CustomPacketPayload.Type<>(ID);
        public static final StreamCodec<FriendlyByteBuf, ConfigSync> CODEC = StreamCodec.ofMember(ConfigSync::write, ConfigSync::read);

        private static final Gson gson = new Gson();
        public static String serialize(ServerConfig config) {
            return gson.toJson(config);
        }

        public void write(FriendlyByteBuf buffer) {
            buffer.writeUtf(json);
        }

        public static ConfigSync read(FriendlyByteBuf buffer) {
            var json = buffer.readUtf();
            return new ConfigSync(json);
        }

        public Type<? extends CustomPacketPayload> type() {
            return PACKET_ID;
        }
    }

    public record Ack(String code) implements CustomPacketPayload {
        public static final Identifier ID = Identifier.fromNamespaceAndPath(CombatRollMod.ID, "ack");
        public static final CustomPacketPayload.Type<Ack> PACKET_ID = new CustomPacketPayload.Type<>(ID);
        public static final StreamCodec<FriendlyByteBuf, Ack> CODEC = StreamCodec.ofMember(Ack::write, Ack::read);

        public void write(FriendlyByteBuf buffer) {
            buffer.writeUtf(code);
        }

        public static Ack read(FriendlyByteBuf buffer) {
            var code = buffer.readUtf();
            return new Ack(code);
        }

        public Type<? extends CustomPacketPayload> type() {
            return PACKET_ID;
        }
    }
}

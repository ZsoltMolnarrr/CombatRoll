package net.combat_roll;

import dev.architectury.injectables.annotations.ExpectPlatform;
import java.util.Collection;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class Platform {
    public static final boolean Fabric;
    public static final boolean Forge;
    public static final boolean NeoForge;

    static
    {
        Fabric = getPlatformType() == Type.FABRIC;
        Forge  = getPlatformType() == Type.FORGE;
        NeoForge = getPlatformType() == Type.NEOFORGE;
    }

    public enum Type { FABRIC, FORGE, NEOFORGE }

    @ExpectPlatform
    protected static Type getPlatformType() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isModLoaded(String modid) {
        throw new AssertionError();
    }

    // MARK: Network hooks

    @ExpectPlatform
    public static Collection<ServerPlayer> tracking(ServerPlayer player) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Collection<ServerPlayer> around(ServerLevel world, Vec3 origin, double distance) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean networkS2C_CanSend(ServerPlayer player, CustomPacketPayload.Type<?> packetId) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void networkS2C_Send(ServerPlayer player, CustomPacketPayload payload) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void networkC2S_Send(CustomPacketPayload payload) {
        throw new AssertionError();
    }
}

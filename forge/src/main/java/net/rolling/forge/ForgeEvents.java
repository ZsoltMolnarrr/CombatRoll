package net.rolling.forge;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.rolling.Rolling;

@Mod.EventBusSubscriber(modid = Rolling.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().world.isClient())
            ServerPlayConnectionEvents.onPlayerJoined.forEach((action) -> action.onPlayReady(
                    ((ServerPlayerEntity) event.getEntity()).networkHandler,
                    (id, data) -> ServerPlayNetworking.send((ServerPlayerEntity) event.getEntity(), id, data),
                    event.getEntity().getServer()
            ));
    }
}

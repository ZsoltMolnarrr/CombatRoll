package net.combatroll;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.combatroll.api.Enchantments_CombatRoll;
import net.combatroll.api.EntityAttributes_CombatRoll;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.combatroll.config.ServerConfig;
import net.combatroll.config.ServerConfigWrapper;
import net.combatroll.network.ServerNetwork;

public class CombatRoll {
    public static final String MOD_ID = "combatroll";
    public static String modName() {
        return I18n.translate("combatroll.mod_name");
    }

    public static ServerConfig config;

    public static void init() {
        AutoConfig.register(ServerConfigWrapper.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
        config = AutoConfig.getConfigHolder(ServerConfigWrapper.class).getConfig().server;
        ServerNetwork.initializeHandlers();
    }

    public static void registerAttributes() {
        Registry.register(Registry.ATTRIBUTE, EntityAttributes_CombatRoll.distanceId, EntityAttributes_CombatRoll.DISTANCE);
        Registry.register(Registry.ATTRIBUTE, EntityAttributes_CombatRoll.rechargeId, EntityAttributes_CombatRoll.RECHARGE);
        Registry.register(Registry.ATTRIBUTE, EntityAttributes_CombatRoll.countId, EntityAttributes_CombatRoll.COUNT);
    }

    public static void registerEnchantments() {
        Registry.register(Registry.ENCHANTMENT, Enchantments_CombatRoll.distanceId, Enchantments_CombatRoll.DISTANCE);
        Registry.register(Registry.ENCHANTMENT, Enchantments_CombatRoll.rechargeChestId, Enchantments_CombatRoll.RECHARGE_CHEST);
        Registry.register(Registry.ENCHANTMENT, Enchantments_CombatRoll.rechargeLegsId, Enchantments_CombatRoll.RECHARGE_LEGS);
        Registry.register(Registry.ENCHANTMENT, Enchantments_CombatRoll.countId, Enchantments_CombatRoll.COUNT);
    }
}
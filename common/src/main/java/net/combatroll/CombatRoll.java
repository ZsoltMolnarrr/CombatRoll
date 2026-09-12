package net.combatroll;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.combatroll.api.Enchantments_CombatRoll;
import net.combatroll.api.EntityAttributes_CombatRoll;
import net.combatroll.config.EnchantmentsConfig;
import net.combatroll.config.ServerConfig;
import net.combatroll.config.ServerConfigWrapper;
import net.combatroll.network.ServerNetwork;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.tinyconfig.ConfigManager;

public class CombatRoll {
    public static final String MOD_ID = "combatroll";
    public static String modName() {
        return I18n.translate("combatroll.mod_name");
    }

    public static ServerConfig config;
    public static ConfigManager<EnchantmentsConfig> enchantmentConfig = new ConfigManager<EnchantmentsConfig>
            ("enchantments", new EnchantmentsConfig())
            .builder()
            .setDirectory(CombatRoll.MOD_ID)
            .sanitize(true)
            .build();


    public static void init() {
        AutoConfig.register(ServerConfigWrapper.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
        config = AutoConfig.getConfigHolder(ServerConfigWrapper.class).getConfig().server;
        enchantmentConfig.refresh();
        ServerNetwork.initializeHandlers();
    }

    public static void registerAttributes() {
        Registry.register(Registries.ATTRIBUTE, EntityAttributes_CombatRoll.distanceId, EntityAttributes_CombatRoll.DISTANCE);
        Registry.register(Registries.ATTRIBUTE, EntityAttributes_CombatRoll.rechargeId, EntityAttributes_CombatRoll.RECHARGE);
        Registry.register(Registries.ATTRIBUTE, EntityAttributes_CombatRoll.countId, EntityAttributes_CombatRoll.COUNT);
    }

    public static void configureEnchantments() {
        var config = enchantmentConfig.value;
        Enchantments_CombatRoll.DISTANCE.properties = config.longfooted;
        Enchantments_CombatRoll.RECHARGE.properties = config.acrobat;
        Enchantments_CombatRoll.COUNT.properties = config.multi_roll;
    }

    public static void registerEnchantments() {
        Registry.register(Registries.ENCHANTMENT, Enchantments_CombatRoll.distanceId, Enchantments_CombatRoll.DISTANCE);
        Registry.register(Registries.ENCHANTMENT, Enchantments_CombatRoll.rechargeId, Enchantments_CombatRoll.RECHARGE);
        Registry.register(Registries.ENCHANTMENT, Enchantments_CombatRoll.countId, Enchantments_CombatRoll.COUNT);
    }
}
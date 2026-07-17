package net.combat_roll.stat;

import net.combat_roll.CombatRollMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

import java.lang.reflect.Field;

public class CombatRollStats {
    public static final Stat<?> ROLL = makeCustomStat("roll");
    public static final Stat<?> ROLL_CM = makeDistanceStat("roll_cm");

    private static Stat<?> makeCustomStat(String key) {
        forceUnfreeze(Registries.CUSTOM_STAT);

        Identifier identifier = Identifier.of(CombatRollMod.ID, key);
        Identifier newStat = Registry.register(Registries.CUSTOM_STAT, key, identifier);

        return Stats.CUSTOM.getOrCreateStat(newStat, StatFormatter.DEFAULT);
    }
    private static Stat<?> makeDistanceStat(String key) {
        forceUnfreeze(Registries.CUSTOM_STAT);

        Identifier identifier = Identifier.of(CombatRollMod.ID, key);
        Identifier newStat = Registry.register(Registries.CUSTOM_STAT, key, identifier);

        return Stats.CUSTOM.getOrCreateStat(newStat, StatFormatter.DISTANCE);
    }

    private static void forceUnfreeze(Registry<?> registry) {
        if (!(registry instanceof SimpleRegistry<?> simpleRegistry)) {
            return;
        }
        try {
            Field frozenField = SimpleRegistry.class.getDeclaredField("frozen");
            frozenField.setAccessible(true);
            frozenField.setBoolean(simpleRegistry, false);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to unfreeze " + registry, e);
        }
    }

    public static void registerStats(){
    }
}

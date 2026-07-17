package net.combat_roll.stat;

import net.combat_roll.CombatRollMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import java.util.ArrayList;

public class CombatRollStats {
    public static final ArrayList<Entry> all = new ArrayList<>();

    private static Entry entry(String name, StatFormatter formatter) {
        var entry = new Entry(name, formatter);
        all.add(entry);
        return entry;
    }

    public static final Entry ROLL = entry("roll", StatFormatter.DEFAULT);
    public static final Entry ROLL_CM = entry("roll_cm", StatFormatter.DISTANCE);

    public static class Entry {
        public final Identifier id;
        public final StatFormatter formatter;
        public Stat<Identifier> stat;

        public Entry(String name, StatFormatter formatter) {
            this.id = Identifier.fromNamespaceAndPath(CombatRollMod.ID, name);
            this.formatter = formatter;
        }

        // Called from StatsMixin at the tail of Stats.<clinit>, while the
        // CUSTOM_STAT registry is still unfrozen (same lifecycle as vanilla stats).
        public void register() {
            Registry.register(BuiltInRegistries.CUSTOM_STAT, id, id);
            stat = Stats.CUSTOM.get(id, formatter);
        }
    }
}

package net.combatroll.stat;

import net.combatroll.CombatRoll;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

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
            this.id = new Identifier(CombatRoll.MOD_ID, name);
            this.formatter = formatter;
        }

        public void register() {
            Registry.register(Registries.CUSTOM_STAT, id, id);
            stat = Stats.CUSTOM.getOrCreateStat(id, formatter);
        }
    }

}

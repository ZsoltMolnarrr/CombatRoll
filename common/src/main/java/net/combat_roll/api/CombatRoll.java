package net.combat_roll.api;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class CombatRoll {
    public static final String NAMESPACE = "combat_roll";

    public static class Attributes {
        public static final ArrayList<Entry> all = new ArrayList<>();
        private static Entry entry(String name, double baseValue, double minValue, double maxValue, boolean tracked) {
            var entry = new Entry(name, baseValue, minValue, maxValue, tracked);
            all.add(entry);
            return entry;
        }

        public static class Entry {
            public final Identifier id;
            public final String translationKey;
            public final Attribute attribute;
            public final double baseValue;
            @Nullable
            public Holder<Attribute> entry;

            public Entry(String name, double baseValue, double minValue, double maxValue, boolean tracked) {
                this.id = Identifier.fromNamespaceAndPath(NAMESPACE, name);
                this.translationKey = "attribute.name." + NAMESPACE + "." + name;
                this.attribute = new RangedAttribute(translationKey, baseValue, minValue, maxValue).setSyncable(tracked);
                this.baseValue = baseValue;
            }

            public void register() {
                entry = Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, id, attribute);
            }
        }

        public static final Entry DISTANCE = entry("distance", 3.0, 1, 24.0, true);
        public static final Entry RECHARGE = entry("recharge", 20, 0.1, 200, true);
        public static final Entry COUNT = entry("count", 1, 0, 20.0, true);
    }
}

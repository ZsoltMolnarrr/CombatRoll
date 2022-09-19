package net.combatroll.api;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;

import java.util.List;

public class EntityAttributes_CombatRoll {
    public static final String distanceId = "combat_roll.distance";
    public static final EntityAttribute DISTANCE = (new ClampedEntityAttribute("attribute.name." + distanceId, 3.0, 1, 24.0)).setTracked(true);
    public static final String rechargeId = "combat_roll.recharge";
    public static final EntityAttribute RECHARGE = (new ClampedEntityAttribute("attribute.name." + rechargeId, 20, 0.1, 200)).setTracked(true);
    public static final String countId = "combat_roll.count";
    public static final EntityAttribute COUNT = (new ClampedEntityAttribute("attribute.name." + countId, 1, 1, 20.0)).setTracked(true);

    public static List<EntityAttribute> all;
    static {
        all = List.of(DISTANCE, RECHARGE, COUNT);
    }
}

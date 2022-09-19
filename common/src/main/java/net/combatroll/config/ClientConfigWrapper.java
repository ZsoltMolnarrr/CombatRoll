package net.combatroll.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.combatroll.CombatRoll;

@Config(name = CombatRoll.MOD_ID)
public class ClientConfigWrapper extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("client")
    @ConfigEntry.Gui.TransitiveObject
    public ClientConfig client = new ClientConfig();
}

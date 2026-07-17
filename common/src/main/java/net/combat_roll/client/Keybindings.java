package net.combat_roll.client;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.List;
import net.minecraft.client.KeyMapping;

public class Keybindings {
    public static KeyMapping roll;
    public static List<KeyMapping> all;

    static {
        roll = new KeyMapping(
                "keybinds.combat_roll.roll",
                InputConstants.Type.KEYSYM,
                InputConstants.KEY_R,
                KeyMapping.Category.MOVEMENT);

        all = List.of(roll);
    }
}

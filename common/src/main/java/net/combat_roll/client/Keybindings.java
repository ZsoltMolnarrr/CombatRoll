package net.combat_roll.client;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

import java.util.List;

public class Keybindings {
    public static KeyBinding roll;
    public static List<KeyBinding> all;

    static {
        roll = new KeyBinding(
                "keybinds.combat_roll.roll",
                InputUtil.Type.KEYSYM,
                InputUtil.GLFW_KEY_R,
                KeyBinding.Category.MOVEMENT);

        all = List.of(roll);
    }
}

package net.rolling.client;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.rolling.Rolling;

import java.util.List;

public class RollingKeybings {
    public static KeyBinding roll;
    public static List<KeyBinding> all;

    static {
        roll = new KeyBinding(
                "keybinds.rolling.roll",
                InputUtil.Type.KEYSYM,
                InputUtil.GLFW_KEY_R,
                Rolling.modName());

        all = List.of(roll);
    }
}

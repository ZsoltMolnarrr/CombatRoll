package net.rolling.client.hud;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.rolling.client.RollingClient;
import net.rolling.config.HudConfig;

public class HudConfigScreen extends Screen {
    private ButtonWidget originButton;
    private ButtonWidget resetButton;

    public HudConfigScreen(Screen parent) {
        super(Text.of("Hello HudConfigScreen"));
        var screenWidth = client.getWindow().getScaledWidth();
        var screenHeight = client.getWindow().getScaledHeight();

        originButton = new ButtonWidget(screenWidth / 2, (screenHeight / 2) - 20, 100, 20, Text.literal("Hello world!"), button -> {
            nextOrigin();
        });
        addSelectableChild(originButton);
        resetButton = new ButtonWidget(screenWidth / 2, (screenHeight / 2) + 20, 100, 20, Text.literal("Reset"), button -> {
            reset();
        });
        addSelectableChild(resetButton);

        addDrawable(parent);
    }

    public static void nextOrigin() {
        var config = RollingClient.hudConfig.currentConfig;
        HudElement.Origin origin;
        try {
            origin = HudElement.Origin.values()[(config.rollWidget.origin.ordinal() + 1)];
            config.rollWidget = new HudElement(origin, origin.initialOffset());
        } catch (Exception e) {
            config.rollWidget = HudConfig.createDefaultRollWidget();
        }
    }

    public static void reset() {
        var config = RollingClient.hudConfig.currentConfig;
        config.rollWidget = HudConfig.createDefaultRollWidget();
    }
}

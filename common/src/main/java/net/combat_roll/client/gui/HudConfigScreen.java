package net.combat_roll.client.gui;

import net.combat_roll.client.CombatRollClient;
import net.combat_roll.config.HudConfig;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec2f;

public class HudConfigScreen extends Screen {
    private Screen previous;

    public HudConfigScreen(Screen previous) {
        super(Text.translatable("gui.combat_roll.hud"));
        this.previous = previous;
    }

    @Override
    protected void init() {
        var buttonWidth = 120;
        var buttonHeight = 20;
        var buttonCenterX = (width / 2) - (buttonWidth / 2);
        var buttonCenterY = (height / 2) - (buttonHeight / 2);

        addDrawableChild(
            ButtonWidget.builder(Text.translatable("gui.combat_roll.close"), button -> { close(); })
                .position(buttonCenterX, buttonCenterY - 30)
                .size(buttonWidth, buttonHeight)
                .build()
        );
        addDrawableChild(
            ButtonWidget.builder(Text.translatable("gui.combat_roll.corner"), button -> { nextOrigin(); })
                .position(buttonCenterX, buttonCenterY)
                .size(buttonWidth, buttonHeight)
                .build()
        );
        addDrawableChild(
            ButtonWidget.builder(Text.translatable("gui.combat_roll.reset"), button -> { reset(); })
                .position(buttonCenterX, buttonCenterY + 30)
                .size(buttonWidth, buttonHeight)
                .build()
        );
    }

    public void close() {
        this.save();
        this.client.setScreen(previous);
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        HudRenderHelper.render(context, delta);
    }

    @Override
    public boolean mouseDragged(Click click, double offsetX, double offsetY) {
        if (!this.isDragging() && click.button() == 0) {
            var config = CombatRollClient.hudConfig.value;
            config.rollWidget.offset = new Vec2f(
                    (float) (config.rollWidget.offset.x + offsetX),
                    (float) (config.rollWidget.offset.y + offsetY));
        }
        return super.mouseDragged(click, offsetX, offsetY);
    }

    public static void nextOrigin() {
        var config = CombatRollClient.hudConfig.value;
        HudElement.Origin origin;
        try {
            origin = HudElement.Origin.values()[(config.rollWidget.origin.ordinal() + 1)];
            config.rollWidget = new HudElement(origin, origin.initialOffset());
        } catch (Exception e) {
            origin = HudElement.Origin.values()[0];
            config.rollWidget = new HudElement(origin, origin.initialOffset());
        }
    }

    public void save() {
        CombatRollClient.hudConfig.save();
    }

    public void reset() {
        var config = CombatRollClient.hudConfig.value;
        config.rollWidget = HudConfig.createDefaultRollWidget();
    }
}

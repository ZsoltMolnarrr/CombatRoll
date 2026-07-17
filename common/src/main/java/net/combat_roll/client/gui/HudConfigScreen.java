package net.combat_roll.client.gui;

import net.combat_roll.client.CombatRollClient;
import net.combat_roll.config.HudConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;

public class HudConfigScreen extends Screen {
    private Screen previous;

    public HudConfigScreen(Screen previous) {
        super(Component.translatable("gui.combat_roll.hud"));
        this.previous = previous;
    }

    @Override
    protected void init() {
        var buttonWidth = 120;
        var buttonHeight = 20;
        var buttonCenterX = (width / 2) - (buttonWidth / 2);
        var buttonCenterY = (height / 2) - (buttonHeight / 2);

        addRenderableWidget(
            Button.builder(Component.translatable("gui.combat_roll.close"), button -> { onClose(); })
                .pos(buttonCenterX, buttonCenterY - 30)
                .size(buttonWidth, buttonHeight)
                .build()
        );
        addRenderableWidget(
            Button.builder(Component.translatable("gui.combat_roll.corner"), button -> { nextOrigin(); })
                .pos(buttonCenterX, buttonCenterY)
                .size(buttonWidth, buttonHeight)
                .build()
        );
        addRenderableWidget(
            Button.builder(Component.translatable("gui.combat_roll.reset"), button -> { reset(); })
                .pos(buttonCenterX, buttonCenterY + 30)
                .size(buttonWidth, buttonHeight)
                .build()
        );
    }

    public void onClose() {
        this.save();
        this.minecraft.setScreen(previous);
    }

    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        HudRenderHelper.render(context, delta);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent click, double offsetX, double offsetY) {
        if (!this.isDragging() && click.button() == 0) {
            var config = CombatRollClient.hudConfig.value;
            config.rollWidget.offset = new Vec2(
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

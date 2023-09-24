package net.combatroll.client.gui;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.combatroll.config.ClientConfigWrapper;

public class ConfigMenuScreen extends Screen {
    private Screen previous;

    public ConfigMenuScreen(Screen parent) {
        super(Text.translatable("gui.combatroll.config_menu"));
        this.previous = parent;
    }

    @Override
    protected void init() {
        var buttonWidth = 120;
        var buttonHeight = 20;
        var buttonCenterX = (width / 2) - (buttonWidth / 2);
        var buttonCenterY = (height / 2) - (buttonHeight / 2);

        addDrawableChild(
                ButtonWidget.builder(Text.translatable("gui.combatroll.close"), button -> { close(); })
                        .position(buttonCenterX, buttonCenterY - 30)
                        .size(buttonWidth, buttonHeight)
                        .build()
        );
        addDrawableChild(
                ButtonWidget.builder(Text.translatable("gui.combatroll.settings"), button -> {
                            client.setScreen(AutoConfig.getConfigScreen(ClientConfigWrapper.class, this).get());
                        })
                        .position(buttonCenterX, buttonCenterY)
                        .size(buttonWidth, buttonHeight)
                        .build()
        );
        addDrawableChild(
                ButtonWidget.builder(Text.translatable("gui.combatroll.hud"), button -> {
                            client.setScreen(new HudConfigScreen(this));
                        })
                        .position(buttonCenterX, buttonCenterY + 30)
                        .size(buttonWidth, buttonHeight)
                        .build()
        );
    }

    public void close() {
        this.client.setScreen(previous);
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
    }
}

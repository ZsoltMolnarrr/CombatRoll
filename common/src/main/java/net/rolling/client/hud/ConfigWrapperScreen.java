package net.rolling.client.hud;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

public class ConfigWrapperScreen extends Screen {
    public ConfigWrapperScreen(Screen child) {
        super(child.getTitle());
        this.addDrawable(child);
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        HudRenderHelper.render(matrices, delta);
    }
}

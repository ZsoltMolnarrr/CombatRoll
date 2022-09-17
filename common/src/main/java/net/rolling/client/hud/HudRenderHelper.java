package net.rolling.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.rolling.client.MinecraftClientExtension;
import net.rolling.client.RollManager;

import java.util.ArrayList;
import java.util.List;

public class HudRenderHelper {
    private static final Identifier ARROW = new Identifier("rolling", "textures/hud/arrow.png");
    private static final Identifier ARROW_BACKGROUND = new Identifier("rolling", "textures/hud/arrow_background.png");

    public static void render(MatrixStack matrixStack, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) {
            return;
        }
        var cooldownInfo = ((MinecraftClientExtension)client).getRollManager().getCooldown();
        var viewModel = createViewModel(cooldownInfo);

        int horizontalSpacing = 8;
        int originX = 10;
        int originY = 10;
        for(var element: viewModel.elements()) {
            int x = 0;
            int y = 0;
            int u = 0;
            int v = 0;
            int width = 0;
            int height = 0;
            int textureSize = 0;

            x = originX;
            y = originY;
            u = 0;
            v = 0;
            width = height = textureSize = 15;
            RenderSystem.setShaderTexture(0, ARROW_BACKGROUND);
            RenderSystem.setShaderColor(1, 1, 1, 0.75F);
            DrawableHelper.drawTexture(matrixStack, x, y, u, v, width, height, textureSize, textureSize);

            var color = element.color;
            float red = ((float) ((color >> 16) & 0xFF)) / 255F;
            float green = ((float) ((color >> 8) & 0xFF)) / 255F;
            float blue = ((float) (color & 0xFF)) / 255F;

            var prevTextureSize = textureSize;
            textureSize = 13;
            var shift = (prevTextureSize - textureSize) / 2;
            width = textureSize;
            height = (int) ((element.full) * textureSize);
            x = originX + shift;
            y = originY + textureSize - height + shift;
            u = 0;
            v = textureSize - height;
            RenderSystem.setShaderTexture(0, ARROW);
            RenderSystem.setShaderColor(red, green, blue, element.full);
            DrawableHelper.drawTexture(matrixStack, x, y, u, v, width, height, textureSize, textureSize);

            originX += horizontalSpacing;
        }
    }

    private record ViewModel(List<Element> elements) {
        record Element(int color, float full) { }
    }

    private static ViewModel createViewModel(RollManager.CooldownInfo info) {
        var elements = new ArrayList<ViewModel.Element>();
        for(int i = 0; i < info.maxRolls(); ++i) {
            var color = 0x5488e3;
            float full = 0;
            if (i == (info.availableRolls())) {
                full = ((float)info.elapsed()) / ((float)info.total());
                full = Math.min(full, 1F);
            }
            if (i < (info.availableRolls())) {
                full = 1;
            }
            elements.add(new ViewModel.Element(color, full));
        }
        return new ViewModel(elements);
    }
}

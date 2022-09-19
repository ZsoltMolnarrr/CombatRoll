package net.rolling.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.rolling.client.MinecraftClientExtension;
import net.rolling.client.RollManager;
import net.rolling.client.RollingClient;

import java.util.ArrayList;
import java.util.List;

public class HudRenderHelper {
    private static final Identifier ARROW = new Identifier("rolling", "textures/hud/arrow.png");
    private static final Identifier ARROW_BACKGROUND = new Identifier("rolling", "textures/hud/arrow_background.png");

    public static void render(MatrixStack matrixStack, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        ViewModel viewModel;
        if (player == null) {
            viewModel = ViewModel.mock();
        } else {
            var cooldownInfo = ((MinecraftClientExtension)client).getRollManager().getCooldown();
            viewModel = ViewModel.create(cooldownInfo, 0);
        }

        var config = RollingClient.config;

        var screenWidth = client.getWindow().getScaledWidth();
        var screenHeight = client.getWindow().getScaledHeight();
        var rollWidget = RollingClient.hudConfig.currentConfig.rollWidget;
        var originPoint = rollWidget.origin.getPoint(screenWidth, screenHeight);
        var drawOffset = rollWidget.offset;

        int horizontalSpacing = 8;
        int biggestTextureSize = 15;
        int widgetWidth = biggestTextureSize + (horizontalSpacing * viewModel.elements.size());
        int widgetHeight = biggestTextureSize;
        int drawX = (int) (originPoint.x + drawOffset.x); // Growing to right by removing `- (widgetWidth) / 2`
        int drawY = (int) (originPoint.y + drawOffset.y - (widgetHeight) / 2);
        for(var element: viewModel.elements()) {
            int x = 0;
            int y = 0;
            int u = 0;
            int v = 0;
            int width = 0;
            int height = 0;
            int textureSize = 0;

            x = drawX;
            y = drawY;
            u = 0;
            v = 0;
            width = height = textureSize = 15;
            RenderSystem.setShaderTexture(0, ARROW_BACKGROUND);
            RenderSystem.setShaderColor(1, 1, 1, ((float)config.hudBackgroundOpacity) / 100F);
            DrawableHelper.drawTexture(matrixStack, x, y, u, v, width, height, textureSize, textureSize);

            var color = element.color;
            float red = ((float) ((color >> 16) & 0xFF)) / 255F;
            float green = ((float) ((color >> 8) & 0xFF)) / 255F;
            float blue = ((float) (color & 0xFF)) / 255F;

            var prevTextureSize = textureSize;
            textureSize = 13;
            var shift = (prevTextureSize - textureSize) / 2;
            width = textureSize;
            height = Math.round((element.full) * textureSize);
            x = drawX + shift;
            y = drawY + textureSize - height + shift;
            u = 0;
            v = textureSize - height;
            RenderSystem.setShaderTexture(0, ARROW);
            RenderSystem.setShaderColor(red, green, blue, element.full);
            DrawableHelper.drawTexture(matrixStack, x, y, u, v, width, height, textureSize, textureSize);

            drawX += horizontalSpacing;
        }
    }

    private record ViewModel(List<Element> elements) {
        record Element(int color, float full) {
        }

        static ViewModel create(RollManager.CooldownInfo info, float tickDelta) {
            var config = RollingClient.config;
            var elements = new ArrayList<ViewModel.Element>();
            for (int i = 0; i < info.maxRolls(); ++i) {
                var color = config.hudArrowColor;
                float full = 0;
                if ((i == info.availableRolls()) || info.elapsed() == 0) {
                    full = ((float) info.elapsed()) / ((float) info.total());
                    full = Math.min(full, 1F);

                    if (config.playCooldownFlash) {
                        var missingTicks = info.total() - info.elapsed();
                        var sparkleTicks = 2;
                        if (missingTicks <= sparkleTicks) {
                            float sparkle = (missingTicks + tickDelta) / (sparkleTicks);
                            float red = ((float) ((color >> 16) & 0xFF)) / 255F;
                            float green = ((float) ((color >> 8) & 0xFF)) / 255F;
                            float blue = ((float) (color & 0xFF)) / 255F;
                            int redBits = (int) (Math.min(red + sparkle * 0.5F, 1F) * 255F);
                            int greenBits = (int) (Math.min(green + sparkle * 0.5F, 1F) * 255F);
                            int blueBits = (int) (Math.min(blue + sparkle * 0.5F, 1F) * 255F);
                            color = redBits * 0xFFFF + greenBits * 0xFF + blueBits;
                        }
                    }
                }
                if (i < (info.availableRolls())) {
                    full = 1;
                }
                elements.add(new ViewModel.Element(color, full));
            }
            return new ViewModel(elements);
        }

        static ViewModel mock() {
            var config = RollingClient.config;
            var color = config.hudArrowColor;
            return new ViewModel(
                    List.of(
                            new ViewModel.Element(color, 1),
                            new ViewModel.Element(color, 0.5F),
                            new ViewModel.Element(color, 0)
                )
            );
        }
    }
}

package net.combatroll.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import net.combatroll.client.Keybindings;
import net.combatroll.mixin.client.KeybindingAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;
import net.combatroll.client.MinecraftClientExtension;
import net.combatroll.client.RollManager;
import net.combatroll.client.CombatRollClient;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HudRenderHelper {
    private static final Identifier ARROW = new Identifier("combatroll", "textures/hud/arrow.png");
    private static final Identifier ARROW_BACKGROUND = new Identifier("combatroll", "textures/hud/arrow_background.png");

    public static void render(DrawContext context, float tickDelta) {
        var config = CombatRollClient.config;
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        ViewModel viewModel;
        if (player == null) {
            viewModel = ViewModel.mock();
        } else {
            if (player.isCreative() && !config.showHUDInCreative) {
                return;
            }
            if (player.isSpectator()) {
                return;
            }
            var cooldownInfo = ((MinecraftClientExtension)client).getRollManager().getCooldown();
            if (!config.showWhenFull && cooldownInfo.availableRolls() == cooldownInfo.maxRolls()) {
                return;
            }
            viewModel = ViewModel.create(cooldownInfo, tickDelta);
        }

        var screenWidth = client.getWindow().getScaledWidth();
        var screenHeight = client.getWindow().getScaledHeight();
        var rollWidget = CombatRollClient.hudConfig.value.rollWidget;
        var originPoint = rollWidget.origin.getPoint(screenWidth, screenHeight);
        var drawOffset = rollWidget.offset;

        int horizontalSpacing = 8;
        int biggestTextureSize = 15;
        int widgetWidth = biggestTextureSize + (horizontalSpacing * viewModel.elements.size());
        int widgetHeight = biggestTextureSize;
        int drawX = (int) (originPoint.x + drawOffset.x); // Growing to right by removing `- (widgetWidth) / 2`
        int drawY = (int) (originPoint.y + drawOffset.y - (widgetHeight) / 2);
        int drawnWith = 0;
        RenderSystem.enableBlend();
        for(var element: viewModel.elements()) {
            int x = 0;
            int y = 0;
            int u = 0;
            int v = 0;
            int width = 0;
            int height = 0;
            int textureSize = 0;

            x = drawX + drawnWith;
            y = drawY;
            u = 0;
            v = 0;
            width = height = textureSize = 15;
            context.setShaderColor(1, 1, 1, ((float)config.hudBackgroundOpacity) / 100F);
            context.drawTexture(ARROW_BACKGROUND, x, y, u, v, width, height, textureSize, textureSize);

            var color = element.color;
            float red = ((float) ((color >> 16) & 0xFF)) / 255F;
            float green = ((float) ((color >> 8) & 0xFF)) / 255F;
            float blue = ((float) (color & 0xFF)) / 255F;

            var prevTextureSize = textureSize;
            textureSize = 13;
            var shift = (prevTextureSize - textureSize) / 2;
            width = textureSize;
            height = Math.round((element.full) * textureSize);
            x = drawX + drawnWith + shift;
            y = drawY + textureSize - height + shift;
            u = 0;
            v = textureSize - height;
            context.setShaderColor(red, green, blue, element.full);
            context.drawTexture(ARROW, x, y, u, v, width, height, textureSize, textureSize);

            drawnWith += horizontalSpacing;
        }

        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        if (config.showKeybinding) {
            int keybindingX = drawX + drawnWith / 2;
            int keybindingY = drawY;
            var buttonY = keybindingY;
            var textRenderer = client.inGameHud.getTextRenderer();
            if (viewModel.drawable != null) {
                viewModel.drawable.draw(context, keybindingX, buttonY, Drawable.Anchor.CENTER, Drawable.Anchor.TRAILING);
            } else if (viewModel.label != null) {
                var label = viewModel.label;
                var textLength = textRenderer.getWidth(label);
                HudKeyVisuals.buttonLeading.draw(context, keybindingX - (textLength / 2), buttonY, Drawable.Anchor.TRAILING, Drawable.Anchor.TRAILING);
                HudKeyVisuals.buttonCenter.drawFlexibleWidth(context, keybindingX - (textLength / 2), buttonY, textLength, Drawable.Anchor.TRAILING);
                HudKeyVisuals.buttonTrailing.draw(context, keybindingX + (textLength / 2), buttonY, Drawable.Anchor.LEADING, Drawable.Anchor.TRAILING);
                context.drawCenteredTextWithShadow(textRenderer, label, keybindingX, keybindingY - 8, 0xFFFFFF);
            }
        }

        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private record ViewModel(List<Element> elements, String label, @Nullable Drawable.Component drawable) {
        record Element(int color, float full) { }

        static ViewModel create(RollManager.CooldownInfo info, float tickDelta) {
            var config = CombatRollClient.config;
            var elements = new ArrayList<ViewModel.Element>();
            for (int i = 0; i < info.maxRolls(); ++i) {
                var color = config.hudArrowColor;
                float full = 0;
                if ((i == info.availableRolls())) {
                    full = ((float) info.elapsed()) / ((float) info.total());
                    full = Math.min(full, 1F);

                    if (config.playCooldownFlash) {
                        var missingTicks = info.total() - info.elapsed();
                        var sparkleTicks = 2;
                        if (missingTicks <= sparkleTicks) {
                            float sparkle = ((sparkleTicks / 2) - ((missingTicks - 1 + (1F - tickDelta)) / (sparkleTicks))); // This is really messy, someone improve pls xD
                            float red = ((float) ((color >> 16) & 0xFF)) / 255F;
                            float green = ((float) ((color >> 8) & 0xFF)) / 255F;
                            float blue = ((float) (color & 0xFF)) / 255F;
//                            System.out.println("Sparkle: " + sparkle + " | info.elapsed():" + info.elapsed() + " | missingTicks:" + missingTicks + " | delta:" + tickDelta);
                            int redBits = (int) (mixNumberFloat(red, 1, sparkle) * 255F);
                            int greenBits = (int) (mixNumberFloat(green, 1, sparkle) * 255F);
                            int blueBits = (int) (mixNumberFloat(blue, 1, sparkle) * 255F);
//                            System.out.println("Blend -" + " R:" + redBits + " G:" + greenBits + " B:" + blueBits);
                            color = redBits;
                            color = (color << 8) + greenBits;
                            color = (color << 8) + blueBits;
                        }
                    }
                }
                if (i < (info.availableRolls())) {
                    full = 1;
                }
                elements.add(new ViewModel.Element(color, full));
            }


            var keybinding = Keybindings.roll;
            var key = ((KeybindingAccessor) keybinding).getBoundKey().toString();
            var drawable = HudKeyVisuals.custom.get(key);
            var label = keybinding.getBoundKeyLocalizedText()
                    .getString()
                    .toUpperCase(Locale.US);
            label = acronym(label, 3);

            return new ViewModel(elements, label, drawable);
        }

        static ViewModel mock() {
            var config = CombatRollClient.config;
            var color = config.hudArrowColor;
            return new ViewModel(
                    List.of(
                            new ViewModel.Element(color, 1),
                            new ViewModel.Element(color, 0.5F),
                            new ViewModel.Element(color, 0)
                    ),
                    "R",
                    null
            );
        }
        
        private static float mixNumberFloat(float a, float b, float bias) {
            return a + (b - a) * bias;
        }

        private static String acronym(String phrase, int maxLength) {
            StringBuilder result = new StringBuilder();
            for (String token : phrase.split("\\s+")) {
                result.append(token.toUpperCase().charAt(0));
            }
            var resultString = result.toString();
            // Make the result at most 3 characters long
            if (resultString.length() > maxLength) {
                resultString = resultString.substring(0, maxLength);
            }
            return result.toString();
        }
    }
}

package net.combat_roll.client.gui;

import net.minecraft.world.phys.Vec2;

public class HudElement {
    public Origin origin;
    public Vec2 offset;

    public HudElement(Origin origin, Vec2 offset) {
        this.origin = origin;
        this.offset = offset;
    }

    public enum Origin {
        TOP, TOP_LEFT, TOP_RIGHT,
        BOTTOM, BOTTOM_LEFT, BOTTOM_RIGHT;

        public Vec2 getPoint(int screenWidth, int screenHeight) {
            switch (this) {
                case TOP -> {
                    return new Vec2(screenWidth / 2F, 0);
                }
                case TOP_LEFT -> {
                    return new Vec2(0, 0);
                }
                case TOP_RIGHT -> {
                    return new Vec2(screenWidth, 0);
                }
                case BOTTOM -> {
                    return new Vec2(screenWidth / 2F, screenHeight);
                }
                case BOTTOM_LEFT -> {
                    return new Vec2(0, screenHeight);
                }
                case BOTTOM_RIGHT -> {
                    return new Vec2(screenWidth, screenHeight);
                }
            }
            return new Vec2(screenWidth / 2F, screenHeight / 2F); // Should never run
        }

        public Vec2 initialOffset() {
            int offset = 12;
            switch (this) {
                case TOP -> {
                    return new Vec2(0, offset);
                }
                case TOP_LEFT -> {
                    return new Vec2(offset, offset);
                }
                case TOP_RIGHT -> {
                    return new Vec2((-1) * offset, offset);
                }
                case BOTTOM -> {
                    return new Vec2(0, (-1) * offset);
                }
                case BOTTOM_LEFT -> {
                    return new Vec2(offset, (-1) * offset);
                }
                case BOTTOM_RIGHT -> {
                    return new Vec2((-1) * offset, (-1) * offset);
                }
            }
            return new Vec2(0, 0); // Should never run
        }
    }
}

package org.polyfrost.overflowanimations.util;

public final class MathUtils {
    private MathUtils() {
    }

    public static float lerp(float previous, float current, float partialTick) {
        return previous + (current - previous) * partialTick;
    }
}

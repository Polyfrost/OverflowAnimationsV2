package org.polyfrost.overflowanimations.hooks;

import org.polyfrost.overflowanimations.config.OldAnimationsSettings;

public final class SmoothSneakHook {
    private SmoothSneakHook() {
    }

    private static float sneakingHeight;

    public static void setSneakingHeight(float sneakingHeight) {
        SmoothSneakHook.sneakingHeight = sneakingHeight;
    }

    public static float getSmoothSneak(float originalEyeHeight) {
        if (OldAnimationsSettings.INSTANCE.enabled && OldAnimationsSettings.smoothSneaking) {
            return sneakingHeight;
        } else {
            return originalEyeHeight;
        }
    }
}

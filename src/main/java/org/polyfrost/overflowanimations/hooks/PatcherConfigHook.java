package org.polyfrost.overflowanimations.hooks;

import club.sk1er.patcher.config.PatcherConfig;
import org.polyfrost.overflowanimations.OverflowAnimations;

public final class PatcherConfigHook {
    private PatcherConfigHook() {
    }

    public static boolean isParallaxFixEnabled() {
        return OverflowAnimations.isPatcherPresent && PatcherConfig.parallaxFix;
    }
}

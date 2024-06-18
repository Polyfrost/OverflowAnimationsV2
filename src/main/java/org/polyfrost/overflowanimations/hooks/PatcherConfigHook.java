package org.polyfrost.overflowanimations.hooks;

import club.sk1er.patcher.config.PatcherConfig;
import org.polyfrost.overflowanimations.OverflowAnimations;

public class PatcherConfigHook {
    public static boolean isParallaxFixEnabled() {
        return OverflowAnimations.isPatcherPresent && PatcherConfig.parallaxFix;
    }
}

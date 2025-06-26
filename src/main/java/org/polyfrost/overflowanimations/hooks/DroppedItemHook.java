package org.polyfrost.overflowanimations.hooks;

import org.polyfrost.overflowanimations.OverflowAnimations;

public final class DroppedItemHook {
    private DroppedItemHook() {
    }

    public static boolean isItemDropped;

    public static boolean isItemPhysicsAndEntityNotDropped() {
        return !OverflowAnimations.isItemPhysics || !isItemDropped;
    }
}
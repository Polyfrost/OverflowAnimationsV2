package org.polyfrost.overflowanimations.hooks;

import org.polyfrost.overflowanimations.OverflowAnimations;

public class DroppedItemHook {

    public static boolean isItemDropped;

    public static boolean isItemPhysicsAndEntityDropped() {
        return OverflowAnimations.isItemPhysics && isItemDropped;
    }

}
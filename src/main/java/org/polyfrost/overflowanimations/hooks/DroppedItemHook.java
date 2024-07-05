package org.polyfrost.overflowanimations.hooks;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import org.polyfrost.overflowanimations.OverflowAnimations;

public class DroppedItemHook {

    public static boolean isItemDropped;

    public static boolean isItemPhysicsAndEntityDropped() {
        return TransformTypeHook.transform == ItemCameraTransforms.TransformType.GROUND && OverflowAnimations.isItemPhysics && isItemDropped;
    }

}

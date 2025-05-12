package org.polyfrost.overflowanimations.hooks;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;

import java.util.EnumSet;

public final class TransformTypeHook {
    public static ItemCameraTransforms.TransformType transform;
    private static final EnumSet<ItemCameraTransforms.TransformType> ITEM_CAMERA_TYPES = EnumSet.of(ItemCameraTransforms.TransformType.GROUND, ItemCameraTransforms.TransformType.FIXED);

    private TransformTypeHook() {
    }

    public static boolean shouldBeSprite() {
        return shouldNotHaveGlint() || isRenderingInGUI();
    }

    public static boolean isRenderingInGUI() {
        return transform == ItemCameraTransforms.TransformType.GUI;
    }

    public static boolean shouldNotHaveGlint() {
        return ITEM_CAMERA_TYPES.contains(TransformTypeHook.transform);
    }
}

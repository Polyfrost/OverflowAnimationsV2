package org.polyfrost.overflowanimations.hooks

import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType
import java.util.*

object TransformTypeHook {

    var transform: TransformType? = null

    private val cameraTypes: EnumSet<TransformType?> = EnumSet.of(
        TransformType.GROUND,
        TransformType.FIXED
    )

    fun shouldBeSprite(): Boolean {
        return shouldNotHaveGlint() || isRenderingInGUI
    }

    private val isRenderingInGUI: Boolean
        get() = transform == TransformType.GUI

    fun shouldNotHaveGlint(): Boolean {
        return cameraTypes.contains(transform)
    }
}

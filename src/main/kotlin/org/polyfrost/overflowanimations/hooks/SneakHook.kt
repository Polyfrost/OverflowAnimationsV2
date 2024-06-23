package org.polyfrost.overflowanimations.hooks

import cc.polyfrost.oneconfig.utils.dsl.mc
import org.polyfrost.overflowanimations.config.MainModSettings.oldSettings

object SneakHook {

    var sneakingHeight: Float = 0f

    fun getSmoothSneak(): Float {
        return if (oldSettings.smoothSneaking && oldSettings.enabled) sneakingHeight else mc.renderViewEntity.eyeHeight
    }

}
package org.polyfrost.overflowanimations.util

object MathUtils {

    fun interpolate(previous: Float, current: Float, partialTicks: Float): Float {
        return previous + (current - previous) * partialTicks
    }

}
/**
 * OverflowAnimations
 * The all-you-could-want legacy animations mod for modern minecraft versions.
 * Brings back animations from the 1.7/1.8 era and more.
 * <p>
 * Copyright (C) 2024-2027 lowercasebtw
 * Copyright (C) 2024-2027 mixces
 * Copyright (C) 2024-2027 Contributors to the project retain their copyright
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * "MINECRAFT" LINKING EXCEPTION TO THE GPL
 */

package org.polyfrost.overflowanimations.handler.compatibility

//? if <26.3 {
/*import com.mojang.blaze3d.pipeline.RenderPipeline
*///?} elif 26.3 {
/*import com.mojang.renderpearl.api.pipeline.RenderPipeline
*///?} else {
import com.mojang.blaze3d.pipeline.RenderPipeline
//?}
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import java.lang.reflect.Method
import java.util.*

object IrisUtil {
    private val pipelineCache = Object2ObjectOpenHashMap<RenderPipeline, IrisPipeline>()
    private var IRIS_INSTANCE: Any? = null
    private var IRIS_ASSIGN_PIPELINE_METHOD: Method? = null

    init {
        try {
            // API
            val irisApiClass = Class.forName("net.irisshaders.iris.api.v0.IrisApi")
            IRIS_INSTANCE = irisApiClass.getMethod("getInstance").invoke(null)

            // Enums
            val irisProgramEnum = Class.forName("net.irisshaders.iris.api.v0.IrisProgram").asSubclass(Enum::class.java)
            Arrays.stream(IrisPipeline.VALUES).forEach { it.initialize(irisProgramEnum) }

            // Methods
            IRIS_ASSIGN_PIPELINE_METHOD = IRIS_INSTANCE!!::class.java.getMethod("assignPipeline", RenderPipeline::class.java, irisProgramEnum)
        } catch (_: Exception) {
        }
    }

    fun assignPipeline(pipeline: RenderPipeline, program: IrisPipeline) {
        try {
            if (pipelineCache.containsKey(pipeline) && pipelineCache[pipeline] == program) {
                return
            }

            pipelineCache[pipeline] = program
            IRIS_ASSIGN_PIPELINE_METHOD?.invoke(IRIS_INSTANCE, pipeline, program.internal())
        } catch (_: Exception) {
        }
    }
}
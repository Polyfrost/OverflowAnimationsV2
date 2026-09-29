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

package org.polyfrost.overflowanimations.handler.rendering.clouds

// Built on the GPU device API (1.21.5) and dynamic uniforms / GUI render states (1.21.6)
//? if >=1.21.6 {
//? if 26.2 {
/*import com.mojang.blaze3d.GpuFormat
*///?}
//? if <26.3 {
/*import com.mojang.blaze3d.pipeline.BlendFunction
*///?}
//? if >=26.1 <26.3 {
/*import com.mojang.blaze3d.pipeline.ColorTargetState
*///?}
//? if <26.3 {
/*import com.mojang.blaze3d.pipeline.RenderPipeline
*///?} elif 26.4 {
import com.mojang.blaze3d.pipeline.RenderPipeline
//?}
//? if >=26.3 {
import com.mojang.renderpearl.api.GpuFormat
import com.mojang.renderpearl.api.pipeline.BlendFunction
import com.mojang.renderpearl.api.pipeline.ColorTargetState
//?}
//? if 26.3 {
/*import com.mojang.renderpearl.api.pipeline.RenderPipeline
*///?}
import net.minecraft.client.CloudStatus
import net.minecraft.client.renderer.RenderPipelines
import org.polyfrost.overflowanimations.OverflowAnimations.location
//? if >=26.1 {
import java.util.*
//?}

data class CloudPipelineSet(
    val defaultPipeline: RenderPipeline,
    val depthOnlyPipeline: RenderPipeline,
    val flatPipeline: RenderPipeline
) {
    companion object {
        fun create(name: String, snippet: RenderPipeline.Snippet): CloudPipelineSet {
            val defaultPipeline = RenderPipelines.register(
                RenderPipeline.builder(snippet)
                    .withLocation(location("pipeline/${name}"))
                    .build()
            )

            val depthOnlyPipeline = RenderPipelines.register(
                RenderPipeline.builder(snippet)
                    .withLocation(location("pipeline/${name}_depth_only"))
                    //? if <26.1 {
                    /*.withBlend(BlendFunction.TRANSLUCENT)
                    .withColorWrite(false)
                    *///?} else {
                    .withColorTargetState(
                        ColorTargetState(
                            Optional.of(BlendFunction.TRANSLUCENT),
                    //?}
                            //? if >=26.2 {
                            GpuFormat.RGBA8_UNORM,
                            //?}
                    //? if >=26.1 {
                            ColorTargetState.WRITE_NONE
                        )
                    )
                    //?}
                    .build()
            )

            val flatPipeline = RenderPipelines.register(
                RenderPipeline.builder(snippet)
                    .withLocation(location("pipeline/${name}_flat"))
                    .withCull(false)
                    .build()
            )

            return CloudPipelineSet(defaultPipeline, depthOnlyPipeline, flatPipeline)
        }
    }

    fun get(status: CloudStatus) = if (status == CloudStatus.FANCY)
        defaultPipeline
    else
        flatPipeline
}
//?}

/**
 * Animatium
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

package org.visuals.legacy.animatium.handler.rendering.lighting.lightmap

//? if <26.3 {
/*import com.mojang.blaze3d.textures.GpuTextureView
*///?} else {
import com.mojang.renderpearl.api.textures.GpuTextureView
//?}
import org.visuals.legacy.animatium.handler.rendering.pipeline.AnimatiumPipelines
import org.visuals.legacy.animatium.renderer.buffer.BasicGeometry
import org.visuals.legacy.animatium.renderer.impl.DeferredRenderer
import org.visuals.legacy.animatium.renderer.uniform.DynamicUniformStorage
import org.visuals.legacy.animatium.renderer.uniform.UniformKey
import org.visuals.legacy.animatium.util.profile

class LegacyLightmapRenderer : AutoCloseable {
    companion object {
        private val SkyDarken = UniformKey.Float("SkyDarken")
        private val SkyDarkness = UniformKey.Float("SkyDarkness")
        private val BlockLightRed = UniformKey.Float("BlockLightRed")
        private val NightVisionScale = UniformKey.Float("NightVisionScale")
        private val Gamma = UniformKey.Float("Gamma")
        private val UseBrightLightmap = UniformKey.Boolean("UseBrightLightmap")

        private val BASE_GEOMETRY = BasicGeometry(0, 3)
    }

    private val lightmapInfoUniform = DynamicUniformStorage.builder("Legacy Lightmap UBO")
        .with(SkyDarken)
        .with(SkyDarkness)
        .with(BlockLightRed)
        .with(NightVisionScale)
        .with(Gamma)
        .with(UseBrightLightmap)
        .build()

    fun render(state: LegacyLightmapState, textureView: GpuTextureView) {
        if (state.needsUpdate) {
            profile("lightmap") {
                DeferredRenderer.of("Legacy Lightmap Update", textureView, null).use { renderer ->
                    renderer.setPipeline(AnimatiumPipelines.LEGACY_LIGHTMAP)
                    renderer.setUniform(
                        "LightmapInfo",
                        this.lightmapInfoUniform
                            .set(SkyDarken, state.skyDarken)
                            .set(SkyDarkness, state.skyDarkness)
                            .set(BlockLightRed, state.blockLightRed)
                            .set(NightVisionScale, state.nightVisionScale)
                            .set(Gamma, state.gamma)
                            .set(UseBrightLightmap, state.useBrightLightmap)
                            .upload()
                    )
                    renderer.draw(BASE_GEOMETRY)
                }
            }
        }
    }

    override fun close() = this.lightmapInfoUniform.close()
}
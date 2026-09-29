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

package org.polyfrost.overflowanimations.handler.rendering.lighting.lightmap

// Built on the GPU device API (1.21.5) and dynamic uniforms / GUI render states (1.21.6)
//? if >=1.21.6 {
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.util.profiling.Profiler
import net.minecraft.world.effect.MobEffects
import org.polyfrost.overflowanimations.util.getLegacySkyDarken

class LegacyLightmapExtractor {
    private var needsUpdate: Boolean = false
    private var blockLightRed: Float = 0.0F

    fun tick(blockLightFlicker: Float) {
        this.blockLightRed += blockLightFlicker - this.blockLightRed
        this.needsUpdate = true
    }

    fun extract(minecraft: Minecraft, state: LegacyLightmapState, tickDelta: Float) {
        state.needsUpdate = this.needsUpdate
        if (this.needsUpdate) {
            val level = minecraft.level ?: return
            val player = minecraft.player ?: return

            val profiler = Profiler.get()
            profiler.push("lightmap")
            state.skyDarken = level.getLegacySkyDarken()
            state.blockLightRed = this.blockLightRed
            //? if <26.1 {
            /*state.skyDarkness = minecraft.gameRenderer.getDarkenWorldAmount(tickDelta)
            *///?} elif 26.1 {
            /*state.skyDarkness = minecraft.gameRenderer.getBossOverlayWorldDarkening(tickDelta)
            *///?} else {
            state.skyDarkness = minecraft.gameRenderer.bossOverlayWorldDarkening(tickDelta)
            //?}
            if (player.hasEffect(MobEffects.NIGHT_VISION)) {
                //? if <26.2 {
                /*state.nightVisionScale = GameRenderer.getNightVisionScale(player, tickDelta)
                *///?} else {
                state.nightVisionScale = GameRenderer.nightVisionScale(player, tickDelta)
                //?}
            }

            state.gamma = minecraft.options.gamma().get().toFloat()
            state.useBrightLightmap = level.dimension() == ClientLevel.END
            profiler.pop()

            this.needsUpdate = false
        }
    }
}
//?}

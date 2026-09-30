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

package org.polyfrost.overflowanimations.handler.rendering.pipeline

//? if <1.21.6 {
/*import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import net.minecraft.client.renderer.RenderStateShard
import net.minecraft.client.renderer.RenderType
import net.minecraft.util.TriState
*///?} elif <1.21.11 {
/*import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.renderer.RenderStateShard
import net.minecraft.client.renderer.RenderType
*///?} else {
import net.minecraft.client.renderer.rendertype.LayeringTransform
import net.minecraft.client.renderer.rendertype.RenderSetup
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.client.renderer.rendertype.TextureTransform
//?}
//? if <1.21.11 {
/*import net.minecraft.Util
*///?} else {
import net.minecraft.util.Util
//?}
//? if <26.1 {
/*import net.minecraft.client.renderer.entity.ItemRenderer
*///?} else {
import net.minecraft.client.renderer.feature.ItemFeatureRenderer
//?}
import net.minecraft.resources.Identifier
import org.joml.Matrix4f
import org.polyfrost.overflowanimations.OverflowAnimations
import org.polyfrost.overflowanimations.OverflowAnimations.location
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig
import org.polyfrost.overflowanimations.util.enums.ArmorGlintSetting
import kotlin.math.cos
import kotlin.math.sin

object ArmorGlint {
    //? if <26.1 {
    /*private val V1_15 = listOf(renderType("v1_15", ItemRenderer.ENCHANTED_GLINT_ITEM) { v1_15Matrix() })
    *///?} else {
    private val V1_15 = listOf(renderType("v1_15", ItemFeatureRenderer.ENCHANTED_GLINT_ITEM) { v1_15Matrix() })
    //?}

    private val V1_8 = listOf(0, 1).map { layer ->
        renderType("v1_8_$layer", location("textures/misc/enchanted_glint_legacy_armor.png")) { v1_8Matrix(layer) }
    }

    @JvmStatic
    fun layers(): List<RenderType> = if (!OverflowAnimations.isEnabled()) emptyList() else when (OverflowAnimationsConfig.instance().items.armorGlint) {
        ArmorGlintSetting.V1_8 -> V1_8
        ArmorGlintSetting.V1_15 -> V1_15
        else -> emptyList()
    }

    @JvmStatic
    fun tintsWithArmor(): Boolean = OverflowAnimationsConfig.instance().items.armorGlint == ArmorGlintSetting.V1_8 && OverflowAnimationsConfig.instance().other.glintAffectsArmorTint

    private fun v1_15Matrix(): Matrix4f {
        val time = Util.getMillis() * 8L
        return Matrix4f().translation(-(time % 110000L) / 110000.0F, (time % 30000L) / 30000.0F, 0.0F).rotateZ(0.17453292F).scale(0.16F)
    }

    private fun v1_8Matrix(layer: Int): Matrix4f {
        val angle = Math.toRadians(30.0 - layer * 60.0)
        val scroll = Util.getMillis() / 50.0 * (0.001 + layer * 0.003) * 20.0 / 3.0
        return Matrix4f().translation((-sin(angle) * scroll % 1.0).toFloat(), (cos(angle) * scroll % 1.0).toFloat(), 0.0F).scale(1.0F / 3.0F).rotateZ(angle.toFloat())
    }

    //? if <1.21.6 {
    /*private fun renderType(name: String, texture: Identifier, matrix: () -> Matrix4f): RenderType {
        var strength = 1.0F
        return RenderType.create(
            "overflowanimations_armor_glint_$name",
            DefaultVertexFormat.POSITION_TEX,
            VertexFormat.Mode.QUADS,
            1536,
            RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_ARMOR_ENTITY_GLINT_SHADER)
                .setTextureState(RenderStateShard.TextureStateShard(texture, TriState.DEFAULT, false))
                .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                .setCullState(RenderStateShard.NO_CULL)
                .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                .setTexturingState(RenderStateShard.TexturingStateShard("overflowanimations_armor_glint_$name", {
                    strength = RenderSystem.getShaderGlintAlpha()
                    RenderSystem.setShaderGlintAlpha(1.0F)
                    RenderSystem.setTextureMatrix(matrix())
                }, {
                    RenderSystem.resetTextureMatrix()
                    RenderSystem.setShaderGlintAlpha(strength)
                }))
                .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
                .createCompositeState(false)
        )
    }
    *///?} elif <1.21.11 {
    /*private fun renderType(name: String, texture: Identifier, matrix: () -> Matrix4f): RenderType = RenderType.create(
        "overflowanimations_armor_glint_$name",
        1536,
        OverflowAnimationsPipelines.FULL_STRENGTH_ARMOR_GLINT,
        RenderType.CompositeState.builder()
            .setTextureState(RenderStateShard.TextureStateShard(texture, false))
            .setTexturingState(RenderStateShard.TexturingStateShard("overflowanimations_armor_glint_$name", { RenderSystem.setTextureMatrix(matrix()) }, RenderSystem::resetTextureMatrix))
            .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
            .setOverlayState(RenderStateShard.OVERLAY)
            .createCompositeState(false)
    )
    *///?} else {
    private fun renderType(name: String, texture: Identifier, matrix: () -> Matrix4f): RenderType = RenderType.create(
        "overflowanimations_armor_glint_$name",
        RenderSetup.builder(OverflowAnimationsPipelines.FULL_STRENGTH_ARMOR_GLINT)
            .withTexture("Sampler0", texture)
            .setTextureTransform(TextureTransform("overflowanimations_armor_glint_$name") { matrix() })
            .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
            .useOverlay()
            //? if >=26.3
            .withForcedSolidModelPhase()
            .createRenderSetup()
    )
    //?}
}

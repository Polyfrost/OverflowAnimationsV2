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

// Built on render pipelines (1.21.5+)
//? if >=1.21.6 {
//? if <1.21.11 {
/*import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.client.renderer.RenderStateShard
import net.minecraft.client.renderer.RenderType
import java.util.OptionalDouble
*///?}
//? if <26.1 {
/*import net.minecraft.client.renderer.entity.ItemRenderer
*///?} else {
import net.minecraft.client.renderer.feature.ItemFeatureRenderer
//?}
//? if >=1.21.11 {
import net.minecraft.client.renderer.rendertype.LayeringTransform
import net.minecraft.client.renderer.rendertype.RenderSetup
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.client.renderer.rendertype.TextureTransform
//?}

object OverflowAnimationsRenderTypes {
    // Glint
    @JvmField
    //? if <1.21.11 {
    /*val ARMOR_GLINT: RenderType = RenderType.create(
        "overflowanimations_armor_glint",
        1536,
        OverflowAnimationsPipelines.ARMOR_GLINT,
        RenderType.CompositeState.builder()
            .setTextureState(RenderStateShard.TextureStateShard(ItemRenderer.ENCHANTED_GLINT_ARMOR, false))
            .setTexturingState(RenderStateShard.ARMOR_ENTITY_GLINT_TEXTURING)
            .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
            .setOverlayState(RenderStateShard.OVERLAY)
            .createCompositeState(false)
    )

    // Line width was part of the render type before 1.21.11
    @JvmField
    val FISHING_LINE_THIN = fishingLine(1.0)

    @JvmField
    val FISHING_LINE_LEGACY = fishingLine(2.0)

    private fun fishingLine(width: Double): RenderType = RenderType.create(
        "overflowanimations_fishing_line",
        1536,
        //? if <1.21.9 {
        /*RenderPipelines.LINE_STRIP,
        *///?} else {
        RenderPipelines.LINES,
        //?}
        RenderType.CompositeState.builder()
            .setLineState(RenderStateShard.LineStateShard(OptionalDouble.of(width)))
            .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
            .setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
            .createCompositeState(false)
    )
    *///?} else {
    val ARMOR_GLINT = RenderType.create(
        "overflowanimations_armor_glint",
        RenderSetup.builder(OverflowAnimationsPipelines.ARMOR_GLINT)
            //? if <26.1 {
            /*.withTexture("Sampler0", ItemRenderer.ENCHANTED_GLINT_ARMOR)
            *///?} else {
            .withTexture("Sampler0", ItemFeatureRenderer.ENCHANTED_GLINT_ARMOR)
            //?}
            .setTextureTransform(TextureTransform.ARMOR_ENTITY_GLINT_TEXTURING)
            .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
            .useOverlay()
            .createRenderSetup()
    )
    //?}
}
//?}

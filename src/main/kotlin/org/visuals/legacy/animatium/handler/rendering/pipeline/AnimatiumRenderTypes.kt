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

package org.visuals.legacy.animatium.handler.rendering.pipeline

//? if 1.21.11 {
/*import net.minecraft.client.renderer.entity.ItemRenderer
*///?} else {
import net.minecraft.client.renderer.feature.ItemFeatureRenderer
//?}
import net.minecraft.client.renderer.rendertype.LayeringTransform
import net.minecraft.client.renderer.rendertype.RenderSetup
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.client.renderer.rendertype.TextureTransform

object AnimatiumRenderTypes {
    // Glint
    @JvmField
    val ARMOR_GLINT = RenderType.create(
        "animatium_armor_glint",
        RenderSetup.builder(AnimatiumPipelines.ARMOR_GLINT)
            //? if 1.21.11 {
            /*.withTexture("Sampler0", ItemRenderer.ENCHANTED_GLINT_ARMOR)
            *///?} else {
            .withTexture("Sampler0", ItemFeatureRenderer.ENCHANTED_GLINT_ARMOR)
            //?}
            .setTextureTransform(TextureTransform.ARMOR_ENTITY_GLINT_TEXTURING)
            .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
            .useOverlay()
            .createRenderSetup()
    )
}
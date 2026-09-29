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

package org.polyfrost.overflowanimations.handler.rendering

import com.mojang.blaze3d.platform.NativeImage
import net.minecraft.client.Minecraft
//? if >=1.21.11
import net.minecraft.client.renderer.texture.MipmapStrategy
import net.minecraft.client.renderer.texture.ReloadableTexture
import net.minecraft.client.renderer.texture.TextureContents
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection
import net.minecraft.resources.Identifier
import net.minecraft.server.packs.resources.ResourceManager

class LegacyLogoTexture(id: Identifier) : ReloadableTexture(id) {
    override fun loadContents(resourceManager: ResourceManager): TextureContents {
        Minecraft.getInstance().resourceManager.open(this.resourceId()).use { inputStream ->
            // TODO: Get real metadata file
            return TextureContents(
                NativeImage.read(inputStream),
                TextureMetadataSection(
                    TextureMetadataSection.DEFAULT_BLUR,
                    //? if <1.21.11 {
                    /*TextureMetadataSection.DEFAULT_CLAMP
                    *///?} else {
                    TextureMetadataSection.DEFAULT_CLAMP,
                    MipmapStrategy.AUTO,
                    TextureMetadataSection.DEFAULT_ALPHA_CUTOFF_BIAS
                    //?}
                )
            )
        }
    }
}
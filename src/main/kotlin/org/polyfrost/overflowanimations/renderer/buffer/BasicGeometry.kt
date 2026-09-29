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

package org.polyfrost.overflowanimations.renderer.buffer

// Built on the GPU device API (1.21.5) and dynamic uniforms / GUI render states (1.21.6)
//? if >=1.21.6 {
//? if <26.3 {
/*import com.mojang.blaze3d.systems.RenderPass
*///?}
import com.mojang.blaze3d.systems.RenderSystem
//? if >=26.3 {
import com.mojang.renderpearl.api.commands.RenderPass
//?}

class BasicGeometry(val firstVertex: Int, val vertexCount: Int) : Geometry {
    override fun bind(
        pass: RenderPass,
        autoStorageIndexBuffer: RenderSystem.AutoStorageIndexBuffer
    ) {
    }

    //? if <26.2 {
    /*override fun draw(pass: RenderPass) = pass.draw(firstVertex, vertexCount)
    *///?} else {
    override fun draw(pass: RenderPass) = pass.draw(vertexCount, 1, firstVertex, 0)
    //?}

    override fun persistent() = true

    override fun isClosed() = false

    override fun close() {
    }
}
//?}

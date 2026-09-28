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

package org.visuals.legacy.animatium

import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey
//? if 1.21.11 {
/*import net.minecraft.client.renderer.block.model.BlockStateModel
*///?} else {
import net.minecraft.client.renderer.block.dispatch.BlockStateModel
//?}
import org.visuals.legacy.animatium.handler.networking.payloads.InfoPayload
import org.visuals.legacy.animatium.util.version.Version
import java.lang.Boolean.parseBoolean
import java.util.*

object AnimatiumConstants {
    const val MOD_ID = "@MODID@"
    const val DEVELOPMENT_VERSION = "@COMMIT@"

    @JvmField
    val VERSION = Version.parse("@VERSION@") ?: Version.BOGUS

    @JvmField
    val IS_DEVELOPMENT = parseBoolean("@DEVELOPMENT@")

    @JvmField
    val FAST_GRASS_MODEL_LOCATION = Animatium.location("block/fast_grass_block")

    @JvmField
    val FAST_GRASS_MODEL_KEY: ExtraModelKey<BlockStateModel> =
        ExtraModelKey.create(FAST_GRASS_MODEL_LOCATION::toString)

    @JvmField
    val INFO_PAYLOAD =
        InfoPayload(VERSION, if (IS_DEVELOPMENT) Optional.of(DEVELOPMENT_VERSION) else Optional.empty())
}
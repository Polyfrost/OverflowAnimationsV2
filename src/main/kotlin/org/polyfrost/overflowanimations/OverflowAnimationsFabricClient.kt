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

package org.polyfrost.overflowanimations

import dev.kikugie.fletching_table.annotation.fabric.Entrypoint
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
//? if >=1.21.5 {
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin
import net.fabricmc.fabric.api.client.model.loading.v1.SimpleUnbakedExtraModel
//?}
//? if <1.21.11 {
/*import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.ResourcePackActivationType
*///?} else {
import net.fabricmc.fabric.api.resource.v1.ResourceLoader
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType
//?}
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.Minecraft
import org.polyfrost.overflowanimations.handler.OverflowAnimationsKeybinds
import org.polyfrost.overflowanimations.handler.command.OverflowAnimationsCommand
import org.polyfrost.overflowanimations.handler.networking.OverflowAnimationsNetworking
import org.polyfrost.overflowanimations.handler.rendering.updateOverlayTint

@Entrypoint
class OverflowAnimationsFabricClient : ClientModInitializer {
    override fun onInitializeClient() {
        OverflowAnimations.initialize()

        val modContainer = FabricLoader.getInstance().getModContainer(OverflowAnimationsConstants.MOD_ID)
            .orElseThrow({ RuntimeException("Mod container data could not be found for OverflowAnimations!") })
        for (pack in listOf("classic_textures", "classic_panorama", "classic_water")) {
            //? if <1.21.11 {
            /*ResourceManagerHelper.registerBuiltinResourcePack(OverflowAnimations.location(pack), modContainer, ResourcePackActivationType.NORMAL)
            *///?} else {
            ResourceLoader.registerBuiltinPack(OverflowAnimations.location(pack), modContainer, PackActivationType.NORMAL)
            //?}
        }

        //? if >=1.21.5 {
        ModelLoadingPlugin.register { context ->
            context.addModel(
                OverflowAnimationsConstants.FAST_GRASS_MODEL_KEY,
                SimpleUnbakedExtraModel.blockStateModel(OverflowAnimationsConstants.FAST_GRASS_MODEL_LOCATION)
            )
        }
        //?}

        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ -> dispatcher.register(OverflowAnimationsCommand.create()) }

        OverflowAnimationsKeybinds.bootstrap()
        OverflowAnimationsNetworking.bootstrap()

        Minecraft.getInstance().execute {
            updateOverlayTint() // Force update overlay for damageTintStyle
        }
    }
}
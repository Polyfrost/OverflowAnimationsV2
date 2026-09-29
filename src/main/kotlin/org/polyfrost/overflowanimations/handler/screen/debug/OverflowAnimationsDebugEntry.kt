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

package org.polyfrost.overflowanimations.handler.screen.debug

// The debug screen entry API was added in 1.21.9
//? if >=1.21.9 {
import net.minecraft.client.gui.components.debug.DebugEntryCategory
import net.minecraft.client.gui.components.debug.DebugScreenDisplayer
import net.minecraft.client.gui.components.debug.DebugScreenEntry
import net.minecraft.network.chat.Component
import net.minecraft.world.level.Level
import net.minecraft.world.level.chunk.LevelChunk
import org.polyfrost.overflowanimations.OverflowAnimations
import org.polyfrost.overflowanimations.OverflowAnimationsConstants
import org.polyfrost.overflowanimations.handler.server_features.ServerFeatureManager
import org.polyfrost.overflowanimations.handler.server_features.ServerFeatures

class OverflowAnimationsDebugEntry : DebugScreenEntry {
    companion object {
        val CATEGORY = DebugEntryCategory(Component.translatable("overflowanimations.category.debug"), Float.MAX_VALUE)
        val GROUP = OverflowAnimations.location("debug")
    }

    override fun display(
        displayer: DebugScreenDisplayer,
        serverOrClientLevel: Level?,
        clientChunk: LevelChunk?,
        serverChunk: LevelChunk?
    ) {
        val list = arrayListOf<String>()
        list.add("OverflowAnimations " + OverflowAnimationsConstants.VERSION + (if (OverflowAnimationsConstants.IS_DEVELOPMENT) " - Development Version (" + OverflowAnimationsConstants.DEVELOPMENT_VERSION + ")" else ""))
        if (!ServerFeatureManager.ENABLED_SERVER_FEATURES.isEmpty()) {
            list.add("Enabled Server Features:")
            for (feature in ServerFeatures.allFeatures()) {
                if (feature != ServerFeatures.ALL) {
                    list.add(" - " + feature.identifier.path)
                }
            }
        }

        displayer.addToGroup(GROUP, list)
    }

    override fun isAllowed(reducedDebugInfo: Boolean) = true

    override fun category() = CATEGORY
}
//?}

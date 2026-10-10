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

//? if >1.8.9 {
import com.mojang.logging.LogUtils
import net.minecraft.SharedConstants
//?}
import net.minecraft.client.Minecraft
//? if >=1.21.9
import net.minecraft.client.gui.components.debug.DebugScreenEntries
//? if >1.8.9
import net.minecraft.resources.Identifier
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig
//? if >=1.21.6
import org.polyfrost.overflowanimations.handler.rendering.lighting.LegacyDiffuseLighting
//? if >=1.21.9
import org.polyfrost.overflowanimations.handler.screen.debug.OverflowAnimationsDebugEntry
import org.polyfrost.overflowanimations.util.config.GeneralConfigUtil
//? if >1.8.9
import org.polyfrost.overflowanimations.util.reinitializeInventorySlots

object OverflowAnimations {
    //? if >1.8.9
    private val LOGGER = LogUtils.getLogger()

    @JvmStatic
    var enabled = true
        set(value) {
            field = value
            GeneralConfigUtil.put(GeneralConfigUtil.ENABLED_KEY, value)
        }

    @JvmStatic
    fun isEnabled(): Boolean = enabled

    @JvmStatic
    fun reloadIfInWorld() {
        //? if <=1.8.9 {
        /*if (Minecraft.getInstance().world != null) reload()
        *///?} else {
        if (Minecraft.getInstance().level != null) reload()
        //?}
    }

    @JvmStatic
    fun reload() {
        val minecraft = Minecraft.getInstance()
        //? if <=1.8.9 {
        /*minecraft.worldRenderer.reload()
        *///?} elif <26.2 {
        /*minecraft.levelRenderer.allChanged()
        *///?} else {
        minecraft.levelExtractor.allChanged()
        //?}
        //? if >=1.21.6
        LegacyDiffuseLighting.refresh()
        //? if >1.8.9
        reinitializeInventorySlots()
    }

    //? if >1.8.9 {
    @JvmStatic
    fun location(path: String) = Identifier.fromNamespaceAndPath(OverflowAnimationsConstants.MOD_ID, path)

    // Network channels of Animatium, which this mod is based on, so servers built for it keep working
    fun animatiumLocation(path: String) = Identifier.fromNamespaceAndPath("animatium", path)

    @JvmStatic
    fun initialize() {
        if (OverflowAnimationsConstants.IS_DEVELOPMENT) {
            SharedConstants.IS_RUNNING_IN_IDE = true
        }

        OverflowAnimationsConfig.load()
        try {
            GeneralConfigUtil.load()
            enabled = GeneralConfigUtil.getBoolean(GeneralConfigUtil.ENABLED_KEY)
            LOGGER.info("Successfully loaded the overflowanimations utility config!")
        } catch (_: Exception) {
            LOGGER.error("Failed to load overflowanimations utility config, defaulting...")
        }

        //? if >=1.21.9
        DebugScreenEntries.register(OverflowAnimationsDebugEntry.GROUP, OverflowAnimationsDebugEntry())
    }
    //?} else {
    /*@JvmStatic
    fun initialize() {
        OverflowAnimationsConfig.load()
        try {
            GeneralConfigUtil.load()
            enabled = GeneralConfigUtil.getBoolean(GeneralConfigUtil.ENABLED_KEY)
        } catch (_: Exception) {
        }
    }
    *///?}
}
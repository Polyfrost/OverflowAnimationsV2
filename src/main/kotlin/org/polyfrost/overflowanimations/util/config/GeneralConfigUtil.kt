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

package org.polyfrost.overflowanimations.util.config

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
//? if >1.8.9
import com.google.gson.Strictness
import net.fabricmc.loader.api.FabricLoader
import org.apache.logging.log4j.LogManager
import java.io.File
import java.nio.file.Files

object GeneralConfigUtil {
    private val LOGGER = LogManager.getLogger()

    //? if >1.8.9 {
    private val GSON = GsonBuilder().setStrictness(Strictness.LENIENT).create()
    //?} else
    //private val GSON = GsonBuilder().create()
    private val CONFIG_FILE = File(FabricLoader.getInstance().gameDir.toFile(), "overflowanimations_utility.json")
    private var data = JsonObject()

    const val ENABLED_KEY = "enabled"
    const val PRESET_VERSION_KEY = "preset_version"

    init {
        // Defaults
        this.data.addProperty(ENABLED_KEY, true)
        //? if >1.8.9
        this.data.addProperty(PRESET_VERSION_KEY, PresetVersion.VANILLA.name)
    }

    @JvmStatic
    fun load() {
        if (CONFIG_FILE.exists()) {
            this.data = GSON.fromJson(Files.readString(CONFIG_FILE.toPath()), JsonObject::class.java)
        } else {
            this.save()
        }
    }

    @JvmStatic
    fun getBoolean(key: String): Boolean =
        if (this.data.has(key)) {
            this.data.get(key).asBoolean
        } else {
            this.put(key, false)
            false
        }

    @JvmStatic
    fun <T : Enum<T>> getEnum(key: String, fallback: T): T {
        try {
            return java.lang.Enum.valueOf(fallback.declaringJavaClass, this.data.get(key).asString)
        } catch (_: Throwable) {
        }

        this.put(key, fallback)
        return fallback
    }

    @JvmStatic
    fun put(key: String, value: Boolean): GeneralConfigUtil {
        this.data.addProperty(key, value)
        return this.save()
    }

    @JvmStatic
    fun put(key: String, value: String): GeneralConfigUtil {
        this.data.addProperty(key, value)
        return this.save()
    }

    @JvmStatic
    fun <T : Enum<T>> put(key: String, value: T): GeneralConfigUtil {
        return this.put(key, value.name)
    }

    @JvmStatic
    fun save(): GeneralConfigUtil {
        var success = true
        try {
            if (!CONFIG_FILE.exists()) {
                success = CONFIG_FILE.createNewFile()
            }

            if (success) {
                Files.writeString(CONFIG_FILE.toPath(), GSON.toJson(data))
            }
        } catch (_: Exception) {
            success = false
        }

        if (!success) {
            LOGGER.error("Failed to save overflowanimations utility config...")
        }

        return this
    }
}
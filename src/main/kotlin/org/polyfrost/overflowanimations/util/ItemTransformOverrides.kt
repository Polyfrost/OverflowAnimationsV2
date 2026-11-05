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

package org.polyfrost.overflowanimations.util

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.item.ItemModel
import net.minecraft.core.component.DataComponents
import net.minecraft.resources.FileToIdConverter
import net.minecraft.resources.Identifier
import net.minecraft.server.packs.resources.ResourceProvider
import net.minecraft.world.item.ItemStack

object ItemTransformOverrides {
    private val ITEM_DEFINITIONS = FileToIdConverter.json("items")
    private val MODELS = FileToIdConverter.json("models")
    private val cache = HashMap<Pair<Identifier, String>, Pair<ItemModel, Boolean>>()

    fun isOverridden(stack: ItemStack, context: String): Boolean {
        val id = stack.get(DataComponents.ITEM_MODEL) ?: return false
        val minecraft = Minecraft.getInstance()
        val model = minecraft.modelManager.getItemModel(id)
        val key = id to context
        cache[key]?.takeIf { it.first === model }?.let { return it.second }

        val overridden = runCatching {
            differsFromVanilla(
                id,
                context,
                { read(minecraft.resourceManager, it) },
                //? if <26.3 {
                /*{ read(minecraft.vanillaPackResources.asProvider(), it) },
                *///?} else {
                { read(minecraft.vanillaPackResources.asResourceManager(), it) },
                //?}
            )
        }.getOrDefault(false)
        cache[key] = model to overridden
        return overridden
    }

    internal fun differsFromVanilla(
        item: Identifier,
        context: String,
        active: (Identifier) -> JsonObject?,
        vanilla: (Identifier) -> JsonObject?,
    ): Boolean {
        val vanillaTransforms = transforms(item, context, vanilla) ?: return false
        return transforms(item, context, active) != vanillaTransforms
    }

    private fun transforms(item: Identifier, context: String, read: (Identifier) -> JsonObject?) =
        read(ITEM_DEFINITIONS.idToFile(item))?.let { definition -> modelIds(definition).map { display(it, context, read) } }

    private fun modelIds(json: JsonElement): List<Identifier> = when {
        json.isJsonArray -> json.asJsonArray.flatMap(::modelIds)
        json.isJsonObject -> json.asJsonObject.entrySet().flatMap { (key, value) ->
            if ((key == "model" || key == "base") && value.isJsonPrimitive) listOf(Identifier.parse(value.asString)) else modelIds(value)
        }
        else -> emptyList()
    }

    private fun display(model: Identifier, context: String, read: (Identifier) -> JsonObject?): Map<String, JsonElement> {
        val display = HashMap<String, JsonElement>()
        val visited = HashSet<Identifier>()
        var current: Identifier? = model
        while (current != null && visited.add(current)) {
            val json = read(MODELS.idToFile(current)) ?: break
            json.getAsJsonObject("display")?.entrySet()?.forEach { (key, value) ->
                if (key.startsWith(context)) display.putIfAbsent(key, value)
            }
            current = json.get("parent")?.asString?.let(Identifier::parse)
        }
        return display
    }

    private fun read(resources: ResourceProvider, file: Identifier) =
        resources.getResource(file).orElse(null)?.openAsReader()?.use { JsonParser.parseReader(it).asJsonObject }
}

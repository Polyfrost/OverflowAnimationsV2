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

//? if <1.21.5
//import com.google.common.collect.MapMaker
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import net.minecraft.client.Minecraft
//? if <26.1 {
/*import net.minecraft.client.renderer.block.model.ItemTransform
*///?}
import net.minecraft.client.renderer.item.ItemModel
import net.minecraft.client.renderer.item.ItemStackRenderState
//? if >=26.1 {
import net.minecraft.client.resources.model.cuboid.ItemTransform
//?}
import net.minecraft.core.component.DataComponents
import net.minecraft.resources.FileToIdConverter
import net.minecraft.resources.Identifier
import net.minecraft.server.packs.resources.ResourceProvider
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import org.joml.Vector3f

object ItemTransformOverrides {
    private val ITEM_DEFINITIONS = FileToIdConverter.json("items")
    private val MODELS = FileToIdConverter.json("models")
    private val HANDS = listOf("thirdperson", "firstperson")
    private val cache = HashMap<Pair<Identifier, String>, Pair<ItemModel, Boolean>>()
    private val vanillaThirdPerson = HashMap<Triple<Identifier, Identifier, ItemDisplayContext>, Pair<ItemModel, ItemTransform?>>()

    @JvmField
    var legacyThirdPerson: ItemStackRenderState? = null

    //? if <1.21.5 {
    /*@JvmField
    val bakedModelIds: MutableMap<Any, Identifier> = MapMaker().weakKeys().makeMap()
    *///?}

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
                ::readVanilla,
            )
        }.getOrDefault(false)
        cache[key] = model to overridden
        return overridden
    }

    @JvmStatic
    fun vanillaThirdPersonTransform(stack: ItemStack, model: Identifier?, context: ItemDisplayContext): ItemTransform? {
        val item = stack.get(DataComponents.ITEM_MODEL) ?: return null
        if (model == null || !context.serializedName.startsWith("thirdperson")) return null
        val minecraft = Minecraft.getInstance()
        val itemModel = minecraft.modelManager.getItemModel(item)
        val key = Triple(item, model, context)
        vanillaThirdPerson[key]?.takeIf { it.first === itemModel }?.let { return it.second }

        val transform = runCatching {
            vanillaDisplay(item, model, context.serializedName, { read(minecraft.resourceManager, it) }, ::readVanilla)?.let(::parseTransform)
        }.getOrNull()
        vanillaThirdPerson[key] = itemModel to transform
        return transform
    }

    internal fun vanillaDisplay(
        item: Identifier,
        model: Identifier,
        key: String,
        active: (Identifier) -> JsonObject?,
        vanilla: (Identifier) -> JsonObject?,
    ): JsonObject? {
        val baseline = if (vanilla(MODELS.idToFile(model)) != null) model else {
            val activeDefinition = active(ITEM_DEFINITIONS.idToFile(item)) ?: return null
            val vanillaDefinition = vanilla(ITEM_DEFINITIONS.idToFile(item)) ?: return null
            val pairs = ArrayList<Pair<Identifier, Identifier>>()
            if (!pairModels(activeDefinition, vanillaDefinition, pairs)) return null
            pairs.filter { it.first == model }.map { it.second }.distinct().singleOrNull() ?: return null
        }

        val context = key.substringBefore('_')
        val vanillaTransform = display(baseline, context, vanilla)[key]
        if (display(model, context, active)[key] == vanillaTransform) return null
        return vanillaTransform?.asJsonObject ?: JsonObject()
    }

    private fun pairModels(active: JsonElement, vanilla: JsonElement, pairs: MutableList<Pair<Identifier, Identifier>>): Boolean = when {
        active.isJsonArray && vanilla.isJsonArray -> active.asJsonArray.size() == vanilla.asJsonArray.size() &&
            active.asJsonArray.zip(vanilla.asJsonArray).all { (a, v) -> pairModels(a, v, pairs) }
        active.isJsonObject && vanilla.isJsonObject -> active.asJsonObject.keySet() == vanilla.asJsonObject.keySet() &&
            active.asJsonObject.entrySet().all { (key, value) ->
                val other = vanilla.asJsonObject.get(key)
                if ((key == "model" || key == "base") && value.isJsonPrimitive && other.isJsonPrimitive) {
                    pairs += Identifier.parse(value.asString) to Identifier.parse(other.asString)
                    true
                } else {
                    pairModels(value, other, pairs)
                }
            }
        else -> active == vanilla
    }

    internal fun parseTransform(json: JsonObject): ItemTransform {
        fun vec(key: String, default: Float) =
            json.getAsJsonArray(key)?.let { Vector3f(it[0].asFloat, it[1].asFloat, it[2].asFloat) } ?: Vector3f(default)
        return ItemTransform(
            vec("rotation", 0.0F),
            vec("translation", 0.0F).mul(0.0625F).max(Vector3f(-5.0F)).min(Vector3f(5.0F)),
            vec("scale", 1.0F).max(Vector3f(-4.0F)).min(Vector3f(4.0F)),
        )
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
            json.getAsJsonObject("display")?.let { entries ->
                entries.entrySet().forEach { (key, value) ->
                    if (key.startsWith(context)) display.putIfAbsent(key, value)
                }
                for (hand in HANDS) {
                    if (hand.startsWith(context)) entries.get("${hand}_righthand")?.let { display.putIfAbsent("${hand}_lefthand", it) }
                }
            }
            current = json.get("parent")?.asString?.let(Identifier::parse)
        }
        return display
    }

    private fun readVanilla(file: Identifier) =
        //? if <26.3 {
        /*read(Minecraft.getInstance().vanillaPackResources.asProvider(), file)
        *///?} else {
        read(Minecraft.getInstance().vanillaPackResources.asResourceManager(), file)
        //?}

    private fun read(resources: ResourceProvider, file: Identifier) =
        resources.getResource(file).orElse(null)?.openAsReader()?.use { JsonParser.parseReader(it).asJsonObject }
}

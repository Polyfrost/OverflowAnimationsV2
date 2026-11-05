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

package org.polyfrost.overflowanimations.test

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import net.minecraft.resources.Identifier
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.polyfrost.overflowanimations.util.ItemTransformOverrides

class ItemTransformOverridesTest {
    private val sword = Identifier.parse("minecraft:sword")
    private val vanilla = mapOf(
        "items/sword.json" to """{"model":{"type":"minecraft:model","model":"minecraft:item/sword"}}""",
        "models/item/sword.json" to """{"parent":"minecraft:item/handheld","textures":{"layer0":"minecraft:item/sword"}}""",
        "models/item/handheld.json" to """{"display":{"firstperson_righthand":{"scale":[1,1,1]},"thirdperson_righthand":{"scale":[1,1,1]}}}""",
    )

    private fun reader(files: Map<String, String>): (Identifier) -> JsonObject? =
        { id -> files[id.path]?.let { JsonParser.parseString(it).asJsonObject } }

    private fun overridden(changes: Map<String, String>, context: String = "firstperson") =
        ItemTransformOverrides.differsFromVanilla(sword, context, reader(vanilla + changes), reader(vanilla))

    @Test
    fun `texture changes are not overrides`() {
        assertFalse(overridden(mapOf("models/item/sword.json" to """{"parent":"minecraft:item/handheld","textures":{"layer0":"pack:sword"}}""")))
    }

    @Test
    fun `display on the item model is an override`() {
        val model = """{"parent":"minecraft:item/handheld","display":{"firstperson_righthand":{"scale":[2,2,2]}}}"""
        assertTrue(overridden(mapOf("models/item/sword.json" to model)))
        assertFalse(overridden(mapOf("models/item/sword.json" to model), "thirdperson"))
    }

    @Test
    fun `display on a parent is an override`() {
        assertTrue(overridden(mapOf("models/item/handheld.json" to """{"display":{"firstperson_righthand":{"scale":[2,2,2]}}}""")))
    }

    @Test
    fun `items missing from vanilla are not overrides`() {
        assertFalse(ItemTransformOverrides.differsFromVanilla(Identifier.parse("minecraft:modded"), "firstperson", reader(vanilla), reader(vanilla)))
    }

    @Test
    fun `parent cycles terminate`() {
        assertFalse(overridden(mapOf("models/item/handheld.json" to """{"parent":"minecraft:item/sword"}"""), "other"))
    }
}

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

//? if > 1.8.9 {
import com.google.gson.JsonObject
import com.google.gson.JsonParser
//? if <26.1 {
/*import net.minecraft.client.renderer.block.model.ItemTransform
*///?} else {
import net.minecraft.client.resources.model.cuboid.ItemTransform
//?}
import net.minecraft.resources.Identifier
import org.joml.Vector3f
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.polyfrost.overflowanimations.util.ItemTransformOverrides

class ItemTransformOverridesTest {
    private val sword = Identifier.parse("minecraft:sword")
    private val vanilla = mapOf(
        "minecraft:items/sword.json" to """{"model":{"type":"minecraft:model","model":"minecraft:item/sword"}}""",
        "minecraft:models/item/sword.json" to """{"parent":"minecraft:item/handheld","textures":{"layer0":"minecraft:item/sword"}}""",
        "minecraft:models/item/handheld.json" to """{"display":{"firstperson_righthand":{"scale":[1,1,1]},"thirdperson_righthand":{"scale":[1,1,1]}}}""",
    )

    private fun reader(files: Map<String, String>): (Identifier) -> JsonObject? =
        { id -> files[id.toString()]?.let { JsonParser.parseString(it).asJsonObject } }

    private fun overridden(changes: Map<String, String>, context: String = "firstperson") =
        ItemTransformOverrides.differsFromVanilla(sword, context, reader(vanilla + changes), reader(vanilla))

    @Test
    fun `texture changes are not overrides`() {
        assertFalse(overridden(mapOf("minecraft:models/item/sword.json" to """{"parent":"minecraft:item/handheld","textures":{"layer0":"pack:sword"}}""")))
    }

    @Test
    fun `display on the item model is an override`() {
        val model = """{"parent":"minecraft:item/handheld","display":{"firstperson_righthand":{"scale":[2,2,2]}}}"""
        assertTrue(overridden(mapOf("minecraft:models/item/sword.json" to model)))
        assertFalse(overridden(mapOf("minecraft:models/item/sword.json" to model), "thirdperson"))
    }

    @Test
    fun `display on a parent is an override`() {
        assertTrue(overridden(mapOf("minecraft:models/item/handheld.json" to """{"display":{"firstperson_righthand":{"scale":[2,2,2]}}}""")))
    }

    @Test
    fun `items missing from vanilla are not overrides`() {
        assertFalse(ItemTransformOverrides.differsFromVanilla(Identifier.parse("minecraft:modded"), "firstperson", reader(vanilla), reader(vanilla)))
    }

    @Test
    fun `a right-hand transform on a child replaces the parent left-hand one`() {
        val handheld = """{"display":{"thirdperson_righthand":{"scale":[1,1,1]},"thirdperson_lefthand":{"scale":[3,3,3]}}}"""
        val vanillaHandheld = vanilla + mapOf("minecraft:models/item/handheld.json" to handheld)
        val swordModel = """{"parent":"minecraft:item/handheld","display":{"thirdperson_righthand":{"scale":[1,1,1]}}}"""
        assertTrue(ItemTransformOverrides.differsFromVanilla(sword, "thirdperson", reader(vanillaHandheld + mapOf("minecraft:models/item/sword.json" to swordModel)), reader(vanillaHandheld)))
    }

    private val trident = Identifier.parse("minecraft:trident")
    private val tridentInHand = Identifier.parse("minecraft:item/trident_in_hand")
    private val tridents = vanilla + mapOf(
        "minecraft:items/trident.json" to """{"model":{"type":"minecraft:select","property":"minecraft:display_context","cases":[{"when":"gui","model":{"type":"minecraft:model","model":"minecraft:item/trident"}}],"fallback":{"type":"minecraft:special","base":"minecraft:item/trident_in_hand","model":{"type":"minecraft:trident"}}}}""",
        "minecraft:models/item/trident.json" to """{"parent":"minecraft:item/handheld"}""",
        "minecraft:models/item/trident_in_hand.json" to """{"display":{"thirdperson_righthand":{"scale":[0.5,0.5,0.5]}}}""",
    )

    private fun vanillaDisplay(item: Identifier, model: Identifier, changes: Map<String, String>, files: Map<String, String> = tridents, key: String = "thirdperson_righthand") =
        ItemTransformOverrides.vanillaDisplay(item, model, key, reader(files + changes), reader(files))

    @Test
    fun `unchanged layers keep their transform`() {
        assertNull(vanillaDisplay(trident, tridentInHand, emptyMap()))
    }

    @Test
    fun `changed layers use the vanilla transform of their own model`() {
        val changed = mapOf("minecraft:models/item/trident_in_hand.json" to """{"display":{"thirdperson_righthand":{"scale":[2,2,2]}}}""")
        assertEquals(JsonParser.parseString("""{"scale":[0.5,0.5,0.5]}"""), vanillaDisplay(trident, tridentInHand, changed))
        assertNull(vanillaDisplay(trident, Identifier.parse("minecraft:item/trident"), changed))
    }

    @Test
    fun `left hand falls back to the vanilla right-hand transform`() {
        val changed = mapOf("minecraft:models/item/trident_in_hand.json" to """{"display":{"thirdperson_lefthand":{"scale":[2,2,2]}}}""")
        assertEquals(JsonParser.parseString("""{"scale":[0.5,0.5,0.5]}"""), vanillaDisplay(trident, tridentInHand, changed, key = "thirdperson_lefthand"))
    }

    @Test
    fun `pack models pair with the vanilla model in the same branch`() {
        val pack = Identifier.parse("pack:item/trident_3d")
        val changes = mapOf(
            "minecraft:items/trident.json" to tridents.getValue("minecraft:items/trident.json").replace("minecraft:item/trident_in_hand", "pack:item/trident_3d"),
            "pack:models/item/trident_3d.json" to """{"display":{"thirdperson_righthand":{"scale":[2,2,2]}}}""",
        )
        assertEquals(JsonParser.parseString("""{"scale":[0.5,0.5,0.5]}"""), vanillaDisplay(trident, pack, changes))
        assertNull(vanillaDisplay(trident, Identifier.parse("pack:item/other"), changes))
    }

    @Test
    fun `pack models sharing a vanilla path stay in their namespace`() {
        val pack = Identifier.parse("pack:item/trident_in_hand")
        val changes = mapOf(
            "minecraft:items/trident.json" to tridents.getValue("minecraft:items/trident.json").replace("minecraft:item/trident_in_hand", pack.toString()),
            "pack:models/item/trident_in_hand.json" to """{"display":{"thirdperson_righthand":{"scale":[2,2,2]}}}""",
        )
        assertEquals(JsonParser.parseString("""{"scale":[0.5,0.5,0.5]}"""), vanillaDisplay(trident, pack, changes))
        assertNull(vanillaDisplay(trident, tridentInHand, changes))
    }

    @Test
    fun `ambiguous pack definitions do not pair`() {
        val pack = Identifier.parse("pack:item/trident_3d")
        val model = "pack:models/item/trident_3d.json" to """{"display":{"thirdperson_righthand":{"scale":[2,2,2]}}}"""
        val definition = tridents.getValue("minecraft:items/trident.json").replace("minecraft:item/trident_in_hand", pack.toString())
        val everywhere = definition.replace("minecraft:item/trident\"", "$pack\"")
        val reshaped = definition.replace("\"gui\"", "\"head\"")
        assertNull(vanillaDisplay(trident, pack, mapOf("minecraft:items/trident.json" to everywhere, model)))
        assertNull(vanillaDisplay(trident, pack, mapOf("minecraft:items/trident.json" to reshaped, model)))
    }

    @Test
    fun `vanilla models without a transform reset to none`() {
        val changed = mapOf("minecraft:models/item/trident_in_hand.json" to """{"display":{"thirdperson_righthand":{"scale":[2,2,2]}}}""")
        val files = tridents + mapOf("minecraft:models/item/trident_in_hand.json" to "{}")
        assertEquals(JsonObject(), vanillaDisplay(trident, tridentInHand, changed, files))
    }

    @Test
    fun `transforms parse like vanilla`() {
        val json = JsonParser.parseString("""{"translation":[16,160,-0.5],"scale":[5,1,-5]}""").asJsonObject
        assertEquals(ItemTransform(Vector3f(), Vector3f(1.0F, 5.0F, -0.03125F), Vector3f(4.0F, 1.0F, -4.0F)), ItemTransformOverrides.parseTransform(json))
    }

    @Test
    fun `parent cycles terminate`() {
        assertFalse(overridden(mapOf("minecraft:models/item/handheld.json" to """{"parent":"minecraft:item/sword"}"""), "other"))
    }
}
//?}

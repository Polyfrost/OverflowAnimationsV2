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
import io.netty.buffer.Unpooled
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.Identifier
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.polyfrost.overflowanimations.handler.networking.ApolloCombat
import org.polyfrost.overflowanimations.handler.server_features.ServerFeatures
import java.util.HexFormat

class ApolloCombatTest {
    private companion object {
        const val SETTINGS_URL = "0a5a747970652e676f6f676c65617069732e636f6d2f6c756e6172636c69656e742e61706f6c6c6f2e636f6e666967757261626c652e76312e4f76657272696465436f6e666967757261626c6553657474696e67734d657373616765"
        val COMBAT_SETTINGS = bytes("${SETTINGS_URL}1297010a32120c636f6c6f7265645f66697265180122200a1a64697361626c652d626c6f636b2d6d6973732d70656e616c7479120220010a611206636f6d6261741801221a0a1464697361626c652d6d6973732d70656e616c74791202200122200a1a64697361626c652d626c6f636b2d6d6973732d70656e616c74791202200022170a11616c6c6f772d6469672d616e642d75736512022001")
        val SINGLE_OPTION = bytes("${SETTINGS_URL}122e0a2c1206636f6d626174180122200a1a64697361626c652d626c6f636b2d6d6973732d70656e616c747912022001")
        val MODULE_DISABLED = bytes("${SETTINGS_URL}120a0a081206636f6d626174")
        const val STANDALONE_URL = "0a4b747970652e676f6f676c65617069732e636f6d2f6c756e6172636c69656e742e61706f6c6c6f2e636f6e666967757261626c652e76312e436f6e666967757261626c6553657474696e6773"
        val STANDALONE_MISS_OFF = bytes("${STANDALONE_URL}12261206636f6d6261741801221a0a1464697361626c652d6d6973732d70656e616c747912022000")
        val STANDALONE_NO_ENABLE = bytes("${STANDALONE_URL}12211206636f6d62617422170a11616c6c6f772d6469672d616e642d75736512022001")
        val OTHER_MESSAGE = bytes("0a49747970652e676f6f676c65617069732e636f6d2f6c756e6172636c69656e742e61706f6c6c6f2e706c617965722e76312e557064617465506c61796572576f726c644d65737361676512080a06636f6d626174")

        val LUNAR_CHANNEL = Identifier.fromNamespaceAndPath("lunar", "apollo")
        val JSON_CHANNEL = Identifier.fromNamespaceAndPath("apollo", "json")
        const val JSON_ENABLE_MODULES = """{"@type":"type.googleapis.com/lunarclient.apollo.configurable.v1.OverrideConfigurableSettingsMessage","configurable_settings":[{"apollo_module":"auto_text_hotkey","enable":true,"properties":{}},{"apollo_module":"colored_fire","enable":true,"properties":{"disable-block-miss-penalty":true}},{"apollo_module":"combat","enable":true,"properties":{"disable-miss-penalty":true,"disable-block-miss-penalty":false,"allow-dig-and-use":true}},{"apollo_module":"server_rule","enable":true,"properties":{"competitive-commands":["/server","/hub"],"brightness":50}}]}"""
        const val JSON_MISS_OFF = """{"apollo_module":"combat","enable":true,"properties":{"disable-miss-penalty":false},"@type":"type.googleapis.com/lunarclient.apollo.configurable.v1.ConfigurableSettings"}"""
        const val JSON_NO_PROPERTIES = """{"apollo_module":"combat","enable":true,"@type":"type.googleapis.com/lunarclient.apollo.configurable.v1.ConfigurableSettings"}"""
        const val JSON_OTHER_MODULE = """{"apollo_module":"colored_fire","enable":true,"properties":{"disable-miss-penalty":false},"@type":"type.googleapis.com/lunarclient.apollo.configurable.v1.ConfigurableSettings"}"""
        const val JSON_OTHER_MESSAGE = """{"@type":"type.googleapis.com/lunarclient.apollo.player.v1.UpdatePlayerWorldMessage","world":"combat"}"""
        const val JSON_MIXED_VALUES = """{"apollo_module":"combat","enable":true,"properties":{"disable-miss-penalty":"false","allow-dig-and-use":0,"disable-block-miss-penalty":true,"nested":{"a":[1]},"empty":null},"@type":"type.googleapis.com/lunarclient.apollo.configurable.v1.ConfigurableSettings"}"""

        fun bytes(hex: String): ByteArray = HexFormat.of().parseHex(hex)
    }

    private fun tapJson(json: String) {
        val buf = FriendlyByteBuf(Unpooled.buffer()).writeIdentifier(JSON_CHANNEL)
        buf.writeBytes(json.toByteArray())
        ApolloCombat.tap(buf, Runnable::run)
        assertEquals(0, buf.readerIndex())
    }

    private fun assertFeatures(miss: Boolean, block: Boolean, digAndUse: Boolean) {
        assertEquals(miss, ApolloCombat.isPresent(ServerFeatures.MISS_PENALTY))
        assertEquals(block, ApolloCombat.isPresent(ServerFeatures.BLOCK_MISS_PENALTY))
        assertEquals(digAndUse, ApolloCombat.isPresent(ServerFeatures.MINING_ITEM_USAGE))
    }

    @BeforeEach
    fun reset() = ApolloCombat.reset()

    @Test
    fun `reads combat options and ignores other modules`() {
        ApolloCombat.handle(COMBAT_SETTINGS)
        assertFeatures(miss = true, block = false, digAndUse = true)
    }

    @Test
    fun `single option update keeps earlier options`() {
        ApolloCombat.handle(COMBAT_SETTINGS)
        ApolloCombat.handle(SINGLE_OPTION)
        assertFeatures(miss = true, block = true, digAndUse = true)
    }

    @Test
    fun `disabling the module turns everything off`() {
        ApolloCombat.handle(COMBAT_SETTINGS)
        ApolloCombat.handle(MODULE_DISABLED)
        assertFeatures(miss = false, block = false, digAndUse = false)
    }

    @Test
    fun `standalone update disables one option and keeps the rest`() {
        ApolloCombat.handle(COMBAT_SETTINGS)
        ApolloCombat.handle(STANDALONE_MISS_OFF)
        assertFeatures(miss = false, block = false, digAndUse = true)
    }

    @Test
    fun `standalone update without enable turns the module off`() {
        ApolloCombat.handle(COMBAT_SETTINGS)
        ApolloCombat.handle(STANDALONE_NO_ENABLE)
        assertFeatures(miss = false, block = false, digAndUse = false)
    }

    @Test
    fun `ignores other Apollo messages`() {
        ApolloCombat.handle(OTHER_MESSAGE)
        assertFeatures(miss = false, block = false, digAndUse = false)
    }

    @Test
    fun `survives malformed packets`() {
        ApolloCombat.handle(COMBAT_SETTINGS.copyOf(COMBAT_SETTINGS.size - 3))
        ApolloCombat.handle(ByteArray(16) { -1 })
        assertFeatures(miss = false, block = false, digAndUse = false)
    }

    @Test
    fun `tap reads Apollo channels and leaves the packet for its decoder`() {
        val buf = FriendlyByteBuf(Unpooled.buffer()).writeIdentifier(LUNAR_CHANNEL)
        buf.writeBytes(COMBAT_SETTINGS)
        ApolloCombat.tap(buf, Runnable::run)
        assertFeatures(miss = true, block = false, digAndUse = true)
        assertEquals(0, buf.readerIndex())
    }

    @Test
    fun `tap ignores other channels`() {
        val buf = FriendlyByteBuf(Unpooled.buffer()).writeIdentifier(Identifier.fromNamespaceAndPath("minecraft", "brand"))
        buf.writeBytes(COMBAT_SETTINGS)
        ApolloCombat.tap(buf, Runnable::run)
        assertFeatures(miss = false, block = false, digAndUse = false)
    }

    @Test
    fun `json override reads combat and ignores other modules`() {
        tapJson(JSON_ENABLE_MODULES)
        assertFeatures(miss = true, block = false, digAndUse = true)
    }

    @Test
    fun `json standalone update disables one option and keeps the rest`() {
        tapJson(JSON_ENABLE_MODULES)
        tapJson(JSON_MISS_OFF)
        assertFeatures(miss = false, block = false, digAndUse = true)
    }

    @Test
    fun `json update without properties keeps earlier options`() {
        tapJson(JSON_ENABLE_MODULES)
        tapJson(JSON_NO_PROPERTIES)
        assertFeatures(miss = true, block = false, digAndUse = true)
    }

    @Test
    fun `json update for another module changes nothing`() {
        tapJson(JSON_ENABLE_MODULES)
        tapJson(JSON_OTHER_MODULE)
        assertFeatures(miss = true, block = false, digAndUse = true)
    }

    @Test
    fun `json ignores other Apollo messages`() {
        tapJson(JSON_ENABLE_MODULES)
        tapJson(JSON_OTHER_MESSAGE)
        assertFeatures(miss = true, block = false, digAndUse = true)
    }

    @Test
    fun `json skips non boolean property values`() {
        tapJson(JSON_ENABLE_MODULES)
        tapJson(JSON_MIXED_VALUES)
        assertFeatures(miss = true, block = true, digAndUse = true)
    }

    @Test
    fun `json survives malformed payloads`() {
        tapJson(JSON_ENABLE_MODULES)
        for (malformed in listOf(JSON_MISS_OFF.dropLast(10), "not json", "", "[]", "null", """{"@type":["x"]}""")) tapJson(malformed)
        tapJson("""{"@type":"type.googleapis.com/lunarclient.apollo.configurable.v1.OverrideConfigurableSettingsMessage","configurable_settings":"combat"}""")
        tapJson("""{"@type":"type.googleapis.com/lunarclient.apollo.configurable.v1.OverrideConfigurableSettingsMessage","configurable_settings":[1]}""")
        assertFeatures(miss = true, block = false, digAndUse = true)
    }

    @Test
    fun `apollo json is announced before the lunar channel servers detect`() {
        assertEquals(listOf(JSON_CHANNEL, LUNAR_CHANNEL), ApolloCombat.CHANNELS.keys.toList())
    }

    @Test
    fun `reset clears options`() {
        ApolloCombat.handle(COMBAT_SETTINGS)
        ApolloCombat.reset()
        assertFeatures(miss = false, block = false, digAndUse = false)
    }
}
//?}

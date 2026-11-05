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
        val OTHER_MESSAGE = bytes("0a49747970652e676f6f676c65617069732e636f6d2f6c756e6172636c69656e742e61706f6c6c6f2e706c617965722e76312e557064617465506c61796572576f726c644d65737361676512080a06636f6d626174")

        fun bytes(hex: String): ByteArray = HexFormat.of().parseHex(hex)
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
    fun `reset clears options`() {
        ApolloCombat.handle(COMBAT_SETTINGS)
        ApolloCombat.reset()
        assertFeatures(miss = false, block = false, digAndUse = false)
    }
}

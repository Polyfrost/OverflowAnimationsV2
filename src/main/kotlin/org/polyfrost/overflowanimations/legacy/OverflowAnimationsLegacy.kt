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

package org.polyfrost.overflowanimations.legacy

import dev.kikugie.fletching_table.annotation.fabric.Entrypoint
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.options.KeyBinding
import net.ornithemc.osl.keybinds.api.KeybindEvents
import net.ornithemc.osl.keybinds.api.KeybindRegistry
import net.ornithemc.osl.lifecycle.api.client.MinecraftClientEvents
import org.lwjgl.input.Keyboard
import org.polyfrost.oneconfig.api.commands.v1.CommandManager
import org.polyfrost.oneconfig.api.platform.v1.Platform
import org.polyfrost.overflowanimations.OverflowAnimations
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig

@Entrypoint
class OverflowAnimationsLegacy : ClientModInitializer {
    private lateinit var configKey: KeyBinding
    private lateinit var reloadKey: KeyBinding
    private var openConfig = false

    override fun onInitializeClient() {
        OverflowAnimations.initialize()

        KeybindEvents.REGISTER_KEYBINDS.register {
            configKey = KeybindRegistry.register("Open Mod Configuration", Keyboard.KEY_BACKSLASH, "key.category.overflowanimations.common")
            reloadKey = KeybindRegistry.register("Reload Mod", Keyboard.KEY_END, "key.category.overflowanimations.common")
        }
        MinecraftClientEvents.TICK_END.register { minecraft ->
            var pressed = openConfig
            openConfig = false
            while (configKey.consumeClick()) pressed = true
            if (pressed && minecraft.screen == null) OverflowAnimationsConfig.openScreen()
            var reload = false
            while (reloadKey.consumeClick()) reload = true
            if (reload) OverflowAnimations.reload()
        }

        CommandManager.register(
            CommandManager.literal("overflowanimations")
                .executes {
                    // The chat screen closes after the command runs, so the config opens at the end of the tick
                    openConfig = true
                    1
                }
                .then(CommandManager.literal("on").executes { setEnabled(true) })
                .then(CommandManager.literal("off").executes { setEnabled(false) })
                .then(CommandManager.literal("reload").executes {
                    OverflowAnimations.reload()
                    message("§aMod reloaded.")
                })
        )
    }

    private fun setEnabled(enabled: Boolean): Int {
        if (OverflowAnimations.isEnabled() == enabled) {
            return message("§eMod is already ${if (enabled) "enabled" else "disabled"}!")
        }
        OverflowAnimations.enabled = enabled
        OverflowAnimations.reload()
        return message(if (enabled) "§aMod enabled." else "§cMod disabled.")
    }

    private fun message(text: String): Int {
        Platform.compatibility().displayChatMessage(text)
        return 1
    }
}

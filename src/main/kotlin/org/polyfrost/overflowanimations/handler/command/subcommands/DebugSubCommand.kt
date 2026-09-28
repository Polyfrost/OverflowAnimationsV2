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

package org.polyfrost.overflowanimations.handler.command.subcommands

import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.network.chat.Component
import org.polyfrost.overflowanimations.OverflowAnimationsConstants

class DebugSubCommand : Command<FabricClientCommandSource> {
    companion object {
        @JvmField
        val UNIT = DebugSubCommand()
    }

    override fun run(context: CommandContext<FabricClientCommandSource>): Int {
        context.getSource().sendFeedback(Component.literal("Commit: " + OverflowAnimationsConstants.DEVELOPMENT_VERSION))
        context.getSource().sendFeedback(Component.literal("Version: " + OverflowAnimationsConstants.VERSION))
        context.getSource().sendFeedback(Component.literal("Packed Version: " + OverflowAnimationsConstants.VERSION.packedValue))
        context.getSource().sendFeedback(Component.literal("Is Dev Build: " + OverflowAnimationsConstants.IS_DEVELOPMENT))
        return Command.SINGLE_SUCCESS
    }
}
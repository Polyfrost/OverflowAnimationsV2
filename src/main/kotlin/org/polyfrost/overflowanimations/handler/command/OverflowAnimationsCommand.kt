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

package org.polyfrost.overflowanimations.handler.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
//? if 1.21.11 {
/*import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
*///?} else {
import net.fabricmc.fabric.api.client.command.v2.ClientCommands
//?}
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig
import org.polyfrost.overflowanimations.handler.command.subcommands.*
import java.util.*

class OverflowAnimationsCommand : Command<FabricClientCommandSource> {
    companion object {
        val UUIDS = listOf(
            UUID.fromString("41ee11aa-bde8-40e2-8283-f51c23a9c817"), // lowercasebtw
            UUID.fromString("b0f27308-0a70-43bf-b025-45c12979b7ad"), // lowercasebtw
            UUID.fromString("0e3ee1e0-f4d2-4550-8fe9-4f7a0d2cd08a"), // Mixces
            UUID.fromString("a5331404-0e77-440e-8bef-24c071dac1ae"), // Wyvest
            UUID.fromString("d8f72541-823d-4ded-9f7f-b67fdb34f43c"), // Tellinq
            UUID.fromString("c8bcf862-e997-4a54-9c59-681bd22096e5"), // Couleur
            UUID.fromString("4a34d440-475d-4ae8-9fd8-56284c5678fd") // Term4
        )

        fun create(): LiteralArgumentBuilder<FabricClientCommandSource> {
            //? if 1.21.11 {
            /*val command = ClientCommandManager.literal("overflowanimations").executes(OverflowAnimationsCommand())
            *///?} else {
            val command = ClientCommands.literal("overflowanimations").executes(OverflowAnimationsCommand())
            //?}
            command.then(subCommand("on", OnSubCommand.UNIT))
            command.then(subCommand("off", OffSubCommand.UNIT))
            command.then(subCommand("reload", ReloadSubCommand.UNIT))
            command.then(subCommand("presets", PresetsSubCommand.UNIT))

            val calendar = Calendar.getInstance()
            if (calendar.get(Calendar.MONTH) == Calendar.SEPTEMBER && calendar.get(Calendar.DAY_OF_MONTH) == 6) {
                command.then(subCommand("birthday", BirthdaySubCommand.UNIT))
            }

            val uuid = Minecraft.getInstance().gameProfile.id()
            if (uuid in UUIDS) {
                command.then(subCommand("debug", DebugSubCommand.UNIT))
            }

            return command
        }

        private fun subCommand(name: String, command: Command<FabricClientCommandSource>) =
            LiteralArgumentBuilder.literal<FabricClientCommandSource>(name).executes(command)
    }

    override fun run(context: CommandContext<FabricClientCommandSource>): Int {
        val source = context.getSource()
        source.sendFeedback(Component.literal("Opening config menu...").withColor(Random().nextInt(0xFFFFFF)))

        val minecraft = source.client
        //? if <26.2 {
        /*minecraft.schedule({ minecraft.setScreen(OverflowAnimationsConfig.getConfigScreen(minecraft.screen)) })
        *///?} else {
        minecraft.schedule({ minecraft.gui.setScreen(OverflowAnimationsConfig.getConfigScreen(minecraft.gui.screen())) })
        //?}

        return Command.SINGLE_SUCCESS
    }
}
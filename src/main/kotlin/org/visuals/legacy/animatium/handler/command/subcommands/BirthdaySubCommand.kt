/**
 * Animatium
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

package org.visuals.legacy.animatium.handler.command.subcommands

import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.ARGB
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.player.Player

class BirthdaySubCommand : Command<FabricClientCommandSource> {
    companion object {
        @JvmField
        val UNIT = BirthdaySubCommand()
    }

    //? if 1.21.11 {
    /*private val random = RandomSource.createNewThreadLocalInstance()
    *///?} else {
    private val random = RandomSource.createThreadLocalInstance()
    //?}

    override fun run(context: CommandContext<FabricClientCommandSource>): Int {
        val entity = context.getSource().entity
        if (entity is Player) {
            //? if 1.21.11 {
            /*val level = context.getSource().world
            *///?} else {
            val level = context.getSource().level
            //?}
            val x = entity.blockX + this.random.nextDouble()
            val y = entity.blockY + this.random.nextDouble()
            val z = entity.blockZ + this.random.nextDouble()
            for (i in 0..<180) {
                val sound = if (this.random.nextIntBetweenInclusive(0, 6) > 3) {
                    SoundEvents.AMETHYST_BLOCK_CHIME
                } else {
                    SoundEvents.FIREWORK_ROCKET_BLAST
                }

                level.playLocalSound(x, y, z, sound, SoundSource.AMBIENT, 20.0F, 0.95F + this.random.nextFloat() * 0.15F, true)
            }

            context.getSource().sendFeedback(colorful("It's the creators birthday today!!! Wish them a happy birthday!"))
        }

        return Command.SINGLE_SUCCESS
    }

    private fun colorful(literal: String): Component {
        val parts = literal.split("")

        val component = Component.empty()
        for (part in parts) {
            component.append(Component.literal(part).withColor(ARGB.opaque((Math.random() * 16777215).toInt())))
        }

        return component
    }
}
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

package org.polyfrost.overflowanimations.handler.screen

import net.minecraft.ChatFormatting
//? if 1.21.11 {
/*import net.minecraft.client.gui.GuiGraphics
*///?} else {
import net.minecraft.client.gui.GuiGraphicsExtractor
//?}
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.Tooltip
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.input.MouseButtonEvent
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component
import net.minecraft.util.ARGB
import org.polyfrost.overflowanimations.handler.rendering.drawScaledText
import org.polyfrost.overflowanimations.util.config.GeneralConfigUtil
import org.polyfrost.overflowanimations.util.config.PresetVersion

class PresetsScreen(private val original: Screen?) :
    Screen(Component.literal("Presets")) {
    private var v1_7Button: Button? = null
    private var v1_8Button: Button? = null
    private var modernButton: Button? = null

    private var presetVersion = PresetVersion.VANILLA

    private val BUTTON_WIDTH = 100

    override fun init() {
        this.original?.init(this.width, this.height)
        this.presetVersion = GeneralConfigUtil.getEnum(GeneralConfigUtil.PRESET_VERSION_KEY, PresetVersion.VANILLA)

        this.setupVersionButtons()

        val doneButton = Button.builder(CommonComponents.GUI_DONE) { _: Button ->
            if (this.presetVersion != GeneralConfigUtil.getEnum(GeneralConfigUtil.PRESET_VERSION_KEY, PresetVersion.VANILLA)) {
                GeneralConfigUtil.put(GeneralConfigUtil.PRESET_VERSION_KEY, this.presetVersion)
                this.presetVersion.apply()
                //? if 1.21.11 {
                /*this.minecraft.gui.chat.addMessage(
                *///?} elif 26.1 {
                /*this.minecraft.gui.chat.addClientSystemMessage(
                *///?} else {
                this.minecraft.gui.hud.chat.addClientSystemMessage(
                //?}
                    Component.literal("Applied preset " + this@PresetsScreen.presetVersion.name + "!").withColor(-0xFF0100)
                )
            } else {
                //? if 1.21.11 {
                /*this.minecraft.gui.chat.addMessage(
                *///?} elif 26.1 {
                /*this.minecraft.gui.chat.addClientSystemMessage(
                *///?} else {
                this.minecraft.gui.hud.chat.addClientSystemMessage(
                //?}
                    Component.literal("No preset applied as it already matches what you have!").withStyle(ChatFormatting.GOLD)
                )
            }

            //? if <26.2 {
            /*this.minecraft.setScreen(this.original)
            *///?} else {
            this.minecraft.gui.setScreen(this.original)
            //?}
        }
        this.addRenderableWidget(
            doneButton
                .pos((this.width / 2) - (Button.DEFAULT_WIDTH / 2), (this.height / 1.2f).toInt())
                .tooltip(
                    Tooltip.create(
                        Component.literal("WARNING! THIS WILL RESET ALL YOUR SETTINGS").withStyle(ChatFormatting.RED)
                    )
                ).build()
        )
    }

    private fun setupVersionButtons() {
        this.v1_7Button = this.addRenderableWidget(
            Button.builder(Component.literal("1.7")) { _: Button -> this.presetVersion = PresetVersion.V1_7 }
                .bounds(
                    ((this.width / 2) - (BUTTON_WIDTH / 2) - (BUTTON_WIDTH + Button.DEFAULT_SPACING)),
                    this.height / 2,
                    BUTTON_WIDTH,
                    Button.DEFAULT_HEIGHT
                )
                .build()
        )
        this.v1_8Button = this.addRenderableWidget(
            Button.builder(Component.literal("1.8")) { _: Button -> this.presetVersion = PresetVersion.V1_8 }
                .bounds((this.width / 2) - (BUTTON_WIDTH / 2), this.height / 2, BUTTON_WIDTH, Button.DEFAULT_HEIGHT)
                .build())
        this.modernButton = this.addRenderableWidget(
            Button.builder(Component.literal("Modern")) { _: Button -> this.presetVersion = PresetVersion.VANILLA }
                .bounds(
                    ((this.width / 2) - (BUTTON_WIDTH / 2) + (BUTTON_WIDTH + Button.DEFAULT_SPACING)),
                    this.height / 2,
                    BUTTON_WIDTH,
                    Button.DEFAULT_HEIGHT
                )
                .build()
        )
        this.updateVersionButtonState()
    }

    //? if 1.21.11 {
    /*override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, tickDelta: Float) {
        this.original?.render(graphics, -999, -999, tickDelta)
    *///?} else {
    override fun extractRenderState(graphics: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, tickDelta: Float) {
        this.original?.extractRenderState(graphics, -999, -999, tickDelta)
    //?}

        graphics.fill(0, 0, this.width, this.height, ARGB.color(0.35F, 0))
        //? if 1.21.11 {
        /*super.render(graphics, mouseX, mouseY, tickDelta)
        *///?} else {
        super.extractRenderState(graphics, mouseX, mouseY, tickDelta)
        //?}

        graphics.drawScaledText(
            this.font,
            "Welcome to OverflowAnimations!",
            this.width / 2,
            this.height / 4,
            2.0F
        )
        //? if 1.21.11 {
        /*graphics.drawCenteredString(
        *///?} else {
        graphics.centeredText(
        //?}
            this.font,
            "Hello! Thank you for downloading OverflowAnimations!",
            this.width / 2,
            (this.height / 2.8).toInt(),
            ARGB.white(0xD6D6D6)
        )
        //? if 1.21.11 {
        /*graphics.drawCenteredString(
        *///?} else {
        graphics.centeredText(
        //?}
            this.font,
            "Please select the version of visuals you would like to use!",
            this.width / 2,
            (this.height / 2.4).toInt(),
            ARGB.white(0xD6D6D6)
        )
        //? if 1.21.11 {
        /*graphics.drawCenteredString(
        *///?} else {
        graphics.centeredText(
        //?}
            this.font,
            "Current saved version: " + GeneralConfigUtil.getEnum(
                GeneralConfigUtil.PRESET_VERSION_KEY,
                this.presetVersion
            ),
            this.width / 2,
            (this.height / 1.3F).toInt(),
            ARGB.white(0.625F)
        )
    }

    override fun mouseClicked(event: MouseButtonEvent, doubleClick: Boolean): Boolean =
        if (this.v1_7Button!!.mouseClicked(event, doubleClick) ||
            this.v1_8Button!!.mouseClicked(event, doubleClick) ||
            this.modernButton!!.mouseClicked(event, doubleClick)
        ) {
            this.updateVersionButtonState()
            true
        } else {
            super.mouseClicked(event, doubleClick)
        }

    private fun updateVersionButtonState() {
        this.v1_7Button?.active = this.presetVersion != PresetVersion.V1_7
        this.v1_8Button?.active = this.presetVersion != PresetVersion.V1_8
        this.modernButton?.active = this.presetVersion != PresetVersion.VANILLA
    }
}
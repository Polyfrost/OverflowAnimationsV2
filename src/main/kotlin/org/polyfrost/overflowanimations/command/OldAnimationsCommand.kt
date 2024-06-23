package org.polyfrost.overflowanimations.command

import cc.polyfrost.oneconfig.utils.commands.annotations.Command
import cc.polyfrost.oneconfig.utils.commands.annotations.Main
import org.polyfrost.overflowanimations.OverflowAnimations
import org.polyfrost.overflowanimations.config.MainModSettings

@Command(
    value = OverflowAnimations.MODID,
    aliases = ["oam", "oldanimations", "animations"],
    description = "Access the " + OverflowAnimations.NAME + " GUI."
)
class OldAnimationsCommand {

    @Main
    fun handle() {
        MainModSettings.openGui()
    }

}

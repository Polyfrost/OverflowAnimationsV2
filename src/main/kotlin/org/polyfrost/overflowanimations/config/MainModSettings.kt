package org.polyfrost.overflowanimations.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.SubConfig
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import org.polyfrost.overflowanimations.OverflowAnimations

object MainModSettings : Config(Mod(OverflowAnimations.NAME, ModType.PVP, "/overflowanimations_dark.svg"), "overflowanimations.json") {

    @SubConfig var oldSettings: OldAnimationsSettings = OldAnimationsSettings()

    @SubConfig var positionSettings: ItemPositionSettings = ItemPositionSettings()

    init {
        initialize()
    }

}

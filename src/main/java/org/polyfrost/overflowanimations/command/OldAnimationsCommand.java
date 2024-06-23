package org.polyfrost.overflowanimations.command;

import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;

@Command(value = "overflowanimations", aliases = {"oam", "oldanimations", "animations"}, description = "Overflow Animations")
public class OldAnimationsCommand {
    @Main
    public void handle() {
        OldAnimationsSettings.INSTANCE.openGui();
    }
}

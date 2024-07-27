package org.polyfrost.overflowanimations.command;

import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import org.polyfrost.oneconfig.api.commands.v1.factories.annotated.Command;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;

@Command(value = {"overflowanimations", "oam", "oldanimations", "animations"}, description = "Overflow Animations")
public class OldAnimationsCommand {
    @Command
    public void handle() {
        OldAnimationsSettings.INSTANCE.openGui();
    }
}

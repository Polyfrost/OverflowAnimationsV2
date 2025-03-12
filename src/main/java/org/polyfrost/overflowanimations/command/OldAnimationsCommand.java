package org.polyfrost.overflowanimations.command;

import org.polyfrost.oneconfig.api.commands.v1.factories.annotated.Command;
import org.polyfrost.oneconfig.utils.v1.dsl.ScreensKt;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;

@Command(value = {"overflowanimations", "oam", "oldanimations", "animations"})
public class OldAnimationsCommand {
    @Command
    public void main() {
        ScreensKt.openUI(OldAnimationsSettings.INSTANCE);
    }
}

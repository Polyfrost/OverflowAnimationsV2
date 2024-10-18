package org.polyfrost.overflowanimations.command;

import org.polyfrost.oneconfig.api.commands.v1.factories.annotated.Command;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.utils.v1.dsl.ScreensKt;

@Command(value = {"overflowanimations", "oam", "oldanimations", "animations"}, description = "Overflow Animations")
public class OldAnimationsCommand {
    @Command
    public void main() {
        //todo: wyvest help
//        OldAnimationsSettings.INSTANCE.openGui();
        ScreensKt.openUI(OldAnimationsSettings.INSTANCE);
    }
}

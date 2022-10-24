package cc.polyfrost.overflowanimations.command;

import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import cc.polyfrost.overflowanimations.OverflowAnimations;

@Command(value = "overflowanimations", aliases = {"oam", "oldanimations"}, description = "Overflow Animations")
public class OldAnimationsCommand {
    @Main
    public void handle() {
        OverflowAnimations.oldAnimationsSettings.openGui();
    }
}

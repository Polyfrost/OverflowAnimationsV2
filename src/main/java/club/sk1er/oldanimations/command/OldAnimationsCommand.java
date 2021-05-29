package club.sk1er.oldanimations.command;

import club.sk1er.oldanimations.OldAnimations;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;
import gg.essential.api.utils.GuiUtil;

import java.util.Objects;

public class OldAnimationsCommand extends Command {
    public OldAnimationsCommand() {
        super("oldanimations");
    }

    @DefaultHandler
    public void handle() {
        GuiUtil.open(Objects.requireNonNull(OldAnimations.oldAnimationsSettings.gui()));
    }
}

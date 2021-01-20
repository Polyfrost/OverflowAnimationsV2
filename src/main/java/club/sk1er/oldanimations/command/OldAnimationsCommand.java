package club.sk1er.oldanimations.command;

import club.sk1er.oldanimations.OldAnimations;
import net.modcore.api.commands.Command;
import net.modcore.api.commands.DefaultHandler;
import net.modcore.api.utils.GuiUtil;

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

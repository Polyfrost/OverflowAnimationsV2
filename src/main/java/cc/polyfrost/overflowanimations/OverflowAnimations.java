package cc.polyfrost.overflowanimations;

import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import cc.polyfrost.overflowanimations.command.OldAnimationsCommand;
import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = OverflowAnimations.MODID, name = OverflowAnimations.NAME, version = OverflowAnimations.VERSION)
public class OverflowAnimations {
    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";
    public static OldAnimationsSettings oldAnimationsSettings;

    public static float eyeHeight;
    public static ItemCameraTransforms.TransformType transform;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        oldAnimationsSettings = new OldAnimationsSettings();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        CommandManager.INSTANCE.registerCommand(new OldAnimationsCommand());
    }
}

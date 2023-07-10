package cc.polyfrost.overflowanimations;

import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import cc.polyfrost.overflowanimations.command.OldAnimationsCommand;
import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = OverflowAnimations.MODID, name = OverflowAnimations.NAME, version = OverflowAnimations.VERSION)
public class OverflowAnimations {
    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";
    public static boolean isPatcherLoaded = false;

    @Mod.EventHandler
    public void onPostInitialization(FMLPostInitializationEvent event) {
        isPatcherLoaded = Loader.isModLoaded("patcher");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        OldAnimationsSettings.INSTANCE.preLoad();
        CommandManager.INSTANCE.registerCommand(new OldAnimationsCommand());
    }
}

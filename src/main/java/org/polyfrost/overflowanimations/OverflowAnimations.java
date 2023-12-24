package org.polyfrost.overflowanimations;

import cc.polyfrost.oneconfig.config.core.ConfigUtils;
import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.events.event.RenderEvent;
import cc.polyfrost.oneconfig.events.event.Stage;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import cc.polyfrost.oneconfig.utils.Notifications;
import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import dulkirmod.config.DulkirConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.polyfrost.overflowanimations.command.OldAnimationsCommand;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.polyfrost.overflowanimations.hooks.AnimationExportUtils;
import org.polyfrost.overflowanimations.hooks.DulkirConfigData;

import java.io.File;

@Mod(modid = OverflowAnimations.MODID, name = OverflowAnimations.NAME, version = OverflowAnimations.VERSION)
public class OverflowAnimations {
    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";

    private static boolean doTheFunnyDulkirThing = false;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        OldAnimationsSettings.INSTANCE.preload();
        CommandManager.INSTANCE.registerCommand(new OldAnimationsCommand());
        EventManager.INSTANCE.register(this);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (Loader.isModLoaded("dulkirmod")) {
            doTheFunnyDulkirThing = true;
        }
    }

    @Subscribe
    private void onTick(RenderEvent event) {
        if (event.stage == Stage.START && Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().thePlayer != null && doTheFunnyDulkirThing && !OldAnimationsSettings.didTheFunnyDulkirThing) {
            if (DulkirConfig.INSTANCE.getCustomAnimations()) {
                Notifications.INSTANCE.send("OverflowAnimations", "OverflowAnimations now replaces DulkirMod's animations feature - would you like to import your DulkirMod config? Click here to automatically transfer your config.", 15000, () -> {
                    try {
                        File dulkirConfigFile = ConfigUtils.getProfileFile("dulkirmod-config.json");
                        String dulkirConfig = FileUtils.readFileToString(dulkirConfigFile, Charsets.UTF_8);
                        FileUtils.writeStringToFile(new File(dulkirConfigFile.getParentFile(), "dulkirmod-config_backup.json"), dulkirConfig, Charsets.UTF_8);
                        File overflowConfigFile = ConfigUtils.getProfileFile("overflowanimations.json");
                        String overflowConfig = FileUtils.readFileToString(overflowConfigFile, Charsets.UTF_8);
                        FileUtils.writeStringToFile(new File(overflowConfigFile.getParentFile(), "overflowanimations_backup.json"), overflowConfig, Charsets.UTF_8);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Notifications.INSTANCE.send("OverflowAnimations", "Failed to backup configs! Click here if you want to continue despite this error.", this::importDulkirConfig);
                        return;
                    }
                    importDulkirConfig();
                });
                doTheFunnyDulkirThing = false;
                OldAnimationsSettings.didTheFunnyDulkirThing = true;
            }
        }
    }

    private void importDulkirConfig() {
        DulkirConfigData data = new DulkirConfigData(
                DulkirConfig.INSTANCE.getCustomSize(),
                DulkirConfig.INSTANCE.getDoesScaleSwing(),
                DulkirConfig.INSTANCE.getCustomX(),
                DulkirConfig.INSTANCE.getCustomY(),
                DulkirConfig.INSTANCE.getCustomZ(),
                DulkirConfig.INSTANCE.getCustomYaw(),
                DulkirConfig.INSTANCE.getCustomPitch(),
                DulkirConfig.INSTANCE.getCustomRoll(),
                DulkirConfig.INSTANCE.getCustomSpeed(),
                DulkirConfig.INSTANCE.getIgnoreHaste(),
                DulkirConfig.INSTANCE.getDrinkingSelector()
        );
        AnimationExportUtils.importDulkir(data);
        DulkirConfig.INSTANCE.setCustomAnimations(false);
        DulkirConfig.INSTANCE.demoButton();
        Notifications.INSTANCE.send("OverflowAnimations", "Successfully imported DulkirMod config!");
    }
}

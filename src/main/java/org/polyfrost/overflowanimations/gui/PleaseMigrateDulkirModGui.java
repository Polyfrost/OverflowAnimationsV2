package org.polyfrost.overflowanimations.gui;

import cc.polyfrost.oneconfig.config.core.ConfigUtils;
import cc.polyfrost.oneconfig.gui.OneConfigGui;
import cc.polyfrost.oneconfig.gui.elements.BasicButton;
import cc.polyfrost.oneconfig.libs.universal.UResolution;
import cc.polyfrost.oneconfig.renderer.NanoVGHelper;
import cc.polyfrost.oneconfig.renderer.font.Fonts;
import cc.polyfrost.oneconfig.utils.InputHandler;
import cc.polyfrost.oneconfig.utils.Notifications;
import cc.polyfrost.oneconfig.utils.color.ColorPalette;
import cc.polyfrost.oneconfig.utils.color.ColorUtils;
import cc.polyfrost.oneconfig.utils.gui.GuiUtils;
import cc.polyfrost.oneconfig.utils.gui.OneUIScreen;
import dulkirmod.config.DulkirConfig;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.polyfrost.overflowanimations.hooks.AnimationExportUtils;
import org.polyfrost.overflowanimations.hooks.DulkirConfigData;

import java.io.File;

public class PleaseMigrateDulkirModGui extends OneUIScreen {
    private static final int GRAY_800 = ColorUtils.getColor(21, 22, 23, 255);
    private static final int WHITE_90 = ColorUtils.getColor(255, 255, 255, 229);
    private static final String TRANSFER = "Transfer";
    private static final String CANCEL = "Cancel";
    private final BasicButton transferButton = new BasicButton(
            -1,
            40,
            TRANSFER,
            2,
            ColorPalette.PRIMARY
    );
    private final BasicButton cancelButton = new BasicButton(
            -1,
            40,
            CANCEL,
            2,
            ColorPalette.PRIMARY_DESTRUCTIVE
    );

    @Override
    public void draw(long vg, float partialTicks, InputHandler inputHandler) {
        if (transferButton.getWidth() == -1) {
            transferButton.setWidth((int) (NanoVGHelper.INSTANCE.getTextWidth(vg, TRANSFER, 14f, Fonts.MEDIUM) + 40));
            transferButton.setClickAction(() -> {
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
        }
        if (cancelButton.getWidth() == -1) {
            cancelButton.setWidth((int) (NanoVGHelper.INSTANCE.getTextWidth(vg, CANCEL, 14f, Fonts.MEDIUM) + 40));
            cancelButton.setClickAction(() -> {
                cancelButton.setText("Confirm");
                cancelButton.setWidth((int) (NanoVGHelper.INSTANCE.getTextWidth(vg, "Confirm", 14f, Fonts.MEDIUM) + 40));
                cancelButton.setClickAction(() -> {
                    cancelButton.setText(CANCEL);
                    cancelButton.setWidth((int) (NanoVGHelper.INSTANCE.getTextWidth(vg, CANCEL, 14f, Fonts.MEDIUM) + 40));
                    GuiUtils.displayScreen(null);
                });
            });
        }
        float scale = OneConfigGui.getScaleFactor();
        int x = (int) ((UResolution.getWindowWidth() - 600 * scale) / 2f / scale);
        int y = (int) ((UResolution.getWindowHeight() - 240 * scale) / 2f / scale);
        NanoVGHelper.INSTANCE.scale(vg, scale, scale);
        inputHandler.scale(scale, scale);

        NanoVGHelper.INSTANCE.drawRoundedRect(vg, x, y, 600, 240, GRAY_800, 20);
        NanoVGHelper.INSTANCE.drawCenteredText(vg, "OverflowAnimations", x + 300, y + 40, WHITE_90, 28, Fonts.MEDIUM);
        NanoVGHelper.INSTANCE.drawCenteredText(vg, "OverflowAnimations now replaces DulkirMod's animations feature.", x + 300, y + 100, WHITE_90, 16, Fonts.REGULAR);
        NanoVGHelper.INSTANCE.drawCenteredText(vg, "Would you like to import your DulkirMod config?", x + 300, y + 120, WHITE_90, 16, Fonts.REGULAR);

        transferButton.draw(vg, x + 300 - transferButton.getWidth(), y + 180, inputHandler);
        cancelButton.draw(vg, x + 300 + 10, y + 180, inputHandler);
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

package com.github.oobila.bukkit.blocks.customblock;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Transformation;

import java.util.Set;


public abstract class TextBlock extends CustomBlock {

    private final String text;

    public TextBlock(Plugin plugin, String name, DisplayItemConfig config, String text) {
        super(plugin, name, config);
        int rows = StringUtils.countMatches(text, "\n") + 1;
        this.text = rows > 10 ? "ERROR\nThere are too\nmany rows!!" : text;
    }

    @Override
    public Set<Display> placeDisplays(Player player, Location location) {
        TextDisplay textDisplay = getTextDisplay(config, text, location);
        return Set.of(textDisplay);
    }

    static TextDisplay getTextDisplay(DisplayItemConfig config, String text, Location location) {
        Location textLocation = location.clone().add(0.5, 0.55, 0.5);
        TextDisplay textDisplay = (TextDisplay) location.getWorld().spawnEntity(
                textLocation,
                EntityType.TEXT_DISPLAY
        );
        textDisplay.setText(text);
        Transformation transformation = textDisplay.getTransformation();
        double widthScale = getTargetScale(getTextWidth(text));
        transformation.getScale().set(widthScale);
        int rows = StringUtils.countMatches(text, "\n") + 1;
        textDisplay.teleport(textLocation.add(0, -(rows * 0.15 * widthScale), 0));
        textDisplay.setTransformation(transformation);
        textDisplay.setViewRange(.1f);
        textDisplay.setBillboard(Display.Billboard.CENTER);
        if (config.isBright()) {
            textDisplay.setBrightness(new Display.Brightness(15, 15));
        }
        return textDisplay;
    }

    private static int getTextWidth(String text) {
        String[] strings = text.split("\n");
        int width = 0;
        for(String s : strings) {
            if (width < s.length()) {
                width = s.length();
            }
        }
        return width;
    }

    private static double getTargetScale(int width) {
        if (width < 8) {
            return 1d;
        } else {
            return 7d/width;
        }
    }

}

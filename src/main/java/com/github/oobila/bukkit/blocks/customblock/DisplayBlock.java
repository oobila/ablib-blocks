package com.github.oobila.bukkit.blocks.customblock;

import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public abstract class DisplayBlock extends CustomBlock {

    private final String text;
    private final DisplayItemConfig textConfig;
    private final DisplayItemConfig headConfig;

    public DisplayBlock(Plugin plugin, String name, DisplayItemConfig config, ItemStack head, String text) {
        this (plugin, name, config, config, head, text);
    }

    public DisplayBlock(Plugin plugin, String name, DisplayItemConfig textConfig, DisplayItemConfig headConfig, ItemStack head, String text) {
        super(plugin, name, headConfig);
        this.text = text;
        this.textConfig = textConfig;
        this.headConfig = headConfig;
    }

    @Override
    public Set<Display> placeDisplays(Player player, Location location) {
        TextDisplay textDisplay = TextBlock.getTextDisplay(
                textConfig,
                text,
                location.clone().add(0, 0.1, 0)
        );
        ItemDisplay headDisplay = HeadBlock.getHeadDisplay(
                headConfig,
                player,
                location.clone().add(0, -0.3, 0),
                0.6d
        );
        return Set.of(textDisplay, headDisplay);
    }
}

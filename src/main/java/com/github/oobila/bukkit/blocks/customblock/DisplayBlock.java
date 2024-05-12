package com.github.oobila.bukkit.blocks.customblock;

import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Transformation;

import java.util.Set;

public abstract class DisplayBlock extends CustomBlock {

    private final ItemStack head;
    private final String text;

    public DisplayBlock(Plugin plugin, String name, DisplayItemConfig config, ItemStack head, String text) {
        super(plugin, name, config);
        this.head = head;
        this.text = text;
    }

    @Override
    public Set<Display> placeDisplays(Player player, Location location) {
        TextDisplay textDisplay = TextBlock.getTextDisplay(config, text, location.clone().add(0, 0.1, 0));
        ItemDisplay headDisplay = HeadBlock.getHeadDisplay(config, head, player,
                location.clone().add(0, -0.3, 0));
        Transformation transformation = headDisplay.getTransformation();
        transformation.getScale().set(0.6d);
        headDisplay.setTransformation(transformation);
        return Set.of(textDisplay, headDisplay);
    }
}

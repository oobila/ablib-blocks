package com.github.oobila.bukkit.blocks.customblock;

import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public class StickerBlock extends CustomBlock {

    public StickerBlock(Plugin plugin, String name, HeadItemConfig config) {
        super(plugin, name, config.toDisplayItemConfig());
    }

    @Override
    public Set<Display> placeDisplays(Player player, Location location) {
        ItemDisplay itemDisplay = HeadBlock.getHeadDisplay(getConfig(), player, location, 1.4);
        return Set.of(itemDisplay);
    }
}

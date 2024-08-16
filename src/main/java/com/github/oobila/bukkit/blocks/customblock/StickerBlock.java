package com.github.oobila.bukkit.blocks.customblock;

import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public class StickerBlock extends CustomBlock {

    private final ItemStack head;

    public StickerBlock(Plugin plugin, String name, HeadItemConfig config, ItemStack head) {
        super(plugin, name, config.toDisplayItemConfig());
        this.head = head;
    }

    @Override
    public Set<Display> placeDisplays(Player player, Location location) {
        ItemDisplay itemDisplay = HeadBlock.getHeadDisplay(getConfig(), head, player, location, 1.4);
        return Set.of(itemDisplay);
    }
}

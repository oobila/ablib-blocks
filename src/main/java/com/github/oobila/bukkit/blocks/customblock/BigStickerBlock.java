package com.github.oobila.bukkit.blocks.customblock;

import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public class BigStickerBlock extends CustomBlock {

    private final ItemStack head;

    public BigStickerBlock(Plugin plugin, String name, DisplayItemConfig config, ItemStack head) {
        super(plugin, name, config);
        this.head = head;
    }

    @Override
    public Set<Display> placeDisplays(Player player, Location location) {
        ItemDisplay itemDisplay = HeadBlock.getHeadDisplay(getConfig(), head, player, location, 6);
        return Set.of(itemDisplay);
    }
}

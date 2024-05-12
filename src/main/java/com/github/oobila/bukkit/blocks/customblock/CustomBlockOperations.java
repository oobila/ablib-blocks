package com.github.oobila.bukkit.blocks.customblock;

import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;

import java.util.Set;

public interface CustomBlockOperations {
    Set<Display> placeDisplays(Player player, Location location);

}

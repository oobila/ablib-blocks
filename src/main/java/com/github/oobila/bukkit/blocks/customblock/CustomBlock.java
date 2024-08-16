package com.github.oobila.bukkit.blocks.customblock;

import com.github.oobila.bukkit.itemstack.PersistentMetaUtil;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.github.oobila.bukkit.common.ABCommon.key;
import static com.github.oobila.bukkit.common.ABCommon.runTaskLater;

public abstract class CustomBlock implements CustomBlockOperations {

    private static final String CUSTOM_BLOCK_ID = "custom_block_id";

    private static final Map<UUID, CustomBlock> registeredBlocks = new HashMap<>();

    @Getter
    private final Plugin plugin;
    @Getter
    private final String name;
    @Getter
    private final UUID customBlockId;
    @Getter(AccessLevel.PROTECTED)
    private final DisplayItemConfig config;

    protected CustomBlock(Plugin plugin, String name, DisplayItemConfig config) {
        this.plugin = plugin;
        this.name = name;
        this.config = config;

        ItemMeta itemMeta = this.config.getItemStack().getItemMeta();
        this.customBlockId = UUID.nameUUIDFromBytes((plugin.getName() + name).getBytes());
        PersistentMetaUtil.add(itemMeta, key(CUSTOM_BLOCK_ID), customBlockId);
        this.config.getItemStack().setItemMeta(itemMeta);

        registeredBlocks.put(customBlockId, this);
    }

    public void blockPlace(Player player, Location location) {
        Set<Display> displays = placeDisplays(player, location);
        displays.forEach(display -> PersistentMetaUtil.add(display, key(CUSTOM_BLOCK_ID), customBlockId));
        runTaskLater(() -> location.getWorld().getBlockAt(location).setType(config.getBlockMaterial()), 1);
    }

    public void blockBreak(Player player, Location location) {
        for (Entity entity : location.getWorld().getNearbyEntities(location.getBlock().getBoundingBox())) {
            if (entity instanceof Display display) {
                UUID id = PersistentMetaUtil.getUUID(display, key(CUSTOM_BLOCK_ID));
                if (id.equals(customBlockId)) {
                    entity.remove();
                }
            }
        }
    }

    public ItemStack getItemStack() {
        return config.getItemStack().clone();
    }

    public static boolean isCustomBlock(Location location) {
        for (Entity entity : location.getWorld().getNearbyEntities(location.getBlock().getBoundingBox())) {
            if (entity instanceof Display display) {
                return PersistentMetaUtil.containsKey(display, key(CUSTOM_BLOCK_ID));
            }
        }
        return false;
    }

    public static boolean isCustomBlock(ItemStack itemInHand) {
        return PersistentMetaUtil.containsKey(itemInHand.getItemMeta(), key(CUSTOM_BLOCK_ID));
    }

    public static CustomBlock getCustomBlock(Location location) {
        for (Entity entity : location.getWorld().getNearbyEntities(location.getBlock().getBoundingBox())) {
            if (entity instanceof Display display) {
                UUID customBlockId = PersistentMetaUtil.getUUID(display, key(CUSTOM_BLOCK_ID));
                return registeredBlocks.get(customBlockId);
            }
        }
        return null;
    }

    public static CustomBlock getCustomBlock(ItemStack itemInHand) {
        UUID id = PersistentMetaUtil.getUUID(itemInHand.getItemMeta(), key(CUSTOM_BLOCK_ID));
        return registeredBlocks.get(id);
    }
}
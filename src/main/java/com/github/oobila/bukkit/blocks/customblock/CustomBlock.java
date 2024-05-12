package com.github.oobila.bukkit.blocks.customblock;

import com.github.oobila.bukkit.common.utils.MaterialUtil;
import com.github.oobila.bukkit.common.utils.model.ColoredMaterialType;
import com.github.oobila.bukkit.itemstack.PersistentMetaUtil;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
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

import static com.github.oobila.bukkit.common.ABCommon.runTaskLater;

public abstract class CustomBlock implements CustomBlockOperations {

    private static final String CUSTOM_BLOCK_ID = "custom_block_id";
    private static NamespacedKey namespacedKey;

    private static final Map<UUID, CustomBlock> registeredBlocks = new HashMap<>();

    @Getter
    private final Plugin plugin;
    @Getter
    private final String name;
    @Getter
    private final UUID customBlockId;
    final DisplayItemConfig config;

    public static void setupNamespace(Plugin plugin) {
        namespacedKey = new NamespacedKey(plugin, CUSTOM_BLOCK_ID);
    }

    public CustomBlock(Plugin plugin, String name, DisplayItemConfig config) {
        this.plugin = plugin;
        this.name = name;
        this.config = config;

        ItemMeta itemMeta = this.config.getItemStack().getItemMeta();
        this.customBlockId = UUID.nameUUIDFromBytes((plugin.getName() + name).getBytes());
        PersistentMetaUtil.add(itemMeta, namespacedKey, customBlockId);
        this.config.getItemStack().setItemMeta(itemMeta);

        if (!MaterialUtil.isColoredBlock(ColoredMaterialType.STAINED_GLASS, config.getGlassMaterial())) {
            throw new RuntimeException("CustomBlock attempted to be created with non stained-glass material: " + config.getGlassMaterial().name());
        }

        registeredBlocks.put(customBlockId, this);
    }

    public void blockPlace(Player player, Location location) {
        Set<Display> displays = placeDisplays(player, location);
        displays.forEach(display -> PersistentMetaUtil.add(display, namespacedKey, customBlockId));
        runTaskLater(() -> location.getWorld().getBlockAt(location).setType(config.getGlassMaterial()), 1);
    }

    public void blockBreak(Player player, Location location) {
        for (Entity entity : location.getWorld().getNearbyEntities(location.getBlock().getBoundingBox())) {
            if (entity instanceof Display display) {
                UUID id = PersistentMetaUtil.getUUID(display, namespacedKey);
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
        if (!MaterialUtil.isColoredBlock(ColoredMaterialType.STAINED_GLASS, location.getBlock().getType())) {
            return false; //is not stained glass block
        }

        if (!containsCustomBlockEntity(location)) {
            return false; //does not contain custom block entity
        }

        return true;
    }

    public static boolean isCustomBlock(ItemStack itemInHand) {
        return PersistentMetaUtil.containsKey(itemInHand.getItemMeta(), namespacedKey);
    }

    private static boolean containsCustomBlockEntity(Location location) {
        for (Entity entity : location.getWorld().getNearbyEntities(location.getBlock().getBoundingBox())) {
            if (entity instanceof Display display) {
                return PersistentMetaUtil.containsKey(display, namespacedKey);
            }
        }
        return false;
    }

    public static CustomBlock getCustomBlock(Location location) {
        if (!MaterialUtil.isColoredBlock(ColoredMaterialType.STAINED_GLASS, location.getBlock().getType())) {
            return null; //is not stained glass block
        }

        for (Entity entity : location.getWorld().getNearbyEntities(location.getBlock().getBoundingBox())) {
            if (entity instanceof Display display) {
                UUID customBlockId = PersistentMetaUtil.getUUID(display, namespacedKey);
                return registeredBlocks.get(customBlockId);
            }
        }
        return null;
    }

    public static CustomBlock getCustomBlock(ItemStack itemInHand) {
        UUID id = PersistentMetaUtil.getUUID(itemInHand.getItemMeta(), namespacedKey);
        return registeredBlocks.get(id);
    }
}
package com.github.oobila.bukkit.blocks.customblock;

import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Set;


public abstract class HeadBlock extends CustomBlock {

    private final ItemStack head;

    public HeadBlock(Plugin plugin, String name, DisplayItemConfig config, ItemStack head) {
        super(plugin, name, config);
        this.head = head;
    }

    @Override
    public Set<Display> placeDisplays(Player player, Location location) {
        ItemDisplay itemDisplay = getHeadDisplay(config, head, player, location);
        return Set.of(itemDisplay);
    }

    static ItemDisplay getHeadDisplay(DisplayItemConfig config, ItemStack head, Player player, Location location){
        ItemDisplay itemDisplay = (ItemDisplay) location.getWorld().spawnEntity(
                location.clone().add(0.5, 0.75, 0.5),
                EntityType.ITEM_DISPLAY
        );
        itemDisplay.setItemStack(head);
        itemDisplay.setViewRange(.2f);
        if (config.isGlowing()) {
            itemDisplay.setGlowing(true);
            if (config.getGlowColor() != null) {
                itemDisplay.setGlowColorOverride(config.getGlowColor());
            }
        }
        if (config.isBright()) {
            itemDisplay.setBrightness(new Display.Brightness(15, 15));
        }
        if (config.isBillboard()){
            itemDisplay.setRotation(180f, 0f);
            itemDisplay.setBillboard(Display.Billboard.CENTER);
        } else {
            float rotation = 0f;
            if (player.getFacing().getDirection().getX() == 1d) {
                rotation = 270f;
            } else if (player.getFacing().getDirection().getZ() == -1d) {
                rotation = 180f;
            } else if(player.getFacing().getDirection().getX() == -1d) {
                rotation = 90f;
            }
            itemDisplay.setRotation(rotation, 0f);
        }
        return itemDisplay;
    }

}

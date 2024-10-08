package com.github.oobila.bukkit.blocks.customblock;

import com.github.oobila.bukkit.common.utils.MaterialUtil;
import com.github.oobila.bukkit.common.utils.model.ColoredMaterialType;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Transformation;

import java.util.Set;


public abstract class HeadBlock extends CustomBlock {

    private final ItemStack head;

    public HeadBlock(Plugin plugin, String name, DisplayItemConfig config, ItemStack head) {
        super(plugin, name, config);
        this.head = head;
    }

    @Override
    public Set<Display> placeDisplays(Player player, Location location) {
        ItemDisplay itemDisplay = getHeadDisplay(getConfig(), head, player, location, 1);
        return Set.of(itemDisplay);
    }

    static ItemDisplay getHeadDisplay(DisplayItemConfig config, ItemStack head, Player player, Location location, double scale){
        double yOffset;
        if (scale > 5.7 && scale <= 6) {
            scale = 5.7d;
            yOffset = 0.925;
        } else if (scale >= 2) {
            scale = 1.90d;
            yOffset = 0.5 + (scale / 4);
        } else {
            yOffset = 0.5 + (scale / 4);
        }

        ItemDisplay itemDisplay = (ItemDisplay) location.getWorld().spawnEntity(
                location.clone().add(0.5, yOffset, 0.5), //dividing by 4 as the head is 0.5 blocks tall //1 - (scale / 4)
                EntityType.ITEM_DISPLAY
        );
        itemDisplay.setItemStack(head);
        itemDisplay.setViewRange(config.getViewRange());
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
            updateRotation(itemDisplay, player);
        }
        Transformation transformation = itemDisplay.getTransformation();
        transformation.getScale().set(scale);
        itemDisplay.setTransformation(transformation);
        return itemDisplay;
    }

    private static void updateRotation(ItemDisplay itemDisplay, Player player) {
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

}

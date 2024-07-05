package com.github.oobila.bukkit.blocks.customblock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DisplayItemConfig {

    private ItemStack itemStack;
    private Material glassMaterial;
    private boolean isGlowing;
    private Color glowColor;
    private boolean isBright;
    private boolean billboard;
    private float viewRange = 0.1F;

    private void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack.clone();
    }

}

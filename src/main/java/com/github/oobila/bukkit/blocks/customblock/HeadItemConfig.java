package com.github.oobila.bukkit.blocks.customblock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static com.github.oobila.bukkit.itemstack.ItemStackProxy.skull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeadItemConfig {

    private String headTexture;
    private Material blockMaterial;
    private boolean isGlowing;
    private Color glowColor;
    private boolean isBright;
    private boolean billboard;
    private float viewRange = 0.1F;

    public DisplayItemConfig toDisplayItemConfig() {
        return new DisplayItemConfig(
                skull(headTexture).getItemStack(),
                blockMaterial,
                isGlowing,
                glowColor,
                isBright,
                billboard,
                0.1F
        );
    }
}

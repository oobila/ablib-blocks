package com.github.oobila.bukkit.blocks.customblock;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;

public class CustomBlockListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if (!CustomBlock.isCustomBlock(e.getItemInHand())) {
            return;
        }
        e.setCancelled(true);
        CustomBlock customBlock = CustomBlock.getCustomBlock(e.getItemInHand());
        if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            if (e.getHand().equals(EquipmentSlot.HAND)) {
                e.getPlayer().getInventory().getItemInMainHand().setAmount(
                        e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1
                );
            } else if (e.getHand().equals(EquipmentSlot.OFF_HAND)) {
                e.getPlayer().getInventory().getItemInOffHand().setAmount(
                        e.getPlayer().getInventory().getItemInOffHand().getAmount() - 1
                );
            }
        }
        customBlock.blockPlace(e.getPlayer(), e.getBlockPlaced().getLocation());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if (!CustomBlock.isCustomBlock(e.getBlock().getLocation())) {
            return;
        }
        CustomBlock customBlock = CustomBlock.getCustomBlock(e.getBlock().getLocation());
        if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation().add(0.5, 0.5, 0.5), customBlock.getItemStack());
        }
        customBlock.blockBreak(e.getPlayer(), e.getBlock().getLocation());
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e){
        List<Block> blocksToRemove = new ArrayList<>();
        for (Block block : e.blockList()) {
            if(CustomBlock.isCustomBlock(block.getLocation())) {
                blocksToRemove.add(block);
            }
        }
        if (!blocksToRemove.isEmpty()) {
            e.blockList().removeAll(blocksToRemove);
        }
    }

    @EventHandler
    public void onPistonEvent(BlockPistonExtendEvent e) {
        for (Block block : e.getBlocks()) {
            if(CustomBlock.isCustomBlock(block.getLocation())) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onPistonEvent(BlockPistonRetractEvent e) {
        if (CustomBlock.isCustomBlock(e.getBlock().getLocation())) {
            e.setCancelled(true);
        }
    }
}

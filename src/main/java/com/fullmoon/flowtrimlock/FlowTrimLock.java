package com.fullmoon.flowtrimlock;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.SmithItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.SmithingInventory;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.plugin.java.JavaPlugin;

public final class FlowTrimLock extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("FlowTrimLock enabled.");
    }

    @EventHandler
    public void onSmithItem(SmithItemEvent event) {
        if (!(event.getInventory() instanceof SmithingInventory inventory)) return;

        ItemStack equipment = inventory.getInputEquipment();
        if (!isLockedFlowResinArmor(equipment)) return;

        event.setCancelled(true);
        for (HumanEntity viewer : event.getViewers()) {
            viewer.sendMessage("§cLocked armor: Flow trim with Resin material cannot be changed or removed.");
        }
    }

    private boolean isLockedFlowResinArmor(ItemStack item) {
        if (item == null || item.getType().isAir()) return false;
        if (!(item.getItemMeta() instanceof ArmorMeta armorMeta)) return false;

        ArmorTrim trim = armorMeta.getTrim();
        return trim != null
                && trim.getPattern().equals(TrimPattern.FLOW)
                && trim.getMaterial().equals(TrimMaterial.RESIN);
    }
}

package net.pgfmc.balance.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.pgfmc.balance.inventories.TeamBase;

public class InventoryEvents implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e)
	{
		if (!(e.getInventory().getHolder() instanceof TeamBase)) { return; }
		
		
		
		
		e.setCancelled(true);
	}

}

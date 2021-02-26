package tk.pgfriends.balance.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import tk.pgfriends.balance.Main;

public class PlayerEvents implements Listener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e)
	{
		List<ItemStack> drops = e.getDrops();
		ItemStack[] dropsArray = new ItemStack[drops.size()];
		drops.toArray(dropsArray);
		Player player = e.getEntity();
		Location loc = player.getLocation();
		World world = player.getWorld();
		
		for (ItemStack drop : dropsArray)
		{
			Item dropItem = world.dropItemNaturally(loc.clone().add(0.5, 1.2, 0.5), drop);
			dropItem.setOwner(player.getUniqueId());
			// dropItem.setVelocity((new Vector())); // enable if items glitch due to velocity
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
				
				@Override
				public void run()
				{
					if (!dropItem.isValid())
					{
						dropItem.setItemStack(drop);
					}
				}
				
			}, 20 * 60);
		}
		
		e.getDrops().clear();
	}
	
	/*
	@EventHandler
	public void onPlace(BlockPlaceEvent e)
	{
		if (!e.getBlockPlaced().getType().equals(Material.CHEST)) { return; }
		if (!e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) { return; }
		
		Player player = e.getPlayer();
		Chest chest = (Chest) e.getBlockPlaced().getState();
		
		chest.setLock(player.getUniqueId().toString()); // check to see if lock of chest matches uuid of player, if so, remove lock temporarily -- no?
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		
		if (!e.getClickedBlock().getType().equals(Material.CHEST)) { return; }
		if (e.getClickedBlock().getState().equals(null)) { return; }
		Chest chest = (Chest) e.getClickedBlock().getState();
		if (!chest.isLocked()) { return; }
		
		Player player = e.getPlayer();
		String lock = chest.getLock();
		
		
		if (lock.equals(player.getUniqueId().toString()))
		{
			player.sendMessage("test");// player.openInventory(chest.getInventory()); // get the inventory and open it?
		}
	}
	*/

}

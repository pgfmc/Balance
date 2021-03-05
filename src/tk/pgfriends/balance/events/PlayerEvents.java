package tk.pgfriends.balance.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
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
	public void onOpenChest(InventoryOpenEvent e)
	{
		if (!(e.getInventory().getHolder() instanceof Chest)) { return; }
		
		Location loc = e.getInventory().getLocation();
		
		for (int x = -1; x < 2; x++)
		{
			for (int z = -1; z < 2; z++)
			{
				Location signLoc = new Location(loc.getWorld(), loc.getX() + x, loc.getY(), loc.getZ() + z);
				
				if (!signLoc.getBlock().getType().equals(Material.OAK_WALL_SIGN)) { continue; }
				
				Directional di = (Directional) signLoc.getBlock().getBlockData();
				BlockFace blockFace = di.getFacing();
				
				if (x == 0 && z == -1)
				{
					if (blockFace != BlockFace.NORTH) { continue; }
				}
				
				if (x == 0 && z == 1)
				{
					if (blockFace != BlockFace.SOUTH) { continue; }
				}
				
				if (x == -1 && z == 0)
				{
					if (blockFace != BlockFace.WEST) { continue; }
				}
					
				if (x == 1 && z == 0)
				{
					if (blockFace != BlockFace.EAST) { continue; }
				}
				
				if (x == z || x == z * -1) { continue; } // This would be NORTHEAST, SOUTHWEST, etc. (We only want to check the 4 faces of the chest, not the corners)
				
				
				
				if (signLoc.getBlock().getType().equals(Material.OAK_WALL_SIGN))
				{
					Sign sign = (Sign) signLoc.getBlock().getState();
					
					for (String line : sign.getLines())
					{
						if (e.getPlayer().getName().toLowerCase().equals(line.toLowerCase()))
						{
							return;
						}
					}
				}
			}
		}
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		if (e.getClickedBlock() == null) { return; }
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

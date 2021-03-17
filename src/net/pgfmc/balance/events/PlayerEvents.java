package net.pgfmc.balance.events;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.pgfmc.balance.Database;
import net.pgfmc.balance.Main;

public class PlayerEvents implements Listener {
	
	File file = new File(Main.plugin.getDataFolder() + File.separator + "database.yml"); // Creates a File object
	FileConfiguration database = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML and loads data
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e)
	{
		List<ItemStack> drops = e.getDrops();
		ItemStack[] dropsArray = new ItemStack[drops.size()];
		drops.toArray(dropsArray);
		
		Player p = e.getEntity();
		Location loc = p.getLocation();
		World world = p.getWorld();
		
		for (ItemStack drop : dropsArray)
		{
			Item dropItem = world.dropItemNaturally(loc.clone().add(0, 0.5, 0), drop);
			dropItem.setOwner(p.getUniqueId());
			dropItem.setGlowing(true);
			dropItem.setInvulnerable(true);
			dropItem.setVelocity((new Vector())); // enable if items glitch due to velocity
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
				
				@Override
				public void run()
				{
					if (!dropItem.isValid())
					{
						dropItem.setItemStack(drop);
					}
				}
				
			}, 20 * 120);
		}
		
		e.getDrops().clear();
		p.sendMessage("§cYour dropped items are protected for 120 seconds.");
		p.sendMessage("§c§o/back to return to your items.");
	}
	
	
	@EventHandler
	public void onOpenChest(InventoryOpenEvent e)
	{
		if (!(e.getInventory().getHolder() instanceof Chest)) { return; }
		
		Location loc = e.getInventory().getLocation();
		
		if (!Database.isLocked(loc, database, file)) { return; }
		if (Database.getOwner(loc, database, file).getUniqueId().equals(e.getPlayer().getUniqueId())) { return; }
		
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
	public void onChestPlace(BlockPlaceEvent e)
	{
		
		if (!e.getBlock().getType().equals(Material.CHEST)) { return; }
		
		Location loc = e.getBlock().getLocation();
		Database.save(e.getPlayer(), loc, database, file);
	}
	
	public void onSignPlace(BlockPlaceEvent e)
	{
		if (!e.getBlock().getType().equals(Material.OAK_WALL_SIGN)) { return; }
		
		Directional di = (Directional) e.getBlock().getBlockData();
		BlockFace blockFace = di.getFacing();
		
		Location loc = e.getBlock().getLocation();
		
		if (blockFace == BlockFace.NORTH)
		{
			loc.setZ(loc.getZ() + 1.0);
			
			if (loc.getBlock().getType().equals(Material.CHEST))
			{
				if (Database.getOwner(loc, database, file) != null)
				{
					if (!Database.getOwner(loc, database, file).getUniqueId().equals(e.getPlayer().getUniqueId()))
					{
						e.getPlayer().sendMessage("§c§oYou cannot alter " + Database.getOwner(loc, database, file).getName() + "'s chest!");
						e.setCancelled(true);
					}
					
					return;
				}
			}
		}
		
		if (blockFace == BlockFace.SOUTH)
		{
			loc.setZ(loc.getZ() - 1.0);
			
			if (loc.getBlock().getType().equals(Material.CHEST))
			{
				if (Database.getOwner(loc, database, file) != null)
				{
					if (!Database.getOwner(loc, database, file).getUniqueId().equals(e.getPlayer().getUniqueId()))
					{
						e.getPlayer().sendMessage("§c§oYou cannot alter " + Database.getOwner(loc, database, file).getName() + "'s chest!");
						e.setCancelled(true);
					}
					
					return;
				}
			}

		}
		
		if (blockFace == BlockFace.EAST)
		{
			loc.setZ(loc.getX() - 1.0);
			
			if (loc.getBlock().getType().equals(Material.CHEST))
			{
				if (Database.getOwner(loc, database, file) != null)
				{
					if (!Database.getOwner(loc, database, file).getUniqueId().equals(e.getPlayer().getUniqueId()))
					{
						e.getPlayer().sendMessage("§c§oYou cannot alter " + Database.getOwner(loc, database, file).getName() + "'s chest!");
						e.setCancelled(true);
					}
					
					return;
				}
			}

		}
		
		if (blockFace == BlockFace.WEST)
		{
			loc.setZ(loc.getX() + 1.0);
			
			if (loc.getBlock().getType().equals(Material.CHEST))
			{
				if (Database.getOwner(loc, database, file) != null)
				{
					if (!Database.getOwner(loc, database, file).getUniqueId().equals(e.getPlayer().getUniqueId()))
					{
						e.getPlayer().sendMessage("§c§oYou cannot alter " + Database.getOwner(loc, database, file).getName() + "'s chest!");
						e.setCancelled(true);
					}
					
					return;
				}
			}

		}
	}
	

}

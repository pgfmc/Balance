package net.pgfmc.balance.events;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Barrel;
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Block;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.block.ShulkerBox;
import org.bukkit.block.Smoker;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.ess3.api.IEssentials;
import net.pgfmc.balance.Database;
import net.pgfmc.balance.Main;

public class PlayerEvents implements Listener {
	
	IEssentials ess = (IEssentials) Bukkit.getPluginManager().getPlugin("Essentials"); // loads the essentials plugin
	
	File file = new File(Main.plugin.getDataFolder() + File.separator + "database.yml"); // Creates a File object
	FileConfiguration database = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML and loads data
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) // makes items dropped on death invulnerable and gives them a glowing effect
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
						dropItem.setGlowing(false);
						dropItem.setInvulnerable(false);
					}
				}
				
			}, 2400); // two minutes
		}
		
		e.getDrops().clear();
		p.sendMessage("§cYour dropped items are protected for 120 seconds.");
	}
	
	

	@EventHandler
	public void onChestOpen(InventoryOpenEvent e)
	{
		// return; if the inventory doesn't belong to a Container I guess
		if (!(e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof Barrel || e.getInventory().getHolder() instanceof BlastFurnace || e.getInventory().getHolder() instanceof BrewingStand || e.getInventory().getHolder() instanceof Dispenser || e.getInventory().getHolder() instanceof Dropper || e.getInventory().getHolder() instanceof Furnace || e.getInventory().getHolder() instanceof Hopper || e.getInventory().getHolder() instanceof ShulkerBox || e.getInventory().getHolder() instanceof Smoker)) { return; }
		
		Player p = (Player) e.getPlayer(); // Player who opened the container
		Location loc = e.getInventory().getLocation(); // Location of the container
		Block b = loc.getBlock(); // Block of the container
		
		if (Database.isLocked(b))
		{
			
		}
	}
	
	
	/* Commenting out to save for later if I ever need in case I fricked up
	@EventHandler
	public void onOpenChest(InventoryOpenEvent e) // code for locked chests
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
	public void onChestPlace(BlockPlaceEvent e) // records the location of chests
												// also putting these two events (onChestPlace and onSignPlace) in the same function would take a lot less processing power i think :0
	{
		
		if (!e.getBlock().getType().equals(Material.CHEST)) { return; }
		
		Location loc = e.getBlock().getLocation();
		Database.save(e.getPlayer(), loc, database, file);
	}
	
	
	
	
	public void onSignPlace(BlockPlaceEvent e) // i literally dont know
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
	
	*/
	
	
	
	//AFK functionality below
	@EventHandler
	public void hitProtect(EntityDamageEvent e) { // cancels damage event if the player is AFK
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			if (ess.getUser(player).isAfk()) {
				
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void deAggro(EntityTargetLivingEntityEvent e) { // -------------- disables aggro if a mob targets an AFK player
	
		if (e.getTarget() instanceof Player && e.getEntity() instanceof Monster) {
			
			Player player = (Player) e.getTarget();
			if (ess.getUser(player).isAfk()) {
				
				e.setCancelled(true);
			}
		}
	}
}



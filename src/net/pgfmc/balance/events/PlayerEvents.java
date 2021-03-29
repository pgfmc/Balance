package net.pgfmc.balance.events;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.ess3.api.IEssentials;
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



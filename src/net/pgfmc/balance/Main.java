package net.pgfmc.balance;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Main extends JavaPlugin implements Listener {
	
	public static Main plugin;
	
	@Override
	public void onEnable()
	{
		plugin = this;
		getServer().getPluginManager().registerEvents(new Main(), this);
	}
	
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
			dropItem.setVelocity((new Vector()));
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				
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
	

}

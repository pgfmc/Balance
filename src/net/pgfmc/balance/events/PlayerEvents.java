package net.pgfmc.balance.events;

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
import org.bukkit.util.Vector;

import net.pgfmc.balance.Main;

public class PlayerEvents implements Listener {

	
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
}



package net.pgfmc.balance.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityEvents implements Listener {
	
	@EventHandler
	public void onExplode(EntityExplodeEvent e)
	{
		
		Location loc = e.getLocation();
		int r = 25; // Radius to check for
		List<Material> mat = new ArrayList<Material>(); // Blocks to check for
		
	        List<Block> blocks = new ArrayList<Block>();
	        
	        for (int x = loc.getBlockX() - r; x <= loc.getBlockX() + r; x++)
	        {
	            for (int y = loc.getBlockY() - r; y <= loc.getBlockY() + r; y++)
	            {
	                for (int z = loc.getBlockZ() - r; z <= loc.getBlockZ() + r; z++)
	                {
	                   blocks.add(loc.getWorld().getBlockAt(x, y, z));
	                }
	            }
	        }
	    
	    for (Block block : blocks)
	    {
	    	for (Material m : mat)
	    	{
	    		if (block.getType().equals(m))
	    		{
	    			e.setCancelled(true);
	    			break; // return; might also work here
	    		}
	    	}
	    	
	    }
	}

}

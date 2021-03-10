package net.pgfmc.balance.events;

import java.util.ArrayList;
import java.util.Arrays;
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
		List<Material> mat = Arrays.asList(Material.LAPIS_BLOCK, Material.GOLD_BLOCK, Material.IRON_BLOCK, Material.COAL_BLOCK, Material.OBSIDIAN, Material.CRYING_OBSIDIAN, Material.DIAMOND_BLOCK, Material.EMERALD_BLOCK, Material.BEACON, Material.ENCHANTING_TABLE, Material.ANVIL, Material.REDSTONE_BLOCK, Material.QUARTZ_BLOCK, Material.PACKED_ICE, Material.BLUE_ICE, Material.CONDUIT, Material.NETHERITE_BLOCK, Material.LODESTONE, Material.RESPAWN_ANCHOR, Material.SKELETON_SKULL, Material.SKELETON_WALL_SKULL, Material.WITHER_SKELETON_SKULL, Material.WITHER_SKELETON_WALL_SKULL, Material.CREEPER_HEAD, Material.CREEPER_WALL_HEAD, Material.DRAGON_HEAD, Material.DRAGON_WALL_HEAD, Material.ZOMBIE_HEAD, Material.ZOMBIE_WALL_HEAD, Material.COAL_BLOCK, Material.ENDER_CHEST); // Material of blocks to check for
		List<Block> blocks = new ArrayList<Block>(); // List to add all blocks to in radius of entity explosion
	        
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
	    
	        
	    for (Block block : blocks) // For every Block in List blocks
	    {
	    	for (Material m : mat) // For every Material in List mat
	    	{
	    		if (block.getType().equals(m)) // If the type of block equals one of that we're checking for
	    		{
	    			e.setCancelled(true);
	    			return;
	    		}
	    	}
	    	
	    }
	}

}

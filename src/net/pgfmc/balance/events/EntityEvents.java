package net.pgfmc.balance.events;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityEvents implements Listener {
	
	@EventHandler
	public void onExplode(EntityExplodeEvent e)
	{
		
		Location loc = e.getLocation();
		int r = 10; // Radius to check for - should be the max radius for protected land
		
		List<Material> mat = Arrays.asList(Material.LAPIS_BLOCK, Material.GOLD_BLOCK, Material.IRON_BLOCK, Material.COAL_BLOCK, Material.CRYING_OBSIDIAN, Material.DIAMOND_BLOCK, Material.EMERALD_BLOCK, Material.BEACON, Material.ENCHANTING_TABLE, Material.ANVIL, Material.REDSTONE_BLOCK, Material.QUARTZ_BLOCK, Material.PACKED_ICE, Material.BLUE_ICE, Material.CONDUIT, Material.NETHERITE_BLOCK, Material.LODESTONE, Material.RESPAWN_ANCHOR, Material.SKELETON_SKULL, Material.SKELETON_WALL_SKULL, Material.WITHER_SKELETON_SKULL, Material.WITHER_SKELETON_WALL_SKULL, Material.CREEPER_HEAD, Material.CREEPER_WALL_HEAD, Material.DRAGON_HEAD, Material.DRAGON_WALL_HEAD, Material.ZOMBIE_HEAD, Material.ZOMBIE_WALL_HEAD, Material.ENDER_CHEST); // Material of blocks to check for
		// List<Double> wealthPercent = Arrays.asList(0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.500, .50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50);
		// List<Block> blocks = new ArrayList<>(); // List to add all blocks to in radius of entity explosion
	        
        for (int x = loc.getBlockX() - r; x <= loc.getBlockX() + r; x++)
        {
            for (int y = loc.getBlockY() - r; y <= loc.getBlockY() + r; y++)
            {
                for (int z = loc.getBlockZ() - r; z <= loc.getBlockZ() + r; z++)
                {
                	if (mat.contains(loc.getWorld().getBlockAt(x, y, z).getType()))
                	{
                		e.setCancelled(true);
                		return;
                		// blocks.add(loc.getWorld().getBlockAt(x, y, z));
                	}
                }
            }
        }
        
        
        
        
        /*
        int rBlock = 10; // How close should the wealth be to each other?
        List<Block> protBlocks = new ArrayList<>(); // List to add all blocks to in radius of entity explosion
        List<Object> radiusHolder = new ArrayList<>();
        List<List<Object>> totalRadiusHolder = new ArrayList<>(); // Holds lists of information for each block checked - used later to determine the max totalRadius to determine if entity should explode
        
        Location blockLoc = null;
        Location bLoc = null;
        int n = 0;
        int totalRadius = 0;
        double wealth = 0.0;
        int finalRadius = 0;
        
        for (Block block : blocks)
        {
        	blockLoc = block.getLocation();
        	
        	for (Block b : blocks)
        	{
        		bLoc = b.getLocation();
        		
                for (int x = blockLoc.getBlockX() - rBlock; x <= blockLoc.getBlockX() + rBlock; x++)
                {
                    for (int y = blockLoc.getBlockY() - rBlock; y <= blockLoc.getBlockY() + rBlock; y++)
                    {
                        for (int z = blockLoc.getBlockZ() - rBlock; z <= blockLoc.getBlockZ() + rBlock; z++)
                        {
                        	if (mat.contains(loc.getWorld().getBlockAt(x, y, z).getType()))
                        	{
                        		protBlocks.add(loc.getWorld().getBlockAt(x, y, z));
                        	}
                        }
                    }
                }
                
                for (Material m : mat)
                {
                    for (Block pb : protBlocks)
                    {
                    	if (pb.getType().equals(m))
                    	{
                    		n++;
                    	}
                    }
                    
                	wealth = wealthPercent.get(mat.indexOf(m));
                	totalRadius += (wealth * 10) * (Math.sqrt(n * wealth));
                	n = 0;
                }
                
                System.out.println(totalRadius); // debug

                
                // Save the variables before reset
                radiusHolder.add(b);
                radiusHolder.add(totalRadius);
                totalRadiusHolder.add(radiusHolder);
                
                // Reset the variables for next use
                totalRadius = 0;
                protBlocks.clear();
                radiusHolder.clear();
                wealth = 0.0;
                n = 0;
        	}
        	
        	finalRadius = (int) totalRadiusHolder.get(blocks.indexOf(block)).get(1);
        	
            for (int x = loc.getBlockX() - finalRadius; x <= loc.getBlockX() + finalRadius; x++)
            {
                for (int y = loc.getBlockY() - finalRadius; y <= loc.getBlockY() + finalRadius; y++)
                {
                    for (int z = loc.getBlockZ() - finalRadius; z <= loc.getBlockZ() + finalRadius; z++)
                    {
                    	if (mat.contains(loc.getWorld().getBlockAt(x, y, z).getType()))
                    	{
                    		e.setCancelled(true);
                    		return;
                    	}
                    }
                }
            }
            
        	
        }
        
        

        
        
        */
	}

}

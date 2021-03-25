package net.pgfmc.balance;

import org.bukkit.block.Block;

public class Database { // JUST USE COREPROTECT ! ! ! ! ! ! how
	
	public static boolean isLocked(Block b)
	{
		return false;
	}
	
	public static void saveLock(Block b, Team t)
	{
		
	}
	
	
	
	/* Commented out in case I ever want to revert back to how I used to do it
	public static void save(Player owner, Location loc, FileConfiguration db, File file)
	{
		
		db.set(loc.toString() + ".isLocked", false);
		db.set(loc.toString() + ".owner", owner.getUniqueId().toString());
		
		try {
			db.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static boolean isLocked(Location loc, FileConfiguration db, File file)
	{
		return (boolean) (db.get(loc.toString() + ".isLocked"));
	}
	
	public static Player getOwner(Location loc, FileConfiguration db, File file)
	{
		return Bukkit.getPlayer(UUID.fromString(db.getString(loc.toString() + ".owner")));
	}
	
	*/


}

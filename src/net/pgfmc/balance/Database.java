package net.pgfmc.balance;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import sun.jvm.hotspot.debugger.win32.coff.COFFException;

public class Database { // JUST USE COREPROTECT ! ! ! ! ! ! how
	
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


}

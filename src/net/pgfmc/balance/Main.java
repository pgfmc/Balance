package net.pgfmc.balance;

import org.bukkit.plugin.java.JavaPlugin;

<<<<<<< HEAD
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;

import net.pgfmc.teams;

import net.pgfmc.balance.commands.Team;

import net.pgfmc.balance.commands.Team;
=======
>>>>>>> d07da1bca10714e99b5e22ef2dea15e4a15769aa
import net.pgfmc.balance.events.EntityEvents;
import net.pgfmc.balance.events.PlayerEvents;

public class Main extends JavaPlugin { // MAIN // initializes plugin
	
	public static Main plugin;
	
	// File file = new File(getDataFolder() + File.separator + "database.yml"); // Creates a File object
	// FileConfiguration database = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML and loads data
	
	@Override
	public void onEnable()
	{
		plugin = this;
		getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
		getServer().getPluginManager().registerEvents(new EntityEvents(), this);
		
	}
	


}

package net.pgfmc.balance;

import org.bukkit.plugin.java.JavaPlugin;

import net.pgfmc.balance.commands.Team;
import net.pgfmc.balance.events.EntityEvents;
import net.pgfmc.balance.events.PlayerEvents;

public class Main extends JavaPlugin {
	
	public static Main plugin;
	
	// File file = new File(getDataFolder() + File.separator + "database.yml"); // Creates a File object
	// FileConfiguration database = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML and loads data
	
	@Override
	public void onEnable()
	{
		plugin = this;
		getCommand("team").setExecutor(new Team());
		getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
		getServer().getPluginManager().registerEvents(new EntityEvents(), this);
		
	}

}

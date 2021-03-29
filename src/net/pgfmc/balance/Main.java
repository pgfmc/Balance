package net.pgfmc.balance;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;

import net.pgfmc.teams;

import net.pgfmc.balance.commands.Team;

import net.pgfmc.balance.commands.Team;
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
		getCommand("team").setExecutor(new Team());
		getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
		getServer().getPluginManager().registerEvents(new EntityEvents(), this);
		
	}
	
	public static CoreProtectAPI getCoreProtect() {
		
        Plugin plugin = Main.plugin.getServer().getPluginManager().getPlugin("CoreProtect");
     
        // Check that CoreProtect is loaded
        if (plugin == null || !(plugin instanceof CoreProtect)) { return null; }

        // Check that the API is enabled
        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
        if (CoreProtect.isEnabled() == false) { return null; }

        // Check that a compatible version of the API is loaded
        if (CoreProtect.APIVersion() < 6) { return null; }

        return CoreProtect;
	}
	
	public static Teams getTeamsPlugin() { // Lets us use the CoreProtectAPI thing :)
		
        Plugin plugin = Main.plugin.getServer().getPluginManager().getPlugin("Teams");
     
        // Check that CoreProtect is loaded
        if (plugin == null || !(plugin instanceof Teams)) { return null; }

        // Check that the API is enabled
        Teams t = ((Teams) plugin);

        return t;
	}

}

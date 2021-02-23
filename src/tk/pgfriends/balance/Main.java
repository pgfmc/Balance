package tk.pgfriends.balance;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import tk.pgfriends.balance.events.EntityEvents;
import tk.pgfriends.balance.events.PlayerEvents;

public class Main extends JavaPlugin {
	
	public static Plugin plugin;
	
	@Override
	public void onEnable()
	{
		plugin = this;
		getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
		getServer().getPluginManager().registerEvents(new EntityEvents(), this);
		
	}

}

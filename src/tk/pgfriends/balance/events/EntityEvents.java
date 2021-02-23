package tk.pgfriends.balance.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityEvents implements Listener {
	
	@EventHandler
	public void onExplode(EntityExplodeEvent e)
	{
		e.setCancelled(true);
	}

}

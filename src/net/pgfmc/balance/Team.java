package net.pgfmc.balance;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Team {
	
	UUID owner;
	List<UUID> members;
	List<Team> allies = null; // Defaults to null if no allies
	
	public Team(UUID owner, List<UUID> members, List<Team> allies)
	{
		this.members = members;
		this.allies = allies;
	}
	
	
	
	public Team(UUID owner, List<UUID> members)
	{
		this.owner = owner;
		this.members = members;
	}
	
	
	
	
	
	
	
	
	public List<UUID> getMembers()
	{
		return members;
	}
	
	
	
	public Player getOwner()
	{
		return Bukkit.getPlayer(owner);
	}
	
	
	
	public boolean kickMember(Player p)
	{
		if (p.getUniqueId().equals(owner)) { return false; } // You cannot remove the owner!
		
		members.remove(p.getUniqueId());
		return true;
	}
	
	public boolean addMember(Player p)
	{
		if (members.contains(p.getUniqueId())) { return false; } // You cannot add an existing member to the team
		
		members.add(p.getUniqueId());
		return true;
	}

}

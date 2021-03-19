package net.pgfmc.balance.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class TeamBase implements InventoryHolder {
	
	private Inventory inv;
	
	public TeamBase()
	{
		inv = Bukkit.createInventory(this, 9, "Team Management"); // Initiates the declared Inventory object
		init(); // Build the inventory
	}
	
	public void init()
	{
		inv.setItem(0, createItem("Info", Material.OAK_SIGN));
		
		inv.setItem(2, createItem(""), Material.AIR
				???6);
		inv.setItem(3, createItem(""), Material.AIR);
		inv.setItem(5, createItem(""));
		inv.setItem(7, createItem(""));
		inv.setItem(8, createItem(""));
	}
	
	public ItemStack createItem(String name, Material mat)
	{
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
	@Override
	public Inventory getInventory() { return inv; }
	
	

}

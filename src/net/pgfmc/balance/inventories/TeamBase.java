package net.pgfmc.balance.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamBase implements InventoryHolder {
	
	private Inventory inv;
	
	public TeamBase()
	{
		inv = Bukkit.createInventory(this, 9, "Team Management"); // Initiates the declared Inventory object
		init(); // Build the inventory
	}
	
	public void init()
	{
		ItemStack oakSign = new ItemStack(Material.OAK_SIGN, 1); // edited by Crimson -------- !
		ItemMeta okesin = oakSign.getItemMeta();
		okesin.setDisplayName("Info");
		oakSign.setItemMeta(okesin);
		
		inv.setItem(0, oakSign);
		
		ItemStack AIRgamg = new ItemStack(Material.AIR, 1);
		ItemMeta AIRgaming = AIRgamg.getItemMeta();
		AIRgaming.setDisplayName("");
		AIRgamg.setItemMeta(AIRgaming);
		
		inv.setItem(2, AIRgamg);
		inv.setItem(3, AIRgamg);
		inv.setItem(5, AIRgamg);
		inv.setItem(7, AIRgamg);
		inv.setItem(8, AIRgamg);
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
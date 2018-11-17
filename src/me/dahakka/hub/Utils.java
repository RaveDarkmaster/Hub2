package me.dahakka.hub;

import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils
{
  public static void fillAirWithItem(Inventory inventory, DyeColor color, int start, int end)
  {
    for (int i = start; i < end + 1; i++) {
      if (inventory.getItem(i) == null) {
        inventory.setItem(i, getStackColor(color));
      }
    } 
  }
  
  public static ItemStack getStackColor(DyeColor color)
  {
    ItemStack pane = 
      createItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)color.getData()), 
      ChatColor.RED + "Welcome To ReignCraft", 
      Arrays.asList(new String[] { ChatColor.DARK_RED + "Visit our shop: " + ChatColor.UNDERLINE + "www.reigncrafted.enjin.com/shop" }));
    return pane;
  }
  
  public static ItemStack createItem(ItemStack material, String name, List<String> lore)
  {
    if ((material == null) || (material.getType() == Material.AIR) || ((name == null) && (lore == null))) {
      return null;
    }
    ItemMeta im = material.getItemMeta();
    if (name != null) {
      im.setDisplayName(name);
    }
    if (lore != null) {
      im.setLore(lore);
    }
    material.setItemMeta(im);
    return material;
  }
}
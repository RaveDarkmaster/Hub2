package me.dahakka.hub;

import java.util.Arrays;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class InventoryManager
  implements Runnable
{
  public static Inventory i = Bukkit.getServer().createInventory(null, InventoryType.CHEST, ChatColor.RED + "Welcome To " + ChatColor.LIGHT_PURPLE + ChatColor.UNDERLINE + "ReignCraft");
  public static Inventory SkyBlock = Bukkit.getServer().createInventory(null, InventoryType.CHEST, ChatColor.RED + "Shops");
  public Main plugin;
  
  public InventoryManager(Main plugin)
  {
    this.plugin = plugin;
  }
  
  public void run()
  {
    Inventory inv = i;
    setItems(inv);
    if (Bukkit.getServerName().contains("SkyBlock")) {
      setSecond(SkyBlock);
    }
  }
  
  public int task = 0;
  public int in = 0;
  
  public void setItems(final Inventory inv)
  {
    this.task = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, 
    
      (Runnable)new BukkitRunnable()
      {
        public void run()
        {
          InventoryManager.this.in += 1;
          if (InventoryManager.this.in <= InventoryManager.i.getSize() - 1)
          {
            if (InventoryManager.this.in >= InventoryManager.i.getSize() - 1) {
              InventoryManager.this.in = 0;
            }
            if ((inv.getItem(InventoryManager.this.in) == null) || (inv.getItem(InventoryManager.this.in).getType() == Material.AIR))
            {
              InventoryManager.this.FirstRun(inv);
            }
            else if (inv.getItem(InventoryManager.this.in).getType() != Material.STAINED_GLASS_PANE)
            {
              if (inv.getItem(InventoryManager.this.in).getType() == Material.CHAINMAIL_CHESTPLATE) {
                InventoryManager.this.in = 0;
              }
            }
            else
            {
              ItemStack item = Utils.getStackColor(InventoryManager.getRandomColor());
              inv.setItem(InventoryManager.this.in, Utils.createItem(new ItemStack(item), ChatColor.RED + "Welcome to Reigncraft", Arrays.asList(new String[] { ChatColor.GRAY + "Visit our site: www.reigncrafted.enjin.com" })));
            }
          }
          else
          {
            Bukkit.getServer().getScheduler().cancelTask(InventoryManager.this.task);
          }
        }
      }.runTaskLater(this.plugin, 1L));
  }
  
  public int sn = 0;
  private static DyeColor[] colours;
  
  public void setSecond(final Inventory inv)
  {
    this.task = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, 
    
      (Runnable)new BukkitRunnable()
      {
        public void run()
        {
          InventoryManager.this.sn += 1;
          if (InventoryManager.this.sn <= InventoryManager.SkyBlock.getSize() + 1)
          {
            if (InventoryManager.this.sn >= InventoryManager.SkyBlock.getSize()) {
              InventoryManager.this.sn = 0;
            }
            if ((InventoryManager.SkyBlock.getItem(InventoryManager.this.sn) == null) || (InventoryManager.SkyBlock.getItem(InventoryManager.this.sn).getType() == Material.AIR))
            {
              InventoryManager.this.SecondRun(InventoryManager.SkyBlock);
            }
            else if (InventoryManager.SkyBlock.getItem(InventoryManager.this.sn).getType() == Material.STAINED_GLASS_PANE)
            {
              ItemStack item = Utils.getStackColor(InventoryManager.getRandomColor());
              inv.setItem(InventoryManager.this.sn, Utils.createItem(new ItemStack(item), ChatColor.RED + "Welcome to Reigncraft", Arrays.asList(new String[] { ChatColor.GRAY + "Visit our site: www.reigncrafted.enjin.com" })));
            }
          }
          else
          {
            Bukkit.getServer().getScheduler().cancelTask(InventoryManager.this.task);
          }
        }
      }.runTaskLater(this.plugin, 1L));
  }
  
  public static DyeColor getRandomColor()
  {
    
    try
    {
      DyeColor[] colors = colours;
      if (colours != null)
      {
        int randomColor = new Random().nextInt(colours.length);
        return colors[randomColor];
      }
    }
    catch (Exception e)
    {
      Log.info(new Object[] {e.getCause() });
    }
    return DyeColor.BLACK;
  }
  
  public static void Colors()
  {
    colours = 
      new DyeColor[] {
      DyeColor.BLUE, 
      DyeColor.RED, 
      DyeColor.CYAN, 
      DyeColor.MAGENTA, 
      DyeColor.LIME, 
      DyeColor.PINK, 
      DyeColor.PURPLE, 
      DyeColor.YELLOW, 
      DyeColor.ORANGE, 
      DyeColor.LIGHT_BLUE };
  }
  
  private void SecondRun(Inventory inv)
  {
    Utils.fillAirWithItem(inv, DyeColor.BLACK, 0, inv.getSize() - 1);
    inv.setItem(11, Utils.createItem(new ItemStack(Material.DIAMOND_BLOCK), 
      ChatColor.AQUA + "Blocks Shop", Arrays.asList(new String[] { ChatColor.LIGHT_PURPLE + "Blocky things" })));
    inv.setItem(15, Utils.createItem(new ItemStack(Material.ENDER_PEARL), 
      ChatColor.GOLD + "Item Shop", Arrays.asList(new String[] { ChatColor.LIGHT_PURPLE + "Stuff..." })));
  }
  
  private void FirstRun(Inventory inv)
  {
    Log.info(
    
      new Object[] { "First Run" });Utils.fillAirWithItem(inv, DyeColor.BLACK, 0, inv.getSize() - 1);inv.setItem(4, Utils.createItem(new ItemStack(Material.TNT), ChatColor.RED + "Factions", Arrays.asList(new String[] { ChatColor.LIGHT_PURPLE + "Needs Staff!" })));inv.setItem(9, Utils.createItem(new ItemStack(Material.STICK), ChatColor.LIGHT_PURPLE + "Velaria", Arrays.asList(new String[] { ChatColor.LIGHT_PURPLE + "Coming Soon" })));inv.setItem(11, Utils.createItem(new ItemStack(Material.IRON_FENCE), ChatColor.AQUA + "Prison", Arrays.asList(new String[] { ChatColor.RED + "Op Prison." })));inv.setItem(15, Utils.createItem(new ItemStack(Material.BLAZE_POWDER), ChatColor.DARK_RED + "MagnumCraft", Arrays.asList(new String[] { ChatColor.DARK_RED + "Ultra Hardcore Bending." })));inv.setItem(17, Utils.createItem(new ItemStack(Material.GRASS), ChatColor.GREEN + "Skyblock", Arrays.asList(new String[] { ChatColor.AQUA + "Razberry's Skyblock." })));inv.setItem(22, Utils.createItem(new ItemStack(Material.BEACON), ChatColor.GOLD + "Creative", Arrays.asList(new String[] { ChatColor.LIGHT_PURPLE + "NEW " + ChatColor.GOLD + "Build with friends or marry someone!" })));inv.setItem(26, Utils.createItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE), ChatColor.DARK_GREEN + "KitPVP", Arrays.asList(new String[] { ChatColor.LIGHT_PURPLE + "Yet To Be Announced." })));inv.setItem(24, Utils.createItem(new ItemStack(Material.GOLD_BLOCK), ChatColor.DARK_GREEN + "Shop", Arrays.asList(new String[] { ChatColor.RED + "http://reigncrafted.enjin.com/shop" })));
  }
}

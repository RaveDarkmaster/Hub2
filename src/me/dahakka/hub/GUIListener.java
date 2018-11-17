package me.dahakka.hub;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIListener
  implements Listener
{
  private Main pl;
  
  public GUIListener(Main plugin)
  {
    this.pl = plugin;
  }
  
  @EventHandler
  public void onClick(InventoryClickEvent e)
  {
    Player p = (Player)e.getWhoClicked();
    ItemStack clicked = e.getCurrentItem();
    Inventory inv = e.getInventory();
    if ((inv.getSize() == InventoryManager.i.getSize()) && (inv.getTitle() == InventoryManager.i.getTitle()))
    {
      e.setCancelled(true);
      if (clicked != null) {
        if ((clicked.getType() != Material.STAINED_GLASS_PANE) && (clicked.getType() != Material.AIR) && (clicked.getType() != Material.GOLD_BLOCK))
        {
          String name = ChatColor.stripColor(clicked.getItemMeta().getDisplayName());
          if ((name != "Creative") && (clicked.getType() != Material.BEACON))
          {
            p.closeInventory();
            new Connect(this.pl, p, name);
          }
          else
          {
            String server = Bukkit.getServerName();
            if (server.contains("Hub"))
            {
              p.getServer().dispatchCommand(p.getServer().getConsoleSender(), "sudo " + p.getName() + " warp plots");
            }
            else
            {
              new Connect(this.pl, p, "Hub");
              p.getServer().dispatchCommand(p.getServer().getConsoleSender(), "sudo " + p.getName() + " warp plots");
            }
          }
        }
        else if (clicked.getType() == Material.GOLD_BLOCK)
        {
          if ((Bukkit.getServerName().contains("SkyBlock")) && (!Bukkit.getServerName().contains("Hub")))
          {
            p.openInventory(InventoryManager.SkyBlock);
            e.setCancelled(true);
          }
          else if (!Bukkit.getServerName().contains("Hub"))
          {
            p.sendMessage(ChatColor.RED + "http://reigncrafted.enjin.com/shop");
            p.closeInventory();
            p.getServer().dispatchCommand(p.getServer().getConsoleSender(), "sudo " + p.getName() + " warp shop");
          }
          else
          {
            p.sendMessage(ChatColor.RED + "http://reigncrafted.enjin.com/shop");
            p.closeInventory();
          }
        }
      }
    }
    else if ((inv.getSize() == InventoryManager.SkyBlock.getSize()) && (inv.getTitle() == InventoryManager.SkyBlock.getTitle()))
    {
      e.setCancelled(true);
      if (clicked.getType() == Material.DIAMOND_BLOCK)
      {
        p.closeInventory();
        p.sendMessage(ChatColor.RED + "http://reigncrafted.enjin.com/shop");
        p.getServer().dispatchCommand(p.getServer().getConsoleSender(), "sudo " + p.getName() + " warp BlockShop");
      }
      else if (clicked.getType() == Material.ENDER_PEARL)
      {
        p.closeInventory();
        p.sendMessage(ChatColor.RED + "http://reigncrafted.enjin.com/shop");
        p.getServer().dispatchCommand(p.getServer().getConsoleSender(), "sudo " + p.getName() + " warp ItemShop");
      }
    }
  }
  
  @EventHandler
  public void onBlockChange(BlockFromToEvent e)
  {
    if (Bukkit.getServerName().contains("SkyBlock"))
    {
      int id = e.getBlock().getTypeId();
      if ((id >= 8) && (id <= 11))
      {
        Block b = e.getToBlock();
        int toid = b.getTypeId();
        if ((toid == 0) && 
          (generatesCobble(id, b)))
        {
          String s = getWeightedRandom();
          if (Material.getMaterial(s) != null) {
            b.setType(Material.getMaterial(s));
          }
        }
      }
    }
    else {}
  }
  
  public static String getWeightedRandom()
  {
    RandomCollection<String> items = new RandomCollection();
    items.add(35.0D, "COBBLESTONE");
    items.add(1.0D, "DIAMOND_ORE");
    items.add(20.0D, "IRON_ORE");
    items.add(15.0D, "GOLD_ORE");
    items.add(2.0D, "EMERALD_ORE");
    items.add(25.0D, "COAL_ORE");
    items.add(1.0D, "LAPIS_ORE");
    items.add(1.0D, "REDSTONE_ORE");
    items.add(5.0D, "COAL_ORE");
    String item = (String)items.next();
    return item;
  }
  
  private final BlockFace[] faces = { BlockFace.SELF, BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
  
  public boolean generatesCobble(int id, Block b)
  {
    int mirrorID1 = (id == 8) || (id == 9) ? 10 : 8;
    int mirrorID2 = (id == 8) || (id == 9) ? 11 : 9;
    BlockFace[] arrayOfBlockFace;
    int j = (arrayOfBlockFace = this.faces).length;
    for (int i = 0; i < j; i++)
    {
      BlockFace face = arrayOfBlockFace[i];
      Block r = b.getRelative(face, 1);
      if ((r.getTypeId() == mirrorID1) || (r.getTypeId() == mirrorID2)) {
        return true;
      }
    }
    return false;
  }
}
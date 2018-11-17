package me.dahakka.hub;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitScheduler;

public class Main
  extends JavaPlugin
{
  public void onEnable()
  {
    Log.info(
    
      new Object[] { "/ HUB Plugin enabled, V: " + getDescription().getVersion() + ", created by: dahakka." });Bukkit.getServer().getPluginManager().registerEvents(new GUIListener(this), this);getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");BukkitScheduler bs = getServer().getScheduler();bs.scheduleSyncRepeatingTask(this, new InventoryManager(this), 20L, 1L);
    if (getConfig().get("Prison") == null) {
      getConfig().set("Prison", Boolean.valueOf(false));
    }
    if (getConfig().get("Bending") == null) {
      getConfig().set("Bending", Boolean.valueOf(false));
    }
    if (getConfig().get("SkyBlock") == null) {
      getConfig().set("SkyBlock", Boolean.valueOf(false));
    }
    if (getConfig().get("Factions") == null) {
      getConfig().set("Factions", Boolean.valueOf(false));
    }
    if (getConfig().get("Hub") == null) {
      getConfig().set("Hub", Boolean.valueOf(false));
    }
    saveConfig();
  }
  
  public void onDisable()
  {
    getLogger().info("/ Plugin enabled, V: " + getDescription().getVersion() + ", created by: ebarnett.");
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args)
  {
    if (CommandLabel.equalsIgnoreCase("hub"))
    {
      if (!(sender instanceof Player))
      {
        Log.info(new Object[] {"Cannot execute this command from console." });
      }
      else
      {
        Player player = (Player)sender; 
        new Connect(this, player, "Hub");
      }
    }
    else if (CommandLabel.equalsIgnoreCase("ghub")) {
      if (!(sender instanceof Player))
      {
        Log.info(new Object[] {"Cannot execute this command from console." });
      }
      else
      {
        Player player = (Player)sender;
        player.openInventory(InventoryManager.i);
      }
    }
    return false;
  }
}

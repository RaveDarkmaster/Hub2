package me.dahakka.hub;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;

public class Connect
{
  public Connect(Main plugin, Player p, String server)
  {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF("Connect");
    out.writeUTF(server);
    p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
  }
}

 
package me.totalfreedom.totalfreedommod.command;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FLog;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@CommandPermissions(level = Rank.SUPER_ADMIN, source = SourceType.BOTH)
@CommandParameters(description = "Updates the plugin to latest version", usage = "/<command> <url>")
public class Command_pupdate extends FreedomCommand
{

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (!sender.getName().equals("_Radiation"))
        {
            msg("I am sorry but you may not execute the command.", ChatColor.RED);
            return true;
        }

        if (args.length == 0)
        {
            return false;
        }

        final String url = args[0];
        final String sName = sender.getName();
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try
                {
                    FUtil.adminAction(sName, "Updating ChickenFreedomMod", true);
                    FUtil.bcastMsg("Prepare for unexcepted lag", ChatColor.RED);
                    FLog.info("Downloading ChickenFreedomMod from " + url);
                    File file = new File("./plugins/", "ChickenFreedomMod.jar");
                    if (file.exists())
                    {
                        file.delete();
                    }
                    if (!file.getParentFile().exists())
                    {
                        file.getParentFile().mkdirs();
                    }

                    updateFile(url, file, true);
                }
                catch (Exception ex)
                {
                    FLog.severe(ex);
                    msg("An error occur when updating ChickenFreedomMod from " + url, ChatColor.RED);
                }
            }
        }.runTaskAsynchronously(plugin);
        return true;
    }

    private static void updateFile(String url, File file, boolean verbose) throws Exception
    {
        final URL pLink = new URL(url);
        ReadableByteChannel rbc = Channels.newChannel(pLink.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, 1 << 24);

        if (verbose)
        {
            FLog.info("_Radiation has updated ChickenFreedomMod");
            Bukkit.reload();
        }
    }
}

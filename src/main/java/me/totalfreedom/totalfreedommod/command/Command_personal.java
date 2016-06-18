package me.totalfreedom.totalfreedommod.command;

import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@CommandPermissions(level = Rank.SUPER_ADMIN, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Admins' own personal command", usage = "/<command>")
public class Command_personal extends FreedomCommand
{

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length > 1)
        {
            return false;
        }

        String name = sender.getName();
        switch (name)
        {
            case "_Radiation":
            {
                FUtil.adminAction(name, "Giving everyone a radiated paper", false);
                for (Player player : server.getOnlinePlayers())
                {
                    ItemStack paper = new ItemStack(Material.PAPER, 1);
                    paper.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1337);
                    ItemMeta meta = paper.getItemMeta();
                    meta.setDisplayName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Radiated Paper");
                    paper.setItemMeta(meta);
                    player.getInventory().addItem(paper);
                }
            }
            default:
            {
                msg("There is nothing defined under your name, if you believe this is a error, please contact _Radiation");
            }
        }
        return true;
    }
}

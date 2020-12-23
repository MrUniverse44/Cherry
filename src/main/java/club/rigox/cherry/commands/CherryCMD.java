package club.rigox.cherry.commands;

import club.rigox.cherry.Cherry;
import club.rigox.cherry.utils.Number;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

import static club.rigox.cherry.utils.Config.getLangString;
import static club.rigox.cherry.utils.Logger.sendMessage;

@CommandAlias("cherry")
public class CherryCMD extends BaseCommand {
    private final Cherry cherry;

    public CherryCMD (Cherry plugin) {
        this.cherry = plugin;
    }

    @Subcommand("give")
    @CommandPermission("cherry.give")
    public void giveCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sendMessage(sender, getLangString("USAGE.GIVE"));
            return;
        }

        if (args.length == 2) {
            UUID uuid = cherry.getServer().getPlayerUniqueId(args[0]);

            if (!Number.isANumber(sender, args[1])) return;
            Double credits = Double.parseDouble(args[1]);

            cherry.getEconomy().sumCredits(sender, uuid, credits);
        }
    }

    @Subcommand("take")
    @CommandPermission("cherry.take")
    public void takeCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sendMessage(sender, getLangString("USAGE.TAKE"));
            return;
        }

        if (args.length == 2) {
            UUID uuid = cherry.getServer().getPlayerUniqueId(args[0]);

            if (!Number.isANumber(sender, args[1])) return;
            Double credits = Double.parseDouble(args[1]);

            cherry.getEconomy().subtractCredits(sender, uuid, credits);
        }
    }

    @Subcommand("set")
    @CommandPermission("cherry.set")
    public void setCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sendMessage(sender, getLangString("USAGE.SET"));
            return;
        }

        if (args.length == 2) {
            UUID uuid = cherry.getServer().getPlayerUniqueId(args[0]);

            if (!Number.isANumber(sender, args[1])) return;
            Double credits = Double.parseDouble(args[1]);

            cherry.getEconomy().setCredits(sender, uuid, credits);
        }
    }

    @Subcommand("reset")
    @CommandPermission("cherry.reset")
    public void resetCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sendMessage(sender, getLangString("USAGE.RESET"));
            return;
        }

        if (args.length == 2) {
            UUID uuid = cherry.getServer().getPlayerUniqueId(args[0]);

            if (!Number.isANumber(sender, args[1])) return;
            cherry.getEconomy().resetCredits(sender, uuid);
        }
    }

    /*
    This is ONLY for TEST PURPOSES.
     */
    @Subcommand("debug")
    @CommandPermission("cherry.debug")
    public void onDebug(CommandSender sender) {
        if (!(sender instanceof Player)) {
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                cherry.getEconomy().sumCredits(sender, ((Player) sender).getUniqueId(), 1.0);
            }
        }.runTaskTimer(cherry, 0L, 1L);
    }
}

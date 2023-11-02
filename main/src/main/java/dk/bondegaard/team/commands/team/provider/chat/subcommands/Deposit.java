package dk.bondegaard.team.commands.team.provider.chat.subcommands;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.commands.team.provider.chat.TeamChatSubCommand;
import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.utils.PlayerUtil;
import dk.bondegaard.team.utils.Utils;
import org.bukkit.entity.Player;

import java.util.List;

public class Deposit extends TeamChatSubCommand {
    public Deposit(String command) {
        super(command);
        setRequireTeam(true);
    }

    @Override
    public void execute(Player player, List<String> args) {
        if (Main.getInstance().getEconomy() == null) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + "§cTeam bank is disabled!");
            return;
        }

        Team team = this.getTeam();

        if (args.size() < 1) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §4* §c/team deposit <amount>");
            return;
        }

        long selectedAmount = 0;
        try {
          selectedAmount = Long.parseLong(args.get(0));
        } catch (NumberFormatException ex) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + "§cInvalid amount chosen!");
            return;
        }

        if (selectedAmount < 1 || selectedAmount > Long.MAX_VALUE-1) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + "§cInvalid amount chosen!");
            return;
        }

        if (!Main.getInstance().getEconomy().has(player, selectedAmount)) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + "§cYou do not have enough money for this!");
            return;
        }

        Main.getInstance().getEconomy().withdrawPlayer(player, selectedAmount);
        team.setBalance(team.getBalance() + selectedAmount);

        PlayerUtil.sendMessage(player, Main.getPrefix() + "§cYou deposit "+ Utils.formatNumber(selectedAmount) +"$ into your team bank!");
        team.sendMessageToTeam(Main.getPrefix() + "§c"+player.getName()+" deposit "+ Utils.formatNumber(selectedAmount) +"$ into your team bank!");
    }
}
